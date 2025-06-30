package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkAttackEvents;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;
import net.minecraftforge.fml.common.Mod;

public class ForgePkAttackEvents {
	public static void registerEventsInBus() {
		// BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgePkAttackEvents.class);

		ArrowNockEvent.BUS.addListener(ForgePkAttackEvents::onArrowShoot);
	}

    @SubscribeEvent
    public static void onArrowShoot(ArrowNockEvent e) {
        PkAttackEvents.onArrowShoot(e.getBow(), e.getLevel(), e.getEntity(), e.getHand(), e.hasAmmo());
    }
}
