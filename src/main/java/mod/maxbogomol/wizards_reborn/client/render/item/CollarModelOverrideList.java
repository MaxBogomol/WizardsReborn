package mod.maxbogomol.wizards_reborn.client.render.item;

import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.LeatherCollarItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CollarModelOverrideList extends CustomModelOverrideList {
    public static Map<String, BakedModel> skins = new HashMap<>();

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int pSeed)
    {
        String skin = LeatherCollarItem.getSkin(stack);
        if (skin != null) {
            return skins.get(skin);
        }
        return originalModel;
    }
}