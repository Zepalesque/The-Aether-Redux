package net.zepalesque.redux.advancement.predicate;

import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Set;

public class StatePredicate {
   public static final StatePredicate ANY = new StatePredicate(null, null, StatePropertiesPredicate.ANY);
   @Nullable
   private final TagKey<Block> tag;
   @Nullable
   private final Set<Block> blocks;
   private final StatePropertiesPredicate properties;

   public StatePredicate(@Nullable TagKey<Block> pTag, @Nullable Set<Block> pBlocks, StatePropertiesPredicate pProperties) {
      this.tag = pTag;
      this.blocks = pBlocks;
      this.properties = pProperties;
   }

   public boolean matches(BlockState blockstate) {
      if (this == ANY) {
         return true;
      } else {
         if (this.tag != null && !blockstate.is(this.tag)) {
            return false;
         } else if (this.blocks != null && !this.blocks.contains(blockstate.getBlock())) {
            return false;
         } else return this.properties.matches(blockstate);
      }
   }

   public static StatePredicate fromJson(@Nullable JsonElement pJson) {
      if (pJson != null && !pJson.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.convertToJsonObject(pJson, "block");
         Set<Block> set = null;
         JsonArray jsonarray = GsonHelper.getAsJsonArray(jsonobject, "blocks", null);
         if (jsonarray != null) {
            ImmutableSet.Builder<Block> builder = ImmutableSet.builder();

            for(JsonElement jsonelement : jsonarray) {
               ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.convertToString(jsonelement, "block"));
               builder.add(BuiltInRegistries.BLOCK.getOptional(resourcelocation).orElseThrow(() -> new JsonSyntaxException("Unknown block id '" + resourcelocation + "'")));
            }

            set = builder.build();
         }

         TagKey<Block> tagkey = null;
         if (jsonobject.has("tag")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.getAsString(jsonobject, "tag"));
            tagkey = TagKey.create(Registries.BLOCK, resourcelocation1);
         }

         StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.fromJson(jsonobject.get("state"));
         return new StatePredicate(tagkey, set, statepropertiespredicate);
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (this.blocks != null) {
            JsonArray jsonarray = new JsonArray();

            for(Block block : this.blocks) {
               jsonarray.add(BuiltInRegistries.BLOCK.getKey(block).toString());
            }

            jsonobject.add("blocks", jsonarray);
         }

         if (this.tag != null) {
            jsonobject.addProperty("tag", this.tag.location().toString());
         }

         jsonobject.add("state", this.properties.serializeToJson());
         return jsonobject;
      }
   }

   public static class Builder {
      @Nullable
      private Set<Block> blocks;
      @Nullable
      private TagKey<Block> tag;
      private StatePropertiesPredicate properties = StatePropertiesPredicate.ANY;

      private Builder() {
      }

      public static StatePredicate.Builder block() {
         return new StatePredicate.Builder();
      }

      public StatePredicate.Builder of(Block... pBlocks) {
         this.blocks = ImmutableSet.copyOf(pBlocks);
         return this;
      }

      public StatePredicate.Builder of(Iterable<Block> pBlocks) {
         this.blocks = ImmutableSet.copyOf(pBlocks);
         return this;
      }

      public StatePredicate.Builder of(TagKey<Block> pTag) {
         this.tag = pTag;
         return this;
      }


      public StatePredicate.Builder setProperties(StatePropertiesPredicate pProperties) {
         this.properties = pProperties;
         return this;
      }

      public StatePredicate build() {
         return new StatePredicate(this.tag, this.blocks, this.properties);
      }
   }
}