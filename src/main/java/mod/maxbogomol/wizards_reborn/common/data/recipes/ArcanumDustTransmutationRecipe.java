package mod.maxbogomol.wizards_reborn.common.data.recipes;

import com.google.gson.JsonObject;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class ArcanumDustTransmutationRecipe implements IRecipe<IInventory>  {

    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_dust_transmutation");
    private final ResourceLocation id;
    private final Ingredient recipeItem;
    private final ItemStack output;
    private boolean place_block;

    public ArcanumDustTransmutationRecipe(ResourceLocation id, Ingredient recipeItem, ItemStack output, boolean place_block) {
        this.id = id;
        this.recipeItem = recipeItem;
        this.output = output;
        this.place_block = place_block;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return recipeItem.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    public Ingredient getIngredientRecipe() {
        return recipeItem;
    }

    public boolean getRecipePlaceBlock() {
        return place_block;
    }

    public ItemStack getIcon() {
        return new ItemStack(WizardsReborn.ARCANUM_DUST.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return WizardsReborn.ARCANUM_DUST_TRANSMUTATION_SERIALIZER.get();
    }

    public static class ArcanumDustTransmutationRecipeType implements IRecipeType<ArcanumDustTransmutationRecipe> {
        @Override
        public String toString() {
            return ArcanumDustTransmutationRecipe.TYPE_ID.toString();
        }
    }

    @Override
    public IRecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    public boolean isDynamic(){
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<ArcanumDustTransmutationRecipe> {

        @Override
        public ArcanumDustTransmutationRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "from"));
            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "to"));
            boolean place_block = JSONUtils.getBoolean(json, "place_block");

            return new ArcanumDustTransmutationRecipe(recipeId, input, output, place_block);
        }

        @Nullable
        @Override
        public ArcanumDustTransmutationRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient input = Ingredient.read(buffer);
            ItemStack output = buffer.readItemStack();
            boolean place_block = buffer.readBoolean();

            return new ArcanumDustTransmutationRecipe(recipeId, input, output, place_block);
        }

        @Override
        public void write(PacketBuffer buffer, ArcanumDustTransmutationRecipe recipe) {
            recipe.getIngredientRecipe().write(buffer);
            buffer.writeItemStack(recipe.getRecipeOutput(), false);
            buffer.writeBoolean(recipe.getRecipePlaceBlock());
        }
    }
}
