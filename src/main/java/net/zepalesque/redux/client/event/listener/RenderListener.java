package net.zepalesque.redux.client.event.listener;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.IPostRenderer;

import java.util.stream.StreamSupport;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderListener {

    @SubscribeEvent
    public static void renderPost(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            LevelRenderer renderer = event.getLevelRenderer();
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            ClientLevel level = minecraft.level;
            if (level == null || player == null) return;
            Frustum frustum = event.getFrustum();
            Camera camera = event.getCamera();
            EntityRenderDispatcher dispatch = minecraft.getEntityRenderDispatcher();
            float partial = Minecraft.getInstance().getPartialTick();
            RenderBuffers buffers = minecraft.renderBuffers();


            Vec3 vec3 = camera.getPosition();
            double x = vec3.x();
            double y = vec3.y();
            double z = vec3.z();

            PoseStack posestack = event.getPoseStack();

            Iterable<Entity> allEntities = level.entitiesForRendering();
            Iterable<Entity> whirlwinds = () -> StreamSupport.stream(allEntities.spliterator(), false)
                    .filter(e -> e.getType() == AetherEntityTypes.EVIL_WHIRLWIND.get() || e.getType() == AetherEntityTypes.WHIRLWIND.get()).iterator();

            for (Entity entity : whirlwinds)
                if (dispatch.shouldRender(entity, frustum, x, y, z) || entity.hasIndirectPassenger(player)) {
                    BlockPos blockpos = entity.blockPosition();
                    if ((level.isOutsideBuildHeight(blockpos.getY()) || renderer.isChunkCompiled(blockpos))
                        && (
                    entity != camera.getEntity()
                    || camera.isDetached()
                    || camera.getEntity() instanceof LivingEntity && ((LivingEntity) camera.getEntity()).isSleeping()
                    )) {
                        
                        MultiBufferSource.BufferSource multibuffersource = buffers.bufferSource();
                        
                        renderEntity(entity, x, y, z, partial, posestack, multibuffersource, dispatch);
                    }
                }
        }
    }

    private static void renderEntity(Entity entity, double camX, double camY, double camZ, float partialTick, PoseStack poseStack, MultiBufferSource.BufferSource buffer, EntityRenderDispatcher dispatcher) {
        double x = Mth.lerp(partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(partialTick, entity.zOld, entity.getZ());
        float f = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
        render(entity, x - camX, y - camY, z - camZ, f, partialTick, poseStack, buffer, dispatcher);
    }

    private static <E extends Entity> void render(
            E entity,
            double x,
            double y,
            double z,
            float rotationYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource.BufferSource buffer,
            EntityRenderDispatcher dispatcher) {

        EntityRenderer<? super E> entityrenderer = dispatcher.getRenderer(entity);

        if (entityrenderer instanceof IPostRenderer<?> post) {
            Vec3 vec3 = entityrenderer.getRenderOffset(entity, partialTicks);
            double d2 = x + vec3.x();
            double d3 = y + vec3.y();
            double d0 = z + vec3.z();
            poseStack.pushPose();
            poseStack.translate(d2, d3, d0);
            if (!post.actuallyRender(entity, rotationYaw, partialTicks, poseStack, buffer, dispatcher.getPackedLightCoords(entity, partialTicks)))
                Redux.LOGGER.debug("Did not render entity: {}", entity);
            else buffer.endBatch();
            poseStack.popPose();
        }
    }
}
