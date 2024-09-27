package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.common.item.ArcanumItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CreativeKnowledgeSrollItem extends ArcanumItem {
    public boolean isAncient;

    public CreativeKnowledgeSrollItem(Properties properties, boolean isAncient) {
        super(properties);
        this.isAncient = isAncient;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            if (isAncient) {
                KnowledgeUtil.removeAllKnowledge(player);
            } else {
                KnowledgeUtil.addAllKnowledge(player);
            }

            if (!player.isCreative()) {
                stack.shrink(1);
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0f, 1.5f);
        }

        return InteractionResultHolder.success(stack);
    }
}
