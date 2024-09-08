package mod.maxbogomol.wizards_reborn.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import java.util.Random;

public class ClientWorldEvent {

    private static Random random = new Random();

    @OnlyIn(Dist.CLIENT)
    public static void onTick(TickEvent.LevelTickEvent event) {

    }

    @OnlyIn(Dist.CLIENT)
    public static void onRender(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        float partialTicks = event.getPartialTick();
        PoseStack ms = event.getPoseStack();

        Vec3 camera = mc.gameRenderer.getMainCamera().getPosition();

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            Player player = mc.player;
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
                    float alpha = (float) (0.25f + Math.abs(Math.sin(Math.toRadians(ticksAlpha)) * 0.75f));

                    HitResult hitresult = player.pick(player.getBlockReach(), 0.0F, false);

                    if (WissenWandItem.getBlock(stack)) {
                        if (WissenWandItem.getMode(stack) == 1 || WissenWandItem.getMode(stack) == 2) {
                            BlockPos blockPos = WissenWandItem.getBlockPos(stack);

                            if (canEffect(blockPos, player.level())) {
                                ms.pushPose();
                                double dX = blockPos.getX() - camera.x();
                                double dY = blockPos.getY() - camera.y();
                                double dZ = blockPos.getZ() - camera.z();
                                ms.translate(dX, dY, dZ);

                                Color color = RenderUtils.colorSelected;
                                RenderUtils.renderBoxLines(new Vec3(1, 1, 1), new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha), partialTicks, ms);
                                ms.popPose();

                                if (hitresult.getType() == HitResult.Type.BLOCK) {
                                    BlockPos blockpos = ((BlockHitResult) hitresult).getBlockPos();
                                    if (canEffect(blockpos, player.level())) {
                                        color = RenderUtils.colorConnectFrom;
                                        if (WissenWandItem.getMode(stack) == 2) {
                                            color = RenderUtils.colorConnectTo;
                                        }

                                        dX = blockpos.getX() - camera.x();
                                        dY = blockpos.getY() - camera.y();
                                        dZ = blockpos.getZ() - camera.z();

                                        Vec3 offset = getEffectPos(blockPos, player.level());
                                        Vec3 newOffset = getEffectPos(blockpos, player.level());

                                        ms.pushPose();
                                        ms.translate(dX, dY, dZ);
                                        RenderUtils.renderBoxLines(new Vec3(1, 1, 1), new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha), partialTicks, ms);
                                        ms.popPose();

                                        dX = blockPos.getX() - camera.x();
                                        dY = blockPos.getY() - camera.y();
                                        dZ = blockPos.getZ() - camera.z();

                                        ms.pushPose();
                                        ms.translate(dX, dY, dZ);
                                        ms.translate(offset.x(), offset.y(), offset.z());
                                        RenderUtils.renderConnectLine(LightUtil.getLightLensPos(blockPos, offset), LightUtil.getLightLensPos(blockpos, newOffset), new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha), partialTicks, ms);
                                        ms.popPose();
                                    }
                                }
                            }
                        }
                    }

                    if (!(WissenWandItem.getMode(stack) == 1 || WissenWandItem.getMode(stack) == 2)) {
                        boolean renderSide = false;
                        boolean renderFluidSide = false;
                        boolean renderSteamSide = false;
                        boolean renderEnergySide = false;
                        Direction direction = Direction.UP;
                        BlockPos blockpos = BlockPos.ZERO;

                        if (hitresult.getType() == HitResult.Type.BLOCK) {
                            blockpos = ((BlockHitResult) hitresult).getBlockPos();
                            direction = ((BlockHitResult) hitresult).getDirection();
                            if (player.level().getBlockEntity(blockpos) != null) {
                                IFluidHandler fluidHandler = player.level().getBlockEntity(blockpos).getCapability(ForgeCapabilities.FLUID_HANDLER, direction).orElse(null);
                                IEnergyStorage energyHandler = player.level().getBlockEntity(blockpos).getCapability(ForgeCapabilities.ENERGY, direction).orElse(null);
                                if (fluidHandler != null) {
                                    renderSide = true;
                                    renderFluidSide = true;
                                }
                                if (energyHandler != null) {
                                    renderSide = true;
                                    renderEnergySide = true;
                                }
                                if (player.level().getBlockEntity(blockpos) instanceof ISteamBlockEntity steamTile) {
                                    renderSide = true;
                                    renderSteamSide = steamTile.canSteamConnection(direction);
                                }
                            }
                        }

                        if (renderSide) {
                            ms.pushPose();
                            double dX = blockpos.getX() - camera.x();
                            double dY = blockpos.getY() - camera.y();
                            double dZ = blockpos.getZ() - camera.z();
                            ms.translate(dX, dY, dZ);
                            if (renderFluidSide) {
                                Color color = RenderUtils.colorFluidSide;
                                RenderUtils.renderSide(direction, new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha), partialTicks, ms);
                            }
                            if (renderSteamSide) {
                                Color color = RenderUtils.colorSteamSide;
                                RenderUtils.renderSide(direction, new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha), partialTicks, ms);
                            }
                            if (renderEnergySide) {
                                Color color = RenderUtils.colorEnergySide;
                                RenderUtils.renderSide(direction, new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha), partialTicks, ms);
                            }
                            ms.popPose();
                        }
                    }
                }
            }
        }
    }

    public static boolean canEffect(BlockPos pos, Level level) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof IWissenBlockEntity) return true;
        if (blockEntity instanceof ILightBlockEntity) return true;

        return false;
    }

    public static Vec3 getEffectPos(BlockPos pos, Level level) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ILightBlockEntity lightTile) return lightTile.getLightLensPos();

        return new Vec3(0.5f, 0.5f, 0.5f);
    }
}
