package com.natamus.pumpkillagersquest.neoforge.events.rendering;

import com.natamus.pumpkillagersquest.data.Constants;
import com.natamus.pumpkillagersquest.events.rendering.ClientRenderEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class NeoForgeClientRenderEvent {
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent e) {
		ClientRenderEvent.onClientTick(Constants.mc.level);
	}
}
