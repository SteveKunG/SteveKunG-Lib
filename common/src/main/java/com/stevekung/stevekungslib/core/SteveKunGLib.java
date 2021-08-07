package com.stevekung.stevekungslib.core;

import com.stevekung.stevekungslib.proxy.LibClientProxy;
import com.stevekung.stevekungslib.proxy.LibCommonProxy;
import com.stevekung.stevekungslib.utils.LoggerBase;
import dev.architectury.utils.EnvExecutor;

public class SteveKunGLib
{
    public static final String MOD_ID = "stevekungs_lib";
    public static final LoggerBase LOGGER = new LoggerBase("SteveKunG's Lib");

    public static void init()
    {
        EnvExecutor.getEnvSpecific(() -> LibClientProxy::new, () -> LibCommonProxy::new);
    }
}