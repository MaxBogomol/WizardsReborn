package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpriteParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.GenericParticleOptions;
import mod.maxbogomol.fluffy_fur.client.particle.options.ItemParticleOptions;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;

public class JewelerTableBurstPacket extends ClientPacket {
    private final BlockPos pos;
    private final float X, Y, Z;
    private final float velX, velY;
    private final float r, g, b;
    private final boolean isParticle;
    private final ItemStack stack;

    private static final Random random = new Random();

    public JewelerTableBurstPacket(BlockPos pos, float X, float Y, float Z, float velX, float velY, float r, float g, float b, boolean isParticle, ItemStack stack) {
        this.pos = pos;

        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.velX = velX;
        this.velY = velY;

        this.r = r;
        this.g = b;
        this.b = b;

        this.isParticle = isParticle;
        this.stack = stack;
    }

    public JewelerTableBurstPacket(BlockPos pos, float X, float Y, float Z, float velX, float velY, float r, float g, float b, ItemStack stack) {
        this.pos = pos;

        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.velX = velX;
        this.velY = velY;

        this.r = r;
        this.g = g;
        this.b = b;

        this.isParticle = true;
        this.stack = stack;
    }

    public JewelerTableBurstPacket(BlockPos pos, float X, float Y, float Z, ItemStack stack) {
        this.pos = pos;

        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.velX = 0;
        this.velY = 0;

        this.r = 0;
        this.g = 0;
        this.b = 0;

        this.isParticle = false;
        this.stack = stack;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                .setScaleData(GenericParticleData.create(0.2f, 0).build())
                .setLifetime(20)
                .randomVelocity(0.05f)
                .repeat(level, pos.getX() + X, pos.getY() + Y + 0.1875f, pos.getZ() + Z, 20);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.05f)
                .repeat(level, pos.getX() + X, pos.getY() + Y + 0.1875f, pos.getZ() + Z, 10);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.05f)
                .repeat(level, pos.getX() + X, pos.getY() + Y + 0.1875f, pos.getZ() + Z, 10);

        for (int i = 0; i < 25; i++) {
            if (random.nextFloat() < 0.6) {
                float x = 0F;
                float y = 0F;

                if (velX == 0) {
                    x = (float) ((random.nextDouble() - 0.5D) / 20);
                } else {
                    x = (float) ((random.nextDouble() / 20) * velX);
                }

                if (velY == 0) {
                    y = (float) ((random.nextDouble() - 0.5D) / 20);
                } else {
                    y = (float) ((random.nextDouble() / 20) * velY);
                }

                boolean item = random.nextBoolean();
                if (!isParticle) item = true;
                ItemParticleOptions options = new ItemParticleOptions(FluffyFurParticles.ITEM.get(), stack);
                ParticleBuilder.create(item ? options : new GenericParticleOptions(FluffyFurParticles.SQUARE.get()))
                        .setRenderType(item ? FluffyFurRenderTypes.TRANSLUCENT_BLOCK_PARTICLE : FluffyFurRenderTypes.ADDITIVE_PARTICLE)
                        .setColorData(ColorParticleData.create(item ? Color.WHITE : new Color(r, g, b)).build())
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setScaleData(GenericParticleData.create(0.025f, 0.06f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                        .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                        .setLifetime(30)
                        .addVelocity(x, (random.nextDouble() / 30), y)
                        .spawn(level, pos.getX() + X, pos.getY() + Y - 0.125f, pos.getZ() + Z);
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, JewelerTableBurstPacket.class, JewelerTableBurstPacket::encode, JewelerTableBurstPacket::decode, JewelerTableBurstPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);
        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeBoolean(isParticle);
        buf.writeItemStack(stack, false);
    }

    public static JewelerTableBurstPacket decode(FriendlyByteBuf buf) {
        return new JewelerTableBurstPacket(buf.readBlockPos(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readBoolean(), buf.readItem());
    }
}