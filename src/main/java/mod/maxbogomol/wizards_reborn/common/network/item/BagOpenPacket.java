package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.common.item.equipment.IBagItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.function.Supplier;

public class BagOpenPacket extends ServerPacket {
    protected final ItemStack bag;

    public BagOpenPacket(ItemStack bag) {
        this.bag = bag;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();

        List<ItemStack> items = player.inventoryMenu.getItems();
        List<SlotResult> curioSlots = CuriosApi.getCuriosInventory(player).resolve().get().findCurios((i) -> {return true;});
        for (SlotResult slot : curioSlots) {
            if (slot.stack() != null) {
                items.add(slot.stack());
            }
        }

        for (ItemStack item : items) {
            if (item.getItem() instanceof IBagItem bag) {
                if (item.equals(this.bag, false)) {
                    if (item.getOrCreateTag().toString().equals(this.bag.getOrCreateTag().toString())) {
                        bag.openBag(player.serverLevel(), player, item);
                        break;
                    }
                }
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BagOpenPacket.class, BagOpenPacket::encode, BagOpenPacket::decode, BagOpenPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(bag);
    }

    public static BagOpenPacket decode(FriendlyByteBuf buf) {
        return new BagOpenPacket(buf.readItem());
    }
}
