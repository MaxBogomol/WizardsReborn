package mod.maxbogomol.wizards_reborn.common.itemgroup;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtils;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotions;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtils;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRituals;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.RegisterAlchemyPotions;
import mod.maxbogomol.wizards_reborn.common.integration.create.CreateIntegration;
import mod.maxbogomol.wizards_reborn.common.integration.farmersdelight.FarmersDelightIntegration;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.FracturedCrystalItem;
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
public class WizardsRebornItemGroup {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WizardsReborn.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WIZARDS_REBORN_GROUP = CREATIVE_MODE_TABS.register("tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(WizardsReborn.FACETED_EARTH_CRYSTAL.get()))
                    .title(Component.translatable("itemGroup.wizards_reborn_mod_tab"))
                    .withBackgroundLocation(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_item_tab.png"))
                    .withTabsImage(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/wizards_reborn_tabs.png"))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == WizardsRebornItemGroup.WIZARDS_REBORN_GROUP.getKey()) {
            event.accept(WizardsReborn.ARCANE_GOLD_INGOT);
            event.accept(WizardsReborn.ARCANE_GOLD_NUGGET);
            event.accept(WizardsReborn.RAW_ARCANE_GOLD);
            event.accept(WizardsReborn.ARCANE_GOLD_BLOCK_ITEM);
            event.accept(WizardsReborn.ARCANE_GOLD_ORE_ITEM);
            event.accept(WizardsReborn.DEEPSLATE_ARCANE_GOLD_ORE_ITEM);
            event.accept(WizardsReborn.NETHER_ARCANE_GOLD_ORE_ITEM);
            event.accept(WizardsReborn.RAW_ARCANE_GOLD_BLOCK_ITEM);

            if (CreateIntegration.isLoaded()) {
                event.accept(CreateIntegration.CRUSHED_RAW_ARCANE_GOLD);
                event.accept(CreateIntegration.ARCANE_GOLD_SHEET);
            }

            event.accept(WizardsReborn.ARCANE_GOLD_SWORD);
            event.accept(WizardsReborn.ARCANE_GOLD_PICKAXE);
            event.accept(WizardsReborn.ARCANE_GOLD_AXE);
            event.accept(WizardsReborn.ARCANE_GOLD_SHOVEL);
            event.accept(WizardsReborn.ARCANE_GOLD_HOE);
            event.accept(WizardsReborn.ARCANE_GOLD_SCYTHE);
            if (FarmersDelightIntegration.isLoaded()) {
                event.accept(FarmersDelightIntegration.ARCANE_GOLD_KNIFE);
            }

            event.accept(WizardsReborn.ARCANE_GOLD_HELMET);
            event.accept(WizardsReborn.ARCANE_GOLD_CHESTPLATE);
            event.accept(WizardsReborn.ARCANE_GOLD_LEGGINGS);
            event.accept(WizardsReborn.ARCANE_GOLD_BOOTS);

            event.accept(WizardsReborn.SARCON_INGOT);
            event.accept(WizardsReborn.SARCON_NUGGET);
            event.accept(WizardsReborn.SARCON_BLOCK_ITEM);
            if (CreateIntegration.isLoaded()) {
                event.accept(CreateIntegration.SARCON_SHEET);
            }

            event.accept(WizardsReborn.VILENIUM_INGOT);
            event.accept(WizardsReborn.VILENIUM_NUGGET);
            event.accept(WizardsReborn.VILENIUM_BLOCK_ITEM);
            if (CreateIntegration.isLoaded()) {
                event.accept(CreateIntegration.VILENIUM_SHEET);
            }

            event.accept(WizardsReborn.ARCANUM);
            event.accept(WizardsReborn.ARCANUM_DUST);
            event.accept(WizardsReborn.ARCANUM_BLOCK_ITEM);
            event.accept(WizardsReborn.ARCANUM_ORE_ITEM);
            event.accept(WizardsReborn.DEEPSLATE_ARCANUM_ORE_ITEM);
            event.accept(WizardsReborn.ARCANUM_DUST_BLOCK_ITEM);

            event.accept(WizardsReborn.ARCACITE);
            event.accept(WizardsReborn.ARCACITE_BLOCK_ITEM);

            event.accept(WizardsReborn.PRECISION_CRYSTAL);
            event.accept(WizardsReborn.PRECISION_CRYSTAL_BLOCK_ITEM);

            event.accept(WizardsReborn.NETHER_SALT);
            event.accept(WizardsReborn.NETHER_SALT_BLOCK_ITEM);
            event.accept(WizardsReborn.NETHER_SALT_ORE_ITEM);

            event.accept(WizardsReborn.ARCANE_WOOD_LOG_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_ITEM);
            event.accept(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM);
            event.accept(WizardsReborn.STRIPPED_ARCANE_WOOD_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_STAIRS_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_SLAB_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_FENCE_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_FENCE_GATE_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_DOOR_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_TRAPDOOR_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_PRESSURE_PLATE_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_BUTTON_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_SIGN_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_HANGING_SIGN_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_BOAT_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_CHEST_BOAT_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_BRANCH);
            event.accept(WizardsReborn.ARCANE_WOOD_SWORD);
            event.accept(WizardsReborn.ARCANE_WOOD_PICKAXE);
            event.accept(WizardsReborn.ARCANE_WOOD_AXE);
            event.accept(WizardsReborn.ARCANE_WOOD_SHOVEL);
            event.accept(WizardsReborn.ARCANE_WOOD_HOE);
            event.accept(WizardsReborn.ARCANE_WOOD_SCYTHE);
            if (FarmersDelightIntegration.isLoaded()) {
                event.accept(FarmersDelightIntegration.ARCANE_WOOD_KNIFE);
            }
            event.accept(WizardsReborn.ARCANE_WOOD_MORTAR);
            event.accept(WizardsReborn.ARCANE_WOOD_LEAVES_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_SAPLING_ITEM);

            event.accept(WizardsReborn.INNOCENT_WOOD_LOG_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_ITEM);
            event.accept(WizardsReborn.STRIPPED_INNOCENT_WOOD_LOG_ITEM);
            event.accept(WizardsReborn.STRIPPED_INNOCENT_WOOD_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_PLANKS_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_STAIRS_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_SLAB_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_FENCE_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_FENCE_GATE_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_DOOR_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_TRAPDOOR_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_PRESSURE_PLATE_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_BUTTON_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_SIGN_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_HANGING_SIGN_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_BOAT_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_CHEST_BOAT_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_BRANCH);
            event.accept(WizardsReborn.INNOCENT_WOOD_SWORD);
            event.accept(WizardsReborn.INNOCENT_WOOD_PICKAXE);
            event.accept(WizardsReborn.INNOCENT_WOOD_AXE);
            event.accept(WizardsReborn.INNOCENT_WOOD_SHOVEL);
            event.accept(WizardsReborn.INNOCENT_WOOD_HOE);
            event.accept(WizardsReborn.INNOCENT_WOOD_SCYTHE);
            if (FarmersDelightIntegration.isLoaded()) {
                event.accept(FarmersDelightIntegration.INNOCENT_WOOD_KNIFE);
            }
            event.accept(WizardsReborn.INNOCENT_WOOD_MORTAR);
            event.accept(WizardsReborn.INNOCENT_WOOD_LEAVES_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_SAPLING_ITEM);
            event.accept(WizardsReborn.PETALS_OF_INNOCENCE_ITEM);

            event.accept(WizardsReborn.WISESTONE_ITEM);
            event.accept(WizardsReborn.WISESTONE_STAIRS_ITEM);
            event.accept(WizardsReborn.WISESTONE_SLAB_ITEM);
            event.accept(WizardsReborn.WISESTONE_WALL_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_STAIRS_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_SLAB_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_WALL_ITEM);
            event.accept(WizardsReborn.WISESTONE_BRICKS_ITEM);
            event.accept(WizardsReborn.WISESTONE_BRICKS_STAIRS_ITEM);
            event.accept(WizardsReborn.WISESTONE_BRICKS_SLAB_ITEM);
            event.accept(WizardsReborn.WISESTONE_BRICKS_WALL_ITEM);
            event.accept(WizardsReborn.WISESTONE_TILE_ITEM);
            event.accept(WizardsReborn.WISESTONE_TILE_STAIRS_ITEM);
            event.accept(WizardsReborn.WISESTONE_TILE_SLAB_ITEM);
            event.accept(WizardsReborn.WISESTONE_TILE_WALL_ITEM);
            event.accept(WizardsReborn.CHISELED_WISESTONE_ITEM);
            event.accept(WizardsReborn.CHISELED_WISESTONE_STAIRS_ITEM);
            event.accept(WizardsReborn.CHISELED_WISESTONE_SLAB_ITEM);
            event.accept(WizardsReborn.CHISELED_WISESTONE_WALL_ITEM);
            event.accept(WizardsReborn.WISESTONE_PILLAR_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_PRESSURE_PLATE_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_BUTTON_ITEM);

            event.accept(WizardsReborn.ARCANE_LINEN_SEEDS);
            event.accept(WizardsReborn.ARCANE_LINEN_ITEM);
            event.accept(WizardsReborn.ARCANE_LINEN_HAY_ITEM);

            event.accept(WizardsReborn.MOR_ITEM);
            event.accept(WizardsReborn.MOR_BLOCK_ITEM);
            event.accept(WizardsReborn.ELDER_MOR_ITEM);
            event.accept(WizardsReborn.ELDER_MOR_BLOCK_ITEM);

            event.accept(WizardsReborn.PETALS);
            event.accept(WizardsReborn.FLOWER_FERTILIZER);
            event.accept(WizardsReborn.GROUND_BROWN_MUSHROOM);
            event.accept(WizardsReborn.GROUND_RED_MUSHROOM);
            event.accept(WizardsReborn.GROUND_CRIMSON_FUNGUS);
            event.accept(WizardsReborn.GROUND_WARPED_FUNGUS);
            event.accept(WizardsReborn.GROUND_MOR);
            event.accept(WizardsReborn.GROUND_ELDER_MOR);

            event.accept(WizardsReborn.ARCANUM_SEED);
            event.accept(WizardsReborn.EARTH_CRYSTAL_SEED);
            event.accept(WizardsReborn.WATER_CRYSTAL_SEED);
            event.accept(WizardsReborn.AIR_CRYSTAL_SEED);
            event.accept(WizardsReborn.FIRE_CRYSTAL_SEED);
            event.accept(WizardsReborn.VOID_CRYSTAL_SEED);

            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsReborn.FRACTURED_EARTH_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsReborn.FRACTURED_WATER_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsReborn.FRACTURED_AIR_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsReborn.FRACTURED_FIRE_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsReborn.FRACTURED_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FACETED_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FACETED_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FACETED_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FACETED_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FACETED_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.ADVANCED_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.ADVANCED_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.ADVANCED_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.ADVANCED_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.ADVANCED_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.MASTERFUL_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.MASTERFUL_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.MASTERFUL_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.MASTERFUL_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.MASTERFUL_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.PURE_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.PURE_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.PURE_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.PURE_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.PURE_VOID_CRYSTAL.get()));

            event.accept(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.ORANGE_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.MAGENTA_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.LIGHT_BLUE_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.YELLOW_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.LIME_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.PINK_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.GRAY_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.LIGHT_GRAY_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.CYAN_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.PURPLE_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.BLUE_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.BROWN_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.GREEN_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.RED_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.BLACK_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.RAINBOW_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.COSMIC_ARCANE_LUMOS_ITEM);

            event.accept(WizardsReborn.ARCANE_PEDESTAL_ITEM);
            event.accept(WizardsReborn.HOVERING_TOME_STAND_ITEM);
            event.accept(WizardsReborn.WISSEN_ALTAR_ITEM);
            event.accept(WizardsReborn.WISSEN_TRANSLATOR_ITEM);
            event.accept(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM);
            event.accept(WizardsReborn.ARCANE_WORKBENCH_ITEM);
            event.accept(WizardsReborn.WISSEN_CELL_ITEM);
            event.accept(WizardsReborn.JEWELER_TABLE_ITEM);
            event.accept(WizardsReborn.ALTAR_OF_DROUGHT_ITEM);
            event.accept(WizardsReborn.TOTEM_BASE_ITEM);
            event.accept(WizardsReborn.TOTEM_OF_FLAMES_ITEM);
            event.accept(WizardsReborn.EXPERIENCE_TOTEM_ITEM);
            event.accept(WizardsReborn.TOTEM_OF_EXPERIENCE_ABSORPTION_ITEM);
            event.accept(WizardsReborn.TOTEM_OF_DISENCHANT_ITEM);
            event.accept(WizardsReborn.ARCANE_ITERATOR_ITEM);

            event.accept(WizardsReborn.WISESTONE_PEDESTAL_ITEM);
            event.accept(WizardsReborn.WISESTONE_HOVERING_TOME_STAND_ITEM);
            event.accept(WizardsReborn.FLUID_PIPE_ITEM);
            event.accept(WizardsReborn.FLUID_EXTRACTOR_ITEM);
            event.accept(WizardsReborn.STEAM_PIPE_ITEM);
            event.accept(WizardsReborn.STEAM_EXTRACTOR_ITEM);
            event.accept(WizardsReborn.ALCHEMY_FURNACE_ITEM);
            event.accept(WizardsReborn.ORBITAL_FLUID_RETAINER_ITEM);
            event.accept(WizardsReborn.STEAM_THERMAL_STORAGE_ITEM);
            event.accept(WizardsReborn.ALCHEMY_MACHINE_ITEM);
            event.accept(WizardsReborn.ALCHEMY_BOILER_ITEM);
            event.accept(WizardsReborn.ARCANE_CENSER_ITEM);

            event.accept(WizardsReborn.LIGHT_EMITTER_ITEM);
            event.accept(WizardsReborn.LIGHT_TRANSFER_LENS_ITEM);
            event.accept(WizardsReborn.RUNIC_PEDESTAL_ITEM);

            event.accept(WizardsReborn.ENGRAVED_WISESTONE_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_LUNAM_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_VITA_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_SOLEM_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_MORS_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_MIRACULUM_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_TEMPUS_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_STATERA_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_ECLIPSIS_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_SICCITAS_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_SOLSTITIUM_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_FAMES_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_RENAISSANCE_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_BELLUM_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_LUX_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_KARA_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_DEGRADATIO_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_PRAEDICTIONEM_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_EVOLUTIONIS_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_TENEBRIS_ITEM);
            event.accept(WizardsReborn.ENGRAVED_WISESTONE_UNIVERSUM_ITEM);

            event.accept(WizardsReborn.INNOCENT_PEDESTAL_ITEM);
            event.accept(WizardsReborn.INNOCENT_HOVERING_TOME_STAND_ITEM);

            event.accept(WizardsReborn.ARCANE_LEVER_ITEM);
            event.accept(WizardsReborn.ARCANE_HOPPER_ITEM);
            event.accept(WizardsReborn.REDSTONE_SENSOR_ITEM);
            event.accept(WizardsReborn.WISSEN_SENSOR_ITEM);
            event.accept(WizardsReborn.COOLDOWN_SENSOR_ITEM);
            event.accept(WizardsReborn.EXPERIENCE_SENSOR_ITEM);
            event.accept(WizardsReborn.LIGHT_SENSOR_ITEM);
            event.accept(WizardsReborn.HEAT_SENSOR_ITEM);
            event.accept(WizardsReborn.FLUID_SENSOR_ITEM);
            event.accept(WizardsReborn.STEAM_SENSOR_ITEM);
            event.accept(WizardsReborn.WISSEN_ACTIVATOR_ITEM);
            event.accept(WizardsReborn.ITEM_SORTER_ITEM);

            event.accept(WizardsReborn.ARCANE_WOOD_FRAME_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_GLASS_FRAME_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_CASING_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_WISSEN_CASING_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_LIGHT_CASING_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_FLUID_CASING_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_STEAM_CASING_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_FRAME_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_GLASS_FRAME_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_CASING_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_WISSEN_CASING_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_LIGHT_CASING_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_FLUID_CASING_ITEM);
            event.accept(WizardsReborn.INNOCENT_WOOD_STEAM_CASING_ITEM);
            event.accept(WizardsReborn.WISESTONE_FRAME_ITEM);
            event.accept(WizardsReborn.WISESTONE_GLASS_FRAME_ITEM);
            event.accept(WizardsReborn.WISESTONE_CASING_ITEM);
            event.accept(WizardsReborn.WISESTONE_WISSEN_CASING_ITEM);
            event.accept(WizardsReborn.WISESTONE_LIGHT_CASING_ITEM);
            event.accept(WizardsReborn.WISESTONE_FLUID_CASING_ITEM);
            event.accept(WizardsReborn.WISESTONE_STEAM_CASING_ITEM);

            event.accept(WizardsReborn.CREATIVE_WISSEN_STORAGE_ITEM);
            event.accept(WizardsReborn.CREATIVE_LIGHT_STORAGE_ITEM);
            event.accept(WizardsReborn.CREATIVE_FLUID_STORAGE_ITEM);
            event.accept(WizardsReborn.CREATIVE_STEAM_STORAGE_ITEM);

            event.accept(WizardsReborn.ARCANE_SALT_TORCH_ITEM);
            event.accept(WizardsReborn.INNOCENT_SALT_TORCH_ITEM);
            event.accept(WizardsReborn.WISESTONE_SALT_TORCH_ITEM);
            event.accept(WizardsReborn.ARCANE_SALT_LANTERN_ITEM);
            event.accept(WizardsReborn.INNOCENT_SALT_LANTERN_ITEM);
            event.accept(WizardsReborn.WISESTONE_SALT_LANTERN_ITEM);
            event.accept(WizardsReborn.ARCANE_SALT_CAMPFIRE_ITEM);
            event.accept(WizardsReborn.INNOCENT_SALT_CAMPFIRE_ITEM);
            event.accept(WizardsReborn.WISESTONE_SALT_CAMPFIRE_ITEM);

            event.accept(WizardsReborn.ALCHEMY_GLASS_ITEM);
            event.accept(WizardsReborn.ALCHEMY_VIAL);
            event.accept(WizardsReborn.ALCHEMY_FLASK);

            event.accept(WizardsReborn.ALCHEMY_CALX);
            event.accept(WizardsReborn.NATURAL_CALX);
            event.accept(WizardsReborn.SCORCHED_CALX);
            event.accept(WizardsReborn.DISTANT_CALX);
            event.accept(WizardsReborn.ENCHANTED_CALX);

            event.accept(WizardsReborn.ALCHEMY_CALX_BLOCK_ITEM);
            event.accept(WizardsReborn.NATURAL_CALX_BLOCK_ITEM);
            event.accept(WizardsReborn.SCORCHED_CALX_BLOCK_ITEM);
            event.accept(WizardsReborn.DISTANT_CALX_BLOCK_ITEM);
            event.accept(WizardsReborn.ENCHANTED_CALX_BLOCK_ITEM);

            event.accept(WizardsReborn.ARCACITE_POLISHING_MIXTURE);

            event.accept(WizardsReborn.ARCANUM_LENS);
            event.accept(WizardsReborn.WISESTONE_PLATE);
            for (CrystalRitual ritual : CrystalRituals.getCrystalRituals()) {
                if (ritual != WizardsReborn.EMPTY_CRYSTAL_RITUAL) {
                    ItemStack stack = new ItemStack(WizardsReborn.RUNIC_WISESTONE_PLATE.get());
                    CrystalRitualUtils.setCrystalRitual(stack, ritual);
                    event.accept(stack);
                }
            }

            event.accept(WizardsReborn.ARCANE_WAND);
            event.accept(WizardsReborn.WISSEN_WAND);

            event.accept(WizardsReborn.ARCANUM_AMULET);
            event.accept(WizardsReborn.ARCANUM_RING);
            event.accept(WizardsReborn.ARCACITE_AMULET);
            event.accept(WizardsReborn.ARCACITE_RING);
            event.accept(WizardsReborn.WISSEN_KEYCHAIN);
            event.accept(WizardsReborn.WISSEN_RING);
            event.accept(WizardsReborn.LEATHER_BELT);
            event.accept(WizardsReborn.ARCANE_FORTRESS_BELT);
            event.accept(WizardsReborn.CRYSTAL_BAG);
            event.accept(WizardsReborn.ALCHEMY_BAG);

            event.accept(WizardsReborn.LEATHER_COLLAR);

            event.accept(WizardsReborn.BROWN_MUSHROOM_CAP);
            event.accept(WizardsReborn.RED_MUSHROOM_CAP);
            event.accept(WizardsReborn.CRIMSON_FUNGUS_CAP);
            event.accept(WizardsReborn.WARPED_FUNGUS_CAP);
            event.accept(WizardsReborn.MOR_CAP);
            event.accept(WizardsReborn.ELDER_MOR_CAP);

            event.accept(WizardsReborn.ARCANE_FORTRESS_HELMET);
            event.accept(WizardsReborn.ARCANE_FORTRESS_CHESTPLATE);
            event.accept(WizardsReborn.ARCANE_FORTRESS_LEGGINGS);
            event.accept(WizardsReborn.ARCANE_FORTRESS_BOOTS);

            event.accept(WizardsReborn.INVENTOR_WIZARD_HAT);
            event.accept(WizardsReborn.INVENTOR_WIZARD_COSTUME);
            event.accept(WizardsReborn.INVENTOR_WIZARD_TROUSERS);
            event.accept(WizardsReborn.INVENTOR_WIZARD_BOOTS);

            event.accept(WizardsReborn.ARCANE_WOOD_SMOKING_PIPE);
            event.accept(WizardsReborn.ARCANE_WOOD_CANE);
            event.accept(WizardsReborn.ARCANE_WOOD_BOW);

            event.accept(WizardsReborn.ARCANEMICON);
            event.accept(WizardsReborn.KNOWLEDGE_SCROLL);
            event.accept(WizardsReborn.CREATIVE_KNOWLEDGE_SCROLL);
            event.accept(WizardsReborn.CREATIVE_KKNOWLEDGE_ANCIENT_SCROLL);
            event.accept(WizardsReborn.CREATIVE_SPELL_SCROLL);
            event.accept(WizardsReborn.CREATIVE_SPELL_ANCIENT_SCROLL);

            event.accept(WizardsReborn.VIOLENCE_BANNER_PATTERN_ITEM);
            event.accept(WizardsReborn.REPRODUCTION_BANNER_PATTERN_ITEM);
            event.accept(WizardsReborn.COOPERATION_BANNER_PATTERN_ITEM);
            event.accept(WizardsReborn.HUNGER_BANNER_PATTERN_ITEM);
            event.accept(WizardsReborn.SURVIVAL_BANNER_PATTERN_ITEM);
            event.accept(WizardsReborn.ELEVATION_BANNER_PATTERN_ITEM);

            event.accept(WizardsReborn.MUSIC_DISC_ARCANUM);
            event.accept(WizardsReborn.MUSIC_DISC_MOR);
            event.accept(WizardsReborn.MUSIC_DISC_REBORN);
            event.accept(WizardsReborn.MUSIC_DISC_SHIMMER);
            event.accept(WizardsReborn.MUSIC_DISC_PANACHE);

            for (AlchemyPotion potion : AlchemyPotions.getAlchemyPotions()) {
                if (potion != RegisterAlchemyPotions.EMPTY && potion != RegisterAlchemyPotions.COMBINED) {
                    ItemStack stack = new ItemStack(WizardsReborn.ALCHEMY_VIAL_POTION.get());
                    AlchemyPotionUtils.setPotion(stack, potion);
                    event.accept(stack);
                }
            }

            for (AlchemyPotion potion : AlchemyPotions.getAlchemyPotions()) {
                if (potion != RegisterAlchemyPotions.EMPTY && potion != RegisterAlchemyPotions.COMBINED) {
                    ItemStack stack = new ItemStack(WizardsReborn.ALCHEMY_FLASK_POTION.get());
                    AlchemyPotionUtils.setPotion(stack, potion);
                    event.accept(stack);
                }
            }

            event.accept(WizardsReborn.MUNDANE_BREW_BUCKET);
            event.accept(WizardsReborn.ALCHEMY_OIL_BUCKET);
            event.accept(WizardsReborn.OIL_TEA_BUCKET);
            event.accept(WizardsReborn.WISSEN_TEA_BUCKET);
            event.accept(WizardsReborn.MUSHROOM_BREW_BUCKET);
            event.accept(WizardsReborn.HELLISH_MUSHROOM_BREW_BUCKET);
            event.accept(WizardsReborn.MOR_BREW_BUCKET);
            event.accept(WizardsReborn.FLOWER_BREW_BUCKET);
        }
    }
}