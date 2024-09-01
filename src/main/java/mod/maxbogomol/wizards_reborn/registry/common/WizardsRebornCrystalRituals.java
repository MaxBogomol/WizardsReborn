package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRituals;
import mod.maxbogomol.wizards_reborn.common.crystalritual.ArtificialFertilityCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.crystalritual.CrystalGrowthAccelerationCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.crystalritual.CrystalInfusionCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.crystalritual.RitualBreedingCrystalRitual;

public class WizardsRebornCrystalRituals {
    public static CrystalRitual EMPTY = new CrystalRitual(WizardsReborn.MOD_ID+":empty");
    public static CrystalRitual ARTIFICIAL_FERTILITY = new ArtificialFertilityCrystalRitual(WizardsReborn.MOD_ID+":artificial_fertility");
    public static CrystalRitual RITUAL_BREEDING = new RitualBreedingCrystalRitual(WizardsReborn.MOD_ID+":ritual_breeding");
    public static CrystalRitual CRYSTAL_GROWTH_ACCELERATION = new CrystalGrowthAccelerationCrystalRitual(WizardsReborn.MOD_ID+":crystal_growth_acceleration");
    public static CrystalRitual CRYSTAL_INFUSION = new CrystalInfusionCrystalRitual(WizardsReborn.MOD_ID+":crystal_infusion");
    //public static CrystalRitual STONE_CALENDAR = new CrystalRitual(WizardsReborn.MOD_ID+":stone_calendar");

    public static void register() {
        CrystalRituals.register(EMPTY);
        CrystalRituals.register(ARTIFICIAL_FERTILITY);
        CrystalRituals.register(RITUAL_BREEDING);
        CrystalRituals.register(CRYSTAL_GROWTH_ACCELERATION);
        CrystalRituals.register(CRYSTAL_INFUSION);
        //CrystalRituals.register(STONE_CALENDAR);
    }
}
