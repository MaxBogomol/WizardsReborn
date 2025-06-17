package mod.maxbogomol.wizards_reborn.api.knowledge;

import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.knowledge.KnowledgeUpdatePacket;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class Echo {
    public String id;

    public Random random = new Random();

    public Echo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void tick(Player player, EchoStack stack) {
        if (!player.level().isClientSide()) {
            stack.tick++;
            if (stack.tick > getMaxTick(player)) {
                remove(player, stack);
            }
        }
    }

    public int getMaxTick(Player player) {
        return 20;
    }

    public static void remove(Player player, EchoStack stack) {
        if (!player.level().isClientSide()) {
            KnowledgeUtil.removeEcho(player, stack);
        }
    }

    public void update(Player player) {
        if (!player.level().isClientSide()) {
            WizardsRebornPacketHandler.sendTo(player, new KnowledgeUpdatePacket(player));
        }
    }
}
