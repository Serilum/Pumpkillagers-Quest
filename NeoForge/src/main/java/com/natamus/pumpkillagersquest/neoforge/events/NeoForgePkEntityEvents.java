package com.natamus.pumpkillagersquest.neoforge.events;

import com.natamus.pumpkillagersquest.events.PkEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

@EventBusSubscriber
public class NeoForgePkEntityEvents {
	@SubscribeEvent
	public static void onEntityJoin(EntityJoinLevelEvent e) {
		Level level = e.getLevel();
		if (level.isClientSide) {
			return;
		}

		if (!PkEntityEvents.onEntityJoin(e.getEntity(), (ServerLevel)level)) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onEntityLeave(EntityLeaveLevelEvent e) {
		PkEntityEvents.onEntityLeave(e.getEntity(), e.getLevel());
	}

	@SubscribeEvent
	public static void onItemPickup(ItemEntityPickupEvent.Post e) {
		Player player = e.getPlayer();
		PkEntityEvents.onItemPickup(player.level(), player, e.getItemEntity().getItem());
	}

	@SubscribeEvent
	public static void onEntityHitByLightning(EntityStruckByLightningEvent e) {
		Entity entity = e.getEntity();
		if (!PkEntityEvents.onEntityHitByLightning(entity.level(), entity, e.getLightning())) {
			e.setCanceled(true);
		}
	}
}
