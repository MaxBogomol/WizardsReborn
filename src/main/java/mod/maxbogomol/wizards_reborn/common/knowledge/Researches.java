package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Research;
import mod.maxbogomol.wizards_reborn.api.knowledge.ResearchMapEntry;
import mod.maxbogomol.wizards_reborn.api.knowledge.ResearchMonogramEntry;

public class Researches {
    public static Research
            EARTH_PROJECTILE, WATER_PROJECTILE, AIR_PROJECTILE, FIRE_PROJECTILE, VOID_PROJECTILE, FROST_PROJECTILE, HOLY_PROJECTILE, CURSE_PROJECTILE,
            EARTH_RAY, WATER_RAY, AIR_RAY, FIRE_RAY, VOID_RAY, FROST_RAY, HOLY_RAY, CURSE_RAY,
            HEART_OF_NATURE, WATER_BREATHING, AIR_FLOW, FIRE_SHIELD, BLINK, SNOWFLAKE, HOLY_CROSS, CURSE_CROSS, POISON,
            MAGIC_SPROUT, DIRT_BLOCK, WATER_BLOCK, AIR_IMPACT, ICE_BLOCK,
            EARTH_CHARGE, WATER_CHARGE, AIR_CHARGE, FIRE_CHARGE, VOID_CHARGE, FROST_CHARGE, HOLY_CHARGE, CURSE_CHARGE,
            EARTH_AURA, WATER_AURA, AIR_AURA, FIRE_AURA, VOID_AURA, FROST_AURA, HOLY_AURA, CURSE_AURA,
            RAIN_CLOUD, LAVA_BLOCK, ICICLE, SHARP_BLINK, CRYSTAL_CRUSHING, TOXIC_RAIN, MOR_SWARM, WITHERING, WHAT, NECROTIC_RAY, LIGHT_RAY,
            INCINERATION, REPENTANCE, RENUNCIATION,
            EMBER_RAY, WISDOM;

