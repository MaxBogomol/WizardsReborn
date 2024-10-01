package mod.maxbogomol.wizards_reborn.common.lighttype;

import mod.maxbogomol.wizards_reborn.api.crystalritual.IGrowableCrystal;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightType;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.common.crystalritual.CrystalGrowthAccelerationCrystalRitual;
import net.minecraft.nbt.CompoundTag;

import java.awt.*;

public class CrystalGrowthAccelerationLightType extends LightType {

    public CrystalGrowthAccelerationLightType(String id) {
        super(id);
    }

    public Color getColor() {
        return CrystalGrowthAccelerationCrystalRitual.color;
    }

    @Override
    public boolean tick(LightTypeStack stack) {
        CompoundTag tag = stack.getTag();
        if (!tag.contains("tick")) {
            tag.putInt("tick", 0);
        }
        if (tag.getInt("tick") > 300) {
            tag.putInt("tick", 0);
        }
        tag.putInt("tick", tag.getInt("tick") + 1);
        return true;
    }

    @Override
    public boolean hitTick(LightTypeStack stack, LightRayHitResult hitResult) {
        if (hitResult != null && hitResult.getBlockEntity() instanceof IGrowableCrystal growable) {
            CompoundTag tag = stack.getTag();
            if (!tag.contains("tick")) {
                tag.putInt("tick", 0);
            }
            if (!tag.contains("resonance")) {
                tag.putInt("resonance", 0);
            }
            if (tag.getInt("tick") > 300) {
                growable.addGrowing();
            }
            int resonanceLevel = tag.getInt("resonance");
            growable.setGrowingPower(5 + resonanceLevel);
            return true;
        }
        return false;
    }

    @Override
    public boolean transferToNew(LightTypeStack oldStack, LightTypeStack newStack) {
        CompoundTag oldTag = oldStack.getTag();
        CompoundTag newTag = newStack.getTag();
        if (!oldTag.contains("resonance")) {
            oldTag.putInt("resonance", 0);
        }
        if (!newTag.contains("tick")) {
            newTag.putInt("resonance", 0);
        }
        if (newTag.getInt("resonance") < oldTag.getInt("resonance")) {
            newTag.putInt("resonance", oldTag.getInt("resonance"));
        }
        return false;
    }
}
