package mod.maxbogomol.wizards_reborn.api.light;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;

public class LightUtil {

    public static Color standardLightRayColor = new Color(0.886f, 0.811f, 0.549f);
    public static Color standardLensColor = new Color(0.564f, 0.682f, 0.705f);

    public static Vec3 getLightLensPos(BlockPos pos, Vec3 lensPos) {
        return new Vec3(pos.getX() + lensPos.x(), pos.getY() + lensPos.y(), pos.getZ() + lensPos.z());
    }

    public static LightRayHitResult getLightRayHitResult(Level level, BlockPos startPos, Vec3 from, Vec3 to, float rayDistance) {
        double dX = to.x() - from.x();
        double dY = to.y() - from.y();
        double dZ = to.z() - from.z();

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        double X = Math.sin(pitch) * Math.cos(yaw) * rayDistance;
        double Y = Math.cos(pitch) * rayDistance;
        double Z = Math.sin(pitch) * Math.sin(yaw) * rayDistance;

        Vec3 newTo = new Vec3(-X, -Y, -Z).add(to);

        HitResult hitresult = level.clip(new ClipContext(from, newTo, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, null));
        Vec3 end = hitresult.getLocation();

        float distance = (float) Math.sqrt(Math.pow(from.x() - end.x, 2) + Math.pow(from.y() - end.y, 2) + Math.pow(from.z() - end.z, 2));
        BlockEntity hitBlockEntity = null;

        BlockPos blockPosHit = BlockPos.containing(end.x(), end.y(), end.z());
        if (startPos.getX() != blockPosHit.getX() || startPos.getY() != blockPosHit.getY() || startPos.getZ() != blockPosHit.getZ()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPosHit);
            if (blockEntity instanceof ILightBlockEntity lightBlockEntityHit && lightBlockEntityHit.getLightLensSize() == 0) {
                distance = (float) Math.sqrt(Math.pow(from.x() - end.x, 2) + Math.pow(from.y() - end.y, 2) + Math.pow(from.z() - end.z, 2));
                hitBlockEntity = blockEntity;
            }
        }

        for (int i = 0; i < distance * 16; i++) {
            float v = (i / (distance * 16)) * distance;
            X = Math.sin(pitch) * Math.cos(yaw) * v;
            Y = Math.cos(pitch) * v;
            Z = Math.sin(pitch) * Math.sin(yaw) * v;

            Vec3 posHit = new Vec3(-X, -Y, -Z).add(from);
            blockPosHit = BlockPos.containing(posHit.x(), posHit.y(), posHit.z());
            if (startPos.getX() != blockPosHit.getX() || startPos.getY() != blockPosHit.getY() || startPos.getZ() != blockPosHit.getZ()) {
                BlockEntity blockEntity = level.getBlockEntity(blockPosHit);
                if (blockEntity instanceof ILightBlockEntity lightBlockEntityHit) {
                    float hitDistance = (float) Math.sqrt(Math.pow(posHit.x() - blockEntity.getBlockPos().getX() - lightBlockEntityHit.getLightLensPos().x(), 2) + Math.pow(posHit.y() - blockEntity.getBlockPos().getY() - lightBlockEntityHit.getLightLensPos().y(), 2) + Math.pow(posHit.z() - blockEntity.getBlockPos().getZ() - lightBlockEntityHit.getLightLensPos().z(), 2));

                    if (hitDistance <= lightBlockEntityHit.getLightLensSize()) {
                        end = posHit;
                        distance = (float) Math.sqrt(Math.pow(from.x() - end.x, 2) + Math.pow(from.y() - end.y, 2) + Math.pow(from.z() - end.z, 2));
                        hitBlockEntity = blockEntity;
                        break;
                    }
                }
            }
        }

        if (distance > rayDistance) {
            distance = rayDistance;
            end = newTo;
            hitBlockEntity = null;
        }

