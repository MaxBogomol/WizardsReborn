package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetWissenWandModePacket {
    private final boolean hand;
    private final int mode;

    public SetWissenWandModePacket(boolean hand, int mode) {
        this.hand = hand;
        this.mode = mode;
    }

    public static SetWissenWandModePacket decode(FriendlyByteBuf buf) {
        return new SetWissenWandModePacket(buf.readBoolean(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(hand);
        buf.writeInt(mode);
    }

    public static void handle(SetWissenWandModePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (!msg.hand) {
                    stack = player.getItemInHand(InteractionHand.OFF_HAND);
                }

                WissenWandItem.setMode(stack, msg.mode);
                WissenWandItem.setBlock(stack, false);
                if (msg.mode == 3 || msg.mode == 0) {
                    WissenWandItem.setBlock(stack, true);
                }

                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                player.serverLevel().playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), WizardsRebornSounds.CRYSTAL_RESONATE.get(), SoundSource.PLAYERS, 1.0f, 1.2f);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
