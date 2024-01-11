package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Research;
import mod.maxbogomol.wizards_reborn.api.knowledge.ResearchMapEntry;
import mod.maxbogomol.wizards_reborn.api.knowledge.ResearchMonogramEntry;

public class Researches {
    public static Research
            EARTH_PROJECTILE, WATER_PROJECTILE, AIR_PROJECTILE, FIRE_PROJECTILE, VOID_PROJECTILE, FROST_PROJECTILE, HOLY_PROJECTILE, CURSE_PROJECTILE,
            EARTH_RAY, WATER_RAY, AIR_RAY, FIRE_RAY, VOID_RAY, FROST_RAY, HOLY_RAY, CURSE_RAY,
            EARTH_CHARGE, WATER_CHARGE, AIR_CHARGE, FIRE_CHARGE, VOID_CHARGE, FROST_CHARGE, HOLY_CHARGE, CURSE_CHARGE,
            HEART_OF_NATURE, WATER_BREATHING, AIR_FLOW, FIRE_SHIELD, MAGIC_SPROUT;

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
                new ResearchMonogramEntry(WizardsReborn.DARK_MONOGRAM, 4),
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
                WizardsReborn.DARK_MONOGRAM,  WizardsReborn.VITA_MONOGRAM,  WizardsReborn.VITA_MONOGRAM,  WizardsReborn.ECLIPSIS_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.LUX_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 5),
                new ResearchMonogramEntry(WizardsReborn.DARK_MONOGRAM, 3)
        );

        EARTH_CHARGE = new Research(12, new ResearchMapEntry(
                WizardsReborn.LUNAM_MONOGRAM,  WizardsReborn.ECLIPSIS_MONOGRAM,  WizardsReborn.DARK_MONOGRAM,  WizardsReborn.DARK_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.DARK_MONOGRAM, 8),
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
                WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.SOLEM_MONOGRAM,  WizardsReborn.ECLIPSIS_MONOGRAM,  WizardsReborn.DARK_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.SOLEM_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.DARK_MONOGRAM, 4),
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
        MAGIC_SPROUT = new Research(12, new ResearchMapEntry(
                WizardsReborn.SOLSTITIUM_MONOGRAM,  WizardsReborn.VITA_MONOGRAM,  WizardsReborn.LUX_MONOGRAM),
                new ResearchMonogramEntry(WizardsReborn.ECLIPSIS_MONOGRAM, 3),
                new ResearchMonogramEntry(WizardsReborn.LUX_MONOGRAM, 4),
                new ResearchMonogramEntry(WizardsReborn.VITA_MONOGRAM, 6),
                new ResearchMonogramEntry(WizardsReborn.SOLSTITIUM_MONOGRAM, 3)
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

        WizardsReborn.EARTH_CHARGE_SPELL.setResearch(EARTH_CHARGE);
        WizardsReborn.WATER_CHARGE_SPELL.setResearch(WATER_CHARGE);
        WizardsReborn.AIR_CHARGE_SPELL.setResearch(AIR_CHARGE);
        WizardsReborn.FIRE_CHARGE_SPELL.setResearch(FIRE_CHARGE);
        WizardsReborn.VOID_CHARGE_SPELL.setResearch(VOID_CHARGE);
        WizardsReborn.FROST_CHARGE_SPELL.setResearch(FROST_CHARGE);
        WizardsReborn.HOLY_CHARGE_SPELL.setResearch(HOLY_CHARGE);
        WizardsReborn.CURSE_CHARGE_SPELL.setResearch(CURSE_CHARGE);

        WizardsReborn.HEART_OF_NATURE_SPELL.setResearch(HEART_OF_NATURE);
        WizardsReborn.WATER_BREATHING_SPELL.setResearch(WATER_BREATHING);
        WizardsReborn.AIR_FLOW_SPELL.setResearch(AIR_FLOW);
        WizardsReborn.FIRE_SHIELD_SPELL.setResearch(FIRE_SHIELD);
        WizardsReborn.MAGIC_SPROUT_SPELL.setResearch(MAGIC_SPROUT);
    }
}
