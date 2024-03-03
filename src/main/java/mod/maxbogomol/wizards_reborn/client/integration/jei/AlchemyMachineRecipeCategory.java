package mod.maxbogomol.wizards_reborn.client.integration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtils;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineRecipe;
import mod.maxbogomol.wizards_reborn.common.recipe.FluidIngredient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AlchemyMachineRecipeCategory implements IRecipeCategory<AlchemyMachineRecipe> {
    public static final RecipeType<AlchemyMachineRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "alchemy_machine", AlchemyMachineRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/alchemy_machine.png");

    private final IDrawable background;
    private final IDrawable icon;

    public AlchemyMachineRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 176, 112);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsReborn.ALCHEMY_MACHINE_ITEM.get()));
    }

    @NotNull
    @Override
    public RecipeType<AlchemyMachineRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(WizardsReborn.ALCHEMY_MACHINE.get().getName().getString());
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull AlchemyMachineRecipe recipe, @NotNull IFocusGroup focusGroup) {
        int x = 0;
        int y = 0;
        for (Ingredient o : recipe.getIngredients()) {
            ItemStack[] items = o.getItems();
            boolean setIngr = true;
            IRecipeSlotBuilder slot =  builder.addSlot(RecipeIngredientRole.INPUT, 15 + x, 30 + y);

            for (ItemStack stack : items) {
                if (!AlchemyPotionUtils.isEmpty(recipe.getRecipeAlchemyPotionIngredient())) {
                    if (stack.getItem() instanceof AlchemyPotionItem item) {
                        setIngr = false;
                        ItemStack bottle = stack.copy();
                        AlchemyPotionUtils.setPotion(bottle, recipe.getRecipeAlchemyPotionIngredient());
                        slot.addItemStack(bottle);
                    }
                }
            }

            if (setIngr) {
                slot.addIngredients(o);
            }
            x = x + 18;
            if (x >= 34) {
                y = y + 18;
                x = 0;
            }
        }

        y = 0;
        for (FluidIngredient o : recipe.getFluidIngredients()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 55, 30 + y)
                    .setFluidRenderer(o.getFluids().get(0).getAmount(), false, 16, 16)
                    .addIngredients(ForgeTypes.FLUID_STACK, o.getFluids());
            y = y + 18;
        }

        if (!recipe.getResultFluid().isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 144, 29)
                    .setFluidRenderer(recipe.getResultFluid().getAmount(), false, 16, 16)
                    .addIngredient(ForgeTypes.FLUID_STACK, recipe.getResultFluid());
        }

        if (!AlchemyPotionUtils.isEmpty(recipe.getRecipeAlchemyPotion())) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 144, 70).addItemStacks(getPotionItems(recipe.getRecipeAlchemyPotion()));
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 144, 70).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
        }
    }

    @Override
    public void draw(@NotNull AlchemyMachineRecipe recipe, @NotNull IRecipeSlotsView view, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        Font font_renderer = Minecraft.getInstance().font;
        if (recipe.getRecipeWissen() > 0) {
            String text_wissen = Integer.toString(recipe.getRecipeWissen());
            int stringWidth = font_renderer.width(text_wissen);

            gui.drawString(Minecraft.getInstance().font, text_wissen, 122 - (stringWidth / 2), 95, 0xffffff);
        } else {
            gui.blit(TEXTURE, 116, 68, 176, 8, 16, 16, 256, 256);
        }

        if (recipe.getRecipeSteam() > 0) {
            String text_steam = Integer.toString(recipe.getRecipeSteam());
            int stringWidth = font_renderer.width(text_steam);

            gui.drawString(Minecraft.getInstance().font, text_steam, 122 - (stringWidth / 2), 15, 0xffffff);
        } else {
            gui.blit(TEXTURE, 116, 28, 176, 8, 16, 16, 256, 256);
        }

        int y = 0;
        for (FluidIngredient o : recipe.getFluidIngredients()) {
            int width = 32;
            width /= (double) 5000 / (double) o.getFluids().get(0).getAmount();
            gui.blit(TEXTURE, 76, 34 + y, 176, 0, width, 8, 256, 256);

            y = y + 18;
        }

        for (int i = 0; i < 3; i ++) {
            if (i + 1 > recipe.getFluidIngredients().size()) {
                gui.blit(TEXTURE, 84, 30 + (i * 18), 176, 8, 16, 16, 256, 256);
            }
        }

        if (!recipe.getResultFluid().isEmpty()) {
            int width = 32;
            width /= (double) 5000 / (double) recipe.getResultFluid().getAmount();
            gui.blit(TEXTURE, 136, 52, 176, 0, width, 8, 256, 256);
        } else {
            gui.blit(TEXTURE, 144, 48, 176, 8, 16, 16, 256, 256);
        }
    }

    public static List<ItemStack> getPotionItems(AlchemyPotion potion) {
        List<ItemStack> items = new ArrayList<>();

        for (Item item : AlchemyPotionItem.potionList) {
            ItemStack itemStack = new ItemStack(item);
            AlchemyPotionUtils.setPotion(itemStack, potion);
            items.add(itemStack);
        }

        return items;
    }
}
