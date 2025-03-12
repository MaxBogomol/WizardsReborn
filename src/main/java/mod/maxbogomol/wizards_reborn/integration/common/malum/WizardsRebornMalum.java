package mod.maxbogomol.wizards_reborn.integration.common.malum;

import com.sammy.malum.common.item.impetus.CrackedImpetusItem;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.common.item.impetus.NodeItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionHandler;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.BlockEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.page.IntegrationPage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.*;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.titled.TitledBlockPage;
import mod.maxbogomol.wizards_reborn.integration.common.malum.client.arcanemicon.recipe.SpiritFocusingPage;
import mod.maxbogomol.wizards_reborn.integration.common.malum.client.arcanemicon.recipe.SpiritInfusionPage;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAlchemyPotions;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WizardsRebornMalum {
    public static boolean LOADED;
    public static String MOD_ID = "malum";
    public static String ID = "malum";
    public static String NAME = "Malum";

    public static class ItemsLoadedOnly {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WizardsReborn.MOD_ID);

        public static RegistryObject<CrackedImpetusItem> CRACKED_ARCANE_GOLD_IMPETUS;
        public static RegistryObject<ImpetusItem> ARCANE_GOLD_IMPETUS;
        public static RegistryObject<NodeItem> ARCANE_GOLD_NODE;

        public static RegistryObject<CrackedImpetusItem> CRACKED_SARCON_IMPETUS;
        public static RegistryObject<ImpetusItem> SARCON_IMPETUS;
        public static RegistryObject<NodeItem> SARCON_NODE;

        public static RegistryObject<CrackedImpetusItem> CRACKED_VILENIUM_IMPETUS;
        public static RegistryObject<ImpetusItem> VILENIUM_IMPETUS;
        public static RegistryObject<NodeItem> VILENIUM_NODE;
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
            ItemsLoadedOnly.CRACKED_ARCANE_GOLD_IMPETUS = ItemsLoadedOnly.ITEMS.register("cracked_arcane_gold_impetus", () -> new CrackedImpetusItem(ItemRegistry.METALLURGIC_PROPERTIES()));
            ItemsLoadedOnly.ARCANE_GOLD_IMPETUS = ItemsLoadedOnly.ITEMS.register("arcane_gold_impetus", () -> new ImpetusItem(ItemRegistry.METALLURGIC_PROPERTIES().durability(800)).setCrackedVariant(() -> ItemsLoadedOnly.CRACKED_ARCANE_GOLD_IMPETUS.get()));
            ItemsLoadedOnly.ARCANE_GOLD_NODE = ItemsLoadedOnly.ITEMS.register("arcane_gold_node", () -> new NodeItem(ItemRegistry.METALLURGIC_NODE_PROPERTIES()));

            ItemsLoadedOnly.CRACKED_SARCON_IMPETUS = ItemsLoadedOnly.ITEMS.register("cracked_sarcon_impetus", () -> new CrackedImpetusItem(ItemRegistry.METALLURGIC_PROPERTIES()));
            ItemsLoadedOnly.SARCON_IMPETUS = ItemsLoadedOnly.ITEMS.register("sarcon_impetus", () -> new ImpetusItem(ItemRegistry.METALLURGIC_PROPERTIES().durability(800)).setCrackedVariant(() -> ItemsLoadedOnly.CRACKED_SARCON_IMPETUS.get()));
            ItemsLoadedOnly.SARCON_NODE = ItemsLoadedOnly.ITEMS.register("sarcon_node", () -> new NodeItem(ItemRegistry.METALLURGIC_NODE_PROPERTIES()));

            ItemsLoadedOnly.CRACKED_VILENIUM_IMPETUS = ItemsLoadedOnly.ITEMS.register("cracked_vilenium_impetus", () -> new CrackedImpetusItem(ItemRegistry.METALLURGIC_PROPERTIES()));
            ItemsLoadedOnly.VILENIUM_IMPETUS = ItemsLoadedOnly.ITEMS.register("vilenium_impetus", () -> new ImpetusItem(ItemRegistry.METALLURGIC_PROPERTIES().durability(800)).setCrackedVariant(() -> ItemsLoadedOnly.CRACKED_VILENIUM_IMPETUS.get()));
            ItemsLoadedOnly.VILENIUM_NODE = ItemsLoadedOnly.ITEMS.register("vilenium_node", () -> new NodeItem(ItemRegistry.METALLURGIC_NODE_PROPERTIES()));
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
            ArcanemiconChapters.ARCANE_GOLD.addPage(new SpiritInfusionPage(new ItemStack(ItemsLoadedOnly.ARCANE_GOLD_IMPETUS.get()), new ItemStack(ItemRegistry.ALCHEMICAL_IMPETUS.get()))
                    .setExtraInputs(new ItemStack(Items.GUNPOWDER, 4), new ItemStack(ItemRegistry.CTHONIC_GOLD.get()), new ItemStack(WizardsRebornItems.ARCANE_GOLD_INGOT.get(), 6))
                    .setSpirits(new ItemStack(ItemRegistry.EARTHEN_SPIRIT.get(), 8), new ItemStack(ItemRegistry.INFERNAL_SPIRIT.get(), 8))
            );
            ArcanemiconChapters.ARCANE_GOLD.addPage(new SpiritFocusingPage(new ItemStack(ItemsLoadedOnly.ARCANE_GOLD_NODE.get(), 3), new ItemStack(ItemsLoadedOnly.ARCANE_GOLD_IMPETUS.get()))
                    .setSpirits(new ItemStack(ItemRegistry.EARTHEN_SPIRIT.get(), 2), new ItemStack(ItemRegistry.INFERNAL_SPIRIT.get(), 2))
            );
            ArcanemiconChapters.ARCANE_GOLD.addPage(new SmeltingPage(new ItemStack(WizardsRebornItems.ARCANE_GOLD_NUGGET.get(), 6), new ItemStack(ItemsLoadedOnly.ARCANE_GOLD_NODE.get())));

            ArcanemiconChapters.WISSEN_ALTAR.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.WISSEN_ALTAR.addPage(new WissenAltarPage(new ItemStack(ItemRegistry.ARCANE_SPIRIT.get())));
            ArcanemiconChapters.WISSEN_ALTAR.addPage(new WissenAltarPage(new ItemStack(ItemRegistry.CHUNK_OF_BRILLIANCE.get())));
            ArcanemiconChapters.WISSEN_ALTAR.addPage(new WissenAltarPage(new ItemStack(ItemRegistry.MNEMONIC_FRAGMENT.get())));

            ArcanemiconChapters.ARCANE_ITERATOR.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.ARCANE_ITERATOR.addPage(new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 8, 0, EnchantmentRegistry.SPIRIT_PLUNDER.get(),
                    new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                    new ItemStack(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), new ItemStack(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()),
                    new ItemStack(Items.DIAMOND), new ItemStack(Items.EMERALD)
            ));
            ArcanemiconChapters.ARCANE_ITERATOR.addPage(new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, EnchantmentRegistry.HAUNTED.get(),
                    new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                    new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ), new ItemStack(Items.DIAMOND),
                    new ItemStack(ItemRegistry.PROCESSED_SOULSTONE.get()), new ItemStack(ItemRegistry.PROCESSED_SOULSTONE.get()), new ItemStack(ItemRegistry.PROCESSED_SOULSTONE.get())
            ));
            ArcanemiconChapters.ARCANE_ITERATOR.addPage(new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, EnchantmentRegistry.ANIMATED.get(),
                    new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                    new ItemStack(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE),
                    new ItemStack(Items.FEATHER), new ItemStack(Items.FEATHER)
            ));
            ArcanemiconChapters.ARCANE_ITERATOR.addPage(new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 6, 0, EnchantmentRegistry.REBOUND.get(),
                    new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                    new ItemStack(ItemRegistry.PROCESSED_SOULSTONE.get()), new ItemStack(ItemRegistry.PROCESSED_SOULSTONE.get()),
                    new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), new ItemStack(ItemRegistry.HALLOWED_GOLD_INGOT.get())
            ));
            ArcanemiconChapters.ARCANE_ITERATOR.addPage(new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 6, 0, EnchantmentRegistry.ASCENSION.get(),
                    new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                    new ItemStack(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), new ItemStack(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()),
                    new ItemStack(Items.BONE), new ItemStack(Items.BONE), new ItemStack(Items.BONE), new ItemStack(ItemRegistry.HALLOWED_GOLD_INGOT.get())
            ));
            ArcanemiconChapters.ARCANE_ITERATOR.addPage(new ArcaneIteratorPage(new ItemStack(Items.ENCHANTED_BOOK), 5, 0, EnchantmentRegistry.REPLENISHING.get(),
                    new ItemStack(Items.BOOK), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LAPIS_LAZULI),
                    new ItemStack(ItemRegistry.MNEMONIC_FRAGMENT.get()),
                    new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(ItemRegistry.HALLOWED_GOLD_INGOT.get())
            ));

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

            ArcanemiconChapters.ALCHEMY_BREWS.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.SACRED_SPIRIT), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.SACRED_SPIRIT.get()), new ItemStack(ItemRegistry.SACRED_SPIRIT.get())
            ));
            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.SACRED_SOUL), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.SACRED_SPIRIT.get()), new ItemStack(ItemRegistry.SACRED_SPIRIT.get()), new ItemStack(ItemRegistry.ELDRITCH_SPIRIT.get())
            ));

            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.WICKED_SPIRIT), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.WICKED_SPIRIT.get()), new ItemStack(ItemRegistry.WICKED_SPIRIT.get())
            ));
            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.WICKED_SOUL), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.WICKED_SPIRIT.get()), new ItemStack(ItemRegistry.WICKED_SPIRIT.get()), new ItemStack(ItemRegistry.ELDRITCH_SPIRIT.get())
            ));

            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.AERIAL_SPIRIT), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.AERIAL_SPIRIT.get()), new ItemStack(ItemRegistry.AERIAL_SPIRIT.get())
            ));
            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.AERIAL_SOUL), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.AERIAL_SPIRIT.get()), new ItemStack(ItemRegistry.AERIAL_SPIRIT.get()), new ItemStack(ItemRegistry.ELDRITCH_SPIRIT.get())
            ));

            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.AQUEOUS_SPIRIT), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.AQUEOUS_SPIRIT.get()), new ItemStack(ItemRegistry.AQUEOUS_SPIRIT.get())
            ));
            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.AQUEOUS_SOUL), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.AQUEOUS_SPIRIT.get()), new ItemStack(ItemRegistry.AQUEOUS_SPIRIT.get()), new ItemStack(ItemRegistry.ELDRITCH_SPIRIT.get())
            ));

            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.EARTHEN_SPIRIT), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.EARTHEN_SPIRIT.get()), new ItemStack(ItemRegistry.EARTHEN_SPIRIT.get())
            ));
            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.EARTHEN_SOUL), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.EARTHEN_SPIRIT.get()), new ItemStack(ItemRegistry.EARTHEN_SPIRIT.get()), new ItemStack(ItemRegistry.ELDRITCH_SPIRIT.get())
            ));

            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.INFERNAL_SPIRIT), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.INFERNAL_SPIRIT.get()), new ItemStack(ItemRegistry.INFERNAL_SPIRIT.get())
            ));
            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.INFERNAL_SOUL), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()), new ItemStack(ItemRegistry.INFERNAL_SPIRIT.get()), new ItemStack(ItemRegistry.INFERNAL_SPIRIT.get()), new ItemStack(ItemRegistry.ELDRITCH_SPIRIT.get())
            ));

            ArcanemiconChapters.ALCHEMY_BREWS.addPage(new AlchemyMachinePage(vialPotions.get(AlchemyPotionsLoadedOnly.GLUTTONY), FluidStack.EMPTY, true, true,
                    FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY,
                    vialPotions.get(WizardsRebornAlchemyPotions.MUNDANE_BREW), new ItemStack(Items.HONEYCOMB), new ItemStack(ItemRegistry.ROTTING_ESSENCE.get()), new ItemStack(ItemRegistry.AQUEOUS_SPIRIT.get()), new ItemStack(ItemRegistry.SACRED_SPIRIT.get()), new ItemStack(ItemRegistry.WICKED_SPIRIT.get())
            ));

            List<MobEffectInstance> noEffects = new ArrayList<>();
            List<MobEffectInstance> regenerationSpiritEffects = new ArrayList<>();
            List<MobEffectInstance> sacredSpiritEffects = new ArrayList<>();
            List<MobEffectInstance> wickedSpiritEffects = new ArrayList<>();
            List<MobEffectInstance> aerialSpiritEffects = new ArrayList<>();
            List<MobEffectInstance> aqueousSpiritEffects = new ArrayList<>();
            List<MobEffectInstance> earthenSpiritEffects = new ArrayList<>();
            List<MobEffectInstance> infernalSpiritEffects = new ArrayList<>();
            List<MobEffectInstance> rottingEssenceEffects = new ArrayList<>();
            List<MobEffectInstance> warpFluxEffects = new ArrayList<>();
            List<MobEffectInstance> hexAshEffects = new ArrayList<>();
            List<MobEffectInstance> livingFleshEffects = new ArrayList<>();
            List<MobEffectInstance> blightedGunkEffects = new ArrayList<>();
            List<MobEffectInstance> voidSaltsEffects = new ArrayList<>();
            List<MobEffectInstance> auricEmbersEffects = new ArrayList<>();

            regenerationSpiritEffects.add(new MobEffectInstance(MobEffects.REGENERATION, 750, 0));

            sacredSpiritEffects.add(new MobEffectInstance(MobEffects.HEAL, 2, 0));
            wickedSpiritEffects.add(new MobEffectInstance(MobEffects.HARM, 2, 0));
            aerialSpiritEffects.add(new MobEffectInstance(MobEffectRegistry.ZEPHYRS_COURAGE.get(), 2500, 0));
            aqueousSpiritEffects.add(new MobEffectInstance(MobEffectRegistry.POSEIDONS_GRASP.get(), 2500, 0));
            earthenSpiritEffects.add(new MobEffectInstance(MobEffectRegistry.GAIAS_BULWARK.get(), 2500, 0));
            infernalSpiritEffects.add(new MobEffectInstance(MobEffectRegistry.MINERS_RAGE.get(), 2500, 0));

            rottingEssenceEffects.add(new MobEffectInstance(MobEffects.HUNGER, 1200, 0));

            warpFluxEffects.add(new MobEffectInstance(MobEffects.LEVITATION, 600, 0));

            hexAshEffects.add(new MobEffectInstance(MobEffects.BLINDNESS, 2400, 0));
            hexAshEffects.add(new MobEffectInstance(MobEffects.CONFUSION, 1600, 0));
            hexAshEffects.add(new MobEffectInstance(MobEffects.HARM, 1, 2));

            livingFleshEffects.add(new MobEffectInstance(MobEffects.SATURATION, 2, 4));

            blightedGunkEffects.add(new MobEffectInstance(MobEffects.WITHER, 1000, 0));

            voidSaltsEffects.add(new MobEffectInstance(MobEffects.REGENERATION, 525, 0));

            auricEmbersEffects.add(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 14000, 0));
            auricEmbersEffects.add(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
            auricEmbersEffects.add(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));

            ArcanemiconChapters.SMOKING_PIPE.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(sacredSpiritEffects, new ItemStack(ItemRegistry.SACRED_SPIRIT.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(wickedSpiritEffects, new ItemStack(ItemRegistry.WICKED_SPIRIT.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(regenerationSpiritEffects, new ItemStack(ItemRegistry.ARCANE_SPIRIT.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(regenerationSpiritEffects, new ItemStack(ItemRegistry.ELDRITCH_SPIRIT.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(aerialSpiritEffects, new ItemStack(ItemRegistry.AERIAL_SPIRIT.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(aqueousSpiritEffects, new ItemStack(ItemRegistry.AQUEOUS_SPIRIT.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(earthenSpiritEffects, new ItemStack(ItemRegistry.EARTHEN_SPIRIT.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(infernalSpiritEffects, new ItemStack(ItemRegistry.INFERNAL_SPIRIT.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(rottingEssenceEffects, new ItemStack(ItemRegistry.ROTTING_ESSENCE.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(ItemRegistry.GRIM_TALC.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(warpFluxEffects, new ItemStack(ItemRegistry.WARP_FLUX.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(hexAshEffects, new ItemStack(ItemRegistry.HEX_ASH.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(livingFleshEffects, new ItemStack(ItemRegistry.LIVING_FLESH.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(noEffects, new ItemStack(ItemRegistry.ALCHEMICAL_CALX.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(blightedGunkEffects, new ItemStack(ItemRegistry.BLIGHTED_GUNK.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(voidSaltsEffects, new ItemStack(ItemRegistry.VOID_SALTS.get())));
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(auricEmbersEffects, new ItemStack(ItemRegistry.AURIC_EMBERS.get())));
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
