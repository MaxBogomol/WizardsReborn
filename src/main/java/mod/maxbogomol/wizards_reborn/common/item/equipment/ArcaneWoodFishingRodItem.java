package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneFishingRodItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class ArcaneWoodFishingRodItem extends ArcaneFishingRodItem {

    private static final ResourceLocation HOOK_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/arcane_wood_fishing_hook.png");

    public ArcaneWoodFishingRodItem(Properties properties) {
        super(properties);
    }

    public ArcaneWoodFishingRodItem addArcaneEnchantmentType(ArcaneEnchantmentType type) {
        super.addArcaneEnchantmentType(type);
        return this;
    }

    public ArcaneWoodFishingRodItem setRepairMaterial(Supplier<Ingredient> repairMaterial) {
        super.setRepairMaterial(repairMaterial);
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTexture(ItemStack stack, Entity entity) {
        return HOOK_TEXTURE;
    }
}
