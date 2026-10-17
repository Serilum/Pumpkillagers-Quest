package com.serilum.pumpkillagersquest.pumpkillager;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective.functions.EntityFunctions;
import com.serilum.pumpkillagersquest.config.ConfigHandler;
import com.serilum.pumpkillagersquest.services.Services;
import com.serilum.pumpkillagersquest.util.Data;
import com.serilum.pumpkillagersquest.util.Reference;
import com.serilum.pumpkillagersquest.util.SpookyHeads;
import com.serilum.pumpkillagersquest.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class Manage {
	public static void spawnPumpkillager(Level level, Player player, BlockPos pos) {
		spawnPumpkillager(level, player, pos, 0, 0);
	}
	public static void spawnPumpkillager(Level level, Player player, BlockPos pos, int conversationId, int placementId) {
		if (!Data.allPumpkillagers.containsKey(level)) {
			Data.allPumpkillagers.put(level, new CopyOnWriteArrayList<Villager>());
		}

		Villager pumpkillager = createPumpkillager(level, pos , player, null, SpookyHeads.getJackoLantern(1), ChatFormatting.YELLOW, "1.0", false);

		if (conversationId == 0) {
			pumpkillager.entityTags().add(Reference.MOD_ID + ".initialencounter");
		}

		level.addFreshEntity(pumpkillager);

		pumpkillager.lookAt(EntityAnchorArgument.Anchor.EYES, player.position());

		placePumpkillagerBlocks(level, pos, placementId);

		Services.PUMPKILLAGER_API.pumpkillagerSummonEvent(player, pumpkillager, pos, conversationId == 0 ? "INITIAL_SUMMON" : "POST_RITUAL");

		Conversations.startTalking(level, pumpkillager, player, conversationId);
	}

	public static Villager createPumpkillager(Level level, BlockPos pos, Player player, ResourceKey<VillagerProfession> profession, ItemStack headStack, ChatFormatting nameColour, String scaleFloatString, boolean replace) {
		Villager oldpumpkillager = null;
		Set<String> oldtags = Set.of("");

		if (replace) {
			for (Entity ea : level.getEntities(null, new AABB(pos.getX()-1, pos.getY()-1, pos.getZ()-1, pos.getX()+1, pos.getY()+1, pos.getZ()+1))) {
				if (Util.isPumpkillager(ea)) {
					oldpumpkillager = (Villager)ea;
					oldtags = oldpumpkillager.entityTags();
					break;
				}
			}
		}

		Villager pumpkillager = EntityTypes.VILLAGER.create(level, EntitySpawnReason.MOB_SUMMONED);

		if (profession == null) {
			pumpkillager.setVillagerData(pumpkillager.getVillagerData().withType(level.registryAccess(), VillagerType.SNOW));
		}
		else {
			pumpkillager.setVillagerData(pumpkillager.getVillagerData().withType(level.registryAccess(), VillagerType.SNOW).withProfession(level.registryAccess(), profession));
		}

		String pumpkillagerName = Data.pumpkillagerName;
		if (!scaleFloatString.equals("1.0")) {
			pumpkillagerName = Data.pumpkillagerName + "|" + scaleFloatString;
		}

		pumpkillager.setPos(pos.getX()+0.5, pos.getY(), pos.getZ()+0.5);
		pumpkillager.setItemSlot(EquipmentSlot.HEAD, headStack);
		pumpkillager.setCustomName(Component.literal(pumpkillagerName).withStyle(nameColour));
		pumpkillager.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, Integer.MAX_VALUE));
		pumpkillager.getBrain().removeAllBehaviors();
		pumpkillager.entityTags().add(Reference.MOD_ID + ".justadded");

		EntityFunctions.setEntitySize(pumpkillager, Util.getDefaultVillagerDimensions().scale(Float.parseFloat(scaleFloatString)), pumpkillager.getEyeHeight());

		EntityFunctions.forceSetHealth(pumpkillager, (float)ConfigHandler.finalBossMaxHealth);

		for (String tag : oldtags) {
			pumpkillager.entityTags().add(tag);
		}

		if (oldpumpkillager != null) {
			pumpkillager.setYHeadRot(oldpumpkillager.getYHeadRot());
			pumpkillager.setYBodyRot(oldpumpkillager.yBodyRot);
			pumpkillager.setXRot(oldpumpkillager.getXRot());
			pumpkillager.setYRot(oldpumpkillager.getYRot());
		}

		ItemStack scaleBarrier = new ItemStack(Items.BARRIER, 64);
		scaleBarrier.set(DataComponents.CUSTOM_NAME, Component.literal(scaleFloatString));
		pumpkillager.setItemInHand(InteractionHand.OFF_HAND, scaleBarrier);

		try {
			float scaleFloat = Float.parseFloat(scaleFloatString);

			AttributeInstance scaleAttributeInstance = pumpkillager.getAttribute(Attributes.SCALE);
			if (scaleAttributeInstance != null) {
				scaleAttributeInstance.setBaseValue(scaleFloat);
			}
		}
		catch (NumberFormatException ignored) { }

		Data.pumpkillagerPlayerTarget.put(pumpkillager, player);
		Data.allPumpkillagers.get(level).add(pumpkillager);
		Data.pumpkillagerPositions.put(pumpkillager, pumpkillager.position());

		if (oldpumpkillager != null) {
			Data.pumpkillagerPlayerTarget.remove(oldpumpkillager);
			Data.allPumpkillagers.get(level).remove(oldpumpkillager);
			Data.pumpkillagerPositions.remove(oldpumpkillager);

			oldpumpkillager.remove(RemovalReason.DISCARDED);
		}

		return pumpkillager;
	}

	public static void placePumpkillagerBlocks(Level level, BlockPos pos, int placementId) {
		List<Pair<BlockPos, BlockState>> pumpkillagerStates = new ArrayList<>();

		if (placementId == 0) {
			BlockPos below = pos.below().immutable();

			pumpkillagerStates.add(new Pair<>(below, Blocks.CRYING_OBSIDIAN.defaultBlockState()));

			for (BlockPos ap : Util.getSidePositions(below)) {
				pumpkillagerStates.add(new Pair<>(ap, Blocks.OBSIDIAN.defaultBlockState()));
			}

			for (BlockPos ap : Util.getSidePositions(pos)) {
				pumpkillagerStates.add(new Pair<>(ap, Blocks.BLACKSTONE_WALL.defaultBlockState()));
			}

			for (BlockPos ap : Util.getSidePositions(pos.above())) {
				pumpkillagerStates.add(new Pair<>(ap.immutable(), Blocks.SOUL_LANTERN.defaultBlockState()));
			}
		}
		else if (placementId == 1) {
			pumpkillagerStates.add(new Pair<>(pos.below().immutable(), Blocks.OBSIDIAN.defaultBlockState()));
		}
		else {
			return;
		}

		List<BlockPos> processedPoss = new ArrayList<>();
		List<Pair<BlockPos, BlockState>> currentStates = new ArrayList<>();
		List<Pair<BlockPos, BlockState>> statesToPlace = new ArrayList<>();

		for (Pair<BlockPos, BlockState> pumpkillagerState : pumpkillagerStates) {
			BlockPos statePos = pumpkillagerState.getFirst();
			if (level.getBlockEntity(statePos) != null) {
				continue;
			}

			currentStates.add(new Pair<>(statePos, level.getBlockState(statePos)));
			statesToPlace.add(pumpkillagerState);
			processedPoss.add(statePos);
		}

		// Top to bottom, so blocks that need support (stems, crops) are replaced before what they stand on.
		for (int i = statesToPlace.size() - 1; i >= 0; i--) {
			level.setBlock(statesToPlace.get(i).getFirst(), statesToPlace.get(i).getSecond(), 3);
		}

		Data.globalProcessedPoss.addAll(processedPoss);
		Data.previousStates.put(pos, currentStates);
	}

	public static void resetPlacedBlocks(Level level, Villager pumpkillager) {
		resetPlacedBlocks(level, pumpkillager.blockPosition());
	}
	public static void resetPlacedBlocks(Level level, BlockPos pos) {
		if (Data.previousStates.containsKey(pos)) {
			Collections.reverse(Data.previousStates.get(pos));
			for (Pair<BlockPos, BlockState> replacePair : Data.previousStates.get(pos)) {
				try {
					level.setBlock(replacePair.getFirst(), replacePair.getSecond(), 3);
				}
				catch (NullPointerException ignored) { }
				Data.globalProcessedPoss.remove(pos);
			}
			Data.previousStates.remove(pos);
		}
	}

	public static void yeetLivingEntityIntoSky(Level level, LivingEntity livingEntity) {
		BlockPos pos = livingEntity.blockPosition();

		livingEntity.entityTags().add(Reference.MOD_ID + ".removed");
		livingEntity.setNoGravity(true);
		Data.entitiesToYeet.get(level).add(livingEntity);
	}

	public static void pumpkillagerMovedWrongly(Level level, Entity pumpkillager, Player player) {
		pumpkillagerMovedWrongly(level, (Villager)pumpkillager, player);
	}
	public static void pumpkillagerMovedWrongly(Level level, Villager pumpkillager, Player player) {
		if (!Data.pumpkillagerPositions.containsKey(pumpkillager)) {
			return;
		}

		if (!pumpkillager.entityTags().contains(Reference.MOD_ID + ".isleaving")) {
			Vec3 pumpkillagerVec = Data.pumpkillagerPositions.get(pumpkillager);
			pumpkillager.setPos(pumpkillagerVec);
		}
	}

	public static void initiateCharacterLeave(Level level, Villager character) {
		character.entityTags().add(Reference.MOD_ID + ".isleaving");

		if (!level.isClientSide()) {
			ServerBossEvent serverBossEvent = Data.pumpkillagerBossEvents.get(character);
			if (serverBossEvent != null) {
				serverBossEvent.setProgress(0);
				serverBossEvent.setVisible(false);
				serverBossEvent.removeAllPlayers();

				Data.pumpkillagerBossEvents.remove(character);
			}
		}

		if (Util.isPumpkillager(character)) {
			Manage.resetPlacedBlocks(level, character);
		}
		Manage.yeetLivingEntityIntoSky(level, character);
	}
}
