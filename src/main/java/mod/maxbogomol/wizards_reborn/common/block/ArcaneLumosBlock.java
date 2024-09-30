package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
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
    private static final Random random = new Random();

    public static LumosColor WHITE = new LumosColor(getDyeColor(DyeColor.WHITE));
    public static LumosColor LIGHT_GRAY = new LumosColor(getDyeColor(DyeColor.LIGHT_GRAY));
    public static LumosColor GRAY = new LumosColor(getDyeColor(DyeColor.GRAY));
    public static LumosColor BLACK = new LumosColor(getDyeColor(DyeColor.BLACK));
    public static LumosColor BROWN = new LumosColor(getDyeColor(DyeColor.BROWN));
    public static LumosColor RED = new LumosColor(getDyeColor(DyeColor.RED));
    public static LumosColor ORANGE = new LumosColor(getDyeColor(DyeColor.ORANGE));
    public static LumosColor YELLOW = new LumosColor(getDyeColor(DyeColor.YELLOW));
    public static LumosColor LIME = new LumosColor(getDyeColor(DyeColor.LIME));
    public static LumosColor GREEN = new LumosColor(getDyeColor(DyeColor.GREEN));
    public static LumosColor CYAN = new LumosColor(getDyeColor(DyeColor.CYAN));
    public static LumosColor LIGHT_BLUE = new LumosColor(getDyeColor(DyeColor.LIGHT_BLUE));
    public static LumosColor BLUE = new LumosColor(getDyeColor(DyeColor.BLUE));
    public static LumosColor PURPLE = new LumosColor(getDyeColor(DyeColor.PURPLE));
    public static LumosColor MAGENTA = new LumosColor(getDyeColor(DyeColor.MAGENTA));
    public static LumosColor PINK = new LumosColor(getDyeColor(DyeColor.PINK));
    public static LumosColor RAINBOW = new RainbowColor();
    public static LumosColor COSMIC = new CosmicColor();

    public LumosColor color;

    public ArcaneLumosBlock(LumosColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Color color = this.color.getColor();

        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(color).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.3f, 0).build())
                .setLifetime(20)
                .randomVelocity(0.015f, 0.015f, 0.015f)
                .spawn(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
        if (random.nextFloat() < 0.5) {
            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                    .setColorData(ColorParticleData.create(color).build())
                    .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                    .setScaleData(GenericParticleData.create(0.1f, 0).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                    .setLifetime(30)
                    .randomVelocity(0.015f, 0.015f, 0.015f)
                    .spawn(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
        }

        if (this.color.hasFirstStar()) {
            if (random.nextFloat() < 0.3) {
                ParticleBuilder.create(FluffyFurParticles.STAR)
                        .setColorData(ColorParticleData.create(this.color.getColorFirstStar()).build())
                        .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                        .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                        .setLifetime(10)
                        .randomVelocity(0.01f, 0.01f, 0.01f)
                        .flatRandomOffset(0.25f, 0.25f, 0.25f)
                        .spawn(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
            }
        }
        if (this.color.hasSecondStar()) {
            if (random.nextFloat() < 0.3) {
                ParticleBuilder.create(FluffyFurParticles.STAR)
                        .setColorData(ColorParticleData.create(this.color.getColorSecondStar()).build())
                        .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                        .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                        .setLifetime(10)
                        .randomVelocity(0.01f, 0.01f, 0.01f)
                        .flatRandomOffset(0.25f, 0.25f, 0.25f)
                        .spawn(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
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

    public static Color getDyeColor(DyeColor color) {
        return new Color(color.getMapColor().col);
    }

    public static class LumosColor {
        public Color color = Color.WHITE;
        public Color colorFirstStar = Color.WHITE;
        public Color colorSecondStar = Color.WHITE;

        public LumosColor() {

        }

        public LumosColor(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public Color getColorFirstStar() {
            return colorFirstStar;
        }

        public Color getColorSecondStar() {
            return colorSecondStar;
        }

        public boolean hasFirstStar() {
            return false;
        }

        public boolean hasSecondStar() {
            return false;
        }

        @OnlyIn(Dist.CLIENT)
        public Color getLightRayColor(float partialTicks) {
            return color;
        }
    }

    public static class RainbowColor extends LumosColor {
        public Color getColor() {
            return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
        }

        @OnlyIn(Dist.CLIENT)
        public Color getLightRayColor(float partialTicks) {
            float ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.1f;
            return ColorUtil.rainbowColor(ticks);
        }
    }

    public static class CosmicColor extends LumosColor {

        public CosmicColor() {
            this.color = new Color(254, 181, 178);
            this.colorFirstStar = color;
            this.colorSecondStar = Color.WHITE;
        }

        public boolean hasFirstStar() {
            return true;
        }

        public boolean hasSecondStar() {
            return true;
        }
    }
}
