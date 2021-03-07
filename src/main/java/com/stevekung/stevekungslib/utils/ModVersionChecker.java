package com.stevekung.stevekungslib.utils;

import java.util.Map;
import java.util.Optional;

import org.apache.maven.artifact.versioning.ComparableVersion;

import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.utils.client.ClientUtils;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;

public class ModVersionChecker
{
    private final String modId;
    private Optional<? extends ModContainer> container;
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
        this.container = ModList.get().getModContainerById(this.modId);
        VersionChecker.CheckResult result = VersionChecker.getResult(this.container.get().getModInfo());

        for (Map.Entry<ComparableVersion, String> entry : result.changes.entrySet())
        {
            ComparableVersion version = entry.getKey();

            if (result.status == VersionChecker.Status.OUTDATED)
            {
                this.latestVersion = version.toString();
                this.url = result.url;
                SteveKunGLib.LOGGER.debug("Mod {} latest version is {} with URL {}", this.modId, this.latestVersion, this.url);
            }
        }
    }

    public void checkFail()
    {
        VersionChecker.CheckResult result = VersionChecker.getResult(this.container.get().getModInfo());
        this.failed = result.status == VersionChecker.Status.FAILED;
    }

    public void printInfo()
    {
        String event = ",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + this.url + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"Check out \",{\"text\":\"" + this.container.get().getModInfo().getDisplayName() + "\",\"color\":\"green\"},\" download page\"]}";
        String title = "[\"[\",{\"text\":\"" + this.container.get().getModInfo().getDisplayName() + "\",\"color\":\"green\"" + event + "},\"]\"";
        String unableToCheck = title + ",{\"text\":\" Unable to check latest version! Please check logs to see more info.\",\"color\":\"red\"}]";
        String newVersion = title + ",{\"text\":\" v" + this.latestVersion + " is now available! \"},{\"text\":\"[Click Here]\",\"color\":\"yellow\"" + event + "}]";

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