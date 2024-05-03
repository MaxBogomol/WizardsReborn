package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThanksHeadPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/blank_page.png");
    public String text, name, date, say;
    public ResourceLocation head;

    public ThanksHeadPage(String textKey, ResourceLocation head) {
        super(BACKGROUND);
        this.text = textKey;
        this.name = textKey + ".name";
        this.date = textKey + ".date";
        this.say = textKey + ".say";
        this.head = head;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 4, 124);

        gui.blit(head, x + 4, y + 108, 0, 0, 24, 24, 32, 32);
        drawText(book, gui, "- " + I18n.get(name), x + 30, y + 116);
        drawText(book, gui, "      " + I18n.get(date), x + 30, y + 128);
        if (mouseX >= x + 4 && mouseY >= y + 108 && mouseX <= x + 4 + 24 && mouseY <= y + 108 + 24) {
            double ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.05f;
            int r = (int)(Math.sin(ticks) * 127 + 128);
            int g = (int)(Math.sin(ticks + Math.PI/2) * 127 + 128);
            int b = (int)(Math.sin(ticks + Math.PI) * 127 + 128);
            int packColor = ColorUtils.packColor(255, r, g, b);

            List<Component> list = new ArrayList<>();
            list.add(Component.translatable(name).withStyle(Style.EMPTY.withColor(packColor)));
            list.add(Component.empty());
            list.add(Component.translatable(say).withStyle(ChatFormatting.GRAY));

            gui.renderTooltip(Minecraft.getInstance().font, list, Optional.empty(), mouseX, mouseY);
        }
    }
}