package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ArcanemiconItem extends Item {
    public ArcanemiconItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (world.isRemote) {
            Minecraft.getInstance().player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            Minecraft.getInstance().displayGuiScreen(new ArcanemiconGui());
        }

        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }
}
