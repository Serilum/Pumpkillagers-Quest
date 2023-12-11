package com.natamus.pumpkillagersquest.neoforge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.pumpkillagersquest.events.PkWorldEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber
public class NeoForgePkWorldEvents {
	@SubscribeEvent
	public static void onWorldUnload(LevelEvent.Unload e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		PkWorldEvents.onWorldUnload((ServerLevel)level);
	}
}
