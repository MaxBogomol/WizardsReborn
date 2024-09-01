package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.api.knowledge.Research;
import mod.maxbogomol.wizards_reborn.api.knowledge.ResearchMapEntry;
import mod.maxbogomol.wizards_reborn.api.knowledge.ResearchMonogramEntry;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMonograms;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

public class Researches {
    public static Research
            EARTH_PROJECTILE, WATER_PROJECTILE, AIR_PROJECTILE, FIRE_PROJECTILE, VOID_PROJECTILE, FROST_PROJECTILE, HOLY_PROJECTILE, CURSE_PROJECTILE,
            EARTH_RAY, WATER_RAY, AIR_RAY, FIRE_RAY, VOID_RAY, FROST_RAY, HOLY_RAY, CURSE_RAY,
            HEART_OF_NATURE, WATER_BREATHING, AIR_FLOW, FIRE_SHIELD, BLINK, SNOWFLAKE, HOLY_CROSS, CURSE_CROSS, POISON,
            MAGIC_SPROUT, DIRT_BLOCK, WATER_BLOCK, AIR_IMPACT, ICE_BLOCK,
            EARTH_CHARGE, WATER_CHARGE, AIR_CHARGE, FIRE_CHARGE, VOID_CHARGE, FROST_CHARGE, HOLY_CHARGE, CURSE_CHARGE,
            EARTH_AURA, WATER_AURA, AIR_AURA, FIRE_AURA, VOID_AURA, FROST_AURA, HOLY_AURA, CURSE_AURA,
            RAIN_CLOUD, LAVA_BLOCK, ICICLE, SHARP_BLINK, CRYSTAL_CRUSHING, TOXIC_RAIN, MOR_SWARM, WITHERING, IRRITATION, NECROTIC_RAY, LIGHT_RAY,
            INCINERATION, REPENTANCE, RENUNCIATION,
            EMBER_RAY, WISDOM;

