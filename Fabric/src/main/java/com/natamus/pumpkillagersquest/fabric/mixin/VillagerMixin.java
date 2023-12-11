package com.natamus.pumpkillagersquest.fabric.mixin;

import com.natamus.pumpkillagersquest.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Villager.class, priority = 1001)
public abstract class VillagerMixin extends AbstractVillager {
    public VillagerMixin(EntityType<? extends AbstractVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At(value = "HEAD"), cancellable = true)
    public void die(DamageSource pCause, CallbackInfo ci) {
        Villager villager = (Villager)(Object)this;
        if (Util.isPumpkillager(villager) || Util.isPrisoner(villager)) {
            super.die(pCause);
            ci.cancel();
        }
    }

    @Inject(method = "onReputationEventFrom(Lnet/minecraft/world/entity/ai/village/ReputationEventType;Lnet/minecraft/world/entity/Entity;)V", at = @At(value = "HEAD"), cancellable = true)
    public void onReputationEventFrom(ReputationEventType pType, Entity pTarget, CallbackInfo ci) {
        Villager villager = (Villager)(Object)this;
        if (Util.isPumpkillager(villager) || Util.isPrisoner(villager)) {
            ci.cancel();
        }
    }
}
