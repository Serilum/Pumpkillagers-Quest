package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgePkEntityEvents {
    @SubscribeEvent
    public void onEntityJoin(EntityJoinLevelEvent e) {
        Level level = e.getLevel();
        if (level.isClientSide) {
            return;
        }

        if (!PkEntityEvents.onEntityJoin(e.getEntity(), (ServerLevel)level)) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityLeave(EntityLeaveLevelEvent e) {
        PkEntityEvents.onEntityLeave(e.getEntity(), e.getLevel());
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent e) {
        Player player = e.getEntity();
        PkEntityEvents.onItemPickup(player.level, player, e.getItem().getItem());
    }

    @SubscribeEvent
    public void onEntityHitByLightning(EntityStruckByLightningEvent e) {
        Entity entity = e.getEntity();
        if (!PkEntityEvents.onEntityHitByLightning(entity.level, entity, e.getLightning())) {
            e.setCanceled(true);
        }
    }
}
