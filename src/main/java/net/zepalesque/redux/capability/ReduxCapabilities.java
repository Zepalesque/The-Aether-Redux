package net.zepalesque.redux.capability;

import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.capability.CapabilityProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimation;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimationCapability;
import net.zepalesque.redux.capability.animation.moa.MoaAnimation;
import net.zepalesque.redux.capability.animation.moa.MoaAnimationCapability;
import net.zepalesque.redux.capability.animation.sentry.SentryAnimation;
import net.zepalesque.redux.capability.animation.sentry.SentryAnimationCapability;
import net.zepalesque.redux.capability.arrow.SubzeroArrow;
import net.zepalesque.redux.capability.arrow.SubzeroArrowCapability;
import net.zepalesque.redux.capability.cockatrice.CockatriceExtension;
import net.zepalesque.redux.capability.cockatrice.CockatriceExtensionCapability;
import net.zepalesque.redux.capability.living.VampireAmulet;
import net.zepalesque.redux.capability.living.VampireAmuletCapability;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.capability.player.ReduxPlayerCapability;
import net.zepalesque.redux.capability.swet.SwetMass;
import net.zepalesque.redux.capability.swet.SwetMassCapability;


@Mod.EventBusSubscriber(modid = Redux.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReduxCapabilities {
    public static final Capability<CockatriceExtension> COCKATRICE_EXTENSION = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<MoaAnimation> MOA_ANIM = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<ReduxPlayer> REDUX_PLAYER = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<SentryAnimation> SENTRY_ANIM = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<MimicAnimation> MIMIC_ANIM = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<SubzeroArrow> SUBZERO_ARROW = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<VampireAmulet> VAMPIRE_AMULET = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<SwetMass> SWET_MASS = CapabilityManager.get(new CapabilityToken<>() {
    });

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(CockatriceExtension.class);
        event.register(MoaAnimation.class);
        event.register(ReduxPlayer.class);
        event.register(SentryAnimation.class);
        event.register(MimicAnimation.class);
        event.register(SubzeroArrow.class);
        event.register(VampireAmulet.class);
        event.register(SwetMass.class);
    }

    public static IllegalStateException error() {
        return new IllegalStateException("Capability was not present despite check to ensure it was! This should not be possible!");
    }

    @Mod.EventBusSubscriber(modid = Redux.MODID)
    public static class Registration {
        @SubscribeEvent
        public static <T extends Sentry> void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {

            if (event.getObject() instanceof Cockatrice cockatrice) {
                event.addCapability(Redux.locate("cockatrice_extension"), new CapabilityProvider(ReduxCapabilities.COCKATRICE_EXTENSION, new CockatriceExtensionCapability(cockatrice)));
            }
            if (event.getObject() instanceof Moa moa && moa.level.isClientSide()) {
                event.addCapability(Redux.locate("moa_anim"), new CapabilityProvider(ReduxCapabilities.MOA_ANIM, new MoaAnimationCapability(moa)));
            }
            if (event.getObject() instanceof Player player) {
                event.addCapability(Redux.locate("redux_player"), new CapabilityProvider(ReduxCapabilities.REDUX_PLAYER, new ReduxPlayerCapability(player)));
            }
            if (event.getObject() instanceof Sentry sentry && sentry.level.isClientSide()) {
                event.addCapability(Redux.locate("sentry_anim"), new CapabilityProvider(ReduxCapabilities.SENTRY_ANIM, new SentryAnimationCapability(sentry)));
            }
            if (event.getObject() instanceof Mimic mimic && mimic.level.isClientSide()) {
                event.addCapability(Redux.locate("mimic_anim"), new CapabilityProvider(ReduxCapabilities.MIMIC_ANIM, new MimicAnimationCapability(mimic)));
            }
            if (event.getObject() instanceof AbstractArrow arrow) {
                event.addCapability(Redux.locate("subzero_arrow"), new CapabilityProvider(ReduxCapabilities.SUBZERO_ARROW, new SubzeroArrowCapability(arrow)));
            }
            if (event.getObject() instanceof LivingEntity entity) {
                event.addCapability(Redux.locate("vampire_amulet"), new CapabilityProvider(ReduxCapabilities.VAMPIRE_AMULET, new VampireAmuletCapability(entity)));
            }
            if (event.getObject() instanceof Swet entity) {
                event.addCapability(Redux.locate("swet_mass"), new CapabilityProvider(ReduxCapabilities.SWET_MASS, new SwetMassCapability(entity)));
            }
        }
    }
}
