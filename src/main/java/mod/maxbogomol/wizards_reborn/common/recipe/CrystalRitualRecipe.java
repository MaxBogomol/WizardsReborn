package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CrystalRitualRecipe implements Recipe<Container> {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "crystal_ritual");
    private final ResourceLocation id;
    private final NonNullList<Ingredient> inputs;
    private final CrystalRitual ritual;

    public CrystalRitualRecipe(ResourceLocation id, CrystalRitual ritual, Ingredient... inputs) {
        this.id = id;
        this.ritual = ritual;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return matches(inputs, container);
    }

    public static boolean matches(List<Ingredient> inputs, Container container) {
        List<Ingredient> ingredientsMissing = new ArrayList<>(inputs);

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack input = container.getItem(i);
            if (input.isEmpty()) {
                break;
            }

            int stackIndex = -1;

            for (int j = 0; j < ingredientsMissing.size(); j++) {
                Ingredient ingredient = ingredientsMissing.get(j);
                if (ingredient.test(input)) {
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

        return ingredientsMissing.isEmpty();
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.RUNIC_PEDESTAL.get());
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
        return WizardsRebornRecipes.CRYSTAL_RITUAL_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public boolean isSpecial(){
        return true;
    }

    public static class Serializer implements RecipeSerializer<CrystalRitualRecipe> {

        @Override
        public CrystalRitualRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonArray ingrs = GsonHelper.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : ingrs) {
                inputs.add(Ingredient.fromJson(e));
            }

            CrystalRitual crystalRitual = CrystalRitualUtil.deserializeCrystalRitual(GsonHelper.getAsJsonObject(json, "crystal_ritual"));

            return new CrystalRitualRecipe(recipeId, crystalRitual, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public CrystalRitualRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient[] inputs = new Ingredient[buffer.readInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(buffer);
            }
            CrystalRitual crystalRitual = CrystalRitualUtil.crystalRitualFromNetwork(buffer);
            return new CrystalRitualRecipe(recipeId, crystalRitual, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CrystalRitualRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(buffer);
            }
            CrystalRitualUtil.crystalRitualToNetwork(recipe.getRitual(), buffer);
        }
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    public CrystalRitual getRitual() {
        return ritual;
    }
}
