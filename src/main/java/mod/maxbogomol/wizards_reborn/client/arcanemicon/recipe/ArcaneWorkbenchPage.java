package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneWorkbenchRecipe;
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

public class ArcaneWorkbenchPage extends RecipePage {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/arcane_workbench_page.png");
    public ItemStack result;
    public ItemStack[] inputs;

    public ArcaneWorkbenchPage(ItemStack result, ItemStack... inputs) {
        super(BACKGROUND);
        this.result = result;
        this.inputs = inputs;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                int index = i * 3 + j;
                if (index < inputs.length && !inputs[index].isEmpty())
                    drawItem(book, gui, inputs[index], x + 38 + j * 18, y + 38 + i * 18, mouseX, mouseY);
            }
        }
        if (9 < inputs.length && !inputs[9].isEmpty()) {
            drawItem(book, gui, inputs[9], x + 56, y + 16, mouseX, mouseY);
        }
        if (10 < inputs.length && !inputs[10].isEmpty()) {
            drawItem(book, gui, inputs[10], x + 96, y + 56, mouseX, mouseY);
        }
        if (11 < inputs.length && !inputs[11].isEmpty()) {
            drawItem(book, gui, inputs[11], x + 56, y + 96, mouseX, mouseY);
        }
        if (12 < inputs.length && !inputs[12].isEmpty()) {
            drawItem(book, gui, inputs[12], x + 16, y + 56, mouseX, mouseY);
        }
        drawItem(book, gui, result,x + 56, y + 128, mouseX, mouseY);

        renderChanged(book, gui, x, y, mouseX, mouseY);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isChanged(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            SimpleContainer inv = new SimpleContainer(14);
            for (ItemStack o : inputs) {
                inv.addItem(o);
            }
            for (int i = 0; i < inputs.length; i++) {
                if (i < 14) inv.setItem(i, inputs[i]);
            }

            Optional<ArcaneWorkbenchRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.ARCANE_WORKBENCH.get(), inv, level);
            return !(recipe.isPresent() && recipe.get().getResultItem(RegistryAccess.EMPTY).getItem().equals(result.getItem()));
        }

        return false;
    }
}