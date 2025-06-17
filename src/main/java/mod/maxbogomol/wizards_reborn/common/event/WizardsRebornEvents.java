package mod.maxbogomol.wizards_reborn.common.event;

import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.fluffy_fur.registry.common.damage.FluffyFurDamageTypeTags;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.knowledge.EchoHandler;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeHandler;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.FireworkJumpArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.capability.*;
import mod.maxbogomol.wizards_reborn.common.command.WizardsRebornCommand;
import mod.maxbogomol.wizards_reborn.common.effect.IrritationEffect;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.knowledge.KnowledgeUpdatePacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMobEffects;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypeTags;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WizardsRebornEvents {
    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) event.addCapability(new ResourceLocation(WizardsReborn.MOD_ID, "knowledge"), new KnowledgeProvider());
        if (event.getObject() instanceof Player) event.addCapability(new ResourceLocation(WizardsReborn.MOD_ID, "player_modifier"), new PlayerModifierProvider());
        if (event.getObject() instanceof AbstractArrow) event.addCapability(new ResourceLocation(WizardsReborn.MOD_ID, "arrow_modifier"), new ArrowModifierProvider());
        if (event.getObject() instanceof FireworkRocketEntity) event.addCapability(new ResourceLocation(WizardsReborn.MOD_ID, "firework_modifier"), new FireworkModifierProvider());
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        Capability<IKnowledge> KNOWLEDGE = IKnowledge.INSTANCE;
        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(KNOWLEDGE).ifPresent((k) -> event.getOriginal().getCapability(KNOWLEDGE).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>) k).deserializeNBT(((INBTSerializable<CompoundTag>) o).serializeNBT())));
        if (!event.getEntity().level().isClientSide) {
            WizardsRebornPacketHandler.sendTo((ServerPlayer) event.getEntity(), new KnowledgeUpdatePacket(event.getEntity()));
        }
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player && !event.getLevel().isClientSide()) {
            WizardsRebornPacketHandler.sendTo(player, new KnowledgeUpdatePacket(player));
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!player.level().isClientSide()) {
            KnowledgeHandler.tickKnowledgeListTrigger(player);
        }
        EchoHandler.tick(player);
        FireworkJumpArcaneEnchantment.playerTick(event);
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
                if (event.getSource().is(FluffyFurDamageTypeTags.MAGIC)) {
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
            if (!event.getSource().is(WizardsRebornDamageTypeTags.ARCANE_MAGIC)) {
                AttributeInstance attr = attacker.getAttribute(WizardsRebornAttributes.ARCANE_DAMAGE.get());
                if (attr != null && attr.getValue() > 0) {
                    int invulnerableTime = event.getEntity().invulnerableTime;
                    event.getEntity().invulnerableTime = 0;
                    event.getEntity().hurt(DamageHandler.create(event.getEntity().level(), WizardsRebornDamageTypes.ARCANE_MAGIC, attacker, attacker), (float) attr.getValue());
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

    @SubscribeEvent
    public void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(WizardsRebornMobEffects.IRRITATION.get())) {
            MobEffectInstance effectInstance = entity.getEffect(WizardsRebornMobEffects.IRRITATION.get());
            if (effectInstance.getEffect().isDurationEffectTick(effectInstance.getDuration(), effectInstance.getAmplifier())) {
                IrritationEffect.effectTick(entity, effectInstance.getAmplifier());
            }
        }
    }

    @SubscribeEvent
    public void onLivingHeal(LivingHealEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(WizardsRebornMobEffects.IRRITATION.get())) {
            MobEffectInstance effectInstance = entity.getEffect(WizardsRebornMobEffects.IRRITATION.get());
            float modifier = 0.6f - (effectInstance.getAmplifier() * 0.1f);
            if (modifier < 0.2f) modifier = 0.2f;
            event.setAmount(event.getAmount() * modifier);
        }
    }
}
