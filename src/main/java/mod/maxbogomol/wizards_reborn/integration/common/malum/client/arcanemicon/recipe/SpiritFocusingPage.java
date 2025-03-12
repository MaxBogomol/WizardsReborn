package mod.maxbogomol.wizards_reborn.integration.common.malum.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.RecipePage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpiritFocusingPage extends RecipePage {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/integration/malum/spirit_focusing_page.png");
    public ItemStack result;
    public ItemStack input;

    public ItemStack[] spirits;

    public SpiritFocusingPage(ItemStack result, ItemStack input) {
        super(BACKGROUND);
        this.result = result;
        this.input = input;
    }

    public SpiritFocusingPage setSpirits(ItemStack... items) {
        spirits = items;
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawItem(book, gui, input, x + 56, y + 56, mouseX, mouseY);
        drawItem(book, gui, result,x + 56, y + 128, mouseX, mouseY);
        int i = 0;
        for (ItemStack o : spirits) {
            int offset = 56 - 5 + (i * 26) - ((spirits.length - 1) * 13);
            gui.blit(BACKGROUND, x + offset, y + 12, 128, 0, 26, 26, 256, 256);
            drawItem(book, gui, o,x + offset + 5, y + 12 + 5, mouseX, mouseY);
            i++;
        }
        renderChanged(book, gui, x, y, mouseX, mouseY);
    }
}