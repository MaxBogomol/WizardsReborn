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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class WaterRaySpellPacket extends PositionColorClientPacket {

    public WaterRaySpellPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public WaterRaySpellPacket(Vec3 pos, Color color) {
        super(pos, color);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                .setScaleData(GenericParticleData.create(0.2f, 0).build())
                .setLifetime(60)
                .setGravity(1f)
                .randomVelocity(0.085f, 0.0625f, 0.085f)
                .addVelocity(0, 0.3f, 0)
                .repeat(level, x, y, z, 15, 0.6f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WaterRaySpellPacket.class, WaterRaySpellPacket::encode, WaterRaySpellPacket::decode, WaterRaySpellPacket::handle);
    }

    public static WaterRaySpellPacket decode(FriendlyByteBuf buf) {
        return decode(WaterRaySpellPacket::new, buf);
    }
}