package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.knowledge.ArcanemiconMessagePacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornKnowledges;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class ArcanemiconKnowledge extends ItemKnowledge {

    public ArcanemiconKnowledge(String id, boolean articles, int points, Item item) {
        super(id, articles, points, item);
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
    public void award(Player player) {
        if (!KnowledgeUtil.isKnowledge(player, WizardsRebornKnowledges.ARCANEMICON_OFFERING)) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), WizardsRebornSounds.SPELL_RELOAD.get(), SoundSource.PLAYERS, 0.25f, 2.0f);
            WizardsRebornPacketHandler.sendTo(player, new ArcanemiconMessagePacket());
        }
    }
}
