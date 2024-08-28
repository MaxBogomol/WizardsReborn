package mod.maxbogomol.wizards_reborn.client.model.item;

import mod.maxbogomol.fluffy_fur.client.model.item.CustomItemOverrides;
import mod.maxbogomol.wizards_reborn.common.item.equipment.DrinkBottleItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class DrinkItemOverrides extends CustomItemOverrides {

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        int stage = DrinkBottleItem.getStageS(stack);
        if (stage > 0) {
            String string = stack.getDescriptionId();
            int i = string.indexOf(".");
            string = string.substring(i + 1);
            i = string.indexOf(".");
            String modId = string.substring(0, i);
            String drinkId = string.substring(i + 1);

            return DrinksModels.getModel(modId + ":" + drinkId, stage);
        }
        return originalModel;
    }
}