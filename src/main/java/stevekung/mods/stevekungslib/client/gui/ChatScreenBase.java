package stevekung.mods.stevekungslib.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import stevekung.mods.stevekungslib.utils.client.ClientUtils;

@Deprecated
public class ChatScreenBase extends ChatScreen
{
    public ChatScreenBase(String input)
    {
        super(input);
    }

    @Override
    public void init()
    {
        super.init();
        ChatScreenRegistry.getChatList().forEach(gui -> gui.initGui(this.buttons, this.width, this.height));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        super.render(mouseX, mouseY, partialTicks);
        ChatScreenRegistry.getChatList().forEach(gui -> gui.render(this.buttons, mouseX, mouseY, partialTicks));
    }

    @Override
    public void tick()
    {
        super.tick();
        ChatScreenRegistry.getChatList().forEach(gui -> gui.tick(this.buttons, this.width, this.height));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (this.suggestions != null && this.suggestions.keyPressed(keyCode, scanCode, modifiers))
        {
            return true;
        }
        else
        {
            if (keyCode == 258)
            {
                this.hasEdits = true;
                this.showSuggestions();
            }

            if (super.keyPressed(keyCode, scanCode, modifiers))
            {
                return true;
            }
            else if (keyCode == 256)
            {
                this.minecraft.displayGuiScreen(null);
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
                    this.minecraft.ingameGUI.getChatGUI().func_194813_a(this.minecraft.ingameGUI.getChatGUI().getLineCount() - 1);
                    ChatScreenRegistry.getChatList().forEach(IChatScreen::pageUp);
                    return true;
                }
                else if (keyCode == 267)
                {
                    this.minecraft.ingameGUI.getChatGUI().func_194813_a(-this.minecraft.ingameGUI.getChatGUI().getLineCount() + 1);
                    ChatScreenRegistry.getChatList().forEach(IChatScreen::pageDown);
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                String s = this.inputField.getText().trim();

                if (!s.isEmpty())
                {
                    this.sendMessage(s);
                }
                this.minecraft.displayGuiScreen(null);
                return true;
            }
        }
    }

    @Override
    public void getSentHistory(int msgPos)
    {
        super.getSentHistory(msgPos);
        ChatScreenRegistry.getChatList().forEach(gui -> gui.getSentHistory(msgPos));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton)
    {
        ChatScreenRegistry.getChatList().forEach(gui -> gui.mouseClicked(mouseX, mouseY, mouseButton));
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state)
    {
        ChatScreenRegistry.getChatList().forEach(gui -> gui.mouseReleased(mouseX, mouseY, state));
        return super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int mouseEvent, double dragX, double dragY)
    {
        ChatScreenRegistry.getChatList().forEach(gui -> gui.mouseDragged(mouseX, mouseY, mouseEvent, dragX, dragY));
        return super.mouseDragged(mouseX, mouseY, mouseEvent, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double wheel)
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

        for (IChatScreen chat : ChatScreenRegistry.getChatList())
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
            this.minecraft.ingameGUI.getChatGUI().func_194813_a(wheel);
            return true;
        }
    }

    @Override
    protected void renderComponentHoverEffect(ITextComponent component, int mouseX, int mouseY)
    {
        if (component != null && component.getStyle().getHoverEvent() != null)
        {
            HoverEvent event = component.getStyle().getHoverEvent();

            if (event.getAction() == HoverEvent.Action.SHOW_ITEM)
            {
                ItemStack itemStack = ItemStack.EMPTY;

                try
                {
                    INBT nbt = JsonToNBT.getTagFromJson(event.getValue().getString());

                    if (nbt instanceof CompoundNBT)
                    {
                        itemStack = ItemStack.read((CompoundNBT)nbt);
                    }
                }
                catch (CommandSyntaxException e) {}

                if (itemStack.isEmpty())
                {
                    this.renderTooltip(TextFormatting.RED + "Invalid Item!", mouseX, mouseY);
                }
                else
                {
                    this.renderTooltip(itemStack, mouseX, mouseY);
                }
            }
            else if (event.getAction() == HoverEvent.Action.SHOW_ENTITY)
            {
                if (this.minecraft.gameSettings.advancedItemTooltips)
                {
                    try
                    {
                        INBT nbt = JsonToNBT.getTagFromJson(event.getValue().getString());
                        List<String> list = new ArrayList<>();

                        if (nbt instanceof CompoundNBT)
                        {
                            CompoundNBT compound = (CompoundNBT)nbt;
                            ITextComponent itextcomponent = ITextComponent.Serializer.fromJson(compound.getString("name"));

                            if (itextcomponent != null)
                            {
                                for (IEntityHoverChat entity : ChatScreenRegistry.getEntityChatList())
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
                        }
                        this.renderTooltip(list, mouseX, mouseY);
                    }
                    catch (CommandSyntaxException | JsonSyntaxException e)
                    {
                        this.renderTooltip(TextFormatting.RED + "Invalid Entity!", mouseX, mouseY);
                    }
                }
            }
            else if (event.getAction() == HoverEvent.Action.SHOW_TEXT)
            {
                this.renderTooltip(this.minecraft.fontRenderer.listFormattedStringToWidth(event.getValue().getFormattedText(), Math.max(this.width / 2, 200)), mouseX, mouseY);
            }
            GlStateManager.disableLighting();
        }
    }

    @Override
    public void onClose()
    {
        super.onClose();
        ChatScreenRegistry.getChatList().forEach(IChatScreen::onGuiClosed);
    }
}