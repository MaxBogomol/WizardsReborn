package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.common.network.ArcanemiconOfferingEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ArcanemiconOfferingKnowledge extends Knowledge {

    public ArcanemiconOfferingKnowledge(String id, boolean articles, int points) {
        super(id, articles, points);
    }

    @Override
    public boolean hasToast() {
        return false;
    }

    @Override
    public boolean hasAllAward() {
        return false;
    }

    public boolean canReceived(Player player, List<ItemStack> items) {
        if (super.canReceived()) {
            return player.getInventory().getFreeSlot() > -1;
        }

        return false;
    }

    @Override
    public void award(Player player) {
        if (!KnowledgeUtils.isKnowledge(player, RegisterKnowledges.ARCANEMICON)) {
            player.sendSystemMessage(Component.literal("<").append(
                            Component.translatable("message.wizards_reborn.someone").withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, 123, 73, 109))))
                    .append(Component.literal("> "))
                    .append(Component.translatable("message.wizards_reborn.arcanemicon_offering").withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, 251, 179, 176)))));
            player.getInventory().add(new ItemStack(WizardsReborn.ARCANEMICON.get()));
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 2.0f, 0.05f);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 2.0f, 2.0f);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 2.0f, 0.05f);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), WizardsReborn.SPELL_CAST_SOUND.get(), SoundSource.PLAYERS, 2.0f, 0.85f);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), WizardsReborn.SPELL_RELOAD_SOUND.get(), SoundSource.PLAYERS, 0.25f, 2.0f);
            PacketHandler.sendToTracking(player.level(), player.getOnPos(), new ArcanemiconOfferingEffectPacket((float) player.getX(), (float) player.getY() + 1f, (float) player.getZ()));
        }
    }
}
