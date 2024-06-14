package com.natamus.pumpkillagersquest.events;

import com.mojang.authlib.GameProfile;
import com.natamus.collective.data.GlobalVariables;
import com.natamus.collective.functions.MessageFunctions;
import com.natamus.pumpkillagersquest.config.ConfigHandler;
import com.natamus.pumpkillagersquest.pumpkillager.Conversations;
import com.natamus.pumpkillagersquest.pumpkillager.Manage;
import com.natamus.pumpkillagersquest.pumpkillager.RitualCheck;
import com.natamus.pumpkillagersquest.util.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Set;
import java.util.UUID;

public class PkBlockEvents {
    public static boolean onBlockBreak(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (level.isClientSide) {
            return true;
        }

        Block block = state.getBlock();
        if (block instanceof SkullBlock || block instanceof WallSkullBlock) {
            if (!player.isCreative()) {
                BlockEntity posBlockEntity = level.getBlockEntity(pos);

                if (posBlockEntity instanceof SkullBlockEntity) {
                    SkullBlockEntity skullBlockEntity = (SkullBlockEntity)posBlockEntity;

                    if (skullBlockEntity != null) {
                        ResolvableProfile resolvableProfile = skullBlockEntity.getOwnerProfile();
                        if (resolvableProfile != null) {
                            GameProfile gameProfile = resolvableProfile.gameProfile();
                            if (gameProfile != null) {
                                UUID uuid = gameProfile.getId();

                                if (uuid != null) {
                                    String headId = uuid.toString();

                                    String correctHeadName = "";
                                    for (String headName : SpookyHeads.allHeadData.keySet()) {
                                        String headNameid = SpookyHeads.allHeadData.get(headName).getFirst();

                                        if (headId.equals(headNameid)) {
                                            correctHeadName = headName;
                                            break;
                                        }
                                    }

                                    if (!correctHeadName.equals("")) {
                                        ItemStack namedHeadItemStack = SpookyHeads.getSpookyOrPumpkinHead(correctHeadName, 1);

                                        if (namedHeadItemStack != null) {
                                            level.destroyBlock(pos, false);
                                            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), namedHeadItemStack));
                                            return false;
                                        }
                                    }
                                }
                            }}
                    }
                }
            }
        }

        if (Data.globalProcessedPoss.contains(pos)) {
            for (Entity ea : level.getEntities(null, new AABB(pos.getX()-2, pos.getY()-2, pos.getZ()-2, pos.getX()+2, pos.getY()+2, pos.getZ()+2))) {
                if (ea instanceof Villager) {
                    if (Util.isPumpkillager(ea)) {
                        Villager pumpkillager = (Villager)ea;

                        MessageFunctions.sendMessage(player, Component.literal(""));
                        Conversations.addMessage(level, pumpkillager, player, "How dare you try to steal my blocks, " + player.getName().getString() + ".", ChatFormatting.WHITE, 0);
                        Conversations.addMessage(level, pumpkillager, player, "I will be back.", ChatFormatting.WHITE, 0);

                        pumpkillager.getTags().add(Reference.MOD_ID + ".preventactions");

                        Manage.initiateCharacterLeave(level, pumpkillager);
                        return false;
                    }
                }
            }
        }

        if (!ConfigHandler.enablePumpkillagerSpawnDuringCreative) {
            if (player.isCreative()) {
                return true;
            }
        }

        Set<String> playerTags = player.getTags();
        if (!playerTags.contains(Reference.MOD_ID + ".completedquest") && !playerTags.contains(Reference.MOD_ID + ".unleashed")) {
            if (Util.isPumpkinBlock(block)) {
                if (!Data.pumpkillagerPlayerTarget.containsValue(player)) {
                    if (Util.pumpkinBlockIsClear(level, pos)) {
                        if (GlobalVariables.random.nextDouble() <= ConfigHandler.pumpkillagerSpawnChance) {
                            boolean foundQuestBook = false;

                            Inventory inventory = player.getInventory();
                            for (int i = 0; i < inventory.getContainerSize(); i++) {
                                ItemStack inventoryStack = inventory.getItem(i);
                                if (inventoryStack.getItem() instanceof WrittenBookItem) {
                                    String bookName = inventoryStack.getHoverName().getString();
                                    if (bookName.equals(Data.questBookName)) {
                                        foundQuestBook = true;
                                        break;
                                    }
                                }
                            }

                            if (foundQuestBook) {
                                return true;
                            }

                            Manage.spawnPumpkillager(level, player, pos);
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean onCandleClick(Level level, Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitVec) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();
        if (item instanceof FlintAndSteelItem) {
            if (!player.getTags().contains(Reference.MOD_ID + ".cansummonfinalform")) {
                return true;
            }

            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();
            if (!(block instanceof CandleBlock)) {
                return true;
            }

            CandleBlock candleBlock = (CandleBlock) block;
            boolean isLit = state.getValue(CandleBlock.LIT);
            if (isLit) {
                return true;
            }

            BlockPos pumpkinPos = null;
            for (BlockPos aroundPos : BlockPos.betweenClosed(pos.getX() - 3, pos.getY(), pos.getZ() - 3, pos.getX() + 3, pos.getY(), pos.getZ() + 3)) {
                if (Util.isPumpkinBlock(level.getBlockState(aroundPos).getBlock())) {
                    pumpkinPos = aroundPos.immutable();
                    break;
                }
            }

            if (pumpkinPos == null) {
                return true;
            }

            RitualCheck.checkRitualFinalSummoning(level, player, pumpkinPos, pos);
        }
        else if (item instanceof WrittenBookItem) {
            String bookName = itemStack.getDisplayName().getString();
            if (bookName.contains("Pumpkillager")) {
                if (level.isClientSide) {
                    GenerateStructure.generateClientRitualVision(level, player, pos, itemStack);
                }

                return false;
            }
        }
        return true;
    }

    public static boolean onBlockPlace(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        if (level.isClientSide) {
            return true;
        }

        if (!(livingEntity instanceof Player)) {
            return true;
        }

        if (!Util.isPumpkinBlock(blockState.getBlock())) {
            return true;
        }

        Player player = (Player)livingEntity;
        if (!player.getTags().contains(Reference.MOD_ID + ".cansummonfinalform")) {
            return true;
        }

        RitualCheck.checkRitualFinalSummoning(level, player, blockPos, null);
        return true;
    }
}
