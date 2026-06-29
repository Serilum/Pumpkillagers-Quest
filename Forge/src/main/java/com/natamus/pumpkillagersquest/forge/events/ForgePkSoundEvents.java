package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkSoundEvents;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgePkSoundEvents {
    @SubscribeEvent
    public static void onSoundEvent(PlaySoundEvent e) {
        PkSoundEvents.onSoundEvent(e.getEngine(), e.getOriginalSound());
    }
}