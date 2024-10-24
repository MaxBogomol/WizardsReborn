package mod.maxbogomol.wizards_reborn.common.lighttype;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightType;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.lightray.LightRayBurstPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class WaterLightType extends LightType {

    public WaterLightType(String id) {
        super(id);
    }

    public Color getColor() {
        return WizardsRebornCrystals.waterCrystalColor;
    }

    @Override
    public boolean hitTick(LightTypeStack stack, LightRayHitResult hitResult) {
        for (LightRayHitResult.EntityContext context : hitResult.getEntities()) {
            if (context.getEntity() instanceof LivingEntity livingEntity) {
                boolean burst = false;
                if (livingEntity.tickCount % 10 == 0) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, stack.isConcentrated() ? 200 : 100, 0));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, stack.isConcentrated() ? 200 : 100, 0));
                    burst = true;
                }
                if (livingEntity.getRemainingFireTicks() > 0) {
                    livingEntity.clearFire();
                    burst = true;
                }
                if (burst) {
                    Vec3 posHit = context.getPosHit();
                    WizardsRebornPacketHandler.sendToTracking(livingEntity.level(), livingEntity.getOnPos(), new LightRayBurstPacket(posHit, getColor()));
                    livingEntity.level().playSound(WizardsReborn.proxy.getPlayer(), posHit.x(), posHit.y(), posHit.z(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.05f, 2f);
                }
            }
        }
        return false;
    }
}
