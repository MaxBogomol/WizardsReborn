package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.common.config.ServerConfig;
import mod.maxbogomol.wizards_reborn.common.network.ArcanemiconOfferingEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornKnowledges;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

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

    @Override
    public boolean hasScrollAward() {
        return false;
    }

    @Override
    public boolean canReceived(Player player) {
        if (!player.level().isClientSide()) {
            if (ServerConfig.ARCANEMICON_OFFERING.get()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    if (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.PLAY_TIME) > ServerConfig.ARCANEMICON_OFFERING_TICKS.get()) {
                        return player.getInventory().getFreeSlot() > -1;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void award(Player player) {
        if (!KnowledgeUtil.isKnowledge(player, WizardsRebornKnowledges.ARCANEMICON)) {
            player.sendSystemMessage(Component.literal("<").append(
                            Component.translatable("message.wizards_reborn.someone").withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 123, 73, 109))))
                    .append(Component.literal("> "))
                    .append(Component.translatable("message.wizards_reborn.arcanemicon_offering").withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 251, 179, 176)))));
            player.getInventory().add(new ItemStack(WizardsRebornItems.ARCANEMICON.get()));
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), WizardsRebornSounds.ARCANEMICON_OFFERING.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            PacketHandler.sendToTracking(player.level(), player.getOnPos(), new ArcanemiconOfferingEffectPacket(player.position().add(0, 1, 0)));
        }
    }
}
