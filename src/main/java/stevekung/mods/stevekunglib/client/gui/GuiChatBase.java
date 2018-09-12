package stevekung.mods.stevekunglib.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;

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
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.initGui(this.buttonList, this.width, this.height));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.drawScreen(this.buttonList, mouseX, mouseY, partialTicks));
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.updateScreen(this.buttonList, this.width, this.height));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.tabCompleter.resetRequested();

        if (keyCode == 15)
        {
            this.tabCompleter.complete();
        }
        else
        {
            this.tabCompleter.resetDidComplete();
        }

        if (keyCode == 1)
        {
            this.mc.displayGuiScreen(null);
        }
        else if (keyCode != 28 && keyCode != 156)
        {
            if (keyCode == 200)
            {
                this.getSentHistory(-1);
            }
            else if (keyCode == 208)
            {
                this.getSentHistory(1);
            }
            else if (keyCode == 201)
            {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
                GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::keyTypedScrollDown);
            }
            else if (keyCode == 209)
            {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
                GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::keyTypedScrollUp);
            }
            else
            {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
            }
        }
        else
        {
            String text = this.inputField.getText().trim();

            if (!text.isEmpty())
            {
                this.sendChatMessage(text);
            }
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void getSentHistory(int msgPos)
    {
        super.getSentHistory(msgPos);
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.getSentHistory(msgPos));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        GuiChatRegistry.getGuiChatList().stream().forEach(gui -> gui.actionPerformed(button));
    }

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

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        GuiChatRegistry.getGuiChatList().stream().forEach(IGuiChat::onGuiClosed);
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();

        GuiChatRegistry.getGuiChatList().stream().forEach(gui ->
        {
            try
            {
                gui.handleMouseInput(this.width, this.height);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}