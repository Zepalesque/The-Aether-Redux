package net.zepalesque.redux.mixin.mixins.common.world.structure;

import com.aetherteam.aether.world.structurepiece.AetherTemplateStructurePiece;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AetherTemplateStructurePiece.class)
public class AetherTemplateStructurePieceMixin {
	// TODO: PR to make this possible without mixins
	/*@Inject(method = "addProcessors", at = @At(value = "RETURN"), cancellable = true)
	protected static void redux$makeSettings(
		StructurePlaceSettings settings,
		Holder<StructureProcessorList> processors,
		CallbackInfoReturnable<StructurePlaceSettings> cir
	) {
		if ( instanceof BronzeDungeonRoom) {
			StructurePlaceSettings original = cir.getReturnValue();
			StructurePlaceSettings modified = original.addProcessor(ReduxDungeonProcessors.BRONZE_BLOCKS).addProcessor(ReduxDungeonProcessors.BRONZE_TRAPS);
			cir.setReturnValue(modified);
		}
	}*/
}
