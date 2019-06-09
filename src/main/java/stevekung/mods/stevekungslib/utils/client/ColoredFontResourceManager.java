package stevekung.mods.stevekungslib.utils.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.providers.DefaultGlyphProvider;
import net.minecraft.client.gui.fonts.providers.GlyphProviderTypes;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;
import stevekung.mods.stevekungslib.core.SteveKunGLib;
import stevekung.mods.stevekungslib.utils.ColorUtils;

@OnlyIn(Dist.CLIENT)
public class ColoredFontResourceManager implements ISelectiveResourceReloadListener
{
    private final Map<ResourceLocation, ColoredFontRenderer> fontRenderers = new HashMap<>();
    private final TextureManager textureManager;
    private boolean forceUnicodeFont;

    public ColoredFontResourceManager(TextureManager textureManager, boolean forceUnicodeFont)
    {
        System.out.println(this.getClass().getName() + " is loaded!");
        this.textureManager = textureManager;
        this.forceUnicodeFont = forceUnicodeFont;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        Map<ResourceLocation, List<IGlyphProvider>> glyphList = new HashMap<>();

        for (ResourceLocation resourceLocation : resourceManager.getAllResourceLocations("font", name -> name.endsWith(".json")))
        {
            String path = resourceLocation.getPath();
            ResourceLocation glyphResource = new ResourceLocation(resourceLocation.getNamespace(), path.substring("font/".length(), path.length() - ".json".length()));
            List<IGlyphProvider> list = glyphList.computeIfAbsent(glyphResource, resource -> Lists.newArrayList(new DefaultGlyphProvider()));

            try
            {
                for (IResource iResource : resourceManager.getAllResources(resourceLocation))
                {
                    try (InputStream stream = iResource.getInputStream())
                    {
                        Throwable throwable = null;

                        try
                        {
                            JsonArray array = JSONUtils.getJsonArray(JSONUtils.fromJson(gson, IOUtils.toString(stream, StandardCharsets.UTF_8), JsonObject.class), "providers");

                            for (int i = array.size() - 1; i >= 0; --i)
                            {
                                JsonObject obj = JSONUtils.getJsonObject(array.get(i), "providers[" + i + "]");

                                try
                                {
                                    GlyphProviderTypes types = GlyphProviderTypes.byName(JSONUtils.getString(obj, "type"));

                                    if (!this.forceUnicodeFont || types == GlyphProviderTypes.LEGACY_UNICODE || !glyphResource.equals(ColorUtils.DEFAULT_FONT_RENDERER_NAME))
                                    {
                                        IGlyphProvider provider = types.getFactory(obj).create(resourceManager);

                                        if (provider != null)
                                        {
                                            list.add(provider);
                                        }
                                    }
                                }
                                catch (RuntimeException e)
                                {
                                    SteveKunGLib.LOGGER.warning("Unable to read definition '{}' in fonts.json in resourcepack: '{}': {}", glyphResource, iResource.getPackName(), e.getMessage());
                                }
                            }
                        }
                        catch (Throwable t)
                        {
                            throwable = t;
                            throw t;
                        }
                        finally
                        {
                            if (throwable != null)
                            {
                                try
                                {
                                    stream.close();
                                }
                                catch (Throwable t)
                                {
                                    throwable.addSuppressed(t);
                                }
                            }
                            else
                            {
                                stream.close();
                            }
                        }
                    }
                    catch (RuntimeException e)
                    {
                        SteveKunGLib.LOGGER.warning("Unable to load font '{}' in fonts.json in resourcepack: '{}': {}", glyphResource, iResource.getPackName(), e.getMessage());
                    }
                }
            }
            catch (IOException e)
            {
                SteveKunGLib.LOGGER.warning("Unable to load font '{}' in fonts.json: {}", glyphResource, e.getMessage());
            }
        }

        Stream.concat(this.fontRenderers.keySet().stream(), glyphList.keySet().stream()).distinct().forEach(resource ->
        {
            List<IGlyphProvider> provider = glyphList.getOrDefault(resource, Collections.emptyList());
            Collections.reverse(provider);
            this.fontRenderers.computeIfAbsent(resource, resourceLocation -> new ColoredFontRenderer(this.textureManager, new Font(this.textureManager, resourceLocation))).setGlyphProviders(provider);
        });
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate)
    {
        if (resourcePredicate.test(VanillaResourceType.TEXTURES))
        {
            this.onResourceManagerReload(resourceManager);
        }
    }

    @Override
    public IResourceType getResourceType()
    {
        return VanillaResourceType.TEXTURES;
    }

    @Nullable
    public ColoredFontRenderer getColoredFontRenderer(ResourceLocation resourceLocation)
    {
        return this.fontRenderers.get(resourceLocation);
    }

    public void setForceUnicodeFont(boolean forceUnicodeFont)
    {
        if (forceUnicodeFont != this.forceUnicodeFont)
        {
            this.forceUnicodeFont = forceUnicodeFont;
            this.onResourceManagerReload(Minecraft.getInstance().getResourceManager());
        }
    }
}