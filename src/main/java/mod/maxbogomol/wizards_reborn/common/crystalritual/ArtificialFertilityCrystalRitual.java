package mod.maxbogomol.wizards_reborn.common.crystalritual;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualArea;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.MagicSproutSpellEffectPacket;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
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
    public int getMaxRitualCooldown(CrystalTileEntity crystal) {
        return 60;
    }

    @Override
    public List<CrystalType> getCrystalsList() {
        List<CrystalType> list = new ArrayList<>();
        list.add(WizardsReborn.EARTH_CRYSTAL_TYPE);
        list.add(WizardsReborn.WATER_CRYSTAL_TYPE);

        return list;
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

                            Color color = getColor();
                            float r = color.getRed() / 255f;
                            float g = color.getGreen() / 255f;
                            float b = color.getBlue() / 255f;

                            PacketHandler.sendToTracking(level, pos, new MagicSproutSpellEffectPacket(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, r, g, b));
                            level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1f, 1f);
                            setMaxCooldown(crystal, getMaxRitualCooldownWithStat(crystal));
                            break;
                        }
                    } else {
                        if (growCrop(level, pos)) {
                            Color color = getColor();
                            float r = color.getRed() / 255f;
                            float g = color.getGreen() / 255f;
                            float b = color.getBlue() / 255f;

                            PacketHandler.sendToTracking(level, pos, new MagicSproutSpellEffectPacket(pos.getX() + 0.5F, pos.above().getY() + 0.5F, pos.getZ() + 0.5F, r, g, b));
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

    public boolean growCrop(Level level, BlockPos blockPos) {
        if (BoneMealItem.growCrop(ItemStack.EMPTY, level, blockPos)) {
            return true;
        } else {
            BlockState blockstate = level.getBlockState(blockPos);
            boolean flag = blockstate.isFaceSturdy(level, blockPos, Direction.UP);
            if (flag && BoneMealItem.growWaterPlant(ItemStack.EMPTY, level, blockPos.relative(Direction.UP), Direction.UP)) {
                return true;
            }
        }

        return false;
    }
}
