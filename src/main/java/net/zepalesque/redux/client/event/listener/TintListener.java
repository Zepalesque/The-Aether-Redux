package net.zepalesque.redux.client.event.listener;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.zepalesque.redux.block.ReduxBlocks;

import java.util.stream.IntStream;

@EventBusSubscriber(Dist.CLIENT)
public class TintListener {
	
	public static final PerlinNoise PRISMA_NOISE = PerlinNoise.create(new XoroshiroRandomSource(2743), IntStream.of(0));
	
	@SubscribeEvent
	public static void registerTintHandlers(RegisterColorHandlersEvent.Block event) {
		event.register(
			(state, level, pos, tintIndex) -> {
				pos = pos == null ? BlockPos.ZERO : pos;
				final var posScale = 50d;
				final var sampleScale = 65d;
				var noise = PRISMA_NOISE.getValue(pos.getX() / posScale, pos.getY() / posScale, pos.getZ() / posScale) * sampleScale;
				
				// Distance from 0, 0 using pythagorean theorem
//				var dist = Math.sqrt(Math.pow(pos.getX(), 2) + Math.pow(pos.getZ(), 2));
				
				// Creating the color channels using the sine of the dist.
				// The steps are:
				// - Get the sine/cosine/third thing, in a range from -1:1
				// - Multiply the result by 128, so they go from -128:128 (range of a signed byte)
				// - Convert to a byte
				// - Add 128, to account for signedness
				var r = (byte)(Math.sin(noise) * 128) + 128;
				var g = (byte)(-Math.sin(noise) * 128) + 128;
				var b1 = (byte)(Math.cos(noise) * 128) + 128;
				var b2 = (byte)(-Math.cos(noise) * 128) + 128;
				var b = Math.max(b1, b2);
				
				// Clamping the values to create a pastel look
				r = Math.clamp(r, 156, 240);
				g = Math.clamp(g, 156, 240);
				b = Math.clamp(b, 192, 230);
				r += 15;
				g += 10;
				b += 25;
				
				// Creating the final tint, using bitor to set the channels 
				var tint = 0xFF000000;
				tint |= r << 16;
				tint |= g << 8;
				tint |= b;
				
				return tint;
			},
			ReduxBlocks.PRISMA_LEAVES.get(),
			ReduxBlocks.PRISMA_LEAF_PILE.get()
		);
	}
}
