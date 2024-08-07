package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.common.item.equipment.IBagItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.function.Supplier;

public class OpenBagPacket {
    private final ItemStack bag;

    public OpenBagPacket(ItemStack bag) {
        this.bag = bag;
    }

    public static OpenBagPacket decode(FriendlyByteBuf buf) {
        return new OpenBagPacket( buf.readItem());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(bag);
    }

    public static void handle(OpenBagPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                List<ItemStack> items = player.inventoryMenu.getItems();
                List<SlotResult> curioSlots = CuriosApi.getCuriosInventory(player).resolve().get().findCurios((i) -> {return true;});
                for (SlotResult slot : curioSlots) {
                    if (slot.stack() != null) {
                        items.add(slot.stack());
                    }
                }

                for (ItemStack item : items) {
                    if (item.getItem() instanceof IBagItem bag) {
                        if (item.equals(msg.bag, false)) {
                            if (item.getOrCreateTag().toString().equals(msg.bag.getOrCreateTag().toString())) {
                                bag.openBag(player.serverLevel(), player, item);
                                break;
                            }
                        }
                    }
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
