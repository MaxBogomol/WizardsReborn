package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IGuiParticleItem {
    void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset);
}
