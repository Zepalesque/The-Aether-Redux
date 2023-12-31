
package net.zepalesque.redux.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.IoSupplier;
import net.zepalesque.redux.api.Conditional;
import net.zepalesque.redux.builtin.BuiltinPackUtils;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ReduxOverridesPackResources extends AbstractPackResources
{
    private final PackMetadataSection packMeta;
    private final List<Conditional<PackResources>> delegates;
    private final Map<String, List<Conditional<PackResources>>> namespacesAssets;
    private static final Gson GSON = new Gson();
    private final Path source;

    public ReduxOverridesPackResources(String packId, boolean isBuiltin, PackMetadataSection packMeta, List<Conditional<PackResources>> packs)
    {

        super(packId, isBuiltin);
        this.source = BuiltinPackUtils.path(packId);
        this.packMeta = packMeta;
        this.delegates = ImmutableList.copyOf(packs);
        this.namespacesAssets = this.buildNamespaceMap(PackType.CLIENT_RESOURCES, packs);
    }
    public Path getSource() {
        return this.source;
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

    private Map<String, List<Conditional<PackResources>>> buildNamespaceMap(PackType type, List<Conditional<PackResources>> packList)
    {
        Map<String, List<Conditional<PackResources>>> map = new HashMap<>();
        for (Conditional<PackResources> optional : packList)
        {
            if (optional.isPopulated()) {
                PackResources pack = optional.getAnyway();
                for (String namespace : pack.getNamespaces(type)) {
                    if (optional.get() != null)
                        map.computeIfAbsent(namespace, k -> new ArrayList<>()).add(optional);
                }
            }
        }
        map.replaceAll((k, list) -> ImmutableList.copyOf(list));
        return ImmutableMap.copyOf(map);
    }




    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) {
        return deserializer.getMetadataSectionName().equals("pack") ? (T) this.packMeta : null;
    }

    @Override
    public void listResources(PackType type, String resourceNamespace, String paths, ResourceOutput resourceOutput)
    {
        for (PackResources delegate : this.getAvaliablePacks())
        {
            delegate.listResources(type, resourceNamespace, paths, resourceOutput);
        }
    }

    @Override
    public @NotNull Set<String> getNamespaces(PackType type)
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

    @javax.annotation.Nullable
    public IoSupplier<InputStream> getRootResource(String... paths) {
        Path path = this.resolve(paths);
        return !Files.exists(path) ? null : IoSupplier.create(path);
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location)
    {
        if (location.getPath().matches("lang/.+\\.json")) {
            Collection<Set<Map.Entry<String, JsonElement>>> matches = Lists.newArrayList();
            JsonObject combined = new JsonObject();
            for (PackResources pack : getCandidatePacks(type, location)) {
                IoSupplier<InputStream> ioSupplier = pack.getResource(type, location);
                if (ioSupplier != null) {
                    try {
                        JsonObject jsonobject = GSON.fromJson(new InputStreamReader(ioSupplier.get(), StandardCharsets.UTF_8), JsonObject.class);
                        matches.add(jsonobject.entrySet());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!matches.isEmpty()) {
                for (Set<Map.Entry<String, JsonElement>> entrySet : matches) {
                    for (Map.Entry<String, JsonElement> entry : entrySet) {
                        combined.add(entry.getKey(), entry.getValue());
                    }
                }
            } else {
                return null;
            }
            String input = GSON.toJson(combined);

            return () -> new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

        } else {
            for (PackResources pack : getCandidatePacks(type, location)) {
                IoSupplier<InputStream> ioSupplier = pack.getResource(type, location);
                if (ioSupplier != null)
                    return pack.getResource(type, location);
            }
        }

        return null;
    }

    @Nullable
    public Collection<PackResources> getChildren()
    {
        return getAvaliablePacks();
    }

    public Collection<PackResources> getAvaliablePacks()
    {
        return delegates.stream().filter(Conditional::isAvailable).map(Conditional::get).toList();
    }

    public Collection<PackResources> getAllPacks()
    {
        return delegates.stream().filter(Conditional::isPopulated).map(Conditional::getAnyway).toList();
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

    public String toString() {
        return String.format("%s: %s", this.getClass().getName(), this.getSource());
    }
}
