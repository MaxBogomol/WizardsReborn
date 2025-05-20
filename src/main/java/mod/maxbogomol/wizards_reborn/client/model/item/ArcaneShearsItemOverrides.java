package mod.maxbogomol.wizards_reborn.client.model.item;

import mod.maxbogomol.fluffy_fur.client.model.item.CustomItemOverrides;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneShearsItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ArcaneShearsItemOverrides extends CustomItemOverrides {
    public BakedModel openModel;
    public BakedModel thrownModel;

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        int open = ArcaneShearsItem.getOpen(stack);
        if (open == 1) return openModel;
        if (open == 2) return thrownModel;
        return originalModel;
    }
}