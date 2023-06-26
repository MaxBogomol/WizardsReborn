package mod.maxbogomol.wizards_reborn.client.render.item;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class CustomModel implements IBakedModel {
    public CustomModel(IBakedModel i_baseModel, CustomModelOverrideList i_itemOverrideList)
    {
        baseModel = i_baseModel;
        itemOverrideList = i_itemOverrideList;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        return baseModel.getQuads(state, side, rand);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return itemOverrideList;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return baseModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return baseModel.isGui3d();
    }

    @Override
    public boolean isSideLit() {
        return baseModel.isSideLit();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return baseModel.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return baseModel.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return baseModel.getItemCameraTransforms();
    }

    private IBakedModel baseModel;
    private CustomModelOverrideList itemOverrideList;

}