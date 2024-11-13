package mod.maxbogomol.wizards_reborn.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IrritationEffect extends MobEffect {
    public static List<MobEffect> effectList = new ArrayList<>();
    public static List<MobEffect> playerEffectList = new ArrayList<>();
    public static Random random = new Random();

    public IrritationEffect() {
        super(MobEffectCategory.HARMFUL, 0xff77a7);
        effectList.add(MobEffects.POISON);
        effectList.add(MobEffects.WEAKNESS);
        effectList.add(MobEffects.MOVEMENT_SLOWDOWN);
        playerEffectList.add(MobEffects.DARKNESS);
        playerEffectList.add(MobEffects.DIG_SLOWDOWN);
        playerEffectList.add(MobEffects.CONFUSION);
        playerEffectList.add(MobEffects.BLINDNESS);
    }

    public static void effectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide()) {
            List<MobEffect> list = new ArrayList<>(effectList);
            if (livingEntity instanceof Player) {
                list.addAll(playerEffectList);
            }
            MobEffect newEffect = list.get(random.nextInt(list.size()));
            int duration = 200 + random.nextInt(1 + (50 * amplifier));
            livingEntity.addEffect(new MobEffectInstance(newEffect, duration, 0));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 40 == 0;
    }
}
