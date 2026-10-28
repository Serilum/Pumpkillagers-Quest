package com.serilum.pumpkillagersquest.neoforge.events.rendering;

import com.serilum.pumpkillagersquest.data.Constants;
import com.serilum.pumpkillagersquest.events.rendering.ClientRenderEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class NeoForgeClientRenderEvent {
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre e) {
		ClientRenderEvent.onClientTick(Constants.mc.level);
	}
}
