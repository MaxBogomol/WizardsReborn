package mod.maxbogomol.wizards_reborn.common.lighttype;

import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightType;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.lightray.LightRayBurstPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class FireLightType extends LightType {

    public FireLightType(String id) {
        super(id);
    }

    public Color getColor() {
        return WizardsRebornCrystals.fireCrystalColor;
    }

    @Override
    public boolean hitTick(LightTypeStack stack, LightRayHitResult hitResult) {
        for (LightRayHitResult.EntityContext context : hitResult.getEntities()) {
            if (context.getEntity() instanceof LivingEntity livingEntity) {
                boolean burst = false;
                if (livingEntity.tickCount % 10 == 0) {
                    int invulnerableTime = livingEntity.invulnerableTime;
                    livingEntity.invulnerableTime = 0;
                    livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                    float damage = stack.isConcentrated() ? 1f : 0.5f;
                    livingEntity.hurt(DamageHandler.create(livingEntity.level(), DamageTypes.ON_FIRE), damage);
                    livingEntity.invulnerableTime = invulnerableTime;
                    int fire = livingEntity.getRemainingFireTicks() + (stack.isConcentrated() ? 60 : 30);
                    if (fire > 200) fire = 200;
                    livingEntity.setSecondsOnFire(fire);
                    livingEntity.setTicksFrozen(0);
                    burst = true;
                }
                if (livingEntity.getRemainingFireTicks() <= 0) {
                    int fire = livingEntity.getRemainingFireTicks() + (stack.isConcentrated() ? 60 : 30);
                    if (fire > 200) fire = 200;
                    livingEntity.setSecondsOnFire(fire);
                    livingEntity.setTicksFrozen(0);
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
