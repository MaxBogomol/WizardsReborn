package mod.maxbogomol.wizards_reborn.client.render.item;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;

public class CustomModel implements BakedModel {
    public CustomModel(BakedModel i_baseModel, CustomModelOverrideList i_itemOverrideList)
    {
        baseModel = i_baseModel;
        itemOverrideList = i_itemOverrideList;
    }

    @Override
    public ItemOverrides getOverrides() {
        return itemOverrideList;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState pState, Direction pDirection, RandomSource pRandom) {
        return baseModel.getQuads(pState, pDirection, pRandom);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return baseModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return baseModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return baseModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return baseModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return baseModel.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return baseModel.getTransforms();
    }

    private BakedModel baseModel;
    private CustomModelOverrideList itemOverrideList;

}