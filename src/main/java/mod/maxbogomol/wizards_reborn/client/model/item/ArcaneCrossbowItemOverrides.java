package mod.maxbogomol.wizards_reborn.client.model.item;

import mod.maxbogomol.fluffy_fur.client.model.item.CrossbowItemOverrides;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;

public class ArcaneCrossbowItemOverrides extends CrossbowItemOverrides {
    public BakedModel arcaneFireworkModel;

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        if (getCharged(stack, level, entity, seed)) {
            boolean arcaneFirework = getArcaneFirework(stack, level, entity, seed);
            if (arcaneFirework) return arcaneFireworkModel;
        }
        return super.resolve(originalModel, stack, level, entity, seed);
    }

    public boolean getArcaneFirework(ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
        return CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, Items.DIRT);
    }
}