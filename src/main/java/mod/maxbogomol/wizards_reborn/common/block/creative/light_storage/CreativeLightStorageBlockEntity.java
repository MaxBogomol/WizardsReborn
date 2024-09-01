package mod.maxbogomol.wizards_reborn.common.block.creative.light_storage;

import mod.maxbogomol.wizards_reborn.common.block.casing.light.LightCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeLightStorageBlockEntity extends LightCasingBlockEntity {

    public CreativeLightStorageBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public CreativeLightStorageBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.CREATIVE_LIGHT_STORAGE.get(), pos, state);
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
