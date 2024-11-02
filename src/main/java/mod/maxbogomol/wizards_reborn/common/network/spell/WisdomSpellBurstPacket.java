package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class WisdomSpellBurstPacket extends PositionColorClientPacket {

    public WisdomSpellBurstPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public WisdomSpellBurstPacket(Vec3 pos, Color color) {
        super(pos, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f).build())
                .setScaleData(GenericParticleData.create(0.2f, 0f).build())
                .setLifetime(15)
                .spawn(level, x, y, z);
        ParticleBuilder.create(FluffyFurParticles.STAR)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f).build())
                .setScaleData(GenericParticleData.create(0.2f, 0f).build())
                .setLifetime(10)
                .spawn(level, x, y, z);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WisdomSpellBurstPacket.class, WisdomSpellBurstPacket::encode, WisdomSpellBurstPacket::decode, WisdomSpellBurstPacket::handle);
    }

    public static WisdomSpellBurstPacket decode(FriendlyByteBuf buf) {
        return decode(WisdomSpellBurstPacket::new, buf);
    }
}