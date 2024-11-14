package mod.maxbogomol.wizards_reborn.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.awt.*;

public class WissenWandRenderHandler {

    public static Vec3 block = Vec3.ZERO;
    public static Vec3 oldBlock = Vec3.ZERO;
    public static Vec3 nowBlock = Vec3.ZERO;
    public static Vec3 blockRay = Vec3.ZERO;
    public static Vec3 oldBlockRay  = Vec3.ZERO;
    public static Vec3 nowBlockRay = Vec3.ZERO;
    public static Vec3 side = Vec3.ZERO;
    public static Vec3 oldSide = Vec3.ZERO;
    public static Vec3 nowSide = Vec3.ZERO;
    public static Vec3 sideRot = Vec3.ZERO;
    public static Vec3 oldSideRot = Vec3.ZERO;
    public static Vec3 nowSideRot = Vec3.ZERO;
    public static int timeBlock = 0;
    public static int timeSide = 0;
    public static Color colorBlock = WizardsRebornRenderUtil.colorConnectFrom;
    public static Color colorSide = WizardsRebornRenderUtil.colorFluidSide;

    @OnlyIn(Dist.CLIENT)
    public static void wissenWandTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!Minecraft.getInstance().isPaused()) {
                Player player = WizardsReborn.proxy.getPlayer();
                if (player != null) {
                    ItemStack main = player.getMainHandItem();
                    ItemStack offhand = player.getOffhandItem();
                    boolean renderWand = false;
                    ItemStack stack = main;

                    if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
                        renderWand = true;
                    } else {
                        if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                            renderWand = true;
                            stack = offhand;
                        }
                    }

                    boolean addBlock = false;
                    boolean addSide = false;
                    if (renderWand) {
                        HitResult hitresult = player.pick(player.getBlockReach(), 0, false);

                        if (hitresult.getType() == HitResult.Type.BLOCK) {
                            if (WissenWandItem.getMode(stack) == 1 || WissenWandItem.getMode(stack) == 2) {
                                BlockPos blockpos = ((BlockHitResult) hitresult).getBlockPos();
                                if (canEffect(blockpos, player.level())) {
                                    block = new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ());
                                    if (timeBlock <= 0) {
                                        nowBlock = block;
                                    }
                                    Vec3 newOffset = LightUtil.getLightLensPos(blockpos, getEffectPos(blockpos, player.level()));
                                    if (WissenWandItem.getBlock(stack)) {
                                        blockRay = newOffset;
                                        if (timeBlock <= 0) {
                                            nowBlockRay = blockRay;
                                        }
                                    } else {
                                        nowBlockRay = newOffset;
                                    }
                                    if (timeBlock < 10) timeBlock = timeBlock + 3;
                                    if (timeBlock >= 10) timeBlock = 10;
                                    addBlock = true;
                                }
                            } else {
                                boolean renderSide = false;
                                BlockPos blockpos = ((BlockHitResult) hitresult).getBlockPos();
                                Direction direction = ((BlockHitResult) hitresult).getDirection();
                                if (player.level().getBlockEntity(blockpos) != null) {
                                    IFluidHandler fluidHandler = player.level().getBlockEntity(blockpos).getCapability(ForgeCapabilities.FLUID_HANDLER, direction).orElse(null);
                                    IEnergyStorage energyHandler = player.level().getBlockEntity(blockpos).getCapability(ForgeCapabilities.ENERGY, direction).orElse(null);
                                    if (fluidHandler != null) {
                                        renderSide = true;
                                        colorSide = WizardsRebornRenderUtil.colorFluidSide;
                                    }
                                    if (energyHandler != null) {
                                        renderSide = true;
                                        colorSide = WizardsRebornRenderUtil.colorEnergySide;
                                    }
                                    if (player.level().getBlockEntity(blockpos) instanceof ISteamBlockEntity steamBlockEntity) {
                                        renderSide = steamBlockEntity.canSteamConnection(direction);
                                        colorSide = WizardsRebornRenderUtil.colorSteamSide;
                                    }
                                }
                                if (renderSide) {
                                    side = new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ());
                                    Vec3i vec3i = direction.getOpposite().getNormal();
                                    sideRot = new Vec3(vec3i.getX(), vec3i.getY(), vec3i.getZ());
                                    if (timeSide <= 0) {
                                        nowSide = side;
                                        nowSideRot = sideRot;
                                    }
                                    if (timeSide < 10) timeSide = timeSide + 3;
                                    if (timeSide >= 10) timeSide = 10;
                                    addSide = true;
                                }
                            }
                        }

                        if (WissenWandItem.getBlock(stack)) {
                            colorBlock = WizardsRebornRenderUtil.colorConnectFrom;
                            if (WissenWandItem.getMode(stack) == 2) {
                                colorBlock = WizardsRebornRenderUtil.colorConnectTo;
                            }
                        } else {
                            colorBlock = WizardsRebornRenderUtil.colorSelected;
                        }
                    }
                    if (!addBlock && timeBlock > 0) timeBlock = timeBlock - 1;
                    if (!addSide && timeSide > 0) timeSide = timeSide - 1;

                    if (timeBlock > 0) {
                        oldBlock = nowBlock;
                        nowBlock = vecStep(block, nowBlock, 0.8f);
                        oldBlockRay = nowBlockRay;
                        nowBlockRay = vecStep(blockRay, nowBlockRay, 0.8f);
                    }

                    if (timeSide > 0) {
                        oldSide = nowSide;
                        nowSide = vecStep(side, nowSide, 0.8f);
                        oldSideRot = nowSideRot;
                        nowSideRot = vecStep(sideRot, nowSideRot, 0.5f);
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void wissenWandRender(RenderLevelStageEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        float partialTicks = event.getPartialTick();
        PoseStack poseStack = event.getPoseStack();

        Vec3 camera = minecraft.gameRenderer.getMainCamera().getPosition();

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            Player player = minecraft.player;
            if (player != null) {
                ItemStack main = player.getMainHandItem();
                ItemStack offhand = player.getOffhandItem();
                boolean renderWand = false;
                ItemStack stack = main;

                if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
                    renderWand = true;
                } else {
                    if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                        renderWand = true;
                        stack = offhand;
                    }
                }

                if (renderWand) {
                    double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks) * 5;
                    float alpha = (float) (0.5f + Math.abs(Math.sin(Math.toRadians(ticksAlpha)) * 0.5f));

                    if (WissenWandItem.getBlock(stack)) {
                        if (WissenWandItem.getMode(stack) == 1 || WissenWandItem.getMode(stack) == 2) {
                            BlockPos blockPos = WissenWandItem.getBlockPos(stack);

                            if (canEffect(blockPos, player.level())) {
                                poseStack.pushPose();
                                double dX = blockPos.getX() - camera.x();
                                double dY = blockPos.getY() - camera.y();
                                double dZ = blockPos.getZ() - camera.z();
                                poseStack.translate(dX, dY, dZ);

                                Color color = WizardsRebornRenderUtil.colorSelected;
                                RenderUtil.renderConnectBoxLines(poseStack, new Vec3(1, 1, 1), color, 0.5f * alpha);
                                poseStack.popPose();
                            }
                        }
                    }

                    if (timeBlock > 0) {
                        double dX = Mth.lerp(partialTicks, oldBlock.x(), nowBlock.x()) - camera.x();
                        double dY = Mth.lerp(partialTicks, oldBlock.y(), nowBlock.y()) - camera.y();
                        double dZ = Mth.lerp(partialTicks, oldBlock.z(), nowBlock.z()) - camera.z();

                        poseStack.pushPose();
                        poseStack.translate(dX, dY, dZ);
                        RenderUtil.renderConnectBoxLines(poseStack, new Vec3(1, 1, 1), colorBlock, 0.5f * alpha * (timeBlock / 12f));
                        poseStack.popPose();

                        if (WissenWandItem.getBlock(stack)) {
                            BlockPos blockPos = WissenWandItem.getBlockPos(stack);
                            Vec3 offset = getEffectPos(blockPos, player.level());

                            dX = blockPos.getX() - camera.x();
                            dY = blockPos.getY() - camera.y();
                            dZ = blockPos.getZ() - camera.z();

                            double X = Mth.lerp(partialTicks, oldBlockRay.x(), nowBlockRay.x());
                            double Y = Mth.lerp(partialTicks, oldBlockRay.y(), nowBlockRay.y());
                            double Z = Mth.lerp(partialTicks, oldBlockRay.z(), nowBlockRay.z());

                            poseStack.pushPose();
                            poseStack.translate(dX, dY, dZ);
                            poseStack.translate(offset.x(), offset.y(), offset.z());
                            RenderUtil.renderConnectLine(poseStack, LightUtil.getLightLensPos(blockPos, offset), new Vec3(X, Y, Z), colorBlock, 0.5f * alpha * (timeBlock / 12f));
                            poseStack.popPose();
                        }
                    }

                    if (timeSide > 0) {
                        double dX = Mth.lerp(partialTicks, oldSide.x(), nowSide.x()) - camera.x();
                        double dY = Mth.lerp(partialTicks, oldSide.y(), nowSide.y()) - camera.y();
                        double dZ = Mth.lerp(partialTicks, oldSide.z(), nowSide.z()) - camera.z();

                        double X = Mth.lerp(partialTicks, oldSideRot.x(), nowSideRot.x());
                        double Y = Mth.lerp(partialTicks, oldSideRot.y(), nowSideRot.y());
                        double Z = Mth.lerp(partialTicks, oldSideRot.z(), nowSideRot.z());

                        double yaw = Math.atan2(Z, X);
                        double pitch = Math.atan2(Math.sqrt(Z * Z + X * X), Y) + Math.PI;

                        poseStack.pushPose();
                        poseStack.translate(dX, dY, dZ);
                        Vec3 size = new Vec3(1, 1, 1);
                        poseStack.translate(0.5f, 0.5f, 0.5f);
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-90f));
                        poseStack.mulPose(Axis.XP.rotationDegrees((float) Math.toDegrees(yaw)));
                        poseStack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));

                        poseStack.translate(0, -0.001f, 0);
                        poseStack.translate(-size.x() / 2f, -size.y() / 2f, -size.z() / 2f);
                        RenderUtil.renderConnectSideLines(poseStack, size, colorSide, 0.5f * alpha * (timeSide / 12f));
                        poseStack.popPose();
                    }
                }
            }
        }
    }

    public static Vec3 vecStep(Vec3 old, Vec3 now, float step) {
        double dX = now.x() - old.x();
        double dY = now.y() - old.y();
        double dZ = now.z() - old.z();

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        float distance = (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2) + Math.pow(dZ, 2));
        float speed = Math.min(distance, step);
        double x = Math.sin(pitch) * Math.cos(yaw) * speed;
        double y = Math.cos(pitch) * speed;
        double z = Math.sin(pitch) * Math.sin(yaw) * speed;
        if (distance <= step) return old;
        return new Vec3(now.x() + x, now.y() + y, now.z() + z);
    }

    public static boolean canEffect(BlockPos pos, Level level) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof IWissenBlockEntity) return true;
        if (blockEntity instanceof ILightBlockEntity) return true;

        return false;
    }

    public static Vec3 getEffectPos(BlockPos pos, Level level) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ILightBlockEntity lightBlockEntity) return lightBlockEntity.getLightLensPos();

        return new Vec3(0.5f, 0.5f, 0.5f);
    }
}
