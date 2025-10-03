package com.natamus.pumpkillagersquest.forge.services;

import com.natamus.pumpkillagersquest.forge.api.PumpkillagerSummonEvent;
import com.natamus.pumpkillagersquest.services.helpers.PumpkillagerAPIHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

public class ForgePumpkillagerAPIHelper implements PumpkillagerAPIHelper {
    @Override
    public void pumpkillagerSummonEvent(Player summoner, Villager pumpkillager, BlockPos pos, String typeString) {
        PumpkillagerSummonEvent.BUS.post(new PumpkillagerSummonEvent(summoner, pumpkillager, pos, typeString));
    }
}