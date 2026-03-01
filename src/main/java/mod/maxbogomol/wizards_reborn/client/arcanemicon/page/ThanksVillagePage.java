package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThanksVillagePage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/thanks_village_page.png");
    public String text, name, welcome;

    public ThanksVillagePage(String textKey) {
        super(BACKGROUND);
        this.text = textKey;
        this.name = textKey + ".name";
        this.welcome = textKey + ".welcome";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 4, 124);

        String name = I18n.get(this.name);
        int nameWidth = Minecraft.getInstance().font.width(name);
        drawText(book, gui, I18n.get(name), x + 64 - nameWidth / 2, y + 136 - Minecraft.getInstance().font.lineHeight);

        if (mouseX >= x + 54 && mouseY >= y + 92 && mouseX < x + 70 && mouseY < y + 125) {
            float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.05f;
            int packColor = ColorUtil.packColor(ColorUtil.rainbowColor(ticks));

            List<Component> list = new ArrayList<>();
            list.add(Component.translatable(name).withStyle(Style.EMPTY.withColor(packColor)));
            list.add(Component.translatable(welcome).withStyle(ChatFormatting.GRAY));

            gui.renderTooltip(Minecraft.getInstance().font, list, Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconScreen gui, int x, int y, int mouseX, int mouseY) {
        if (mouseX >= x + 54 && mouseY >= y + 92 && mouseX < x + 70 && mouseY < y + 125) {
            linkTo("https://discord.gg/cKf55qNugw");
            return true;
        }
        return false;
    }

    public void linkTo(String url) {
        Minecraft.getInstance().setScreen(new ConfirmLinkScreen((click) -> {
            if (click) Util.getPlatform().openUri(url);
            Minecraft.getInstance().setScreen(new ArcanemiconScreen());
        }, url, true));
    }
}