package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonObject;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
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

public class ArcanumDustTransmutationRecipe implements Recipe<Container>  {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_dust_transmutation");
    private final ResourceLocation id;
    private final Ingredient recipeItem;
    private final ItemStack output;
    private final ItemStack display;
    private final boolean place_block;

    public ArcanumDustTransmutationRecipe(ResourceLocation id, Ingredient recipeItem, ItemStack output, ItemStack display, boolean place_block) {
        this.id = id;
        this.recipeItem = recipeItem;
        this.output = output;
        this.display = display;
        this.place_block = place_block;
    }

    @Override
    public boolean matches(Container inv, Level levelIn) {
        return recipeItem.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    public Ingredient getIngredientRecipe() {
        return recipeItem;
    }

    public boolean getPlaceBlock() {
        return place_block;
    }

    public ItemStack getDisplay() {
        return display;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.ARCANUM_DUST.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WizardsRebornRecipes.ARCANUM_DUST_TRANSMUTATION_SERIALIZER.get();
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

    public static class Serializer implements RecipeSerializer<ArcanumDustTransmutationRecipe> {

        @Override
        public ArcanumDustTransmutationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "from"));
            ItemStack output = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "to")).getDefaultInstance();
            ItemStack display = ItemStack.EMPTY;
            boolean place_block = true;
            if (GsonHelper.isValidNode(json, "display")) {
                display = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "display"));
            }
            if (GsonHelper.isValidNode(json, "place_block")) {
                place_block = GsonHelper.getAsBoolean(json, "place_block");
            }

            return new ArcanumDustTransmutationRecipe(recipeId, input, output, display, place_block);
        }

        @Nullable
        @Override
        public ArcanumDustTransmutationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            ItemStack display = buffer.readItem();
            boolean place_block = buffer.readBoolean();

            return new ArcanumDustTransmutationRecipe(recipeId, input, output, display, place_block);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ArcanumDustTransmutationRecipe recipe) {
            recipe.getIngredientRecipe().toNetwork(buffer);
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buffer.writeItemStack(recipe.getDisplay(), false);
            buffer.writeBoolean(recipe.getPlaceBlock());
        }
    }
}