package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

public class CreativeWissenKeychainItem extends WissenKeychainItem {

    public CreativeWissenKeychainItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxWissen() {
        return Integer.MAX_VALUE;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();;
        WissenItemUtils.existWissen(stack);
        WissenItemUtils.setWissen(stack, Integer.MAX_VALUE);
        return stack;
    }

    @Override
    public int getWissenTransfer() {
        return 1000000;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        WissenItemUtils.existWissen(stack);
        WissenItemUtils.setWissen(stack, Integer.MAX_VALUE);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        WissenItemUtils.existWissen(stack);
        WissenItemUtils.setWissen(stack, Integer.MAX_VALUE);
    }
}
