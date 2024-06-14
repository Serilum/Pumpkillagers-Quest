package com.natamus.pumpkillagersquest.neoforge.events;

import com.natamus.pumpkillagersquest.events.PkAttackEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ArrowNockEvent;

@EventBusSubscriber
public class NeoForgePkAttackEvents {
	@SubscribeEvent
	public static void onArrowShoot(ArrowNockEvent e) {
		PkAttackEvents.onArrowShoot(e.getBow(), e.getLevel(), e.getEntity(), e.getHand(), e.hasAmmo());
	}
}
