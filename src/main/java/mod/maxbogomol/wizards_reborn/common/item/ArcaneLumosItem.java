package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
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
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            if (random.nextFloat() < 0.1) {
                Particles.create(WizardsReborn.WISP_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                        .setAlpha(0.5f, 0).setScale(0.3f, 0)
                        .setColor(r, g, b)
                        .setLifetime(20)
                        .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
            }
            if (random.nextFloat() < 0.05) {
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                        .setAlpha(0.75f, 0).setScale(0.1f, 0)
                        .setColor(r, g, b)
                        .setLifetime(30)
                        .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
            }

            if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                if (random.nextFloat() < 0.03) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                            .setAlpha(0.75f, 0).setScale(0.1f, 0)
                            .setColor(r, g, b)
                            .setLifetime(5)
                            .spawn(level, entity.getX() + ((random.nextDouble() - 0.5D) / 2), entity.getY() + ((random.nextDouble() - 0.5D) / 2), entity.getZ() + ((random.nextDouble() - 0.5D) / 2));
                }
                if (random.nextFloat() < 0.03) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                            .setAlpha(0.75f, 0).setScale(0.1f, 0)
                            .setColor(1f, 1f, 1f)
                            .setLifetime(5)
                            .spawn(level, entity.getX() + ((random.nextDouble() - 0.5D) / 2), entity.getY() + ((random.nextDouble() - 0.5D) / 2), entity.getZ() + ((random.nextDouble() - 0.5D) / 2));
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
            float ticks = ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() + (seedI * 100f) * 0.1f;
            float angle = (randomI.nextFloat() * 360f) + ticks;
            float offset = (float) (0.75f + Math.abs(Math.sin(Math.toRadians(randomI.nextFloat() * 360f + ticks * 0.4f)) * 0.25f));

            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(WizardsRebornClient::getGlowingShader);
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

            TextureAtlasSprite sparkle = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/sparkle"));

            pose.pushPose();
            pose.translate(x + 8, y + 9, 100);
            pose.mulPose(Axis.ZP.rotationDegrees(angle));
            RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 14f * offset, 14f * offset, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), r, g, b, 1F);
            buffersource.endBatch();
            pose.popPose();

            if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                pose.pushPose();
                pose.translate(x + 8, y + 9, 100);
                pose.mulPose(Axis.ZP.rotationDegrees(angle + 45f));
                RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 14f * offset, 14f * offset, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), 1f, 1f, 1f, 0.75F);
                buffersource.endBatch();
                pose.popPose();
            }

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        }
    }
}