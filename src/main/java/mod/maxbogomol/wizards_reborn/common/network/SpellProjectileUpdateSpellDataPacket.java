package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SpellProjectileUpdateSpellDataPacket {
    private final UUID uuid;
    private final CompoundTag tag;

    public SpellProjectileUpdateSpellDataPacket(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public static void encode(SpellProjectileUpdateSpellDataPacket object, FriendlyByteBuf buffer) {
        buffer.writeUUID(object.uuid);
        buffer.writeNbt(object.tag);
    }

    public static SpellProjectileUpdateSpellDataPacket decode(FriendlyByteBuf buffer) {
        return new SpellProjectileUpdateSpellDataPacket(buffer.readUUID(), buffer.readNbt());
    }

    public static void handle(SpellProjectileUpdateSpellDataPacket packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    Entity entity = ((ClientLevel) world).entityStorage.getEntityGetter().get(packet.uuid);
                    if (entity instanceof SpellProjectileEntity projectile) {
                        projectile.setSpellData(packet.tag);
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}