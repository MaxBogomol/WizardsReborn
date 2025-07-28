package mod.maxbogomol.wizards_reborn.common.mobeffect;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.shader.postprocess.IrritationPostProcess;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IrritationMobEffect extends MobEffect {
    public static List<MobEffect> effectList = new ArrayList<>();
    public static List<MobEffect> playerEffectList = new ArrayList<>();
    public static Random random = new Random();

    public IrritationMobEffect() {
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

    @OnlyIn(Dist.CLIENT)
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!Minecraft.getInstance().isPaused()) {
                IrritationPostProcess.INSTANCE.tickEffect();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean hasEffect() {
        if (WizardsReborn.proxy.getPlayer() != null) {
            return WizardsReborn.proxy.getPlayer().hasEffect(WizardsRebornMobEffects.IRRITATION.get());
        }
        return false;
    }
}
