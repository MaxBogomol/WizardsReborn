package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class EntityPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/entity_page.png");
    public String text;
    public Supplier<LivingEntity> entity;
    public LivingEntity cashedEntity;

    public EntityPage(String textKey, Supplier<LivingEntity> entity) {
        super(BACKGROUND);
        this.text = textKey;
        this.entity = entity;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 4, 124);

        float ticks = ClientTickHandler.getTotal() * -0.15f;

        if (cashedEntity == null) cashedEntity = entity.get();
        InventoryScreen.renderEntityInInventoryFollowsAngle(gui, x + 64, y + 137, 11, ticks, -1f, cashedEntity);
    }
}