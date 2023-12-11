package com.natamus.pumpkillagersquest.neoforge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.pumpkillagersquest.cmds.CommandPumpkillager;
import com.natamus.pumpkillagersquest.events.PkOtherEvents;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber
public class NeoForgePkOtherEvents {
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
