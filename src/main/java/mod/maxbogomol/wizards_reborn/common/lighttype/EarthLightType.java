package mod.maxbogomol.wizards_reborn.common.lighttype;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightType;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.lightray.LightRayBurstPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class EarthLightType extends LightType {

    public EarthLightType(String id) {
        super(id);
    }

    public Color getColor() {
        return WizardsRebornCrystals.earthCrystalColor;
    }

    @Override
    public boolean hitTick(LightTypeStack stack, LightRayHitResult hitResult) {
        for (LightRayHitResult.EntityContext context : hitResult.getEntities()) {
            if (context.getEntity() instanceof LivingEntity livingEntity) {
                if (livingEntity.tickCount % 5 == 0) {
                    livingEntity.heal(stack.isConcentrated() ? 0.5f : 0.25f);
                    Vec3 posHit = context.getPosHit();
                    PacketHandler.sendToTracking(livingEntity.level(), livingEntity.getOnPos(), new LightRayBurstPacket(posHit, getColor()));
                    livingEntity.level().playSound(WizardsReborn.proxy.getPlayer(), posHit.x(), posHit.y(), posHit.z(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.05f, 2f);
                }
            }
        }
        return false;
    }
}
