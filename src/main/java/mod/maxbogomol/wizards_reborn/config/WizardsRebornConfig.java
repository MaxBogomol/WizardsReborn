package mod.maxbogomol.wizards_reborn.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;

public class WizardsRebornConfig {
    public static ForgeConfigSpec.ConfigValue<Integer>
            STANDARD_WISSEN_COLOR_R, STANDARD_WISSEN_COLOR_G, STANDARD_WISSEN_COLOR_B;

    public WizardsRebornConfig(ForgeConfigSpec.Builder builder) {
        STANDARD_WISSEN_COLOR_R = builder.comment("Standard wissen color RED.")
                .defineInRange("standardWissenColorR", 119, 0, 255);
        STANDARD_WISSEN_COLOR_G = builder.comment("Standard wissen color GREEN.")
                .defineInRange("standardWissenColorG", 164, 0, 255);
        STANDARD_WISSEN_COLOR_B = builder.comment("Standard wissen color BLUE.")
                .defineInRange("standardWissenColorB", 208, 0, 255);
    }

    public static final WizardsRebornConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<WizardsRebornConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(WizardsRebornConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static float wissenColorR() {
        return STANDARD_WISSEN_COLOR_R.get() / 255f;
    }

    public static float wissenColorG() {
        return STANDARD_WISSEN_COLOR_G.get() / 255f;
    }

    public static float wissenColorB() {
        return STANDARD_WISSEN_COLOR_B.get() / 255f;
    }

    public static Color wissenColor() {
        return new Color(wissenColorR(), wissenColorG(), wissenColorB());
    }
}
