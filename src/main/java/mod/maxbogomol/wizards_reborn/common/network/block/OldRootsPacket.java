package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpriteParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.ItemParticleOptions;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class OldRootsPacket extends PositionClientPacket {
    protected final ItemStack itemStack;

    public OldRootsPacket(double x, double y, double z, ItemStack itemStack) {
        super(x, y, z);
        this.itemStack = itemStack;
    }

    public OldRootsPacket(Vec3 vec, ItemStack itemStack) {
        super(vec);
        this.itemStack = itemStack;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ItemParticleOptions options = new ItemParticleOptions(FluffyFurParticles.ITEM.get(), itemStack);
        ParticleBuilder.create(options)
                .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
                .setColorData(ColorParticleData.create(Color.WHITE).build())
                .setTransparencyData(GenericParticleData.create(1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.075f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                .setLifetime(20, 20)
                .randomVelocity(0.05f)
                .randomOffset(0.25f)
                .repeat(level, x, y, z, 35);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, OldRootsPacket.class, OldRootsPacket::encode, OldRootsPacket::decode, OldRootsPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeItem(itemStack);
    }

    public static OldRootsPacket decode(FriendlyByteBuf buf) {
        return new OldRootsPacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readItem());
    }
}
