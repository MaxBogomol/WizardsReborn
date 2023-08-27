package mod.maxbogomol.wizards_reborn.common.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;
import java.util.StringJoiner;

@Document("mods/WizardsReborn/WissenAltar")
@ZenRegister
@IRecipeHandler.For(WissenAltarRecipe.class)
@ZenCodeType.Name("mods.wizards_reborn.WissenAltar")
public class WissenAltarRecipeManager implements IRecipeManager, IRecipeHandler<WissenAltarRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient input, int wissen) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new WissenAltarRecipe(resourceLocation, input.asVanillaIngredient(), wissen), ""));
    }

    @Override
    public RecipeType getRecipeType() {
        return WizardsReborn.WISSEN_ALTAR_RECIPE.get();
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, WissenAltarRecipe recipe) {
        StringJoiner s = new StringJoiner(", ", manager.getCommandString() + ".addRecipe(", ");");

        s.add(StringUtil.quoteAndEscape(recipe.getId()));
        s.add(IIngredient.fromIngredient(recipe.getIngredientRecipe()).getCommandString());
        s.add(String.valueOf(recipe.getRecipeWissen()));
        return s.toString();
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super WissenAltarRecipe> manager, WissenAltarRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super WissenAltarRecipe> manager, WissenAltarRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<WissenAltarRecipe> recompose(IRecipeManager<? super WissenAltarRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }
}