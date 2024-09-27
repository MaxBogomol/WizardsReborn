package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.common.item.IParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.Random;

public class ArcaneLumosItem extends BlockItem implements IParticleItem, IGuiParticleItem {

    private static Random random = new Random();

    public ArcaneLumosItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    public void addParticles(Level level, ItemEntity entity) {
        if (getBlock() instanceof ArcaneLumosBlock lumos) {
            Color color = ArcaneLumosBlock.getColor(lumos.color);

            if (random.nextFloat() < 0.1) {
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                        .setScaleData(GenericParticleData.create(0.3f, 0).build())
                        .setLifetime(20)
                        .randomVelocity(0.015f)
                        .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
            }
            if (random.nextFloat() < 0.05) {
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                        .setScaleData(GenericParticleData.create(0.1f, 0).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                        .setLifetime(30)
                        .randomVelocity(0.015f)
                        .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
            }

            if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                if (random.nextFloat() < 0.03) {;
                    ParticleBuilder.create(FluffyFurParticles.STAR)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                            .setLifetime(10)
                            .flatRandomOffset(0.25f, 0.25f, 0.25f)
                            .spawn(level, entity.getX(), entity.getY(), entity.getZ());
                }
                if (random.nextFloat() < 0.03) {
                    ParticleBuilder.create(FluffyFurParticles.STAR)
                            .setColorData(ColorParticleData.create(Color.WHITE).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                            .setLifetime(10)
                            .flatRandomOffset(0.25f, 0.25f, 0.25f)
                            .spawn(level, entity.getX(), entity.getY(), entity.getZ());
                }
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        if (getBlock() instanceof ArcaneLumosBlock lumos) {
            Color color = ArcaneLumosBlock.getColor(lumos.color);
            if (lumos.color == ArcaneLumosBlock.Colors.RAINBOW) {
                double ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.1f;
                color = ColorUtil.rainbowColor((float) ticks);
            }

            int seedI = this.getDescriptionId().length();
            Random randomI = new Random(seedI);
            float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() + (seedI * 100f));
            float angle = (randomI.nextFloat() * 360f) + ticks;

            poseStack.pushPose();
            poseStack.translate(x + 8, y + 9, 100);
            poseStack.mulPose(Axis.ZP.rotationDegrees(angle));
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                    .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/star"))
                    .setColor(color).setAlpha(1f)
                    .renderCenteredQuad(poseStack, 8f)
                    .endBatch();
            poseStack.popPose();

            if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                poseStack.pushPose();
                poseStack.translate(x + 7.5, y + 9, 100);
                poseStack.mulPose(Axis.ZP.rotationDegrees(angle + 22.5f));
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                        .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/star"))
                        .setColor(color).setAlpha(0.75f)
                        .renderCenteredQuad(poseStack, 7f)
                        .endBatch();
                poseStack.popPose();
            }
        }
    }
}