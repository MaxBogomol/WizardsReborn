package mod.maxbogomol.wizards_reborn.registry.client;

import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.model.armor.*;
import mod.maxbogomol.wizards_reborn.client.model.block.AlchemyBottleModel;
import mod.maxbogomol.wizards_reborn.client.model.block.AlchemyFlaskModel;
import mod.maxbogomol.wizards_reborn.client.model.block.AlchemyVialModel;
import mod.maxbogomol.wizards_reborn.client.model.curio.*;
import mod.maxbogomol.wizards_reborn.client.model.sniffalo.SniffaloArcaneArmorModel;
import mod.maxbogomol.wizards_reborn.client.model.sniffalo.SniffaloCarpetArmorModel;
import mod.maxbogomol.wizards_reborn.client.model.sniffalo.SniffaloSaddleArmorModel;
import mod.maxbogomol.wizards_reborn.client.render.fluid.FluidCuboid;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

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

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void modelRegistry(ModelEvent.RegisterAdditional event) {
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

    public static ModelLayerLocation addLayer(String layer) {
        return FluffyFurModels.addLayer(WizardsReborn.MOD_ID, layer);
    }

    public static ModelResourceLocation addCustomModel(String model) {
        return FluffyFurModels.addCustomModel(WizardsReborn.MOD_ID, model);
    }
}
