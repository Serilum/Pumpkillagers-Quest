package com.natamus.pumpkillagersquest.neoforge.services;

import com.natamus.pumpkillagersquest.neoforge.api.PumpkillagerSummonEvent;
import com.natamus.pumpkillagersquest.services.helpers.PumpkillagerAPIHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;

public class NeoForgePumpkillagerAPIHelper implements PumpkillagerAPIHelper {
	@Override
	public void pumpkillagerSummonEvent(Player summoner, Villager pumpkillager, BlockPos pos, String typeString) {
		NeoForge.EVENT_BUS.post(new PumpkillagerSummonEvent(summoner, pumpkillager, pos, typeString));
	}
}