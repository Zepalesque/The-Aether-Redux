package net.zepalesque.redux.block.state.enums;

import net.minecraft.util.StringRepresentable;
import net.zepalesque.redux.client.ReduxColors;
import org.jetbrains.annotations.Nullable;

public enum BlightGrassColor implements CustomTintingProperty, StringRepresentable {
	TINTABLE("tintable"), CONSTANT("constant", ReduxColors.Tints.BLIGHT_GRASS_COLOR);

	final String name;
	@Nullable final Integer color;
	BlightGrassColor(String name) {
		this.name = name;
		this.color = null;
	}

	BlightGrassColor(String name, int color) {
		this.name = name;
		this.color = color;
	}

	@Nullable @Override
	public Integer colorOverride() {
		return this.color;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
