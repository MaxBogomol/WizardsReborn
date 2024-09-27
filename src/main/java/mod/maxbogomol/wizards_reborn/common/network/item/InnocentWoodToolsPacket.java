package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.network.ArcanemiconOfferingEffectPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class InnocentWoodToolsPacket extends PositionClientPacket {

    public InnocentWoodToolsPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public InnocentWoodToolsPacket(Vec3 vec) {
        super(vec);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.SMOKE)
                .setColorData(ColorParticleData.create(0.694F, 0.274F, 0.309F).build())
                .setTransparencyData(GenericParticleData.create(0, 0.5f).build())
                .setScaleData(GenericParticleData.create(0.5f, 0f).build())
                .setLifetime(30)
                .randomVelocity(0.05f)
                .randomOffset(0.125f)
                .repeat(level, x, y, z, 3);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ArcanemiconOfferingEffectPacket.class, ArcanemiconOfferingEffectPacket::encode, ArcanemiconOfferingEffectPacket::decode, ArcanemiconOfferingEffectPacket::handle);
    }

    public static ArcanemiconOfferingEffectPacket decode(FriendlyByteBuf buf) {
        return decode(ArcanemiconOfferingEffectPacket::new, buf);
    }
}
