package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemType;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.List;

public class WissenRingItem extends BaseWissenCurioItem {

    public WissenRingItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public int getMaxWissen(ItemStack stack) {
        return 5000;
    }

    @Override
    public WissenItemType getWissenItemType() {
        return WissenItemType.NONE;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        WissenItemUtil.existWissen(stack);
        return stack;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!slotContext.entity().level().isClientSide()) {
            WissenItemUtil.existWissen(stack);

            if (slotContext.entity() instanceof Player player) {
                if (player.tickCount % 8 == 0) {
                    WissenItemUtil.addWissen(stack, 1, getMaxWissen(stack));
                }

                List<ItemStack> itemsAdd = WissenUtil.getWissenItemsActive(player);
                List<ItemStack> itemsUsing = WissenUtil.getWissenItemsUsing(itemsAdd);
                itemsUsing.addAll(WissenUtil.getWissenItemsStorage(itemsAdd));

                for (ItemStack item : itemsUsing) {
                    if (item.getItem() instanceof IWissenItem wissenItem) {
                        int wissenRemain = WissenUtil.getRemoveWissenRemain(WissenItemUtil.getWissen(stack), 1);
                        wissenRemain = 1 - wissenRemain;
                        WissenItemUtil.existWissen(item);
                        int itemWissenRemain = WissenItemUtil.getAddWissenRemain(item, wissenRemain, wissenItem.getMaxWissen(item));
                        wissenRemain = wissenRemain - itemWissenRemain;
                        if (wissenRemain > 0) {
                            WissenItemUtil.addWissen(item, wissenRemain, wissenItem.getMaxWissen(item));
                            WissenItemUtil.removeWissen(stack, wissenRemain);
                        }
                    }
                }
            }
        }
    }
}
