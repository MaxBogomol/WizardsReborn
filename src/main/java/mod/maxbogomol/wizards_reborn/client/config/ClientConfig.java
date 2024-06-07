package mod.maxbogomol.wizards_reborn.client.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            BETTER_LAYERING,
            LARGE_ITEM_MODEL, SPELLS_ANIMATIONS, SPELLS_ITEM_ANIMATIONS, SPELLS_FIRST_PERSON_ITEM_ANIMATIONS,
            RESEARCH_HARDMODE, OLD_RESEARCH_MONOGRAM_OUTLINE, BRIGHT_RESEARCH_MONOGRAM_OUTLINE, RESEARCH_MONOGRAM_CONNECTS, MONOGRAM_GLOW, MONOGRAM_GLOW_COLOR, MONOGRAM_COLOR, MONOGRAM_RAYS,
            NUMERICAL_WISSEN, NUMERICAL_COOLDOWN, SHOW_LIGHT_NAME, NUMERICAL_EXPERIENCE, NUMERICAL_HEAT, NUMERICAL_FLUID, NUMERICAL_STEAM,
            ARCANE_WAND_OVERLAY_UP, ARCANE_WAND_OVERLAY_RIGHT, ARCANE_WAND_OVERLAY_SIDE_HUD, ARCANE_WAND_OVERLAY_SIDE_BAR, ARCANE_WAND_OVERLAY_HORIZONTAL_BAR, ARCANE_WAND_OVERLAY_SECOND_HUD_FREE, ARCANE_WAND_OVERLAY_BAR_FREE, ARCANE_WAND_OVERLAY_COOLDOWN_TEXT, ARCANE_WAND_OVERLAY_WISSEN_TEXT, ARCANE_WAND_OVERLAY_REVERSE_BAR, ARCANE_WAND_OVERLAY_SHOW_EMPTY,
            CUSTOM_PANORAMA, PANORAMA_BUTTON;
    public static ForgeConfigSpec.ConfigValue<Integer>
            WISSEN_RAYS_LIMIT,
            ARCANE_WAND_OVERLAY_X_OFFSET, ARCANE_WAND_OVERLAY_Y_OFFSET, ARCANE_WAND_OVERLAY_SECOND_X_OFFSET, ARCANE_WAND_OVERLAY_SECOND_Y_OFFSET, ARCANE_WAND_OVERLAY_BAR_X_OFFSET, ARCANE_WAND_OVERLAY_BAR_Y_OFFSET,
            PANORAMA_BUTTON_ROW, PANORAMA_BUTTON_ROW_X_OFFSET, PANORAMA_BUTTON_X_OFFSET, PANORAMA_BUTTON_Y_OFFSET;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Graphics").push("graphics");
        BETTER_LAYERING = builder.comment("Enable better particle/effect layering.")
                .comment("Fixes particles and effects rendering behind clouds and weather.")
                .comment("NOTE: Does NOT work with fabulous graphics mode.")
                .define("betterLayering", true);

        builder.comment("Animations").push("animations");
        LARGE_ITEM_MODEL = builder.comment("Enable large item models.")
                .define("largeItemModel", true);
        SPELLS_ANIMATIONS = builder.comment("Enable spells animations player.")
                .define("spellsAnimation", true);
        SPELLS_ITEM_ANIMATIONS = builder.comment("Enable spells item animations.")
                .define("spellsItemAnimation", true);
        SPELLS_FIRST_PERSON_ITEM_ANIMATIONS = builder.comment("Enable spells first person item animations.")
                .define("spellsFirstPersonItemAnimation", true);
        builder.pop();

        builder.comment("Particles").push("particles");
        WISSEN_RAYS_LIMIT = builder.comment("Limit wissen rays per tick.")
                .defineInRange("wissenRaysLimit", 200, 0, 1000);
        builder.pop();
        builder.pop();

        builder.comment("Arcanemicon").push("arcanemicon");
        RESEARCH_HARDMODE = builder.comment("Enable hard mode in research.")
                .define("researchHardMode", false);
        OLD_RESEARCH_MONOGRAM_OUTLINE = builder.comment("Enable old type of monogram outline in research.")
                .define("oldResearchMonogramOutline", false);
        BRIGHT_RESEARCH_MONOGRAM_OUTLINE = builder.comment("Enable bright type of monogram outline in research.")
                .define("brightResearchMonogramOutline", false);
        RESEARCH_MONOGRAM_CONNECTS = builder.comment("Enable connects with monogram in research.")
                .define("brightResearchMonogramOutline", true);
        MONOGRAM_GLOW = builder.comment("Enable monogram glow.")
                .define("monogramGlow", true);
        MONOGRAM_GLOW_COLOR = builder.comment("Enable monogram glow color.")
                .define("monogramGlowColor", true);
        MONOGRAM_COLOR = builder.comment("Enable monogram color.")
                .define("monogramColor", false);
        MONOGRAM_RAYS = builder.comment("Enable monogram glow rays.")
                .define("monogramRays", true);
        builder.pop();

        builder.comment("Numerical").push("numerical");
        NUMERICAL_WISSEN = builder.comment("Enable numerical wissen.")
                .define("wissen", false);
        NUMERICAL_COOLDOWN = builder.comment("Enable numerical cooldown.")
                .define("cooldown", false);
        SHOW_LIGHT_NAME = builder.comment("Enable show light name.")
                .define("light", false);
        NUMERICAL_EXPERIENCE = builder.comment("Enable numerical experience.")
                .define("experience", false);
        NUMERICAL_HEAT = builder.comment("Enable numerical heat.")
                .define("heat", false);
        NUMERICAL_FLUID = builder.comment("Enable numerical fluid.")
                .define("fluid", true);
        NUMERICAL_STEAM = builder.comment("Enable numerical steam.")
                .define("steam", false);
        builder.pop();

        builder.comment("Overlay").push("overlay");
        builder.comment("Arcane Wand").push("arcaneWand");
        ARCANE_WAND_OVERLAY_UP = builder.comment("Enable display HUD on top.")
                .define("up", true);
        ARCANE_WAND_OVERLAY_RIGHT = builder.comment("Enable display HUD on right.")
                .define("right", false);
        ARCANE_WAND_OVERLAY_SIDE_HUD = builder.comment("Enable side display HUD.")
                .define("sideHud", false);
        ARCANE_WAND_OVERLAY_SIDE_BAR = builder.comment("Enable side display bar.")
                .define("sideBar", false);
        ARCANE_WAND_OVERLAY_HORIZONTAL_BAR = builder.comment("Enable horizontal bar.")
                .define("horizontalBar", false);
        ARCANE_WAND_OVERLAY_X_OFFSET = builder.comment("HUD X offset.")
                .define("xOffset", 0);
        ARCANE_WAND_OVERLAY_Y_OFFSET = builder.comment("HUD Y offset.")
                .define("yOffset", 0);
        ARCANE_WAND_OVERLAY_SECOND_X_OFFSET = builder.comment("Second HUD X offset.")
                .define("secondXOffset", 0);
        ARCANE_WAND_OVERLAY_SECOND_Y_OFFSET = builder.comment("Second HUD Y offset.")
                .define("secondYOffset", 0);
        ARCANE_WAND_OVERLAY_BAR_X_OFFSET = builder.comment("Bar X offset.")
                .define("barXOffset", 0);
        ARCANE_WAND_OVERLAY_BAR_Y_OFFSET = builder.comment("Bar Y offset.")
                .define("barYOffset", 0);
        ARCANE_WAND_OVERLAY_SECOND_HUD_FREE = builder.comment("Enable second hud free offset.")
                .define("secondHudFree", false);
        ARCANE_WAND_OVERLAY_BAR_FREE = builder.comment("Enable bar free offset.")
                .define("barFree", false);
        ARCANE_WAND_OVERLAY_COOLDOWN_TEXT = builder.comment("Enable draw cooldown in HUD.")
                .define("cooldownText", false);
        ARCANE_WAND_OVERLAY_WISSEN_TEXT = builder.comment("Enable draw wissen in HUD.")
                .define("wissenText", false);
        ARCANE_WAND_OVERLAY_REVERSE_BAR = builder.comment("Enable reverse bar.")
                .define("reverseBar", false);
        ARCANE_WAND_OVERLAY_SHOW_EMPTY = builder.comment("Enable show empty spells in bar.")
                .define("showEmptySpells", true);
        builder.pop();
        builder.pop();

        builder.comment("Menu").push("menu");
        CUSTOM_PANORAMA = builder.comment("Enable custom panorama.")
                .define("customPanorama", false);
        PANORAMA_BUTTON = builder.comment("Enable custom panorama button.")
                .define("panoramaButton", true);
        PANORAMA_BUTTON_ROW = builder.comment("Custom panorama button row.")
                .defineInRange("panoramaButtonRow", 3, 0, 4);
        PANORAMA_BUTTON_ROW_X_OFFSET = builder.comment("Custom panorama button X offset with row.")
                .define("panoramaButtonRowXOffset", 4);
        PANORAMA_BUTTON_X_OFFSET = builder.comment("Custom panorama button X offset")
                .define("panoramaButtonXOffset", 0);
        PANORAMA_BUTTON_Y_OFFSET = builder.comment("Custom panorama button Y offset.")
                .define("panoramaButtonYOffset", 0);
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
