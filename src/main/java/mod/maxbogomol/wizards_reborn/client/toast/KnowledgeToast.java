package mod.maxbogomol.wizards_reborn.client.toast;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeHandler;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KnowledgeToast implements Toast {

    public String id;
    public int count = 1;
    public static KnowledgeToast instance;
    public boolean all;
    public boolean articles;
    public ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/knowledge_toast.png");

    public KnowledgeToast(String id) {
        this.id = id;
    }

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
        guiGraphics.blit(TEXTURE, 0, 0, 0, 0, this.width(), this.height(), 256, 32);

        if (all) {
            guiGraphics.renderItem(WizardsRebornItems.ARCANEMICON.get().getDefaultInstance(), 8, 8);
        } else if (!id.isEmpty()) {
            Knowledge knowledge = KnowledgeHandler.getKnowledge(id);
            guiGraphics.renderItem(knowledge.getIcon(), 8, 8);
        }

        guiGraphics.drawString(toastComponent.getMinecraft().font, getNameWithCount(), 30, 7, 16776960, false);
        if (articles) {
            guiGraphics.drawString(toastComponent.getMinecraft().font, Component.translatable("knowledge.toast.wizards_reborn.new_articles"), 30, 18, -1, false);
        }

        if (all) {
            return (double) timeSinceLastVisible >= 5000.0D * toastComponent.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
        }

        return (double) timeSinceLastVisible >= 5000.0D * toastComponent.getNotificationDisplayTimeMultiplier() + (count * 100.0D) ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    public Component getNameWithCount() {
        Component component = Component.translatable("knowledge.toast.wizards_reborn.new_knowledge");
        if (count > 1) {
            component = Component.empty().append(component).append(Component.literal(" ")).append(Component.literal(String.valueOf(count))).append(Component.literal("x"));
        }
        return component;
    }
}
