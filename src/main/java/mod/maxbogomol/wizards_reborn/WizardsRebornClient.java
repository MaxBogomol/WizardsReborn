package mod.maxbogomol.wizards_reborn;

import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.event.SplashHandler;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurMod;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurPanorama;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.event.WizardsRebornClientEvents;
import mod.maxbogomol.wizards_reborn.client.event.WizardsRebornKeyBindHandler;
import mod.maxbogomol.wizards_reborn.client.gui.WizardsRebornTooltipHandler;
import mod.maxbogomol.wizards_reborn.client.render.WissenWandRenderHandler;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornCreativeTabs;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.awt.*;
import java.util.Random;

public class WizardsRebornClient {
    public static Random random = new Random();

    public static class ClientOnly {
        public static void clientInit() {
            IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
            IEventBus forgeBus = MinecraftForge.EVENT_BUS;
            WizardsRebornModels.setupWandCrystalsModels();
            WissenWandItem.setupTooltips();

            forgeBus.addListener(WissenWandRenderHandler::wissenWandTick);
            forgeBus.addListener(WissenWandRenderHandler::wissenWandRender);
            forgeBus.addListener(WizardsRebornTooltipHandler::onPostTooltipEvent);
            forgeBus.addListener(WizardsRebornKeyBindHandler::onInput);
            forgeBus.addListener(WizardsRebornKeyBindHandler::onKey);
            forgeBus.addListener(WizardsRebornKeyBindHandler::onMouseKey);
            forgeBus.register(new WizardsRebornClientEvents());

            eventBus.addListener(WizardsRebornCreativeTabs::addCreativeTabContent);
        }
    }

    public static void clientSetup(final FMLClientSetupEvent event) {
        setupMenu();
        setupSplashes();
        ArcanemiconChapters.init();
    }

    public static FluffyFurMod MOD_INSTANCE;
    public static FluffyFurPanorama MAGICAL_ORIGINS_PANORAMA;

    public static void setupMenu() {
        MOD_INSTANCE = new FluffyFurMod(WizardsReborn.MOD_ID, WizardsReborn.NAME, WizardsReborn.VERSION).setDev("MaxBogomol").setItem(new ItemStack(WizardsRebornItems.ARCANUM.get()))
                .setEdition(WizardsReborn.VERSION_NUMBER).setNameColor(new Color(205, 237, 254)).setVersionColor(new Color(255, 243, 177))
                .setDescription(Component.translatable("mod_description.wizards_reborn"))
                .addGithubLink("https://github.com/MaxBogomol/WizardsReborn")
                .addCurseForgeLink("https://www.curseforge.com/minecraft/mc-mods/wizards-reborn")
                .addModrinthLink("https://modrinth.com/mod/wizards-reborn")
                .addDiscordLink("https://discord.gg/cKf55qNugw");
        MAGICAL_ORIGINS_PANORAMA = new FluffyFurPanorama(WizardsReborn.MOD_ID + ":magical_origins", Component.translatable("panorama.wizards_reborn.magical_origins"))
                .setMod(MOD_INSTANCE).setItem(new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get())).setSort(0)
                .setTexture(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/title/background/panorama"))
                .setLogo(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/title/wizards_reborn.png"));

        FluffyFurClient.registerMod(MOD_INSTANCE);
        FluffyFurClient.registerPanorama(MAGICAL_ORIGINS_PANORAMA);
    }

    public static void setupSplashes() {
        SplashHandler.addSplash("Also try Valoria!");
        SplashHandler.addSplash("Also try Implosion!");
    }
}
