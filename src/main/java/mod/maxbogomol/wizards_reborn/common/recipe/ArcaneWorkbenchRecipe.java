package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArcaneWorkbenchRecipe implements IRecipe<IInventory> {

    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "arcane_workbench");
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;
    private int wissen;

    public ArcaneWorkbenchRecipe(ResourceLocation id, ItemStack output, int wissen, Ingredient... inputs) {
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
        boolean craft = true;
        for (int i = 0; i < 13; i += 1) {
            if (!inputs.get(i).test(inv.getStackInSlot(i))) {
                craft = false;
            }
        }

        return craft;
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
        return new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return WizardsReborn.ARCANE_WORKBENCH_SERIALIZER.get();
    }

    public static class ArcaneWorkbenchRecipeType implements IRecipeType<ArcaneWorkbenchRecipe> {
        @Override
        public String toString() {
            return ArcaneWorkbenchRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<ArcaneWorkbenchRecipe> {

        @Override
        public ArcaneWorkbenchRecipe read(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(13, Ingredient.EMPTY);

            Map<String, Ingredient> keys = new HashMap<String, Ingredient>();
            for(Map.Entry<String, JsonElement> entry : JSONUtils.getJsonObject(json, "key").entrySet()) {
                if (entry.getKey().length() != 1) {
                    throw new JsonSyntaxException("Invalid key entry: '" + (String) entry.getKey() + "' is an invalid symbol (must be 1 character only).");
                }

                if (" ".equals(entry.getKey())) {
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
                }

                keys.put(entry.getKey(), Ingredient.deserialize(entry.getValue()));
            }

            int index=0;
            JsonArray pattern = JSONUtils.getJsonArray(json, "pattern");
            for (int i = 0; i < pattern.size(); i++) {
                String str = pattern.get(i).getAsString();
                for (int ii = 0; ii < str.length(); ii++) {
                    if (keys.containsKey(str.substring(ii,ii+1))) {
                        inputs.set(index, keys.get(str.substring(ii, ii + 1)));
                    } else {
                        inputs.set(index, Ingredient.EMPTY);
                    }
                    index++;
                }
            }

            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            int wissen = JSONUtils.getInt(json, "wissen");

            return new ArcaneWorkbenchRecipe(recipeId, output, wissen, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public ArcaneWorkbenchRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient[] inputs = new Ingredient[buffer.readVarInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.read(buffer);
            }
            ItemStack output = buffer.readItemStack();
            int wissen = buffer.readInt();
            return new ArcaneWorkbenchRecipe(recipeId, output, wissen, inputs);
        }

        @Override
        public void write(PacketBuffer buffer, ArcaneWorkbenchRecipe recipe) {
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
