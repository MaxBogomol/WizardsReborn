package mod.maxbogomol.wizards_reborn.registry.client;

import mod.maxbogomol.fluffy_fur.client.render.fluid.FluidCuboid;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.model.armor.*;
import mod.maxbogomol.wizards_reborn.client.model.block.AlchemyBottleModel;
import mod.maxbogomol.wizards_reborn.client.model.block.AlchemyFlaskModel;
import mod.maxbogomol.wizards_reborn.client.model.block.AlchemyVialModel;
import mod.maxbogomol.wizards_reborn.client.model.block.PipeModel;
import mod.maxbogomol.wizards_reborn.client.model.curio.*;
import mod.maxbogomol.wizards_reborn.client.model.item.WandCrystalsModels;
import mod.maxbogomol.wizards_reborn.client.model.sniffalo.SniffaloArcaneArmorModel;
import mod.maxbogomol.wizards_reborn.client.model.sniffalo.SniffaloCarpetArmorModel;
import mod.maxbogomol.wizards_reborn.client.model.sniffalo.SniffaloSaddleArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Map;

public class WizardsRebornModels {
    public static ModelLayerLocation BELT_LAYER = addLayer("belt");
    public static ModelLayerLocation BAG_LAYER = addLayer("bag");
    public static ModelLayerLocation AMULET_LAYER = addLayer("amulet");
    public static ModelLayerLocation COLLAR_LAYER = addLayer("collar");
    public static ModelLayerLocation MUSHROOM_CAP_LAYER = addLayer("mushroom_cap");

    public static final ModelLayerLocation INVENTOR_WIZARD_ARMOR_LAYER = addLayer("inventor_wizard_armor");
    public static final ModelLayerLocation ARCANE_FORTRESS_ARMOR_LAYER = addLayer("arcane_fortress_armor");
    public static final ModelLayerLocation ARCANE_FORTRESS_SLIM_ARMOR_LAYER = addLayer("arcane_fortress_slim_armor");

    public static final ModelLayerLocation TOP_HAT_ARMOR_LAYER = addLayer("top_hat_armor");
    public static final ModelLayerLocation SOUL_HUNTER_ARMOR_LAYER = addLayer("soul_hunter_armor");
    public static final ModelLayerLocation MAGNIFICENT_MAID_ARMOR_LAYER = addLayer("magnificent_maid_armor");
    public static final ModelLayerLocation MAGNIFICENT_MAID_SLIM_ARMOR_LAYER = addLayer("magnificent_maid_slim_armor");

    public static final ModelLayerLocation SNIFFALO_SADDLE_LAYER = addLayer("sniffalo_saddle");
    public static final ModelLayerLocation SNIFFALO_CARPET_LAYER = addLayer("sniffalo_carpet");
    public static final ModelLayerLocation SNIFFALO_ARCANE_ARMOR_LAYER = addLayer("sniffalo_arcane_armor");

    public static final ModelLayerLocation ALCHEMY_VIAL_LAYER = addLayer("alchemy_vial");
    public static final ModelLayerLocation ALCHEMY_FLASK_LAYER = addLayer("alchemy_flask");
    public static final ModelLayerLocation ALCHEMY_BOTTLE_LAYER = addLayer("alchemy_bottle");

    public static InventorWizardArmorModel INVENTOR_WIZARD_ARMOR = null;
    public static ArcaneFortressArmorModel ARCANE_FORTRESS_ARMOR = null;
    public static ArcaneFortressArmorModel ARCANE_FORTRESS_SLIM_ARMOR = null;

    public static TopHatArmorModel TOP_HAT_ARMOR = null;
    public static SoulHunterArmorModel SOUL_HUNTER_ARMOR = null;
    public static MagnificentMaidArmorModel MAGNIFICENT_MAID_ARMOR = null;
    public static MagnificentMaidSlimArmorModel MAGNIFICENT_MAID_SLIM_ARMOR = null;

    public static SniffaloSaddleArmorModel SNIFFALO_SADDLE = null;
    public static SniffaloCarpetArmorModel SNIFFALO_CARPET = null;
    public static SniffaloArcaneArmorModel SNIFFALO_ARCANE_ARMOR = null;

    public static AlchemyVialModel ALCHEMY_VIAL = null;
    public static AlchemyFlaskModel ALCHEMY_FLASK = null;
    public static AlchemyBottleModel ALCHEMY_BOTTLE = null;

