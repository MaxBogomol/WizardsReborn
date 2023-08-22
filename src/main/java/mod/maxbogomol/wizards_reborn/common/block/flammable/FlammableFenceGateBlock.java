package mod.maxbogomol.wizards_reborn.common.block.flammable;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.WoodType;

public class FlammableFenceGateBlock extends FenceGateBlock {
    public int fireSpeed;
    public int flammability;

    public FlammableFenceGateBlock(Properties pProperties, WoodType pType, int fireSpeed, int flammability) {
        super(pProperties, pType);
        this.fireSpeed = fireSpeed;
        this.flammability = flammability;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return flammability;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return fireSpeed;
    }
}
