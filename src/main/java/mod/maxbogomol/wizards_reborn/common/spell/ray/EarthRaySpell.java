package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.EarthRaySpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;

import java.awt.*;

public class EarthRaySpell extends RaySpell {

    public EarthRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.earthSpellColor;
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);

        if (!entity.level().isClientSide()) {
            if (target.tickCount % 10 == 0) {
                if (entity.getSpellContext().canRemoveWissen(this)) {
                    entity.getSpellContext().removeWissen(this);
                    int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
                    float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
                    float damage = (2.0f + (focusLevel * 0.5f)) + magicModifier;
                    DamageSource damageSource = getDamage(DamageTypes.GENERIC, entity, entity.getOwner());
                    target.hurt(damageSource, damage);
                    if (target instanceof Player player) {
                        player.getInventory().hurtArmor(damageSource, damage, Inventory.ALL_ARMOR_SLOTS);
                    }
                }
            }
        }
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult) {
        super.onImpact(level, entity, hitResult);

        if (!entity.level().isClientSide()) {
            if (entity.getSpellContext().getAlternative()) {
                int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
                if (entity.tickCount % (20 - (focusLevel * 3)) == 0) {
                    if (entity.getSpellContext().canRemoveWissen(this)) {
                        BlockPos blockPos = hitResult.getBlockPos();
                        BlockState blockState = level.getBlockState(blockPos);
                        Player player = null;
                        if (entity.getOwner() instanceof Player) {
                            player = (Player) entity.getOwner();
                        }
                        BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(level, blockPos, level.getBlockState(blockPos), player);
                        if (!blockState.isAir() && !MinecraftForge.EVENT_BUS.post(breakEvent)) {
                            if (canBreak(blockState)) {
                                level.destroyBlock(blockPos, true);
                                entity.getSpellContext().removeWissen(this);
                                WizardsRebornPacketHandler.sendToTracking(level, hitResult.getBlockPos(), new EarthRaySpellPacket(hitResult.getBlockPos(), getColor()));
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean canBreak(BlockState blockState) {
        float destroyTime = blockState.getBlock().defaultDestroyTime();
        if (blockState.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        }
        if (blockState.is(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        }
        return destroyTime > 0 && destroyTime < 10f;
    }
}
