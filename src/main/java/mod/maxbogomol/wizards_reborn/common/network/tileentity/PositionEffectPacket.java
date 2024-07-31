package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public abstract class PositionEffectPacket extends ClientPacket {
    protected final double posX;
    protected final double posY;
    protected final double posZ;

    public PositionEffectPacket(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public PositionEffectPacket(BlockPos pos) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
    }

    public PositionEffectPacket(Vec3 vec) {
        this.posX = vec.x();
        this.posY = vec.y();
        this.posZ = vec.z();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    public static <T extends PositionEffectPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return provider.getPacket(posX, posY, posZ);
    }

    public interface PacketProvider<T extends PositionEffectPacket> {
        T getPacket(double posX, double posY, double posZ);
    }
}
