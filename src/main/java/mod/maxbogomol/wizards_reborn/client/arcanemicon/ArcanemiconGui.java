package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

public class ArcanemiconGui extends Screen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon.png");
    public static int xSize = 312;
    public static int ySize = 180;

    public Chapter currentChapter;
    public int currentPage = 0;
    public ItemStack currentItem;

    public ArcanemiconGui() {
        super(new TranslationTextComponent("gui.wizards_reborn.arcanemicon.title"));
        currentChapter = ArcanemiconChapters.ARCANE_NATURE_INDEX;
    }

    public void changeChapter(Chapter next) {
        currentChapter = next;
        currentPage = 0;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindTexture(BACKGROUND);

        currentItem = ItemStack.EMPTY;

        this.width = mc.getMainWindow().getScaledWidth();
        this.height = mc.getMainWindow().getScaledHeight();
        int guiLeft = (width - xSize) / 2, guiTop = (height - ySize) / 2;
        blit(matrixStack, guiLeft, guiTop, 0, 180, xSize, ySize, 512, 512);

        for (int i = 0; i < ArcanemiconChapters.categories.size(); i ++) {
            int y = guiTop + 12 + (i % 8) * 20;
            ArcanemiconChapters.categories.get(i).draw(this, matrixStack, guiLeft + (i >= 8 ? 301 : 10), y, i >= 8, mouseX, mouseY);
        }

        mc.getTextureManager().bindTexture(BACKGROUND);
        blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);
        Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
        if (left != null) left.fullRender(this, matrixStack, guiLeft + 10, guiTop + 8, mouseX, mouseY);
        if (right != null) right.fullRender(this, matrixStack, guiLeft + 174, guiTop + 8, mouseX, mouseY);

        mc.getTextureManager().bindTexture(BACKGROUND);
        if (currentPage > 0) {
            int x = 11, y = 151;
            int v = 0;
            if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX <= guiLeft + x + 32 && mouseY <= guiTop + y + 16) v += 16;
            blit(matrixStack, guiLeft + x, guiTop + y, 351, v, 32, 16, 512, 512);
        }
        if (currentPage + 2 < currentChapter.size()) {
            int x = 269, y = 151;
            int v = 0;
            if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX <= guiLeft + x + 32 && mouseY <= guiTop + y + 16) v += 16;
            blit(matrixStack, guiLeft + x, guiTop + y, 383, v, 32, 16, 512, 512);
        }

        for (int i = 0; i < ArcanemiconChapters.categories.size(); i ++) {
            int y = guiTop + 12 + (i % 8) * 20;
            ArcanemiconChapters.categories.get(i).drawTooltip(this, matrixStack, guiLeft + (i >= 8 ? 301 : 10), y, i >= 8, mouseX, mouseY);
        }

        renderTooltip(matrixStack, currentItem, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            Minecraft mc = Minecraft.getInstance();
            this.width = mc.getMainWindow().getScaledWidth();
            this.height = mc.getMainWindow().getScaledHeight();
            int guiLeft = (width - xSize) / 2, guiTop = (height - ySize) / 2;

            if (currentPage > 0) {
                int x = guiLeft + 11, y = guiTop + 151;
                if (mouseX >= x && mouseY >= y && mouseX <= x + 32 && mouseY <= y + 16) {
                    currentPage -= 2;
                    Minecraft.getInstance().player.playSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }
            if (currentPage + 2 < currentChapter.size()) {
                int x = guiLeft + 269, y = guiTop + 151;
                if (mouseX >= x && mouseY >= y && mouseX <= x + 32 && mouseY <= y + 16) {
                    currentPage += 2;
                    Minecraft.getInstance().player.playSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }

            for (int i = 0; i < ArcanemiconChapters.categories.size(); i ++) {
                int y = guiTop + 12 + (i % 8) * 20;
                if (ArcanemiconChapters.categories.get(i).click(this, guiLeft + (i >= 8 ? 301 : 10), y, i >= 8, (int)mouseX, (int)mouseY)) return true;
            }

            Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
            if (left != null) if (left.click(this,guiLeft + 11, guiTop + 9, (int)mouseX, (int)mouseY)) return true;
            if (right != null) if (right.click(this,guiLeft + 175, guiTop + 9, (int)mouseX, (int)mouseY)) return true;
        }
        return false;
    }

    @Override
    public void renderTooltip(MatrixStack mStack, ItemStack stack, int x, int y) {
        if (!stack.isEmpty()) super.renderTooltip(mStack, stack, x, y);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
