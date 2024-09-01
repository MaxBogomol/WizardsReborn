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
import mod.maxbogomol.wizards_reborn.common.recipe.ArcanumDustTransmutationRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;
import java.util.StringJoiner;

@Document("mods/WizardsReborn/ArcanumDustTransmutation")
@ZenRegister
@IRecipeHandler.For(ArcanumDustTransmutationRecipe.class)
@ZenCodeType.Name("mods.wizards_reborn.ArcanumDustTransmutation")
public class ArcanumDustTransmutationRecipeManager implements IRecipeManager, IRecipeHandler<ArcanumDustTransmutationRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient input, @ZenCodeType.OptionalBoolean(true) boolean placeBlock, @ZenCodeType.Optional IItemStack display) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        ItemStack displayR = ItemStack.EMPTY;

        if (display != null) {
            displayR = display.getImmutableInternal();
        }

        CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new ArcanumDustTransmutationRecipe(resourceLocation, input.asVanillaIngredient(), output.getImmutableInternal(), displayR, placeBlock), ""));
    }

    @Override
    public RecipeType getRecipeType() {
        return WizardsRebornRecipes.ARCANUM_DUST_TRANSMUTATION.get();
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, ArcanumDustTransmutationRecipe recipe) {
        StringJoiner s = new StringJoiner(", ", manager.getCommandString() + ".addRecipe(", ");");

        s.add(StringUtil.quoteAndEscape(recipe.getId()));
        s.add(IIngredient.fromIngredient(recipe.getIngredientRecipe()).getCommandString());
        s.add(new MCItemStackMutable(recipe.getResultItem(RegistryAccess.EMPTY)).getCommandString());
        s.add(recipe.getDisplay() != ItemStack.EMPTY ? new MCItemStackMutable(recipe.getDisplay()).getCommandString() : null);
        s.add(String.valueOf(recipe.getPlaceBlock()));
        return s.toString();
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super ArcanumDustTransmutationRecipe> manager, ArcanumDustTransmutationRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super ArcanumDustTransmutationRecipe> manager, ArcanumDustTransmutationRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<ArcanumDustTransmutationRecipe> recompose(IRecipeManager<? super ArcanumDustTransmutationRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }
}