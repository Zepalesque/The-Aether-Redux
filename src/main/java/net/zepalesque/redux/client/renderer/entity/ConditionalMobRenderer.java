/*
package net.zepalesque.redux.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public class ConditionalMobRenderer<E extends Mob, TM extends EntityModel<E>, FM extends EntityModel<E>, TR extends MobRenderer<E, TM>, FR extends MobRenderer<E, FM>> extends MobRenderer<E, ConditionalModel<E, TM, FM>>{
	final TR trueRend;
	final FR falseRend;
	final ModConfigSpec.BooleanValue cfg;
	
	public ConditionalMobRenderer(EntityRendererProvider.Context ctx, TR trueRend, FR falseRend, ModConfigSpec.BooleanValue cfg) {
		super(ctx, new ConditionalModel<>(trueRend.getModel(), falseRend.getModel(), cfg), 0f);
		this.trueRend = trueRend;
		this.falseRend = falseRend;
		this.cfg = cfg;
	}
	
	@Override
	public ResourceLocation getTextureLocation(E e) {
		return this.cfg.getAsBoolean() ? this.trueRend.getTextureLocation(e) : this.falseRend.getTextureLocation(e);
	}
	
	@Override
	protected boolean isBodyVisible(E livingEntity) {
		return this.cfg.getAsBoolean()
			? this.trueRend.isBodyVisible(livingEntity)
			: this.falseRend.isBodyVisible(livingEntity);
	}
	
	@Override
	public boolean shouldRender(E livingEntity, Frustum camera, double camX, double camY, double camZ) {
		return this.cfg.getAsBoolean()
			? this.trueRend.shouldRender(livingEntity, camera, camX, camY, camZ)
			: this.falseRend.shouldRender(livingEntity, camera, camX, camY, camZ);
	}
	
	@Override
	protected boolean isShaking(E entity) {
		return this.cfg.getAsBoolean()
			? this.trueRend.isShaking(entity)
			: this.falseRend.isShaking(entity);
	}
	
	@Override
	protected boolean shouldShowName(E entity) {
		return this.cfg.getAsBoolean()
			? this.trueRend.shouldShowName(entity)
			: this.falseRend.shouldShowName(entity);
	}
	
	@Override
	protected float getAttackAnim(E livingBase, float partialTickTime) {
		return this.cfg.getAsBoolean()
			? this.trueRend.getAttackAnim(livingBase, partialTickTime)
			: this.falseRend.getAttackAnim(livingBase, partialTickTime);
	}
	
	@Override
	protected float getBob(E livingBase, float partialTick) {
		return this.cfg.getAsBoolean()
			? this.trueRend.getBob(livingBase, partialTick)
			: this.falseRend.getBob(livingBase, partialTick);
	}
	
	@Override
	protected float getFlipDegrees(E livingEntity) {
		return this.cfg.getAsBoolean()
			? this.trueRend.getFlipDegrees(livingEntity)
			: this.falseRend.getFlipDegrees(livingEntity);
	}
	
	@Override
	protected float getShadowRadius(E entity) {
		return this.cfg.getAsBoolean()
			? this.trueRend.getShadowRadius(entity)
			: this.falseRend.getShadowRadius(entity);
	}
	
	@Override
	protected float getWhiteOverlayProgress(E livingEntity, float partialTicks) {
		return this.cfg.getAsBoolean()
			? this.trueRend.getWhiteOverlayProgress(livingEntity, partialTicks)
			: this.falseRend.getWhiteOverlayProgress(livingEntity, partialTicks);
	}
	
	@Override
	protected int getBlockLightLevel(E entity, BlockPos pos) {
		return this.cfg.getAsBoolean()
			? this.trueRend.getBlockLightLevel(entity, pos)
			: this.falseRend.getBlockLightLevel(entity, pos);
	}
	
	@Override
	protected int getSkyLightLevel(E entity, BlockPos pos) {
		return this.cfg.getAsBoolean()
			? this.trueRend.getSkyLightLevel(entity, pos)
			: this.falseRend.getSkyLightLevel(entity, pos);
	}
	
	@Nullable
	@Override
	protected RenderType getRenderType(E livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
		return this.cfg.getAsBoolean()
			? this.trueRend.getRenderType(livingEntity, bodyVisible, translucent, glowing)
			: this.falseRend.getRenderType(livingEntity, bodyVisible, translucent, glowing);
	}
	
	@Override
	protected void renderNameTag(E entity, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick) {
		if (this.cfg.getAsBoolean())
			this.trueRend.renderNameTag(entity, displayName, poseStack, bufferSource, packedLight, partialTick);
			else this.falseRend.renderNameTag(entity, displayName, poseStack, bufferSource, packedLight, partialTick);
	}
	
	@Override
	protected void scale(E livingEntity, PoseStack poseStack, float partialTickTime) {
		if (this.cfg.getAsBoolean())
			this.trueRend.scale(livingEntity, poseStack, partialTickTime);
		else this.falseRend.scale(livingEntity, poseStack, partialTickTime);
	}
	
	@Override
	protected void setupRotations(E entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
		if (this.cfg.getAsBoolean())
			this.trueRend.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
		else this.falseRend.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
	}
	
	@Override
	public Font getFont() {
		return this.cfg.getAsBoolean()
			? this.trueRend.getFont()
			: this.falseRend.getFont();
	}
	
	@Override
	public Vec3 getRenderOffset(E entity, float partialTicks) {
		return this.cfg.getAsBoolean()
			? this.trueRend.getRenderOffset(entity, partialTicks)
			: this.falseRend.getRenderOffset(entity, partialTicks);
	}
	
	@Override
	public void render(E entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		if (this.cfg.getAsBoolean())
			this.trueRend.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
		else this.falseRend.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}
}
*/
