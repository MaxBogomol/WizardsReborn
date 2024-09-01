package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.material.Fluid;

public class WizardsRebornTags {
    public static final TagKey<Item> SCYTHES_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "scythes"));
    public static final TagKey<Item> ARCANE_GOLD_TOOLS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "arcane_gold_tools"));
    public static final TagKey<Item> ARCANE_WOOD_LOGS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_logs"));
    public static final TagKey<Item> ARCANE_WOOD_TOOLS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_tools"));
    public static final TagKey<Item> INNOCENT_WOOD_LOGS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_logs"));
    public static final TagKey<Item> INNOCENT_WOOD_TOOLS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_tools"));
    public static final TagKey<Item> ARCANE_LUMOS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "arcane_lumos"));
    public static final TagKey<Item> CRYSTALS_SEEDS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "crystal_seeds"));
    public static final TagKey<Item> FRACTURED_CRYSTALS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "fractured_crystals"));
    public static final TagKey<Item> CRYSTALS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "crystals"));
    public static final TagKey<Item> FACETED_CRYSTALS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "faceted_crystals"));
    public static final TagKey<Item> ADVANCED_CRYSTALS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "advanced_crystals"));
    public static final TagKey<Item> MASTERFUL_CRYSTALS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "masterful_crystals"));
    public static final TagKey<Item> PURE_CRYSTALS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "pure_crystals"));
    public static final TagKey<Item> ALL_CRYSTALS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "all_crystals"));
    public static final TagKey<Item> CRYSTAL_BAG_SLOTS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "crystal_bag_slots"));
    public static final TagKey<Item> ALCHEMY_BAG_SLOTS_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "alchemy_bag_slots"));
    public static final TagKey<Item> WISSEN_CASINGS_ITEM  = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "wissen_casings"));
    public static final TagKey<Item> LIGHT_CASINGS_ITEM  = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "light_casings"));
    public static final TagKey<Item> FLUID_CASINGS_ITEM  = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "fluid_casings"));
    public static final TagKey<Item> STEAM_CASINGS_ITEM  = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "steam_casings"));
    public static final TagKey<Item> SNIFFALO_FOOD_ITEM  = TagKey.create(Registries.ITEM, new ResourceLocation(WizardsReborn.MOD_ID, "sniffalo_food"));

    public static final TagKey<Block> FLUID_PIPE_CONNECTION_BLOCK = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_connection"));
    public static final TagKey<Block> FLUID_PIPE_CONNECTION_TOGGLE_BLOCK = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_connection_toggle"));
    public static final TagKey<Block> STEAM_PIPE_CONNECTION_BLOCK = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_connection"));
    public static final TagKey<Block> STEAM_PIPE_CONNECTION_TOGGLE_BLOCK = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_connection_toggle"));
    public static final TagKey<Block> EXTRACTOR_LEVER_CONNECTION_BLOCK = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "extractor_lever_connection"));
    public static final TagKey<Block> ALTAR_OF_DROUGHT_TARGET_BLOCK  = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "altar_of_drought_target"));
    public static final TagKey<Block> ORES_BLOCK  = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "ores"));
    public static final TagKey<Block> CORK_BAMBOO_PLANTABLE_ON_BLOCK  = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_plantable_on"));

    public static final TagKey<Fluid> STEAM_SOURCE_FLUID = TagKey.create(Registries.FLUID, new ResourceLocation(WizardsReborn.MOD_ID, "steam_source"));
    public static final TagKey<Fluid> HEAT_SOURCE_FLUID = TagKey.create(Registries.FLUID, new ResourceLocation(WizardsReborn.MOD_ID, "heat_source"));

    public static final TagKey<BannerPattern> VIOLENCE_BANNER_PATTERN = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/violence"));
    public static final TagKey<BannerPattern> REPRODUCTION_BANNER_PATTERN = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/reproduction"));
    public static final TagKey<BannerPattern> COOPERATION_BANNER_PATTERN = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/cooperation"));
    public static final TagKey<BannerPattern> HUNGER_BANNER_PATTERN = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/hunger"));
    public static final TagKey<BannerPattern> SURVIVAL_BANNER_PATTERN = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/survival"));
    public static final TagKey<BannerPattern> ELEVATION_BANNER_PATTERN = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/elevation"));

    public static final TagKey<DamageType> MAGIC_DAMAGE_TYPE = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge", "is_magic"));
    public static final TagKey<DamageType> ARCANE_MAGIC_DAMAGE_TYPE = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WizardsReborn.MOD_ID, "is_arcane_magic"));

    public static final ResourceLocation SNIFFALO_DIGGING_LOOT_TABLE = new ResourceLocation(WizardsReborn.MOD_ID, "gameplay/sniffalo_digging");
}
