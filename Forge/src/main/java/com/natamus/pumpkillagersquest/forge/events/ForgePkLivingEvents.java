package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkLivingEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgePkLivingEvents {
    @SubscribeEvent
    public void onDamagePumpkillager(LivingHurtEvent e) {
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
    public void onLivingDeath(LivingDeathEvent e) {
        if (!PkLivingEvents.onLivingDeath(e.getEntity(), e.getSource(), 0F)) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityItemDrop(LivingDropsEvent e) {
        LivingEntity livingEntity = e.getEntity();
        PkLivingEvents.onEntityItemDrop(livingEntity.level(), livingEntity, e.getSource());
    }
}
