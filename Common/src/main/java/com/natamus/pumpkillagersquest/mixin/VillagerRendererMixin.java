package com.natamus.pumpkillagersquest.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.pumpkillagersquest.util.Util;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = VillagerRenderer.class, priority = 1001)
public class VillagerRendererMixin {
    @Inject(method = "scale(Lnet/minecraft/client/renderer/entity/state/VillagerRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V", at = @At(value = "HEAD"), cancellable = true)
    protected void scale(VillagerRenderState villagerRenderState, PoseStack poseStack, CallbackInfo ci) {
        if (Util.isPumpkillager(villagerRenderState.customName, villagerRenderState.leftHandItem)) {
			ItemStack leftHandStack = villagerRenderState.leftHandItem;
			if (!leftHandStack.isEmpty()) {
				if (leftHandStack.getItem().equals(Items.BARRIER)) {
					String scaleFloatString = leftHandStack.getHoverName().getString();

					try {
						float scale = Float.parseFloat(scaleFloatString);
						poseStack.scale(scale, scale, scale);
						ci.cancel();
					} catch (NumberFormatException ignored) {
					}
				}
			}
		}
    }
}
