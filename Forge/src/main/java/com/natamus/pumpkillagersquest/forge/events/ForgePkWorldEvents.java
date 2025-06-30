package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.pumpkillagersquest.events.PkWorldEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ForgePkWorldEvents {
    @SubscribeEvent
    public static void onWorldUnload(LevelEvent.Unload e) {
        Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
        if (level == null) {
            return;
        }

        PkWorldEvents.onWorldUnload((ServerLevel)level);
    }
}
