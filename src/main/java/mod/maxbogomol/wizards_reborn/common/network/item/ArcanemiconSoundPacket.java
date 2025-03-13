package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ArcanemiconSoundPacket extends ServerPacket {
    protected final double x;
    protected final double y;
    protected final double z;

    public ArcanemiconSoundPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ArcanemiconSoundPacket(Vec3 vec) {
        this.x = vec.x();
        this.y = vec.y();
        this.z = vec.z();
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        player.serverLevel().playSound(null, x, y, z, SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ArcanemiconSoundPacket.class, ArcanemiconSoundPacket::encode, ArcanemiconSoundPacket::decode, ArcanemiconSoundPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public static ArcanemiconSoundPacket decode(FriendlyByteBuf buf) {
        return new ArcanemiconSoundPacket(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }
}
