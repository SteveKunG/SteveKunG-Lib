package stevekung.mods.stevekungslib.client.gui;

import net.minecraft.util.text.ITextComponent;

public interface IEntityHoverChat extends IChatScreen
{
    default ITextComponent addEntityComponent(ITextComponent component)
    {
        return component;
    }
}