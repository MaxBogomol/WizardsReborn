package mod.maxbogomol.wizards_reborn.integration.common.farmers_delight;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemClassSkinEntry;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.BlockEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.CenserPage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.CraftingTablePage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.titled.TitledBlockPage;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.common.item.ArcaneKnifeItem;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.common.item.ArcaneWoodKnifeItem;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.common.item.InnocentWoodKnifeItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItemTiers;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class WizardsRebornFarmersDelight {
    public static boolean LOADED;

    public static class ItemsLoadedOnly {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WizardsReborn.MOD_ID);

        public static RegistryObject<Item> ARCANE_GOLD_KNIFE;
        public static RegistryObject<Item> ARCANE_WOOD_KNIFE;
        public static RegistryObject<Item> INNOCENT_WOOD_KNIFE;

        public static RegistryObject<Item> MOR_COLONY;
        public static RegistryObject<Item> ELDER_MOR_COLONY;
    }

    public static class BlocksLoadedOnly {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WizardsReborn.MOD_ID);

        public static RegistryObject<Block> MOR_COLONY;
        public static RegistryObject<Block> ELDER_MOR_COLONY;
    }

    public static class LoadedOnly {
        public static void makeItems() {
            ItemsLoadedOnly.ARCANE_GOLD_KNIFE = ItemsLoadedOnly.ITEMS.register("arcane_gold_knife", () -> new ArcaneKnifeItem(WizardsRebornItemTiers.ARCANE_GOLD, 0.5F, -2.0F, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_COLD));
            ItemsLoadedOnly.ARCANE_WOOD_KNIFE = ItemsLoadedOnly.ITEMS.register("arcane_wood_knife", () -> new ArcaneWoodKnifeItem(WizardsRebornItemTiers.ARCANE_WOOD, 0.5F, -2.0F, new Item.Properties(), WizardsRebornItems.ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_WOOD));
            ItemsLoadedOnly.INNOCENT_WOOD_KNIFE = ItemsLoadedOnly.ITEMS.register("innocent_wood_knife", () -> new InnocentWoodKnifeItem(WizardsRebornItemTiers.INNOCENT_WOOD, 0.5F, -2.0F, new Item.Properties(), WizardsRebornItems.INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INNOCENT_WOOD));
            ItemsLoadedOnly.MOR_COLONY = ItemsLoadedOnly.ITEMS.register("mor_colony", () -> new MushroomColonyItem(BlocksLoadedOnly.MOR_COLONY.get(), new Item.Properties()));
            ItemsLoadedOnly.ELDER_MOR_COLONY = ItemsLoadedOnly.ITEMS.register("elder_mor_colony", () -> new MushroomColonyItem(BlocksLoadedOnly.ELDER_MOR_COLONY.get(), new Item.Properties()));
        }

        public static void makeBlocks() {
            BlocksLoadedOnly.MOR_COLONY = BlocksLoadedOnly.BLOCKS.register("mor_colony", () -> new MushroomColonyBlock(BlockBehaviour.Properties.copy(WizardsRebornBlocks.MOR.get()), WizardsRebornItems.MOR));
            BlocksLoadedOnly.ELDER_MOR_COLONY = BlocksLoadedOnly.BLOCKS.register("elder_mor_colony", () -> new MushroomColonyBlock(BlockBehaviour.Properties.copy(WizardsRebornBlocks.ELDER_MOR.get()), WizardsRebornItems.ELDER_MOR));
        }

        public static void addKnifeSkin(ItemSkin skin, String item) {
            skin.addSkinEntry(new ItemClassSkinEntry(ArcaneKnifeItem.class, item));
        }
    }

    public static class ClientLoadedOnly {
        public static void arcanemiconChaptersInit() {
            ArcanemiconChapters.ARCANE_GOLD.addPage(new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold_knife",
                    new BlockEntry(ArcanemiconChapters.ARCANE_PEDESTAL_ITEM, new ItemStack(WizardsRebornFarmersDelight.ItemsLoadedOnly.ARCANE_GOLD_KNIFE.get()))
            ));
            ArcanemiconChapters.ARCANE_GOLD.addPage(new CraftingTablePage(new ItemStack(WizardsRebornFarmersDelight.ItemsLoadedOnly.ARCANE_GOLD_KNIFE.get()),
                    ArcanemiconChapters.EMPTY_ITEM, ArcanemiconChapters.ARCANE_GOLD_INGOT_ITEM, ArcanemiconChapters.EMPTY_ITEM,
                    ArcanemiconChapters.EMPTY_ITEM, ArcanemiconChapters.ARCANE_WOOD_BRANCH_ITEM
            ));

            List<MobEffectInstance> noEffects = new ArrayList<>();

            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(ModItems.CABBAGE_SEEDS.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(ModItems.TOMATO_SEEDS.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(ModItems.RICE.get())));
        }
    }

    public static class JeiLoadedOnly {
        public static void addKnifeJEIInfo(IRecipeRegistration registration) {
            registration.addIngredientInfo(new ItemStack(ItemsLoadedOnly.ARCANE_GOLD_KNIFE.get()), VanillaTypes.ITEM_STACK, TextUtils.getTranslation("jei.info.knife"));
            registration.addIngredientInfo(new ItemStack(ItemsLoadedOnly.ARCANE_WOOD_KNIFE.get()), VanillaTypes.ITEM_STACK, TextUtils.getTranslation("jei.info.knife"));
            registration.addIngredientInfo(new ItemStack(ItemsLoadedOnly.INNOCENT_WOOD_KNIFE.get()), VanillaTypes.ITEM_STACK, TextUtils.getTranslation("jei.info.knife"));
        }
    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded("farmersdelight");

        if (isLoaded()) {
            LoadedOnly.makeItems();
            LoadedOnly.makeBlocks();
            ItemsLoadedOnly.ITEMS.register(eventBus);
            BlocksLoadedOnly.BLOCKS.register(eventBus);
        }
    }

    public static boolean isLoaded() {
        return LOADED;
    }
}
