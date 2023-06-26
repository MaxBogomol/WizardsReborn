package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class WissenCrystallizerPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_wissen_crystallizer_page.png");
    public ItemStack result;
    public ItemStack[] inputs;

    public WissenCrystallizerPage(ItemStack result, ItemStack... inputs) {
        super(BACKGROUND);
        this.result = result;
        this.inputs = inputs;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        int index = 1;
        double angleBetweenEach = 360.0 / (inputs.length - 1);
        Vector2f point = new Vector2f(56, 22), center = new Vector2f(56, 57);
        boolean first = false;

        for (ItemStack o : inputs) {
            if (first) {
                drawItem(gui, mStack, o,(int) point.x + x, (int) point.y + y, mouseX, mouseY);
                index += 1;
                point = rotatePointAbout(point, center, angleBetweenEach);
            } else {
                drawItem(gui, mStack, o,x + 56, y + 57, mouseX, mouseY);
                first = true;
            }
        }
        drawItem(gui, mStack, result,x + 56, y + 128, mouseX, mouseY);
    }

    public static Vector2f rotatePointAbout(Vector2f in, Vector2f about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vector2f((float) newX, (float) newY);
    }
}