package com.natamus.pumpkillagersquest.neoforge.events.rendering;

import com.natamus.pumpkillagersquest.data.Constants;
import com.natamus.pumpkillagersquest.events.rendering.ClientRenderEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class NeoForgeClientRenderEvent {
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre e) {
		ClientRenderEvent.onClientTick(Constants.mc.level);
	}
}
