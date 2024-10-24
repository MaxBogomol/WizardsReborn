package mod.maxbogomol.wizards_reborn.common.lighttype;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightType;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.lightray.LightRayBurstPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamage;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class VoidLightType extends LightType {

    public VoidLightType(String id) {
        super(id);
    }

    public Color getColor() {
        return WizardsRebornCrystals.voidCrystalColor;
    }

    @Override
    public boolean hitTick(LightTypeStack stack, LightRayHitResult hitResult) {
        for (LightRayHitResult.EntityContext context : hitResult.getEntities()) {
            if (context.getEntity() instanceof LivingEntity livingEntity) {
                if (livingEntity.tickCount % 10 == 0) {
                    livingEntity.invulnerableTime = 0;
                    livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                    float damage = stack.isConcentrated() ? 2f : 1f;
                    livingEntity.hurt(new DamageSource(WizardsRebornDamage.create(livingEntity.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder()), damage);
                    Vec3 posHit = context.getPosHit();
                    WizardsRebornPacketHandler.sendToTracking(livingEntity.level(), livingEntity.getOnPos(), new LightRayBurstPacket(posHit, getColor()));
                    livingEntity.level().playSound(WizardsReborn.proxy.getPlayer(), posHit.x(), posHit.y(), posHit.z(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.05f, 2f);
                }
            }
        }
        return false;
    }
}
