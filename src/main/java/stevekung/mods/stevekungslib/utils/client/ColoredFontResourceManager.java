package stevekung.mods.stevekungslib.utils.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
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
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.EmptyProfiler;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import stevekung.mods.stevekungslib.core.SteveKunGLib;
import stevekung.mods.stevekungslib.utils.ColorUtils;

@OnlyIn(Dist.CLIENT)
public class ColoredFontResourceManager implements AutoCloseable
{
    private final Map<ResourceLocation, ColoredFontRenderer> fontRenderers = new HashMap<>();
    private final Set<IGlyphProvider> providers = new HashSet<>();
    private final TextureManager textureManager;
    private boolean forceUnicodeFont;
    private final IFutureReloadListener listener = new ReloadListener<Map<ResourceLocation, List<IGlyphProvider>>>()
    {
        @Override
        protected Map<ResourceLocation, List<IGlyphProvider>> func_212854_a_(IResourceManager manager, IProfiler profiler)
        {
            profiler.func_219894_a();
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            Map<ResourceLocation, List<IGlyphProvider>> map = new HashMap<>();

            for (ResourceLocation resourcelocation : manager.getAllResourceLocations("font", name -> name.endsWith(".json")))
            {
                String path = resourcelocation.getPath();
                ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), path.substring("font/".length(), path.length() - ".json".length()));
                List<IGlyphProvider> list = map.computeIfAbsent(resourcelocation1, resource -> Lists.newArrayList(new DefaultGlyphProvider()));
                profiler.startSection(resourcelocation1::toString);

                try
                {
                    for (IResource iresource : manager.getAllResources(resourcelocation))
                    {
                        profiler.startSection(iresource::getPackName);

                        try (InputStream inputstream = iresource.getInputStream())
                        {
                            profiler.startSection("reading");
                            JsonArray jsonarray = JSONUtils.getJsonArray(JSONUtils.fromJson(gson, IOUtils.toString(inputstream, StandardCharsets.UTF_8), JsonObject.class), "providers");
                            profiler.endStartSection("parsing");

                            for (int i = jsonarray.size() - 1; i >= 0; --i)
                            {
                                JsonObject jsonobject = JSONUtils.getJsonObject(jsonarray.get(i), "providers[" + i + "]");

                                try
                                {
                                    String s1 = JSONUtils.getString(jsonobject, "type");
                                    GlyphProviderTypes glyphprovidertypes = GlyphProviderTypes.byName(s1);

                                    if (!ColoredFontResourceManager.this.forceUnicodeFont || glyphprovidertypes == GlyphProviderTypes.LEGACY_UNICODE || !resourcelocation1.equals(ColorUtils.DEFAULT_FONT_RENDERER_NAME))
                                    {
                                        profiler.startSection(s1);
                                        list.add(glyphprovidertypes.getFactory(jsonobject).create(manager));
                                        profiler.endSection();
                                    }
                                }
                                catch (RuntimeException e)
                                {
                                    SteveKunGLib.LOGGER.warning("Unable to read definition '{}' in fonts.json in resourcepack: '{}': {}", resourcelocation1, iresource.getPackName(), e.getMessage());
                                }
                            }
                            profiler.endSection();
                        }
                        catch (RuntimeException e)
                        {
                            SteveKunGLib.LOGGER.warning("Unable to load font '{}' in fonts.json in resourcepack: '{}': {}", resourcelocation1, iresource.getPackName(), e.getMessage());
                        }
                        profiler.endSection();
                    }
                }
                catch (IOException e)
                {
                    SteveKunGLib.LOGGER.warning("Unable to load font '{}' in fonts.json: {}", resourcelocation1, e.getMessage());
                }

                profiler.startSection("caching");

                for (char c0 = 0; c0 < '\uffff'; ++c0)
                {
                    if (c0 != ' ')
                    {
                        for (IGlyphProvider iglyphprovider : Lists.reverse(list))
                        {
                            if (iglyphprovider.func_212248_a(c0) != null)
                            {
                                break;
                            }
                        }
                    }
                }
                profiler.endSection();
                profiler.endSection();
            }
            profiler.func_219897_b();
            return map;
        }

        @Override
        protected void func_212853_a_(Map<ResourceLocation, List<IGlyphProvider>> provider, IResourceManager manager, IProfiler profiler)
        {
            profiler.func_219894_a();
            profiler.startSection("reloading");
            Stream.concat(ColoredFontResourceManager.this.fontRenderers.keySet().stream(), provider.keySet().stream()).distinct().forEach(resource ->
            {
                List<IGlyphProvider> list = provider.getOrDefault(resource, Collections.emptyList());
                Collections.reverse(list);
                ColoredFontResourceManager.this.fontRenderers.computeIfAbsent(resource, resource1 -> new ColoredFontRenderer(ColoredFontResourceManager.this.textureManager, new Font(ColoredFontResourceManager.this.textureManager, resource1))).setGlyphProviders(list);
            });
            Collection<List<IGlyphProvider>> collection = provider.values();
            Set<IGlyphProvider> set = ColoredFontResourceManager.this.providers;
            collection.forEach(set::addAll);
            profiler.endSection();
            profiler.func_219897_b();
        }
    };

    public ColoredFontResourceManager(TextureManager textureManager, boolean forceUnicodeFont)
    {
        System.out.println(this.getClass().getName() + " is loaded!");
        this.textureManager = textureManager;
        this.forceUnicodeFont = forceUnicodeFont;
    }

    @Override
    public void close()
    {
        this.fontRenderers.values().forEach(ColoredFontRenderer::close);
        this.providers.forEach(IGlyphProvider::close);
    }

    @Nullable
    public ColoredFontRenderer getFontRenderer(ResourceLocation id)
    {
        return this.fontRenderers.computeIfAbsent(id, resouce ->
        {
            ColoredFontRenderer fontrenderer = new ColoredFontRenderer(this.textureManager, new Font(this.textureManager, resouce));
            fontrenderer.setGlyphProviders(Lists.newArrayList(new DefaultGlyphProvider()));
            return fontrenderer;
        });
    }

    public void setForceUnicodeFont(boolean forceUnicodeFont, Executor executor1, Executor executor2)
    {
        if (forceUnicodeFont != this.forceUnicodeFont)
        {
            this.forceUnicodeFont = forceUnicodeFont;
            IResourceManager iresourcemanager = Minecraft.getInstance().getResourceManager();
            IFutureReloadListener.IStage stage = new IFutureReloadListener.IStage()
            {
                @Override
                public <T> CompletableFuture<T> func_216872_a(T obj)
                {
                    return CompletableFuture.completedFuture(obj);
                }
            };
            this.listener.func_215226_a(stage, iresourcemanager, EmptyProfiler.INSTANCE, EmptyProfiler.INSTANCE, executor1, executor2);
        }
    }

    public IFutureReloadListener getReloadListener()
    {
        return this.listener;
    }
}