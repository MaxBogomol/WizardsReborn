package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import mod.maxbogomol.fluffy_fur.registry.common.item.FluffyFurItems;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionHandler;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualHandler;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.*;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.page.*;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.*;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.titled.*;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.DrinkBottleItem;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.integration.common.create.WizardsRebornCreate;
import mod.maxbogomol.wizards_reborn.integration.common.embers.WizardsRebornEmbers;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.WizardsRebornFarmersDelight;
import mod.maxbogomol.wizards_reborn.integration.common.malum.WizardsRebornMalum;
import mod.maxbogomol.wizards_reborn.registry.common.*;
import mod.maxbogomol.wizards_reborn.registry.common.entity.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.fluid.WizardsRebornFluids;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ArcanemiconChapters {
    public static List<Category> categories = new ArrayList<>();
    public static List<Category> additionalCategories = new ArrayList<>();
    public static Category ARCANE_NATURE, SPELLS, CRYSTAL_RITUALS, ALCHEMY, ADDITIONAL;
    public static Chapter ARCANE_NATURE_INDEX, SPELLS_INDEX, CRYSTAL_RITUALS_INDEX, ALCHEMY_INDEX, ADDITIONAL_INDEX,
            ARCANUM, ARCANUM_DUST_TRANSMUTATION, ARCANE_WOOD, ARCANE_GOLD, SCYTHES, TRINKETS, ARCANE_WOOD_BOW, ARCANE_WOOD_CROSSBOW, ARCANE_WOOD_FISHING_ROD, ARCANE_GOLD_SHEARS, BANNER_PATTERNS, WISSEN, WISSEN_TRANSLATOR, ARCANE_PEDESTAL, WISSEN_ALTAR, WISSEN_CRYSTALLIZER, ARCANE_WORKBENCH, MUSIC_DISC_SHIMMER, ARCANE_LUMOS, CRYSTALS, ARCANE_WAND, AUTOMATION, WISSEN_CELL, WISSEN_CHARGER, CRYSTAL_BAG, TOTEM_OF_FLAMES, EXPERIENCE_TOTEM, TOTEM_OF_EXPERIENCE_ABSORPTION, TOTEM_OF_DISENCHANT, ALTAR_OF_DROUGHT, VOID_CRYSTAL, ARCANE_FORTRESS_ARMOR, INVENTOR_WIZARD_ARMOR, ARCANE_WOOD_CANE, ARCANE_ITERATOR, KNOWLEDGE_SCROLL, MUSIC_DISC_REBORN, MUSIC_DISC_PANACHE, ARCANUM_LENS, WISSEN_KEYCHAIN, WISSEN_RING, LEATHER_COLLAR, JEWELER_TABLE, FACETED_CRYSTALS, TRIMS, TOP_HAT_TRIM, MAGNIFICENT_MAID_TRIM, SUMMER_LOVE_TRIM, MUSIC_DISC_CAPITALISM, CARGO_CARPET,
            ARCANE_LEVER, ARCANE_HOPPER, REDSTONE_SENSOR, WISSEN_SENSOR, COOLDOWN_SENSOR, EXPERIENCE_SENSOR, LIGHT_SENSOR, HEAT_SENSOR, FLUID_SENSOR, STEAM_SENSOR, WISSEN_ACTIVATOR, ITEM_SORTER, ARCANE_WOOD_FRAME, WISSEN_CASING, WISESTONE_CASING, FLUID_CASING, STEAM_CASING, GLASS_FRAME, LIGHT_CASING, INNOCENT_CASING,
            ALL_SPELLS, EARTH_SPELLS, WATER_SPELLS, AIR_SPELLS, FIRE_SPELLS, VOID_SPELLS,
            EARTH_PROJECTILE, WATER_PROJECTILE, AIR_PROJECTILE, FIRE_PROJECTILE, VOID_PROJECTILE, FROST_PROJECTILE, HOLY_PROJECTILE, CURSE_PROJECTILE,
            EARTH_RAY, WATER_RAY, AIR_RAY, FIRE_RAY, VOID_RAY, FROST_RAY, HOLY_RAY, CURSE_RAY,
            HEART_OF_NATURE, WATER_BREATHING, AIR_FLOW, FIRE_SHIELD, BLINK, SNOWFLAKE, HOLY_CROSS, CURSE_CROSS, POISON,
            MAGIC_SPROUT, DIRT_BLOCK, WATER_BLOCK, AIR_IMPACT, ICE_BLOCK,
            EARTH_CHARGE, WATER_CHARGE, AIR_CHARGE, FIRE_CHARGE, VOID_CHARGE, FROST_CHARGE, HOLY_CHARGE, CURSE_CHARGE,
            EARTH_AURA, WATER_AURA, AIR_AURA, FIRE_AURA, VOID_AURA, FROST_AURA, HOLY_AURA, CURSE_AURA,
            RAIN_CLOUD, LAVA_BLOCK, ICICLE, SHARP_BLINK, CRYSTAL_CRUSHING, TOXIC_RAIN, MOR_SWARM, WITHERING, IRRITATION, NECROTIC_RAY, LIGHT_RAY,
            INCINERATION, REPENTANCE, RENUNCIATION,
            EMBER_RAY, WISDOM,
            RESEARCHES, MONOGRAMS, RESEARCH,
            LUNAM, VITA, SOLEM, MORS, MIRACULUM, TEMPUS, STATERA, ECLIPSIS, SICCITAS, SOLSTITIUM, FAMES, RENAISSANCE, BELLUM, LUX, KARA, DEGRADATIO, PRAEDICTIONEM, EVOLUTIONIS, TENEBRIS, UNIVERSUM,
            LIGHT_RAYS, LIGHT_EMITTER, LIGHT_TRANSFER_LENS, RUNIC_PEDESTAL, CRYSTALS_RITUALS, FOCUSING, ARTIFICIAL_FERTILITY, RITUAL_BREEDING, CRYSTAL_GROWTH_ACCELERATION, CRYSTAL_INFUSION, ARCANUM_SEED, INNOCENT_WOOD, INNOCENT_WOOD_TOOLS, PHANTOM_INK_TRIM, MUSIC_DISC_DISCO,
            MOR, MORTAR, ARCANE_LINEN, MUSHROOM_CAPS, WISESTONE, WISESTONE_PEDESTAL, FLUID_PIPES, STEAM_PIPES, ORBITAL_FLUID_RETAINER, ALCHEMY_FURNACE, STEAM_THERMAL_STORAGE, ALCHEMY_MACHINE, ALCHEMY_OIL, MUSIC_DISC_ARCANUM, MUSIC_DISC_MOR, ALCHEMY_CALX, ALCHEMY_GLASS, ALCHEMY_BAG, ALCHEMY_POTIONS, TEA, JAM, NETHER_SALT, BLAZING_WAND, PANCAKES, PIES, ALCHEMY_BREWS, ADVANCED_CALX, ALCHEMY_TRANSMUTATION, ARCANE_CENSER, SMOKING_PIPE, ARCACITE, ARCACITE_POLISHING_MIXTURE, SOUL_HUNTER_TRIM, IMPLOSION_TRIM, SNIFFALO, TORCHFLOWER, PITCHER, OLD_ROOTS, SHINY_CLOVER, UNDERGROUND_GRAPE, CORK_BAMBOO, BREWING,
            KEG, VODKA_BOTTLE, BOURBON_BOTTLE, WHISKEY_BOTTLE, WHITE_WINE_BOTTLE, RED_WINE_BOTTLE, PORT_WINE_BOTTLE, PALM_LIQUEUR_BOTTLE, MEAD_BOTTLE, SBITEN_BOTTLE, SLIVOVITZ_BOTTLE, SAKE_BOTTLE, SOJU_BOTTLE, CHICHA_BOTTLE, CHACHA_BOTTLE, APPLEJACK_BOTTLE, RAKIA_BOTTLE, KIRSCH_BOTTLE, BOROVICHKA_BOTTLE, PALINKA_BOTTLE, TEQUILA_BOTTLE, PULQUE_BOTTLE, ARKHI_BOTTLE, TEJ_BOTTLE, WISSEN_BEER_BOTTLE, MOR_TINCTURE_BOTTLE, INNOCENT_WINE_BOTTLE, TARKHUNA_BOTTLE, BAIKAL_BOTTLE, KVASS_BOTTLE, KISSEL_BOTTLE, ROTTEN_DRINK_BOTTLE,
            PROGRESSION, STATISTIC, CONFIGS, SPECIAL_THANKS, GRAPHICS_CONFIGS, ANIMATIONS_CONFIGS, PARTICLES_CONFIGS, ARCANEMICON_CONFIGS, NUMERICAL_CONFIGS, OVERLAY_CONFIGS, ARCANE_WAND_OVERLAY_CONFIGS;
    public static ResearchPage RESEARCH_MAIN, RESEARCH_LIST;

    public static ItemStack EMPTY_ITEM,
            ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANUM_ITEM, ARCANUM_DUST_ITEM, ARCACITE_ITEM,
            NETHER_SALT_ITEM, NETHER_SALT_PILE_ITEM,
            ALCHEMY_CALX_ITEM, NATURAL_CALX_ITEM, SCORCHED_CALX_ITEM, DISTANT_CALX_ITEM, ENCHANTED_CALX_ITEM,
            ALCHEMY_CALX_PILE_ITEM, NATURAL_CALX_PILE_ITEM, SCORCHED_CALX_PILE_ITEM, DISTANT_CALX_PILE_ITEM, ENCHANTED_CALX_PILE_ITEM,
            ARCANE_LINEN_ITEM,
            ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_BRANCH_ITEM,
            INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_SLAB_ITEM, INNOCENT_WOOD_BRANCH_ITEM,
            CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_SLAB_ITEM, CORK_BAMBOO_CHISELED_PLANKS_ITEM, CORK_BAMBOO_CHISELED_SLAB_ITEM,
            WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
            ARCANE_PEDESTAL_ITEM, INNOCENT_PEDESTAL_ITEM, CORK_BAMBOO_PEDESTAL_ITEM, WISESTONE_PEDESTAL_ITEM,
            TOTEM_BASE_ITEM,
            ACLHEMY_GLASS,
            ARCANUM_LENS_ITEM,
            RUNIC_PEDESTAL_ITEM;

    public static void itemsInit() {
        EMPTY_ITEM = ItemStack.EMPTY;
        NETHER_SALT_ITEM = new ItemStack(WizardsRebornItems.NETHER_SALT.get());
        NETHER_SALT_PILE_ITEM = new ItemStack(WizardsRebornItems.NETHER_SALT_PILE.get());
        ACLHEMY_GLASS = new ItemStack(WizardsRebornItems.ALCHEMY_GLASS.get());
        ALCHEMY_CALX_ITEM = new ItemStack(WizardsRebornItems.ALCHEMY_CALX.get());
        NATURAL_CALX_ITEM = new ItemStack(WizardsRebornItems.NATURAL_CALX.get());
        SCORCHED_CALX_ITEM = new ItemStack(WizardsRebornItems.SCORCHED_CALX.get());
        DISTANT_CALX_ITEM = new ItemStack(WizardsRebornItems.DISTANT_CALX.get());
        ENCHANTED_CALX_ITEM = new ItemStack(WizardsRebornItems.ENCHANTED_CALX.get());
        ALCHEMY_CALX_PILE_ITEM = new ItemStack(WizardsRebornItems.ALCHEMY_CALX_PILE.get());
        NATURAL_CALX_PILE_ITEM = new ItemStack(WizardsRebornItems.NATURAL_CALX_PILE.get());
        SCORCHED_CALX_PILE_ITEM = new ItemStack(WizardsRebornItems.SCORCHED_CALX_PILE.get());
        DISTANT_CALX_PILE_ITEM = new ItemStack(WizardsRebornItems.DISTANT_CALX_PILE.get());
        ENCHANTED_CALX_PILE_ITEM = new ItemStack(WizardsRebornItems.ENCHANTED_CALX_PILE.get());
        ARCANE_GOLD_INGOT_ITEM = new ItemStack(WizardsRebornItems.ARCANE_GOLD_INGOT.get());
        ARCANE_GOLD_NUGGET_ITEM = new ItemStack(WizardsRebornItems.ARCANE_GOLD_NUGGET.get());
        ARCANUM_ITEM = new ItemStack(WizardsRebornItems.ARCANUM.get());
        ARCANUM_DUST_ITEM = new ItemStack(WizardsRebornItems.ARCANUM_DUST.get());
        ARCACITE_ITEM = new ItemStack(WizardsRebornItems.ARCACITE.get());
        ARCANE_LINEN_ITEM = new ItemStack(WizardsRebornItems.ARCANE_LINEN.get());
        ARCANE_WOOD_PLANKS_ITEM = new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS.get());
        ARCANE_WOOD_SLAB_ITEM = new ItemStack(WizardsRebornItems.ARCANE_WOOD_SLAB.get());
        ARCANE_WOOD_BRANCH_ITEM = new ItemStack(WizardsRebornItems.ARCANE_WOOD_BRANCH.get());
        INNOCENT_WOOD_PLANKS_ITEM = new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PLANKS.get());
        INNOCENT_WOOD_SLAB_ITEM = new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SLAB.get());
        INNOCENT_WOOD_BRANCH_ITEM = new ItemStack(WizardsRebornItems.INNOCENT_WOOD_BRANCH.get());
        CORK_BAMBOO_PLANKS_ITEM = new ItemStack(WizardsRebornItems.CORK_BAMBOO_PLANKS.get());
        CORK_BAMBOO_SLAB_ITEM = new ItemStack(WizardsRebornItems.CORK_BAMBOO_SLAB.get());
        CORK_BAMBOO_CHISELED_PLANKS_ITEM = new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS.get());
        CORK_BAMBOO_CHISELED_SLAB_ITEM = new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHISELED_SLAB.get());
        WISESTONE_ITEM = new ItemStack(WizardsRebornItems.WISESTONE.get());
        POLISHED_WISESTONE_ITEM = new ItemStack(WizardsRebornItems.POLISHED_WISESTONE.get());
        POLISHED_WISESTONE_SLAB_ITEM = new ItemStack(WizardsRebornItems.POLISHED_WISESTONE_SLAB.get());
        ARCANE_PEDESTAL_ITEM = new ItemStack(WizardsRebornItems.ARCANE_PEDESTAL.get());
        INNOCENT_PEDESTAL_ITEM = new ItemStack(WizardsRebornItems.INNOCENT_PEDESTAL.get());
        CORK_BAMBOO_PEDESTAL_ITEM = new ItemStack(WizardsRebornItems.CORK_BAMBOO_PEDESTAL.get());
        WISESTONE_PEDESTAL_ITEM = new ItemStack(WizardsRebornItems.WISESTONE_PEDESTAL.get());
        TOTEM_BASE_ITEM = new ItemStack(WizardsRebornItems.TOTEM_BASE.get());
        ARCANUM_LENS_ITEM = new ItemStack(WizardsRebornItems.ARCANUM_LENS.get());
        RUNIC_PEDESTAL_ITEM = new ItemStack(WizardsRebornItems.RUNIC_PEDESTAL.get());
    }

    public static void arcaneNatureInit() {
        ARCANUM = new Chapter("wizards_reborn.arcanemicon.chapter.arcanum",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANUM_ITEM),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANUM_DUST_ITEM),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANUM_BLOCK.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANUM_ORE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.DEEPSLATE_ARCANUM_ORE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANUM_DUST_BLOCK.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANUM_BLOCK.get()),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANUM.get(), 9), new ItemStack(WizardsRebornItems.ARCANUM_BLOCK.get())),
                new SmeltingPage(ARCANUM_ITEM, new ItemStack(WizardsRebornItems.ARCANUM_ORE.get())),
                new SmeltingPage(ARCANUM_ITEM, new ItemStack(WizardsRebornItems.DEEPSLATE_ARCANUM_ORE.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANUM_DUST_BLOCK.get()),
                        ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM,
                        ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM,
                        ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANUM_DUST.get(), 9), new ItemStack(WizardsRebornItems.ARCANUM_DUST_BLOCK.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANUM_DUST.get(), 2), ARCANUM_ITEM),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANUM_DUST.get(), 4), ARCANUM_ITEM, new ItemStack(Items.REDSTONE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANEMICON.get()), new ItemStack(Items.BOOK), ARCANUM_ITEM)
        );

        ARCANUM_DUST_TRANSMUTATION = new Chapter("wizards_reborn.arcanemicon.chapter.arcanum_dust_transmutation",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum_dust_transmutation",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANUM_DUST_ITEM))
        );

        ARCANE_WOOD = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_wood",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_sapling",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_SAPLING.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_SAPLING.get()), new ItemStack(Items.OAK_SAPLING)),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood",
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD.get())),
                        new BlockEntry(ARCANE_WOOD_PLANKS_ITEM),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_STAIRS.get())),
                        new BlockEntry(ARCANE_WOOD_SLAB_ITEM),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_FENCE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_FENCE_GATE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_DOOR.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_TRAPDOOR.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_PRESSURE_PLATE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_BUTTON.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_SIGN.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_HANGING_SIGN.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOAT.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WOOD_CHEST_BOAT.get())),
                        new BlockEntry(ARCANE_WOOD_BRANCH_ITEM)
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD.get()),
                        new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD.get()),
                        new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS.get(), 4), new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_STAIRS.get(), 4),
                        ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_SLAB.get(), 6),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_FENCE.get(), 3),
                        ARCANE_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK), ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK), ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_FENCE_GATE.get()),
                        new ItemStack(Items.STICK), ARCANE_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK),
                        new ItemStack(Items.STICK), ARCANE_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_DOOR.get(), 3),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_TRAPDOOR.get(), 2),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_PRESSURE_PLATE.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_BUTTON.get()), ARCANE_WOOD_PLANKS_ITEM),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_SIGN.get(), 3),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        EMPTY_ITEM, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_HANGING_SIGN.get(), 6),
                        new ItemStack(Items.CHAIN), EMPTY_ITEM, new ItemStack(Items.CHAIN),
                        new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()),
                        new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOAT.get()),
                        ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_CHEST_BOAT.get()), new ItemStack(Items.CHEST), new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOAT.get())),
                new CraftingTablePage(ARCANE_WOOD_BRANCH_ITEM,
                        new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get())
                ),
                new CraftingTablePage(ARCANE_WOOD_BRANCH_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_baulks",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_BAULK.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_BAULK.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS_BAULK.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_BAULK.get(), 6),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()), EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_BAULK.get(), 6),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG.get()), EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS_BAULK.get(), 6),
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_tools",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_SWORD.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_PICKAXE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_AXE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_SHOVEL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_HOE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_SCYTHE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_SWORD.get()),
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_PICKAXE.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_AXE.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_SHOVEL.get()),
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_HOE.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_SCYTHE.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_BRANCH_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM, EMPTY_ITEM
                )
        );

        ARCANE_GOLD = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_gold",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANE_GOLD_INGOT_ITEM),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANE_GOLD_NUGGET_ITEM),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_BLOCK.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_ORE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.DEEPSLATE_ARCANE_GOLD_ORE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.NETHER_ARCANE_GOLD_ORE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD_BLOCK.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_ORE.get()), new ItemStack(Items.GOLD_ORE)),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsRebornItems.DEEPSLATE_ARCANE_GOLD_ORE.get()), new ItemStack(Items.DEEPSLATE_GOLD_ORE)),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsRebornItems.NETHER_ARCANE_GOLD_ORE.get()), new ItemStack(Items.NETHER_GOLD_ORE)),
                new SmeltingPage(ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get())),
                new SmeltingPage(ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_ORE.get())),
                new SmeltingPage(ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsRebornItems.DEEPSLATE_ARCANE_GOLD_ORE.get())),
                new SmeltingPage(ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsRebornItems.NETHER_ARCANE_GOLD_ORE.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_BLOCK.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM
                ),
                new CraftingTablePage(ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD_BLOCK.get()),
                        new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get()),
                        new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get()),
                        new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_INGOT.get(), 9), new ItemStack(WizardsRebornItems.ARCANE_GOLD_BLOCK.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_NUGGET.get(), 9), ARCANE_GOLD_INGOT_ITEM),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get(), 9), new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD_BLOCK.get())),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold_tools",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_SWORD.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_PICKAXE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_AXE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_SHOVEL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_HOE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_SCYTHE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_SWORD.get()),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_PICKAXE.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_AXE.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_SHOVEL.get()),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_HOE.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_SCYTHE.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_BRANCH_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM, EMPTY_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold_armor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_HELMET.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_CHESTPLATE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_LEGGINGS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_BOOTS.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_HELMET.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_CHESTPLATE.get()),
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_LEGGINGS.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_BOOTS.get()),
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold_carrot",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_CARROT.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_CARROT.get()),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, new ItemStack(Items.CARROT), ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.gilded_arcane_wood_planks",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GILDED_ARCANE_WOOD_PLANKS.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GILDED_ARCANE_WOOD_PLANKS.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_NUGGET_ITEM
                )
        );

        SCYTHES = new Chapter("wizards_reborn.arcanemicon.chapter.scythes",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.scythes",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_SCYTHE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_SCYTHE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_SCYTHE.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_BRANCH_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_SCYTHE.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_BRANCH_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM, EMPTY_ITEM
                )
        );

        TRINKETS = new Chapter("wizards_reborn.arcanemicon.chapter.trinkets",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.trinkets",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANUM_AMULET.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANUM_RING.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.LEATHER_BELT.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANUM_AMULET.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANUM_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANUM_RING.get()),
                        ARCANUM_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.LEATHER_BELT.get()),
                        EMPTY_ITEM, new ItemStack(Items.LEATHER), EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), EMPTY_ITEM, new ItemStack(Items.LEATHER),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM
                )
        );

        ARCANE_WOOD_BOW = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_wood_bow",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_bow",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOW.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOW.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_BRANCH_ITEM, new ItemStack(Items.STRING),
                        ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM, new ItemStack(Items.STRING),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_BRANCH_ITEM, new ItemStack(Items.STRING)
                )
        );

        ARCANE_WOOD_CROSSBOW = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_wood_crossbow",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_crossbow",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_CROSSBOW.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_CROSSBOW.get()),
                        ARCANE_WOOD_BRANCH_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_BRANCH_ITEM,
                        new ItemStack(Items.STRING), new ItemStack(Items.TRIPWIRE_HOOK), new ItemStack(Items.STRING),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANE_GOLD_INGOT_ITEM
                )
        );

        ARCANE_WOOD_FISHING_ROD = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_wood_fishing_rod",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_fishing_rod",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_FISHING_ROD.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_FISHING_ROD.get()),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_BRANCH_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_BRANCH_ITEM, new ItemStack(Items.STRING),
                        ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM, new ItemStack(Items.STRING)
                )
        );

        ARCANE_GOLD_SHEARS = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_gold_shears",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold_shears",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_SHEARS.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_SHEARS.get()),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM
                )
        );

        BANNER_PATTERNS = new Chapter("wizards_reborn.arcanemicon.chapter.banner_patterns",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.banner_patterns",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.VIOLENCE_BANNER_PATTERN.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.REPRODUCTION_BANNER_PATTERN.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.COOPERATION_BANNER_PATTERN.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.HUNGER_BANNER_PATTERN.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SURVIVAL_BANNER_PATTERN.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ASCENSION_BANNER_PATTERN.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.VIOLENCE_BANNER_PATTERN.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.RED_WOOL), ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.REPRODUCTION_BANNER_PATTERN.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.PINK_WOOL), ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.COOPERATION_BANNER_PATTERN.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.BLUE_WOOL), ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.HUNGER_BANNER_PATTERN.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.BROWN_WOOL), ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.SURVIVAL_BANNER_PATTERN.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.GREEN_WOOL), ARCANUM_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ASCENSION_BANNER_PATTERN.get()),
                        new ItemStack(Items.PAPER), new ItemStack(Items.YELLOW_WOOL), ARCANUM_ITEM
                )
        );

        WISSEN = new Chapter("wizards_reborn.arcanemicon.chapter.wissen",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_CRYSTALLIZER.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WORKBENCH.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_wand",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_WAND.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_wand_mode.functional",
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISSEN_CRYSTALLIZER.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ARCANE_WORKBENCH.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_wand_mode.receive_connect",
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get())),
                        new BlockEntry(),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_wand_mode.send_connect",
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get())),
                        new BlockEntry(),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISSEN_CRYSTALLIZER.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.wissen_wand_mode.reload"),
                new TitlePage("wizards_reborn.arcanemicon.page.wissen_wand_mode.off"),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISSEN_WAND.get()),
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANUM_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANE_WOOD_BRANCH_ITEM,
                        ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM, EMPTY_ITEM
                ),
                new ImagePage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/wissen_image_page.png"))
        );

        WISSEN_TRANSLATOR = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_translator",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_translator",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get(), 2),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM
                )
        );

        ARCANE_PEDESTAL = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_pedestal",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_pedestal",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANE_PEDESTAL_ITEM)
                ),
                new CraftingTablePage(ARCANE_PEDESTAL_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM
                )
        );

        WISSEN_ALTAR = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_altar",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_altar.0",
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()), ARCANUM_ITEM),
                        new BlockEntry(),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get())),
                        new BlockEntry(),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()), ARCANUM_DUST_ITEM)
                ),
                new TextPage("wizards_reborn.arcanemicon.page.wissen_altar.1"),
                new WissenAltarPage(ARCANUM_ITEM),
                new WissenAltarPage(ARCANUM_DUST_ITEM),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()),
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_PEDESTAL_ITEM, ARCANE_GOLD_NUGGET_ITEM
                )
        );

        WISSEN_CRYSTALLIZER = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_crystallizer",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_crystallizer",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_CRYSTALLIZER.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISSEN_CRYSTALLIZER.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM
                )
        );

        ARCANE_WORKBENCH = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_workbench",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_workbench",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WORKBENCH.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WORKBENCH.get()),
                        new ItemStack(Items.RED_WOOL), new ItemStack(Items.RED_WOOL), new ItemStack(Items.RED_WOOL),
                        ARCANE_GOLD_INGOT_ITEM, ARCANUM_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                )
        );

        MUSIC_DISC_SHIMMER = new Chapter("wizards_reborn.arcanemicon.chapter.music_disc_shimmer",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.music_disc_shimmer.0",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MUSIC_DISC_SHIMMER.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.music_disc_shimmer.1"),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.MUSIC_DISC_SHIMMER.get()),
                        new ItemStack(Items.MUSIC_DISC_13), new ItemStack(Items.COBBLED_DEEPSLATE), ARCANUM_ITEM
                )
        );

        ARCANE_LUMOS = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_lumos",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_lumos.0",
                        new BlockEntry(new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIGHT_GRAY_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.GRAY_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BLACK_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BROWN_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.RED_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ORANGE_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.YELLOW_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIME_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.GREEN_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CYAN_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIGHT_BLUE_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BLUE_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.PURPLE_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.MAGENTA_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.PINK_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.RAINBOW_ARCANE_LUMOS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.COSMIC_ARCANE_LUMOS.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.arcane_lumos.1"),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()),
                        ARCANUM_ITEM, new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.GLOWSTONE_DUST)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.RAINBOW_ARCANE_LUMOS.get(), 8),
                        new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.RED_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.ORANGE_ARCANE_LUMOS.get()),
                        new ItemStack(WizardsRebornItems.YELLOW_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.LIME_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.LIGHT_BLUE_ARCANE_LUMOS.get()),
                        new ItemStack(WizardsRebornItems.BLUE_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.PURPLE_ARCANE_LUMOS.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.COSMIC_ARCANE_LUMOS.get()),
                        new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.AMETHYST_SHARD)
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.WHITE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.LIGHT_GRAY_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.LIGHT_GRAY_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GRAY_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.GRAY_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.BLACK_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.BLACK_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.BROWN_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.BROWN_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.RED_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.RED_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ORANGE_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.ORANGE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.YELLOW_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.YELLOW_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.LIME_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.LIME_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GREEN_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.GREEN_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CYAN_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.CYAN_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.LIGHT_BLUE_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.LIGHT_BLUE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.BLUE_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.BLUE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.PURPLE_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.PURPLE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.MAGENTA_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.MAGENTA_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.PINK_ARCANE_LUMOS.get()), new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), new ItemStack(Items.PINK_DYE))
        );

        CRYSTALS = new Chapter("wizards_reborn.arcanemicon.chapter.crystals",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystal_seeds",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.EARTH_CRYSTAL_SEED.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WATER_CRYSTAL_SEED.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.AIR_CRYSTAL_SEED.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FIRE_CRYSTAL_SEED.get()))
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.EARTH_CRYSTAL_SEED.get()),
                        ARCANUM_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.DIRT), new ItemStack(Items.STONE)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.WATER_CRYSTAL_SEED.get()),
                        ARCANUM_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.SUGAR_CANE), new ItemStack(Items.COD)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.AIR_CRYSTAL_SEED.get()),
                        ARCANUM_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.FEATHER), new ItemStack(Items.WHITE_WOOL)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.FIRE_CRYSTAL_SEED.get()),
                        ARCANUM_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.COAL), new ItemStack(Items.BLAZE_POWDER)
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystals",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WATER_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.AIR_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FIRE_CRYSTAL.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()), new ItemStack(WizardsRebornItems.EARTH_CRYSTAL_SEED.get())),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsRebornItems.WATER_CRYSTAL.get()), new ItemStack(WizardsRebornItems.WATER_CRYSTAL_SEED.get())),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsRebornItems.AIR_CRYSTAL.get()), new ItemStack(WizardsRebornItems.AIR_CRYSTAL_SEED.get())),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsRebornItems.FIRE_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FIRE_CRYSTAL_SEED.get())),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.fractured_crystals",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FRACTURED_EARTH_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FRACTURED_WATER_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FRACTURED_AIR_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FRACTURED_FIRE_CRYSTAL.get()))
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()),
                        ARCANUM_ITEM, new ItemStack(WizardsRebornItems.FRACTURED_EARTH_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FRACTURED_EARTH_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FRACTURED_EARTH_CRYSTAL.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.WATER_CRYSTAL.get()),
                        ARCANUM_ITEM, new ItemStack(WizardsRebornItems.FRACTURED_WATER_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FRACTURED_WATER_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FRACTURED_WATER_CRYSTAL.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.AIR_CRYSTAL.get()),
                        ARCANUM_ITEM, new ItemStack(WizardsRebornItems.FRACTURED_AIR_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FRACTURED_AIR_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FRACTURED_AIR_CRYSTAL.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.FIRE_CRYSTAL.get()),
                        ARCANUM_ITEM, new ItemStack(WizardsRebornItems.FRACTURED_FIRE_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FRACTURED_FIRE_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FRACTURED_FIRE_CRYSTAL.get())
                )
        );

        ARCANE_WAND = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_wand",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wand.0",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WAND.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand.focus"),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand.balance"),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand.absorption"),
                new TextPage("wizards_reborn.arcanemicon.page.arcane_wand.1"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WAND.get()),
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                )
        );

        ARCANE_LEVER = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_lever",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_lever",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_LEVER.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_LEVER.get(), 4),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM
                )
        );

        ARCANE_HOPPER = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_hopper",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_hopper",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_HOPPER.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_HOPPER.get()),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, new ItemStack(Items.HOPPER), ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_FRAME.get()), ARCANE_WOOD_PLANKS_ITEM
                )
        );

        REDSTONE_SENSOR = new Chapter("wizards_reborn.arcanemicon.chapter.redstone_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.redstone_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get()),
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM,
                        new ItemStack(Items.COMPARATOR), EMPTY_ITEM, new ItemStack(Items.REDSTONE)
                )
        );

        WISSEN_SENSOR = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_SENSOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISSEN_SENSOR.get()),
                        EMPTY_ITEM, ARCANUM_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get()), EMPTY_ITEM,
                        EMPTY_ITEM, ARCANUM_ITEM
                )
        );

        COOLDOWN_SENSOR = new Chapter("wizards_reborn.arcanemicon.chapter.cooldown_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.cooldown_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.COOLDOWN_SENSOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.COOLDOWN_SENSOR.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get())
                )
        );

        EXPERIENCE_SENSOR = new Chapter("wizards_reborn.arcanemicon.chapter.experience_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.experience_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.EXPERIENCE_SENSOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.EXPERIENCE_SENSOR.get()),
                        EMPTY_ITEM, ENCHANTED_CALX_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get())
                )
        );

        LIGHT_SENSOR = new Chapter("wizards_reborn.arcanemicon.chapter.light_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.light_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.LIGHT_SENSOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.LIGHT_SENSOR.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get())
                )
        );

        HEAT_SENSOR = new Chapter("wizards_reborn.arcanemicon.chapter.heat_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.heat_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.HEAT_SENSOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.HEAT_SENSOR.get()),
                        EMPTY_ITEM, new ItemStack(Items.FURNACE), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get())
                )
        );

        FLUID_SENSOR = new Chapter("wizards_reborn.arcanemicon.chapter.fluid_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.fluid_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FLUID_SENSOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.FLUID_SENSOR.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.FLUID_PIPE.get())
                )
        );

        STEAM_SENSOR = new Chapter("wizards_reborn.arcanemicon.chapter.steam_sensor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.steam_sensor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.STEAM_SENSOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.STEAM_SENSOR.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.STEAM_PIPE.get())
                )
        );

        WISSEN_ACTIVATOR = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_activator",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_activator",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_ACTIVATOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISSEN_ACTIVATOR.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISSEN_WAND.get()), EMPTY_ITEM,
                        ARCANUM_ITEM, new ItemStack(WizardsRebornItems.WISSEN_SENSOR.get()), ARCANUM_ITEM
                )
        );

        ITEM_SORTER = new Chapter("wizards_reborn.arcanemicon.chapter.item_sorter",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.item_sorter",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ITEM_SORTER.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ITEM_SORTER.get()),
                        EMPTY_ITEM, new ItemStack(Items.CHEST), EMPTY_ITEM,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get()), ACLHEMY_GLASS,
                        EMPTY_ITEM, new ItemStack(Items.REDSTONE_BLOCK)
                )
        );

        ARCANE_WOOD_FRAME = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_wood_frame",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_FRAME.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_FRAME.get()),
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_SLAB_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_CASING.get()),
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_FRAME.get())
                )
        );

        WISSEN_CASING = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_WISSEN_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_WISSEN_CASING.get()),
                        new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get())
                )
        );

        WISESTONE_CASING = new Chapter("wizards_reborn.arcanemicon.chapter.wisestone_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_FRAME.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISESTONE_FRAME.get()),
                        POLISHED_WISESTONE_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, POLISHED_WISESTONE_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISESTONE_CASING.get()),
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_FRAME.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_wissen_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_WISSEN_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISESTONE_WISSEN_CASING.get()),
                        new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get())
                )
        );

        FLUID_CASING = new Chapter("wizards_reborn.arcanemicon.chapter.fluid_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_fluid_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_FLUID_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_FLUID_CASING.get()),
                        new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_fluid_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_FLUID_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISESTONE_FLUID_CASING.get()),
                        new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get())
                )
        );

        STEAM_CASING = new Chapter("wizards_reborn.arcanemicon.chapter.steam_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_steam_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_STEAM_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_STEAM_CASING.get()),
                        new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_steam_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_STEAM_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISESTONE_STEAM_CASING.get()),
                        new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get())
                )
        );

        GLASS_FRAME = new Chapter("wizards_reborn.arcanemicon.chapter.glass_frame",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_glass_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_GLASS_FRAME.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_GLASS_FRAME.get()),
                        EMPTY_ITEM, ACLHEMY_GLASS, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_FRAME.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_glass_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_GLASS_FRAME.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISESTONE_GLASS_FRAME.get()),
                        EMPTY_ITEM, ACLHEMY_GLASS, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_FRAME.get())
                )
        );

        LIGHT_CASING = new Chapter("wizards_reborn.arcanemicon.chapter.light_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_light_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_LIGHT_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_LIGHT_CASING.get()),
                        new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_light_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_LIGHT_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISESTONE_LIGHT_CASING.get()),
                        new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get())
                )
        );

        INNOCENT_CASING = new Chapter("wizards_reborn.arcanemicon.chapter.innocent_wood_casing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_FRAME.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_FRAME.get()),
                        INNOCENT_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, INNOCENT_WOOD_SLAB_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        INNOCENT_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM, INNOCENT_WOOD_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CASING.get()),
                        EMPTY_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_FRAME.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_wissen_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_WISSEN_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_WISSEN_CASING.get()),
                        new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_fluid_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_FLUID_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_FLUID_CASING.get()),
                        new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_steam_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_STEAM_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_STEAM_CASING.get()),
                        new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(WizardsRebornItems.STEAM_PIPE.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_glass_frame",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_GLASS_FRAME.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_GLASS_FRAME.get()),
                        EMPTY_ITEM, ACLHEMY_GLASS, EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_FRAME.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_light_casing",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LIGHT_CASING.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LIGHT_CASING.get()),
                        new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CASING.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get())
                )
        );

        AUTOMATION = new Chapter("wizards_reborn.arcanemicon.chapter.automation",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.automation"),
                new IndexPage(
                        new IndexEntry(ARCANE_LEVER, new ItemStack(WizardsRebornItems.ARCANE_LEVER.get()), WizardsRebornKnowledges.WISESTONE),
                        new IndexEntry(ARCANE_HOPPER, new ItemStack(WizardsRebornItems.ARCANE_HOPPER.get())),
                        new IndexEntry(REDSTONE_SENSOR, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get())),
                        new IndexEntry(WISSEN_SENSOR, new ItemStack(WizardsRebornItems.WISSEN_SENSOR.get())),
                        new IndexEntry(COOLDOWN_SENSOR, new ItemStack(WizardsRebornItems.COOLDOWN_SENSOR.get())),
                        new IndexEntry(EXPERIENCE_SENSOR, new ItemStack(WizardsRebornItems.EXPERIENCE_SENSOR.get()), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(LIGHT_SENSOR, new ItemStack(WizardsRebornItems.LIGHT_SENSOR.get()), WizardsRebornKnowledges.LIGHT_EMITTER)
                ),
                new IndexPage(
                        new IndexEntry(HEAT_SENSOR, new ItemStack(WizardsRebornItems.HEAT_SENSOR.get()), WizardsRebornKnowledges.ALCHEMY_FURNACE),
                        new IndexEntry(FLUID_SENSOR, new ItemStack(WizardsRebornItems.FLUID_SENSOR.get()), WizardsRebornKnowledges.FLUID_PIPE),
                        new IndexEntry(STEAM_SENSOR, new ItemStack(WizardsRebornItems.STEAM_SENSOR.get()), WizardsRebornKnowledges.STEAM_PIPE),
                        new IndexEntry(WISSEN_ACTIVATOR, new ItemStack(WizardsRebornItems.WISSEN_ACTIVATOR.get())),
                        new IndexEntry(ITEM_SORTER, new ItemStack(WizardsRebornItems.ITEM_SORTER.get()), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(ARCANE_WOOD_FRAME, new ItemStack(WizardsRebornItems.ARCANE_WOOD_FRAME.get())),
                        new IndexEntry(WISSEN_CASING, new ItemStack(WizardsRebornItems.ARCANE_WOOD_WISSEN_CASING.get()))
                ),
                new IndexPage(
                        new IndexEntry(WISESTONE_CASING, new ItemStack(WizardsRebornItems.WISESTONE_CASING.get()), WizardsRebornKnowledges.WISESTONE),
                        new IndexEntry(FLUID_CASING, new ItemStack(WizardsRebornItems.ARCANE_WOOD_FLUID_CASING.get()), WizardsRebornKnowledges.FLUID_PIPE),
                        new IndexEntry(STEAM_CASING, new ItemStack(WizardsRebornItems.ARCANE_WOOD_STEAM_CASING.get()), WizardsRebornKnowledges.STEAM_PIPE),
                        new IndexEntry(GLASS_FRAME, new ItemStack(WizardsRebornItems.ARCANE_WOOD_GLASS_FRAME.get()), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(LIGHT_CASING, new ItemStack(WizardsRebornItems.ARCANE_WOOD_LIGHT_CASING.get()), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(INNOCENT_CASING, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CASING.get()), WizardsRebornKnowledges.CRYSTAL_INFUSION)
                )
        );

        WISSEN_CELL = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_cell",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_cell.0",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_CELL.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.wissen_cell.1"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISSEN_CELL.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()), EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, new ItemStack(WizardsRebornItems.ARCANUM_BLOCK.get()), ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANUM_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM
                )
        );

        WISSEN_CHARGER = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_charger",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_charger",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_CHARGER.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISSEN_CHARGER.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()), EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, new ItemStack(WizardsRebornItems.ARCANUM_BLOCK.get()), ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANUM_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM
                )
        );

        CRYSTAL_BAG = new Chapter("wizards_reborn.arcanemicon.chapter.crystal_bag",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystal_bag",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CRYSTAL_BAG.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.CRYSTAL_BAG.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.LEATHER_BELT.get()), EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(Items.CHEST), new ItemStack(Items.LEATHER),
                        new ItemStack(WizardsRebornItems.EARTH_CRYSTAL_SEED.get()), new ItemStack(Items.LEATHER), new ItemStack(WizardsRebornItems.EARTH_CRYSTAL_SEED.get()),
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ENCHANTED_CALX_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.CRYSTAL_BAG.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.LEATHER_BELT.get()), EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(Items.CHEST), new ItemStack(Items.LEATHER),
                        new ItemStack(WizardsRebornItems.WATER_CRYSTAL_SEED.get()), new ItemStack(Items.LEATHER), new ItemStack(WizardsRebornItems.WATER_CRYSTAL_SEED.get()),
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ENCHANTED_CALX_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.CRYSTAL_BAG.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.LEATHER_BELT.get()), EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(Items.CHEST), new ItemStack(Items.LEATHER),
                        new ItemStack(WizardsRebornItems.AIR_CRYSTAL_SEED.get()), new ItemStack(Items.LEATHER), new ItemStack(WizardsRebornItems.AIR_CRYSTAL_SEED.get()),
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ENCHANTED_CALX_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.CRYSTAL_BAG.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.LEATHER_BELT.get()), EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(Items.CHEST), new ItemStack(Items.LEATHER),
                        new ItemStack(WizardsRebornItems.FIRE_CRYSTAL_SEED.get()), new ItemStack(Items.LEATHER), new ItemStack(WizardsRebornItems.FIRE_CRYSTAL_SEED.get()),
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ENCHANTED_CALX_ITEM
                )
        );

        TOTEM_OF_FLAMES = new Chapter("wizards_reborn.arcanemicon.chapter.totem_of_flames",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.totem_of_flames",
                        new BlockEntry(TOTEM_BASE_ITEM, new ItemStack(WizardsRebornItems.TOTEM_OF_FLAMES.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.TOTEM_OF_FLAMES.get()),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ALCHEMY_CALX_PILE_ITEM, new ItemStack(Items.COAL), ALCHEMY_CALX_PILE_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM
                )
        );

        EXPERIENCE_TOTEM = new Chapter("wizards_reborn.arcanemicon.chapter.experience_totem",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.experience_totem",
                        new BlockEntry(TOTEM_BASE_ITEM, new ItemStack(WizardsRebornItems.EXPERIENCE_TOTEM.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.EXPERIENCE_TOTEM.get()),
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ENCHANTED_CALX_ITEM, EMPTY_ITEM, ENCHANTED_CALX_ITEM
                )
        );

        TOTEM_OF_EXPERIENCE_ABSORPTION = new Chapter("wizards_reborn.arcanemicon.chapter.totem_of_experience_absorption",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.totem_of_experience_absorption",
                        new BlockEntry(TOTEM_BASE_ITEM, new ItemStack(WizardsRebornItems.TOTEM_OF_EXPERIENCE_ABSORPTION.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.TOTEM_OF_EXPERIENCE_ABSORPTION.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCACITE_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM
                )
        );

        TOTEM_OF_DISENCHANT = new Chapter("wizards_reborn.arcanemicon.chapter.totem_of_disenchant",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.totem_of_disenchant",
                        new BlockEntry(TOTEM_BASE_ITEM, new ItemStack(WizardsRebornItems.TOTEM_OF_DISENCHANT.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.TOTEM_OF_DISENCHANT.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCACITE_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM
                )
        );

        ALTAR_OF_DROUGHT = new Chapter("wizards_reborn.arcanemicon.chapter.altar_of_drought",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.altar_of_drought",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ALTAR_OF_DROUGHT.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ALTAR_OF_DROUGHT.get()),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, EMPTY_ITEM, ARCANE_WOOD_SLAB_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_SLAB_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, WISESTONE_PEDESTAL_ITEM, ARCACITE_ITEM
                ),
                new TextPage("wizards_reborn.arcanemicon.page.altar_of_drought.poem")
        );

        VOID_CRYSTAL = new Chapter("wizards_reborn.arcanemicon.chapter.void_crystal",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.void_crystal",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.VOID_CRYSTAL_SEED.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.VOID_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FRACTURED_VOID_CRYSTAL.get()))
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.VOID_CRYSTAL_SEED.get(), 2),
                        ARCACITE_ITEM, new ItemStack(WizardsRebornItems.EARTH_CRYSTAL_SEED.get()), new ItemStack(WizardsRebornItems.WATER_CRYSTAL_SEED.get()), new ItemStack(WizardsRebornItems.AIR_CRYSTAL_SEED.get()), new ItemStack(WizardsRebornItems.FIRE_CRYSTAL_SEED.get())
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsRebornItems.VOID_CRYSTAL.get()), new ItemStack(WizardsRebornItems.VOID_CRYSTAL_SEED.get())),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.VOID_CRYSTAL.get()),
                        ARCANUM_ITEM, new ItemStack(WizardsRebornItems.FRACTURED_VOID_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FRACTURED_VOID_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FRACTURED_VOID_CRYSTAL.get())
                )
        );

        ARCANE_FORTRESS_ARMOR = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_fortress_armor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_fortress_armor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_HELMET.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_CHESTPLATE.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_LEGGINGS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_BOOTS.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_fortress_armor.arcane_bastion"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_HELMET.get()),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_HELMET.get()), ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_CHESTPLATE.get()),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(WizardsRebornItems.ARCANE_GOLD_CHESTPLATE.get()), new ItemStack(Items.LEATHER),
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_LEGGINGS.get()),
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_LEGGINGS.get()), ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_BOOTS.get()),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsRebornItems.ARCANE_GOLD_BOOTS.get()), ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_fortress_belt",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_BELT.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_BELT.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.LEATHER_BELT.get()), EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, NATURAL_CALX_ITEM
                )
        );

        INVENTOR_WIZARD_ARMOR = new Chapter("wizards_reborn.arcanemicon.chapter.inventor_wizard_armor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.inventor_wizard_armor",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_HAT.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_COSTUME.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_TROUSERS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_BOOTS.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.inventor_wizard_armor.wissen_nature"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_HAT.get()),
                        new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), new ItemStack(WizardsRebornItems.ARCANE_GOLD_HELMET.get()), new ItemStack(Items.LEATHER),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        ARCACITE_ITEM, new ItemStack(Items.LEATHER), ARCACITE_ITEM, new ItemStack(Items.LEATHER)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_COSTUME.get()),
                        new ItemStack(Items.LEATHER), ARCANE_GOLD_NUGGET_ITEM, new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), new ItemStack(WizardsRebornItems.ARCANE_GOLD_CHESTPLATE.get()), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER),
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_TROUSERS.get()),
                        new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), new ItemStack(WizardsRebornItems.ARCANE_GOLD_LEGGINGS.get()), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), ARCANE_GOLD_NUGGET_ITEM, new ItemStack(Items.LEATHER),
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_BOOTS.get()),
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(WizardsRebornItems.ARCANE_GOLD_BOOTS.get()), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.LEATHER), EMPTY_ITEM, new ItemStack(Items.LEATHER),
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.inventor_wizard_belt",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_BELT.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_BELT.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.LEATHER_BELT.get()), EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANUM_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ENCHANTED_CALX_ITEM
                )
        );

        ARCANE_WOOD_CANE = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_wood_cane",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_cane",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_CANE.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_CANE.get()),
                        new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS.get()), EMPTY_ITEM,
                        ARCACITE_ITEM, EMPTY_ITEM, ARCACITE_ITEM
                )
        );

        ARCANE_ITERATOR = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_iterator",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_iterator.0",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_ITERATOR.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.arcane_iterator.1"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_ITERATOR.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsRebornItems.ARCACITE_BLOCK.get()), ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM
                ),
                new ImagePage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/arcane_iterator_image_page.png")),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_enchantments"),
                new TitlePage("wizards_reborn.arcanemicon.page.wissen_mending"),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_PICKAXE.get()), new ItemStack(WizardsRebornItems.ARCANE_GOLD_PICKAXE.get()),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ).setExperience(5).setArcaneEnchantment(WizardsRebornArcaneEnchantments.WISSEN_MENDING),
                new TitlePage("wizards_reborn.arcanemicon.page.magic_blade"),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_SWORD.get()), new ItemStack(WizardsRebornItems.ARCANE_GOLD_SWORD.get()),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ).setExperience(5).setArcaneEnchantment(WizardsRebornArcaneEnchantments.MAGIC_BLADE),
                new TitlePage("wizards_reborn.arcanemicon.page.throw"),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_SCYTHE.get()), new ItemStack(WizardsRebornItems.ARCANE_GOLD_SCYTHE.get()),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        ARCANUM_ITEM, ARCANUM_ITEM,
                        new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), ARCANE_GOLD_INGOT_ITEM
                ).setExperience(10).setArcaneEnchantment(WizardsRebornArcaneEnchantments.THROW),
                new TitlePage("wizards_reborn.arcanemicon.page.life_roots"),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_PICKAXE.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_PICKAXE.get()),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM,
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM,
                        new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get())
                ).setExperience(5).setHealth(5).setArcaneEnchantment(WizardsRebornArcaneEnchantments.LIFE_ROOTS),
                new TitlePage("wizards_reborn.arcanemicon.page.wissen_charge"),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOW.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOW.get()),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        ARCANUM_ITEM, ARCANUM_ITEM,
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.ARROW)
                ).setExperience(5).setArcaneEnchantment(WizardsRebornArcaneEnchantments.WISSEN_CHARGE),
                new TitlePage("wizards_reborn.arcanemicon.page.eagle_shot"),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOW.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOW.get()),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        ARCANUM_ITEM, new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD),
                        new ItemStack(Items.DIAMOND), new ItemStack(Items.ARROW)
                ).setExperience(5).setArcaneEnchantment(WizardsRebornArcaneEnchantments.EAGLE_SHOT),
                new TitlePage("wizards_reborn.arcanemicon.page.split"),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOW.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOW.get()),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        ARCANUM_ITEM, new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_ROD),
                        new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.ARROW)
                ).setExperience(5).setArcaneEnchantment(WizardsRebornArcaneEnchantments.SPLIT),
                new TitlePage("wizards_reborn.arcanemicon.page.sonar"),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_PICKAXE.get()), new ItemStack(WizardsRebornItems.ARCANE_GOLD_PICKAXE.get()),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        ARCANUM_ITEM, new ItemStack(Items.RAW_IRON), new ItemStack(Items.RAW_COPPER), new ItemStack(Items.RAW_GOLD), new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get())
                ).setExperience(5).setArcaneEnchantment(WizardsRebornArcaneEnchantments.SONAR),
                new TitlePage("wizards_reborn.arcanemicon.page.curse_arcane_enchantments"),
                new TitlePage("wizards_reborn.arcanemicon.page.life_mending"),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_PICKAXE.get()), new ItemStack(WizardsRebornItems.ARCANE_GOLD_PICKAXE.get()),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        ARCACITE_ITEM, ARCACITE_ITEM
                ).setExperience(5).setHealth(5).setArcaneEnchantment(WizardsRebornArcaneEnchantments.LIFE_MENDING),
                new TitlePage("wizards_reborn.arcanemicon.page.dual_blade"),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_SWORD.get()), new ItemStack(WizardsRebornItems.ARCANE_GOLD_SWORD.get()),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        ARCACITE_ITEM, ARCACITE_ITEM, ARCACITE_ITEM, ARCACITE_ITEM
                ).setExperience(5).setHealth(5).setArcaneEnchantment(WizardsRebornArcaneEnchantments.DUAL_BLADE),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_iterator_enchantments"),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.OBSIDIAN), new ItemStack(Items.OBSIDIAN), new ItemStack(Items.OBSIDIAN)
                ).setExperience(5).setEnchantment(Enchantments.ALL_DAMAGE_PROTECTION),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.OBSIDIAN), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER)
                ).setExperience(5).setEnchantment(Enchantments.FIRE_PROTECTION),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.OBSIDIAN), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.GUNPOWDER)
                ).setExperience(5).setEnchantment(Enchantments.BLAST_PROTECTION),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.OBSIDIAN), new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.ARROW)
                ).setExperience(5).setEnchantment(Enchantments.PROJECTILE_PROTECTION),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.STRING), new ItemStack(Items.FEATHER), new ItemStack(Items.FEATHER)
                ).setExperience(5).setEnchantment(Enchantments.FALL_PROTECTION),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.IRON_INGOT), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL)
                ).setExperience(5).setEnchantment(Enchantments.RESPIRATION),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL)
                ).setExperience(6).setEnchantment(Enchantments.AQUA_AFFINITY),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.CACTUS), new ItemStack(Items.CACTUS), new ItemStack(Items.CACTUS)
                ).setExperience(5).setEnchantment(Enchantments.THORNS),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.SLIME_BALL), new ItemStack(Items.SLIME_BALL), new ItemStack(Items.CLAY_BALL), new ItemStack(Items.CLAY_BALL)
                ).setExperience(5).setEnchantment(Enchantments.DEPTH_STRIDER),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.SLIME_BALL), new ItemStack(Items.SLIME_BALL), new ItemStack(Items.PACKED_ICE), new ItemStack(Items.PACKED_ICE)
                ).setExperience(5).setEnchantment(Enchantments.FROST_WALKER),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ)
                ).setExperience(5).setEnchantment(Enchantments.SHARPNESS),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH)
                ).setExperience(5).setEnchantment(Enchantments.SMITE),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.SPIDER_EYE)
                ).setExperience(5).setEnchantment(Enchantments.BANE_OF_ARTHROPODS),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.PISTON), new ItemStack(Items.PISTON)
                ).setExperience(6).setEnchantment(Enchantments.KNOCKBACK),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER)
                ).setExperience(6).setEnchantment(Enchantments.FIRE_ASPECT),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.DIAMOND), new ItemStack(Items.EMERALD)
                ).setExperience(7).setEnchantment(Enchantments.MOB_LOOTING),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.BONE), new ItemStack(Items.BONE), new ItemStack(Items.BONE)
                ).setExperience(6).setEnchantment(Enchantments.SWEEPING_EDGE),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE)
                ).setExperience(5).setEnchantment(Enchantments.BLOCK_EFFICIENCY),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.GOLD_BLOCK), new ItemStack(Items.DIAMOND)
                ).setExperience(10).setEnchantment(Enchantments.SILK_TOUCH),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT)
                ).setExperience(5).setEnchantment(Enchantments.UNBREAKING),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.DIAMOND), new ItemStack(Items.EMERALD), new ItemStack(Items.EMERALD)
                ).setExperience(8).setEnchantment(Enchantments.BLOCK_FORTUNE),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_ROD)
                ).setExperience(5).setEnchantment(Enchantments.POWER_ARROWS),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.PISTON), new ItemStack(Items.PISTON)
                ).setExperience(6).setEnchantment(Enchantments.PUNCH_ARROWS),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER)
                ).setExperience(8).setEnchantment(Enchantments.FLAMING_ARROWS),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND)
                ).setExperience(10).setEnchantment(Enchantments.INFINITY_ARROWS),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.STRING), new ItemStack(Items.STRING), new ItemStack(Items.EMERALD), new ItemStack(Items.EMERALD)
                ).setExperience(5).setEnchantment(Enchantments.FISHING_LUCK),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.STRING), new ItemStack(Items.STRING), new ItemStack(Items.COD), new ItemStack(Items.COD)
                ).setExperience(5).setEnchantment(Enchantments.FISHING_SPEED),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.STRING), new ItemStack(Items.STRING), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD)
                ).setExperience(5).setEnchantment(Enchantments.LOYALTY),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.QUARTZ), new ItemStack(Items.PRISMARINE_CRYSTALS), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD)
                ).setExperience(5).setEnchantment(Enchantments.IMPALING),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.PRISMARINE_CRYSTALS), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_SHARD)
                ).setExperience(5).setEnchantment(Enchantments.RIPTIDE),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.HEART_OF_THE_SEA)
                ).setExperience(10).setEnchantment(Enchantments.CHANNELING),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.STICK), new ItemStack(Items.DISPENSER)
                ).setExperience(10).setEnchantment(Enchantments.MULTISHOT),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.STICK), new ItemStack(Items.SLIME_BALL)
                ).setExperience(5).setEnchantment(Enchantments.QUICK_CHARGE),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.STICK), new ItemStack(Items.QUARTZ)
                ).setExperience(5).setEnchantment(Enchantments.PIERCING),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.IRON_INGOT)
                ).setExperience(1).setHealth(10).setEnchantment(Enchantments.VANISHING_CURSE),
                new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                        new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.OBSIDIAN)
                ).setExperience(10).setHealth(10).setEnchantment(Enchantments.BINDING_CURSE)
        );

        KNOWLEDGE_SCROLL = new Chapter("wizards_reborn.arcanemicon.chapter.knowledge_scroll",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.knowledge_scroll",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.KNOWLEDGE_SCROLL.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.KNOWLEDGE_SCROLL.get()), new ItemStack(Items.BOOK),
                        ENCHANTED_CALX_PILE_ITEM, new ItemStack(Items.INK_SAC), new ItemStack(Items.FEATHER), ARCANUM_ITEM
                ).setExperience(10)
        );

        MUSIC_DISC_REBORN = new Chapter("wizards_reborn.arcanemicon.chapter.music_disc_reborn",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.music_disc_reborn",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MUSIC_DISC_REBORN.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.MUSIC_DISC_REBORN.get()), new ItemStack(Items.MUSIC_DISC_13),
                        ARCANE_GOLD_INGOT_ITEM
                )
        );

        MUSIC_DISC_PANACHE = new Chapter("wizards_reborn.arcanemicon.chapter.music_disc_panache",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.music_disc_panache",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MUSIC_DISC_PANACHE.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.MUSIC_DISC_PANACHE.get()), new ItemStack(Items.MUSIC_DISC_13),
                        ARCANE_WOOD_PLANKS_ITEM, new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get())
                )
        );

        ARCANUM_LENS = new Chapter("wizards_reborn.arcanemicon.chapter.arcanum_lens",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum_lens",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANUM_LENS_ITEM)
                ),
                new ArcaneIteratorPage(ARCANUM_LENS_ITEM, ACLHEMY_GLASS,
                        ARCANUM_ITEM, ALCHEMY_CALX_PILE_ITEM
                )
        );

        WISSEN_KEYCHAIN = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_keychain",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_keychain",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_KEYCHAIN.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.WISSEN_KEYCHAIN.get()), new ItemStack(WizardsRebornItems.ARCANUM_AMULET.get()),
                        ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM,
                        ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM
                )
        );

        WISSEN_RING = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_ring",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_ring",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_RING.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.WISSEN_RING.get()), new ItemStack(WizardsRebornItems.ARCANUM_RING.get()),
                        ARCANUM_LENS_ITEM, ARCACITE_ITEM, ARCANUM_LENS_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANUM_LENS_ITEM, ARCACITE_ITEM
                )
        );

        LEATHER_COLLAR = new Chapter("wizards_reborn.arcanemicon.chapter.leather_collar",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.leather_collar",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.LEATHER_COLLAR.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.LEATHER_COLLAR.get()), new ItemStack(Items.BELL),
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER), new ItemStack(Items.LEATHER),
                        new ItemStack(Items.STRING), new ItemStack(Items.STRING), new ItemStack(Items.STRING),
                        new ItemStack(Items.COD), new ItemStack(Items.COD), new ItemStack(Items.COD),
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM
                )
        );

        JEWELER_TABLE = new Chapter("wizards_reborn.arcanemicon.chapter.jeweler_table",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.jeweler_table",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.JEWELER_TABLE.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.JEWELER_TABLE.get()),
                        ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM,
                        new ItemStack(Items.RED_WOOL), new ItemStack(Items.RED_WOOL), new ItemStack(Items.RED_WOOL),
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()), ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                )
        );

        FACETED_CRYSTALS = new Chapter("wizards_reborn.arcanemicon.chapter.faceted_crystals",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.faceted_crystals",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FACETED_EARTH_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FACETED_WATER_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FACETED_AIR_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FACETED_FIRE_CRYSTAL.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FACETED_VOID_CRYSTAL.get()))
                ),
                new JewelerTablePage(new ItemStack(WizardsRebornItems.FACETED_EARTH_CRYSTAL.get()), new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get())),
                new JewelerTablePage(new ItemStack(WizardsRebornItems.FACETED_WATER_CRYSTAL.get()), new ItemStack(WizardsRebornItems.WATER_CRYSTAL.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get())),
                new JewelerTablePage(new ItemStack(WizardsRebornItems.FACETED_AIR_CRYSTAL.get()), new ItemStack(WizardsRebornItems.AIR_CRYSTAL.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get())),
                new JewelerTablePage(new ItemStack(WizardsRebornItems.FACETED_FIRE_CRYSTAL.get()), new ItemStack(WizardsRebornItems.FIRE_CRYSTAL.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get())),
                new JewelerTablePage(new ItemStack(WizardsRebornItems.FACETED_VOID_CRYSTAL.get()), new ItemStack(WizardsRebornItems.VOID_CRYSTAL.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get()))
        );

        TRIMS = new Chapter("wizards_reborn.arcanemicon.chapter.trims",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.trims",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_TRIM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_TRIM.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_TRIM.get()), ARCANE_GOLD_NUGGET_ITEM,
                        new ItemStack(Items.STRING), ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.WISESTONE_TRIM.get()), ARCANE_GOLD_NUGGET_ITEM,
                        new ItemStack(Items.STRING), WISESTONE_ITEM, WISESTONE_ITEM, WISESTONE_ITEM, WISESTONE_ITEM
                )
        );

        TOP_HAT_TRIM = new Chapter("wizards_reborn.arcanemicon.chapter.top_hat_trim",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.top_hat_trim",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.TOP_HAT_TRIM.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.TOP_HAT_TRIM.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_TRIM.get()),
                        new ItemStack(Items.BLACK_WOOL), new ItemStack(Items.BLACK_WOOL), new ItemStack(Items.BLACK_WOOL)
                ).setExperience(10)
        );

        MAGNIFICENT_MAID_TRIM = new Chapter("wizards_reborn.arcanemicon.chapter.magnificent_maid_trim",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.magnificent_maid_trim",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MAGNIFICENT_MAID_TRIM.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.MAGNIFICENT_MAID_TRIM.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_TRIM.get()),
                        new ItemStack(Items.BLACK_WOOL), new ItemStack(Items.BLACK_WOOL), new ItemStack(Items.WHITE_WOOL), new ItemStack(Items.WHITE_WOOL), ARCANUM_ITEM
                )
        );

        SUMMER_LOVE_TRIM = new Chapter("wizards_reborn.arcanemicon.chapter.summer_love_trim",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.summer_love_trim",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SUMMER_LOVE_TRIM.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.SUMMER_LOVE_TRIM.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_TRIM.get()),
                        new ItemStack(Items.WHITE_WOOL), new ItemStack(Items.ORANGE_WOOL), new ItemStack(Items.LIGHT_BLUE_WOOL), new ItemStack(Items.LIGHT_BLUE_WOOL), new ItemStack(WizardsRebornItems.PETALS_OF_INNOCENCE.get())
                )
        );

        MUSIC_DISC_CAPITALISM = new Chapter("wizards_reborn.arcanemicon.chapter.music_disc_capitalism",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.music_disc_capitalism",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MUSIC_DISC_CAPITALISM.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.MUSIC_DISC_CAPITALISM.get()), new ItemStack(WizardsRebornItems.MUSIC_DISC_PANACHE.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_INGOT_ITEM
                )
        );

        CARGO_CARPET = new Chapter("wizards_reborn.arcanemicon.chapter.cargo_carpet",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.cargo_carpet",
                        new BlockEntry(new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIGHT_GRAY_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.GRAY_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BLACK_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BROWN_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.RED_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ORANGE_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.YELLOW_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIME_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.GREEN_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CYAN_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIGHT_BLUE_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BLUE_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.PURPLE_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.MAGENTA_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.PINK_CARGO_CARPET.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.RAINBOW_CARGO_CARPET.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()),
                        new ItemStack(Items.STRING), new ItemStack(WizardsRebornItems.FERAL_COTTON.get()), new ItemStack(Items.STRING),
                        new ItemStack(WizardsRebornItems.FERAL_COTTON.get()), EMPTY_ITEM, new ItemStack(WizardsRebornItems.FERAL_COTTON.get()),
                        new ItemStack(WizardsRebornItems.FERAL_COTTON.get()), new ItemStack(WizardsRebornItems.FERAL_COTTON.get()), new ItemStack(WizardsRebornItems.FERAL_COTTON.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.FERAL_COTTON.get()), EMPTY_ITEM, new ItemStack(WizardsRebornItems.FERAL_COTTON.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.WHITE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.LIGHT_GRAY_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.LIGHT_GRAY_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GRAY_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.GRAY_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.BLACK_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.BLACK_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.BROWN_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.BROWN_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.RED_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.RED_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ORANGE_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.ORANGE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.YELLOW_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.YELLOW_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.LIME_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.LIME_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GREEN_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.GREEN_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CYAN_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.CYAN_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.LIGHT_BLUE_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.LIGHT_BLUE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.BLUE_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.BLUE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.PURPLE_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.PURPLE_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.MAGENTA_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.MAGENTA_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.PINK_CARGO_CARPET.get()), new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.PINK_DYE)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.RAINBOW_CARGO_CARPET.get()),
                        new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), new ItemStack(Items.PURPLE_DYE), new ItemStack(Items.BLUE_DYE),
                        new ItemStack(Items.LIGHT_BLUE_DYE), new ItemStack(Items.LIME_DYE), new ItemStack(Items.YELLOW_DYE),
                        new ItemStack(Items.ORANGE_DYE), new ItemStack(Items.RED_DYE)
                )
        );

        ItemStack knowledgeScroll = new ItemStack(WizardsRebornItems.KNOWLEDGE_SCROLL.get());
        CompoundTag knowledgeScrollNbt = knowledgeScroll.getOrCreateTag();
        ListTag knowledgeScrollList = new ListTag();
        knowledgeScrollNbt.put("knowledges", knowledgeScrollList);

        ARCANE_NATURE_INDEX = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_nature_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.arcane_nature_index",
                        new IndexEntry(ARCANUM, ARCANUM_ITEM),
                        new IndexEntry(ARCANUM_DUST_TRANSMUTATION, ARCANUM_DUST_ITEM, WizardsRebornKnowledges.ARCANUM_DUST),
                        new IndexEntry(ARCANE_WOOD, ARCANE_WOOD_PLANKS_ITEM, WizardsRebornKnowledges.ARCANUM_DUST),
                        new IndexEntry(ARCANE_GOLD, ARCANE_GOLD_INGOT_ITEM, WizardsRebornKnowledges.ARCANUM_DUST),
                        new IndexEntry(SCYTHES, new ItemStack(WizardsRebornItems.ARCANE_GOLD_SCYTHE.get()), WizardsRebornKnowledges.ARCANE_GOLD),
                        new IndexEntry(TRINKETS, new ItemStack(WizardsRebornItems.ARCANUM_AMULET.get()), WizardsRebornKnowledges.ARCANE_GOLD)
                ),
                new IndexPage(
                        new IndexEntry(ARCANE_WOOD_BOW, new ItemStack(WizardsRebornItems.ARCANE_WOOD_BOW.get()), WizardsRebornKnowledges.ARCANE_GOLD),
                        new IndexEntry(ARCANE_WOOD_CROSSBOW, new ItemStack(WizardsRebornItems.ARCANE_WOOD_CROSSBOW.get()), WizardsRebornKnowledges.ARCANE_GOLD),
                        new IndexEntry(ARCANE_WOOD_FISHING_ROD, new ItemStack(WizardsRebornItems.ARCANE_WOOD_FISHING_ROD.get()), WizardsRebornKnowledges.ARCANE_GOLD),
                        new IndexEntry(ARCANE_GOLD_SHEARS, new ItemStack(WizardsRebornItems.ARCANE_GOLD_SHEARS.get()), WizardsRebornKnowledges.ARCANE_GOLD),
                        new IndexEntry(BANNER_PATTERNS, new ItemStack(WizardsRebornItems.ASCENSION_BANNER_PATTERN.get()), WizardsRebornKnowledges.ARCANUM_DUST),
                        new IndexEntry(WISSEN, new ItemStack(WizardsRebornItems.WISSEN_WAND.get()), WizardsRebornKnowledges.ARCANUM_DUST),
                        new IndexEntry(WISSEN_TRANSLATOR, new ItemStack(WizardsRebornItems.WISSEN_TRANSLATOR.get()), WizardsRebornKnowledges.ARCANE_GOLD)
                ),
                new IndexPage(
                        new IndexEntry(ARCANE_PEDESTAL, ARCANE_PEDESTAL_ITEM, WizardsRebornKnowledges.ARCANE_WOOD),
                        new IndexEntry(WISSEN_ALTAR, new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()), WizardsRebornKnowledges.ARCANE_GOLD),
                        new IndexEntry(WISSEN_CRYSTALLIZER, new ItemStack(WizardsRebornItems.WISSEN_CRYSTALLIZER.get()), WizardsRebornKnowledges.ARCANE_GOLD),
                        new IndexEntry(ARCANE_WORKBENCH, new ItemStack(WizardsRebornItems.ARCANE_WORKBENCH.get()), WizardsRebornKnowledges.ARCANE_GOLD),
                        new IndexEntry(MUSIC_DISC_SHIMMER, new ItemStack(WizardsRebornItems.MUSIC_DISC_SHIMMER.get()), WizardsRebornKnowledges.WISSEN_CRYSTALLIZER),
                        new IndexEntry(ARCANE_LUMOS, new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), WizardsRebornKnowledges.WISSEN_CRYSTALLIZER),
                        new IndexEntry(CRYSTALS, new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()), WizardsRebornKnowledges.WISSEN_CRYSTALLIZER)
                ),
                new IndexPage(
                        new IndexEntry(ARCANE_WAND, new ItemStack(WizardsRebornItems.ARCANE_WAND.get()), WizardsRebornKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(AUTOMATION, new ItemStack(WizardsRebornItems.REDSTONE_SENSOR.get()), WizardsRebornKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(WISSEN_CELL, new ItemStack(WizardsRebornItems.WISSEN_CELL.get()), WizardsRebornKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(WISSEN_CHARGER, new ItemStack(WizardsRebornItems.WISSEN_CHARGER.get()), WizardsRebornKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(CRYSTAL_BAG, new ItemStack(WizardsRebornItems.CRYSTAL_BAG.get()), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(TOTEM_OF_FLAMES, new ItemStack(WizardsRebornItems.TOTEM_OF_FLAMES.get()), WizardsRebornKnowledges.ALCHEMY_CALX),
                        new IndexEntry(EXPERIENCE_TOTEM, new ItemStack(WizardsRebornItems.EXPERIENCE_TOTEM.get()), WizardsRebornKnowledges.ALCHEMY_CALX)
                ),
                new IndexPage(
                        new IndexEntry(TOTEM_OF_EXPERIENCE_ABSORPTION, new ItemStack(WizardsRebornItems.TOTEM_OF_EXPERIENCE_ABSORPTION.get()), WizardsRebornKnowledges.ARCACITE),
                        new IndexEntry(TOTEM_OF_DISENCHANT, new ItemStack(WizardsRebornItems.TOTEM_OF_DISENCHANT.get()), WizardsRebornKnowledges.ARCACITE),
                        new IndexEntry(ALTAR_OF_DROUGHT, new ItemStack(WizardsRebornItems.ALTAR_OF_DROUGHT.get()), WizardsRebornKnowledges.ARCACITE),
                        new IndexEntry(VOID_CRYSTAL, new ItemStack(WizardsRebornItems.VOID_CRYSTAL.get()), WizardsRebornKnowledges.ARCACITE),
                        new IndexEntry(ARCANE_FORTRESS_ARMOR, new ItemStack(WizardsRebornItems.ARCANE_FORTRESS_CHESTPLATE.get()), WizardsRebornKnowledges.ARCACITE),
                        new IndexEntry(INVENTOR_WIZARD_ARMOR, new ItemStack(WizardsRebornItems.INVENTOR_WIZARD_HAT.get()), WizardsRebornKnowledges.ARCACITE),
                        new IndexEntry(ARCANE_WOOD_CANE, new ItemStack(WizardsRebornItems.ARCANE_WOOD_CANE.get()), WizardsRebornKnowledges.ARCACITE)
                ),
                new IndexPage(
                        new IndexEntry(ARCANE_ITERATOR, new ItemStack(WizardsRebornItems.ARCANE_ITERATOR.get()), WizardsRebornKnowledges.ARCACITE),
                        new IndexEntry(KNOWLEDGE_SCROLL, knowledgeScroll, WizardsRebornKnowledges.ARCANE_ITERATOR),
                        new IndexEntry(MUSIC_DISC_REBORN, new ItemStack(WizardsRebornItems.MUSIC_DISC_REBORN.get()), WizardsRebornKnowledges.ARCANE_ITERATOR),
                        new IndexEntry(MUSIC_DISC_PANACHE, new ItemStack(WizardsRebornItems.MUSIC_DISC_PANACHE.get()), WizardsRebornKnowledges.ARCANE_ITERATOR),
                        new IndexEntry(ARCANUM_LENS, ARCANUM_LENS_ITEM, WizardsRebornKnowledges.ARCANE_ITERATOR),
                        new IndexEntry(WISSEN_KEYCHAIN, new ItemStack(WizardsRebornItems.WISSEN_KEYCHAIN.get()), WizardsRebornKnowledges.ARCANE_ITERATOR),
                        new IndexEntry(WISSEN_RING, new ItemStack(WizardsRebornItems.WISSEN_RING.get()), WizardsRebornKnowledges.ARCANUM_LENS)
                ),
                new IndexPage(
                        new IndexEntry(LEATHER_COLLAR, new ItemStack(WizardsRebornItems.LEATHER_COLLAR.get()), WizardsRebornKnowledges.ARCANE_ITERATOR),
                        new IndexEntry(JEWELER_TABLE, new ItemStack(WizardsRebornItems.JEWELER_TABLE.get()), WizardsRebornKnowledges.ARCANE_ITERATOR),
                        new IndexEntry(FACETED_CRYSTALS, new ItemStack(WizardsRebornItems.FACETED_EARTH_CRYSTAL.get()), WizardsRebornKnowledges.ARCACITE_POLISHING_MIXTURE),
                        new IndexEntry(TRIMS, new ItemStack(WizardsRebornItems.ARCANE_WOOD_TRIM.get()), WizardsRebornKnowledges.JEWELER_TABLE),
                        new IndexEntry(TOP_HAT_TRIM, new ItemStack(WizardsRebornItems.TOP_HAT_TRIM.get()), WizardsRebornKnowledges.JEWELER_TABLE),
                        new IndexEntry(MAGNIFICENT_MAID_TRIM, new ItemStack(WizardsRebornItems.MAGNIFICENT_MAID_TRIM.get()), WizardsRebornKnowledges.JEWELER_TABLE),
                        new IndexEntry(SUMMER_LOVE_TRIM, new ItemStack(WizardsRebornItems.SUMMER_LOVE_TRIM.get()), WizardsRebornKnowledges.JEWELER_TABLE)
                ),
                new IndexPage(
                        new IndexEntry(MUSIC_DISC_CAPITALISM, new ItemStack(WizardsRebornItems.MUSIC_DISC_CAPITALISM.get()), WizardsRebornKnowledges.ARCANE_ITERATOR),
                        new IndexEntry(CARGO_CARPET, new ItemStack(WizardsRebornItems.WHITE_CARGO_CARPET.get()), WizardsRebornKnowledges.ARCANE_ITERATOR)
                )
        );
    }

    public static void spellsInit() {
        EARTH_PROJECTILE = new Chapter("wizards_reborn.arcanemicon.chapter.earth_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.earth_projectile", WizardsRebornSpells.EARTH_PROJECTILE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.earth_projectile.char", WizardsRebornSpells.EARTH_PROJECTILE)
        );
        WATER_PROJECTILE = new Chapter("wizards_reborn.arcanemicon.chapter.water_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_projectile", WizardsRebornSpells.WATER_PROJECTILE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.water_projectile.char", WizardsRebornSpells.WATER_PROJECTILE)
        );
        AIR_PROJECTILE = new Chapter("wizards_reborn.arcanemicon.chapter.air_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_projectile", WizardsRebornSpells.AIR_PROJECTILE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.air_projectile.char", WizardsRebornSpells.AIR_PROJECTILE)
        );
        FIRE_PROJECTILE = new Chapter("wizards_reborn.arcanemicon.chapter.fire_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.fire_projectile", WizardsRebornSpells.FIRE_PROJECTILE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.fire_projectile.char", WizardsRebornSpells.FIRE_PROJECTILE)
        );
        VOID_PROJECTILE = new Chapter("wizards_reborn.arcanemicon.chapter.void_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.void_projectile", WizardsRebornSpells.VOID_PROJECTILE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.void_projectile.char", WizardsRebornSpells.VOID_PROJECTILE)
        );
        FROST_PROJECTILE = new Chapter("wizards_reborn.arcanemicon.chapter.frost_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.frost_projectile", WizardsRebornSpells.FROST_PROJECTILE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.frost_projectile.char", WizardsRebornSpells.FROST_PROJECTILE)
        );
        HOLY_PROJECTILE = new Chapter("wizards_reborn.arcanemicon.chapter.holy_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.holy_projectile", WizardsRebornSpells.HOLY_PROJECTILE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.holy_projectile.char", WizardsRebornSpells.HOLY_PROJECTILE)
        );
        CURSE_PROJECTILE = new Chapter("wizards_reborn.arcanemicon.chapter.curse_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.curse_projectile", WizardsRebornSpells.CURSE_PROJECTILE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.curse_projectile.char", WizardsRebornSpells.CURSE_PROJECTILE)
        );

        EARTH_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.earth_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.earth_ray", WizardsRebornSpells.EARTH_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.earth_ray.char", WizardsRebornSpells.EARTH_RAY)
        );
        WATER_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.water_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_ray", WizardsRebornSpells.WATER_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.water_ray.char", WizardsRebornSpells.WATER_RAY)
        );
        AIR_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.air_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_ray", WizardsRebornSpells.AIR_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.air_ray.char", WizardsRebornSpells.AIR_RAY)
        );
        FIRE_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.fire_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.fire_ray", WizardsRebornSpells.FIRE_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.fire_ray.char", WizardsRebornSpells.FIRE_RAY)
        );
        VOID_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.void_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.void_ray", WizardsRebornSpells.VOID_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.void_ray.char", WizardsRebornSpells.VOID_RAY)
        );
        FROST_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.frost_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.frost_ray", WizardsRebornSpells.FROST_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.frost_ray.char", WizardsRebornSpells.FROST_RAY)
        );
        HOLY_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.holy_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.holy_ray", WizardsRebornSpells.HOLY_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.holy_ray.char", WizardsRebornSpells.HOLY_RAY)
        );
        CURSE_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.curse_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.curse_ray", WizardsRebornSpells.CURSE_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.curse_ray.char", WizardsRebornSpells.CURSE_RAY)
        );

        HEART_OF_NATURE = new Chapter("wizards_reborn.arcanemicon.chapter.heart_of_nature",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.heart_of_nature", WizardsRebornSpells.HEART_OF_NATURE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.heart_of_nature.char", WizardsRebornSpells.HEART_OF_NATURE)
        );
        WATER_BREATHING = new Chapter("wizards_reborn.arcanemicon.chapter.water_breathing",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_breathing", WizardsRebornSpells.WATER_BREATHING),
                new SpellCharPage("wizards_reborn.arcanemicon.page.water_breathing.char", WizardsRebornSpells.WATER_BREATHING)
        );
        AIR_FLOW = new Chapter("wizards_reborn.arcanemicon.chapter.air_flow",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_flow", WizardsRebornSpells.AIR_FLOW),
                new SpellCharPage("wizards_reborn.arcanemicon.page.air_flow.char", WizardsRebornSpells.AIR_FLOW)
        );
        FIRE_SHIELD = new Chapter("wizards_reborn.arcanemicon.chapter.fire_shield",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.fire_shield", WizardsRebornSpells.FIRE_SHIELD),
                new SpellCharPage("wizards_reborn.arcanemicon.page.fire_shield.char", WizardsRebornSpells.FIRE_SHIELD)
        );
        BLINK = new Chapter("wizards_reborn.arcanemicon.chapter.blink",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.blink", WizardsRebornSpells.BLINK),
                new SpellCharPage("wizards_reborn.arcanemicon.page.blink.char", WizardsRebornSpells.BLINK)
        );
        SNOWFLAKE = new Chapter("wizards_reborn.arcanemicon.chapter.snowflake",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.snowflake", WizardsRebornSpells.SNOWFLAKE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.blink.snowflake", WizardsRebornSpells.SNOWFLAKE)
        );
        HOLY_CROSS = new Chapter("wizards_reborn.arcanemicon.chapter.holy_cross",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.holy_cross", WizardsRebornSpells.HOLY_CROSS),
                new SpellCharPage("wizards_reborn.arcanemicon.page.holy_cross.char", WizardsRebornSpells.HOLY_CROSS)
        );
        CURSE_CROSS = new Chapter("wizards_reborn.arcanemicon.chapter.curse_cross",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.curse_cross", WizardsRebornSpells.CURSE_CROSS),
                new SpellCharPage("wizards_reborn.arcanemicon.page.curse_cross.char", WizardsRebornSpells.CURSE_CROSS)
        );
        POISON = new Chapter("wizards_reborn.arcanemicon.chapter.poison",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.poison", WizardsRebornSpells.POISON),
                new SpellCharPage("wizards_reborn.arcanemicon.page.poison.char", WizardsRebornSpells.POISON)
        );

        MAGIC_SPROUT = new Chapter("wizards_reborn.arcanemicon.chapter.magic_sprout",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.magic_sprout", WizardsRebornSpells.MAGIC_SPROUT),
                new SpellCharPage("wizards_reborn.arcanemicon.page.magic_sprout.char", WizardsRebornSpells.MAGIC_SPROUT)
        );
        DIRT_BLOCK = new Chapter("wizards_reborn.arcanemicon.chapter.dirt_block",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.dirt_block", WizardsRebornSpells.DIRT_BLOCK),
                new SpellCharPage("wizards_reborn.arcanemicon.page.dirt_block.char", WizardsRebornSpells.DIRT_BLOCK)
        );
        WATER_BLOCK = new Chapter("wizards_reborn.arcanemicon.chapter.water_block",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_block", WizardsRebornSpells.WATER_BLOCK),
                new SpellCharPage("wizards_reborn.arcanemicon.page.water_block.char", WizardsRebornSpells.WATER_BLOCK)
        );
        AIR_IMPACT = new Chapter("wizards_reborn.arcanemicon.chapter.air_impact",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_impact", WizardsRebornSpells.AIR_IMPACT),
                new SpellCharPage("wizards_reborn.arcanemicon.page.air_impact.char", WizardsRebornSpells.AIR_IMPACT)
        );
        ICE_BLOCK = new Chapter("wizards_reborn.arcanemicon.chapter.ice_block",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.ice_block", WizardsRebornSpells.ICE_BLOCK),
                new SpellCharPage("wizards_reborn.arcanemicon.page.ice_block.char", WizardsRebornSpells.ICE_BLOCK)
        );

        EARTH_CHARGE = new Chapter("wizards_reborn.arcanemicon.chapter.earth_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.earth_charge", WizardsRebornSpells.EARTH_CHARGE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.earth_charge.char", WizardsRebornSpells.EARTH_CHARGE)
        );
        WATER_CHARGE = new Chapter("wizards_reborn.arcanemicon.chapter.water_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_charge", WizardsRebornSpells.WATER_CHARGE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.water_charge.char", WizardsRebornSpells.WATER_CHARGE)
        );
        AIR_CHARGE = new Chapter("wizards_reborn.arcanemicon.chapter.air_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_charge", WizardsRebornSpells.AIR_CHARGE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.air_charge.char", WizardsRebornSpells.AIR_CHARGE)
        );
        FIRE_CHARGE = new Chapter("wizards_reborn.arcanemicon.chapter.fire_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.fire_charge", WizardsRebornSpells.FIRE_CHARGE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.fire_charge.char", WizardsRebornSpells.FIRE_CHARGE)
        );
        VOID_CHARGE = new Chapter("wizards_reborn.arcanemicon.chapter.void_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.void_charge", WizardsRebornSpells.VOID_CHARGE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.void_charge.char", WizardsRebornSpells.VOID_CHARGE)
        );
        FROST_CHARGE = new Chapter("wizards_reborn.arcanemicon.chapter.frost_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.frost_charge", WizardsRebornSpells.FROST_CHARGE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.frost_charge.char", WizardsRebornSpells.FROST_CHARGE)
        );
        HOLY_CHARGE = new Chapter("wizards_reborn.arcanemicon.chapter.holy_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.holy_charge", WizardsRebornSpells.HOLY_CHARGE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.holy_charge.char", WizardsRebornSpells.HOLY_CHARGE)
        );
        CURSE_CHARGE = new Chapter("wizards_reborn.arcanemicon.chapter.curse_charge",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.curse_charge", WizardsRebornSpells.CURSE_CHARGE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.curse_charge.char", WizardsRebornSpells.CURSE_CHARGE)
        );

        EARTH_AURA = new Chapter("wizards_reborn.arcanemicon.chapter.earth_aura",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.earth_aura", WizardsRebornSpells.EARTH_AURA),
                new SpellCharPage("wizards_reborn.arcanemicon.page.earth_aura.char", WizardsRebornSpells.EARTH_AURA)
        );
        WATER_AURA = new Chapter("wizards_reborn.arcanemicon.chapter.water_aura",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_aura", WizardsRebornSpells.WATER_AURA),
                new SpellCharPage("wizards_reborn.arcanemicon.page.water_aura.char", WizardsRebornSpells.WATER_AURA)
        );
        AIR_AURA = new Chapter("wizards_reborn.arcanemicon.chapter.air_aura",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_aura", WizardsRebornSpells.AIR_AURA),
                new SpellCharPage("wizards_reborn.arcanemicon.page.air_aura.char", WizardsRebornSpells.AIR_AURA)
        );
        FIRE_AURA = new Chapter("wizards_reborn.arcanemicon.chapter.fire_aura",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.fire_aura", WizardsRebornSpells.FIRE_AURA),
                new SpellCharPage("wizards_reborn.arcanemicon.page.fire_aura.char", WizardsRebornSpells.FIRE_AURA)
        );
        VOID_AURA = new Chapter("wizards_reborn.arcanemicon.chapter.void_aura",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.void_aura", WizardsRebornSpells.VOID_AURA),
                new SpellCharPage("wizards_reborn.arcanemicon.page.void_aura.char", WizardsRebornSpells.VOID_AURA)
        );
        FROST_AURA = new Chapter("wizards_reborn.arcanemicon.chapter.frost_aura",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.frost_aura", WizardsRebornSpells.FROST_AURA),
                new SpellCharPage("wizards_reborn.arcanemicon.page.frost_aura.char", WizardsRebornSpells.FROST_AURA)
        );
        HOLY_AURA = new Chapter("wizards_reborn.arcanemicon.chapter.holy_aura",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.holy_aura", WizardsRebornSpells.HOLY_AURA),
                new SpellCharPage("wizards_reborn.arcanemicon.page.holy_aura.char", WizardsRebornSpells.HOLY_AURA)
        );
        CURSE_AURA = new Chapter("wizards_reborn.arcanemicon.chapter.curse_aura",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.curse_aura", WizardsRebornSpells.CURSE_AURA),
                new SpellCharPage("wizards_reborn.arcanemicon.page.curse_aura.char", WizardsRebornSpells.CURSE_AURA)
        );

        RAIN_CLOUD = new Chapter("wizards_reborn.arcanemicon.chapter.rain_cloud",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.rain_cloud", WizardsRebornSpells.RAIN_CLOUD),
                new SpellCharPage("wizards_reborn.arcanemicon.page.rain_cloud.char", WizardsRebornSpells.RAIN_CLOUD)
        );
        LAVA_BLOCK = new Chapter("wizards_reborn.arcanemicon.chapter.lava_block",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.lava_block", WizardsRebornSpells.LAVA_BLOCK),
                new SpellCharPage("wizards_reborn.arcanemicon.page.lava_block.char", WizardsRebornSpells.LAVA_BLOCK)
        );
        ICICLE = new Chapter("wizards_reborn.arcanemicon.chapter.icicle",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.icicle", WizardsRebornSpells.ICICLE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.icicle.char", WizardsRebornSpells.ICICLE)
        );
        SHARP_BLINK = new Chapter("wizards_reborn.arcanemicon.chapter.sharp_blink",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.sharp_blink", WizardsRebornSpells.SHARP_BLINK),
                new SpellCharPage("wizards_reborn.arcanemicon.page.sharp_blink.char", WizardsRebornSpells.SHARP_BLINK)
        );
        CRYSTAL_CRUSHING = new Chapter("wizards_reborn.arcanemicon.chapter.crystal_crushing",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.crystal_crushing", WizardsRebornSpells.CRYSTAL_CRUSHING),
                new SpellCharPage("wizards_reborn.arcanemicon.page.crystal_crushing.char", WizardsRebornSpells.CRYSTAL_CRUSHING)
        );
        TOXIC_RAIN = new Chapter("wizards_reborn.arcanemicon.chapter.toxic_rain",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.toxic_rain", WizardsRebornSpells.TOXIC_RAIN),
                new SpellCharPage("wizards_reborn.arcanemicon.page.toxic_rain.char", WizardsRebornSpells.TOXIC_RAIN)
        );
        MOR_SWARM = new Chapter("wizards_reborn.arcanemicon.chapter.mor_swarm",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.mor_swarm", WizardsRebornSpells.MOR_SWARM),
                new SpellCharPage("wizards_reborn.arcanemicon.page.mor_swarm.char", WizardsRebornSpells.MOR_SWARM)
        );
        WITHERING = new Chapter("wizards_reborn.arcanemicon.chapter.withering",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.withering", WizardsRebornSpells.WITHERING),
                new SpellCharPage("wizards_reborn.arcanemicon.page.withering.char", WizardsRebornSpells.WITHERING)
        );
        WITHERING = new Chapter("wizards_reborn.arcanemicon.chapter.withering",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.withering", WizardsRebornSpells.WITHERING),
                new SpellCharPage("wizards_reborn.arcanemicon.page.withering.char", WizardsRebornSpells.WITHERING)
        );
        IRRITATION = new Chapter("wizards_reborn.arcanemicon.chapter.irritation",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.irritation", WizardsRebornSpells.IRRITATION),
                new SpellCharPage("wizards_reborn.arcanemicon.page.irritation.char", WizardsRebornSpells.IRRITATION)
        );
        NECROTIC_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.necrotic_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.necrotic_ray", WizardsRebornSpells.NECROTIC_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.necrotic_ray.char", WizardsRebornSpells.NECROTIC_RAY)
        );
        LIGHT_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.light_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.light_ray", WizardsRebornSpells.LIGHT_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.light_ray.char", WizardsRebornSpells.LIGHT_RAY)
        );

        INCINERATION = new Chapter("wizards_reborn.arcanemicon.chapter.incineration",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.incineration", WizardsRebornSpells.INCINERATION),
                new SpellCharPage("wizards_reborn.arcanemicon.page.incineration.char", WizardsRebornSpells.INCINERATION)
        );
        REPENTANCE = new Chapter("wizards_reborn.arcanemicon.chapter.repentance",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.repentance", WizardsRebornSpells.REPENTANCE),
                new SpellCharPage("wizards_reborn.arcanemicon.page.repentance.char", WizardsRebornSpells.REPENTANCE)
        );
        RENUNCIATION = new Chapter("wizards_reborn.arcanemicon.chapter.renunciation",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.renunciation", WizardsRebornSpells.RENUNCIATION),
                new SpellCharPage("wizards_reborn.arcanemicon.page.renunciation.char", WizardsRebornSpells.RENUNCIATION)
        );

        EMBER_RAY = new Chapter("wizards_reborn.arcanemicon.chapter.ember_ray",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.ember_ray", WizardsRebornSpells.EMBER_RAY),
                new SpellCharPage("wizards_reborn.arcanemicon.page.ember_ray.char", WizardsRebornSpells.EMBER_RAY)
        );
        WISDOM = new Chapter("wizards_reborn.arcanemicon.chapter.wisdom",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.wisdom", WizardsRebornSpells.WISDOM),
                new SpellCharPage("wizards_reborn.arcanemicon.page.wisdom.char", WizardsRebornSpells.WISDOM)
        );

        LUNAM = new Chapter("wizards_reborn.arcanemicon.chapter.lunam_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.lunam_monogram", WizardsRebornMonograms.LUNAM),
                new MonogramRecipesPage(WizardsRebornMonograms.LUNAM)
        );
        VITA = new Chapter("wizards_reborn.arcanemicon.chapter.vita_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.vita_monogram", WizardsRebornMonograms.VITA),
                new MonogramRecipesPage(WizardsRebornMonograms.VITA)
        );
        SOLEM = new Chapter("wizards_reborn.arcanemicon.chapter.solem_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.solem_monogram", WizardsRebornMonograms.SOLEM),
                new MonogramRecipesPage(WizardsRebornMonograms.SOLEM)
        );
        MORS = new Chapter("wizards_reborn.arcanemicon.chapter.mors_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.mors_monogram", WizardsRebornMonograms.MORS),
                new MonogramRecipesPage(WizardsRebornMonograms.MORS)
        );
        MIRACULUM = new Chapter("wizards_reborn.arcanemicon.chapter.miraculum_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.miraculum_monogram", WizardsRebornMonograms.MIRACULUM),
                new MonogramRecipesPage(WizardsRebornMonograms.MIRACULUM)
        );
        TEMPUS = new Chapter("wizards_reborn.arcanemicon.chapter.tempus_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.tempus_monogram", WizardsRebornMonograms.TEMPUS),
                new MonogramRecipesPage(WizardsRebornMonograms.TEMPUS)
        );
        STATERA = new Chapter("wizards_reborn.arcanemicon.chapter.statera_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.statera_monogram", WizardsRebornMonograms.STATERA),
                new MonogramRecipesPage(WizardsRebornMonograms.STATERA)
        );
        ECLIPSIS = new Chapter("wizards_reborn.arcanemicon.chapter.eclipsis_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.eclipsis_monogram", WizardsRebornMonograms.ECLIPSIS),
                new MonogramRecipesPage(WizardsRebornMonograms.ECLIPSIS)
        );
        SICCITAS = new Chapter("wizards_reborn.arcanemicon.chapter.siccitas_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.siccitas_monogram", WizardsRebornMonograms.SICCITAS),
                new MonogramRecipesPage(WizardsRebornMonograms.SICCITAS)
        );
        SOLSTITIUM = new Chapter("wizards_reborn.arcanemicon.chapter.solstitium_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.solstitium_monogram", WizardsRebornMonograms.SOLSTITIUM),
                new MonogramRecipesPage(WizardsRebornMonograms.SOLSTITIUM)
        );
        FAMES = new Chapter("wizards_reborn.arcanemicon.chapter.fames_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.fames_monogram", WizardsRebornMonograms.FAMES),
                new MonogramRecipesPage(WizardsRebornMonograms.FAMES)
        );
        RENAISSANCE = new Chapter("wizards_reborn.arcanemicon.chapter.renaissance_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.renaissance_monogram", WizardsRebornMonograms.RENAISSANCE),
                new MonogramRecipesPage(WizardsRebornMonograms.RENAISSANCE)
        );
        BELLUM = new Chapter("wizards_reborn.arcanemicon.chapter.bellum_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.bellum_monogram", WizardsRebornMonograms.BELLUM),
                new MonogramRecipesPage(WizardsRebornMonograms.BELLUM)
        );
        LUX = new Chapter("wizards_reborn.arcanemicon.chapter.lux_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.lux_monogram", WizardsRebornMonograms.LUX),
                new MonogramRecipesPage(WizardsRebornMonograms.LUX)
        );
        KARA = new Chapter("wizards_reborn.arcanemicon.chapter.kara_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.kara_monogram", WizardsRebornMonograms.KARA),
                new MonogramRecipesPage(WizardsRebornMonograms.KARA)
        );
        DEGRADATIO = new Chapter("wizards_reborn.arcanemicon.chapter.degradatio_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.degradatio_monogram", WizardsRebornMonograms.DEGRADATIO),
                new MonogramRecipesPage(WizardsRebornMonograms.DEGRADATIO)
        );
        PRAEDICTIONEM = new Chapter("wizards_reborn.arcanemicon.chapter.praedictionem_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.praedictionem_monogram", WizardsRebornMonograms.PRAEDICTIONEM),
                new MonogramRecipesPage(WizardsRebornMonograms.PRAEDICTIONEM)
        );
        EVOLUTIONIS = new Chapter("wizards_reborn.arcanemicon.chapter.evolutionis_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.evolutionis_monogram", WizardsRebornMonograms.EVOLUTIONIS),
                new MonogramRecipesPage(WizardsRebornMonograms.EVOLUTIONIS)
        );
        TENEBRIS = new Chapter("wizards_reborn.arcanemicon.chapter.tenebris_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.tenebris_monogram", WizardsRebornMonograms.TENEBRIS),
                new MonogramRecipesPage(WizardsRebornMonograms.TENEBRIS)
        );
        UNIVERSUM = new Chapter("wizards_reborn.arcanemicon.chapter.universum_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.universum_monogram", WizardsRebornMonograms.UNIVERSUM),
                new MonogramRecipesPage(WizardsRebornMonograms.UNIVERSUM)
        );

        ALL_SPELLS = new Chapter("wizards_reborn.arcanemicon.chapter.all_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.all_spells",
                        new SpellIndexEntry(EARTH_PROJECTILE, WizardsRebornSpells.EARTH_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_PROJECTILE, WizardsRebornSpells.WATER_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_PROJECTILE, WizardsRebornSpells.AIR_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FIRE_PROJECTILE, WizardsRebornSpells.FIRE_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(VOID_PROJECTILE, WizardsRebornSpells.VOID_PROJECTILE, WizardsRebornKnowledges.VOID_CRYSTAL),
                        new SpellIndexEntry(FROST_PROJECTILE, WizardsRebornSpells.FROST_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(HOLY_PROJECTILE, WizardsRebornSpells.HOLY_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(CURSE_PROJECTILE, WizardsRebornSpells.CURSE_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(EARTH_RAY, WizardsRebornSpells.EARTH_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_RAY, WizardsRebornSpells.WATER_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_RAY, WizardsRebornSpells.AIR_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FIRE_RAY, WizardsRebornSpells.FIRE_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(VOID_RAY, WizardsRebornSpells.VOID_RAY, WizardsRebornKnowledges.VOID_CRYSTAL)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(FROST_RAY, WizardsRebornSpells.FROST_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_RAY, WizardsRebornSpells.HOLY_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(CURSE_RAY, WizardsRebornSpells.CURSE_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HEART_OF_NATURE, WizardsRebornSpells.HEART_OF_NATURE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_BREATHING, WizardsRebornSpells.WATER_BREATHING, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_FLOW, WizardsRebornSpells.AIR_FLOW, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FIRE_SHIELD, WizardsRebornSpells.FIRE_SHIELD, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(BLINK, WizardsRebornSpells.BLINK, WizardsRebornKnowledges.VOID_CRYSTAL),
                        new SpellIndexEntry(SNOWFLAKE, WizardsRebornSpells.SNOWFLAKE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_CROSS, WizardsRebornSpells.HOLY_CROSS, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(CURSE_CROSS, WizardsRebornSpells.CURSE_CROSS, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(POISON, WizardsRebornSpells.POISON, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(MAGIC_SPROUT, WizardsRebornSpells.MAGIC_SPROUT, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(DIRT_BLOCK, WizardsRebornSpells.DIRT_BLOCK, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(WATER_BLOCK, WizardsRebornSpells.WATER_BLOCK, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_IMPACT, WizardsRebornSpells.AIR_IMPACT, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(ICE_BLOCK, WizardsRebornSpells.ICE_BLOCK, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(EARTH_CHARGE, WizardsRebornSpells.EARTH_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WATER_CHARGE, WizardsRebornSpells.WATER_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(AIR_CHARGE, WizardsRebornSpells.AIR_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FIRE_CHARGE, WizardsRebornSpells.FIRE_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(VOID_CHARGE, WizardsRebornSpells.VOID_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FROST_CHARGE, WizardsRebornSpells.FROST_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(HOLY_CHARGE, WizardsRebornSpells.HOLY_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(CURSE_CHARGE, WizardsRebornSpells.CURSE_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(EARTH_AURA, WizardsRebornSpells.EARTH_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WATER_AURA, WizardsRebornSpells.WATER_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(AIR_AURA, WizardsRebornSpells.AIR_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FIRE_AURA, WizardsRebornSpells.FIRE_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(VOID_AURA, WizardsRebornSpells.VOID_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FROST_AURA, WizardsRebornSpells.FROST_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(HOLY_AURA, WizardsRebornSpells.HOLY_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(CURSE_AURA, WizardsRebornSpells.CURSE_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(RAIN_CLOUD, WizardsRebornSpells.RAIN_CLOUD, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(LAVA_BLOCK, WizardsRebornSpells.LAVA_BLOCK, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(ICICLE, WizardsRebornSpells.ICICLE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(SHARP_BLINK, WizardsRebornSpells.SHARP_BLINK, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(CRYSTAL_CRUSHING, WizardsRebornSpells.CRYSTAL_CRUSHING, WizardsRebornKnowledges.FACETED_CRYSTALS)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(TOXIC_RAIN, WizardsRebornSpells.TOXIC_RAIN, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(MOR_SWARM, WizardsRebornSpells.MOR_SWARM, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WITHERING, WizardsRebornSpells.WITHERING, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(IRRITATION, WizardsRebornSpells.IRRITATION, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(NECROTIC_RAY, WizardsRebornSpells.NECROTIC_RAY, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(LIGHT_RAY, WizardsRebornSpells.LIGHT_RAY, WizardsRebornKnowledges.LIGHT_EMITTER)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(INCINERATION, WizardsRebornSpells.INCINERATION, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(REPENTANCE, WizardsRebornSpells.REPENTANCE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(RENUNCIATION, WizardsRebornSpells.RENUNCIATION, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(EMBER_RAY, WizardsRebornSpells.EMBER_RAY, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WISDOM, WizardsRebornSpells.WISDOM, WizardsRebornKnowledges.FACETED_CRYSTALS)
                )
        );

        EARTH_SPELLS = new Chapter("wizards_reborn.arcanemicon.chapter.earth_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.earth_spells",
                        new SpellIndexEntry(EARTH_PROJECTILE, WizardsRebornSpells.EARTH_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_PROJECTILE, WizardsRebornSpells.HOLY_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(EARTH_RAY, WizardsRebornSpells.EARTH_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_RAY, WizardsRebornSpells.HOLY_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HEART_OF_NATURE, WizardsRebornSpells.HEART_OF_NATURE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_CROSS, WizardsRebornSpells.HOLY_CROSS, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(POISON, WizardsRebornSpells.POISON, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(MAGIC_SPROUT, WizardsRebornSpells.MAGIC_SPROUT, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(DIRT_BLOCK, WizardsRebornSpells.DIRT_BLOCK, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(EARTH_CHARGE, WizardsRebornSpells.EARTH_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(HOLY_CHARGE, WizardsRebornSpells.HOLY_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(EARTH_AURA, WizardsRebornSpells.EARTH_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(HOLY_AURA, WizardsRebornSpells.HOLY_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(TOXIC_RAIN, WizardsRebornSpells.TOXIC_RAIN, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(MOR_SWARM, WizardsRebornSpells.MOR_SWARM, WizardsRebornKnowledges.FACETED_CRYSTALS)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(REPENTANCE, WizardsRebornSpells.REPENTANCE, WizardsRebornKnowledges.FACETED_CRYSTALS)
                )
        );

        WATER_SPELLS = new Chapter("wizards_reborn.arcanemicon.chapter.water_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.water_spells",
                        new SpellIndexEntry(WATER_PROJECTILE, WizardsRebornSpells.WATER_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FROST_PROJECTILE, WizardsRebornSpells.FROST_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_RAY, WizardsRebornSpells.WATER_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FROST_RAY, WizardsRebornSpells.FROST_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_BREATHING, WizardsRebornSpells.WATER_BREATHING, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(SNOWFLAKE, WizardsRebornSpells.SNOWFLAKE, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(POISON, WizardsRebornSpells.POISON, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(MAGIC_SPROUT, WizardsRebornSpells.MAGIC_SPROUT, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_BLOCK, WizardsRebornSpells.WATER_BLOCK, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(ICE_BLOCK, WizardsRebornSpells.ICE_BLOCK, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(WATER_CHARGE, WizardsRebornSpells.WATER_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FROST_CHARGE, WizardsRebornSpells.FROST_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WATER_AURA, WizardsRebornSpells.WATER_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FROST_AURA, WizardsRebornSpells.FROST_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(RAIN_CLOUD, WizardsRebornSpells.RAIN_CLOUD, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(ICICLE, WizardsRebornSpells.ICICLE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(TOXIC_RAIN, WizardsRebornSpells.TOXIC_RAIN, WizardsRebornKnowledges.FACETED_CRYSTALS)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(MOR_SWARM, WizardsRebornSpells.MOR_SWARM, WizardsRebornKnowledges.FACETED_CRYSTALS)
                )
        );

        AIR_SPELLS = new Chapter("wizards_reborn.arcanemicon.chapter.air_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.air_spells",
                        new SpellIndexEntry(AIR_PROJECTILE, WizardsRebornSpells.AIR_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_PROJECTILE, WizardsRebornSpells.HOLY_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_RAY, WizardsRebornSpells.AIR_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_RAY, WizardsRebornSpells.HOLY_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_FLOW, WizardsRebornSpells.AIR_FLOW, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_CROSS, WizardsRebornSpells.HOLY_CROSS, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(AIR_IMPACT, WizardsRebornSpells.AIR_IMPACT, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(AIR_CHARGE, WizardsRebornSpells.AIR_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(HOLY_CHARGE, WizardsRebornSpells.HOLY_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(AIR_AURA, WizardsRebornSpells.AIR_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(HOLY_AURA, WizardsRebornSpells.HOLY_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WITHERING, WizardsRebornSpells.WITHERING, WizardsRebornKnowledges.FACETED_CRYSTALS)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(REPENTANCE, WizardsRebornSpells.REPENTANCE, WizardsRebornKnowledges.FACETED_CRYSTALS)
                )
        );

        FIRE_SPELLS = new Chapter("wizards_reborn.arcanemicon.chapter.fire_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.fire_spells",
                        new SpellIndexEntry(FIRE_PROJECTILE, WizardsRebornSpells.FIRE_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(CURSE_PROJECTILE, WizardsRebornSpells.CURSE_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FIRE_RAY, WizardsRebornSpells.FIRE_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(CURSE_RAY, WizardsRebornSpells.CURSE_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FIRE_SHIELD, WizardsRebornSpells.FIRE_SHIELD, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(CURSE_CROSS, WizardsRebornSpells.CURSE_CROSS, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(FIRE_CHARGE, WizardsRebornSpells.FIRE_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(CURSE_CHARGE, WizardsRebornSpells.CURSE_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(FIRE_AURA, WizardsRebornSpells.FIRE_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(CURSE_AURA, WizardsRebornSpells.CURSE_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(LAVA_BLOCK, WizardsRebornSpells.LAVA_BLOCK, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WITHERING, WizardsRebornSpells.WITHERING, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(LIGHT_RAY, WizardsRebornSpells.LIGHT_RAY, WizardsRebornKnowledges.LIGHT_EMITTER)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(INCINERATION, WizardsRebornSpells.INCINERATION, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(RENUNCIATION, WizardsRebornSpells.RENUNCIATION, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(EMBER_RAY, WizardsRebornSpells.EMBER_RAY, WizardsRebornKnowledges.FACETED_CRYSTALS)
                )
        );

        VOID_SPELLS = new Chapter("wizards_reborn.arcanemicon.chapter.void_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.void_spells",
                        new SpellIndexEntry(VOID_PROJECTILE, WizardsRebornSpells.VOID_PROJECTILE, WizardsRebornKnowledges.VOID_CRYSTAL),
                        new SpellIndexEntry(CURSE_PROJECTILE, WizardsRebornSpells.CURSE_PROJECTILE, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(VOID_RAY, WizardsRebornSpells.VOID_RAY, WizardsRebornKnowledges.VOID_CRYSTAL),
                        new SpellIndexEntry(CURSE_RAY, WizardsRebornSpells.CURSE_RAY, WizardsRebornKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(BLINK, WizardsRebornSpells.BLINK, WizardsRebornKnowledges.VOID_CRYSTAL),
                        new SpellIndexEntry(CURSE_CROSS, WizardsRebornSpells.CURSE_CROSS, WizardsRebornKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(VOID_CHARGE, WizardsRebornSpells.VOID_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(CURSE_CHARGE, WizardsRebornSpells.CURSE_CHARGE, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(VOID_AURA, WizardsRebornSpells.VOID_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(CURSE_AURA, WizardsRebornSpells.CURSE_AURA, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(SHARP_BLINK, WizardsRebornSpells.SHARP_BLINK, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(CRYSTAL_CRUSHING, WizardsRebornSpells.CRYSTAL_CRUSHING, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(IRRITATION, WizardsRebornSpells.IRRITATION, WizardsRebornKnowledges.FACETED_CRYSTALS)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(NECROTIC_RAY, WizardsRebornSpells.NECROTIC_RAY, WizardsRebornKnowledges.FACETED_CRYSTALS)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(RENUNCIATION, WizardsRebornSpells.RENUNCIATION, WizardsRebornKnowledges.FACETED_CRYSTALS),
                        new SpellIndexEntry(WISDOM, WizardsRebornSpells.WISDOM, WizardsRebornKnowledges.FACETED_CRYSTALS)
                )
        );

        RESEARCH_MAIN = new ResearchPage(true);
        RESEARCH_LIST = new ResearchPage(false);

        RESEARCH = new Chapter("wizards_reborn.arcanemicon.chapter.research",
                RESEARCH_MAIN,
                RESEARCH_LIST
        );

        RESEARCHES = new Chapter("wizards_reborn.arcanemicon.chapter.researches",
                new TitlePage("wizards_reborn.arcanemicon.page.researches.0"),
                new ResearchesPage("wizards_reborn.arcanemicon.page.researches.1")
        );

        MONOGRAMS = new Chapter("wizards_reborn.arcanemicon.chapter.monograms",
                new TitledMonogramIndexPage("wizards_reborn.arcanemicon.page.monograms",
                        new MonogramIndexEntry(LUNAM, WizardsRebornMonograms.LUNAM),
                        new MonogramIndexEntry(VITA, WizardsRebornMonograms.VITA),
                        new MonogramIndexEntry(SOLEM, WizardsRebornMonograms.SOLEM),
                        new MonogramIndexEntry(MORS, WizardsRebornMonograms.MORS),
                        new MonogramIndexEntry(MIRACULUM, WizardsRebornMonograms.MIRACULUM),
                        new MonogramIndexEntry(TEMPUS, WizardsRebornMonograms.TEMPUS)
                ),
                new MonogramIndexPage(
                        new MonogramIndexEntry(STATERA, WizardsRebornMonograms.STATERA),
                        new MonogramIndexEntry(ECLIPSIS, WizardsRebornMonograms.ECLIPSIS),
                        new MonogramIndexEntry(SICCITAS, WizardsRebornMonograms.SICCITAS),
                        new MonogramIndexEntry(SOLSTITIUM, WizardsRebornMonograms.SOLSTITIUM),
                        new MonogramIndexEntry(FAMES, WizardsRebornMonograms.FAMES),
                        new MonogramIndexEntry(RENAISSANCE, WizardsRebornMonograms.RENAISSANCE),
                        new MonogramIndexEntry(BELLUM, WizardsRebornMonograms.BELLUM)
                ),
                new MonogramIndexPage(
                        new MonogramIndexEntry(LUX, WizardsRebornMonograms.LUX),
                        new MonogramIndexEntry(KARA, WizardsRebornMonograms.KARA),
                        new MonogramIndexEntry(DEGRADATIO, WizardsRebornMonograms.DEGRADATIO),
                        new MonogramIndexEntry(PRAEDICTIONEM, WizardsRebornMonograms.PRAEDICTIONEM),
                        new MonogramIndexEntry(EVOLUTIONIS, WizardsRebornMonograms.EVOLUTIONIS),
                        new MonogramIndexEntry(TENEBRIS, WizardsRebornMonograms.TENEBRIS),
                        new MonogramIndexEntry(UNIVERSUM, WizardsRebornMonograms.UNIVERSUM)
                )
        );

        SPELLS_INDEX = new Chapter("wizards_reborn.arcanemicon.chapter.spells_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.spells_index",
                        new IndexEntry(ALL_SPELLS, new ItemStack(WizardsRebornItems.ARCANE_WAND.get()), WizardsRebornKnowledges.ARCANE_WAND),
                        new IndexEntry(EARTH_SPELLS, new ItemStack(WizardsRebornItems.FACETED_EARTH_CRYSTAL.get()), WizardsRebornKnowledges.ARCANE_WAND),
                        new IndexEntry(WATER_SPELLS, new ItemStack(WizardsRebornItems.FACETED_WATER_CRYSTAL.get()), WizardsRebornKnowledges.ARCANE_WAND),
                        new IndexEntry(AIR_SPELLS, new ItemStack(WizardsRebornItems.FACETED_AIR_CRYSTAL.get()), WizardsRebornKnowledges.ARCANE_WAND),
                        new IndexEntry(FIRE_SPELLS, new ItemStack(WizardsRebornItems.FACETED_FIRE_CRYSTAL.get()), WizardsRebornKnowledges.ARCANE_WAND),
                        new IndexEntry(VOID_SPELLS, new ItemStack(WizardsRebornItems.FACETED_VOID_CRYSTAL.get()), WizardsRebornKnowledges.VOID_CRYSTAL)
                ),
                new IndexPage(
                        new IndexEntry(RESEARCHES, new ItemStack(WizardsRebornItems.ARCANE_ENCHANTED_BOOK.get()), WizardsRebornKnowledges.ARCANE_WAND),
                        new IndexEntry(MONOGRAMS, new ItemStack(WizardsRebornItems.ARCANEMICON.get()), WizardsRebornKnowledges.ARCANE_WAND)
                )
        );
    }

    public static void crystalRitualsInit() {
        LIGHT_RAYS = new Chapter("wizards_reborn.arcanemicon.chapter.light_rays",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.light_rays.0",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.LIGHT_EMITTER.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, RUNIC_PEDESTAL_ITEM)
                ),
                new TextPage("wizards_reborn.arcanemicon.page.light_rays.1"),
                new ImagePage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/light_rays_image_page.png"))
        );

        LIGHT_EMITTER = new Chapter("wizards_reborn.arcanemicon.chapter.light_emitter",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.light_emitter",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.LIGHT_EMITTER.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.LIGHT_EMITTER.get()),
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, WISESTONE_PEDESTAL_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        ARCANUM_LENS_ITEM
                )
        );

        LIGHT_TRANSFER_LENS = new Chapter("wizards_reborn.arcanemicon.chapter.light_transfer_lens",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.light_transfer_lens",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get(), 2),
                        EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
                        ARCANUM_LENS_ITEM, EMPTY_ITEM, ARCANUM_LENS_ITEM
                )
        );

        RUNIC_PEDESTAL = new Chapter("wizards_reborn.arcanemicon.chapter.runic_pedestal",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.runic_pedestal",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, RUNIC_PEDESTAL_ITEM)
                ),
                new ArcaneWorkbenchPage(RUNIC_PEDESTAL_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        ARCANUM_LENS_ITEM, WISESTONE_PEDESTAL_ITEM, ARCANUM_LENS_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_plate",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get())),
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.RUNIC_WISESTONE_PLATE.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get()), ARCACITE_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM
                )
        );

        Map<CrystalRitual, ItemStack> crystalRituals = new HashMap<>();

        for (CrystalRitual ritual : CrystalRitualHandler.getCrystalRituals()) {
            ItemStack stack = new ItemStack(WizardsRebornItems.RUNIC_WISESTONE_PLATE.get());
            CrystalRitualUtil.setCrystalRitual(stack, ritual);
            crystalRituals.put(ritual, stack);
        }

        CRYSTALS_RITUALS = new Chapter("wizards_reborn.arcanemicon.chapter.crystal_rituals",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystal_rituals.0",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.crystal_rituals.1"),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand.resonance")
        );

        FOCUSING = new Chapter("wizards_reborn.arcanemicon.chapter.focusing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.focusing",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, crystalRituals.get(WizardsRebornCrystalRituals.FOCUSING))
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsRebornCrystalRituals.FOCUSING), new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get()),
                        ARCACITE_ITEM, ARCACITE_ITEM, ARCACITE_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM
                )
        );

        ARTIFICIAL_FERTILITY = new Chapter("wizards_reborn.arcanemicon.chapter.artificial_fertility",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.artificial_fertility",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, crystalRituals.get(WizardsRebornCrystalRituals.ARTIFICIAL_FERTILITY))
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsRebornCrystalRituals.ARTIFICIAL_FERTILITY), new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsRebornItems.FLOWER_FERTILIZER.get()), new ItemStack(WizardsRebornItems.FLOWER_FERTILIZER.get()), new ItemStack(WizardsRebornItems.FLOWER_FERTILIZER.get()),
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, new ItemStack(Items.HAY_BLOCK), new ItemStack(Items.WATER_BUCKET)
                )
        );

        RITUAL_BREEDING = new Chapter("wizards_reborn.arcanemicon.chapter.ritual_breeding",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.ritual_breeding",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, crystalRituals.get(WizardsRebornCrystalRituals.RITUAL_BREEDING))
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsRebornCrystalRituals.RITUAL_BREEDING), new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get()),
                        NATURAL_CALX_ITEM, SCORCHED_CALX_ITEM, SCORCHED_CALX_ITEM, SCORCHED_CALX_ITEM,
                        new ItemStack(Items.DIRT), new ItemStack(Items.BREAD), new ItemStack(Items.GOLDEN_CARROT)
                ),
                new CrystalRitualPage(WizardsRebornCrystalRituals.RITUAL_BREEDING,
                        new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.APPLE), new ItemStack(Items.APPLE), new ItemStack(Items.APPLE)
                ),
                new CrystalRitualPage(WizardsRebornCrystalRituals.RITUAL_BREEDING,
                        new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT)
                ),
                new CrystalRitualPage(WizardsRebornCrystalRituals.RITUAL_BREEDING,
                        new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO)
                ),
                new CrystalRitualPage(WizardsRebornCrystalRituals.RITUAL_BREEDING,
                        new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT)
                ),
                new CrystalRitualPage(WizardsRebornCrystalRituals.RITUAL_BREEDING,
                        new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.APPLE)
                ),
                new CrystalRitualPage(WizardsRebornCrystalRituals.RITUAL_BREEDING,
                        new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.CARROT)
                ),
                new CrystalRitualPage(WizardsRebornCrystalRituals.RITUAL_BREEDING,
                        new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.POTATO)
                ),
                new CrystalRitualPage(WizardsRebornCrystalRituals.RITUAL_BREEDING,
                        new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.BEETROOT)
                )
        );

        CRYSTAL_GROWTH_ACCELERATION = new Chapter("wizards_reborn.arcanemicon.chapter.crystal_growth_acceleration",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystal_growth_acceleration",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, crystalRituals.get(WizardsRebornCrystalRituals.CRYSTAL_GROWTH_ACCELERATION))
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsRebornCrystalRituals.CRYSTAL_GROWTH_ACCELERATION), new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get()),
                        ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM,
                        new ItemStack(Items.REDSTONE_BLOCK), new ItemStack(Items.REDSTONE_BLOCK),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                )
        );

        CRYSTAL_INFUSION = new Chapter("wizards_reborn.arcanemicon.chapter.crystal_infusion",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystal_infusion",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, crystalRituals.get(WizardsRebornCrystalRituals.CRYSTAL_INFUSION))
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsRebornCrystalRituals.CRYSTAL_INFUSION), new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()),
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsRebornCrystalRituals.CRYSTAL_INFUSION), new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsRebornItems.WATER_CRYSTAL.get()),
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsRebornCrystalRituals.CRYSTAL_INFUSION), new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsRebornItems.AIR_CRYSTAL.get()),
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsRebornCrystalRituals.CRYSTAL_INFUSION), new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsRebornItems.FIRE_CRYSTAL.get()),
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM
                ),
                new ArcaneIteratorPage(crystalRituals.get(WizardsRebornCrystalRituals.CRYSTAL_INFUSION), new ItemStack(WizardsRebornItems.WISESTONE_PLATE.get()),
                        new ItemStack(WizardsRebornItems.VOID_CRYSTAL.get()),
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANUM_LENS_ITEM, ARCANUM_LENS_ITEM
                )
        );

        ARCANUM_SEED = new Chapter("wizards_reborn.arcanemicon.chapter.arcanum_seed",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum_seed",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANUM_SEED.get()))
                ),
                new CrystalInfusionPage(new ItemStack(WizardsRebornItems.ARCANUM_SEED.get()), new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM,
                        ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM,
                        new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS)
                ),
                new ArcanumDustTransmutationPage(ARCANUM_ITEM, new ItemStack(WizardsRebornItems.ARCANUM_SEED.get()))
        );

        INNOCENT_WOOD = new Chapter("wizards_reborn.arcanemicon.chapter.innocent_wood",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.petals_of_innocence",
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.PETALS_OF_INNOCENCE.get()))
                ),
                new CrystalInfusionPage(new ItemStack(WizardsRebornItems.PETALS_OF_INNOCENCE.get()), new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()),
                        new ItemStack(Items.PINK_PETALS), ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM,
                        new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()),
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_sapling",
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SAPLING.get()))
                ),
                new CrystalInfusionPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SAPLING.get()), new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()),
                        new ItemStack(Items.CHERRY_SAPLING), ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM,
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood",
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD.get())),
                        new BlockEntry(INNOCENT_WOOD_PLANKS_ITEM),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_STAIRS.get())),
                        new BlockEntry(INNOCENT_WOOD_SLAB_ITEM),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_FENCE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_FENCE_GATE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_DOOR.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_TRAPDOOR.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PRESSURE_PLATE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_BUTTON.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SIGN.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_HANGING_SIGN.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_BOAT.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CHEST_BOAT.get())),
                        new BlockEntry(INNOCENT_WOOD_BRANCH_ITEM)
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD.get()),
                        new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD.get()),
                        new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PLANKS.get(), 4), new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_STAIRS.get(), 4),
                        INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SLAB.get(), 6),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_FENCE.get(), 3),
                        INNOCENT_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK), INNOCENT_WOOD_PLANKS_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK), INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_FENCE_GATE.get()),
                        new ItemStack(Items.STICK), INNOCENT_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK),
                        new ItemStack(Items.STICK), INNOCENT_WOOD_PLANKS_ITEM, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_DOOR.get(), 3),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_TRAPDOOR.get(), 2),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PRESSURE_PLATE.get()),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_BUTTON.get()), INNOCENT_WOOD_PLANKS_ITEM),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SIGN.get(), 3),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        EMPTY_ITEM, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_HANGING_SIGN.get(), 6),
                        new ItemStack(Items.CHAIN), EMPTY_ITEM, new ItemStack(Items.CHAIN),
                        new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()),
                        new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_BOAT.get()),
                        INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CHEST_BOAT.get()), new ItemStack(Items.CHEST), new ItemStack(WizardsRebornItems.INNOCENT_WOOD_BOAT.get())),
                new CraftingTablePage(INNOCENT_WOOD_BRANCH_ITEM,
                        new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get())
                ),
                new CraftingTablePage(INNOCENT_WOOD_BRANCH_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.gilded_innocent_wood_planks",
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GILDED_INNOCENT_WOOD_PLANKS.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GILDED_INNOCENT_WOOD_PLANKS.get()),
                        INNOCENT_WOOD_PLANKS_ITEM, ARCANE_GOLD_NUGGET_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_baulks",
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_BAULK.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_BAULK.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PLANKS_BAULK.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CROSS_BAULK.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_CROSS_BAULK.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PLANKS_CROSS_BAULK.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_BAULK.get(), 6),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()), EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_BAULK.get(), 6),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG.get()), EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PLANKS_BAULK.get(), 6),
                        EMPTY_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_CROSS_BAULK.get()),
                        new ItemStack(WizardsRebornItems.INNOCENT_WOOD_BAULK.get()), ARCANE_LINEN_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_CROSS_BAULK.get()),
                        new ItemStack(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_BAULK.get()), ARCANE_LINEN_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PLANKS_CROSS_BAULK.get()),
                        new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PLANKS_BAULK.get()), ARCANE_LINEN_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_mortar",
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_MORTAR.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_smoking_pipe",
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SMOKING_PIPE.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SMOKING_PIPE.get()),
                        INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        NATURAL_CALX_ITEM, EMPTY_ITEM, ALCHEMY_CALX_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_pedestal",
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, INNOCENT_PEDESTAL_ITEM)
                ),
                new CraftingTablePage(INNOCENT_PEDESTAL_ITEM,
                        INNOCENT_WOOD_SLAB_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_SLAB_ITEM,
                        EMPTY_ITEM, INNOCENT_WOOD_PLANKS_ITEM, EMPTY_ITEM,
                        INNOCENT_WOOD_SLAB_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_keg",
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_KEG.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_KEG.get()),
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_SLAB_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, NETHER_SALT_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_SLAB_ITEM, INNOCENT_WOOD_PLANKS_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM,  ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_fire",
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_SALT_TORCH.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_SALT_LANTERN.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_SALT_CAMPFIRE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_SALT_TORCH.get(), 4),
                        EMPTY_ITEM, NETHER_SALT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_SALT_LANTERN.get()),
                        EMPTY_ITEM, new ItemStack(Items.CHAIN), EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_SALT_TORCH.get()), ARCANE_GOLD_NUGGET_ITEM,
                        INNOCENT_WOOD_SLAB_ITEM, INNOCENT_WOOD_SLAB_ITEM, INNOCENT_WOOD_SLAB_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.INNOCENT_SALT_CAMPFIRE.get()),
                        EMPTY_ITEM, new ItemStack(Items.STICK), EMPTY_ITEM,
                        new ItemStack(Items.STICK), NETHER_SALT_ITEM, new ItemStack(Items.STICK),
                        new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get())
                )
        );

        INNOCENT_WOOD_TOOLS = new Chapter("wizards_reborn.arcanemicon.chapter.innocent_wood_tools",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wood_tools",
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SWORD.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PICKAXE.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_AXE.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SHOVEL.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_HOE.get())),
                        new BlockEntry(INNOCENT_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SCYTHE.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SWORD.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_SWORD.get()),
                        INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM,
                        ARCACITE_ITEM, ARCACITE_ITEM, NATURAL_CALX_ITEM
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PICKAXE.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_PICKAXE.get()),
                        INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM,
                        ARCACITE_ITEM, ARCACITE_ITEM, NATURAL_CALX_ITEM
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_AXE.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_AXE.get()),
                        INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM,
                        ARCACITE_ITEM, ARCACITE_ITEM, NATURAL_CALX_ITEM
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SHOVEL.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_SHOVEL.get()),
                        INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM,
                        ARCACITE_ITEM, ARCACITE_ITEM, NATURAL_CALX_ITEM
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_HOE.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_HOE.get()),
                        INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM,
                        ARCACITE_ITEM, ARCACITE_ITEM, NATURAL_CALX_ITEM
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SCYTHE.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_SCYTHE.get()),
                        INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM, INNOCENT_WOOD_BRANCH_ITEM,
                        ARCACITE_ITEM, ARCACITE_ITEM, NATURAL_CALX_ITEM
                )
        );

        PHANTOM_INK_TRIM = new Chapter("wizards_reborn.arcanemicon.chapter.phantom_ink_trim",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.phantom_ink_trim",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_TRIM.get())),
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.PHANTOM_INK_TRIM.get()))
                ),
                new ArcaneIteratorPage(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_TRIM.get()), ARCANE_GOLD_NUGGET_ITEM,
                        new ItemStack(Items.STRING), INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM, INNOCENT_WOOD_PLANKS_ITEM
                ),
                new CrystalInfusionPage(new ItemStack(WizardsRebornItems.PHANTOM_INK_TRIM.get()), new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()),
                        new ItemStack(WizardsRebornItems.INNOCENT_WOOD_TRIM.get()), new ItemStack(Items.GLOW_INK_SAC), new ItemStack(Items.ENDER_PEARL),
                        ARCANUM_ITEM, ARCANUM_ITEM, ENCHANTED_CALX_ITEM
                )
        );

        MUSIC_DISC_DISCO = new Chapter("wizards_reborn.arcanemicon.chapter.music_disc_disco",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.music_disc_disco",
                        new BlockEntry(RUNIC_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MUSIC_DISC_DISCO.get()))
                ),
                new CrystalInfusionPage(new ItemStack(WizardsRebornItems.MUSIC_DISC_DISCO.get()), new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()),
                        new ItemStack(Items.MUSIC_DISC_13), INNOCENT_WOOD_PLANKS_ITEM, new ItemStack(WizardsRebornItems.EARTH_CRYSTAL_SEED.get())
                )
        );

        CRYSTAL_RITUALS_INDEX = new Chapter("wizards_reborn.arcanemicon.chapter.crystal_rituals_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.crystal_rituals_index",
                        new IndexEntry(LIGHT_RAYS, new ItemStack(WizardsRebornItems.WISSEN_WAND.get()), WizardsRebornKnowledges.ARCANUM_LENS),
                        new IndexEntry(LIGHT_EMITTER, new ItemStack(WizardsRebornItems.LIGHT_EMITTER.get()), WizardsRebornKnowledges.ARCANUM_LENS),
                        new IndexEntry(LIGHT_TRANSFER_LENS, new ItemStack(WizardsRebornItems.LIGHT_TRANSFER_LENS.get()), WizardsRebornKnowledges.ARCANUM_LENS),
                        new IndexEntry(RUNIC_PEDESTAL, RUNIC_PEDESTAL_ITEM, WizardsRebornKnowledges.ARCANUM_LENS),
                        new IndexEntry(CRYSTALS_RITUALS, new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()), WizardsRebornKnowledges.RUNIC_PEDESTAL),
                        new IndexEntry(FOCUSING, crystalRituals.get(WizardsRebornCrystalRituals.FOCUSING), WizardsRebornKnowledges.RUNIC_PEDESTAL)
                ),
                new IndexPage(
                        new IndexEntry(ARTIFICIAL_FERTILITY, crystalRituals.get(WizardsRebornCrystalRituals.ARTIFICIAL_FERTILITY), WizardsRebornKnowledges.RUNIC_PEDESTAL),
                        new IndexEntry(RITUAL_BREEDING, crystalRituals.get(WizardsRebornCrystalRituals.RITUAL_BREEDING), WizardsRebornKnowledges.RUNIC_PEDESTAL),
                        new IndexEntry(CRYSTAL_GROWTH_ACCELERATION, crystalRituals.get(WizardsRebornCrystalRituals.CRYSTAL_GROWTH_ACCELERATION), WizardsRebornKnowledges.RUNIC_PEDESTAL),
                        new IndexEntry(CRYSTAL_INFUSION, crystalRituals.get(WizardsRebornCrystalRituals.CRYSTAL_INFUSION), WizardsRebornKnowledges.RUNIC_PEDESTAL),
                        new IndexEntry(ARCANUM_SEED, new ItemStack(WizardsRebornItems.ARCANUM_SEED.get()), WizardsRebornKnowledges.CRYSTAL_INFUSION),
                        new IndexEntry(INNOCENT_WOOD, INNOCENT_WOOD_PLANKS_ITEM, WizardsRebornKnowledges.CRYSTAL_INFUSION),
                        new IndexEntry(INNOCENT_WOOD_TOOLS, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PICKAXE.get()), WizardsRebornKnowledges.INNOCENT_WOOD)
                ),
                new IndexPage(
                        new IndexEntry(PHANTOM_INK_TRIM, new ItemStack(WizardsRebornItems.PHANTOM_INK_TRIM.get()), WizardsRebornKnowledges.INNOCENT_WOOD),
                        new IndexEntry(MUSIC_DISC_DISCO, new ItemStack(WizardsRebornItems.MUSIC_DISC_DISCO.get()), WizardsRebornKnowledges.INNOCENT_WOOD)
                )
        );
    }

    public static void alchemyInit() {
        MOR = new Chapter("wizards_reborn.arcanemicon.chapter.mor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.mor.0",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MOR.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MOR_BLOCK.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ELDER_MOR.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ELDER_MOR_BLOCK.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.mor.1"),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.mor_soup",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MOR_SOUP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ELDER_MOR_SOUP.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.MOR_SOUP.get()),
                        new ItemStack(Items.BOWL), new ItemStack(WizardsRebornItems.MOR.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.MOR.get()), new ItemStack(WizardsRebornItems.MOR.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ELDER_MOR_SOUP.get()),
                        new ItemStack(Items.BOWL), new ItemStack(WizardsRebornItems.ELDER_MOR.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.ELDER_MOR.get()), new ItemStack(WizardsRebornItems.ELDER_MOR.get())
                )
        );

        MORTAR = new Chapter("wizards_reborn.arcanemicon.chapter.mortar",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.mortar",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_MORTAR.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.petals",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.PETALS.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.flower_fertilizer",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FLOWER_FERTILIZER.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.bunch_of_things",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.BUNCH_OF_THINGS.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.ground_mushrooms",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GROUND_BROWN_MUSHROOM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GROUND_RED_MUSHROOM.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GROUND_CRIMSON_FUNGUS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GROUND_WARPED_FUNGUS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GROUND_MOR.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GROUND_ELDER_MOR.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_MORTAR.get()),
                        new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS.get()), ARCANE_WOOD_BRANCH_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS.get())
                ),
                new MortarPage(new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(Items.DANDELION)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.FLOWER_FERTILIZER.get()), new ItemStack(Items.BONE_MEAL), new ItemStack(WizardsRebornItems.PETALS.get()), EMPTY_ITEM, new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.BUNCH_OF_THINGS.get()), ARCANE_WOOD_BRANCH_ITEM, ARCANE_WOOD_BRANCH_ITEM, EMPTY_ITEM, new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get())),
                new MortarPage(new ItemStack(WizardsRebornItems.GROUND_BROWN_MUSHROOM.get()), new ItemStack(Items.BROWN_MUSHROOM)),
                new MortarPage(new ItemStack(WizardsRebornItems.GROUND_RED_MUSHROOM.get()), new ItemStack(Items.RED_MUSHROOM)),
                new MortarPage(new ItemStack(WizardsRebornItems.GROUND_CRIMSON_FUNGUS.get()), new ItemStack(Items.CRIMSON_FUNGUS)),
                new MortarPage(new ItemStack(WizardsRebornItems.GROUND_WARPED_FUNGUS.get()), new ItemStack(Items.WARPED_FUNGUS)),
                new MortarPage(new ItemStack(WizardsRebornItems.GROUND_MOR.get()), new ItemStack(WizardsRebornItems.MOR.get())),
                new MortarPage(new ItemStack(WizardsRebornItems.GROUND_ELDER_MOR.get()), new ItemStack(WizardsRebornItems.ELDER_MOR.get()))
        );

        ARCANE_LINEN = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_linen",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_linen",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_LINEN_SEEDS.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, ARCANE_LINEN_ITEM),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_LINEN_BALE.get()))
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsRebornItems.ARCANE_LINEN_SEEDS.get()),
                        new ItemStack(Items.WHEAT_SEEDS), ARCANUM_ITEM, ARCANUM_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT)
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_LINEN_BALE.get()),
                        ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM,
                        ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM,
                        ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_LINEN.get(), 9), new ItemStack(WizardsRebornItems.ARCANE_LINEN_BALE.get())),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.cross_baulks",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_CROSS_BAULK.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_CROSS_BAULK.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS_CROSS_BAULK.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_CROSS_BAULK.get()),
                        new ItemStack(WizardsRebornItems.ARCANE_WOOD_BAULK.get()), ARCANE_LINEN_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_CROSS_BAULK.get()),
                        new ItemStack(WizardsRebornItems.STRIPPED_ARCANE_WOOD_BAULK.get()), ARCANE_LINEN_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS_CROSS_BAULK.get()),
                        new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS_BAULK.get()), ARCANE_LINEN_ITEM
                )
        );

        MUSHROOM_CAPS = new Chapter("wizards_reborn.arcanemicon.chapter.mushroom_caps",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.mushroom_caps",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.BROWN_MUSHROOM_CAP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.RED_MUSHROOM_CAP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CRIMSON_FUNGUS_CAP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WARPED_FUNGUS_CAP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MOR_CAP.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ELDER_MOR_CAP.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.BROWN_MUSHROOM_CAP.get()),
                        new ItemStack(Items.BROWN_MUSHROOM_BLOCK), new ItemStack(Items.BROWN_MUSHROOM_BLOCK), new ItemStack(Items.BROWN_MUSHROOM_BLOCK),
                        new ItemStack(Items.BROWN_MUSHROOM_BLOCK), new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.BROWN_MUSHROOM_BLOCK),
                        EMPTY_ITEM, new ItemStack(Items.MUSHROOM_STEM)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.RED_MUSHROOM_CAP.get()),
                        new ItemStack(Items.RED_MUSHROOM_BLOCK), new ItemStack(Items.RED_MUSHROOM_BLOCK), new ItemStack(Items.RED_MUSHROOM_BLOCK),
                        new ItemStack(Items.RED_MUSHROOM_BLOCK), new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.RED_MUSHROOM_BLOCK),
                        EMPTY_ITEM, new ItemStack(Items.MUSHROOM_STEM)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.CRIMSON_FUNGUS_CAP.get()),
                        new ItemStack(Items.NETHER_WART_BLOCK), new ItemStack(Items.NETHER_WART_BLOCK), new ItemStack(Items.NETHER_WART_BLOCK),
                        new ItemStack(Items.NETHER_WART_BLOCK), new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.NETHER_WART_BLOCK),
                        EMPTY_ITEM, new ItemStack(Items.CRIMSON_STEM)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WARPED_FUNGUS_CAP.get()),
                        new ItemStack(Items.WARPED_WART_BLOCK), new ItemStack(Items.WARPED_WART_BLOCK), new ItemStack(Items.WARPED_WART_BLOCK),
                        new ItemStack(Items.WARPED_WART_BLOCK), new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.WARPED_WART_BLOCK),
                        EMPTY_ITEM, new ItemStack(Items.WARPED_STEM)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.MOR_CAP.get()),
                        new ItemStack(WizardsRebornItems.MOR_BLOCK.get()), new ItemStack(WizardsRebornItems.MOR_BLOCK.get()), new ItemStack(WizardsRebornItems.MOR_BLOCK.get()),
                        new ItemStack(WizardsRebornItems.MOR_BLOCK.get()), new ItemStack(Items.LEATHER_HELMET), new ItemStack(WizardsRebornItems.MOR_BLOCK.get()),
                        EMPTY_ITEM, new ItemStack(Items.MUSHROOM_STEM)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ELDER_MOR_CAP.get()),
                        new ItemStack(WizardsRebornItems.ELDER_MOR_BLOCK.get()), new ItemStack(WizardsRebornItems.ELDER_MOR_BLOCK.get()), new ItemStack(WizardsRebornItems.ELDER_MOR_BLOCK.get()),
                        new ItemStack(WizardsRebornItems.ELDER_MOR_BLOCK.get()), new ItemStack(Items.LEATHER_HELMET), new ItemStack(WizardsRebornItems.ELDER_MOR_BLOCK.get()),
                        EMPTY_ITEM, new ItemStack(Items.MUSHROOM_STEM)
                )
        );

        WISESTONE = new Chapter("wizards_reborn.arcanemicon.chapter.wisestone",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISESTONE.get(), 8),
                        new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE),
                        new ItemStack(Items.COBBLESTONE), ARCANUM_ITEM, new ItemStack(Items.COBBLESTONE),
                        new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISESTONE.get(), 8),
                        new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE),
                        new ItemStack(Items.COBBLED_DEEPSLATE), ARCANUM_ITEM, new ItemStack(Items.COBBLED_DEEPSLATE),
                        new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE)
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WISESTONE.get(), 8),
                        new ItemStack(Items.BLACKSTONE), new ItemStack(Items.BLACKSTONE), new ItemStack(Items.BLACKSTONE),
                        new ItemStack(Items.BLACKSTONE), ARCANUM_ITEM, new ItemStack(Items.BLACKSTONE),
                        new ItemStack(Items.BLACKSTONE), new ItemStack(Items.BLACKSTONE), new ItemStack(Items.BLACKSTONE)
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.decorative_wisestone",
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_STAIRS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_SLAB.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_WALL.get())),
                        new BlockEntry(POLISHED_WISESTONE_ITEM),
                        new BlockEntry(new ItemStack(WizardsRebornItems.POLISHED_WISESTONE_STAIRS.get())),
                        new BlockEntry(POLISHED_WISESTONE_SLAB_ITEM),
                        new BlockEntry(new ItemStack(WizardsRebornItems.POLISHED_WISESTONE_WALL.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_BRICKS_STAIRS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_BRICKS_SLAB.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_BRICKS_WALL.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_TILE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_TILE_STAIRS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_TILE_SLAB.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_TILE_WALL.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CHISELED_WISESTONE_STAIRS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CHISELED_WISESTONE_SLAB.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CHISELED_WISESTONE_WALL.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.WISESTONE_PILLAR.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.POLISHED_WISESTONE_PRESSURE_PLATE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.POLISHED_WISESTONE_BUTTON.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_STAIRS.get(), 4),
                        new ItemStack(WizardsRebornItems.WISESTONE.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_SLAB.get(), 6),
                        new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_WALL.get(), 6),
                        new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get()),
                        new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.POLISHED_WISESTONE.get(), 4),
                        new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISESTONE.get()), new ItemStack(WizardsRebornItems.WISESTONE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.POLISHED_WISESTONE_STAIRS.get(), 4),
                        POLISHED_WISESTONE_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.POLISHED_WISESTONE_SLAB.get(), 6),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.POLISHED_WISESTONE_WALL.get(), 6),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get(), 4),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_BRICKS_STAIRS.get(), 4),
                        new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_BRICKS_SLAB.get(), 6),
                        new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_BRICKS_WALL.get(), 6),
                        new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()),
                        new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_TILE.get(), 4),
                        new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get()), new ItemStack(WizardsRebornItems.WISESTONE_BRICKS.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_TILE_STAIRS.get(), 4),
                        new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), new ItemStack(WizardsRebornItems.WISESTONE_TILE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_TILE_SLAB.get(), 6),
                        new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), new ItemStack(WizardsRebornItems.WISESTONE_TILE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_TILE_WALL.get(), 6),
                        new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()),
                        new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), new ItemStack(WizardsRebornItems.WISESTONE_TILE.get()), new ItemStack(WizardsRebornItems.WISESTONE_TILE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get(), 4),
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, EMPTY_ITEM, POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CHISELED_WISESTONE_STAIRS.get(), 4),
                        new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CHISELED_WISESTONE_SLAB.get(), 6),
                        new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CHISELED_WISESTONE_WALL.get(), 6),
                        new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()),
                        new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get()), new ItemStack(WizardsRebornItems.CHISELED_WISESTONE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_PILLAR.get(), 2),
                        POLISHED_WISESTONE_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.POLISHED_WISESTONE_PRESSURE_PLATE.get(), 1),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.POLISHED_WISESTONE_BUTTON.get(), 1),
                        POLISHED_WISESTONE_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.gilded_polished_wisestone",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GILDED_POLISHED_WISESTONE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GILDED_POLISHED_WISESTONE.get()),
                        POLISHED_WISESTONE_ITEM, ARCANE_GOLD_NUGGET_ITEM
                )
        );

        WISESTONE_PEDESTAL = new Chapter("wizards_reborn.arcanemicon.chapter.wisestone_pedestal",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wisestone_pedestal",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, WISESTONE_PEDESTAL_ITEM)
                ),
                new ArcaneWorkbenchPage(WISESTONE_PEDESTAL_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
                        ARCANE_GOLD_INGOT_ITEM
                )
        );

        FLUID_PIPES = new Chapter("wizards_reborn.arcanemicon.chapter.fluid_pipes",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.fluid_pipe",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FLUID_PIPE.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.fluid_extractor",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FLUID_EXTRACTOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.FLUID_PIPE.get(), 6),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_SLAB_ITEM, EMPTY_ITEM, POLISHED_WISESTONE_SLAB_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.FLUID_EXTRACTOR.get()),
                        new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(Items.REDSTONE)
                )
        );

        STEAM_PIPES = new Chapter("wizards_reborn.arcanemicon.chapter.steam_pipes",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.steam_pipe",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.STEAM_PIPE.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.steam_extractor",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.STEAM_EXTRACTOR.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.STEAM_PIPE.get(), 4),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.STEAM_EXTRACTOR.get()),
                        new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), new ItemStack(Items.REDSTONE)
                )
        );

        ORBITAL_FLUID_RETAINER = new Chapter("wizards_reborn.arcanemicon.chapter.orbital_fluid_retainer",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.orbital_fluid_retainer",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ORBITAL_FLUID_RETAINER.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ORBITAL_FLUID_RETAINER.get()),
                        ARCANUM_ITEM, ARCANUM_ITEM, ARCANUM_ITEM,
                        POLISHED_WISESTONE_ITEM, WISESTONE_PEDESTAL_ITEM, POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM
                )
        );

        ALCHEMY_FURNACE = new Chapter("wizards_reborn.arcanemicon.chapter.alchemy_furnace",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_furnace",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ALCHEMY_FURNACE.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ALCHEMY_FURNACE.get()),
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
                        POLISHED_WISESTONE_ITEM, new ItemStack(Items.FURNACE), POLISHED_WISESTONE_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), EMPTY_ITEM, new ItemStack(WizardsRebornItems.FLUID_PIPE.get())
                ),
                new ImagePage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/alchemy_furnace_image_page.png"))
        );

        STEAM_THERMAL_STORAGE = new Chapter("wizards_reborn.arcanemicon.chapter.steam_thermal_storage",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.steam_thermal_storage",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.STEAM_THERMAL_STORAGE.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.STEAM_THERMAL_STORAGE.get()),
                        ARCANE_GOLD_INGOT_ITEM, POLISHED_WISESTONE_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        POLISHED_WISESTONE_ITEM, new ItemStack(Items.GLASS), POLISHED_WISESTONE_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, POLISHED_WISESTONE_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), ARCANE_GOLD_NUGGET_ITEM
                )
        );

        ALCHEMY_MACHINE = new Chapter("wizards_reborn.arcanemicon.chapter.alchemy_machine",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_machine",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ALCHEMY_MACHINE.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_boiler",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ALCHEMY_BOILER.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ALCHEMY_MACHINE.get()),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ORBITAL_FLUID_RETAINER.get()), EMPTY_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsRebornItems.FLUID_PIPE.get())
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ALCHEMY_BOILER.get()),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()), EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), ARCANE_GOLD_INGOT_ITEM, new ItemStack(WizardsRebornItems.FLUID_PIPE.get())
                ),
                new ImagePage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/alchemy_machine_image_page.png")),
                new AlchemyMachinePage().setFluidResult(new FluidStack(Fluids.LAVA, 750)).setIsSteam(true).setInputs(
                        new ItemStack(Items.MAGMA_BLOCK), new ItemStack(Items.MAGMA_BLOCK), new ItemStack(Items.MAGMA_BLOCK), new ItemStack(Items.MAGMA_BLOCK))
        );

        ALCHEMY_OIL = new Chapter("wizards_reborn.arcanemicon.chapter.alchemy_oil",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_oil",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ALCHEMY_OIL_BUCKET.get()))
                ),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 1000)).setIsSteam(true).setInputs(
                        ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM,
                        new ItemStack(WizardsRebornItems.PETALS.get()))
        );

        MUSIC_DISC_ARCANUM = new Chapter("wizards_reborn.arcanemicon.chapter.music_disc_arcanum",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.music_disc_arcanum",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MUSIC_DISC_ARCANUM.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.MUSIC_DISC_ARCANUM.get())).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 1000))
                        .setInputs(new ItemStack(Items.MUSIC_DISC_13), ARCANUM_ITEM)
        );

        MUSIC_DISC_MOR = new Chapter("wizards_reborn.arcanemicon.chapter.music_disc_mor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.music_disc_mor",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MUSIC_DISC_MOR.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.MUSIC_DISC_MOR.get())).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 1000))
                        .setInputs(new ItemStack(Items.MUSIC_DISC_13), new ItemStack(WizardsRebornItems.MOR.get()))
        );

        ALCHEMY_CALX = new Chapter("wizards_reborn.arcanemicon.chapter.alchemy_calx",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_calx",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, ALCHEMY_CALX_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, ALCHEMY_CALX_PILE_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ALCHEMY_CALX_BLOCK.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.ALCHEMY_CALX.get(), 3)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 150))
                        .setInputs(new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ), new ItemStack(Items.CALCITE),
                        new ItemStack(Items.BONE_MEAL), new ItemStack(Items.BONE_MEAL), ARCANE_LINEN_ITEM),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ALCHEMY_CALX_BLOCK.get()),
                        ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM,
                        ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM,
                        ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ALCHEMY_CALX.get(), 9), new ItemStack(WizardsRebornItems.ALCHEMY_CALX_BLOCK.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ALCHEMY_CALX_PILE.get(), 5), ALCHEMY_CALX_ITEM),
                new CraftingTablePage(ALCHEMY_CALX_ITEM,
                        ALCHEMY_CALX_PILE_ITEM, ALCHEMY_CALX_PILE_ITEM, ALCHEMY_CALX_PILE_ITEM,
                        ALCHEMY_CALX_PILE_ITEM, ALCHEMY_CALX_PILE_ITEM
                )
        );

        Map<AlchemyPotion, ItemStack> vialPotions = new HashMap<>();
        Map<AlchemyPotion, ItemStack> flaskPotions = new HashMap<>();

        for (AlchemyPotion potion : AlchemyPotionHandler.getAlchemyPotions()) {
            ItemStack stack = new ItemStack(WizardsRebornItems.ALCHEMY_VIAL_POTION.get());
            AlchemyPotionUtil.setPotion(stack, potion);
            vialPotions.put(potion, stack);

            stack = new ItemStack(WizardsRebornItems.ALCHEMY_FLASK_POTION.get());
            AlchemyPotionUtil.setPotion(stack, potion);
            flaskPotions.put(potion, stack);
        }

        ALCHEMY_GLASS = new Chapter("wizards_reborn.arcanemicon.chapter.alchemy_glass",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_glass",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, ACLHEMY_GLASS)
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.ALCHEMY_GLASS.get(), 4)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 500))
                        .setInputs(new ItemStack(Items.GLASS), new ItemStack(Items.GLASS), new ItemStack(Items.GLASS), new ItemStack(Items.GLASS),
                        ARCANUM_DUST_ITEM, ALCHEMY_CALX_PILE_ITEM),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_vial",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_PLANKS.get()), EMPTY_ITEM,
                        ACLHEMY_GLASS, EMPTY_ITEM, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.ALCHEMY_OIL)).setIsSteam(true).setInputs(
                        ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM, ARCANE_LINEN_ITEM,
                        new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get())
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.luminal_glass",
                        new BlockEntry(new ItemStack(WizardsRebornItems.WHITE_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIGHT_GRAY_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.GRAY_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BLACK_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BROWN_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.RED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ORANGE_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.YELLOW_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIME_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.GREEN_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CYAN_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIGHT_BLUE_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BLUE_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.PURPLE_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.MAGENTA_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.PINK_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.RAINBOW_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.COSMIC_LUMINAL_GLASS.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.WHITE_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.LIGHT_GRAY_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.LIGHT_GRAY_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.GRAY_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.GRAY_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.BLACK_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.BLACK_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.BROWN_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.BROWN_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.RED_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.RED_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ORANGE_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.ORANGE_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.YELLOW_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.YELLOW_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.LIME_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.LIME_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.GREEN_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.GREEN_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.CYAN_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.CYAN_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.LIGHT_BLUE_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.LIGHT_BLUE_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.BLUE_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.BLUE_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.PURPLE_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.PURPLE_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.MAGENTA_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.MAGENTA_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.PINK_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.PINK_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.RAINBOW_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.RAINBOW_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.COSMIC_LUMINAL_GLASS.get()),
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS,
                        ACLHEMY_GLASS, new ItemStack(WizardsRebornItems.COSMIC_ARCANE_LUMOS.get()), ACLHEMY_GLASS,
                        ACLHEMY_GLASS, ACLHEMY_GLASS, ACLHEMY_GLASS
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.framed_luminal_glass",
                        new BlockEntry(new ItemStack(WizardsRebornItems.WHITE_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIGHT_GRAY_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.GRAY_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BLACK_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BROWN_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.RED_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.ORANGE_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.YELLOW_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIME_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.GREEN_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CYAN_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.LIGHT_BLUE_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.BLUE_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.PURPLE_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.MAGENTA_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.PINK_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.RAINBOW_FRAMED_LUMINAL_GLASS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.COSMIC_FRAMED_LUMINAL_GLASS.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WHITE_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.WHITE_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.LIGHT_GRAY_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.LIGHT_GRAY_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GRAY_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.GRAY_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.BLACK_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.BLACK_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.BROWN_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.BROWN_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.RED_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.RED_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ORANGE_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.ORANGE_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.YELLOW_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.YELLOW_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.LIME_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.LIME_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GREEN_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.GREEN_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CYAN_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.CYAN_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.LIGHT_BLUE_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.LIGHT_BLUE_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.BLUE_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.BLUE_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.PURPLE_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.PURPLE_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.MAGENTA_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.MAGENTA_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.PINK_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.PINK_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.RAINBOW_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.RAINBOW_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.COSMIC_FRAMED_LUMINAL_GLASS.get()), new ItemStack(WizardsRebornItems.COSMIC_LUMINAL_GLASS.get()), new ItemStack(Items.IRON_NUGGET))
        );

        ALCHEMY_BAG = new Chapter("wizards_reborn.arcanemicon.chapter.alchemy_bag",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_bag",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ALCHEMY_BAG.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ALCHEMY_BAG.get()),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.LEATHER_BELT.get()), EMPTY_ITEM,
                        new ItemStack(Items.LEATHER), new ItemStack(Items.CHEST), new ItemStack(Items.LEATHER),
                        new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()), new ItemStack(Items.LEATHER), new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()),
                        ALCHEMY_CALX_ITEM, EMPTY_ITEM, ALCHEMY_CALX_ITEM
                )
        );

        ALCHEMY_POTIONS = new Chapter("wizards_reborn.arcanemicon.chapter.alchemy_potions",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_potions",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW))
                ),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.MUNDANE_BREW.get(), 250)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 250))
                        .setInputs(new ItemStack(Items.NETHER_WART)),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.MUNDANE_BREW.get(), 1000)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW)).setIsSteam(true).setInputs(
                        new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART), vialPotions.get(WizardsRebornAlchemyPotions.WATER)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.NIGHT_VISION)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.REDSTONE)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.INVISIBILITY)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.NIGHT_VISION), new ItemStack(Items.FERMENTED_SPIDER_EYE)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.LEAPING)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.RABBIT_FOOT), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.FIRE_RESISTANCE)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.REDSTONE)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.SWIFTNESS)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.SUGAR), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.SLOWNESS)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.SWIFTNESS), new ItemStack(Items.FERMENTED_SPIDER_EYE)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.SLOWNESS)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.LEAPING), new ItemStack(Items.FERMENTED_SPIDER_EYE)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.TURTLE_MASTER)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.TURTLE_HELMET), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.WATER_BREATHING)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.PUFFERFISH), new ItemStack(Items.REDSTONE)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.HEALING)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.GLISTERING_MELON_SLICE), new ItemStack(Items.GLOWSTONE_DUST)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.HARMING)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.HEALING), new ItemStack(Items.FERMENTED_SPIDER_EYE)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.HARMING)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.POISON), new ItemStack(Items.FERMENTED_SPIDER_EYE)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.POISON)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.REGENERATION)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.STRENGTH)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.WEAKNESS)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.STRENGTH), new ItemStack(Items.FERMENTED_SPIDER_EYE)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.SLOW_FALLING)).setIsSteam(true).setInputs(
                        vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.PHANTOM_MEMBRANE), new ItemStack(Items.REDSTONE))
        );

        TEA = new Chapter("wizards_reborn.arcanemicon.chapter.tea",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.oil_tea",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, vialPotions.get(WizardsRebornAlchemyPotions.OIL_TEA))
                ),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.OIL_TEA.get(), 1000)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 500), new FluidStack(Fluids.WATER, 500))
                        .setInputs(new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get())),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.OIL_TEA)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 500))
                        .setInputs(new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()), vialPotions.get(WizardsRebornAlchemyPotions.WATER)),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_tea",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, vialPotions.get(WizardsRebornAlchemyPotions.WISSEN_TEA))
                ),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.WISSEN_TEA.get(), 1000)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()), ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.WISSEN_TEA)).setIsWissen(true).setIsSteam(true).setInputs(
                        new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()), ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, vialPotions.get(WizardsRebornAlchemyPotions.WATER)),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.milk_tea",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, vialPotions.get(WizardsRebornAlchemyPotions.MILK_TEA))
                ),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.MILK_TEA.get(), 1000)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(ForgeMod.MILK.get(), 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get())),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.MILK_TEA)).setIsWissen(true).setIsSteam(true).setInputs(
                        new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()), vialPotions.get(WizardsRebornAlchemyPotions.MILK))
        );

        JAM = new Chapter("wizards_reborn.arcanemicon.chapter.jam",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.jam",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SWEET_BERRIES_JAM_VIAL.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GLOW_BERRIES_JAM_VIAL.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MOR_JAM_VIAL.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ELDER_MOR_JAM_VIAL.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.SWEET_BERRIES_JAM_VIAL.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 250))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()), new ItemStack(Items.SWEET_BERRIES), new ItemStack(Items.SWEET_BERRIES), new ItemStack(Items.SUGAR)),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.GLOW_BERRIES_JAM_VIAL.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 250))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()), new ItemStack(Items.GLOW_BERRIES), new ItemStack(Items.GLOW_BERRIES), new ItemStack(Items.SUGAR)),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.MOR_JAM_VIAL.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 250))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()), new ItemStack(WizardsRebornItems.MOR.get()), new ItemStack(WizardsRebornItems.MOR.get()), new ItemStack(Items.SUGAR)),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.ELDER_MOR_JAM_VIAL.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 250))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()), new ItemStack(WizardsRebornItems.ELDER_MOR.get()), new ItemStack(WizardsRebornItems.ELDER_MOR.get()), new ItemStack(Items.SUGAR)),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.honey",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.HONEY_VIAL.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.HONEY_VIAL.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 250))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()), new ItemStack(Items.HONEY_BOTTLE), new ItemStack(Items.HONEY_BOTTLE)),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.chocolate",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CHOCOLATE_VIAL.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.CHOCOLATE_VIAL.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(ForgeMod.MILK.get(), 250))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()), new ItemStack(Items.COCOA_BEANS), new ItemStack(Items.COCOA_BEANS), new ItemStack(Items.SUGAR))
        );

        NETHER_SALT = new Chapter("wizards_reborn.arcanemicon.chapter.nether_salt",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.nether_salt",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, NETHER_SALT_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, NETHER_SALT_PILE_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.NETHER_SALT_BLOCK.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.NETHER_SALT_ORE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.NETHER_SALT_BLOCK.get()),
                        NETHER_SALT_ITEM, NETHER_SALT_ITEM, NETHER_SALT_ITEM,
                        NETHER_SALT_ITEM, NETHER_SALT_ITEM, NETHER_SALT_ITEM,
                        NETHER_SALT_ITEM, NETHER_SALT_ITEM, NETHER_SALT_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.NETHER_SALT.get(), 9), new ItemStack(WizardsRebornItems.NETHER_SALT_BLOCK.get())),
                new SmeltingPage(NETHER_SALT_ITEM, new ItemStack(WizardsRebornItems.NETHER_SALT_ORE.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.NETHER_SALT_PILE.get(), 3), NETHER_SALT_ITEM),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.salt_torch",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_SALT_TORCH.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_SALT_TORCH.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_SALT_TORCH.get(), 4),
                        EMPTY_ITEM, NETHER_SALT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_SALT_TORCH.get(), 4),
                        EMPTY_ITEM, NETHER_SALT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.salt_lantern",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_SALT_LANTERN.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_SALT_LANTERN.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_SALT_LANTERN.get()),
                        EMPTY_ITEM, new ItemStack(Items.CHAIN), EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsRebornItems.ARCANE_SALT_TORCH.get()), ARCANE_GOLD_NUGGET_ITEM,
                        ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_SLAB_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_SALT_LANTERN.get()),
                        EMPTY_ITEM, new ItemStack(Items.CHAIN), EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_SALT_TORCH.get()), ARCANE_GOLD_NUGGET_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.salt_campfire",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_SALT_CAMPFIRE.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISESTONE_SALT_CAMPFIRE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCANE_SALT_CAMPFIRE.get()),
                        EMPTY_ITEM, new ItemStack(Items.STICK), EMPTY_ITEM,
                        new ItemStack(Items.STICK), NETHER_SALT_ITEM, new ItemStack(Items.STICK),
                        new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()), new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.WISESTONE_SALT_CAMPFIRE.get()),
                        EMPTY_ITEM, new ItemStack(Items.STICK), EMPTY_ITEM,
                        new ItemStack(Items.STICK), NETHER_SALT_ITEM, new ItemStack(Items.STICK),
                        POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.blazing_wand",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.BLAZING_WAND.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.BLAZING_WAND.get()),
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, NETHER_SALT_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANE_WOOD_BRANCH_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM
                )
        );

        BLAZING_WAND = new Chapter("wizards_reborn.arcanemicon.chapter.blazing_wand",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.blazing_wand",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.BLAZING_WAND.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.BLAZING_WAND.get()),
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, NETHER_SALT_ITEM,
                        EMPTY_ITEM, ARCANE_WOOD_BRANCH_ITEM, ARCANE_WOOD_BRANCH_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_INGOT_ITEM
                )
        );

        PANCAKES = new Chapter("wizards_reborn.arcanemicon.chapter.pancakes",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.flour",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WHEAT_FLOUR.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_LINEN_FLOUR.get()))
                ),
                new MortarPage(new ItemStack(WizardsRebornItems.WHEAT_FLOUR.get()), new ItemStack(Items.WHEAT)),
                new MortarPage(new ItemStack(WizardsRebornItems.ARCANE_LINEN_FLOUR.get()), new ItemStack(WizardsRebornItems.ARCANE_LINEN.get())),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.pancakes",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.BLIN.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CREPE.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.pancakes_jam"),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.BLIN.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(ForgeMod.MILK.get(), 500))
                        .setInputs(new ItemStack(WizardsRebornItems.WHEAT_FLOUR.get()), new ItemStack(WizardsRebornItems.WHEAT_FLOUR.get()), new ItemStack(WizardsRebornItems.WHEAT_FLOUR.get()), new ItemStack(Items.EGG), NETHER_SALT_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.CREPE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(ForgeMod.MILK.get(), 500))
                        .setInputs(new ItemStack(WizardsRebornItems.ARCANE_LINEN_FLOUR.get()), new ItemStack(WizardsRebornItems.ARCANE_LINEN_FLOUR.get()), new ItemStack(WizardsRebornItems.ARCANE_LINEN_FLOUR.get()), new ItemStack(Items.EGG), NETHER_SALT_PILE_ITEM)
        );

        PIES = new Chapter("wizards_reborn.arcanemicon.chapter.pies",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.pies",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MOR_PIE.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ELDER_MOR_PIE.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CARROT_PIE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.MOR_PIE.get()),
                        new ItemStack(WizardsRebornItems.MOR.get()), new ItemStack(WizardsRebornItems.MOR.get()), EMPTY_ITEM,
                        new ItemStack(Items.EGG), NETHER_SALT_PILE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ELDER_MOR_PIE.get()),
                        new ItemStack(WizardsRebornItems.ELDER_MOR.get()), new ItemStack(WizardsRebornItems.ELDER_MOR.get()), EMPTY_ITEM,
                        new ItemStack(Items.EGG), NETHER_SALT_PILE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CARROT_PIE.get()),
                        new ItemStack(Items.CARROT), new ItemStack(Items.CARROT), EMPTY_ITEM,
                        new ItemStack(Items.EGG), NETHER_SALT_PILE_ITEM
                )
        );

        ALCHEMY_BREWS = new Chapter("wizards_reborn.arcanemicon.chapter.alchemy_brews",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.alchemy_brews",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MUSHROOM_BREW_BUCKET.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.HELLISH_MUSHROOM_BREW_BUCKET.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MOR_BREW_BUCKET.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.FLOWER_BREW_BUCKET.get()))
                ),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.MUSHROOM_BREW.get(), 1000)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_BROWN_MUSHROOM.get()), new ItemStack(WizardsRebornItems.GROUND_BROWN_MUSHROOM.get()), new ItemStack(WizardsRebornItems.GROUND_BROWN_MUSHROOM.get())),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.MUSHROOM_BREW.get(), 1000)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_RED_MUSHROOM.get()), new ItemStack(WizardsRebornItems.GROUND_RED_MUSHROOM.get()), new ItemStack(WizardsRebornItems.GROUND_RED_MUSHROOM.get())),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.HELLISH_MUSHROOM_BREW.get(), 1000)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_CRIMSON_FUNGUS.get()), new ItemStack(WizardsRebornItems.GROUND_CRIMSON_FUNGUS.get()), new ItemStack(WizardsRebornItems.GROUND_CRIMSON_FUNGUS.get())),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.HELLISH_MUSHROOM_BREW.get(), 1000)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_WARPED_FUNGUS.get()), new ItemStack(WizardsRebornItems.GROUND_WARPED_FUNGUS.get()), new ItemStack(WizardsRebornItems.GROUND_WARPED_FUNGUS.get())),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.MOR_BREW.get(), 1000)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_MOR.get())),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.MOR_BREW.get(), 1000)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_ELDER_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_ELDER_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_ELDER_MOR.get())),
                new AlchemyMachinePage().setFluidResult(new FluidStack(WizardsRebornFluids.FLOWER_BREW.get(), 1000)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get())),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.MUSHROOM_BREW)).setIsSteam(true).setInputs(
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_BROWN_MUSHROOM.get()), new ItemStack(WizardsRebornItems.GROUND_BROWN_MUSHROOM.get()), new ItemStack(WizardsRebornItems.GROUND_BROWN_MUSHROOM.get()), vialPotions.get(WizardsRebornAlchemyPotions.WATER)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.MUSHROOM_BREW)).setIsSteam(true).setInputs(
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_RED_MUSHROOM.get()), new ItemStack(WizardsRebornItems.GROUND_RED_MUSHROOM.get()), new ItemStack(WizardsRebornItems.GROUND_RED_MUSHROOM.get()), vialPotions.get(WizardsRebornAlchemyPotions.WATER)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.HELLISH_MUSHROOM_BREW)).setIsSteam(true).setInputs(
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_CRIMSON_FUNGUS.get()), new ItemStack(WizardsRebornItems.GROUND_CRIMSON_FUNGUS.get()), new ItemStack(WizardsRebornItems.GROUND_CRIMSON_FUNGUS.get()), vialPotions.get(WizardsRebornAlchemyPotions.WATER)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.HELLISH_MUSHROOM_BREW)).setIsSteam(true).setInputs(
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_WARPED_FUNGUS.get()), new ItemStack(WizardsRebornItems.GROUND_WARPED_FUNGUS.get()), new ItemStack(WizardsRebornItems.GROUND_WARPED_FUNGUS.get()), vialPotions.get(WizardsRebornAlchemyPotions.WATER)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.MOR_BREW)).setIsSteam(true).setInputs(
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_MOR.get()), vialPotions.get(WizardsRebornAlchemyPotions.WATER)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.MOR_BREW)).setIsSteam(true).setInputs(
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.GROUND_ELDER_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_ELDER_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_ELDER_MOR.get()), vialPotions.get(WizardsRebornAlchemyPotions.WATER)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.FLOWER_BREW)).setIsSteam(true).setInputs(
                        new ItemStack(Items.NETHER_WART), new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()), vialPotions.get(WizardsRebornAlchemyPotions.WATER)),
                new AlchemyMachinePage().setResult(new ItemStack(Items.FERMENTED_SPIDER_EYE)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 50), new FluidStack(WizardsRebornFluids.MUSHROOM_BREW.get(), 100))
                        .setInputs(new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.SUGAR)),
                new AlchemyMachinePage().setResult(new ItemStack(Items.FERMENTED_SPIDER_EYE)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 50), new FluidStack(WizardsRebornFluids.HELLISH_MUSHROOM_BREW.get(), 100))
                        .setInputs(new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.SUGAR)),
                new AlchemyMachinePage().setResult(new ItemStack(Items.FERMENTED_SPIDER_EYE)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 50), new FluidStack(WizardsRebornFluids.MOR_BREW.get(), 100))
                        .setInputs(new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.SUGAR)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.ABSORPTION)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.FLOWER_BREW.get(), 1000))
                        .setInputs(vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.RESISTANCE)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.MUSHROOM_BREW.get(), 1000))
                        .setInputs(vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.OBSIDIAN), new ItemStack(Items.OBSIDIAN), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST)),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.DARKNESS)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.MUSHROOM_BREW.get(), 2000), new FluidStack(WizardsRebornFluids.HELLISH_MUSHROOM_BREW.get(), 1000))
                        .setInputs(vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.SCULK), new ItemStack(Items.SCULK), new ItemStack(Items.SCULK), new ItemStack(Items.SCULK), new ItemStack(Items.REDSTONE))
        );

        ADVANCED_CALX = new Chapter("wizards_reborn.arcanemicon.chapter.advanced_calx",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.advanced_calx",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, NATURAL_CALX_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, SCORCHED_CALX_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, DISTANT_CALX_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, ENCHANTED_CALX_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, NATURAL_CALX_PILE_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, SCORCHED_CALX_PILE_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, DISTANT_CALX_PILE_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, ENCHANTED_CALX_PILE_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.NATURAL_CALX_BLOCK.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SCORCHED_CALX_BLOCK.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.DISTANT_CALX_BLOCK.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ENCHANTED_CALX_BLOCK.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.NATURAL_CALX.get(), 2)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 500), new FluidStack(WizardsRebornFluids.FLOWER_BREW.get(), 500))
                        .setInputs(ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, new ItemStack(Items.SLIME_BALL),
                        new ItemStack(Items.MOSS_BLOCK), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(WizardsRebornItems.PETALS.get())),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.SCORCHED_CALX.get(), 2)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 500), new FluidStack(WizardsRebornFluids.HELLISH_MUSHROOM_BREW.get(), 500))
                        .setInputs(ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, new ItemStack(Items.NETHERRACK),
                        new ItemStack(Items.SOUL_SAND), new ItemStack(Items.CRIMSON_FUNGUS), new ItemStack(Items.WARPED_FUNGUS)),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.DISTANT_CALX.get(), 2)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 500), new FluidStack(WizardsRebornFluids.MOR_BREW.get(), 500))
                        .setInputs(ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, new ItemStack(Items.END_STONE),
                        new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.CHORUS_FRUIT), new ItemStack(Items.OBSIDIAN)),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.ENCHANTED_CALX.get(), 2)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 500), new FluidStack(WizardsRebornFluids.WISSEN_TEA.get(), 500))
                        .setInputs(ALCHEMY_CALX_ITEM, ALCHEMY_CALX_ITEM, new ItemStack(Items.ENCHANTED_BOOK),
                        new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.DIAMOND)),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.NATURAL_CALX_BLOCK.get()),
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM,
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM,
                        NATURAL_CALX_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.NATURAL_CALX.get(), 9), new ItemStack(WizardsRebornItems.NATURAL_CALX_BLOCK.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.SCORCHED_CALX_BLOCK.get()),
                        SCORCHED_CALX_ITEM, SCORCHED_CALX_ITEM, SCORCHED_CALX_ITEM,
                        SCORCHED_CALX_ITEM, SCORCHED_CALX_ITEM, SCORCHED_CALX_ITEM,
                        SCORCHED_CALX_ITEM, SCORCHED_CALX_ITEM, SCORCHED_CALX_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.SCORCHED_CALX.get(), 9), new ItemStack(WizardsRebornItems.SCORCHED_CALX_BLOCK.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.DISTANT_CALX_BLOCK.get()),
                        DISTANT_CALX_ITEM, DISTANT_CALX_ITEM, DISTANT_CALX_ITEM,
                        DISTANT_CALX_ITEM, DISTANT_CALX_ITEM, DISTANT_CALX_ITEM,
                        DISTANT_CALX_ITEM, DISTANT_CALX_ITEM, DISTANT_CALX_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.DISTANT_CALX.get(), 9), new ItemStack(WizardsRebornItems.DISTANT_CALX_BLOCK.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ENCHANTED_CALX_BLOCK.get()),
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM,
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM,
                        ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM, ENCHANTED_CALX_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ENCHANTED_CALX.get(), 9), new ItemStack(WizardsRebornItems.ENCHANTED_CALX_BLOCK.get())),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.NATURAL_CALX_PILE.get(), 5), NATURAL_CALX_ITEM),
                new CraftingTablePage(NATURAL_CALX_ITEM,
                        NATURAL_CALX_PILE_ITEM, NATURAL_CALX_PILE_ITEM, NATURAL_CALX_PILE_ITEM,
                        NATURAL_CALX_PILE_ITEM, NATURAL_CALX_PILE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.SCORCHED_CALX_PILE.get(), 5), SCORCHED_CALX_ITEM),
                new CraftingTablePage(SCORCHED_CALX_ITEM,
                        SCORCHED_CALX_PILE_ITEM, SCORCHED_CALX_PILE_ITEM, SCORCHED_CALX_PILE_ITEM,
                        SCORCHED_CALX_PILE_ITEM, SCORCHED_CALX_PILE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.DISTANT_CALX_PILE.get(), 5), DISTANT_CALX_ITEM),
                new CraftingTablePage(DISTANT_CALX_ITEM,
                        DISTANT_CALX_PILE_ITEM, DISTANT_CALX_PILE_ITEM, DISTANT_CALX_PILE_ITEM,
                        DISTANT_CALX_PILE_ITEM, DISTANT_CALX_PILE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ENCHANTED_CALX_PILE.get(), 5), ENCHANTED_CALX_ITEM),
                new CraftingTablePage(ENCHANTED_CALX_ITEM,
                        ENCHANTED_CALX_PILE_ITEM, ENCHANTED_CALX_PILE_ITEM, ENCHANTED_CALX_PILE_ITEM,
                        ENCHANTED_CALX_PILE_ITEM, ENCHANTED_CALX_PILE_ITEM
                )
        );

        ALCHEMY_TRANSMUTATION = new Chapter("wizards_reborn.arcanemicon.chapter.alchemy_transmutation",
                new TitlePage("wizards_reborn.arcanemicon.page.alchemy_transmutation"),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get(), 4)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100), new FluidStack(WizardsRebornFluids.WISSEN_TEA.get(), 200))
                        .setInputs(new ItemStack(Items.RAW_GOLD), new ItemStack(Items.RAW_GOLD), new ItemStack(Items.RAW_GOLD), new ItemStack(Items.RAW_GOLD), ARCANUM_ITEM, NATURAL_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.COAL, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 50))
                        .setInputs(new ItemStack(Items.CHARCOAL), new ItemStack(Items.CHARCOAL), new ItemStack(Items.CHARCOAL), new ItemStack(Items.COBBLESTONE), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.GLOWSTONE_DUST, 12)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100), new FluidStack(WizardsRebornFluids.HELLISH_MUSHROOM_BREW.get(), 200))
                        .setInputs(new ItemStack(Items.COAL), new ItemStack(Items.COAL), SCORCHED_CALX_PILE_ITEM, new ItemStack(Items.GOLD_NUGGET)),
                new AlchemyMachinePage().setResult(new ItemStack(Items.LEATHER, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH), NATURAL_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.DEEPSLATE, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.DEEPSLATE), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.GRANITE, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.GRANITE), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.DIORITE, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.DIORITE), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.ANDESITE, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.ANDESITE), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.CALCITE, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.CALCITE), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.TUFF, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.TUFF), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.DRIPSTONE_BLOCK, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.DRIPSTONE_BLOCK), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.BLACKSTONE, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.BLACKSTONE), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.BASALT, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.BASALT), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.COBBLESTONE, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLESTONE), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.NETHERRACK, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.NETHERRACK), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.END_STONE, 5)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.END_STONE), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.COBBLESTONE, 4)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE), new ItemStack(Items.COBBLED_DEEPSLATE), NATURAL_CALX_PILE_ITEM, ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.NETHERRACK, 4)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), SCORCHED_CALX_PILE_ITEM, ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.END_STONE, 4)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), new ItemStack(Items.COBBLESTONE), DISTANT_CALX_PILE_ITEM, ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.SLIME_BALL, 4)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.PACKED_ICE), ALCHEMY_CALX_PILE_ITEM),
                new AlchemyMachinePage().setResult(new ItemStack(Items.MAGMA_CREAM, 4)).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100))
                        .setInputs(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.SLIME_BALL), new ItemStack(Items.SLIME_BALL), new ItemStack(Items.SLIME_BALL), new ItemStack(Items.BLAZE_POWDER), ALCHEMY_CALX_PILE_ITEM)
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

        List<MobEffectInstance> arcanumDustEffects = new ArrayList<>();
        List<MobEffectInstance> naturalCalxEffects = new ArrayList<>();
        List<MobEffectInstance> scorchedCalxEffects = new ArrayList<>();
        List<MobEffectInstance> distantCalxEffects = new ArrayList<>();
        List<MobEffectInstance> enchantedCalxEffects = new ArrayList<>();

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

        arcanumDustEffects.add(new MobEffectInstance(WizardsRebornMobEffects.WISSEN_AURA.get(), 3000, 0));
        naturalCalxEffects.add(new MobEffectInstance(MobEffects.SATURATION, 2, 0));
        scorchedCalxEffects.add(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 500, 0));
        distantCalxEffects.add(new MobEffectInstance(MobEffects.LEVITATION, 250, 0));
        enchantedCalxEffects.add(new MobEffectInstance(MobEffects.REGENERATION, 500, 0));

        ARCANE_CENSER = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_censer",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_censer",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_CENSER.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.smoke_warning"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_CENSER.get()),
                        ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        POLISHED_WISESTONE_ITEM, new ItemStack(Items.GLASS), POLISHED_WISESTONE_ITEM,
                        POLISHED_WISESTONE_SLAB_ITEM, POLISHED_WISESTONE_ITEM, POLISHED_WISESTONE_SLAB_ITEM,
                        ALCHEMY_CALX_ITEM, NATURAL_CALX_ITEM, new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), NATURAL_CALX_ITEM
                ),
                new CenserPage(petalsEffects, new ItemStack(WizardsRebornItems.PETALS.get())),
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

        SMOKING_PIPE = new Chapter("wizards_reborn.arcanemicon.chapter.smoking_pipe",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.smoking_pipe",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_SMOKING_PIPE.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.BAMBOO_SMOKING_PIPE.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.smoke_warning"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_SMOKING_PIPE.get()),
                        ARCANE_WOOD_PLANKS_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        NATURAL_CALX_ITEM, EMPTY_ITEM, ALCHEMY_CALX_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.BAMBOO_SMOKING_PIPE.get()),
                        new ItemStack(Items.BAMBOO_BLOCK), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(Items.BAMBOO_BLOCK), new ItemStack(Items.BAMBOO_BLOCK), new ItemStack(Items.BAMBOO_BLOCK),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        NATURAL_CALX_ITEM, EMPTY_ITEM, ALCHEMY_CALX_ITEM
                ),
                new CenserPage(noEffects, new ItemStack(WizardsRebornItems.ARCANE_LINEN_SEEDS.get())),
                new CenserPage(noEffects, new ItemStack(Items.WHEAT_SEEDS)),
                new CenserPage(noEffects, new ItemStack(Items.BEETROOT_SEEDS)),
                new CenserPage(noEffects, new ItemStack(Items.MELON_SEEDS)),
                new CenserPage(noEffects, new ItemStack(Items.PUMPKIN_SEEDS)),
                new CenserPage(noEffects, new ItemStack(Items.BAMBOO)),
                new CenserPage(noEffects, new ItemStack(Items.PAPER)),
                new CenserPage(noEffects, new ItemStack(Items.FEATHER)),
                new CenserPage(noEffects, new ItemStack(Items.PITCHER_POD)),
                new CenserPage(noEffects, new ItemStack(Items.TORCHFLOWER_SEEDS)),
                new CenserPage(arcanumDustEffects, ARCANUM_DUST_ITEM),
                new CenserPage(noEffects, ALCHEMY_CALX_ITEM),
                new CenserPage(naturalCalxEffects, NATURAL_CALX_ITEM),
                new CenserPage(scorchedCalxEffects, SCORCHED_CALX_ITEM),
                new CenserPage(distantCalxEffects, DISTANT_CALX_ITEM),
                new CenserPage(enchantedCalxEffects, ENCHANTED_CALX_ITEM)
        );

        ARCACITE = new Chapter("wizards_reborn.arcanemicon.chapter.arcacite",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcacite",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, ARCACITE_ITEM),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCACITE_BLOCK.get()))
                ),
                new AlchemyMachinePage().setResult(ARCACITE_ITEM).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 250))
                        .setInputs(ARCANUM_ITEM, ARCANUM_ITEM, new ItemStack(Items.QUARTZ),
                        new ItemStack(Items.QUARTZ), SCORCHED_CALX_ITEM),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCACITE_BLOCK.get()),
                        ARCACITE_ITEM, ARCACITE_ITEM, ARCACITE_ITEM,
                        ARCACITE_ITEM, ARCACITE_ITEM, ARCACITE_ITEM,
                        ARCACITE_ITEM, ARCACITE_ITEM, ARCACITE_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCACITE.get(), 9), new ItemStack(WizardsRebornItems.ARCACITE_BLOCK.get())),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcacite_trinkets",
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCACITE_AMULET.get())),
                        new BlockEntry(ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCACITE_RING.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCACITE_AMULET.get()),
                        ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM, ARCANE_GOLD_INGOT_ITEM,
                        ARCACITE_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCACITE_RING.get()),
                        ARCACITE_ITEM, ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM,
                        ARCANE_GOLD_INGOT_ITEM, EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM,
                        EMPTY_ITEM, ARCANE_GOLD_NUGGET_ITEM, EMPTY_ITEM
                ),
                new AlchemyMachinePage().setResult(vialPotions.get(WizardsRebornAlchemyPotions.MAGICAL_ATTUNEMENT)).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.MOR_BREW.get(), 1000))
                        .setInputs(vialPotions.get(WizardsRebornAlchemyPotions.WISSEN_TEA), ARCANUM_ITEM, ARCANUM_ITEM, ARCACITE_ITEM, new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE))
        );

        ARCACITE_POLISHING_MIXTURE = new Chapter("wizards_reborn.arcanemicon.chapter.arcacite_polishing_mixture",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcacite_polishing_mixture",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE_BLOCK.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get())).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 2000))
                        .setInputs(ARCACITE_ITEM, ARCACITE_ITEM,
                        ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ARCANUM_DUST_ITEM, ALCHEMY_CALX_ITEM),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE_BLOCK.get()),
                        new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get()),
                        new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get()),
                        new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get()), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get(), 9), new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE_BLOCK.get()))
        );

        SOUL_HUNTER_TRIM = new Chapter("wizards_reborn.arcanemicon.chapter.soul_hunter_trim",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.soul_hunter_trim",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SOUL_HUNTER_TRIM.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.SOUL_HUNTER_TRIM.get())).setIsWissen(true).setIsSteam(true)
                        .setInputs(new ItemStack(WizardsRebornItems.WISESTONE_TRIM.get()),
                        new ItemStack(Items.PURPLE_WOOL), new ItemStack(Items.PURPLE_WOOL), new ItemStack(Items.BLACK_WOOL), new ItemStack(Items.BLACK_WOOL))
        );

        IMPLOSION_TRIM = new Chapter("wizards_reborn.arcanemicon.chapter.implosion_trim",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.implosion_trim",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.IMPLOSION_TRIM.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.IMPLOSION_TRIM.get())).setIsWissen(true).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.LAVA, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.WISESTONE_TRIM.get()),
                        new ItemStack(Items.BLUE_ICE), new ItemStack(Items.BLUE_ICE), new ItemStack(Items.BLUE_ICE))
        );

        Supplier<LivingEntity> sniffer = () -> new Sniffer(EntityType.SNIFFER, WizardsReborn.proxy.getLevel());
        Supplier<LivingEntity> sniffalo = () -> new SniffaloEntity(WizardsRebornEntities.SNIFFALO.get(), WizardsReborn.proxy.getLevel());

        SNIFFALO = new Chapter("wizards_reborn.arcanemicon.chapter.sniffalo",
                new TitledEntityPage("wizards_reborn.arcanemicon.page.sniffer", sniffer),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.sniffer_items",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(Items.TORCHFLOWER_SEEDS)),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(Items.PITCHER_POD)),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SHINY_CLOVER_SEED.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.OLD_ROOTS.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.sniffalo_egg",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SNIFFALO_EGG.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.SNIFFALO_EGG.get())).setIsSteam(true).setIsWissen(true)
                        .setFluidInputs(new FluidStack(WizardsRebornFluids.MOR_BREW.get(), 3000), new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 5000), new FluidStack(ForgeMod.MILK.get(), 1500))
                        .setInputs(new ItemStack(Items.SNIFFER_EGG), ARCACITE_ITEM, ARCACITE_ITEM, NETHER_SALT_ITEM, NATURAL_CALX_ITEM, NATURAL_CALX_ITEM),
                new TitledEntityPage("wizards_reborn.arcanemicon.page.sniffalo", sniffalo),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.sniffalo_items",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SHINY_CLOVER_SEED.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE_VINE.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_SEED.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.OLD_ROOTS.get()))
                )
        );

        TORCHFLOWER = new Chapter("wizards_reborn.arcanemicon.chapter.torchflower",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.torchflower",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(Items.TORCHFLOWER_SEEDS)),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(Items.TORCHFLOWER))
                )
        );

        PITCHER = new Chapter("wizards_reborn.arcanemicon.chapter.pitcher",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.pitcher",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(Items.PITCHER_POD)),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(Items.PITCHER_PLANT))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.pitcher_dew",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.PITCHER_DEW.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.PITCHER_DEW_JAM_VIAL.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 250))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()), new ItemStack(WizardsRebornItems.PITCHER_DEW.get()), new ItemStack(WizardsRebornItems.PITCHER_DEW.get()), new ItemStack(Items.SUGAR)),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.pitcher_turnip",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.PITCHER_TURNIP.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.PITCHER_TURNIP_BLOCK.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsRebornItems.PITCHER_TURNIP_BLOCK.get()), new ItemStack(WizardsRebornItems.PITCHER_TURNIP.get())),
                new TextPage("wizards_reborn.arcanemicon.page.pitcher_turnip.tale.0"),
                new TextPage("wizards_reborn.arcanemicon.page.pitcher_turnip.tale.1"),
                new TextPage("wizards_reborn.arcanemicon.page.pitcher_turnip.tale.2"),
                new TextPage("wizards_reborn.arcanemicon.page.pitcher_turnip.tale.3"),
                new TextPage("wizards_reborn.arcanemicon.page.pitcher_turnip.tale.4"),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.PITCHER_TURNIP_PIE.get()),
                        new ItemStack(WizardsRebornItems.PITCHER_TURNIP.get()), new ItemStack(WizardsRebornItems.PITCHER_DEW.get()), EMPTY_ITEM,
                        NETHER_SALT_PILE_ITEM
                )
        );

        OLD_ROOTS = new Chapter("wizards_reborn.arcanemicon.chapter.old_roots",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.old_roots",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.OLD_ROOTS.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.old_flowers",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.OLD_DANDELION.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.OLD_PAEONIA.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.OLD_ROSE.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.OLD_BLUE_ROSE.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.old_implosion_flowers",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.OLD_SUNRISE_BLOSSOM.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.OLD_MOONLIGHT_BLOOM.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.missing_flower")
        );

        SHINY_CLOVER = new Chapter("wizards_reborn.arcanemicon.chapter.shiny_clover",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.shiny_clover",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SHINY_CLOVER_SEED.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SHINY_CLOVER.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.SHINY_CLOVER_JAM_VIAL.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 250))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()), new ItemStack(WizardsRebornItems.SHINY_CLOVER.get()), new ItemStack(WizardsRebornItems.SHINY_CLOVER.get()), new ItemStack(Items.SUGAR))
        );

        UNDERGROUND_GRAPE = new Chapter("wizards_reborn.arcanemicon.chapter.underground_grape",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.underground_grape",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE_VINE.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE_JAM_VIAL.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 250))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_VIAL.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(Items.SUGAR))
        );

        CORK_BAMBOO = new Chapter("wizards_reborn.arcanemicon.chapter.cork_bamboo",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.cork_bamboo",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_SEED.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.cork_bamboo_blocks",
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get())),
                        new BlockEntry(CORK_BAMBOO_PLANKS_ITEM),
                        new BlockEntry(CORK_BAMBOO_CHISELED_PLANKS_ITEM),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_STAIRS.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHISELED_STAIRS.get())),
                        new BlockEntry(CORK_BAMBOO_SLAB_ITEM),
                        new BlockEntry(CORK_BAMBOO_CHISELED_SLAB_ITEM),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_FENCE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_FENCE_GATE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_DOOR.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_TRAPDOOR.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_PRESSURE_PLATE.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_BUTTON.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_SIGN.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_HANGING_SIGN.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_RAFT.get())),
                        new BlockEntry(new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHEST_RAFT.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()),
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO.get()),
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO.get()),
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_PLANKS.get(), 4), new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get())),
                new CraftingTablePage(CORK_BAMBOO_CHISELED_PLANKS_ITEM,
                        CORK_BAMBOO_SLAB_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        CORK_BAMBOO_SLAB_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_STAIRS.get(), 4),
                        CORK_BAMBOO_PLANKS_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, EMPTY_ITEM,
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHISELED_STAIRS.get(), 4),
                        CORK_BAMBOO_CHISELED_PLANKS_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        CORK_BAMBOO_CHISELED_PLANKS_ITEM, CORK_BAMBOO_CHISELED_PLANKS_ITEM, EMPTY_ITEM,
                        CORK_BAMBOO_CHISELED_PLANKS_ITEM, CORK_BAMBOO_CHISELED_PLANKS_ITEM, CORK_BAMBOO_CHISELED_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_SLAB.get(), 6),
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHISELED_SLAB.get(), 6),
                        CORK_BAMBOO_CHISELED_PLANKS_ITEM, CORK_BAMBOO_CHISELED_PLANKS_ITEM, CORK_BAMBOO_CHISELED_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_FENCE.get(), 3),
                        CORK_BAMBOO_PLANKS_ITEM, new ItemStack(Items.STICK), CORK_BAMBOO_PLANKS_ITEM,
                        CORK_BAMBOO_PLANKS_ITEM, new ItemStack(Items.STICK), CORK_BAMBOO_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_FENCE_GATE.get()),
                        new ItemStack(Items.STICK), CORK_BAMBOO_PLANKS_ITEM, new ItemStack(Items.STICK),
                        new ItemStack(Items.STICK), CORK_BAMBOO_PLANKS_ITEM, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_DOOR.get(), 3),
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, EMPTY_ITEM,
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, EMPTY_ITEM,
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_TRAPDOOR.get(), 2),
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM,
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_PRESSURE_PLATE.get()),
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_BUTTON.get()), CORK_BAMBOO_PLANKS_ITEM),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_SIGN.get(), 3),
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM,
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM,
                        EMPTY_ITEM, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_HANGING_SIGN.get(), 6),
                        new ItemStack(Items.CHAIN), EMPTY_ITEM, new ItemStack(Items.CHAIN),
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()),
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_RAFT.get()),
                        CORK_BAMBOO_PLANKS_ITEM, EMPTY_ITEM, CORK_BAMBOO_PLANKS_ITEM,
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHEST_RAFT.get()), new ItemStack(Items.CHEST), new ItemStack(WizardsRebornItems.CORK_BAMBOO_RAFT.get())),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.gilded_cork_bamboo_planks",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GILDED_CORK_BAMBOO_PLANKS.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.GILDED_CORK_BAMBOO_CHISELED_PLANKS.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GILDED_CORK_BAMBOO_PLANKS.get()),
                        CORK_BAMBOO_PLANKS_ITEM, ARCANE_GOLD_NUGGET_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.GILDED_CORK_BAMBOO_CHISELED_PLANKS.get()),
                        CORK_BAMBOO_CHISELED_PLANKS_ITEM, ARCANE_GOLD_NUGGET_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.cork_bamboo_baulks",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_BAULK.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_PLANKS_BAULK.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS_BAULK.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_CROSS_BAULK.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_PLANKS_CROSS_BAULK.get())),
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_BAULK.get(), 6),
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), EMPTY_ITEM,
                        EMPTY_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_PLANKS_BAULK.get(), 6),
                        EMPTY_ITEM, CORK_BAMBOO_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, CORK_BAMBOO_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, CORK_BAMBOO_PLANKS_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS_BAULK.get(), 6),
                        EMPTY_ITEM, CORK_BAMBOO_CHISELED_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, CORK_BAMBOO_CHISELED_PLANKS_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, CORK_BAMBOO_CHISELED_PLANKS_ITEM, EMPTY_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_CROSS_BAULK.get()),
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO_BAULK.get()), ARCANE_LINEN_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_PLANKS_CROSS_BAULK.get()),
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO_PLANKS_BAULK.get()), ARCANE_LINEN_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK.get()),
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS_BAULK.get()), ARCANE_LINEN_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.cork_bamboo_smoking_pipe",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_SMOKING_PIPE.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_SMOKING_PIPE.get()),
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), EMPTY_ITEM, EMPTY_ITEM,
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()),
                        EMPTY_ITEM, EMPTY_ITEM, EMPTY_ITEM,
                        NATURAL_CALX_ITEM, EMPTY_ITEM, ALCHEMY_CALX_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.cork_bamboo_pedestal",
                        new BlockEntry(WISESTONE_PEDESTAL_ITEM, CORK_BAMBOO_PEDESTAL_ITEM)
                ),
                new CraftingTablePage(CORK_BAMBOO_PEDESTAL_ITEM,
                        CORK_BAMBOO_SLAB_ITEM, CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_SLAB_ITEM,
                        EMPTY_ITEM, CORK_BAMBOO_PLANKS_ITEM, EMPTY_ITEM,
                        CORK_BAMBOO_SLAB_ITEM, CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_SLAB_ITEM
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.cork_bamboo_fire",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_SALT_TORCH.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_SALT_LANTERN.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_SALT_CAMPFIRE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_SALT_TORCH.get(), 4),
                        EMPTY_ITEM, NETHER_SALT_ITEM, EMPTY_ITEM,
                        EMPTY_ITEM, CORK_BAMBOO_PLANKS_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_SALT_LANTERN.get()),
                        EMPTY_ITEM, new ItemStack(Items.CHAIN), EMPTY_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_SALT_TORCH.get()), ARCANE_GOLD_NUGGET_ITEM,
                        CORK_BAMBOO_SLAB_ITEM, CORK_BAMBOO_SLAB_ITEM, CORK_BAMBOO_SLAB_ITEM
                ),
                new CraftingTablePage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_SALT_CAMPFIRE.get()),
                        EMPTY_ITEM, new ItemStack(Items.STICK), EMPTY_ITEM,
                        new ItemStack(Items.STICK), NETHER_SALT_ITEM, new ItemStack(Items.STICK),
                        new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get()), new ItemStack(WizardsRebornItems.CORK_BAMBOO_BLOCK.get())
                )
        );

        KEG = new Chapter("wizards_reborn.arcanemicon.chapter.keg",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.keg",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARCANE_WOOD_KEG.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CORK_BAMBOO_KEG.get()))
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.ARCANE_WOOD_KEG.get()),
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, NETHER_SALT_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_WOOD_PLANKS_ITEM, ARCANE_WOOD_SLAB_ITEM, ARCANE_WOOD_PLANKS_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM,  ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM
                ),
                new ArcaneWorkbenchPage(new ItemStack(WizardsRebornItems.CORK_BAMBOO_KEG.get()),
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_SLAB_ITEM, CORK_BAMBOO_PLANKS_ITEM,
                        CORK_BAMBOO_PLANKS_ITEM, NETHER_SALT_ITEM, CORK_BAMBOO_PLANKS_ITEM,
                        CORK_BAMBOO_PLANKS_ITEM, CORK_BAMBOO_SLAB_ITEM, CORK_BAMBOO_PLANKS_ITEM,
                        ARCANE_GOLD_NUGGET_ITEM,  ARCANE_GOLD_NUGGET_ITEM, ARCANE_GOLD_INGOT_ITEM, ARCANE_GOLD_NUGGET_ITEM
                )
        );

        VODKA_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.vodka_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.vodka_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.VODKA_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.VODKA_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.VODKA_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.SUGAR), new ItemStack(Items.SUGAR),
                                new ItemStack(WizardsRebornItems.PITCHER_TURNIP.get()))
        );

        BOURBON_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.bourbon_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.bourbon_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.BOURBON_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.BOURBON_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.BOURBON_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(WizardsRebornItems.ARCANE_LINEN_SEEDS.get()), new ItemStack(WizardsRebornItems.ARCANE_LINEN_SEEDS.get()),
                                new ItemStack(WizardsRebornItems.ARCANE_LINEN_FLOUR.get()))
        );

        WHISKEY_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.whiskey_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.whiskey_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WHISKEY_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.WHISKEY_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.WHISKEY_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS),
                                new ItemStack(WizardsRebornItems.WHEAT_FLOUR.get()))
        );

        WHITE_WINE_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.white_wine_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.white_wine_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WHITE_WINE_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.WHITE_WINE_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.WHITE_WINE_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()),
                                new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(Items.DANDELION))
        );

        RED_WINE_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.red_wine_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.red_wine_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.RED_WINE_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.RED_WINE_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.RED_WINE_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()),
                                new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(Items.POPPY))
        );

        PORT_WINE_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.port_wine_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.port_wine_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.PORT_WINE_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.PORT_WINE_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.PORT_WINE_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()),
                                new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(Items.RED_MUSHROOM))
        );

        PALM_LIQUEUR_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.palm_liqueur_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.palm_liqueur_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.PALM_LIQUEUR_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.PALM_LIQUEUR_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.PALM_LIQUEUR_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.SUGAR), new ItemStack(Items.SUGAR),
                                new ItemStack(Items.SUGAR), new ItemStack(WizardsRebornItems.PITCHER_DEW.get()), new ItemStack(WizardsRebornItems.PITCHER_DEW.get()))
        );

        MEAD_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.mead_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.mead_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MEAD_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.MEAD_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.MEAD_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.SUGAR), new ItemStack(Items.HONEY_BOTTLE),
                                new ItemStack(Items.HONEY_BOTTLE))
        );

        SBITEN_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.sbiten_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.sbiten_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SBITEN_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.SBITEN_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.SBITEN_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.HONEY_BOTTLE), new ItemStack(WizardsRebornItems.CENTURIAL_HOP_CONE.get()),
                                new ItemStack(WizardsRebornItems.SHINY_CLOVER.get()), new ItemStack(WizardsRebornItems.PETALS.get()), new ItemStack(WizardsRebornItems.PETALS.get()))
        );

        SLIVOVITZ_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.slivovitz_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.slivovitz_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SLIVOVITZ_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.SLIVOVITZ_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.SLIVOVITZ_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(WizardsRebornItems.FERAL_FRUIT.get()), new ItemStack(WizardsRebornItems.PITCHER_TURNIP.get()))
        );

        SAKE_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.sake_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.sake_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SAKE_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.SAKE_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.SAKE_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.BONE_MEAL), new ItemStack(Items.BONE_MEAL),
                                new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS))
        );

        SOJU_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.soju_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.soju_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.SOJU_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.SOJU_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.SOJU_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO),
                                new ItemStack(WizardsRebornItems.ARCANE_LINEN_SEEDS.get()), new ItemStack(WizardsRebornItems.ARCANE_LINEN_SEEDS.get()))
        );

        CHICHA_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.chicha_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.chicha_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CHICHA_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.CHICHA_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.CHICHA_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.POTATO), new ItemStack(WizardsRebornItems.ARCANE_LINEN_FLOUR.get()),
                                new ItemStack(WizardsRebornItems.ARCANE_LINEN_FLOUR.get()), new ItemStack(WizardsRebornItems.ARCANE_LINEN_FLOUR.get()))
        );

        CHACHA_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.chacha_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.chacha_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.CHACHA_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.CHACHA_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.CHACHA_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()),
                                new ItemStack(WizardsRebornItems.PITCHER_TURNIP.get()))
        );

        APPLEJACK_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.applejack_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.applejack_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.APPLEJACK_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.APPLEJACK_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.APPLEJACK_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.APPLE), new ItemStack(Items.APPLE),
                                new ItemStack(Items.APPLE), new ItemStack(Items.APPLE))
        );

        RAKIA_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.rakia_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.rakia_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.RAKIA_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.RAKIA_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.RAKIA_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.APPLE), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()),
                                new ItemStack(Items.SWEET_BERRIES), new ItemStack(Items.GLOW_BERRIES))
        );

        KIRSCH_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.kirsch_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.kirsch_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.KIRSCH_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.KIRSCH_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.KIRSCH_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.GLOW_BERRIES), new ItemStack(Items.GLOW_BERRIES),
                                new ItemStack(WizardsRebornItems.PITCHER_TURNIP.get()))
        );

        BOROVICHKA_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.borovichka_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.borovichka_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.BOROVICHKA_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.BOROVICHKA_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.BOROVICHKA_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS),
                                new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.GLOW_BERRIES), new ItemStack(Items.GLOW_BERRIES))
        );

        PALINKA_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.palinka_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.palinka_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.PALINKA_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.PALINKA_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.PALINKA_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(WizardsRebornItems.FERAL_FRUIT.get()), new ItemStack(Items.APPLE),
                                new ItemStack(Items.APPLE), new ItemStack(Items.APPLE))
        );

        TEQUILA_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.tequila_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.tequila_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.TEQUILA_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.TEQUILA_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.TEQUILA_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.CACTUS), new ItemStack(Items.CACTUS),
                                new ItemStack(WizardsRebornItems.PITCHER_DEW.get()))
        );

        PULQUE_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.pulque_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.pulque_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.PULQUE_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.PULQUE_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.PULQUE_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.CACTUS), new ItemStack(Items.CACTUS),
                                new ItemStack(Items.CACTUS))
        );

        ARKHI_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.arkhi_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arkhi_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ARKHI_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.ARKHI_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.ARKHI_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(ForgeMod.MILK.get(), 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS),
                                new ItemStack(Items.WHEAT_SEEDS), new ItemStack(WizardsRebornItems.PITCHER_TURNIP.get()))
        );

        TEJ_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.tej_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.tej_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.TEJ_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.TEJ_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.TEJ_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.HONEY_BOTTLE), new ItemStack(WizardsRebornItems.CENTURIAL_HOP_CONE.get()),
                                new ItemStack(WizardsRebornItems.CENTURIAL_HOP_CONE.get()))
        );

        WISSEN_BEER_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.wissen_beer_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_beer_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.WISSEN_BEER_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.WISSEN_BEER_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.WISSEN_BEER_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(WizardsRebornItems.CENTURIAL_HOP_CONE.get()), new ItemStack(WizardsRebornItems.CENTURIAL_HOP_CONE.get()),
                                new ItemStack(WizardsRebornItems.CENTURIAL_HOP_CONE.get()), ARCANUM_ITEM, ARCANUM_ITEM)
        );

        MOR_TINCTURE_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.mor_tincture_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.mor_tincture_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.MOR_TINCTURE_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.MOR_TINCTURE_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.MOR_TINCTURE_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.SUGAR), new ItemStack(WizardsRebornItems.MOR.get()),
                                new ItemStack(WizardsRebornItems.MOR.get()), new ItemStack(WizardsRebornItems.GROUND_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_MOR.get())),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.MOR_TINCTURE_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.SUGAR), new ItemStack(WizardsRebornItems.ELDER_MOR.get()),
                                new ItemStack(WizardsRebornItems.ELDER_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_ELDER_MOR.get()), new ItemStack(WizardsRebornItems.GROUND_ELDER_MOR.get()))
        );

        INNOCENT_WINE_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.innocent_wine_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.innocent_wine_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.INNOCENT_WINE_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.INNOCENT_WINE_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.INNOCENT_WINE_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()),
                                new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), new ItemStack(WizardsRebornItems.PETALS_OF_INNOCENCE.get()))
        );

        TARKHUNA_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.tarkhuna_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.tarkhuna_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.TARKHUNA_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.TARKHUNA_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.TARKHUNA_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.SUGAR), new ItemStack(WizardsRebornItems.SHINY_CLOVER.get()),
                                new ItemStack(WizardsRebornItems.SHINY_CLOVER.get()), new ItemStack(WizardsRebornItems.SHINY_CLOVER.get()))
        );

        BAIKAL_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.baikal_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.baikal_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.BAIKAL_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.BAIKAL_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.BAIKAL_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.SUGAR), new ItemStack(Items.SUGAR),
                                new ItemStack(Items.FERN), new ItemStack(Items.SPRUCE_LEAVES), new ItemStack(WizardsRebornItems.CENTURIAL_HOP_SEED.get()))
        );

        KVASS_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.kvass_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.kvass_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.KVASS_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.KVASS_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.KVASS_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.SUGAR), new ItemStack(Items.BREAD),
                                new ItemStack(Items.BREAD), new ItemStack(Items.BREAD))
        );

        KISSEL_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.kissel_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.kissel_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.KISSEL_BOTTLE.get())),
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, DrinkBottleItem.getItemStage(WizardsRebornItems.KISSEL_BOTTLE.get(), 3))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.KISSEL_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(Items.SUGAR), new ItemStack(Items.POTATO),
                                new ItemStack(Items.SWEET_BERRIES), new ItemStack(Items.SWEET_BERRIES), new ItemStack(Items.SWEET_BERRIES))
        );

        ROTTEN_DRINK_BOTTLE = new Chapter("wizards_reborn.arcanemicon.chapter.rotten_drink_bottle",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.rotten_drink_bottle",
                        new BlockEntry(CORK_BAMBOO_PEDESTAL_ITEM, new ItemStack(WizardsRebornItems.ROTTEN_DRINK_BOTTLE.get()))
                ),
                new AlchemyMachinePage().setResult(new ItemStack(WizardsRebornItems.ROTTEN_DRINK_BOTTLE.get())).setIsSteam(true)
                        .setFluidInputs(new FluidStack(Fluids.WATER, 1000))
                        .setInputs(new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get()), new ItemStack(WizardsRebornItems.ROTTEN_FERAL_FRUIT.get()))
        );

        BREWING = new Chapter("wizards_reborn.arcanemicon.chapter.brewing",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.brewing"),
                new IndexPage(
                        new IndexEntry(KEG, new ItemStack(WizardsRebornItems.ARCANE_WOOD_KEG.get())),
                        new IndexEntry(VODKA_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.VODKA_BOTTLE.get(), 3)),
                        new IndexEntry(BOURBON_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.BOURBON_BOTTLE.get(), 3)),
                        new IndexEntry(WHISKEY_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.WHISKEY_BOTTLE.get(), 3)),
                        new IndexEntry(WHITE_WINE_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.WHITE_WINE_BOTTLE.get(), 3)),
                        new IndexEntry(RED_WINE_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.RED_WINE_BOTTLE.get(), 3)),
                        new IndexEntry(PORT_WINE_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.PORT_WINE_BOTTLE.get(), 3))
                ),
                new IndexPage(
                        new IndexEntry(PALM_LIQUEUR_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.PALM_LIQUEUR_BOTTLE.get(), 3)),
                        new IndexEntry(MEAD_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.MEAD_BOTTLE.get(), 3)),
                        new IndexEntry(SBITEN_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.SBITEN_BOTTLE.get(), 3)),
                        new IndexEntry(SLIVOVITZ_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.SLIVOVITZ_BOTTLE.get(), 3)),
                        new IndexEntry(SAKE_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.SAKE_BOTTLE.get(), 3)),
                        new IndexEntry(SOJU_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.SOJU_BOTTLE.get(), 3)),
                        new IndexEntry(CHICHA_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.CHICHA_BOTTLE.get(), 3))
                ),
                new IndexPage(
                        new IndexEntry(CHACHA_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.CHACHA_BOTTLE.get(), 3)),
                        new IndexEntry(APPLEJACK_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.APPLEJACK_BOTTLE.get(), 3)),
                        new IndexEntry(RAKIA_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.RAKIA_BOTTLE.get(), 3)),
                        new IndexEntry(KIRSCH_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.KIRSCH_BOTTLE.get(), 3)),
                        new IndexEntry(BOROVICHKA_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.BOROVICHKA_BOTTLE.get(), 3)),
                        new IndexEntry(PALINKA_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.PALINKA_BOTTLE.get(), 3)),
                        new IndexEntry(TEQUILA_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.TEQUILA_BOTTLE.get(), 3))
                ),
                new IndexPage(
                        new IndexEntry(PULQUE_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.PULQUE_BOTTLE.get(), 3)),
                        new IndexEntry(ARKHI_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.ARKHI_BOTTLE.get(), 3)),
                        new IndexEntry(TEJ_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.TEJ_BOTTLE.get(), 3)),
                        new IndexEntry(WISSEN_BEER_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.WISSEN_BEER_BOTTLE.get(), 3)),
                        new IndexEntry(MOR_TINCTURE_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.MOR_TINCTURE_BOTTLE.get(), 3)),
                        new IndexEntry(INNOCENT_WINE_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.INNOCENT_WINE_BOTTLE.get(), 3)),
                        new IndexEntry(TARKHUNA_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.TARKHUNA_BOTTLE.get(), 3))
                ),
                new IndexPage(
                        new IndexEntry(BAIKAL_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.BAIKAL_BOTTLE.get(), 3)),
                        new IndexEntry(KVASS_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.KVASS_BOTTLE.get(), 3)),
                        new IndexEntry(KISSEL_BOTTLE, DrinkBottleItem.getItemStage(WizardsRebornItems.KISSEL_BOTTLE.get(), 3)),
                        new IndexEntry(ROTTEN_DRINK_BOTTLE, new ItemStack(WizardsRebornItems.ROTTEN_DRINK_BOTTLE.get()))
                )
        );

        ALCHEMY_INDEX = new Chapter("wizards_reborn.arcanemicon.chapter.alchemy_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.alchemy_index",
                        new IndexEntry(MOR, new ItemStack(WizardsRebornItems.MOR.get())),
                        new IndexEntry(MORTAR, new ItemStack(WizardsRebornItems.ARCANE_WOOD_MORTAR.get()), WizardsRebornKnowledges.ARCANE_WOOD),
                        new IndexEntry(ARCANE_LINEN, ARCANE_LINEN_ITEM, WizardsRebornKnowledges.WISSEN_CRYSTALLIZER),
                        new IndexEntry(MUSHROOM_CAPS, new ItemStack(WizardsRebornItems.BROWN_MUSHROOM_CAP.get()), WizardsRebornKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(WISESTONE, new ItemStack(WizardsRebornItems.WISESTONE.get()), WizardsRebornKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(WISESTONE_PEDESTAL, WISESTONE_PEDESTAL_ITEM, WizardsRebornKnowledges.WISESTONE)
                ),
                new IndexPage(
                        new IndexEntry(FLUID_PIPES, new ItemStack(WizardsRebornItems.FLUID_PIPE.get()), WizardsRebornKnowledges.WISESTONE),
                        new IndexEntry(STEAM_PIPES, new ItemStack(WizardsRebornItems.STEAM_PIPE.get()), WizardsRebornKnowledges.WISESTONE),
                        new IndexEntry(ORBITAL_FLUID_RETAINER, new ItemStack(WizardsRebornItems.ORBITAL_FLUID_RETAINER.get()), WizardsRebornKnowledges.WISESTONE),
                        new IndexEntry(ALCHEMY_FURNACE, new ItemStack(WizardsRebornItems.ALCHEMY_FURNACE.get()), WizardsRebornKnowledges.ORBITAL_FLUID_RETAINER),
                        new IndexEntry(STEAM_THERMAL_STORAGE, new ItemStack(WizardsRebornItems.STEAM_THERMAL_STORAGE.get()), WizardsRebornKnowledges.ALCHEMY_FURNACE),
                        new IndexEntry(ALCHEMY_MACHINE, new ItemStack(WizardsRebornItems.ALCHEMY_MACHINE.get()), WizardsRebornKnowledges.ALCHEMY_FURNACE),
                        new IndexEntry(ALCHEMY_OIL, new ItemStack(WizardsRebornItems.ALCHEMY_OIL_BUCKET.get()), WizardsRebornKnowledges.ALCHEMY_MACHINE)
                ),
                new IndexPage(
                        new IndexEntry(MUSIC_DISC_ARCANUM, new ItemStack(WizardsRebornItems.MUSIC_DISC_ARCANUM.get()), WizardsRebornKnowledges.ALCHEMY_OIL),
                        new IndexEntry(MUSIC_DISC_MOR, new ItemStack(WizardsRebornItems.MUSIC_DISC_MOR.get()), WizardsRebornKnowledges.ALCHEMY_OIL),
                        new IndexEntry(ALCHEMY_CALX, ALCHEMY_CALX_ITEM, WizardsRebornKnowledges.ALCHEMY_OIL),
                        new IndexEntry(ALCHEMY_GLASS, ACLHEMY_GLASS, WizardsRebornKnowledges.ALCHEMY_CALX),
                        new IndexEntry(ALCHEMY_BAG, new ItemStack(WizardsRebornItems.ALCHEMY_BAG.get()), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(ALCHEMY_POTIONS, vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(TEA, vialPotions.get(WizardsRebornAlchemyPotions.WISSEN_TEA), WizardsRebornKnowledges.ALCHEMY_GLASS)
                ),
                new IndexPage(
                        new IndexEntry(JAM, new ItemStack(WizardsRebornItems.SWEET_BERRIES_JAM_VIAL.get()), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(NETHER_SALT, NETHER_SALT_ITEM, WizardsRebornKnowledges.ALCHEMY_OIL),
                        new IndexEntry(BLAZING_WAND, new ItemStack(WizardsRebornItems.BLAZING_WAND.get()), WizardsRebornKnowledges.NETHER_SALT),
                        new IndexEntry(PANCAKES, new ItemStack(WizardsRebornItems.BLIN.get()), WizardsRebornKnowledges.NETHER_SALT),
                        new IndexEntry(PIES, new ItemStack(WizardsRebornItems.MOR_PIE.get()), WizardsRebornKnowledges.NETHER_SALT),
                        new IndexEntry(ALCHEMY_BREWS, new ItemStack(WizardsRebornItems.MOR_BREW_BUCKET.get()), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(ADVANCED_CALX, NATURAL_CALX_ITEM, WizardsRebornKnowledges.ALCHEMY_GLASS)
                ),
                new IndexPage(
                        new IndexEntry(ALCHEMY_TRANSMUTATION, new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get()), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(ARCANE_CENSER, new ItemStack(WizardsRebornItems.ARCANE_CENSER.get()), WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(SMOKING_PIPE, new ItemStack(WizardsRebornItems.ARCANE_WOOD_SMOKING_PIPE.get()), WizardsRebornKnowledges.ARCANE_CENSER),
                        new IndexEntry(ARCACITE, ARCACITE_ITEM, WizardsRebornKnowledges.ALCHEMY_GLASS),
                        new IndexEntry(ARCACITE_POLISHING_MIXTURE, new ItemStack(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get()), WizardsRebornKnowledges.ARCACITE),
                        new IndexEntry(SOUL_HUNTER_TRIM, new ItemStack(WizardsRebornItems.SOUL_HUNTER_TRIM.get()), WizardsRebornKnowledges.JEWELER_TABLE),
                        new IndexEntry(IMPLOSION_TRIM, new ItemStack(WizardsRebornItems.IMPLOSION_TRIM.get()), WizardsRebornKnowledges.JEWELER_TABLE)
                ),
                new IndexPage(
                        new IndexEntry(SNIFFALO, new ItemStack(WizardsRebornItems.SNIFFALO_EGG.get()), WizardsRebornKnowledges.JEWELER_TABLE),
                        new IndexEntry(TORCHFLOWER, new ItemStack(Items.TORCHFLOWER), WizardsRebornKnowledges.JEWELER_TABLE),
                        new IndexEntry(PITCHER, new ItemStack(WizardsRebornItems.PITCHER_TURNIP.get()), WizardsRebornKnowledges.JEWELER_TABLE),
                        new IndexEntry(OLD_ROOTS, new ItemStack(WizardsRebornItems.OLD_ROOTS.get()), WizardsRebornKnowledges.JEWELER_TABLE),
                        new IndexEntry(SHINY_CLOVER, new ItemStack(WizardsRebornItems.SHINY_CLOVER.get()), WizardsRebornKnowledges.JEWELER_TABLE),
                        new IndexEntry(UNDERGROUND_GRAPE, new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE.get()), WizardsRebornKnowledges.JEWELER_TABLE),
                        new IndexEntry(CORK_BAMBOO, new ItemStack(WizardsRebornItems.CORK_BAMBOO.get()), WizardsRebornKnowledges.JEWELER_TABLE)
                ),
                new IndexPage(
                        new IndexEntry(BREWING, new ItemStack(WizardsRebornItems.ARCANE_WOOD_KEG.get()), WizardsRebornKnowledges.JEWELER_TABLE)
                )
        );
    }

    public static void additionalInit() {
        PROGRESSION = new Chapter("wizards_reborn.arcanemicon.chapter.progression",
                new ProgressionPage("wizards_reborn.arcanemicon.page.progression", WizardsRebornKnowledges.progression),
                new ProgressionPage("wizards_reborn.arcanemicon.page.additional_progression", WizardsRebornKnowledges.additionalProgression)
        );

        STATISTIC = new Chapter("wizards_reborn.arcanemicon.chapter.statistics",
                new StatisticsPage("wizards_reborn.arcanemicon.page.statistics", WizardsRebornKnowledges.progression, WizardsRebornKnowledges.additionalProgression)
        );

        ANIMATIONS_CONFIGS = new Chapter("wizards_reborn.arcanemicon.chapter.animations_configs",
                new TitlePage("wizards_reborn.arcanemicon.page.animations_configs"),
                new ConfigPage(
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.SPELLS_ITEM_ANIMATIONS)
                )
        );

        PARTICLES_CONFIGS = new Chapter("wizards_reborn.arcanemicon.chapter.particles_configs",
                new TitlePage("wizards_reborn.arcanemicon.page.particles_configs"),
                new ConfigPage(
                        new ConfigIndexEntry().setIntegerConfig(WizardsRebornClientConfig.WISSEN_RAYS_LIMIT),
                        new ConfigIndexEntry(FluffyFur.MOD_ID).setBooleanConfig(FluffyFurClientConfig.ITEM_PARTICLE).setSpecConfig(FluffyFurClientConfig.SPEC),
                        new ConfigIndexEntry(FluffyFur.MOD_ID).setBooleanConfig(FluffyFurClientConfig.ITEM_GUI_PARTICLE).setSpecConfig(FluffyFurClientConfig.SPEC),
                        new ConfigIndexEntry(FluffyFur.MOD_ID).setBooleanConfig(FluffyFurClientConfig.BLOOD_PARTICLE).setSpecConfig(FluffyFurClientConfig.SPEC)
                )
        );

        GRAPHICS_CONFIGS = new Chapter("wizards_reborn.arcanemicon.chapter.graphics_configs",
                new TitlePage("wizards_reborn.arcanemicon.page.graphics_configs"),
                new IndexPage(
                        new IndexEntry(ANIMATIONS_CONFIGS, new ItemStack(WizardsRebornItems.ARCANE_GOLD_SCYTHE.get())),
                        new IndexEntry(PARTICLES_CONFIGS, new ItemStack(WizardsRebornItems.ARCANUM_DUST.get()))
                )
        );

        NUMERICAL_CONFIGS = new Chapter("wizards_reborn.arcanemicon.chapter.numerical_configs",
                new TitlePage("wizards_reborn.arcanemicon.page.numerical_configs"),
                new ConfigPage(
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.NUMERICAL_WISSEN),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.NUMERICAL_COOLDOWN),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.SHOW_LIGHT_NAME),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.NUMERICAL_EXPERIENCE),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.NUMERICAL_HEAT),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.NUMERICAL_FLUID)
                ),
                new ConfigPage(
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.NUMERICAL_STEAM)
                )
        );

        ARCANEMICON_CONFIGS = new Chapter("wizards_reborn.arcanemicon.chapter.arcanemicon_configs",
                new TitlePage("wizards_reborn.arcanemicon.page.arcanemicon_configs"),
                new ConfigPage(
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.RESEARCH_HARDMODE),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.OLD_RESEARCH_MONOGRAM_OUTLINE),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.BRIGHT_RESEARCH_MONOGRAM_OUTLINE),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.RESEARCH_MONOGRAM_CONNECTS),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.MONOGRAM_GLOW),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.MONOGRAM_GLOW_COLOR)
                ),
                new ConfigPage(
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.MONOGRAM_COLOR),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.MONOGRAM_RAYS),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.CONFIG_CENTER)
                )
        );

        ARCANE_WAND_OVERLAY_CONFIGS = new Chapter("wizards_reborn.arcanemicon.chapter.arcane_wand_overlay_configs",
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand_overlay_configs"),
                new ConfigPage(
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_UP),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_RIGHT),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SIDE_HUD),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SIDE_BAR),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_HORIZONTAL_BAR),
                        new ConfigIndexEntry().setIntegerConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_X_OFFSET)
                ),
                new ConfigPage(
                        new ConfigIndexEntry().setIntegerConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_Y_OFFSET),
                        new ConfigIndexEntry().setIntegerConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SECOND_X_OFFSET),
                        new ConfigIndexEntry().setIntegerConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SECOND_Y_OFFSET),
                        new ConfigIndexEntry().setIntegerConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_BAR_X_OFFSET),
                        new ConfigIndexEntry().setIntegerConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_BAR_Y_OFFSET),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SECOND_HUD_FREE)
                ),
                new ConfigPage(
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_BAR_FREE),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_COOLDOWN_TEXT),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_WISSEN_TEXT),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_REVERSE_BAR),
                        new ConfigIndexEntry().setBooleanConfig(WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SHOW_EMPTY)
                )
        );

        OVERLAY_CONFIGS = new Chapter("wizards_reborn.arcanemicon.chapter.overlay_configs",
                new TitlePage("wizards_reborn.arcanemicon.page.overlay_configs"),
                new IndexPage(
                        new IndexEntry(ARCANE_WAND_OVERLAY_CONFIGS, new ItemStack(WizardsRebornItems.ARCANE_WAND.get()), WizardsRebornKnowledges.ARCANE_WAND)
                )
        );

        CONFIGS = new Chapter("wizards_reborn.arcanemicon.chapter.configs",
                new TitlePage("wizards_reborn.arcanemicon.page.configs"),
                new IndexPage(
                        new IndexEntry(GRAPHICS_CONFIGS, new ItemStack(WizardsRebornItems.LIME_ARCANE_LUMOS.get())),
                        new IndexEntry(NUMERICAL_CONFIGS, new ItemStack(WizardsRebornItems.WISSEN_WAND.get()), WizardsRebornKnowledges.ARCANUM_DUST),
                        new IndexEntry(ARCANEMICON_CONFIGS, new ItemStack(WizardsRebornItems.ARCANEMICON.get())),
                        new IndexEntry(OVERLAY_CONFIGS, new ItemStack(WizardsRebornItems.ARCANE_WAND.get()))
                )
        );

        SPECIAL_THANKS = new Chapter("wizards_reborn.arcanemicon.chapter.special_thanks",
                new ThanksPage("wizards_reborn.arcanemicon.page.special_thanks.maxbogomol.0"),
                new ThanksPlushPage("wizards_reborn.arcanemicon.page.special_thanks.maxbogomol.1", new ItemStack(FluffyFurItems.MAXBOGOMOL_PLUSH.get())),
                new TextPage("wizards_reborn.arcanemicon.page.special_thanks.onixthecat.0"),
                new ThanksPlushPage("wizards_reborn.arcanemicon.page.special_thanks.onixthecat.1", new ItemStack(FluffyFurItems.ONIXTHECAT_PLUSH.get())),
                new BunnyThanksPage("wizards_reborn.arcanemicon.page.special_thanks.unlogicalsamsar"),
                new BunnyThanksPlushPage("wizards_reborn.arcanemicon.page.special_thanks.unlogicalsamsar", new ItemStack(FluffyFurItems.UNOLOGICALSAMSAR_PLUSH.get())),
                new TextPage("wizards_reborn.arcanemicon.page.special_thanks.foxairplane.0"),
                new ThanksPlushPage("wizards_reborn.arcanemicon.page.special_thanks.foxairplane.1", new ItemStack(FluffyFurItems.FOXAIRPLANE_PLUSH.get())),
                new TextPage("wizards_reborn.arcanemicon.page.special_thanks.onjerlay.0"),
                new ThanksPlushPage("wizards_reborn.arcanemicon.page.special_thanks.onjerlay.1", new ItemStack(FluffyFurItems.ONJERLAY_PLUSH.get())),
                new ThanksVillagePage("wizards_reborn.arcanemicon.page.special_thanks.fluffy_village")
        );

        ADDITIONAL_INDEX = new Chapter("wizards_reborn.arcanemicon.chapter.additional_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.additional_index",
                        new IndexEntry(PROGRESSION, new ItemStack(WizardsRebornItems.CREATIVE_KNOWLEDGE_SCROLL.get())),
                        new IndexEntry(STATISTIC, new ItemStack(WizardsRebornItems.KNOWLEDGE_SCROLL.get())),
                        new IndexEntry(CONFIGS, new ItemStack(WizardsRebornItems.WISSEN_WAND.get())),
                        new IndexEntry(SPECIAL_THANKS, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SAPLING.get()))
                ),
                new EditionPage()
        );
    }

    public static void init() {
        itemsInit();
        arcaneNatureInit();
        spellsInit();
        crystalRitualsInit();
        alchemyInit();
        additionalInit();
        integrationsInit();

        ARCANE_NATURE = new Category(
                "arcane_nature",
                ARCANUM_ITEM,
                ARCANE_NATURE_INDEX
        );

        ItemStack wandItem = new ItemStack(WizardsRebornItems.ARCANE_WAND.get());
        SimpleContainer wandItemInv = ArcaneWandItem.getInventory(wandItem);
        CompoundTag nbt = wandItem.getOrCreateTag();
        wandItemInv.setItem(0, new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()));
        nbt.putBoolean("crystal", true);
        wandItem.setTag(nbt);

        SPELLS = new Category(
                "spells",
                wandItem,
                SPELLS_INDEX
        );

        CRYSTAL_RITUALS = new Category(
                "crystal_rituals",
                new ItemStack(WizardsRebornItems.FACETED_EARTH_CRYSTAL.get()),
                CRYSTAL_RITUALS_INDEX
        );

        ALCHEMY = new Category(
                "alchemy",
                new ItemStack(WizardsRebornItems.ELDER_MOR.get()),
                ALCHEMY_INDEX
        );

        ADDITIONAL = new Category(
                "additional",
                new ItemStack(WizardsRebornItems.WISSEN_WAND.get()),
                ADDITIONAL_INDEX
        );

        categories.add(ARCANE_NATURE);
        categories.add(SPELLS);
        categories.add(CRYSTAL_RITUALS);
        categories.add(ALCHEMY);
        additionalCategories.add(ADDITIONAL);
    }

    public static void integrationsInit() {
        if (WizardsRebornCreate.isLoaded()) WizardsRebornCreate.ClientLoadedOnly.arcanemiconChaptersInit();
        if (WizardsRebornFarmersDelight.isLoaded()) WizardsRebornFarmersDelight.ClientLoadedOnly.arcanemiconChaptersInit();
        if (WizardsRebornMalum.isLoaded()) WizardsRebornMalum.ClientLoadedOnly.arcanemiconChaptersInit();
        if (WizardsRebornEmbers.isLoaded()) WizardsRebornEmbers.ClientLoadedOnly.arcanemiconChaptersInit();
    }
}
