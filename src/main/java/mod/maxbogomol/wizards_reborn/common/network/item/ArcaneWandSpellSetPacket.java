package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ArcaneWandSpellSetPacket extends ServerPacket {
    protected final boolean hand;
    protected final String spell;
    protected final boolean sound;

    public ArcaneWandSpellSetPacket(boolean hand, String spell) {
        this.hand = hand;
        this.spell = spell;
        this.sound = true;
    }

    public ArcaneWandSpellSetPacket(boolean hand, String spell, boolean sound) {
        this.hand = hand;
        this.spell = spell;
        this.sound = sound;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!hand) stack = player.getItemInHand(InteractionHand.OFF_HAND);

        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString("spell", spell);
        if (sound) player.serverLevel().playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), WizardsRebornSounds.CRYSTAL_RESONATE.get(), SoundSource.PLAYERS, 1.0f, 1.5f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ArcaneWandSpellSetPacket.class, ArcaneWandSpellSetPacket::encode, ArcaneWandSpellSetPacket::decode, ArcaneWandSpellSetPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(hand);
        buf.writeUtf(spell);
        buf.writeBoolean(sound);
    }

    public static ArcaneWandSpellSetPacket decode(FriendlyByteBuf buf) {
        return new ArcaneWandSpellSetPacket(buf.readBoolean(), buf.readUtf(), buf.readBoolean());
    }
}
