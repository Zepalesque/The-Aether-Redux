package net.zepalesque.redux.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;

public class ReduxSoundData extends SoundDefinitionsProvider {
    public ReduxSoundData(PackOutput output, ExistingFileHelper helper) {
        super(output, Redux.MODID, helper);
    }

    @Override
    public void registerSounds() {

        this.add(ReduxSoundEvents.MIMIC_SLAM,
                definition().with(sound("aether_redux:entity/mimic/mimic_slam"))
                        .subtitle("subtitles.aether_redux.entity.mimic_slam"));
        this.add(ReduxSoundEvents.HEARTBEAT_FAST,
                definition().with(sound("aether_redux:generic/heartbeat/heartbeat_fast")));
        this.add(ReduxSoundEvents.HEARTBEAT_MED,
                definition().with(sound("aether_redux:generic/heartbeat/heartbeat_med")));
        this.add(ReduxSoundEvents.HEARTBEAT_SLOW,
                definition().with(sound("aether_redux:generic/heartbeat/heartbeat_slow")));
        this.add(ReduxSoundEvents.SENTRY_LAND_BASE,
                definition().with(sound("minecraft:mob/slime/big1"), sound("minecraft:mob/slime/big2"), sound("minecraft:mob/slime/big3"), sound("minecraft:mob/slime/big4"))
                        .subtitle("subtitles.aether.entity.sentry.jump"));
        this.add(ReduxSoundEvents.SENTRY_AMBIENT, definition().with(sound("aether_redux:generic/silence")));
        this.add(ReduxSoundEvents.EQUIP_BITTERSWEET_CHARM,
                definition().with(
                        sound("minecraft:item/armor/equip_chain1"),
                        sound("minecraft:item/armor/equip_chain2"),
                        sound("minecraft:item/armor/equip_chain3"),
                        sound("minecraft:item/armor/equip_chain4"),
                        sound("minecraft:item/armor/equip_chain5"),
                        sound("minecraft:item/armor/equip_chain6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_bittersweet_charm")
        );
        this.add(ReduxSoundEvents.EQUIP_ENCHANTED_RING,
                definition().with(
                        sound("minecraft:mob/turtle/armor")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_enchanted_ring")
        );
        this.add(ReduxSoundEvents.EQUIP_GRAND_MEDAL,
                definition().with(
                        sound("minecraft:item/armor/equip_gold1"),
                        sound("minecraft:item/armor/equip_gold2"),
                        sound("minecraft:item/armor/equip_gold3"),
                        sound("minecraft:item/armor/equip_gold4"),
                        sound("minecraft:item/armor/equip_gold5"),
                        sound("minecraft:item/armor/equip_gold6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_valkyrie_ring")
        );
        this.add(ReduxSoundEvents.EQUIP_WISDOM_RING,
                definition().with(
                        sound("minecraft:item/armor/equip_iron1"),
                        sound("minecraft:item/armor/equip_iron2"),
                        sound("minecraft:item/armor/equip_iron3"),
                        sound("minecraft:item/armor/equip_iron4"),
                        sound("minecraft:item/armor/equip_iron5"),
                        sound("minecraft:item/armor/equip_iron6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_wisdom_ring")
        );
        this.add(ReduxSoundEvents.EQUIP_SENTRY_RING,
                definition().with(
                        sound("minecraft:item/armor/equip_iron1"),
                        sound("minecraft:item/armor/equip_iron2"),
                        sound("minecraft:item/armor/equip_iron3"),
                        sound("minecraft:item/armor/equip_iron4"),
                        sound("minecraft:item/armor/equip_iron5"),
                        sound("minecraft:item/armor/equip_iron6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_sentry_ring")
        );
        this.add(ReduxSoundEvents.EQUIP_VAMPIRE_AMULET,
                definition().with(
                        sound("minecraft:item/armor/equip_gold1"),
                        sound("minecraft:item/armor/equip_gold2"),
                        sound("minecraft:item/armor/equip_gold3"),
                        sound("minecraft:item/armor/equip_gold4"),
                        sound("minecraft:item/armor/equip_gold5"),
                        sound("minecraft:item/armor/equip_gold6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_vampire_amulet")
        );
        this.add(ReduxSoundEvents.BOOST_JUMP,
                definition().with(sound("aether_redux:generic/boost_jump"))
                        .subtitle("subtitles.aether_redux.generic.boost_jump"));
        this.add(ReduxSoundEvents.FIREBALL_SHOOT,
                definition().with(sound("aether_redux:generic/fireball_shoot"))
                        .subtitle("subtitles.aether_redux.generic.fireball_shoot"));
        this.add(ReduxSoundEvents.BLIGHTSHADE_SPRAY,
                definition().with(sound("aether_redux:block/blightshade_spray"))
                        .subtitle("subtitles.aether_redux.block.blightshade_spray"));
        this.add(ReduxSoundEvents.MUSIC_DISC_LABYRINTHS_VENGEANCE, definition().with(sound("aether_redux:music/evk/labyrinths_vengeance_mono").stream()));
        this.add(ReduxSoundEvents.CONVERT_AMBROSIUM,
                definition().with(sound("aether_redux:item/convert_ambrosium"))
                        .subtitle("subtitles.aether_redux.item.convert_ambrosium"));
        this.add(ReduxSoundEvents.CONVERT_SWET_BALL,
                definition().with(sound("aether_redux:item/convert_swet_ball"))
                        .subtitle("subtitles.aether_redux.item.convert_swet_ball"));
        this.add(ReduxSoundEvents.CONVERT_BLIGHTED_SPORES,
                definition().with(sound("aether_redux:item/convert_blighted_spores"))
                        .subtitle("subtitles.aether_redux.item.convert_blighted_spores"));
        this.add(ReduxSoundEvents.INFUSE_ITEM,
                definition().with(sound("aether_redux:item/infuse_item"))
                        .subtitle("subtitles.aether_redux.item.infuse_item"));
        this.add(ReduxSoundEvents.QUICKROOTS_PICK, definition().with(
                sound("minecraft:item/sweet_berries/pick_from_bush1"),
                sound("minecraft:item/sweet_berries/pick_from_bush2")
        ).subtitle("subtitles.aether_redux.block.quickroots_pick"));
        this.add(ReduxSoundEvents.LIGHTROOTS_PICK, definition().with(
                sound("minecraft:item/sweet_berries/pick_from_bush1"),
                sound("minecraft:item/sweet_berries/pick_from_bush2")
        ).subtitle("subtitles.aether_redux.block.lightroots_pick"));
        this.add(ReduxSoundEvents.FUNGUS_BOUNCE,
                definition().with(sound("aether_redux:block/fungus/bounce"))
                        .subtitle("subtitles.aether_redux.block.fungus_bounce")
        );

        this.add(ReduxSoundEvents.EMBER_BOUNCE_BIG, definition().with(
                sound("aether_redux:entity/ember/ember_bouncebig")
        ).subtitle("subtitles.aether_redux.entity.ember_bounce"));

        this.add(ReduxSoundEvents.EMBER_BOUNCE_MED, definition().with(
                sound("aether_redux:entity/ember/ember_bouncemed")
        ).subtitle("subtitles.aether_redux.entity.ember_bounce"));

        this.add(ReduxSoundEvents.EMBER_BOUNCE_SMALL, definition().with(
                sound("aether_redux:entity/ember/ember_bouncesmall")
        ).subtitle("subtitles.aether_redux.entity.ember_bounce"));

        this.add(ReduxSoundEvents.GLOWSTONE_BREAK,
                definition().with(
                        sound("aether_redux:block/glowstone/break1"),
                        sound("aether_redux:block/glowstone/break2"),
                        sound("aether_redux:block/glowstone/break3")
                ).subtitle("subtitles.block.generic.break")
        );
        this.add(ReduxSoundEvents.GLOWSTONE_PLACE,
                definition().with(
                        sound("aether_redux:block/glowstone/place1"),
                        sound("aether_redux:block/glowstone/place2"),
                        sound("aether_redux:block/glowstone/place3"),
                        sound("aether_redux:block/glowstone/place4")
                ).subtitle("subtitles.block.generic.place")
        );
        this.add(ReduxSoundEvents.GLOWSTONE_STEP,
                definition().with(
                        sound("aether_redux:block/glowstone/step1"),
                        sound("aether_redux:block/glowstone/step2"),
                        sound("aether_redux:block/glowstone/step3"),
                        sound("aether_redux:block/glowstone/step4"),
                        sound("aether_redux:block/glowstone/step5"),
                        sound("aether_redux:block/glowstone/step6"),
                        sound("aether_redux:block/glowstone/step7"),
                        sound("aether_redux:block/glowstone/step8"),
                        sound("aether_redux:block/glowstone/step9"),
                        sound("aether_redux:block/glowstone/step10"),
                        sound("aether_redux:block/glowstone/step11"),
                        sound("aether_redux:block/glowstone/step12")
                ).subtitle("subtitles.block.generic.footsteps")
        );
        this.add(ReduxSoundEvents.GLOWSTONE_HIT,
                definition().with(
                        sound("aether_redux:block/glowstone/step1"),
                        sound("aether_redux:block/glowstone/step2"),
                        sound("aether_redux:block/glowstone/step3"),
                        sound("aether_redux:block/glowstone/step4"),
                        sound("aether_redux:block/glowstone/step5"),
                        sound("aether_redux:block/glowstone/step6"),
                        sound("aether_redux:block/glowstone/step7"),
                        sound("aether_redux:block/glowstone/step8"),
                        sound("aether_redux:block/glowstone/step9"),
                        sound("aether_redux:block/glowstone/step10"),
                        sound("aether_redux:block/glowstone/step11"),
                        sound("aether_redux:block/glowstone/step12")
                ).subtitle("subtitles.block.generic.hit")
        );
        this.add(ReduxSoundEvents.GLOWSTONE_FALL,
                definition().with(
                        sound("aether_redux:block/glowstone/step1"),
                        sound("aether_redux:block/glowstone/step2"),
                        sound("aether_redux:block/glowstone/step3"),
                        sound("aether_redux:block/glowstone/step4"),
                        sound("aether_redux:block/glowstone/step5"),
                        sound("aether_redux:block/glowstone/step6"),
                        sound("aether_redux:block/glowstone/step7"),
                        sound("aether_redux:block/glowstone/step8"),
                        sound("aether_redux:block/glowstone/step9"),
                        sound("aether_redux:block/glowstone/step10"),
                        sound("aether_redux:block/glowstone/step11"),
                        sound("aether_redux:block/glowstone/step12")
                )
        );

    }

}