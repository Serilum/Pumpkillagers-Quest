package com.natamus.pumpkillagersquest.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Data {
    public static WeakHashMap<Level, CopyOnWriteArrayList<Villager>> allPumpkillagers = new WeakHashMap<Level, CopyOnWriteArrayList<Villager>>();
    public static WeakHashMap<Level, CopyOnWriteArrayList<Villager>> allPrisoners = new WeakHashMap<Level, CopyOnWriteArrayList<Villager>>();
    public static HashMap<Villager, Player> pumpkillagerPlayerTarget = new HashMap<Villager, Player>();
    public static WeakHashMap<Villager, Vec3> pumpkillagerPositions = new WeakHashMap<Villager, Vec3>();
    public static WeakHashMap<Villager, Vec3> prisonerPositions = new WeakHashMap<Villager, Vec3>();
    public static WeakHashMap<Villager, ServerBossEvent> pumpkillagerBossEvents = new WeakHashMap<Villager, ServerBossEvent>();
    public static WeakHashMap<Level, List<Pair<Player, MutableComponent>>> messagesToSend = new WeakHashMap<Level, List<Pair<Player, MutableComponent>>>();
    public static WeakHashMap<Level, CopyOnWriteArrayList<LivingEntity>> entitiesToYeet = new WeakHashMap<Level, CopyOnWriteArrayList<LivingEntity>>();
    public static WeakHashMap<Level, HashMap<Villager, Runnable>> lightningTasks = new WeakHashMap<Level, HashMap<Villager, Runnable>>();
    public static WeakHashMap<Level, List<LivingEntity>> spawnPumpkin = new WeakHashMap<Level, List<LivingEntity>>();

    public static HashMap<BlockPos, List<Pair<BlockPos, BlockState>>> previousStates = new HashMap<BlockPos, List<Pair<BlockPos, BlockState>>>();
    public static List<BlockPos> globalProcessedPoss = new ArrayList<BlockPos>();

    public static final TagKey<Block> pumpkinTag = TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "crops/pumpkin"));
    public static float pumpkillagerMaxHealth = 300F;
    public static final String pumpkillagerName = "The Pumpkillager";
    public static final String prisonerNameUnknown = "Prisoner";
    public static final String prisonerNameKnown = "Jax o'Saturn";
    public static final String questBookName = "Pumpkillager's Quest";
    public static final String stopPkBookName = "Stopping the Pumpkillager";
    public static final ChatFormatting defaultPumpkillagerColour = ChatFormatting.RED;
    public static final ChatFormatting defaultPrisonerColour = ChatFormatting.GOLD;

    public static MutableComponent addCharacterPrefix(Villager character, Player targetPlayer, MutableComponent component) {
        if (Util.isPumpkillager(character)) {
            return addCharacterPrefix("pumpkillager", targetPlayer, component);
        }
        else if (Util.isPrisoner(character)) {
            if (Util.prisonerIsKnown(character, targetPlayer)) {
                return addCharacterPrefix("knownprisoner", targetPlayer, component);
            }
            return addCharacterPrefix("unknownprisoner", targetPlayer, component);
        }
        return component;
    }
    public static MutableComponent addCharacterPrefix(String characterString, Player targetPlayer, MutableComponent component) {
        MutableComponent messageComponent = component;
        if (characterString.equals("pumpkillager")) {
            messageComponent = Component.literal("<").withStyle(ChatFormatting.WHITE).append(Component.literal(pumpkillagerName).withStyle(defaultPumpkillagerColour)).append(Component.literal(("> ")).withStyle(ChatFormatting.WHITE)).append(component);
        }
        else if (characterString.contains("prisoner")) {
            String prisonerName = prisonerNameUnknown;
            if (characterString.equals("knownprisoner")) {
                prisonerName = prisonerNameKnown;
            }
            messageComponent = Component.literal("<").withStyle(ChatFormatting.WHITE).append(Component.literal(prisonerName).withStyle(defaultPrisonerColour)).append(Component.literal(("> ")).withStyle(ChatFormatting.WHITE)).append(component);
        }
        else if (characterString.contains("Ghost")) {
            messageComponent = Component.literal("<").withStyle(ChatFormatting.WHITE).append(Component.literal(characterString).withStyle(ChatFormatting.RED)).append(Component.literal(("> ")).withStyle(ChatFormatting.WHITE)).append(component);
        }
        return messageComponent;
    }

    public static ItemStack getQuestbook(Level level) {
        String nbtstring = QuestData.questbookOneNbtString;

        try {
            CompoundTag newnbt = TagParser.parseTag(nbtstring);
            Optional<ItemStack> questBookOptional = ItemStack.parse(level.registryAccess(), newnbt);
            if (questBookOptional.isPresent()) {
                ItemStack questBook = questBookOptional.get();
                questBook.set(DataComponents.CUSTOM_NAME, Component.translatable(questBookName).withStyle(ChatFormatting.YELLOW));

                return questBook;
            }
        } catch (CommandSyntaxException ignored) { }

        System.out.println("Unable to get questbook.");
        return ItemStack.EMPTY;
    }

    public static ItemStack getStopPkbook(Level level) {
        String nbtstring = QuestData.questOneStoppingPumpkillagerBookNbtString;

        try {
            CompoundTag newnbt = TagParser.parseTag(nbtstring);
            Optional<ItemStack> questBookOptional = ItemStack.parse(level.registryAccess(), newnbt);
            if (questBookOptional.isPresent()) {
                ItemStack questBook = questBookOptional.get();
                questBook.set(DataComponents.CUSTOM_NAME, Component.translatable(questBookName).withStyle(ChatFormatting.YELLOW));

                return questBook;
            }
        } catch (CommandSyntaxException ignored) { }

        System.out.println("Unable to get questbook.");
        return ItemStack.EMPTY;
    }
}
