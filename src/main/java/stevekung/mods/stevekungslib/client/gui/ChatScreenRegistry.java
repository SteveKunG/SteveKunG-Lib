package stevekung.mods.stevekungslib.client.gui;

import java.util.ArrayList;
import java.util.List;

public class ChatScreenRegistry
{
    private static final List<IChatScreen> CHAT = new ArrayList<>();
    private static final List<IEntityHoverChat> ENTITY_CHAT = new ArrayList<>();

    public static void register(IChatScreen chat)
    {
        ChatScreenRegistry.CHAT.add(chat);

        if (chat instanceof IEntityHoverChat)
        {
            ChatScreenRegistry.ENTITY_CHAT.add((IEntityHoverChat)chat);
        }
    }

    public static List<IChatScreen> getChatList()
    {
        return ChatScreenRegistry.CHAT;
    }

    public static List<IEntityHoverChat> getEntityChatList()
    {
        return ChatScreenRegistry.ENTITY_CHAT;
    }
}