package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

public class WandSpellContext extends SpellContext {

    @Override
    public void setCooldown(Spell spell) {
        spell.setCooldown(getItemStack(), getStats());
    }

    @Override
    public void setCooldown(Spell spell, int cost) {
        spell.setCooldown(getItemStack(), getStats(), cost);
    }

    @Override
    public void removeWissen(Spell spell) {
        WissenItemUtil.removeWissen(getItemStack(), spell.getWissenCostWithStat(getStats(), getEntity()));
    }

    @Override
    public void removeWissen(Spell spell, int cost) {
        WissenItemUtil.removeWissen(getItemStack(), spell.getWissenCostWithStat(getStats(), getEntity(), cost));
    }

    @Override
    public void removeWissen(int cost) {
        WissenItemUtil.removeWissen(getItemStack(), cost);
    }

    public boolean canRemoveWissen(Spell spell) {
        return WissenItemUtil.canRemoveWissen(getItemStack(), spell.getWissenCostWithStat(getStats(), getEntity()));
    }

    public boolean canRemoveWissen(Spell spell, int cost) {
        return WissenItemUtil.canRemoveWissen(getItemStack(), spell.getWissenCostWithStat(getStats(), getEntity(), cost));
    }

    public boolean canRemoveWissen(int cost) {
        return WissenItemUtil.canRemoveWissen(getItemStack(), cost);
    }

    @Override
    public void spellSound(Spell spell) {
        getLevel().playSound(null, pos.x(), pos.y(), pos.z(), WizardsRebornSounds.SPELL_CAST.get(), SoundSource.PLAYERS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }

    @Override
    public void awardStat(Spell spell) {
        if (getEntity() instanceof Player player && !getItemStack().isEmpty()) {
            player.awardStat(Stats.ITEM_USED.get(getItemStack().getItem()));
        }
    }

    public void startUsing(Spell spell) {
        if (getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.startUsingItem(mainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
        }
    }

    public void stopUsing(Spell spell) {
        if (getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.stopUsingItem();
        }
    }

    @Override
    public CompoundTag getSpellData() {
        return ArcaneWandItem.getSpellData(getItemStack());
    }

    @Override
    public void setSpellData(CompoundTag spellData) {
        ArcaneWandItem.setSpellData(getItemStack(), spellData);
    }

    public static WandSpellContext getFromWand(Entity entity, ItemStack stack, InteractionHand hand) {
        WandSpellContext spellContext = new WandSpellContext();
        spellContext.setLevel(entity.level());
        spellContext.setEntity(entity);
        spellContext.setPos(entity.getEyePosition());
        spellContext.setVec(entity.getLookAngle());
        if (entity instanceof Player player) {
            spellContext.setDistance(player.getAttributeValue(ForgeMod.ENTITY_REACH.get()));
            spellContext.setAlternative(player.isShiftKeyDown());
        } else {
            spellContext.setDistance(3);
            spellContext.setAlternative(false);
        }
        if (hand != null) {
            spellContext.setMainHand(hand);
        } else {
            spellContext.setMainHand(true);
        }
        spellContext.setItemStack(stack);
        spellContext.setStats(Spell.getStats(stack));
        return spellContext;
    }

    public static WandSpellContext getFromWand(Entity entity, ItemStack stack) {
        return getFromWand(entity, stack, null);
    }
}
