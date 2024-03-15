package net.zepalesque.redux.client.resource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.ResourcePackFileNotFoundException;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.api.Conditional;
import net.zepalesque.redux.builtin.BuiltinPackUtils;
import org.apache.commons.compress.utils.Lists;

import javax.annotation.Nonnull;

public class ReduxOverridesPackResources extends AbstractPackResources
{
    private final List<Conditional<PackResources>> delegates;
    private Map<String, List<Conditional<PackResources>>> namespacesAssets;
    private final Path source;
    private final String id;
    private final PackMetadataSection packInfo;
    private static final Gson GSON = new Gson();

    public ReduxOverridesPackResources(String id, PackMetadataSection packInfo, List<Conditional<PackResources>> packs)
    {
        super(new File(id));
        this.id = id;
        this.source = BuiltinPackUtils.path(id);
        this.packInfo = packInfo;
        this.delegates = ImmutableList.copyOf(packs);
        this.namespacesAssets = this.buildNamespaceMap(PackType.CLIENT_RESOURCES, delegates);
    }
    public Path getSource() {
        return this.source;
    }

    @Override
    public void initForNamespace(final String nameSpace)
    {
        this.delegates.forEach(delegate -> delegate.getAnyway().initForNamespace(nameSpace));
    }

    @Override
    public void init(final PackType packType)
    {
        this.delegates.forEach(packResources -> packResources.getAnyway().init(packType));

        this.namespacesAssets = buildNamespaceMap(PackType.CLIENT_RESOURCES, delegates);
    }

    private Map<String, List<Conditional<PackResources>>> buildNamespaceMap(PackType type, List<Conditional<PackResources>> packList)
    {
        Map<String, List<Conditional<PackResources>>> map = new HashMap<>();
        for (Conditional<PackResources> optional : packList)
        {
            if (optional.isPopulated()) {
                PackResources pack = optional.getAnyway();
                for (String namespace : pack.getNamespaces(type)) {
                    map.computeIfAbsent(namespace, k -> new ArrayList<>()).add(optional);
                }
            }
        }
        map.replaceAll((k, list) -> ImmutableList.copyOf(list));
        return ImmutableMap.copyOf(map);
    }

    @Override
    public String getName()
    {
        return this.id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) throws IOException
    {
        if (deserializer.getMetadataSectionName().equals("pack"))
        {
            return (T) packInfo;
        }
        return null;
    }

    @Override
    public Collection<ResourceLocation> getResources(PackType type, String pathIn, String pathIn2, Predicate<ResourceLocation> filter)
    {
        return getAvaliablePacks().stream()
                .flatMap(r -> r.getResources(type, pathIn, pathIn2, filter).stream())
                .collect(Collectors.toList());
    }
    public Collection<PackResources> getAvaliablePacks()
    {
        return delegates.stream().filter(Conditional::isAvailable).map(Conditional::get).toList();
    }
    public Collection<PackResources> getAllPacks()
    {
        return delegates.stream().filter(Conditional::isPopulated).map(Conditional::getAnyway).toList();
    }
    @Override
    public Set<String> getNamespaces(PackType type)
    {
        return type == PackType.CLIENT_RESOURCES ? namespacesAssets.keySet() : Set.of();
    }

    @Override
    public void close()
    {
        for (PackResources pack : getAllPacks())
        {
            pack.close();
        }
    }


    @Override
    public InputStream getRootResource(String pFileName) throws IOException {
        if (!pFileName.contains("/") && !pFileName.contains("\\")) {
            return this.getResource(pFileName);
        } else {
            throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
        }
    }

    protected Path resolve(String... paths) {
        Path path = this.getSource();
        String[] var3 = paths;
        int var4 = paths.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String name = var3[var5];
            path = path.resolve(name);
        }

        return path;
    }
    @Override
    protected boolean hasResource(@Nonnull String name) {
        Path path = this.resolve(name);
        return Files.exists(path, new LinkOption[0]);
    }

    @Override
    @Nonnull
    protected InputStream getResource(@Nonnull String name) throws IOException {
        Path path = this.resolve(name);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Can't find resource " + name + " at " + this.getSource());
        } else {
            return Files.newInputStream(path, StandardOpenOption.READ);
        }
    }

    @Override
    public InputStream getResource(PackType type, ResourceLocation location) throws IOException
    {
        if (location.getPath().matches("lang/.+\\.json") || location.getPath().matches(".*sounds\\.json")) {
            Collection<Set<Map.Entry<String, JsonElement>>> matches = Lists.newArrayList();
            JsonObject combined = new JsonObject();
            for (PackResources pack : getCandidatePacks(type, location)) {
                if (pack.hasResource(type, location)) {
                    InputStream iStream = pack.getResource(type, location);
                    JsonObject jsonobject = GSON.fromJson(new InputStreamReader(iStream, StandardCharsets.UTF_8), JsonObject.class);
                    matches.add(jsonobject.entrySet());
                }
            }
            if (!matches.isEmpty()) {
                for (Set<Map.Entry<String, JsonElement>> entrySet : matches) {
                    for (Map.Entry<String, JsonElement> entry : entrySet) {
                        combined.add(entry.getKey(), entry.getValue());
                    }
                }
            } else {
                throw new ResourcePackFileNotFoundException(this.file, getFullPath(type, location));
            }
            String input = GSON.toJson(combined);

            return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

        } else {
            for (PackResources pack : getCandidatePacks(type, location)) {
                if (pack.hasResource(type, location)) {
                    return pack.getResource(type, location);
                }
            }
        }
        throw new ResourcePackFileNotFoundException(this.file, getFullPath(type, location));
    }

    @Override
    public boolean hasResource(PackType type, ResourceLocation location)
    {
        for (PackResources pack : getCandidatePacks(type, location))
        {
            if (pack.hasResource(type, location))
            {
                return true;
            }
        }
        return false;
    }

    private List<PackResources> getCandidatePacks(PackType type, ResourceLocation location)
    {
        if (type == PackType.CLIENT_RESOURCES) {
            Map<String, List<Conditional<PackResources>>> map = namespacesAssets;
            List<Conditional<PackResources>> packsWithNamespace = map.get(location.getNamespace());
            return packsWithNamespace == null ? Collections.emptyList() : packsWithNamespace.stream().filter(Conditional::isAvailable).map(Conditional::get).toList();
        } else {
            return Collections.emptyList();
        }
    }

    private static String getFullPath(PackType type, ResourceLocation location)
    {
        return String.format(Locale.ROOT, "%s/%s/%s", type.getDirectory(), location.getNamespace(), location.getPath());
    }

}