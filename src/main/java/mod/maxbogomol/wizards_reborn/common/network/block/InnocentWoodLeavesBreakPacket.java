package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class InnocentWoodLeavesBreakPacket extends PositionClientPacket {

    public InnocentWoodLeavesBreakPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public InnocentWoodLeavesBreakPacket(BlockPos pos) {
        super(pos);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(0.968f, 0.968f, 0.968f).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                .setScaleData(GenericParticleData.create(0.2f, 0).build())
                .setLifetime(20)
                .randomVelocity(0.1)
                .randomOffset(0.05)
                .repeat(level, x + 0.5f, y + 0.5f, z + 0.5f, 5);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, InnocentWoodLeavesBreakPacket.class, InnocentWoodLeavesBreakPacket::encode, InnocentWoodLeavesBreakPacket::decode, InnocentWoodLeavesBreakPacket::handle);
    }

    public static InnocentWoodLeavesBreakPacket decode(FriendlyByteBuf buf) {
        return decode(InnocentWoodLeavesBreakPacket::new, buf);
    }
}