    public static FluidCuboid VIAL_FLUID_CUBE_0 = new FluidCuboid(new Vector3f(0, 0, 0), new Vector3f(4, 3, 4), FluidCuboid.DEFAULT_FACES);
    public static FluidCuboid VIAL_FLUID_CUBE_1 = new FluidCuboid(new Vector3f(0, 0, 0), new Vector3f(2, 2, 2), FluidCuboid.DEFAULT_FACES);
    public static FluidCuboid FLASK_FLUID_CUBE = new FluidCuboid(new Vector3f(0, 0, 0), new Vector3f(4, 5, 4), FluidCuboid.DEFAULT_FACES);

    public static ModelResourceLocation JEWELER_TABLE_STONE = addCustomModel("jeweler_table_stone");
    public static ModelResourceLocation ALTAR_OF_DROUGHT_FRAME = addCustomModel("altar_of_drought_frame");
    public static ModelResourceLocation TOTEM_OF_EXPERIENCE_ABSORPTION_PIECE = addCustomModel("totem_of_experience_absorption_piece");
    public static ModelResourceLocation ARCANE_ITERATOR_PIECE = addCustomModel("arcane_iterator_piece");
    public static ModelResourceLocation HOVERING_LENS = addCustomModel("hovering_lens");
    public static ModelResourceLocation REDSTONE_SENSOR_PIECE = addCustomModel("redstone_sensor_piece");
    public static ModelResourceLocation REDSTONE_SENSOR_PIECE_ON = addCustomModel("redstone_sensor_piece_on");
    public static ModelResourceLocation WISSEN_SENSOR_PIECE = addCustomModel("wissen_sensor_piece");
    public static ModelResourceLocation COOLDOWN_SENSOR_PIECE = addCustomModel("cooldown_sensor_piece");
    public static ModelResourceLocation LIGHT_SENSOR_PIECE = addCustomModel("light_sensor_piece");
    public static ModelResourceLocation EXPERIENCE_SENSOR_PIECE = addCustomModel("experience_sensor_piece");
    public static ModelResourceLocation HEAT_SENSOR_PIECE = addCustomModel("heat_sensor_piece");
    public static ModelResourceLocation FLUID_SENSOR_PIECE = addCustomModel("fluid_sensor_piece");
    public static ModelResourceLocation FLUID_SENSOR_PIECE_ON = addCustomModel("fluid_sensor_piece_on");
    public static ModelResourceLocation STEAM_SENSOR_PIECE = addCustomModel("steam_sensor_piece");
    public static ModelResourceLocation WISSEN_ACTIVATOR_PIECE = addCustomModel("wissen_activator_piece");
    public static ModelResourceLocation ITEM_SORTER_PIECE = addCustomModel("item_sorter_piece");

    public static PipeModel fluidPipe;
    public static PipeModel steamPipe;
    public static ArrayList<PipeModel> fluidExtractor = new ArrayList<>();
    public static ArrayList<PipeModel> steamExtractor = new ArrayList<>();
    public static ArrayList<PipeModel> orbitalFluidRetainer = new ArrayList<>();
    public static ArrayList<PipeModel> alchemyMachine = new ArrayList<>();
    public static ArrayList<PipeModel> alchemyBoiler = new ArrayList<>();
    public static ArrayList<PipeModel> arcaneWoodFluidCasing = new ArrayList<>();
    public static ArrayList<PipeModel> innocentWoodFluidCasing = new ArrayList<>();
    public static ArrayList<PipeModel> corkBambooFluidCasing = new ArrayList<>();
    public static ArrayList<PipeModel> wisestoneFluidCasing = new ArrayList<>();
    public static ArrayList<PipeModel> arcaneWoodSteamCasing = new ArrayList<>();
    public static ArrayList<PipeModel> innocentWoodSteamCasing = new ArrayList<>();
    public static ArrayList<PipeModel> corkBambooSteamCasing = new ArrayList<>();
    public static ArrayList<PipeModel> wisestoneSteamCasing = new ArrayList<>();
    public static ArrayList<PipeModel> creativeFluidStorage = new ArrayList<>();
    public static ArrayList<PipeModel> creativeSteamStorage = new ArrayList<>();

