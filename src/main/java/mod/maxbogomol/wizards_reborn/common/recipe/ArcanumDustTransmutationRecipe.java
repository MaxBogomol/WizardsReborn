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
    private final Ingredient input;
    private final ItemStack output;
    private ItemStack display = ItemStack.EMPTY;
    private boolean placeBlock = true;

    public ArcanumDustTransmutationRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    public ArcanumDustTransmutationRecipe setDisplay(ItemStack display) {
        this.display = display;
        return this;
    }

    public ArcanumDustTransmutationRecipe setPlaceBlock(boolean placeBlock) {
        this.placeBlock = placeBlock;
        return this;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return input.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.ARCANUM_DUST.get());
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
        return WizardsRebornRecipes.ARCANUM_DUST_TRANSMUTATION_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
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
            boolean placeBlock = true;
            if (GsonHelper.isValidNode(json, "display")) {
                display = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "display"));
            }
            if (GsonHelper.isValidNode(json, "placeBlock")) {
                placeBlock = GsonHelper.getAsBoolean(json, "placeBlock");
            }
            return new ArcanumDustTransmutationRecipe(recipeId, input, output).setDisplay(display).setPlaceBlock(placeBlock);
        }

        @Nullable
        @Override
        public ArcanumDustTransmutationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            ItemStack display = buffer.readItem();
            boolean placeBlock = buffer.readBoolean();
            return new ArcanumDustTransmutationRecipe(recipeId, input, output).setDisplay(display).setPlaceBlock(placeBlock);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ArcanumDustTransmutationRecipe recipe) {
            recipe.getInput().toNetwork(buffer);
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buffer.writeItemStack(recipe.getDisplay(), false);
            buffer.writeBoolean(recipe.getPlaceBlock());
        }
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output;
    }

    public Ingredient getInput() {
        return input;
    }

    public boolean getPlaceBlock() {
        return placeBlock;
    }

    public ItemStack getDisplay() {
        return display;
    }
}