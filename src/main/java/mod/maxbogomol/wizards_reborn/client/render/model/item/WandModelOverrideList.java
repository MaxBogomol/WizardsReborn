package mod.maxbogomol.wizards_reborn.client.render.model.item;

import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;

public class WandModelOverrideList extends CustomModelOverrideList {

    public WandModelOverrideList()
    {
        super();
    }

    @Override
    public IBakedModel getOverrideModel(IBakedModel originalModel, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity)
    {
        CompoundNBT nbt = stack.getTag();
        if (nbt != null) {
            if (nbt.getBoolean("crystal")) {
                Inventory stack_inv = ArcaneWandItem.getInventory(stack);

                return WandCrystalsModels.getModel(stack_inv.getStackInSlot(0).getItem().getRegistryName().toString());
            }
        }
        return originalModel;
    }
}