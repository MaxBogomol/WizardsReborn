package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TextPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_blank_page.png");
    public String text;

    public TextPage(String textKey) {
        super(BACKGROUND);
        this.text = textKey;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(gui, mStack, I18n.format(text), x + 4, y + 4, 124);
    }
}