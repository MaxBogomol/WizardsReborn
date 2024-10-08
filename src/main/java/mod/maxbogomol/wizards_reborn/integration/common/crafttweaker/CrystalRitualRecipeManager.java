package mod.maxbogomol.wizards_reborn.integration.common.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRituals;
import mod.maxbogomol.wizards_reborn.common.recipe.CrystalRitualRecipe;
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

@Document("mods/WizardsReborn/CrystalRitual")
@ZenRegister
@IRecipeHandler.For(CrystalRitualRecipe.class)
@ZenCodeType.Name("mods.wizards_reborn.CrystalRitual")
public class CrystalRitualRecipeManager implements IRecipeManager, IRecipeHandler<CrystalRitualRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, String crystalRitual, IIngredient... inputs) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CrystalRitual crystalRitualF = CrystalRituals.getCrystalRitual(crystalRitual);

        CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new CrystalRitualRecipe(resourceLocation, crystalRitualF, Arrays.stream(inputs).map(IIngredient::asVanillaIngredient).toArray(Ingredient[]::new)), ""));
    }

    @ZenCodeType.Method
    public void removeRitual(String crystalRitual) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, iRecipe -> ((CrystalRitualRecipe) iRecipe).getRecipeRitual().equals(CrystalRituals.getCrystalRitual(crystalRitual))));
    }

    @Override
    public RecipeType getRecipeType() {
        return WizardsRebornRecipes.CRYSTAL_RITUAL.get();
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, CrystalRitualRecipe recipe) {
        StringJoiner s = new StringJoiner(", ", manager.getCommandString() + ".addRecipe(", ");");

        s.add(StringUtil.quoteAndEscape(recipe.getId()));
        s.add(new MCItemStackMutable(recipe.getResultItem(RegistryAccess.EMPTY)).getCommandString());
        recipe.getIngredients().stream()
                .map(IIngredient::fromIngredient)
                .map(IIngredient::getCommandString)
                .forEach(s::add);
        return s.toString();
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super CrystalRitualRecipe> manager, CrystalRitualRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super CrystalRitualRecipe> manager, CrystalRitualRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<CrystalRitualRecipe> recompose(IRecipeManager<? super CrystalRitualRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }
}