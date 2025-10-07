package com.natamus.pumpkillagersquest.services;

import com.natamus.pumpkillagersquest.services.helpers.PumpkillagerAPIHelper;
import com.natamus.pumpkillagersquest.util.Reference;

import java.util.ServiceLoader;

public class Services {
    public static final PumpkillagerAPIHelper PUMPKILLAGER_API = load(PumpkillagerAPIHelper.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("[" + Reference.NAME + "] Failed to load service for " + clazz.getName() + "."));
    }
}