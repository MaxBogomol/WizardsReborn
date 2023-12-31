package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CenserPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/censer_page.png");
    public List<MobEffectInstance> result;
    public ItemStack input;

    public CenserPage(List<MobEffectInstance> result, ItemStack input) {
        super(BACKGROUND);
        this.result = result;
        this.input = input;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        if (mouseX >= x + 38 && mouseY >= y + 47 && mouseX <= x + 38 + 55 && mouseY <= y + 47 + 44) {
            List<Component> tooltips = new ArrayList<>();
            PotionUtils.addPotionTooltip(result, tooltips, 1.0f);
            gui.renderTooltip(Minecraft.getInstance().font, tooltips, Optional.empty(), ItemStack.EMPTY, mouseX, mouseY);
        }
        drawItem(book, gui, input,x + 56, y + 100, mouseX, mouseY);
    }
}