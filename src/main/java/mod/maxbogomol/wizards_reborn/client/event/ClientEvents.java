package mod.maxbogomol.wizards_reborn.client.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.components.CustomLogoRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {
    @SubscribeEvent
    public void openMainMenu(ScreenEvent.Opening event) {
        if (event.getScreen() instanceof TitleScreen titleScreen) {
            ResourceLocation base = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/title/background/panorama");
            TitleScreen.CUBE_MAP = new CubeMap(base);
            titleScreen.panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
            titleScreen.logoRenderer = new CustomLogoRenderer(false);
        }
    }
}
