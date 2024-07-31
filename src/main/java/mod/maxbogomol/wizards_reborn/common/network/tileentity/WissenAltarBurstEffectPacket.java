package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
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
        Level level = WizardsReborn.proxy.getWorld();

        Particles.create(WizardsReborn.WISP_PARTICLE)
                .randomVelocity(0.05f, 0.05f, 0.05f)
                .setAlpha(0.125f, 0).setScale(0.3f, 0)
                .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                .setLifetime(20)
                .repeat(level, posX + 0.5F, posY + 1.3125F, posZ + 0.5F, 20);
        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                .randomVelocity(0.05f, 0.05f, 0.05f)
                .setAlpha(0.25f, 0).setScale(0.1f, 0)
                .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                .setLifetime(30)
                .randomSpin(0.5f)
                .repeat(level, posX + 0.5F, posY + 1.3125F, posZ + 0.5F, 20);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WissenAltarBurstEffectPacket.class, WissenAltarBurstEffectPacket::encode, WissenAltarBurstEffectPacket::decode, WissenAltarBurstEffectPacket::handle);
    }

    public static WissenAltarBurstEffectPacket decode(FriendlyByteBuf buf) {
        return decode(WissenAltarBurstEffectPacket::new, buf);
    }
}
