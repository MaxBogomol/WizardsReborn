package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ArcanemiconChapters {
    public static List<Category> categories = new ArrayList<>();
    public static Category ARCANE_NATURE;
    public static Chapter ARCANE_NATURE_INDEX,
            ARCANUM, ARCANUM_DUST_TRANSMUTATION, ARCANE_WOOD, ARCANE_GOLD, WISSEN, WISSEN_TRANSLATOR, ARCANE_PEDESTAL, WISSEN_ALTAR, WISSEN_CRYSTALLIZER, ARCANE_WORKBENCH;

    public static void init() {
        ARCANUM = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcanum",
                new TitledBlockPage("wizards_reborn.arcanemicon.page.arcanum",
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_DUST.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_BLOCK_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANUM_ORE_ITEM.get()))
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
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_WOOD_BOAT_ITEM.get())),
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
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_BLOCK_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_ORE_ITEM.get())),
                        new BlockEntry(new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get()), new ItemStack(WizardsReborn.NETHER_ARCANE_GOLD_ORE_ITEM.get()))
                ),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.ARCANE_GOLD_ORE_ITEM.get()), new ItemStack(Items.GOLD_ORE)),
                new ArcanumDustTransmutationPage(new ItemStack(WizardsReborn.NETHER_ARCANE_GOLD_ORE_ITEM.get()), new ItemStack(Items.NETHER_GOLD_ORE)),
                new SmeltingPage(new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get()), new ItemStack(WizardsReborn.ARCANE_GOLD_ORE_ITEM.get())),
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
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get(), 9), new ItemStack(WizardsReborn.ARCANE_GOLD_BLOCK_ITEM.get())),
                new CraftingTablePage(new ItemStack(WizardsReborn.ARCANE_GOLD_NUGGET.get(), 9), new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get())),
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

        ARCANE_NATURE_INDEX = new Chapter(
                "wizards_reborn.arcanemicon.chapter.arcane_nature_index",
                new TitledIndexPage("wizards_reborn.arcanemicon.page.arcane_nature_index",
                        new IndexEntry(ARCANUM, new ItemStack(WizardsReborn.ARCANUM.get())),
                        new IndexEntry(ARCANUM_DUST_TRANSMUTATION, new ItemStack(WizardsReborn.ARCANUM_DUST.get())),
                        new IndexEntry(ARCANE_WOOD, new ItemStack(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM.get())),
                        new IndexEntry(ARCANE_GOLD, new ItemStack(WizardsReborn.ARCANE_GOLD_INGOT.get())),
                        new IndexEntry(WISSEN, new ItemStack(WizardsReborn.WISSEN_WAND.get())),
                        new IndexEntry(WISSEN_TRANSLATOR, new ItemStack(WizardsReborn.WISSEN_TRANSLATOR_ITEM.get()))
                ),
                new IndexPage(
                        new IndexEntry(ARCANE_PEDESTAL, new ItemStack(WizardsReborn.ARCANE_PEDESTAL_ITEM.get())),
                        new IndexEntry(WISSEN_ALTAR, new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get())),
                        new IndexEntry(WISSEN_CRYSTALLIZER, new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get())),
                        new IndexEntry(ARCANE_WORKBENCH, new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()))
                )
        );

        ARCANE_NATURE = new Category(
                "arcane_nature",
                new ItemStack(WizardsReborn.ARCANUM.get()),
                ARCANE_NATURE_INDEX
        );

        categories.add(ARCANE_NATURE);
    }
}
