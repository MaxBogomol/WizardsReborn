package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.common.recipe.MortarRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
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

public class MortarPage extends RecipePage {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/mortar_page.png");
    public ItemStack result;
    public ItemStack input;

    public MortarPage(ItemStack result, ItemStack input) {
        super(BACKGROUND);
        this.result = result;
        this.input = input;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawItem(book, gui, new ItemStack(WizardsRebornItems.ARCANE_WOOD_MORTAR.get()),x + 56, y + 18, mouseX, mouseY);
        drawItem(book, gui, input,x + 56, y + 69, mouseX, mouseY);
        drawItem(book, gui, result,x + 56, y + 123, mouseX, mouseY);
        renderChanged(book, gui, x, y, mouseX, mouseY);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isChanged(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, input);

            Optional<MortarRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.MORTAR.get(), inv, level);
            return !(recipe.isPresent() && recipe.get().getResultItem(RegistryAccess.EMPTY).getItem().equals(result.getItem()));
        }

        return false;
    }
}