package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkAttackEvents;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgePkAttackEvents {
    @SubscribeEvent
    public void onArrowShoot(ArrowNockEvent e) {
        PkAttackEvents.onArrowShoot(e.getBow(), e.getWorld(), e.getPlayer(), e.getHand(), e.hasAmmo());
    }
}
