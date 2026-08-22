package net.zepalesque.redux.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Objects;
import java.util.function.Function;

public class ConditionalModel<E extends Entity, T extends EntityModel<E>, F extends EntityModel<E>> extends EntityModel<E> {
	final T modelTrue;
	final F modelFalse;
	final ModConfigSpec.BooleanValue config;
	
	public ConditionalModel(T modelTrue, F modelFalse, ModConfigSpec.BooleanValue config) {
		super(new CondFun<>(modelTrue, modelFalse, config));
		
		this.modelTrue = modelTrue;
		this.modelFalse = modelFalse;
		this.config = config;
	}
	
	private record CondFun<E extends Entity, T extends EntityModel<E>, F extends EntityModel<E>>(T modelTrue, F modelFalse, ModConfigSpec.BooleanValue config) implements Function<ResourceLocation, RenderType> {
		@Override
		public RenderType apply(ResourceLocation loc) {
			return this.config.getAsBoolean() ? this.modelTrue.renderType(loc) : this.modelFalse.renderType(loc);
		}
	}
	
	@Override
	public String toString() {
		return "ConditionalModel[" +
			"modelTrue=" + this.modelTrue +
			", modelFalse=" + this.modelFalse +
			", config=" + this.config +
			']';
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) return false;
		var that = (ConditionalModel<?, ?, ?>) o;
		return Objects.equals(this.modelTrue, that.modelTrue) && Objects.equals(this.modelFalse, that.modelFalse) && Objects.equals(this.config, that.config);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.modelTrue, this.modelFalse, this.config);
	}
	
	@Override
	public void copyPropertiesTo(EntityModel<E> otherModel) {
		if (this.config.getAsBoolean())
			this.modelTrue.copyPropertiesTo(otherModel);
		else this.modelFalse.copyPropertiesTo(otherModel);
	}
	
	@Override
	public void prepareMobModel(E entity, float limbSwing, float swingAmount, float partial) {
		if (this.config.getAsBoolean())
			this.modelTrue.prepareMobModel(entity, limbSwing, swingAmount, partial);
		else this.modelFalse.prepareMobModel(entity, limbSwing, swingAmount, partial);
	}
	
	@Override
	public void setupAnim(E entity, float limbSwing, float swingAmount, float age, float yaw, float pitch) {
		if (this.config.getAsBoolean())
			this.modelTrue.setupAnim(entity, limbSwing, swingAmount, age, yaw, pitch);
		else this.modelFalse.setupAnim(entity, limbSwing, swingAmount, age, yaw, pitch);
	}
	
	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer vertices, int light, int overlay, int color) {
		if (this.config.getAsBoolean())
			this.modelTrue.renderToBuffer(stack, vertices, light, overlay, color);
		else this.modelFalse.renderToBuffer(stack, vertices, light, overlay, color);
	}
}
