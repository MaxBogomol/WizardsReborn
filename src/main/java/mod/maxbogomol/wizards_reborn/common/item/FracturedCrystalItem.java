package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.item.IParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalStat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class FracturedCrystalItem extends Item implements IParticleItem {

    public CrystalType type;

    private static final Random random = new Random();

    public FracturedCrystalItem(CrystalType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public CrystalType getType() {
        return type;
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
                CrystalUtil.createCrystalItemStats(stack, type, level, 6);
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
            list.add(Component.translatable(stat.getTranslatedName()).append(": " + statLevel).withStyle(Style.EMPTY.withColor(packColor)));
        }
    }

    @Override
    public void addParticles(Level level, ItemEntity entity) {
        CrystalType type = getType();

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
    }
}