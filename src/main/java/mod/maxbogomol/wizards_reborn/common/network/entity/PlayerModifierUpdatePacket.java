package mod.maxbogomol.wizards_reborn.common.network.entity;

import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.capability.IPlayerModifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.UUID;
import java.util.function.Supplier;

public class PlayerModifierUpdatePacket extends ClientPacket {
    protected final UUID uuid;
    protected CompoundTag tag;

    public PlayerModifierUpdatePacket(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public PlayerModifierUpdatePacket(Player entity) {
        this.uuid = entity.getUUID();
        entity.getCapability(IPlayerModifier.INSTANCE, null).ifPresent((k) -> {
            this.tag = ((INBTSerializable<CompoundTag>)k).serializeNBT();
        });
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        Player player = level.getPlayerByUUID(uuid);
        if (player != null) {
            player.getCapability(IPlayerModifier.INSTANCE, null).ifPresent((k) -> {
                ((INBTSerializable<CompoundTag>)k).deserializeNBT(tag);
            });
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, PlayerModifierUpdatePacket.class, PlayerModifierUpdatePacket::encode, PlayerModifierUpdatePacket::decode, PlayerModifierUpdatePacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeNbt(tag);
    }

    public static PlayerModifierUpdatePacket decode(FriendlyByteBuf buf) {
        return new PlayerModifierUpdatePacket(buf.readUUID(), buf.readNbt());
    }
}