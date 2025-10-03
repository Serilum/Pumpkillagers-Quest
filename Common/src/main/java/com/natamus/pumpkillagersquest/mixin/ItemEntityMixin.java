package com.natamus.pumpkillagersquest.mixin;

import com.natamus.pumpkillagersquest.util.Data;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemEntity.class, priority = 1001)
public abstract class ItemEntityMixin {
	@Inject(method = "hurtServer", at = @At(value = "HEAD"), cancellable = true)
	public void hurt(ServerLevel serverLevel, DamageSource damageSource, float damage, CallbackInfoReturnable<Boolean> cir) {
		if (damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
			String itemName = ((ItemEntity)(Object)this).getItem().getDisplayName().getString();
			if (itemName.contains(Data.questBookName) || itemName.contains(Data.stopPkBookName)) {
				cir.setReturnValue(false);
			}
		}
	}
}
