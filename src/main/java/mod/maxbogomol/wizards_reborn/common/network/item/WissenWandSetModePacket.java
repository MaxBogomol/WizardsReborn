package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
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
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class WissenWandSetModePacket extends ServerPacket {
    protected final boolean hand;
    protected final int mode;
    protected final boolean sound;

    public WissenWandSetModePacket(boolean hand, int mode) {
        this.hand = hand;
        this.mode = mode;
        this.sound = true;
    }

    public WissenWandSetModePacket(boolean hand, int mode, boolean sound) {
        this.hand = hand;
        this.mode = mode;
        this.sound = sound;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!hand) stack = player.getItemInHand(InteractionHand.OFF_HAND);

        WissenWandItem.setMode(stack, mode);
        WissenWandItem.setBlock(stack, false);
        if (mode == 3 || mode == 0) {
            WissenWandItem.setBlock(stack, true);
        }

        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        if (sound) player.serverLevel().playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), WizardsRebornSounds.CRYSTAL_RESONATE.get(), SoundSource.PLAYERS, 1.0f, 1.2f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WissenWandSetModePacket.class, WissenWandSetModePacket::encode, WissenWandSetModePacket::decode, WissenWandSetModePacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(hand);
        buf.writeInt(mode);
        buf.writeBoolean(sound);
    }

    public static WissenWandSetModePacket decode(FriendlyByteBuf buf) {
        return new WissenWandSetModePacket(buf.readBoolean(), buf.readInt(), buf.readBoolean());
    }
}
