package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkPlayerEvents;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgePkPlayerEvents {
    @SubscribeEvent
    public void onCharacterInteract(PlayerInteractEvent.EntityInteract e) {
        if (PkPlayerEvents.onCharacterInteract(e.getEntity(), e.getLevel(), e.getHand(), e.getTarget(), null).equals(InteractionResult.SUCCESS)) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem e) {
        PkPlayerEvents.onRightClickItem(e.getEntity(), e.getLevel(), e.getHand());
    }
}
