package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornKnowledges;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
            player.sendSystemMessage(Component.literal("<").append(
                            Component.translatable("message.wizards_reborn.someone").withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 123, 73, 109))))
                    .append(Component.literal("> "))
                    .append(Component.translatable("message.wizards_reborn.arcanemicon").withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 251, 179, 176)))));
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), WizardsRebornSounds.SPELL_RELOAD.get(), SoundSource.PLAYERS, 0.25f, 2.0f);
        }
    }
}
