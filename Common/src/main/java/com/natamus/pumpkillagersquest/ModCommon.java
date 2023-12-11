package com.natamus.pumpkillagersquest;

import com.natamus.pumpkillagersquest.config.ConfigHandler;
import com.natamus.pumpkillagersquest.util.Data;
import com.natamus.pumpkillagersquest.util.SpookyHeads;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		Data.pumpkillagerMaxHealth = (float)ConfigHandler.finalBossMaxHealth;
		SpookyHeads.initPumpkinHeadData();
	}
}