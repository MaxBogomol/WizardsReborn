package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArcanumDustTransmutationPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_arcanum_dust_transmutation_page.png");
    public ItemStack result;
    public ItemStack input;

    public ArcanumDustTransmutationPage(ItemStack result, ItemStack input) {
        super(BACKGROUND);
        this.result = result;
        this.input = input;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        drawItem(gui, mStack, new ItemStack(WizardsReborn.ARCANUM_DUST.get()),x + 56, y + 18, mouseX, mouseY);
        drawItem(gui, mStack, input,x + 56, y + 69, mouseX, mouseY);
        drawItem(gui, mStack, result,x + 56, y + 123, mouseX, mouseY);
    }
}