package mod.maxbogomol.wizards_reborn.common.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneWorkbenchRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import com.blamejared.crafttweaker.api.util.StringUtil;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

@Document("mods/WizardsReborn/ArcaneWorkbench")
@ZenRegister
@IRecipeHandler.For(ArcaneWorkbenchRecipe.class)
@ZenCodeType.Name("mods.wizards_reborn.ArcaneWorkbench")
public class ArcaneWorkbenchRecipeManager implements IRecipeManager, IRecipeHandler<ArcaneWorkbenchRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, int wissen, IIngredient... inputs) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new ArcaneWorkbenchRecipe(resourceLocation, output.getInternal(), wissen, Arrays.stream(inputs).map(IIngredient::asVanillaIngredient).toArray(Ingredient[]::new)), ""));
    }

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, int wissen, int saveNBT, IIngredient... inputs) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new ArcaneWorkbenchRecipe(resourceLocation, output.getInternal(), wissen, saveNBT, Arrays.stream(inputs).map(IIngredient::asVanillaIngredient).toArray(Ingredient[]::new)), ""));
    }

    @Override
    public RecipeType getRecipeType() {
        return WizardsReborn.ARCANE_WORKBENCH_RECIPE.get();
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, ArcaneWorkbenchRecipe recipe) {
        StringJoiner s = new StringJoiner(", ", manager.getCommandString() + ".addRecipe(", ");");

        s.add(StringUtil.quoteAndEscape(recipe.getId()));
        s.add(new MCItemStackMutable(recipe.getResultItem(RegistryAccess.EMPTY)).getCommandString());
        s.add(String.valueOf(recipe.getRecipeWissen()));
        recipe.getIngredients().stream()
                .map(IIngredient::fromIngredient)
                .map(IIngredient::getCommandString)
                .forEach(s::add);
        return s.toString();
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super ArcaneWorkbenchRecipe> manager, ArcaneWorkbenchRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super ArcaneWorkbenchRecipe> manager, ArcaneWorkbenchRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<ArcaneWorkbenchRecipe> recompose(IRecipeManager<? super ArcaneWorkbenchRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }
}