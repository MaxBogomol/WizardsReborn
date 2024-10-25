package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalStat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalHandler;
import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;
import mod.maxbogomol.wizards_reborn.common.crystal.*;

import java.awt.*;

public class WizardsRebornCrystals {
    public static Color earthCrystalColor = new Color(138, 201, 123);
    public static Color waterCrystalColor = new Color(152, 180, 223);
    public static Color airCrystalColor = new Color(230, 173, 134);
    public static Color fireCrystalColor = new Color(225, 127, 103);
    public static Color voidCrystalColor = new Color(175, 140, 194);

    public static CrystalStat FOCUS = new CrystalStat(WizardsReborn.MOD_ID+":focus", 3);
    public static CrystalStat BALANCE = new CrystalStat(WizardsReborn.MOD_ID+":balance", 3);
    public static CrystalStat ABSORPTION = new CrystalStat(WizardsReborn.MOD_ID+":absorption", 3);
    public static CrystalStat RESONANCE = new CrystalStat(WizardsReborn.MOD_ID+":resonance", 3);

    public static final PolishingType CRYSTAL = new CrystalPolishingType(WizardsReborn.MOD_ID+":crystal");
    public static final PolishingType FACETED = new FacetedPolishingType(WizardsReborn.MOD_ID+":faceted");
    public static final PolishingType ADVANCED = new AdvancedPolishingType(WizardsReborn.MOD_ID+":advanced");
    public static final PolishingType MASTERFUL = new MasterfulPolishingType(WizardsReborn.MOD_ID+":masterful");
    public static final PolishingType PURE = new PurePolishingType(WizardsReborn.MOD_ID+":pure");

    public static final CrystalType EARTH = new EarthCrystalType(WizardsReborn.MOD_ID+":earth");
    public static final CrystalType WATER = new WaterCrystalType(WizardsReborn.MOD_ID+":water");
    public static final CrystalType AIR = new AirCrystalType(WizardsReborn.MOD_ID+":air");
    public static final CrystalType FIRE = new FireCrystalType(WizardsReborn.MOD_ID+":fire");
    public static final CrystalType VOID = new VoidCrystalType(WizardsReborn.MOD_ID+":void");

    public static void register() {
        CrystalHandler.registerPolishing(CRYSTAL);
        CrystalHandler.registerPolishing(FACETED);
        CrystalHandler.registerPolishing(ADVANCED);
        CrystalHandler.registerPolishing(MASTERFUL);
        CrystalHandler.registerPolishing(PURE);

        CrystalHandler.registerType(EARTH);
        CrystalHandler.registerType(WATER);
        CrystalHandler.registerType(AIR);
        CrystalHandler.registerType(FIRE);
        CrystalHandler.registerType(VOID);
    }
}
