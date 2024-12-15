package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.knowledge.ArcanemiconOfferingMessagePacket;
import mod.maxbogomol.wizards_reborn.common.network.knowledge.ArcanemiconOfferingPacket;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornKnowledges;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
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
            if (WizardsRebornConfig.ARCANEMICON_OFFERING.get()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    if (serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.PLAY_TIME) > WizardsRebornConfig.ARCANEMICON_OFFERING_TICKS.get()) {
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
            player.getInventory().add(new ItemStack(WizardsRebornItems.ARCANEMICON.get()));
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), WizardsRebornSounds.ARCANEMICON_OFFERING.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            WizardsRebornPacketHandler.sendToTracking(player.level(), player.getOnPos(), new ArcanemiconOfferingPacket(player.position().add(0, 1, 0)));
            WizardsRebornPacketHandler.sendTo(player, new ArcanemiconOfferingMessagePacket());
        }
    }
}
