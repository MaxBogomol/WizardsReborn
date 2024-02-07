package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeWissenStorageTileEntity extends WissenCellTileEntity {
    public CreativeWissenStorageTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public CreativeWissenStorageTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.CREATIVE_WISSEN_STORAGE_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        wissen = getMaxWissen();
        super.tick();
    }

    @Override
    public int getWissenPerReceive() {
        return 1000000;
    }

    @Override
    public int getWissen() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxWissen() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setWissen(int wissen) {

    }

    @Override
    public void addWissen(int wissen) {

    }

    @Override
    public void removeWissen(int wissen) {

    }
}
