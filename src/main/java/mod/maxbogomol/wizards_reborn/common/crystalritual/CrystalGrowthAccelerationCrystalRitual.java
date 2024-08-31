package mod.maxbogomol.wizards_reborn.common.crystalritual;

import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.IGrowableCrystal;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
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
    public boolean hasArea(CrystalBlockEntity crystal) {
        return false;
    }

    @Override
    public boolean hasLightRay(CrystalBlockEntity crystal) {
        return true;
    }

    @Override
    public int getMaxRitualCooldown(CrystalBlockEntity crystal) {
        return 400;
    }

    @Override
    public void start(CrystalBlockEntity crystal) {
        if (!crystal.getLevel().isClientSide()) {
            setMaxCooldown(crystal, getMaxRitualCooldownWithStat(crystal));
            setCooldown(crystal, getMaxCooldown(crystal));
        }
    }

    @Override
    public void tick(CrystalBlockEntity crystal) {
        Level level = crystal.getLevel();
        BlockPos blockPos = crystal.getBlockPos();

        if (!level.isClientSide()) {
            LightRayHitResult hitResult = crystal.setupLightRay();
            if (hitResult != null && hitResult.getBlockEntity() instanceof IGrowableCrystal growable) {
                int resonanceLevel = getStatLevel(crystal, WizardsReborn.RESONANCE_CRYSTAL_STAT);
                growable.setGrowingPower(5 + resonanceLevel);
                if (getCooldown(crystal) <= 0) {
                    growable.addGrowing();
                }
                BlockEntityUpdate.packet(hitResult.getBlockEntity());
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
