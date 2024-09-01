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
import mod.maxbogomol.wizards_reborn.common.recipe.CrystalInfusionRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

@Document("mods/WizardsReborn/CrystalInfusion")
@ZenRegister
@IRecipeHandler.For(CrystalInfusionRecipe.class)
@ZenCodeType.Name("mods.wizards_reborn.CrystalInfusion")
public class CrystalInfusionRecipeManager implements IRecipeManager, IRecipeHandler<CrystalInfusionRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, int light, IIngredient... inputs) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new CrystalInfusionRecipe(resourceLocation, output.getInternal(), light, Arrays.stream(inputs).map(IIngredient::asVanillaIngredient).toArray(Ingredient[]::new)), ""));
    }

    @Override
    public RecipeType getRecipeType() {
        return WizardsRebornRecipes.CRYSTAL_INFUSION.get();
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, CrystalInfusionRecipe recipe) {
        StringJoiner s = new StringJoiner(", ", manager.getCommandString() + ".addRecipe(", ");");

        s.add(StringUtil.quoteAndEscape(recipe.getId()));
        s.add(new MCItemStackMutable(recipe.getResultItem(RegistryAccess.EMPTY)).getCommandString());
        s.add(String.valueOf(recipe.getRecipeLight()));
        recipe.getIngredients().stream()
                .map(IIngredient::fromIngredient)
                .map(IIngredient::getCommandString)
                .forEach(s::add);
        return s.toString();
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super CrystalInfusionRecipe> manager, CrystalInfusionRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super CrystalInfusionRecipe> manager, CrystalInfusionRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<CrystalInfusionRecipe> recompose(IRecipeManager<? super CrystalInfusionRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }
}