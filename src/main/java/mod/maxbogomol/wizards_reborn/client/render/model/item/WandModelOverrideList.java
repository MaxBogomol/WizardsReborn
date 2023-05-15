package mod.maxbogomol.wizards_reborn.client.render.model.item;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class WandModelOverrideList extends CustomModelOverrideList {

    public WandModelOverrideList()
    {
        super();
    }

    @Override
    public IBakedModel getOverrideModel(IBakedModel originalModel, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity)
    {
        return WandCrystalsModels.getModel("wizards_reborn:masterful_earth_crystal");
    }
}