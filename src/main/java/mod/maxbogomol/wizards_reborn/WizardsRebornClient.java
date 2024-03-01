package mod.maxbogomol.wizards_reborn;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.model.armor.ArcaneFortressArmorModel;
import mod.maxbogomol.wizards_reborn.client.model.armor.InventorWizardArmorModel;
import mod.maxbogomol.wizards_reborn.client.model.curio.AmuletModel;
import mod.maxbogomol.wizards_reborn.client.model.curio.BeltModel;
import mod.maxbogomol.wizards_reborn.client.model.curio.MushroomCapModel;
import mod.maxbogomol.wizards_reborn.client.particle.*;
import mod.maxbogomol.wizards_reborn.client.render.block.PipeModel;
import mod.maxbogomol.wizards_reborn.client.render.entity.CustomBoatModel;
import mod.maxbogomol.wizards_reborn.client.render.entity.SpellProjectileRenderer;
import mod.maxbogomol.wizards_reborn.client.render.item.*;
import mod.maxbogomol.wizards_reborn.client.render.tileentity.*;
import mod.maxbogomol.wizards_reborn.common.block.CustomBlockColor;
import mod.maxbogomol.wizards_reborn.common.entity.CustomBoatEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.RunicWisestonePlateItem;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class WizardsRebornClient {
    private static final String CATEGORY_KEY = "key.category."+WizardsReborn.MOD_ID+".general";
    public static final KeyMapping OPEN_WAND_SELECTION_KEY = new KeyMapping("key."+WizardsReborn.MOD_ID+".selection_hud", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY_KEY);

    public static ModelLayerLocation BELT_LAYER = new ModelLayerLocation(new ResourceLocation(WizardsReborn.MOD_ID, "belt"), "main");
    public static ModelLayerLocation AMULET_LAYER = new ModelLayerLocation(new ResourceLocation(WizardsReborn.MOD_ID, "amulet"), "main");
    public static ModelLayerLocation MUSHROOM_CAP_LAYER = new ModelLayerLocation(new ResourceLocation(WizardsReborn.MOD_ID, "mushroom_cap"), "main");

    public static final ModelLayerLocation INVENTOR_WIZARD_ARMOR_LAYER = new ModelLayerLocation(new ResourceLocation(WizardsReborn.MOD_ID, "inventor_wizard_armor"), "main");
    public static final ModelLayerLocation ARCANE_FORTRESS_ARMOR_LAYER = new ModelLayerLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_fortress_armor"), "main");

    public static InventorWizardArmorModel INVENTOR_WIZARD_ARMOR_MODEL = null;
    public static ArcaneFortressArmorModel ARCANE_FORTRESS_ARMOR_MODEL = null;

    public static ModelResourceLocation JEWELER_TABLE_STONE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "jeweler_table_stone", "");
    public static ModelResourceLocation ALTAR_OF_DROUGHT_FRAME_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "altar_of_drought_frame", "");
    public static ModelResourceLocation TOTEM_OF_EXPERIENCE_ABSORPTION_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "totem_of_experience_absorption_piece", "");
    public static ModelResourceLocation ARCANE_ITERATOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "arcane_iterator_piece", "");
    public static ModelResourceLocation HOVERING_LENS_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "hovering_lens", "");
    public static ModelResourceLocation REDSTONE_SENSOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "redstone_sensor_piece", "");
    public static ModelResourceLocation REDSTONE_SENSOR_PIECE_ON_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "redstone_sensor_piece_on", "");
    public static ModelResourceLocation WISSEN_SENSOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "wissen_sensor_piece", "");
    public static ModelResourceLocation COOLDOWN_SENSOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "cooldown_sensor_piece", "");
    public static ModelResourceLocation LIGHT_SENSOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "light_sensor_piece", "");
    public static ModelResourceLocation EXPERIENCE_SENSOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "experience_sensor_piece", "");
    public static ModelResourceLocation HEAT_SENSOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "heat_sensor_piece", "");
    public static ModelResourceLocation FLUID_SENSOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "fluid_sensor_piece", "");
    public static ModelResourceLocation FLUID_SENSOR_PIECE_ON_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "fluid_sensor_piece_on", "");
    public static ModelResourceLocation STEAM_SENSOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "steam_sensor_piece", "");
    public static ModelResourceLocation WISSEN_ACTIVATOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "wissen_activator_piece", "");
    public static ModelResourceLocation ITEM_SORTER_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "item_sorter_piece", "");

    public static ShaderInstance GLOWING_SHADER, GLOWING_SPRITE_SHADER, GLOWING_PARTICLE_SHADER, SPRITE_PARTICLE_SHADER, FLUID_SHADER;

    public static ShaderInstance getGlowingShader() { return GLOWING_SHADER; }
    public static ShaderInstance getGlowingSpriteShader() { return GLOWING_SPRITE_SHADER; }
    public static ShaderInstance getGlowingParticleShader() { return GLOWING_PARTICLE_SHADER; }
    public static ShaderInstance getSpriteParticleShader() { return SPRITE_PARTICLE_SHADER; }
    public static ShaderInstance getFluidShader() { return FLUID_SHADER; }

    public static boolean optifinePresent = false;
    public static boolean firstScreen = true;

    public static PipeModel fluidPipe;
    public static PipeModel steamPipe;
    public static ArrayList<PipeModel> fluidExtractor = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> steamExtractor = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> orbitalFluidRetainer = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> alchemyMachine = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> alchemyBoiler = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> arcaneWoodFluidCasing = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> wisestoneFluidCasing = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> arcaneWoodSteamCasing = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> wisestoneSteamCasing = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> creativeFluidStorage = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> creativeSteamStorage = new ArrayList<PipeModel>();

    public static final ModelResourceLocation FLUID_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_center"), "");
    public static final ModelResourceLocation FLUID_EXTRACTOR = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_extractor_center"), "");
    public static final ModelResourceLocation FLUID_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_connection"), "");
    public static final ModelResourceLocation FLUID_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_end"), "");
    public static final ModelResourceLocation FLUID_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_connection_opposite"), "");
    public static final ModelResourceLocation FLUID_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_end_opposite"), "");

    public static final ModelResourceLocation STEAM_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_center"), "");
    public static final ModelResourceLocation STEAM_EXTRACTOR = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_extractor_center"), "");
    public static final ModelResourceLocation STEAM_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_connection"), "");
    public static final ModelResourceLocation STEAM_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_end"), "");
    public static final ModelResourceLocation STEAM_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_connection_opposite"), "");
    public static final ModelResourceLocation STEAM_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_end_opposite"), "");

    public static final Music MOR_MUSIC = new Music(WizardsReborn.MUSIC_DISC_MOR_SOUND.getHolder().get(), 6000, 12000, true);
    public static final Music REBORN_MUSIC = new Music(WizardsReborn.MUSIC_DISC_REBORN_SOUND.getHolder().get(), 400, 1200, true);

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onRenderTypeSetup(FMLClientSetupEvent event) {
            Sheets.addWoodType(WizardsReborn.ARCANE_WOOD_TYPE);
            Sheets.addWoodType(WizardsReborn.INNOCENT_WOOD_TYPE);

            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_TRAPDOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_LEAVES.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_ARCANE_WOOD_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.INNOCENT_WOOD_DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.INNOCENT_WOOD_TRAPDOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.INNOCENT_WOOD_LEAVES.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.INNOCENT_WOOD_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_INNOCENT_WOOD_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.PETALS_OF_INNOCENCE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_PETALS_OF_INNOCENCE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_PINK_PETALS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_LINEN.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ELDER_MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_ELDER_MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.STEAM_THERMAL_STORAGE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_CENSER.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ALCHEMY_GLASS.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.FLUID_SENSOR.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.WISSEN_ACTIVATOR.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ITEM_SORTER.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_GLASS_FRAME.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.INNOCENT_WOOD_GLASS_FRAME.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.WISESTONE_GLASS_FRAME.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.MUNDANE_BREW_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.FLOWING_MUNDANE_BREW_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ALCHEMY_OIL_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.FLOWING_ALCHEMY_OIL_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.OIL_TEA_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.FLOWING_OIL_TEA_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.WISSEN_TEA_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.FLOWING_WISSEN_TEA_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.MUSHROOM_BREW_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.FLOWING_MUSHROOM_BREW_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.FLOWING_HELLISH_MUSHROOM_BREW_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.MOR_BREW_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.FLOWING_MOR_BREW_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.FLOWER_BREW_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.FLOWING_FLOWER_BREW_FLUID.get(), RenderType.translucent());

            BlockEntityRenderers.register(WizardsReborn.SIGN_TILE_ENTITY.get(), SignRenderer::new);
            BlockEntityRenderers.register(WizardsReborn.HANGING_SIGN_TILE_ENTITY.get(), HangingSignRenderer::new);
            BlockEntityRenderers.register(WizardsReborn.ARCANE_PEDESTAL_TILE_ENTITY.get(), (trd) -> new ArcanePedestalTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.HOVERING_TOME_STAND_TILE_ENTITY.get(), HoveringTomeStandTileEntityRenderer::new);
            BlockEntityRenderers.register(WizardsReborn.WISSEN_ALTAR_TILE_ENTITY.get(), (trd) -> new WissenAltarTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.WISSEN_TRANSLATOR_TILE_ENTITY.get(), (trd) -> new WissenTranslatorTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.WISSEN_CRYSTALLIZER_TILE_ENTITY.get(), (trd) -> new WissenCrystallizerTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.ARCANE_WORKBENCH_TILE_ENTITY.get(), (trd) -> new ArcaneWorkbenchTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.WISSEN_CELL_TILE_ENTITY.get(), (trd) -> new WissenCellTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.JEWELER_TABLE_TILE_ENTITY.get(), (trd) -> new JewelerTableTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.ORBITAL_FLUID_RETAINER_TILE_ENTITY.get(), (trd) -> new OrbitalFluidRetainerTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.ALTAR_OF_DROUGHT_TILE_ENTITY.get(), (trd) -> new AltarOfDroughtTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.ALCHEMY_MACHINE_TILE_ENTITY.get(), (trd) -> new AlchemyMachineTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.ALCHEMY_BOILER_TILE_ENTITY.get(), (trd) -> new AlchemyBoilerTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.ARCANE_CENSER_TILE_ENTITY.get(), (trd) -> new ArcaneCenserTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.EXPERIENCE_TOTEM_TILE_ENTITY.get(), (trd) -> new ExperienceTotemTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.TOTEM_OF_EXPERIENCE_ABSORPTION_TILE_ENTITY.get(), (trd) -> new TotemOfExperienceAbsorptionTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.ARCANE_ITERATOR_TILE_ENTITY.get(), (trd) -> new ArcaneIteratorTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.LIGHT_EMITTER_TILE_ENTITY.get(), (trd) -> new LightEmitterBlockTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.LIGHT_TRANSFER_LENS_TILE_ENTITY.get(), (trd) -> new LightTransferLensTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.RUNIC_PEDESTAL_TILE_ENTITY.get(), (trd) -> new RunicPedestalTileEntityRenderer());

            BlockEntityRenderers.register(WizardsReborn.SENSOR_TILE_ENTITY.get(), (trd) -> new SensorTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.FLUID_SENSOR_TILE_ENTITY.get(), (trd) -> new FluidSensorTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.WISSEN_ACTIVATOR_TILE_ENTITY.get(), (trd) -> new SensorTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.ITEM_SORTER_TILE_ENTITY.get(), (trd) -> new ItemSorterTileEntityRenderer());

            BlockEntityRenderers.register(WizardsReborn.WISSEN_CASING_TILE_ENTITY.get(), (trd) -> new WissenCasingTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.LIGHT_CASING_TILE_ENTITY.get(), (trd) -> new LightCasingTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.FLUID_CASING_TILE_ENTITY.get(), (trd) -> new FluidCasingTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.STEAM_CASING_TILE_ENTITY.get(), (trd) -> new SteamCasingTileEntityRenderer());

            BlockEntityRenderers.register(WizardsReborn.CREATIVE_WISSEN_STORAGE_TILE_ENTITY.get(), (trd) -> new CreativeWissenStorageTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.CREATIVE_LIGHT_STORAGE_TILE_ENTITY.get(), (trd) -> new LightCasingTileEntityRenderer());

            BlockEntityRenderers.register(WizardsReborn.ARCANUM_GROWTH_TILE_ENTITY.get(), (trd) -> new ArcanumGrowthTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.CRYSTAL_GROWTH_TILE_ENTITY.get(), (trd) -> new CrystalGrowthTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.CRYSTAL_TILE_ENTITY.get(), (trd) -> new CrystalTileEntityRenderer());

            EntityRenderers.register(WizardsReborn.BOAT.get(), m -> new CustomBoatModel(m, false));
            EntityRenderers.register(WizardsReborn.CHEST_BOAT.get(), m -> new CustomBoatModel(m, true));
            EntityRenderers.register(WizardsReborn.SPELL_PROJECTILE.get(), SpellProjectileRenderer::new);

            makeBow(WizardsReborn.ARCANE_WOOD_BOW.get());

            ItemProperties.register(WizardsReborn.ALCHEMY_VIAL_POTION.get(), new ResourceLocation("uses"), (stack, world, living, count) -> AlchemyPotionItem.getUses(stack));
            ItemProperties.register(WizardsReborn.ALCHEMY_FLASK_POTION.get(), new ResourceLocation("uses"), (stack, world, living, count) -> AlchemyPotionItem.getUses(stack));
        }

        @SubscribeEvent
        public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event) {
            for (String crystal : WandCrystalsModels.getCrystals()) {
                event.register(WandCrystalsModels.getModelLocationCrystal(crystal));
            }

            if (ClientConfig.LARGE_ITEM_MODEL.get()) {
                for (String item : Item2DRenderer.HAND_MODEL_ITEMS) {
                    event.register(new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, item + "_in_hand"), "inventory"));
                }
            }

            event.register(JEWELER_TABLE_STONE_MODEl);
            event.register(ALTAR_OF_DROUGHT_FRAME_MODEl);
            event.register(TOTEM_OF_EXPERIENCE_ABSORPTION_PIECE_MODEl);
            event.register(ARCANE_ITERATOR_PIECE_MODEl);
            event.register(HOVERING_LENS_MODEl);
            event.register(REDSTONE_SENSOR_PIECE_MODEl);
            event.register(REDSTONE_SENSOR_PIECE_ON_MODEl);
            event.register(WISSEN_SENSOR_PIECE_MODEl);
            event.register(COOLDOWN_SENSOR_PIECE_MODEl);
            event.register(LIGHT_SENSOR_PIECE_MODEl);
            event.register(EXPERIENCE_SENSOR_PIECE_MODEl);
            event.register(HEAT_SENSOR_PIECE_MODEl);
            event.register(FLUID_SENSOR_PIECE_MODEl);
            event.register(FLUID_SENSOR_PIECE_ON_MODEl);
            event.register(STEAM_SENSOR_PIECE_MODEl);
            event.register(WISSEN_ACTIVATOR_PIECE_MODEl);
            event.register(ITEM_SORTER_PIECE_MODEl);

            event.register(FLUID_CENTER);
            event.register(FLUID_EXTRACTOR);
            event.register(FLUID_CONNECTION);
            event.register(FLUID_END);
            event.register(FLUID_CONNECTION_2);
            event.register(FLUID_END_2);

            event.register(STEAM_CENTER);
            event.register(STEAM_EXTRACTOR);
            event.register(STEAM_CONNECTION);
            event.register(STEAM_END);
            event.register(STEAM_CONNECTION_2);
            event.register(STEAM_END_2);
        }

        @SubscribeEvent
        public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
            Map<ResourceLocation, BakedModel> map = event.getModels();
            BakedModel existingModel = map.get(new ModelResourceLocation(WizardsReborn.ARCANE_WAND.getId(), "inventory"));

            for (String crystal : WandCrystalsModels.getCrystals()) {
                BakedModel model = map.get(WandCrystalsModels.getModelLocationCrystal(crystal));
                WandCrystalsModels.addModelCrystals(crystal, model);
                model = new CustomFinalisedModel(existingModel, WandCrystalsModels.getModelCrystals(crystal));
                WandCrystalsModels.addModel(crystal, model);
            }
            CustomModel customModel = new CustomModel(existingModel, new WandModelOverrideList());
            map.replace(new ModelResourceLocation(WizardsReborn.ARCANE_WAND.getId(), "inventory"), customModel);

            if (ClientConfig.LARGE_ITEM_MODEL.get()) {
                Item2DRenderer.onModelBakeEvent(event);
            }

            fluidPipe = new PipeModel(map.get(FLUID_CENTER), "fluid_pipe");
            steamPipe = new PipeModel(map.get(STEAM_CENTER), "steam_pipe");

            addPipeModel(map, WizardsReborn.MOD_ID, "fluid_pipe", "waterlogged=false", fluidPipe);
            addPipeModel(map, WizardsReborn.MOD_ID, "fluid_pipe", "waterlogged=true", fluidPipe);
            addPipeModel(map, WizardsReborn.MOD_ID, "steam_pipe", "waterlogged=false", steamPipe);
            addPipeModel(map, WizardsReborn.MOD_ID, "steam_pipe", "waterlogged=true", steamPipe);
            addPipeModel(map, WizardsReborn.MOD_ID, "fluid_extractor", "lit=false,powered=false", "fluid_pipe", fluidExtractor, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "fluid_extractor", "lit=true,powered=false", "fluid_pipe", fluidExtractor, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "fluid_extractor", "lit=false,powered=true", "fluid_pipe", fluidExtractor, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "fluid_extractor", "lit=true,powered=true", "fluid_pipe", fluidExtractor, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "steam_extractor", "lit=false,powered=false", "steam_pipe", steamExtractor, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "steam_extractor", "lit=true,powered=false", "steam_pipe", steamExtractor, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "steam_extractor", "lit=false,powered=true", "steam_pipe", steamExtractor, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "steam_extractor", "lit=true,powered=true", "steam_pipe", steamExtractor, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "orbital_fluid_retainer", "", "fluid_pipe", orbitalFluidRetainer, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "alchemy_machine", "facing=east", "fluid_pipe", alchemyMachine, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "alchemy_machine", "facing=north", "fluid_pipe", alchemyMachine, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "alchemy_machine", "facing=south", "fluid_pipe", alchemyMachine, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "alchemy_machine", "facing=west", "fluid_pipe", alchemyMachine, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "alchemy_boiler", "facing=east", "steam_pipe", alchemyBoiler, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "alchemy_boiler", "facing=north", "steam_pipe", alchemyBoiler, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "alchemy_boiler", "facing=south", "steam_pipe", alchemyBoiler, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "alchemy_boiler", "facing=west", "steam_pipe", alchemyBoiler, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "arcane_wood_fluid_casing", "powered=false", "fluid_pipe", arcaneWoodFluidCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "arcane_wood_fluid_casing", "powered=true", "fluid_pipe", arcaneWoodFluidCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "wisestone_fluid_casing", "powered=false", "fluid_pipe", wisestoneFluidCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "wisestone_fluid_casing", "powered=true", "fluid_pipe", wisestoneFluidCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "arcane_wood_steam_casing", "powered=false", "steam_pipe", arcaneWoodSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "arcane_wood_steam_casing", "powered=true", "steam_pipe", arcaneWoodSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "wisestone_steam_casing", "powered=false", "steam_pipe", wisestoneSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "wisestone_steam_casing", "powered=true", "steam_pipe", wisestoneSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "creative_fluid_storage", "", "fluid_pipe", creativeFluidStorage, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "creative_steam_storage", "", "steam_pipe", creativeSteamStorage, true);
        }

        @SubscribeEvent
        public static void afterModelBake(ModelEvent.BakingCompleted event) {
            fluidPipe.init(event.getModelManager());
            steamPipe.init(event.getModelManager());
            bakePipeModel(fluidExtractor, event.getModelManager());
            bakePipeModel(steamExtractor, event.getModelManager());
            bakePipeModel(orbitalFluidRetainer, event.getModelManager());
            bakePipeModel(alchemyMachine, event.getModelManager());
            bakePipeModel(alchemyBoiler, event.getModelManager());
            bakePipeModel(arcaneWoodFluidCasing, event.getModelManager());
            bakePipeModel(wisestoneFluidCasing, event.getModelManager());
            bakePipeModel(arcaneWoodSteamCasing, event.getModelManager());
            bakePipeModel(wisestoneSteamCasing, event.getModelManager());
            bakePipeModel(creativeFluidStorage, event.getModelManager());
            bakePipeModel(creativeSteamStorage, event.getModelManager());
        }

        @SubscribeEvent
        public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
            event.register(WizardsRebornClient.OPEN_WAND_SELECTION_KEY);
        }

        @SubscribeEvent
        public static void registerFactories(RegisterParticleProvidersEvent event) {
            Minecraft.getInstance().particleEngine.register(WizardsReborn.WISP_PARTICLE.get(), WispParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(WizardsReborn.SPARKLE_PARTICLE.get(), SparkleParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(WizardsReborn.KARMA_PARTICLE.get(), KarmaParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(WizardsReborn.ARCANE_WOOD_LEAF_PARTICLE.get(), ArcaneWoodLeafParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(WizardsReborn.STEAM_PARTICLE.get(), SteamParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(WizardsReborn.SMOKE_PARTICLE.get(), SmokeParticleType.Factory::new);
        }

        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("wizards_reborn:glowing"), DefaultVertexFormat.POSITION_COLOR),
                    shader -> { GLOWING_SHADER = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("wizards_reborn:glowing_sprite"), DefaultVertexFormat.POSITION_TEX_COLOR),
                    shader -> { GLOWING_SPRITE_SHADER = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("wizards_reborn:glowing_particle"), DefaultVertexFormat.PARTICLE),
                    shader -> { GLOWING_PARTICLE_SHADER = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("wizards_reborn:sprite_particle"), DefaultVertexFormat.PARTICLE),
                    shader -> { SPRITE_PARTICLE_SHADER = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("wizards_reborn:fluid"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP),
                    shader -> { FLUID_SHADER = shader; });
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            for (CustomBoatEntity.Type boatType : CustomBoatEntity.Type.values()) {
                event.registerLayerDefinition(CustomBoatModel.createBoatModelName(boatType), BoatModel::createBodyModel);
                event.registerLayerDefinition(CustomBoatModel.createChestBoatModelName(boatType), ChestBoatModel::createBodyModel);
            }

            event.registerLayerDefinition(WizardsRebornClient.BELT_LAYER, BeltModel::createBodyLayer);
            event.registerLayerDefinition(WizardsRebornClient.AMULET_LAYER, AmuletModel::createBodyLayer);
            event.registerLayerDefinition(WizardsRebornClient.MUSHROOM_CAP_LAYER, MushroomCapModel::createBodyLayer);

            event.registerLayerDefinition(INVENTOR_WIZARD_ARMOR_LAYER, InventorWizardArmorModel::createBodyLayer);
            event.registerLayerDefinition(ARCANE_FORTRESS_ARMOR_LAYER, ArcaneFortressArmorModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.AddLayers event) {
            INVENTOR_WIZARD_ARMOR_MODEL = new InventorWizardArmorModel(event.getEntityModels().bakeLayer(INVENTOR_WIZARD_ARMOR_LAYER));
            ARCANE_FORTRESS_ARMOR_MODEL = new ArcaneFortressArmorModel(event.getEntityModels().bakeLayer(ARCANE_FORTRESS_ARMOR_LAYER));
        }

        @SubscribeEvent
        public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
            event.register(new AlchemyPotionItem.ColorHandler(), WizardsReborn.ALCHEMY_VIAL_POTION.get(), WizardsReborn.ALCHEMY_FLASK_POTION.get());
            event.register(new RunicWisestonePlateItem.ColorHandler(), WizardsReborn.RUNIC_WISESTONE_PLATE.get());
        }

        @SubscribeEvent
        public static void ColorMappingBlocks(RegisterColorHandlersEvent.Block event) {
            event.register((state, world, pos, tintIndex) -> CustomBlockColor.getInstance().getColor(state, world, pos, tintIndex), CustomBlockColor.PLANTS);
        }
    }

    public static void makeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);
    }

    public static void addPipeModel(Map<ResourceLocation, BakedModel> map, String modId, String modelId, String path, PipeModel pipe) {
        map.put(new ModelResourceLocation(new ResourceLocation(modId + ":" + modelId), path), pipe);
    }

    public static void addPipeModel(Map<ResourceLocation, BakedModel> map, String modId, String modelId, String path, String pipe, ArrayList<PipeModel> pipes) {
        if (map.get(new ModelResourceLocation(new ResourceLocation(modId + ":" + modelId), path)) != null) {
            PipeModel model = new PipeModel(map.get(new ModelResourceLocation(new ResourceLocation(modId + ":" + modelId), path)), pipe);
            pipes.add(model);
            addPipeModel(map, modId, modelId, path, model);
        }
    }

    public static void addPipeModel(Map<ResourceLocation, BakedModel> map, String modId, String modelId, String path, String pipe, ArrayList<PipeModel> pipes, boolean waterlogged) {
        if (waterlogged) {
            if (path.equals("")) {
                addPipeModel(map, modId, modelId, "waterlogged=false", pipe, pipes);
                addPipeModel(map, modId, modelId, "waterlogged=true", pipe, pipes);
            } else {
                addPipeModel(map, modId, modelId, "waterlogged=false," + path, pipe, pipes);
                addPipeModel(map, modId, modelId, "waterlogged=true," + path, pipe, pipes);
                addPipeModel(map, modId, modelId, path + ",waterlogged=false", pipe, pipes);
                addPipeModel(map, modId, modelId, path + ",waterlogged=true", pipe, pipes);
            }
        } else {
            addPipeModel(map, modId, modelId, path, pipe, pipes);
        }
    }

    public static void bakePipeModel(ArrayList<PipeModel> pipes, ModelManager manager) {
        for (PipeModel model : pipes) {
            model.init(manager);
        }
    }
}