        return new LightRayHitResult(end, distance, hitBlockEntity);
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderLightRay(Vec3 from, Vec3 to, Color color, Color colorType, Color colorConcentrated, boolean concentrated, float partialTicks, PoseStack poseStack) {
        RenderBuilder builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/star"))
                .setColor(color)
                .setAlpha(0.2f);
        float tick = (ClientTickHandler.ticksInGame + partialTicks) * 2.5f;
        float tickConcentrated = (ClientTickHandler.ticksInGame + partialTicks) * 5f;

        poseStack.pushPose();
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.ZP.rotationDegrees(tick));
        builder.renderCenteredQuad(poseStack, 0.15f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-tick * 2));
        builder.renderCenteredQuad(poseStack, 0.15f);
        poseStack.popPose();

        if (concentrated) {
            builder.setColor(colorConcentrated)
                    .setAlpha(0.3f);
            poseStack.pushPose();
            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            poseStack.mulPose(Axis.ZP.rotationDegrees(tick+ 22.5f));
            builder.renderCenteredQuad(poseStack, 0.3f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(-tick * 2));
            builder.renderCenteredQuad(poseStack, 0.3f);
            poseStack.popPose();
        }

        builder.setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/tiny_wisp"))
                .setAlpha(0.1f);
        poseStack.pushPose();
        Vec3 pos = to.subtract(from);
        poseStack.translate(pos.x(), pos.y(), pos.z());
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.ZP.rotationDegrees(tick));
        builder.renderCenteredQuad(poseStack, 0.15f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-tick * 2));
        builder.renderCenteredQuad(poseStack, 0.15f);
        poseStack.popPose();

        if (concentrated) {
            builder.setColor(colorConcentrated);
            poseStack.pushPose();
            poseStack.translate(pos.x(), pos.y(), pos.z());
            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            poseStack.mulPose(Axis.ZP.rotationDegrees(tick + 22.5f));
            builder.renderCenteredQuad(poseStack, 0.3f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(-tick * 2));
            builder.renderCenteredQuad(poseStack, 0.3f);
            poseStack.popPose();
        }

        poseStack.pushPose();
        poseStack.translate(-from.x(), -from.y(), -from.z());
        builder.setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/trail"));
        builder.setColor(colorType)
                .setAlpha(0.5f)
                .renderBeam(poseStack.last().pose(), from, to, 0.12f);
        builder.setColor(color)
                .setAlpha(0.4f)
                .renderBeam(poseStack.last().pose(), from, to, 0.16f);
        if (concentrated) {
            builder.setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/dual_trail"))
                    .setColor(colorConcentrated)
                    .setAlpha(0.35f + ((float) Math.sin(Math.toRadians(tickConcentrated)) * 0.1f))
                    .renderBeam(poseStack.last().pose(), from, to, 0.32f);
        }
        poseStack.popPose();
    }

    public static void renderLightRay(Level level, BlockPos startPos, Vec3 from, Vec3 to, float rayDistance, Color color, Color colorType, Color colorConcentrated, boolean concentrated, float partialTicks, PoseStack poseStack) {
        LightRayHitResult hitResult = getLightRayHitResult(level, startPos, from, to, rayDistance);
        renderLightRay(from, hitResult.getPosHit(), color, colorType, colorConcentrated, concentrated, partialTicks, poseStack);
    }

    public static void transferLight(BlockEntity from, BlockEntity to) {
        if (from != null && to != null) {
            if (from instanceof ILightBlockEntity fromLight) {
                if (to instanceof ILightBlockEntity toLight) {
                    boolean update = false;
                    int max = fromLight.getLight();
                    if (max > 1) {
                        if (max > toLight.getLight()) {
                            toLight.setLight(max);
                            update = true;
                        }
                    }

                    for (LightTypeStack stack : fromLight.getLightTypes()) {
                        LightTypeStack toStack = getStack(stack.getType(), toLight.getLightTypes());
                        if (toStack == null) {
                            LightTypeStack stackCopy = stack.copy();
                            stackCopy.setTick(stack.getTick());
                            stackCopy.setTickConcentrated(stack.getTickConcentrated());
                            if (stack.isConcentrated()) {
                                stackCopy.setConcentrated(true);
                            }
                            toLight.addLightType(stackCopy);
                            update = true;
                        } else {
                            if (toStack.getTick() < stack.getTick()) {
                                toStack.setTick(stack.getTick());
                                update = true;
                            }
                            if (toStack.getTickConcentrated() < stack.getTickConcentrated()) {
                                toStack.setTickConcentrated(stack.getTickConcentrated());
                                update = true;
                            }
                            if (!toStack.isConcentrated() && stack.isConcentrated()) {
                                toStack.setConcentrated(true);
                                update = true;
                            }
                        }
                    }

                    if (update) BlockEntityUpdate.packet(to);
                }
            }
        }
    }

    public static Color getColorFromLumos(Color color, ArcaneLumosBlock lumos, float partialTicks) {
        if (lumos != null) {
            if (lumos.color == ArcaneLumosBlock.Colors.RAINBOW) {
                float ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.1f;
                return ColorUtil.rainbowColor(ticks);
            }
        }

        return color;
    }

    public static Color getLensColorFromLumos(ArcaneLumosBlock lumos, float partialTicks) {
        if (lumos != null) {
            return getColorFromLumos(ArcaneLumosBlock.getColor(lumos.color), lumos, partialTicks);
        }
        return standardLensColor;
    }

    public static Color getColorFromTypes(ArrayList<LightTypeStack> lightTypes) {
        ArrayList<Color> colors = new ArrayList<>();
        for (LightTypeStack stack : lightTypes) {
            colors.add(stack.getType().getColor());
        }
        if (!colors.isEmpty()) {
            return ColorUtil.getBlendColor(colors);
        }
        return Color.WHITE;
    }

    public static Color getColorConcentratedFromTypes(ArrayList<LightTypeStack> lightTypes) {
        ArrayList<Color> colors = new ArrayList<>();
        for (LightTypeStack stack : lightTypes) {
            if (stack.isConcentrated()) {
                colors.add(stack.getType().getColor());
            }
        }
        if (!colors.isEmpty()) {
            return ColorUtil.getBlendColor(colors);
        }
        return Color.WHITE;
    }

    public static boolean isConcentratedType(ArrayList<LightTypeStack> lightTypes) {
        for (LightTypeStack stack : lightTypes) {
            if (stack.isConcentrated()) {
                return true;
            }
        }
        return false;
    }

    public static void tickLightTypeStack(BlockEntity blockEntity, ArrayList<LightTypeStack> lightTypes) {
        if (blockEntity instanceof ILightBlockEntity lightBlockEntity) {
            ArrayList<LightTypeStack> stacks = new ArrayList<>(lightTypes);
            for (LightTypeStack stack : stacks) {
                stack.setTick(stack.getTick() - 1);
                if (stack.getTick() <= 0) {
                    lightBlockEntity.removeLightType(stack);
                }
                if (stack.getTickConcentrated() > 0) {
                    stack.setTickConcentrated(stack.getTickConcentrated() - 1);
                    if (stack.getTickConcentrated() <= 0) {
                        stack.setConcentrated(false);
                    }
                }
            }
        }
    }

    public static ListTag stacksToTag(ArrayList<LightTypeStack> lightTypes) {
        ListTag tagList = new ListTag();
        for (LightTypeStack lightType : lightTypes) {
            tagList.add(lightType.toTag());
        }
        return tagList;
    }

    public static ArrayList<LightTypeStack> stacksFromTag(ListTag tagList) {
        ArrayList<LightTypeStack> lightTypes = new ArrayList<>();
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag stackTags = tagList.getCompound(i);
            lightTypes.add(LightTypeStack.fromTag(stackTags));
        }
        return lightTypes;
    }

    public static boolean containsType(LightType lightType, ArrayList<LightTypeStack> lightTypes) {
        return getStack(lightType, lightTypes) != null;
    }

    public static LightTypeStack getStack(LightType lightType, ArrayList<LightTypeStack> lightTypes) {
        for (LightTypeStack stack : lightTypes) {
            if (stack.getType() == lightType) {
                return stack;
            }
        }
        return null;
    }
}
