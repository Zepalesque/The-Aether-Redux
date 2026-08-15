package net.zepalesque.redux.block.natural;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.unity.block.natural.bush.CustomBoundsFlowerBlock;

// TODO: Particles
public class GloomshadeBlock extends CustomBoundsFlowerBlock {
	public GloomshadeBlock(VoxelShape shape, Holder<MobEffect> effect, int duration, Properties properties) {
		super(shape, effect, duration, properties);
	}
}
