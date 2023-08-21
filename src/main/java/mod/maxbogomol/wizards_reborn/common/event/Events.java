package mod.maxbogomol.wizards_reborn.common.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import mod.maxbogomol.wizards_reborn.common.capability.KnowledgeProvider;
import mod.maxbogomol.wizards_reborn.common.knowledge.ItemKnowledge;
import mod.maxbogomol.wizards_reborn.common.network.KnowledgeUpdatePacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class Events {
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
        if (!event.getPlayer().world.isRemote) {
            PacketHandler.sendTo((ServerPlayerEntity) event.getPlayer(), new KnowledgeUpdatePacket(event.getPlayer()));
        }
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof LivingEntity && !event.getWorld().isRemote) {
            if (event.getEntity() instanceof PlayerEntity) {
                PacketHandler.sendTo((ServerPlayerEntity) event.getEntity(), new KnowledgeUpdatePacket((PlayerEntity)event.getEntity()));
            }
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        List<ItemStack> items = player.container.getInventory();

        for (Knowledge knowledge : Knowledges.getSpells()) {
            if (knowledge instanceof ItemKnowledge) {
                ItemKnowledge itemKnowledge = (ItemKnowledge) knowledge;
                if (itemKnowledge.canReceived(items)) {
                    KnowledgeUtils.addKnowledge(player, knowledge);
                }
            }
        }
    }
}
