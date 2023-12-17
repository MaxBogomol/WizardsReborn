package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.client.animation.ItemAnimation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICustomAnimationItem {
    @OnlyIn(Dist.CLIENT)
    ItemAnimation getAnimation(ItemStack stack);
}
