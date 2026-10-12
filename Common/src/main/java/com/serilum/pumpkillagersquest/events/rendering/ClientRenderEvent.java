package com.serilum.pumpkillagersquest.events.rendering;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective.functions.TaskFunctions;
import com.serilum.pumpkillagersquest.data.Constants;
import com.serilum.pumpkillagersquest.util.QuestData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.PlayerHeadBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientRenderEvent {
	public static int timeLeftRenderRitual = 0;
	public static BlockPos centerPos = null;
	public static Block centerBlock = null;
	public static Block ritualBlock = null;
	public static boolean shouldReset = false;

	public static List<Pair<BlockPos, BlockState>> previousStates = new ArrayList<Pair<BlockPos, BlockState>>();
	private static HashMap<BlockPos, SkullBlockEntity> playerHeadEntities = new HashMap<BlockPos, SkullBlockEntity>();
	private static HashMap<BlockPos, Block> renderedBlocks = new HashMap<>();
	private static List<BlockPos> changedPositions = new ArrayList<>();

	public static void setTemporaryRitualRender(BlockPos cP, Block cB, Block rB) {
		ClientLevel clientLevel = Constants.mc.level;

		TaskFunctions.enqueueImmediateTask(clientLevel, () -> {
			if (previousStates.size() > 0) {
				shouldReset = true;
			}

			centerPos = cP;
			centerBlock = cB;
			ritualBlock = rB;

			if (cP != null) {
				timeLeftRenderRitual = 100;
				Constants.mc.player.displayClientMessage(Component.translatable("collective.pumpkillagersquest.message.magicalbookshows").withStyle(ChatFormatting.GOLD), true);
			}
		}, true);
	}

	public static void onClientTick(ClientLevel clientLevel) {
		if (shouldReset) {
			for (Pair<BlockPos, BlockState> previousPair : previousStates) {
				BlockPos setPos = previousPair.getFirst();
				BlockState setState = previousPair.getSecond();

				if (worldChangedDuringRender(clientLevel, setPos, setState)) {
					continue;
				}

				clientLevel.setBlock(setPos, setState, 3);

				if (setState.getBlock() instanceof PlayerHeadBlock && playerHeadEntities.containsKey(setPos)) {
					clientLevel.setBlockEntity(playerHeadEntities.get(setPos));
				}
			}

			shouldReset = false;
			previousStates = new ArrayList<Pair<BlockPos, BlockState>>();
			playerHeadEntities = new HashMap<BlockPos, SkullBlockEntity>();
			renderedBlocks = new HashMap<>();
			changedPositions = new ArrayList<>();
			return;
		}

		if (timeLeftRenderRitual > 0 && centerBlock != null) {
			boolean setStates = previousStates.size() == 0;

			if (setStates) {
				previousStates.add(new Pair<BlockPos, BlockState>(centerPos.immutable(), clientLevel.getBlockState(centerPos)));
				previousStates.add(new Pair<BlockPos, BlockState>(centerPos.below().immutable(), clientLevel.getBlockState(centerPos.below())));
			}
			renderRitualBlock(clientLevel, centerPos, centerBlock.defaultBlockState());

			int i = 0;
			for (BlockPos posAround : BlockPos.betweenClosed(centerPos.getX()-3, centerPos.getY(), centerPos.getZ()-3, centerPos.getX()+3, centerPos.getY(), centerPos.getZ()+3)) {
				if (!posAround.equals(centerPos)) {
					if (setStates) {
						BlockState aroundState = clientLevel.getBlockState(posAround);
						previousStates.add(new Pair<BlockPos, BlockState>(posAround.immutable(), aroundState));

						if (aroundState.getBlock() instanceof PlayerHeadBlock) {
							playerHeadEntities.put(posAround.immutable(), (SkullBlockEntity)clientLevel.getBlockEntity(posAround));
						}
					}

					if (QuestData.questOneRitualBlockPositions.contains(i)) {
						BlockState state = ritualBlock.defaultBlockState();
						if (ritualBlock instanceof CandleBlock) {
							state = state.setValue(CandleBlock.LIT, true);
						}

						renderRitualBlock(clientLevel, posAround, state);
					} else {
						renderRitualBlock(clientLevel, posAround, Blocks.AIR.defaultBlockState());
					}
				}

				i++;
			}

			timeLeftRenderRitual-=1;
			if (timeLeftRenderRitual <= 0) {
				setTemporaryRitualRender(null, null, null);
			}
		}
	}

	private static void renderRitualBlock(ClientLevel clientLevel, BlockPos pos, BlockState ritualState) {
		if (changedPositions.contains(pos)) {
			return;
		}

		Block renderedBlock = renderedBlocks.get(pos);
		if (renderedBlock != null && !clientLevel.getBlockState(pos).getBlock().equals(renderedBlock)) {
			changedPositions.add(pos.immutable());
			return;
		}

		clientLevel.setBlock(pos, ritualState, 3);
		renderedBlocks.put(pos.immutable(), ritualState.getBlock());
	}

	private static boolean worldChangedDuringRender(ClientLevel clientLevel, BlockPos pos, BlockState previousState) {
		if (changedPositions.contains(pos)) {
			return true;
		}

		Block expectedBlock = renderedBlocks.getOrDefault(pos, previousState.getBlock());
		return !clientLevel.getBlockState(pos).getBlock().equals(expectedBlock);
	}

	/*public void onEntitySize(EntityEvent.Size e) {
		Entity entity = e.getEntity();
		if (!Util.isPumpkillager(entity)) {
			return;
		}

		String scaleFloatString = "";

		Villager pumpkillager = (Villager)entity;
		Component pumpkillagerComponent = pumpkillager.getName();
		String pumpkillagerName = pumpkillager.getName().getString();

		ItemStack footStack = pumpkillager.getItemBySlot(EquipmentSlot.FEET);
		if (footStack.getItem().equals(Items.BARRIER)) {
			scaleFloatString = footStack.getHoverName().getString();
		}
		else if (pumpkillagerName.contains("|")) {
			scaleFloatString = pumpkillagerName.split("\\|")[1];
		}

		float scale;
		try {
			scale = Float.parseFloat(scaleFloatString);
		}
		catch (NumberFormatException ex) { return; }

		EntityDimensions size = EntityType.VILLAGER.getDimensions().scale(scale);
		e.setNewSize(size, true);
	}*/
}
