package com.serilum.pumpkillagersquest;

import com.natamus.collective.fabric.callbacks.CollectiveSoundEvents;
import com.serilum.pumpkillagersquest.events.PkSoundEvents;
import com.serilum.pumpkillagersquest.events.PkTickEvents;
import com.serilum.pumpkillagersquest.events.rendering.ClientRenderEvent;
import net.fabricmc.api.ClientModInitializer;
import com.serilum.pumpkillagersquest.util.Reference;
import com.natamus.collective.check.ShouldLoadCheck;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { 
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		registerEvents();
	}
	
	private void registerEvents() {
		// PkSoundEvents
		CollectiveSoundEvents.SOUND_PLAY.register((SoundEngine soundEngine, SoundInstance soundInstance) -> {
			return PkSoundEvents.onSoundEvent(soundEngine, soundInstance);
		});

		// PkTickEvents
		ClientTickEvents.START_WORLD_TICK.register((ClientLevel level) -> {
			PkTickEvents.onLevelTick(level);
			ClientRenderEvent.onClientTick(level);
		});
	}
}
