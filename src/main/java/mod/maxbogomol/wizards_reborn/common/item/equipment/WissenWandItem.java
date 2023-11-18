package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalTileEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenTranslatorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

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

            player.awardStat(Stats.ITEM_USED.get(this));

            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level world = context.getLevel();
        InteractionResult result = InteractionResult.PASS;

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
                            result = InteractionResult.SUCCESS;
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
                                            result = InteractionResult.SUCCESS;
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
                                            result = InteractionResult.SUCCESS;
                                        }
                                    }
                                }
                            }

                            if (nbt.getInt("mode") == 3) {
                                translator.isFromBlock = false;
                                translator.isToBlock = false;
                                PacketUtils.SUpdateTileEntityPacket(translator);
                                result = InteractionResult.SUCCESS;
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
                    result = InteractionResult.SUCCESS;
                }
            }
        }

        if (result == InteractionResult.SUCCESS) {
            context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
        }

        return result;
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

    public static int getMode(ItemStack stack) {
        int mode = 0;

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

        mode = nbt.getInt("mode");

        return mode;
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWissenGui(GuiGraphics gui) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        ItemStack main = mc.player.getMainHandItem();
        ItemStack offhand = mc.player.getOffhandItem();

        boolean render = false;

        if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
            render = true;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                render = true;
            }
        }

        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (render) {
            if (!player.isSpectator()) {
                HitResult pos = mc.hitResult;
                if (pos != null) {
                    BlockPos bpos = pos.getType() == HitResult.Type.BLOCK ? ((BlockHitResult) pos).getBlockPos() : null;
                    BlockEntity tileentity = bpos != null ? mc.level.getBlockEntity(bpos) : null;

                    if (tileentity != null) {
                        if (tileentity instanceof IWissenTileEntity) {
                            IWissenTileEntity wissenTile = (IWissenTileEntity) tileentity;

                            int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                            int y = mc.getWindow().getGuiScaledHeight() / 2 + 32 - 10;

                            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), x, y, 0, 0, 48, 10, 64, 64);
                            int width = 32;
                            width /= (double) wissenTile.getMaxWissen() / (double) wissenTile.getWissen();
                            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), x + 8, y + 1, 0, 10, width, 8, 64, 64);
                        }

                        if (tileentity instanceof ICooldownTileEntity) {
                            ICooldownTileEntity cooldownTile = (ICooldownTileEntity) tileentity;

                            int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                            int y = mc.getWindow().getGuiScaledHeight() / 2 + 32 - 10 - 11;

                            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/cooldown_frame.png"), x, y, 0, 0, 48, 10, 64, 64);
                            int width = 32;
                            width /= (double) cooldownTile.getCooldown();
                            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/cooldown_frame.png"), x + 8, y + 1, 0, 10, width, 8, 64, 64);
                        }

                        if (tileentity instanceof IFluidTileEntity) {
                            IFluidTileEntity fluidTile = (IFluidTileEntity) tileentity;

                            int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                            int y = mc.getWindow().getGuiScaledHeight() / 2 + 32 - 10 - 11;

                            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/cooldown_frame.png"), x, y, 0, 0, 48, 10, 64, 64);
                            int width = 32;
                            width /= (double) fluidTile.getFluidMaxAmount() / (double) fluidTile.getFluidAmount();
                            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/cooldown_frame.png"), x + 8, y + 1, 0, 10, width, 8, 64, 64);
                        }
                    }
                }
            }
        }

        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }
}
