package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.CrystalBagItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DeleteCrystalPacket {
    private final boolean hand;

    public DeleteCrystalPacket(boolean hand) {
        this.hand = hand;
    }

    public static DeleteCrystalPacket decode(FriendlyByteBuf buf) {
        return new DeleteCrystalPacket(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(hand);
    }

    public static void handle(DeleteCrystalPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (!msg.hand) {
                    stack = player.getItemInHand(InteractionHand.OFF_HAND);
                }

                SimpleContainer stack_inv = ArcaneWandItem.getInventory(stack);
                CompoundTag nbt = stack.getTag();

                List<ItemStack> items = player.inventoryMenu.getItems();
                List<SlotResult> curioSlots = CuriosApi.getCuriosInventory(player).resolve().get().findCurios((i) -> {return true;});
                for (SlotResult slot : curioSlots) {
                    if (slot.stack() != null) {
                        items.add(slot.stack());
                    }
                }

                List<ItemStack> itemsBag = new ArrayList<>();
                boolean added = false;

                for (ItemStack item : items) {
                    if (item.getItem() instanceof CrystalBagItem) {
                        itemsBag.add(item);
                    }
                }

                if (itemsBag.isEmpty()) {
                    if (player.getInventory().getFreeSlot() > -1) {
                        player.getInventory().add(stack_inv.getItem(0));
                    } else {
                        player.serverLevel().addFreshEntity(new ItemEntity(player.serverLevel(), player.getX(), player.getY() + 0.5F, player.getZ(), stack_inv.getItem(0)));
                    }
                } else {
                    for (ItemStack item : itemsBag) {
                        if (item.getItem() instanceof CrystalBagItem) {
                            SimpleContainer container = CrystalBagItem.getInventory(item);
                            if (container.canAddItem(stack_inv.getItem(0))) {
                                container.addItem(stack_inv.getItem(0));
                                player.serverLevel().playSound(WizardsReborn.proxy.getPlayer(), player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1f, 1f);
                                added = true;
                                break;
                            }
                        }
                    }
                    if (!added) {
                        if (player.getInventory().getFreeSlot() > -1) {
                            player.getInventory().add(stack_inv.getItem(0));
                        } else {
                            player.serverLevel().addFreshEntity(new ItemEntity(player.serverLevel(), player.getX(), player.getY() + 0.5F, player.getZ(), stack_inv.getItem(0)));
                        }
                    }
                }

                stack_inv.clearContent();
                nbt.putBoolean("crystal", false);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
