package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalStat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.block.CrystalBlock;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class CrystalItem extends BlockItem implements IParticleItem, IGuiParticleItem, ICustomBlockEntityDataItem {
    private static Random random = new Random();

    public CrystalItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    public CrystalType getType() {
        if (getBlock() instanceof CrystalBlock) {
            CrystalBlock block = (CrystalBlock) getBlock();
            return block.type;
        }

        return new CrystalType("");
    }

    public PolishingType getPolishing() {
        if (getBlock() instanceof CrystalBlock) {
            CrystalBlock block = (CrystalBlock) getBlock();
            return block.polishing;
        }

        return new PolishingType("", 0);
    }

    public static int getStatLevel(ItemStack stack, CrystalStat stat) {
        CompoundTag nbt = stack.getOrCreateTag();
        int statlevel = 0;
        if (nbt.contains(stat.getId())) {
            statlevel = nbt.getInt(stat.getId());
        }
        return statlevel;
    }

    public static ItemStack creativeTabRandomStats(Item item) {
        ItemStack stack = item.getDefaultInstance();
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putBoolean("random_stats", true);
        stack.setTag(nbt);
        return stack;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide()) {
            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("random_stats")) {
                nbt.remove("random_stats");
                CrystalUtils.createCrystalItemStats(stack, getType(), world, 6);
                stack.setTag(nbt);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        CrystalType type = getType();
        Color color = type.getColor();
        for (CrystalStat stat : type.getStats()) {
            int statlevel = getStatLevel(stack, stat);
            int red = (int) Mth.lerp((float) statlevel / stat.getMaxLevel(), Color.GRAY.getRed(), color.getRed());
            int green = (int) Mth.lerp((float) statlevel / stat.getMaxLevel(), Color.GRAY.getGreen(), color.getGreen());
            int blue = (int) Mth.lerp((float) statlevel / stat.getMaxLevel(), Color.GRAY.getBlue(), color.getBlue());

            int packColor = ColorUtils.packColor(255, red, green, blue);
            list.add(Component.translatable(stat.getTranslatedName()).append(": " + statlevel).withStyle(Style.EMPTY.withColor(packColor)));
        }
    }

    @Override
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag tileNbt) {
        if (!tileNbt.contains("Items")) {
            NonNullList<ItemStack> ret = NonNullList.withSize(1, ItemStack.EMPTY);
            ret.set(0, stack);
            ContainerHelper.saveAllItems(tileNbt, ret);
        }

        return tileNbt;
    }

    @Override
    public void addParticles(Level level, ItemEntity entity) {
        CrystalType type = getType();
        PolishingType polishing = getPolishing();

        if (random.nextFloat() < 0.01) {
            Color color = type.getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                    .setAlpha(0.5f, 0).setScale(0.1f, 0)
                    .setColor(r, g, b)
                    .setLifetime(30)
                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(level, entity.getX() + ((random.nextDouble() - 0.5D) * 0.25), entity.getY() + 0.25F + ((random.nextDouble() - 0.5D) * 0.25), entity.getZ() + ((random.nextDouble() - 0.5D) * 0.25));
        }

        if (polishing.hasParticle()) {
            if (random.nextFloat() < 0.01) {
                Color color = polishing.getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                        .setAlpha(0.5f, 0).setScale(0.1f, 0)
                        .setColor(r, g, b)
                        .setLifetime(30)
                        .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .spawn(level, entity.getX() + ((random.nextDouble() - 0.5D) * 0.25), entity.getY() + 0.125F + ((random.nextDouble() - 0.5D) * 0.25), entity.getZ() + ((random.nextDouble() - 0.5D) * 0.25));
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        if (getPolishing().getPolishingLevel() > 0) {
            int polishingLevel = getPolishing().getPolishingLevel();
            if (polishingLevel > 4) {
                polishingLevel = 4;
            }
            Color color = getType().getColor();
            int seedI = this.getDescriptionId().length();

            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(WizardsRebornClient::getGlowingShader);
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

            pose.pushPose();
            pose.translate(x, y, 100);
            RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.75F * (polishingLevel / 4f));
            RenderUtils.dragon(pose, buffersource, 8, 8, 0, 12f, Minecraft.getInstance().getPartialTick(), 1, 1, 1, seedI);
            buffersource.endBatch();
            pose.popPose();

            if (getPolishing().hasParticle()) {
                Color polishingColor = getPolishing().getColor();
                pose.pushPose();
                pose.translate(x, y, 100);
                RenderSystem.setShaderColor(polishingColor.getRed() / 255f, polishingColor.getGreen() / 255f, polishingColor.getBlue() / 255f, 0.5F);
                RenderUtils.dragon(pose, buffersource, 8, 8, 0, 10f, Minecraft.getInstance().getPartialTick(), 1, 1, 1, seedI + 1f);
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