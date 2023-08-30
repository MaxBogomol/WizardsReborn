package mod.maxbogomol.wizards_reborn.common.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class TESyncPacket {
    BlockPos pos;
    CompoundTag tag;

    public TESyncPacket(BlockPos pos, CompoundTag tag) {
        this.pos = pos;
        this.tag = tag;
    }

    public static void encode(TESyncPacket object, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(object.pos);
        buffer.writeNbt(object.tag);
    }

    public static TESyncPacket decode(FriendlyByteBuf buffer) {
        return new TESyncPacket(buffer.readBlockPos(), buffer.readNbt());
    }

    public static void handle(TESyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(new Runnable() {
            @Override
            public void run() {
                Level world;
                if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
                    world = Minecraft.getInstance().level;
                else {
                    if (ctx.get().getSender() == null) return;
                    world = ctx.get().getSender().level();
                }

                BlockEntity t = world.getBlockEntity(packet.pos);
                if (t != null) {
                    world.getBlockEntity(packet.pos).load(packet.tag);
                    world.getBlockEntity(packet.pos).setChanged();
                }
                ctx.get().setPacketHandled(true);
            }
        });
    }
}