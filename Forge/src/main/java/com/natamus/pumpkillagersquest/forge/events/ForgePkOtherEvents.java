package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.pumpkillagersquest.cmds.CommandPumpkillager;
import com.natamus.pumpkillagersquest.events.PkOtherEvents;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.level.PistonEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgePkOtherEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent e) {
        CommandPumpkillager.register(e.getDispatcher());
    }

    @SubscribeEvent
    public static void onTNTExplode(ExplosionEvent.Detonate e) {
        Explosion explosion = e.getExplosion();
        PkOtherEvents.onTNTExplode(e.getLevel(), explosion.getExploder(), explosion);
    }

    @SubscribeEvent
    public static void onPistonMove(PistonEvent.Pre e) {
        Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
        if (level == null) {
            return;
        }

        if (!PkOtherEvents.onPistonMove(level, e.getPos(), e.getDirection(), e.getPistonMoveType().isExtend)) {
            e.setCanceled(true);
        }
    }
}
