package mod.maxbogomol.wizards_reborn.common.integration.farmersdelight;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.common.item.equipment.CustomItemTier;
import mod.maxbogomol.wizards_reborn.common.skin.ItemClassSkinEntry;
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
            return new ArcaneKnifeItem(CustomItemTier.ARCANE_GOLD, 0.5F, -2.0F, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_COLD);
        }

        public static Item makeArcaneWoodKnife() {
            return new ArcaneWoodKnifeItem(CustomItemTier.ARCANE_WOOD, 0.5F, -2.0F, new Item.Properties(), WizardsReborn.ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_WOOD);
        }

        public static Item makeInnocentWoodKnife() {
            return new InnocentWoodKnifeItem(CustomItemTier.INNOCENT_WOOD, 0.5F, -2.0F, new Item.Properties(), WizardsReborn.INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.INNOCENT_WOOD);
        }

        public static void addKnifeSkin(Skin skin, String item) {
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
