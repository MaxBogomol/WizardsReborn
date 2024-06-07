package mod.maxbogomol.wizards_reborn.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IrritationEffect extends MobEffect {
    List<MobEffect> effectList = new ArrayList<>();
    public static Random random = new Random();

    public IrritationEffect() {
        super(MobEffectCategory.HARMFUL, 0xff77a7);
        effectList.add(MobEffects.POISON);
        effectList.add(MobEffects.WEAKNESS);
        effectList.add(MobEffects.DARKNESS);
        effectList.add(MobEffects.DIG_SLOWDOWN);
        effectList.add(MobEffects.MOVEMENT_SLOWDOWN);
        effectList.add(MobEffects.UNLUCK);
        effectList.add(MobEffects.CONFUSION);
        effectList.add(MobEffects.BLINDNESS);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide()) {
            livingEntity.addEffect(new MobEffectInstance(effectList.get(random.nextInt(effectList.size())), 100 + random.nextInt(1 + (15 * amplifier)), 0));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 40 == 0;
    }
}
