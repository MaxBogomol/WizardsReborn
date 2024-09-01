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

public class JewelerTableRecipe implements Recipe<Container> {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "jeweler_table");
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;
    private final int wissen;
    private final boolean isSaveNBT;

    public JewelerTableRecipe(ResourceLocation id, ItemStack output, int wissen, Ingredient... inputs) {
        this.id = id;
        this.output = output;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
        this.wissen = wissen;
        this.isSaveNBT = false;
    }

    public JewelerTableRecipe(ResourceLocation id, ItemStack output, int wissen, boolean isSaveNBT, Ingredient... inputs) {
        this.id = id;
        this.output = output;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
        this.wissen = wissen;
        this.isSaveNBT = isSaveNBT;
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return matches(inputs, inv);
    }

    public static boolean matches(List<Ingredient> inputs, Container inv) {
        boolean craft = true;
        for (int i = 0; i < 2; i += 1) {
            if (!inputs.get(i).test(inv.getItem(i))) {
                craft = false;
            }
        }

        return craft;
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

    public boolean getRecipeIsSaveNBT() {
        return isSaveNBT;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.JEWELER_TABLE.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WizardsRebornRecipes.JEWELER_TABLE_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<JewelerTableRecipe> {

        @Override
        public JewelerTableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            int wissen = GsonHelper.getAsInt(json, "wissen");
            JsonArray ingrs = GsonHelper.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : ingrs) {
                inputs.add(Ingredient.fromJson(e));
            }
            boolean isNBTCrystal = false;
            boolean isSaveNBT = false;

            if (json.has("saveNBT")) {
                isSaveNBT = GsonHelper.getAsBoolean(json, "saveNBT");
            }

            return new JewelerTableRecipe(recipeId, output, wissen, isSaveNBT, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public JewelerTableRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient[] inputs = new Ingredient[buffer.readInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(buffer);
            }
            ItemStack output = buffer.readItem();
            int wissen = buffer.readInt();
            boolean isSaveNBT = buffer.readBoolean();
            return new JewelerTableRecipe(recipeId, output, wissen, isSaveNBT, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, JewelerTableRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buffer.writeInt(recipe.getRecipeWissen());
            buffer.writeBoolean(recipe.getRecipeIsSaveNBT());
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
