package mod.maxbogomol.wizards_reborn.client.gui;

import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;

public class HUDEventHandler {
    private HUDEventHandler() {}

    public static void onDrawScreenPost(RenderGuiOverlayEvent.Pre event) {
        WissenWandItem.drawWissenGui(event.getGuiGraphics());
        ArcaneWandItem.drawWandGui(event.getGuiGraphics());
    }
}
