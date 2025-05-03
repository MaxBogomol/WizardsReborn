package mod.maxbogomol.wizards_reborn.client.model.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.resources.model.ModelBakery.ModelBakerImpl;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

import java.util.ArrayList;
import java.util.List;

public class PipeModel implements BakedModel {
    private final String name;
    private final BakedModel centerModel;
    private BakedModel[] connectionModel;
    private BakedModel[] endModel;
    public static final List<BakedQuad> EMPTY = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public final List<BakedQuad>[] QUAD_CACHE = new List[729];

    public String modId;

    public PipeModel(BakedModel centerModel, String name) {
        this.name = name;
        this.centerModel = centerModel;
        this.modId = WizardsReborn.MOD_ID;
    }

    public PipeModel(BakedModel centerModel, String name, String modId) {
        this.name = name;
        this.centerModel = centerModel;
        this.modId = modId;
    }

    public void init(ModelManager manager) {
        connectionModel = getRotatedModels(manager.getModelBakery(), name + "_connection", modId);
        endModel = getRotatedModels(manager.getModelBakery(), name + "_end", modId);
    }

    public static BakedModel[] getRotatedModels(ModelBakery bakery, String name, String modId) {
        ResourceLocation location = new ResourceLocation(modId, "block/" + name);
        ResourceLocation location2 = new ResourceLocation(modId, "block/" + name + "_alt");

        ModelBakerImpl bakerImpl = bakery.new ModelBakerImpl((modelLoc, material) -> material.sprite(), location);

        UnbakedModel model = bakery.getModel(location);
        UnbakedModel model2 = bakery.getModel(location2);
        BakedModel[] models = {
                model.bake(bakerImpl, Material::sprite, BlockModelRotation.X0_Y0, location),
                model2.bake(bakerImpl, Material::sprite, BlockModelRotation.X180_Y0, location2),
                model.bake(bakerImpl, Material::sprite, BlockModelRotation.X90_Y180, location),
                model2.bake(bakerImpl, Material::sprite, BlockModelRotation.X90_Y0, location2),
                model.bake(bakerImpl, Material::sprite, BlockModelRotation.X90_Y90, location),
                model2.bake(bakerImpl, Material::sprite, BlockModelRotation.X90_Y270, location2)
        };
        return models;
    }

    public static int getCacheIndex(int[] data) {
        return (((((data[0] * 3 + data[1]) * 3 + data[2]) * 3 + data[3]) * 3 + data[4]) * 3) + data[5];
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType renderType) {
        if (side != null) return EMPTY;
        int[] sides = data.get(PipeBaseBlockEntity.DATA_TYPE);

        if (sides != null) {
            List<BakedQuad> quads = QUAD_CACHE[getCacheIndex(sides)];
            if (quads != null) return quads;

            quads = new ArrayList<>();
            quads.addAll(centerModel.getQuads(state, side, rand, data, renderType));
            if (quads.isEmpty()) return quads;
            for (int i = 0; i < sides.length; i++) {
                if (sides[i] == 1) {
                    quads.addAll(connectionModel[i].getQuads(state, side, rand, data, renderType));
                } else if (sides[i] == 2) {
                    quads.addAll(endModel[i].getQuads(state, side, rand, data, renderType));
                }
            }
            if (!quads.isEmpty()) QUAD_CACHE[getCacheIndex(sides)] = new ArrayList<>(quads);
            return quads;
        }
        return centerModel.getQuads(state, side, rand, data, renderType);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
        return centerModel.getQuads(state, side, rand);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return centerModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return centerModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return centerModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return centerModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return centerModel.getParticleIcon();
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}