package mod.maxbogomol.wizards_reborn.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.lwjgl.opengl.GL11;

public class TooltipEventHandler {
    private TooltipEventHandler() {}

    public static void onPostTooltipEvent(RenderTooltipEvent.Pre event) {
        ItemStack stack = event.getItemStack();

        int x = event.getX();
        int y = event.getY();
        PoseStack ms = event.getGraphics().pose();

        if (stack.getItem() instanceof IWissenItem) {
            IWissenItem item = (IWissenItem) stack.getItem();
            CompoundTag nbt = stack.getTag();

            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            ms.translate(0, 0, 410.0);

            event.getGraphics().blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), x - 4, y - 15, 0, 0, 48, 10, 64, 64);

            if (nbt!=null) {
                if (nbt.contains("wissen")) {
                    int width_wissen = 32;
                    width_wissen /= (double) item.getMaxWissen() / (double) WissenItemUtils.getWissen(stack);
                    event.getGraphics().blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), x + 4, y - 14, 0, 10, width_wissen, 8, 64, 64);
                }
            }

            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
    }
}
