//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.AetherTags.Fluids;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.fluid.ReduxFluids;

public class ReduxFluidTagData extends FluidTagsProvider {
    public ReduxFluidTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, Redux.MODID, helper);
    }

    public void addTags(HolderLookup.Provider provider) {
        this.tag(Fluids.ALLOWED_BUCKET_PICKUP).add(ReduxFluids.SYRUP_STILL.get(), ReduxFluids.SYRUP_FLOW.get());
    }
}
