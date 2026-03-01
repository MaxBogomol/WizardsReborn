package mod.maxbogomol.wizards_reborn.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.maxbogomol.fluffy_fur.util.RecipeUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ArcaneIteratorRecipe implements Recipe<Container> {
    public static ResourceLocation TYPE_ID = new ResourceLocation(WizardsReborn.MOD_ID, "arcane_iterator");
    private final ResourceLocation id;
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private Enchantment enchantment = null;
    private ArcaneEnchantment arcaneEnchantment = null;
    private CrystalRitual crystalRitual = null;
    private int wissen = 0;
    private int health = 0;
    private int experience = 0;
    private boolean isSaveNBT = false;

    public ArcaneIteratorRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs) {
        this.id = id;
        this.output = output;
        this.inputs = inputs;
    }

    public ArcaneIteratorRecipe setEnchantment(Enchantment enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    public ArcaneIteratorRecipe setArcaneEnchantment(ArcaneEnchantment arcaneEnchantment) {
        this.arcaneEnchantment = arcaneEnchantment;
        return this;
    }

    public ArcaneIteratorRecipe setCrystalRitual(CrystalRitual crystalRitual) {
        this.crystalRitual = crystalRitual;
        return this;
    }

    public ArcaneIteratorRecipe setWissen(int wissen) {
        this.wissen = wissen;
        return this;
    }

    public ArcaneIteratorRecipe setHealth(int health) {
        this.health = health;
        return this;
    }

    public ArcaneIteratorRecipe setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public ArcaneIteratorRecipe setIsSaveNBT(boolean isSaveNBT) {
        this.isSaveNBT = isSaveNBT;
        return this;
    }

    @Override
    public boolean matches(Container container, Level level) {
        boolean hasEnchantment = (getResultItem(RegistryAccess.EMPTY).isEmpty() && (hasEnchantment() || hasArcaneEnchantment()));
        return matches(inputs, container, hasEnchantment);
    }

    public static boolean matches(List<Ingredient> inputs, Container container, boolean hasEnchantment) {
        List<Ingredient> ingredientsMissing = new ArrayList<>(inputs);

        int u = 0;
        if (hasEnchantment) {
            u = 1;
        }

        for (int i = u; i < container.getContainerSize(); i++) {
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

        if (!hasEnchantment) {
            ItemStack stack = container.getItem(0);
            if (stack.isEmpty()) {
                return false;
            }
            Ingredient ingredient = inputs.get(0);
            if (!ingredient.test(stack)) {
                return false;
            }
        }

        return ingredientsMissing.isEmpty();
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return output;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(WizardsRebornItems.ARCANE_ITERATOR.get());
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
        return WizardsRebornRecipes.ARCANE_ITERATOR_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public boolean isSpecial(){
        return true;
    }

    public static class Serializer implements RecipeSerializer<ArcaneIteratorRecipe> {

        @Override
        public ArcaneIteratorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ItemStack.EMPTY;
            Enchantment enchantment = null;
            ArcaneEnchantment arcaneEnchantment = null;
            CrystalRitual crystalRitual = null;

            int wissen = GsonHelper.getAsInt(json, "wissen");
            int health = 0;
            int experience = 0;

            boolean isSaveNBT = false;

            if (json.has("output")) {
                output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            }
            if (json.has("enchantment")) {
                enchantment = RecipeUtil.deserializeEnchantment(GsonHelper.getAsJsonObject(json, "enchantment"));
            }
            if (json.has("arcane_enchantment")) {
                arcaneEnchantment = ArcaneEnchantmentUtil.deserializeArcaneEnchantment(GsonHelper.getAsJsonObject(json, "arcane_enchantment"));
            }
            if (json.has("crystal_ritual")) {
                crystalRitual = CrystalRitualUtil.deserializeCrystalRitual(GsonHelper.getAsJsonObject(json, "crystal_ritual"));
            }

            if (json.has("health")) {
                health = GsonHelper.getAsInt(json, "health");
            }
            if (json.has("experience")) {
                experience = GsonHelper.getAsInt(json, "experience");
            }

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.create();
            for (JsonElement e : ingredients) {
                inputs.add(Ingredient.fromJson(e));
            }

            if (json.has("saveNBT")) {
                isSaveNBT = GsonHelper.getAsBoolean(json, "saveNBT");
            }

            return new ArcaneIteratorRecipe(recipeId, output, inputs).setEnchantment(enchantment).setArcaneEnchantment(arcaneEnchantment).setCrystalRitual(crystalRitual).setWissen(wissen).setHealth(health).setExperience(experience).setIsSaveNBT(isSaveNBT);
        }

        @Nullable
        @Override
        public ArcaneIteratorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.create();
            int inputsSize = buffer.readInt();
            for (int i = 0; i < inputsSize; i++) {
                inputs.add(Ingredient.fromNetwork(buffer));
            }
            ItemStack output = buffer.readItem();
            Enchantment enchantment = RecipeUtil.enchantmentFromNetwork(buffer);
            ArcaneEnchantment arcaneEnchantment = ArcaneEnchantmentUtil.arcaneEnchantmentFromNetwork(buffer);
            CrystalRitual crystalRitual = CrystalRitualUtil.crystalRitualFromNetwork(buffer);
            int wissen = buffer.readInt();
            int health = buffer.readInt();
            int experience = buffer.readInt();
            boolean isSaveNBT = buffer.readBoolean();
            return new ArcaneIteratorRecipe(recipeId, output, inputs).setEnchantment(enchantment).setArcaneEnchantment(arcaneEnchantment).setCrystalRitual(crystalRitual).setWissen(wissen).setHealth(health).setExperience(experience).setIsSaveNBT(isSaveNBT);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ArcaneIteratorRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
            RecipeUtil.enchantmentToNetwork(recipe.getEnchantment(), buffer);
            ArcaneEnchantmentUtil.arcaneEnchantmentToNetwork(recipe.getArcaneEnchantment(), buffer);
            CrystalRitualUtil.crystalRitualToNetwork(recipe.getCrystalRitual(), buffer);
            buffer.writeInt(recipe.getWissen());
            buffer.writeInt(recipe.getHealth());
            buffer.writeInt(recipe.getExperience());
            buffer.writeBoolean(recipe.getIsSaveNBT());
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

    public int getHealth() {
        return health;
    }

    public int getExperience() {
        return experience;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public ArcaneEnchantment getArcaneEnchantment() {
        return arcaneEnchantment;
    }

    public CrystalRitual getCrystalRitual() {
        return crystalRitual;
    }

    public boolean getIsSaveNBT() {
        return isSaveNBT;
    }

    public boolean hasEnchantment() {
        return enchantment != null;
    }

    public boolean hasArcaneEnchantment() {
        return arcaneEnchantment != null;
    }

    public boolean hasCrystalRitual() {
        return crystalRitual != null;
    }
}
