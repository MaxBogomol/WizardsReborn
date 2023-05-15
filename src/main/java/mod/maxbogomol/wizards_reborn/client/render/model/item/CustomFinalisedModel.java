package mod.maxbogomol.wizards_reborn.client.render.model.item;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomFinalisedModel implements IBakedModel {

    public CustomFinalisedModel(IBakedModel i_parentModel, IBakedModel i_subModel)
    {
        parentModel = i_parentModel;
        subModel = i_subModel;
    }
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        if (side != null) {
            return parentModel.getQuads(state, side, rand);
        }

        List<BakedQuad> combinedQuadsList = new ArrayList(parentModel.getQuads(state, side, rand));
        combinedQuadsList.addAll(subModel.getQuads(state, side, rand));
        return combinedQuadsList;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return parentModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return parentModel.isGui3d();
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return parentModel.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return parentModel.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return parentModel.getItemCameraTransforms();
    }

    @Override
    public ItemOverrideList getOverrides() {
        throw new UnsupportedOperationException("The finalised model does not have an override list.");
    }
    private IBakedModel parentModel;
    private IBakedModel subModel;
}