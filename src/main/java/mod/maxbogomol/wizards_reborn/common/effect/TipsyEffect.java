package mod.maxbogomol.wizards_reborn.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class TipsyEffect extends MobEffect {

    public TipsyEffect() {
        super(MobEffectCategory.NEUTRAL, 0xb5d2d2);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {

    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 60 == 0;
    }
}
