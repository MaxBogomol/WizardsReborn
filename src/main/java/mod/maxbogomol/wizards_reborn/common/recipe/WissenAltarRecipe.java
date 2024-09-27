package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonObject;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class WissenAltarRecipe implements Recipe<Container> {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "wissen_altar");
    private final ResourceLocation id;
    private final Ingredient recipeItem;
    private final int wissen;

    public WissenAltarRecipe(ResourceLocation id, Ingredient recipeItem, int wissen) {
        this.id = id;
        this.recipeItem = recipeItem;
        this.wissen = wissen;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return recipeItem.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    public Ingredient getIngredientRecipe() {
        return recipeItem;
    }

    public int getRecipeWissen() {
        return wissen;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WizardsRebornRecipes.WISSEN_ALTAR_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<WissenAltarRecipe> {

        @Override
        public WissenAltarRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            int wissen = GsonHelper.getAsInt(json, "wissen");

            return new WissenAltarRecipe(recipeId, input, wissen);
        }

        @Nullable
        @Override
        public WissenAltarRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            int wissen = buffer.readInt();
            return new WissenAltarRecipe(recipeId, input, wissen);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WissenAltarRecipe recipe) {
            recipe.getIngredientRecipe().toNetwork(buffer);
            buffer.writeInt(recipe.getRecipeWissen());
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
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial(){
        return true;
    }
}
