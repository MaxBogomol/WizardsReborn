package mod.maxbogomol.wizards_reborn.integration.common.farmersdelight;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemClassSkinEntry;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItemTiers;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.utility.TextUtils;

public class FarmersDelightIntegration {
    public static boolean LOADED;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WizardsReborn.MOD_ID);

    public static final RegistryObject<Item> ARCANE_GOLD_KNIFE = ITEMS.register("arcane_gold_knife", () -> isLoaded() ? LoadedOnly.makeArcaneGoldKnife() : new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_KNIFE = ITEMS.register("arcane_wood_knife", () -> isLoaded() ? LoadedOnly.makeArcaneWoodKnife() : new Item(new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_KNIFE = ITEMS.register("innocent_wood_knife", () -> isLoaded() ? LoadedOnly.makeInnocentWoodKnife() : new Item(new Item.Properties()));

    public static class LoadedOnly {
        public static Item makeArcaneGoldKnife() {
            return new ArcaneKnifeItem(WizardsRebornItemTiers.ARCANE_GOLD, 0.5F, -2.0F, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_COLD);
        }

        public static Item makeArcaneWoodKnife() {
            return new ArcaneWoodKnifeItem(WizardsRebornItemTiers.ARCANE_WOOD, 0.5F, -2.0F, new Item.Properties(), WizardsRebornItems.ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_WOOD);
        }

        public static Item makeInnocentWoodKnife() {
            return new InnocentWoodKnifeItem(WizardsRebornItemTiers.INNOCENT_WOOD, 0.5F, -2.0F, new Item.Properties(), WizardsRebornItems.INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.INNOCENT_WOOD);
        }

        public static void addKnifeSkin(ItemSkin skin, String item) {
            skin.addSkinEntry(new ItemClassSkinEntry(ArcaneKnifeItem.class, item));
        }
    }

    public static class JeiLoadedOnly {
        public static void addKnifeJEIInfo(IRecipeRegistration registration) {
            registration.addIngredientInfo(new ItemStack(ARCANE_GOLD_KNIFE.get()), VanillaTypes.ITEM_STACK, TextUtils.getTranslation("jei.info.knife"));
            registration.addIngredientInfo(new ItemStack(ARCANE_WOOD_KNIFE.get()), VanillaTypes.ITEM_STACK, TextUtils.getTranslation("jei.info.knife"));
            registration.addIngredientInfo(new ItemStack(INNOCENT_WOOD_KNIFE.get()), VanillaTypes.ITEM_STACK, TextUtils.getTranslation("jei.info.knife"));
        }
    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded("farmersdelight");

        if (isLoaded()) {
            ITEMS.register(eventBus);
        }
    }

    public static boolean isLoaded() {
        return LOADED;
    }
}
