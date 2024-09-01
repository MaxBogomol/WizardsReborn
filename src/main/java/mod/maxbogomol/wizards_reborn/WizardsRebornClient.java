package mod.maxbogomol.wizards_reborn;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurMod;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurPanorama;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.event.ClientEvents;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.event.ClientWorldEvent;
import mod.maxbogomol.wizards_reborn.client.event.KeyBindHandler;
import mod.maxbogomol.wizards_reborn.client.gui.TooltipEventHandler;
import mod.maxbogomol.wizards_reborn.client.model.block.PipeModel;
import mod.maxbogomol.wizards_reborn.client.model.item.ItemSkinsModels;
import mod.maxbogomol.wizards_reborn.client.model.item.WandCrystalsModels;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornItems;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class WizardsRebornClient {
    private static final String CATEGORY_KEY = "key.category."+WizardsReborn.MOD_ID+".general";
    public static final KeyMapping OPEN_SELECTION_MENU_KEY = new KeyMapping("key."+WizardsReborn.MOD_ID+".selection_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY_KEY);
    public static final KeyMapping OPEN_BAG_MENU_KEY = new KeyMapping("key."+WizardsReborn.MOD_ID+".bag_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_KEY);
    public static final KeyMapping NEXT_SPELL_KEY = new KeyMapping("key."+WizardsReborn.MOD_ID+".next_spell", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, CATEGORY_KEY);
    public static final KeyMapping PREVIOUS_SPELL_KEY = new KeyMapping("key."+WizardsReborn.MOD_ID+".previous_spell", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, CATEGORY_KEY);
    public static final KeyMapping SPELL_SETS_TOGGLE_KEY = new KeyMapping("key."+WizardsReborn.MOD_ID+".spell_sets_toggle", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, CATEGORY_KEY);

    public static ShaderInstance GLOWING_SHADER, GLOWING_SPRITE_SHADER, GLOWING_PARTICLE_SHADER, SPRITE_PARTICLE_SHADER, FLUID_SHADER;

    public static ShaderInstance getGlowingShader() { return GLOWING_SHADER; }
    public static ShaderInstance getGlowingSpriteShader() { return GLOWING_SPRITE_SHADER; }
    public static ShaderInstance getGlowingParticleShader() { return GLOWING_PARTICLE_SHADER; }
    public static ShaderInstance getSpriteParticleShader() { return SPRITE_PARTICLE_SHADER; }
    public static ShaderInstance getFluidShader() { return FLUID_SHADER; }

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

    public static Random random = new Random();

    public static class ClientOnly {
        public static void clientInit() {
            IEventBus forgeBus = MinecraftForge.EVENT_BUS;
            setupWandCrystalsModels();
            WissenWandItem.setupTooltips();

            forgeBus.addListener(ClientTickHandler::clientTickEnd);
            forgeBus.addListener(ClientWorldEvent::onTick);
            forgeBus.addListener(ClientWorldEvent::onRender);
            forgeBus.addListener(TooltipEventHandler::onPostTooltipEvent);
            forgeBus.addListener(KeyBindHandler::onInput);
            forgeBus.addListener(KeyBindHandler::onKey);
            forgeBus.addListener(KeyBindHandler::onMouseKey);
            forgeBus.register(new ClientEvents());
        }

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
    }

    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            setupMenu();
            ArcanemiconChapters.init();
        });
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event) {
            for (String crystal : WandCrystalsModels.getCrystals()) {
                event.register(WandCrystalsModels.getModelLocationCrystal(crystal));
            }

            for (String skin : ItemSkinsModels.getSkins()) {
                event.register(ItemSkinsModels.getModelLocationSkin(skin));
            }

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
        public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
            Map<ResourceLocation, BakedModel> map = event.getModels();

            for (String skin : ItemSkinsModels.getSkins()) {
                BakedModel model = map.get(ItemSkinsModels.getModelLocationSkin(skin));
                ItemSkinsModels.addModelSkins(skin, model);
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
        public static void afterModelBake(ModelEvent.BakingCompleted event) {
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
        public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
            event.register(WizardsRebornClient.OPEN_SELECTION_MENU_KEY);
            event.register(WizardsRebornClient.OPEN_BAG_MENU_KEY);
            event.register(WizardsRebornClient.NEXT_SPELL_KEY);
            event.register(WizardsRebornClient.PREVIOUS_SPELL_KEY);
            event.register(WizardsRebornClient.SPELL_SETS_TOGGLE_KEY);
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
    }

    public static FluffyFurMod MOD_INSTANCE;
    public static FluffyFurPanorama MAGICAL_ORIGINS_PANORAMA;

    public static void setupMenu() {
        MOD_INSTANCE = new FluffyFurMod(WizardsReborn.MOD_ID, WizardsReborn.NAME, WizardsReborn.VERSION).setDev("MaxBogomol").setItem(new ItemStack(WizardsRebornItems.ARCANUM.get()))
                .setEdition(WizardsReborn.VERSION_NUMBER).setNameColor(new Color(205, 237, 254)).setVersionColor(new Color(255, 243, 177))
                .setDescription(Component.translatable("mod_description.wizards_reborn"))
                .addGithubLink("https://github.com/MaxBogomol/WizardsReborn")
                .addCurseForgeLink("https://www.curseforge.com/minecraft/mc-mods/wizards-reborn")
                .addModrinthLink("https://modrinth.com/mod/wizards-reborn");
        MAGICAL_ORIGINS_PANORAMA = new FluffyFurPanorama(WizardsReborn.MOD_ID + ":magical_origins", Component.translatable("panorama.wizards_reborn.magical_origins"))
                .setMod(MOD_INSTANCE).setItem(new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get())).setSort(0)
                .setTexture(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/title/background/panorama"))
                .setLogo(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/title/wizards_reborn.png"));

        FluffyFurClient.registerMod(MOD_INSTANCE);
        FluffyFurClient.registerPanorama(MAGICAL_ORIGINS_PANORAMA);
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
