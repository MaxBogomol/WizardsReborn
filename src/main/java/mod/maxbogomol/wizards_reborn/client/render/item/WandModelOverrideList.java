package mod.maxbogomol.wizards_reborn.client.render.item;

import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public class WandModelOverrideList extends CustomModelOverrideList {

    public WandModelOverrideList()
    {
        super();
    }

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int pSeed)
    {
        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            if (nbt.getBoolean("crystal")) {
                SimpleContainer stack_inv = ArcaneWandItem.getInventory(stack);

                return WandCrystalsModels.getModel(stack_inv.getItem(0).getItem().getDescriptionId().toString());
            }
        }
        return originalModel;
    }
}