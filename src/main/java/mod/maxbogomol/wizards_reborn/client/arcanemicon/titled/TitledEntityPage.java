package mod.maxbogomol.wizards_reborn.client.arcanemicon.titled;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class TitledEntityPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/entity_page.png");
    public String text, title;
    public Supplier<LivingEntity> entity;
    public LivingEntity cashedEntity;

    public TitledEntityPage(String textKey, Supplier<LivingEntity> entity) {
        super(BACKGROUND);
        this.text = textKey;
        this.title = textKey + ".title";
        this.entity = entity;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(BACKGROUND, x, y, 128, 0, 128, 20);

        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 24, 124);

        float ticks = ClientTickHandler.getTotal() * -0.15f;

        if (cashedEntity == null) cashedEntity = entity.get();
        InventoryScreen.renderEntityInInventoryFollowsAngle(gui, x + 64, y + 137, 11, ticks, -1f, cashedEntity);
    }
}