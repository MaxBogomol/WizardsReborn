package mod.maxbogomol.wizards_reborn.registry.common.item;

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
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WizardsRebornCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WizardsReborn.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WIZARDS_REBORN = CREATIVE_MODE_TABS.register("tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(WizardsRebornItems.FACETED_EARTH_CRYSTAL.get()))
                    .title(Component.translatable("creative_tab.wizards_reborn"))
                    .withLabelColor(ColorUtil.packColor(255, 55, 48, 54))
                    .withBackgroundLocation(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_item_tab.png"))
                    .withTabsImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_tabs.png"))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void addCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == WizardsRebornCreativeTabs.WIZARDS_REBORN.getKey()) {
            event.accept(WizardsRebornItems.ARCANEMICON);

            event.accept(WizardsRebornItems.ARCANE_GOLD_INGOT);
            event.accept(WizardsRebornItems.ARCANE_GOLD_NUGGET);
            event.accept(WizardsRebornItems.RAW_ARCANE_GOLD);
            event.accept(WizardsRebornItems.ARCANE_GOLD_BLOCK);
            event.accept(WizardsRebornItems.ARCANE_GOLD_ORE);
            event.accept(WizardsRebornItems.DEEPSLATE_ARCANE_GOLD_ORE);
            event.accept(WizardsRebornItems.NETHER_ARCANE_GOLD_ORE);
            event.accept(WizardsRebornItems.RAW_ARCANE_GOLD_BLOCK);
            if (WizardsRebornCreate.isLoaded()) {
                event.accept(WizardsRebornCreate.ItemsLoadedOnly.CRUSHED_RAW_ARCANE_GOLD);
                event.accept(WizardsRebornCreate.ItemsLoadedOnly.ARCANE_GOLD_SHEET);
            }
            if (WizardsRebornMalum.isLoaded()) {
                event.accept(WizardsRebornMalum.ItemsLoadedOnly.CRACKED_ARCANE_GOLD_IMPETUS);
                event.accept(WizardsRebornMalum.ItemsLoadedOnly.ARCANE_GOLD_IMPETUS);
                event.accept(WizardsRebornMalum.ItemsLoadedOnly.ARCANE_GOLD_NODE);
            }

            event.accept(WizardsRebornItems.ARCANE_GOLD_SWORD);
            event.accept(WizardsRebornItems.ARCANE_GOLD_PICKAXE);
            event.accept(WizardsRebornItems.ARCANE_GOLD_AXE);
            event.accept(WizardsRebornItems.ARCANE_GOLD_SHOVEL);
            event.accept(WizardsRebornItems.ARCANE_GOLD_HOE);
            event.accept(WizardsRebornItems.ARCANE_GOLD_SCYTHE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                event.accept(WizardsRebornFarmersDelight.ItemsLoadedOnly.ARCANE_GOLD_KNIFE);
            }
            event.accept(WizardsRebornItems.ARCANE_GOLD_HELMET);
            event.accept(WizardsRebornItems.ARCANE_GOLD_CHESTPLATE);
            event.accept(WizardsRebornItems.ARCANE_GOLD_LEGGINGS);
            event.accept(WizardsRebornItems.ARCANE_GOLD_BOOTS);

            event.accept(WizardsRebornItems.SARCON_INGOT);
            event.accept(WizardsRebornItems.SARCON_NUGGET);
            event.accept(WizardsRebornItems.SARCON_BLOCK);
            if (WizardsRebornCreate.isLoaded()) {
                event.accept(WizardsRebornCreate.ItemsLoadedOnly.SARCON_SHEET);
            }
            if (WizardsRebornMalum.isLoaded()) {
                event.accept(WizardsRebornMalum.ItemsLoadedOnly.CRACKED_SARCON_IMPETUS);
                event.accept(WizardsRebornMalum.ItemsLoadedOnly.SARCON_IMPETUS);
                event.accept(WizardsRebornMalum.ItemsLoadedOnly.SARCON_NODE);
            }

            event.accept(WizardsRebornItems.VILENIUM_INGOT);
            event.accept(WizardsRebornItems.VILENIUM_NUGGET);
            event.accept(WizardsRebornItems.VILENIUM_BLOCK);
            if (WizardsRebornCreate.isLoaded()) {
                event.accept(WizardsRebornCreate.ItemsLoadedOnly.VILENIUM_SHEET);
            }
            if (WizardsRebornMalum.isLoaded()) {
                event.accept(WizardsRebornMalum.ItemsLoadedOnly.CRACKED_VILENIUM_IMPETUS);
                event.accept(WizardsRebornMalum.ItemsLoadedOnly.VILENIUM_IMPETUS);
                event.accept(WizardsRebornMalum.ItemsLoadedOnly.VILENIUM_NODE);
            }

            event.accept(WizardsRebornItems.ARCANUM);
            event.accept(WizardsRebornItems.ARCANUM_DUST);
            event.accept(WizardsRebornItems.ARCANUM_BLOCK);
            event.accept(WizardsRebornItems.ARCANUM_ORE);
            event.accept(WizardsRebornItems.DEEPSLATE_ARCANUM_ORE);
            event.accept(WizardsRebornItems.ARCANUM_DUST_BLOCK);

            event.accept(WizardsRebornItems.ARCACITE);
            event.accept(WizardsRebornItems.ARCACITE_BLOCK);

            event.accept(WizardsRebornItems.PRECISION_CRYSTAL);
            event.accept(WizardsRebornItems.PRECISION_CRYSTAL_BLOCK);

            event.accept(WizardsRebornItems.NETHER_SALT);
            event.accept(WizardsRebornItems.NETHER_SALT_BLOCK);
            event.accept(WizardsRebornItems.NETHER_SALT_ORE);

            event.accept(WizardsRebornItems.ARCANE_WOOD_LOG);
            event.accept(WizardsRebornItems.ARCANE_WOOD);
            event.accept(WizardsRebornItems.STRIPPED_ARCANE_WOOD_LOG);
            event.accept(WizardsRebornItems.STRIPPED_ARCANE_WOOD);
            event.accept(WizardsRebornItems.ARCANE_WOOD_PLANKS);
            event.accept(WizardsRebornItems.ARCANE_WOOD_STAIRS);
            event.accept(WizardsRebornItems.ARCANE_WOOD_SLAB);
            event.accept(WizardsRebornItems.ARCANE_WOOD_BAULK);
            event.accept(WizardsRebornItems.ARCANE_WOOD_CROSS_BAULK);
            event.accept(WizardsRebornItems.STRIPPED_ARCANE_WOOD_BAULK);
            event.accept(WizardsRebornItems.STRIPPED_ARCANE_WOOD_CROSS_BAULK);
            event.accept(WizardsRebornItems.ARCANE_WOOD_PLANKS_BAULK);
            event.accept(WizardsRebornItems.ARCANE_WOOD_PLANKS_CROSS_BAULK);
            event.accept(WizardsRebornItems.ARCANE_WOOD_FENCE);
            event.accept(WizardsRebornItems.ARCANE_WOOD_FENCE_GATE);
            event.accept(WizardsRebornItems.ARCANE_WOOD_DOOR);
            event.accept(WizardsRebornItems.ARCANE_WOOD_TRAPDOOR);
            event.accept(WizardsRebornItems.ARCANE_WOOD_PRESSURE_PLATE);
            event.accept(WizardsRebornItems.ARCANE_WOOD_BUTTON);
            event.accept(WizardsRebornItems.ARCANE_WOOD_SIGN);
            event.accept(WizardsRebornItems.ARCANE_WOOD_HANGING_SIGN);
            event.accept(WizardsRebornItems.ARCANE_WOOD_BOAT);
            event.accept(WizardsRebornItems.ARCANE_WOOD_CHEST_BOAT);
            event.accept(WizardsRebornItems.ARCANE_WOOD_BRANCH);
            event.accept(WizardsRebornItems.ARCANE_WOOD_SWORD);
            event.accept(WizardsRebornItems.ARCANE_WOOD_PICKAXE);
            event.accept(WizardsRebornItems.ARCANE_WOOD_AXE);
            event.accept(WizardsRebornItems.ARCANE_WOOD_SHOVEL);
            event.accept(WizardsRebornItems.ARCANE_WOOD_HOE);
            event.accept(WizardsRebornItems.ARCANE_WOOD_SCYTHE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                event.accept(WizardsRebornFarmersDelight.ItemsLoadedOnly.ARCANE_WOOD_KNIFE);
            }
            event.accept(WizardsRebornItems.ARCANE_WOOD_MORTAR);
            event.accept(WizardsRebornItems.ARCANE_WOOD_LEAVES);
            event.accept(WizardsRebornItems.ARCANE_WOOD_SAPLING);

            event.accept(WizardsRebornItems.INNOCENT_WOOD_LOG);
            event.accept(WizardsRebornItems.INNOCENT_WOOD);
            event.accept(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_LOG);
            event.accept(WizardsRebornItems.STRIPPED_INNOCENT_WOOD);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_PLANKS);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_STAIRS);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_SLAB);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_BAULK);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_CROSS_BAULK);
            event.accept(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_BAULK);
            event.accept(WizardsRebornItems.STRIPPED_INNOCENT_WOOD_CROSS_BAULK);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_PLANKS_BAULK);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_PLANKS_CROSS_BAULK);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_FENCE);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_FENCE_GATE);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_DOOR);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_TRAPDOOR);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_PRESSURE_PLATE);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_BUTTON);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_SIGN);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_HANGING_SIGN);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_BOAT);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_CHEST_BOAT);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_BRANCH);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_SWORD);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_PICKAXE);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_AXE);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_SHOVEL);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_HOE);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_SCYTHE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                event.accept(WizardsRebornFarmersDelight.ItemsLoadedOnly.INNOCENT_WOOD_KNIFE);
            }
            event.accept(WizardsRebornItems.INNOCENT_WOOD_MORTAR);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_LEAVES);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_SAPLING);
            event.accept(WizardsRebornItems.PETALS_OF_INNOCENCE);

            event.accept(WizardsRebornItems.WISESTONE);
            event.accept(WizardsRebornItems.WISESTONE_STAIRS);
            event.accept(WizardsRebornItems.WISESTONE_SLAB);
            event.accept(WizardsRebornItems.WISESTONE_WALL);
            event.accept(WizardsRebornItems.POLISHED_WISESTONE);
            event.accept(WizardsRebornItems.POLISHED_WISESTONE_STAIRS);
            event.accept(WizardsRebornItems.POLISHED_WISESTONE_SLAB);
            event.accept(WizardsRebornItems.POLISHED_WISESTONE_WALL);
            event.accept(WizardsRebornItems.WISESTONE_BRICKS);
            event.accept(WizardsRebornItems.WISESTONE_BRICKS_STAIRS);
            event.accept(WizardsRebornItems.WISESTONE_BRICKS_SLAB);
            event.accept(WizardsRebornItems.WISESTONE_BRICKS_WALL);
            event.accept(WizardsRebornItems.WISESTONE_TILE);
            event.accept(WizardsRebornItems.WISESTONE_TILE_STAIRS);
            event.accept(WizardsRebornItems.WISESTONE_TILE_SLAB);
            event.accept(WizardsRebornItems.WISESTONE_TILE_WALL);
            event.accept(WizardsRebornItems.CHISELED_WISESTONE);
            event.accept(WizardsRebornItems.CHISELED_WISESTONE_STAIRS);
            event.accept(WizardsRebornItems.CHISELED_WISESTONE_SLAB);
            event.accept(WizardsRebornItems.CHISELED_WISESTONE_WALL);
            event.accept(WizardsRebornItems.WISESTONE_PILLAR);
            event.accept(WizardsRebornItems.POLISHED_WISESTONE_PRESSURE_PLATE);
            event.accept(WizardsRebornItems.POLISHED_WISESTONE_BUTTON);

            event.accept(WizardsRebornItems.ARCANE_LINEN_SEEDS);
            event.accept(WizardsRebornItems.ARCANE_LINEN);
            event.accept(WizardsRebornItems.ARCANE_LINEN_HAY);

            event.accept(WizardsRebornItems.MOR);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                event.accept(WizardsRebornFarmersDelight.ItemsLoadedOnly.MOR_COLONY);
            }
            event.accept(WizardsRebornItems.MOR_BLOCK);
            event.accept(WizardsRebornItems.ELDER_MOR);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                event.accept(WizardsRebornFarmersDelight.ItemsLoadedOnly.ELDER_MOR_COLONY);
            }
            event.accept(WizardsRebornItems.ELDER_MOR_BLOCK);

            event.accept(WizardsRebornItems.PITCHER_DEW);
            event.accept(WizardsRebornItems.PITCHER_TURNIP);
            event.accept(WizardsRebornItems.PITCHER_TURNIP_BLOCK);
            event.accept(WizardsRebornItems.SHINY_CLOVER_SEED);
            event.accept(WizardsRebornItems.SHINY_CLOVER);
            event.accept(WizardsRebornItems.UNDERGROUND_GRAPE_VINE);
            event.accept(WizardsRebornItems.UNDERGROUND_GRAPE);

            event.accept(WizardsRebornItems.CORK_BAMBOO_SEED);
            event.accept(WizardsRebornItems.CORK_BAMBOO);
            event.accept(WizardsRebornItems.CORK_BAMBOO_BLOCK);
            event.accept(WizardsRebornItems.CORK_BAMBOO_PLANKS);
            event.accept(WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS);
            event.accept(WizardsRebornItems.CORK_BAMBOO_STAIRS);
            event.accept(WizardsRebornItems.CORK_BAMBOO_CHISELED_STAIRS);
            event.accept(WizardsRebornItems.CORK_BAMBOO_SLAB);
            event.accept(WizardsRebornItems.CORK_BAMBOO_CHISELED_SLAB);
            event.accept(WizardsRebornItems.CORK_BAMBOO_BAULK);
            event.accept(WizardsRebornItems.CORK_BAMBOO_CROSS_BAULK);
            event.accept(WizardsRebornItems.CORK_BAMBOO_PLANKS_BAULK);
            event.accept(WizardsRebornItems.CORK_BAMBOO_PLANKS_CROSS_BAULK);
            event.accept(WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS_BAULK);
            event.accept(WizardsRebornItems.CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK);
            event.accept(WizardsRebornItems.CORK_BAMBOO_FENCE);
            event.accept(WizardsRebornItems.CORK_BAMBOO_FENCE_GATE);
            event.accept(WizardsRebornItems.CORK_BAMBOO_DOOR);
            event.accept(WizardsRebornItems.CORK_BAMBOO_TRAPDOOR);
            event.accept(WizardsRebornItems.CORK_BAMBOO_PRESSURE_PLATE);
            event.accept(WizardsRebornItems.CORK_BAMBOO_BUTTON);
            event.accept(WizardsRebornItems.CORK_BAMBOO_SIGN);
            event.accept(WizardsRebornItems.CORK_BAMBOO_HANGING_SIGN);
            event.accept(WizardsRebornItems.CORK_BAMBOO_RAFT);
            event.accept(WizardsRebornItems.CORK_BAMBOO_CHEST_RAFT);

            event.accept(WizardsRebornItems.PETALS);
            event.accept(WizardsRebornItems.FLOWER_FERTILIZER);
            event.accept(WizardsRebornItems.BUNCH_OF_THINGS);
            event.accept(WizardsRebornItems.GROUND_BROWN_MUSHROOM);
            event.accept(WizardsRebornItems.GROUND_RED_MUSHROOM);
            event.accept(WizardsRebornItems.GROUND_CRIMSON_FUNGUS);
            event.accept(WizardsRebornItems.GROUND_WARPED_FUNGUS);
            event.accept(WizardsRebornItems.GROUND_MOR);
            event.accept(WizardsRebornItems.GROUND_ELDER_MOR);

            event.accept(WizardsRebornItems.ARCANUM_SEED);
            event.accept(WizardsRebornItems.EARTH_CRYSTAL_SEED);
            event.accept(WizardsRebornItems.WATER_CRYSTAL_SEED);
            event.accept(WizardsRebornItems.AIR_CRYSTAL_SEED);
            event.accept(WizardsRebornItems.FIRE_CRYSTAL_SEED);
            event.accept(WizardsRebornItems.VOID_CRYSTAL_SEED);

            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsRebornItems.FRACTURED_EARTH_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsRebornItems.FRACTURED_WATER_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsRebornItems.FRACTURED_AIR_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsRebornItems.FRACTURED_FIRE_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsRebornItems.FRACTURED_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.FACETED_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.FACETED_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.FACETED_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.FACETED_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.FACETED_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.ADVANCED_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.ADVANCED_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.ADVANCED_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.ADVANCED_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.ADVANCED_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.MASTERFUL_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.MASTERFUL_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.MASTERFUL_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.MASTERFUL_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.MASTERFUL_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.PURE_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.PURE_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.PURE_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.PURE_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsRebornItems.PURE_VOID_CRYSTAL.get()));

            event.accept(WizardsRebornItems.WHITE_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.LIGHT_GRAY_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.GRAY_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.BLACK_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.BROWN_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.RED_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.ORANGE_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.YELLOW_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.LIME_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.GREEN_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.CYAN_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.LIGHT_BLUE_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.BLUE_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.PURPLE_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.MAGENTA_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.PINK_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.RAINBOW_ARCANE_LUMOS);
            event.accept(WizardsRebornItems.COSMIC_ARCANE_LUMOS);

            event.accept(WizardsRebornItems.ARCANE_PEDESTAL);
            event.accept(WizardsRebornItems.WISSEN_ALTAR);
            event.accept(WizardsRebornItems.WISSEN_TRANSLATOR);
            event.accept(WizardsRebornItems.WISSEN_CRYSTALLIZER);
            event.accept(WizardsRebornItems.ARCANE_WORKBENCH);
            event.accept(WizardsRebornItems.WISSEN_CELL);
            event.accept(WizardsRebornItems.JEWELER_TABLE);
            event.accept(WizardsRebornItems.ALTAR_OF_DROUGHT);
            event.accept(WizardsRebornItems.TOTEM_BASE);
            event.accept(WizardsRebornItems.TOTEM_OF_FLAMES);
            event.accept(WizardsRebornItems.EXPERIENCE_TOTEM);
            event.accept(WizardsRebornItems.TOTEM_OF_EXPERIENCE_ABSORPTION);
            event.accept(WizardsRebornItems.TOTEM_OF_DISENCHANT);
            event.accept(WizardsRebornItems.ARCANE_ITERATOR);

            event.accept(WizardsRebornItems.WISESTONE_PEDESTAL);
            event.accept(WizardsRebornItems.FLUID_PIPE);
            event.accept(WizardsRebornItems.FLUID_EXTRACTOR);
            event.accept(WizardsRebornItems.STEAM_PIPE);
            event.accept(WizardsRebornItems.STEAM_EXTRACTOR);
            event.accept(WizardsRebornItems.ALCHEMY_FURNACE);
            event.accept(WizardsRebornItems.ORBITAL_FLUID_RETAINER);
            event.accept(WizardsRebornItems.STEAM_THERMAL_STORAGE);
            event.accept(WizardsRebornItems.ALCHEMY_MACHINE);
            event.accept(WizardsRebornItems.ALCHEMY_BOILER);
            event.accept(WizardsRebornItems.ARCANE_CENSER);

            event.accept(WizardsRebornItems.CORK_BAMBOO_PEDESTAL);

            event.accept(WizardsRebornItems.LIGHT_EMITTER);
            event.accept(WizardsRebornItems.LIGHT_TRANSFER_LENS);
            event.accept(WizardsRebornItems.RUNIC_PEDESTAL);

            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_LUNAM);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_VITA);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_SOLEM);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_MORS);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_MIRACULUM);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_TEMPUS);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_STATERA);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_ECLIPSIS);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_SICCITAS);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_SOLSTITIUM);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_FAMES);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_RENAISSANCE);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_BELLUM);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_LUX);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_KARA);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_DEGRADATIO);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_PRAEDICTIONEM);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_EVOLUTIONIS);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_TENEBRIS);
            event.accept(WizardsRebornItems.ENGRAVED_WISESTONE_UNIVERSUM);

            event.accept(WizardsRebornItems.INNOCENT_PEDESTAL);

            event.accept(WizardsRebornItems.ARCANE_LEVER);
            event.accept(WizardsRebornItems.ARCANE_HOPPER);
            event.accept(WizardsRebornItems.REDSTONE_SENSOR);
            event.accept(WizardsRebornItems.WISSEN_SENSOR);
            event.accept(WizardsRebornItems.COOLDOWN_SENSOR);
            event.accept(WizardsRebornItems.EXPERIENCE_SENSOR);
            event.accept(WizardsRebornItems.LIGHT_SENSOR);
            event.accept(WizardsRebornItems.HEAT_SENSOR);
            event.accept(WizardsRebornItems.FLUID_SENSOR);
            event.accept(WizardsRebornItems.STEAM_SENSOR);
            event.accept(WizardsRebornItems.WISSEN_ACTIVATOR);
            event.accept(WizardsRebornItems.ITEM_SORTER);

            event.accept(WizardsRebornItems.ARCANE_WOOD_FRAME);
            event.accept(WizardsRebornItems.ARCANE_WOOD_GLASS_FRAME);
            event.accept(WizardsRebornItems.ARCANE_WOOD_CASING);
            event.accept(WizardsRebornItems.ARCANE_WOOD_WISSEN_CASING);
            event.accept(WizardsRebornItems.ARCANE_WOOD_LIGHT_CASING);
            event.accept(WizardsRebornItems.ARCANE_WOOD_FLUID_CASING);
            event.accept(WizardsRebornItems.ARCANE_WOOD_STEAM_CASING);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_FRAME);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_GLASS_FRAME);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_CASING);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_WISSEN_CASING);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_LIGHT_CASING);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_FLUID_CASING);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_STEAM_CASING);
            event.accept(WizardsRebornItems.CORK_BAMBOO_FRAME);
            event.accept(WizardsRebornItems.CORK_BAMBOO_GLASS_FRAME);
            event.accept(WizardsRebornItems.CORK_BAMBOO_CASING);
            event.accept(WizardsRebornItems.CORK_BAMBOO_WISSEN_CASING);
            event.accept(WizardsRebornItems.CORK_BAMBOO_LIGHT_CASING);
            event.accept(WizardsRebornItems.CORK_BAMBOO_FLUID_CASING);
            event.accept(WizardsRebornItems.CORK_BAMBOO_STEAM_CASING);
            event.accept(WizardsRebornItems.WISESTONE_FRAME);
            event.accept(WizardsRebornItems.WISESTONE_GLASS_FRAME);
            event.accept(WizardsRebornItems.WISESTONE_CASING);
            event.accept(WizardsRebornItems.WISESTONE_WISSEN_CASING);
            event.accept(WizardsRebornItems.WISESTONE_LIGHT_CASING);
            event.accept(WizardsRebornItems.WISESTONE_FLUID_CASING);
            event.accept(WizardsRebornItems.WISESTONE_STEAM_CASING);

            event.accept(WizardsRebornItems.CREATIVE_WISSEN_STORAGE);
            event.accept(WizardsRebornItems.CREATIVE_LIGHT_STORAGE);
            event.accept(WizardsRebornItems.CREATIVE_FLUID_STORAGE);
            event.accept(WizardsRebornItems.CREATIVE_STEAM_STORAGE);

            event.accept(WizardsRebornItems.GILDED_ARCANE_WOOD_PLANKS);
            event.accept(WizardsRebornItems.GILDED_INNOCENT_WOOD_PLANKS);
            event.accept(WizardsRebornItems.GILDED_CORK_BAMBOO_PLANKS);
            event.accept(WizardsRebornItems.GILDED_CORK_BAMBOO_CHISELED_PLANKS);
            event.accept(WizardsRebornItems.GILDED_POLISHED_WISESTONE);

            event.accept(WizardsRebornItems.ARCANE_SALT_TORCH);
            event.accept(WizardsRebornItems.ARCANE_SALT_LANTERN);
            event.accept(WizardsRebornItems.ARCANE_SALT_CAMPFIRE);
            event.accept(WizardsRebornItems.INNOCENT_SALT_TORCH);
            event.accept(WizardsRebornItems.INNOCENT_SALT_LANTERN);
            event.accept(WizardsRebornItems.INNOCENT_SALT_CAMPFIRE);
            event.accept(WizardsRebornItems.CORK_BAMBOO_SALT_TORCH);
            event.accept(WizardsRebornItems.CORK_BAMBOO_SALT_LANTERN);
            event.accept(WizardsRebornItems.CORK_BAMBOO_SALT_CAMPFIRE);
            event.accept(WizardsRebornItems.WISESTONE_SALT_TORCH);
            event.accept(WizardsRebornItems.WISESTONE_SALT_LANTERN);
            event.accept(WizardsRebornItems.WISESTONE_SALT_CAMPFIRE);

            event.accept(WizardsRebornItems.ALCHEMY_GLASS);
            event.accept(WizardsRebornItems.ALCHEMY_VIAL);
            event.accept(WizardsRebornItems.ALCHEMY_FLASK);
            event.accept(WizardsRebornItems.ALCHEMY_BOTTLE);

            event.accept(WizardsRebornItems.WHITE_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.LIGHT_GRAY_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.GRAY_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.BLACK_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.BROWN_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.RED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.ORANGE_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.YELLOW_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.LIME_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.GREEN_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.CYAN_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.LIGHT_BLUE_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.BLUE_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.PURPLE_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.MAGENTA_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.PINK_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.RAINBOW_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.COSMIC_LUMINAL_GLASS);

            event.accept(WizardsRebornItems.WHITE_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.LIGHT_GRAY_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.GRAY_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.BLACK_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.BROWN_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.RED_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.ORANGE_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.YELLOW_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.LIME_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.GREEN_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.CYAN_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.LIGHT_BLUE_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.BLUE_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.PURPLE_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.MAGENTA_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.PINK_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.RAINBOW_FRAMED_LUMINAL_GLASS);
            event.accept(WizardsRebornItems.COSMIC_FRAMED_LUMINAL_GLASS);

            event.accept(WizardsRebornItems.ALCHEMY_CALX);
            event.accept(WizardsRebornItems.NATURAL_CALX);
            event.accept(WizardsRebornItems.SCORCHED_CALX);
            event.accept(WizardsRebornItems.DISTANT_CALX);
            event.accept(WizardsRebornItems.ENCHANTED_CALX);

            event.accept(WizardsRebornItems.ALCHEMY_CALX_BLOCK);
            event.accept(WizardsRebornItems.NATURAL_CALX_BLOCK);
            event.accept(WizardsRebornItems.SCORCHED_CALX_BLOCK);
            event.accept(WizardsRebornItems.DISTANT_CALX_BLOCK);
            event.accept(WizardsRebornItems.ENCHANTED_CALX_BLOCK);

            event.accept(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE);
            event.accept(WizardsRebornItems.ARCACITE_POLISHING_MIXTURE_BLOCK);

            event.accept(WizardsRebornItems.SNIFFALO_EGG);

            event.accept(WizardsRebornItems.ARCANUM_LENS);
            event.accept(WizardsRebornItems.WISESTONE_PLATE);
            for (CrystalRitual ritual : CrystalRitualHandler.getCrystalRituals()) {
                if (ritual != WizardsRebornCrystalRituals.EMPTY) {
                    ItemStack stack = new ItemStack(WizardsRebornItems.RUNIC_WISESTONE_PLATE.get());
                    CrystalRitualUtil.setCrystalRitual(stack, ritual);
                    event.accept(stack);
                }
            }

            event.accept(WizardsRebornItems.ARCANUM_AMULET);
            event.accept(WizardsRebornItems.ARCANUM_RING);
            event.accept(WizardsRebornItems.ARCACITE_AMULET);
            event.accept(WizardsRebornItems.ARCACITE_RING);
            event.accept(WizardsRebornItems.WISSEN_KEYCHAIN);
            event.accept(WizardsRebornItems.WISSEN_RING);
            event.accept(WizardsRebornItems.CREATIVE_WISSEN_KEYCHAIN);
            event.accept(WizardsRebornItems.LEATHER_BELT);
            event.accept(WizardsRebornItems.ARCANE_FORTRESS_BELT);
            event.accept(WizardsRebornItems.INVENTOR_WIZARD_BELT);
            event.accept(WizardsRebornItems.CRYSTAL_BAG);
            event.accept(WizardsRebornItems.ALCHEMY_BAG);

            event.accept(WizardsRebornItems.LEATHER_COLLAR);

            event.accept(WizardsRebornItems.BROWN_MUSHROOM_CAP);
            event.accept(WizardsRebornItems.RED_MUSHROOM_CAP);
            event.accept(WizardsRebornItems.CRIMSON_FUNGUS_CAP);
            event.accept(WizardsRebornItems.WARPED_FUNGUS_CAP);
            event.accept(WizardsRebornItems.MOR_CAP);
            event.accept(WizardsRebornItems.ELDER_MOR_CAP);

            event.accept(WizardsRebornItems.ARCANE_FORTRESS_HELMET);
            event.accept(WizardsRebornItems.ARCANE_FORTRESS_CHESTPLATE);
            event.accept(WizardsRebornItems.ARCANE_FORTRESS_LEGGINGS);
            event.accept(WizardsRebornItems.ARCANE_FORTRESS_BOOTS);

            event.accept(WizardsRebornItems.INVENTOR_WIZARD_HAT);
            event.accept(WizardsRebornItems.INVENTOR_WIZARD_COSTUME);
            event.accept(WizardsRebornItems.INVENTOR_WIZARD_TROUSERS);
            event.accept(WizardsRebornItems.INVENTOR_WIZARD_BOOTS);

            event.accept(WizardsRebornItems.ARCANE_WAND);
            event.accept(WizardsRebornItems.WISSEN_WAND);
            event.accept(WizardsRebornItems.BLAZING_WAND);

            event.accept(WizardsRebornItems.ARCANE_WOOD_SMOKING_PIPE);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_SMOKING_PIPE);
            event.accept(WizardsRebornItems.BAMBOO_SMOKING_PIPE);
            event.accept(WizardsRebornItems.CORK_BAMBOO_SMOKING_PIPE);
            
            event.accept(WizardsRebornItems.ARCANE_WOOD_CANE);
            event.accept(WizardsRebornItems.ARCANE_WOOD_BOW);

            event.accept(WizardsRebornItems.WHITE_CARGO_CARPET);
            event.accept(WizardsRebornItems.LIGHT_GRAY_CARGO_CARPET);
            event.accept(WizardsRebornItems.GRAY_CARGO_CARPET);
            event.accept(WizardsRebornItems.BLACK_CARGO_CARPET);
            event.accept(WizardsRebornItems.BROWN_CARGO_CARPET);
            event.accept(WizardsRebornItems.RED_CARGO_CARPET);
            event.accept(WizardsRebornItems.ORANGE_CARGO_CARPET);
            event.accept(WizardsRebornItems.YELLOW_CARGO_CARPET);
            event.accept(WizardsRebornItems.LIME_CARGO_CARPET);
            event.accept(WizardsRebornItems.GREEN_CARGO_CARPET);
            event.accept(WizardsRebornItems.CYAN_CARGO_CARPET);
            event.accept(WizardsRebornItems.LIGHT_BLUE_CARGO_CARPET);
            event.accept(WizardsRebornItems.BLUE_CARGO_CARPET);
            event.accept(WizardsRebornItems.PURPLE_CARGO_CARPET);
            event.accept(WizardsRebornItems.MAGENTA_CARGO_CARPET);
            event.accept(WizardsRebornItems.PINK_CARGO_CARPET);
            event.accept(WizardsRebornItems.RAINBOW_CARGO_CARPET);

            event.accept(WizardsRebornItems.MOR_PIE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                event.accept(WizardsRebornFarmersDelight.ItemsLoadedOnly.MOR_PIE_SLICE);
            }
            event.accept(WizardsRebornItems.ELDER_MOR_PIE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                event.accept(WizardsRebornFarmersDelight.ItemsLoadedOnly.ELDER_MOR_PIE_SLICE);
            }
            event.accept(WizardsRebornItems.PITCHER_TURNIP_PIE);
            if (WizardsRebornFarmersDelight.isLoaded()) {
                event.accept(WizardsRebornFarmersDelight.ItemsLoadedOnly.PITCHER_TURNIP_PIE_SLICE);
            }

            event.accept(WizardsRebornItems.KNOWLEDGE_SCROLL);
            event.accept(WizardsRebornItems.CREATIVE_KNOWLEDGE_SCROLL);
            event.accept(WizardsRebornItems.CREATIVE_KNOWLEDGE_ANCIENT_SCROLL);
            event.accept(WizardsRebornItems.CREATIVE_SPELL_SCROLL);
            event.accept(WizardsRebornItems.CREATIVE_SPELL_ANCIENT_SCROLL);

            event.accept(WizardsRebornItems.VIOLENCE_BANNER_PATTERN);
            event.accept(WizardsRebornItems.REPRODUCTION_BANNER_PATTERN);
            event.accept(WizardsRebornItems.COOPERATION_BANNER_PATTERN);
            event.accept(WizardsRebornItems.HUNGER_BANNER_PATTERN);
            event.accept(WizardsRebornItems.SURVIVAL_BANNER_PATTERN);
            event.accept(WizardsRebornItems.ASCENSION_BANNER_PATTERN);

            event.accept(WizardsRebornItems.MUSIC_DISC_ARCANUM);
            event.accept(WizardsRebornItems.MUSIC_DISC_MOR);
            event.accept(WizardsRebornItems.MUSIC_DISC_REBORN);
            event.accept(WizardsRebornItems.MUSIC_DISC_SHIMMER);
            event.accept(WizardsRebornItems.MUSIC_DISC_CAPITALISM);
            event.accept(WizardsRebornItems.MUSIC_DISC_PANACHE);
            event.accept(WizardsRebornItems.MUSIC_DISC_DISCO);

            event.accept(WizardsRebornItems.ARCANE_WOOD_TRIM);
            event.accept(WizardsRebornItems.WISESTONE_TRIM);
            event.accept(WizardsRebornItems.INNOCENT_WOOD_TRIM);
            event.accept(WizardsRebornItems.TOP_HAT_TRIM);
            event.accept(WizardsRebornItems.MAGNIFICENT_MAID_TRIM);
            event.accept(WizardsRebornItems.SUMMER_LOVE_TRIM);
            event.accept(WizardsRebornItems.SOUL_HUNTER_TRIM);
            event.accept(WizardsRebornItems.IMPLOSION_TRIM);
            event.accept(WizardsRebornItems.PHANTOM_INK_TRIM);

            for (ArcaneEnchantment enchantment : ArcaneEnchantmentHandler.getArcaneEnchantments()) {
                for (int i = 0; i < enchantment.getMaxLevel(); i++) {
                    ItemStack stack = new ItemStack(WizardsRebornItems.ARCANE_ENCHANTED_BOOK.get());
                    ArcaneEnchantmentUtil.addArcaneEnchantment(stack, enchantment, i + 1);
                    event.accept(stack);
                }
            }

            for (AlchemyPotion potion : AlchemyPotionHandler.getAlchemyPotions()) {
                if (potion != WizardsRebornAlchemyPotions.EMPTY && potion != WizardsRebornAlchemyPotions.COMBINED) {
                    ItemStack stack = new ItemStack(WizardsRebornItems.ALCHEMY_VIAL_POTION.get());
                    AlchemyPotionUtil.setPotion(stack, potion);
                    event.accept(stack);
                }
            }

            for (AlchemyPotion potion : AlchemyPotionHandler.getAlchemyPotions()) {
                if (potion != WizardsRebornAlchemyPotions.EMPTY && potion != WizardsRebornAlchemyPotions.COMBINED) {
                    ItemStack stack = new ItemStack(WizardsRebornItems.ALCHEMY_FLASK_POTION.get());
                    AlchemyPotionUtil.setPotion(stack, potion);
                    event.accept(stack);
                }
            }

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

            event.accept(WizardsRebornItems.MUNDANE_BREW_BUCKET);
            event.accept(WizardsRebornItems.ALCHEMY_OIL_BUCKET);
            event.accept(WizardsRebornItems.OIL_TEA_BUCKET);
            event.accept(WizardsRebornItems.WISSEN_TEA_BUCKET);
            event.accept(WizardsRebornItems.MILK_TEA_BUCKET);
            event.accept(WizardsRebornItems.MUSHROOM_BREW_BUCKET);
            event.accept(WizardsRebornItems.HELLISH_MUSHROOM_BREW_BUCKET);
            event.accept(WizardsRebornItems.MOR_BREW_BUCKET);
            event.accept(WizardsRebornItems.FLOWER_BREW_BUCKET);

            event.accept(WizardsRebornItems.SNIFFALO_SPAWN_EGG);
        }
    }
}