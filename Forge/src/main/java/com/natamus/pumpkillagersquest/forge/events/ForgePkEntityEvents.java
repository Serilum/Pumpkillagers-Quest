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
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;
import net.minecraftforge.fml.common.Mod;

public class ForgePkEntityEvents {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgePkEntityEvents.class);
	}

    @SubscribeEvent
    public static boolean onEntityJoin(EntityJoinLevelEvent e) {
        Level level = e.getLevel();
        if (level.isClientSide) {
            return false;
        }

        if (!PkEntityEvents.onEntityJoin(e.getEntity(), (ServerLevel)level)) {
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent e) {
        PkEntityEvents.onEntityLeave(e.getEntity(), e.getLevel());
    }

    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent e) {
        Player player = e.getEntity();
        PkEntityEvents.onItemPickup(player.level(), player, e.getItem().getItem());
    }

    @SubscribeEvent
    public static boolean onEntityHitByLightning(EntityStruckByLightningEvent e) {
        Entity entity = e.getEntity();
        if (!PkEntityEvents.onEntityHitByLightning(entity.level(), entity, e.getLightning())) {
            return true;
        }
        return false;
    }
}
