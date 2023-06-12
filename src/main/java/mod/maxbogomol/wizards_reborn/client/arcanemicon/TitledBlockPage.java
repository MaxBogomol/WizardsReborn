package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class TitledBlockPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_block_page.png");
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
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
        gui.blit(mStack, x, y, 128, 0, 128, 20);

        String title = I18n.format(this.title);
        int titleWidth = Minecraft.getInstance().fontRenderer.getStringWidth(title);
        drawText(gui, mStack, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().fontRenderer.FONT_HEIGHT);
        drawWrappingText(gui, mStack, I18n.format(text), x + 4, y + 24, 124);

        int lines = (int) Math.ceil(blocks.size() / 6);
        int line = 0;
        int ii = 0;

        for (int i = 0; i < blocks.size(); i++) {
            Minecraft.getInstance().getItemRenderer().zLevel -= 1.0F;
            int width = 0;
            if (lines == line) {
                width = (120 - (20 * (6 - ((lines + 1) * 6 - blocks.size())))) / 2;
            }
            if (!blocks.get(i).block.isEmpty()) {
                Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
                gui.blit(mStack, x + 4 + width + (ii * 20), y + 120 - (20 * line), 128, 20, 18, 18, 256, 256);
            }
            blocks.get(i).render(mStack, x + 4 + width + (ii * 20) + 1, y + 120 - (20 * line) + 1, mouseX, mouseY);

            if (ii >= 5) {
                line++;
                ii = 0;
            } else {
                ii++;
            }
        }
        Minecraft.getInstance().getItemRenderer().zLevel += 1.0F * blocks.size();

        line = 0;
        ii = 0;
        for (int i = 0; i < blocks.size(); i++) {
            int width = 0;
            if (lines == line) {
                width = (120 - (20 * (6 - ((lines + 1) * 6 - blocks.size())))) / 2;
            }
            blocks.get(i).drawTooltip(gui, mStack, x + 4 + width + (ii * 20) + 1, y + 120 - (20 * line) + 1, mouseX, mouseY);

            if (ii >= 5) {
                line++;
                ii = 0;
            } else {
                ii++;
            }
        }
    }
}