package mod.maxbogomol.wizards_reborn.common.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PitcherDewItem extends Item {

    public PitcherDewItem(Properties properties) {
        super(properties);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 3000, 0));
        livingEntity.setAirSupply(livingEntity.getMaxAirSupply());
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
