package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.item.ICustomBlockEntityDataItem;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.common.item.IParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalStat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlock;
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
import net.minecraftforge.client.event.RenderTooltipEvent;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class CrystalItem extends BlockItem implements IParticleItem, IGuiParticleItem, ICustomBlockEntityDataItem {

    private static final Random random = new Random();

    public CrystalItem(Block block, Properties properties) {
        super(block, properties);
    }

    public CrystalType getType() {
        if (getBlock() instanceof CrystalBlock block) {
            return block.type;
        }

        return new CrystalType("");
    }

    public PolishingType getPolishing() {
        if (getBlock() instanceof CrystalBlock block) {
            return block.polishing;
        }

        return new PolishingType("", 0);
    }

    public static int getStatLevel(ItemStack stack, CrystalStat stat) {
        CompoundTag nbt = stack.getOrCreateTag();
        int statLevel = 0;
        if (nbt.contains(stat.getId())) {
            statLevel = nbt.getInt(stat.getId());
        }
        return statLevel;
    }

    public static ItemStack creativeTabRandomStats(Item item) {
        ItemStack stack = item.getDefaultInstance();
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putBoolean("randomStats", true);
        stack.setTag(nbt);
        return stack;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (!level.isClientSide()) {
            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("randomStats")) {
                nbt.remove("randomStats");
                CrystalUtil.createCrystalItemStats(stack, getType(), level, getType().getRandomStats());
                stack.setTag(nbt);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        CrystalType type = getType();
        Color color = type.getColor();
        for (CrystalStat stat : type.getStats()) {
            int statLevel = getStatLevel(stack, stat);
            int red = (int) Mth.lerp((float) statLevel / stat.getMaxLevel(), Color.GRAY.getRed(), color.getRed());
            int green = (int) Mth.lerp((float) statLevel / stat.getMaxLevel(), Color.GRAY.getGreen(), color.getGreen());
            int blue = (int) Mth.lerp((float) statLevel / stat.getMaxLevel(), Color.GRAY.getBlue(), color.getBlue());
            int packColor = ColorUtil.packColor(255, red, green, blue);
            
            String statLevelString = String.valueOf(statLevel);
            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("randomStats")) statLevelString = "?";
            list.add(Component.translatable(stat.getTranslatedName()).append(": " + statLevelString).withStyle(Style.EMPTY.withColor(packColor)));
        }
    }

    @Override
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag nbt) {
        if (!nbt.contains("Items")) {
            NonNullList<ItemStack> ret = NonNullList.withSize(1, ItemStack.EMPTY);
            ret.set(0, stack);
            ContainerHelper.saveAllItems(nbt, ret);
        }

        return nbt;
    }

    @Override
    public void addParticles(Level level, ItemEntity entity) {
        CrystalType type = getType();
        PolishingType polishing = getPolishing();

        if (random.nextFloat() < 0.05) {
            Color color = type.getColor();
            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                    .setColorData(ColorParticleData.create(color).build())
                    .setTransparencyData(GenericParticleData.create(0.4f, 0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                    .setLifetime(30)
                    .randomVelocity(0.01f)
                    .randomOffset(0.125f)
                    .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
        }

        if (polishing.hasParticle()) {
            if (random.nextFloat() < 0.03) {
                Color color = polishing.getColor();
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.4f, 0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                        .setLifetime(30)
                        .randomVelocity(0.01f)
                        .randomOffset(0.125f)
                        .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        if (getPolishing().getPolishingLevel() > 0) {
            int polishingLevel = getPolishing().getPolishingLevel();
            if (polishingLevel > 4) polishingLevel = 4;
            Color color = getType().getColor();
            int seedI = this.getDescriptionId().length();

            poseStack.pushPose();
            poseStack.translate(x + 8, y + 8, 100);
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                    .setColor(color).setAlpha(0.75F * (polishingLevel / 4f))
                    .renderDragon(poseStack, 12f, ClientTickHandler.partialTicks, seedI)
                    .endBatch();
            poseStack.popPose();

            if (getPolishing().hasParticle()) {
                Color polishingColor = getPolishing().getColor();
                poseStack.pushPose();
                poseStack.translate(x + 8, y + 8, 100);
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                        .setColor(polishingColor).setAlpha(0.5f)
                        .renderDragon(poseStack, 10f, ClientTickHandler.partialTicks, seedI + 1)
                        .endBatch();
                poseStack.popPose();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void onTooltipRenderColor(RenderTooltipEvent.Color event) {
        if (event.getItemStack().getItem() instanceof CrystalItem item) {
            Color color = item.getType().getColor();
            int packColorStart = ColorUtil.packColor(255 / 5, color.getRed(), color.getGreen(), color.getBlue());
            int packColorEnd = ColorUtil.packColor(color);
            if (item.getPolishing().hasParticle()) {
                color = item.getPolishing().getColor();
                packColorStart = ColorUtil.packColor(255 / 3, color.getRed(), color.getGreen(), color.getBlue());
            }
            event.setBorderStart(packColorStart);
            event.setBorderEnd(packColorEnd);
        }
    }
}