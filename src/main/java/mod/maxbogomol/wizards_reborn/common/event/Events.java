package mod.maxbogomol.wizards_reborn.common.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import mod.maxbogomol.wizards_reborn.common.capability.ArrowModifierProvider;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import mod.maxbogomol.wizards_reborn.common.capability.KnowledgeProvider;
import mod.maxbogomol.wizards_reborn.common.command.WizardsRebornCommand;
import mod.maxbogomol.wizards_reborn.common.damage.DamageSourceRegistry;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneFortressArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.KnowledgeUpdatePacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
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
        if (event.getEntity() instanceof LivingEntity && !event.getLevel().isClientSide) {
            if (event.getEntity() instanceof Player) {
                PacketHandler.sendTo((ServerPlayer) event.getEntity(), new KnowledgeUpdatePacket((Player)event.getEntity()));
            }
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.level().isClientSide) {
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
        ArcaneEnchantmentUtils.onLivingDamage(event);
        arcaneDamage(event);
    }

    public void magicArmor(LivingDamageEvent event) {
        if (!event.getEntity().level().isClientSide) {
            float scale = 1;
            AttributeInstance attr = event.getEntity().getAttribute(WizardsReborn.MAGIC_ARMOR.get());

            if (attr != null) {
                if (event.getSource().is(WizardsReborn.MAGIC_DAMAGE_TYPE_TAG)) {
                    scale = (float) (1f - (attr.getValue() / 100f));
                }
                if (scale == 1 && event.getSource().getDirectEntity() instanceof SpellProjectileEntity) {
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
            if (!event.getSource().is(WizardsReborn.ARCANE_MAGIC_DAMAGE_TYPE_TAG)) {
                AttributeInstance attr = attacker.getAttribute(WizardsReborn.ARCANE_DAMAGE.get());
                if (attr != null && attr.getValue() > 0) {
                    event.getEntity().invulnerableTime = 0;
                    event.getEntity().hurt(new DamageSource(DamageSourceRegistry.create(event.getEntity().level(), DamageSourceRegistry.ARCANE_MAGIC).typeHolder(), attacker), (float) attr.getValue());
                }
            }
        }
    }

    @SubscribeEvent
    public void addCustomWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().add(new VillagerTrades.ItemsForEmeralds(WizardsReborn.MOR_ITEM.get(), 1, 1, 16, 1));
        event.getGenericTrades().add(new VillagerTrades.ItemsForEmeralds(WizardsReborn.ELDER_MOR_ITEM.get(), 2, 1, 16, 1));
        event.getGenericTrades().add(new VillagerTrades.ItemsForEmeralds(WizardsReborn.ARCANUM_DUST.get(), 3, 2, 8, 1));
        event.getGenericTrades().add(new VillagerTrades.ItemsForEmeralds(WizardsReborn.ARCANUM.get(), 4, 1, 6, 1));
    }
}
