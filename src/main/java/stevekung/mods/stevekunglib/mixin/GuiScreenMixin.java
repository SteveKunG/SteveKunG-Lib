package stevekung.mods.stevekunglib.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import stevekung.mods.stevekunglib.client.gui.GuiChatRegistry;
import stevekung.mods.stevekunglib.client.gui.IEntityHoverChat;

@Mixin(GuiScreen.class)
public abstract class GuiScreenMixin
{
    @Shadow
    protected abstract void drawHoveringText(String text, int x, int y);

    @Shadow
    protected abstract void drawHoveringText(List<String> textLines, int x, int y);

    @Shadow
    protected abstract void renderToolTip(ItemStack stack, int x, int y);

    @Shadow
    private Minecraft mc;

    @Shadow
    private int width;

    @Inject(method = "mouseReleased(III)V", at = @At("RETURN"))
    private void mouseReleased(int mouseX, int mouseY, int mouseButton, CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseReleased(mouseX, mouseY, mouseButton));
    }

    @Inject(method = "mouseClickMove(IIIJ)V", at = @At("RETURN"))
    private void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick, CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));
    }

    @Inject(method = "actionPerformed(Lnet/minecraft/client/gui/GuiButton;)V", at = @At("RETURN"))
    private void actionPerformed(GuiButton button, CallbackInfo info)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.actionPerformed(button));
    }

    @Overwrite
    public void handleComponentHover(ITextComponent component, int mouseX, int mouseY)
    {
        if (component != null && component.getStyle().getHoverEvent() != null)
        {
            HoverEvent hover = component.getStyle().getHoverEvent();

            if (hover.getAction() == HoverEvent.Action.SHOW_ITEM)
            {
                ItemStack itemStack = ItemStack.EMPTY;

                try
                {
                    NBTBase nbt = JsonToNBT.getTagFromJson(hover.getValue().getUnformattedText());

                    if (nbt instanceof NBTTagCompound)
                    {
                        itemStack = new ItemStack((NBTTagCompound)nbt);
                    }
                }
                catch (NBTException e) {}

                if (itemStack.isEmpty())
                {
                    this.drawHoveringText(TextFormatting.RED + "Invalid Item!", mouseX, mouseY);
                }
                else
                {
                    this.renderToolTip(itemStack, mouseX, mouseY);
                }
            }
            else if (hover.getAction() == HoverEvent.Action.SHOW_ENTITY)
            {
                if (this.mc.gameSettings.advancedItemTooltips)
                {
                    try
                    {
                        NBTTagCompound compound = JsonToNBT.getTagFromJson(hover.getValue().getUnformattedText());
                        List<String> list = new ArrayList<>();
                        String name = compound.getString("name");

                        for (IEntityHoverChat entity : GuiChatRegistry.getEntityHoverChatList())
                        {
                            name = entity.addEntityComponent(name);
                        }

                        list.add(name);

                        if (compound.hasKey("type", 8))
                        {
                            String s = compound.getString("type");
                            list.add("Type: " + s);
                        }
                        list.add(compound.getString("id"));
                        this.drawHoveringText(list, mouseX, mouseY);
                    }
                    catch (NBTException e)
                    {
                        this.drawHoveringText(TextFormatting.RED + "Invalid Entity!", mouseX, mouseY);
                    }
                }
            }
            else if (hover.getAction() == HoverEvent.Action.SHOW_TEXT)
            {
                this.drawHoveringText(this.mc.fontRenderer.listFormattedStringToWidth(hover.getValue().getFormattedText(), Math.max(this.width / 2, 200)), mouseX, mouseY);
            }
            GlStateManager.disableLighting();
        }
    }
}