package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import com.mojang.math.Axis;
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

public class BunnyThanksPlushPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/thanks_plush_page.png");
    public String text, meow, name, say;
    public ItemStack plush;

    public BunnyThanksPlushPage(String textKey, ItemStack plush) {
        super(BACKGROUND);
        this.text = textKey;
        this.meow = textKey + ".meow";
        this.name = textKey + ".name";
        this.say = textKey + ".say";
        this.plush = plush;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        int height = Minecraft.getInstance().font.lineHeight;
        String title = I18n.get(this.meow + ".3");
        drawText(book, gui, title, x + 4, y + 4);
        title = I18n.get(this.meow + ".4");
        drawText(book, gui, title, x + 16, y + 4 + height + 1 + 10);
        drawText(book, gui, title, x + 12, y + 4 + ((height + 1 ) * 2) + 16);

        title = I18n.get(this.meow + ".5");
        gui.pose().pushPose();
        gui.pose().translate(x, y + 19, 0);
        gui.pose().mulPose(Axis.ZP.rotationDegrees(90f));
        drawText(book, gui, title, 0,  - height);
        gui.pose().popPose();

        title = I18n.get(this.meow + ".6");
        int titleWidth = Minecraft.getInstance().font.width(title);
        gui.pose().pushPose();
        gui.pose().translate(x + 64, y + 66, 0);
        gui.pose().mulPose(Axis.ZP.rotationDegrees(180f));
        drawText(book, gui, title, - titleWidth / 2 + 4, - height);
        gui.pose().popPose();

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