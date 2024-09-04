package mod.maxbogomol.wizards_reborn.registry.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.render.block.*;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_boiler.AlchemyBoilerBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_furnace.AlchemyFurnaceBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.altar_of_drought.AltarOfDroughtBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.arcane_censer.ArcaneCenserBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.arcane_hopper.ArcaneHopperBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.arcane_iterator.ArcaneIteratorBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.arcane_pedestal.ArcanePedestalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.arcane_workbench.ArcaneWorkbenchBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.arcanum_growth.ArcanumGrowthBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.baulk.CrossBaulkBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.casing.fluid.FluidCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.casing.light.LightCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.casing.steam.SteamCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.casing.wissen.WissenCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.creative.fluid_storage.CreativeFluidStorageBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.creative.light_storage.CreativeLightStorageBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.creative.steam_storage.CreativeSteamStorageBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.creative.wissen_storage.CreativeWissenStorageBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.crystal_growth.CrystalGrowthBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.engraved_wisestone.EngravedWisestoneBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.fluid_extractor.FluidExtractorBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.fluid_pipe.FluidPipeBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.hovering_tome_stand.HoveringTomeStandBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.jeweler_table.JewelerTableBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.light_emitter.LightEmitterBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.light_transfer_lens.LightTransferLensBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.orbital_fluid_retainer.OrbitalFluidRetainerBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.runic_pedestal.RunicPedestalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.salt_campfire.SaltCampfireBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.salt_lantern.SaltLanternBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.salt_torch.SaltTorchBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.sensor.fluid.FluidSensorBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.sensor.item_sorter.ItemSorterBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.sensor.wissen_activator.WissenActivatorBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.steam_extractor.SteamExtractorBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.steam_pipe.SteamPipeBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.steam_thermal_storage.SteamThermalStorageBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.totem.disenchant.TotemOfDisenchantBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience_absorption.TotemOfExperienceAbsorptionBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience_totem.ExperienceTotemBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.totem.flames.TotemOfFlamesBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.wissen_altar.WissenAltarBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.wissen_cell.WissenCellBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.wissen_crystallizer.WissenCrystallizerBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.wissen_translator.WissenTranslatorBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WizardsReborn.MOD_ID);

    public static RegistryObject<BlockEntityType<ArcanumGrowthBlockEntity>> ARCANUM_GROWTH = BLOCK_ENTITIES.register("arcanum_growth", () -> BlockEntityType.Builder.of(ArcanumGrowthBlockEntity::new, WizardsRebornBlocks.ARCANUM_GROWTH.get()).build(null));
    public static RegistryObject<BlockEntityType<CrystalBlockEntity>> CRYSTAL = BLOCK_ENTITIES.register("crystal", () -> BlockEntityType.Builder.of(CrystalBlockEntity::new,
                    WizardsRebornBlocks.EARTH_CRYSTAL.get(), WizardsRebornBlocks.WATER_CRYSTAL.get(), WizardsRebornBlocks.AIR_CRYSTAL.get(), WizardsRebornBlocks.FIRE_CRYSTAL.get(), WizardsRebornBlocks.VOID_CRYSTAL.get(),
                    WizardsRebornBlocks.FACETED_EARTH_CRYSTAL.get(), WizardsRebornBlocks.FACETED_WATER_CRYSTAL.get(), WizardsRebornBlocks.FACETED_AIR_CRYSTAL.get(), WizardsRebornBlocks.FACETED_FIRE_CRYSTAL.get(), WizardsRebornBlocks.FACETED_VOID_CRYSTAL.get(),
                    WizardsRebornBlocks.ADVANCED_EARTH_CRYSTAL.get(), WizardsRebornBlocks.ADVANCED_WATER_CRYSTAL.get(), WizardsRebornBlocks.ADVANCED_AIR_CRYSTAL.get(), WizardsRebornBlocks.ADVANCED_FIRE_CRYSTAL.get(), WizardsRebornBlocks.ADVANCED_VOID_CRYSTAL.get(),
                    WizardsRebornBlocks.MASTERFUL_EARTH_CRYSTAL.get(), WizardsRebornBlocks.MASTERFUL_WATER_CRYSTAL.get(), WizardsRebornBlocks.MASTERFUL_AIR_CRYSTAL.get(), WizardsRebornBlocks.MASTERFUL_FIRE_CRYSTAL.get(), WizardsRebornBlocks.MASTERFUL_VOID_CRYSTAL.get(),
                    WizardsRebornBlocks.PURE_EARTH_CRYSTAL.get(), WizardsRebornBlocks.PURE_WATER_CRYSTAL.get(), WizardsRebornBlocks.PURE_AIR_CRYSTAL.get(), WizardsRebornBlocks.PURE_FIRE_CRYSTAL.get(), WizardsRebornBlocks.PURE_VOID_CRYSTAL.get())
            .build(null));
    public static RegistryObject<BlockEntityType<CrystalGrowthBlockEntity>> CRYSTAL_GROWTH = BLOCK_ENTITIES.register("crystal_growth", () -> BlockEntityType.Builder.of(CrystalGrowthBlockEntity::new,
                    WizardsRebornBlocks.EARTH_CRYSTAL_GROWTH.get(), WizardsRebornBlocks.WATER_CRYSTAL_GROWTH.get(), WizardsRebornBlocks.AIR_CRYSTAL_GROWTH.get(), WizardsRebornBlocks.FIRE_CRYSTAL_GROWTH.get(), WizardsRebornBlocks.VOID_CRYSTAL_GROWTH.get())
            .build(null));

    public static RegistryObject<BlockEntityType<ArcanePedestalBlockEntity>> ARCANE_PEDESTAL = BLOCK_ENTITIES.register("arcane_pedestal", () -> BlockEntityType.Builder.of(ArcanePedestalBlockEntity::new, WizardsRebornBlocks.ARCANE_PEDESTAL.get(), WizardsRebornBlocks.INNOCENT_PEDESTAL.get(), WizardsRebornBlocks.CORK_BAMBOO_PEDESTAL.get(), WizardsRebornBlocks.WISESTONE_PEDESTAL.get()).build(null));
    public static RegistryObject<BlockEntityType<HoveringTomeStandBlockEntity>> HOVERING_TOME_STAND = BLOCK_ENTITIES.register("hovering_tome_stand", () -> BlockEntityType.Builder.of(HoveringTomeStandBlockEntity::new, WizardsRebornBlocks.HOVERING_TOME_STAND.get(), WizardsRebornBlocks.INNOCENT_HOVERING_TOME_STAND.get(), WizardsRebornBlocks.CORK_BAMBOO_HOVERING_TOME_STAND.get(), WizardsRebornBlocks.WISESTONE_HOVERING_TOME_STAND.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenAltarBlockEntity>> WISSEN_ALTAR = BLOCK_ENTITIES.register("wissen_altar", () -> BlockEntityType.Builder.of(WissenAltarBlockEntity::new, WizardsRebornBlocks.WISSEN_ALTAR.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenTranslatorBlockEntity>> WISSEN_TRANSLATOR = BLOCK_ENTITIES.register("wissen_translator", () -> BlockEntityType.Builder.of(WissenTranslatorBlockEntity::new, WizardsRebornBlocks.WISSEN_TRANSLATOR.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenCrystallizerBlockEntity>> WISSEN_CRYSTALLIZER = BLOCK_ENTITIES.register("wissen_crystallizer", () -> BlockEntityType.Builder.of(WissenCrystallizerBlockEntity::new, WizardsRebornBlocks.WISSEN_CRYSTALLIZER.get()).build(null));
    public static RegistryObject<BlockEntityType<ArcaneWorkbenchBlockEntity>> ARCANE_WORKBENCH = BLOCK_ENTITIES.register("arcane_workbench", () -> BlockEntityType.Builder.of(ArcaneWorkbenchBlockEntity::new, WizardsRebornBlocks.ARCANE_WORKBENCH.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenCellBlockEntity>> WISSEN_CELL = BLOCK_ENTITIES.register("wissen_cell", () -> BlockEntityType.Builder.of(WissenCellBlockEntity::new, WizardsRebornBlocks.WISSEN_CELL.get()).build(null));
    public static RegistryObject<BlockEntityType<JewelerTableBlockEntity>> JEWELER_TABLE = BLOCK_ENTITIES.register("jeweler_table", () -> BlockEntityType.Builder.of(JewelerTableBlockEntity::new, WizardsRebornBlocks.JEWELER_TABLE.get()).build(null));
    public static RegistryObject<BlockEntityType<AltarOfDroughtBlockEntity>> ALTAR_OF_DROUGHT = BLOCK_ENTITIES.register("altar_of_drought", () -> BlockEntityType.Builder.of(AltarOfDroughtBlockEntity::new, WizardsRebornBlocks.ALTAR_OF_DROUGHT.get()).build(null));
    public static RegistryObject<BlockEntityType<TotemOfFlamesBlockEntity>> TOTEM_OF_FLAMES = BLOCK_ENTITIES.register("totem_of_flames", () -> BlockEntityType.Builder.of(TotemOfFlamesBlockEntity::new, WizardsRebornBlocks.TOTEM_OF_FLAMES.get()).build(null));
    public static RegistryObject<BlockEntityType<ExperienceTotemBlockEntity>> EXPERIENCE_TOTEM = BLOCK_ENTITIES.register("experience_totem", () -> BlockEntityType.Builder.of(ExperienceTotemBlockEntity::new, WizardsRebornBlocks.EXPERIENCE_TOTEM.get()).build(null));
    public static RegistryObject<BlockEntityType<TotemOfExperienceAbsorptionBlockEntity>> TOTEM_OF_EXPERIENCE_ABSORPTION = BLOCK_ENTITIES.register("totem_of_experience_absorption", () -> BlockEntityType.Builder.of(TotemOfExperienceAbsorptionBlockEntity::new, WizardsRebornBlocks.TOTEM_OF_EXPERIENCE_ABSORPTION.get()).build(null));
    public static RegistryObject<BlockEntityType<TotemOfDisenchantBlockEntity>> TOTEM_OF_DISENCHANT = BLOCK_ENTITIES.register("totem_of_disenchant", () -> BlockEntityType.Builder.of(TotemOfDisenchantBlockEntity::new, WizardsRebornBlocks.TOTEM_OF_DISENCHANT.get()).build(null));
    public static RegistryObject<BlockEntityType<ArcaneIteratorBlockEntity>> ARCANE_ITERATOR = BLOCK_ENTITIES.register("arcane_iterator", () -> BlockEntityType.Builder.of(ArcaneIteratorBlockEntity::new, WizardsRebornBlocks.ARCANE_ITERATOR.get()).build(null));

    public static RegistryObject<BlockEntityType<FluidPipeBlockEntity>> FLUID_PIPE = BLOCK_ENTITIES.register("fluid_pipe", () -> BlockEntityType.Builder.of(FluidPipeBlockEntity::new, WizardsRebornBlocks.FLUID_PIPE.get()).build(null));
    public static RegistryObject<BlockEntityType<FluidExtractorBlockEntity>> FLUID_EXTRACTOR = BLOCK_ENTITIES.register("fluid_extractor", () -> BlockEntityType.Builder.of(FluidExtractorBlockEntity::new, WizardsRebornBlocks.FLUID_EXTRACTOR.get()).build(null));
    public static RegistryObject<BlockEntityType<SteamPipeBlockEntity>> STEAM_PIPE = BLOCK_ENTITIES.register("steam_pipe", () -> BlockEntityType.Builder.of(SteamPipeBlockEntity::new, WizardsRebornBlocks.STEAM_PIPE.get()).build(null));
    public static RegistryObject<BlockEntityType<SteamExtractorBlockEntity>> STEAM_EXTRACTOR = BLOCK_ENTITIES.register("steam_extractor", () -> BlockEntityType.Builder.of(SteamExtractorBlockEntity::new, WizardsRebornBlocks.STEAM_EXTRACTOR.get()).build(null));
    public static RegistryObject<BlockEntityType<AlchemyFurnaceBlockEntity>> ALCHEMY_FURNACE = BLOCK_ENTITIES.register("alchemy_furnace", () -> BlockEntityType.Builder.of(AlchemyFurnaceBlockEntity::new, WizardsRebornBlocks.ALCHEMY_FURNACE.get()).build(null));
    public static RegistryObject<BlockEntityType<OrbitalFluidRetainerBlockEntity>> ORBITAL_FLUID_RETAINER = BLOCK_ENTITIES.register("orbital_fluid_retainer", () -> BlockEntityType.Builder.of(OrbitalFluidRetainerBlockEntity::new, WizardsRebornBlocks.ORBITAL_FLUID_RETAINER.get()).build(null));
    public static RegistryObject<BlockEntityType<SteamThermalStorageBlockEntity>> STEAM_THERMAL_STORAGE = BLOCK_ENTITIES.register("steam_thermal_storage", () -> BlockEntityType.Builder.of(SteamThermalStorageBlockEntity::new, WizardsRebornBlocks.STEAM_THERMAL_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<AlchemyMachineBlockEntity>> ALCHEMY_MACHINE = BLOCK_ENTITIES.register("alchemy_machine", () -> BlockEntityType.Builder.of(AlchemyMachineBlockEntity::new, WizardsRebornBlocks.ALCHEMY_MACHINE.get()).build(null));
    public static RegistryObject<BlockEntityType<AlchemyBoilerBlockEntity>> ALCHEMY_BOILER = BLOCK_ENTITIES.register("alchemy_boiler", () -> BlockEntityType.Builder.of(AlchemyBoilerBlockEntity::new, WizardsRebornBlocks.ALCHEMY_BOILER.get()).build(null));
    public static RegistryObject<BlockEntityType<ArcaneCenserBlockEntity>> ARCANE_CENSER = BLOCK_ENTITIES.register("arcane_censer", () -> BlockEntityType.Builder.of(ArcaneCenserBlockEntity::new, WizardsRebornBlocks.ARCANE_CENSER.get()).build(null));

    public static RegistryObject<BlockEntityType<LightEmitterBlockEntity>> LIGHT_EMITTER = BLOCK_ENTITIES.register("light_emitter", () -> BlockEntityType.Builder.of(LightEmitterBlockEntity::new, WizardsRebornBlocks.LIGHT_EMITTER.get()).build(null));
    public static RegistryObject<BlockEntityType<LightTransferLensBlockEntity>> LIGHT_TRANSFER_LENS = BLOCK_ENTITIES.register("light_transfer_lens", () -> BlockEntityType.Builder.of(LightTransferLensBlockEntity::new, WizardsRebornBlocks.LIGHT_TRANSFER_LENS.get()).build(null));
    public static RegistryObject<BlockEntityType<RunicPedestalBlockEntity>> RUNIC_PEDESTAL = BLOCK_ENTITIES.register("runic_pedestal", () -> BlockEntityType.Builder.of(RunicPedestalBlockEntity::new, WizardsRebornBlocks.RUNIC_PEDESTAL.get()).build(null));
    public static RegistryObject<BlockEntityType<EngravedWisestoneBlockEntity>> ENGRAVED_WISESTONE = BLOCK_ENTITIES.register("engraved_wisestone", () -> BlockEntityType.Builder.of(EngravedWisestoneBlockEntity::new, WizardsRebornBlocks.ENGRAVED_WISESTONE.get(),
                    WizardsRebornBlocks.ENGRAVED_WISESTONE_LUNAM.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_VITA.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_SOLEM.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_MORS.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_MIRACULUM.get(),
                    WizardsRebornBlocks.ENGRAVED_WISESTONE_TEMPUS.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_STATERA.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_ECLIPSIS.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_SICCITAS.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_SOLSTITIUM.get(),
                    WizardsRebornBlocks.ENGRAVED_WISESTONE_FAMES.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_RENAISSANCE.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_BELLUM.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_LUX.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_KARA.get(),
                    WizardsRebornBlocks.ENGRAVED_WISESTONE_DEGRADATIO.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_PRAEDICTIONEM.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_EVOLUTIONIS.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_TENEBRIS.get(), WizardsRebornBlocks.ENGRAVED_WISESTONE_UNIVERSUM.get())
            .build(null));

    public static RegistryObject<BlockEntityType<ArcaneHopperBlockEntity>> ARCANE_HOPPER = BLOCK_ENTITIES.register("arcane_hopper", () -> BlockEntityType.Builder.of(ArcaneHopperBlockEntity::new, WizardsRebornBlocks.ARCANE_HOPPER.get()).build(null));
    public static RegistryObject<BlockEntityType<SensorBlockEntity>> SENSOR = BLOCK_ENTITIES.register("sensor", () -> BlockEntityType.Builder.of(SensorBlockEntity::new, WizardsRebornBlocks.REDSTONE_SENSOR.get(), WizardsRebornBlocks.WISSEN_SENSOR.get(), WizardsRebornBlocks.COOLDOWN_SENSOR.get(), WizardsRebornBlocks.LIGHT_SENSOR.get(), WizardsRebornBlocks.EXPERIENCE_SENSOR.get(), WizardsRebornBlocks.HEAT_SENSOR.get(), WizardsRebornBlocks.STEAM_SENSOR.get()).build(null));
    public static RegistryObject<BlockEntityType<FluidSensorBlockEntity>> FLUID_SENSOR = BLOCK_ENTITIES.register("fluid_sensor", () -> BlockEntityType.Builder.of(FluidSensorBlockEntity::new, WizardsRebornBlocks.FLUID_SENSOR.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenActivatorBlockEntity>> WISSEN_ACTIVATOR = BLOCK_ENTITIES.register("wissen_activator", () -> BlockEntityType.Builder.of(WissenActivatorBlockEntity::new, WizardsRebornBlocks.WISSEN_ACTIVATOR.get()).build(null));
    public static RegistryObject<BlockEntityType<ItemSorterBlockEntity>> ITEM_SORTER = BLOCK_ENTITIES.register("item_sorter", () -> BlockEntityType.Builder.of(ItemSorterBlockEntity::new, WizardsRebornBlocks.ITEM_SORTER.get()).build(null));

    public static RegistryObject<BlockEntityType<WissenCasingBlockEntity>> WISSEN_CASING = BLOCK_ENTITIES.register("wissen_casing", () -> BlockEntityType.Builder.of(WissenCasingBlockEntity::new, WizardsRebornBlocks.ARCANE_WOOD_WISSEN_CASING.get(), WizardsRebornBlocks.INNOCENT_WOOD_WISSEN_CASING.get(), WizardsRebornBlocks.CORK_BAMBOO_WISSEN_CASING.get(), WizardsRebornBlocks.WISESTONE_WISSEN_CASING.get()).build(null));
    public static RegistryObject<BlockEntityType<LightCasingBlockEntity>> LIGHT_CASING = BLOCK_ENTITIES.register("light_casing", () -> BlockEntityType.Builder.of(LightCasingBlockEntity::new, WizardsRebornBlocks.ARCANE_WOOD_LIGHT_CASING.get(), WizardsRebornBlocks.INNOCENT_WOOD_LIGHT_CASING.get(), WizardsRebornBlocks.CORK_BAMBOO_LIGHT_CASING.get(), WizardsRebornBlocks.WISESTONE_LIGHT_CASING.get()).build(null));
    public static RegistryObject<BlockEntityType<FluidCasingBlockEntity>> FLUID_CASING = BLOCK_ENTITIES.register("fluid_casing", () -> BlockEntityType.Builder.of(FluidCasingBlockEntity::new, WizardsRebornBlocks.ARCANE_WOOD_FLUID_CASING.get(), WizardsRebornBlocks.INNOCENT_WOOD_FLUID_CASING.get(), WizardsRebornBlocks.CORK_BAMBOO_FLUID_CASING.get(), WizardsRebornBlocks.WISESTONE_FLUID_CASING.get()).build(null));
    public static RegistryObject<BlockEntityType<SteamCasingBlockEntity>> STEAM_CASING = BLOCK_ENTITIES.register("steam_casing", () -> BlockEntityType.Builder.of(SteamCasingBlockEntity::new, WizardsRebornBlocks.ARCANE_WOOD_STEAM_CASING.get(), WizardsRebornBlocks.INNOCENT_WOOD_STEAM_CASING.get(), WizardsRebornBlocks.CORK_BAMBOO_STEAM_CASING.get(), WizardsRebornBlocks.WISESTONE_STEAM_CASING.get()).build(null));

    public static RegistryObject<BlockEntityType<CreativeWissenStorageBlockEntity>> CREATIVE_WISSEN_STORAGE = BLOCK_ENTITIES.register("creative_wissen_storage", () -> BlockEntityType.Builder.of(CreativeWissenStorageBlockEntity::new, WizardsRebornBlocks.CREATIVE_WISSEN_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<CreativeLightStorageBlockEntity>> CREATIVE_LIGHT_STORAGE = BLOCK_ENTITIES.register("creative_light_storage", () -> BlockEntityType.Builder.of(CreativeLightStorageBlockEntity::new, WizardsRebornBlocks.CREATIVE_LIGHT_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<CreativeFluidStorageBlockEntity>> CREATIVE_FLUID_STORAGE = BLOCK_ENTITIES.register("creative_fluid_storage", () -> BlockEntityType.Builder.of(CreativeFluidStorageBlockEntity::new, WizardsRebornBlocks.CREATIVE_FLUID_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<CreativeSteamStorageBlockEntity>> CREATIVE_STEAM_STORAGE = BLOCK_ENTITIES.register("creative_steam_storage", () -> BlockEntityType.Builder.of(CreativeSteamStorageBlockEntity::new, WizardsRebornBlocks.CREATIVE_STEAM_STORAGE.get()).build(null));

    public static RegistryObject<BlockEntityType<SaltTorchBlockEntity>> SALT_TORCH = BLOCK_ENTITIES.register("salt_torch", () -> BlockEntityType.Builder.of(SaltTorchBlockEntity::new, WizardsRebornBlocks.ARCANE_SALT_TORCH.get(), WizardsRebornBlocks.ARCANE_SALT_WALL_TORCH.get(), WizardsRebornBlocks.INNOCENT_SALT_TORCH.get(), WizardsRebornBlocks.INNOCENT_SALT_WALL_TORCH.get(), WizardsRebornBlocks.CORK_BAMBOO_SALT_TORCH.get(), WizardsRebornBlocks.CORK_BAMBOO_SALT_WALL_TORCH.get(), WizardsRebornBlocks.WISESTONE_SALT_TORCH.get(), WizardsRebornBlocks.WISESTONE_SALT_WALL_TORCH.get()).build(null));
    public static RegistryObject<BlockEntityType<SaltLanternBlockEntity>> SALT_LANTERN = BLOCK_ENTITIES.register("salt_lantern", () -> BlockEntityType.Builder.of(SaltLanternBlockEntity::new, WizardsRebornBlocks.ARCANE_SALT_LANTERN.get(), WizardsRebornBlocks.INNOCENT_SALT_LANTERN.get(), WizardsRebornBlocks.CORK_BAMBOO_SALT_LANTERN.get(), WizardsRebornBlocks.WISESTONE_SALT_LANTERN.get()).build(null));
    public static RegistryObject<BlockEntityType<SaltCampfireBlockEntity>> SALT_CAMPFIRE = BLOCK_ENTITIES.register("salt_campfire", () -> BlockEntityType.Builder.of(SaltCampfireBlockEntity::new, WizardsRebornBlocks.ARCANE_SALT_CAMPFIRE.get(), WizardsRebornBlocks.INNOCENT_SALT_CAMPFIRE.get(), WizardsRebornBlocks.CORK_BAMBOO_SALT_CAMPFIRE.get(), WizardsRebornBlocks.WISESTONE_SALT_CAMPFIRE.get()).build(null));

    public static RegistryObject<BlockEntityType<CrossBaulkBlockEntity>> CROSS_BAULK = BLOCK_ENTITIES.register("cross_baulk", () -> BlockEntityType.Builder.of(CrossBaulkBlockEntity::new, WizardsRebornBlocks.ARCANE_WOOD_CROSS_BAULK.get(), WizardsRebornBlocks.STRIPPED_ARCANE_WOOD_CROSS_BAULK.get(), WizardsRebornBlocks.ARCANE_WOOD_PLANKS_CROSS_BAULK.get(), WizardsRebornBlocks.INNOCENT_WOOD_CROSS_BAULK.get(), WizardsRebornBlocks.STRIPPED_INNOCENT_WOOD_CROSS_BAULK.get(), WizardsRebornBlocks.INNOCENT_WOOD_PLANKS_CROSS_BAULK.get(), WizardsRebornBlocks.CORK_BAMBOO_CROSS_BAULK.get(), WizardsRebornBlocks.CORK_BAMBOO_PLANKS_CROSS_BAULK.get(), WizardsRebornBlocks.CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK.get()).build(null));
    public static RegistryObject<BlockEntityType<PlacedItemsBlockEntity>> PLACED_ITEMS = BLOCK_ENTITIES.register("placed_items", () -> BlockEntityType.Builder.of(PlacedItemsBlockEntity::new, WizardsRebornBlocks.PLACED_ITEMS.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            BlockEntityRenderers.register(WizardsRebornBlockEntities.ARCANE_PEDESTAL.get(), (trd) -> new ArcanePedestalRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.HOVERING_TOME_STAND.get(), HoveringTomeStandRenderer::new);
            BlockEntityRenderers.register(WizardsRebornBlockEntities.WISSEN_ALTAR.get(), (trd) -> new WissenAltarRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.WISSEN_TRANSLATOR.get(), (trd) -> new WissenTranslatorRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.WISSEN_CRYSTALLIZER.get(), (trd) -> new WissenCrystallizerRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.ARCANE_WORKBENCH.get(), (trd) -> new ArcaneWorkbenchRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.WISSEN_CELL.get(), (trd) -> new WissenCellRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.JEWELER_TABLE.get(), (trd) -> new JewelerTableRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.ORBITAL_FLUID_RETAINER.get(), (trd) -> new OrbitalFluidRetainerRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.ALTAR_OF_DROUGHT.get(), (trd) -> new AltarOfDroughtRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.ALCHEMY_MACHINE.get(), (trd) -> new AlchemyMachineRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.ALCHEMY_BOILER.get(), (trd) -> new AlchemyBoilerRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.ARCANE_CENSER.get(), (trd) -> new ArcaneCenserRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.EXPERIENCE_TOTEM.get(), (trd) -> new ExperienceTotemRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.TOTEM_OF_EXPERIENCE_ABSORPTION.get(), (trd) -> new TotemOfExperienceAbsorptionRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.TOTEM_OF_DISENCHANT.get(), (trd) -> new TotemOfDisenchantRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.ARCANE_ITERATOR.get(), (trd) -> new ArcaneIteratorRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.LIGHT_EMITTER.get(), (trd) -> new LightEmitterBlockRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.LIGHT_TRANSFER_LENS.get(), (trd) -> new LightTransferLensRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.RUNIC_PEDESTAL.get(), (trd) -> new RunicPedestalRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.ENGRAVED_WISESTONE.get(), (trd) -> new EngravedWisestoneRenderer());

            BlockEntityRenderers.register(WizardsRebornBlockEntities.SENSOR.get(), (trd) -> new SensorRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.FLUID_SENSOR.get(), (trd) -> new FluidSensorRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.WISSEN_ACTIVATOR.get(), (trd) -> new SensorRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.ITEM_SORTER.get(), (trd) -> new ItemSorterRenderer());

            BlockEntityRenderers.register(WizardsRebornBlockEntities.WISSEN_CASING.get(), (trd) -> new WissenCasingRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.LIGHT_CASING.get(), (trd) -> new LightCasingRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.FLUID_CASING.get(), (trd) -> new FluidCasingRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.STEAM_CASING.get(), (trd) -> new SteamCasingRenderer());

            BlockEntityRenderers.register(WizardsRebornBlockEntities.CREATIVE_WISSEN_STORAGE.get(), (trd) -> new CreativeWissenStorageRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.CREATIVE_LIGHT_STORAGE.get(), (trd) -> new LightCasingRenderer());

            BlockEntityRenderers.register(WizardsRebornBlockEntities.SALT_CAMPFIRE.get(), (trd) -> new SaltCampfireRenderer());

            BlockEntityRenderers.register(WizardsRebornBlockEntities.ARCANUM_GROWTH.get(), (trd) -> new ArcanumGrowthRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.CRYSTAL_GROWTH.get(), (trd) -> new CrystalGrowthRenderer());
            BlockEntityRenderers.register(WizardsRebornBlockEntities.CRYSTAL.get(), (trd) -> new CrystalRenderer());

            BlockEntityRenderers.register(WizardsRebornBlockEntities.PLACED_ITEMS.get(), (trd) -> new PlacedItemsRenderer());
        }
    }
}