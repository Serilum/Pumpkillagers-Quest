package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.pumpkillagersquest.events.PkTickEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgePkTickEvents {
    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent e) {
        if (!e.phase.equals(TickEvent.Phase.END)) {
            return;
        }

        PkTickEvents.onLevelTick(e.level);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!e.phase.equals(TickEvent.Phase.END)) {
            return;
        }

        Player player = e.player;
        Level level = player.level;
        if (level.isClientSide) {
            return;
        }

        PkTickEvents.onPlayerTick((ServerLevel)level, (ServerPlayer)player);
    }
}
