package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.CrystalBagItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.function.Supplier;

public class OpenCrystalBagPacket {
    public OpenCrystalBagPacket() {

    }

    public static OpenCrystalBagPacket decode(FriendlyByteBuf buf) {
        return new OpenCrystalBagPacket();
    }

    public void encode(FriendlyByteBuf buf) {

    }

    public static void handle(OpenCrystalBagPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                List<ItemStack> items = player.inventoryMenu.getItems();
                List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(player, (i) -> {return true;});
                for (SlotResult slot : curioSlots) {
                    if (slot.stack() != null) {
                        items.add(slot.stack());
                    }
                }

                for (ItemStack item : items) {
                    if (item.getItem() instanceof CrystalBagItem stack) {
                        MenuProvider containerProvider = stack.createContainerProvider(player.level(), item);
                        NetworkHooks.openScreen(((ServerPlayer) player), containerProvider, b -> b.writeItem(item));
                        player.serverLevel().playSound(WizardsReborn.proxy.getPlayer(), player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1f, 1f);
                        break;
                    }
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
