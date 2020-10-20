package com.stevekung.stevekungslib.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;

public class ItemUtils
{
    public static ItemStack getSkullItemStack(String skullId, String skullValue)
    {
        ItemStack itemStack = new ItemStack(Items.PLAYER_HEAD);
        return ItemUtils.setSkullSkin(itemStack, skullId, skullValue);
    }

    public static ItemStack setSkullSkin(ItemStack itemStack, String skullId, String skullValue)
    {
        CompoundNBT compound = new CompoundNBT();
        CompoundNBT properties = new CompoundNBT();
        properties.putIntArray("Id", ItemUtils.uuidToIntArray(skullId));
        CompoundNBT texture = new CompoundNBT();
        ListNBT list = new ListNBT();
        CompoundNBT value = new CompoundNBT();
        value.putString("Value", ItemUtils.toSkullURL(skullValue));
        list.add(value);
        texture.put("textures", list);
        properties.put("Properties", texture);
        compound.put("SkullOwner", properties);
        itemStack.setTag(compound);

        if (!itemStack.hasTag())
        {
            compound.put("SkullOwner", properties);
            itemStack.setTag(compound);
        }
        else
        {
            itemStack.getTag().put("SkullOwner", properties);
        }

        return itemStack;
    }

    public static ItemStack getPlayerHead(String name)
    {
        ItemStack itemStack = new ItemStack(Items.PLAYER_HEAD);
        CompoundNBT compound = new CompoundNBT();
        GameProfile profile = SkullTileEntity.updateGameProfile(new GameProfile(null, name));
        compound.remove("SkullOwner");
        compound.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), profile));
        itemStack.setTag(compound);
        return itemStack;
    }

    public static int[] uuidToIntArray(String id)
    {
        UUID uuid = UUID.fromString(id);
        long uuidMost = uuid.getMostSignificantBits();
        long uuidLeast = uuid.getLeastSignificantBits();
        return new int[]{(int)(uuidMost >> 32), (int)uuidMost, (int)(uuidLeast >> 32), (int)uuidLeast};
    }

    public static String decodeTextureURL(String source)
    {
        JsonObject obj = new JsonParser().parse(new String(Base64.getDecoder().decode(source))).getAsJsonObject();
        String textureurl = obj.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
        return textureurl.substring(textureurl.lastIndexOf("/") + 1);
    }

    public static String toSkullURL(String url)
    {
        JsonObject skin = new JsonObject();
        skin.addProperty("url", "http://textures.minecraft.net/texture/" + url);
        JsonObject textures = new JsonObject();
        textures.add("SKIN", skin);
        JsonObject root = new JsonObject();
        root.add("textures", textures);
        return Base64.getEncoder().encodeToString(TextComponentUtils.GSON.toJson(root).getBytes(StandardCharsets.UTF_8));
    }
}