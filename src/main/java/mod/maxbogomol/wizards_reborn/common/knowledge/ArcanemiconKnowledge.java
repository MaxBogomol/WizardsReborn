package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
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
    public void award(Player player) {
        if (!KnowledgeUtils.isKnowledge(player, RegisterKnowledges.ARCANEMICON_OFFERING)) {
            player.sendSystemMessage(Component.literal("<").append(
                            Component.translatable("message.wizards_reborn.someone").withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, 123, 73, 109))))
                    .append(Component.literal("> "))
                    .append(Component.translatable("message.wizards_reborn.arcanemicon").withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, 251, 179, 176)))));
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), WizardsReborn.SPELL_RELOAD_SOUND.get(), SoundSource.PLAYERS, 0.25f, 2.0f);
        }
    }
}
