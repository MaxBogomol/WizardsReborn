package mod.maxbogomol.wizards_reborn.client.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean> BETTER_LAYERING, LARGE_ITEM_MODEL, SPELLS_ANIMATIONS, SPELLS_ITEM_ANIMATIONS, SPELLS_FIRST_PERSON_ITEM_ANIMATIONS, PANORAMA_TEST;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        PANORAMA_TEST = builder.comment("123")
                .define("panoramaTest", false);

        builder.comment("Graphics settings").push("graphics");
        BETTER_LAYERING = builder.comment("Enable better particle/effect layering.")
                .comment("Fixes particles and effects rendering behind clouds and weather.")
                .comment("NOTE: Does NOT work with fabulous graphics mode.")
                .define("betterLayering", true);

        builder.comment("Animations settings").push("animations");
        LARGE_ITEM_MODEL = builder.comment("Enable large item models.")
                .define("largeItemModel", true);
        SPELLS_ANIMATIONS = builder.comment("Enable spells animations player.")
                .define("spellsAnimation", true);
        SPELLS_ITEM_ANIMATIONS = builder.comment("Enable spells item animations.")
                .define("spellsItemAnimation", true);
        SPELLS_FIRST_PERSON_ITEM_ANIMATIONS = builder.comment("Enable spells first person item animations.")
                .define("spellsFirstPersonItemAnimation", true);
        builder.pop();

        builder.pop();
    }

    public static final ClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
