package mod.maxbogomol.wizards_reborn.client.render.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.entity.CustomBoatEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import org.joml.Quaternionf;

import java.util.Map;
import java.util.stream.Stream;

public class CustomBoatRenderer extends EntityRenderer<CustomBoatEntity> {

    private final Map<CustomBoatEntity.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public CustomBoatRenderer(EntityRendererProvider.Context context, boolean chest) {
        super(context);
        this.shadowRadius = 0.8F;
        this.boatResources = Stream.of(CustomBoatEntity.Type.values()).collect(ImmutableMap.toImmutableMap(type -> type, type -> Pair.of(new ResourceLocation(WizardsReborn.MOD_ID, getTextureLocation(type, chest)), this.createBoatModel(context, type, chest))));
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, CustomBoatEntity.Type type, boolean chest) {
        ModelLayerLocation modellayerlocation = chest ? createChestBoatModelName(type) : createBoatModelName(type);
        ModelPart modelpart = context.bakeLayer(modellayerlocation);
        if (type == CustomBoatEntity.Type.CORK_BAMBOO) {
            return chest ? new ChestRaftModel(modelpart) : new RaftModel(modelpart);
        } else {
            return chest ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
        }
    }

    private static ModelLayerLocation createLocation(String path) {
        return new ModelLayerLocation(new ResourceLocation(WizardsReborn.MOD_ID, path), "main");
    }

    public static ModelLayerLocation createBoatModelName(CustomBoatEntity.Type type) {
        return createLocation("boat/" + type.getName());
    }

    public static ModelLayerLocation createChestBoatModelName(CustomBoatEntity.Type type) {
        return createLocation("chest_boat/" + type.getName());
    }

    private static String getTextureLocation(CustomBoatEntity.Type type, boolean chest) {
        return chest ? "textures/entity/chest_boat/" + type.getName() + ".png" : "textures/entity/boat/" + type.getName() + ".png";
    }

    @Override
    public void render(CustomBoatEntity boat, float boatYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        stack.pushPose();
        stack.translate(0.0F, 0.375F, 0.0F);
        stack.mulPose(Axis.YP.rotationDegrees(180.0F - boatYaw));
        float f = (float)boat.getHurtTime() - partialTicks;
        float f1 = boat.getDamage() - partialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            stack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)boat.getHurtDir()));
        }

        float f2 = boat.getBubbleAngle(partialTicks);
        if (!Mth.equal(f2, 0.0F)) {
            stack.mulPose((new Quaternionf()).setAngleAxis(boat.getBubbleAngle(partialTicks) * ((float)Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, ListModel<Boat>> pair = this.getModelWithLocation(boat);
        ResourceLocation resourcelocation = pair.getFirst();
        ListModel<Boat> model = pair.getSecond();
        stack.scale(-1.0F, -1.0F, 1.0F);
        stack.mulPose(Axis.YP.rotationDegrees(90.0F));
        model.setupAnim(boat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = buffer.getBuffer(model.renderType(resourcelocation));
        model.renderToBuffer(stack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!boat.isUnderWater()) {
            VertexConsumer vertexconsumer1 = buffer.getBuffer(RenderType.waterMask());
            if (model instanceof WaterPatchModel waterpatchmodel) {
                waterpatchmodel.waterPatch().render(stack, vertexconsumer1, light, OverlayTexture.NO_OVERLAY);
            }
        }

        stack.popPose();
        super.render(boat, boatYaw, partialTicks, stack, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(CustomBoatEntity boat) {
        return this.getModelWithLocation(boat).getFirst();
    }

    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(CustomBoatEntity boat) {
        return this.boatResources.get(boat.getCustomBoatEntityType());
    }
}

