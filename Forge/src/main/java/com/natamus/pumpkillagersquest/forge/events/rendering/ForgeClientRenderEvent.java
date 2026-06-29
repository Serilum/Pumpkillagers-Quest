package com.natamus.pumpkillagersquest.forge.events.rendering;

import com.natamus.pumpkillagersquest.data.Constants;
import com.natamus.pumpkillagersquest.events.rendering.ClientRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeClientRenderEvent {
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent e) {
		if (!e.phase.equals(TickEvent.Phase.START)) {
			return;
		}

		ClientRenderEvent.onClientTick(Constants.mc.level);
	}
}
