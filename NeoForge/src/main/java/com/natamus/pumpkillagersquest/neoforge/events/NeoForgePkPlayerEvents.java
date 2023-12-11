package com.natamus.pumpkillagersquest.neoforge.events;

import com.natamus.pumpkillagersquest.events.PkPlayerEvents;
import net.minecraft.world.InteractionResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class NeoForgePkPlayerEvents {
	@SubscribeEvent
	public static void onCharacterInteract(PlayerInteractEvent.EntityInteract e) {
		if (PkPlayerEvents.onCharacterInteract(e.getEntity(), e.getLevel(), e.getHand(), e.getTarget(), null).equals(InteractionResult.SUCCESS)) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem e) {
		PkPlayerEvents.onRightClickItem(e.getEntity(), e.getLevel(), e.getHand());
	}
}
