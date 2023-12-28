package mod.maxbogomol.wizards_reborn.common.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByOutput;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.recipe.CenserRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Document("mods/WizardsReborn/Censer")
@ZenRegister
@IRecipeHandler.For(CenserRecipe.class)
@ZenCodeType.Name("mods.wizards_reborn.Censer")
public class CenserRecipeManager implements IRecipeManager, IRecipeHandler<CenserRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient input, MobEffect[] mobEffects, int[] duration, int[] amplifier) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        int i = 0;
        List<MobEffectInstance> effects = new ArrayList<>();
        for (MobEffect effect : mobEffects) {
            effects.add(new MobEffectInstance(effect, duration[i], amplifier[i]));
            i++;
        }

        CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new CenserRecipe(resourceLocation, input.asVanillaIngredient(), effects), ""));
    }

    @ZenCodeType.Method
    public void remove(IItemStack input) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, iRecipe -> ((CenserRecipe) iRecipe).getIngredientRecipe().test(input.getInternal())));
    }

    @Override
    public RecipeType getRecipeType() {
        return WizardsReborn.CENSER_RECIPE.get();
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, CenserRecipe recipe) {
        StringJoiner s = new StringJoiner(", ", manager.getCommandString() + ".addRecipe(", ");");

        s.add(StringUtil.quoteAndEscape(recipe.getId()));
        s.add(IIngredient.fromIngredient(recipe.getIngredientRecipe()).getCommandString());
        s.add(new MCItemStackMutable(recipe.getResultItem(RegistryAccess.EMPTY)).getCommandString());
        return s.toString();
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super CenserRecipe> manager, CenserRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super CenserRecipe> manager, CenserRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<CenserRecipe> recompose(IRecipeManager<? super CenserRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }
}