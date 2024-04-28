package com.natamus.pumpkillagersquest.forge.events.rendering;

import com.natamus.pumpkillagersquest.data.Constants;
import com.natamus.pumpkillagersquest.events.rendering.ClientRenderEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ForgeClientRenderEvent {
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent e) {
		ClientRenderEvent.onClientTick(Constants.mc.level);
	}
}
