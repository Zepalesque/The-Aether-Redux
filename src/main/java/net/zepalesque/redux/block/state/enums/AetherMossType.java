package net.zepalesque.redux.block.state.enums;

import net.minecraft.util.StringRepresentable;

public enum AetherMossType implements StringRepresentable {
	FLUTEMOSS("flutemoss"), BLEAKMOSS("bleakmoss"), GILDENMOSS("gildenmoss");
	
	final String id;
	AetherMossType(String name) {
		this.id = name;
	}
	
	@Override
	public String getSerializedName() {
		return this.id;
	}
}
