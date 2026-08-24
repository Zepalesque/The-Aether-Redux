package net.zepalesque.redux.data.prov;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.redux.mixin.mixins.common.accessor.LangProviderAccessor;
import net.zepalesque.unity.data.prov.UnityLanguageProvider;
import org.jetbrains.annotations.Nullable;

public abstract class ReduxLanguageProvider extends UnityLanguageProvider {
	//protected final Map<String, String> TIPS = new HashMap<>();

	public ReduxLanguageProvider(PackOutput output, String id) {
		super(output, id);
	}

	/*@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		return generateTips(super.run(cache), cache);
	}*/

	@Nullable protected String remove(String key) {
		return ((LangProviderAccessor) this).redux$getData().remove(key);
	}
	
	protected <T> void addCfg(ModConfigSpec.ConfigValue<T> cfg) {
		var key = cfg.getSpec().getTranslationKey();
		if (key == null) return;
		this.add(key, cfg.getPath().getLast());
	}
	
	protected void addDeath(ResourceKey<DamageType> key, String name) {
		this.add("death.attack." + this.id + "." + key.location().getPath(), name);
	}
	
	protected void addDeathByPlayer(ResourceKey<DamageType> key, String name) {
		this.add("death.attack." + this.id + "." + key.location().getPath() + ".player", name);
	}

	/*private CompletableFuture<?> generateTips(CompletableFuture<?> languageGen, CachedOutput cache) {
		ImmutableList.Builder<CompletableFuture<?>> futuresBuilder = new ImmutableList.Builder<>();
		futuresBuilder.add(languageGen);

		for (Map.Entry<String, String> entry : this.TIPS.entrySet()) {
			JsonObject object = new JsonObject();
			object.add("title", Component.Serializer.toJson(Component.translatable("tipsmod.title.default").withStyle(ChatFormatting.BOLD, ChatFormatting.UNDERLINE).withStyle(style -> style.withColor(Redux.REDUX_PURPLE))));
			object.add("tip", Component.Serializer.toJsonTree(Component.translatable((entry).getKey()).withStyle(ChatFormatting.WHITE)));
			futuresBuilder.add(DataProvider.saveStable(cache, GSON.toJsonTree(object), this.out.getOutputFolder().resolve("packs/resource/redux_tips/assets/aether_redux/tips/" + entry.getValue() + ".json")));
		}

		return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
	}*/
}
