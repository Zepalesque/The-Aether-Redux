package net.zepalesque.redux.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public interface TextureExtensions {
    
    default ResourceLocation texture(Block block) {
        ResourceLocation loc = ForgeRegistries.BLOCKS.getKey(block);
        if (loc == null) throw new IllegalStateException();
        return loc.withPath("block/" + name(block));
    }
    
    default ResourceLocation texture(Block block, String location) {
        ResourceLocation loc = ForgeRegistries.BLOCKS.getKey(block);
        if (loc == null) throw new IllegalStateException();
        return loc.withPath("block/" + location + name(block));
    }
    
    default ResourceLocation texture(Block block, String location, String suffix) {
        ResourceLocation loc = ForgeRegistries.BLOCKS.getKey(block);
        if (loc == null) throw new IllegalStateException();
        return loc.withPath("block/" + location + name(block) + suffix);
    }
    
    default String name(Block block) {
        ResourceLocation loc = ForgeRegistries.BLOCKS.getKey(block);
        if (loc == null) throw new IllegalStateException();
        return loc.getPath();
    }
    
    default String nameID(Block block) {
        ResourceLocation loc = ForgeRegistries.BLOCKS.getKey(block);
        if (loc == null) throw new IllegalStateException();
        return loc.toString();
    }
}
