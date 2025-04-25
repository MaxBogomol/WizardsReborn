package mod.maxbogomol.wizards_reborn.api.knowledge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IItemKnowledge {
    boolean canReceived(Player player, ItemStack itemStack);
}
