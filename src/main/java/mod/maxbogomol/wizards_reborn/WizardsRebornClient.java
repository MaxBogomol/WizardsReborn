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
import mod.maxbogomol.wizards_reborn.client.render.entity.ArcaneWoodBoatModel;
import mod.maxbogomol.wizards_reborn.client.render.entity.SpellProjectileRenderer;
import mod.maxbogomol.wizards_reborn.client.render.item.*;
import mod.maxbogomol.wizards_reborn.client.render.tileentity.*;
import mod.maxbogomol.wizards_reborn.common.entity.CustomBoatEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
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
    public static ModelResourceLocation ARCANE_ITERATOR_PIECE_MODEl = new ModelResourceLocation(WizardsReborn.MOD_ID, "arcane_iterator_piece", "");

    public static ShaderInstance GLOWING_SHADER, GLOWING_SPRITE_SHADER, GLOWING_PARTICLE_SHADER, SPRITE_PARTICLE_SHADER;

    public static ShaderInstance getGlowingShader() { return GLOWING_SHADER; }
    public static ShaderInstance getGlowingSpriteShader() { return GLOWING_SPRITE_SHADER; }
    public static ShaderInstance getGlowingParticleShader() { return GLOWING_PARTICLE_SHADER; }
    public static ShaderInstance getSpriteParticleShader() { return SPRITE_PARTICLE_SHADER; }

    public static boolean optifinePresent = false;

    public static PipeModel fluidPipe;
    public static PipeModel steamPipe;
    public static ArrayList<PipeModel> fluidExtractor = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> steamExtractor = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> orbitalFluidRetainer = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> alchemyMachine = new ArrayList<PipeModel>();
    public static ArrayList<PipeModel> alchemyBoiler = new ArrayList<PipeModel>();

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

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onRenderTypeSetup(FMLClientSetupEvent event) {
            Sheets.addWoodType(WizardsReborn.ARCANE_WOOD_TYPE);

            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_TRAPDOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_LEAVES.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_ARCANE_WOOD_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_LINEN.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ELDER_MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_ELDER_MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.STEAM_THERMAL_STORAGE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_CENSER.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ALCHEMY_GLASS.get(), RenderType.translucent());

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
            BlockEntityRenderers.register(WizardsReborn.ARCANE_CENSER_TILE_ENTITY.get(), (trd) -> new ArcaneCenserTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.ARCANE_ITERATOR_TILE_ENTITY.get(), (trd) -> new ArcaneIteratorTileEntityRenderer());

            BlockEntityRenderers.register(WizardsReborn.CRYSTAL_TILE_ENTITY.get(), (trd) -> new CrystalTileEntityRenderer());

            EntityRenderers.register(WizardsReborn.BOAT.get(), m -> new ArcaneWoodBoatModel(m, false));
            EntityRenderers.register(WizardsReborn.CHEST_BOAT.get(), m -> new ArcaneWoodBoatModel(m, true));
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
            event.register(ARCANE_ITERATOR_PIECE_MODEl);

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

            for (ResourceLocation resourceLocation : event.getModels().keySet()) {
                if (resourceLocation.getNamespace().equals(WizardsReborn.MOD_ID)) {
                    if (resourceLocation.getPath().equals("fluid_pipe") && !resourceLocation.toString().contains("inventory")) {
                        map.put(resourceLocation, fluidPipe);
                    }
                    if (resourceLocation.getPath().equals("steam_pipe") && !resourceLocation.toString().contains("inventory")) {
                        map.put(resourceLocation, steamPipe);
                    }
                    if (resourceLocation.getPath().equals("fluid_extractor") && !resourceLocation.toString().contains("inventory")) {
                        PipeModel model = new PipeModel(map.get(resourceLocation), "fluid_pipe");
                        fluidExtractor.add(model);
                        map.put(resourceLocation, model);
                    }
                    if (resourceLocation.getPath().equals("steam_extractor") && !resourceLocation.toString().contains("inventory")) {
                        PipeModel model = new PipeModel(map.get(resourceLocation), "steam_pipe");
                        steamExtractor.add(model);
                        map.put(resourceLocation, model);
                    }
                    if (resourceLocation.getPath().equals("orbital_fluid_retainer") && !resourceLocation.toString().contains("inventory")) {
                        PipeModel model = new PipeModel(map.get(resourceLocation), "fluid_pipe");
                        orbitalFluidRetainer.add(model);
                        map.put(resourceLocation, model);
                    }
                    if (resourceLocation.getPath().equals("alchemy_machine") && !resourceLocation.toString().contains("inventory")) {
                        PipeModel model = new PipeModel(map.get(resourceLocation), "fluid_pipe");
                        alchemyMachine.add(model);
                        map.put(resourceLocation, model);
                    }
                    if (resourceLocation.getPath().equals("alchemy_boiler") && !resourceLocation.toString().contains("inventory")) {
                        PipeModel model = new PipeModel(map.get(resourceLocation), "steam_pipe");
                        alchemyBoiler.add(model);
                        map.put(resourceLocation, model);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void afterModelBake(ModelEvent.BakingCompleted event) {
            fluidPipe.init(event.getModelManager());
            steamPipe.init(event.getModelManager());
            for (PipeModel model : fluidExtractor) {
                model.init(event.getModelManager());
            }
            for (PipeModel model : steamExtractor) {
                model.init(event.getModelManager());
            }
            for (PipeModel model : orbitalFluidRetainer) {
                model.init(event.getModelManager());
            }
            for (PipeModel model : alchemyMachine) {
                model.init(event.getModelManager());
            }
            for (PipeModel model : alchemyBoiler) {
                model.init(event.getModelManager());
            }
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
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            for (CustomBoatEntity.Type boatType : CustomBoatEntity.Type.values()) {
                event.registerLayerDefinition(ArcaneWoodBoatModel.createBoatModelName(boatType), BoatModel::createBodyModel);
                event.registerLayerDefinition(ArcaneWoodBoatModel.createChestBoatModelName(boatType), ChestBoatModel::createBodyModel);
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
}
