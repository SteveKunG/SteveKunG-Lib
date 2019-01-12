package stevekung.mods.stevekunglib.client.gui;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;

import java.util.ArrayList;
import java.util.List;

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
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.initGui(this.buttons, this.width, this.height));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        super.render(mouseX, mouseY, partialTicks);
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.drawScreen(this.buttons, mouseX, mouseY, partialTicks));
    }

    @Override
    public void tick()
    {
        super.tick();
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.updateScreen(this.buttons, this.width, this.height));
    }

    //    @Override
    //    public boolean charTyped(char typedChar, int keyCode)TODO
    //    {
    //        this.suggestions.resetRequested();
    //
    //        if (keyCode == 15)
    //        {
    //            this.tabCompleter.complete();
    //        }
    //        else
    //        {
    //            this.tabCompleter.resetDidComplete();
    //        }
    //
    //        if (keyCode == 1)
    //        {
    //            this.mc.displayGuiScreen(null);
    //        }
    //        else if (keyCode != 28 && keyCode != 156)
    //        {
    //            if (keyCode == 200)
    //            {
    //                this.getSentHistory(-1);
    //            }
    //            else if (keyCode == 208)
    //            {
    //                this.getSentHistory(1);
    //            }
    //            else if (keyCode == 201)
    //            {
    //                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
    //                GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::keyTypedScrollDown);
    //            }
    //            else if (keyCode == 209)
    //            {
    //                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
    //                GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::keyTypedScrollUp);
    //            }
    //            else
    //            {
    //                this.inputField.textboxKeyTyped(typedChar, keyCode);
    //            }
    //        }
    //        else
    //        {
    //            String text = this.inputField.getText().trim();
    //
    //            if (!text.isEmpty())
    //            {
    //                this.sendChatMessage(text);
    //            }
    //            this.mc.displayGuiScreen(null);
    //        }
    //    }

    @Override
    public void getSentHistory(int msgPos)
    {
        super.getSentHistory(msgPos);
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.getSentHistory(msgPos));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton)
    {
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.mouseClicked(mouseX, mouseY, mouseButton));
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state)
    {
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.mouseReleased(mouseX, mouseY, state));
        return super.mouseReleased(mouseX, mouseY, state);
    }

    //    @Override
    //    public boolean mouseClickMove(double mouseX, double mouseY, int clickedMouseButton, long timeSinceLastClick)TODO
    //    {
    //        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));
    //        return super.mou
    //    }

    //    @Override
    //    protected void actionPerformed(GuiButton button)TODO
    //    {
    //        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.actionPerformed(button));
    //    }

    @Override
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
                    INBTBase nbt = JsonToNBT.getTagFromJson(hover.getValue().getString());

                    if (nbt instanceof NBTTagCompound)
                    {
                        itemStack = ItemStack.read((NBTTagCompound)nbt);
                    }
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
            else if (hover.getAction() == HoverEvent.Action.SHOW_ENTITY)
            {
                if (this.mc.gameSettings.advancedItemTooltips)
                {
                    try
                    {
                        NBTTagCompound compound = JsonToNBT.getTagFromJson(hover.getValue().getString());
                        List<String> list = new ArrayList<>();
                        String name = compound.getString("name");

                        for (IEntityHoverChat entity : GuiChatRegistry.getEntityHoverChatList())
                        {
                            name = entity.addEntityComponent(name);
                        }

                        list.add(name);

                        if (compound.contains("type", 8))
                        {
                            String s = compound.getString("type");
                            list.add("Type: " + s);
                        }
                        list.add(compound.getString("id"));
                        this.drawHoveringText(list, mouseX, mouseY);
                    }
                    catch (CommandSyntaxException e)
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

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::onGuiClosed);
    }

    //    @Override
    //    public void handleMouseInput() throws IOException TODO
    //    {
    //        super.handleMouseInput();
    //
    //        GuiChatRegistry.getGuiChatList().stream().forEach(gui ->
    //        {
    //            try
    //            {
    //                gui.handleMouseInput(this.width, this.height);
    //            }
    //            catch (IOException e)
    //            {
    //                e.printStackTrace();
    //            }
    //        });
    //    }
}