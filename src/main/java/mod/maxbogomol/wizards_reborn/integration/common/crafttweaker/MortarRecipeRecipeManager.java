package mod.maxbogomol.wizards_reborn.integration.common.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mod.maxbogomol.wizards_reborn.common.recipe.MortarRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;
import java.util.StringJoiner;

@Document("mods/WizardsReborn/Mortar")
@ZenRegister
@IRecipeHandler.For(MortarRecipe.class)
@ZenCodeType.Name("mods.wizards_reborn.Mortar")
public class MortarRecipeRecipeManager implements IRecipeManager, IRecipeHandler<MortarRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient input) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new MortarRecipe(resourceLocation, input.asVanillaIngredient(), output.getImmutableInternal()), ""));
    }

    @Override
    public RecipeType getRecipeType() {
        return WizardsRebornRecipes.MORTAR.get();
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, MortarRecipe recipe) {
        StringJoiner s = new StringJoiner(", ", manager.getCommandString() + ".addRecipe(", ");");

        s.add(StringUtil.quoteAndEscape(recipe.getId()));
        s.add(IIngredient.fromIngredient(recipe.getIngredientRecipe()).getCommandString());
        s.add(new MCItemStackMutable(recipe.getResultItem(RegistryAccess.EMPTY)).getCommandString());
        return s.toString();
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super MortarRecipe> manager, MortarRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super MortarRecipe> manager, MortarRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<MortarRecipe> recompose(IRecipeManager<? super MortarRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }
}