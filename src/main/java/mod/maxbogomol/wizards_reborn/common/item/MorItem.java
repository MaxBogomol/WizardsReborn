package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Random;

public class MorItem extends BlockItem {
    private static Random random = new Random();
    public int minEffect;
    public int maxEffect;

    public MorItem(Block blockIn, Properties properties, int minEffect, int maxEffect) {
        super(blockIn, properties);
        this.minEffect = minEffect;
        this.maxEffect = maxEffect;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(WizardsRebornMobEffects.MOR_SPORES.get(), random.nextInt(minEffect, maxEffect), 0));
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
