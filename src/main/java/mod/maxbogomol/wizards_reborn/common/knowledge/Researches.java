package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Research;
import mod.maxbogomol.wizards_reborn.api.knowledge.ResearchMapEntry;
import mod.maxbogomol.wizards_reborn.api.knowledge.ResearchMonogramEntry;

public class Researches {
    public static Research
            EARTH_PROJECTILE, WATER_PROJECTILE, AIR_PROJECTILE, FIRE_PROJECTILE, VOID_PROJECTILE, FROST_PROJECTILE, HOLY_PROJECTILE,
            EARTH_RAY, WATER_RAY, AIR_RAY, FIRE_RAY, VOID_RAY, FROST_RAY, HOLY_RAY;

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

        WizardsReborn.EARTH_PROJECTILE_SPELL.setResearch(EARTH_PROJECTILE);
        WizardsReborn.WATER_PROJECTILE_SPELL.setResearch(WATER_PROJECTILE);
        WizardsReborn.AIR_PROJECTILE_SPELL.setResearch(AIR_PROJECTILE);
        WizardsReborn.FIRE_PROJECTILE_SPELL.setResearch(FIRE_PROJECTILE);
        WizardsReborn.VOID_PROJECTILE_SPELL.setResearch(VOID_PROJECTILE);
        WizardsReborn.FROST_PROJECTILE_SPELL.setResearch(FROST_PROJECTILE);
        WizardsReborn.HOLY_PROJECTILE_SPELL.setResearch(HOLY_PROJECTILE);

        WizardsReborn.EARTH_RAY_SPELL.setResearch(EARTH_RAY);
        WizardsReborn.WATER_RAY_SPELL.setResearch(WATER_RAY);
        WizardsReborn.AIR_RAY_SPELL.setResearch(AIR_RAY);
        WizardsReborn.FIRE_RAY_SPELL.setResearch(FIRE_RAY);
        WizardsReborn.VOID_RAY_SPELL.setResearch(VOID_RAY);
        WizardsReborn.FROST_RAY_SPELL.setResearch(FROST_RAY);
        WizardsReborn.HOLY_RAY_SPELL.setResearch(HOLY_RAY);
    }
}
