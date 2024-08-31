package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.common.item.IParticleItem;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
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
                ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                        .setScaleData(GenericParticleData.create(0.3f, 0).build())
                        .setLifetime(320)
                        .randomVelocity(0.015f)
                        .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
            }
            if (random.nextFloat() < 0.05) {
                ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                        .setScaleData(GenericParticleData.create(0.1f, 0).build())
                        .randomSpin(0.5f)
                        .setLifetime(30)
                        .randomVelocity(0.015f)
                        .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
            }

            if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                if (random.nextFloat() < 0.03) {;
                    ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0).build())
                            .randomSpin(0.5f)
                            .setLifetime(5)
                            .flatRandomOffset(0.25f, 0.25f, 0.25f)
                            .spawn(level, entity.getX(), entity.getY(), entity.getZ());
                }
                if (random.nextFloat() < 0.03) {
                    ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                            .setColorData(ColorParticleData.create(Color.WHITE).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0).build())
                            .randomSpin(0.5f)
                            .setLifetime(5)
                            .flatRandomOffset(0.25f, 0.25f, 0.25f)
                            .spawn(level, entity.getX(), entity.getY(), entity.getZ());
                }
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        if (getBlock() instanceof ArcaneLumosBlock lumos) {
            Color color = ArcaneLumosBlock.getColor(lumos.color);
            if (lumos.color == ArcaneLumosBlock.Colors.RAINBOW) {
                double ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.1f;
                color = new Color((int)(Math.sin(ticks) * 127 + 128), (int)(Math.sin(ticks + Math.PI/2) * 127 + 128), (int)(Math.sin(ticks + Math.PI) * 127 + 128));
            }
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            int seedI = this.getDescriptionId().length();
            Random randomI = new Random(seedI);
            float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() + (seedI * 100f));
            float angle = (randomI.nextFloat() * 360f) + ticks;
            float offset = (float) (0.75f + Math.abs(Math.sin(Math.toRadians(randomI.nextFloat() * 360f + ticks * 0.4f)) * 0.25f));

            RenderUtils.startGuiParticle();
            MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();

            TextureAtlasSprite star = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(FluffyFur.MOD_ID, "particle/star"));

            pose.pushPose();
            pose.translate(x + 8, y + 9, 100);
            pose.mulPose(Axis.ZP.rotationDegrees(angle));
            RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 16f * offset, 16f * offset, star.getU0(), star.getU1(), star.getV0(), star.getV1(), r, g, b, 1F);
            buffersource.endBatch();
            pose.popPose();

            if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                pose.pushPose();
                pose.translate(x + 7.5, y + 9, 100);
                pose.mulPose(Axis.ZP.rotationDegrees(angle + 22.5f));
                RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 14f * offset, 14f * offset, star.getU0(), star.getU1(), star.getV0(), star.getV1(), 1f, 1f, 1f, 0.75F);
                buffersource.endBatch();
                pose.popPose();
            }

            RenderUtils.endGuiParticle();
        }
    }
}