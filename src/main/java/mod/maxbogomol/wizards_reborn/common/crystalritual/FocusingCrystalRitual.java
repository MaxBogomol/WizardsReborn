package mod.maxbogomol.wizards_reborn.common.crystalritual;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.light.LightType;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornLightTypes;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FocusingCrystalRitual extends CrystalRitual {

    public static Map<CrystalType, LightType> lightRayTypes = new HashMap<>();

    public FocusingCrystalRitual(String id) {
        super(id);
        lightRayTypes.put(WizardsRebornCrystals.EARTH, WizardsRebornLightTypes.EARTH);
        lightRayTypes.put(WizardsRebornCrystals.WATER, WizardsRebornLightTypes.WATER);
        lightRayTypes.put(WizardsRebornCrystals.AIR, WizardsRebornLightTypes.AIR);
        lightRayTypes.put(WizardsRebornCrystals.FIRE, WizardsRebornLightTypes.FIRE);
        lightRayTypes.put(WizardsRebornCrystals.VOID, WizardsRebornLightTypes.VOID);
    }

    @Override
    public Color getColor() {
        return LightUtil.standardLightRayColor;
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
            CrystalType crystalType = getCrystalType(getCrystalItem(crystal));
            LightType type = lightRayTypes.get(crystalType);
            if (type != null) {
                LightTypeStack oldStack = LightUtil.getStack(type, crystal.getLightTypes());
                if (oldStack == null) {
                    LightTypeStack stack = new LightTypeStack(type);
                    stack.setTick(crystal.getLight());
                    crystal.addLightType(stack);
                } else {
                    oldStack.setTick(crystal.getLight());
                }
            }
        }
    }
}
