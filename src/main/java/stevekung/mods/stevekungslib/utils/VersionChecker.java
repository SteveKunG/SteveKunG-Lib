package stevekung.mods.stevekungslib.utils;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.versions.mcp.MCPVersion;
import org.apache.maven.artifact.versioning.ComparableVersion;

import java.util.Map;

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

    public void printInfo(EntityPlayerSP player)
    {
        if (this.failed)
        {
            player.sendMessage(JsonUtils.create("Unable to check latest version of " + this.formatText(TextFormatting.DARK_RED, this.modName) + "!, Please check your internet connection.").setStyle(JsonUtils.red().setBold(true)));
            return;
        }
        if (this.latestVersion != null)
        {
            String text = String.format("New version of %s is available %s for %s", this.formatText(TextFormatting.AQUA, this.modName), this.formatText(TextFormatting.GREEN, "v" + this.latestVersion), this.formatText(TextFormatting.BLUE, "Minecraft " + MCPVersion.getMCVersion()));
            player.sendMessage(JsonUtils.create(text));
            player.sendMessage(JsonUtils.create("Download Link ").setStyle(JsonUtils.style().setColor(TextFormatting.YELLOW)).appendSibling(JsonUtils.create("[CLICK HERE]").setStyle(JsonUtils.style().setColor(TextFormatting.RED).setHoverEvent(JsonUtils.hover(HoverEvent.Action.SHOW_TEXT, JsonUtils.create("Click Here!").setStyle(JsonUtils.style().setColor(TextFormatting.DARK_GREEN)))).setClickEvent(JsonUtils.click(ClickEvent.Action.OPEN_URL, this.url)))));
        }
    }

    private String formatText(TextFormatting color, String text)
    {
        return color + text + TextFormatting.WHITE;
    }
}