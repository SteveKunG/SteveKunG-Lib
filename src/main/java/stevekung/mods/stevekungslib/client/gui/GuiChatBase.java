package stevekung.mods.stevekungslib.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import stevekung.mods.stevekungslib.utils.client.ClientUtils;

public class GuiChatBase extends GuiChat
{
    public GuiChatBase() {}

    public GuiChatBase(String input)
    {
        super(input);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.initGui(this.buttons, this.width, this.height));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        super.render(mouseX, mouseY, partialTicks);
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.render(this.buttons, mouseX, mouseY, partialTicks));
    }

    @Override
    public void tick()
    {
        super.tick();
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.tick(this.buttons, this.width, this.height));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (this.suggestions != null && this.suggestions.keyPressed(keyCode, scanCode, modifiers))
        {
            return true;
        }
        else if (keyCode == 256)
        {
            this.mc.displayGuiScreen(null);
            return true;
        }
        else if (keyCode != 257 && keyCode != 335)
        {
            if (keyCode == 265)
            {
                this.getSentHistory(-1);
                return true;
            }
            else if (keyCode == 264)
            {
                this.getSentHistory(1);
                return true;
            }
            else if (keyCode == 266)
            {
                this.mc.ingameGUI.getChatGUI().func_194813_a(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
                GuiChatRegistry.getGuiChatList().forEach(IGuiChat::keyTypedScrollDown);
                return true;
            }
            else if (keyCode == 267)
            {
                this.mc.ingameGUI.getChatGUI().func_194813_a(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
                GuiChatRegistry.getGuiChatList().forEach(IGuiChat::keyTypedScrollUp);
                return true;
            }
            else
            {
                if (keyCode == 258)
                {
                    this.hasEdits = true;
                    this.showSuggestions();
                }
                return this.inputField.keyPressed(keyCode, scanCode, modifiers);
            }
        }
        else
        {
            String s = this.inputField.getText().trim();

            if (!s.isEmpty())
            {
                this.sendChatMessage(s);
            }
            this.mc.displayGuiScreen(null);
            return true;
        }
    }

    @Override
    public void getSentHistory(int msgPos)
    {
        super.getSentHistory(msgPos);
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.getSentHistory(msgPos));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseClicked(mouseX, mouseY, mouseButton));
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseReleased(mouseX, mouseY, state));
        return super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int mouseEvent, double dragX, double dragY)
    {
        GuiChatRegistry.getGuiChatList().forEach(gui -> gui.mouseDragged(mouseX, mouseY, mouseEvent, dragX, dragY));
        return super.mouseDragged(mouseX, mouseY, mouseEvent, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double wheel)
    {
        if (wheel > 1.0D)
        {
            wheel = 1.0D;
        }
        if (wheel < -1.0D)
        {
            wheel = -1.0D;
        }

        boolean otherConditions = false;

        for (IGuiChat chat : GuiChatRegistry.getGuiChatList())
        {
            otherConditions = chat.mouseScrolled(wheel);
        }

        if (otherConditions)
        {
            return true;
        }
        else if (this.suggestions != null && this.suggestions.mouseScrolled(wheel))
        {
            return true;
        }
        else
        {
            if (!ClientUtils.isShiftKeyDown())
            {
                wheel *= 7.0D;
            }
            this.mc.ingameGUI.getChatGUI().func_194813_a(wheel);
            return true;
        }
    }

    @Override
    protected void handleComponentHover(ITextComponent component, int mouseX, int mouseY)
    {
        if (component != null && component.getStyle().getHoverEvent() != null)
        {
            HoverEvent event = component.getStyle().getHoverEvent();

            if (event.getAction() == HoverEvent.Action.SHOW_ITEM)
            {
                ItemStack itemStack = ItemStack.EMPTY;

                try
                {
                    NBTTagCompound nbt = JsonToNBT.getTagFromJson(event.getValue().getString());
                    itemStack = ItemStack.read(nbt);
                }
                catch (CommandSyntaxException e) {}

                if (itemStack.isEmpty())
                {
                    this.drawHoveringText(TextFormatting.RED + "Invalid Item!", mouseX, mouseY);
                }
                else
                {
                    this.renderToolTip(itemStack, mouseX, mouseY);
                }
            }
            else if (event.getAction() == HoverEvent.Action.SHOW_ENTITY)
            {
                if (this.mc.gameSettings.advancedItemTooltips)
                {
                    try
                    {
                        NBTTagCompound compound = JsonToNBT.getTagFromJson(event.getValue().getString());
                        List<String> list = new ArrayList<>();
                        ITextComponent itextcomponent = ITextComponent.Serializer.fromJson(compound.getString("name"));

                        if (itextcomponent != null)
                        {
                            for (IEntityHoverChat entity : GuiChatRegistry.getEntityHoverChatList())
                            {
                                itextcomponent = entity.addEntityComponent(itextcomponent);
                            }
                            list.add(itextcomponent.getFormattedText());
                        }

                        if (compound.contains("type", 8))
                        {
                            String s = compound.getString("type");
                            list.add("Type: " + s);
                        }

                        list.add(compound.getString("id"));
                        this.drawHoveringText(list, mouseX, mouseY);
                    }
                    catch (CommandSyntaxException | JsonSyntaxException e)
                    {
                        this.drawHoveringText(TextFormatting.RED + "Invalid Entity!", mouseX, mouseY);
                    }
                }
            }
            else if (event.getAction() == HoverEvent.Action.SHOW_TEXT)
            {
                this.drawHoveringText(this.mc.fontRenderer.listFormattedStringToWidth(event.getValue().getFormattedText(), Math.max(this.width / 2, 200)), mouseX, mouseY);
            }
            GlStateManager.disableLighting();
        }
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        GuiChatRegistry.getGuiChatList().forEach(IGuiChat::onGuiClosed);
    }
}