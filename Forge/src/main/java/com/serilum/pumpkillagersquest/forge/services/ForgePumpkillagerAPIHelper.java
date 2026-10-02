package com.serilum.pumpkillagersquest.forge.services;

import com.serilum.pumpkillagersquest.forge.api.PumpkillagerSummonEvent;
import com.serilum.pumpkillagersquest.services.helpers.PumpkillagerAPIHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

public class ForgePumpkillagerAPIHelper implements PumpkillagerAPIHelper {
	@Override
	public void pumpkillagerSummonEvent(Player summoner, Villager pumpkillager, BlockPos pos, String typeString) {
		MinecraftForge.EVENT_BUS.post(new PumpkillagerSummonEvent(summoner, pumpkillager, pos, typeString));
	}
}