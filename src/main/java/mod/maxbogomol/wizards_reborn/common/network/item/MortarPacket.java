package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpriteParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.ItemParticleOptions;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.TwoPositionClientPacket;
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

public class MortarPacket  extends TwoPositionClientPacket {
    protected final ItemStack itemStack;

    public MortarPacket(double x1, double y1, double z1, double x2, double y2, double z2, ItemStack itemStack) {
        super(x1, y1, z1, x2, y2, z2);
        this.itemStack = itemStack;
    }

    public MortarPacket(Vec3 vec1, Vec3 vec2, ItemStack itemStack) {
        super(vec1, vec2);
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
                .setTransparencyData(GenericParticleData.create(0.3f, 0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.075f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                .setLifetime(20)
                .randomVelocity(0.15f)
                .addVelocity(x2, y2, z2)
                .randomOffset(0.15f)
                .setFriction(0.97f)
                .setGravity(0.5f)
                .repeat(level, x1, y1, z1, 20);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, MortarPacket.class, MortarPacket::encode, MortarPacket::decode, MortarPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x1);
        buf.writeDouble(y1);
        buf.writeDouble(z1);
        buf.writeDouble(x2);
        buf.writeDouble(y2);
        buf.writeDouble(z2);
        buf.writeItem(itemStack);
    }

    public static MortarPacket decode(FriendlyByteBuf buf) {
        return new MortarPacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readItem());
    }
}
