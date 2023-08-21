package mod.maxbogomol.wizards_reborn.common.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneWorkbenchRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
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

    @Override
    public IRecipeType<ArcaneWorkbenchRecipe> getRecipeType() {
        return WizardsReborn.ARCANE_WORKBENCH_RECIPE;
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, ArcaneWorkbenchRecipe recipe) {
        StringJoiner s = new StringJoiner(", ", manager.getCommandString() + ".addRecipe(", ");");

        s.add(StringUtils.quoteAndEscape(recipe.getId()));
        s.add(new MCItemStackMutable(recipe.getRecipeOutput()).getCommandString());
        s.add(String.valueOf(recipe.getRecipeWissen()));
        recipe.getIngredients().stream()
                .map(IIngredient::fromIngredient)
                .map(IIngredient::getCommandString)
                .forEach(s::add);
        return s.toString();
    }
}