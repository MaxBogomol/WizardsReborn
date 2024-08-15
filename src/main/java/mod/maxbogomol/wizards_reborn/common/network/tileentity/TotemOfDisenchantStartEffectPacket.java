package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.client.sound.TotemOfDisenchantSoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.totem.disenchant.TotemOfDisenchantBlockEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TotemOfDisenchantStartEffectPacket {
    private final BlockPos pos;
    private final Component enchantment;
    private final Component arcaneEnchantment;
    private final int enchantmentLevel;

    public TotemOfDisenchantStartEffectPacket(BlockPos pos, ItemStack stack) {
        this.pos = pos;

        Component enchantment1 = Component.empty();
        Component arcaneEnchantment1 = Component.empty();
        int enchantmentLevel1 = 0;

        Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
        Map<ArcaneEnchantment, Integer> arcaneEnchantments = new HashMap<>();

        if (ArcaneEnchantmentUtils.isArcaneItem(stack)) {
            arcaneEnchantments = ArcaneEnchantmentUtils.getAllArcaneEnchantments(stack);
        }

        if (stack.getItem().equals(Items.ENCHANTED_BOOK)) {
            ListTag listtag = EnchantedBookItem.getEnchantments(stack);
            for(int ii = 0; ii < listtag.size(); ++ii) {
                CompoundTag compoundtag = listtag.getCompound(ii);
                Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(EnchantmentHelper.getEnchantmentId(compoundtag));
                enchantments.put(enchantment, EnchantmentHelper.getEnchantmentLevel(compoundtag));
            }
        }

        for (ArcaneEnchantment enchantment : arcaneEnchantments.keySet()) {
            if (enchantment != null) {
                arcaneEnchantment1 = Component.literal(enchantment.getId());
                enchantmentLevel1 = arcaneEnchantments.get(enchantment);
                break;
            }
        }

        for (Enchantment enchantment : enchantments.keySet()) {
            if (enchantment != null) {
                String id = enchantment.getDescriptionId();

                int i = id.indexOf(".");
                id = id.substring(i + 1);
                i = id.indexOf(".");
                String modId = id.substring(0, i);
                String enchantmentId = id.substring(i + 1);

                enchantment1 = Component.literal(modId + ":" + enchantmentId);
                enchantmentLevel1 = enchantments.get(enchantment);
                break;
            }
        }

        this.enchantment = enchantment1;
        this.arcaneEnchantment = arcaneEnchantment1;
        this.enchantmentLevel = enchantmentLevel1;
    }

    public TotemOfDisenchantStartEffectPacket(BlockPos pos, Component enchantment, Component arcaneEnchantment, int enchantmentLevel) {
        this.pos = pos;
        this.enchantment = enchantment;
        this.arcaneEnchantment = arcaneEnchantment;
        this.enchantmentLevel = enchantmentLevel;
    }

    public static TotemOfDisenchantStartEffectPacket decode(FriendlyByteBuf buf) {
        return new TotemOfDisenchantStartEffectPacket(buf.readBlockPos(), buf.readComponent(), buf.readComponent(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeComponent(enchantment);
        buf.writeComponent(arcaneEnchantment);
        buf.writeInt(enchantmentLevel);
    }

    public static void handle(TotemOfDisenchantStartEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                ServerLevel level = player.serverLevel();

                if (level.getBlockEntity(msg.pos) instanceof TotemOfDisenchantBlockEntity totem) {
                    totem.isStart = true;
                    totem.cooldown = 200;
                    totem.enchantment = msg.enchantment.getString();
                    totem.arcaneEnchantment = msg.arcaneEnchantment.getString();
                    totem.enchantmentLevel = msg.enchantmentLevel;
                    PacketUtils.SUpdateTileEntityPacket(totem);

                    level.playSound(WizardsReborn.proxy.getPlayer(), totem.getBlockPos(), WizardsReborn.TOTEM_OF_DISENCHANT_START_SOUND.get(), SoundSource.BLOCKS, 1f, 1f);
                    TotemOfDisenchantSoundInstance.playSound(totem);
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
