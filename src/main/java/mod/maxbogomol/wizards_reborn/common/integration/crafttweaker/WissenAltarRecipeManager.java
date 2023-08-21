package mod.maxbogomol.wizards_reborn.common.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

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
    public IRecipeType<WissenAltarRecipe> getRecipeType() {
        return WizardsReborn.WISSEN_ALTAR_RECIPE;
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, WissenAltarRecipe recipe) {
        StringJoiner s = new StringJoiner(", ", manager.getCommandString() + ".addRecipe(", ");");

        s.add(StringUtils.quoteAndEscape(recipe.getId()));
        s.add(IIngredient.fromIngredient(recipe.getIngredientRecipe()).getCommandString());
        s.add(String.valueOf(recipe.getRecipeWissen()));
        return s.toString();
    }
}