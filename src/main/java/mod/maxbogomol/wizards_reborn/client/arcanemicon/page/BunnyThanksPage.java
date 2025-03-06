package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BunnyThanksPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/blank_page.png");
    public String text, meow;

    public BunnyThanksPage(String textKey) {
        super(BACKGROUND);
        this.text = textKey;
        this.meow = textKey + ".meow";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 4, 124);

        int height = Minecraft.getInstance().font.lineHeight;
        String title = I18n.get(this.meow + ".0");
        gui.pose().pushPose();
        gui.pose().translate(x + 6, y + 42, 0);
        gui.pose().mulPose(Axis.ZP.rotationDegrees(-45f));
        drawText(book, gui, title, 0, 0);
        gui.pose().popPose();

        title = I18n.get(this.meow + ".1");
        drawText(book, gui, title, x + 43, y + 39);

        title = I18n.get(this.meow + ".0");
        drawText(book, gui, title, x + 73, y + 59);

        title = I18n.get(this.meow + ".0");
        drawText(book, gui, title, x + 13, y + 76);

        title = I18n.get(this.meow + ".4");
        drawText(book, gui, title, x + 6, y + 120);

        title = I18n.get(this.meow + ".1");
        drawText(book, gui, title, x + 26, y + 99);

        title = I18n.get(this.meow + ".2");
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 84 - height);
    }
}