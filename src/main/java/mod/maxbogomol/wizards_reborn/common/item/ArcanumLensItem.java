package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.network.ArcanumLensBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public class ArcanumLensItem extends ArcanumItem {
    private static Random random = new Random();

    public ArcanumLensItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide()) {
            int wissen = random.nextInt(2000, 3000);

            List<ItemStack> items = WissenUtils.getWissenItems(player);
            List<ItemStack> itemsOff = WissenUtils.getWissenItemsOff(items);
            items.removeAll(itemsOff);

            for (ItemStack item : items) {
                if (item.getItem() instanceof IWissenItem wissenItem) {
                    WissenItemUtils.existWissen(item);
                    int itemWissenRemain = WissenItemUtils.getAddWissenRemain(item, wissen, wissenItem.getMaxWissen());
                    if (wissen - itemWissenRemain > 0) {
                        WissenItemUtils.addWissen(item, wissen - itemWissenRemain, wissenItem.getMaxWissen());
                        wissen = wissen - itemWissenRemain;
                    }
                }
            }

            if (!player.isCreative()) {
                stack.setCount(stack.getCount() - 1);
            }

            PacketHandler.sendToTracking(world, player.getOnPos(), new ArcanumLensBurstEffectPacket((float) player.getX(), (float) player.getY() + (player.getBbHeight() / 2), (float) player.getZ()));
            world.playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), WizardsReborn.WISSEN_BURST_SOUND.get(), SoundSource.PLAYERS, 0.5f, (float) (1.3f + ((random.nextFloat() - 0.5D) / 2)));
            world.playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 1f, (float) (0.75f + ((random.nextFloat() - 0.5D) / 2)));
        }

        player.getCooldowns().addCooldown(this, 50);

        return InteractionResultHolder.success(stack);
    }
}
