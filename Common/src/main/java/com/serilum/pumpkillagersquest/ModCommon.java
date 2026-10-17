package com.serilum.pumpkillagersquest;

import com.natamus.collective.translations.ServerTranslationPack;
import com.serilum.pumpkillagersquest.config.ConfigHandler;
import com.serilum.pumpkillagersquest.util.Reference;
import com.serilum.pumpkillagersquest.util.SpookyHeads;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		SpookyHeads.initPumpkinHeadData();
		ServerTranslationPack.requireClientTranslations(Reference.NAME);
	}
}
