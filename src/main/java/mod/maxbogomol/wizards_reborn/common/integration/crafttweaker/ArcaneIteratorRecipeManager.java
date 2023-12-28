package mod.maxbogomol.wizards_reborn.common.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineRecipe;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneIteratorRecipe;
import mod.maxbogomol.wizards_reborn.common.recipe.FluidIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

@Document("mods/WizardsReborn/ArcaneIterator")
@ZenRegister
@IRecipeHandler.For(ArcaneIteratorRecipe.class)
@ZenCodeType.Name("mods.wizards_reborn.ArcaneIterator")
public class ArcaneIteratorRecipeManager implements IRecipeManager, IRecipeHandler<ArcaneIteratorRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, @ZenCodeType.Nullable() IItemStack output, @ZenCodeType.Nullable() Enchantment enchantment, @ZenCodeType.Nullable() String arcaneEnchantment, int wissen, int health, int experience, boolean isSaveNBT, IIngredient[] inputs) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        ItemStack outputItemF = ItemStack.EMPTY;
        Enchantment enchantmentF = null;
        ArcaneEnchantment arcaneEnchantmentF = null;

        NonNullList<Ingredient> inputsF = NonNullList.create();
        Ingredient[] inputsArray = Arrays.stream(inputs).map(IIngredient::asVanillaIngredient).toArray(Ingredient[]::new);
        inputsF.addAll(Arrays.asList(inputsArray));

        if (enchantment != null) enchantmentF = enchantment;
        if (arcaneEnchantment != null) arcaneEnchantmentF = ArcaneEnchantments.getArcaneEnchantment(arcaneEnchantment);
        if (output != null) outputItemF = output.getInternal();

            CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new ArcaneIteratorRecipe(resourceLocation, outputItemF, enchantmentF, arcaneEnchantmentF, wissen, health, experience, isSaveNBT, inputsF), ""));
    }

    @ZenCodeType.Method
    public void remove(Enchantment enchantment) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, iRecipe -> ((ArcaneIteratorRecipe) iRecipe).getRecipeEnchantment() != null && ((ArcaneIteratorRecipe) iRecipe).getRecipeEnchantment().equals(enchantment)));
    }

    @ZenCodeType.Method
    public void remove(String arcaneEnchantment) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, iRecipe -> ((ArcaneIteratorRecipe) iRecipe).getRecipeArcaneEnchantment() != null && ((ArcaneIteratorRecipe) iRecipe).getRecipeArcaneEnchantment().equals(ArcaneEnchantments.getArcaneEnchantment(arcaneEnchantment))));
    }

    @Override
    public RecipeType getRecipeType() {
        return WizardsReborn.ARCANE_ITERATOR_RECIPE.get();
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, ArcaneIteratorRecipe recipe) {
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
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super ArcaneIteratorRecipe> manager, ArcaneIteratorRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super ArcaneIteratorRecipe> manager, ArcaneIteratorRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<ArcaneIteratorRecipe> recompose(IRecipeManager<? super ArcaneIteratorRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }
}