package mod.maxbogomol.wizards_reborn.common.item.equipment;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.awt.*;

public interface IBagItem {
    Color getColor(ItemStack stack);
    void openBag(Level level, Player player, ItemStack stack);
}
