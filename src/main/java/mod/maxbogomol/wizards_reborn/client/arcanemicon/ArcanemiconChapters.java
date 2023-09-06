package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
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
            ARCANUM, ARCANUM_DUST_TRANSMUTATION, ARCANE_WOOD, ARCANE_GOLD, WISSEN, WISSEN_TRANSLATOR, ARCANE_PEDESTAL, WISSEN_ALTAR, WISSEN_CRYSTALLIZER, ARCANE_WORKBENCH, ARCANE_LUMOS,
            ALL_SPELLS, EARTH_SPELLS, WATER_SPELLS, AIR_SPELLS, FIRE_SPELLS, VOID_SPELLS,
            EARTH_PROJECTILE, WATER_PROJECTILE, AIR_PROJECTILE, FIRE_PROJECTILE, VOID_PROJECTILE,
            MONOGRAMS, TEST1_MONOGRAM, TEST2_MONOGRAM, TEST3_MONOGRAM, TEST4_MONOGRAM, TEST5_MONOGRAM, TEST6_MONOGRAM, TEST7_MONOGRAM, TEST8_MONOGRAM, TEST9_MONOGRAM, TEST10_MONOGRAM, TEST11_MONOGRAM, TEST12_MONOGRAM, TEST13_MONOGRAM, TEST14_MONOGRAM, TEST15_MONOGRAM, TEST16_MONOGRAM, TEST17_MONOGRAM, TEST18_MONOGRAM, TEST19_MONOGRAM, TEST20_MONOGRAM;

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
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANUM_DUST.get(), 3), new ItemStack(WizardsReborn.ARCANUM.get()), new ItemStack(Items.REDSTONE))
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
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_WOOD_BOAT_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), ItemStack.EMPTY, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()),
                        new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())
                ),
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
                new TitledBlockPage("wizards_reborn.arcanemicon.page.wissen_altar",
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM.get())),
                        new BlockEntry(),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get())),
                        new BlockEntry(),
                        new BlockEntry(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get()))
                ),
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
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_lumos",
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

        ARCANE_NATURE_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_nature_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.arcane_nature_index",
                        new IndexEntry(ARCANUM, new ItemStack(WizardsReborn.ARCANUM.get())),
                        new IndexEntry(ARCANUM_DUST_TRANSMUTATION, new ItemStack(WizardsReborn.ARCANUM_DUST.get()), RegisterKnowledges.ARCANUM_DUST_KNOWLEDGE),
                        new IndexEntry(ARCANE_WOOD, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get()), RegisterKnowledges.ARCANUM_DUST_KNOWLEDGE),
                        new IndexEntry(ARCANE_GOLD, new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), RegisterKnowledges.ARCANUM_DUST_KNOWLEDGE),
                        new IndexEntry(WISSEN, new ItemStack(WizardsReborn.WISSEN_WAND.get()), RegisterKnowledges.ARCANUM_DUST_KNOWLEDGE),
                        new IndexEntry(WISSEN_TRANSLATOR, new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()), RegisterKnowledges.ARCANE_GOLD_KNOWLEDGE)
                ),
                new IndexPage(
                        new IndexEntry(ARCANE_PEDESTAL, new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), RegisterKnowledges.ARCANE_WOOD_KNOWLEDGE),
                        new IndexEntry(WISSEN_ALTAR, new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), RegisterKnowledges.ARCANE_GOLD_KNOWLEDGE),
                        new IndexEntry(WISSEN_CRYSTALLIZER, new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()), RegisterKnowledges.ARCANE_GOLD_KNOWLEDGE),
                        new IndexEntry(ARCANE_WORKBENCH, new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()), RegisterKnowledges.ARCANE_GOLD_KNOWLEDGE),
                        new IndexEntry(ARCANE_LUMOS, new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM.get()), RegisterKnowledges.WISSEN_CRYSTALLIZER)
                )
        );

        EARTH_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.earth_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.earth_projectile", WizardsReborn.EARTH_PROJECTILE_SPELL)
        );

        WATER_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.water_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.water_projectile", WizardsReborn.WATER_PROJECTILE_SPELL)
        );

        AIR_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.air_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.air_projectile", WizardsReborn.AIR_PROJECTILE_SPELL)
        );

        FIRE_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fire_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.fire_projectile", WizardsReborn.FIRE_PROJECTILE_SPELL)
        );

        VOID_PROJECTILE = new Chapter(
                "wizards_reborn.arcanemicon.chapter.void_projectile",
                new TitledSpellPage("wizards_reborn.arcanemicon.page.void_projectile", WizardsReborn.VOID_PROJECTILE_SPELL)
        );

        TEST1_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test1_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test1_monogram", WizardsReborn.TEST1_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST1_MONOGRAM)
        );
        TEST2_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test2_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test2_monogram", WizardsReborn.TEST2_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST2_MONOGRAM)
        );
        TEST3_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test3_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test3_monogram", WizardsReborn.TEST3_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST3_MONOGRAM)
        );
        TEST4_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test4_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test4_monogram", WizardsReborn.TEST4_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST4_MONOGRAM)
        );
        TEST5_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test5_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test5_monogram", WizardsReborn.TEST5_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST5_MONOGRAM)
        );
        TEST6_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test6_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test6_monogram", WizardsReborn.TEST6_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST6_MONOGRAM)
        );
        TEST7_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test7_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test7_monogram", WizardsReborn.TEST7_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST7_MONOGRAM)
        );
        TEST8_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test8_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test8_monogram", WizardsReborn.TEST8_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST8_MONOGRAM)
        );
        TEST9_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test9_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test9_monogram", WizardsReborn.TEST9_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST9_MONOGRAM)
        );
        TEST10_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test10_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test10_monogram", WizardsReborn.TEST10_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST10_MONOGRAM)
        );
        TEST11_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test11_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test11_monogram", WizardsReborn.TEST11_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST11_MONOGRAM)
        );
        TEST12_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test12_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test12_monogram", WizardsReborn.TEST12_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST12_MONOGRAM)
        );
        TEST13_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test13_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test13_monogram", WizardsReborn.TEST13_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST13_MONOGRAM)
        );
        TEST14_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test14_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test14_monogram", WizardsReborn.TEST14_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST14_MONOGRAM)
        );
        TEST15_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test15_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test15_monogram", WizardsReborn.TEST15_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST15_MONOGRAM)
        );
        TEST16_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test16_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test16_monogram", WizardsReborn.TEST16_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST16_MONOGRAM)
        );
        TEST17_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test17_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test17_monogram", WizardsReborn.TEST17_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST17_MONOGRAM)
        );
        TEST18_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test18_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test18_monogram", WizardsReborn.TEST18_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST18_MONOGRAM)
        );
        TEST19_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test19_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test19_monogram", WizardsReborn.TEST19_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST19_MONOGRAM)
        );
        TEST20_MONOGRAM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.test20_monogram",
                new TitledMonogramPage("wizards_reborn.arcanemicon.page.test20_monogram", WizardsReborn.TEST20_MONOGRAM),
                new MonogramRecipesPage(WizardsReborn.TEST20_MONOGRAM)
        );

        ALL_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.all_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.all_spells",
                        new SpellIndexEntry(EARTH_PROJECTILE, WizardsReborn.EARTH_PROJECTILE_SPELL),
                        new SpellIndexEntry(WATER_PROJECTILE, WizardsReborn.WATER_PROJECTILE_SPELL),
                        new SpellIndexEntry(AIR_PROJECTILE, WizardsReborn.AIR_PROJECTILE_SPELL),
                        new SpellIndexEntry(FIRE_PROJECTILE, WizardsReborn.FIRE_PROJECTILE_SPELL),
                        new SpellIndexEntry(VOID_PROJECTILE, WizardsReborn.VOID_PROJECTILE_SPELL)
                )
        );

        EARTH_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.earth_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.earth_spells",
                        new SpellIndexEntry(EARTH_PROJECTILE, WizardsReborn.EARTH_PROJECTILE_SPELL)
                )
        );

        WATER_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.water_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.water_spells",
                        new SpellIndexEntry(WATER_PROJECTILE, WizardsReborn.WATER_PROJECTILE_SPELL)
                )
        );

        AIR_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.air_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.air_spells",
                        new SpellIndexEntry(AIR_PROJECTILE, WizardsReborn.AIR_PROJECTILE_SPELL)
                )
        );

        FIRE_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.fire_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.fire_spells",
                        new SpellIndexEntry(FIRE_PROJECTILE, WizardsReborn.FIRE_PROJECTILE_SPELL)
                )
        );

        VOID_SPELLS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.void_spells",
                new TitledSpellIndexPage("wizards_reborn.arcanemicon.page.void_spells",
                        new SpellIndexEntry(VOID_PROJECTILE, WizardsReborn.VOID_PROJECTILE_SPELL)
                )
        );

        MONOGRAMS = new Chapter(
                "wizards_reborn.arcanemicon.chapter.monograms",
                new TitledMonogramIndexPage("wizards_reborn.arcanemicon.page.monograms",
                        new MonogramIndexEntry(TEST1_MONOGRAM, WizardsReborn.TEST1_MONOGRAM),
                        new MonogramIndexEntry(TEST2_MONOGRAM, WizardsReborn.TEST2_MONOGRAM),
                        new MonogramIndexEntry(TEST3_MONOGRAM, WizardsReborn.TEST3_MONOGRAM),
                        new MonogramIndexEntry(TEST4_MONOGRAM, WizardsReborn.TEST4_MONOGRAM),
                        new MonogramIndexEntry(TEST5_MONOGRAM, WizardsReborn.TEST5_MONOGRAM),
                        new MonogramIndexEntry(TEST6_MONOGRAM, WizardsReborn.TEST6_MONOGRAM)
                ),
                new MonogramIndexPage(
                        new MonogramIndexEntry(TEST7_MONOGRAM, WizardsReborn.TEST7_MONOGRAM),
                        new MonogramIndexEntry(TEST8_MONOGRAM, WizardsReborn.TEST8_MONOGRAM),
                        new MonogramIndexEntry(TEST9_MONOGRAM, WizardsReborn.TEST9_MONOGRAM),
                        new MonogramIndexEntry(TEST10_MONOGRAM, WizardsReborn.TEST10_MONOGRAM),
                        new MonogramIndexEntry(TEST11_MONOGRAM, WizardsReborn.TEST11_MONOGRAM),
                        new MonogramIndexEntry(TEST12_MONOGRAM, WizardsReborn.TEST12_MONOGRAM)
                ),
                new MonogramIndexPage(
                        new MonogramIndexEntry(TEST13_MONOGRAM, WizardsReborn.TEST13_MONOGRAM),
                        new MonogramIndexEntry(TEST14_MONOGRAM, WizardsReborn.TEST14_MONOGRAM),
                        new MonogramIndexEntry(TEST15_MONOGRAM, WizardsReborn.TEST15_MONOGRAM),
                        new MonogramIndexEntry(TEST16_MONOGRAM, WizardsReborn.TEST16_MONOGRAM),
                        new MonogramIndexEntry(TEST17_MONOGRAM, WizardsReborn.TEST17_MONOGRAM),
                        new MonogramIndexEntry(TEST18_MONOGRAM, WizardsReborn.TEST18_MONOGRAM)
                ),
                new MonogramIndexPage(
                        new MonogramIndexEntry(TEST19_MONOGRAM, WizardsReborn.TEST19_MONOGRAM),
                        new MonogramIndexEntry(TEST20_MONOGRAM, WizardsReborn.TEST20_MONOGRAM)
                )
        );

        SPELLS_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.spells_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.spells_index",
                        new IndexEntry(ALL_SPELLS, new ItemStack(WizardsReborn.ARCANE_WAND.get())),
                        new IndexEntry(EARTH_SPELLS, new ItemStack(WizardsReborn.FACETED_EARTH_CRYSTAL.get())),
                        new IndexEntry(WATER_SPELLS, new ItemStack(WizardsReborn.FACETED_WATER_CRYSTAL.get())),
                        new IndexEntry(AIR_SPELLS, new ItemStack(WizardsReborn.FACETED_AIR_CRYSTAL.get())),
                        new IndexEntry(FIRE_SPELLS, new ItemStack(WizardsReborn.FACETED_FIRE_CRYSTAL.get())),
                        new IndexEntry(VOID_SPELLS, new ItemStack(WizardsReborn.FACETED_VOID_CRYSTAL.get()))
                ),
                new IndexPage(
                        new IndexEntry(MONOGRAMS, new ItemStack(WizardsReborn.ARCANEMICON.get()))
                )
        );

        CRYSTALS_RITUALS_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.crystals_rituals_index"
        );

        ALCHEMY_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.alchemy_index"
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
