package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArcaneWorkbenchRecipe implements Recipe<Container> {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "arcane_workbench");
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;
    private final int wissen;
    private int saveNBT = -1;

    public ArcaneWorkbenchRecipe(ResourceLocation id, ItemStack output, int wissen, Ingredient... inputs) {
        this.id = id;
        this.output = output;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
        this.wissen = wissen;
    }

    public ArcaneWorkbenchRecipe setSaveNBT(int saveNBT) {
        this.saveNBT = saveNBT;
        return this;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return matches(inputs, container);
    }

    public static boolean matches(List<Ingredient> inputs, Container container) {
        boolean craft = true;
        for (int i = 0; i < inputs.size(); i += 1) {
            if (!inputs.get(i).test(container.getItem(i))) {
                craft = false;
            }
        }

        return craft;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return output;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.ARCANE_WORKBENCH.get());
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
        return WizardsRebornRecipes.ARCANE_WORKBENCH_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public boolean isSpecial(){
        return true;
    }

    public static class Serializer implements RecipeSerializer<ArcaneWorkbenchRecipe> {

        @Override
        public ArcaneWorkbenchRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(13, Ingredient.EMPTY);

            Map<String, Ingredient> keys = new HashMap<String, Ingredient>();
            for (Map.Entry<String, JsonElement> entry : GsonHelper.getAsJsonObject(json, "key").entrySet()) {
                if (entry.getKey().length() != 1) {
                    throw new JsonSyntaxException("Invalid key entry: '" + (String) entry.getKey() + "' is an invalid symbol (must be 1 character only).");
                }

                if (" ".equals(entry.getKey())) {
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
                }

                keys.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
            }

            int index=0;
            JsonArray pattern = GsonHelper.getAsJsonArray(json, "pattern");
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

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            int wissen = GsonHelper.getAsInt(json, "wissen");

            int saveNBT = -1;
            if (json.has("saveNBT")) {
                saveNBT = GsonHelper.getAsInt(json, "saveNBT");
            }

            return new ArcaneWorkbenchRecipe(recipeId, output, wissen, inputs.toArray(new Ingredient[0])).setSaveNBT(saveNBT);
        }

        @Nullable
        @Override
        public ArcaneWorkbenchRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient[] inputs = new Ingredient[buffer.readInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(buffer);
            }
            ItemStack output = buffer.readItem();
            int wissen = buffer.readInt();
            int saveNBT = buffer.readInt();
            return new ArcaneWorkbenchRecipe(recipeId, output, wissen, inputs).setSaveNBT(saveNBT);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ArcaneWorkbenchRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            buffer.writeInt(recipe.getWissen());
            buffer.writeInt(recipe.getSaveNBT());
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

    public int getSaveNBT() {
        return saveNBT;
    }
}
