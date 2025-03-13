package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.maxbogomol.fluffy_fur.common.recipe.FluidIngredient;
import mod.maxbogomol.fluffy_fur.util.RecipeUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAlchemyPotions;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
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
    private NonNullList<Ingredient> inputs = NonNullList.create();
    private NonNullList<FluidIngredient> fluidInputs = NonNullList.create();
    private ItemStack output = ItemStack.EMPTY;
    private FluidStack fluidOutput = FluidStack.EMPTY;
    private int wissen = 0;
    private int steam = 0;
    private AlchemyPotion alchemyPotion = null;
    private AlchemyPotion alchemyPotionIngredient = null;

    public AlchemyMachineRecipe(ResourceLocation id) {
        this.id = id;
    }

    public AlchemyMachineRecipe setOutput(ItemStack output) {
        this.output = output;
        return this;
    }

    public AlchemyMachineRecipe setFluidOutput(FluidStack fluidOutput) {
        this.fluidOutput = fluidOutput;
        return this;
    }

    public AlchemyMachineRecipe setInputs(NonNullList<Ingredient> inputs) {
        this.inputs = inputs;
        return this;
    }

    public AlchemyMachineRecipe setFluidInputs(NonNullList<FluidIngredient> fluidInputs) {
        this.fluidInputs = fluidInputs;
        return this;
    }

    public AlchemyMachineRecipe setWissen(int wissen) {
        this.wissen = wissen;
        return this;
    }

    public AlchemyMachineRecipe setSteam(int steam) {
        this.steam = steam;
        return this;
    }

    public AlchemyMachineRecipe setAlchemyPotion(AlchemyPotion alchemyPotion) {
        this.alchemyPotion = alchemyPotion;
        return this;
    }

    public AlchemyMachineRecipe setAlchemyPotionIngredient(AlchemyPotion alchemyPotionIngredient) {
        this.alchemyPotionIngredient = alchemyPotionIngredient;
        return this;
    }

    @Override
    public boolean matches(AlchemyMachineContext context, Level level) {
        if (matches(inputs, context.container)) {
            if (!fluidInputs.isEmpty()) {
                return fluidMatches(context, level);
            } else {
                return true;
            }
        }
        return false;
    }

    public static boolean matches(List<Ingredient> inputs, Container container) {
        List<Ingredient> ingredientsMissing = new ArrayList<>(inputs);
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ItemStack input = container.getItem(i);
            if (!input.isEmpty()) {
                items.add(input);
            }
        }

        for (ItemStack input : items) {
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

    public boolean fluidMatches(AlchemyMachineContext context, Level level) {
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
    public ItemStack assemble(AlchemyMachineContext context, RegistryAccess registryAccess) {
        return output;
    }


    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.ALCHEMY_MACHINE.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeType<?> getType(){
        return BuiltInRegistries.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WizardsRebornRecipes.ALCHEMY_MACHINE_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public boolean isSpecial(){
        return true;
    }

    public static class Serializer implements RecipeSerializer<AlchemyMachineRecipe> {

        @Override
        public AlchemyMachineRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack outputItem = ItemStack.EMPTY;
            FluidStack outputFluid = FluidStack.EMPTY;
            AlchemyPotion alchemyPotion = WizardsRebornAlchemyPotions.EMPTY;
            AlchemyPotion alchemyPotionIngredient = WizardsRebornAlchemyPotions.EMPTY;
            int wissen = 0;
            int steam = 0;

            if (json.has("outputItem")) {
                outputItem = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "outputItem"));
            }
            if (json.has("outputFluid")) {
                outputFluid = RecipeUtil.deserializeFluidStack(GsonHelper.getAsJsonObject(json, "outputFluid"));
            }
            if (json.has("alchemyPotion")) {
                alchemyPotion = AlchemyPotionUtil.deserializeAlchemyPotion(GsonHelper.getAsJsonObject(json, "alchemyPotion"));
            }

            if (json.has("alchemyPotionIngredient")) {
                alchemyPotionIngredient = AlchemyPotionUtil.deserializeAlchemyPotion(GsonHelper.getAsJsonObject(json, "alchemyPotionIngredient"));
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

            return new AlchemyMachineRecipe(recipeId).setOutput(outputItem).setFluidOutput(outputFluid).setInputs(inputs).setFluidInputs(fluidInputs).setWissen(wissen).setSteam(steam).setAlchemyPotion(alchemyPotion).setAlchemyPotionIngredient(alchemyPotionIngredient);
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
            AlchemyPotion alchemyPotionIngredient = AlchemyPotionUtil.alchemyPotionFromNetwork(buffer);
            ItemStack outputItem = buffer.readItem();
            FluidStack outputFluid = buffer.readFluidStack();
            AlchemyPotion alchemyPotion = AlchemyPotionUtil.alchemyPotionFromNetwork(buffer);
            int wissen = buffer.readInt();
            int steam = buffer.readInt();
            return new AlchemyMachineRecipe(recipeId).setOutput(outputItem).setFluidOutput(outputFluid).setInputs(inputs).setFluidInputs(fluidInputs).setWissen(wissen).setSteam(steam).setAlchemyPotion(alchemyPotion).setAlchemyPotionIngredient(alchemyPotionIngredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlchemyMachineRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(buffer);
            }
            buffer.writeInt(recipe.getFluidInputs().size());
            for (FluidIngredient input : recipe.getFluidInputs()) {
                input.write(buffer);
            }
            AlchemyPotionUtil.alchemyPotionToNetwork(recipe.getAlchemyPotion(), buffer);
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buffer.writeFluidStack(recipe.getFluidResult());
            AlchemyPotionUtil.alchemyPotionToNetwork(recipe.getAlchemyPotion(), buffer);
            buffer.writeInt(recipe.getWissen());
            buffer.writeInt(recipe.getSteam());
        }
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output;
    }

    public NonNullList<FluidIngredient> getFluidInputs() {
        return fluidInputs;
    }

    public FluidStack getFluidResult() {
        return fluidOutput;
    }

    public AlchemyPotion getAlchemyPotion() {
        return alchemyPotion;
    }

    public AlchemyPotion getAlchemyPotionIngredient() {
        return alchemyPotionIngredient;
    }

    public int getWissen() {
        return wissen;
    }

    public int getSteam() {
        return steam;
    }
}
