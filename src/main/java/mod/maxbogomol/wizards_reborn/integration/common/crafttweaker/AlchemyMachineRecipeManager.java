package mod.maxbogomol.wizards_reborn.integration.common.crafttweaker;

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
import mod.maxbogomol.fluffy_fur.common.recipe.FluidIngredient;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotions;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAlchemyPotions;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

@Document("mods/WizardsReborn/AlchemyMachine")
@ZenRegister
@IRecipeHandler.For(AlchemyMachineRecipe.class)
@ZenCodeType.Name("mods.wizards_reborn.AlchemyMachine")
public class AlchemyMachineRecipeManager implements IRecipeManager, IRecipeHandler<AlchemyMachineRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, @ZenCodeType.Nullable() IItemStack outputItem, @ZenCodeType.Nullable() IFluidStack outputFluid, @ZenCodeType.Nullable() String alchemyPotion, int wissen, int steam, @ZenCodeType.Nullable() IIngredient[] inputsItem,  @ZenCodeType.Nullable() IFluidStack[] inputsFluid, @ZenCodeType.Nullable() String outputAlchemyPotion) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ItemStack outputItemF = ItemStack.EMPTY;
        FluidStack outputFluidF = FluidStack.EMPTY;
        AlchemyPotion alchemyPotionF = WizardsRebornAlchemyPotions.EMPTY;
        AlchemyPotion outputAlchemyPotionF = WizardsRebornAlchemyPotions.EMPTY;
        NonNullList<Ingredient> inputs = NonNullList.create();
        NonNullList<FluidIngredient> fluidInputs = NonNullList.create();

        if (outputItem != null) outputItemF = outputItem.getInternal();
        if (outputFluid != null) outputFluidF = outputFluid.getInternal();
        if (alchemyPotion != null) alchemyPotionF = AlchemyPotions.getAlchemyPotion(alchemyPotion);
        if (outputAlchemyPotion != null) outputAlchemyPotionF = AlchemyPotions.getAlchemyPotion(outputAlchemyPotion);

        if (inputsItem != null) {
            Ingredient[] inputsArray =  Arrays.stream(inputsItem).map(IIngredient::asVanillaIngredient).toArray(Ingredient[]::new);
            inputs.addAll(Arrays.asList(inputsArray));
        }

        if (inputsFluid != null) {
            CTFluidIngredient[] inputsArray =  Arrays.stream(inputsFluid).map(IFluidStack::asFluidIngredient).toArray(CTFluidIngredient[]::new);

            for (CTFluidIngredient fluid : inputsArray) {
                for (IFluidStack fluidStack : fluid.getMatchingStacks()) {
                    fluidInputs.add(FluidIngredient.of(fluidStack.getFluid(), (int) fluidStack.getAmount()));
                }
            }
        }

            CraftTweakerAPI.apply(new ActionAddRecipe(this,
                new AlchemyMachineRecipe(resourceLocation, outputItemF, outputFluidF, alchemyPotionF, wissen, steam, inputs, fluidInputs, outputAlchemyPotionF), ""));
    }

    @ZenCodeType.Method
    public void remove(IFluidStack input) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, iRecipe -> ((AlchemyMachineRecipe) iRecipe).getResultFluid().isFluidEqual((FluidStack) input.getInternal())));
    }

    @Override
    public RecipeType getRecipeType() {
        return WizardsRebornRecipes.ALCHEMY_MACHINE.get();
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, AlchemyMachineRecipe recipe) {
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
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super AlchemyMachineRecipe> manager, AlchemyMachineRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super AlchemyMachineRecipe> manager, AlchemyMachineRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<AlchemyMachineRecipe> recompose(IRecipeManager<? super AlchemyMachineRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }
}