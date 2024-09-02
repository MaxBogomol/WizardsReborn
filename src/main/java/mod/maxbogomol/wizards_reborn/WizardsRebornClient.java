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
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
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

    public static Random random = new Random();

    public static class ClientOnly {
        public static void clientInit() {
            IEventBus forgeBus = MinecraftForge.EVENT_BUS;
            WizardsRebornModels.setupWandCrystalsModels();
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
    }

    public static void clientSetup(final FMLClientSetupEvent event) {
        setupMenu();
        ArcanemiconChapters.init();
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents {
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
}
