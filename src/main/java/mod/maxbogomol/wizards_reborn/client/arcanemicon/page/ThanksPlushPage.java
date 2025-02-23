package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThanksPlushPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/thanks_plush_page.png");
    public String text, name, say;
    public ItemStack plush;

    public ThanksPlushPage(String textKey, ItemStack plush) {
        super(BACKGROUND);
        this.text = textKey;
        this.name = textKey + ".name";
        this.say = textKey + ".say";
        this.plush = plush;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 4, 124);

        String name = I18n.get(this.name);
        int nameWidth = Minecraft.getInstance().font.width(name);
        drawText(book, gui, I18n.get(name), x + 64 - nameWidth / 2, y + 136 - Minecraft.getInstance().font.lineHeight);

        float partialTicks = Minecraft.getInstance().getPartialTick();
        double tick = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        if (plush != ItemStack.EMPTY) RenderUtil.renderFloatingItemModelIntoGUI(gui, plush, x + 56, y + 104, (float) tick, 0);

        if (mouseX >= x + 54 && mouseY >= y + 104 && mouseX <= x + 70 && mouseY <= y + 120) {
            float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.05f;
            int packColor = ColorUtil.packColor(ColorUtil.rainbowColor(ticks));

            List<Component> list = new ArrayList<>();
            list.add(Component.translatable(name).withStyle(Style.EMPTY.withColor(packColor)));
            list.add(Component.translatable(say).withStyle(ChatFormatting.GRAY));

            gui.renderTooltip(Minecraft.getInstance().font, list, Optional.empty(), mouseX, mouseY);
        }
    }
}