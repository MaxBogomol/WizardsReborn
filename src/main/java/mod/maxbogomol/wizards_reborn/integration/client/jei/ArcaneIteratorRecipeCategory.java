package mod.maxbogomol.wizards_reborn.integration.client.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneIteratorRecipe;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

public class ArcaneIteratorRecipeCategory implements IRecipeCategory<ArcaneIteratorRecipe> {
    public static final RecipeType<ArcaneIteratorRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "arcane_iterator", ArcaneIteratorRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/arcane_iterator.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ArcaneIteratorRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 144, 88);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsRebornItems.ARCANE_ITERATOR.get()));
    }

    @NotNull
    @Override
    public RecipeType<ArcaneIteratorRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(WizardsRebornBlocks.ARCANE_ITERATOR.get().getName().getString());
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ArcaneIteratorRecipe recipe, @NotNull IFocusGroup focusGroup) {
        int size = recipe.getIngredients().size();
        ItemStack book = ItemStack.EMPTY;
        ItemStack bookResult = recipe.getResultItem(RegistryAccess.EMPTY).copy();

        if ((recipe.hasRecipeEnchantment() || recipe.hasRecipeArcaneEnchantment()) && recipe.getResultItem(RegistryAccess.EMPTY).isEmpty()) {
            size++;
            book = new ItemStack(Items.BOOK);
            bookResult = new ItemStack(Items.ENCHANTED_BOOK);

            if (recipe.hasRecipeArcaneEnchantment()) {
                book = new ItemStack(WizardsRebornItems.ARCANE_ENCHANTED_BOOK.get());
                bookResult = new ItemStack(WizardsRebornItems.ARCANE_ENCHANTED_BOOK.get());
            }
        }

        if (recipe.hasRecipeEnchantment()) {
            if (bookResult.getItem().equals(Items.ENCHANTED_BOOK)) {
                EnchantedBookItem.addEnchantment(bookResult, new EnchantmentInstance(recipe.getRecipeEnchantment(), 1));
            } else {
                bookResult.enchant(recipe.getRecipeEnchantment(), 1);
            }
        }
        if (recipe.hasRecipeArcaneEnchantment()) {
            ArcaneEnchantmentUtil.addItemArcaneEnchantment(bookResult, recipe.getRecipeArcaneEnchantment());
        }
        if (recipe.hasRecipeCrystalRitual()) {
            CrystalRitualUtil.setCrystalRitual(bookResult, recipe.getRecipeCrystalRitual());
        }

        int index = 1;
        double angleBetweenEach = 360.0 / (size - 1);
        Vec2 point = new Vec2(35, 0), center = new Vec2(35, 36);
        boolean first = false;

        for (int i = 0; i < size; i++)  {
            if (first) {
                if (book.isEmpty()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, (int) point.x, (int) point.y).addIngredients(recipe.getIngredients().get(i));
                } else {
                    builder.addSlot(RecipeIngredientRole.INPUT, (int) point.x, (int) point.y).addIngredients(recipe.getIngredients().get(i - 1));
                }
                index += 1;
                point = rotatePointAbout(point, center, angleBetweenEach);
            } else {
                if (book.isEmpty()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, 35, 36).addIngredients(recipe.getIngredients().get(i));
                } else {
                    builder.addSlot(RecipeIngredientRole.INPUT, 35, 36).addItemStack(book);
                }
                first = true;
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 121, 36).addItemStack(bookResult);
    }

    public static Vec2 rotatePointAbout(Vec2 in, Vec2 about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vec2((float) newX, (float) newY);
    }

    @Override
    public void draw(@NotNull ArcaneIteratorRecipe recipe, @NotNull IRecipeSlotsView view, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        String textWissen = Integer.toString(recipe.getRecipeWissen());
        int stringWidth = font.width(textWissen);

        if (WizardsRebornClientConfig.NUMERICAL_WISSEN.get()) {
            gui.drawString(font, textWissen, 141 - stringWidth + 1, 59, ArcanemiconScreen.TEXT_SHADOW_COLOR_INT, false);
            gui.drawString(font, textWissen, 141 - stringWidth, 58, ArcanemiconScreen.TEXT_COLOR_INT, false);
        }

        if (recipe.getRecipeExperience() > 0) {
            gui.blit(TEXTURE, 90, 5, 144 + 9, 0, 9, 9, 256, 256);
            String stringSize = String.valueOf(recipe.getRecipeExperience());
            gui.drawString(font, stringSize, 100, 5, 0xffffff);
        }

        if (recipe.getRecipeHealth() > 0) {
            gui.blit(TEXTURE, 90, 15, 144, 0, 9, 9, 256, 256);
            String stringSize = String.valueOf(recipe.getRecipeHealth());
            gui.drawString(font, stringSize, 100, 15, 0xffffff);
        }
    }
}
