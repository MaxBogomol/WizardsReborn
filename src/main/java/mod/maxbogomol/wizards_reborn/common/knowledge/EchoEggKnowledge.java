package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.api.knowledge.EchoStack;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEchoes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class EchoEggKnowledge extends ItemKnowledge {

    public EchoEggKnowledge(String id, boolean articles, int points, Item item) {
        super(id, articles, points, item);
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
        if (!KnowledgeUtil.isEcho(player, WizardsRebornEchoes.EGG)) {
            KnowledgeUtil.addEcho(player, new EchoStack(WizardsRebornEchoes.EGG));
        }
    }
}
