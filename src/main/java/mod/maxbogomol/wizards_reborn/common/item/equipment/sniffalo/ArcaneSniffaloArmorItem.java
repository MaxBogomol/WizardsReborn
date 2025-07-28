package mod.maxbogomol.wizards_reborn.common.item.equipment.sniffalo;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.model.sniffalo.SniffaloArmorModel;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.SniffaloArmorItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArcaneSniffaloArmorItem extends SniffaloArmorItem {

    public static final ResourceLocation SNIFFALO_ARMOR_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/armor/arcane_armor.png");

    public ArcaneSniffaloArmorItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getSniffaloArmorTexture() {
        return SNIFFALO_ARMOR_TEXTURE;
    }

    @OnlyIn(Dist.CLIENT)
    public SniffaloArmorModel getSniffaloArmorModel() {
        return WizardsRebornModels.SNIFFALO_ARCANE_ARMOR;
    }
}
