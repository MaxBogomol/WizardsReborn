package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.maxbogomol.fluffy_fur.util.RecipeUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CenserRecipe implements Recipe<Container>  {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "censer");
    private final ResourceLocation id;
    private final Ingredient input;
    private final List<MobEffectInstance> effects;

    public CenserRecipe(ResourceLocation id, Ingredient input, List<MobEffectInstance> effects) {
        this.id = id;
        this.input = input;
        this.effects = effects;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return input.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    public Ingredient getIngredientRecipe() {
        return input;
    }

    public List<MobEffectInstance> getEffects() {
        return effects;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.ARCANE_CENSER.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WizardsRebornRecipes.CENSER_SERIALIZER.get();
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

    public static class Serializer implements RecipeSerializer<CenserRecipe> {

        @Override
        public CenserRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));

            List<MobEffectInstance> effects = new ArrayList<>();

            if (json.has("effects")) {
                JsonArray eff = GsonHelper.getAsJsonArray(json, "effects");
                for (JsonElement e : eff) {
                    effects.add(RecipeUtil.deserializeMobEffect(e.getAsJsonObject()));
                }
            }

            return new CenserRecipe(recipeId, input, effects);
        }

        @Nullable
        @Override
        public CenserRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);

            List<MobEffectInstance> effects = new ArrayList<>();
            int effectsSize = buffer.readInt();
            for (int i = 0; i < effectsSize; i++) {
                effects.add(RecipeUtil.mobEffectFromNetwork(buffer));
            }

            return new CenserRecipe(recipeId, input, effects);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CenserRecipe recipe) {
            recipe.getIngredientRecipe().toNetwork(buffer);

            buffer.writeInt(recipe.getEffects().size());
            for (MobEffectInstance effect : recipe.getEffects()) {
                RecipeUtil.mobEffectToNetwork(effect, buffer);
            }
        }
    }
}