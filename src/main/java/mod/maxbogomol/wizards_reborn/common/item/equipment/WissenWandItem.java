package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.common.network.PacketUtils;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenTranslatorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WissenWandItem extends Item {
    public WissenWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!slotChanged) {
            return false;
        }
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();

        if(!world.isRemote) {
            TileEntity tileentity = world.getTileEntity(context.getPos());

            if (tileentity instanceof IWissenTileEntity) {
                CompoundNBT nbt = stack.getTag();
                if (nbt==null) {
                    nbt = new CompoundNBT();
                    stack.setTag(nbt);
                }

                if (!nbt.contains("block")) {
                    nbt.putBoolean("block", false);
                }

                if (!nbt.getBoolean("block")) {
                    nbt.putInt("blockX", context.getPos().getX());
                    nbt.putInt("blockY", context.getPos().getY());
                    nbt.putInt("blockZ", context.getPos().getZ());
                    nbt.putBoolean("block", true);
                    nbt.putBoolean("sneak", context.getPlayer().isSneaking());
                } else {
                    if (tileentity instanceof WissenTranslatorTileEntity) {
                        WissenTranslatorTileEntity translator = (WissenTranslatorTileEntity) tileentity;
                        if (!context.getPlayer().isSneaking()) {
                            if (nbt.getBoolean("sneak")) {
                                BlockPos blockPos = new BlockPos(nbt.getInt("blockX"), nbt.getInt("blockY"), nbt.getInt("blockZ"));
                                TileEntity oldTileentity = world.getTileEntity(blockPos);
                                if (oldTileentity instanceof IWissenTileEntity) {
                                    IWissenTileEntity wissenTileentity = (IWissenTileEntity) oldTileentity;
                                    if ((!translator.isSameFromAndTo(blockPos)) && (wissenTileentity.canConnectReceiveWissen())) {
                                        translator.blockFromX = nbt.getInt("blockX");
                                        translator.blockFromY = nbt.getInt("blockY");
                                        translator.blockFromZ = nbt.getInt("blockZ");
                                        translator.isFromBlock = true;
                                        nbt.putBoolean("block", false);
                                        nbt.putBoolean("sneak", false);
                                        PacketUtils.SUpdateTileEntityPacket(translator);
                                    }
                                }
                            } else {
                                BlockPos blockPos = new BlockPos(nbt.getInt("blockX"), nbt.getInt("blockY"), nbt.getInt("blockZ"));
                                TileEntity oldTileentity = world.getTileEntity(blockPos);
                                if (oldTileentity instanceof IWissenTileEntity) {
                                    IWissenTileEntity wissenTileentity = (IWissenTileEntity) oldTileentity;
                                    if ((!translator.isSameFromAndTo(blockPos)) && (wissenTileentity.canConnectSendWissen())) {
                                        translator.blockToX = nbt.getInt("blockX");
                                        translator.blockToY = nbt.getInt("blockY");
                                        translator.blockToZ = nbt.getInt("blockZ");
                                        translator.isToBlock = true;
                                        nbt.putBoolean("block", false);
                                        nbt.putBoolean("sneak", false);
                                        PacketUtils.SUpdateTileEntityPacket(translator);
                                    }
                                }
                            }
                        } else {
                            translator.isFromBlock = false;
                            translator.isToBlock = false;
                            nbt.putBoolean("block", false);
                            nbt.putBoolean("sneak", false);
                            PacketUtils.SUpdateTileEntityPacket(translator);
                        }
                    }
                }

                stack.setTag(nbt);
            }
        }

        return super.onItemUseFirst(stack, context);
    }
}
