package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArcaneWorkbenchPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_arcane_workbench_page.png");
    public ItemStack result;
    public ItemStack[] inputs;

    public ArcaneWorkbenchPage(ItemStack result, ItemStack... inputs) {
        super(BACKGROUND);
        this.result = result;
        this.inputs = inputs;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                int index = i * 3 + j;
                if (index < inputs.length && !inputs[index].isEmpty())
                    drawItem(gui, mStack, inputs[index], x + 38 + j * 18, y + 38 + i * 18, mouseX, mouseY);
            }
        }
        drawItem(gui, mStack, inputs[9], x + 57, y + 17, mouseX, mouseY);
        drawItem(gui, mStack, inputs[10], x + 97, y + 57, mouseX, mouseY);
        drawItem(gui, mStack, inputs[11], x + 57, y + 97, mouseX, mouseY);
        drawItem(gui, mStack, inputs[12], x + 17, y + 57, mouseX, mouseY);
        drawItem(gui, mStack, result,x + 56, y + 128, mouseX, mouseY);
    }
}