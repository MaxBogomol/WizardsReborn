package mod.maxbogomol.wizards_reborn.common.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import mod.maxbogomol.wizards_reborn.common.capability.ArrowModifierProvider;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import mod.maxbogomol.wizards_reborn.common.capability.KnowledgeProvider;
import mod.maxbogomol.wizards_reborn.common.command.WizardsRebornCommand;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneFortressArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.KnowledgeUpdatePacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamage;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTags;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events {
    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) event.addCapability(new ResourceLocation(WizardsReborn.MOD_ID, "knowledge"), new KnowledgeProvider());
        if (event.getObject() instanceof AbstractArrow) event.addCapability(new ResourceLocation(WizardsReborn.MOD_ID, "arrow_modifier"), new ArrowModifierProvider());
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        Capability<IKnowledge> KNOWLEDGE = IKnowledge.INSTANCE;
        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(KNOWLEDGE).ifPresent((k) -> event.getOriginal().getCapability(KNOWLEDGE).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>) k).deserializeNBT(((INBTSerializable<CompoundTag>) o).serializeNBT())));
        if (!event.getEntity().level().isClientSide) {
            PacketHandler.sendTo((ServerPlayer) event.getEntity(), new KnowledgeUpdatePacket(event.getEntity()));
        }
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player && !event.getLevel().isClientSide()) {
            PacketHandler.sendTo(player, new KnowledgeUpdatePacket(player));
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.level().isClientSide()) {
            Player player = event.player;

            for (Knowledge knowledge : Knowledges.getKnowledges()) {
                knowledge.addTick(player);
            }

            ArcaneFortressArmorItem.playerTick(event);
        }
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        WizardsRebornCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        magicArmor(event);
        ArcaneEnchantmentUtil.onLivingDamage(event);
        arcaneDamage(event);
    }

    public void magicArmor(LivingDamageEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            float scale = 1;
            AttributeInstance attr = event.getEntity().getAttribute(WizardsRebornAttributes.MAGIC_ARMOR.get());

            if (attr != null) {
                if (event.getSource().is(WizardsRebornDamageTags.MAGIC)) {
                    scale = (float) (1f - (attr.getValue() / 100f));
                }
                if (scale == 1 && event.getSource().getDirectEntity() instanceof SpellEntity) {
                    scale = (float) (1f - ((attr.getValue() / 2) / 100f));
                }
                if (scale < 0) {
                    scale = 0;
                }

                if (scale < 1) {
                    event.setAmount(event.getAmount() * scale);
                }
            }
        }
    }

    public void arcaneDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (!event.getSource().is(WizardsRebornDamageTags.ARCANE_MAGIC)) {
                AttributeInstance attr = attacker.getAttribute(WizardsRebornAttributes.ARCANE_DAMAGE.get());
                if (attr != null && attr.getValue() > 0) {
                    int invulnerableTime = event.getEntity().invulnerableTime;
                    event.getEntity().invulnerableTime = 0;
                    event.getEntity().hurt(new DamageSource(WizardsRebornDamage.create(event.getEntity().level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), attacker), (float) attr.getValue());
                    event.getEntity().invulnerableTime = invulnerableTime;
                }
            }
        }
    }

    @SubscribeEvent
    public void addCustomWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().add(new VillagerTrades.ItemsForEmeralds(WizardsRebornItems.MOR.get(), 1, 1, 16, 1));
        event.getGenericTrades().add(new VillagerTrades.ItemsForEmeralds(WizardsRebornItems.ELDER_MOR.get(), 2, 1, 16, 1));
        event.getGenericTrades().add(new VillagerTrades.ItemsForEmeralds(WizardsRebornItems.ARCANUM_DUST.get(), 3, 2, 8, 1));
        event.getGenericTrades().add(new VillagerTrades.ItemsForEmeralds(WizardsRebornItems.ARCANUM.get(), 4, 1, 6, 1));
    }
}
