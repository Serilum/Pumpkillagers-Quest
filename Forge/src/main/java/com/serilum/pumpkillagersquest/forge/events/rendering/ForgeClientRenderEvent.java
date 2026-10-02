package com.serilum.pumpkillagersquest.forge.events.rendering;

import com.serilum.pumpkillagersquest.data.Constants;
import com.serilum.pumpkillagersquest.events.rendering.ClientRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeClientRenderEvent {
	public static void registerEventsInBus() {
		// BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeClientRenderEvent.class);

		TickEvent.ClientTickEvent.Pre.BUS.addListener(ForgeClientRenderEvent::onClientTick);
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent.Pre e) {
		ClientRenderEvent.onClientTick(Constants.mc.level);
	}
}
