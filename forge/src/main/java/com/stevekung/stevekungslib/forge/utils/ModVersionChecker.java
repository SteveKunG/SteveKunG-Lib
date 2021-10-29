package com.stevekung.stevekungslib.forge.utils;

import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.utils.TextComponentUtils;
import com.stevekung.stevekungslib.utils.client.ClientUtils;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;

public class ModVersionChecker
{
    private final String modId;
    private ModContainer container;
    private String latestVersion;
    private String url;
    private boolean failed;
    private boolean hasChecked;

    public ModVersionChecker(String modId)
    {
        this.modId = modId;
    }

    public void startCheck()
    {
        var container = ModList.get().getModContainerById(this.modId);
        container.ifPresent(modContainer -> this.container = modContainer);
        var result = VersionChecker.getResult(this.container.getModInfo());

        if (result.changes() != null && result.changes().size() > 0)
        {
            for (var entry : result.changes().entrySet())
            {
                var version = entry.getKey();

                if (result.status() == VersionChecker.Status.OUTDATED)
                {
                    this.latestVersion = version.toString();
                    this.url = result.url();
                    SteveKunGLib.LOGGER.debug("Mod {} latest version is {} with URL {}", this.modId, this.latestVersion, this.url);
                }
            }
        }
    }

    public void checkFail()
    {
        var result = VersionChecker.getResult(this.container.getModInfo());
        this.failed = result.status() == VersionChecker.Status.FAILED;
    }

    public void printInfo()
    {
        var event = ",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + this.url + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"Check out \",{\"text\":\"" + this.container.getModInfo().getDisplayName() + "\",\"color\":\"green\"},\" download page\"]}";
        var title = "[\"[\",{\"text\":\"" + this.container.getModInfo().getDisplayName() + "\",\"color\":\"green\"" + event + "},\"]\"";
        var unableToCheck = title + ",{\"text\":\" Unable to check latest version! Please check logs to see more info.\",\"color\":\"red\"}]";
        var newVersion = title + ",{\"text\":\" v" + this.latestVersion + " is now available! \"},{\"text\":\"[Click Here]\",\"color\":\"yellow\"" + event + "}]";

        if (this.failed)
        {
            ClientUtils.printClientMessage(TextComponentUtils.fromJson(unableToCheck));
            return;
        }
        if (this.latestVersion != null)
        {
            ClientUtils.printClientMessage(TextComponentUtils.fromJson(newVersion));
        }
    }

    public boolean hasChecked()
    {
        return this.hasChecked;
    }

    public void setChecked(boolean checked)
    {
        this.hasChecked = checked;
    }
}