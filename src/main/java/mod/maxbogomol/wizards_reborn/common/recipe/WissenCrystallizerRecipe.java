package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class WissenCrystallizerRecipe implements Recipe<Container> {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "wissen_crystallizer");
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;
    private int wissen;

    public WissenCrystallizerRecipe(ResourceLocation id, ItemStack output, int wissen, Ingredient... inputs) {
        this.id = id;
        this.output = output;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
        this.wissen = wissen;
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return matches(inputs, inv);
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

        ItemStack stack = inv.getItem(0);
        if (stack.isEmpty()) {
            return false;
        }
        Ingredient ingr = inputs.get(0);
        if (!ingr.test(stack)) {
            return false;
        }

        return ingredientsMissing.isEmpty();
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess pRegistryAccess) {
        return output;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }

    public int getRecipeWissen() {
        return wissen;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WizardsReborn.WISSEN_CRYSTALLIZER_SERIALIZER.get();
    }

    public static class WissenCrystallizerRecipeType implements RecipeType<WissenCrystallizerRecipe> {
        @Override
        public String toString() {
            return WissenCrystallizerRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer implements RecipeSerializer<WissenCrystallizerRecipe> {

        @Override
        public WissenCrystallizerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "output")).getDefaultInstance();
            int wissen = GsonHelper.getAsInt(json, "wissen");
            JsonArray ingrs = GsonHelper.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : ingrs) {
                inputs.add(Ingredient.fromJson(e));
            }

            return new WissenCrystallizerRecipe(recipeId, output, wissen, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public WissenCrystallizerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient[] inputs = new Ingredient[buffer.readVarInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(buffer);
            }
            ItemStack output = buffer.readItem();
            int wissen = buffer.readInt();
            return new WissenCrystallizerRecipe(recipeId, output, wissen, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WissenCrystallizerRecipe recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buffer.writeVarInt(recipe.getRecipeWissen());
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

    @Override
    public boolean isSpecial(){
        return true;
    }

}
