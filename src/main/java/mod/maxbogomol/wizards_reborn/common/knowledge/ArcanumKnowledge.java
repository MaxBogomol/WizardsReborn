package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.common.network.ArcanemiconToastPacket;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class ArcanumKnowledge extends ItemKnowledge {

    public ArcanumKnowledge(String id, boolean articles, int points, Item item) {
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
    public void award(Player player) {
        WizardsRebornPacketHandler.sendTo(player, new ArcanemiconToastPacket());
    }
}
