package com.natamus.pumpkillagersquest;

import com.natamus.collective.check.RegisterMod;
import com.natamus.pumpkillagersquest.neoforge.config.IntegrateNeoForgeConfig;
import com.natamus.pumpkillagersquest.neoforge.events.*;
import com.natamus.pumpkillagersquest.neoforge.events.rendering.NeoForgeClientRenderEvent;
import com.natamus.pumpkillagersquest.util.Reference;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Reference.MOD_ID)
public class ModNeoForge {
	
	public ModNeoForge(IEventBus modEventBus) {
		modEventBus.addListener(this::loadComplete);

		setGlobalConstants();
		ModCommon.init();

		IntegrateNeoForgeConfig.registerScreen(ModLoadingContext.get());

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {
		NeoForge.EVENT_BUS.register(NeoForgePkAttackEvents.class);
		NeoForge.EVENT_BUS.register(NeoForgePkBlockEvents.class);
		NeoForge.EVENT_BUS.register(NeoForgePkEntityEvents.class);
		NeoForge.EVENT_BUS.register(NeoForgePkLivingEvents.class);
		NeoForge.EVENT_BUS.register(NeoForgePkOtherEvents.class);
		NeoForge.EVENT_BUS.register(NeoForgePkPlayerEvents.class);
		NeoForge.EVENT_BUS.register(NeoForgePkTickEvents.class);
		NeoForge.EVENT_BUS.register(NeoForgePkWorldEvents.class);

		if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
			NeoForge.EVENT_BUS.register(NeoForgeClientRenderEvent.class);
			NeoForge.EVENT_BUS.register(NeoForgePkSoundEvents.class);
		}
	}

	private static void setGlobalConstants() {

	}
}