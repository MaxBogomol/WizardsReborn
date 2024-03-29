package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeLightStorageTileEntity extends LightCasingTileEntity {
    public CreativeLightStorageTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public CreativeLightStorageTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.CREATIVE_LIGHT_STORAGE_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public float getLightLensSize() {
        return 0.25f;
    }

    @Override
    public float getLightLensOffset() {
        return 0.25f;
    }

    @Override
    public int getLight() {
        return getMaxLight();
    }

    @Override
    public int getMaxLight() {
        return 10;
    }

    @Override
    public void setLight(int light) {

    }

    @Override
    public void addLight(int light) {

    }

    @Override
    public void removeLight(int light) {

    }
}
