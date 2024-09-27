package mod.maxbogomol.wizards_reborn.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WizardsRebornServerConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            ARCANEMICON_OFFERING;
    public static ForgeConfigSpec.ConfigValue<Integer>
            ARCANEMICON_OFFERING_TICKS;

    public WizardsRebornServerConfig(ForgeConfigSpec.Builder builder) {
        ARCANEMICON_OFFERING = builder.comment("Enable Arcanemicon Offering")
                .define("arcanemiconOffering", true);
        ARCANEMICON_OFFERING_TICKS = builder.comment("Ticks with game for Arcanemicon Offering.")
                .defineInRange("arcanemiconOfferingTicks", 144000, 0, Integer.MAX_VALUE);
    }

    public static final WizardsRebornServerConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<WizardsRebornServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(WizardsRebornServerConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
