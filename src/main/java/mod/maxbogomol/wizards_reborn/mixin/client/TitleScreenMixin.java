package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {

    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (WizardsRebornClient.optifinePresent) {
            TitleScreen self = (TitleScreen) ((Object) this);
            Font font = Minecraft.getInstance().font;
            String string = Component.translatable("gui.wizards_reborn.menu.optifine.0").getString();
            guiGraphics.drawString(font, string, (self.width - font.width(string)) / 2, 1, 16777215);
            string = Component.translatable("gui.wizards_reborn.menu.optifine.1").getString();
            guiGraphics.drawString(font, string, (self.width - font.width(string)) / 2, 2 + font.lineHeight, 16777215);
        }

        /*TitleScreen self = (TitleScreen) ((Object) this);
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        RenderUtils.renderItemModelInGui(new ItemStack(WizardsReborn.MOR_ITEM.get()), self.width / 2f, self.height / 2f, 320, 320, 320, 10, 10, 10);

        RenderUtils.renderItemModelInGui(new ItemStack(WizardsReborn.ALCHEMY_GLASS_ITEM.get()), self.width / 2f - 200, self.height / 2f, 160, 160, 160, 10, 10, -100);

        RenderUtils.renderItemModelInGui(new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()), self.width / 2f, self.height / 2f - 200, 160, 160, 160, 100, 0, 110);*/
    }
}
