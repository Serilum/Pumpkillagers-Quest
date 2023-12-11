package com.natamus.pumpkillagersquest.neoforge.events.rendering;

import com.natamus.pumpkillagersquest.data.Constants;
import com.natamus.pumpkillagersquest.events.rendering.ClientRenderEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class NeoForgeClientRenderEvent {
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent e) {
		ClientRenderEvent.onClientTick(Constants.mc.level);
	}
}
