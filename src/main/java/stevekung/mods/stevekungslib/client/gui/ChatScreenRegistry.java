package stevekung.mods.stevekungslib.client.gui;

import java.util.ArrayList;
import java.util.List;

public class ChatScreenRegistry
{
    private static final List<IChatScreen> guiChat = new ArrayList<>();
    private static final List<IEntityHoverChat> entityHoverChat = new ArrayList<>();

    public static void register(IChatScreen chat)
    {
        ChatScreenRegistry.guiChat.add(chat);

        if (chat instanceof IEntityHoverChat)
        {
            ChatScreenRegistry.entityHoverChat.add((IEntityHoverChat)chat);
        }
    }

    static List<IChatScreen> getGuiChatList()
    {
        return ChatScreenRegistry.guiChat;
    }

    static List<IEntityHoverChat> getEntityHoverChatList()
    {
        return ChatScreenRegistry.entityHoverChat;
    }
}