package mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.RecipePage;
import mod.maxbogomol.wizards_reborn.common.recipe.MortarRecipe;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.WizardsRebornFarmersDelight;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class CuttingPage extends RecipePage {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/integration/farmers_delight/cutting_page.png");
    public ItemStack result;
    public ItemStack input;

    public CuttingPage(ItemStack result, ItemStack input) {
        super(BACKGROUND);
        this.result = result;
        this.input = input;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawItem(book, gui, new ItemStack(WizardsRebornFarmersDelight.ItemsLoadedOnly.ARCANE_GOLD_KNIFE.get()),x + 56, y + 18, mouseX, mouseY);
        drawItem(book, gui, input,x + 56, y + 69, mouseX, mouseY);
        drawItem(book, gui, result,x + 56, y + 123, mouseX, mouseY);
        renderChanged(book, gui, x, y, mouseX, mouseY);
    }
}