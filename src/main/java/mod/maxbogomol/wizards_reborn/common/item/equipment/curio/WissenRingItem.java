package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemType;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.List;

public class WissenRingItem extends BaseCurioItem implements IWissenItem {

    public WissenRingItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public int getMaxWissen() {
        return 5000;
    }

    @Override
    public WissenItemType getWissenItemType() {
        return WissenItemType.OFF;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        WissenItemUtils.existWissen(stack);
        return stack;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!slotContext.entity().level().isClientSide()) {
            WissenItemUtils.existWissen(stack);

            if (slotContext.entity() instanceof Player player) {
                if (player.tickCount % 8 == 0) {
                    WissenItemUtils.addWissen(stack, 1, getMaxWissen());
                }

                List<ItemStack> itemsAdd = WissenUtils.getWissenItemsActive(player);
                List<ItemStack> itemsUsing = WissenUtils.getWissenItemsUsing(itemsAdd);
                itemsUsing.addAll(WissenUtils.getWissenItemsStorage(itemsAdd));

                for (ItemStack item : itemsUsing) {
                    if (item.getItem() instanceof IWissenItem wissenItem) {
                        int wissenRemain = WissenUtils.getRemoveWissenRemain(WissenItemUtils.getWissen(stack), 1);
                        wissenRemain = 1 - wissenRemain;
                        WissenItemUtils.existWissen(item);
                        int itemWissenRemain = WissenItemUtils.getAddWissenRemain(item, wissenRemain, wissenItem.getMaxWissen());
                        wissenRemain = wissenRemain - itemWissenRemain;
                        if (wissenRemain > 0) {
                            WissenItemUtils.addWissen(item, wissenRemain, wissenItem.getMaxWissen());
                            WissenItemUtils.removeWissen(stack, wissenRemain);
                        }
                    }
                }
            }
        }
    }
}
