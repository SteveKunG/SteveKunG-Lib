package stevekung.mods.stevekunglib.client.gui;

import net.minecraft.util.text.ITextComponent;

public interface IEntityHoverChat extends IGuiChat
{
    ITextComponent addEntityComponent(ITextComponent component);
}