package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.pumpkillagersquest.cmds.CommandPumpkillager;
import com.natamus.pumpkillagersquest.events.PkOtherEvents;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.level.PistonEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;
import net.minecraftforge.fml.common.Mod;

public class ForgePkOtherEvents {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgePkOtherEvents.class);
	}

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent e) {
        CommandPumpkillager.register(e.getDispatcher());
    }

    @SubscribeEvent
    public static void onTNTExplode(ExplosionEvent.Detonate e) {
        Explosion explosion = e.getExplosion();
        PkOtherEvents.onTNTExplode(e.getLevel(), explosion.getDirectSourceEntity(), explosion);
    }

    @SubscribeEvent
    public static boolean onPistonMove(PistonEvent.Pre e) {
        Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
        if (level == null) {
            return false;
        }

        if (!PkOtherEvents.onPistonMove(level, e.getPos(), e.getDirection(), e.getPistonMoveType().isExtend)) {
            return true;
        }
        return false;
    }
}
