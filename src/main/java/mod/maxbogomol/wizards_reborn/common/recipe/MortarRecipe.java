package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonObject;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class MortarRecipe implements Recipe<Container>  {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "mortar");
    private final ResourceLocation id;
    private final Ingredient recipeItem;
    private final ItemStack output;

    public MortarRecipe(ResourceLocation id, Ingredient recipeItem, ItemStack output) {
        this.id = id;
        this.recipeItem = recipeItem;
        this.output = output;
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return recipeItem.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    public Ingredient getIngredientRecipe() {
        return recipeItem;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.ARCANE_WOOD_MORTAR.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WizardsRebornRecipes.MORTAR_SERIALIZER.get();
    }

    public static class MortarRecipeType implements RecipeType<MortarRecipe> {
        @Override
        public String toString() {
            return MortarRecipe.TYPE_ID.toString();
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

    public static class Serializer implements RecipeSerializer<MortarRecipe> {

        @Override
        public MortarRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "from"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "to"));

            return new MortarRecipe(recipeId, input, output);
        }

        @Nullable
        @Override
        public MortarRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();

            return new MortarRecipe(recipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MortarRecipe recipe) {
            recipe.getIngredientRecipe().toNetwork(buffer);
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }
}