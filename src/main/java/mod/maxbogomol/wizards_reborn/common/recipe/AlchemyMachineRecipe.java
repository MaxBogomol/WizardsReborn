package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.utils.RecipeUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AlchemyMachineRecipe implements Recipe<AlchemyMachineContext> {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "alchemy_machine");
    private final ResourceLocation id;
    private final ItemStack output;
    private final FluidStack fluidOutput;
    private final NonNullList<Ingredient> inputs;
    private final NonNullList<FluidIngredient> fluidInputs;
    private final int wissen;
    private final int steam;

    public AlchemyMachineRecipe(ResourceLocation id, ItemStack output, FluidStack fluidOutput, int wissen, int steam, NonNullList<Ingredient> inputs, NonNullList<FluidIngredient> fluidInputs) {
        this.id = id;
        this.output = output;
        this.fluidOutput = fluidOutput;
        this.inputs = inputs;
        this.fluidInputs = fluidInputs;
        this.wissen = wissen;
        this.steam = steam;
    }

    @Override
    public boolean matches(AlchemyMachineContext inv, Level worldIn) {
        if (matches(inputs, inv.container)) {
            if (fluidInputs.size() > 0) {
                return fluidMatches(inv, worldIn);
            } else {
                return true;
            }
        }
        return false;
    }

    public static boolean matches(List<Ingredient> inputs, Container inv) {
        List<Ingredient> ingredientsMissing = new ArrayList<>(inputs);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack input = inv.getItem(i);
            if (input.isEmpty()) {
                break;
            }

            int stackIndex = -1;

            for (int j = 0; j < ingredientsMissing.size(); j++) {
                Ingredient ingr = ingredientsMissing.get(j);
                if (ingr.test(input)) {
                    stackIndex = j;
                    break;
                }
            }

            if (stackIndex != -1) {
                ingredientsMissing.remove(stackIndex);
            } else {
                return false;
            }
        }

        return ingredientsMissing.isEmpty();
    }

    public boolean fluidMatches(AlchemyMachineContext context, Level pLevel) {
        HashSet<FluidIngredient> remaining = new HashSet<>();
        remaining.addAll(fluidInputs);

        for (IFluidHandler handler : context.fluids) {
            boolean matched = false;
            for (FluidIngredient fluid : remaining) {
                for (FluidStack stack : fluid.getAllFluids()) {
                    if (fluid.test(handler.drain(stack, IFluidHandler.FluidAction.SIMULATE))) {
                        remaining.remove(fluid);
                        matched = true;
                        break;
                    }
                }
                if (matched)
                    break;
            }
            if (!matched && !handler.drain(1, IFluidHandler.FluidAction.SIMULATE).isEmpty())
                return false;
        }
        return remaining.isEmpty();
    }

    @Override
    public ItemStack assemble(AlchemyMachineContext inv, RegistryAccess pRegistryAccess) {
        return output;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }

    public NonNullList<FluidIngredient> getFluidIngredients() {
        return fluidInputs;
    }

    public int getRecipeWissen() {
        return wissen;
    }

    public int getRecipeSteam() {
        return steam;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsReborn.ALCHEMY_MACHINE_ITEM.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WizardsReborn.ALCHEMY_MACHINE_SERIALIZER.get();
    }

    public static class AlchemyMachineRecipeType implements RecipeType<AlchemyMachineRecipe> {
        @Override
        public String toString() {
            return AlchemyMachineRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer implements RecipeSerializer<AlchemyMachineRecipe> {

        @Override
        public AlchemyMachineRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack outputItem = ItemStack.EMPTY;
            FluidStack outputFluid = FluidStack.EMPTY;
            int wissen = 0;
            int steam = 0;

            if (json.has("outputItem")) {
                outputItem = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "outputItem"));
            }
            if (json.has("outputFluid")) {
                outputFluid = RecipeUtils.deserializeFluidStack(GsonHelper.getAsJsonObject(json, "outputFluid"));
            }

            if (json.has("wissen")) {
                wissen = GsonHelper.getAsInt(json, "wissen");
            }

            if (json.has("steam")) {
                steam = GsonHelper.getAsInt(json, "steam");
            }

            NonNullList<Ingredient> inputs = NonNullList.create();
            NonNullList<FluidIngredient> fluidInputs = NonNullList.create();

            if (json.has("ingredients")) {
                JsonArray ingrs = GsonHelper.getAsJsonArray(json, "ingredients");
                for (JsonElement e : ingrs) {
                    inputs.add(Ingredient.fromJson(e));
                }
            }

            if (json.has("fluidIngredients")) {
                JsonArray fluids = GsonHelper.getAsJsonArray(json, "fluidIngredients", null);
                for (JsonElement e : fluids) {
                    fluidInputs.add(FluidIngredient.deserialize(e, "fluid"));
                }
            }

            return new AlchemyMachineRecipe(recipeId, outputItem, outputFluid, wissen, steam, inputs, fluidInputs);
        }

        @Nullable
        @Override
        public AlchemyMachineRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.create();
            int inputsSize = buffer.readInt();
            for (int i = 0; i < inputsSize; i++) {
                inputs.add(Ingredient.fromNetwork(buffer));
            }
            NonNullList<FluidIngredient> fluidInputs = NonNullList.create();
            int fluidInputsSize = buffer.readInt();
            for (int i = 0; i < fluidInputsSize; i++) {
                fluidInputs.add(FluidIngredient.read(buffer));
            }
            ItemStack outputItem = buffer.readItem();
            FluidStack outputFluid = buffer.readFluidStack();
            int wissen = buffer.readInt();
            int steam = buffer.readInt();
            return new AlchemyMachineRecipe(recipeId, outputItem, outputFluid, wissen, steam, inputs, fluidInputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlchemyMachineRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(buffer);
            }
            buffer.writeInt(recipe.getFluidIngredients().size());
            for (FluidIngredient input : recipe.getFluidIngredients()) {
                input.write(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buffer.writeFluidStack(recipe.getResultFluid());
            buffer.writeInt(recipe.getRecipeWissen());
            buffer.writeInt(recipe.getRecipeSteam());
        }
    }

    @Override
    public RecipeType<?> getType(){
        return BuiltInRegistries.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output;
    }

    public FluidStack getResultFluid() {
        return fluidOutput;
    }

    @Override
    public boolean isSpecial(){
        return true;
    }
}
