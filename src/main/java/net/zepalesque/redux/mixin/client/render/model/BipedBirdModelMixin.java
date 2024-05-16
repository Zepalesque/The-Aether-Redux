package net.zepalesque.redux.mixin.client.render.model;

import com.aetherteam.aether.client.renderer.entity.model.BipedBirdModel;
import com.aetherteam.aether.entity.NotGrounded;
import com.aetherteam.aether.entity.WingedBird;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BipedBirdModel.class)
public class BipedBirdModelMixin<T extends Entity & WingedBird & NotGrounded> extends EntityModelMixin<T> {
    @Shadow @Final public ModelPart middleTailFeather;
    @Shadow @Final public ModelPart leftTailFeather;
    @Shadow @Final public ModelPart rightTailFeather;
    @Shadow @Final public ModelPart head;
    @Shadow @Final public ModelPart body;
    @Shadow @Final public ModelPart rightWing;
    @Shadow @Final public ModelPart leftWing;
    @Shadow @Final public ModelPart leftLeg;
    @Shadow @Final public ModelPart rightLeg;
    @Shadow @Final public ModelPart jaw;
}
