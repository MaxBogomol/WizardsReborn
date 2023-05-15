package mod.maxbogomol.wizards_reborn.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class RenderUtils {
    public static void renderItemModelInGui(ItemStack stack, int x, int y, int xSize, int ySize, int zSize) {
        Minecraft mc = Minecraft.getInstance();

        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)x+(xSize/2), (float)y+(ySize/2), 10.0F);
        RenderSystem.scalef((float)xSize/16, (float)ySize/16, (float)zSize/16);
        RenderSystem.translatef((float)-(x+(xSize/2)), (float)-(y+(ySize/2)), 0.0F);
        mc.getItemRenderer().renderItemAndEffectIntoGUI(mc.player, stack, x, y);
        RenderSystem.popMatrix();
    }
}
