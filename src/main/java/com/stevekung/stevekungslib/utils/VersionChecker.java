package com.stevekung.stevekungslib.utils;

import java.util.Map;

import org.apache.maven.artifact.versioning.ComparableVersion;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.versions.mcp.MCPVersion;

public class VersionChecker
{
    private final Object mod;
    private final String modName;
    private final String url;
    private String latestVersion;
    private boolean failed;

    public VersionChecker(Object mod, String modName, String url)
    {
        this.mod = mod;
        this.modName = modName;
        this.url = url;
    }

    public void startCheck()
    {
        if (ModList.get().getModContainerByObject(this.mod).isPresent())
        {
            return;
        }

        net.minecraftforge.fml.VersionChecker.CheckResult result = net.minecraftforge.fml.VersionChecker.getResult(ModList.get().getModContainerByObject(this.mod).get().getModInfo());

        for (Map.Entry<ComparableVersion, String> entry : result.changes.entrySet())
        {
            ComparableVersion version = entry.getKey();

            if (result.status == net.minecraftforge.fml.VersionChecker.Status.OUTDATED)
            {
                this.latestVersion = version.toString();
            }
        }
    }

    public void startCheckIfFailed()
    {
        if (ModList.get().getModContainerByObject(this.mod).isPresent())
        {
            return;
        }
        net.minecraftforge.fml.VersionChecker.CheckResult result = net.minecraftforge.fml.VersionChecker.getResult(ModList.get().getModContainerByObject(this.mod).get().getModInfo());
        this.failed = result.status == net.minecraftforge.fml.VersionChecker.Status.FAILED || result.status == net.minecraftforge.fml.VersionChecker.Status.PENDING;
    }

    public void printInfo(ClientPlayerEntity player)
    {
        if (this.failed)
        {
            player.sendMessage(JsonUtils.create("Unable to check latest version of " + this.formatText(TextFormatting.DARK_RED, this.modName) + "!, Please check your internet connection.").func_240701_a_(TextFormatting.RED, TextFormatting.BOLD), Util.field_240973_b_);
            return;
        }
        if (this.latestVersion != null)
        {
            String text = String.format("New version of %s is available %s for %s", this.formatText(TextFormatting.AQUA, this.modName), this.formatText(TextFormatting.GREEN, "v" + this.latestVersion), this.formatText(TextFormatting.BLUE, "Minecraft " + MCPVersion.getMCVersion()));
            player.sendMessage(JsonUtils.create(text), Util.field_240973_b_);
            player.sendMessage(JsonUtils.create("Download Link ").func_240699_a_(TextFormatting.YELLOW).func_230529_a_(JsonUtils.create("[CLICK HERE]").func_240700_a_(style -> style.func_240712_a_(TextFormatting.RED).func_240716_a_(JsonUtils.hover(HoverEvent.Action.field_230550_a_, JsonUtils.create("Click Here!").func_240699_a_(TextFormatting.DARK_GREEN))).func_240715_a_(JsonUtils.click(ClickEvent.Action.OPEN_URL, this.url)))), Util.field_240973_b_);
        }
    }

    private String formatText(TextFormatting color, String text)
    {
        return color + text + TextFormatting.WHITE;
    }
}