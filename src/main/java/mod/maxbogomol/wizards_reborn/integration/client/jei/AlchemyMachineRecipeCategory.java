package mod.maxbogomol.wizards_reborn.integration.client.jei;

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
import mod.maxbogomol.fluffy_fur.common.recipe.FluidIngredient;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineRecipe;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.util.NumericalUtil;
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
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsRebornItems.ALCHEMY_MACHINE.get()));
    }

    @NotNull
    @Override
    public RecipeType<AlchemyMachineRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(WizardsRebornBlocks.ALCHEMY_MACHINE.get().getName().getString());
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
        for (int i = 0; i < 6; i++) {
            if (i < recipe.getIngredients().size()) {
                Ingredient ingredient = recipe.getIngredients().get(i);
                ItemStack[] items = ingredient.getItems();
                boolean setIngr = true;
                IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, 15 + x, 30 + y);

                for (ItemStack stack : items) {
                    if (!AlchemyPotionUtil.isEmpty(recipe.getRecipeAlchemyPotionIngredient())) {
                        if (stack.getItem() instanceof AlchemyPotionItem item) {
                            setIngr = false;
                            ItemStack bottle = stack.copy();
                            AlchemyPotionUtil.setPotion(bottle, recipe.getRecipeAlchemyPotionIngredient());
                            slot.addItemStack(bottle);
                        }
                    }
                }

                if (setIngr) {
                    slot.addIngredients(ingredient);
                }
            } else {
                builder.addSlot(RecipeIngredientRole.INPUT, 15 + x, 30 + y);
            }

            x = x + 18;
            if (x >= 34) {
                y = y + 18;
                x = 0;
            }
        }

        y = 0;
        for (FluidIngredient o : recipe.getFluidIngredients()) {
            builder.addSlot(RecipeIngredientRole.CATALYST, 55, 30 + y)
                    .setFluidRenderer(o.getFluids().get(0).getAmount(), false, 16, 16)
                    .addIngredients(ForgeTypes.FLUID_STACK, o.getFluids());
            y = y + 18;
        }

        if (!recipe.getResultFluid().isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 144, 29)
                    .setFluidRenderer(recipe.getResultFluid().getAmount(), false, 16, 16)
                    .addIngredient(ForgeTypes.FLUID_STACK, recipe.getResultFluid());
        }

        if (!AlchemyPotionUtil.isEmpty(recipe.getRecipeAlchemyPotion())) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 144, 70).addItemStacks(getPotionItems(recipe.getRecipeAlchemyPotion()));
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 144, 70).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
        }
    }

    @Override
    public void draw(@NotNull AlchemyMachineRecipe recipe, @NotNull IRecipeSlotsView view, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        if (recipe.getRecipeWissen() > 0 && WizardsRebornClientConfig.NUMERICAL_WISSEN.get()) {
            String textWissen = Integer.toString(recipe.getRecipeWissen());
            int stringWidth = font.width(textWissen);

            gui.drawString(font, textWissen, 122 - (stringWidth / 2) + 1, 95, ArcanemiconScreen.TEXT_SHADOW_COLOR_INT, false);
            gui.drawString(font, textWissen, 122 - (stringWidth / 2), 95, ArcanemiconScreen.TEXT_COLOR_INT, false);
            if (mouseX >= 116 && mouseY >= 68 && mouseX <= 116 + 16 && mouseY <= 68 + 16) {
                gui.renderTooltip(font, NumericalUtil.getWissenName(), (int) mouseX, (int) mouseY);
            }
        } else {
            gui.blit(TEXTURE, 116, 68, 176, 8, 16, 16, 256, 256);
        }

        if (recipe.getRecipeSteam() > 0 && WizardsRebornClientConfig.NUMERICAL_STEAM.get()) {
            String textSteam = Integer.toString(recipe.getRecipeSteam());
            int stringWidth = font.width(textSteam);

            gui.drawString(font, textSteam, 122 - (stringWidth / 2) + 1, 15, ArcanemiconScreen.TEXT_SHADOW_COLOR_INT, false);
            gui.drawString(font, textSteam, 122 - (stringWidth / 2), 15, ArcanemiconScreen.TEXT_COLOR_INT, false);
            if (mouseX >= 116 && mouseY >= 28 && mouseX <= 116 + 16 && mouseY <= 28 + 16) {
                gui.renderTooltip(font, NumericalUtil.getSteamName(), (int) mouseX, (int) mouseY);
            }
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
            AlchemyPotionUtil.setPotion(itemStack, potion);
            items.add(itemStack);
        }

        return items;
    }
}
