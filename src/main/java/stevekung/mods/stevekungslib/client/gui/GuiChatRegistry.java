package stevekung.mods.stevekungslib.client.gui;

import java.util.ArrayList;
import java.util.List;

public class GuiChatRegistry
{
    private static final List<IGuiChat> guiChat = new ArrayList<>();
    private static final List<IEntityHoverChat> entityHoverChat = new ArrayList<>();

    public static void register(IGuiChat chat)
    {
        GuiChatRegistry.guiChat.add(chat);

        if (chat instanceof IEntityHoverChat)
        {
            GuiChatRegistry.entityHoverChat.add((IEntityHoverChat)chat);
        }
    }

    static List<IGuiChat> getGuiChatList()
    {
        return GuiChatRegistry.guiChat;
    }

    static List<IEntityHoverChat> getEntityHoverChatList()
    {
        return GuiChatRegistry.entityHoverChat;
    }
}