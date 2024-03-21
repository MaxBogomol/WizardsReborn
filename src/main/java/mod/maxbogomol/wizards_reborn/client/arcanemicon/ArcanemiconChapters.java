package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtils;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotions;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtils;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRituals;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.BlockEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.IndexEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.MonogramIndexEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.SpellIndexEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.page.*;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.*;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.titledpage.*;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.RegisterAlchemyPotions;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.knowledge.RegisterKnowledges;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArcanemiconChapters {
    public static List<Category> categories = new ArrayList<>();
    public static Category ARCANE_NATURE, SPELLS, CRYSTAL_RITUALS, ALCHEMY;
    public static Chapter ARCANE_NATURE_INDEX, SPELLS_INDEX, CRYSTAL_RITUALS_INDEX, ALCHEMY_INDEX,
            ARCANUM, ARCANUM_DUST_TRANSMUTATION, ARCANE_WOOD, ARCANE_GOLD, SCYTHES, TRINKETS, ARCANE_WOOD_BOW, BANNER_PATTERNS, WISSEN, WISSEN_TRANSLATOR, ARCANE_PEDESTAL, WISSEN_ALTAR, WISSEN_CRYSTALLIZER, ARCANE_WORKBENCH, ARCANE_LUMOS, CRYSTALS, ARCANE_WAND, AUTOMATION, WISSEN_CELL, CRYSTAL_BAG, TOTEM_OF_FLAMES, EXPERIENCE_TOTEM, TOTEM_OF_EXPERIENCE_ABSORPTION, TOTEM_OF_DISENCHANT, ALTAR_OF_DROUGHT, VOID_CRYSTAL, ARCANE_FORTRESS_ARMOR, INVENTOR_WIZARD_ARMOR, ARCANE_WOOD_CANE, ARCANE_ITERATOR, ARCANUM_LENS, WISSEN_KEYCHAIN, WISSEN_RING, JEWELER_TABLE, MUSIC_DISC_REBORN,
            ARCANE_LEVER, REDSTONE_SENSOR, WISSEN_SENSOR, COOLDOWN_SENSOR, EXPERIENCE_SENSOR, LIGHT_SENSOR, HEAT_SENSOR, FLUID_SENSOR, STEAM_SENSOR, WISSEN_ACTIVATOR, ITEM_SORTER, ARCANE_WOOD_FRAME, WISSEN_CASING, WISESTONE_CASING, FLUID_CASING, STEAM_CASING, GLASS_FRAME, LIGHT_CASING, INNOCENT_CASING,
            ALL_SPELLS, EARTH_SPELLS, WATER_SPELLS, AIR_SPELLS, FIRE_SPELLS, VOID_SPELLS,
            EARTH_PROJECTILE, WATER_PROJECTILE, AIR_PROJECTILE, FIRE_PROJECTILE, VOID_PROJECTILE, FROST_PROJECTILE, HOLY_PROJECTILE, CURSE_PROJECTILE,
            EARTH_RAY, WATER_RAY, AIR_RAY, FIRE_RAY, VOID_RAY, FROST_RAY, HOLY_RAY, CURSE_RAY,
            EARTH_CHARGE, WATER_CHARGE, AIR_CHARGE, FIRE_CHARGE, VOID_CHARGE, FROST_CHARGE, HOLY_CHARGE, CURSE_CHARGE,
            HEART_OF_NATURE, WATER_BREATHING, AIR_FLOW, FIRE_SHIELD, MAGIC_SPROUT,
            MONOGRAMS, RESEARCHES, RESEARCH, LUNAM_MONOGRAM, VITA_MONOGRAM, SOLEM_MONOGRAM, MORS_MONOGRAM, MIRACULUM_MONOGRAM, TEMPUS_MONOGRAM, STATERA_MONOGRAM, ECLIPSIS_MONOGRAM, SICCITAS_MONOGRAM, SOLSTITIUM_MONOGRAM, FAMES_MONOGRAM, RENAISSANCE_MONOGRAM, BELLUM_MONOGRAM, LUX_MONOGRAM, KARA_MONOGRAM, DEGRADATIO_MONOGRAM, PRAEDICTIONEM_MONOGRAM, EVOLUTIONIS_MONOGRAM, TENEBRIS_MONOGRAM, UNIVERSUM_MONOGRAM,
            LIGHT_RAYS, LIGHT_EMITTER, LIGHT_TRANSFER_LENS, RUNIC_PEDESTAL, CRYSTALS_RITUALS, ARTIFICIAL_FERTILITY, RITUAL_BREEDING, CRYSTAL_GROWTH_ACCELERATION, CRYSTAL_INFUSION, ARCANUM_SEED, INNOCENT_WOOD,
            MOR, MORTAR, ARCANE_LINEN, MUSHROOM_CAPS, WISESTONE, WISESTONE_PEDESTAL, FLUID_PIPES, STEAM_PIPES, ORBITAL_FLUID_RETAINER, ALCHEMY_FURNACE, STEAM_THERMAL_STORAGE, ALCHEMY_MACHINE, ALCHEMY_OIL, MUSIC_DISC_ARCANUM, MUSIC_DISC_MOR, ALCHEMY_CALX, ALCHEMY_GLASS, ALCHEMY_BAG, ALCHEMY_POTIONS, TEA, ALCHEMY_BREWS, ADVANCED_CALX, ALCHEMY_TRANSMUTATION, ARCANE_CENSER, SMOKING_PIPE, ARCACITE, ARCACITE_POLISHING_MIXTURE;
    public static ResearchPage RESEARCH_MAIN, RESEARCH_LIST;

    public static ItemStack EMPTY_ITEM, 
            ARCANE_PEDESTAL_ITEM, TOTEM_BASE_ITEM, WISESTONE_PEDESTAL_ITEM, RUNIC_PEDESTAL_ITEM, INNOCENT_PEDESTAL_ITEM, 
            ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANUM_ITEM, ARCANUM_DUST_ITEM, ARCACITE_ITEM, 
            ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_SLAB_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
            ALCHEMY_CALX_ITEM, NATURAL_CALX_ITEM, SCORCHED_CALX_ITEM, DISTANT_CALX_ITEM, ENCHANTED_CALX_ITEM, ARCANUM_LENS_ITEM;

    public static void itemsInit() {
        EMPTY_ITEM = ItemStack.EMPTY;
        ARCANE_PEDESTAL_ITEM = new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get());
        TOTEM_BASE_ITEM = new ItemStack(WizardsReborn.TOTEM_BASE_ITEM.get());
        WISESTONE_PEDESTAL_ITEM = new ItemStack(WizardsReborn.WISESTONE_PEDESTAL_ITEM.get());
        RUNIC_PEDESTAL_ITEM = new ItemStack(WizardsReborn.RUNIC_PEDESTAL_ITEM.get());
        INNOCENT_PEDESTAL_ITEM = new ItemStack(WizardsReborn.INNOCENT_PEDESTAL_ITEM.get());
        ARCANE_GOLD_INGOT_ITEM = new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get());
        ARCANE_GOLD_NUGGET_ITEM = new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get());
        ARCANUM_ITEM = new ItemStack(WizardsReborn.ARCANUM.get());
        ARCANUM_DUST_ITEM = new ItemStack(WizardsReborn.ARCANUM_DUST.get());
        ARCACITE_ITEM = new ItemStack(WizardsReborn.ARCACITE.get());
        ARCANE_WOOD_PLANKS_ITEM = new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get());
        ARCANE_WOOD_SLAB_ITEM = new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get());
        INNOCENT_WOOD_PLANKS_ITEM = new ItemStack(WizardsReborn.INNOCENT_WOOD_PLANKS_ITEM.get());
        INNOCENT_WOOD_SLAB_ITEM = new ItemStack(WizardsReborn.INNOCENT_WOOD_SLAB_ITEM.get());
        POLISHED_WISESTONE_ITEM = new ItemStack(WizardsReborn.POLISHED_WISESTONE_ITEM.get());
        POLISHED_WISESTONE_SLAB_ITEM = new ItemStack(WizardsReborn.POLISHED_WISESTONE_SLAB_ITEM.get());
        ALCHEMY_CALX_ITEM = new ItemStack(WizardsReborn.ALCHEMY_CALX.get());
        NATURAL_CALX_ITEM = new ItemStack(WizardsReborn.NATURAL_CALX.get());
        SCORCHED_CALX_ITEM = new ItemStack(WizardsReborn.SCORCHED_CALX.get());
        DISTANT_CALX_ITEM = new ItemStack(WizardsReborn.DISTANT_CALX.get());
        ENCHANTED_CALX_ITEM = new ItemStack(WizardsReborn.ENCHANTED_CALX.get());
        ARCANUM_LENS_ITEM = new ItemStack(WizardsReborn.ARCANUM_LENS.get());
    }

    public static void arcaneNatureInit() {
        ARCANUM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcanum",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANUM_ITEM),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANUM_DUST_ITEM),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANUM_BLOCK_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANUM_ORE_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.DEEPSLATE_ARCANUM_ORE_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_BLOCK_ITEM.get()),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM.get(), 9), new ItemStack(WizardsReborn.ARCANUM_BLOCK_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_DUST.get(), 2), ARCANUM_ITEM),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_DUST.get(), 3), ARCANUM_ITEM, new ItemStack(Items.REDSTONE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANEMICON.get()), new ItemStack(Items.BOOK), ARCANUM_ITEM)
        );

        ARCANUM_DUST_TRANSMUTATION = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcanum_dust_transmutation",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum_dust_transmutation",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANUM_DUST_ITEM))
        );

        ARCANE_WOOD = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_wood",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_sapling",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_SAPLING_ITEM.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.ARCANE_WOOD_SAPLING_ITEM.get()), new ItemStack(Items.OAK_SAPLING)),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_ITEM.get())),
                        new BlockEntry(ARCANE_WOOD_PLANKS_ITEM),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_STAIRS_ITEM.get())),
                        new BlockEntry(ARCANE_WOOD_SLAB_ITEM),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_FENCE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_FENCE_GATE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_DOOR_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_TRAPDOOR_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_PRESSURE_PLATE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_BUTTON_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_SIGN_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_HANGING_SIGN_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_BOAT_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_CHEST_BOAT_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_ITEM.get()),
                        new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get(), 4), new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_STAIRS_ITEM.get(), 4),
                        ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get(), 6),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_FENCE_ITEM.get(), 3),
                        ARCANE_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK), ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK), ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_FENCE_GATE_ITEM.get()),
                        new ItemStack(Items.STICK), ARCANE_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK),
                        new ItemStack(Items.STICK), ARCANE_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_DOOR_ITEM.get(), 3),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_TRAPDOOR_ITEM.get(), 2),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_PRESSURE_PLATE_ITEM.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_BUTTON_ITEM.get()), ARCANE_WOOD_PLANKS_ITEM),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_SIGN_ITEM.get(), 3),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        EMPTY_ITEM, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_HANGING_SIGN_ITEM.get(), 6),
                        new ItemStack(Items.CHAIN), EMPTY_ITEM, new ItemStack(Items.CHAIN),
                        new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get()),
                        new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_BOAT_ITEM.get()),
                        ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_CHEST_BOAT_ITEM.get()), new ItemStack(Items.CHEST), new ItemStack(WizardsReborn.ARCANE_WOOD_BOAT_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get())
                )
        );

        ARCANE_GOLD = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_gold",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANE_GOLD_INGOT_ITEM),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANE_GOLD_NUGGET_ITEM),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_BLOCK_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_ORE_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.DEEPSLATE_ARCANE_GOLD_ORE_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.NETHER_ARCANE_GOLD_ORE_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.RAW_ARCANE_GOLD_BLOCK_ITEM.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.ARCANE_GOLD_ORE_ITEM.get()), new ItemStack(Items.GOLD_ORE)),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.DEEPSLATE_ARCANE_GOLD_ORE_ITEM.get()), new ItemStack(Items.DEEPSLATE_GOLD_ORE)),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.NETHER_ARCANE_GOLD_ORE_ITEM.get()), new ItemStack(Items.NETHER_GOLD_ORE)),
                new SmeltingPage(ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get())),
                new SmeltingPage(ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_ORE_ITEM.get())),
                new SmeltingPage(ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.DEEPSLATE_ARCANE_GOLD_ORE_ITEM.get())),
                new SmeltingPage(ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.NETHER_ARCANE_GOLD_ORE_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_BLOCK_ITEM.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM
                ),
                new CraftingTablePage(ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.RAW_ARCANE_GOLD_BLOCK_ITEM.get()),
                        new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()),
                        new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()),
                        new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get(), 9), new ItemStack(WizardsReborn.ARCANE_GOLD_BLOCK_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get(), 9), ARCANE_GOLD_INGOT_ITEM),
                new CraftingTablePage(new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get(), 9), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD_BLOCK_ITEM.get())),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold_tools",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_SWORD.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_PICKAXE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_AXE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_SHOVEL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_HOE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_SWORD.get()),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_PICKAXE.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_AXE.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_SHOVEL.get()),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_HOE.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ARCANE_GOLD_INGOT_ITEM,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM, EMPTY_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold_armor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_HELMET.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_CHESTPLATE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_LEGGINGS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_BOOTS.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_HELMET.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_CHESTPLATE.get()),
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_LEGGINGS.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_BOOTS.get()),
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM
                )
        );

        SCYTHES = new Chapter(
                "wizards_reborn.arcanemicon.chapter.scythes",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.scythes",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ARCANE_GOLD_INGOT_ITEM,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM, EMPTY_ITEM
                )
        );

        TRINKETS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.trinkets",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.trinkets",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANUM_AMULET.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANUM_RING.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.LEATHER_BELT.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_AMULET.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANUM_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_RING.get()),
                        ARCANUM_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.LEATHER_BELT.get()),
                        EMPTY_ITEM, new ItemStack(Items.LEATHER), EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), EMPTY_ITEM, new ItemStack(Items.LEATHER),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM
                )
        );

        ARCANE_WOOD_BOW = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_wood_bow",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_bow",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BOW.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_BOW.get()),
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), new ItemStack(Items.STRING),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM, new ItemStack(Items.STRING),
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), new ItemStack(Items.STRING)
                )
        );

        BANNER_PATTERNS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.banner_patterns",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.banner_patterns",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.VIOLENCE_BANNER_PATTERN_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.REPRODUCTION_BANNER_PATTERN_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.COOPERATION_BANNER_PATTERN_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.HUNGER_BANNER_PATTERN_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.SURVIVAL_BANNER_PATTERN_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ELEVATION_BANNER_PATTERN_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.VIOLENCE_BANNER_PATTERN_ITEM.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.RED_WOOL), ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.REPRODUCTION_BANNER_PATTERN_ITEM.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.PINK_WOOL), ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.COOPERATION_BANNER_PATTERN_ITEM.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.BLUE_WOOL), ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.HUNGER_BANNER_PATTERN_ITEM.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.BROWN_WOOL), ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.SURVIVAL_BANNER_PATTERN_ITEM.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.GREEN_WOOL), ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ELEVATION_BANNER_PATTERN_ITEM.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.YELLOW_WOOL), ARCANUM_ITEM
                )
        );

        WISSEN = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_wand",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_WAND.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_wand_mode.functional",
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_wand_mode.receive_connect",
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get())),
                        new BlockEntry(),
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_wand_mode.send_connect",
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get())),
                        new BlockEntry(),
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.wissen_wand_mode.reload"),
                new TitlePage("wizards_reborn.arcanemicon.page.wissen_wand_mode.off"),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISSEN_WAND.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ARCANUM_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM, EMPTY_ITEM
                ),
                new ImagePage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/wissen_image_page.png"))
        );

        WISSEN_TRANSLATOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_translator",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_translator",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get(), 2),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM
                )
        );

        ARCANE_PEDESTAL = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_pedestal",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_pedestal",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANE_PEDESTAL_ITEM)
                ),
                new CraftingTablePage(ARCANE_PEDESTAL_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.hovering_tome_stand",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.HOVERING_TOME_STAND.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.HOVERING_TOME_STAND.get()),
                        ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANEMICON.get())
                )
        );

        WISSEN_ALTAR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_altar",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_altar.0",
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), ARCANUM_ITEM),
                        new BlockEntry(),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get())),
                        new BlockEntry(),
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), ARCANUM_DUST_ITEM)
                ),
                new TextPage("wizards_reborn.arcanemicon.page.wissen_altar.1"),
                new WissenAltarPage(ARCANUM_ITEM),
                new WissenAltarPage(ARCANUM_DUST_ITEM),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()),
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_PEDESTAL_ITEM, ARCANE_GOLD_NUGGET_ITEM
                )
        );

        WISSEN_CRYSTALLIZER = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_crystallizer",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_crystallizer",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM
                )
        );

        ARCANE_WORKBENCH = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_workbench",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_workbench",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()),
                        new ItemStack(Items.RED_WOOL), new ItemStack(Items.RED_WOOL), new ItemStack(Items.RED_WOOL),
                        ARCANE_GOLD_INGOT_ITEM, ARCANUM_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                )
        );

        ARCANE_LUMOS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_lumos",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_lumos.0",
                        new BlockEntry(new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ORANGE_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.MAGENTA_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.LIGHT_BLUE_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.YELLOW_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.LIME_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.PINK_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.GRAY_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.LIGHT_GRAY_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.CYAN_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.PURPLE_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.BLUE_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.BROWN_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.GREEN_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.RED_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.BLACK_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.RAINBOW_ARCANE_LUMOS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.COSMIC_ARCANE_LUMOS_ITEM.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.arcane_lumos.1"),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()),
                        ARCANUM_ITEM, new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.RAINBOW_ARCANE_LUMOS_ITEM.get(), 8),
                        new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.RED_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.ORANGE_ARCANE_LUMOS_ITEM.get()),
                        new ItemStack(WizardsReborn.YELLOW_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.LIME_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_BLUE_ARCANE_LUMOS_ITEM.get()),
                        new ItemStack(WizardsReborn.BLUE_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.PURPLE_ARCANE_LUMOS_ITEM.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.COSMIC_ARCANE_LUMOS_ITEM.get()),
                        new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.AMETHYST_SHARD)
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.WHITE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.ORANGE_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.ORANGE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.MAGENTA_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.MAGENTA_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.LIGHT_BLUE_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.LIGHT_BLUE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.YELLOW_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.YELLOW_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.LIME_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.LIME_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.PINK_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.PINK_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.GRAY_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.GRAY_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.LIGHT_GRAY_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.LIGHT_GRAY_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.CYAN_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.CYAN_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.PURPLE_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.PURPLE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.BLUE_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.BLUE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.BROWN_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.BROWN_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.GREEN_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.GREEN_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.RED_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.RED_DYE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.BLACK_ARCANE_LUMOS_ITEM.get()), new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), new ItemStack(Items.BLACK_DYE))
        );

        CRYSTALS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.crystals",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystal_seeds",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WATER_CRYSTAL_SEED.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.AIR_CRYSTAL_SEED.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FIRE_CRYSTAL_SEED.get()))
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get()),
                        ARCANUM_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.DIRT), new ItemStack(Items.STONE)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.WATER_CRYSTAL_SEED.get()),
                        ARCANUM_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.SUGAR_CANE), new ItemStack(Items.COD)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.AIR_CRYSTAL_SEED.get()),
                        ARCANUM_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.FEATHER), new ItemStack(Items.WHITE_WOOL)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.FIRE_CRYSTAL_SEED.get()),
                        ARCANUM_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.COAL), new ItemStack(Items.BLAZE_POWDER)
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystals",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.EARTH_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WATER_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.AIR_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FIRE_CRYSTAL.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()), new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get())),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.WATER_CRYSTAL.get()), new ItemStack(WizardsReborn.WATER_CRYSTAL_SEED.get())),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.AIR_CRYSTAL.get()), new ItemStack(WizardsReborn.AIR_CRYSTAL_SEED.get())),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.FIRE_CRYSTAL.get()), new ItemStack(WizardsReborn.FIRE_CRYSTAL_SEED.get())),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.fractured_crystals",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FRACTURED_EARTH_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FRACTURED_WATER_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FRACTURED_AIR_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FRACTURED_FIRE_CRYSTAL.get()))
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()),
                        ARCANUM_ITEM, new ItemStack(WizardsReborn.FRACTURED_EARTH_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_EARTH_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_EARTH_CRYSTAL.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.WATER_CRYSTAL.get()),
                        ARCANUM_ITEM, new ItemStack(WizardsReborn.FRACTURED_WATER_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_WATER_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_WATER_CRYSTAL.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.AIR_CRYSTAL.get()),
                        ARCANUM_ITEM, new ItemStack(WizardsReborn.FRACTURED_AIR_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_AIR_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_AIR_CRYSTAL.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.FIRE_CRYSTAL.get()),
                        ARCANUM_ITEM, new ItemStack(WizardsReborn.FRACTURED_FIRE_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_FIRE_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_FIRE_CRYSTAL.get())
                )
        );

        ARCANE_WAND = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_wand",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wand.0",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WAND.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand.focus"),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand.balance"),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand.absorption"),
                new TextPage("wizards_reborn.arcanemicon.page.arcane_wand.1"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WAND.get()),
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_INGOT_ITEM,  EMPTY_ITEM,  EMPTY_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                )
        );

        ARCANE_LEVER = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_lever",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_lever",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_LEVER_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_LEVER_ITEM.get(), 4),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM
                )
        );

        REDSTONE_SENSOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.redstone_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.redstone_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get()),
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM,
                        new ItemStack(Items.COMPARATOR), EMPTY_ITEM, new ItemStack(Items.REDSTONE)
                )
        );

        WISSEN_SENSOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_SENSOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISSEN_SENSOR_ITEM.get()),
                        EMPTY_ITEM, ARCANUM_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get()), EMPTY_ITEM,
                        EMPTY_ITEM, ARCANUM_ITEM
                )
        );

        COOLDOWN_SENSOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.cooldown_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.cooldown_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.COOLDOWN_SENSOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.COOLDOWN_SENSOR_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get())
                )
        );

        EXPERIENCE_SENSOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.experience_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.experience_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.EXPERIENCE_SENSOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.EXPERIENCE_SENSOR_ITEM.get()),
                        EMPTY_ITEM, ENCHANTED_CALX_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get())
                )
        );

        LIGHT_SENSOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.light_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.light_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.LIGHT_SENSOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.LIGHT_SENSOR_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get())
                )
        );

        HEAT_SENSOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.heat_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.heat_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.HEAT_SENSOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.HEAT_SENSOR_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(Items.FURNACE), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get())
                )
        );

        FLUID_SENSOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fluid_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.fluid_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FLUID_SENSOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.FLUID_SENSOR_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get())
                )
        );

        STEAM_SENSOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.steam_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.steam_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.STEAM_SENSOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.STEAM_SENSOR_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get())
                )
        );

        WISSEN_ACTIVATOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_activator",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_activator",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_ACTIVATOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISSEN_ACTIVATOR_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISSEN_WAND.get()), EMPTY_ITEM,
                        ARCANUM_ITEM, new ItemStack(WizardsReborn.WISSEN_SENSOR_ITEM.get()), ARCANUM_ITEM
                )
        );

        ITEM_SORTER = new Chapter(
                "wizards_reborn.arcanemicon.chapter.item_sorter",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.item_sorter",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ITEM_SORTER_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ITEM_SORTER_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(Items.CHEST), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()), new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get()), new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(Items.REDSTONE_BLOCK)
                )
        );

        ARCANE_WOOD_FRAME = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_wood_frame",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_FRAME_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WOOD_FRAME_ITEM.get()),
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_SLAB_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WOOD_CASING_ITEM.get()),
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_FRAME_ITEM.get())
                )
        );

        WISSEN_CASING = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_WISSEN_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WOOD_WISSEN_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get())
                )
        );

        WISESTONE_CASING = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wisestone_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISESTONE_FRAME_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISESTONE_FRAME_ITEM.get()),
                        POLISHED_WISESTONE_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, POLISHED_WISESTONE_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISESTONE_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISESTONE_CASING_ITEM.get()),
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISESTONE_FRAME_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_wissen_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISESTONE_WISSEN_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISESTONE_WISSEN_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISESTONE_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get())
                )
        );

        FLUID_CASING = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fluid_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_fluid_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_FLUID_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WOOD_FLUID_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_fluid_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISESTONE_FLUID_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISESTONE_FLUID_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISESTONE_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get())
                )
        );

        STEAM_CASING = new Chapter(
                "wizards_reborn.arcanemicon.chapter.steam_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_steam_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_STEAM_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WOOD_STEAM_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_steam_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISESTONE_STEAM_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISESTONE_STEAM_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISESTONE_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get())
                )
        );

        GLASS_FRAME = new Chapter(
                "wizards_reborn.arcanemicon.chapter.glass_frame",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_glass_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_GLASS_FRAME_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WOOD_GLASS_FRAME_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISESTONE_FRAME_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_glass_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISESTONE_GLASS_FRAME_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISESTONE_GLASS_FRAME_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISESTONE_FRAME_ITEM.get())
                )
        );

        LIGHT_CASING = new Chapter(
                "wizards_reborn.arcanemicon.chapter.light_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_light_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_LIGHT_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WOOD_LIGHT_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_light_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISESTONE_LIGHT_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISESTONE_LIGHT_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISESTONE_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get())
                )
        );

        INNOCENT_CASING = new Chapter(
                "wizards_reborn.arcanemicon.chapter.innocent_wood_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_FRAME_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INNOCENT_WOOD_FRAME_ITEM.get()),
                        INNOCENT_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, INNOCENT_WOOD_SLAB_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        INNOCENT_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, INNOCENT_WOOD_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INNOCENT_WOOD_CASING_ITEM.get()),
                        EMPTY_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_FRAME_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_wissen_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_WISSEN_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INNOCENT_WOOD_WISSEN_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_fluid_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_FLUID_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INNOCENT_WOOD_FLUID_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_steam_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_STEAM_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INNOCENT_WOOD_STEAM_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_glass_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_GLASS_FRAME_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INNOCENT_WOOD_GLASS_FRAME_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_FRAME_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_light_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_LIGHT_CASING_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INNOCENT_WOOD_LIGHT_CASING_ITEM.get()),
                        new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_CASING_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get())
                )
        );

        AUTOMATION = new Chapter(
                "wizards_reborn.arcanemicon.chapter.automation",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.automation"),
                new IndexPage(
                        new IndexEntry(ARCANE_LEVER, new ItemStack(WizardsReborn.ARCANE_LEVER_ITEM.get()), RegisterKnowledges.WISESTONE),
                        new IndexEntry(REDSTONE_SENSOR, new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get())),
                        new IndexEntry(WISSEN_SENSOR, new ItemStack(WizardsReborn.WISSEN_SENSOR_ITEM.get())),
                        new IndexEntry(COOLDOWN_SENSOR, new ItemStack(WizardsReborn.COOLDOWN_SENSOR_ITEM.get())),
                        new IndexEntry(EXPERIENCE_SENSOR, new ItemStack(WizardsReborn.EXPERIENCE_SENSOR_ITEM.get())),
                        new IndexEntry(LIGHT_SENSOR, new ItemStack(WizardsReborn.LIGHT_SENSOR_ITEM.get())),
                        new IndexEntry(HEAT_SENSOR, new ItemStack(WizardsReborn.HEAT_SENSOR_ITEM.get()), RegisterKnowledges.ALCHEMY_FURNACE)
                ),
                new IndexPage(
                        new IndexEntry(FLUID_SENSOR, new ItemStack(WizardsReborn.FLUID_SENSOR_ITEM.get()), RegisterKnowledges.FLUID_PIPE),
                        new IndexEntry(STEAM_SENSOR, new ItemStack(WizardsReborn.STEAM_SENSOR_ITEM.get()), RegisterKnowledges.STEAM_PIPE),
                        new IndexEntry(WISSEN_ACTIVATOR, new ItemStack(WizardsReborn.WISSEN_ACTIVATOR_ITEM.get())),
                        new IndexEntry(ITEM_SORTER, new ItemStack(WizardsReborn.ITEM_SORTER_ITEM.get()), RegisterKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(ARCANE_WOOD_FRAME, new ItemStack(WizardsReborn.ARCANE_WOOD_FRAME_ITEM.get())),
                        new IndexEntry(WISSEN_CASING, new ItemStack(WizardsReborn.ARCANE_WOOD_WISSEN_CASING_ITEM.get())),
                        new IndexEntry(WISESTONE_CASING, new ItemStack(WizardsReborn.WISESTONE_CASING_ITEM.get()), RegisterKnowledges.WISESTONE)
                ),
                new IndexPage(
                        new IndexEntry(FLUID_CASING, new ItemStack(WizardsReborn.ARCANE_WOOD_FLUID_CASING_ITEM.get()), RegisterKnowledges.FLUID_PIPE),
                        new IndexEntry(STEAM_CASING, new ItemStack(WizardsReborn.ARCANE_WOOD_STEAM_CASING_ITEM.get()), RegisterKnowledges.STEAM_PIPE),
                        new IndexEntry(GLASS_FRAME, new ItemStack(WizardsReborn.ARCANE_WOOD_GLASS_FRAME_ITEM.get()), RegisterKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(LIGHT_CASING, new ItemStack(WizardsReborn.ARCANE_WOOD_LIGHT_CASING_ITEM.get()), RegisterKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(INNOCENT_CASING, new ItemStack(WizardsReborn.INNOCENT_WOOD_CASING_ITEM.get()), RegisterKnowledges.CRYSTAL_INFUSION)
                )
        );

        WISSEN_CELL = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_cell",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_cell.0",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_CELL_ITEM.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.wissen_cell.1"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISSEN_CELL_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, new ItemStack(WizardsReborn.ARCANUM_BLOCK_ITEM.get()), ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANUM_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM
                )
        );

        CRYSTAL_BAG = new Chapter(
                "wizards_reborn.arcanemicon.chapter.crystal_bag",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystal_bag",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.CRYSTAL_BAG.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.CRYSTAL_BAG.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.LEATHER_BELT.get()),  EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(Items.CHEST),  new ItemStack(Items.LEATHER),
                        new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get()), new ItemStack(Items.LEATHER),  new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get()),
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ENCHANTED_CALX_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.CRYSTAL_BAG.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.LEATHER_BELT.get()),  EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(Items.CHEST),  new ItemStack(Items.LEATHER),
                        new ItemStack(WizardsReborn.WATER_CRYSTAL_SEED.get()), new ItemStack(Items.LEATHER),  new ItemStack(WizardsReborn.WATER_CRYSTAL_SEED.get()),
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ENCHANTED_CALX_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.CRYSTAL_BAG.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.LEATHER_BELT.get()),  EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(Items.CHEST),  new ItemStack(Items.LEATHER),
                        new ItemStack(WizardsReborn.AIR_CRYSTAL_SEED.get()), new ItemStack(Items.LEATHER),  new ItemStack(WizardsReborn.AIR_CRYSTAL_SEED.get()),
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ENCHANTED_CALX_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.CRYSTAL_BAG.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.LEATHER_BELT.get()),  EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(Items.CHEST),  new ItemStack(Items.LEATHER),
                        new ItemStack(WizardsReborn.FIRE_CRYSTAL_SEED.get()), new ItemStack(Items.LEATHER),  new ItemStack(WizardsReborn.FIRE_CRYSTAL_SEED.get()),
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ENCHANTED_CALX_ITEM
                )
        );

        TOTEM_OF_FLAMES = new Chapter(
                "wizards_reborn.arcanemicon.chapter.totem_of_flames",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.totem_of_flames",
                        new BlockEntry(TOTEM_BASE_ITEM, new ItemStack(WizardsReborn.TOTEM_OF_FLAMES_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.TOTEM_OF_FLAMES_ITEM.get()),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ALCHEMY_CALX_ITEM, new ItemStack(Items.COAL), ALCHEMY_CALX_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM
                )
        );

        EXPERIENCE_TOTEM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.experience_totem",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.experience_totem",
                        new BlockEntry(TOTEM_BASE_ITEM, new ItemStack(WizardsReborn.EXPERIENCE_TOTEM_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.EXPERIENCE_TOTEM_ITEM.get()),
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        NATURAL_CALX_ITEM, EMPTY_ITEM, NATURAL_CALX_ITEM
                )
        );

        TOTEM_OF_EXPERIENCE_ABSORPTION = new Chapter(
                "wizards_reborn.arcanemicon.chapter.totem_of_experience_absorption",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.totem_of_experience_absorption",
                        new BlockEntry(TOTEM_BASE_ITEM, new ItemStack(WizardsReborn.TOTEM_OF_EXPERIENCE_ABSORPTION_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.TOTEM_OF_EXPERIENCE_ABSORPTION_ITEM.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCACITE_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ARCACITE_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCACITE_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM
                )
        );

        TOTEM_OF_DISENCHANT = new Chapter(
                "wizards_reborn.arcanemicon.chapter.totem_of_disenchant",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.totem_of_disenchant",
                        new BlockEntry(TOTEM_BASE_ITEM, new ItemStack(WizardsReborn.TOTEM_OF_DISENCHANT_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.TOTEM_OF_DISENCHANT_ITEM.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCACITE_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM
                )
        );

        ALTAR_OF_DROUGHT = new Chapter(
                "wizards_reborn.arcanemicon.chapter.altar_of_drought",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.altar_of_drought",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ALTAR_OF_DROUGHT_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ALTAR_OF_DROUGHT_ITEM.get()),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, EMPTY_ITEM, ARCANE_WOOD_SLAB_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, WISESTONE_PEDESTAL_ITEM, ARCACITE_ITEM
                ),
                new TextPage("wizards_reborn.arcanemicon.page.altar_of_drought.poem")
        );

        VOID_CRYSTAL = new Chapter(
                "wizards_reborn.arcanemicon.chapter.void_crystal",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.void_crystal",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.VOID_CRYSTAL_SEED.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.VOID_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FRACTURED_VOID_CRYSTAL.get()))
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.VOID_CRYSTAL_SEED.get(), 2),
                        ARCACITE_ITEM, new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get()), new ItemStack(WizardsReborn.WATER_CRYSTAL_SEED.get()), new ItemStack(WizardsReborn.AIR_CRYSTAL_SEED.get()), new ItemStack(WizardsReborn.FIRE_CRYSTAL_SEED.get())
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.VOID_CRYSTAL.get()), new ItemStack(WizardsReborn.VOID_CRYSTAL_SEED.get())),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.VOID_CRYSTAL.get()),
                        ARCANUM_ITEM, new ItemStack(WizardsReborn.FRACTURED_VOID_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_VOID_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_VOID_CRYSTAL.get())
                )
        );

        ARCANE_FORTRESS_ARMOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_fortress_armor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_fortress_armor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_FORTRESS_HELMET.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_FORTRESS_CHESTPLATE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_FORTRESS_LEGGINGS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_FORTRESS_BOOTS.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_FORTRESS_HELMET.get()),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_HELMET.get()), ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_FORTRESS_CHESTPLATE.get()),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(WizardsReborn.ARCANE_GOLD_CHESTPLATE.get()), new ItemStack(Items.LEATHER),
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_FORTRESS_LEGGINGS.get()),
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_LEGGINGS.get()), ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_FORTRESS_BOOTS.get()),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsReborn.ARCANE_GOLD_BOOTS.get()), ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_fortress_belt",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_FORTRESS_BELT.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_FORTRESS_BELT.get()),
                        EMPTY_ITEM,  new ItemStack(WizardsReborn.LEATHER_BELT.get()), EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, NATURAL_CALX_ITEM
                )
        );

        INVENTOR_WIZARD_ARMOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.inventor_wizard_armor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.inventor_wizard_armor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INVENTOR_WIZARD_HAT.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INVENTOR_WIZARD_COSTUME.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INVENTOR_WIZARD_TROUSERS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INVENTOR_WIZARD_BOOTS.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.inventor_wizard_armor.wissen_nature"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INVENTOR_WIZARD_HAT.get()),
                        new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), new ItemStack(WizardsReborn.ARCANE_GOLD_HELMET.get()),  new ItemStack(Items.LEATHER),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCACITE_ITEM, new ItemStack(Items.LEATHER), ARCACITE_ITEM, new ItemStack(Items.LEATHER)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INVENTOR_WIZARD_COSTUME.get()),
                        new ItemStack(Items.LEATHER), ARCANE_GOLD_NUGGET_ITEM, new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), new ItemStack(WizardsReborn.ARCANE_GOLD_CHESTPLATE.get()), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER),
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INVENTOR_WIZARD_TROUSERS.get()),
                        new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), new ItemStack(WizardsReborn.ARCANE_GOLD_LEGGINGS.get()), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), ARCANE_GOLD_NUGGET_ITEM, new ItemStack(Items.LEATHER),
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.INVENTOR_WIZARD_BOOTS.get()),
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(WizardsReborn.ARCANE_GOLD_BOOTS.get()), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), EMPTY_ITEM, new ItemStack(Items.LEATHER),
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                )
        );

        ARCANE_WOOD_CANE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_wood_cane",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_cane",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_CANE.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WOOD_CANE.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()), EMPTY_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                )
        );

        ARCANE_ITERATOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_iterator",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_iterator.0",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_ITERATOR_ITEM.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.arcane_iterator.1"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_ITERATOR_ITEM.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.ARCACITE_BLOCK_ITEM.get()), ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM
                ),
                new ImagePage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/arcane_iterator_image_page.png")),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_enchantments"),
                new TitlePage("wizards_reborn.arcanemicon.page.wissen_mending"),
                new ArcaneIteratorPage(new ItemStack(WizardsReborn.ARCANE_GOLD_PICKAXE.get()), 5, 0, WizardsReborn.WISSEN_MENDING_ARCANE_ENCHANTMENT,
                        new ItemStack(WizardsReborn.ARCANE_GOLD_PICKAXE.get()), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.magic_blade"),
                new ArcaneIteratorPage(new ItemStack(WizardsReborn.ARCANE_GOLD_SWORD.get()), 5, 0, WizardsReborn.MAGIC_BLADE_ARCANE_ENCHANTMENT,
                        new ItemStack(WizardsReborn.ARCANE_GOLD_SWORD.get()), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_iterator_enchantments"),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.ALL_DAMAGE_PROTECTION,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.OBSIDIAN), new ItemStack(Items.OBSIDIAN), new ItemStack(Items.OBSIDIAN)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.FIRE_PROTECTION,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.OBSIDIAN), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.BLAST_PROTECTION,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.OBSIDIAN), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.GUNPOWDER)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.PROJECTILE_PROTECTION,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.OBSIDIAN), new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.ARROW)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.FALL_PROTECTION,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.STRING), new ItemStack(Items.FEATHER), new ItemStack(Items.FEATHER)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.RESPIRATION,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.IRON_INGOT), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 6, 0, Enchantments.AQUA_AFFINITY,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.THORNS,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.CACTUS), new ItemStack(Items.CACTUS), new ItemStack(Items.CACTUS)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.DEPTH_STRIDER,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.SLIME_BALL), new ItemStack(Items.SLIME_BALL), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.FROST_WALKER,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.SLIME_BALL), new ItemStack(Items.SLIME_BALL), new ItemStack(Items.PACKED_ICE), new ItemStack(Items.PACKED_ICE)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.SHARPNESS,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.SMITE,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.BANE_OF_ARTHROPODS,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.SPIDER_EYE)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 6, 0, Enchantments.KNOCKBACK,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.PISTON), new ItemStack(Items.PISTON)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 6, 0, Enchantments.FIRE_ASPECT,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 7, 0, Enchantments.MOB_LOOTING,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.DIAMOND), new ItemStack(Items.EMERALD)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 6, 0, Enchantments.SWEEPING_EDGE,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.BONE), new ItemStack(Items.BONE), new ItemStack(Items.BONE)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.BLOCK_EFFICIENCY,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 10, 0, Enchantments.SILK_TOUCH,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.GOLD_BLOCK), new ItemStack(Items.DIAMOND)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.UNBREAKING,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 8, 0, Enchantments.BLOCK_FORTUNE,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.DIAMOND), new ItemStack(Items.EMERALD), new ItemStack(Items.EMERALD)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.POWER_ARROWS,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_ROD)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 6, 0, Enchantments.PUNCH_ARROWS,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.PISTON), new ItemStack(Items.PISTON)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 8, 0, Enchantments.FLAMING_ARROWS,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 10, 0, Enchantments.INFINITY_ARROWS,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.FISHING_LUCK,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.STRING), new ItemStack(Items.STRING), new ItemStack(Items.EMERALD), new ItemStack(Items.EMERALD)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.FISHING_SPEED,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.STRING), new ItemStack(Items.STRING), new ItemStack(Items.COD), new ItemStack(Items.COD)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.LOYALTY,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.STRING), new ItemStack(Items.STRING), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.IMPALING,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.PRISMARINE_CRYSTALS), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.RIPTIDE,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.PRISMARINE_CRYSTALS), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 10, 0, Enchantments.CHANNELING,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.HEART_OF_THE_SEA)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 10, 0, Enchantments.MULTISHOT,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.STICK), new ItemStack(Items.DISPENSER)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.QUICK_CHARGE,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.STICK), new ItemStack(Items.SLIME_BALL)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, Enchantments.PIERCING,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.STICK), new ItemStack(Items.QUARTZ)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 1, 10, Enchantments.VANISHING_CURSE,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.IRON_INGOT)
                ),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 1, 10, Enchantments.BINDING_CURSE,
                        new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.OBSIDIAN)
                )
        );

        ARCANUM_LENS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcanum_lens",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum_lens",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANUM_LENS_ITEM)
                ),
                new ArcaneIteratorPage(ARCANUM_LENS_ITEM, 0, 0, new ItemStack(WizardsReborn.ARCANUM_AMULET.get()),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_DUST_ITEM,
                        ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM
                )
        );

        WISSEN_KEYCHAIN = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_keychain",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_keychain",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_KEYCHAIN.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsReborn.WISSEN_KEYCHAIN.get()), 0, 0, new ItemStack(WizardsReborn.ARCANUM_AMULET.get()),
                        ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM,
                        ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM
                )
        );

        WISSEN_RING = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_ring",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_ring",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISSEN_RING.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsReborn.WISSEN_RING.get()), 0, 0, new ItemStack(WizardsReborn.ARCANUM_AMULET.get()),
                        ARCANUM_LENS_ITEM, ARCACITE_ITEM, ARCANUM_LENS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANUM_LENS_ITEM, ARCACITE_ITEM
                )
        );

        JEWELER_TABLE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.jeweler_table",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.jeweler_table",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.JEWELER_TABLE_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.JEWELER_TABLE_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()),
                        new ItemStack(Items.RED_WOOL), new ItemStack(Items.RED_WOOL), new ItemStack(Items.RED_WOOL),
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.WISSEN_ALTAR.get()), ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.faceted_crystals",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FACETED_EARTH_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FACETED_WATER_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FACETED_AIR_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FACETED_FIRE_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FACETED_VOID_CRYSTAL.get()))
                ),
                new JewelerTablePage(new ItemStack(WizardsReborn.FACETED_EARTH_CRYSTAL.get()), new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()), new ItemStack(WizardsReborn.ARCACITE_POLISHING_MIXTURE.get())),
                new JewelerTablePage(new ItemStack(WizardsReborn.FACETED_WATER_CRYSTAL.get()), new ItemStack(WizardsReborn.WATER_CRYSTAL.get()), new ItemStack(WizardsReborn.ARCACITE_POLISHING_MIXTURE.get())),
                new JewelerTablePage(new ItemStack(WizardsReborn.FACETED_AIR_CRYSTAL.get()), new ItemStack(WizardsReborn.AIR_CRYSTAL.get()), new ItemStack(WizardsReborn.ARCACITE_POLISHING_MIXTURE.get())),
                new JewelerTablePage(new ItemStack(WizardsReborn.FACETED_FIRE_CRYSTAL.get()), new ItemStack(WizardsReborn.FIRE_CRYSTAL.get()), new ItemStack(WizardsReborn.ARCACITE_POLISHING_MIXTURE.get())),
                new JewelerTablePage(new ItemStack(WizardsReborn.FACETED_VOID_CRYSTAL.get()), new ItemStack(WizardsReborn.VOID_CRYSTAL.get()), new ItemStack(WizardsReborn.ARCACITE_POLISHING_MIXTURE.get()))
        );

        MUSIC_DISC_REBORN = new Chapter(
                "wizards_reborn.arcanemicon.chapter.music_disc_reborn",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.music_disc_reborn",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.MUSIC_DISC_REBORN.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsReborn.MUSIC_DISC_REBORN.get()), 0, 0,
                        new ItemStack(Items.MUSIC_DISC_13), ARCANE_GOLD_INGOT_ITEM
                )
        );

        ARCANE_NATURE_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_nature_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.arcane_nature_index",
                        new IndexEntry(ARCANUM, ARCANUM_ITEM),
                        new IndexEntry(ARCANUM_DUST_TRANSMUTATION, ARCANUM_DUST_ITEM, RegisterKnowledges.ARCANUM_DUST),
                        new IndexEntry(ARCANE_WOOD, ARCANE_WOOD_PLANKS_ITEM, RegisterKnowledges.ARCANUM_DUST),
                        new IndexEntry(ARCANE_GOLD, ARCANE_GOLD_INGOT_ITEM, RegisterKnowledges.ARCANUM_DUST),
                        new IndexEntry(SCYTHES, new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()), RegisterKnowledges.ARCANE_GOLD),
                        new IndexEntry(TRINKETS, new ItemStack(WizardsReborn.ARCANUM_AMULET.get()), RegisterKnowledges.ARCANE_GOLD)
                ),
                new IndexPage(
                        new IndexEntry(ARCANE_WOOD_BOW, new ItemStack(WizardsReborn.ARCANE_WOOD_BOW.get()), RegisterKnowledges.ARCANE_GOLD),
                        new IndexEntry(BANNER_PATTERNS, new ItemStack(WizardsReborn.VIOLENCE_BANNER_PATTERN_ITEM.get()), RegisterKnowledges.ARCANUM_DUST),
                        new IndexEntry(WISSEN, new ItemStack(WizardsReborn.WISSEN_WAND.get()), RegisterKnowledges.ARCANUM_DUST),
                        new IndexEntry(WISSEN_TRANSLATOR, new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), RegisterKnowledges.ARCANE_GOLD),
                        new IndexEntry(ARCANE_PEDESTAL, ARCANE_PEDESTAL_ITEM, RegisterKnowledges.ARCANE_WOOD),
                        new IndexEntry(WISSEN_ALTAR, new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), RegisterKnowledges.ARCANE_GOLD),
                        new IndexEntry(WISSEN_CRYSTALLIZER, new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()), RegisterKnowledges.ARCANE_GOLD)
                ),
                new IndexPage(
                        new IndexEntry(ARCANE_WORKBENCH, new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()), RegisterKnowledges.ARCANE_GOLD),
                        new IndexEntry(ARCANE_LUMOS, new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), RegisterKnowledges.WISSEN_CRYSTALLIZER),
                        new IndexEntry(CRYSTALS, new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()), RegisterKnowledges.WISSEN_CRYSTALLIZER),
                        new IndexEntry(ARCANE_WAND, new ItemStack(WizardsReborn.ARCANE_WAND.get()), RegisterKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(AUTOMATION, new ItemStack(WizardsReborn.REDSTONE_SENSOR_ITEM.get()), RegisterKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(WISSEN_CELL, new ItemStack(WizardsReborn.WISSEN_CELL_ITEM.get()), RegisterKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(CRYSTAL_BAG, new ItemStack(WizardsReborn.CRYSTAL_BAG.get()), RegisterKnowledges.ALCHEMY_GLASS)
                ),
                new IndexPage(
                        new IndexEntry(TOTEM_OF_FLAMES, new ItemStack(WizardsReborn.TOTEM_OF_FLAMES_ITEM.get()), RegisterKnowledges.ALCHEMY_CALX),
                        new IndexEntry(EXPERIENCE_TOTEM, new ItemStack(WizardsReborn.EXPERIENCE_TOTEM_ITEM.get()), RegisterKnowledges.ALCHEMY_CALX),
                        new IndexEntry(TOTEM_OF_EXPERIENCE_ABSORPTION, new ItemStack(WizardsReborn.TOTEM_OF_EXPERIENCE_ABSORPTION_ITEM.get()), RegisterKnowledges.ARCACITE),
                        new IndexEntry(TOTEM_OF_DISENCHANT, new ItemStack(WizardsReborn.TOTEM_OF_DISENCHANT_ITEM.get()), RegisterKnowledges.ARCACITE),
                        new IndexEntry(ALTAR_OF_DROUGHT, new ItemStack(WizardsReborn.ALTAR_OF_DROUGHT_ITEM.get()), RegisterKnowledges.ARCACITE),
                        new IndexEntry(VOID_CRYSTAL, new ItemStack(WizardsReborn.VOID_CRYSTAL.get()), RegisterKnowledges.ARCACITE),
                        new IndexEntry(ARCANE_FORTRESS_ARMOR, new ItemStack(WizardsReborn.ARCANE_FORTRESS_CHESTPLATE.get()), RegisterKnowledges.ARCACITE)
                ),
                new IndexPage(
                        new IndexEntry(INVENTOR_WIZARD_ARMOR, new ItemStack(WizardsReborn.INVENTOR_WIZARD_HAT.get()), RegisterKnowledges.ARCACITE),
                        new IndexEntry(ARCANE_WOOD_CANE, new ItemStack(WizardsReborn.ARCANE_WOOD_CANE.get()), RegisterKnowledges.ARCACITE),
                        new IndexEntry(ARCANE_ITERATOR, new ItemStack(WizardsReborn.ARCANE_ITERATOR_ITEM.get()), RegisterKnowledges.ARCACITE),
                        new IndexEntry(ARCANUM_LENS, ARCANUM_LENS_ITEM, RegisterKnowledges.ARCANE_ITERATOR),
                        new IndexEntry(WISSEN_KEYCHAIN, new ItemStack(WizardsReborn.WISSEN_KEYCHAIN.get()), RegisterKnowledges.ARCANE_ITERATOR),
                        new IndexEntry(WISSEN_RING, new ItemStack(WizardsReborn.WISSEN_RING.get()), RegisterKnowledges.ARCANUM_LENS),
                        new IndexEntry(JEWELER_TABLE, new ItemStack(WizardsReborn.JEWELER_TABLE_ITEM.get()), RegisterKnowledges.ARCACITE_POLISHING_MIXTURE)
                ),
                new IndexPage(
                        new IndexEntry(MUSIC_DISC_REBORN, new ItemStack(WizardsReborn.MUSIC_DISC_REBORN.get()), RegisterKnowledges.ARCANE_ITERATOR)
                )
        );
    }

    public static void spellsInit() {
        EARTH_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.earth_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.earth_projectile", WizardsReborn.EARTH_PROJECTILE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.earth_projectile.char", WizardsReborn.EARTH_PROJECTILE_SPELL)
        );
        WATER_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.water_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_projectile", WizardsReborn.WATER_PROJECTILE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.water_projectile.char", WizardsReborn.WATER_PROJECTILE_SPELL)
        );
        AIR_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.air_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_projectile", WizardsReborn.AIR_PROJECTILE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.air_projectile.char", WizardsReborn.AIR_PROJECTILE_SPELL)
        );
        FIRE_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fire_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.fire_projectile", WizardsReborn.FIRE_PROJECTILE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.fire_projectile.char", WizardsReborn.FIRE_PROJECTILE_SPELL)
        );
        VOID_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.void_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.void_projectile", WizardsReborn.VOID_PROJECTILE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.void_projectile.char", WizardsReborn.VOID_PROJECTILE_SPELL)
        );
        FROST_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.frost_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.frost_projectile", WizardsReborn.FROST_PROJECTILE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.frost_projectile.char", WizardsReborn.FROST_PROJECTILE_SPELL)
        );
        HOLY_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.holy_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.holy_projectile", WizardsReborn.HOLY_PROJECTILE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.holy_projectile.char", WizardsReborn.HOLY_PROJECTILE_SPELL)
        );
        CURSE_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.curse_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.curse_projectile", WizardsReborn.CURSE_PROJECTILE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.curse_projectile.char", WizardsReborn.CURSE_PROJECTILE_SPELL)
        );

        EARTH_RAY = new Chapter(
                "wizards_reborn.arcanemicon.chapter.earth_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.earth_ray", WizardsReborn.EARTH_RAY_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.earth_ray.char", WizardsReborn.EARTH_RAY_SPELL)
        );
        WATER_RAY = new Chapter(
                "wizards_reborn.arcanemicon.chapter.water_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_ray", WizardsReborn.WATER_RAY_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.water_ray.char", WizardsReborn.WATER_RAY_SPELL)
        );
        AIR_RAY = new Chapter(
                "wizards_reborn.arcanemicon.chapter.air_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_ray", WizardsReborn.AIR_RAY_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.air_ray.char", WizardsReborn.AIR_RAY_SPELL)
        );
        FIRE_RAY = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fire_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.fire_ray", WizardsReborn.FIRE_RAY_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.fire_ray.char", WizardsReborn.FIRE_RAY_SPELL)
        );
        VOID_RAY = new Chapter(
                "wizards_reborn.arcanemicon.chapter.void_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.void_ray", WizardsReborn.VOID_RAY_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.void_ray.char", WizardsReborn.VOID_RAY_SPELL)
        );
        FROST_RAY = new Chapter(
                "wizards_reborn.arcanemicon.chapter.frost_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.frost_ray", WizardsReborn.FROST_RAY_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.frost_ray.char", WizardsReborn.FROST_RAY_SPELL)
        );
        HOLY_RAY = new Chapter(
                "wizards_reborn.arcanemicon.chapter.holy_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.holy_ray", WizardsReborn.HOLY_RAY_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.holy_ray.char", WizardsReborn.HOLY_RAY_SPELL)
        );
        CURSE_RAY = new Chapter(
                "wizards_reborn.arcanemicon.chapter.curse_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.curse_ray", WizardsReborn.CURSE_RAY_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.curse_ray.char", WizardsReborn.CURSE_RAY_SPELL)
        );

        EARTH_CHARGE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.earth_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.earth_charge", WizardsReborn.EARTH_CHARGE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.earth_charge.char", WizardsReborn.EARTH_CHARGE_SPELL)
        );
        WATER_CHARGE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.water_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_charge", WizardsReborn.WATER_CHARGE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.water_charge.char", WizardsReborn.WATER_CHARGE_SPELL)
        );
        AIR_CHARGE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.air_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_charge", WizardsReborn.AIR_CHARGE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.air_charge.char", WizardsReborn.AIR_CHARGE_SPELL)
        );
        FIRE_CHARGE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fire_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.fire_charge", WizardsReborn.FIRE_CHARGE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.fire_charge.char", WizardsReborn.FIRE_CHARGE_SPELL)
        );
        VOID_CHARGE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.void_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.void_charge", WizardsReborn.VOID_CHARGE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.void_charge.char", WizardsReborn.VOID_CHARGE_SPELL)
        );
        FROST_CHARGE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.frost_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.frost_charge", WizardsReborn.FROST_CHARGE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.frost_charge.char", WizardsReborn.FROST_CHARGE_SPELL)
        );
        HOLY_CHARGE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.holy_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.holy_charge", WizardsReborn.HOLY_CHARGE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.holy_charge.char", WizardsReborn.HOLY_CHARGE_SPELL)
        );
        CURSE_CHARGE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.curse_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.curse_charge", WizardsReborn.CURSE_CHARGE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.curse_charge.char", WizardsReborn.CURSE_CHARGE_SPELL)
        );

        HEART_OF_NATURE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.heart_of_nature",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.heart_of_nature", WizardsReborn.HEART_OF_NATURE_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.heart_of_nature.char", WizardsReborn.HEART_OF_NATURE_SPELL)
        );
        WATER_BREATHING = new Chapter(
                "wizards_reborn.arcanemicon.chapter.water_breathing",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_breathing", WizardsReborn.WATER_BREATHING_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.water_breathing.char", WizardsReborn.WATER_BREATHING_SPELL)
        );
        AIR_FLOW = new Chapter(
                "wizards_reborn.arcanemicon.chapter.air_flow",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_flow", WizardsReborn.AIR_FLOW_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.air_flow.char", WizardsReborn.AIR_FLOW_SPELL)
        );
        FIRE_SHIELD = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fire_shield",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.fire_shield", WizardsReborn.FIRE_SHIELD_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.fire_shield.char", WizardsReborn.FIRE_SHIELD_SPELL)
        );

        MAGIC_SPROUT = new Chapter(
                "wizards_reborn.arcanemicon.chapter.magic_sprout",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.magic_sprout", WizardsReborn.MAGIC_SPROUT_SPELL),
                new SpellCharPage("wizards_reborn.arcanemicon.page.magic_sprout.char", WizardsReborn.MAGIC_SPROUT_SPELL)
        );

        LUNAM_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.lunam_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.lunam_monogram", WizardsReborn.LUNAM_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.LUNAM_MONOGRAM)
        );
        VITA_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.vita_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.vita_monogram", WizardsReborn.VITA_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.VITA_MONOGRAM)
        );
        SOLEM_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.solem_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.solem_monogram", WizardsReborn.SOLEM_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.SOLEM_MONOGRAM)
        );
        MORS_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.mors_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.mors_monogram", WizardsReborn.MORS_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.MORS_MONOGRAM)
        );
        MIRACULUM_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.miraculum_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.miraculum_monogram", WizardsReborn.MIRACULUM_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.MIRACULUM_MONOGRAM)
        );
        TEMPUS_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.tempus_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.tempus_monogram", WizardsReborn.TEMPUS_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEMPUS_MONOGRAM)
        );
        STATERA_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.statera_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.statera_monogram", WizardsReborn.STATERA_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.STATERA_MONOGRAM)
        );
        ECLIPSIS_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.eclipsis_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.eclipsis_monogram", WizardsReborn.ECLIPSIS_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.ECLIPSIS_MONOGRAM)
        );
        SICCITAS_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.siccitas_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.siccitas_monogram", WizardsReborn.SICCITAS_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.SICCITAS_MONOGRAM)
        );
        SOLSTITIUM_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.solstitium_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.solstitium_monogram", WizardsReborn.SOLSTITIUM_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.SOLSTITIUM_MONOGRAM)
        );
        FAMES_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fames_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.fames_monogram", WizardsReborn.FAMES_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.FAMES_MONOGRAM)
        );
        RENAISSANCE_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.renaissance_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.renaissance_monogram", WizardsReborn.RENAISSANCE_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.RENAISSANCE_MONOGRAM)
        );
        BELLUM_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.bellum_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.bellum_monogram", WizardsReborn.BELLUM_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.BELLUM_MONOGRAM)
        );
        LUX_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.lux_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.lux_monogram", WizardsReborn.LUX_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.LUX_MONOGRAM)
        );
        KARA_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.kara_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.kara_monogram", WizardsReborn.KARA_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.KARA_MONOGRAM)
        );
        DEGRADATIO_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.degradatio_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.degradatio_monogram", WizardsReborn.DEGRADATIO_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.DEGRADATIO_MONOGRAM)
        );
        PRAEDICTIONEM_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.praedictionem_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.praedictionem_monogram", WizardsReborn.PRAEDICTIONEM_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.PRAEDICTIONEM_MONOGRAM)
        );
        EVOLUTIONIS_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.evolutionis_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.evolutionis_monogram", WizardsReborn.EVOLUTIONIS_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.EVOLUTIONIS_MONOGRAM)
        );
        TENEBRIS_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.tenebris_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.tenebris_monogram", WizardsReborn.TENEBRIS_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TENEBRIS_MONOGRAM)
        );
        UNIVERSUM_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.universum_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.universum_monogram", WizardsReborn.UNIVERSUM_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.UNIVERSUM_MONOGRAM)
        );

        ALL_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.all_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.all_spells",
                        new SpellIndexEntry(EARTH_PROJECTILE, WizardsReborn.EARTH_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_PROJECTILE, WizardsReborn.WATER_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_PROJECTILE, WizardsReborn.AIR_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FIRE_PROJECTILE, WizardsReborn.FIRE_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(VOID_PROJECTILE, WizardsReborn.VOID_PROJECTILE_SPELL, RegisterKnowledges.VOID_CRYSTAL),
                        new SpellIndexEntry(FROST_PROJECTILE, WizardsReborn.FROST_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(HOLY_PROJECTILE, WizardsReborn.HOLY_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(CURSE_PROJECTILE, WizardsReborn.CURSE_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(EARTH_RAY, WizardsReborn.EARTH_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_RAY, WizardsReborn.WATER_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_RAY, WizardsReborn.AIR_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FIRE_RAY, WizardsReborn.FIRE_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(VOID_RAY, WizardsReborn.VOID_RAY_SPELL, RegisterKnowledges.VOID_CRYSTAL)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(FROST_RAY, WizardsReborn.FROST_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_RAY, WizardsReborn.HOLY_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(CURSE_RAY, WizardsReborn.CURSE_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(EARTH_CHARGE, WizardsReborn.EARTH_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WATER_CHARGE, WizardsReborn.WATER_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(AIR_CHARGE, WizardsReborn.AIR_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FIRE_CHARGE, WizardsReborn.FIRE_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(VOID_CHARGE, WizardsReborn.VOID_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FROST_CHARGE, WizardsReborn.FROST_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(HOLY_CHARGE, WizardsReborn.HOLY_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(CURSE_CHARGE, WizardsReborn.CURSE_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(HEART_OF_NATURE, WizardsReborn.HEART_OF_NATURE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WATER_BREATHING, WizardsReborn.WATER_BREATHING_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(AIR_FLOW, WizardsReborn.AIR_FLOW_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FIRE_SHIELD, WizardsReborn.FIRE_SHIELD_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(MAGIC_SPROUT, WizardsReborn.MAGIC_SPROUT_SPELL, RegisterKnowledges.FACETED_CRYSTALS)
                )
        );

        EARTH_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.earth_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.earth_spells",
                        new SpellIndexEntry(EARTH_PROJECTILE, WizardsReborn.EARTH_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_PROJECTILE, WizardsReborn.HOLY_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(EARTH_RAY, WizardsReborn.EARTH_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_RAY, WizardsReborn.HOLY_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(EARTH_CHARGE, WizardsReborn.EARTH_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(HOLY_CHARGE, WizardsReborn.HOLY_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(HEART_OF_NATURE, WizardsReborn.HEART_OF_NATURE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(MAGIC_SPROUT, WizardsReborn.MAGIC_SPROUT_SPELL, RegisterKnowledges.FACETED_CRYSTALS)
                )
        );

        WATER_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.water_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.water_spells",
                        new SpellIndexEntry(WATER_PROJECTILE, WizardsReborn.WATER_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FROST_PROJECTILE, WizardsReborn.FROST_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_RAY, WizardsReborn.WATER_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FROST_RAY, WizardsReborn.FROST_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(WATER_CHARGE, WizardsReborn.WATER_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FROST_CHARGE, WizardsReborn.FROST_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WATER_BREATHING, WizardsReborn.WATER_BREATHING_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(MAGIC_SPROUT, WizardsReborn.MAGIC_SPROUT_SPELL, RegisterKnowledges.FACETED_CRYSTALS)
                )
        );

        AIR_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.air_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.air_spells",
                        new SpellIndexEntry(AIR_PROJECTILE, WizardsReborn.AIR_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_PROJECTILE, WizardsReborn.HOLY_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_RAY, WizardsReborn.AIR_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_RAY, WizardsReborn.HOLY_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(AIR_CHARGE, WizardsReborn.AIR_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(HOLY_CHARGE, WizardsReborn.HOLY_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(AIR_FLOW, WizardsReborn.AIR_FLOW_SPELL, RegisterKnowledges.FACETED_CRYSTALS)
                )
        );

        FIRE_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fire_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.fire_spells",
                        new SpellIndexEntry(FIRE_PROJECTILE, WizardsReborn.FIRE_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(CURSE_PROJECTILE, WizardsReborn.CURSE_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FIRE_RAY, WizardsReborn.FIRE_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(CURSE_RAY, WizardsReborn.CURSE_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(FIRE_CHARGE, WizardsReborn.FIRE_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(CURSE_CHARGE, WizardsReborn.CURSE_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FIRE_SHIELD, WizardsReborn.FIRE_SHIELD_SPELL, RegisterKnowledges.FACETED_CRYSTALS)
                )
        );

        VOID_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.void_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.void_spells",
                        new SpellIndexEntry(VOID_PROJECTILE, WizardsReborn.VOID_PROJECTILE_SPELL, RegisterKnowledges.VOID_CRYSTAL),
                        new SpellIndexEntry(CURSE_PROJECTILE, WizardsReborn.CURSE_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(VOID_RAY, WizardsReborn.VOID_RAY_SPELL, RegisterKnowledges.VOID_CRYSTAL),
                        new SpellIndexEntry(CURSE_RAY, WizardsReborn.CURSE_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(VOID_CHARGE, WizardsReborn.VOID_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(CURSE_CHARGE, WizardsReborn.CURSE_CHARGE_SPELL, RegisterKnowledges.FACETED_CRYSTALS)
                )
        );

        RESEARCH_MAIN = new ResearchPage(true);
        RESEARCH_LIST = new ResearchPage(false);

        RESEARCH = new Chapter(
                "wizards_reborn.arcanemicon.chapter.research",
                RESEARCH_MAIN,
                RESEARCH_LIST
        );

        RESEARCHES = new Chapter(
                "wizards_reborn.arcanemicon.chapter.researches",
                new TitlePage("wizards_reborn.arcanemicon.page.researches.0"),
                new TextPage("wizards_reborn.arcanemicon.page.researches.1")
        );

        MONOGRAMS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.monograms",
                new TitledMonogramIndexPage("wizards_reborn.arcanemicon.page.monograms",
                        new MonogramIndexEntry(LUNAM_MONOGRAM, WizardsReborn.LUNAM_MONOGRAM),
                        new MonogramIndexEntry(VITA_MONOGRAM, WizardsReborn.VITA_MONOGRAM),
                        new MonogramIndexEntry(SOLEM_MONOGRAM, WizardsReborn.SOLEM_MONOGRAM),
                        new MonogramIndexEntry(MORS_MONOGRAM, WizardsReborn.MORS_MONOGRAM),
                        new MonogramIndexEntry(MIRACULUM_MONOGRAM, WizardsReborn.MIRACULUM_MONOGRAM),
                        new MonogramIndexEntry(TEMPUS_MONOGRAM, WizardsReborn.TEMPUS_MONOGRAM)
                ),
                new MonogramIndexPage(
                        new MonogramIndexEntry(STATERA_MONOGRAM, WizardsReborn.STATERA_MONOGRAM),
                        new MonogramIndexEntry(ECLIPSIS_MONOGRAM, WizardsReborn.ECLIPSIS_MONOGRAM),
                        new MonogramIndexEntry(SICCITAS_MONOGRAM, WizardsReborn.SICCITAS_MONOGRAM),
                        new MonogramIndexEntry(SOLSTITIUM_MONOGRAM, WizardsReborn.SOLSTITIUM_MONOGRAM),
                        new MonogramIndexEntry(FAMES_MONOGRAM, WizardsReborn.FAMES_MONOGRAM),
                        new MonogramIndexEntry(RENAISSANCE_MONOGRAM, WizardsReborn.RENAISSANCE_MONOGRAM),
                        new MonogramIndexEntry(BELLUM_MONOGRAM, WizardsReborn.BELLUM_MONOGRAM)
                ),
                new MonogramIndexPage(
                        new MonogramIndexEntry(LUX_MONOGRAM, WizardsReborn.LUX_MONOGRAM),
                        new MonogramIndexEntry(KARA_MONOGRAM, WizardsReborn.KARA_MONOGRAM),
                        new MonogramIndexEntry(DEGRADATIO_MONOGRAM, WizardsReborn.DEGRADATIO_MONOGRAM),
                        new MonogramIndexEntry(PRAEDICTIONEM_MONOGRAM, WizardsReborn.PRAEDICTIONEM_MONOGRAM),
                        new MonogramIndexEntry(EVOLUTIONIS_MONOGRAM, WizardsReborn.EVOLUTIONIS_MONOGRAM),
                        new MonogramIndexEntry(TENEBRIS_MONOGRAM, WizardsReborn.TENEBRIS_MONOGRAM),
                        new MonogramIndexEntry(UNIVERSUM_MONOGRAM, WizardsReborn.UNIVERSUM_MONOGRAM)
                )
        );

        SPELLS_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.spells_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.spells_index",
                        new IndexEntry(ALL_SPELLS, new ItemStack(WizardsReborn.ARCANE_WAND.get()), RegisterKnowledges.ARCANE_WAND),
                        new IndexEntry(EARTH_SPELLS, new ItemStack(WizardsReborn.FACETED_EARTH_CRYSTAL.get()), RegisterKnowledges.ARCANE_WAND),
                        new IndexEntry(WATER_SPELLS, new ItemStack(WizardsReborn.FACETED_WATER_CRYSTAL.get()), RegisterKnowledges.ARCANE_WAND),
                        new IndexEntry(AIR_SPELLS, new ItemStack(WizardsReborn.FACETED_AIR_CRYSTAL.get()), RegisterKnowledges.ARCANE_WAND),
                        new IndexEntry(FIRE_SPELLS, new ItemStack(WizardsReborn.FACETED_FIRE_CRYSTAL.get()), RegisterKnowledges.ARCANE_WAND),
                        new IndexEntry(VOID_SPELLS, new ItemStack(WizardsReborn.FACETED_VOID_CRYSTAL.get()), RegisterKnowledges.VOID_CRYSTAL)
                ),
                new IndexPage(
                        new IndexEntry(RESEARCHES, new ItemStack(WizardsReborn.ARCANEMICON.get()), RegisterKnowledges.ARCANE_WAND),
                        new IndexEntry(MONOGRAMS, new ItemStack(WizardsReborn.ARCANEMICON.get()), RegisterKnowledges.ARCANE_WAND)
                )
        );
    }

    public static void crystalRitualsInit() {
        LIGHT_RAYS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.light_rays",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.light_rays.0",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.LIGHT_EMITTER_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, RUNIC_PEDESTAL_ITEM)
                ),
                new TextPage("wizards_reborn.arcanemicon.page.light_rays.1"),
                new ImagePage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/light_rays_image_page.png"))
        );

        LIGHT_EMITTER = new Chapter(
                "wizards_reborn.arcanemicon.chapter.light_emitter",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.light_emitter",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.LIGHT_EMITTER_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.LIGHT_EMITTER_ITEM.get()),
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,  ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, WISESTONE_PEDESTAL_ITEM,  ARCANE_GOLD_NUGGET_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_ITEM,
                        ARCANUM_LENS_ITEM
                )
        );

        LIGHT_TRANSFER_LENS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.light_transfer_lens",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.light_transfer_lens",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get(), 2),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,  EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_PLANKS_ITEM,  ARCANE_GOLD_NUGGET_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_SLAB_ITEM,
                        ARCANUM_LENS_ITEM
                )
        );

        RUNIC_PEDESTAL = new Chapter(
                "wizards_reborn.arcanemicon.chapter.runic_pedestal",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.runic_pedestal",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, RUNIC_PEDESTAL_ITEM)
                ),
                new ArcaneWorkbenchPage(RUNIC_PEDESTAL_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_ITEM,
                        ARCANUM_LENS_ITEM, WISESTONE_PEDESTAL_ITEM,  ARCANUM_LENS_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_plate",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISESTONE_PLATE.get())),
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsReborn.RUNIC_WISESTONE_PLATE.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsReborn.WISESTONE_PLATE.get()), 0, 0, ARCACITE_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                )
        );

        Map<CrystalRitual, ItemStack> crystalRituals = new HashMap<CrystalRitual, ItemStack>();

        for (CrystalRitual ritual : CrystalRituals.getCrystalRituals()) {
            ItemStack stack = new ItemStack(WizardsReborn.RUNIC_WISESTONE_PLATE.get());
            CrystalRitualUtils.setCrystalRitual(stack, ritual);
            crystalRituals.put(ritual, stack);
        }

        CRYSTALS_RITUALS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.crystal_rituals",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystal_rituals.0",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.crystal_rituals.1")
        );

        ARTIFICIAL_FERTILITY = new Chapter(
                "wizards_reborn.arcanemicon.chapter.artificial_fertility",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.artificial_fertility",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, crystalRituals.get(WizardsReborn.ARTIFICIAL_FERTILITY_CRYSTAL_RITUAL))
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsReborn.ARTIFICIAL_FERTILITY_CRYSTAL_RITUAL), 0, 0, new ItemStack(WizardsReborn.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsReborn.FLOWER_FERTILIZER.get()), new ItemStack(WizardsReborn.FLOWER_FERTILIZER.get()), new ItemStack(WizardsReborn.FLOWER_FERTILIZER.get()),
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, new ItemStack(Items.HAY_BLOCK), new ItemStack(Items.WATER_BUCKET)
                )
        );

        RITUAL_BREEDING = new Chapter(
                "wizards_reborn.arcanemicon.chapter.ritual_breeding",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.ritual_breeding",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, crystalRituals.get(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL))
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL), 0, 0, new ItemStack(WizardsReborn.WISESTONE_PLATE.get()),
                        NATURAL_CALX_ITEM, SCORCHED_CALX_ITEM, SCORCHED_CALX_ITEM, SCORCHED_CALX_ITEM,
                        new ItemStack(Items.DIRT), new ItemStack(Items.BREAD), new ItemStack(Items.GOLDEN_CARROT)
                ),
                new CrystalRitualPage(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL,
                        new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.APPLE), new ItemStack(Items.APPLE), new ItemStack(Items.APPLE)
                ),
                new CrystalRitualPage(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL,
                        new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT)
                ),
                new CrystalRitualPage(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL,
                        new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO)
                ),
                new CrystalRitualPage(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL,
                        new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT)
                ),
                new CrystalRitualPage(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL,
                        new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.APPLE)
                ),
                new CrystalRitualPage(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL,
                        new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.CARROT)
                ),
                new CrystalRitualPage(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL,
                        new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.POTATO)
                ),
                new CrystalRitualPage(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL,
                        new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.BEETROOT)
                )
        );

        CRYSTAL_GROWTH_ACCELERATION = new Chapter(
                "wizards_reborn.arcanemicon.chapter.crystal_growth_acceleration",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystal_growth_acceleration",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, crystalRituals.get(WizardsReborn.CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL))
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsReborn.CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL), 0, 0, new ItemStack(WizardsReborn.WISESTONE_PLATE.get()),
                        ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM,
                        new ItemStack(Items.REDSTONE_BLOCK), new ItemStack(Items.REDSTONE_BLOCK),
                        new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()), new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()), new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get())
                )
        );

        CRYSTAL_INFUSION = new Chapter(
                "wizards_reborn.arcanemicon.chapter.crystal_infusion",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystal_infusion",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, crystalRituals.get(WizardsReborn.CRYSTAL_INFUSION_CRYSTAL_RITUAL))
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsReborn.CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL), 0, 0, new ItemStack(WizardsReborn.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()),
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsReborn.CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL), 0, 0, new ItemStack(WizardsReborn.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsReborn.WATER_CRYSTAL.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsReborn.CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL), 0, 0, new ItemStack(WizardsReborn.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsReborn.AIR_CRYSTAL.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsReborn.CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL), 0, 0, new ItemStack(WizardsReborn.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsReborn.FIRE_CRYSTAL.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsReborn.CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL), 0, 0, new ItemStack(WizardsReborn.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsReborn.VOID_CRYSTAL.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                )
        );

        ARCANUM_SEED = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcanum_seed",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum_seed",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANUM_SEED.get()))
                ),
                new CrystalInfusionPage(new ItemStack(WizardsReborn.ARCANUM_SEED.get()), new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM,
                        ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM,
                        new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS)
                ),
                new ArcanumDustTransmutationPage(ARCANUM_ITEM, new ItemStack(WizardsReborn.ARCANUM_SEED.get()))
        );

        INNOCENT_WOOD = new Chapter(
                "wizards_reborn.arcanemicon.chapter.innocent_wood",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.petals_of_innocence",
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.PETALS_OF_INNOCENCE_ITEM.get()))
                ),
                new CrystalInfusionPage(new ItemStack(WizardsReborn.PETALS_OF_INNOCENCE_ITEM.get()), new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()),
                        new ItemStack(Items.PINK_PETALS), ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM,
                        new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.PETALS.get()),
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_sapling",
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.INNOCENT_WOOD_SAPLING_ITEM.get()))
                ),
                new CrystalInfusionPage(new ItemStack(WizardsReborn.INNOCENT_WOOD_SAPLING_ITEM.get()), new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()),
                        new ItemStack(Items.CHERRY_SAPLING), ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM,
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood",
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_ITEM.get())),
                        new BlockEntry(INNOCENT_WOOD_PLANKS_ITEM),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_STAIRS_ITEM.get())),
                        new BlockEntry(INNOCENT_WOOD_SLAB_ITEM),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_FENCE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_FENCE_GATE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_DOOR_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_TRAPDOOR_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_PRESSURE_PLATE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_BUTTON_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_SIGN_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_HANGING_SIGN_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_BOAT_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_CHEST_BOAT_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.INNOCENT_WOOD_BRANCH.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_ITEM.get()),
                        new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_ITEM.get()),
                        new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_PLANKS_ITEM.get(), 4), new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_STAIRS_ITEM.get(), 4),
                        INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_SLAB_ITEM.get(), 6),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_FENCE_ITEM.get(), 3),
                        INNOCENT_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK), INNOCENT_WOOD_PLANKS_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK), INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_FENCE_GATE_ITEM.get()),
                        new ItemStack(Items.STICK), INNOCENT_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK),
                        new ItemStack(Items.STICK), INNOCENT_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_DOOR_ITEM.get(), 3),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_TRAPDOOR_ITEM.get(), 2),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_PRESSURE_PLATE_ITEM.get()),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_BUTTON_ITEM.get()), INNOCENT_WOOD_PLANKS_ITEM),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_SIGN_ITEM.get(), 3),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        EMPTY_ITEM, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_HANGING_SIGN_ITEM.get(), 6),
                        new ItemStack(Items.CHAIN), EMPTY_ITEM, new ItemStack(Items.CHAIN),
                        new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG.get()),
                        new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_BOAT_ITEM.get()),
                        INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_CHEST_BOAT_ITEM.get()), new ItemStack(Items.CHEST), new ItemStack(WizardsReborn.INNOCENT_WOOD_BOAT_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_BRANCH.get()),
                        new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_BRANCH.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_mortar",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_MORTAR.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_WOOD_MORTAR.get()),
                        new ItemStack(WizardsReborn.INNOCENT_WOOD_PLANKS.get()), new ItemStack(WizardsReborn.INNOCENT_WOOD_BRANCH.get()), new ItemStack(WizardsReborn.INNOCENT_WOOD_PLANKS.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.INNOCENT_WOOD_PLANKS.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_pedestal",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, INNOCENT_PEDESTAL_ITEM)
                ),
                new CraftingTablePage(INNOCENT_PEDESTAL_ITEM,
                        INNOCENT_WOOD_SLAB_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_SLAB_ITEM,
                        EMPTY_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_SLAB_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_hovering_tome_stand",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.INNOCENT_HOVERING_TOME_STAND.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.INNOCENT_HOVERING_TOME_STAND.get()),
                        INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANEMICON.get())
                )
        );

        CRYSTAL_RITUALS_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.crystal_rituals_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.crystal_rituals_index",
                        new IndexEntry(LIGHT_RAYS, new ItemStack(WizardsReborn.WISSEN_WAND.get()), RegisterKnowledges.ARCANUM_LENS),
                        new IndexEntry(LIGHT_EMITTER, new ItemStack(WizardsReborn.LIGHT_EMITTER_ITEM.get()), RegisterKnowledges.ARCANUM_LENS),
                        new IndexEntry(LIGHT_TRANSFER_LENS, new ItemStack(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM.get()), RegisterKnowledges.ARCANUM_LENS),
                        new IndexEntry(RUNIC_PEDESTAL, RUNIC_PEDESTAL_ITEM, RegisterKnowledges.ARCANUM_LENS),
                        new IndexEntry(CRYSTALS_RITUALS, new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()), RegisterKnowledges.RUNIC_PEDESTAL),
                        new IndexEntry(ARTIFICIAL_FERTILITY, crystalRituals.get(WizardsReborn.ARTIFICIAL_FERTILITY_CRYSTAL_RITUAL), RegisterKnowledges.RUNIC_PEDESTAL)
                ),
                new IndexPage(
                        new IndexEntry(RITUAL_BREEDING, crystalRituals.get(WizardsReborn.RITUAL_BREEDING_CRYSTAL_RITUAL), RegisterKnowledges.RUNIC_PEDESTAL),
                        new IndexEntry(CRYSTAL_GROWTH_ACCELERATION, crystalRituals.get(WizardsReborn.CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL), RegisterKnowledges.RUNIC_PEDESTAL),
                        new IndexEntry(CRYSTAL_INFUSION, crystalRituals.get(WizardsReborn.CRYSTAL_INFUSION_CRYSTAL_RITUAL), RegisterKnowledges.RUNIC_PEDESTAL),
                        new IndexEntry(ARCANUM_SEED, new ItemStack(WizardsReborn.ARCANUM_SEED.get()), RegisterKnowledges.CRYSTAL_INFUSION),
                        new IndexEntry(INNOCENT_WOOD, INNOCENT_WOOD_PLANKS_ITEM, RegisterKnowledges.CRYSTAL_INFUSION)
                )
        );
    }

    public static void alchemyInit() {
        MOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.mor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.mor.0",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.MOR_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.MOR_BLOCK_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ELDER_MOR_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ELDER_MOR_BLOCK_ITEM.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.mor.1")
        );

        MORTAR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.mortar",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.mortar",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_MORTAR.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.petals",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.PETALS.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.flower_fertilizer",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FLOWER_FERTILIZER.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.ground_mushrooms",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.GROUND_BROWN_MUSHROOM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.GROUND_RED_MUSHROOM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.GROUND_CRIMSON_FUNGUS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.GROUND_WARPED_FUNGUS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.GROUND_MOR.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.GROUND_ELDER_MOR.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_MORTAR.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get())
                ),
                new MortarPage(new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(Items.DANDELION)),
                new CraftingTablePage(new ItemStack(WizardsReborn.FLOWER_FERTILIZER.get()), new ItemStack(Items.BONE_MEAL), new ItemStack(WizardsReborn.PETALS.get()), EMPTY_ITEM, new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.PETALS.get())),
                new MortarPage(new ItemStack(WizardsReborn.GROUND_BROWN_MUSHROOM.get()), new ItemStack(Items.BROWN_MUSHROOM)),
                new MortarPage(new ItemStack(WizardsReborn.GROUND_RED_MUSHROOM.get()), new ItemStack(Items.RED_MUSHROOM)),
                new MortarPage(new ItemStack(WizardsReborn.GROUND_CRIMSON_FUNGUS.get()), new ItemStack(Items.CRIMSON_FUNGUS)),
                new MortarPage(new ItemStack(WizardsReborn.GROUND_WARPED_FUNGUS.get()), new ItemStack(Items.WARPED_FUNGUS)),
                new MortarPage(new ItemStack(WizardsReborn.GROUND_MOR.get()), new ItemStack(WizardsReborn.MOR_ITEM.get())),
                new MortarPage(new ItemStack(WizardsReborn.GROUND_ELDER_MOR.get()), new ItemStack(WizardsReborn.ELDER_MOR_ITEM.get()))
        );

        ARCANE_LINEN = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_linen",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_linen",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_LINEN_SEEDS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_LINEN_HAY_ITEM.get()))
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.ARCANE_LINEN_SEEDS.get()),
                        new ItemStack(Items.WHEAT_SEEDS), ARCANUM_ITEM, ARCANUM_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT)
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_LINEN_HAY_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get(), 9), new ItemStack(WizardsReborn.ARCANE_LINEN_HAY_ITEM.get()))
        );

        MUSHROOM_CAPS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.mushroom_caps",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.mushroom_caps",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.BROWN_MUSHROOM_CAP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.RED_MUSHROOM_CAP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.CRIMSON_FUNGUS_CAP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WARPED_FUNGUS_CAP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.MOR_CAP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ELDER_MOR_CAP.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.BROWN_MUSHROOM_CAP.get()),
                        new ItemStack(Items.BROWN_MUSHROOM_BLOCK), new ItemStack(Items.BROWN_MUSHROOM_BLOCK), new ItemStack(Items.BROWN_MUSHROOM_BLOCK),
                        new ItemStack(Items.BROWN_MUSHROOM_BLOCK), new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.BROWN_MUSHROOM_BLOCK),
                        EMPTY_ITEM, new ItemStack(Items.MUSHROOM_STEM)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.RED_MUSHROOM_CAP.get()),
                        new ItemStack(Items.RED_MUSHROOM_BLOCK), new ItemStack(Items.RED_MUSHROOM_BLOCK), new ItemStack(Items.RED_MUSHROOM_BLOCK),
                        new ItemStack(Items.RED_MUSHROOM_BLOCK), new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.RED_MUSHROOM_BLOCK),
                        EMPTY_ITEM, new ItemStack(Items.MUSHROOM_STEM)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.CRIMSON_FUNGUS_CAP.get()),
                        new ItemStack(Items.NETHER_WART_BLOCK), new ItemStack(Items.NETHER_WART_BLOCK), new ItemStack(Items.NETHER_WART_BLOCK),
                        new ItemStack(Items.NETHER_WART_BLOCK), new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.NETHER_WART_BLOCK),
                        EMPTY_ITEM, new ItemStack(Items.CRIMSON_STEM)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WARPED_FUNGUS_CAP.get()),
                        new ItemStack(Items.WARPED_WART_BLOCK), new ItemStack(Items.WARPED_WART_BLOCK), new ItemStack(Items.WARPED_WART_BLOCK),
                        new ItemStack(Items.WARPED_WART_BLOCK), new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.WARPED_WART_BLOCK),
                        EMPTY_ITEM, new ItemStack(Items.WARPED_STEM)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.MOR_CAP.get()),
                        new ItemStack(WizardsReborn.MOR_BLOCK_ITEM.get()), new ItemStack(WizardsReborn.MOR_BLOCK_ITEM.get()), new ItemStack(WizardsReborn.MOR_BLOCK_ITEM.get()),
                        new ItemStack(WizardsReborn.MOR_BLOCK_ITEM.get()), new ItemStack(Items.LEATHER_HELMET), new ItemStack(WizardsReborn.MOR_BLOCK_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(Items.MUSHROOM_STEM)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ELDER_MOR_CAP.get()),
                        new ItemStack(WizardsReborn.ELDER_MOR_BLOCK_ITEM.get()), new ItemStack(WizardsReborn.ELDER_MOR_BLOCK_ITEM.get()), new ItemStack(WizardsReborn.ELDER_MOR_BLOCK_ITEM.get()),
                        new ItemStack(WizardsReborn.ELDER_MOR_BLOCK_ITEM.get()), new ItemStack(Items.LEATHER_HELMET), new ItemStack(WizardsReborn.ELDER_MOR_BLOCK_ITEM.get()),
                        EMPTY_ITEM, new ItemStack(Items.MUSHROOM_STEM)
                )
        );

        WISESTONE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wisestone",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISESTONE_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISESTONE_ITEM.get()),
                        new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE),
                        new ItemStack(Items.COBBLESTONE), ARCANUM_ITEM, new ItemStack(Items.COBBLESTONE),
                        new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISESTONE_ITEM.get()),
                        new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE),
                        new ItemStack(Items.COBBLED_DEEPSLATE), ARCANUM_ITEM, new ItemStack(Items.COBBLED_DEEPSLATE),
                        new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISESTONE_ITEM.get()),
                        new ItemStack(Items.BLACKSTONE), new ItemStack(Items.BLACKSTONE), new ItemStack(Items.BLACKSTONE),
                        new ItemStack(Items.BLACKSTONE), ARCANUM_ITEM, new ItemStack(Items.BLACKSTONE),
                        new ItemStack(Items.BLACKSTONE), new ItemStack(Items.BLACKSTONE), new ItemStack(Items.BLACKSTONE)
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.decorative_wisestone",
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_STAIRS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_SLAB_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_WALL_ITEM.get())),
                        new BlockEntry(POLISHED_WISESTONE_ITEM),
                        new BlockEntry(new ItemStack(WizardsReborn.POLISHED_WISESTONE_STAIRS_ITEM.get())),
                        new BlockEntry(POLISHED_WISESTONE_SLAB_ITEM),
                        new BlockEntry(new ItemStack(WizardsReborn.POLISHED_WISESTONE_WALL_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_BRICKS_STAIRS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_BRICKS_SLAB_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_BRICKS_WALL_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_TILE_STAIRS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_TILE_SLAB_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_TILE_WALL_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.CHISELED_WISESTONE_STAIRS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.CHISELED_WISESTONE_SLAB_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.CHISELED_WISESTONE_WALL_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.WISESTONE_PILLAR_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.POLISHED_WISESTONE_PRESSURE_PLATE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.POLISHED_WISESTONE_BUTTON_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_STAIRS_ITEM.get(), 4),
                        new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_SLAB_ITEM.get(), 6),
                        new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_WALL_ITEM.get(), 6),
                        new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get()),
                        new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.POLISHED_WISESTONE_ITEM.get(), 4),
                        new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.POLISHED_WISESTONE_STAIRS_ITEM.get(), 4),
                        POLISHED_WISESTONE_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.POLISHED_WISESTONE_SLAB_ITEM.get(), 6),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.POLISHED_WISESTONE_WALL_ITEM.get(), 6),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get(), 4),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_BRICKS_STAIRS_ITEM.get(), 4),
                        new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_BRICKS_SLAB_ITEM.get(), 6),
                        new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_BRICKS_WALL_ITEM.get(), 6),
                        new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()),
                        new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get(), 4),
                        new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_BRICKS_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_TILE_STAIRS_ITEM.get(), 4),
                        new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_TILE_SLAB_ITEM.get(), 6),
                        new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_TILE_WALL_ITEM.get(), 6),
                        new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()),
                        new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get()), new ItemStack(WizardsReborn.WISESTONE_TILE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get(), 4),
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, EMPTY_ITEM, POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.CHISELED_WISESTONE_STAIRS_ITEM.get(), 4),
                        new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), EMPTY_ITEM,
                        new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.CHISELED_WISESTONE_SLAB_ITEM.get(), 6),
                        new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.CHISELED_WISESTONE_WALL_ITEM.get(), 6),
                        new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()),
                        new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get()), new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISESTONE_PILLAR_ITEM.get(), 2),
                        POLISHED_WISESTONE_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.POLISHED_WISESTONE_PRESSURE_PLATE_ITEM.get(), 1),
                        POLISHED_WISESTONE_ITEM, new ItemStack(WizardsReborn.CHISELED_WISESTONE_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.POLISHED_WISESTONE_BUTTON_ITEM.get(), 1),
                        POLISHED_WISESTONE_ITEM
                )
        );

        WISESTONE_PEDESTAL = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wisestone_pedestal",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_pedestal",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, WISESTONE_PEDESTAL_ITEM)
                ),
                new ArcaneWorkbenchPage(WISESTONE_PEDESTAL_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
                        ARCANE_GOLD_INGOT_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_hovering_tome_stand",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.WISESTONE_HOVERING_TOME_STAND.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.HOVERING_TOME_STAND.get()),
                        WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANEMICON.get())
                )
        );

        FLUID_PIPES = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fluid_pipes",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.fluid_pipe",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.fluid_extractor",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FLUID_EXTRACTOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get(), 6),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_SLAB_ITEM, EMPTY_ITEM, POLISHED_WISESTONE_SLAB_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.FLUID_EXTRACTOR_ITEM.get()),
                        new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(Items.REDSTONE)
                )
        );

        STEAM_PIPES = new Chapter(
                "wizards_reborn.arcanemicon.chapter.steam_pipes",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.steam_pipe",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.steam_extractor",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.STEAM_EXTRACTOR_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get(), 4),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.STEAM_EXTRACTOR_ITEM.get()),
                        new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), new ItemStack(Items.REDSTONE)
                )
        );

        ORBITAL_FLUID_RETAINER = new Chapter(
                "wizards_reborn.arcanemicon.chapter.orbital_fluid_retainer",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.orbital_fluid_retainer",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ORBITAL_FLUID_RETAINER_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ORBITAL_FLUID_RETAINER_ITEM.get()),
                        ARCANUM_ITEM, ARCANUM_ITEM,  ARCANUM_ITEM,
                        POLISHED_WISESTONE_ITEM, WISESTONE_PEDESTAL_ITEM,  POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM
                )
        );

        ALCHEMY_FURNACE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_furnace",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_furnace",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ALCHEMY_FURNACE_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ALCHEMY_FURNACE_ITEM.get()),
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_SLAB_ITEM,
                        POLISHED_WISESTONE_ITEM, new ItemStack(Items.FURNACE),  POLISHED_WISESTONE_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_ITEM,
                        new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), EMPTY_ITEM, new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get())
                ),
                new ImagePage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/alchemy_furnace_image_page.png"))
        );

        STEAM_THERMAL_STORAGE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.steam_thermal_storage",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.steam_thermal_storage",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.STEAM_THERMAL_STORAGE_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.STEAM_THERMAL_STORAGE_ITEM.get()),
                        ARCANE_GOLD_INGOT_ITEM, POLISHED_WISESTONE_ITEM,  ARCANE_GOLD_INGOT_ITEM,
                        POLISHED_WISESTONE_ITEM, new ItemStack(Items.GLASS),  POLISHED_WISESTONE_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, POLISHED_WISESTONE_ITEM,  ARCANE_GOLD_INGOT_ITEM,
                        new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), ARCANE_GOLD_NUGGET_ITEM
                )
        );

        ALCHEMY_MACHINE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_machine",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_machine",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ALCHEMY_MACHINE_ITEM.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_boiler",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ALCHEMY_BOILER_ITEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ALCHEMY_MACHINE_ITEM.get()),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ORBITAL_FLUID_RETAINER_ITEM.get()), EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_ITEM,
                        new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get())
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ALCHEMY_BOILER_ITEM.get()),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,  POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()),  EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get())
                ),
                new ImagePage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/alchemy_machine_image_page.png")),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(Fluids.LAVA, 750), false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.MAGMA_BLOCK), new ItemStack(Items.MAGMA_BLOCK), new ItemStack(Items.MAGMA_BLOCK), new ItemStack(Items.MAGMA_BLOCK)
                )
        );

        ALCHEMY_OIL = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_oil",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_oil",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ALCHEMY_OIL_BUCKET.get()))
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 1000), false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()),
                        new ItemStack(WizardsReborn.PETALS.get())
                )
        );

        MUSIC_DISC_ARCANUM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.music_disc_arcanum",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.music_disc_arcanum",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.MUSIC_DISC_ARCANUM.get()))
                ),
                new AlchemyMachinePage(new ItemStack(WizardsReborn.MUSIC_DISC_ARCANUM.get()), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.MUSIC_DISC_13), ARCANUM_ITEM
                )
        );

        MUSIC_DISC_MOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.music_disc_mor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.music_disc_mor",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.MUSIC_DISC_MOR.get()))
                ),
                new AlchemyMachinePage(new ItemStack(WizardsReborn.MUSIC_DISC_MOR.get()), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.MUSIC_DISC_13), new ItemStack(WizardsReborn.MOR.get())
                )
        );

        ALCHEMY_CALX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_calx",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_calx",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, ALCHEMY_CALX_ITEM)
                ),
                new AlchemyMachinePage(new ItemStack(WizardsReborn.ALCHEMY_CALX.get(), 3), FluidStack.EMPTY, false, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 150), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ), new ItemStack(Items.CALCITE),
                        new ItemStack(Items.BONE_MEAL), new ItemStack(Items.BONE_MEAL), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get())
                )
        );

        Map<AlchemyPotion, ItemStack> vialPotions = new HashMap<AlchemyPotion, ItemStack>();
        Map<AlchemyPotion, ItemStack> flaskPotions = new HashMap<AlchemyPotion, ItemStack>();

        for (AlchemyPotion potion : AlchemyPotions.getAlchemyPotions()) {
            ItemStack stack = new ItemStack(WizardsReborn.ALCHEMY_VIAL_POTION.get());
            AlchemyPotionUtils.setPotion(stack, potion);
            vialPotions.put(potion, stack);

            stack = new ItemStack(WizardsReborn.ALCHEMY_FLASK_POTION.get());
            AlchemyPotionUtils.setPotion(stack, potion);
            flaskPotions.put(potion, stack);
        }

        ALCHEMY_GLASS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_glass",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_glass",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()))
                ),
                new AlchemyMachinePage(new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get(), 4), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 500), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.GLASS), new ItemStack(Items.GLASS), new ItemStack(Items.GLASS), new ItemStack(Items.GLASS),
                        ARCANUM_DUST_ITEM, ALCHEMY_CALX_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_vial",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ALCHEMY_VIAL.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ALCHEMY_VIAL.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()),  EMPTY_ITEM,
                        new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()), EMPTY_ITEM,  new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()),
                        new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()), new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()),  new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get())
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.ALCHEMY_OIL), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()),
                        new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.ALCHEMY_VIAL.get())
                )
        );

        ALCHEMY_BAG = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_bag",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_bag",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ALCHEMY_BAG.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ALCHEMY_BAG.get()),
                        EMPTY_ITEM, new ItemStack(WizardsReborn.LEATHER_BELT.get()),  EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(Items.CHEST),  new ItemStack(Items.LEATHER),
                        new ItemStack(WizardsReborn.ALCHEMY_VIAL.get()), new ItemStack(Items.LEATHER),  new ItemStack(WizardsReborn.ALCHEMY_VIAL.get()),
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ALCHEMY_CALX_ITEM
                )
        );

        ALCHEMY_POTIONS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_potions",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_potions",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW))
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.MUNDANE_BREW_FLUID.get(), 250), false, true,
                        new FluidStack(Fluids.WATER, 250), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART)
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.MUNDANE_BREW_FLUID.get(), 1000), false, true,
                        new FluidStack(Fluids.WATER, 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART), vialPotions.get(RegisterAlchemyPotions.WATER)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.NIGHT_VISION), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.REDSTONE)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.INVISIBILITY), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.NIGHT_VISION), new ItemStack(Items.FERMENTED_SPIDER_EYE)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.LEAPING), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.RABBIT_FOOT), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.FIRE_RESISTANCE), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.REDSTONE)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.SWIFTNESS), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.SUGAR), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.SLOWNESS), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.SWIFTNESS), new ItemStack(Items.FERMENTED_SPIDER_EYE)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.SLOWNESS), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.LEAPING), new ItemStack(Items.FERMENTED_SPIDER_EYE)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.TURTLE_MASTER), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.TURTLE_HELMET), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.WATER_BREATHING), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.PUFFERFISH), new ItemStack(Items.REDSTONE)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.HEALING), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.GLISTERING_MELON_SLICE), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.HARMING), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.HEALING), new ItemStack(Items.FERMENTED_SPIDER_EYE)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.HARMING), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.POISON), new ItemStack(Items.FERMENTED_SPIDER_EYE)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.POISON), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.REGENERATION), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.STRENGTH), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.WEAKNESS), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.STRENGTH), new ItemStack(Items.FERMENTED_SPIDER_EYE)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.SLOW_FALLING), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.PHANTOM_MEMBRANE), new ItemStack(Items.REDSTONE)
                )
        );

        TEA = new Chapter(
                "wizards_reborn.arcanemicon.chapter.tea",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.oil_tea",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, vialPotions.get(RegisterAlchemyPotions.OIL_TEA))
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.OIL_TEA_FLUID.get(), 1000), true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 500), new FluidStack(Fluids.WATER, 500), FluidStack.EMPTY,
                        new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.PETALS.get())
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.OIL_TEA), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 500), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.PETALS.get()), vialPotions.get(RegisterAlchemyPotions.WATER)
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_tea",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, vialPotions.get(RegisterAlchemyPotions.WISSEN_TEA))
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.WISSEN_TEA_FLUID.get(), 1000), true, true,
                        new FluidStack(Fluids.WATER, 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.PETALS.get()), ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.WISSEN_TEA), FluidStack.EMPTY, true, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.PETALS.get()), ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, vialPotions.get(RegisterAlchemyPotions.WATER)
                )
        );

        ALCHEMY_BREWS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_brews",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_brews",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.MUSHROOM_BREW_BUCKET.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.HELLISH_MUSHROOM_BREW_BUCKET.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.MOR_BREW_BUCKET.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.FLOWER_BREW_BUCKET.get()))
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.MUNDANE_BREW_FLUID.get(), 1000), false, true,
                        new FluidStack(Fluids.WATER, 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_BROWN_MUSHROOM.get()), new ItemStack(WizardsReborn.GROUND_BROWN_MUSHROOM.get()), new ItemStack(WizardsReborn.GROUND_BROWN_MUSHROOM.get())
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.MUNDANE_BREW_FLUID.get(), 1000), false, true,
                        new FluidStack(Fluids.WATER, 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_RED_MUSHROOM.get()), new ItemStack(WizardsReborn.GROUND_RED_MUSHROOM.get()), new ItemStack(WizardsReborn.GROUND_RED_MUSHROOM.get())
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID.get(), 1000), false, true,
                        new FluidStack(Fluids.WATER, 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_CRIMSON_FUNGUS.get()), new ItemStack(WizardsReborn.GROUND_CRIMSON_FUNGUS.get()), new ItemStack(WizardsReborn.GROUND_CRIMSON_FUNGUS.get())
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID.get(), 1000), false, true,
                        new FluidStack(Fluids.WATER, 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_WARPED_FUNGUS.get()), new ItemStack(WizardsReborn.GROUND_WARPED_FUNGUS.get()), new ItemStack(WizardsReborn.GROUND_WARPED_FUNGUS.get())
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.MOR_BREW_FLUID.get(), 1000), false, true,
                        new FluidStack(Fluids.WATER, 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_MOR.get()), new ItemStack(WizardsReborn.GROUND_MOR.get()), new ItemStack(WizardsReborn.GROUND_MOR.get())
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.MOR_BREW_FLUID.get(), 1000), false, true,
                        new FluidStack(Fluids.WATER, 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_ELDER_MOR.get()), new ItemStack(WizardsReborn.GROUND_ELDER_MOR.get()), new ItemStack(WizardsReborn.GROUND_ELDER_MOR.get())
                ),
                new AlchemyMachinePage(EMPTY_ITEM, new FluidStack(WizardsReborn.FLOWER_BREW_FLUID.get(), 1000), false, true,
                        new FluidStack(Fluids.WATER, 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.PETALS.get())
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.MUSHROOM_BREW), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_BROWN_MUSHROOM.get()), new ItemStack(WizardsReborn.GROUND_BROWN_MUSHROOM.get()), new ItemStack(WizardsReborn.GROUND_BROWN_MUSHROOM.get()), vialPotions.get(RegisterAlchemyPotions.WATER)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.MUSHROOM_BREW), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_RED_MUSHROOM.get()), new ItemStack(WizardsReborn.GROUND_RED_MUSHROOM.get()), new ItemStack(WizardsReborn.GROUND_RED_MUSHROOM.get()), vialPotions.get(RegisterAlchemyPotions.WATER)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.HELLISH_MUSHROOM_BREW), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_CRIMSON_FUNGUS.get()), new ItemStack(WizardsReborn.GROUND_CRIMSON_FUNGUS.get()), new ItemStack(WizardsReborn.GROUND_CRIMSON_FUNGUS.get()), vialPotions.get(RegisterAlchemyPotions.WATER)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.HELLISH_MUSHROOM_BREW), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_WARPED_FUNGUS.get()), new ItemStack(WizardsReborn.GROUND_WARPED_FUNGUS.get()), new ItemStack(WizardsReborn.GROUND_WARPED_FUNGUS.get()), vialPotions.get(RegisterAlchemyPotions.WATER)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.MOR_BREW), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_MOR.get()), new ItemStack(WizardsReborn.GROUND_MOR.get()), new ItemStack(WizardsReborn.GROUND_MOR.get()), vialPotions.get(RegisterAlchemyPotions.WATER)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.MOR_BREW), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.GROUND_ELDER_MOR.get()), new ItemStack(WizardsReborn.GROUND_ELDER_MOR.get()), new ItemStack(WizardsReborn.GROUND_ELDER_MOR.get()), vialPotions.get(RegisterAlchemyPotions.WATER)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.FLOWER_BREW), FluidStack.EMPTY, false, true,
                        FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.PETALS.get()), new ItemStack(WizardsReborn.PETALS.get()), vialPotions.get(RegisterAlchemyPotions.WATER)
                ),
                new AlchemyMachinePage(new ItemStack(Items.FERMENTED_SPIDER_EYE), FluidStack.EMPTY, false, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 50), new FluidStack(WizardsReborn.MUSHROOM_BREW_FLUID.get(), 100), FluidStack.EMPTY,
                        new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.SUGAR)
                ),
                new AlchemyMachinePage(new ItemStack(Items.FERMENTED_SPIDER_EYE), FluidStack.EMPTY, false, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 50), new FluidStack(WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID.get(), 100), FluidStack.EMPTY,
                        new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.SUGAR)
                ),
                new AlchemyMachinePage(new ItemStack(Items.FERMENTED_SPIDER_EYE), FluidStack.EMPTY, false, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 50), new FluidStack(WizardsReborn.MOR_BREW_FLUID.get(), 100), FluidStack.EMPTY,
                        new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.SUGAR)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.ABSORPTION), FluidStack.EMPTY, false, true,
                        new FluidStack(WizardsReborn.FLOWER_BREW_FLUID.get(), 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.RESISTANCE), FluidStack.EMPTY, false, true,
                        new FluidStack(WizardsReborn.MUSHROOM_BREW_FLUID.get(), 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.OBSIDIAN), new ItemStack(Items.OBSIDIAN), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.MAGICAL_ATTUNEMENT), FluidStack.EMPTY, false, true,
                        new FluidStack(WizardsReborn.MOR_BREW_FLUID.get(), 1000), FluidStack.EMPTY, FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.WISSEN_TEA), ARCANUM_ITEM, ARCANUM_ITEM, ARCACITE_ITEM, new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE)
                ),
                new AlchemyMachinePage(vialPotions.get(RegisterAlchemyPotions.DARKNESS), FluidStack.EMPTY, false, true,
                        new FluidStack(WizardsReborn.MUSHROOM_BREW_FLUID.get(), 2000),  new FluidStack(WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID.get(), 1000), FluidStack.EMPTY,
                        vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.SCULK), new ItemStack(Items.SCULK), new ItemStack(Items.SCULK), new ItemStack(Items.SCULK), new ItemStack(Items.REDSTONE)
                )
        );

        ADVANCED_CALX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.advanced_calx",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.advanced_calx",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, NATURAL_CALX_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, SCORCHED_CALX_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, DISTANT_CALX_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, ENCHANTED_CALX_ITEM)
                ),
                new AlchemyMachinePage(new ItemStack(WizardsReborn.NATURAL_CALX.get(), 2), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 500), new FluidStack(WizardsReborn.FLOWER_BREW_FLUID.get(), 500), FluidStack.EMPTY,
                        ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, new ItemStack(Items.SLIME_BALL),
                        new ItemStack(Items.MOSS_BLOCK), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(WizardsReborn.PETALS.get())
                ),
                new AlchemyMachinePage(new ItemStack(WizardsReborn.SCORCHED_CALX.get(), 2), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 500), new FluidStack(WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID.get(), 500), FluidStack.EMPTY,
                        ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, new ItemStack(Items.NETHERRACK),
                        new ItemStack(Items.SOUL_SAND), new ItemStack(Items.CRIMSON_FUNGUS), new ItemStack(Items.WARPED_FUNGUS)
                ),
                new AlchemyMachinePage(new ItemStack(WizardsReborn.DISTANT_CALX.get(), 2), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 500), new FluidStack(WizardsReborn.MOR_BREW_FLUID.get(), 500), FluidStack.EMPTY,
                        ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, new ItemStack(Items.END_STONE),
                        new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.CHORUS_FRUIT), new ItemStack(Items.OBSIDIAN)
                ),
                new AlchemyMachinePage(new ItemStack(WizardsReborn.ENCHANTED_CALX.get(), 2), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 500), new FluidStack(WizardsReborn.WISSEN_TEA_FLUID.get(), 500), FluidStack.EMPTY,
                        ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, new ItemStack(Items.ENCHANTED_BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.DIAMOND)
                )
        );

        ALCHEMY_TRANSMUTATION = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_transmutation",
                new TitlePage("wizards_reborn.arcanemicon.page.alchemy_transmutation"),
                new AlchemyMachinePage(new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get(), 2), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 100), new FluidStack(WizardsReborn.WISSEN_TEA_FLUID.get(), 200), FluidStack.EMPTY,
                        new ItemStack(Items.RAW_GOLD), new ItemStack(Items.RAW_GOLD), ARCANUM_ITEM, ALCHEMY_CALX_ITEM, NATURAL_CALX_ITEM
                ),
                new AlchemyMachinePage(new ItemStack(Items.COAL, 2), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 50), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.CHARCOAL), new ItemStack(Items.CHARCOAL), new ItemStack(Items.COBBLESTONE), ALCHEMY_CALX_ITEM
                ),
                new AlchemyMachinePage(new ItemStack(Items.GLOWSTONE_DUST, 12), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 100), new FluidStack(WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID.get(), 200), FluidStack.EMPTY,
                        new ItemStack(Items.COAL), new ItemStack(Items.COAL), SCORCHED_CALX_ITEM, new ItemStack(Items.GOLD_NUGGET)
                ),
                new AlchemyMachinePage(new ItemStack(Items.LEATHER, 3), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 100), FluidStack.EMPTY, FluidStack.EMPTY,
                        new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.HONEYCOMB), ALCHEMY_CALX_ITEM, NATURAL_CALX_ITEM
                )
        );

        List<MobEffectInstance> noEffects = new ArrayList<>();
        List<MobEffectInstance> petalsEffects = new ArrayList<>();
        List<MobEffectInstance> coalEffects = new ArrayList<>();
        List<MobEffectInstance> magmaCreamEffects = new ArrayList<>();
        List<MobEffectInstance> ghastTearEffects = new ArrayList<>();
        List<MobEffectInstance> phantomMembraneEffects = new ArrayList<>();
        List<MobEffectInstance> pufferfishEffects = new ArrayList<>();
        List<MobEffectInstance> goldenCarrotEffects = new ArrayList<>();
        List<MobEffectInstance> sugarEffects = new ArrayList<>();
        List<MobEffectInstance> blazePowderEffects = new ArrayList<>();
        List<MobEffectInstance> spiderEyeEffects = new ArrayList<>();
        List<MobEffectInstance> fermentedSpiderEyeEffects = new ArrayList<>();
        List<MobEffectInstance> rabbitGootEffects = new ArrayList<>();
        List<MobEffectInstance> glisteringMelonSliceEffects = new ArrayList<>();

        petalsEffects.add(new MobEffectInstance(MobEffects.REGENERATION, 1000, 0));
        coalEffects.add(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0));
        coalEffects.add(new MobEffectInstance(MobEffects.CONFUSION, 600, 0));
        magmaCreamEffects.add(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 10000, 0));
        ghastTearEffects.add(new MobEffectInstance(MobEffects.REGENERATION, 3000, 0));
        phantomMembraneEffects.add(new MobEffectInstance(MobEffects.SLOW_FALLING, 3000, 0));
        pufferfishEffects.add(new MobEffectInstance(MobEffects.WATER_BREATHING, 10000, 0));
        goldenCarrotEffects.add(new MobEffectInstance(MobEffects.NIGHT_VISION, 8400, 0));
        sugarEffects.add(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000, 0));
        blazePowderEffects.add(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 0));
        spiderEyeEffects.add(new MobEffectInstance(MobEffects.POISON, 3000, 0));
        fermentedSpiderEyeEffects.add(new MobEffectInstance(MobEffects.WEAKNESS, 3000, 0));
        rabbitGootEffects.add(new MobEffectInstance(MobEffects.JUMP, 10000, 0));
        glisteringMelonSliceEffects.add(new MobEffectInstance(MobEffects.HEAL, 2, 0));

        ARCANE_CENSER = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_censer",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_censer",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_CENSER_ITEM.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.smoke_warning"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_CENSER_ITEM.get()),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        POLISHED_WISESTONE_ITEM, new ItemStack(Items.GLASS), POLISHED_WISESTONE_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
                        ALCHEMY_CALX_ITEM, NATURAL_CALX_ITEM, new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), NATURAL_CALX_ITEM
                ),
                new CenserPage(petalsEffects, new ItemStack(WizardsReborn.PETALS.get())),
                new CenserPage(coalEffects, new ItemStack(Items.COAL)),
                new CenserPage(magmaCreamEffects, new ItemStack(Items.MAGMA_CREAM)),
                new CenserPage(ghastTearEffects, new ItemStack(Items.GHAST_TEAR)),
                new CenserPage(phantomMembraneEffects, new ItemStack(Items.PHANTOM_MEMBRANE)),
                new CenserPage(pufferfishEffects, new ItemStack(Items.PUFFERFISH)),
                new CenserPage(goldenCarrotEffects, new ItemStack(Items.GOLDEN_CARROT)),
                new CenserPage(sugarEffects, new ItemStack(Items.SUGAR)),
                new CenserPage(blazePowderEffects, new ItemStack(Items.BLAZE_POWDER)),
                new CenserPage(spiderEyeEffects, new ItemStack(Items.SPIDER_EYE)),
                new CenserPage(fermentedSpiderEyeEffects, new ItemStack(Items.FERMENTED_SPIDER_EYE)),
                new CenserPage(rabbitGootEffects, new ItemStack(Items.RABBIT_FOOT)),
                new CenserPage(glisteringMelonSliceEffects, new ItemStack(Items.GLISTERING_MELON_SLICE))
        );

        SMOKING_PIPE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.smoking_pipe",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.smoking_pipe",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCANE_WOOD_SMOKING_PIPE.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.smoke_warning"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WOOD_SMOKING_PIPE.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS.get()),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        NATURAL_CALX_ITEM, EMPTY_ITEM, ALCHEMY_CALX_ITEM
                ),
                new CenserPage(noEffects, new ItemStack(WizardsReborn.ARCANE_LINEN_SEEDS.get())),
                new CenserPage(noEffects, new ItemStack(Items.WHEAT_SEEDS)),
                new CenserPage(noEffects, new ItemStack(Items.BEETROOT_SEEDS)),
                new CenserPage(noEffects, new ItemStack(Items.MELON_SEEDS)),
                new CenserPage(noEffects, new ItemStack(Items.PUMPKIN_SEEDS)),
                new CenserPage(noEffects, new ItemStack(Items.BAMBOO)),
                new CenserPage(noEffects, new ItemStack(Items.PAPER)),
                new CenserPage(noEffects, new ItemStack(Items.FEATHER)),
                new CenserPage(noEffects, new ItemStack(Items.PITCHER_POD)),
                new CenserPage(noEffects, new ItemStack(Items.TORCHFLOWER_SEEDS))
        );

        ARCACITE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcacite",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcacite",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, ARCACITE_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCACITE_BLOCK_ITEM.get()))
                ),
                new AlchemyMachinePage(ARCACITE_ITEM, FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 250), FluidStack.EMPTY, FluidStack.EMPTY,
                        ARCANUM_ITEM, ARCANUM_ITEM, new ItemStack(Items.QUARTZ),
                        new ItemStack(Items.QUARTZ), SCORCHED_CALX_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCACITE_BLOCK_ITEM.get()),
                        ARCACITE_ITEM, ARCACITE_ITEM, ARCACITE_ITEM,
                        ARCACITE_ITEM, ARCACITE_ITEM, ARCACITE_ITEM,
                        ARCACITE_ITEM, ARCACITE_ITEM, ARCACITE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCACITE.get(), 9), new ItemStack(WizardsReborn.ARCACITE_BLOCK_ITEM.get())),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcacite_trinkets",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCACITE_AMULET.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCACITE_RING.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCACITE_AMULET.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCACITE_RING.get()),
                        ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM
                )
        );

        ARCACITE_POLISHING_MIXTURE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcacite_polishing_mixture",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcacite_polishing_mixture",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsReborn.ARCACITE_POLISHING_MIXTURE.get()))
                ),
                new AlchemyMachinePage(new ItemStack(WizardsReborn.ARCACITE_POLISHING_MIXTURE.get()), FluidStack.EMPTY, true, true,
                        new FluidStack(WizardsReborn.ALCHEMY_OIL_FLUID.get(), 2000), FluidStack.EMPTY, FluidStack.EMPTY,
                        ARCACITE_ITEM, ARCACITE_ITEM,
                        ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ALCHEMY_CALX_ITEM
                )
        );

        ALCHEMY_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.alchemy_index",
                        new IndexEntry(MOR, new ItemStack(WizardsReborn.MOR_ITEM.get())),
                        new IndexEntry(MORTAR, new ItemStack(WizardsReborn.ARCANE_WOOD_MORTAR.get()), RegisterKnowledges.ARCANE_WOOD),
                        new IndexEntry(ARCANE_LINEN, new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), RegisterKnowledges.WISSEN_CRYSTALLIZER),
                        new IndexEntry(MUSHROOM_CAPS, new ItemStack(WizardsReborn.BROWN_MUSHROOM_CAP.get()), RegisterKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(WISESTONE, new ItemStack(WizardsReborn.WISESTONE_ITEM.get()), RegisterKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(WISESTONE_PEDESTAL, WISESTONE_PEDESTAL_ITEM, RegisterKnowledges.WISESTONE)
                ),
                new IndexPage(
                        new IndexEntry(FLUID_PIPES, new ItemStack(WizardsReborn.FLUID_PIPE_ITEM.get()), RegisterKnowledges.WISESTONE),
                        new IndexEntry(STEAM_PIPES, new ItemStack(WizardsReborn.STEAM_PIPE_ITEM.get()), RegisterKnowledges.WISESTONE),
                        new IndexEntry(ORBITAL_FLUID_RETAINER, new ItemStack(WizardsReborn.ORBITAL_FLUID_RETAINER_ITEM.get()), RegisterKnowledges.WISESTONE),
                        new IndexEntry(ALCHEMY_FURNACE, new ItemStack(WizardsReborn.ALCHEMY_FURNACE_ITEM.get()), RegisterKnowledges.ORBITAL_FLUID_RETAINER),
                        new IndexEntry(STEAM_THERMAL_STORAGE, new ItemStack(WizardsReborn.STEAM_THERMAL_STORAGE_ITEM.get()), RegisterKnowledges.ALCHEMY_FURNACE),
                        new IndexEntry(ALCHEMY_MACHINE, new ItemStack(WizardsReborn.ALCHEMY_MACHINE_ITEM.get()), RegisterKnowledges.ALCHEMY_FURNACE),
                        new IndexEntry(ALCHEMY_OIL, new ItemStack(WizardsReborn.ALCHEMY_OIL_BUCKET.get()), RegisterKnowledges.ALCHEMY_MACHINE)
                ),
                new IndexPage(
                        new IndexEntry(MUSIC_DISC_ARCANUM, new ItemStack(WizardsReborn.MUSIC_DISC_ARCANUM.get()), RegisterKnowledges.ALCHEMY_OIL),
                        new IndexEntry(MUSIC_DISC_MOR, new ItemStack(WizardsReborn.MUSIC_DISC_MOR.get()), RegisterKnowledges.ALCHEMY_OIL),
                        new IndexEntry(ALCHEMY_CALX, ALCHEMY_CALX_ITEM, RegisterKnowledges.ALCHEMY_OIL),
                        new IndexEntry(ALCHEMY_GLASS, new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()), RegisterKnowledges.ALCHEMY_CALX),
                        new IndexEntry(ALCHEMY_BAG, new ItemStack(WizardsReborn.ALCHEMY_BAG.get()), RegisterKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(ALCHEMY_POTIONS, vialPotions.get(RegisterAlchemyPotions.MUNDANE_BREW), RegisterKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(TEA, vialPotions.get(RegisterAlchemyPotions.WISSEN_TEA), RegisterKnowledges.ALCHEMY_GLASS)
                ),
                new IndexPage(
                        new IndexEntry(ALCHEMY_BREWS, new ItemStack(WizardsReborn.MOR_BREW_BUCKET.get()), RegisterKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(ADVANCED_CALX, NATURAL_CALX_ITEM, RegisterKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(ALCHEMY_TRANSMUTATION, new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), RegisterKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(ARCANE_CENSER, new ItemStack(WizardsReborn.ARCANE_CENSER_ITEM.get()), RegisterKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(SMOKING_PIPE, new ItemStack(WizardsReborn.ARCANE_WOOD_SMOKING_PIPE.get()), RegisterKnowledges.ARCANE_CENSER),
                        new IndexEntry(ARCACITE, ARCACITE_ITEM, RegisterKnowledges.ALCHEMY_OIL),
                        new IndexEntry(ARCACITE_POLISHING_MIXTURE, new ItemStack(WizardsReborn.ARCACITE_POLISHING_MIXTURE.get()), RegisterKnowledges.ARCACITE)
                )
        );
    }

    public static void init() {
        itemsInit();
        arcaneNatureInit();
        spellsInit();
        crystalRitualsInit();
        alchemyInit();

        ARCANE_NATURE = new Category(
                "arcane_nature",
                ARCANUM_ITEM,
                ARCANE_NATURE_INDEX
        );

        ItemStack wandItem = new ItemStack(WizardsReborn.ARCANE_WAND.get());
        SimpleContainer wandItemInv = ArcaneWandItem.getInventory(wandItem);
        CompoundTag nbt = wandItem.getOrCreateTag();
        wandItemInv.setItem(0, new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()));
        nbt.putBoolean("crystal", true);
        wandItem.setTag(nbt);

        SPELLS = new Category(
                "spells",
                wandItem,
                SPELLS_INDEX
        );

        CRYSTAL_RITUALS = new Category(
                "crystal_rituals",
                new ItemStack(WizardsReborn.FACETED_EARTH_CRYSTAL.get()),
                CRYSTAL_RITUALS_INDEX
        );

        ALCHEMY = new Category(
                "alchemy",
                new ItemStack(WizardsReborn.ELDER_MOR_ITEM.get()),
                ALCHEMY_INDEX
        );

        categories.add(ARCANE_NATURE);
        categories.add(SPELLS);
        categories.add(CRYSTAL_RITUALS);
        categories.add(ALCHEMY);
    }
}
