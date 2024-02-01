package mod.maxbogomol.wizards_reborn.client.gui.components;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WizardsRebornMenuButton extends Button {
    public static boolean on = false;
    public static ItemStack icon;

    public WizardsRebornMenuButton(int x, int y) {
        super(x, y, 20, 20, Component.empty(), WizardsRebornMenuButton::click, DEFAULT_NARRATION);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        if (icon == null) icon = new ItemStack(WizardsReborn.ARCANUM.get());
        guiGraphics.renderItem(icon, this.getX() + 2, this.getY() + 2);
    }

    public static void click(Button b) {
        if (Minecraft.getInstance().screen instanceof TitleScreen titleScreen) {
            if (!on) {
                ResourceLocation base = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/title/background/panorama");
                TitleScreen.CUBE_MAP = new CubeMap(base);
                titleScreen.panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
                titleScreen.logoRenderer = new CustomLogoRenderer(false);
            } else {
                ResourceLocation base = new ResourceLocation("textures/gui/title/background/panorama");
                TitleScreen.CUBE_MAP = new CubeMap(base);
                titleScreen.panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
                titleScreen.logoRenderer = new LogoRenderer(false);
            }
            on = !on;
        }
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public static class SingleMenuRow {
        public final String left, right;
        public SingleMenuRow(String left, String right) {
            this.left = I18n.get(left);
            this.right = I18n.get(right);
        }
        public SingleMenuRow(String center) {
            this(center, center);
        }
    }

    public static class MenuRows {
        public static final MenuRows MAIN_MENU = new MenuRows(Arrays.asList(
                new SingleMenuRow("menu.singleplayer"),
                new SingleMenuRow("menu.multiplayer"),
                new SingleMenuRow("fml.menu.mods", "menu.online"),
                new SingleMenuRow("narrator.button.language", "narrator.button.accessibility")
        ));

        protected final List<String> leftButtons, rightButtons;

        public MenuRows(List<SingleMenuRow> variants) {
            leftButtons = variants.stream().map(r -> r.left).collect(Collectors.toList());
            rightButtons = variants.stream().map(r -> r.right).collect(Collectors.toList());
        }
    }

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class OpenConfigButtonHandler {
        @SubscribeEvent
        public static void onGuiInit(ScreenEvent.Init event) {
            Screen gui = event.getScreen();

            MenuRows menu = null;
            int rowIdx = 0, offsetX = 0;
            if (gui instanceof TitleScreen) {
                menu = MenuRows.MAIN_MENU;
                rowIdx = 3;
                offsetX = 4;
            }

            if (rowIdx != 0 && menu != null) {
                boolean onLeft = offsetX < 0;
                String target = (onLeft ? menu.leftButtons : menu.rightButtons).get(rowIdx - 1);

                int offsetX_ = offsetX;
                MutableObject<GuiEventListener> toAdd = new MutableObject<>(null);
                event.getListenersList()
                        .stream()
                        .filter(w -> w instanceof AbstractWidget)
                        .map(w -> (AbstractWidget) w)
                        .filter(w -> w.getMessage()
                                .getString()
                                .equals(target))
                        .findFirst()
                        .ifPresent(w -> toAdd
                                .setValue(new WizardsRebornMenuButton(w.getX() + offsetX_ + (onLeft ? -20 : w.getWidth()), w.getY())));
                if (toAdd.getValue() != null)
                    event.addListener(toAdd.getValue());
            }
        }

    }
}
