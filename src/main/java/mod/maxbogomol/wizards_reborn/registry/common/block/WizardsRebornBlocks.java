package mod.maxbogomol.wizards_reborn.registry.common.block;

import mod.maxbogomol.fluffy_fur.common.block.WaterloggableLeverBlock;
import mod.maxbogomol.fluffy_fur.common.block.sign.CustomCeilingHangingSignBlock;
import mod.maxbogomol.fluffy_fur.common.block.sign.CustomStandingSignBlock;
import mod.maxbogomol.fluffy_fur.common.block.sign.CustomWallHangingSignBlock;
import mod.maxbogomol.fluffy_fur.common.block.sign.CustomWallSignBlock;
import mod.maxbogomol.fluffy_fur.registry.common.block.FluffyFurBlocks;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.common.block.CustomBlockColor;
import mod.maxbogomol.wizards_reborn.common.block.LuminalGlassBlock;
import mod.maxbogomol.wizards_reborn.common.block.SniffaloEggBlock;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_boiler.AlchemyBoilerBlock;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_furnace.AlchemyFurnaceBlock;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlock;
import mod.maxbogomol.wizards_reborn.common.block.altar_of_drought.AltarOfDroughtBlock;
import mod.maxbogomol.wizards_reborn.common.block.arcane_censer.ArcaneCenserBlock;
import mod.maxbogomol.wizards_reborn.common.block.arcane_hopper.ArcaneHopperBlock;
import mod.maxbogomol.wizards_reborn.common.block.arcane_iterator.ArcaneIteratorBlock;
import mod.maxbogomol.wizards_reborn.common.block.arcane_pedestal.ArcanePedestalBlock;
import mod.maxbogomol.wizards_reborn.common.block.arcane_workbench.ArcaneWorkbenchBlock;
import mod.maxbogomol.wizards_reborn.common.block.arcanum_growth.ArcanumGrowthBlock;
import mod.maxbogomol.wizards_reborn.common.block.baulk.BaulkBlock;
import mod.maxbogomol.wizards_reborn.common.block.baulk.CrossBaulkBlock;
import mod.maxbogomol.wizards_reborn.common.block.cargo_carpet.CargoCarpetBlock;
import mod.maxbogomol.wizards_reborn.common.block.casing.CasingBlock;
import mod.maxbogomol.wizards_reborn.common.block.casing.FrameBlock;
import mod.maxbogomol.wizards_reborn.common.block.casing.GlassFrameBlock;
import mod.maxbogomol.wizards_reborn.common.block.casing.fluid.FluidCasingBlock;
import mod.maxbogomol.wizards_reborn.common.block.casing.light.LightCasingBlock;
import mod.maxbogomol.wizards_reborn.common.block.casing.steam.SteamCasingBlock;
import mod.maxbogomol.wizards_reborn.common.block.casing.wissen.WissenCasingBlock;
import mod.maxbogomol.wizards_reborn.common.block.cork_bamboo.CorkBambooSaplingBlock;
import mod.maxbogomol.wizards_reborn.common.block.cork_bamboo.CorkBambooStalkBlock;
import mod.maxbogomol.wizards_reborn.common.block.creative.fluid_storage.CreativeFluidStorageBlock;
import mod.maxbogomol.wizards_reborn.common.block.creative.light_storage.CreativeLightStorageBlock;
import mod.maxbogomol.wizards_reborn.common.block.creative.steam_storage.CreativeSteamStorageBlock;
import mod.maxbogomol.wizards_reborn.common.block.creative.wissen_storage.CreativeWissenStorageBlock;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlock;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalSeedBlock;
import mod.maxbogomol.wizards_reborn.common.block.crystal_growth.CrystalGrowthBlock;
import mod.maxbogomol.wizards_reborn.common.block.engraved_wisestone.EngravedWisestoneBlock;
import mod.maxbogomol.wizards_reborn.common.block.grower.ArcaneWoodTreeGrower;
import mod.maxbogomol.wizards_reborn.common.block.grower.InnocentWoodTreeGrower;
import mod.maxbogomol.wizards_reborn.common.block.jeweler_table.JewelerTableBlock;
import mod.maxbogomol.wizards_reborn.common.block.light_emitter.LightEmitterBlock;
import mod.maxbogomol.wizards_reborn.common.block.light_transfer_lens.LightTransferLensBlock;
import mod.maxbogomol.wizards_reborn.common.block.orbital_fluid_retainer.OrbitalFluidRetainerBlock;
import mod.maxbogomol.wizards_reborn.common.block.ore.ArcanumOreBlock;
import mod.maxbogomol.wizards_reborn.common.block.ore.NetherSaltOreBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.fluid.FluidExtractorBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.steam.SteamExtractorBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.fluid.FluidPipeBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.steam.SteamPipeBlock;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlock;
import mod.maxbogomol.wizards_reborn.common.block.plant.*;
import mod.maxbogomol.wizards_reborn.common.block.runic_pedestal.RunicPedestalBlock;
import mod.maxbogomol.wizards_reborn.common.block.salt.campfire.SaltCampfireBlock;
import mod.maxbogomol.wizards_reborn.common.block.salt.lantern.SaltLanternBlock;
import mod.maxbogomol.wizards_reborn.common.block.salt.torch.SaltTorchBlock;
import mod.maxbogomol.wizards_reborn.common.block.salt.torch.SaltWallTorchBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.*;
import mod.maxbogomol.wizards_reborn.common.block.sensor.fluid.FluidSensorBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.item_sorter.ItemSorterBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.wissen_activator.WissenActivatorBlock;
import mod.maxbogomol.wizards_reborn.common.block.steam_thermal_storage.SteamThermalStorageBlock;
import mod.maxbogomol.wizards_reborn.common.block.totem.TotemBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.totem.disenchant.TotemOfDisenchantBlock;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience.ExperienceTotemBlock;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience_absorption.TotemOfExperienceAbsorptionBlock;
import mod.maxbogomol.wizards_reborn.common.block.totem.flames.TotemOfFlamesBlock;
import mod.maxbogomol.wizards_reborn.common.block.underground_grape.UndergroundGrapeVinesBlock;
import mod.maxbogomol.wizards_reborn.common.block.underground_grape.UndergroundGrapeVinesPlantBlock;
import mod.maxbogomol.wizards_reborn.common.block.wissen_altar.WissenAltarBlock;
import mod.maxbogomol.wizards_reborn.common.block.wissen_cell.WissenCellBlock;
import mod.maxbogomol.wizards_reborn.common.block.wissen_crystallizer.WissenCrystallizerBlock;
import mod.maxbogomol.wizards_reborn.common.block.wissen_translator.WissenTranslatorBlock;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMonograms;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.fluid.WizardsRebornFluids;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WizardsReborn.MOD_ID);

    //MATERIALS
    public static final RegistryObject<Block> ARCANE_GOLD_BLOCK = BLOCKS.register("arcane_gold_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).sound(WizardsRebornSounds.ARCANE_GOLD)));
    public static final RegistryObject<Block> ARCANE_GOLD_ORE = BLOCKS.register("arcane_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).sound(WizardsRebornSounds.ARCANE_GOLD_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_ARCANE_GOLD_ORE = BLOCKS.register("deepslate_arcane_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE).sound(WizardsRebornSounds.DEEPSLATE_ARCANE_GOLD_ORE)));
    public static final RegistryObject<Block> NETHER_ARCANE_GOLD_ORE = BLOCKS.register("nether_arcane_gold_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_GOLD_ORE).sound(WizardsRebornSounds.NETHER_ARCANE_GOLD_ORE), UniformInt.of(0, 1)));
    public static final RegistryObject<Block> RAW_ARCANE_GOLD_BLOCK = BLOCKS.register("raw_arcane_gold_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).sound(WizardsRebornSounds.RAW_ARCANE_GOLD)));

    public static final RegistryObject<Block> SARCON_BLOCK = BLOCKS.register("sarcon_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).mapColor(MapColor.COLOR_PURPLE).sound(WizardsRebornSounds.SARCON)));

    public static final RegistryObject<Block> VILENIUM_BLOCK = BLOCKS.register("vilenium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).mapColor(MapColor.COLOR_YELLOW).sound(WizardsRebornSounds.VILENIUM)));

    public static final RegistryObject<Block> ARCANUM_BLOCK = BLOCKS.register("arcanum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).sound(WizardsRebornSounds.ARCANUM)));
    public static final RegistryObject<Block> ARCANUM_ORE = BLOCKS.register("arcanum_ore", () -> new ArcanumOreBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).sound(WizardsRebornSounds.ARCANUM_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_ARCANUM_ORE = BLOCKS.register("deepslate_arcanum_ore", () -> new ArcanumOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE).sound(WizardsRebornSounds.DEEPSLATE_ARCANUM_ORE)));
    public static final RegistryObject<Block> ARCANUM_DUST_BLOCK = BLOCKS.register("arcanum_dust_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_CONCRETE_POWDER)));

    public static final RegistryObject<Block> ARCACITE_BLOCK = BLOCKS.register("arcacite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_RED).sound(WizardsRebornSounds.ARCACITE)));
    public static final RegistryObject<Block> PRECISION_CRYSTAL_BLOCK = BLOCKS.register("precision_crystal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_LIGHT_GREEN).sound(WizardsRebornSounds.PRECISION_CRYSTAL)));

    public static final RegistryObject<Block> NETHER_SALT_BLOCK = BLOCKS.register("nether_salt_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).sound(WizardsRebornSounds.NETHER_SALT)));
    public static final RegistryObject<Block> NETHER_SALT_ORE = BLOCKS.register("nether_salt_ore", () -> new NetherSaltOreBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_GOLD_ORE).sound(WizardsRebornSounds.NETHER_SALT_ORE)));

    public static final RegistryObject<Block> ALCHEMY_CALX_BLOCK = BLOCKS.register("alchemy_calx_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_CONCRETE_POWDER)));
    public static final RegistryObject<Block> NATURAL_CALX_BLOCK = BLOCKS.register("natural_calx_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.GREEN_CONCRETE_POWDER)));
    public static final RegistryObject<Block> SCORCHED_CALX_BLOCK = BLOCKS.register("scorched_calx_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.RED_CONCRETE_POWDER)));
    public static final RegistryObject<Block> DISTANT_CALX_BLOCK = BLOCKS.register("distant_calx_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.CYAN_CONCRETE_POWDER)));
    public static final RegistryObject<Block> ENCHANTED_CALX_BLOCK = BLOCKS.register("enchanted_calx_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.LIME_CONCRETE_POWDER)));

    public static final RegistryObject<Block> ARCACITE_POLISHING_MIXTURE_BLOCK = BLOCKS.register("arcacite_polishing_mixture_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).mapColor(MapColor.TERRACOTTA_RED).sound(WizardsRebornSounds.MIXTURE)));

    //PLANTS
    public static final RegistryObject<Block> ARCANE_LINEN = BLOCKS.register("arcane_linen", () -> new ArcaneLinenBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> ARCANE_LINEN_HAY = BLOCKS.register("arcane_linen_hay", () -> new HayBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));

    public static final RegistryObject<Block> MOR = BLOCKS.register("mor", () -> new MorBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM).mapColor(MapColor.COLOR_BLUE).sound(WizardsRebornSounds.MOR)));
    public static final RegistryObject<Block> POTTED_MOR = BLOCKS.register("potted_mor", () -> new FlowerPotBlock(MOR.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> MOR_BLOCK = BLOCKS.register("mor_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK).mapColor(MapColor.COLOR_BLUE).sound(WizardsRebornSounds.MOR_BLOCK)));
    public static final RegistryObject<Block> ELDER_MOR = BLOCKS.register("elder_mor", () -> new MorBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).mapColor(MapColor.COLOR_BLACK).sound(WizardsRebornSounds.ELDER_MOR)));
    public static final RegistryObject<Block> POTTED_ELDER_MOR = BLOCKS.register("potted_elder_mor", () -> new FlowerPotBlock(ELDER_MOR.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> ELDER_MOR_BLOCK = BLOCKS.register("elder_mor_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK).mapColor(MapColor.COLOR_BLACK).sound(WizardsRebornSounds.ELDER_MOR_BLOCK)));

    public static final RegistryObject<Block> PITCHER_TURNIP = BLOCKS.register("pitcher_turnip", () -> new PitcherTurnipBlock(BlockBehaviour.Properties.of().strength(0.5F).mapColor(MapColor.COLOR_ORANGE).sound(SoundType.CROP).noOcclusion()));
    public static final RegistryObject<Block> PITCHER_TURNIP_BLOCK = BLOCKS.register("pitcher_turnip_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.PUMPKIN)));
    public static final RegistryObject<Block> POTTED_PITCHER_TURNIP = BLOCKS.register("potted_pitcher_turnip", () -> new PottedPitcherTurnipBlock(PITCHER_TURNIP.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> SHINY_CLOVER_CROP = BLOCKS.register("shiny_clover_crop", () -> new ShinyCloverCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> SHINY_CLOVER = BLOCKS.register("shiny_clover", () -> new ShinyCloverBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> POTTED_SHINY_CLOVER = BLOCKS.register("potted_shiny_clover", () -> new FlowerPotBlock(SHINY_CLOVER.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> UNDERGROUND_GRAPE_VINES = BLOCKS.register("underground_grape_vines", () -> new UndergroundGrapeVinesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().noCollission().instabreak().sound(SoundType.CAVE_VINES).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> UNDERGROUND_GRAPE_VINES_PLANT = BLOCKS.register("underground_grape_vines_plant", () -> new UndergroundGrapeVinesPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().noCollission().instabreak().sound(SoundType.CAVE_VINES).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> PLACED_ITEMS = BLOCKS.register("placed_items", () -> new PlacedItemsBlock(BlockBehaviour.Properties.of().strength(0.25F).mapColor(MapColor.COLOR_BROWN).sound(SoundType.CROP).noOcclusion()));

    //WOOD
    public static final RegistryObject<Block> ARCANE_WOOD_LOG = BLOCKS.register("arcane_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> ARCANE_WOOD = BLOCKS.register("arcane_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD_LOG = BLOCKS.register("stripped_arcane_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD = BLOCKS.register("stripped_arcane_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> ARCANE_WOOD_PLANKS = BLOCKS.register("arcane_wood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> ARCANE_WOOD_STAIRS = BLOCKS.register("arcane_wood_stairs", () -> new StairBlock(() -> ARCANE_WOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_SLAB = BLOCKS.register("arcane_wood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_BAULK = BLOCKS.register("arcane_wood_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_LOG.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_CROSS_BAULK = BLOCKS.register("arcane_wood_cross_baulk", () -> new CrossBaulkBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD_BAULK = BLOCKS.register("stripped_arcane_wood_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(STRIPPED_ARCANE_WOOD_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD_CROSS_BAULK = BLOCKS.register("stripped_arcane_wood_cross_baulk", () -> new CrossBaulkBlock(BlockBehaviour.Properties.copy(STRIPPED_ARCANE_WOOD_LOG.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_PLANKS_BAULK = BLOCKS.register("arcane_wood_planks_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_PLANKS_CROSS_BAULK = BLOCKS.register("arcane_wood_planks_cross_baulk", () -> new CrossBaulkBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_FENCE = BLOCKS.register("arcane_wood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_FENCE_GATE = BLOCKS.register("arcane_wood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()), WizardsRebornWoodTypes.ARCANE_WOOD));
    public static final RegistryObject<Block> ARCANE_WOOD_DOOR = BLOCKS.register("arcane_wood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion(), WizardsRebornBlockSetTypes.ARCANE_WOOD));
    public static final RegistryObject<Block> ARCANE_WOOD_TRAPDOOR = BLOCKS.register("arcane_wood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion(), WizardsRebornBlockSetTypes.ARCANE_WOOD));
    public static final RegistryObject<Block> ARCANE_WOOD_PRESSURE_PLATE = BLOCKS.register("arcane_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion().noCollission(), WizardsRebornBlockSetTypes.ARCANE_WOOD));
    public static final RegistryObject<Block> ARCANE_WOOD_BUTTON = BLOCKS.register("arcane_wood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(WizardsRebornSounds.ARCANE_WOOD), WizardsRebornBlockSetTypes.ARCANE_WOOD, 30, true));
    public static final RegistryObject<Block> ARCANE_WOOD_SIGN = BLOCKS.register("arcane_wood_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion().noCollission(), WizardsRebornWoodTypes.ARCANE_WOOD));
    public static final RegistryObject<Block> ARCANE_WOOD_WALL_SIGN = BLOCKS.register("arcane_wood_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion().noCollission(), WizardsRebornWoodTypes.ARCANE_WOOD));
    public static final RegistryObject<Block> ARCANE_WOOD_HANGING_SIGN = BLOCKS.register("arcane_wood_hanging_sign", () -> new CustomCeilingHangingSignBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).sound(WizardsRebornSounds.ARCANE_WOOD_HANGING_SIGN).noOcclusion().noCollission(), WizardsRebornWoodTypes.ARCANE_WOOD));
    public static final RegistryObject<Block> ARCANE_WOOD_WALL_HANGING_SIGN = BLOCKS.register("arcane_wood_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).sound(WizardsRebornSounds.ARCANE_WOOD_HANGING_SIGN).noOcclusion().noCollission(), WizardsRebornWoodTypes.ARCANE_WOOD));
    public static final RegistryObject<Block> ARCANE_WOOD_LEAVES = BLOCKS.register("arcane_wood_leaves", () -> new ArcaneWoodLeavesBlock(BlockBehaviour.Properties.copy(Blocks.AZALEA_LEAVES).lightLevel((state) -> 5)));
    public static final RegistryObject<Block> ARCANE_WOOD_SAPLING = BLOCKS.register("arcane_wood_sapling", () -> new SaplingBlock(new ArcaneWoodTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.AZALEA)));
    public static final RegistryObject<Block> POTTED_ARCANE_WOOD_SAPLING = BLOCKS.register("potted_arcane_wood_sapling", () -> new FlowerPotBlock(ARCANE_WOOD_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));

    public static final RegistryObject<Block> INNOCENT_WOOD_LOG = BLOCKS.register("innocent_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LOG).mapColor(MapColor.TERRACOTTA_GRAY).sound(WizardsRebornSounds.INNOCENT_WOOD)));
    public static final RegistryObject<Block> INNOCENT_WOOD = BLOCKS.register("innocent_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LOG).mapColor(MapColor.TERRACOTTA_GRAY).sound(WizardsRebornSounds.INNOCENT_WOOD)));
    public static final RegistryObject<Block> STRIPPED_INNOCENT_WOOD_LOG = BLOCKS.register("stripped_innocent_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LOG).mapColor(MapColor.TERRACOTTA_GRAY).sound(WizardsRebornSounds.INNOCENT_WOOD)));
    public static final RegistryObject<Block> STRIPPED_INNOCENT_WOOD = BLOCKS.register("stripped_innocent_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LOG).mapColor(MapColor.TERRACOTTA_GRAY).sound(WizardsRebornSounds.INNOCENT_WOOD)));
    public static final RegistryObject<Block> INNOCENT_WOOD_PLANKS = BLOCKS.register("innocent_wood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).mapColor(MapColor.TERRACOTTA_GRAY).sound(WizardsRebornSounds.INNOCENT_WOOD)));
    public static final RegistryObject<Block> INNOCENT_WOOD_STAIRS = BLOCKS.register("innocent_wood_stairs", () -> new StairBlock(() -> INNOCENT_WOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_SLAB = BLOCKS.register("innocent_wood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_BAULK = BLOCKS.register("innocent_wood_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_LOG.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_CROSS_BAULK = BLOCKS.register("innocent_wood_cross_baulk", () -> new CrossBaulkBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_INNOCENT_WOOD_BAULK = BLOCKS.register("stripped_innocent_wood_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(STRIPPED_INNOCENT_WOOD_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_INNOCENT_WOOD_CROSS_BAULK = BLOCKS.register("stripped_innocent_wood_cross_baulk", () -> new CrossBaulkBlock(BlockBehaviour.Properties.copy(STRIPPED_INNOCENT_WOOD_LOG.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_PLANKS_BAULK = BLOCKS.register("innocent_wood_planks_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_PLANKS_CROSS_BAULK = BLOCKS.register("innocent_wood_planks_cross_baulk", () -> new CrossBaulkBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_FENCE = BLOCKS.register("innocent_wood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_FENCE_GATE = BLOCKS.register("innocent_wood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()), WizardsRebornWoodTypes.INNOCENT_WOOD));
    public static final RegistryObject<Block> INNOCENT_WOOD_DOOR = BLOCKS.register("innocent_wood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion(), WizardsRebornBlockSetTypes.INNOCENT_WOOD));
    public static final RegistryObject<Block> INNOCENT_WOOD_TRAPDOOR = BLOCKS.register("innocent_wood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion(), WizardsRebornBlockSetTypes.INNOCENT_WOOD));
    public static final RegistryObject<Block> INNOCENT_WOOD_PRESSURE_PLATE = BLOCKS.register("innocent_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion().noCollission(), BlockSetType.OAK));
    public static final RegistryObject<Block> INNOCENT_WOOD_BUTTON = BLOCKS.register("innocent_wood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_BUTTON).sound(WizardsRebornSounds.INNOCENT_WOOD), WizardsRebornBlockSetTypes.INNOCENT_WOOD, 30, true));
    public static final RegistryObject<Block> INNOCENT_WOOD_SIGN = BLOCKS.register("innocent_wood_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion().noCollission(),WizardsRebornWoodTypes. INNOCENT_WOOD));
    public static final RegistryObject<Block> INNOCENT_WOOD_WALL_SIGN = BLOCKS.register("innocent_wood_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion().noCollission(), WizardsRebornWoodTypes.INNOCENT_WOOD));
    public static final RegistryObject<Block> INNOCENT_WOOD_HANGING_SIGN = BLOCKS.register("innocent_wood_hanging_sign", () -> new CustomCeilingHangingSignBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).sound(WizardsRebornSounds.INNOCENT_WOOD_HANGING_SIGN).noOcclusion().noCollission(), WizardsRebornWoodTypes.INNOCENT_WOOD));
    public static final RegistryObject<Block> INNOCENT_WOOD_WALL_HANGING_SIGN = BLOCKS.register("innocent_wood_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).sound(WizardsRebornSounds.INNOCENT_WOOD_HANGING_SIGN).noOcclusion().noCollission(), WizardsRebornWoodTypes.INNOCENT_WOOD));
    public static final RegistryObject<Block> INNOCENT_WOOD_LEAVES = BLOCKS.register("innocent_wood_leaves", () -> new InnocentWoodLeavesBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LEAVES)));
    public static final RegistryObject<Block> INNOCENT_WOOD_SAPLING = BLOCKS.register("innocent_wood_sapling", () -> new SaplingBlock(new InnocentWoodTreeGrower(), BlockBehaviour.Properties.copy(Blocks.CHERRY_SAPLING)));
    public static final RegistryObject<Block> POTTED_INNOCENT_WOOD_SAPLING = BLOCKS.register("potted_innocent_wood_sapling", () -> new FlowerPotBlock(INNOCENT_WOOD_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> PETALS_OF_INNOCENCE = BLOCKS.register("petals_of_innocence", () -> new PetalsOfInnocenceBlock(BlockBehaviour.Properties.copy(Blocks.PINK_PETALS)));
    public static final RegistryObject<Block> POTTED_PETALS_OF_INNOCENCE = BLOCKS.register("potted_petals_of_innocence", () -> new FlowerPotBlock(PETALS_OF_INNOCENCE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));

    public static final RegistryObject<Block> CORK_BAMBOO_SAPLING = BLOCKS.register("cork_bamboo_sapling", () -> new CorkBambooSaplingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().randomTicks().instabreak().noCollission().strength(1.0F).sound(WizardsRebornSounds.CORK_BAMBOO_SAPLING).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> CORK_BAMBOO = BLOCKS.register("cork_bamboo", () -> new CorkBambooStalkBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).forceSolidOn().randomTicks().instabreak().strength(1.0F).sound(WizardsRebornSounds.CORK_BAMBOO).noOcclusion().dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> POTTED_CORK_BAMBOO = BLOCKS.register("potted_cork_bamboo", () -> new FlowerPotBlock(CORK_BAMBOO.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_BLOCK = BLOCKS.register("cork_bamboo_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_BLOCK).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.CORK_BAMBOO_WOOD)));
    public static final RegistryObject<Block> CORK_BAMBOO_PLANKS = BLOCKS.register("cork_bamboo_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BAMBOO_BLOCK).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.CORK_BAMBOO_WOOD)));
    public static final RegistryObject<Block> CORK_BAMBOO_CHISELED_PLANKS = BLOCKS.register("cork_bamboo_chiseled_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BAMBOO_BLOCK).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.CORK_BAMBOO_WOOD)));
    public static final RegistryObject<Block> CORK_BAMBOO_STAIRS = BLOCKS.register("cork_bamboo_stairs", () -> new StairBlock(() -> CORK_BAMBOO_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_CHISELED_STAIRS = BLOCKS.register("cork_bamboo_chiseled_stairs", () -> new StairBlock(() -> CORK_BAMBOO_CHISELED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CORK_BAMBOO_CHISELED_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_SLAB = BLOCKS.register("cork_bamboo_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_CHISELED_SLAB = BLOCKS.register("cork_bamboo_chiseled_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_CHISELED_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_BAULK = BLOCKS.register("cork_bamboo_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_BLOCK.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_CROSS_BAULK = BLOCKS.register("cork_bamboo_cross_baulk", () -> new CrossBaulkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_BLOCK.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_PLANKS_BAULK = BLOCKS.register("cork_bamboo_planks_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_PLANKS_CROSS_BAULK = BLOCKS.register("cork_bamboo_planks_cross_baulk", () -> new CrossBaulkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_CHISELED_PLANKS_BAULK = BLOCKS.register("cork_bamboo_chiseled_planks_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_CHISELED_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK = BLOCKS.register("cork_bamboo_chiseled_planks_cross_baulk", () -> new CrossBaulkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_CHISELED_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_FENCE = BLOCKS.register("cork_bamboo_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_FENCE_GATE = BLOCKS.register("cork_bamboo_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()), WizardsRebornWoodTypes.CORK_BAMBOO));
    public static final RegistryObject<Block> CORK_BAMBOO_DOOR = BLOCKS.register("cork_bamboo_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion(), WizardsRebornBlockSetTypes.CORK_BAMBOO));
    public static final RegistryObject<Block> CORK_BAMBOO_TRAPDOOR = BLOCKS.register("cork_bamboo_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion(), WizardsRebornBlockSetTypes.CORK_BAMBOO));
    public static final RegistryObject<Block> CORK_BAMBOO_PRESSURE_PLATE = BLOCKS.register("cork_bamboo_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion().noCollission(), WizardsRebornBlockSetTypes.CORK_BAMBOO));
    public static final RegistryObject<Block> CORK_BAMBOO_BUTTON = BLOCKS.register("cork_bamboo_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(WizardsRebornSounds.CORK_BAMBOO_WOOD), WizardsRebornBlockSetTypes.CORK_BAMBOO, 30, true));
    public static final RegistryObject<Block> CORK_BAMBOO_SIGN = BLOCKS.register("cork_bamboo_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion().noCollission(), WizardsRebornWoodTypes.CORK_BAMBOO));
    public static final RegistryObject<Block> CORK_BAMBOO_WALL_SIGN = BLOCKS.register("cork_bamboo_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion().noCollission(), WizardsRebornWoodTypes.CORK_BAMBOO));
    public static final RegistryObject<Block> CORK_BAMBOO_HANGING_SIGN = BLOCKS.register("cork_bamboo_hanging_sign", () -> new CustomCeilingHangingSignBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).sound(WizardsRebornSounds.CORK_BAMBOO_HANGING_SIGN).noOcclusion().noCollission(), WizardsRebornWoodTypes.CORK_BAMBOO));
    public static final RegistryObject<Block> CORK_BAMBOO_WALL_HANGING_SIGN = BLOCKS.register("cork_bamboo_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).sound(WizardsRebornSounds.CORK_BAMBOO_HANGING_SIGN).noOcclusion().noCollission(), WizardsRebornWoodTypes.CORK_BAMBOO));

    //STONE
    public static final RegistryObject<Block> WISESTONE = BLOCKS.register("wisestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.WISESTONE)));
    public static final RegistryObject<Block> WISESTONE_STAIRS = BLOCKS.register("wisestone_stairs", () -> new StairBlock(() -> WISESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(WISESTONE.get())));
    public static final RegistryObject<Block> WISESTONE_SLAB = BLOCKS.register("wisestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WISESTONE.get())));
    public static final RegistryObject<Block> WISESTONE_WALL = BLOCKS.register("wisestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(WISESTONE.get())));
    public static final RegistryObject<Block> POLISHED_WISESTONE = BLOCKS.register("polished_wisestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.POLISHED_WISESTONE)));
    public static final RegistryObject<Block> POLISHED_WISESTONE_STAIRS = BLOCKS.register("polished_wisestone_stairs", () -> new StairBlock(() -> POLISHED_WISESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> POLISHED_WISESTONE_SLAB = BLOCKS.register("polished_wisestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> POLISHED_WISESTONE_WALL = BLOCKS.register("polished_wisestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> WISESTONE_BRICKS = BLOCKS.register("wisestone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.WISESTONE_BRICKS)));
    public static final RegistryObject<Block> WISESTONE_BRICKS_STAIRS = BLOCKS.register("wisestone_bricks_stairs", () -> new StairBlock(() -> WISESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(WISESTONE_BRICKS.get())));
    public static final RegistryObject<Block> WISESTONE_BRICKS_SLAB = BLOCKS.register("wisestone_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WISESTONE_BRICKS.get())));
    public static final RegistryObject<Block> WISESTONE_BRICKS_WALL = BLOCKS.register("wisestone_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(WISESTONE_BRICKS.get())));
    public static final RegistryObject<Block> WISESTONE_TILE = BLOCKS.register("wisestone_tile", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_TILES).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.WISESTONE_TILE)));
    public static final RegistryObject<Block> WISESTONE_TILE_STAIRS = BLOCKS.register("wisestone_tile_stairs", () -> new StairBlock(() -> WISESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(WISESTONE_TILE.get())));
    public static final RegistryObject<Block> WISESTONE_TILE_SLAB = BLOCKS.register("wisestone_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WISESTONE_TILE.get())));
    public static final RegistryObject<Block> WISESTONE_TILE_WALL = BLOCKS.register("wisestone_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(WISESTONE_TILE.get())));
    public static final RegistryObject<Block> CHISELED_WISESTONE = BLOCKS.register("chiseled_wisestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.CHISELED_WISESTONE)));
    public static final RegistryObject<Block> CHISELED_WISESTONE_STAIRS = BLOCKS.register("chiseled_wisestone_stairs", () -> new StairBlock(() -> POLISHED_WISESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(CHISELED_WISESTONE.get())));
    public static final RegistryObject<Block> CHISELED_WISESTONE_SLAB = BLOCKS.register("chiseled_wisestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CHISELED_WISESTONE.get())));
    public static final RegistryObject<Block> CHISELED_WISESTONE_WALL = BLOCKS.register("chiseled_wisestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(CHISELED_WISESTONE.get())));
    public static final RegistryObject<Block> WISESTONE_PILLAR = BLOCKS.register("wisestone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> POLISHED_WISESTONE_PRESSURE_PLATE = BLOCKS.register("polished_wisestone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.POLISHED_WISESTONE).noOcclusion().noCollission(), WizardsRebornBlockSetTypes.WISESTONE));
    public static final RegistryObject<Block> POLISHED_WISESTONE_BUTTON = BLOCKS.register("polished_wisestone_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BUTTON).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.POLISHED_WISESTONE), WizardsRebornBlockSetTypes.WISESTONE, 20, false));

    //CRYSTALS
    public static final RegistryObject<Block> ARCANUM_SEED = BLOCKS.register("arcanum_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_LIGHT_BLUE).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> ARCANUM_GROWTH = BLOCKS.register("arcanum_growth", () -> new ArcanumGrowthBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_LIGHT_BLUE).sound(WizardsRebornSounds.CRYSTAL)));

    public static final RegistryObject<Block> EARTH_CRYSTAL_SEED = BLOCKS.register("earth_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_GREEN).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> WATER_CRYSTAL_SEED = BLOCKS.register("water_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_BLUE).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> AIR_CRYSTAL_SEED = BLOCKS.register("air_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_YELLOW).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> FIRE_CRYSTAL_SEED = BLOCKS.register("fire_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_RED).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> VOID_CRYSTAL_SEED = BLOCKS.register("void_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_PURPLE).sound(WizardsRebornSounds.CRYSTAL)));

    public static final RegistryObject<Block> EARTH_CRYSTAL_GROWTH = BLOCKS.register("earth_crystal_growth", () -> new CrystalGrowthBlock(WizardsRebornCrystals.EARTH, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_GREEN).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> WATER_CRYSTAL_GROWTH = BLOCKS.register("water_crystal_growth", () -> new CrystalGrowthBlock(WizardsRebornCrystals.WATER, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_BLUE).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> AIR_CRYSTAL_GROWTH = BLOCKS.register("air_crystal_growth", () -> new CrystalGrowthBlock(WizardsRebornCrystals.AIR, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_YELLOW).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> FIRE_CRYSTAL_GROWTH = BLOCKS.register("fire_crystal_growth", () -> new CrystalGrowthBlock(WizardsRebornCrystals.FIRE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_RED).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> VOID_CRYSTAL_GROWTH = BLOCKS.register("void_crystal_growth", () -> new CrystalGrowthBlock(WizardsRebornCrystals.VOID, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_PURPLE).sound(WizardsRebornSounds.CRYSTAL)));

    public static final RegistryObject<Block> EARTH_CRYSTAL = BLOCKS.register("earth_crystal", () -> new CrystalBlock(WizardsRebornCrystals.EARTH, WizardsRebornCrystals.CRYSTAL, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_GREEN).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> WATER_CRYSTAL = BLOCKS.register("water_crystal", () -> new CrystalBlock(WizardsRebornCrystals.WATER, WizardsRebornCrystals.CRYSTAL, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_BLUE).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> AIR_CRYSTAL = BLOCKS.register("air_crystal", () -> new CrystalBlock(WizardsRebornCrystals.AIR, WizardsRebornCrystals.CRYSTAL, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_YELLOW).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> FIRE_CRYSTAL = BLOCKS.register("fire_crystal", () -> new CrystalBlock(WizardsRebornCrystals.FIRE, WizardsRebornCrystals.CRYSTAL, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_RED).sound(WizardsRebornSounds.CRYSTAL)));
    public static final RegistryObject<Block> VOID_CRYSTAL = BLOCKS.register("void_crystal", () -> new CrystalBlock(WizardsRebornCrystals.VOID, WizardsRebornCrystals.CRYSTAL, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_PURPLE).sound(WizardsRebornSounds.CRYSTAL)));

    public static final RegistryObject<Block> FACETED_EARTH_CRYSTAL = BLOCKS.register("faceted_earth_crystal", () -> new CrystalBlock(WizardsRebornCrystals.EARTH, WizardsRebornCrystals.FACETED, BlockBehaviour.Properties.copy(EARTH_CRYSTAL.get())));
    public static final RegistryObject<Block> FACETED_WATER_CRYSTAL = BLOCKS.register("faceted_water_crystal", () -> new CrystalBlock(WizardsRebornCrystals.WATER, WizardsRebornCrystals.FACETED, BlockBehaviour.Properties.copy(WATER_CRYSTAL.get())));
    public static final RegistryObject<Block> FACETED_AIR_CRYSTAL = BLOCKS.register("faceted_air_crystal", () -> new CrystalBlock(WizardsRebornCrystals.AIR, WizardsRebornCrystals.FACETED, BlockBehaviour.Properties.copy(AIR_CRYSTAL.get())));
    public static final RegistryObject<Block> FACETED_FIRE_CRYSTAL = BLOCKS.register("faceted_fire_crystal", () -> new CrystalBlock(WizardsRebornCrystals.FIRE, WizardsRebornCrystals.FACETED, BlockBehaviour.Properties.copy(FIRE_CRYSTAL.get())));
    public static final RegistryObject<Block> FACETED_VOID_CRYSTAL = BLOCKS.register("faceted_void_crystal", () -> new CrystalBlock(WizardsRebornCrystals.VOID, WizardsRebornCrystals.FACETED, BlockBehaviour.Properties.copy(VOID_CRYSTAL.get())));

    public static final RegistryObject<Block> ADVANCED_EARTH_CRYSTAL = BLOCKS.register("advanced_earth_crystal", () -> new CrystalBlock(WizardsRebornCrystals.EARTH, WizardsRebornCrystals.ADVANCED, BlockBehaviour.Properties.copy(EARTH_CRYSTAL.get())));
    public static final RegistryObject<Block> ADVANCED_WATER_CRYSTAL = BLOCKS.register("advanced_water_crystal", () -> new CrystalBlock(WizardsRebornCrystals.WATER, WizardsRebornCrystals.ADVANCED, BlockBehaviour.Properties.copy(WATER_CRYSTAL.get())));
    public static final RegistryObject<Block> ADVANCED_AIR_CRYSTAL = BLOCKS.register("advanced_air_crystal", () -> new CrystalBlock(WizardsRebornCrystals.AIR, WizardsRebornCrystals.ADVANCED, BlockBehaviour.Properties.copy(AIR_CRYSTAL.get())));
    public static final RegistryObject<Block> ADVANCED_FIRE_CRYSTAL = BLOCKS.register("advanced_fire_crystal", () -> new CrystalBlock(WizardsRebornCrystals.FIRE, WizardsRebornCrystals.ADVANCED, BlockBehaviour.Properties.copy(FIRE_CRYSTAL.get())));
    public static final RegistryObject<Block> ADVANCED_VOID_CRYSTAL = BLOCKS.register("advanced_void_crystal", () -> new CrystalBlock(WizardsRebornCrystals.VOID, WizardsRebornCrystals.ADVANCED, BlockBehaviour.Properties.copy(VOID_CRYSTAL.get())));

    public static final RegistryObject<Block> MASTERFUL_EARTH_CRYSTAL = BLOCKS.register("masterful_earth_crystal", () -> new CrystalBlock(WizardsRebornCrystals.EARTH, WizardsRebornCrystals.MASTERFUL, BlockBehaviour.Properties.copy(EARTH_CRYSTAL.get())));
    public static final RegistryObject<Block> MASTERFUL_WATER_CRYSTAL = BLOCKS.register("masterful_water_crystal", () -> new CrystalBlock(WizardsRebornCrystals.WATER, WizardsRebornCrystals.MASTERFUL, BlockBehaviour.Properties.copy(WATER_CRYSTAL.get())));
    public static final RegistryObject<Block> MASTERFUL_AIR_CRYSTAL = BLOCKS.register("masterful_air_crystal", () -> new CrystalBlock(WizardsRebornCrystals.AIR, WizardsRebornCrystals.MASTERFUL, BlockBehaviour.Properties.copy(AIR_CRYSTAL.get())));
    public static final RegistryObject<Block> MASTERFUL_FIRE_CRYSTAL = BLOCKS.register("masterful_fire_crystal", () -> new CrystalBlock(WizardsRebornCrystals.FIRE, WizardsRebornCrystals.MASTERFUL, BlockBehaviour.Properties.copy(FIRE_CRYSTAL.get())));
    public static final RegistryObject<Block> MASTERFUL_VOID_CRYSTAL = BLOCKS.register("masterful_void_crystal", () -> new CrystalBlock(WizardsRebornCrystals.VOID, WizardsRebornCrystals.MASTERFUL, BlockBehaviour.Properties.copy(VOID_CRYSTAL.get())));

    public static final RegistryObject<Block> PURE_EARTH_CRYSTAL = BLOCKS.register("pure_earth_crystal", () -> new CrystalBlock(WizardsRebornCrystals.EARTH, WizardsRebornCrystals.PURE, BlockBehaviour.Properties.copy(EARTH_CRYSTAL.get())));
    public static final RegistryObject<Block> PURE_WATER_CRYSTAL = BLOCKS.register("pure_water_crystal", () -> new CrystalBlock(WizardsRebornCrystals.WATER, WizardsRebornCrystals.PURE, BlockBehaviour.Properties.copy(WATER_CRYSTAL.get())));
    public static final RegistryObject<Block> PURE_AIR_CRYSTAL = BLOCKS.register("pure_air_crystal", () -> new CrystalBlock(WizardsRebornCrystals.AIR, WizardsRebornCrystals.PURE, BlockBehaviour.Properties.copy(AIR_CRYSTAL.get())));
    public static final RegistryObject<Block> PURE_FIRE_CRYSTAL = BLOCKS.register("pure_fire_crystal", () -> new CrystalBlock(WizardsRebornCrystals.FIRE, WizardsRebornCrystals.PURE, BlockBehaviour.Properties.copy(FIRE_CRYSTAL.get())));
    public static final RegistryObject<Block> PURE_VOID_CRYSTAL = BLOCKS.register("pure_void_crystal", () -> new CrystalBlock(WizardsRebornCrystals.VOID, WizardsRebornCrystals.PURE, BlockBehaviour.Properties.copy(VOID_CRYSTAL.get())));

    //LUMOS
    public static final RegistryObject<Block> WHITE_ARCANE_LUMOS = BLOCKS.register("white_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.WHITE, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> LIGHT_GRAY_ARCANE_LUMOS = BLOCKS.register("light_gray_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.LIGHT_GRAY, BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> GRAY_ARCANE_LUMOS = BLOCKS.register("gray_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.GRAY, BlockBehaviour.Properties.copy(Blocks.GRAY_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> BLACK_ARCANE_LUMOS = BLOCKS.register("black_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.BLACK, BlockBehaviour.Properties.copy(Blocks.BLACK_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> BROWN_ARCANE_LUMOS = BLOCKS.register("brown_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.BROWN, BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> RED_ARCANE_LUMOS = BLOCKS.register("red_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.RED, BlockBehaviour.Properties.copy(Blocks.RED_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> ORANGE_ARCANE_LUMOS = BLOCKS.register("orange_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.ORANGE, BlockBehaviour.Properties.copy(Blocks.ORANGE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> YELLOW_ARCANE_LUMOS = BLOCKS.register("yellow_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.YELLOW, BlockBehaviour.Properties.copy(Blocks.YELLOW_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> LIME_ARCANE_LUMOS = BLOCKS.register("lime_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.LIME, BlockBehaviour.Properties.copy(Blocks.LIME_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> GREEN_ARCANE_LUMOS = BLOCKS.register("green_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.GREEN, BlockBehaviour.Properties.copy(Blocks.GREEN_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> CYAN_ARCANE_LUMOS = BLOCKS.register("cyan_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.CYAN, BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> LIGHT_BLUE_ARCANE_LUMOS = BLOCKS.register("light_blue_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.LIGHT_BLUE, BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> BLUE_ARCANE_LUMOS = BLOCKS.register("blue_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.BLUE, BlockBehaviour.Properties.copy(Blocks.BLUE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> PURPLE_ARCANE_LUMOS = BLOCKS.register("purple_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.PURPLE, BlockBehaviour.Properties.copy(Blocks.PURPLE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> MAGENTA_ARCANE_LUMOS = BLOCKS.register("magenta_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.MAGENTA, BlockBehaviour.Properties.copy(Blocks.MAGENTA_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> PINK_ARCANE_LUMOS = BLOCKS.register("pink_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.PINK, BlockBehaviour.Properties.copy(Blocks.PINK_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> RAINBOW_ARCANE_LUMOS = BLOCKS.register("rainbow_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.RAINBOW, BlockBehaviour.Properties.copy(Blocks.PINK_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> COSMIC_ARCANE_LUMOS = BLOCKS.register("cosmic_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.COSMIC, BlockBehaviour.Properties.copy(Blocks.MAGENTA_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));

    public static final RegistryObject<Block> WHITE_LUMINAL_GLASS = BLOCKS.register("white_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIGHT_GRAY_LUMINAL_GLASS = BLOCKS.register("light_gray_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GRAY_LUMINAL_GLASS = BLOCKS.register("gray_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_GRAY).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BLACK_LUMINAL_GLASS = BLOCKS.register("black_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BLACK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BROWN_LUMINAL_GLASS = BLOCKS.register("brown_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BROWN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> RED_LUMINAL_GLASS = BLOCKS.register("red_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_RED).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> ORANGE_LUMINAL_GLASS = BLOCKS.register("orange_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_ORANGE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> YELLOW_LUMINAL_GLASS = BLOCKS.register("yellow_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_YELLOW).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIME_LUMINAL_GLASS = BLOCKS.register("lime_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GREEN_LUMINAL_GLASS = BLOCKS.register("green_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_GREEN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> CYAN_LUMINAL_GLASS = BLOCKS.register("cyan_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_CYAN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIGHT_BLUE_LUMINAL_GLASS = BLOCKS.register("light_blue_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BLUE_LUMINAL_GLASS = BLOCKS.register("blue_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BLUE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> PURPLE_LUMINAL_GLASS = BLOCKS.register("purple_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PURPLE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> MAGENTA_LUMINAL_GLASS = BLOCKS.register("magenta_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_MAGENTA).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> PINK_LUMINAL_GLASS = BLOCKS.register("pink_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PINK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> RAINBOW_LUMINAL_GLASS = BLOCKS.register("rainbow_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PINK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> COSMIC_LUMINAL_GLASS = BLOCKS.register("cosmic_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_MAGENTA).noOcclusion().lightLevel((state) -> 8)));

    public static final RegistryObject<Block> WHITE_FRAMED_LUMINAL_GLASS = BLOCKS.register("white_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIGHT_GRAY_FRAMED_LUMINAL_GLASS = BLOCKS.register("light_gray_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GRAY_FRAMED_LUMINAL_GLASS = BLOCKS.register("gray_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_GRAY).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BLACK_FRAMED_LUMINAL_GLASS = BLOCKS.register("black_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BLACK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BROWN_FRAMED_LUMINAL_GLASS = BLOCKS.register("brown_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BROWN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> RED_FRAMED_LUMINAL_GLASS = BLOCKS.register("red_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_RED).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> ORANGE_FRAMED_LUMINAL_GLASS = BLOCKS.register("orange_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_ORANGE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> YELLOW_FRAMED_LUMINAL_GLASS = BLOCKS.register("yellow_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_YELLOW).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIME_FRAMED_LUMINAL_GLASS = BLOCKS.register("lime_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GREEN_FRAMED_LUMINAL_GLASS = BLOCKS.register("green_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_GREEN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> CYAN_FRAMED_LUMINAL_GLASS = BLOCKS.register("cyan_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_CYAN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIGHT_BLUE_FRAMED_LUMINAL_GLASS = BLOCKS.register("light_blue_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BLUE_FRAMED_LUMINAL_GLASS = BLOCKS.register("blue_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BLUE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> PURPLE_FRAMED_LUMINAL_GLASS = BLOCKS.register("purple_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PURPLE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> MAGENTA_FRAMED_LUMINAL_GLASS = BLOCKS.register("magenta_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_MAGENTA).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> PINK_FRAMED_LUMINAL_GLASS = BLOCKS.register("pink_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PINK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> RAINBOW_FRAMED_LUMINAL_GLASS = BLOCKS.register("rainbow_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PINK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> COSMIC_FRAMED_LUMINAL_GLASS = BLOCKS.register("cosmic_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_MAGENTA).noOcclusion().lightLevel((state) -> 8)));

    //BUILD
    public static final RegistryObject<Block> ARCANE_PEDESTAL = BLOCKS.register("arcane_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_PEDESTAL = BLOCKS.register("innocent_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_PEDESTAL = BLOCKS.register("cork_bamboo_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> WISESTONE_PEDESTAL = BLOCKS.register("wisestone_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));

    public static final RegistryObject<Block> GILDED_ARCANE_WOOD_PLANKS = BLOCKS.register("gilded_arcane_wood_planks", () -> new Block(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> GILDED_INNOCENT_WOOD_PLANKS = BLOCKS.register("gilded_innocent_wood_planks", () -> new Block(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> GILDED_CORK_BAMBOO_PLANKS = BLOCKS.register("gilded_cork_bamboo_planks", () -> new Block(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> GILDED_CORK_BAMBOO_CHISELED_PLANKS = BLOCKS.register("gilded_cork_bamboo_chiseled_planks", () -> new Block(BlockBehaviour.Properties.copy(CORK_BAMBOO_CHISELED_PLANKS.get())));
    public static final RegistryObject<Block> GILDED_POLISHED_WISESTONE = BLOCKS.register("gilded_polished_wisestone", () -> new Block(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));

    public static final RegistryObject<Block> ARCANE_SALT_TORCH = BLOCKS.register("arcane_salt_torch", () -> new SaltTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> ARCANE_SALT_WALL_TORCH = BLOCKS.register("arcane_salt_wall_torch", () -> new SaltWallTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> ARCANE_SALT_LANTERN = BLOCKS.register("arcane_salt_lantern", () -> new SaltLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> ARCANE_SALT_CAMPFIRE = BLOCKS.register("arcane_salt_campfire", () -> new SaltCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> INNOCENT_SALT_TORCH = BLOCKS.register("innocent_salt_torch", () -> new SaltTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.TERRACOTTA_GRAY).sound(WizardsRebornSounds.INNOCENT_WOOD)));
    public static final RegistryObject<Block> INNOCENT_SALT_WALL_TORCH = BLOCKS.register("innocent_salt_wall_torch", () -> new SaltWallTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.TERRACOTTA_GRAY).sound(WizardsRebornSounds.INNOCENT_WOOD)));
    public static final RegistryObject<Block> INNOCENT_SALT_LANTERN = BLOCKS.register("innocent_salt_lantern", () -> new SaltLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.TERRACOTTA_GRAY).sound(WizardsRebornSounds.INNOCENT_WOOD)));
    public static final RegistryObject<Block> INNOCENT_SALT_CAMPFIRE = BLOCKS.register("innocent_salt_campfire", () -> new SaltCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.TERRACOTTA_GRAY).sound(WizardsRebornSounds.INNOCENT_WOOD)));
    public static final RegistryObject<Block> CORK_BAMBOO_SALT_TORCH = BLOCKS.register("cork_bamboo_salt_torch", () -> new SaltTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> CORK_BAMBOO_SALT_WALL_TORCH = BLOCKS.register("cork_bamboo_salt_wall_torch", () -> new SaltWallTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> CORK_BAMBOO_SALT_LANTERN = BLOCKS.register("cork_bamboo_salt_lantern", () -> new SaltLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> CORK_BAMBOO_SALT_CAMPFIRE = BLOCKS.register("cork_bamboo_salt_campfire", () -> new SaltCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.PODZOL).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> WISESTONE_SALT_TORCH = BLOCKS.register("wisestone_salt_torch", () -> new SaltTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.POLISHED_WISESTONE)));
    public static final RegistryObject<Block> WISESTONE_SALT_WALL_TORCH = BLOCKS.register("wisestone_salt_wall_torch", () -> new SaltWallTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.POLISHED_WISESTONE)));
    public static final RegistryObject<Block> WISESTONE_SALT_LANTERN = BLOCKS.register("wisestone_salt_lantern", () -> new SaltLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.POLISHED_WISESTONE)));
    public static final RegistryObject<Block> WISESTONE_SALT_CAMPFIRE = BLOCKS.register("wisestone_salt_campfire", () -> new SaltCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).mapColor(MapColor.TERRACOTTA_BLACK).sound(WizardsRebornSounds.POLISHED_WISESTONE)));

    //ARCANE_NATURE
    public static final RegistryObject<Block> WISSEN_ALTAR = BLOCKS.register("wissen_altar", () -> new WissenAltarBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_TRANSLATOR = BLOCKS.register("wissen_translator", () -> new WissenTranslatorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_CRYSTALLIZER = BLOCKS.register("wissen_crystallizer", () -> new WissenCrystallizerBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WORKBENCH = BLOCKS.register("arcane_workbench", () -> new ArcaneWorkbenchBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_CELL = BLOCKS.register("wissen_cell", () -> new WissenCellBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> JEWELER_TABLE = BLOCKS.register("jeweler_table", () -> new JewelerTableBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ALTAR_OF_DROUGHT = BLOCKS.register("altar_of_drought", () -> new AltarOfDroughtBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> TOTEM_BASE = BLOCKS.register("totem_base", () -> new TotemBaseBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> TOTEM_OF_FLAMES = BLOCKS.register("totem_of_flames", () -> new TotemOfFlamesBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).lightLevel(TotemOfFlamesBlock::getLightLevel)));
    public static final RegistryObject<Block> EXPERIENCE_TOTEM = BLOCKS.register("experience_totem", () -> new ExperienceTotemBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> TOTEM_OF_EXPERIENCE_ABSORPTION = BLOCKS.register("totem_of_experience_absorption", () -> new TotemOfExperienceAbsorptionBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> TOTEM_OF_DISENCHANT = BLOCKS.register("totem_of_disenchant", () -> new TotemOfDisenchantBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_ITERATOR = BLOCKS.register("arcane_iterator", () -> new ArcaneIteratorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));

    //ALCHEMY
    public static final RegistryObject<Block> FLUID_PIPE = BLOCKS.register("fluid_pipe", () -> new FluidPipeBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> FLUID_EXTRACTOR = BLOCKS.register("fluid_extractor", () -> new FluidExtractorBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> STEAM_PIPE = BLOCKS.register("steam_pipe", () -> new SteamPipeBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> STEAM_EXTRACTOR = BLOCKS.register("steam_extractor", () -> new SteamExtractorBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> ALCHEMY_FURNACE = BLOCKS.register("alchemy_furnace", () -> new AlchemyFurnaceBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 13 : 0)));
    public static final RegistryObject<Block> ORBITAL_FLUID_RETAINER = BLOCKS.register("orbital_fluid_retainer", () -> new OrbitalFluidRetainerBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> STEAM_THERMAL_STORAGE = BLOCKS.register("steam_thermal_storage", () -> new SteamThermalStorageBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> ALCHEMY_MACHINE = BLOCKS.register("alchemy_machine", () -> new AlchemyMachineBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> ALCHEMY_BOILER = BLOCKS.register("alchemy_boiler", () -> new AlchemyBoilerBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> ARCANE_CENSER = BLOCKS.register("arcane_censer", () -> new ArcaneCenserBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));

    public static final RegistryObject<Block> ALCHEMY_GLASS = BLOCKS.register("alchemy_glass", () -> new TintedGlassBlock(BlockBehaviour.Properties.copy(Blocks.TINTED_GLASS).mapColor(MapColor.COLOR_LIGHT_GRAY).noOcclusion()));

    public static final RegistryObject<Block> SNIFFALO_EGG = BLOCKS.register("sniffalo_egg", () -> new SniffaloEggBlock(BlockBehaviour.Properties.copy(Blocks.SNIFFER_EGG).mapColor(MapColor.COLOR_BROWN)));

    //CRYSTAL_RITUALS
    public static final RegistryObject<Block> LIGHT_EMITTER = BLOCKS.register("light_emitter", () -> new LightEmitterBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> LIGHT_TRANSFER_LENS = BLOCKS.register("light_transfer_lens", () -> new LightTransferLensBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> RUNIC_PEDESTAL = BLOCKS.register("runic_pedestal", () -> new RunicPedestalBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));

    public static final RegistryObject<Block> ENGRAVED_WISESTONE = BLOCKS.register("engraved_wisestone", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_LUNAM = BLOCKS.register("engraved_wisestone_lunam", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.LUNAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_VITA = BLOCKS.register("engraved_wisestone_vita", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.VITA));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_SOLEM = BLOCKS.register("engraved_wisestone_solem", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.SOLEM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_MORS = BLOCKS.register("engraved_wisestone_mors", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.MORS));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_MIRACULUM = BLOCKS.register("engraved_wisestone_miraculum", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.MIRACULUM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_TEMPUS = BLOCKS.register("engraved_wisestone_tempus", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.TEMPUS));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_STATERA = BLOCKS.register("engraved_wisestone_statera", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.STATERA));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_ECLIPSIS = BLOCKS.register("engraved_wisestone_eclipsis", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.ECLIPSIS));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_SICCITAS = BLOCKS.register("engraved_wisestone_siccitas", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.SICCITAS));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_SOLSTITIUM = BLOCKS.register("engraved_wisestone_solstitium", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.SOLSTITIUM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_FAMES = BLOCKS.register("engraved_wisestone_fames", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.FAMES));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_RENAISSANCE = BLOCKS.register("engraved_wisestone_renaissance", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.RENAISSANCE));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_BELLUM = BLOCKS.register("engraved_wisestone_bellum", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.BELLUM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_LUX = BLOCKS.register("engraved_wisestone_lux", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.LUX));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_KARA = BLOCKS.register("engraved_wisestone_kara", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.KARA));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_DEGRADATIO = BLOCKS.register("engraved_wisestone_degradatio", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.DEGRADATIO));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_PRAEDICTIONEM = BLOCKS.register("engraved_wisestone_praedictionem", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.PRAEDICTIONEM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_EVOLUTIONIS = BLOCKS.register("engraved_wisestone_evolutionis", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.EVOLUTIONIS));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_TENEBRIS = BLOCKS.register("engraved_wisestone_tenebris", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.TENEBRIS));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_UNIVERSUM = BLOCKS.register("engraved_wisestone_universum", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), WizardsRebornMonograms.UNIVERSUM));

    //AUTOMATION
    public static final RegistryObject<Block> ARCANE_LEVER = BLOCKS.register("arcane_lever", () -> new WaterloggableLeverBlock(BlockBehaviour.Properties.copy(Blocks.LEVER).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> ARCANE_HOPPER = BLOCKS.register("arcane_hopper", () -> new ArcaneHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER).sound(WizardsRebornSounds.ARCANE_WOOD)));
    public static final RegistryObject<Block> REDSTONE_SENSOR = BLOCKS.register("redstone_sensor", () -> new RedstoneSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_SENSOR = BLOCKS.register("wissen_sensor", () -> new WissenSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> COOLDOWN_SENSOR = BLOCKS.register("cooldown_sensor", () -> new CooldownSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> EXPERIENCE_SENSOR = BLOCKS.register("experience_sensor", () -> new ExperienceSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> LIGHT_SENSOR = BLOCKS.register("light_sensor", () -> new LightSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> HEAT_SENSOR = BLOCKS.register("heat_sensor", () -> new HeatSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> FLUID_SENSOR = BLOCKS.register("fluid_sensor", () -> new FluidSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> STEAM_SENSOR = BLOCKS.register("steam_sensor", () -> new SteamSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_ACTIVATOR = BLOCKS.register("wissen_activator", () -> new WissenActivatorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ITEM_SORTER = BLOCKS.register("item_sorter", () -> new ItemSorterBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));

    public static final RegistryObject<Block> ARCANE_WOOD_FRAME = BLOCKS.register("arcane_wood_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_GLASS_FRAME = BLOCKS.register("arcane_wood_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_CASING = BLOCKS.register("arcane_wood_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_WISSEN_CASING = BLOCKS.register("arcane_wood_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_LIGHT_CASING = BLOCKS.register("arcane_wood_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_FLUID_CASING = BLOCKS.register("arcane_wood_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_STEAM_CASING = BLOCKS.register("arcane_wood_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_FRAME = BLOCKS.register("innocent_wood_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_GLASS_FRAME = BLOCKS.register("innocent_wood_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_CASING = BLOCKS.register("innocent_wood_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_WISSEN_CASING = BLOCKS.register("innocent_wood_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_LIGHT_CASING = BLOCKS.register("innocent_wood_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_FLUID_CASING = BLOCKS.register("innocent_wood_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_STEAM_CASING = BLOCKS.register("innocent_wood_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_FRAME = BLOCKS.register("cork_bamboo_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_GLASS_FRAME = BLOCKS.register("cork_bamboo_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_CASING = BLOCKS.register("cork_bamboo_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_WISSEN_CASING = BLOCKS.register("cork_bamboo_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_LIGHT_CASING = BLOCKS.register("cork_bamboo_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_FLUID_CASING = BLOCKS.register("cork_bamboo_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_STEAM_CASING = BLOCKS.register("cork_bamboo_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_FRAME = BLOCKS.register("wisestone_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_GLASS_FRAME = BLOCKS.register("wisestone_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_CASING = BLOCKS.register("wisestone_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_WISSEN_CASING = BLOCKS.register("wisestone_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_LIGHT_CASING = BLOCKS.register("wisestone_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_FLUID_CASING = BLOCKS.register("wisestone_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_STEAM_CASING = BLOCKS.register("wisestone_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));

    public static final RegistryObject<Block> CREATIVE_WISSEN_STORAGE = BLOCKS.register("creative_wissen_storage", () -> new CreativeWissenStorageBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> CREATIVE_LIGHT_STORAGE = BLOCKS.register("creative_light_storage", () -> new CreativeLightStorageBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> CREATIVE_FLUID_STORAGE = BLOCKS.register("creative_fluid_storage", () -> new CreativeFluidStorageBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> CREATIVE_STEAM_STORAGE = BLOCKS.register("creative_steam_storage", () -> new CreativeSteamStorageBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));

    //EQUIPMENT
    public static final RegistryObject<Block> WHITE_CARGO_CARPET = BLOCKS.register("white_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).noOcclusion()));
    public static final RegistryObject<Block> LIGHT_GRAY_CARGO_CARPET = BLOCKS.register("light_gray_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_WOOL).noOcclusion()));
    public static final RegistryObject<Block> GRAY_CARGO_CARPET = BLOCKS.register("gray_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_WOOL).noOcclusion()));
    public static final RegistryObject<Block> BLACK_CARGO_CARPET = BLOCKS.register("black_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_WOOL).noOcclusion()));
    public static final RegistryObject<Block> BROWN_CARGO_CARPET = BLOCKS.register("brown_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).noOcclusion()));
    public static final RegistryObject<Block> RED_CARGO_CARPET = BLOCKS.register("red_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.RED_WOOL).noOcclusion()));
    public static final RegistryObject<Block> ORANGE_CARGO_CARPET = BLOCKS.register("orange_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.ORANGE_WOOL).noOcclusion()));
    public static final RegistryObject<Block> YELLOW_CARGO_CARPET = BLOCKS.register("yellow_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.YELLOW_WOOL).noOcclusion()));
    public static final RegistryObject<Block> LIME_CARGO_CARPET = BLOCKS.register("lime_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.LIME_WOOL).noOcclusion()));
    public static final RegistryObject<Block> GREEN_CARGO_CARPET = BLOCKS.register("green_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.GREEN_WOOL).noOcclusion()));
    public static final RegistryObject<Block> CYAN_CARGO_CARPET = BLOCKS.register("cyan_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).noOcclusion()));
    public static final RegistryObject<Block> LIGHT_BLUE_CARGO_CARPET = BLOCKS.register("light_blue_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_WOOL).noOcclusion()));
    public static final RegistryObject<Block> BLUE_CARGO_CARPET = BLOCKS.register("blue_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_WOOL).noOcclusion()));
    public static final RegistryObject<Block> PURPLE_CARGO_CARPET = BLOCKS.register("purple_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.PURPLE_WOOL).noOcclusion()));
    public static final RegistryObject<Block> MAGENTA_CARGO_CARPET = BLOCKS.register("magenta_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.MAGENTA_WOOL).noOcclusion()));
    public static final RegistryObject<Block> PINK_CARGO_CARPET = BLOCKS.register("pink_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.PINK_WOOL).noOcclusion()));
    public static final RegistryObject<Block> RAINBOW_CARGO_CARPET = BLOCKS.register("rainbow_cargo_carpet", () -> new CargoCarpetBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).noOcclusion()));

    //FLUIDS
    public static final RegistryObject<LiquidBlock> MUNDANE_BREW = BLOCKS.register("mundane_brew", () -> new LiquidBlock(WizardsRebornFluids.MUNDANE_BREW, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> ALCHEMY_OIL = BLOCKS.register("alchemy_oil", () -> new LiquidBlock(WizardsRebornFluids.ALCHEMY_OIL, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> OIL_TEA = BLOCKS.register("oil_tea", () -> new LiquidBlock(WizardsRebornFluids.OIL_TEA, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> WISSEN_TEA = BLOCKS.register("wissen_tea", () -> new LiquidBlock(WizardsRebornFluids.WISSEN_TEA, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> MILK_TEA = BLOCKS.register("milk_tea", () -> new LiquidBlock(WizardsRebornFluids.MILK_TEA, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> MUSHROOM_BREW = BLOCKS.register("mushroom_brew", () -> new LiquidBlock(WizardsRebornFluids.MUSHROOM_BREW, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> HELLISH_MUSHROOM_BREW = BLOCKS.register("hellish_mushroom_brew", () -> new LiquidBlock(WizardsRebornFluids.HELLISH_MUSHROOM_BREW, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> MOR_BREW = BLOCKS.register("mor_brew", () -> new LiquidBlock(WizardsRebornFluids.MOR_BREW, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> FLOWER_BREW = BLOCKS.register("flower_brew", () -> new LiquidBlock(WizardsRebornFluids.FLOWER_BREW, BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerColorMappingBlocks(RegisterColorHandlersEvent.Block event) {
            event.register((state, world, pos, tintIndex) -> CustomBlockColor.getInstance().getColor(state, world, pos, tintIndex), CustomBlockColor.PLANTS);
        }
    }

    public static void setupBlocks() {
        FluffyFurBlocks.axeStrippables(ARCANE_WOOD_LOG.get(), STRIPPED_ARCANE_WOOD_LOG.get());
        FluffyFurBlocks.axeStrippables(ARCANE_WOOD.get(), STRIPPED_ARCANE_WOOD.get());
        FluffyFurBlocks.axeStrippables(ARCANE_WOOD_BAULK.get(), STRIPPED_ARCANE_WOOD_BAULK.get());
        FluffyFurBlocks.axeStrippables(INNOCENT_WOOD_LOG.get(), STRIPPED_INNOCENT_WOOD_LOG.get());
        FluffyFurBlocks.axeStrippables(INNOCENT_WOOD.get(), STRIPPED_INNOCENT_WOOD.get());
        FluffyFurBlocks.axeStrippables(INNOCENT_WOOD_BAULK.get(), STRIPPED_INNOCENT_WOOD_BAULK.get());

        FluffyFurBlocks.fireBlock(ARCANE_WOOD_LOG.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD.get(), 5, 20);
        FluffyFurBlocks.fireBlock(STRIPPED_ARCANE_WOOD_LOG.get(), 5, 20);
        FluffyFurBlocks.fireBlock(STRIPPED_ARCANE_WOOD.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD_PLANKS.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD_STAIRS.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD_SLAB.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD_CROSS_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(STRIPPED_ARCANE_WOOD_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(STRIPPED_ARCANE_WOOD_CROSS_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD_PLANKS_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD_PLANKS_CROSS_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD_FENCE.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD_FENCE_GATE.get(), 5, 20);
        FluffyFurBlocks.fireBlock(ARCANE_WOOD_LEAVES.get(), 30, 60);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_LOG.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD.get(), 5, 20);
        FluffyFurBlocks.fireBlock(STRIPPED_INNOCENT_WOOD_LOG.get(), 5, 20);
        FluffyFurBlocks.fireBlock(STRIPPED_INNOCENT_WOOD.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_PLANKS.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_STAIRS.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_SLAB.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_CROSS_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(STRIPPED_INNOCENT_WOOD_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(STRIPPED_INNOCENT_WOOD_CROSS_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_PLANKS_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_PLANKS_CROSS_BAULK.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_FENCE.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_FENCE_GATE.get(), 5, 20);
        FluffyFurBlocks.fireBlock(INNOCENT_WOOD_LEAVES.get(), 30, 60);
        FluffyFurBlocks.fireBlock(PETALS_OF_INNOCENCE.get(), 60, 100);
        FluffyFurBlocks.fireBlock(ARCANE_LINEN_HAY.get(), 60, 20);
        FluffyFurBlocks.fireBlock(SHINY_CLOVER_CROP.get(), 60, 100);
        FluffyFurBlocks.fireBlock(SHINY_CLOVER.get(), 60, 100);
    }
}
