package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EditionPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/edition_page.png");

    public EditionPage() {
        super(BACKGROUND);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        super.render(book, gui, x, y, mouseX, mouseY);

        String titleW = "Wizard's";
        String titleR = "Reborn";
        int titleWWidth = Minecraft.getInstance().font.width(titleW) + titleW.length();
        int titleRWidth = Minecraft.getInstance().font.width(titleR) + titleR.length();
        float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 5f;

        int i = 0;
        int offset = 0;
        for (char c : titleW.toCharArray()) {
            String text = String.valueOf(c);
            double ii = Math.toRadians(-ticks + i * 10);
            drawText(book, gui, text, (int) (x + 64 - titleWWidth / 2 + offset + Math.sin(ii)),
                    (int) (y + 15 - Minecraft.getInstance().font.lineHeight + Math.cos(ii)));
            offset = offset + Minecraft.getInstance().font.width(text) + 1;
            i++;
        }

        i = 0;
        offset = 0;
        for (char c : titleR.toCharArray()) {
            String text = String.valueOf(c);
            double ii = Math.toRadians(-ticks + 90 + i * 10);
            drawText(book, gui, text, (int) (x + 64 - titleRWidth / 2 + offset + Math.sin(ii)),
                    (int) (y + 31 - Minecraft.getInstance().font.lineHeight + Math.cos(ii)));
            offset = offset + Minecraft.getInstance().font.width(text) + 1;
            i++;
        }

        int edition = WizardsReborn.VERSION_NUMBER;
        String title = I18n.get("wizards_reborn.arcanemicon.edition", edition);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 145 - Minecraft.getInstance().font.lineHeight);
    }
}
