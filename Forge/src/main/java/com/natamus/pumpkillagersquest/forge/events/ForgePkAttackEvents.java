package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkAttackEvents;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ForgePkAttackEvents {
    @SubscribeEvent
    public static void onArrowShoot(ArrowNockEvent e) {
        PkAttackEvents.onArrowShoot(e.getBow(), e.getLevel(), e.getEntity(), e.getHand(), e.hasAmmo());
    }
}
