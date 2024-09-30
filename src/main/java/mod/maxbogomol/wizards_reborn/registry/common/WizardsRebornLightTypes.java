package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.light.LightType;
import mod.maxbogomol.wizards_reborn.api.light.LightTypes;
import mod.maxbogomol.wizards_reborn.common.lighttype.*;

public class WizardsRebornLightTypes {

    public static final LightType EARTH = new EarthLightType(WizardsReborn.MOD_ID+":earth");
    public static final LightType WATER = new WaterLightType(WizardsReborn.MOD_ID+":water");
    public static final LightType AIR = new AirLightType(WizardsReborn.MOD_ID+":air");
    public static final LightType FIRE = new FireLightType(WizardsReborn.MOD_ID+":fire");
    public static final LightType VOID = new VoidLightType(WizardsReborn.MOD_ID+":void");
    public static final LightType CRYSTAL_GROWTH_ACCELERATION = new CrystalGrowthAccelerationLightType(WizardsReborn.MOD_ID+":crystal_growth_acceleration");

    public static void register() {
        LightTypes.register(EARTH);
        LightTypes.register(WATER);
        LightTypes.register(AIR);
        LightTypes.register(FIRE);
        LightTypes.register(VOID);
        LightTypes.register(CRYSTAL_GROWTH_ACCELERATION);
    }
}
