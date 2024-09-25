package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class CrystalGrowthBreakPacket extends PositionColorClientPacket {
    protected final float i;

    public CrystalGrowthBreakPacket(double x, double y, double z, float r, float g, float b, float a, float i) {
        super(x, y, z, r, g, b, a);
        this.i = i;
    }

    public CrystalGrowthBreakPacket(BlockPos pos, Color color, float i) {
        super(pos, color);
        this.i = i;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0.35f, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.035f)
                .randomOffset(0.25f)
                .repeat(level, x + 0.5f, y + 0.5f, z + 0.5f, (int) i);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, CrystalGrowthBreakPacket.class, CrystalGrowthBreakPacket::encode, CrystalGrowthBreakPacket::decode, CrystalGrowthBreakPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.r);
        buf.writeFloat(this.g);
        buf.writeFloat(this.b);
        buf.writeFloat(this.a);
        buf.writeFloat(this.i);
    }

    public static CrystalGrowthBreakPacket decode(FriendlyByteBuf buf) {
        return new CrystalGrowthBreakPacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }
}
