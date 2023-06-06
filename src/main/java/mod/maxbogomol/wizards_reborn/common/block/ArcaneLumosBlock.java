package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Random;

public class ArcaneLumosBlock extends Block {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(6, 6, 6, 10, 10, 10);

    public enum Colors {
        WHITE,
        ORANGE,
        MAGENTA,
        LIGHT_BLUE,
        YELLOW,
        LIME,
        PINK,
        GRAY,
        LIGHT_GRAY,
        CYAN,
        PURPLE,
        BLUE,
        BROWN,
        GREEN,
        RED,
        BLACK,
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
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        return SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        Color color = getColor(this.color);
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        Particles.create(WizardsReborn.WISP_PARTICLE)
                .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                .setAlpha(0.5f, 0).setScale(0.3f, 0)
                .setColor(r, g, b)
                .setLifetime(20)
                .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
        if (random.nextFloat() < 0.5) {
            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                    .setAlpha(0.75f, 0).setScale(0.1f, 0)
                    .setColor(r, g, b)
                    .setLifetime(30)
                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
        }

        if (this.color == Colors.COSMIC) {
            if (random.nextFloat() < 0.3) {
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                        .setAlpha(0.75f, 0).setScale(0.1f, 0)
                        .setColor(r, g, b)
                        .setLifetime(5)
                        .spawn(world, pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) / 2), pos.getY() + 0.5F + ((random.nextDouble() - 0.5D) / 2), pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) / 2));
            }
            if (random.nextFloat() < 0.3) {
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                        .setAlpha(0.75f, 0).setScale(0.1f, 0)
                        .setColor(1f, 1f, 1f)
                        .setLifetime(5)
                        .spawn(world, pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) / 2), pos.getY() + 0.5F + ((random.nextDouble() - 0.5D) / 2), pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) / 2));
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean addHitEffects(BlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
        return true;
    }

    public Color getColor(Colors color) {
        switch (color) {
            case WHITE:
                return getDyeColor(DyeColor.WHITE);
            case ORANGE:
                return getDyeColor(DyeColor.ORANGE);
            case MAGENTA:
                return getDyeColor(DyeColor.MAGENTA);
            case LIGHT_BLUE:
                return getDyeColor(DyeColor.LIGHT_BLUE);
            case YELLOW:
                return getDyeColor(DyeColor.YELLOW);
            case LIME:
                return getDyeColor(DyeColor.LIME);
            case PINK:
                return getDyeColor(DyeColor.PINK);
            case GRAY:
                return getDyeColor(DyeColor.GRAY);
            case LIGHT_GRAY:
                return getDyeColor(DyeColor.LIGHT_GRAY);
            case CYAN:
                return getDyeColor(DyeColor.CYAN);
            case PURPLE:
                return getDyeColor(DyeColor.PURPLE);
            case BLUE:
                return getDyeColor(DyeColor.BLUE);
            case BROWN:
                return getDyeColor(DyeColor.BROWN);
            case GREEN:
                return getDyeColor(DyeColor.GREEN);
            case RED:
                return getDyeColor(DyeColor.RED);
            case BLACK:
                return getDyeColor(DyeColor.BLACK);
            case RAINBOW:
                return getRainbowColor();
            case COSMIC:
                return new Color(254, 181, 178);
        }

        return getDyeColor(DyeColor.WHITE);
    }

    public Color getDyeColor(DyeColor color) {
        return new Color(color.getColorValue());
    }

    public Color getRainbowColor() {
        float i = 1f / 6f;
        float tick = (float) Math.sin(Math.toRadians(ClientTickHandler.rainbowTicks * 0.4 % 360));
        if (tick < 0) {
            tick = 1f + tick;
        }

        Color color = new Color(255, 255, 255);

        if (tick <= i * 1) {
            color = getRainbowBetweenColor(getDyeColor(DyeColor.RED), getDyeColor(DyeColor.ORANGE), tick, i);
        }
        if (tick > i * 1 && tick <= i * 2) {
            color = getRainbowBetweenColor(getDyeColor(DyeColor.ORANGE), getDyeColor(DyeColor.YELLOW), tick, i * 2);
        }
        if (tick > i * 2 && tick <= i * 3) {
            color = getRainbowBetweenColor(getDyeColor(DyeColor.YELLOW), getDyeColor(DyeColor.LIME), tick, i * 3);
        }
        if (tick > i * 3 && tick <= i * 4) {
            color = getRainbowBetweenColor(getDyeColor(DyeColor.LIME), getDyeColor(DyeColor.LIGHT_BLUE), tick, i * 4);
        }
        if (tick > i * 4 && tick <= i * 5) {
            color = getRainbowBetweenColor(getDyeColor(DyeColor.LIGHT_BLUE), getDyeColor(DyeColor.BLUE), tick, i * 5);
        }
        if (tick > i * 5 && tick <= i * 6) {
            color = getRainbowBetweenColor(getDyeColor(DyeColor.BLUE), getDyeColor(DyeColor.PURPLE), tick, i * 6);
        }

        return color;
    }

    public Color getRainbowBetweenColor(Color color1, Color color2, float tick, float maxTick) {
        int R = (int) (color1.getRed() + ((color2.getRed() - color1.getRed()) * (tick / maxTick)));
        int G = (int) (color1.getGreen() + ((color2.getGreen() - color1.getGreen()) * (tick / maxTick)));
        int B = (int) (color1.getBlue() + ((color2.getBlue() - color1.getBlue()) * (tick / maxTick)));

        if (R < 0) {
            R = 255 + R;
        }
        if (G < 0) {
            G = 255 + R;
        }
        if (B < 0) {
            B = 255 + R;
        }

        if (R > 255) {
            R = R - 255;
        }
        if (G > 255) {
            G = G - 255;
        }
        if (B > 255) {
            B = B - 255;
        }

        return new Color(R, G, B);
    }
}
