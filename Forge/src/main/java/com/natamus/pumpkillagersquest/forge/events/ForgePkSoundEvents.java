package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkSoundEvents;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgePkSoundEvents {
	public static void registerEventsInBus() {
		// BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgePkSoundEvents.class);

		PlaySoundEvent.BUS.addListener(ForgePkSoundEvents::onSoundEvent);
	}

    @SubscribeEvent
    public static void onSoundEvent(PlaySoundEvent e) {
        PkSoundEvents.onSoundEvent(e.getEngine(), e.getOriginalSound());
    }
}