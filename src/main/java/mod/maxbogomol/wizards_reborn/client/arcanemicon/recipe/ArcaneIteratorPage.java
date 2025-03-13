package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneIteratorRecipe;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneWorkbenchRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class ArcaneIteratorPage extends RecipePage {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/arcane_iterator_page.png");
    public ItemStack result;
    public ItemStack[] inputs;
    public Enchantment enchantment;
    public ArcaneEnchantment arcaneEnchantment;
    public int experience = 0;
    public int health = 0;

    public ArcaneIteratorPage(ItemStack result, ItemStack... inputs) {
        super(BACKGROUND);
        this.result = result;
        this.inputs = inputs;
    }

    public ArcaneIteratorPage setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public ArcaneIteratorPage setHealth(int health) {
        this.health = health;
        return this;
    }

    public ArcaneIteratorPage setEnchantment(Enchantment enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    public ArcaneIteratorPage setArcaneEnchantment(ArcaneEnchantment arcaneEnchantment) {
        this.arcaneEnchantment = arcaneEnchantment;
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        Font font = Minecraft.getInstance().font;

        if (experience > 0) {
            gui.blit(BACKGROUND, x + 20, y + 98, 128 + 9, 0, 9, 9, 256, 256);
            String stringSize = String.valueOf(experience);
            drawText(book, gui, stringSize, x + 25 - (font.width(stringSize) / 2), y + 108);
        }

        if (health > 0) {
            gui.blit(BACKGROUND, x + 98, y + 98, 128, 0, 9, 9, 256, 256);
            String stringSize = String.valueOf(health);
            drawText(book, gui, stringSize, x + 103 - (font.width(stringSize) / 2), y + 108);
        }

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
        ItemStack stack = result.copy();
        if (enchantment != null) {
            stack.enchant(enchantment, 1);
        }
        if (arcaneEnchantment != null) {
            if (ArcaneEnchantmentUtil.canAddItemArcaneEnchantment(stack, arcaneEnchantment)) {
                ArcaneEnchantmentUtil.addItemArcaneEnchantment(stack, arcaneEnchantment);
            }
        }

        drawItem(book, gui, stack,x + 56, y + 128, mouseX, mouseY);

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
    public boolean isChanged(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            SimpleContainer inv = new SimpleContainer(inputs.length);
            for (int i = 0; i < inputs.length; i++) {
                inv.setItem(i, inputs[i]);
            }

            Optional<ArcaneIteratorRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.ARCANE_ITERATOR.get(), inv, level);
            return !recipe.isPresent();
        }

        return false;
    }
}