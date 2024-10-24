package com.natamus.pumpkillagersquest.neoforge.events;

import com.natamus.pumpkillagersquest.events.PkLivingEvents;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class NeoForgePkLivingEvents {
	@SubscribeEvent
	public static void onDamagePumpkillager(LivingIncomingDamageEvent e) {
		LivingEntity livingEntity = e.getEntity();

		float damageAmount = e.getAmount();
		float newAmount = PkLivingEvents.onDamagePumpkillager(livingEntity.level(), livingEntity, e.getSource(), damageAmount);

		if (damageAmount != newAmount) {
			e.setAmount(newAmount);

			if (newAmount == 0F) {
				e.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent e) {
		if (!PkLivingEvents.onLivingDeath(e.getEntity(), e.getSource(), 0F)) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onEntityItemDrop(LivingDropsEvent e) {
		LivingEntity livingEntity = e.getEntity();
		PkLivingEvents.onEntityItemDrop(livingEntity.level(), livingEntity, e.getSource());
	}
}
