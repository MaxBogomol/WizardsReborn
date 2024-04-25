package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class WissenAltarPage extends RecipePage {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/wissen_altar_page.png");
    public ItemStack input;

    public WissenAltarPage(ItemStack input) {
        super(BACKGROUND);
        this.input = input;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawItem(book, gui, input,x + 56, y + 72, mouseX, mouseY);
        renderChanged(book, gui, x, y, mouseX, mouseY);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isChanged(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, input);

            Optional<WissenAltarRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.WISSEN_ALTAR_RECIPE.get(), inv, level);
            return !recipe.isPresent();
        }

        return false;
    }
}