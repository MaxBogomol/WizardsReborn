package mod.maxbogomol.wizards_reborn.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static ForgeConfigSpec.ConfigValue<Integer> ARCANUM_MAX_Y, ARCANUM_VEIN_SIZE, ARCANUM_VEIN_COUNT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ARCANUM_ENABLED;

    public Config(ForgeConfigSpec.Builder builder) {
        
        builder.comment("World generation settings").push("world");
        ARCANUM_ENABLED = builder.comment("Whether arcanum ore is enabled. Set to false to disable spawning.")
                .define("arcanumEnabled", true);
        ARCANUM_MAX_Y = builder.comment("Maximum Y value for arcanum ore veins")
                .defineInRange("arcanumOreMaxY", 51, 1, 255);
        ARCANUM_VEIN_SIZE = builder.comment("Maximum number of blocks per arcanum ore vein")
                .defineInRange("arcanumOreVeinSize", 8, 1, 255);
        ARCANUM_VEIN_COUNT = builder.comment("Number of arcanum ore veins per chunk")
                .defineInRange("arcanumOreVeinCount", 8, 0, 255);
    }

    public static final Config INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
