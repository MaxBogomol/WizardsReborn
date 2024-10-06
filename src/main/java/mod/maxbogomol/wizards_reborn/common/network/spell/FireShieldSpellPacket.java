package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
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

public class FireShieldSpellPacket extends PositionColorClientPacket {

    public FireShieldSpellPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public FireShieldSpellPacket(Vec3 pos, Color color) {
        super(pos, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.4f).build())
                .setLifetime(60)
                .randomVelocity(0.05f)
                .repeat(level, x, y, z, 15, 0.6f);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(0.979f, 0.912f, 0.585f).build())
                .setTransparencyData(GenericParticleData.create(0.6f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.3f).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                .setLifetime(80)
                .randomVelocity(0.085f)
                .randomOffset(0.1f)
                .repeat(level, x, y, z, 15, 0.6f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, FireShieldSpellPacket.class, FireShieldSpellPacket::encode, FireShieldSpellPacket::decode, FireShieldSpellPacket::handle);
    }

    public static FireShieldSpellPacket decode(FriendlyByteBuf buf) {
        return decode(FireShieldSpellPacket::new, buf);
    }
}