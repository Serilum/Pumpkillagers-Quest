package com.natamus.pumpkillagersquest.forge.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

/**
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS} whenever a player summons the
 * pumpkillager. The event is not cancelable and does not have a result.
 * Author: noeppi-noeppi and Serilum
 */
public class PumpkillagerSummonEvent extends Event {

    private final Player summoner;
    private final Villager pumpkillager;
    private final BlockPos pos;
    private final Type type;

    public PumpkillagerSummonEvent(Player summoner, Villager pumpkillager, BlockPos pos, Type type) {
        this.summoner = summoner;
        this.pumpkillager = pumpkillager;
        this.pos = pos.immutable();
        this.type = type;
    }
    public PumpkillagerSummonEvent(Player summoner, Villager pumpkillager, BlockPos pos, String typeString) {
        this.summoner = summoner;
        this.pumpkillager = pumpkillager;
        this.pos = pos.immutable();
        this.type = getTypeByString(typeString);
    }

    /**
     * Gets the player, who summoned the pumpkillager.
     */
    public Player getSummoner() {
        return summoner;
    }

    /**
     * Gets the pumpkillager entity.
     */
    public Villager getPumpkillager() {
        return pumpkillager;
    }

    /**
     * Gets the center block pos of the summoning ritual.
     */
    public BlockPos getPos() {
        return pos;
    }

    /**
     * Gets the type of pumpkillager that is summoned.
     */
    public Type getType() {
        return type;
    }

    public Type getTypeByString(String typeString) {
        return switch (typeString) {
            case "INITIAL_SUMMON" -> Type.INITIAL_SUMMON;
            case "POST_RITUAL" -> Type.POST_RITUAL;
            case "FINAL_BOSS" -> Type.FINAL_BOSS;
            default -> null;
        };
    }

    public enum Type {
        INITIAL_SUMMON,
        POST_RITUAL,
        FINAL_BOSS
    }
}