package mod.maxbogomol.wizards_reborn.client.toast;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArcanemiconToast implements Toast {

    public ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_toast.png");

    @Override
    public int width() {
        return 110;
    }

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
        guiGraphics.blit(TEXTURE, 0, 0, 0, 0, this.width(), this.height(), 256, 32);

        guiGraphics.renderItem(Items.BOOK.getDefaultInstance(), 8, 8);
        guiGraphics.renderItem(WizardsRebornItems.ARCANUM.get().getDefaultInstance(), 44, 8);
        guiGraphics.renderItem(WizardsRebornItems.ARCANEMICON.get().getDefaultInstance(), 86, 8);

        return (double)timeSinceLastVisible >= 5000.0D * toastComponent.getNotificationDisplayTimeMultiplier() ? Visibility.HIDE : Visibility.SHOW;
    }
}
