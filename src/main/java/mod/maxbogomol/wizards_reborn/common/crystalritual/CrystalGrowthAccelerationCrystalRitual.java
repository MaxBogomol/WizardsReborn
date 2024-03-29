package mod.maxbogomol.wizards_reborn.common.crystalritual;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.IGrowableCrystal;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.awt.*;

public class CrystalGrowthAccelerationCrystalRitual extends CrystalRitual {
    public CrystalGrowthAccelerationCrystalRitual(String id) {
        super(id);
    }

    @Override
    public Color getColor() {
        return new Color(86, 125, 182);
    }

    @Override
    public boolean hasArea(CrystalTileEntity crystal) {
        return false;
    }

    @Override
    public boolean hasLightRay(CrystalTileEntity crystal) {
        return true;
    }

    @Override
    public int getMaxRitualCooldown(CrystalTileEntity crystal) {
        return 400;
    }

    @Override
    public void start(CrystalTileEntity crystal) {
        if (!crystal.getLevel().isClientSide()) {
            setMaxCooldown(crystal, getMaxRitualCooldownWithStat(crystal));
            setCooldown(crystal, getMaxCooldown(crystal));
        }
    }

    @Override
    public void tick(CrystalTileEntity crystal) {
        Level level = crystal.getLevel();
        BlockPos blockPos = crystal.getBlockPos();

        if (!level.isClientSide()) {
            LightRayHitResult hitResult = crystal.setupLightRay();
            if (hitResult != null && hitResult.getTile() instanceof IGrowableCrystal growable) {
                int resonanceLevel = getStatLevel(crystal, WizardsReborn.RESONANCE_CRYSTAL_STAT);
                growable.setGrowingPower(5 + resonanceLevel);
                if (getCooldown(crystal) <= 0) {
                    growable.addGrowing();
                }
                PacketUtils.SUpdateTileEntityPacket(hitResult.getTile());
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
