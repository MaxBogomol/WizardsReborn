package mod.maxbogomol.wizards_reborn.common.crystalritual;

import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornLightTypes;
import net.minecraft.world.level.Level;

import java.awt.*;

public class CrystalGrowthAccelerationCrystalRitual extends CrystalRitual {

    public static Color color = new Color(86, 125, 182);

    public CrystalGrowthAccelerationCrystalRitual(String id) {
        super(id);
    }

    @Override
    public Color getColor() {
        return color;
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
    public void tick(CrystalBlockEntity crystal) {
        Level level = crystal.getLevel();

        if (!level.isClientSide()) {
            LightTypeStack oldStack = LightUtil.getStack(WizardsRebornLightTypes.CRYSTAL_GROWTH_ACCELERATION, crystal.getLightTypes());
            if (oldStack == null) {
                LightTypeStack stack = new LightTypeStack(WizardsRebornLightTypes.CRYSTAL_GROWTH_ACCELERATION);
                stack.setTick(crystal.getLight());
                crystal.addLightType(stack);
            } else {
                oldStack.setTick(crystal.getLight());
            }
        }

        //if (!level.isClientSide()) {
            /*LightRayHitResult hitResult = crystal.setupLightRay();
            if (hitResult != null && hitResult.getBlockEntity() instanceof IGrowableCrystal growable) {
                int resonanceLevel = getStatLevel(crystal, WizardsRebornCrystals.RESONANCE);
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
            }*/
        //}
    }
}
