package com.stevekung.stevekunglib.core;

import com.stevekung.stevekunglib.proxy.LibClientProxy;
import com.stevekung.stevekunglib.proxy.LibCommonProxy;
import com.stevekung.stevekunglib.utils.LoggerBase;
import dev.architectury.utils.EnvExecutor;

public class SteveKunGLib
{
    public static final String MOD_ID = "stevekung_lib";
    public static final LoggerBase LOGGER = new LoggerBase("SteveKunG's Lib");

    public static void init()
    {
        EnvExecutor.getEnvSpecific(() -> LibClientProxy::new, () -> LibCommonProxy::new);
    }
}