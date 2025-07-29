package mod.maxbogomol.wizards_reborn.client.arcanemicon.titled;

import com.google.common.collect.Lists;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.BlockEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class TitledBlockPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/block_page.png");
    public String text, title;
    public List<BlockEntry> blocks;

    public TitledBlockPage(String textKey, BlockEntry... blocks) {
        super(BACKGROUND);
        this.text = textKey;
        this.title = textKey + ".title";
        this.blocks = Lists.newArrayList(blocks);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(BACKGROUND, x, y, 128, 0, 128, 20);

        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 24, 124);

        int lines = (int) Math.ceil(blocks.size() / 6);
        int line = 0;
        int ii = 0;

        for (int i = 0; i < blocks.size(); i++) {
            RenderUtil.blitOffset += 10.0F;
            int width = 0;
            if (lines == line) {
                width = (120 - (20 * (6 - ((lines + 1) * 6 - blocks.size())))) / 2;
            }
            if (!blocks.get(i).block.isEmpty()) {
                gui.blit(BACKGROUND, x + 4 + width + (ii * 20), y + 120 - (20 * line), 128, 20, 18, 18, 256, 256);
            }
            blocks.get(i).render(gui, x + 4 + width + (ii * 20) + 1, y + 120 - (20 * line) + 1, mouseX, mouseY, i);

            if (ii >= 5) {
                line++;
                ii = 0;
            } else {
                ii++;
            }
        }
        RenderUtil.blitOffset -= 10.0F * blocks.size();

        line = 0;
        ii = 0;
        for (int i = 0; i < blocks.size(); i++) {
            int width = 0;
            if (lines == line) {
                width = (120 - (20 * (6 - ((lines + 1) * 6 - blocks.size())))) / 2;
            }
            blocks.get(i).drawTooltip(book, x + 4 + width + (ii * 20) + 1, y + 120 - (20 * line) + 1, mouseX, mouseY);

            if (ii >= 5) {
                line++;
                ii = 0;
            } else {
                ii++;
            }
        }
    }
}