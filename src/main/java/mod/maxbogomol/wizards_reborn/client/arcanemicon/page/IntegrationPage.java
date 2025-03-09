package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IntegrationPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/integration/integration_page.png");
    public String modId, modName;

    public IntegrationPage(boolean newBackground, String modId, String modName) {
        super(BACKGROUND);
        this.modId = modId;
        this.modName = modName;
        background = BACKGROUND;
        if (newBackground) {
            background = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/integration/" + modId + "/" + modId + "_page.png");
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        String title = modName;
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 120 - Minecraft.getInstance().font.lineHeight);
    }
}