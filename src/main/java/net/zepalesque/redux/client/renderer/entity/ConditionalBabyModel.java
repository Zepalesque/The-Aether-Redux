package net.zepalesque.redux.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.util.BoolCell;

public final class ConditionalBabyModel<E extends LivingEntity, T extends EntityModel<E>, F extends EntityModel<E>, C extends BooleanSupplier> extends ConditionalModel<E, T, F, C> {
	final T babyModelTrue;
	
	final BoolCell isBaby;
	
	public ConditionalBabyModel(T modelTrue, T babyModelTrue, F modelFalse, C predicate) {
		this(modelTrue, babyModelTrue, modelFalse, predicate, new BoolCell(false));
	}
	private ConditionalBabyModel(T modelTrue, T babyModelTrue, F modelFalse, C predicate, BoolCell isBaby) {
		super(modelTrue, modelFalse, predicate, new BabyCondFun<>(modelTrue, babyModelTrue, modelFalse, predicate, isBaby));
		this.babyModelTrue = babyModelTrue;
		this.isBaby = isBaby;
	}
	
	
	protected record BabyCondFun<E extends LivingEntity, T extends EntityModel<E>, F extends EntityModel<E>, C extends BooleanSupplier>(T modelTrue, T babyModelTrue, F modelFalse, C predicate, BoolCell isBaby) implements Function<ResourceLocation, RenderType> {
		@Override
		public RenderType apply(ResourceLocation loc) {
			return this.predicate.getAsBoolean() ? this.isBaby.deref ? this.babyModelTrue.renderType(loc) : this.modelTrue.renderType(loc) : this.modelFalse.renderType(loc);
		}
	}
	
	@Override
	public void setupAnim(E entity, float limbSwing, float swingAmount, float age, float yaw, float pitch) {
		if (this.predicate.getAsBoolean() && this.isBaby.deref) this.babyModelTrue.setupAnim(entity, limbSwing, swingAmount, age, yaw, pitch);
		else super.setupAnim(entity, limbSwing, swingAmount, age, yaw, pitch);
	}
	
	@Override
	public void prepareMobModel(E entity, float limbSwing, float swingAmount, float partial) {
		this.isBaby.deref = entity.isBaby();
		if (this.predicate.getAsBoolean() && this.isBaby.deref) this.babyModelTrue.prepareMobModel(entity, limbSwing, swingAmount, partial);
		else super.prepareMobModel(entity, limbSwing, swingAmount, partial);
	}
	
	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer vertices, int light, int overlay, int color) {
		if (this.predicate.getAsBoolean() && this.isBaby.deref) this.babyModelTrue.renderToBuffer(stack, vertices, light, overlay, color);
		else super.renderToBuffer(stack, vertices, light, overlay, color);
	}
	
	@Override
	public String toString() {
		return "ConditionalBabyModel[" +
			"babyModelTrue=" + this.babyModelTrue +
			", isBaby=" + this.isBaby +
			", modelTrue=" + this.modelTrue +
			", modelFalse=" + this.modelFalse +
			", predicate=" + this.predicate +
			']';
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ConditionalBabyModel<?, ?, ?, ?> that = (ConditionalBabyModel<?, ?, ?, ?>) o;
		return Objects.equals(this.babyModelTrue, that.babyModelTrue) && Objects.equals(this.isBaby, that.isBaby);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), this.babyModelTrue, this.isBaby);
	}
}
