package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.pumpkillagersquest.events.PkBlockEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgePkBlockEvents {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgePkBlockEvents.class);
	}

    @SubscribeEvent
    public static boolean onBlockBreak(BlockEvent.BreakEvent e) {
        Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
        if (level.isClientSide) {
            return false;
        }

        if (!PkBlockEvents.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null)) {
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public static boolean onCandleClick(PlayerInteractEvent.RightClickBlock e) {
        if (!PkBlockEvents.onCandleClick(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), e.getHitVec())) {
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent e) {
        Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
        if (level == null) {
            return;
        }

        Entity entity = e.getEntity();
        if (!(entity instanceof LivingEntity)) {
            return;
        }

        PkBlockEvents.onBlockPlace(level, e.getPos(), e.getPlacedBlock(), (LivingEntity)entity, null);
    }
}
