package mod.maxbogomol.wizards_reborn.client.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.data.recipes.ArcanumDustTransmutationRecipe;
import mod.maxbogomol.wizards_reborn.common.data.recipes.WissenAltarRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.stream.Collectors;

@JeiPlugin
public class WizardsRebornJei implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new ArcanumDustTransmutationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new WissenAltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().world).getRecipeManager();
        registration.addRecipes(rm.getRecipesForType(WizardsReborn.ARCANUM_DUST_TRANSMUTATION_RECIPE).stream()
                        .filter(r -> r instanceof ArcanumDustTransmutationRecipe).collect(Collectors.toList()),
                ArcanumDustTransmutationRecipeCategory.UID);
        registration.addRecipes(rm.getRecipesForType(WizardsReborn.WISSEN_ALTAR_RECIPE).stream()
                        .filter(r -> r instanceof WissenAltarRecipe).collect(Collectors.toList()),
                WissenAltarRecipeCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANUM_DUST.get()), ArcanumDustTransmutationRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), WissenAltarRecipeCategory.UID);
    }
}