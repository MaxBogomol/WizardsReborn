package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;

public class ArcanemiconItem extends Item {
    public ArcanemiconItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
            Minecraft.getInstance().setScreen(new ArcanemiconGui());
        }

        return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
    }
}
