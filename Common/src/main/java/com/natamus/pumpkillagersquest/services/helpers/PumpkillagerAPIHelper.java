package com.natamus.pumpkillagersquest.services.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

public interface PumpkillagerAPIHelper {
    void pumpkillagerSummonEvent(Player summoner, Villager pumpkillager, BlockPos pos, String typeString);
}