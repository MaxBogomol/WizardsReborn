package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class SteamBreakPacket extends PositionClientPacket {
    protected final float i;

    public SteamBreakPacket(double x, double y, double z, float i) {
        super(x, y, z);
        this.i = i;
    }

    public SteamBreakPacket(BlockPos pos, float i) {
        super(pos);
        this.i = i;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.SMOKE)
                .setColorData(ColorParticleData.create(Color.WHITE).build())
                .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                .setLifetime(30)
                .randomVelocity(0.015f)
                .flatRandomOffset(0.25f, 0.25f, 0.25f)
                .repeat(level, x + 0.5f, y + 0.5F, z + 0.5f, (int) i);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SteamBreakPacket.class, SteamBreakPacket::encode, SteamBreakPacket::decode, SteamBreakPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.i);
    }

    public static SteamBreakPacket decode(FriendlyByteBuf buf) {
        return new SteamBreakPacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat());
    }
}
