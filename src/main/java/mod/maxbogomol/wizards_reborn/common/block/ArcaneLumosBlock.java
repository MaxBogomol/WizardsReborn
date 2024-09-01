package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Random;
import java.util.function.Consumer;

public class ArcaneLumosBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);
    private static Random random = new Random();

    public enum Colors {
        WHITE,
        LIGHT_GRAY,
        GRAY,
        BLACK,
        BROWN,
        RED,
        ORANGE,
        YELLOW,
        LIME,
        GREEN,
        CYAN,
        LIGHT_BLUE,
        BLUE,
        PURPLE,
        MAGENTA,
        PINK,
        RAINBOW,
        COSMIC
    }

    public Colors color;

    public ArcaneLumosBlock(Colors color, Properties builder) {
        super(builder);
        this.color = color;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        Color color = getColor(this.color);

        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(color).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.3f, 0).build())
                .setLifetime(20)
                .randomVelocity(0.015f, 0.015f, 0.015f)
                .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
        if (random.nextFloat() < 0.5) {
            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                    .setColorData(ColorParticleData.create(color).build())
                    .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                    .setScaleData(GenericParticleData.create(0.1f, 0).build())
                    .randomSpin(0.5f)
                    .setLifetime(30)
                    .randomVelocity(0.015f, 0.015f, 0.015f)
                    .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
        }

        if (this.color == Colors.COSMIC) {
            if (random.nextFloat() < 0.3) {
                ParticleBuilder.create(FluffyFurParticles.STAR)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                        .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                        .randomSpin(0.5f)
                        .setLifetime(5)
                        .randomVelocity(0.01f, 0.01f, 0.01f)
                        .flatRandomOffset(0.25f, 0.25f, 0.25f)
                        .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
            }
            if (random.nextFloat() < 0.3) {
                ParticleBuilder.create(FluffyFurParticles.STAR)
                        .setColorData(ColorParticleData.create(Color.WHITE).build())
                        .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                        .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                        .randomSpin(0.5f)
                        .setLifetime(5)
                        .randomVelocity(0.01f, 0.01f, 0.01f)
                        .flatRandomOffset(0.25f, 0.25f, 0.25f)
                        .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
            }
        }
    }

    @Override
    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        consumer.accept(new IClientBlockExtensions() {
            @Override
            public boolean addHitEffects(BlockState state, Level level, HitResult target, ParticleEngine manager) {
                return true;
            }

            @Override
            public boolean addDestroyEffects(BlockState state, Level level, BlockPos pos, ParticleEngine manager) {
                return true;
            }
        });
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        return true;
    }

    public static Color getColor(Colors color) {
        switch (color) {
            case WHITE:
                return getDyeColor(DyeColor.WHITE);
            case LIGHT_GRAY:
                return getDyeColor(DyeColor.LIGHT_GRAY);
            case GRAY:
                return getDyeColor(DyeColor.GRAY);
            case BLACK:
                return getDyeColor(DyeColor.BLACK);
            case BROWN:
                return getDyeColor(DyeColor.BROWN);
            case RED:
                return getDyeColor(DyeColor.RED);
            case ORANGE:
                return getDyeColor(DyeColor.ORANGE);
            case YELLOW:
                return getDyeColor(DyeColor.YELLOW);
            case LIME:
                return getDyeColor(DyeColor.LIME);
            case GREEN:
                return getDyeColor(DyeColor.GREEN);
            case CYAN:
                return getDyeColor(DyeColor.CYAN);
            case LIGHT_BLUE:
                return getDyeColor(DyeColor.LIGHT_BLUE);
            case PURPLE:
                return getDyeColor(DyeColor.PURPLE);
            case BLUE:
                return getDyeColor(DyeColor.BLUE);
            case MAGENTA:
                return getDyeColor(DyeColor.MAGENTA);
            case PINK:
                return getDyeColor(DyeColor.PINK);
            case RAINBOW:
                return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
            case COSMIC:
                return new Color(254, 181, 178);
        }

        return getDyeColor(DyeColor.WHITE);
    }

    public static Color getDyeColor(DyeColor color) {
        return new Color(color.getMapColor().col);
    }
}