    public static void init() {
        EARTH_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsRebornMonograms.VITA, WizardsRebornMonograms.VITA),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.MIRACULUM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.DEGRADATIO, 3)
        );
        WATER_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsRebornMonograms.STATERA, WizardsRebornMonograms.DEGRADATIO),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.DEGRADATIO, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.KARA, 4)
        );
        AIR_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsRebornMonograms.FAMES,  WizardsRebornMonograms.TEMPUS),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 5)
        );
        FIRE_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsRebornMonograms.ECLIPSIS,  WizardsRebornMonograms.LUNAM),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 4)
        );
        VOID_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.LUX),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.PRAEDICTIONEM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUX, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 5)
        );
        FROST_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsRebornMonograms.LUNAM,  WizardsRebornMonograms.TEMPUS),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 6)
        );
        HOLY_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsRebornMonograms.MORS,  WizardsRebornMonograms.ECLIPSIS),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.TENEBRIS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 3)
        );
        CURSE_PROJECTILE = new Research(6, new ResearchMapEntry(
                WizardsRebornMonograms.RENAISSANCE,  WizardsRebornMonograms.SOLSTITIUM),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.BELLUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 4)
        );

        EARTH_RAY = new Research(10, new ResearchMapEntry(
                WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.PRAEDICTIONEM,  WizardsRebornMonograms.PRAEDICTIONEM,  WizardsRebornMonograms.PRAEDICTIONEM),
                new ResearchMonogramEntry(WizardsRebornMonograms.PRAEDICTIONEM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.MIRACULUM, 8)
        );
        WATER_RAY = new Research(10, new ResearchMapEntry(
                WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.MIRACULUM,  WizardsRebornMonograms.MIRACULUM,  WizardsRebornMonograms.MIRACULUM),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.MIRACULUM, 8)
        );
        AIR_RAY = new Research(10, new ResearchMapEntry(
                WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.BELLUM,  WizardsRebornMonograms.BELLUM,  WizardsRebornMonograms.SOLSTITIUM),
                new ResearchMonogramEntry(WizardsRebornMonograms.BELLUM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 7),
                new ResearchMonogramEntry(WizardsRebornMonograms.MIRACULUM, 4)
        );
        FIRE_RAY = new Research(10, new ResearchMapEntry(
                WizardsRebornMonograms.PRAEDICTIONEM,  WizardsRebornMonograms.ECLIPSIS,  WizardsRebornMonograms.VITA,  WizardsRebornMonograms.VITA),
                new ResearchMonogramEntry(WizardsRebornMonograms.PRAEDICTIONEM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.EVOLUTIONIS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 6)
        );
        VOID_RAY = new Research(10, new ResearchMapEntry(
                WizardsRebornMonograms.RENAISSANCE,  WizardsRebornMonograms.LUNAM,  WizardsRebornMonograms.MORS,  WizardsRebornMonograms.LUNAM),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 7)
        );
        FROST_RAY = new Research(10, new ResearchMapEntry(
                WizardsRebornMonograms.LUNAM,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.TEMPUS),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.UNIVERSUM, 7)
        );
        HOLY_RAY = new Research(10, new ResearchMapEntry(
                WizardsRebornMonograms.SICCITAS,  WizardsRebornMonograms.SICCITAS,  WizardsRebornMonograms.MORS,  WizardsRebornMonograms.MORS),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 7)
        );
        CURSE_RAY = new Research(10, new ResearchMapEntry(
                WizardsRebornMonograms.TENEBRIS,  WizardsRebornMonograms.VITA,  WizardsRebornMonograms.VITA,  WizardsRebornMonograms.ECLIPSIS),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUX, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.TENEBRIS, 3)
        );

        HEART_OF_NATURE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.UNIVERSUM,  WizardsRebornMonograms.MORS,  WizardsRebornMonograms.MORS),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.BELLUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.UNIVERSUM, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 2)
        );
        WATER_BREATHING = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.VITA,  WizardsRebornMonograms.LUNAM,  WizardsRebornMonograms.LUNAM),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 2)
        );
        AIR_FLOW = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.MIRACULUM,  WizardsRebornMonograms.VITA,  WizardsRebornMonograms.VITA),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.MIRACULUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 2)
        );
        FIRE_SHIELD = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.SOLEM,  WizardsRebornMonograms.RENAISSANCE),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 5)
        );
        BLINK = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.EVOLUTIONIS,  WizardsRebornMonograms.STATERA,  WizardsRebornMonograms.TEMPUS),
                new ResearchMonogramEntry(WizardsRebornMonograms.EVOLUTIONIS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 4)
        );
        SNOWFLAKE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.STATERA,  WizardsRebornMonograms.STATERA),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.UNIVERSUM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.PRAEDICTIONEM, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 4)
        );
        HOLY_CROSS = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.PRAEDICTIONEM,  WizardsRebornMonograms.MIRACULUM,  WizardsRebornMonograms.MIRACULUM),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.MIRACULUM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.PRAEDICTIONEM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 3)
        );
        CURSE_CROSS = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.VITA,  WizardsRebornMonograms.SOLEM,  WizardsRebornMonograms.VITA),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.TENEBRIS, 2)
        );
        POISON = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.MIRACULUM,  WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.SOLEM),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.MIRACULUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.PRAEDICTIONEM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUX, 2)
        );

        MAGIC_SPROUT = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.VITA,  WizardsRebornMonograms.LUX),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUX, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 3)
        );
        DIRT_BLOCK = new Research(8, new ResearchMapEntry(
                WizardsRebornMonograms.RENAISSANCE,  WizardsRebornMonograms.SOLEM,  WizardsRebornMonograms.ECLIPSIS),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.TENEBRIS, 3)
        );
        WATER_BLOCK = new Research(8, new ResearchMapEntry(
                WizardsRebornMonograms.SICCITAS,  WizardsRebornMonograms.SICCITAS,  WizardsRebornMonograms.KARA),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.KARA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 4)
        );
        AIR_IMPACT = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.RENAISSANCE,  WizardsRebornMonograms.MORS),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.BELLUM, 5)
        );
        ICE_BLOCK = new Research(8, new ResearchMapEntry(
                WizardsRebornMonograms.UNIVERSUM,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.TEMPUS),
                new ResearchMonogramEntry(WizardsRebornMonograms.UNIVERSUM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 6)
        );

        EARTH_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.LUNAM,  WizardsRebornMonograms.ECLIPSIS,  WizardsRebornMonograms.TENEBRIS,  WizardsRebornMonograms.TENEBRIS),
                new ResearchMonogramEntry(WizardsRebornMonograms.TENEBRIS, 8),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 9),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 3)
        );
        WATER_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.MORS,  WizardsRebornMonograms.MORS,  WizardsRebornMonograms.MORS,  WizardsRebornMonograms.ECLIPSIS),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 7)
        );
        AIR_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.STATERA,  WizardsRebornMonograms.EVOLUTIONIS,  WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.VITA),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.PRAEDICTIONEM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.EVOLUTIONIS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 7)
        );
        FIRE_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.MORS,  WizardsRebornMonograms.RENAISSANCE),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 6)
        );
        VOID_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.SOLEM,  WizardsRebornMonograms.SOLEM,  WizardsRebornMonograms.ECLIPSIS,  WizardsRebornMonograms.TENEBRIS),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.TENEBRIS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 4)
        );
        FROST_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.SOLEM,  WizardsRebornMonograms.SOLEM,  WizardsRebornMonograms.SOLSTITIUM),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUX, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 6)
        );
        HOLY_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.BELLUM,  WizardsRebornMonograms.MORS,  WizardsRebornMonograms.SICCITAS,  WizardsRebornMonograms.MORS),
                new ResearchMonogramEntry(WizardsRebornMonograms.BELLUM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 8),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 5)
        );
        CURSE_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.VITA,  WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.BELLUM,  WizardsRebornMonograms.SOLSTITIUM),
                new ResearchMonogramEntry(WizardsRebornMonograms.BELLUM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUX, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 7)
        );

        EARTH_AURA = new Research(15, new ResearchMapEntry(
                WizardsRebornMonograms.VITA,  WizardsRebornMonograms.TENEBRIS,  WizardsRebornMonograms.TENEBRIS,  WizardsRebornMonograms.DEGRADATIO),
                new ResearchMonogramEntry(WizardsRebornMonograms.DEGRADATIO, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 8),
                new ResearchMonogramEntry(WizardsRebornMonograms.TENEBRIS, 7)
        );
        WATER_AURA = new Research(15, new ResearchMapEntry(
                WizardsRebornMonograms.LUNAM,  WizardsRebornMonograms.RENAISSANCE,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.TEMPUS),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 8),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 5)
        );
        AIR_AURA = new Research(15, new ResearchMapEntry(
                WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.MIRACULUM,  WizardsRebornMonograms.VITA,  WizardsRebornMonograms.MIRACULUM),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.MIRACULUM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 6)
        );
        FIRE_AURA = new Research(15, new ResearchMapEntry(
                WizardsRebornMonograms.SOLEM,  WizardsRebornMonograms.SOLEM,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.SOLEM),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.TENEBRIS, 2)
        );
        VOID_AURA = new Research(15, new ResearchMapEntry(
                WizardsRebornMonograms.LUNAM,  WizardsRebornMonograms.LUNAM,  WizardsRebornMonograms.SOLEM,  WizardsRebornMonograms.MIRACULUM),
                new ResearchMonogramEntry(WizardsRebornMonograms.MIRACULUM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 10)
        );
        FROST_AURA = new Research(15, new ResearchMapEntry(
                WizardsRebornMonograms.VITA,  WizardsRebornMonograms.VITA,  WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.MORS),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUX, 2)
        );
        HOLY_AURA = new Research(15, new ResearchMapEntry(
                WizardsRebornMonograms.ECLIPSIS,  WizardsRebornMonograms.MORS,  WizardsRebornMonograms.LUNAM,  WizardsRebornMonograms.LUNAM),
                new ResearchMonogramEntry(WizardsRebornMonograms.DEGRADATIO, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 8),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 6)
        );
        CURSE_AURA = new Research(15, new ResearchMapEntry(
                WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.RENAISSANCE,  WizardsRebornMonograms.TEMPUS),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 8)
        );

        RAIN_CLOUD = new Research(13, new ResearchMapEntry(
                WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.STATERA,  WizardsRebornMonograms.RENAISSANCE,  WizardsRebornMonograms.MORS),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.UNIVERSUM, 4)
        );
        LAVA_BLOCK = new Research(9, new ResearchMapEntry(
                WizardsRebornMonograms.STATERA,  WizardsRebornMonograms.EVOLUTIONIS),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.EVOLUTIONIS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 3)
        );
        ICICLE = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.STATERA,  WizardsRebornMonograms.MORS,  WizardsRebornMonograms.MORS),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.EVOLUTIONIS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.UNIVERSUM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.BELLUM, 5)
        );
        SHARP_BLINK = new Research(11, new ResearchMapEntry(
                WizardsRebornMonograms.EVOLUTIONIS,  WizardsRebornMonograms.FAMES,  WizardsRebornMonograms.FAMES,  WizardsRebornMonograms.FAMES),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.EVOLUTIONIS, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 3)
        );
        CRYSTAL_CRUSHING = new Research(15, new ResearchMapEntry(
                WizardsRebornMonograms.SICCITAS),
                new ResearchMonogramEntry(WizardsRebornMonograms.PRAEDICTIONEM, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 7),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 3)
        );
        TOXIC_RAIN = new Research(14, new ResearchMapEntry(
                WizardsRebornMonograms.LUNAM,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.MORS),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 5)
        );
        MOR_SWARM = new Research(13, new ResearchMapEntry(
                WizardsRebornMonograms.EVOLUTIONIS,  WizardsRebornMonograms.STATERA),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.EVOLUTIONIS, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.UNIVERSUM, 3)
        );
        WITHERING = new Research(12, new ResearchMapEntry(
                WizardsRebornMonograms.MORS,  WizardsRebornMonograms.BELLUM,  WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.MORS),
                new ResearchMonogramEntry(WizardsRebornMonograms.EVOLUTIONIS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 7),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.BELLUM, 2)
        );
        IRRITATION = new Research(18, new ResearchMapEntry(
                WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.STATERA,  WizardsRebornMonograms.SICCITAS,  WizardsRebornMonograms.STATERA),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 7),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.KARA, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 2)
        );
        NECROTIC_RAY = new Research(18, new ResearchMapEntry(
                WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.UNIVERSUM,  WizardsRebornMonograms.UNIVERSUM),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 7),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.EVOLUTIONIS, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.UNIVERSUM, 6)
        );
        LIGHT_RAY = new Research(20, new ResearchMapEntry(),
                new ResearchMonogramEntry(WizardsRebornMonograms.PRAEDICTIONEM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.TENEBRIS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 7)
        );

        INCINERATION = new Research(18, new ResearchMapEntry(
                WizardsRebornMonograms.RENAISSANCE,  WizardsRebornMonograms.FAMES,  WizardsRebornMonograms.FAMES,  WizardsRebornMonograms.SOLEM),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 8),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.FAMES, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLEM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 2)
        );
        REPENTANCE = new Research(18, new ResearchMapEntry(
                WizardsRebornMonograms.ECLIPSIS,  WizardsRebornMonograms.SOLSTITIUM,  WizardsRebornMonograms.MORS,  WizardsRebornMonograms.BELLUM),
                new ResearchMonogramEntry(WizardsRebornMonograms.VITA, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 7),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.BELLUM, 4)
        );
        RENUNCIATION = new Research(18, new ResearchMapEntry(
                WizardsRebornMonograms.MORS,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.TEMPUS),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 7),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.UNIVERSUM, 2)
        );

        EMBER_RAY = new Research(16, new ResearchMapEntry(
                WizardsRebornMonograms.KARA,  WizardsRebornMonograms.TEMPUS,  WizardsRebornMonograms.SICCITAS,  WizardsRebornMonograms.MORS),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.STATERA, 5),
                new ResearchMonogramEntry(WizardsRebornMonograms.KARA, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 4)
        );
        WISDOM = new Research(25, new ResearchMapEntry(),
                new ResearchMonogramEntry(WizardsRebornMonograms.TEMPUS, 8),
                new ResearchMonogramEntry(WizardsRebornMonograms.LUNAM, 3),
                new ResearchMonogramEntry(WizardsRebornMonograms.SOLSTITIUM, 4),
                new ResearchMonogramEntry(WizardsRebornMonograms.MORS, 9),
                new ResearchMonogramEntry(WizardsRebornMonograms.ECLIPSIS, 2),
                new ResearchMonogramEntry(WizardsRebornMonograms.SICCITAS, 6),
                new ResearchMonogramEntry(WizardsRebornMonograms.RENAISSANCE, 5)
        );

        WizardsRebornSpells.EARTH_PROJECTILE.setResearch(EARTH_PROJECTILE);
        WizardsRebornSpells.WATER_PROJECTILE.setResearch(WATER_PROJECTILE);
        WizardsRebornSpells.AIR_PROJECTILE.setResearch(AIR_PROJECTILE);
        WizardsRebornSpells.FIRE_PROJECTILE.setResearch(FIRE_PROJECTILE);
        WizardsRebornSpells.VOID_PROJECTILE.setResearch(VOID_PROJECTILE);
        WizardsRebornSpells.FROST_PROJECTILE.setResearch(FROST_PROJECTILE);
        WizardsRebornSpells.HOLY_PROJECTILE.setResearch(HOLY_PROJECTILE);
        WizardsRebornSpells.CURSE_PROJECTILE.setResearch(CURSE_PROJECTILE);

        WizardsRebornSpells.EARTH_RAY.setResearch(EARTH_RAY);
        WizardsRebornSpells.WATER_RAY.setResearch(WATER_RAY);
        WizardsRebornSpells.AIR_RAY.setResearch(AIR_RAY);
        WizardsRebornSpells.FIRE_RAY.setResearch(FIRE_RAY);
        WizardsRebornSpells.VOID_RAY.setResearch(VOID_RAY);
        WizardsRebornSpells.FROST_RAY.setResearch(FROST_RAY);
        WizardsRebornSpells.HOLY_RAY.setResearch(HOLY_RAY);
        WizardsRebornSpells.CURSE_RAY.setResearch(CURSE_RAY);

        WizardsRebornSpells.HEART_OF_NATURE.setResearch(HEART_OF_NATURE);
        WizardsRebornSpells.WATER_BREATHING.setResearch(WATER_BREATHING);
        WizardsRebornSpells.AIR_FLOW.setResearch(AIR_FLOW);
        WizardsRebornSpells.FIRE_SHIELD.setResearch(FIRE_SHIELD);
        WizardsRebornSpells.BLINK.setResearch(BLINK);
        WizardsRebornSpells.SNOWFLAKE.setResearch(SNOWFLAKE);
        WizardsRebornSpells.HOLY_CROSS.setResearch(HOLY_CROSS);
        WizardsRebornSpells.CURSE_CROSS.setResearch(CURSE_CROSS);
        WizardsRebornSpells.POISON.setResearch(POISON);

        WizardsRebornSpells.MAGIC_SPROUT.setResearch(MAGIC_SPROUT);
        WizardsRebornSpells.DIRT_BLOCK.setResearch(DIRT_BLOCK);
        WizardsRebornSpells.WATER_BLOCK.setResearch(WATER_BLOCK);
        WizardsRebornSpells.AIR_IMPACT.setResearch(AIR_IMPACT);
        WizardsRebornSpells.ICE_BLOCK.setResearch(ICE_BLOCK);

        WizardsRebornSpells.EARTH_CHARGE.setResearch(EARTH_CHARGE);
        WizardsRebornSpells.WATER_CHARGE.setResearch(WATER_CHARGE);
        WizardsRebornSpells.AIR_CHARGE.setResearch(AIR_CHARGE);
        WizardsRebornSpells.FIRE_CHARGE.setResearch(FIRE_CHARGE);
        WizardsRebornSpells.VOID_CHARGE.setResearch(VOID_CHARGE);
        WizardsRebornSpells.FROST_CHARGE.setResearch(FROST_CHARGE);
        WizardsRebornSpells.HOLY_CHARGE.setResearch(HOLY_CHARGE);
        WizardsRebornSpells.CURSE_CHARGE.setResearch(CURSE_CHARGE);

        WizardsRebornSpells.EARTH_AURA.setResearch(EARTH_AURA);
        WizardsRebornSpells.WATER_AURA.setResearch(WATER_AURA);
        WizardsRebornSpells.AIR_AURA.setResearch(AIR_AURA);
        WizardsRebornSpells.FIRE_AURA.setResearch(FIRE_AURA);
        WizardsRebornSpells.VOID_AURA.setResearch(VOID_AURA);
        WizardsRebornSpells.FROST_AURA.setResearch(FROST_AURA);
        WizardsRebornSpells.HOLY_AURA.setResearch(HOLY_AURA);
        WizardsRebornSpells.CURSE_AURA.setResearch(CURSE_AURA);

        WizardsRebornSpells.RAIN_CLOUD.setResearch(RAIN_CLOUD);
        WizardsRebornSpells.LAVA_BLOCK.setResearch(LAVA_BLOCK);
        WizardsRebornSpells.ICICLE.setResearch(ICICLE);
        WizardsRebornSpells.SHARP_BLINK.setResearch(SHARP_BLINK);
        WizardsRebornSpells.CRYSTAL_CRUSHING.setResearch(CRYSTAL_CRUSHING);
        WizardsRebornSpells.TOXIC_RAIN.setResearch(TOXIC_RAIN);
        WizardsRebornSpells.MOR_SWARM.setResearch(MOR_SWARM);
        WizardsRebornSpells.WITHERING.setResearch(WITHERING);
        WizardsRebornSpells.IRRITATION.setResearch(IRRITATION);
        WizardsRebornSpells.NECROTIC_RAY.setResearch(NECROTIC_RAY);
        WizardsRebornSpells.LIGHT_RAY.setResearch(LIGHT_RAY);

        WizardsRebornSpells.INCINERATION.setResearch(INCINERATION);
        WizardsRebornSpells.REPENTANCE.setResearch(REPENTANCE);
        WizardsRebornSpells.RENUNCIATION.setResearch(RENUNCIATION);

        WizardsRebornSpells.EMBER_RAY.setResearch(EMBER_RAY);
        WizardsRebornSpells.WISDOM.setResearch(WISDOM);
    }
}
