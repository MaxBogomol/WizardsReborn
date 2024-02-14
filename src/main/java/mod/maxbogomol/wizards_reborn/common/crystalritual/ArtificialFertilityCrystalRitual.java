package mod.maxbogomol.wizards_reborn.common.crystalritual;

import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualArea;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.MagicSproutSpellEffectPacket;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public class ArtificialFertilityCrystalRitual extends CrystalRitual {
    public ArtificialFertilityCrystalRitual(String id) {
        super(id);
    }

    @Override
    public Color getColor() {
        return new Color(138, 201, 123);
    }

    @Override
    public int getMaxCooldown(CrystalTileEntity crystal) {
        return 60;
    }

    @Override
    public void start(CrystalTileEntity crystal) {
        if (!crystal.getLevel().isClientSide()) {
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
                boolean grow = false;

                for (double x = -area.getSizeFrom().x(); x <= area.getSizeTo().x(); x++) {
                    for (double y = -area.getSizeFrom().y(); y <= area.getSizeTo().y(); y++) {
                        for (double z = -area.getSizeFrom().z(); z <= area.getSizeTo().z(); z++) {
                            BlockPos pos = new BlockPos(new BlockPos(blockPos.getX() + Mth.floor(x), blockPos.getY() + Mth.floor(y), blockPos.getZ() + Mth.floor(z)));
                            if (level.isLoaded(pos)) {
                                BlockState state = level.getBlockState(pos);
                                if (state.getBlock() instanceof CropBlock crop) {
                                    if (!crop.isMaxAge(state)) {
                                        crop.growCrops(level, pos, state);

                                        Color color = getColor();
                                        float r = color.getRed() / 255f;
                                        float g = color.getGreen() / 255f;
                                        float b = color.getBlue() / 255f;

                                        PacketHandler.sendToTracking(level, pos, new MagicSproutSpellEffectPacket((float) (blockPos.getX() + x + 0.5F), (float) (blockPos.getY() + y + 0.5F), (float) (blockPos.getZ() + z + 0.5F), r, g, b));
                                        level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1f, 1f);
                                        grow = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (grow) break;
                    }
                    if (grow) break;
                }
                setCooldown(crystal, getMaxCooldown(crystal));
            } else {
                setCooldown(crystal, getCooldown(crystal) - 1);
            }
        }
    }

    public InteractionResult growCrop(ItemStack stack, UseOnContext context, BlockPos blockPos) {
        if (BoneMealItem.growCrop(ItemStack.EMPTY, context.getLevel(), blockPos)) {
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
