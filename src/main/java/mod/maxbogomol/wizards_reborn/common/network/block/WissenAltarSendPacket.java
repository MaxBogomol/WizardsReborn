package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class WissenAltarSendPacket extends PositionClientPacket {

    public WissenAltarSendPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public WissenAltarSendPacket(BlockPos pos) {
        super(pos);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                .setScaleData(GenericParticleData.create(0.05f, 0).build())
                .setLifetime(20)
                .addVelocity(((random.nextDouble() - 0.5D) / 100), -(random.nextDouble() / 40), ((random.nextDouble() - 0.5D) / 100))
                .spawn(level, x + 0.5f, y + 1.3125f, z + 0.5f);
        boolean square = random.nextFloat() < 0.3f;
        float i = square ? 0.5f : 1f;
        ParticleBuilder.create(square ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                .setScaleData(GenericParticleData.create(0.025f * i, 0.05f * i, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.2f).build())
                .setLifetime(30)
                .addVelocity(((random.nextDouble() - 0.5D) / 100), -(random.nextDouble() / 40), ((random.nextDouble() - 0.5D) / 100))
                .spawn(level, x + 0.5f, y + 1.3125f, z + 0.5f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WissenAltarSendPacket.class, WissenAltarSendPacket::encode, WissenAltarSendPacket::decode, WissenAltarSendPacket::handle);
    }

    public static WissenAltarSendPacket decode(FriendlyByteBuf buf) {
        return decode(WissenAltarSendPacket::new, buf);
    }
}
