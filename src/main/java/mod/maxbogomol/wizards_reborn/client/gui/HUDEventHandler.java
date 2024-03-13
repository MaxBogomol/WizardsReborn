package mod.maxbogomol.wizards_reborn.client.gui;

import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

public class HUDEventHandler {
    private HUDEventHandler() {}

    public static void onDrawScreenPost(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay().id() == VanillaGuiOverlay.CROSSHAIR.id()) {
            WissenWandItem.drawWissenGui(event.getGuiGraphics());
            ArcaneWandItem.drawWandGui(event.getGuiGraphics());
        }
    }
}
