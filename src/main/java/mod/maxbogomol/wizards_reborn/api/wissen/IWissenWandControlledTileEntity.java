package mod.maxbogomol.wizards_reborn.api.wissen;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IWissenWandControlledTileEntity {
    boolean wissenWandReceiveConnect(ItemStack stack, UseOnContext context, BlockEntity tile);
    boolean wissenWandSendConnect(ItemStack stack, UseOnContext context, BlockEntity tile);
    boolean wissenWandReload(ItemStack stack, UseOnContext context, BlockEntity tile);
}
