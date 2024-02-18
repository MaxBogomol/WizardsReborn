package mod.maxbogomol.wizards_reborn.common.crystalritual;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.IGrowableCrystal;
import mod.maxbogomol.wizards_reborn.api.light.ILightTileEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightUtils;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class CrystalGrowthAccelerationCrystalRitual extends CrystalRitual {
    public CrystalGrowthAccelerationCrystalRitual(String id) {
        super(id);
    }

    @Override
    public Color getColor() {
        return new Color(138, 201, 123);
    }

    @Override
    public int getMaxRitualCooldown(CrystalTileEntity crystal) {
        return 300;
    }

    @Override
    public void start(CrystalTileEntity crystal) {
        if (!crystal.getLevel().isClientSide()) {
            setMaxCooldown(crystal, 1);
            setCooldown(crystal, 1);
        }
    }

    @Override
    public void tick(CrystalTileEntity crystal) {
        Level level = crystal.getLevel();
        BlockPos blockPos = crystal.getBlockPos();

        if (!level.isClientSide()) {
            if (crystal.isToBlock) {
                BlockPos pos = new BlockPos(crystal.blockToX, crystal.blockToY, crystal.blockToZ);
                if (level.isLoaded(pos)) {
                    BlockEntity tileentity = level.getBlockEntity(pos);
                    if (tileentity instanceof ILightTileEntity lightTileEntity) {
                        Vec3 from = LightUtils.getLightLensPos(crystal.getBlockPos(), crystal.getLightLensPos());
                        Vec3 to = LightUtils.getLightLensPos(pos, lightTileEntity.getLightLensPos());

                        double dX = to.x() - from.x();
                        double dY = to.y() - from.y();
                        double dZ = to.z() - from.z();

                        double yaw = Math.atan2(dZ, dX);
                        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                        float rayDistance = 0.3f;

                        double X = Math.sin(pitch) * Math.cos(yaw) * rayDistance;
                        double Y = Math.cos(pitch) * rayDistance;
                        double Z = Math.sin(pitch) * Math.sin(yaw) * rayDistance;

                        from = from.add(-X, -Y, -Z);

                        LightRayHitResult hitResult = LightUtils.getLightRayHitResult(level, crystal.getBlockPos(), from, to, 25);
                        BlockEntity hitTile = hitResult.getTile();
                        LightUtils.transferLight(crystal, hitTile);

                        if (hitTile instanceof IGrowableCrystal growable) {
                            int resonanceLevel = getStatLevel(crystal, WizardsReborn.RESONANCE_CRYSTAL_STAT);
                            growable.setGrowingPower(5 + resonanceLevel);
                            if (getCooldown(crystal) <= 0) {
                                growable.addGrowing();
                            }
                            PacketUtils.SUpdateTileEntityPacket(hitTile);
                        }
                    }
                }
            }
            if (getCooldown(crystal) <= 0) {
                setMaxCooldown(crystal, getMaxRitualCooldownWithStat(crystal));
                setCooldown(crystal, getMaxCooldown(crystal));
            } else {
                setCooldown(crystal, getCooldown(crystal) - 1);
            }
        }
    }
}
