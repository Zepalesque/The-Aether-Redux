package net.zepalesque.redux.client.audio;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Redux.MODID);
    public static final RegistryObject<SoundEvent> SENTRY_LAND_BASE = register("entity.sentry.land_base");
    public static final RegistryObject<SoundEvent> SENTRY_HURT = register("entity.sentry.hurt");
    public static final RegistryObject<SoundEvent> SENTRY_AMBIENT = register("entity.sentry.ambient");
    public static final RegistryObject<SoundEvent> SENTRY_DEATH = register("entity.sentry.death");
    public static final RegistryObject<SoundEvent> SENTRY_POUNCE = register("entity.sentry.pounce");
    public static final RegistryObject<SoundEvent> SENTRY_LAND = register("entity.sentry.land");
    public static final RegistryObject<SoundEvent> MOA_AMBIENT = register("entity.moa.ambient");
    public static final RegistryObject<SoundEvent> MOA_HURT = register("entity.moa.hurt");
    public static final RegistryObject<SoundEvent> MOA_DEATH = register("entity.moa.death");
    public static final RegistryObject<SoundEvent> AERWHALE_AMBIENT = register("entity.aerwhale.ambient");
    public static final RegistryObject<SoundEvent> SLIDER_HURT = register("entity.slider.hurt");
    public static final RegistryObject<SoundEvent> SLIDER_AMBIENT = register("entity.slider.ambient");
    public static final RegistryObject<SoundEvent> MIMIC_SLAM = register("entity.mimic.slam");
    public static final RegistryObject<SoundEvent> MIMIC_AWAKEN = register("entity.mimic.awaken");

    public static final RegistryObject<SoundEvent> GLOWSTONE_BREAK = register("block.glowstone.break");
    public static final RegistryObject<SoundEvent> GLOWSTONE_PLACE = register("block.glowstone.place");
    public static final RegistryObject<SoundEvent> GLOWSTONE_STEP = register("block.glowstone.step");
    public static final RegistryObject<SoundEvent> GLOWSTONE_FALL = register("block.glowstone.fall");
    public static final RegistryObject<SoundEvent> GLOWSTONE_HIT = register("block.glowstone.hit");

    public static final RegistryObject<SoundEvent> FUNGUS_BOUNCE = register("block.fungus.bounce");

    public static final RegistryObject<SoundEvent> TEMPEST_AMBIENT = register("entity.tempest.ambient");
    public static final RegistryObject<SoundEvent> TEMPEST_HURT = register("entity.tempest.hurt");
    public static final RegistryObject<SoundEvent> TEMPEST_DEATH = register("entity.tempest.death");
    public static final RegistryObject<SoundEvent> TEMPEST_ZAP = register("entity.tempest.zap");

    public static final RegistryObject<SoundEvent> EQUIP_BITTERSWEET_CHARM = register("item.accessory.equip_bittersweet_charm");
    public static final RegistryObject<SoundEvent> EQUIP_ENCHANTED_RING = register("item.accessory.equip_enchanted_ring");
    public static final RegistryObject<SoundEvent> EQUIP_VALKYRIE_RING = register("item.accessory.equip_valkyrie_ring");
    public static final RegistryObject<SoundEvent> EQUIP_WISDOM_RING = register("item.accessory.equip_wisdom_ring");
    public static final RegistryObject<SoundEvent> EQUIP_PHOENIX_RING = register("item.accessory.equip_phoenix_ring");
    public static final RegistryObject<SoundEvent> EQUIP_VAMPIRE_AMULET = register("item.accessory.equip_vampire_amulet");

    public static final RegistryObject<SoundEvent> CONVERT_AMBROSIUM = register("item.convert.ambrosium");
    public static final RegistryObject<SoundEvent> CONVERT_SWET_BALL = register("item.convert.swet_ball");
    public static final RegistryObject<SoundEvent> CONVERT_BLIGHTED_SPORES = register("item.convert.blighted_spores");
    public static final RegistryObject<SoundEvent> INFUSE_ITEM = register("item.sfx.infuse");

    public static final RegistryObject<SoundEvent> BOOST_JUMP = register("generic.boost_jump");
    public static final RegistryObject<SoundEvent> FIREBALL_SHOOT = register("generic.fireball_shoot");

    public static final RegistryObject<SoundEvent> BLIGHTSHADE_SPRAY = register("block.blightshade_spray");
    public static final RegistryObject<SoundEvent> QUICKROOTS_PICK = register("block.quickroots_pick");
    public static final RegistryObject<SoundEvent> LIGHTROOTS_PICK = register("block.lightroots_pick");

    public static final RegistryObject<SoundEvent> COCKATRICE_AMBIENT = register("entity.cockatrice.ambient");
    public static final RegistryObject<SoundEvent> COCKATRICE_HURT = register("entity.cockatrice.hurt");
    public static final RegistryObject<SoundEvent> COCKATRICE_DEATH = register("entity.cockatrice.death");

    public static final RegistryObject<SoundEvent> AECHOR_PLANT_DEATH = register("entity.aechor_plant.death");
    public static final RegistryObject<SoundEvent> AECHOR_PLANT_HURT = register("entity.aechor_plant.hurt");
    public static final RegistryObject<SoundEvent> MUSIC_DISC_LABYRINTHS_VENGEANCE = register("item.music_disc.labyrinths_vengeance");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(Redux.locate(name)));
    }
}
