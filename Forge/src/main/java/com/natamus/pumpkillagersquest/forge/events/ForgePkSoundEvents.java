package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkSoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ForgePkSoundEvents {
    @SubscribeEvent
    public void onSoundEvent(PlaySoundEvent e) {
        PkSoundEvents.onSoundEvent(e.getEngine(), e.getOriginalSound());
    }
}