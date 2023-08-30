package mod.maxbogomol.wizards_reborn.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class Item2DRenderer {
    public static final String[] HAND_MODEL_ITEMS = new String[]{"arcane_gold_scythe"};

    @SubscribeEvent
    public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> map = event.getModels();
        for (String item : HAND_MODEL_ITEMS) {
            ResourceLocation modelInventory = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, item), "inventory");
            ResourceLocation modelHand = new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, item + "_in_hand"), "inventory");

            BakedModel bakedModelDefault = map.get(modelInventory);
            BakedModel bakedModelHand = map.get(modelHand);
            BakedModel modelWrapper = new BakedModel() {
                @Override
                public List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, RandomSource pRandom) {
                    return bakedModelDefault.getQuads(pState, pDirection, pRandom);
                }

                @Override
                public boolean useAmbientOcclusion() {
                    return bakedModelDefault.useAmbientOcclusion();
                }

                @Override
                public boolean isGui3d() {
                    return bakedModelDefault.isGui3d();
                }

                @Override
                public boolean usesBlockLight() {
                    return bakedModelDefault.usesBlockLight();
                }

                @Override
                public boolean isCustomRenderer() {
                    return bakedModelDefault.isCustomRenderer();
                }

                @Override
                public TextureAtlasSprite getParticleIcon() {
                    return bakedModelDefault.getParticleIcon();
                }

                @Override
                public ItemOverrides getOverrides() {
                    return bakedModelDefault.getOverrides();
                }

                @Override
                public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
                    BakedModel modelToUse = bakedModelDefault;
                    if (transformType != ItemDisplayContext.GUI && transformType != ItemDisplayContext.GROUND){
                        modelToUse = bakedModelHand;
                    }
                    return ForgeHooksClient.handleCameraTransforms(poseStack, modelToUse, transformType, applyLeftHandTransform);
                }
            };
            map.put(modelInventory, modelWrapper);
        }
    }
}