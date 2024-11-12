package mod.maxbogomol.wizards_reborn.common.crystalritual;

import mod.maxbogomol.fluffy_fur.util.BlockUtil;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualArea;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.MagicSproutSpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArtificialFertilityCrystalRitual extends CrystalRitual {

    public ArtificialFertilityCrystalRitual(String id) {
        super(id);
    }

    @Override
    public Color getColor() {
        return new Color(138, 201, 123);
    }

    @Override
    public int getMaxRitualCooldown(CrystalBlockEntity crystal) {
        return 60;
    }

    @Override
    public List<CrystalType> getCrystalsList() {
        List<CrystalType> list = new ArrayList<>();
        list.add(WizardsRebornCrystals.EARTH);
        list.add(WizardsRebornCrystals.WATER);

        return list;
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
            if (getCooldown(crystal) <= 0) {
                CrystalRitualArea area = getArea(crystal);

                ArrayList<BlockPos> blockPosList = getBlockPosWithArea(level, blockPos, area, (p) -> {
                    if (level.getBlockState(p).getBlock() instanceof BonemealableBlock bonemealableBlock) {
                        return bonemealableBlock.isValidBonemealTarget(level, p, level.getBlockState(p), false);
                    }
                    return false;
                    }, true, true, 1);

                for (BlockPos pos : blockPosList) {
                    BlockState state = level.getBlockState(pos);
                    if (state.getBlock() instanceof CropBlock crop) {
                        if (!crop.isMaxAge(state)) {
                            crop.growCrops(level, pos, state);
                            WizardsRebornPacketHandler.sendToTracking(level, pos, new MagicSproutSpellPacket(pos.getCenter(), getColor()));
                            level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1f, 1f);
                            setMaxCooldown(crystal, getMaxRitualCooldownWithStat(crystal));
                            break;
                        }
                    } else {
                        if (BlockUtil.growCrop(level, pos)) {
                            WizardsRebornPacketHandler.sendToTracking(level, pos, new MagicSproutSpellPacket(pos.getCenter(), getColor()));
                            level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1f, 1f);
                            setMaxCooldown(crystal, getMaxRitualCooldownWithStat(crystal) * 2);
                            break;
                        }
                    }
                }
                setCooldown(crystal, getMaxCooldown(crystal));
            } else {
                setCooldown(crystal, getCooldown(crystal) - 1);
            }
        }
    }
}
