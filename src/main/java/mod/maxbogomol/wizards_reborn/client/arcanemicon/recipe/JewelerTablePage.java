package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class JewelerTablePage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/jeweler_table_page.png");
    public ItemStack result;
    public ItemStack[] inputs;

    public JewelerTablePage(ItemStack result, ItemStack... inputs) {
        super(BACKGROUND);
        this.result = result;
        this.inputs = inputs;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawItem(book, gui, inputs[0],x + 56, y + 18, mouseX, mouseY);
        drawItem(book, gui, inputs[1],x + 56, y + 69, mouseX, mouseY);
        drawItem(book, gui, result,x + 56, y + 123, mouseX, mouseY);
    }
}