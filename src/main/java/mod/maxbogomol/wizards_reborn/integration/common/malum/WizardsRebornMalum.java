package mod.maxbogomol.wizards_reborn.integration.common.malum;

import com.sammy.malum.common.item.impetus.CrackedImpetusItem;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.common.item.impetus.NodeItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionHandler;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.BlockEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.page.IntegrationPage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.CenserPage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.titled.TitledBlockPage;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class WizardsRebornMalum {
    public static boolean LOADED;
    public static String MOD_ID = "malum";
    public static String ID = "malum";
    public static String NAME = "Malum";

    public static class ItemsLoadedOnly {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WizardsReborn.MOD_ID);

        public static RegistryObject<Item> CRACKED_ARCANE_GOLD_IMPETUS;
        public static RegistryObject<Item> ARCANE_GOLD_IMPETUS;
        public static RegistryObject<Item> ARCANE_GOLD_NODE;

        public static RegistryObject<Item> CRACKED_SARCON_IMPETUS;
        public static RegistryObject<Item> SARCON_IMPETUS;
        public static RegistryObject<Item> SARCON_NODE;

        public static RegistryObject<Item> CRACKED_VILENIUM_IMPETUS;
        public static RegistryObject<Item> VILENIUM_IMPETUS;
        public static RegistryObject<Item> VILENIUM_NODE;
    }

    public static class AlchemyPotionsLoadedOnly {
        public static AlchemyPotion SACRED_SPIRIT, WICKED_SPIRIT, AERIAL_SPIRIT, AQUEOUS_SPIRIT, EARTHEN_SPIRIT, INFERNAL_SPIRIT,
                SACRED_SOUL, WICKED_SOUL, AERIAL_SOUL, AQUEOUS_SOUL, EARTHEN_SOUL, INFERNAL_SOUL,
                GLUTTONY;

        public static void init() {
            SACRED_SPIRIT = new AlchemyPotion(WizardsReborn.MOD_ID+":sacred_spirit", new MobEffectInstance(MobEffects.HEAL, 1, 1));
            SACRED_SOUL = new AlchemyPotion(WizardsReborn.MOD_ID+":sacred_soul", new MobEffectInstance(MobEffects.HEAL, 1, 1), new MobEffectInstance(MobEffects.REGENERATION, 1800, 1));

            WICKED_SPIRIT = new AlchemyPotion(WizardsReborn.MOD_ID+":wicked_spirit", new MobEffectInstance(MobEffects.HARM, 1, 1));
            WICKED_SOUL = new AlchemyPotion(WizardsReborn.MOD_ID+":wicked_soul", new MobEffectInstance(MobEffects.HARM, 1, 1), new MobEffectInstance(MobEffects.POISON, 1800, 1));

            AERIAL_SPIRIT = new AlchemyPotion(WizardsReborn.MOD_ID+":aerial_spirit", new MobEffectInstance(MobEffectRegistry.ZEPHYRS_COURAGE.get(), 6000, 1));
            AERIAL_SOUL = new AlchemyPotion(WizardsReborn.MOD_ID+":aerial_soul", new MobEffectInstance(MobEffectRegistry.AETHERS_CHARM.get(), 6000, 1));

            AQUEOUS_SPIRIT = new AlchemyPotion(WizardsReborn.MOD_ID+":aqueous_spirit", new MobEffectInstance(MobEffectRegistry.POSEIDONS_GRASP.get(), 6000, 1));
            AQUEOUS_SOUL = new AlchemyPotion(WizardsReborn.MOD_ID+":aqueous_soul", new MobEffectInstance(MobEffectRegistry.ANGLERS_LURE.get(), 6000, 1));

            EARTHEN_SPIRIT = new AlchemyPotion(WizardsReborn.MOD_ID+":earthen_spirit", new MobEffectInstance(MobEffectRegistry.GAIAS_BULWARK.get(), 6000, 1));
            EARTHEN_SOUL = new AlchemyPotion(WizardsReborn.MOD_ID+":earthen_soul", new MobEffectInstance(MobEffectRegistry.EARTHEN_MIGHT.get(), 6000, 1));

            INFERNAL_SPIRIT = new AlchemyPotion(WizardsReborn.MOD_ID+":infernal_spirit", new MobEffectInstance(MobEffectRegistry.MINERS_RAGE.get(), 6000, 1));
            INFERNAL_SOUL = new AlchemyPotion(WizardsReborn.MOD_ID+":infernal_soul", new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), new MobEffectInstance(MobEffects.REGENERATION, 1800, 1));

            GLUTTONY = new AlchemyPotion(WizardsReborn.MOD_ID+":gluttony", new MobEffectInstance(MobEffectRegistry.GLUTTONY.get(), 1200, 2));

            AlchemyPotionHandler.register(SACRED_SPIRIT);
            AlchemyPotionHandler.register(SACRED_SOUL);
            AlchemyPotionHandler.register(WICKED_SPIRIT);
            AlchemyPotionHandler.register(WICKED_SOUL);
            AlchemyPotionHandler.register(AERIAL_SPIRIT);
            AlchemyPotionHandler.register(AERIAL_SOUL);
            AlchemyPotionHandler.register(AQUEOUS_SPIRIT);
            AlchemyPotionHandler.register(AQUEOUS_SOUL);
            AlchemyPotionHandler.register(EARTHEN_SPIRIT);
            AlchemyPotionHandler.register(EARTHEN_SOUL);
            AlchemyPotionHandler.register(INFERNAL_SPIRIT);
            AlchemyPotionHandler.register(INFERNAL_SOUL);
            AlchemyPotionHandler.register(GLUTTONY);
        }
    }

    public static class LoadedOnly {
        public static void makeItems() {
            ItemsLoadedOnly.CRACKED_ARCANE_GOLD_IMPETUS = ItemsLoadedOnly.ITEMS.register("cracked_arcane_gold_impetus", () -> new CrackedImpetusItem(new Item.Properties().stacksTo(1)));
            ItemsLoadedOnly.ARCANE_GOLD_IMPETUS = ItemsLoadedOnly.ITEMS.register("arcane_gold_impetus", () -> new ImpetusItem(new Item.Properties().stacksTo(1)));
            ItemsLoadedOnly.ARCANE_GOLD_NODE = ItemsLoadedOnly.ITEMS.register("arcane_gold_node", () -> new NodeItem(new Item.Properties().stacksTo(1)));

            ItemsLoadedOnly.CRACKED_SARCON_IMPETUS = ItemsLoadedOnly.ITEMS.register("cracked_sarcon_impetus", () -> new CrackedImpetusItem(new Item.Properties().stacksTo(1)));
            ItemsLoadedOnly.SARCON_IMPETUS = ItemsLoadedOnly.ITEMS.register("sarcon_impetus", () -> new ImpetusItem(new Item.Properties().stacksTo(1)));
            ItemsLoadedOnly.SARCON_NODE = ItemsLoadedOnly.ITEMS.register("sarcon_node", () -> new NodeItem(new Item.Properties().stacksTo(1)));

            ItemsLoadedOnly.CRACKED_VILENIUM_IMPETUS = ItemsLoadedOnly.ITEMS.register("cracked_vilenium_impetus", () -> new CrackedImpetusItem(new Item.Properties().stacksTo(1)));
            ItemsLoadedOnly.VILENIUM_IMPETUS = ItemsLoadedOnly.ITEMS.register("vilenium_impetus", () -> new ImpetusItem(new Item.Properties().stacksTo(1)));
            ItemsLoadedOnly.VILENIUM_NODE = ItemsLoadedOnly.ITEMS.register("vilenium_node", () -> new NodeItem(new Item.Properties().stacksTo(1)));
        }
    }

    public static class ClientLoadedOnly {
        public static IntegrationPage INTEGRATION_PAGE;

        public static void arcanemiconChaptersInit() {
            INTEGRATION_PAGE = new IntegrationPage(false, ID, NAME);

            ArcanemiconChapters.ARCANE_GOLD.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.ARCANE_GOLD.addPage(new TitledBlockPage("wizards_reborn.arcanemicon.page.arcane_gold_impetus",
                    new BlockEntry(ArcanemiconChapters.ARCANE_PEDESTAL_ITEM, new ItemStack(ItemsLoadedOnly.CRACKED_ARCANE_GOLD_IMPETUS.get())),
                    new BlockEntry(ArcanemiconChapters.ARCANE_PEDESTAL_ITEM, new ItemStack(ItemsLoadedOnly.ARCANE_GOLD_IMPETUS.get())),
                    new BlockEntry(ArcanemiconChapters.ARCANE_PEDESTAL_ITEM, new ItemStack(ItemsLoadedOnly.ARCANE_GOLD_NODE.get()))
            ));

            List<MobEffectInstance> noEffects = new ArrayList<>();

            ArcanemiconChapters.SMOKING_PIPE.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(ItemRegistry.ROTTING_ESSENCE.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(ItemRegistry.HEX_ASH.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(ItemRegistry.ALCHEMICAL_CALX.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(ItemRegistry.VOID_SALTS.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(ItemRegistry.BLIGHTED_GUNK.get())));
        }
    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded(MOD_ID);

        if (isLoaded()) {
            LoadedOnly.makeItems();
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