    public static PipeModel arcaneWoodCrossBaulk;
    public static PipeModel strippedArcaneWoodCrossBaulk;
    public static PipeModel arcaneWoodPlanksCrossBaulk;
    public static PipeModel innocentWoodCrossBaulk;
    public static PipeModel strippedInnocentWoodCrossBaulk;
    public static PipeModel innocentWoodPlanksCrossBaulk;
    public static PipeModel corkBambooCrossBaulk;
    public static PipeModel corkBambooPlanksCrossBaulk;
    public static PipeModel corkBambooChiseledPlanksCrossBaulk;

    public static final ModelResourceLocation FLUID_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_center"), "");
    public static final ModelResourceLocation FLUID_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_connection"), "");
    public static final ModelResourceLocation FLUID_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_end"), "");
    public static final ModelResourceLocation FLUID_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_connection_opposite"), "");
    public static final ModelResourceLocation FLUID_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_end_opposite"), "");

    public static final ModelResourceLocation STEAM_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_center"), "");
    public static final ModelResourceLocation STEAM_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_connection"), "");
    public static final ModelResourceLocation STEAM_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_end"), "");
    public static final ModelResourceLocation STEAM_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_connection_opposite"), "");
    public static final ModelResourceLocation STEAM_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_end_opposite"), "");

    public static final ModelResourceLocation ARCANE_WOOD_CROSS_BAULK_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_cross_baulk_center"), "");
    public static final ModelResourceLocation ARCANE_WOOD_CROSS_BAULK_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_cross_baulk_connection"), "");
    public static final ModelResourceLocation ARCANE_WOOD_CROSS_BAULK_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_cross_baulk_end"), "");
    public static final ModelResourceLocation ARCANE_WOOD_CROSS_BAULK_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_cross_baulk_connection_opposite"), "");
    public static final ModelResourceLocation ARCANE_WOOD_CROSS_BAULK_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_cross_baulk_end_opposite"), "");

