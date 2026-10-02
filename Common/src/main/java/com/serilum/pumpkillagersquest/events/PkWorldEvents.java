package com.serilum.pumpkillagersquest.events;

import com.serilum.pumpkillagersquest.pumpkillager.Manage;
import com.serilum.pumpkillagersquest.util.Data;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.Villager;

public class PkWorldEvents {
	public static void onWorldUnload(ServerLevel level) {
		if (Data.allPumpkillagers.containsKey(level)) {
			for (Villager pumpkillager : Data.allPumpkillagers.get(level)) {
				BlockPos pos = pumpkillager.blockPosition();
				Manage.resetPlacedBlocks(level, pos);
			}
		}

		Data.allPumpkillagers.remove(level);
		Data.entitiesToYeet.remove(level);
		Data.messagesToSend.remove(level);
	}
}
