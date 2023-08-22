package mod.maxbogomol.wizards_reborn.client.render.item;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomFinalisedModel implements BakedModel {

    public CustomFinalisedModel(BakedModel i_parentModel, BakedModel i_subModel)
    {
        parentModel = i_parentModel;
        subModel = i_subModel;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState pState, Direction pDirection, RandomSource pRandom) {
        if (pDirection != null) {
            return parentModel.getQuads(pState, pDirection, pRandom);
        }

        List<BakedQuad> combinedQuadsList = new ArrayList(parentModel.getQuads(pState, pDirection, pRandom));
        combinedQuadsList.addAll(subModel.getQuads(pState, pDirection, pRandom));
        return combinedQuadsList;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return parentModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return parentModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return parentModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return parentModel.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return parentModel.getTransforms();
    }

    @Override
    public ItemOverrides getOverrides() {
        throw new UnsupportedOperationException("The finalised model does not have an override list.");
    }
    private BakedModel parentModel;
    private BakedModel subModel;
}