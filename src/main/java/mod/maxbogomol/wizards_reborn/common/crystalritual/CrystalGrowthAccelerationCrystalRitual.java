package mod.maxbogomol.wizards_reborn.common.crystalritual;

import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornLightTypes;
import net.minecraft.nbt.CompoundTag;
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
            int resonanceLevel = getStatLevel(crystal, WizardsRebornCrystals.RESONANCE);
            LightTypeStack oldStack = LightUtil.getStack(WizardsRebornLightTypes.CRYSTAL_GROWTH_ACCELERATION, crystal.getLightTypes());
            if (oldStack == null) {
                LightTypeStack stack = new LightTypeStack(WizardsRebornLightTypes.CRYSTAL_GROWTH_ACCELERATION);
                stack.setTick(crystal.getLight());
                crystal.addLightType(stack);
                CompoundTag oldTag = stack.getTag();
                if (resonanceLevel > oldTag.getInt("resonance")) {
                    oldTag.putInt("resonance", resonanceLevel);
                }
            } else {
                oldStack.setTick(crystal.getLight());
                CompoundTag oldTag = oldStack.getTag();
                if (resonanceLevel > oldTag.getInt("resonance")) {
                    oldTag.putInt("resonance", resonanceLevel);
                }
            }
        }
    }
}
