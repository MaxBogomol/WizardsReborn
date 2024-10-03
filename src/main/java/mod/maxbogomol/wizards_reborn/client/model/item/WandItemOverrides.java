package mod.maxbogomol.wizards_reborn.client.model.item;

import mod.maxbogomol.fluffy_fur.client.model.item.CustomItemOverrides;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class WandItemOverrides extends CustomItemOverrides {

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("crystal") && nbt.getBoolean("crystal")) {
            SimpleContainer stack_inv = ArcaneWandItem.getInventory(stack);
            String string = stack_inv.getItem(0).getDescriptionId();

            int i = string.indexOf(".");
            string = string.substring(i + 1);
            i = string.indexOf(".");
            String modId = string.substring(0, i);
            String crystalId = string.substring(i + 1);

            ItemSkin skin = ItemSkin.getSkinFromItem(stack);
            if (skin != null) {
                BakedModel model = WandCrystalsModels.getModel(skin.getItemModelName(stack), modId + ":" + crystalId);
                if (model != null) return model;
            } else {
                string = stack.getDescriptionId();
                i = string.indexOf(".");
                string = string.substring(i + 1);
                i = string.indexOf(".");
                String modIdW = string.substring(0, i);
                String wandId = string.substring(i + 1);

                BakedModel model = WandCrystalsModels.getModel(modIdW + ":" + wandId, modId + ":" + crystalId);
                if (model != null) return model;
            }
        }

        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) {
            BakedModel model = WandCrystalsModels.getModel(skin.getItemModelName(stack), "");
            if (model != null) return model;
        }

        return originalModel;
    }
}