package com.serilum.pumpkillagersquest.neoforge.events;

import com.serilum.pumpkillagersquest.events.PkSoundEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

public class NeoForgePkSoundEvents {
	@SubscribeEvent
	public static void onSoundEvent(PlaySoundEvent e) {
		PkSoundEvents.onSoundEvent(e.getEngine(), e.getOriginalSound());
	}
}