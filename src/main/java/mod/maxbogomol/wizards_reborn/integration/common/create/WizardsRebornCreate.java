package mod.maxbogomol.wizards_reborn.integration.common.create;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllEnchantments;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionHandler;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.BlockEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.page.IntegrationPage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.AlchemyMachinePage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.ArcaneIteratorPage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.CenserPage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.SmeltingPage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.titled.TitledBlockPage;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import mod.maxbogomol.wizards_reborn.integration.common.create.client.arcanemicon.recipe.CrushingPage;
import mod.maxbogomol.wizards_reborn.integration.common.create.common.alchemypotion.HoneyAlchemyPotion;
import mod.maxbogomol.wizards_reborn.registry.common.fluid.WizardsRebornFluids;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WizardsRebornCreate {
    public static boolean LOADED;
    public static String MOD_ID = "create";
    public static String ID = "create";
    public static String NAME = "Create";

    public static class ItemsLoadedOnly {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WizardsReborn.MOD_ID);

        public static final RegistryObject<Item> CRUSHED_RAW_ARCANE_GOLD = ITEMS.register("crushed_raw_arcane_gold", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> ARCANE_GOLD_SHEET = ITEMS.register("arcane_gold_sheet", () -> new Item(new Item.Properties()));

        public static final RegistryObject<Item> SARCON_SHEET = ITEMS.register("sarcon_sheet", () -> new Item(new Item.Properties()));

        public static final RegistryObject<Item> VILENIUM_SHEET = ITEMS.register("vilenium_sheet", () -> new Item(new Item.Properties()));
    }

    public static class AlchemyPotionsLoadedOnly {
        public static AlchemyPotion HONEY, CHOCOLATE, BUILDERS_TEA;

        public static void init() {
            HONEY = new HoneyAlchemyPotion(WizardsReborn.MOD_ID+":honey", AllFluids.HONEY.get().getSource(), new Color(252, 208, 83), new MobEffectInstance(MobEffects.SATURATION, 1, 1));
            CHOCOLATE = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":chocolate", AllFluids.CHOCOLATE.get().getSource(), new Color(173, 81, 60), new MobEffectInstance(MobEffects.SATURATION, 1, 2));
            BUILDERS_TEA = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":builders_tea", AllFluids.TEA.get().getSource(), new Color(223, 131, 103), new MobEffectInstance(MobEffects.DIG_SPEED, 3600, 0));

            AlchemyPotionHandler.register(HONEY);
            AlchemyPotionHandler.register(CHOCOLATE);
            AlchemyPotionHandler.register(BUILDERS_TEA);
        }
    }

    public static class ClientLoadedOnly {
        public static IntegrationPage INTEGRATION_PAGE;

        public static void arcanemiconChaptersInit() {
            INTEGRATION_PAGE = new IntegrationPage(true, ID, NAME);

            ItemStack experienceNugget = new ItemStack(AllItems.EXP_NUGGET.get());
            ItemStack experienceNuggetTwo = new ItemStack(AllItems.EXP_NUGGET.get(), 2);
            ItemStack experienceNuggetBig = new ItemStack(AllItems.EXP_NUGGET.get(), 18);

            ArcanemiconChapters.ARCANUM.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.ARCANUM.addPage(new CrushingPage(ArcanemiconChapters.ARCANUM_ITEM,
                    new ItemStack(WizardsRebornItems.ARCANUM_DUST.get(), 3), ArcanemiconChapters.ARCANUM_DUST_ITEM
            ));
            ArcanemiconChapters.ARCANUM.addPage(new CrushingPage(new ItemStack(WizardsRebornItems.ARCANUM_ORE.get()),
                    new ItemStack(WizardsRebornItems.ARCANUM.get(), 3), ArcanemiconChapters.ARCANUM_ITEM, experienceNugget, new ItemStack(Items.COBBLESTONE)
            ));
            ArcanemiconChapters.ARCANUM.addPage(new CrushingPage(new ItemStack(WizardsRebornItems.DEEPSLATE_ARCANUM_ORE.get()),
                    new ItemStack(WizardsRebornItems.ARCANUM.get(), 3), ArcanemiconChapters.ARCANUM_ITEM, experienceNugget, new ItemStack(Items.COBBLED_DEEPSLATE)
            ));

            ArcanemiconChapters.ARCANE_GOLD.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.ARCANE_GOLD.addPage(new TitledBlockPage("wizards_reborn.arcanemicon.page.crushed_raw_arcane_gold",
                    new BlockEntry(ArcanemiconChapters.ARCANE_PEDESTAL_ITEM, new ItemStack(ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD.get()))
            ));
            ArcanemiconChapters.ARCANE_GOLD.addPage(new CrushingPage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_ORE.get()),
                    new ItemStack(ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD.get()), new ItemStack(ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD.get()), experienceNuggetTwo, new ItemStack(Items.COBBLED_DEEPSLATE)
            ));
            ArcanemiconChapters.ARCANE_GOLD.addPage(new CrushingPage(new ItemStack(WizardsRebornItems.DEEPSLATE_ARCANE_GOLD_ORE.get()),
                    new ItemStack(ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD.get()), new ItemStack(ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD.get()), experienceNuggetTwo, new ItemStack(Items.COBBLED_DEEPSLATE)
            ));
            ArcanemiconChapters.ARCANE_GOLD.addPage(new CrushingPage(new ItemStack(WizardsRebornItems.NETHER_ARCANE_GOLD_ORE.get()),
                    new ItemStack(WizardsRebornItems.ARCANE_GOLD_NUGGET.get(), 12), experienceNugget, new ItemStack(Items.NETHERRACK)
            ));
            ArcanemiconChapters.ARCANE_GOLD.addPage(new CrushingPage(new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD.get()),
                    new ItemStack(ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD.get()), experienceNugget
            ));
            ArcanemiconChapters.ARCANE_GOLD.addPage(new CrushingPage(new ItemStack(WizardsRebornItems.RAW_ARCANE_GOLD_BLOCK.get()),
                    new ItemStack(ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD.get(), 9), experienceNuggetBig
            ));
            ArcanemiconChapters.ARCANE_GOLD.addPage(new SmeltingPage(ArcanemiconChapters.ARCANE_GOLD_INGOT_ITEM, new ItemStack(ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD.get())));
            ArcanemiconChapters.ARCANE_GOLD.addPage(new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold_sheet",
                    new BlockEntry(ArcanemiconChapters.ARCANE_PEDESTAL_ITEM, new ItemStack(ItemsLoadedOnly.ARCANE_GOLD_SHEET.get()))
            ));

            ArcanemiconChapters.ARCANE_ITERATOR.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.ARCANE_ITERATOR.addPage(new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, AllEnchantments.POTATO_RECOVERY.get(),
                    new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                    new ItemStack(AllBlocks.COGWHEEL.asItem()),
                    new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO)
            ));
            ArcanemiconChapters.ARCANE_ITERATOR.addPage(new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, AllEnchantments.CAPACITY.get(),
                    new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                    new ItemStack(AllBlocks.FLUID_TANK.asItem()), new ItemStack(AllItems.COPPER_SHEET), new ItemStack(AllBlocks.COGWHEEL.asItem())
            ));

            ArcanemiconChapters.ARCANUM_LENS.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.ARCANUM_LENS.addPage(new CrushingPage(ArcanemiconChapters.ARCANUM_LENS_ITEM,
                    new ItemStack(WizardsRebornItems.ARCANUM_DUST.get(), 4), ArcanemiconChapters.ARCANUM_DUST_ITEM
            ));

            ArcanemiconChapters.NETHER_SALT.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.NETHER_SALT.addPage(new CrushingPage(new ItemStack(WizardsRebornItems.NETHER_SALT_ORE.get()),
                    new ItemStack(WizardsRebornItems.NETHER_SALT.get(), 2), ArcanemiconChapters.NETHER_SALT_ITEM, new ItemStack(Items.QUARTZ), experienceNugget, new ItemStack(Items.NETHERRACK)
            ));

            List<MobEffectInstance> noEffects = new ArrayList<>();
            List<MobEffectInstance> cinderFlourEffects = new ArrayList<>();
            List<MobEffectInstance> powderedObsidianEffects = new ArrayList<>();

            cinderFlourEffects.add(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1000, 0));
            cinderFlourEffects.add(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
            cinderFlourEffects.add(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));

            powderedObsidianEffects.add(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 800, 0));
            powderedObsidianEffects.add(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 500, 0));

            ArcanemiconChapters.SMOKING_PIPE.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(AllItems.WHEAT_FLOUR.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(cinderFlourEffects, new ItemStack(AllItems.CINDER_FLOUR.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(powderedObsidianEffects, new ItemStack(AllItems.POWDERED_OBSIDIAN.get())));

            ArcanemiconChapters.ALCHEMY_TRANSMUTATION.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.ALCHEMY_TRANSMUTATION.addPage(new AlchemyMachinePage(new ItemStack(ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD.get(), 4), FluidStack.EMPTY, true, true,
                    new FluidStack(WizardsRebornFluids.ALCHEMY_OIL.get(), 100), new FluidStack(WizardsRebornFluids.WISSEN_TEA.get(), 200), FluidStack.EMPTY,
                    new ItemStack(AllItems.CRUSHED_GOLD.get()), new ItemStack(AllItems.CRUSHED_GOLD.get()),
                    new ItemStack(AllItems.CRUSHED_GOLD.get()), new ItemStack(AllItems.CRUSHED_GOLD.get()),
                    ArcanemiconChapters.ARCANUM_ITEM, ArcanemiconChapters.NATURAL_CALX_ITEM
            ));
        }
    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded(MOD_ID);

        if (isLoaded()) {
            ItemsLoadedOnly.ITEMS.register(eventBus);
        }
    }

    public static void setup() {
        if (isLoaded()) {
            AlchemyPotionsLoadedOnly.init();
        }
    }

    public static boolean isLoaded() {
        return LOADED;
    }
}
