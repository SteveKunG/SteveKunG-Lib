package stevekung.mods.stevekunglib.core;

import java.util.Map;

import javax.annotation.Nullable;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import stevekung.mods.stevekunglib.utils.LoggerSL;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class SteveKunGLibPlugin implements IFMLLoadingPlugin
{
    static
    {
        LoggerSL.info("Initializing SteveKunG's Lib plugin!");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.stevekung's_lib.gui_screen.json");
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    @Nullable
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}
}