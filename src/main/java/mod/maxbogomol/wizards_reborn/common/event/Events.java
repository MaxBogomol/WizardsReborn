package mod.maxbogomol.wizards_reborn.common.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import mod.maxbogomol.wizards_reborn.common.knowledge.ItemKnowledge;
import mod.maxbogomol.wizards_reborn.common.network.KnowledgeUpdatePacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class Events {
    /*
    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Entity) event.addCapability(new ResourceLocation(WizardsReborn.MOD_ID, "knowledge"), new KnowledgeProvider());
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        Capability<IKnowledge> KNOWLEDGE = KnowledgeProvider.CAPABILITY;
        KNOWLEDGE.getStorage().readNBT(
                KNOWLEDGE,
                event.getPlayer().getCapability(KNOWLEDGE, null).resolve().get(),
                null,
                KNOWLEDGE.getStorage().writeNBT(KNOWLEDGE, event.getOriginal().getCapability(KNOWLEDGE, null).resolve().get(), null)
        );
        if (!event.getPlayer().level.isClientSide) {
            PacketHandler.sendTo((ServerPlayer) event.getPlayer(), new KnowledgeUpdatePacket(event.getPlayer()));
        }
    }*/

    /*@SubscribeEvent
    public void registerCustomAI(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof LivingEntity && !event.getWorld().isClientSide) {
            if (event.getEntity() instanceof Player) {
                PacketHandler.sendTo((ServerPlayer) event.getEntity(), new KnowledgeUpdatePacket((Player)event.getEntity()));
            }
        }
    }*/

    /*
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        List<ItemStack> items = player.inventoryMenu.getItems();

        for (Knowledge knowledge : Knowledges.getSpells()) {
            if (knowledge instanceof ItemKnowledge) {
                ItemKnowledge itemKnowledge = (ItemKnowledge) knowledge;
                if (itemKnowledge.canReceived(items)) {
                    KnowledgeUtils.addKnowledge(player, knowledge);
                }
            }
        }
    }*/
}
