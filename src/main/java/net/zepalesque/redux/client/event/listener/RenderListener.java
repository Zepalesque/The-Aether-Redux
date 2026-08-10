package net.zepalesque.redux.client.event.listener;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.stream.StreamSupport;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
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
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.renderer.api.IPostRenderer;

@EventBusSubscriber(Dist.CLIENT)
public class RenderListener {

    @SubscribeEvent
    public static void renderPost(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            LevelRenderer renderer = event.getLevelRenderer();
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            ClientLevel level = minecraft.level;
            if (level == null || player == null) return;
	        var frustum = event.getFrustum();
	        var cam = event.getCamera();
	        var renderDisp = minecraft.getEntityRenderDispatcher();
	        var timer = minecraft.getTimer();
	        var ticker = level.tickRateManager();
	        var bufs = minecraft.renderBuffers();
	        
	        
	        var camPos = cam.getPosition();
	        var x = camPos.x();
	        var y = camPos.y();
	        var z = camPos.z();
	        
	        var stack = event.getPoseStack();
            
			// TODO: find more optimized way to do this while still letting it be functional and stuff
	        //  (THIS IS WHY I WANT STRUCTS IN JAVA ffs even closures are expensive like they could be trivial with monomorphization and structs)
            var entities = level.entitiesForRendering();
//            var stream = StreamSupport.stream(entities.spliterator(), true)
//                .filter(e -> e.getType() == AetherEntityTypes.EVIL_WHIRLWIND.get());
//            Iterable<Entity> whirlwinds = stream::iterator;
			
            for (var entity : entities) {
				if (entity.getType() != AetherEntityTypes.EVIL_WHIRLWIND.get())
					break;
             
	            if (renderDisp.shouldRender(entity, frustum, x, y, z) || entity.hasIndirectPassenger(player)) {
		            var pos = entity.blockPosition();
	                if ((level.isOutsideBuildHeight(pos.getY()) || renderer.isSectionCompiled(pos))
		                && (
							entity != cam.getEntity() || cam.isDetached()
								|| cam.getEntity() instanceof LivingEntity living && living.isSleeping()
	                )) {
		                
		                var buf = bufs.bufferSource();
		                
		                
		                var partial = timer.getGameTimeDeltaPartialTick(!ticker.isEntityFrozen(entity));
	                    renderEntity(entity, x, y, z, partial, stack, buf, renderDisp);
	                }
	            }
            }
            
//            stream.close();
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
