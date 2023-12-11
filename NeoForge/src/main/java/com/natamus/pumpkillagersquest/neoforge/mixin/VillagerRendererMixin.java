package com.natamus.pumpkillagersquest.neoforge.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.pumpkillagersquest.util.Util;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = VillagerRenderer.class, priority = 1001)
public class VillagerRendererMixin {
	@Inject(method = "scale(Lnet/minecraft/world/entity/npc/Villager;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", at = @At(value = "HEAD"), cancellable = true)
	protected void scale(Villager pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime, CallbackInfo ci) {
		if (Util.isPumpkillager(pLivingEntity)) {
			ItemStack footStack = pLivingEntity.getItemBySlot(EquipmentSlot.FEET);
			if (footStack.getItem().equals(Items.BARRIER)) {
				String scaleFloatString = footStack.getHoverName().getString();

				try {
					float scale = Float.parseFloat(scaleFloatString);
					pMatrixStack.scale(scale, scale, scale);
					ci.cancel();
				} catch (NumberFormatException ignored) { }
			}
		}
	}
}
