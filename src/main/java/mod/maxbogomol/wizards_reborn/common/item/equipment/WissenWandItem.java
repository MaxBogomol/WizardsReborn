package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalTileEntity;
import mod.maxbogomol.wizards_reborn.common.network.PacketUtils;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenTranslatorTileEntity;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

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

        if (player.isSneaking()) {
            CompoundNBT nbt = stack.getTag();
            if (nbt==null) {
                nbt = new CompoundNBT();
                stack.setTag(nbt);
            }

            if (!nbt.contains("mode")) {
                nbt.putInt("mode", 0);
            }

            nbt.putBoolean("block", false);
            nbt.putInt("mode", (nbt.getInt("mode") + 1 ) % 5);

            if (nbt.getInt("mode") == 3 || nbt.getInt("mode") == 0) {
                nbt.putBoolean("block", true);
            }

            stack.setTag(nbt);
            player.sendStatusMessage(getModeTranslate(stack), true);
        }

        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();

        if(!world.isRemote) {
            TileEntity tileentity = world.getTileEntity(context.getPos());
            CompoundNBT nbt = stack.getTag();
            if (nbt == null) {
                nbt = new CompoundNBT();
                stack.setTag(nbt);
            }

            if (!nbt.contains("block")) {
                nbt.putBoolean("block", false);
            }
            if (!nbt.contains("mode")) {
                nbt.putInt("mode", 0);
            }

            if (nbt.getInt("mode") != 4) {
                if (tileentity instanceof IWissenTileEntity) {
                    IWissenTileEntity wissenTile = (IWissenTileEntity) tileentity;

                    if (!nbt.getBoolean("block")) {
                        if (((nbt.getInt("mode") == 1) && wissenTile.canConnectReceiveWissen()) || ((nbt.getInt("mode") == 2) && wissenTile.canConnectSendWissen())) {
                            nbt.putInt("blockX", context.getPos().getX());
                            nbt.putInt("blockY", context.getPos().getY());
                            nbt.putInt("blockZ", context.getPos().getZ());
                            nbt.putBoolean("block", true);
                        }
                    } else {
                        if (tileentity instanceof WissenTranslatorTileEntity) {
                            WissenTranslatorTileEntity translator = (WissenTranslatorTileEntity) tileentity;
                            BlockPos blockPos = new BlockPos(nbt.getInt("blockX"), nbt.getInt("blockY"), nbt.getInt("blockZ"));
                            TileEntity oldTileentity = world.getTileEntity(blockPos);

                            if (!context.getPlayer().isSneaking()) {
                                if (nbt.getInt("mode") == 1) {
                                    if (oldTileentity instanceof IWissenTileEntity) {
                                        IWissenTileEntity wissenTileentity = (IWissenTileEntity) oldTileentity;
                                        if ((!translator.isSameFromAndTo(translator.getPos(), blockPos)) && (wissenTileentity.canConnectReceiveWissen())) {
                                            translator.blockFromX = nbt.getInt("blockX");
                                            translator.blockFromY = nbt.getInt("blockY");
                                            translator.blockFromZ = nbt.getInt("blockZ");
                                            translator.isFromBlock = true;
                                            nbt.putBoolean("block", false);
                                            PacketUtils.SUpdateTileEntityPacket(translator);
                                        }
                                    }
                                } else if (nbt.getInt("mode") == 2) {
                                    if (oldTileentity instanceof IWissenTileEntity) {
                                        IWissenTileEntity wissenTileentity = (IWissenTileEntity) oldTileentity;
                                        if ((!translator.isSameFromAndTo(translator.getPos(), blockPos)) && (wissenTileentity.canConnectSendWissen())) {
                                            translator.blockToX = nbt.getInt("blockX");
                                            translator.blockToY = nbt.getInt("blockY");
                                            translator.blockToZ = nbt.getInt("blockZ");
                                            translator.isToBlock = true;
                                            nbt.putBoolean("block", false);
                                            PacketUtils.SUpdateTileEntityPacket(translator);
                                        }
                                    }
                                }
                            }

                            if (nbt.getInt("mode") == 3) {
                                translator.isFromBlock = false;
                                translator.isToBlock = false;
                                PacketUtils.SUpdateTileEntityPacket(translator);
                            }
                        }
                    }
                    stack.setTag(nbt);
                }

                if (tileentity instanceof IWissenWandFunctionalTileEntity) {
                    IWissenWandFunctionalTileEntity functionalTile = (IWissenWandFunctionalTileEntity) tileentity;
                    if (nbt.getInt("mode") == 0) {
                        functionalTile.wissenWandFuction();
                    }
                    stack.setTag(nbt);
                }
            }
        }

        return super.onItemUseFirst(stack, context);
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flags) {
        list.add(new TranslationTextComponent(getModeString(stack)).mergeStyle(getModeColor(stack)));
    }

    @Override
    public ITextComponent getHighlightTip(ItemStack stack, ITextComponent displayName) {
        ITextComponent mode = getModeTranslate(stack);
        return displayName.deepCopy().appendSibling(mode);
    }

    public static String getModeString(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (nbt != null) {
            if (nbt.contains("mode")) {
                switch (nbt.getInt("mode")) {
                    case 0:
                        return "lore.wizards_reborn.wissen_wand_mode.functional";
                    case 1:
                        return "lore.wizards_reborn.wissen_wand_mode.receive_connect";
                    case 2:
                        return "lore.wizards_reborn.wissen_wand_mode.send_connect";
                    case 3:
                        return "lore.wizards_reborn.wissen_wand_mode.reload";
                    case 4:
                        return "lore.wizards_reborn.wissen_wand_mode.off";
                }
            }
        }

        return "lore.wizards_reborn.wissen_wand_mode.functional";
    }

    public static TextFormatting getModeColor(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (nbt != null) {
            if (nbt.contains("mode")) {
                switch (nbt.getInt("mode")) {
                    case 0:
                        return TextFormatting.DARK_AQUA;
                    case 1:
                        return TextFormatting.GREEN;
                    case 2:
                        return TextFormatting.AQUA;
                    case 3:
                        return TextFormatting.YELLOW;
                    case 4:
                        return TextFormatting.GRAY;
                }
            }
        }

        return TextFormatting.DARK_AQUA;
    }

    public static ITextComponent getModeTranslate(ItemStack stack) {
        ITextComponent mode = new StringTextComponent(" (")
                .appendSibling(new TranslationTextComponent(getModeString(stack)).mergeStyle(getModeColor(stack)))
                .appendString(")");
        return mode;
    }
}