    public static final ModelResourceLocation STRIPPED_ARCANE_WOOD_CROSS_BAULK_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "stripped_arcane_wood_cross_baulk_center"), "");
    public static final ModelResourceLocation STRIPPED_ARCANE_WOOD_CROSS_BAULK_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "stripped_arcane_wood_cross_baulk_connection"), "");
    public static final ModelResourceLocation STRIPPED_ARCANE_WOOD_CROSS_BAULK_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "stripped_arcane_wood_cross_baulk_end"), "");
    public static final ModelResourceLocation STRIPPED_ARCANE_WOOD_CROSS_BAULK_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "stripped_arcane_wood_cross_baulk_connection_opposite"), "");
    public static final ModelResourceLocation STRIPPED_ARCANE_WOOD_CROSS_BAULK_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "stripped_arcane_wood_cross_baulk_end_opposite"), "");

    public static final ModelResourceLocation ARCANE_WOOD_PLANKS_CROSS_BAULK_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_planks_cross_baulk_center"), "");
    public static final ModelResourceLocation ARCANE_WOOD_PLANKS_CROSS_BAULK_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_planks_cross_baulk_connection"), "");
    public static final ModelResourceLocation ARCANE_WOOD_PLANKS_CROSS_BAULK_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_planks_cross_baulk_end"), "");
    public static final ModelResourceLocation ARCANE_WOOD_PLANKS_CROSS_BAULK_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_planks_cross_baulk_connection_opposite"), "");
    public static final ModelResourceLocation ARCANE_WOOD_PLANKS_CROSS_BAULK_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_planks_cross_baulk_end_opposite"), "");

    public static final ModelResourceLocation INNOCENT_WOOD_CROSS_BAULK_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_cross_baulk_center"), "");
    public static final ModelResourceLocation INNOCENT_WOOD_CROSS_BAULK_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_cross_baulk_connection"), "");
    public static final ModelResourceLocation INNOCENT_WOOD_CROSS_BAULK_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_cross_baulk_end"), "");
    public static final ModelResourceLocation INNOCENT_WOOD_CROSS_BAULK_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_cross_baulk_connection_opposite"), "");
    public static final ModelResourceLocation INNOCENT_WOOD_CROSS_BAULK_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_cross_baulk_end_opposite"), "");

    public static final ModelResourceLocation STRIPPED_INNOCENT_WOOD_CROSS_BAULK_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "stripped_innocent_wood_cross_baulk_center"), "");
    public static final ModelResourceLocation STRIPPED_INNOCENT_WOOD_CROSS_BAULK_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "stripped_innocent_wood_cross_baulk_connection"), "");
    public static final ModelResourceLocation STRIPPED_INNOCENT_WOOD_CROSS_BAULK_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "stripped_innocent_wood_cross_baulk_end"), "");
    public static final ModelResourceLocation STRIPPED_INNOCENT_WOOD_CROSS_BAULK_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "stripped_innocent_wood_cross_baulk_connection_opposite"), "");
    public static final ModelResourceLocation STRIPPED_INNOCENT_WOOD_CROSS_BAULK_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "stripped_innocent_wood_cross_baulk_end_opposite"), "");

    public static final ModelResourceLocation INNOCENT_WOOD_PLANKS_CROSS_BAULK_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_planks_cross_baulk_center"), "");
    public static final ModelResourceLocation INNOCENT_WOOD_PLANKS_CROSS_BAULK_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_planks_cross_baulk_connection"), "");
    public static final ModelResourceLocation INNOCENT_WOOD_PLANKS_CROSS_BAULK_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_planks_cross_baulk_end"), "");
    public static final ModelResourceLocation INNOCENT_WOOD_PLANKS_CROSS_BAULK_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_planks_cross_baulk_connection_opposite"), "");
    public static final ModelResourceLocation INNOCENT_WOOD_PLANKS_CROSS_BAULK_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_planks_cross_baulk_end_opposite"), "");

    public static final ModelResourceLocation CORK_BAMBOO_CROSS_BAULK_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_cross_baulk_center"), "");
    public static final ModelResourceLocation CORK_BAMBOO_CROSS_BAULK_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_cross_baulk_connection"), "");
    public static final ModelResourceLocation CORK_BAMBOO_CROSS_BAULK_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_cross_baulk_end"), "");
    public static final ModelResourceLocation CORK_BAMBOO_CROSS_BAULK_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_cross_baulk_connection_opposite"), "");
    public static final ModelResourceLocation CORK_BAMBOO_CROSS_BAULK_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_cross_baulk_end_opposite"), "");

    public static final ModelResourceLocation CORK_BAMBOO_PLANKS_CROSS_BAULK_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_planks_cross_baulk_center"), "");
    public static final ModelResourceLocation CORK_BAMBOO_PLANKS_CROSS_BAULK_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_planks_cross_baulk_connection"), "");
    public static final ModelResourceLocation CORK_BAMBOO_PLANKS_CROSS_BAULK_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_planks_cross_baulk_end"), "");
    public static final ModelResourceLocation CORK_BAMBOO_PLANKS_CROSS_BAULK_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_planks_cross_baulk_connection_opposite"), "");
    public static final ModelResourceLocation CORK_BAMBOO_PLANKS_CROSS_BAULK_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_planks_cross_baulk_end_opposite"), "");

    public static final ModelResourceLocation CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_CENTER = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_chiseled_planks_cross_baulk_center"), "");
    public static final ModelResourceLocation CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_CONNECTION = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_chiseled_planks_cross_baulk_connection"), "");
    public static final ModelResourceLocation CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_END = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_chiseled_planks_cross_baulk_end"), "");
    public static final ModelResourceLocation CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_CONNECTION_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_chiseled_planks_cross_baulk_connection_opposite"), "");
    public static final ModelResourceLocation CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_END_2 = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_chiseled_planks_cross_baulk_end_opposite"), "");

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void modelRegistry(ModelEvent.RegisterAdditional event) {
            for (String crystal : WandCrystalsModels.getCrystals()) {
                event.register(WandCrystalsModels.getModelLocationCrystal(crystal));
            }

            event.register(JEWELER_TABLE_STONE);
            event.register(ALTAR_OF_DROUGHT_FRAME);
            event.register(TOTEM_OF_EXPERIENCE_ABSORPTION_PIECE);
            event.register(ARCANE_ITERATOR_PIECE);
            event.register(HOVERING_LENS);
            event.register(REDSTONE_SENSOR_PIECE);
            event.register(REDSTONE_SENSOR_PIECE_ON);
            event.register(WISSEN_SENSOR_PIECE);
            event.register(COOLDOWN_SENSOR_PIECE);
            event.register(LIGHT_SENSOR_PIECE);
            event.register(EXPERIENCE_SENSOR_PIECE);
            event.register(HEAT_SENSOR_PIECE);
            event.register(FLUID_SENSOR_PIECE);
            event.register(FLUID_SENSOR_PIECE_ON);
            event.register(STEAM_SENSOR_PIECE);
            event.register(WISSEN_ACTIVATOR_PIECE);
            event.register(ITEM_SORTER_PIECE);

            event.register(FLUID_CENTER);
            event.register(FLUID_CONNECTION);
            event.register(FLUID_END);
            event.register(FLUID_CONNECTION_2);
            event.register(FLUID_END_2);

            event.register(STEAM_CENTER);
            event.register(STEAM_CONNECTION);
            event.register(STEAM_END);
            event.register(STEAM_CONNECTION_2);
            event.register(STEAM_END_2);

            event.register(ARCANE_WOOD_CROSS_BAULK_CENTER);
            event.register(ARCANE_WOOD_CROSS_BAULK_CONNECTION);
            event.register(ARCANE_WOOD_CROSS_BAULK_END);
            event.register(ARCANE_WOOD_CROSS_BAULK_CONNECTION_2);
            event.register(ARCANE_WOOD_CROSS_BAULK_END_2);

            event.register(STRIPPED_ARCANE_WOOD_CROSS_BAULK_CENTER);
            event.register(STRIPPED_ARCANE_WOOD_CROSS_BAULK_CONNECTION);
            event.register(STRIPPED_ARCANE_WOOD_CROSS_BAULK_END);
            event.register(STRIPPED_ARCANE_WOOD_CROSS_BAULK_CONNECTION_2);
            event.register(STRIPPED_ARCANE_WOOD_CROSS_BAULK_END_2);

            event.register(ARCANE_WOOD_PLANKS_CROSS_BAULK_CENTER);
            event.register(ARCANE_WOOD_PLANKS_CROSS_BAULK_CONNECTION);
            event.register(ARCANE_WOOD_PLANKS_CROSS_BAULK_END);
            event.register(ARCANE_WOOD_PLANKS_CROSS_BAULK_CONNECTION_2);
            event.register(ARCANE_WOOD_PLANKS_CROSS_BAULK_END_2);

            event.register(INNOCENT_WOOD_CROSS_BAULK_CENTER);
            event.register(INNOCENT_WOOD_CROSS_BAULK_CONNECTION);
            event.register(INNOCENT_WOOD_CROSS_BAULK_END);
            event.register(INNOCENT_WOOD_CROSS_BAULK_CONNECTION_2);
            event.register(INNOCENT_WOOD_CROSS_BAULK_END_2);

            event.register(STRIPPED_INNOCENT_WOOD_CROSS_BAULK_CENTER);
            event.register(STRIPPED_INNOCENT_WOOD_CROSS_BAULK_CONNECTION);
            event.register(STRIPPED_INNOCENT_WOOD_CROSS_BAULK_END);
            event.register(STRIPPED_INNOCENT_WOOD_CROSS_BAULK_CONNECTION_2);
            event.register(STRIPPED_INNOCENT_WOOD_CROSS_BAULK_END_2);

            event.register(INNOCENT_WOOD_PLANKS_CROSS_BAULK_CENTER);
            event.register(INNOCENT_WOOD_PLANKS_CROSS_BAULK_CONNECTION);
            event.register(INNOCENT_WOOD_PLANKS_CROSS_BAULK_END);
            event.register(INNOCENT_WOOD_PLANKS_CROSS_BAULK_CONNECTION_2);
            event.register(INNOCENT_WOOD_PLANKS_CROSS_BAULK_END_2);

            event.register(CORK_BAMBOO_CROSS_BAULK_CENTER);
            event.register(CORK_BAMBOO_CROSS_BAULK_CONNECTION);
            event.register(CORK_BAMBOO_CROSS_BAULK_END);
            event.register(CORK_BAMBOO_CROSS_BAULK_CONNECTION_2);
            event.register(CORK_BAMBOO_CROSS_BAULK_END_2);

            event.register(CORK_BAMBOO_PLANKS_CROSS_BAULK_CENTER);
            event.register(CORK_BAMBOO_PLANKS_CROSS_BAULK_CONNECTION);
            event.register(CORK_BAMBOO_PLANKS_CROSS_BAULK_END);
            event.register(CORK_BAMBOO_PLANKS_CROSS_BAULK_CONNECTION_2);
            event.register(CORK_BAMBOO_PLANKS_CROSS_BAULK_END_2);

            event.register(CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_CENTER);
            event.register(CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_CONNECTION);
            event.register(CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_END);
            event.register(CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_CONNECTION_2);
            event.register(CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_END_2);
        }

        @SubscribeEvent
        public static void modelBake(ModelEvent.ModifyBakingResult event) {
            Map<ResourceLocation, BakedModel> map = event.getModels();

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
            addPipeModel(map, WizardsReborn.MOD_ID, "innocent_wood_fluid_casing", "powered=false", "fluid_pipe", innocentWoodFluidCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "innocent_wood_fluid_casing", "powered=true", "fluid_pipe", innocentWoodFluidCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "cork_bamboo_fluid_casing", "powered=false", "fluid_pipe", corkBambooFluidCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "cork_bamboo_fluid_casing", "powered=true", "fluid_pipe", corkBambooFluidCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "wisestone_fluid_casing", "powered=false", "fluid_pipe", wisestoneFluidCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "wisestone_fluid_casing", "powered=true", "fluid_pipe", wisestoneFluidCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "arcane_wood_steam_casing", "powered=false", "steam_pipe", arcaneWoodSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "arcane_wood_steam_casing", "powered=true", "steam_pipe", arcaneWoodSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "innocent_wood_steam_casing", "powered=false", "steam_pipe", innocentWoodSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "innocent_wood_steam_casing", "powered=true", "steam_pipe", innocentWoodSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "cork_bamboo_steam_casing", "powered=false", "steam_pipe", corkBambooSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "cork_bamboo_steam_casing", "powered=true", "steam_pipe", corkBambooSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "wisestone_steam_casing", "powered=false", "steam_pipe", wisestoneSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "wisestone_steam_casing", "powered=true", "steam_pipe", wisestoneSteamCasing, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "creative_fluid_storage", "", "fluid_pipe", creativeFluidStorage, true);
            addPipeModel(map, WizardsReborn.MOD_ID, "creative_steam_storage", "", "steam_pipe", creativeSteamStorage, true);

            arcaneWoodCrossBaulk = new PipeModel(map.get(ARCANE_WOOD_CROSS_BAULK_CENTER), "arcane_wood_cross_baulk");
            strippedArcaneWoodCrossBaulk = new PipeModel(map.get(STRIPPED_ARCANE_WOOD_CROSS_BAULK_CENTER), "stripped_arcane_wood_cross_baulk");
            arcaneWoodPlanksCrossBaulk = new PipeModel(map.get(ARCANE_WOOD_PLANKS_CROSS_BAULK_CENTER), "arcane_wood_planks_cross_baulk");
            innocentWoodCrossBaulk = new PipeModel(map.get(INNOCENT_WOOD_CROSS_BAULK_CENTER), "innocent_wood_cross_baulk");
            strippedInnocentWoodCrossBaulk = new PipeModel(map.get(STRIPPED_INNOCENT_WOOD_CROSS_BAULK_CENTER), "stripped_innocent_wood_cross_baulk");
            innocentWoodPlanksCrossBaulk = new PipeModel(map.get(INNOCENT_WOOD_PLANKS_CROSS_BAULK_CENTER), "innocent_wood_planks_cross_baulk");
            corkBambooCrossBaulk = new PipeModel(map.get(CORK_BAMBOO_CROSS_BAULK_CENTER), "cork_bamboo_cross_baulk");
            corkBambooPlanksCrossBaulk = new PipeModel(map.get(CORK_BAMBOO_PLANKS_CROSS_BAULK_CENTER), "cork_bamboo_planks_cross_baulk");
            corkBambooChiseledPlanksCrossBaulk = new PipeModel(map.get(CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_CENTER), "cork_bamboo_chiseled_planks_cross_baulk");

            addPipeModel(map, WizardsReborn.MOD_ID, "arcane_wood_cross_baulk", "waterlogged=false", arcaneWoodCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "arcane_wood_cross_baulk", "waterlogged=true", arcaneWoodCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "stripped_arcane_wood_cross_baulk", "waterlogged=false", strippedArcaneWoodCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "stripped_arcane_wood_cross_baulk", "waterlogged=true", strippedArcaneWoodCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "arcane_wood_planks_cross_baulk", "waterlogged=false", arcaneWoodPlanksCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "arcane_wood_planks_cross_baulk", "waterlogged=true", arcaneWoodPlanksCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "innocent_wood_cross_baulk", "waterlogged=false", innocentWoodCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "innocent_wood_cross_baulk", "waterlogged=true", innocentWoodCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "stripped_innocent_wood_cross_baulk", "waterlogged=false", strippedInnocentWoodCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "stripped_innocent_wood_cross_baulk", "waterlogged=true", strippedInnocentWoodCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "innocent_wood_planks_cross_baulk", "waterlogged=false", innocentWoodPlanksCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "innocent_wood_planks_cross_baulk", "waterlogged=true", innocentWoodPlanksCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "cork_bamboo_cross_baulk", "waterlogged=false", corkBambooCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "cork_bamboo_cross_baulk", "waterlogged=true", corkBambooCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "cork_bamboo_planks_cross_baulk", "waterlogged=false", corkBambooPlanksCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "cork_bamboo_planks_cross_baulk", "waterlogged=true", corkBambooPlanksCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "cork_bamboo_chiseled_planks_cross_baulk", "waterlogged=false", corkBambooChiseledPlanksCrossBaulk);
            addPipeModel(map, WizardsReborn.MOD_ID, "cork_bamboo_chiseled_planks_cross_baulk", "waterlogged=true", corkBambooChiseledPlanksCrossBaulk);
        }

        @SubscribeEvent
        public static void completeModelBake(ModelEvent.BakingCompleted event) {
            fluidPipe.init(event.getModelManager());
            steamPipe.init(event.getModelManager());
            bakePipeModel(fluidExtractor, event.getModelManager());
            bakePipeModel(steamExtractor, event.getModelManager());
            bakePipeModel(orbitalFluidRetainer, event.getModelManager());
            bakePipeModel(alchemyMachine, event.getModelManager());
            bakePipeModel(alchemyBoiler, event.getModelManager());
            bakePipeModel(arcaneWoodFluidCasing, event.getModelManager());
            bakePipeModel(innocentWoodFluidCasing, event.getModelManager());
            bakePipeModel(corkBambooFluidCasing, event.getModelManager());
            bakePipeModel(wisestoneFluidCasing, event.getModelManager());
            bakePipeModel(arcaneWoodSteamCasing, event.getModelManager());
            bakePipeModel(innocentWoodSteamCasing, event.getModelManager());
            bakePipeModel(corkBambooSteamCasing, event.getModelManager());
            bakePipeModel(wisestoneSteamCasing, event.getModelManager());
            bakePipeModel(creativeFluidStorage, event.getModelManager());
            bakePipeModel(creativeSteamStorage, event.getModelManager());

            arcaneWoodCrossBaulk.init(event.getModelManager());
            strippedArcaneWoodCrossBaulk.init(event.getModelManager());
            arcaneWoodPlanksCrossBaulk.init(event.getModelManager());
            innocentWoodCrossBaulk.init(event.getModelManager());
            strippedInnocentWoodCrossBaulk.init(event.getModelManager());
            innocentWoodPlanksCrossBaulk.init(event.getModelManager());
            corkBambooCrossBaulk.init(event.getModelManager());
            corkBambooPlanksCrossBaulk.init(event.getModelManager());
            corkBambooChiseledPlanksCrossBaulk.init(event.getModelManager());
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BELT_LAYER, BeltModel::createBodyLayer);
            event.registerLayerDefinition(BAG_LAYER, BagModel::createBodyLayer);
            event.registerLayerDefinition(AMULET_LAYER, AmuletModel::createBodyLayer);
            event.registerLayerDefinition(COLLAR_LAYER, CollarModel::createBodyLayer);
            event.registerLayerDefinition(MUSHROOM_CAP_LAYER, MushroomCapModel::createBodyLayer);

            event.registerLayerDefinition(INVENTOR_WIZARD_ARMOR_LAYER, InventorWizardArmorModel::createBodyLayer);
            event.registerLayerDefinition(ARCANE_FORTRESS_ARMOR_LAYER, ArcaneFortressArmorModel::createBodyLayer);
            event.registerLayerDefinition(ARCANE_FORTRESS_SLIM_ARMOR_LAYER, ArcaneFortressSlimArmorModel::createBodyLayer);

            event.registerLayerDefinition(TOP_HAT_ARMOR_LAYER, TopHatArmorModel::createBodyLayer);
            event.registerLayerDefinition(SOUL_HUNTER_ARMOR_LAYER, SoulHunterArmorModel::createBodyLayer);
            event.registerLayerDefinition(MAGNIFICENT_MAID_ARMOR_LAYER, MagnificentMaidArmorModel::createBodyLayer);
            event.registerLayerDefinition(MAGNIFICENT_MAID_SLIM_ARMOR_LAYER, MagnificentMaidSlimArmorModel::createBodyLayer);

            event.registerLayerDefinition(SNIFFALO_SADDLE_LAYER, SniffaloSaddleArmorModel::createBodyLayer);
            event.registerLayerDefinition(SNIFFALO_CARPET_LAYER, SniffaloCarpetArmorModel::createBodyLayer);
            event.registerLayerDefinition(SNIFFALO_ARCANE_ARMOR_LAYER, SniffaloArcaneArmorModel::createBodyLayer);

            event.registerLayerDefinition(ALCHEMY_VIAL_LAYER, AlchemyVialModel::createBodyLayer);
            event.registerLayerDefinition(ALCHEMY_FLASK_LAYER, AlchemyFlaskModel::createBodyLayer);
            event.registerLayerDefinition(ALCHEMY_BOTTLE_LAYER, AlchemyBottleModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers event) {
            INVENTOR_WIZARD_ARMOR = new InventorWizardArmorModel(event.getEntityModels().bakeLayer(INVENTOR_WIZARD_ARMOR_LAYER));
            ARCANE_FORTRESS_ARMOR = new ArcaneFortressArmorModel(event.getEntityModels().bakeLayer(ARCANE_FORTRESS_ARMOR_LAYER));
            ARCANE_FORTRESS_SLIM_ARMOR = new ArcaneFortressSlimArmorModel(event.getEntityModels().bakeLayer(ARCANE_FORTRESS_SLIM_ARMOR_LAYER));

            TOP_HAT_ARMOR = new TopHatArmorModel(event.getEntityModels().bakeLayer(TOP_HAT_ARMOR_LAYER));
            SOUL_HUNTER_ARMOR = new SoulHunterArmorModel(event.getEntityModels().bakeLayer(SOUL_HUNTER_ARMOR_LAYER));
            MAGNIFICENT_MAID_ARMOR = new MagnificentMaidArmorModel(event.getEntityModels().bakeLayer(MAGNIFICENT_MAID_ARMOR_LAYER));
            MAGNIFICENT_MAID_SLIM_ARMOR = new MagnificentMaidSlimArmorModel(event.getEntityModels().bakeLayer(MAGNIFICENT_MAID_SLIM_ARMOR_LAYER));

            SNIFFALO_SADDLE = new SniffaloSaddleArmorModel(event.getEntityModels().bakeLayer(SNIFFALO_SADDLE_LAYER));
            SNIFFALO_CARPET = new SniffaloCarpetArmorModel(event.getEntityModels().bakeLayer(SNIFFALO_CARPET_LAYER));
            SNIFFALO_ARCANE_ARMOR = new SniffaloArcaneArmorModel(event.getEntityModels().bakeLayer(SNIFFALO_ARCANE_ARMOR_LAYER));

            ALCHEMY_VIAL = new AlchemyVialModel(event.getEntityModels().bakeLayer(ALCHEMY_VIAL_LAYER));
            ALCHEMY_FLASK = new AlchemyFlaskModel(event.getEntityModels().bakeLayer(ALCHEMY_FLASK_LAYER));
            ALCHEMY_BOTTLE = new AlchemyBottleModel(event.getEntityModels().bakeLayer(ALCHEMY_BOTTLE_LAYER));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void setupWandCrystalsModels() {
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":earth_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":water_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":air_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":fire_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":void_crystal");

        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":faceted_earth_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":faceted_water_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":faceted_air_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":faceted_fire_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":faceted_void_crystal");

        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":advanced_earth_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":advanced_water_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":advanced_air_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":advanced_fire_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":advanced_void_crystal");

        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":masterful_earth_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":masterful_water_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":masterful_air_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":masterful_fire_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":masterful_void_crystal");

        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":pure_earth_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":pure_water_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":pure_air_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":pure_fire_crystal");
        WandCrystalsModels.addCrystal(WizardsReborn.MOD_ID+":pure_void_crystal");
    }

    public static ModelLayerLocation addLayer(String layer) {
        return FluffyFurModels.addLayer(WizardsReborn.MOD_ID, layer);
    }

    public static ModelResourceLocation addCustomModel(String model) {
        return FluffyFurModels.addCustomModel(WizardsReborn.MOD_ID, model);
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
