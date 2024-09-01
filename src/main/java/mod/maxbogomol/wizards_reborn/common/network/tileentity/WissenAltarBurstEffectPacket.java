package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class WissenAltarBurstEffectPacket extends PositionEffectPacket {

    public WissenAltarBurstEffectPacket(double posX, double posY, double posZ) {
        super(posX, posY, posZ);
    }

    public WissenAltarBurstEffectPacket(BlockPos pos) {
        super(pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                .setScaleData(GenericParticleData.create(0.3f, 0).build())
                .setLifetime(20)
                .randomVelocity(0.05f)
                .repeat(level, posX + 0.5F, posY + 1.3125F, posZ + 0.5F, 20);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .randomSpin(0.5f)
                .setLifetime(30)
                .randomVelocity(0.05f)
                .repeat(level, posX + 0.5F, posY + 1.3125F, posZ + 0.5F, 10);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .randomSpin(0.5f)
                .setLifetime(30)
                .randomVelocity(0.05f)
                .repeat(level, posX + 0.5F, posY + 1.3125F, posZ + 0.5F, 10);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WissenAltarBurstEffectPacket.class, WissenAltarBurstEffectPacket::encode, WissenAltarBurstEffectPacket::decode, WissenAltarBurstEffectPacket::handle);
    }

    public static WissenAltarBurstEffectPacket decode(FriendlyByteBuf buf) {
        return decode(WissenAltarBurstEffectPacket::new, buf);
    }
}
