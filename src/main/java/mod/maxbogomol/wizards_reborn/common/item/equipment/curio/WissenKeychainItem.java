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

public class WissenKeychainItem extends BaseWissenCurioItem {

    public WissenKeychainItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public int getMaxWissen() {
        return 25000;
    }

    @Override
    public WissenItemType getWissenItemType() {
        return WissenItemType.STORAGE;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();;
        WissenItemUtils.existWissen(stack);
        return stack;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!slotContext.entity().level().isClientSide()) {
            WissenItemUtils.existWissen(stack);

            if (slotContext.entity() instanceof Player player) {
                List<ItemStack> itemsAdd = WissenUtils.getWissenItemsActive(player);
                List<ItemStack> itemsUsing = WissenUtils.getWissenItemsUsing(itemsAdd);

                for (ItemStack item : itemsUsing) {
                    if (item.getItem() instanceof IWissenItem wissenItem) {
                        int wissenRemain = WissenUtils.getRemoveWissenRemain(WissenItemUtils.getWissen(stack), 10);
                        wissenRemain = 10 - wissenRemain;
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
