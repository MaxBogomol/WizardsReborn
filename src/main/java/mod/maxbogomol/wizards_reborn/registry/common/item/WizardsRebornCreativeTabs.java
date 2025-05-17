package mod.maxbogomol.wizards_reborn.registry.common.item;

import mod.maxbogomol.fluffy_fur.common.creativetab.MultiCreativeTab;
import mod.maxbogomol.fluffy_fur.common.creativetab.SubCreativeTab;
import mod.maxbogomol.fluffy_fur.common.creativetab.SubCreativeTabStack;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionHandler;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentHandler;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualHandler;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.FracturedCrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.DrinkBottleItem;
import mod.maxbogomol.wizards_reborn.integration.common.create.WizardsRebornCreate;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.WizardsRebornFarmersDelight;
import mod.maxbogomol.wizards_reborn.integration.common.malum.WizardsRebornMalum;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAlchemyPotions;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystalRituals;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WizardsRebornCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WizardsReborn.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WIZARDS_REBORN = CREATIVE_MODE_TABS.register("tab",
            () -> MultiCreativeTab.builder().icon(() -> new ItemStack(WizardsRebornItems.FACETED_EARTH_CRYSTAL.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn"))
                    .withLabelColor(ColorUtil.packColor(255, 55, 48, 54))
                    .withBackgroundLocation(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_item_tab.png"))
                    .withTabsImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_tabs.png"))
                    .withSubArrowsImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_arrows.png"))
                    .multiBuild());

    public static final SubCreativeTabStack ALL =
            SubCreativeTabStack.create()
                    .subTitle(Component.translatable("creative_tab.wizards_reborn"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab MATERIALS =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.ARCANUM.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.materials")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.materials"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab PLANTS =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.ARCANE_LINEN.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.plants")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.plants"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab WOOD =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.wood")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.wood"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab STONE =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.WISESTONE.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.stone")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.stone"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab CRYSTALS =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.crystals")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.crystals"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab LUMOS =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.RAINBOW_ARCANE_LUMOS.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.lumos")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.lumos"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTabStack BUILD =
            SubCreativeTabStack.create().subIcon(() -> new ItemStack(WizardsRebornItems.ARCANE_PEDESTAL.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.build")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.build"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTabStack MECHANISMS =
            SubCreativeTabStack.create().subIcon(() -> new ItemStack(WizardsRebornItems.ARCANE_WORKBENCH.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.mechanisms")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.mechanisms"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTabStack ARCANE_NATURE =
            SubCreativeTabStack.create().subIcon(() -> new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.arcane_nature")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.arcane_nature"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTabStack ALCHEMY =
            SubCreativeTabStack.create().subIcon(() -> new ItemStack(WizardsRebornItems.ELDER_MOR.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.alchemy")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.alchemy"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTabStack CRYSTAL_RITUALS =
            SubCreativeTabStack.create().subIcon(() -> new ItemStack(WizardsRebornItems.ARCANUM_LENS.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.crystal_rituals")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.crystal_rituals"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTabStack AUTOMATION =
            SubCreativeTabStack.create().subIcon(() -> new ItemStack(WizardsRebornItems.ARCANE_WOOD_FRAME.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.automation")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.automation"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab EQUIPMENT =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.ARCANE_GOLD_PICKAXE.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.equipment")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.equipment"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab CREATIVE_ITEMS =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.CREATIVE_WISSEN_KEYCHAIN.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.creative_items")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.creative_items"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab COSMETICS =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.LEATHER_COLLAR.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.cosmetics")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.cosmetics"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab ARCANE_ENCHANTMENTS =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.ARCANE_ENCHANTED_BOOK.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.arcane_enchantments")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.arcane_enchantments"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab FOOD =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.MOR_PIE.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.food")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.food"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab ALCHEMY_POTIONS =
            SubCreativeTab.create().subIcon(() -> {
                        ItemStack stack = new ItemStack(WizardsRebornItems.ALCHEMY_FLASK_POTION.get());
                        AlchemyPotionUtil.setPotion(stack, WizardsRebornAlchemyPotions.WATER);
                        return stack;
                    })
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.alchemy_potions")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.alchemy_potions"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab DRINKS =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.VODKA_BOTTLE.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.drinks")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.drinks"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab FLUIDS =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.ALCHEMY_OIL_BUCKET.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.fluids")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.fluids"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static final SubCreativeTab ENTITIES =
            SubCreativeTab.create().subIcon(() -> new ItemStack(WizardsRebornItems.SNIFFALO_SPAWN_EGG.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn.sub").append(": ").append(Component.translatable("creative_tab.wizards_reborn.sub.entities")))
                    .subTitle(Component.translatable("creative_tab.wizards_reborn.sub.entities"))
                    .withSubTabImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_sub_tab.png"));

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void init() {
        if (WizardsRebornCreativeTabs.WIZARDS_REBORN.get() instanceof MultiCreativeTab multiCreativeTab) {
            multiCreativeTab.addSubTab(ALL);
            multiCreativeTab.addSubTab(MATERIALS);
            multiCreativeTab.addSubTab(PLANTS);
            multiCreativeTab.addSubTab(WOOD);
            multiCreativeTab.addSubTab(STONE);
            multiCreativeTab.addSubTab(CRYSTALS);
            multiCreativeTab.addSubTab(LUMOS);
            multiCreativeTab.addSubTab(BUILD);
            multiCreativeTab.addSubTab(MECHANISMS);
            multiCreativeTab.addSubTab(ARCANE_NATURE);
            multiCreativeTab.addSubTab(ALCHEMY);
            multiCreativeTab.addSubTab(CRYSTAL_RITUALS);
            multiCreativeTab.addSubTab(AUTOMATION);
            multiCreativeTab.addSubTab(CREATIVE_ITEMS);
            multiCreativeTab.addSubTab(EQUIPMENT);
            multiCreativeTab.addSubTab(COSMETICS);
            multiCreativeTab.addSubTab(ARCANE_ENCHANTMENTS);
            multiCreativeTab.addSubTab(FOOD);
            multiCreativeTab.addSubTab(ALCHEMY_POTIONS);
            multiCreativeTab.addSubTab(DRINKS);
            multiCreativeTab.addSubTab(FLUIDS);
            multiCreativeTab.addSubTab(ENTITIES);

            MECHANISMS.addSubTab(ARCANE_NATURE).addSubTab(ALCHEMY).addSubTab(CRYSTAL_RITUALS).addSubTab(AUTOMATION);
            ALCHEMY.addSubTab(FLUIDS);
        }
    }

    public static void addCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == WizardsRebornCreativeTabs.WIZARDS_REBORN.getKey()) {
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANEMICON);

            //MATERIALS
            addInSub(event, MATERIALS, WizardsRebornItems.ARCANE_GOLD_INGOT);
            addInSub(event, MATERIALS, WizardsRebornItems.ARCANE_GOLD_NUGGET);
            addInSub(event, MATERIALS, WizardsRebornItems.RAW_ARCANE_GOLD);
            addInSub(event, MATERIALS, WizardsRebornItems.ARCANE_GOLD_BLOCK);
            addInSub(event, MATERIALS, WizardsRebornItems.ARCANE_GOLD_ORE);
            addInSub(event, MATERIALS, WizardsRebornItems.DEEPSLATE_ARCANE_GOLD_ORE);
            addInSub(event, MATERIALS, WizardsRebornItems.NETHER_ARCANE_GOLD_ORE);
            addInSub(event, MATERIALS, WizardsRebornItems.RAW_ARCANE_GOLD_BLOCK);
            if (WizardsRebornCreate.isLoaded()) {
                addInSub(event, MATERIALS, WizardsRebornCreate.ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD);
                addInSub(event, MATERIALS, WizardsRebornCreate.ItemsLoadedOnly.ARCANE_GOLD_SHEET);
            }
            if (WizardsRebornMalum.isLoaded()) {
                addInSub(event, MATERIALS, WizardsRebornMalum.ItemsLoadedOnly.CRACKED_ARCANE_GOLD_IMPETUS);
                addInSub(event, MATERIALS, WizardsRebornMalum.ItemsLoadedOnly.ARCANE_GOLD_IMPETUS);
                addInSub(event, MATERIALS, WizardsRebornMalum.ItemsLoadedOnly.ARCANE_GOLD_NODE);
            }

            addInSub(event, MATERIALS, WizardsRebornItems.SARCON_INGOT);
            addInSub(event, MATERIALS, WizardsRebornItems.SARCON_NUGGET);
            addInSub(event, MATERIALS, WizardsRebornItems.SARCON_BLOCK);
            if (WizardsRebornCreate.isLoaded()) {
                addInSub(event, MATERIALS, WizardsRebornCreate.ItemsLoadedOnly.SARCON_SHEET);
            }
            if (WizardsRebornMalum.isLoaded()) {
                addInSub(event, MATERIALS, WizardsRebornMalum.ItemsLoadedOnly.CRACKED_SARCON_IMPETUS);
                addInSub(event, MATERIALS, WizardsRebornMalum.ItemsLoadedOnly.SARCON_IMPETUS);
                addInSub(event, MATERIALS, WizardsRebornMalum.ItemsLoadedOnly.SARCON_NODE);
            }

            addInSub(event, MATERIALS, WizardsRebornItems.VILENIUM_INGOT);
            addInSub(event, MATERIALS, WizardsRebornItems.VILENIUM_NUGGET);
            addInSub(event, MATERIALS, WizardsRebornItems.VILENIUM_BLOCK);
            if (WizardsRebornCreate.isLoaded()) {
                addInSub(event, MATERIALS, WizardsRebornCreate.ItemsLoadedOnly.VILENIUM_SHEET);
            }
            if (WizardsRebornMalum.isLoaded()) {
                addInSub(event, MATERIALS, WizardsRebornMalum.ItemsLoadedOnly.CRACKED_VILENIUM_IMPETUS);
                addInSub(event, MATERIALS, WizardsRebornMalum.ItemsLoadedOnly.VILENIUM_IMPETUS);
                addInSub(event, MATERIALS, WizardsRebornMalum.ItemsLoadedOnly.VILENIUM_NODE);
            }

            addInSub(event, MATERIALS, WizardsRebornItems.ARCANUM);
            addInSub(event, MATERIALS, WizardsRebornItems.ARCANUM_DUST);
            addInSub(event, MATERIALS, WizardsRebornItems.ARCANUM_BLOCK);
            addInSub(event, MATERIALS, WizardsRebornItems.ARCANUM_ORE);
            addInSub(event, MATERIALS, WizardsRebornItems.DEEPSLATE_ARCANUM_ORE);
            addInSub(event, MATERIALS, WizardsRebornItems.ARCANUM_DUST_BLOCK);

            addInSub(event, MATERIALS, WizardsRebornItems.ARCACITE);
            addInSub(event, MATERIALS, WizardsRebornItems.ARCACITE_BLOCK);

            addInSub(event, MATERIALS, WizardsRebornItems.PRECISION_CRYSTAL);
            addInSub(event, MATERIALS, WizardsRebornItems.PRECISION_CRYSTAL_BLOCK);

            addInSub(event, MATERIALS, WizardsRebornItems.NETHER_SALT);
            addInSub(event, MATERIALS, WizardsRebornItems.NETHER_SALT_BLOCK);
            addInSub(event, MATERIALS, WizardsRebornItems.NETHER_SALT_ORE);

            addInSub(event, MATERIALS, WizardsRebornItems.ALCHEMY_CALX);
            addInSub(event, MATERIALS, WizardsRebornItems.ALCHEMY_CALX_BLOCK);
            addInSub(event, MATERIALS, WizardsRebornItems.NATURAL_CALX);
            addInSub(event, MATERIALS, WizardsRebornItems.NATURAL_CALX_BLOCK);
            addInSub(event, MATERIALS, WizardsRebornItems.SCORCHED_CALX);
            addInSub(event, MATERIALS, WizardsRebornItems.SCORCHED_CALX_BLOCK);
            addInSub(event, MATERIALS, WizardsRebornItems.DISTANT_CALX);
            addInSub(event, MATERIALS, WizardsRebornItems.DISTANT_CALX_BLOCK);
            addInSub(event, MATERIALS, WizardsRebornItems.ENCHANTED_CALX);
            addInSub(event, MATERIALS, WizardsRebornItems.ENCHANTED_CALX_BLOCK);

            addInSub(event, MATERIALS, WizardsRebornItems.ARCACITE_POLISHING_MIXTURE);
            addInSub(event, MATERIALS, WizardsRebornItems.ARCACITE_POLISHING_MIXTURE_BLOCK);

            //PLANTS
            addInSub(event, PLANTS, WizardsRebornItems.ARCANE_LINEN_SEEDS);
            addInSub(event, PLANTS, WizardsRebornItems.ARCANE_LINEN);
            addInSub(event, PLANTS, WizardsRebornItems.ARCANE_LINEN_HAY);

            addInSub(event, PLANTS, WizardsRebornItems.MOR);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                addInSub(event, PLANTS, WizardsRebornFarmersDelight.ItemsLoadedOnly.MOR_COLONY);
            }
            addInSub(event, PLANTS, WizardsRebornItems.MOR_BLOCK);
            addInSub(event, PLANTS, WizardsRebornItems.ELDER_MOR);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                addInSub(event, PLANTS, WizardsRebornFarmersDelight.ItemsLoadedOnly.ELDER_MOR_COLONY);
            }
            addInSub(event, PLANTS, WizardsRebornItems.ELDER_MOR_BLOCK);

            addInSub(event, PLANTS, WizardsRebornItems.PITCHER_DEW);
            addInSub(event, PLANTS, WizardsRebornItems.PITCHER_TURNIP);
            addInSub(event, PLANTS, WizardsRebornItems.PITCHER_TURNIP_BLOCK);
            addInSub(event, PLANTS, WizardsRebornItems.SHINY_CLOVER_SEED);
            addInSub(event, PLANTS, WizardsRebornItems.SHINY_CLOVER);
            addInSub(event, PLANTS, WizardsRebornItems.UNDERGROUND_GRAPE_VINE);
            addInSub(event, PLANTS, WizardsRebornItems.UNDERGROUND_GRAPE);

            PLANTS.addDisplayItem(WizardsRebornItems.CORK_BAMBOO_SEED.get());
            PLANTS.addDisplayItem(WizardsRebornItems.CORK_BAMBOO.get());

            addInSub(event, PLANTS, WizardsRebornItems.PETALS);
            addInSub(event, PLANTS, WizardsRebornItems.FLOWER_FERTILIZER);
            addInSub(event, PLANTS, WizardsRebornItems.BUNCH_OF_THINGS);
            addInSub(event, PLANTS, WizardsRebornItems.GROUND_BROWN_MUSHROOM);
            addInSub(event, PLANTS, WizardsRebornItems.GROUND_RED_MUSHROOM);
            addInSub(event, PLANTS, WizardsRebornItems.GROUND_CRIMSON_FUNGUS);
            addInSub(event, PLANTS, WizardsRebornItems.GROUND_WARPED_FUNGUS);
            addInSub(event, PLANTS, WizardsRebornItems.GROUND_MOR);
            addInSub(event, PLANTS, WizardsRebornItems.GROUND_ELDER_MOR);

            PLANTS.addDisplayItem(WizardsRebornItems.ARCANE_WOOD_LEAVES.get());
            PLANTS.addDisplayItem(WizardsRebornItems.ARCANE_WOOD_SAPLING.get());

            PLANTS.addDisplayItem(WizardsRebornItems.INNOCENT_WOOD_LEAVES.get());
            PLANTS.addDisplayItem(WizardsRebornItems.INNOCENT_WOOD_SAPLING.get());
            PLANTS.addDisplayItem(WizardsRebornItems.PETALS_OF_INNOCENCE.get());

            //WOOD
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_LOG);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD);
            addInSub(event, WOOD, WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG);
            addInSub(event, WOOD, WizardsRebornItems.STRIPPED_ARCANE_WOOD);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_PLANKS);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_STAIRS);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_SLAB);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_CROSS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.STRIPPED_ARCANE_WOOD_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.STRIPPED_ARCANE_WOOD_CROSS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_PLANKS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_PLANKS_CROSS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_FENCE);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_FENCE_GATE);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_DOOR);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_TRAPDOOR);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_PRESSURE_PLATE);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_BUTTON);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_SIGN);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_HANGING_SIGN);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_BOAT);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_CHEST_BOAT);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_BRANCH);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_LEAVES);
            addInSub(event, WOOD, WizardsRebornItems.ARCANE_WOOD_SAPLING);

            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_LOG);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD);
            addInSub(event, WOOD, WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG);
            addInSub(event, WOOD, WizardsRebornItems.STRIPPED_INNOCENT_WOOD);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_PLANKS);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_STAIRS);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_SLAB);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_CROSS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.STRIPPED_INNOCENT_WOOD_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.STRIPPED_INNOCENT_WOOD_CROSS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_PLANKS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_PLANKS_CROSS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_FENCE);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_FENCE_GATE);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_DOOR);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_TRAPDOOR);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_PRESSURE_PLATE);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_BUTTON);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_SIGN);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_HANGING_SIGN);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_BOAT);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_CHEST_BOAT);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_BRANCH);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_LEAVES);
            addInSub(event, WOOD, WizardsRebornItems.INNOCENT_WOOD_SAPLING);
            addInSub(event, WOOD, WizardsRebornItems.PETALS_OF_INNOCENCE);

            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_SEED);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_BLOCK);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_PLANKS);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_STAIRS);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_CHISELED_STAIRS);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_SLAB);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_CHISELED_SLAB);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_CROSS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_PLANKS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_PLANKS_CROSS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_FENCE);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_FENCE_GATE);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_DOOR);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_TRAPDOOR);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_PRESSURE_PLATE);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_BUTTON);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_SIGN);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_HANGING_SIGN);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_RAFT);
            addInSub(event, WOOD, WizardsRebornItems.CORK_BAMBOO_CHEST_RAFT);

            //STONE
            addInSub(event, STONE, WizardsRebornItems.WISESTONE);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_STAIRS);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_SLAB);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_WALL);
            addInSub(event, STONE, WizardsRebornItems.POLISHED_WISESTONE);
            addInSub(event, STONE, WizardsRebornItems.POLISHED_WISESTONE_STAIRS);
            addInSub(event, STONE, WizardsRebornItems.POLISHED_WISESTONE_SLAB);
            addInSub(event, STONE, WizardsRebornItems.POLISHED_WISESTONE_WALL);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_BRICKS);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_BRICKS_STAIRS);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_BRICKS_SLAB);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_BRICKS_WALL);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_TILE);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_TILE_STAIRS);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_TILE_SLAB);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_TILE_WALL);
            addInSub(event, STONE, WizardsRebornItems.CHISELED_WISESTONE);
            addInSub(event, STONE, WizardsRebornItems.CHISELED_WISESTONE_STAIRS);
            addInSub(event, STONE, WizardsRebornItems.CHISELED_WISESTONE_SLAB);
            addInSub(event, STONE, WizardsRebornItems.CHISELED_WISESTONE_WALL);
            addInSub(event, STONE, WizardsRebornItems.WISESTONE_PILLAR);
            addInSub(event, STONE, WizardsRebornItems.POLISHED_WISESTONE_PRESSURE_PLATE);
            addInSub(event, STONE, WizardsRebornItems.POLISHED_WISESTONE_BUTTON);

            //CRYSTALS
            addInSub(event, CRYSTALS, WizardsRebornItems.ARCANUM_SEED);
            addInSub(event, CRYSTALS, WizardsRebornItems.EARTH_CRYSTAL_SEED);
            addInSub(event, CRYSTALS, WizardsRebornItems.WATER_CRYSTAL_SEED);
            addInSub(event, CRYSTALS, WizardsRebornItems.AIR_CRYSTAL_SEED);
            addInSub(event, CRYSTALS, WizardsRebornItems.FIRE_CRYSTAL_SEED);
            addInSub(event, CRYSTALS, WizardsRebornItems.VOID_CRYSTAL_SEED);

            addInSub(event, CRYSTALS, FracturedCrystalItem.creativeTabRandomStats(WizardsRebornItems.FRACTURED_EARTH_CRYSTAL.get()));
            addInSub(event, CRYSTALS, FracturedCrystalItem.creativeTabRandomStats(WizardsRebornItems.FRACTURED_WATER_CRYSTAL.get()));
            addInSub(event, CRYSTALS, FracturedCrystalItem.creativeTabRandomStats(WizardsRebornItems.FRACTURED_AIR_CRYSTAL.get()));
            addInSub(event, CRYSTALS, FracturedCrystalItem.creativeTabRandomStats(WizardsRebornItems.FRACTURED_FIRE_CRYSTAL.get()));
            addInSub(event, CRYSTALS, FracturedCrystalItem.creativeTabRandomStats(WizardsRebornItems.FRACTURED_VOID_CRYSTAL.get()));

            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.EARTH_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.WATER_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.AIR_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.FIRE_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.VOID_CRYSTAL.get()));

            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.FACETED_EARTH_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.FACETED_WATER_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.FACETED_AIR_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.FACETED_FIRE_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.FACETED_VOID_CRYSTAL.get()));

            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.ADVANCED_EARTH_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.ADVANCED_WATER_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.ADVANCED_AIR_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.ADVANCED_FIRE_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.ADVANCED_VOID_CRYSTAL.get()));

            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.MASTERFUL_EARTH_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.MASTERFUL_WATER_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.MASTERFUL_AIR_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.MASTERFUL_FIRE_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.MASTERFUL_VOID_CRYSTAL.get()));

            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.PURE_EARTH_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.PURE_WATER_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.PURE_AIR_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.PURE_FIRE_CRYSTAL.get()));
            addInSub(event, CRYSTALS, CrystalItem.creativeTabRandomStats(WizardsRebornItems.PURE_VOID_CRYSTAL.get()));

            //LUMOS
            addInSub(event, LUMOS, WizardsRebornItems.WHITE_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.LIGHT_GRAY_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.GRAY_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.BLACK_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.BROWN_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.RED_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.ORANGE_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.YELLOW_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.LIME_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.GREEN_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.CYAN_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.LIGHT_BLUE_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.BLUE_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.PURPLE_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.MAGENTA_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.PINK_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.RAINBOW_ARCANE_LUMOS);
            addInSub(event, LUMOS, WizardsRebornItems.COSMIC_ARCANE_LUMOS);

            addInSub(event, LUMOS, WizardsRebornItems.WHITE_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.LIGHT_GRAY_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.GRAY_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.BLACK_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.BROWN_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.RED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.ORANGE_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.YELLOW_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.LIME_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.GREEN_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.CYAN_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.LIGHT_BLUE_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.BLUE_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.PURPLE_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.MAGENTA_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.PINK_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.RAINBOW_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.COSMIC_LUMINAL_GLASS);

            addInSub(event, LUMOS, WizardsRebornItems.WHITE_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.LIGHT_GRAY_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.GRAY_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.BLACK_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.BROWN_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.RED_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.ORANGE_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.YELLOW_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.LIME_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.GREEN_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.CYAN_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.LIGHT_BLUE_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.BLUE_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.PURPLE_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.MAGENTA_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.PINK_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.RAINBOW_FRAMED_LUMINAL_GLASS);
            addInSub(event, LUMOS, WizardsRebornItems.COSMIC_FRAMED_LUMINAL_GLASS);

            //BUILD
            BUILD.addDisplayItem(WizardsRebornItems.BUNCH_OF_THINGS.get());

            addInSub(event, BUILD, WizardsRebornItems.ARCANE_PEDESTAL);
            addInSub(event, BUILD, WizardsRebornItems.INNOCENT_PEDESTAL);
            addInSub(event, BUILD, WizardsRebornItems.CORK_BAMBOO_PEDESTAL);
            addInSub(event, BUILD, WizardsRebornItems.WISESTONE_PEDESTAL);

            addInSub(event, BUILD, WizardsRebornItems.GILDED_ARCANE_WOOD_PLANKS);
            addInSub(event, BUILD, WizardsRebornItems.GILDED_INNOCENT_WOOD_PLANKS);
            addInSub(event, BUILD, WizardsRebornItems.GILDED_CORK_BAMBOO_PLANKS);
            addInSub(event, BUILD, WizardsRebornItems.GILDED_CORK_BAMBOO_CHISELED_PLANKS);
            addInSub(event, BUILD, WizardsRebornItems.GILDED_POLISHED_WISESTONE);

            addInSub(event, BUILD, WizardsRebornItems.ARCANE_SALT_TORCH);
            addInSub(event, BUILD, WizardsRebornItems.ARCANE_SALT_LANTERN);
            addInSub(event, BUILD, WizardsRebornItems.ARCANE_SALT_CAMPFIRE);
            addInSub(event, BUILD, WizardsRebornItems.INNOCENT_SALT_TORCH);
            addInSub(event, BUILD, WizardsRebornItems.INNOCENT_SALT_LANTERN);
            addInSub(event, BUILD, WizardsRebornItems.INNOCENT_SALT_CAMPFIRE);
            addInSub(event, BUILD, WizardsRebornItems.CORK_BAMBOO_SALT_TORCH);
            addInSub(event, BUILD, WizardsRebornItems.CORK_BAMBOO_SALT_LANTERN);
            addInSub(event, BUILD, WizardsRebornItems.CORK_BAMBOO_SALT_CAMPFIRE);
            addInSub(event, BUILD, WizardsRebornItems.WISESTONE_SALT_TORCH);
            addInSub(event, BUILD, WizardsRebornItems.WISESTONE_SALT_LANTERN);
            addInSub(event, BUILD, WizardsRebornItems.WISESTONE_SALT_CAMPFIRE);

            //ARCANE_NATURE
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.WISSEN_ALTAR);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.WISSEN_TRANSLATOR);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.WISSEN_CRYSTALLIZER);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.ARCANE_WORKBENCH);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.WISSEN_CELL);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.JEWELER_TABLE);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.ALTAR_OF_DROUGHT);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.TOTEM_BASE);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.TOTEM_OF_FLAMES);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.EXPERIENCE_TOTEM);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.TOTEM_OF_EXPERIENCE_ABSORPTION);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.TOTEM_OF_DISENCHANT);
            addInSub(event, ARCANE_NATURE, WizardsRebornItems.ARCANE_ITERATOR);

            ARCANE_NATURE.addDisplayItem(WizardsRebornItems.CREATIVE_WISSEN_STORAGE.get());

            //ALCHEMY
            addInSub(event, ALCHEMY, WizardsRebornItems.FLUID_PIPE);
            addInSub(event, ALCHEMY, WizardsRebornItems.FLUID_EXTRACTOR);
            addInSub(event, ALCHEMY, WizardsRebornItems.STEAM_PIPE);
            addInSub(event, ALCHEMY, WizardsRebornItems.STEAM_EXTRACTOR);
            addInSub(event, ALCHEMY, WizardsRebornItems.ALCHEMY_FURNACE);
            addInSub(event, ALCHEMY, WizardsRebornItems.ORBITAL_FLUID_RETAINER);
            addInSub(event, ALCHEMY, WizardsRebornItems.STEAM_THERMAL_STORAGE);
            addInSub(event, ALCHEMY, WizardsRebornItems.ALCHEMY_MACHINE);
            addInSub(event, ALCHEMY, WizardsRebornItems.ALCHEMY_BOILER);
            addInSub(event, ALCHEMY, WizardsRebornItems.ARCANE_CENSER);

            addInSub(event, ALCHEMY, WizardsRebornItems.ALCHEMY_GLASS);
            addInSub(event, ALCHEMY, WizardsRebornItems.ALCHEMY_VIAL);
            addInSub(event, ALCHEMY, WizardsRebornItems.ALCHEMY_FLASK);
            addInSub(event, ALCHEMY, WizardsRebornItems.ALCHEMY_BOTTLE);

            addInSub(event, ALCHEMY, WizardsRebornItems.SNIFFALO_EGG);

            ALCHEMY.addDisplayItem(WizardsRebornItems.CREATIVE_FLUID_STORAGE.get());
            ALCHEMY.addDisplayItem(WizardsRebornItems.CREATIVE_STEAM_STORAGE.get());

            //CRYSTAL_RITUALS
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.LIGHT_EMITTER);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.LIGHT_TRANSFER_LENS);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.RUNIC_PEDESTAL);

            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_LUNAM);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_VITA);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_SOLEM);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_MORS);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_MIRACULUM);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_TEMPUS);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_STATERA);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_ECLIPSIS);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_SICCITAS);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_SOLSTITIUM);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_FAMES);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_RENAISSANCE);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_BELLUM);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_LUX);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_KARA);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_DEGRADATIO);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_PRAEDICTIONEM);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_EVOLUTIONIS);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_TENEBRIS);
            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ENGRAVED_WISESTONE_UNIVERSUM);

            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.ARCANUM_LENS);

            addInSub(event, CRYSTAL_RITUALS, WizardsRebornItems.WISESTONE_PLATE);
            for (CrystalRitual ritual : CrystalRitualHandler.getCrystalRituals()) {
                if (ritual != WizardsRebornCrystalRituals.EMPTY) {
                    ItemStack stack = new ItemStack(WizardsRebornItems.RUNIC_WISESTONE_PLATE.get());
                    CrystalRitualUtil.setCrystalRitual(stack, ritual);
                    addInSub(event, CRYSTAL_RITUALS, stack);
                }
            }

            CRYSTAL_RITUALS.addDisplayItem(WizardsRebornItems.CREATIVE_LIGHT_STORAGE.get());

            //AUTOMATION
            addInSub(event, AUTOMATION, WizardsRebornItems.ARCANE_LEVER);
            addInSub(event, AUTOMATION, WizardsRebornItems.ARCANE_HOPPER);
            addInSub(event, AUTOMATION, WizardsRebornItems.REDSTONE_SENSOR);
            addInSub(event, AUTOMATION, WizardsRebornItems.WISSEN_SENSOR);
            addInSub(event, AUTOMATION, WizardsRebornItems.COOLDOWN_SENSOR);
            addInSub(event, AUTOMATION, WizardsRebornItems.EXPERIENCE_SENSOR);
            addInSub(event, AUTOMATION, WizardsRebornItems.LIGHT_SENSOR);
            addInSub(event, AUTOMATION, WizardsRebornItems.HEAT_SENSOR);
            addInSub(event, AUTOMATION, WizardsRebornItems.FLUID_SENSOR);
            addInSub(event, AUTOMATION, WizardsRebornItems.STEAM_SENSOR);
            addInSub(event, AUTOMATION, WizardsRebornItems.WISSEN_ACTIVATOR);
            addInSub(event, AUTOMATION, WizardsRebornItems.ITEM_SORTER);

            addInSub(event, AUTOMATION, WizardsRebornItems.ARCANE_WOOD_FRAME);
            addInSub(event, AUTOMATION, WizardsRebornItems.ARCANE_WOOD_GLASS_FRAME);
            addInSub(event, AUTOMATION, WizardsRebornItems.ARCANE_WOOD_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.ARCANE_WOOD_WISSEN_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.ARCANE_WOOD_LIGHT_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.ARCANE_WOOD_FLUID_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.ARCANE_WOOD_STEAM_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.INNOCENT_WOOD_FRAME);
            addInSub(event, AUTOMATION, WizardsRebornItems.INNOCENT_WOOD_GLASS_FRAME);
            addInSub(event, AUTOMATION, WizardsRebornItems.INNOCENT_WOOD_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.INNOCENT_WOOD_WISSEN_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.INNOCENT_WOOD_LIGHT_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.INNOCENT_WOOD_FLUID_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.INNOCENT_WOOD_STEAM_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.CORK_BAMBOO_FRAME);
            addInSub(event, AUTOMATION, WizardsRebornItems.CORK_BAMBOO_GLASS_FRAME);
            addInSub(event, AUTOMATION, WizardsRebornItems.CORK_BAMBOO_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.CORK_BAMBOO_WISSEN_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.CORK_BAMBOO_LIGHT_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.CORK_BAMBOO_FLUID_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.CORK_BAMBOO_STEAM_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.WISESTONE_FRAME);
            addInSub(event, AUTOMATION, WizardsRebornItems.WISESTONE_GLASS_FRAME);
            addInSub(event, AUTOMATION, WizardsRebornItems.WISESTONE_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.WISESTONE_WISSEN_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.WISESTONE_LIGHT_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.WISESTONE_FLUID_CASING);
            addInSub(event, AUTOMATION, WizardsRebornItems.WISESTONE_STEAM_CASING);

            addInSub(event, AUTOMATION, WizardsRebornItems.CREATIVE_WISSEN_STORAGE);
            addInSub(event, AUTOMATION, WizardsRebornItems.CREATIVE_LIGHT_STORAGE);
            addInSub(event, AUTOMATION, WizardsRebornItems.CREATIVE_FLUID_STORAGE);
            addInSub(event, AUTOMATION, WizardsRebornItems.CREATIVE_STEAM_STORAGE);

            //CREATIVE_ITEMS
            CREATIVE_ITEMS.addDisplayItem(WizardsRebornItems.CREATIVE_WISSEN_STORAGE.get());
            CREATIVE_ITEMS.addDisplayItem(WizardsRebornItems.CREATIVE_LIGHT_STORAGE.get());
            CREATIVE_ITEMS.addDisplayItem(WizardsRebornItems.CREATIVE_FLUID_STORAGE.get());
            CREATIVE_ITEMS.addDisplayItem(WizardsRebornItems.CREATIVE_STEAM_STORAGE.get());
            CREATIVE_ITEMS.addDisplayItem(WizardsRebornItems.CREATIVE_WISSEN_KEYCHAIN.get());
            CREATIVE_ITEMS.addDisplayItem(WizardsRebornItems.CREATIVE_KNOWLEDGE_SCROLL.get());
            CREATIVE_ITEMS.addDisplayItem(WizardsRebornItems.CREATIVE_KNOWLEDGE_ANCIENT_SCROLL.get());
            CREATIVE_ITEMS.addDisplayItem(WizardsRebornItems.CREATIVE_SPELL_SCROLL.get());
            CREATIVE_ITEMS.addDisplayItem(WizardsRebornItems.CREATIVE_SPELL_ANCIENT_SCROLL.get());

            //EQUIPMENT
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_SWORD);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_PICKAXE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_AXE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_SHOVEL);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_HOE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_SCYTHE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                addInSub(event, EQUIPMENT, WizardsRebornFarmersDelight.ItemsLoadedOnly.ARCANE_WOOD_KNIFE);
            }
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_MORTAR);

            addInSub(event, EQUIPMENT, WizardsRebornItems.INNOCENT_WOOD_SWORD);
            addInSub(event, EQUIPMENT, WizardsRebornItems.INNOCENT_WOOD_PICKAXE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.INNOCENT_WOOD_AXE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.INNOCENT_WOOD_SHOVEL);
            addInSub(event, EQUIPMENT, WizardsRebornItems.INNOCENT_WOOD_HOE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.INNOCENT_WOOD_SCYTHE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                addInSub(event, EQUIPMENT, WizardsRebornFarmersDelight.ItemsLoadedOnly.INNOCENT_WOOD_KNIFE);
            }
            addInSub(event, EQUIPMENT, WizardsRebornItems.INNOCENT_WOOD_MORTAR);

            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_GOLD_SWORD);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_GOLD_PICKAXE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_GOLD_AXE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_GOLD_SHOVEL);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_GOLD_HOE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_GOLD_SCYTHE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                addInSub(event, EQUIPMENT, WizardsRebornFarmersDelight.ItemsLoadedOnly.ARCANE_GOLD_KNIFE);
            }
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_GOLD_HELMET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_GOLD_CHESTPLATE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_GOLD_LEGGINGS);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_GOLD_BOOTS);

            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_FORTRESS_HELMET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_FORTRESS_CHESTPLATE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_FORTRESS_LEGGINGS);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_FORTRESS_BOOTS);

            addInSub(event, EQUIPMENT, WizardsRebornItems.INVENTOR_WIZARD_HAT);
            addInSub(event, EQUIPMENT, WizardsRebornItems.INVENTOR_WIZARD_COSTUME);
            addInSub(event, EQUIPMENT, WizardsRebornItems.INVENTOR_WIZARD_TROUSERS);
            addInSub(event, EQUIPMENT, WizardsRebornItems.INVENTOR_WIZARD_BOOTS);

            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_CANE);

            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_SMOKING_PIPE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.INNOCENT_WOOD_SMOKING_PIPE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.BAMBOO_SMOKING_PIPE);
            addInSub(event, EQUIPMENT, WizardsRebornItems.CORK_BAMBOO_SMOKING_PIPE);

            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_BOW);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_CROSSBOW);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_FISHING_ROD);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WOOD_SHEARS);

            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_WAND);
            addInSub(event, EQUIPMENT, WizardsRebornItems.WISSEN_WAND);
            addInSub(event, EQUIPMENT, WizardsRebornItems.BLAZING_WAND);

            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANUM_AMULET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANUM_RING);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCACITE_AMULET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCACITE_RING);
            addInSub(event, EQUIPMENT, WizardsRebornItems.WISSEN_KEYCHAIN);
            addInSub(event, EQUIPMENT, WizardsRebornItems.WISSEN_RING);
            addInSub(event, EQUIPMENT, WizardsRebornItems.CREATIVE_WISSEN_KEYCHAIN);
            addInSub(event, EQUIPMENT, WizardsRebornItems.LEATHER_BELT);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ARCANE_FORTRESS_BELT);
            addInSub(event, EQUIPMENT, WizardsRebornItems.INVENTOR_WIZARD_BELT);
            addInSub(event, EQUIPMENT, WizardsRebornItems.CRYSTAL_BAG);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ALCHEMY_BAG);

            addInSub(event, EQUIPMENT, WizardsRebornItems.LEATHER_COLLAR);

            addInSub(event, EQUIPMENT, WizardsRebornItems.BROWN_MUSHROOM_CAP);
            addInSub(event, EQUIPMENT, WizardsRebornItems.RED_MUSHROOM_CAP);
            addInSub(event, EQUIPMENT, WizardsRebornItems.CRIMSON_FUNGUS_CAP);
            addInSub(event, EQUIPMENT, WizardsRebornItems.WARPED_FUNGUS_CAP);
            addInSub(event, EQUIPMENT, WizardsRebornItems.MOR_CAP);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ELDER_MOR_CAP);

            addInSub(event, EQUIPMENT, WizardsRebornItems.WHITE_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.LIGHT_GRAY_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.GRAY_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.BLACK_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.BROWN_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.RED_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.ORANGE_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.YELLOW_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.LIME_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.GREEN_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.CYAN_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.LIGHT_BLUE_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.BLUE_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.PURPLE_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.MAGENTA_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.PINK_CARGO_CARPET);
            addInSub(event, EQUIPMENT, WizardsRebornItems.RAINBOW_CARGO_CARPET);

            addInSub(event, EQUIPMENT, WizardsRebornItems.KNOWLEDGE_SCROLL);
            addInSub(event, EQUIPMENT, WizardsRebornItems.CREATIVE_KNOWLEDGE_SCROLL);
            addInSub(event, EQUIPMENT, WizardsRebornItems.CREATIVE_KNOWLEDGE_ANCIENT_SCROLL);
            addInSub(event, EQUIPMENT, WizardsRebornItems.CREATIVE_SPELL_SCROLL);
            addInSub(event, EQUIPMENT, WizardsRebornItems.CREATIVE_SPELL_ANCIENT_SCROLL);

            //COSMETICS
            COSMETICS.addDisplayItem(WizardsRebornItems.LEATHER_COLLAR.get());

            COSMETICS.addDisplayItem(WizardsRebornItems.BROWN_MUSHROOM_CAP.get());
            COSMETICS.addDisplayItem(WizardsRebornItems.RED_MUSHROOM_CAP.get());
            COSMETICS.addDisplayItem(WizardsRebornItems.CRIMSON_FUNGUS_CAP.get());
            COSMETICS.addDisplayItem(WizardsRebornItems.WARPED_FUNGUS_CAP.get());
            COSMETICS.addDisplayItem(WizardsRebornItems.MOR_CAP.get());
            COSMETICS.addDisplayItem(WizardsRebornItems.ELDER_MOR_CAP.get());

            addInSub(event, COSMETICS, WizardsRebornItems.VIOLENCE_BANNER_PATTERN);
            addInSub(event, COSMETICS, WizardsRebornItems.REPRODUCTION_BANNER_PATTERN);
            addInSub(event, COSMETICS, WizardsRebornItems.COOPERATION_BANNER_PATTERN);
            addInSub(event, COSMETICS, WizardsRebornItems.HUNGER_BANNER_PATTERN);
            addInSub(event, COSMETICS, WizardsRebornItems.SURVIVAL_BANNER_PATTERN);
            addInSub(event, COSMETICS, WizardsRebornItems.ASCENSION_BANNER_PATTERN);

            addInSub(event, COSMETICS, WizardsRebornItems.MUSIC_DISC_ARCANUM);
            addInSub(event, COSMETICS, WizardsRebornItems.MUSIC_DISC_MOR);
            addInSub(event, COSMETICS, WizardsRebornItems.MUSIC_DISC_REBORN);
            addInSub(event, COSMETICS, WizardsRebornItems.MUSIC_DISC_SHIMMER);
            addInSub(event, COSMETICS, WizardsRebornItems.MUSIC_DISC_CAPITALISM);
            addInSub(event, COSMETICS, WizardsRebornItems.MUSIC_DISC_PANACHE);
            addInSub(event, COSMETICS, WizardsRebornItems.MUSIC_DISC_DISCO);

            addInSub(event, COSMETICS, WizardsRebornItems.ARCANE_WOOD_TRIM);
            addInSub(event, COSMETICS, WizardsRebornItems.WISESTONE_TRIM);
            addInSub(event, COSMETICS, WizardsRebornItems.INNOCENT_WOOD_TRIM);
            addInSub(event, COSMETICS, WizardsRebornItems.TOP_HAT_TRIM);
            addInSub(event, COSMETICS, WizardsRebornItems.MAGNIFICENT_MAID_TRIM);
            addInSub(event, COSMETICS, WizardsRebornItems.SUMMER_LOVE_TRIM);
            addInSub(event, COSMETICS, WizardsRebornItems.SOUL_HUNTER_TRIM);
            addInSub(event, COSMETICS, WizardsRebornItems.IMPLOSION_TRIM);
            addInSub(event, COSMETICS, WizardsRebornItems.PHANTOM_INK_TRIM);

            //ARCANE_ENCHANTMENTS
            for (ArcaneEnchantment enchantment : ArcaneEnchantmentHandler.getArcaneEnchantments()) {
                for (int i = 0; i < enchantment.getMaxLevel(); i++) {
                    ItemStack stack = new ItemStack(WizardsRebornItems.ARCANE_ENCHANTED_BOOK.get());
                    ArcaneEnchantmentUtil.addArcaneEnchantment(stack, enchantment, i + 1);
                    addInSub(event, ARCANE_ENCHANTMENTS, stack);
                }
            }

            //FOOD
            addInSub(event, FOOD, WizardsRebornItems.MOR_PIE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                addInSub(event, FOOD, WizardsRebornFarmersDelight.ItemsLoadedOnly.MOR_PIE_SLICE);
            }
            addInSub(event, FOOD, WizardsRebornItems.ELDER_MOR_PIE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                addInSub(event, FOOD, WizardsRebornFarmersDelight.ItemsLoadedOnly.ELDER_MOR_PIE_SLICE);
            }
            addInSub(event, FOOD, WizardsRebornItems.PITCHER_TURNIP_PIE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                addInSub(event, FOOD, WizardsRebornFarmersDelight.ItemsLoadedOnly.PITCHER_TURNIP_PIE_SLICE);
            }

            addInSub(event, FOOD, WizardsRebornItems.BLIN);
            addInSub(event, FOOD, WizardsRebornItems.CREPE);
            addInSub(event, FOOD, WizardsRebornItems.SWEET_BERRIES_JAM_BLIN);
            addInSub(event, FOOD, WizardsRebornItems.SWEET_BERRIES_JAM_CREPE);
            addInSub(event, FOOD, WizardsRebornItems.GLOW_BERRIES_JAM_BLIN);
            addInSub(event, FOOD, WizardsRebornItems.GLOW_BERRIES_JAM_CREPE);
            addInSub(event, FOOD, WizardsRebornItems.UNDERGROUND_GRAPE_JAM_BLIN);
            addInSub(event, FOOD, WizardsRebornItems.UNDERGROUND_GRAPE_JAM_CREPE);
            addInSub(event, FOOD, WizardsRebornItems.PITCHER_DEW_JAM_BLIN);
            addInSub(event, FOOD, WizardsRebornItems.PITCHER_DEW_JAM_CREPE);
            addInSub(event, FOOD, WizardsRebornItems.SHINY_CLOVER_JAM_BLIN);
            addInSub(event, FOOD, WizardsRebornItems.SHINY_CLOVER_JAM_CREPE);
            addInSub(event, FOOD, WizardsRebornItems.MOR_JAM_BLIN);
            addInSub(event, FOOD, WizardsRebornItems.MOR_JAM_CREPE);
            addInSub(event, FOOD, WizardsRebornItems.ELDER_MOR_JAM_BLIN);
            addInSub(event, FOOD, WizardsRebornItems.ELDER_MOR_JAM_CREPE);
            addInSub(event, FOOD, WizardsRebornItems.HONEY_BLIN);
            addInSub(event, FOOD, WizardsRebornItems.HONEY_CREPE);
            addInSub(event, FOOD, WizardsRebornItems.CHOCOLATE_BLIN);
            addInSub(event, FOOD, WizardsRebornItems.CHOCOLATE_CREPE);

            addInSub(event, FOOD, WizardsRebornItems.MOR_SOUP);
            addInSub(event, FOOD, WizardsRebornItems.ELDER_MOR_SOUP);

            addInSub(event, FOOD, WizardsRebornItems.SWEET_BERRIES_JAM_VIAL);
            addInSub(event, FOOD, WizardsRebornItems.SWEET_BERRIES_JAM_FLASK);
            addInSub(event, FOOD, WizardsRebornItems.GLOW_BERRIES_JAM_VIAL);
            addInSub(event, FOOD, WizardsRebornItems.GLOW_BERRIES_JAM_FLASK);
            addInSub(event, FOOD, WizardsRebornItems.UNDERGROUND_GRAPE_JAM_VIAL);
            addInSub(event, FOOD, WizardsRebornItems.UNDERGROUND_GRAPE_JAM_FLASK);
            addInSub(event, FOOD, WizardsRebornItems.PITCHER_DEW_JAM_VIAL);
            addInSub(event, FOOD, WizardsRebornItems.PITCHER_DEW_JAM_FLASK);
            addInSub(event, FOOD, WizardsRebornItems.SHINY_CLOVER_JAM_VIAL);
            addInSub(event, FOOD, WizardsRebornItems.SHINY_CLOVER_JAM_FLASK);
            addInSub(event, FOOD, WizardsRebornItems.MOR_JAM_VIAL);
            addInSub(event, FOOD, WizardsRebornItems.MOR_JAM_FLASK);
            addInSub(event, FOOD, WizardsRebornItems.ELDER_MOR_JAM_VIAL);
            addInSub(event, FOOD, WizardsRebornItems.ELDER_MOR_JAM_FLASK);
            addInSub(event, FOOD, WizardsRebornItems.HONEY_VIAL);
            addInSub(event, FOOD, WizardsRebornItems.HONEY_FLASK);
            addInSub(event, FOOD, WizardsRebornItems.CHOCOLATE_VIAL);
            addInSub(event, FOOD, WizardsRebornItems.CHOCOLATE_FLASK);

            //ALCHEMY_POTIONS
            for (AlchemyPotion potion : AlchemyPotionHandler.getAlchemyPotions()) {
                if (potion != WizardsRebornAlchemyPotions.EMPTY && potion != WizardsRebornAlchemyPotions.COMBINED) {
                    ItemStack stack = new ItemStack(WizardsRebornItems.ALCHEMY_VIAL_POTION.get());
                    AlchemyPotionUtil.setPotion(stack, potion);
                    addInSub(event, ALCHEMY_POTIONS, stack);
                }
            }

            for (AlchemyPotion potion : AlchemyPotionHandler.getAlchemyPotions()) {
                if (potion != WizardsRebornAlchemyPotions.EMPTY && potion != WizardsRebornAlchemyPotions.COMBINED) {
                    ItemStack stack = new ItemStack(WizardsRebornItems.ALCHEMY_FLASK_POTION.get());
                    AlchemyPotionUtil.setPotion(stack, potion);
                    addInSub(event, ALCHEMY_POTIONS, stack);
                }
            }

            //DRINKS
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.VODKA_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.BOURBON_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.WHISKEY_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.WHITE_WINE_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.RED_WINE_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.PORT_WINE_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.PALM_LIQUEUR_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.MEAD_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.SBITEN_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.SLIVOVITZ_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.SAKE_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.SOJU_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.CHICHA_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.CHACHA_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.APPLEJACK_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.RAKIA_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.KIRSCH_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.BOROVICHKA_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.PALINKA_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.TEQUILA_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.PULQUE_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.ARKHI_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.TEJ_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.WISSEN_BEER_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.MOR_TINCTURE_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.INNOCENT_WINE_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.TARKHUNA_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.BAIKAL_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.KVASS_BOTTLE.get()));
            event.acceptAll(DrinkBottleItem.getItemsForTab(WizardsRebornItems.KISSEL_BOTTLE.get()));

            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.VODKA_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.BOURBON_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.WHISKEY_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.WHITE_WINE_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.RED_WINE_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.PORT_WINE_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.PALM_LIQUEUR_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.MEAD_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.SBITEN_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.SLIVOVITZ_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.SAKE_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.SOJU_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.CHICHA_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.CHACHA_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.APPLEJACK_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.RAKIA_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.KIRSCH_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.BOROVICHKA_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.PALINKA_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.TEQUILA_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.PULQUE_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.ARKHI_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.TEJ_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.WISSEN_BEER_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.MOR_TINCTURE_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.INNOCENT_WINE_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.TARKHUNA_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.BAIKAL_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.KVASS_BOTTLE.get()));
            addInSub(DRINKS, DrinkBottleItem.getItemsAllStages(WizardsRebornItems.KISSEL_BOTTLE.get()));

            //FLUIDS
            addInSub(event, FLUIDS, WizardsRebornItems.MUNDANE_BREW_BUCKET);
            addInSub(event, FLUIDS, WizardsRebornItems.ALCHEMY_OIL_BUCKET);
            addInSub(event, FLUIDS, WizardsRebornItems.OIL_TEA_BUCKET);
            addInSub(event, FLUIDS, WizardsRebornItems.WISSEN_TEA_BUCKET);
            addInSub(event, FLUIDS, WizardsRebornItems.MILK_TEA_BUCKET);
            addInSub(event, FLUIDS, WizardsRebornItems.MUSHROOM_BREW_BUCKET);
            addInSub(event, FLUIDS, WizardsRebornItems.HELLISH_MUSHROOM_BREW_BUCKET);
            addInSub(event, FLUIDS, WizardsRebornItems.MOR_BREW_BUCKET);
            addInSub(event, FLUIDS, WizardsRebornItems.FLOWER_BREW_BUCKET);

            //ENTITIES
            addInSub(event, ENTITIES, WizardsRebornItems.SNIFFALO_SPAWN_EGG);
        }
    }

    public static void addInSub(BuildCreativeModeTabContentsEvent event, SubCreativeTab subTab, Supplier<? extends ItemLike> item) {
        event.accept(item);
        subTab.addDisplayItem(item.get());
    }

    public static void addInSub(BuildCreativeModeTabContentsEvent event, SubCreativeTab subTab, ItemStack item) {
        event.accept(item);
        subTab.addDisplayItem(item);
    }

    public static void addInSub(BuildCreativeModeTabContentsEvent event, SubCreativeTab subTab, Collection<ItemStack> items) {
        event.acceptAll(items);
        subTab.addDisplayItems(items);
    }

    public static void addInSub(SubCreativeTab subTab, Supplier<? extends ItemLike> item) {
        subTab.addDisplayItem(item.get());
    }

    public static void addInSub(SubCreativeTab subTab, ItemStack item) {
        subTab.addDisplayItem(item);
    }

    public static void addInSub(SubCreativeTab subTab, Collection<ItemStack> items) {
        subTab.addDisplayItems(items);
    }
}