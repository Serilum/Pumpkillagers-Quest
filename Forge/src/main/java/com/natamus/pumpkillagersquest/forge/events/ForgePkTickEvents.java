package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkTickEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;
import net.minecraftforge.fml.common.Mod;

public class ForgePkTickEvents {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgePkTickEvents.class);
	}

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent.Post e) {
        PkTickEvents.onLevelTick(e.level);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent.Post e) {
        Player player = e.player;
        Level level = player.level();
        if (level.isClientSide) {
            return;
        }

        PkTickEvents.onPlayerTick((ServerLevel)level, (ServerPlayer)player);
    }
}
