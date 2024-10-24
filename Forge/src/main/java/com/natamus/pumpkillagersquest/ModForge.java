package com.natamus.pumpkillagersquest;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.pumpkillagersquest.forge.config.IntegrateForgeConfig;
import com.natamus.pumpkillagersquest.forge.events.*;
import com.natamus.pumpkillagersquest.forge.events.rendering.ForgeClientRenderEvent;
import com.natamus.pumpkillagersquest.util.Reference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Reference.MOD_ID)
public class ModForge {
	
	public ModForge() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::loadComplete);

		setGlobalConstants();
		ModCommon.init();

		IntegrateForgeConfig.registerScreen(ModLoadingContext.get());

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {
		MinecraftForge.EVENT_BUS.register(new ForgePkAttackEvents());
		MinecraftForge.EVENT_BUS.register(new ForgePkBlockEvents());
		MinecraftForge.EVENT_BUS.register(new ForgePkEntityEvents());
		MinecraftForge.EVENT_BUS.register(new ForgePkLivingEvents());
		MinecraftForge.EVENT_BUS.register(new ForgePkOtherEvents());
		MinecraftForge.EVENT_BUS.register(new ForgePkPlayerEvents());
		MinecraftForge.EVENT_BUS.register(new ForgePkTickEvents());
		MinecraftForge.EVENT_BUS.register(new ForgePkWorldEvents());

		if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
			MinecraftForge.EVENT_BUS.register(new ForgeClientRenderEvent());
			MinecraftForge.EVENT_BUS.register(new ForgePkSoundEvents());
		}
	}

	private static void setGlobalConstants() {

	}
}