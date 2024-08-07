package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenCrystallizerRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class WissenCrystallizerPage extends RecipePage {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/wissen_crystallizer_page.png");
    public ItemStack result;
    public ItemStack[] inputs;

    public WissenCrystallizerPage(ItemStack result, ItemStack... inputs) {
        super(BACKGROUND);
        this.result = result;
        this.inputs = inputs;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        int index = 1;
        double angleBetweenEach = 360.0 / (inputs.length - 1);
        Vec2 point = new Vec2(56, 22), center = new Vec2(56, 57);
        boolean first = false;

        for (ItemStack o : inputs) {
            if (first) {
                drawItem(book, gui, o,(int) point.x + x, (int) point.y + y, mouseX, mouseY);
                index += 1;
                point = rotatePointAbout(point, center, angleBetweenEach);
            } else {
                drawItem(book, gui, o,x + 56, y + 56, mouseX, mouseY);
                first = true;
            }
        }
        drawItem(book, gui, result,x + 56, y + 128, mouseX, mouseY);

        renderChanged(book, gui, x, y, mouseX, mouseY);
    }

    public static Vec2 rotatePointAbout(Vec2 in, Vec2 about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vec2((float) newX, (float) newY);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isChanged(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            SimpleContainer inv = new SimpleContainer(inputs.length);
            for (int i = 0; i < inputs.length; i++) {
                inv.setItem(i, inputs[i]);
            }

            Optional<WissenCrystallizerRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.WISSEN_CRYSTALLIZER_RECIPE.get(), inv, level);
            return !(recipe.isPresent() && recipe.get().getResultItem(RegistryAccess.EMPTY).getItem().equals(result.getItem()));
        }

        return false;
    }
}