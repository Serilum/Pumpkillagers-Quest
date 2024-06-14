package com.natamus.pumpkillagersquest.util;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective.data.GlobalVariables;
import com.natamus.collective.functions.BlockPosFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Util {
    public static Pair<Player, MutableComponent> modifyMessagePair(Pair<Player, MutableComponent> pair, MutableComponent newMessageComponent) {
        return new Pair<Player, MutableComponent>(pair.getFirst(), newMessageComponent);
    }

    public static boolean isPumpkillager(Entity entity) {
        if (entity instanceof Villager) {
            Villager villager = (Villager)entity;
            if (villager.getName().getString().contains(Data.pumpkillagerName)) {
                return true;
            }

            try {
                ItemStack footStack = villager.getItemBySlot(EquipmentSlot.FEET);
                if (!footStack.isEmpty()) {
                    return footStack.getItem().equals(Items.BARRIER);
                }
            }
            catch (NullPointerException ignored) { }
        }
        return false;
    }
    public static boolean isPrisoner(Entity entity) {
        return entity instanceof Villager && entity.getTags().contains(Reference.MOD_ID + ".prisoner");
    }
    public static boolean prisonerIsKnown(Villager character, Player player) {
        return character.getTags().contains(Reference.MOD_ID + ".isknownto." + player.getName().getString());
    }

    public static boolean isPumpkinBlock(Block block) {
        return block instanceof PumpkinBlock || block instanceof CarvedPumpkinBlock;
    }

    public static boolean pumpkinBlockIsClear(Level level, BlockPos pumpkinPos) {
        BlockPos abovePumpkinPos = pumpkinPos.above().immutable();
        List<BlockPos> toCheckPositions = new ArrayList<BlockPos>(Stream.concat(getSidePositions(pumpkinPos.immutable()).stream(), getSidePositions(abovePumpkinPos).stream()).toList());
        toCheckPositions.add(abovePumpkinPos);

        for (BlockPos aroundPos : toCheckPositions) {
            BlockState aroundState = level.getBlockState(aroundPos);
            MapColor material = aroundState.getMapColor(level, aroundPos);
            if (aroundState.getLightBlock(level, aroundPos) < 15 && !GlobalVariables.surfacematerials.contains(material)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public static List<BlockPos> getSidePositions(BlockPos pos) {
        return Arrays.asList(pos.north(), pos.east(), pos.south(), pos.west());
    }

    public static BlockPos getPrisonerCampCoordinates(Level level, Villager pumpkillager, Player player) {
        String coordinates = "";
        if (level.isClientSide) {
            return null;
        }

        return BlockPosFunctions.getRandomCoordinatesInNearestUngeneratedChunk((ServerLevel)level, pumpkillager.blockPosition());
    }

    public static void spawnLightning(Level level, BlockPos pos, LivingEntity target, Player player, boolean healTarget) {
        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
        lightningbolt.moveTo(Vec3.atBottomCenterOf(pos));
        level.addFreshEntity(lightningbolt);

        if (player != null) {
            player.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 5.0F, 1.0F);
        }

        Scheduler.scheduleFireExtuingish(level, pos, target, healTarget);
    }

    public static InputStream getSchematicsInputStream(MinecraftServer minecraftServer, String schematicName) {
        return getSchematicsInputStream(minecraftServer, schematicName, ".schem");
    }
    public static InputStream getSchematicsInputStream(MinecraftServer minecraftServer, String schematicName, String fileExtension) {
        try {
            Optional<Resource> resourceOptional = minecraftServer.getResourceManager().getResource(ResourceLocation.parse(Reference.MOD_ID + ":schematics/" + schematicName + fileExtension));
            if (resourceOptional.isPresent()) { Resource resource = resourceOptional.get(); return resource.open(); }
        }
        catch (IOException ignored) { }
        return null;
    }

    public static float roundFloat(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public static EntityDimensions getDefaultVillagerDimensions() {
        return EntityDimensions.scalable(0.6F, 1.95F);
    }
}
