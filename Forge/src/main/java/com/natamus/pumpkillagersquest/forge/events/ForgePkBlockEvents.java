package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.pumpkillagersquest.events.PkBlockEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgePkBlockEvents {
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent e) {
        Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getWorld());
        if (level.isClientSide) {
            return;
        }

        if (!PkBlockEvents.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null)) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onCandleClick(PlayerInteractEvent.RightClickBlock e) {
        if (!PkBlockEvents.onCandleClick(e.getWorld(), e.getPlayer(), e.getHand(), e.getPos(), e.getHitVec())) {
            e.setCanceled(true);
            e.setCancellationResult(InteractionResult.FAIL);
        }
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent e) {
        Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getWorld());
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
