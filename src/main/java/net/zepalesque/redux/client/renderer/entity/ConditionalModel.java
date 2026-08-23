package net.zepalesque.redux.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ConditionalModel<E extends Entity, T extends EntityModel<E>, F extends EntityModel<E>, C extends BooleanSupplier> extends EntityModel<E> {
	final T modelTrue;
	final F modelFalse;
	final C predicate;
	
	public ConditionalModel(T modelTrue, F modelFalse, C predicate) {
		super(new CondFun<>(modelTrue, modelFalse, predicate));
		
		this.modelTrue = modelTrue;
		this.modelFalse = modelFalse;
		this.predicate = predicate;
	}
	
	private record CondFun<E extends Entity, T extends EntityModel<E>, F extends EntityModel<E>, C extends BooleanSupplier>(T modelTrue, F modelFalse, C predicate) implements Function<ResourceLocation, RenderType> {
		@Override
		public RenderType apply(ResourceLocation loc) {
			return this.predicate.getAsBoolean() ? this.modelTrue.renderType(loc) : this.modelFalse.renderType(loc);
		}
	}
	
	@Override
	public String toString() {
		return "ConditionalModel[" +
			"modelTrue=" + this.modelTrue +
			", modelFalse=" + this.modelFalse +
			", predicate=" + this.predicate +
			']';
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) return false;
		var that = (ConditionalModel<?, ?, ?, ?>) o;
		return Objects.equals(this.modelTrue, that.modelTrue) && Objects.equals(this.modelFalse, that.modelFalse) && Objects.equals(this.predicate, that.predicate);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.modelTrue, this.modelFalse, this.predicate);
	}
	
	@Override
	public void copyPropertiesTo(EntityModel<E> otherModel) {
		if (this.predicate.getAsBoolean())
			this.modelTrue.copyPropertiesTo(otherModel);
		else this.modelFalse.copyPropertiesTo(otherModel);
	}
	
	@Override
	public void prepareMobModel(E entity, float limbSwing, float swingAmount, float partial) {
		if (this.predicate.getAsBoolean())
			this.modelTrue.prepareMobModel(entity, limbSwing, swingAmount, partial);
		else this.modelFalse.prepareMobModel(entity, limbSwing, swingAmount, partial);
	}
	
	@Override
	public void setupAnim(E entity, float limbSwing, float swingAmount, float age, float yaw, float pitch) {
		if (this.predicate.getAsBoolean())
			this.modelTrue.setupAnim(entity, limbSwing, swingAmount, age, yaw, pitch);
		else this.modelFalse.setupAnim(entity, limbSwing, swingAmount, age, yaw, pitch);
	}
	
	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer vertices, int light, int overlay, int color) {
		if (this.predicate.getAsBoolean())
			this.modelTrue.renderToBuffer(stack, vertices, light, overlay, color);
		else this.modelFalse.renderToBuffer(stack, vertices, light, overlay, color);
	}
}
