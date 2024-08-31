package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import mod.maxbogomol.wizards_reborn.common.item.ArcanumItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class KnowledgeSrollItem extends ArcanumItem {

    public KnowledgeSrollItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide()) {
            CompoundTag nbt = stack.getOrCreateTag();
            if (!nbt.contains("knowledges")) {
                ListTag knowledges = new ListTag();
                for (Knowledge knowledge : Knowledges.getKnowledges()) {
                    if (KnowledgeUtil.isKnowledge(player, knowledge)) {
                        knowledges.add(StringTag.valueOf(knowledge.getId()));
                    }
                }
                nbt.put("knowledges", knowledges);
                nbt.putUUID("player", player.getUUID());
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0f, 1.2f);
            } else {
                ListTag knowledges = nbt.getList("knowledges", Tag.TAG_STRING);
                for (int i = 0; i < knowledges.size(); i++) {
                    Knowledge knowledge = Knowledges.getKnowledge(knowledges.getString(i));
                    if (knowledge != null) KnowledgeUtil.addKnowledgeFromScroll(player, knowledge);
                }

                if (!player.isCreative()) {
                    stack.setCount(stack.getCount() - 1);
                }

                if (nbt.contains("player")) {
                    Player playerFrom = world.getPlayerByUUID(nbt.getUUID("player"));
                    if (playerFrom != null) {
                        player.sendSystemMessage(Component.translatable("message.wizards_reborn.knowledge_scroll", playerFrom.getName()).withStyle(ChatFormatting.GRAY));
                    }
                }
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0f, 1.5f);
            }
        }

        return InteractionResultHolder.success(stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("player")) {
            Player player = world.getPlayerByUUID(nbt.getUUID("player"));
            if (player != null) {
                list.add(Component.translatable("lore.wizards_reborn.knowledge_scroll", player.getName()).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public static boolean hasKnowledge(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.contains("knowledges");
    }
}
