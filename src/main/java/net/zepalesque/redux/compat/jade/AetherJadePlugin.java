package net.zepalesque.redux.compat.jade;

import com.aetherteam.aether.Aether;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.block.ReduxBlocks;
import noobanidus.mods.lootr.init.ModBlocks;
import org.jetbrains.annotations.Nullable;
import snownee.jade.addon.vanilla.VanillaPlugin;
import snownee.jade.api.*;

@WailaPlugin
public class AetherJadePlugin implements IWailaPlugin {
	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.addRayTraceCallback(this::registerAetherOverrides);
	}

	@Nullable
	public Accessor<?> registerAetherOverrides(HitResult hitResult, @Nullable Accessor<?> accessor, @Nullable Accessor<?> originalAccessor) {
		if (accessor instanceof BlockAccessor target) {
			Player player = accessor.getPlayer();
			if (player.isCreative() || player.isSpectator()) {
				return accessor;
			}
			IWailaClientRegistration client = VanillaPlugin.CLIENT_REGISTRATION;
			if (target.getBlock() == ReduxBlocks.SKYROOT_CHEST_MIMIC.get()) { // Mimics show up as normal chests. There's not a single way to tell the difference between these and normal chests from the tooltip.
				if (ModList.get().isLoaded("lootr")) { // Disguise as Lootr Loot Chest
					return client.blockAccessor().from(target).serverData(this.createFakeChestData(target)).blockState(ModBlocks.CHEST.get().defaultBlockState()).build();
				} else {
					return client.blockAccessor().from(target).serverData(this.createFakeChestData(target)).blockState(ReduxBlocks.SKYROOT_CHEST_MIMIC.get().defaultBlockState()).build();
				}
			}
		}
		return accessor;
	}

	/**
	 * Adds the "inventory not generated" text to the mimic's tooltip
	 */
	private CompoundTag createFakeChestData(BlockAccessor target) {
		CompoundTag tag = new CompoundTag();
		if (!target.getServerData().isEmpty()) {
			tag.putBoolean("Loot", true);
		}
		return tag;
	}

	/**
	 * Converts doorway blocks to their appropriate locked blocks
	 */
	@Nullable
	private Block getLockedDungeonBlock(String name) {
		if (name.startsWith("boss_doorway_")) {
			return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Aether.MODID, "locked_" + name.substring(13)));
		} else if (name.startsWith("treasure_doorway_")) {
			return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Aether.MODID, "locked_" + name.substring(17)));
		}
		return null;
	}
}