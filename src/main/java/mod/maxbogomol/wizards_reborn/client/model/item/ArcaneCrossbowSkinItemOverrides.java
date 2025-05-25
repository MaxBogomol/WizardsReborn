package mod.maxbogomol.wizards_reborn.client.model.item;

import mod.maxbogomol.fluffy_fur.client.model.item.ItemSkinModels;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;

public class ArcaneCrossbowSkinItemOverrides extends ArcaneCrossbowItemOverrides {

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) {
            String skinStr = getSkinModel(skin, originalModel, stack, level, entity, seed);
            if (skinStr != null) return ItemSkinModels.getModelSkins(skinStr);
        }
        return super.resolve(originalModel, stack, level, entity, seed);
    }

    public String getSkinModel(ItemSkin skin, BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        if (getPulling(stack, level, entity, seed) > 0) {
            float pull = getPull(stack, level, entity, seed);
            String skinStr = skin.getItemModelName(stack) + "_pulling_0";
            if (pull >= 0.58f) {
                skinStr = skin.getItemModelName(stack) + "_pulling_1";
            }
            if (pull >= 1f) {
                skinStr = skin.getItemModelName(stack) + "_pulling_2";
            }
            return skinStr;
        }
        if ( getCharged(stack, level, entity, seed)) {
            boolean firework = getFirework(stack, level, entity, seed);
            boolean arcaneFirework = getArcaneFirework(stack, level, entity, seed);
            String skinStr = skin.getItemModelName(stack) + "_arrow";
            if (firework) skinStr = skin.getItemModelName(stack) + "_firework";
            if (arcaneFirework) skinStr = skin.getItemModelName(stack) + "_arcane_firework";
            return skinStr;
        }
        return skin.getItemModelName(stack);
    }

    public boolean getArcaneFirework(ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
        return CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, Items.DIRT);
    }
}