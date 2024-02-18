package mod.maxbogomol.wizards_reborn.common.crystalritual;

import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualArea;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.awt.*;
import java.util.List;

public class RitualBreedingCrystalRitual extends CrystalRitual {
    public RitualBreedingCrystalRitual(String id) {
        super(id);
    }

    @Override
    public Color getColor() {
        return new Color(210, 129, 147);
    }

    @Override
    public int getMaxRitualCooldown(CrystalTileEntity crystal) {
        return 200;
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

        if (!level.isClientSide()) {
            if (getCooldown(crystal) > 0) {
                setCooldown(crystal, getCooldown(crystal) - 1);
            }
        }
    }

    @Override
    public boolean canEnd(CrystalTileEntity crystal) {
        Level level = crystal.getLevel();

        if (!level.isClientSide()) {
            return  (getCooldown(crystal) <= 0);
        }
        return false;
    }

    @Override
    public void end(CrystalTileEntity crystal) {
        Level level = crystal.getLevel();
        BlockPos blockPos = crystal.getBlockPos();

        if (!crystal.getLevel().isClientSide()) {
            CrystalRitualArea area = getArea(crystal);

            List<Animal> animals = level.getEntitiesOfClass(Animal.class, new AABB(blockPos.getX() - area.getSizeFrom().x(), blockPos.getY() - area.getSizeFrom().y(), blockPos.getZ() - area.getSizeFrom().z(), blockPos.getX() + area.getSizeTo().x(), blockPos.getY() + area.getSizeTo().y(), blockPos.getZ() + area.getSizeTo().z()));
            for (Animal animal : animals) {
                if (animal.canFallInLove()) {
                    animal.setInLove(null);
                    level.playSound(null, animal.getOnPos(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1f, 1f);
                }
            }
        }
    }
}
