package net.zepalesque.redux.client.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


public class SparkOptions implements ParticleOptions {

   protected final UUID entity;

   public SparkOptions(UUID entity) {
      this.entity = entity;
   }

   public static final Codec<SparkOptions> CODEC = RecordCodecBuilder.create((p_253369_) -> {
      return p_253369_.group(UUIDUtil.CODEC.fieldOf("entity").forGetter((p_253368_) -> {
         return p_253368_.entity;
      })).apply(p_253369_, SparkOptions::new);
   });

   @Override
   public ParticleType<?> getType() {
      return ReduxParticleTypes.SPARK.get();
   }

   public void writeToNetwork(FriendlyByteBuf buffer) {
      buffer.writeUUID(this.entity);

   }

   @Override
   public @NotNull String writeToString() {
      return BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()) + " " + this.entity.toString();
   }

   public UUID getEntity() {
      return this.entity;
   }


   public static final ParticleOptions.Deserializer<SparkOptions> DESERIALIZER = new ParticleOptions.Deserializer<SparkOptions>() {
      public SparkOptions fromCommand(ParticleType<SparkOptions> type, StringReader reader) throws CommandSyntaxException {
         UUID uuid = UUID.fromString(reader.readString());
         return new SparkOptions(uuid);
      }

      public SparkOptions fromNetwork(ParticleType<SparkOptions> type, FriendlyByteBuf buf) {
         UUID uuid = buf.readUUID();
         return new SparkOptions(uuid);
      }
   };

}