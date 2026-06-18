package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkLivingEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgePkLivingEvents {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgePkLivingEvents.class);
	}

    @SubscribeEvent
    public static boolean onDamagePumpkillager(LivingHurtEvent e) {
        LivingEntity livingEntity = e.getEntity();

        float damageAmount = e.getAmount();
        float newAmount = PkLivingEvents.onDamagePumpkillager(livingEntity.level(), livingEntity, e.getSource(), damageAmount);

        if (damageAmount != newAmount) {
            e.setAmount(newAmount);

            if (newAmount == 0F) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static boolean onLivingDeath(LivingDeathEvent e) {
        if (!PkLivingEvents.onLivingDeath(e.getEntity(), e.getSource(), 0F)) {
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void onEntityItemDrop(LivingDropsEvent e) {
        LivingEntity livingEntity = e.getEntity();
        PkLivingEvents.onEntityItemDrop(livingEntity.level(), livingEntity, e.getSource());
    }
}
