package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WissenCrystallizerRecipe implements Recipe<Container> {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "wissen_crystallizer");
    private final ResourceLocation id;
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final int wissen;
    private boolean isNBTCrystal = false;
    private boolean isSaveNBT = false;

    public WissenCrystallizerRecipe(ResourceLocation id, ItemStack output, int wissen, Ingredient... inputs) {
        this.id = id;
        this.output = output;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
        this.wissen = wissen;
    }

    public WissenCrystallizerRecipe setIsNBTCrystal(boolean isNBTCrystal) {
        this.isNBTCrystal = isNBTCrystal;
        return this;
    }

    public WissenCrystallizerRecipe setIsSaveNBT(boolean isSaveNBT) {
        this.isSaveNBT = isSaveNBT;
        return this;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return matches(inputs, container);
    }

    public static boolean matches(List<Ingredient> inputs, Container container) {
        List<Ingredient> ingredientsMissing = new ArrayList<>(inputs);

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack input = container.getItem(i);
            if (input.isEmpty()) {
                break;
            }

            int stackIndex = -1;

            for (int j = 0; j < ingredientsMissing.size(); j++) {
                Ingredient ingredient = ingredientsMissing.get(j);
                if (ingredient.test(input)) {
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

        ItemStack stack = container.getItem(0);
        if (stack.isEmpty()) {
            return false;
        }
        Ingredient ingredient = inputs.get(0);
        if (!ingredient.test(stack)) {
            return false;
        }

        return ingredientsMissing.isEmpty();
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.WISSEN_CRYSTALLIZER.get());
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
        return WizardsRebornRecipes.WISSEN_CRYSTALLIZER_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public boolean isSpecial(){
        return true;
    }

    public static class Serializer implements RecipeSerializer<WissenCrystallizerRecipe> {

        @Override
        public WissenCrystallizerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            int wissen = GsonHelper.getAsInt(json, "wissen");
            JsonArray ingrs = GsonHelper.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : ingrs) {
                inputs.add(Ingredient.fromJson(e));
            }
            boolean isNBTCrystal = false;
            boolean isSaveNBT = false;

            if (json.has("NBTCrystal")) {
                isNBTCrystal = GsonHelper.getAsBoolean(json, "NBTCrystal");
            }
            if (json.has("saveNBT")) {
                isSaveNBT = GsonHelper.getAsBoolean(json, "saveNBT");
            }
            if (json.has("SkullOwner")) {
                CompoundTag tag = output.getOrCreateTag();
                tag.putString("SkullOwner", json.get("SkullOwner").getAsString());
            }

            return new WissenCrystallizerRecipe(recipeId, output, wissen, inputs.toArray(new Ingredient[0])).setIsNBTCrystal(isNBTCrystal).setIsSaveNBT(isSaveNBT);
        }

        @Nullable
        @Override
        public WissenCrystallizerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient[] inputs = new Ingredient[buffer.readInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(buffer);
            }
            ItemStack output = buffer.readItem();
            int wissen = buffer.readInt();
            boolean isNBTCrystal = buffer.readBoolean();
            boolean isSaveNBT = buffer.readBoolean();
            return new WissenCrystallizerRecipe(recipeId, output, wissen, inputs).setIsNBTCrystal(isNBTCrystal).setIsSaveNBT(isSaveNBT);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WissenCrystallizerRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buffer.writeInt(recipe.getWissen());
            buffer.writeBoolean(recipe.getIsNBTCrystal());
            buffer.writeBoolean(recipe.getIsSaveNBT());
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

    public int getWissen() {
        return wissen;
    }

    public boolean getIsNBTCrystal() {
        return isNBTCrystal;
    }

    public boolean getIsSaveNBT() {
        return isSaveNBT;
    }
}
