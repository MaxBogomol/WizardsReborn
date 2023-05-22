package mod.maxbogomol.wizards_reborn.common.data.recipes;

import com.google.gson.JsonObject;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class WissenAltarRecipe implements IRecipe<IInventory> {

    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "wissen_altar");
    private final ResourceLocation id;
    private final Ingredient recipeItem;
    private int wissen;

    public WissenAltarRecipe(ResourceLocation id, Ingredient recipeItem, int wissen) {
        this.id = id;
        this.recipeItem = recipeItem;
        this.wissen = wissen;
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

    public int getRecipeWissen() {
        return wissen;
    }

    public ItemStack getIcon() {
        return new ItemStack(WizardsReborn.WISSEN_ALTAR.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return WizardsReborn.WISSEN_ALTAR_SERIALIZER.get();
    }

    public static class WissenAltarRecipeType implements IRecipeType<WissenAltarRecipe> {
        @Override
        public String toString() {
            return WissenAltarRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<WissenAltarRecipe> {

        @Override
        public WissenAltarRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            int wissen = JSONUtils.getInt(json, "wissen");

            return new WissenAltarRecipe(recipeId, input, wissen);
        }

        @Nullable
        @Override
        public WissenAltarRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient input = Ingredient.read(buffer);
            int wissen = buffer.readInt();
            return new WissenAltarRecipe(recipeId, input, wissen);
        }

        @Override
        public void write(PacketBuffer buffer, WissenAltarRecipe recipe) {
            recipe.getIngredientRecipe().write(buffer);
            buffer.writeInt(recipe.getRecipeWissen());
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
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isDynamic(){
        return true;
    }

}
