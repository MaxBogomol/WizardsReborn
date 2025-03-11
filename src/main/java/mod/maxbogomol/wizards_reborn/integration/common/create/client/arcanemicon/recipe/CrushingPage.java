package mod.maxbogomol.wizards_reborn.integration.common.create.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.RecipePage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrushingPage extends RecipePage {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/integration/create/crushing_page.png");
    public ItemStack input;
    public ItemStack[] results;

    public CrushingPage(ItemStack input, ItemStack... results) {
        super(BACKGROUND);
        this.input = input;
        this.results = results;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        int i = 0;
        for (ItemStack o : results) {
            int offset = 54 + (i * 22) - ((results.length - 1) * 11);
            gui.blit(BACKGROUND, x + offset, y + 121, 128, 0, 20, 20, 256, 256);
            drawItem(book, gui, o,x + offset + 2, y + 121 + 2, mouseX, mouseY);
            i++;
        }
        drawItem(book, gui, input,x + 56, y + 18, mouseX, mouseY);
    }
}