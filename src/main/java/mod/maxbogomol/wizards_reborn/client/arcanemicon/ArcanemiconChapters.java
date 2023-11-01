package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.BlockEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.IndexEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.MonogramIndexEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.SpellIndexEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.page.*;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.*;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.titledpage.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.knowledge.RegisterKnowledges;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class ArcanemiconChapters {
    public static List<Category> categories = new ArrayList<>();
    public static Category ARCANE_NATURE, SPELLS, CRYSTALS_RITUALS, ALCHEMY;
    public static Chapter ARCANE_NATURE_INDEX, SPELLS_INDEX, CRYSTALS_RITUALS_INDEX, ALCHEMY_INDEX,
            ARCANUM, ARCANUM_DUST_TRANSMUTATION, ARCANE_WOOD, ARCANE_GOLD, SCYTHES, TRINKETS, WISSEN, WISSEN_TRANSLATOR, ARCANE_PEDESTAL, WISSEN_ALTAR, WISSEN_CRYSTALLIZER, ARCANE_WORKBENCH, ARCANE_LUMOS, CRYSTALS, ARCANE_WAND, WISSEN_CELL,
            ALL_SPELLS, EARTH_SPELLS, WATER_SPELLS, AIR_SPELLS, FIRE_SPELLS, VOID_SPELLS,
            EARTH_PROJECTILE, WATER_PROJECTILE, AIR_PROJECTILE, FIRE_PROJECTILE, VOID_PROJECTILE, FROST_PROJECTILE, HOLY_PROJECTILE,
            EARTH_RAY, WATER_RAY, AIR_RAY, FIRE_RAY, VOID_RAY, FROST_RAY, HOLY_RAY,
            MONOGRAMS, RESEARCHES, RESEARCH, LUNAM_MONOGRAM, VITA_MONOGRAM, SOLEM_MONOGRAM, MORS_MONOGRAM, MIRACULUM_MONOGRAM, TEMPUS_MONOGRAM, STATERA_MONOGRAM, ECLIPSIS_MONOGRAM, SICCITAS_MONOGRAM, SOLSTITIUM_MONOGRAM, FAMES_MONOGRAM, RENAISSANCE_MONOGRAM, BELLUM_MONOGRAM, LUX_MONOGRAM, KARA_MONOGRAM, DEGRADATIO_MONOGRAM, PRAEDICTIONEM_MONOGRAM, EVOLUTIONIS_MONOGRAM, DARK_MONOGRAM, UNIVERSUM_MONOGRAM,
            MOR, ARCANE_LINEN;
    public static ResearchPage RESEARCH_MAIN, RESEARCH_LIST;

    public static void init() {
        ARCANUM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcanum",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_BLOCK_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_ORE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.DEEPSLATE_ARCANUM_ORE_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_BLOCK_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM.get(), 9), new ItemStack(WizardsReborn.ARCANUM_BLOCK_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_DUST.get(), 2), new ItemStack(WizardsReborn.ARCANUM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_DUST.get(), 3), new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(Items.REDSTONE)),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANEMICON.get()), new ItemStack(Items.BOOK), new ItemStack(WizardsReborn.ARCANUM.get()))
        );

        ARCANUM_DUST_TRANSMUTATION = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcanum_dust_transmutation",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum_dust_transmutation",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get())))
        );

        ARCANE_WOOD = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_wood",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood_sapling",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_SAPLING_ITEM.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.ARCANE_WOOD_SAPLING_ITEM.get()), new ItemStack(Items.OAK_SAPLING)),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wood",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_STAIRS_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get())),
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
                        new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()), ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_ITEM.get()),
                        new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM.get()), ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get(), 4), new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_STAIRS_ITEM.get(), 4),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), ItemStack.EMPTY, ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get(), 6),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_FENCE_ITEM.get(), 3),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(Items.STICK), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(Items.STICK), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_FENCE_GATE_ITEM.get()),
                        new ItemStack(Items.STICK), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(Items.STICK),
                        new ItemStack(Items.STICK), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_DOOR_ITEM.get(), 3),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), ItemStack.EMPTY
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_TRAPDOOR_ITEM.get(), 2),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_PRESSURE_PLATE_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_BUTTON_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_SIGN_ITEM.get(), 3),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()),
                        ItemStack.EMPTY, new ItemStack(Items.STICK)
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_HANGING_SIGN_ITEM.get(), 6),
                        new ItemStack(Items.CHAIN), ItemStack.EMPTY, new ItemStack(Items.CHAIN),
                        new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get()),
                        new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get()), new ItemStack(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_BOAT_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_CHEST_BOAT_ITEM.get()), new ItemStack(Items.CHEST), new ItemStack(WizardsReborn.ARCANE_WOOD_BOAT_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()), ItemStack.EMPTY, ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get())
                )
        );

        ARCANE_GOLD = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_gold",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_BLOCK_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_ORE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.DEEPSLATE_ARCANE_GOLD_ORE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.NETHER_ARCANE_GOLD_ORE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD_BLOCK_ITEM.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.ARCANE_GOLD_ORE_ITEM.get()), new ItemStack(Items.GOLD_ORE)),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.DEEPSLATE_ARCANE_GOLD_ORE_ITEM.get()), new ItemStack(Items.DEEPSLATE_GOLD_ORE)),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.NETHER_ARCANE_GOLD_ORE_ITEM.get()), new ItemStack(Items.NETHER_GOLD_ORE)),
                new SmeltingPage(new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get())),
                new SmeltingPage(new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_ORE_ITEM.get())),
                new SmeltingPage(new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.DEEPSLATE_ARCANE_GOLD_ORE_ITEM.get())),
                new SmeltingPage(new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.NETHER_ARCANE_GOLD_ORE_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_BLOCK_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.RAW_ARCANE_GOLD_BLOCK_ITEM.get()),
                        new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()),
                        new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()),
                        new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get()), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get(), 9), new ItemStack(WizardsReborn.ARCANE_GOLD_BLOCK_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get(), 9), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.RAW_ARCANE_GOLD.get(), 9), new ItemStack(WizardsReborn.RAW_ARCANE_GOLD_BLOCK_ITEM.get())),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold_tools",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_SWORD.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_PICKAXE.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_AXE.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_SHOVEL.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_HOE.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_SWORD.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_PICKAXE.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_AXE.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_SHOVEL.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_HOE.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY, ItemStack.EMPTY
                )
        );

        SCYTHES = new Chapter(
                "wizards_reborn.arcanemicon.chapter.scythes",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.scythes",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()))
                )
        );

        TRINKETS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.trinkets",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.trinkets",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_AMULET.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_RING.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.LEATHER_BELT.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_AMULET.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), ItemStack.EMPTY
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_RING.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), ItemStack.EMPTY
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.LEATHER_BELT.get()),
                        ItemStack.EMPTY, new ItemStack(Items.LEATHER), ItemStack.EMPTY,
                        new ItemStack(Items.LEATHER), ItemStack.EMPTY, new ItemStack(Items.LEATHER),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), ItemStack.EMPTY
                )
        );

        WISSEN = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()))
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_wand",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_WAND.get()))
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
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), new ItemStack(WizardsReborn.ARCANUM.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), ItemStack.EMPTY, ItemStack.EMPTY
                )
        );

        WISSEN_TRANSLATOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_translator",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_translator",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get(), 2),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get())
                )
        );

        ARCANE_PEDESTAL = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_pedestal",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_pedestal",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get())
                )
        );

        WISSEN_ALTAR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_altar",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_altar.0",
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM.get())),
                        new BlockEntry(),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get())),
                        new BlockEntry(),
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.wissen_altar.1"),
                new WissenAltarPage(new ItemStack(WizardsReborn.ARCANUM.get())),
                new WissenAltarPage(new ItemStack(WizardsReborn.ARCANUM_DUST.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get())
                )
        );

        WISSEN_CRYSTALLIZER = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_crystallizer",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_crystallizer",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_SLAB_ITEM.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), ItemStack.EMPTY
                )
        );

        ARCANE_WORKBENCH = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_workbench",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_workbench",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()))
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()),
                        new ItemStack(Items.RED_WOOL), new ItemStack(Items.RED_WOOL), new ItemStack(Items.RED_WOOL),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())
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
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.GLOWSTONE_DUST)
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
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WATER_CRYSTAL_SEED.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.AIR_CRYSTAL_SEED.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.FIRE_CRYSTAL_SEED.get()))
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get()), new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.DIRT), new ItemStack(Items.STONE)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.WATER_CRYSTAL_SEED.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get()), new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.SUGAR_CANE), new ItemStack(Items.COD)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.AIR_CRYSTAL_SEED.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get()), new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.FEATHER), new ItemStack(Items.WHITE_WOOL)
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.FIRE_CRYSTAL_SEED.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get()), new ItemStack(Items.WHEAT_SEEDS),
                        new ItemStack(Items.COAL), new ItemStack(Items.BLAZE_POWDER)
                ),
                new TitledBlockPage("wizards_reborn.arcanemicon.page.crystals",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.EARTH_CRYSTAL.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WATER_CRYSTAL.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.AIR_CRYSTAL.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.FIRE_CRYSTAL.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.FRACTURED_EARTH_CRYSTAL.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.FRACTURED_WATER_CRYSTAL.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.FRACTURED_AIR_CRYSTAL.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.FRACTURED_FIRE_CRYSTAL.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()), new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get())),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.WATER_CRYSTAL.get()), new ItemStack(WizardsReborn.WATER_CRYSTAL_SEED.get())),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.AIR_CRYSTAL.get()), new ItemStack(WizardsReborn.AIR_CRYSTAL_SEED.get())),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.FIRE_CRYSTAL.get()), new ItemStack(WizardsReborn.FIRE_CRYSTAL_SEED.get())),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.FRACTURED_EARTH_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_EARTH_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_EARTH_CRYSTAL.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.WATER_CRYSTAL.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.FRACTURED_WATER_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_WATER_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_WATER_CRYSTAL.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.AIR_CRYSTAL.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.FRACTURED_AIR_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_AIR_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_AIR_CRYSTAL.get())
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.FIRE_CRYSTAL.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.FRACTURED_FIRE_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_FIRE_CRYSTAL.get()), new ItemStack(WizardsReborn.FRACTURED_FIRE_CRYSTAL.get())
                )
        );

        ARCANE_WAND = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_wand",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_wand.0",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WAND.get()))
                ),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand.focus"),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand.balance"),
                new TitlePage("wizards_reborn.arcanemicon.page.arcane_wand.absorption"),
                new TextPage("wizards_reborn.arcanemicon.page.arcane_wand.1"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.ARCANE_WAND.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_BRANCH.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()),  ItemStack.EMPTY,  ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM.get())
                )
        );

        WISSEN_CELL = new Chapter(
                "wizards_reborn.arcanemicon.chapter.wissen_cell",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_cell.0",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_CELL_ITEM.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.wissen_cell.1"),
                new ArcaneWorkbenchPage(new ItemStack(WizardsReborn.WISSEN_CELL_ITEM.get()),
                        ItemStack.EMPTY, new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), ItemStack.EMPTY,
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_BLOCK_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get())
                )
        );

        ARCANE_NATURE_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_nature_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.arcane_nature_index",
                        new IndexEntry(ARCANUM, new ItemStack(WizardsReborn.ARCANUM.get())),
                        new IndexEntry(ARCANUM_DUST_TRANSMUTATION, new ItemStack(WizardsReborn.ARCANUM_DUST.get()), RegisterKnowledges.ARCANUM_DUST_KNOWLEDGE),
                        new IndexEntry(ARCANE_WOOD, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), RegisterKnowledges.ARCANUM_DUST_KNOWLEDGE),
                        new IndexEntry(ARCANE_GOLD, new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), RegisterKnowledges.ARCANUM_DUST_KNOWLEDGE),
                        new IndexEntry(SCYTHES, new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()), RegisterKnowledges.ARCANE_GOLD_KNOWLEDGE),
                        new IndexEntry(TRINKETS, new ItemStack(WizardsReborn.ARCANUM_AMULET.get()), RegisterKnowledges.ARCANE_GOLD_KNOWLEDGE)
                ),
                new IndexPage(
                        new IndexEntry(WISSEN, new ItemStack(WizardsReborn.WISSEN_WAND.get()), RegisterKnowledges.ARCANUM_DUST_KNOWLEDGE),
                        new IndexEntry(WISSEN_TRANSLATOR, new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), RegisterKnowledges.ARCANE_GOLD_KNOWLEDGE),
                        new IndexEntry(ARCANE_PEDESTAL, new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), RegisterKnowledges.ARCANE_WOOD_KNOWLEDGE),
                        new IndexEntry(WISSEN_ALTAR, new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), RegisterKnowledges.ARCANE_GOLD_KNOWLEDGE),
                        new IndexEntry(WISSEN_CRYSTALLIZER, new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()), RegisterKnowledges.ARCANE_GOLD_KNOWLEDGE),
                        new IndexEntry(ARCANE_WORKBENCH, new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()), RegisterKnowledges.ARCANE_GOLD_KNOWLEDGE),
                        new IndexEntry(ARCANE_LUMOS, new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), RegisterKnowledges.WISSEN_CRYSTALLIZER)
                ),
                new IndexPage(
                        new IndexEntry(CRYSTALS, new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()), RegisterKnowledges.WISSEN_CRYSTALLIZER),
                        new IndexEntry(ARCANE_WAND, new ItemStack(WizardsReborn.ARCANE_WAND.get()), RegisterKnowledges.ARCANE_WORKBENCH),
                        new IndexEntry(WISSEN_CELL, new ItemStack(WizardsReborn.WISSEN_CELL_ITEM.get()), RegisterKnowledges.ARCANE_WORKBENCH)
                )
        );

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
        DARK_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.dark_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.dark_monogram", WizardsReborn.DARK_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.DARK_MONOGRAM)
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
                        new SpellIndexEntry(EARTH_RAY, WizardsReborn.EARTH_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_RAY, WizardsReborn.WATER_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_RAY, WizardsReborn.AIR_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FIRE_RAY, WizardsReborn.FIRE_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(VOID_RAY, WizardsReborn.VOID_RAY_SPELL, RegisterKnowledges.VOID_CRYSTAL),
                        new SpellIndexEntry(FROST_RAY, WizardsReborn.FROST_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                ),
                new SpellIndexPage(
                        new SpellIndexEntry(HOLY_RAY, WizardsReborn.HOLY_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                )
        );

        EARTH_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.earth_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.earth_spells",
                        new SpellIndexEntry(EARTH_PROJECTILE, WizardsReborn.EARTH_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_PROJECTILE, WizardsReborn.HOLY_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(EARTH_RAY, WizardsReborn.EARTH_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_RAY, WizardsReborn.HOLY_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                )
        );

        WATER_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.water_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.water_spells",
                        new SpellIndexEntry(WATER_PROJECTILE, WizardsReborn.WATER_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FROST_PROJECTILE, WizardsReborn.FROST_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(WATER_RAY, WizardsReborn.WATER_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FROST_RAY, WizardsReborn.FROST_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                )
        );

        AIR_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.air_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.air_spells",
                        new SpellIndexEntry(AIR_PROJECTILE, WizardsReborn.AIR_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_PROJECTILE, WizardsReborn.HOLY_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(AIR_RAY, WizardsReborn.AIR_RAY_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(HOLY_RAY, WizardsReborn.HOLY_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                )
        );

        FIRE_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fire_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.fire_spells",
                        new SpellIndexEntry(FIRE_PROJECTILE, WizardsReborn.FIRE_PROJECTILE_SPELL, RegisterKnowledges.ARCANE_WAND),
                        new SpellIndexEntry(FIRE_RAY, WizardsReborn.FIRE_RAY_SPELL, RegisterKnowledges.ARCANE_WAND)
                )
        );

        VOID_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.void_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.void_spells",
                        new SpellIndexEntry(VOID_PROJECTILE, WizardsReborn.VOID_PROJECTILE_SPELL, RegisterKnowledges.VOID_CRYSTAL),
                        new SpellIndexEntry(VOID_RAY, WizardsReborn.VOID_RAY_SPELL, RegisterKnowledges.VOID_CRYSTAL)
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
                        new MonogramIndexEntry(RENAISSANCE_MONOGRAM, WizardsReborn.RENAISSANCE_MONOGRAM)
                ),
                new MonogramIndexPage(
                        new MonogramIndexEntry(BELLUM_MONOGRAM, WizardsReborn.BELLUM_MONOGRAM),
                        new MonogramIndexEntry(LUX_MONOGRAM, WizardsReborn.LUX_MONOGRAM),
                        new MonogramIndexEntry(KARA_MONOGRAM, WizardsReborn.KARA_MONOGRAM),
                        new MonogramIndexEntry(DEGRADATIO_MONOGRAM, WizardsReborn.DEGRADATIO_MONOGRAM),
                        new MonogramIndexEntry(PRAEDICTIONEM_MONOGRAM, WizardsReborn.PRAEDICTIONEM_MONOGRAM),
                        new MonogramIndexEntry(EVOLUTIONIS_MONOGRAM, WizardsReborn.EVOLUTIONIS_MONOGRAM)
                ),
                new MonogramIndexPage(
                        new MonogramIndexEntry(DARK_MONOGRAM, WizardsReborn.DARK_MONOGRAM),
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

        CRYSTALS_RITUALS_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.crystals_rituals_index"
        );

        MOR = new Chapter(
                "wizards_reborn.arcanemicon.chapter.mor",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.mor.0",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.MOR_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.MOR_BLOCK_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ELDER_MOR_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ELDER_MOR_BLOCK_ITEM.get()))
                ),
                new TextPage("wizards_reborn.arcanemicon.page.mor.1")
        );

        ARCANE_LINEN = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_linen",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_linen",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_SEEDS.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_HAY_ITEM.get()))
                ),
                new WissenCrystallizerPage(new ItemStack(WizardsReborn.ARCANE_LINEN_SEEDS.get()),
                        new ItemStack(Items.WHEAT_SEEDS), new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(WizardsReborn.ARCANUM.get()),
                        new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get()),
                        new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT)
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_LINEN_HAY_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get())
                ),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get(), 9), new ItemStack(WizardsReborn.ARCANE_LINEN_HAY_ITEM.get()))
        );

        ALCHEMY_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.alchemy_index",
                    new IndexEntry(MOR, new ItemStack(WizardsReborn.MOR_ITEM.get())),
                    new IndexEntry(ARCANE_LINEN, new ItemStack(WizardsReborn.ARCANE_LINEN_ITEM.get()), RegisterKnowledges.WISSEN_CRYSTALLIZER)
                )
        );

        ARCANE_NATURE = new Category(
                "arcane_nature",
                new ItemStack(WizardsReborn.ARCANUM.get()),
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

        CRYSTALS_RITUALS = new Category(
                "crystals_rituals",
                new ItemStack(WizardsReborn.FACETED_EARTH_CRYSTAL.get()),
                CRYSTALS_RITUALS_INDEX
        );

        ALCHEMY = new Category(
                "alchemy",
                new ItemStack(WizardsReborn.ELDER_MOR_ITEM.get()),
                ALCHEMY_INDEX
        );

        categories.add(ARCANE_NATURE);
        categories.add(SPELLS);
        categories.add(CRYSTALS_RITUALS);
        categories.add(ALCHEMY);
    }
}
