package mod.maxbogomol.wizards_reborn.client.model.item;

import mod.maxbogomol.fluffy_fur.client.model.item.CustomItemOverrides;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class SkinItemOverrides extends CustomItemOverrides {
    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        Skin skin = Skin.getSkinFromItem(stack);
        if (skin != null) {
            String skinStr = skin.getItemModelName(stack);
            if (skinStr != null) return ItemSkinsModels.getModelSkins(skinStr);
        }
        return originalModel;
    }
}