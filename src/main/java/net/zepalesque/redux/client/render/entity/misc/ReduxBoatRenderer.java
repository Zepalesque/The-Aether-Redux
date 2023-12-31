package net.zepalesque.redux.client.render.entity.misc;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.Redux;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class    ReduxBoatRenderer extends BoatRenderer {
    private final ResourceLocation boat_texture;
    private final Pair<ResourceLocation, ListModel<Boat>> skyrootBoatResource;

    public ReduxBoatRenderer(EntityRendererProvider.Context context, boolean chest, String wood) {
        super(context, chest);
        this.boat_texture = new ResourceLocation(Redux.MODID, "textures/entity/" + (chest ? "chest_" : "") + "boat/" + wood + ".png");
        this.skyrootBoatResource = Pair.of(boat_texture, chest ? new ChestBoatModel(context.bakeLayer(AetherModelLayers.SKYROOT_CHEST_BOAT)) : new BoatModel(context.bakeLayer(AetherModelLayers.SKYROOT_BOAT)));
    }

    @Nonnull
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(@Nonnull Boat boat) {
        return this.skyrootBoatResource;
    }
}
