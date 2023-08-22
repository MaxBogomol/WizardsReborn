package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalTileEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenTranslatorTileEntity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

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
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            CompoundTag nbt = stack.getTag();
            if (nbt==null) {
                nbt = new CompoundTag();
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
            player.displayClientMessage(getModeTranslate(stack), true);
        }

        return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level world = context.getLevel();

        if(!world.isClientSide) {
            BlockEntity tileentity = world.getBlockEntity(context.getClickedPos());
            CompoundTag nbt = stack.getTag();
            if (nbt == null) {
                nbt = new CompoundTag();
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
                            nbt.putInt("blockX", context.getClickedPos().getX());
                            nbt.putInt("blockY", context.getClickedPos().getY());
                            nbt.putInt("blockZ", context.getClickedPos().getZ());
                            nbt.putBoolean("block", true);
                        }
                    } else {
                        if (tileentity instanceof WissenTranslatorTileEntity) {
                            WissenTranslatorTileEntity translator = (WissenTranslatorTileEntity) tileentity;
                            BlockPos blockPos = new BlockPos(nbt.getInt("blockX"), nbt.getInt("blockY"), nbt.getInt("blockZ"));
                            BlockEntity oldTileentity = world.getBlockEntity(blockPos);

                            if (!context.getPlayer().isShiftKeyDown()) {
                                if (nbt.getInt("mode") == 1) {
                                    if (oldTileentity instanceof IWissenTileEntity) {
                                        IWissenTileEntity wissenTileentity = (IWissenTileEntity) oldTileentity;
                                        if ((!translator.isSameFromAndTo(translator.getBlockPos(), blockPos)) && (wissenTileentity.canConnectReceiveWissen())) {
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
                                        if ((!translator.isSameFromAndTo(translator.getBlockPos(), blockPos)) && (wissenTileentity.canConnectSendWissen())) {
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
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        list.add(Component.translatable(getModeString(stack)).withStyle(getModeColor(stack)));
    }

    @Override
    public Component getHighlightTip(ItemStack stack, Component displayName) {
        Component mode = getModeTranslate(stack);
        return displayName.copy().append(mode);
    }

    public static String getModeString(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
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

    public static ChatFormatting getModeColor(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            if (nbt.contains("mode")) {
                switch (nbt.getInt("mode")) {
                    case 0:
                        return ChatFormatting.DARK_AQUA;
                    case 1:
                        return ChatFormatting.GREEN;
                    case 2:
                        return ChatFormatting.AQUA;
                    case 3:
                        return ChatFormatting.YELLOW;
                    case 4:
                        return ChatFormatting.GRAY;
                }
            }
        }

        return ChatFormatting.DARK_AQUA;
    }

    public static Component getModeTranslate(ItemStack stack) {
        Component mode = Component.literal(" (")
                .append(Component.translatable(getModeString(stack)).withStyle(getModeColor(stack)))
                .append(")");
        return mode;
    }
}
