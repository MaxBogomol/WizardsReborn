package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.CrystalBagItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ArcaneWandRemoveCrystalPacket extends ServerPacket {
    protected final boolean hand;
    protected final boolean sound;

    public ArcaneWandRemoveCrystalPacket(boolean hand) {
        this.hand = hand;
        this.sound = true;
    }

    public ArcaneWandRemoveCrystalPacket(boolean hand, boolean sound) {
        this.hand = hand;
        this.sound = sound;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!hand) stack = player.getItemInHand(InteractionHand.OFF_HAND);

        SimpleContainer inventory = ArcaneWandItem.getInventory(stack);
        CompoundTag nbt = stack.getOrCreateTag();

        List<ItemStack> items = player.inventoryMenu.getItems();
        LazyOptional<ICuriosItemHandler> curiosItemHandler = CuriosApi.getCuriosInventory(player);
        if (curiosItemHandler.isPresent() && curiosItemHandler.resolve().isPresent()) {
            List<SlotResult> curioSlots = curiosItemHandler.resolve().get().findCurios((i) -> true);
            for (SlotResult slot : curioSlots) {
                if (slot.stack() != null) {
                    items.add(slot.stack());
                }
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
            BlockSimpleInventory.addPlayerItem(player.level(), player, inventory.getItem(0));
        } else {
            for (ItemStack item : itemsBag) {
                if (item.getItem() instanceof CrystalBagItem) {
                    SimpleContainer container = CrystalBagItem.getInventory(item);
                    if (container.canAddItem(inventory.getItem(0))) {
                        container.addItem(inventory.getItem(0));
                        player.serverLevel().playSound(WizardsReborn.proxy.getPlayer(), player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1f, 1f);
                        added = true;
                        break;
                    }
                }
            }
            if (!added) {
                BlockSimpleInventory.addPlayerItem(player.level(), player, inventory.getItem(0));
            }
        }

        inventory.clearContent();
        nbt.putBoolean("crystal", false);
        if (sound) player.serverLevel().playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), WizardsRebornSounds.CRYSTAL_RESONATE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ArcaneWandRemoveCrystalPacket.class, ArcaneWandRemoveCrystalPacket::encode, ArcaneWandRemoveCrystalPacket::decode, ArcaneWandRemoveCrystalPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(hand);
        buf.writeBoolean(sound);
    }

    public static ArcaneWandRemoveCrystalPacket decode(FriendlyByteBuf buf) {
        return new ArcaneWandRemoveCrystalPacket(buf.readBoolean(), buf.readBoolean());
    }
}
