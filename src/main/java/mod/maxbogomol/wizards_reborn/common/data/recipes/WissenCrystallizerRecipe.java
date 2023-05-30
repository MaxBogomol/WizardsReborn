package mod.maxbogomol.wizards_reborn.common.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntSet;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WissenCrystallizerRecipe implements IRecipe<IInventory> {

    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "wissen_crystallizer");
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;
    private int wissen;

    public WissenCrystallizerRecipe(ResourceLocation id, ItemStack output, int wissen, Ingredient... inputs) {
        this.id = id;
        this.output = output;
        this.inputs = NonNullList.from(Ingredient.EMPTY, inputs);
        this.wissen = wissen;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return matches(inputs, inv);
    }

    public static boolean matches(List<Ingredient> inputs, IInventory inv) {
        List<Ingredient> ingredientsMissing = new ArrayList<>(inputs);

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack input = inv.getStackInSlot(i);
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

        ItemStack stack = inv.getStackInSlot(0);
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
    public ItemStack getCraftingResult(IInventory inv) {
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

    public ItemStack getIcon() {
        return new ItemStack(WizardsReborn.WISSEN_ALTAR.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return WizardsReborn.WISSEN_CRYSTALLIZER_SERIALIZER.get();
    }

    public static class WissenCrystallizerRecipeType implements IRecipeType<WissenCrystallizerRecipe> {
        @Override
        public String toString() {
            return WissenCrystallizerRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<WissenCrystallizerRecipe> {

        @Override
        public WissenCrystallizerRecipe read(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            int wissen = JSONUtils.getInt(json, "wissen");
            JsonArray ingrs = JSONUtils.getJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : ingrs) {
                inputs.add(Ingredient.deserialize(e));
            }

            return new WissenCrystallizerRecipe(recipeId, output, wissen, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public WissenCrystallizerRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient[] inputs = new Ingredient[buffer.readVarInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.read(buffer);
            }
            ItemStack output = buffer.readItemStack();
            int wissen = buffer.readInt();
            return new WissenCrystallizerRecipe(recipeId, output, wissen, inputs);
        }

        @Override
        public void write(PacketBuffer buffer, WissenCrystallizerRecipe recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.write(buffer);
            }
            buffer.writeItemStack(recipe.getRecipeOutput(), false);
            buffer.writeVarInt(recipe.getRecipeWissen());
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

}
