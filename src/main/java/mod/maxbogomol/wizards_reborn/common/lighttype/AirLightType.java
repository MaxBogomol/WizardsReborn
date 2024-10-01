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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class AirLightType extends LightType {

    public AirLightType(String id) {
        super(id);
    }

    public Color getColor() {
        return WizardsRebornCrystals.airCrystalColor;
    }

    @Override
    public boolean hitTick(LightTypeStack stack, LightRayHitResult hitResult) {
        for (LightRayHitResult.EntityContext context : hitResult.getEntities()) {
            Entity entity = context.getEntity();

            Vec3 posHit = context.getPosHit();
            Vec3 pos = entity.position().add(0, entity.getBbWidth() / 2f, 0);

            double dX = posHit.x() - pos.x();
            double dY = posHit.y() - pos.y();
            double dZ = posHit.z() - pos.z();

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float speed = stack.isConcentrated() ? 1.5f : 0.75f;
            double x = Math.sin(pitch) * Math.cos(yaw) * speed;
            double y = Math.cos(pitch) * speed;
            double z = Math.sin(pitch) * Math.sin(yaw) * speed;

            entity.push(x, y / 2f, z);
            entity.hurtMarked = true;
            PacketHandler.sendToTracking(entity.level(), entity.getOnPos(), new LightRayBurstPacket(posHit, getColor()));
            entity.level().playSound(WizardsReborn.proxy.getPlayer(), posHit.x(), posHit.y(), posHit.z(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.05f, 2f);
        }
        return false;
    }
}
