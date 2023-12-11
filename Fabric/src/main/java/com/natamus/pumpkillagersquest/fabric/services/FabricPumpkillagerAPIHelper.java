package com.natamus.pumpkillagersquest.fabric.services;

import com.natamus.pumpkillagersquest.services.helpers.PumpkillagerAPIHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

public class FabricPumpkillagerAPIHelper implements PumpkillagerAPIHelper {
    @Override
    public void pumpkillagerSummonEvent(Player summoner, Villager pumpkillager, BlockPos pos, String typeString) {
        // No Fabric hook exists / is needed yet.
    }
}