package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.CrystalBagItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ArcaneWandSetCrystalPacket extends ServerPacket {
    protected final boolean hand;
    protected final ItemStack crystal;

    public ArcaneWandSetCrystalPacket(boolean hand, ItemStack crystal) {
        this.hand = hand;
        this.crystal = crystal;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!hand) stack = player.getItemInHand(InteractionHand.OFF_HAND);

        SimpleContainer inventory = ArcaneWandItem.getInventory(stack);
        CompoundTag nbt = stack.getOrCreateTag();

        ItemStack removeCrystal = null;
        boolean set = false;
        boolean sound = false;
        boolean added = false;

        List<ItemStack> items = player.inventoryMenu.getItems();
        List<SlotResult> curioSlots = CuriosApi.getCuriosInventory(player).resolve().get().findCurios((i) -> {return true;});
        for (SlotResult slot : curioSlots) {
            if (slot.stack() != null) {
                items.add(slot.stack());
            }
        }

        List<ItemStack> itemsBag = new ArrayList<>();

        for (ItemStack item : items) {
            if (!set) {
                if (item.equals(crystal, false)) {
                    if (item.getOrCreateTag().toString().equals(crystal.getOrCreateTag().toString())) {
                        player.getInventory().removeItem(item);
                        if (nbt.getBoolean("crystal")) {
                            removeCrystal = inventory.getItem(0).copy();
                        }
                        inventory.setItem(0, crystal);
                        nbt.putBoolean("crystal", true);
                        set = true;
                    }
                }
                if (item.getItem() instanceof CrystalBagItem) {
                    SimpleContainer container = CrystalBagItem.getInventory(item);
                    for (int i = 0; i < container.getContainerSize(); i++) {
                        if (!set) {
                            ItemStack itemStack = container.getItem(i);
                            if (itemStack.equals(crystal, false)) {
                                if (itemStack.getOrCreateTag().toString().equals(crystal.getOrCreateTag().toString())) {
                                    container.removeItem(i, 1);
                                    if (nbt.getBoolean("crystal")) {
                                        removeCrystal = inventory.getItem(0).copy();
                                    }
                                    inventory.setItem(0, crystal);
                                    nbt.putBoolean("crystal", true);
                                    set = true;
                                    sound = true;
                                }
                            }
                        }
                    }
                }
            }
            if (item.getItem() instanceof CrystalBagItem) {
                itemsBag.add(item);
            }
        }

        if (removeCrystal != null) {
            if (!itemsBag.isEmpty()) {
                for (ItemStack item : itemsBag) {
                    if (item.getItem() instanceof CrystalBagItem) {
                        SimpleContainer container = CrystalBagItem.getInventory(item);
                        if (container.canAddItem(removeCrystal)) {
                            container.addItem(removeCrystal);
                            sound = true;
                            added = true;
                            break;
                        }
                    }
                }
            }

            if (!added) player.getInventory().add(removeCrystal);
        }

        if (sound) player.serverLevel().playSound(WizardsReborn.proxy.getPlayer(), player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1f, 1f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ArcaneWandSetCrystalPacket.class, ArcaneWandSetCrystalPacket::encode, ArcaneWandSetCrystalPacket::decode, ArcaneWandSetCrystalPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(hand);
        buf.writeItem(crystal);
    }

    public static ArcaneWandSetCrystalPacket decode(FriendlyByteBuf buf) {
        return new ArcaneWandSetCrystalPacket(buf.readBoolean(), buf.readItem());
    }
}