    public static void init() {
        EARTH_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsReborn.VITA_MONOGRAM, WizardsReborn.VITA_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.MIRACULUM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.DEGRADATIO_MONOGRAM, 3)
        );
        WATER_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsReborn.STATERA_MONOGRAM, WizardsReborn.DEGRADATIO_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.STATERA_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.DEGRADATIO_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.KARA_MONOGRAM, 4)
        );
        AIR_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsReborn.FAMES_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SICCITAS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.FAMES_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 5)
        );
        FIRE_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsReborn.ECLIPSIS_MONOGRAM,  WizardsReborn.LUNAM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 4)
        );
        VOID_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.LUX_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.PRAEDICTIONEM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.LUX_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 5)
        );
        FROST_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsReborn.LUNAM_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.FAMES_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 6)
        );
        HOLY_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsReborn.MORS_MONOGRAM,  WizardsReborn.ECLIPSIS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.TENEBRIS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 3)
        );
        CURSE_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsReborn.RENAISSANCE_MONOGRAM,  WizardsReborn.SOLSTITIUM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.BELLUM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 4)
        );

        EARTH_RAY = new Research(10, new ResearchMapEntry(
                WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.PRAEDICTIONEM_MONOGRAM,  WizardsReborn.PRAEDICTIONEM_MONOGRAM,  WizardsReborn.PRAEDICTIONEM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.PRAEDICTIONEM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.MIRACULUM_MONOGRAM, 8)
        );
        WATER_RAY = new Research(10, new ResearchMapEntry(
                WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.MIRACULUM_MONOGRAM,  WizardsReborn.MIRACULUM_MONOGRAM,  WizardsReborn.MIRACULUM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.MIRACULUM_MONOGRAM, 8)
        );
        AIR_RAY = new Research(10, new ResearchMapEntry(
                WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.BELLUM_MONOGRAM,  WizardsReborn.BELLUM_MONOGRAM,  WizardsReborn.SOLSTITIUM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.BELLUM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 7),
                new ResearchMonogramEntry(WizardsReborn.MIRACULUM_MONOGRAM, 4)
        );
        FIRE_RAY = new Research(10, new ResearchMapEntry(
                WizardsReborn.PRAEDICTIONEM_MONOGRAM,  WizardsReborn.ECLIPSIS_MONOGRAM,  WizardsReborn.VITA_MONOGRAM,  WizardsReborn.VITA_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.PRAEDICTIONEM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.EVOLUTIONIS_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 6)
        );
        VOID_RAY = new Research(10, new ResearchMapEntry(
                WizardsReborn.RENAISSANCE_MONOGRAM,  WizardsReborn.LUNAM_MONOGRAM,  WizardsReborn.MORS_MONOGRAM,  WizardsReborn.LUNAM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 7)
        );
        FROST_RAY = new Research(10, new ResearchMapEntry(
                WizardsReborn.LUNAM_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.UNIVERSUM_MONOGRAM, 7)
        );
        HOLY_RAY = new Research(10, new ResearchMapEntry(
                WizardsReborn.SICCITAS_MONOGRAM,  WizardsReborn.SICCITAS_MONOGRAM,  WizardsReborn.MORS_MONOGRAM,  WizardsReborn.MORS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.SICCITAS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 7)
        );
        CURSE_RAY = new Research(10, new ResearchMapEntry(
                WizardsReborn.TENEBRIS_MONOGRAM,  WizardsReborn.VITA_MONOGRAM,  WizardsReborn.VITA_MONOGRAM,  WizardsReborn.ECLIPSIS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.LUX_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.TENEBRIS_MONOGRAM, 3)
        );

        HEART_OF_NATURE = new Research(12, new ResearchMapEntry(
                WizardsReborn.UNIVERSUM_MONOGRAM,  WizardsReborn.MORS_MONOGRAM,  WizardsReborn.MORS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.BELLUM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.UNIVERSUM_MONOGRAM, 2),
                new ResearchMonogramEntry(WizardsReborn.STATERA_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 2)
        );
        WATER_BREATHING = new Research(12, new ResearchMapEntry(
                WizardsReborn.VITA_MONOGRAM,  WizardsReborn.LUNAM_MONOGRAM,  WizardsReborn.LUNAM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.STATERA_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 2)
        );
        AIR_FLOW = new Research(12, new ResearchMapEntry(
                WizardsReborn.MIRACULUM_MONOGRAM,  WizardsReborn.VITA_MONOGRAM,  WizardsReborn.VITA_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 2),
                new ResearchMonogramEntry(WizardsReborn.MIRACULUM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 2)
        );
        FIRE_SHIELD = new Research(12, new ResearchMapEntry(
                WizardsReborn.TEMPUS_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.RENAISSANCE_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.FAMES_MONOGRAM, 2),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 2),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 5)
        );
        BLINK = new Research(12, new ResearchMapEntry(
                WizardsReborn.EVOLUTIONIS_MONOGRAM,  WizardsReborn.STATERA_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.EVOLUTIONIS_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.STATERA_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 2),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 4)
        );
        SNOWFLAKE = new Research(12, new ResearchMapEntry(
                WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.STATERA_MONOGRAM,  WizardsReborn.STATERA_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.UNIVERSUM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.PRAEDICTIONEM_MONOGRAM, 2),
                new ResearchMonogramEntry(WizardsReborn.STATERA_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 4)
        );
        HOLY_CROSS = new Research(12, new ResearchMapEntry(
                WizardsReborn.PRAEDICTIONEM_MONOGRAM,  WizardsReborn.MIRACULUM_MONOGRAM,  WizardsReborn.MIRACULUM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.MIRACULUM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.PRAEDICTIONEM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 3)
        );
        CURSE_CROSS = new Research(12, new ResearchMapEntry(
                WizardsReborn.VITA_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.VITA_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.TENEBRIS_MONOGRAM, 2)
        );
        POISON = new Research(12, new ResearchMapEntry(
                WizardsReborn.MIRACULUM_MONOGRAM,  WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.MIRACULUM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.PRAEDICTIONEM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.LUX_MONOGRAM, 2)
        );

        MAGIC_SPROUT = new Research(12, new ResearchMapEntry(
                WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.VITA_MONOGRAM,  WizardsReborn.LUX_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.LUX_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 3)
        );
        DIRT_BLOCK = new Research(8, new ResearchMapEntry(
                WizardsReborn.RENAISSANCE_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.ECLIPSIS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.TENEBRIS_MONOGRAM, 3)
        );
        WATER_BLOCK = new Research(8, new ResearchMapEntry(
                WizardsReborn.SICCITAS_MONOGRAM,  WizardsReborn.SICCITAS_MONOGRAM,  WizardsReborn.KARA_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.STATERA_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.KARA_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.SICCITAS_MONOGRAM, 4)
        );
        AIR_IMPACT = new Research(12, new ResearchMapEntry(
                WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.RENAISSANCE_MONOGRAM,  WizardsReborn.MORS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 2),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.BELLUM_MONOGRAM, 5)
        );
        ICE_BLOCK = new Research(8, new ResearchMapEntry(
                WizardsReborn.UNIVERSUM_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.UNIVERSUM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.FAMES_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.SICCITAS_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 6)
        );

        EARTH_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsReborn.LUNAM_MONOGRAM,  WizardsReborn.ECLIPSIS_MONOGRAM,  WizardsReborn.TENEBRIS_MONOGRAM,  WizardsReborn.TENEBRIS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.TENEBRIS_MONOGRAM, 8),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 9),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 3)
        );
        WATER_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsReborn.MORS_MONOGRAM,  WizardsReborn.MORS_MONOGRAM,  WizardsReborn.MORS_MONOGRAM,  WizardsReborn.ECLIPSIS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.FAMES_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 7)
        );
        AIR_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsReborn.STATERA_MONOGRAM,  WizardsReborn.EVOLUTIONIS_MONOGRAM,  WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.VITA_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.PRAEDICTIONEM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.EVOLUTIONIS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.STATERA_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 7)
        );
        FIRE_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsReborn.TEMPUS_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM,  WizardsReborn.MORS_MONOGRAM,  WizardsReborn.RENAISSANCE_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.FAMES_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 6)
        );
        VOID_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.ECLIPSIS_MONOGRAM,  WizardsReborn.TENEBRIS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.TENEBRIS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 4)
        );
        FROST_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.SOLSTITIUM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.LUX_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 6)
        );
        HOLY_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsReborn.BELLUM_MONOGRAM,  WizardsReborn.MORS_MONOGRAM,  WizardsReborn.SICCITAS_MONOGRAM,  WizardsReborn.MORS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.BELLUM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.SICCITAS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 8),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 5)
        );
        CURSE_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsReborn.VITA_MONOGRAM,  WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.BELLUM_MONOGRAM,  WizardsReborn.SOLSTITIUM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.BELLUM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.LUX_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 7)
        );

        EARTH_AURA = new Research(15, new ResearchMapEntry(
                WizardsReborn.VITA_MONOGRAM,  WizardsReborn.TENEBRIS_MONOGRAM,  WizardsReborn.TENEBRIS_MONOGRAM,  WizardsReborn.DEGRADATIO_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.DEGRADATIO_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 8),
                new ResearchMonogramEntry(WizardsReborn.TENEBRIS_MONOGRAM, 7)
        );
        WATER_AURA = new Research(15, new ResearchMapEntry(
                WizardsReborn.LUNAM_MONOGRAM,  WizardsReborn.RENAISSANCE_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 8),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 5)
        );
        AIR_AURA = new Research(15, new ResearchMapEntry(
                WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.MIRACULUM_MONOGRAM,  WizardsReborn.VITA_MONOGRAM,  WizardsReborn.MIRACULUM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.MIRACULUM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 6)
        );
        FIRE_AURA = new Research(15, new ResearchMapEntry(
                WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.TENEBRIS_MONOGRAM, 2)
        );
        VOID_AURA = new Research(15, new ResearchMapEntry(
                WizardsReborn.LUNAM_MONOGRAM,  WizardsReborn.LUNAM_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.MIRACULUM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.MIRACULUM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 10)
        );
        FROST_AURA = new Research(15, new ResearchMapEntry(
                WizardsReborn.VITA_MONOGRAM,  WizardsReborn.VITA_MONOGRAM,  WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.MORS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.LUX_MONOGRAM, 2)
        );
        HOLY_AURA = new Research(15, new ResearchMapEntry(
                WizardsReborn.ECLIPSIS_MONOGRAM,  WizardsReborn.MORS_MONOGRAM,  WizardsReborn.LUNAM_MONOGRAM,  WizardsReborn.LUNAM_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.DEGRADATIO_MONOGRAM, 2),
                new ResearchMonogramEntry(WizardsReborn.MORS_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 8),
                new ResearchMonogramEntry(WizardsReborn.LUNAM_MONOGRAM, 6)
        );
        CURSE_AURA = new Research(15, new ResearchMapEntry(
                WizardsReborn.TEMPUS_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM,  WizardsReborn.RENAISSANCE_MONOGRAM,  WizardsReborn.TEMPUS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.FAMES_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.RENAISSANCE_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.TEMPUS_MONOGRAM, 8)
        );

        WizardsReborn.EARTH_PROJECTILE_SPELL.setResearch(EARTH_PROJECTILE);
        WizardsReborn.WATER_PROJECTILE_SPELL.setResearch(WATER_PROJECTILE);
        WizardsReborn.AIR_PROJECTILE_SPELL.setResearch(AIR_PROJECTILE);
        WizardsReborn.FIRE_PROJECTILE_SPELL.setResearch(FIRE_PROJECTILE);
        WizardsReborn.VOID_PROJECTILE_SPELL.setResearch(VOID_PROJECTILE);
        WizardsReborn.FROST_PROJECTILE_SPELL.setResearch(FROST_PROJECTILE);
        WizardsReborn.HOLY_PROJECTILE_SPELL.setResearch(HOLY_PROJECTILE);
        WizardsReborn.CURSE_PROJECTILE_SPELL.setResearch(CURSE_PROJECTILE);

        WizardsReborn.EARTH_RAY_SPELL.setResearch(EARTH_RAY);
        WizardsReborn.WATER_RAY_SPELL.setResearch(WATER_RAY);
        WizardsReborn.AIR_RAY_SPELL.setResearch(AIR_RAY);
        WizardsReborn.FIRE_RAY_SPELL.setResearch(FIRE_RAY);
        WizardsReborn.VOID_RAY_SPELL.setResearch(VOID_RAY);
        WizardsReborn.FROST_RAY_SPELL.setResearch(FROST_RAY);
        WizardsReborn.HOLY_RAY_SPELL.setResearch(HOLY_RAY);
        WizardsReborn.CURSE_RAY_SPELL.setResearch(CURSE_RAY);

        WizardsReborn.HEART_OF_NATURE_SPELL.setResearch(HEART_OF_NATURE);
        WizardsReborn.WATER_BREATHING_SPELL.setResearch(WATER_BREATHING);
        WizardsReborn.AIR_FLOW_SPELL.setResearch(AIR_FLOW);
        WizardsReborn.FIRE_SHIELD_SPELL.setResearch(FIRE_SHIELD);
        WizardsReborn.BLINK_SPELL.setResearch(BLINK);
        WizardsReborn.SNOWFLAKE_SPELL.setResearch(SNOWFLAKE);
        WizardsReborn.HOLY_CROSS_SPELL.setResearch(HOLY_CROSS);
        WizardsReborn.CURSE_CROSS_SPELL.setResearch(CURSE_CROSS);
        WizardsReborn.POISON_SPELL.setResearch(POISON);

        WizardsReborn.MAGIC_SPROUT_SPELL.setResearch(MAGIC_SPROUT);
        WizardsReborn.DIRT_BLOCK_SPELL.setResearch(DIRT_BLOCK);
        WizardsReborn.WATER_BLOCK_SPELL.setResearch(WATER_BLOCK);
        WizardsReborn.AIR_IMPACT_SPELL.setResearch(AIR_IMPACT);
        WizardsReborn.ICE_BLOCK_SPELL.setResearch(ICE_BLOCK);

        WizardsReborn.EARTH_CHARGE_SPELL.setResearch(EARTH_CHARGE);
        WizardsReborn.WATER_CHARGE_SPELL.setResearch(WATER_CHARGE);
        WizardsReborn.AIR_CHARGE_SPELL.setResearch(AIR_CHARGE);
        WizardsReborn.FIRE_CHARGE_SPELL.setResearch(FIRE_CHARGE);
        WizardsReborn.VOID_CHARGE_SPELL.setResearch(VOID_CHARGE);
        WizardsReborn.FROST_CHARGE_SPELL.setResearch(FROST_CHARGE);
        WizardsReborn.HOLY_CHARGE_SPELL.setResearch(HOLY_CHARGE);
        WizardsReborn.CURSE_CHARGE_SPELL.setResearch(CURSE_CHARGE);

        WizardsReborn.EARTH_AURA_SPELL.setResearch(EARTH_AURA);
        WizardsReborn.WATER_AURA_SPELL.setResearch(WATER_AURA);
        WizardsReborn.AIR_AURA_SPELL.setResearch(AIR_AURA);
        WizardsReborn.FIRE_AURA_SPELL.setResearch(FIRE_AURA);
        WizardsReborn.VOID_AURA_SPELL.setResearch(VOID_AURA);
        WizardsReborn.FROST_AURA_SPELL.setResearch(FROST_AURA);
        WizardsReborn.HOLY_AURA_SPELL.setResearch(HOLY_AURA);
        WizardsReborn.CURSE_AURA_SPELL.setResearch(CURSE_AURA);
    }
}
