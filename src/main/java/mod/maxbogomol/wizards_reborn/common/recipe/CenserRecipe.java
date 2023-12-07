package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

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
    public boolean matches(Container inv, Level worldIn) {
        return input.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    public Ingredient getIngredientRecipe() {
        return input;
    }

    public List<MobEffectInstance> getEffects() {
        return effects;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsReborn.ARCANE_CENSER_ITEM.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WizardsReborn.MORTAR_SERIALIZER.get();
    }

    public static class MortarRecipeType implements RecipeType<CenserRecipe> {
        @Override
        public String toString() {
            return CenserRecipe.TYPE_ID.toString();
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
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
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
                    effects.add(deserializeMobEffect(e.getAsJsonObject()));
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
                MobEffect mobEffect = ForgeRegistries.MOB_EFFECTS.getValue(buffer.readResourceLocation());
                int duration = buffer.readInt();
                int amplifier = buffer.readInt();
                effects.add(new MobEffectInstance(mobEffect, duration, amplifier));
            }

            return new CenserRecipe(recipeId, input, effects);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CenserRecipe recipe) {
            recipe.getIngredientRecipe().toNetwork(buffer);
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);

            buffer.writeInt(recipe.getEffects().size());
            for (MobEffectInstance effect : recipe.getEffects()) {
                buffer.writeRegistryId(ForgeRegistries.MOB_EFFECTS, effect.getEffect());
                buffer.writeVarInt(effect.getDuration());
                buffer.writeVarInt(effect.getAmplifier());
            }
        }
    }

    public static MobEffectInstance deserializeMobEffect(JsonObject json) {
        String effectName = GsonHelper.getAsString(json, "effect");
        MobEffect mobEffect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(effectName));
        if (mobEffect == null) {
            throw new JsonSyntaxException("Unknown effect " + effectName);
        }
        int duration = GsonHelper.getAsInt(json, "duration");
        int amplifier = GsonHelper.getAsInt(json, "amplifier");
        return new MobEffectInstance(mobEffect, duration, amplifier);
    }
}