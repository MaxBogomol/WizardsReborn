package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.FireRaySpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.level.BlockEvent;

import java.awt.*;

public class FireRaySpell extends RaySpell {

    public FireRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.fireSpellColor;
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
                    float damage = (1.5f + (focusLevel * 0.5f)) + magicModifier;

                    int fire = target.getRemainingFireTicks() + 10;
                    if (fire > 50) fire = 50;
                    target.setSecondsOnFire(fire);
                    target.setSecondsOnFire(fire);
                    target.setTicksFrozen(0);

                    DamageSource damageSource = getDamage(DamageTypes.ON_FIRE, entity, entity.getOwner());
                    target.hurt(damageSource, damage);
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

                if (entity.tickCount % getBlockTicks(entity, focusLevel) == 0) {
                    if (entity.getSpellContext().canRemoveWissen(this, getBlockWissen(entity, focusLevel))) {
                        BlockPos blockPos = hitResult.getBlockPos();
                        BlockState blockState = level.getBlockState(blockPos);
                        if (!CampfireBlock.canLight(blockState) && !CandleBlock.canLight(blockState) && !CandleCakeBlock.canLight(blockState)) {
                            BlockPos blockPos1 = hitResult.getBlockPos().relative(hitResult.getDirection());
                            BlockEvent.EntityPlaceEvent placeEvent = new BlockEvent.EntityPlaceEvent(BlockSnapshot.create(level.dimension(), level, blockPos1), BaseFireBlock.getState(level, blockPos1), entity.getOwner());
                            if (BaseFireBlock.canBePlacedAt(level, blockPos1, Direction.UP) && !MinecraftForge.EVENT_BUS.post(placeEvent)) {
                                level.playSound(null, blockPos1, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.1F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                                BlockState blockstate1 = BaseFireBlock.getState(level, blockPos1);
                                level.setBlock(blockPos1, blockstate1, 11);
                                entity.getSpellContext().removeWissen(this, getBlockWissen(entity, focusLevel));
                                WizardsRebornPacketHandler.sendToTracking(level, blockPos1, new FireRaySpellPacket(new Vec3(blockPos1.getX() + 0.5f, blockPos1.getY() + 0.2f, blockPos1.getZ() + 0.5f), getColor()));
                            }
                        } else {
                            BlockEvent.EntityPlaceEvent placeEvent = new BlockEvent.EntityPlaceEvent(BlockSnapshot.create(level.dimension(), level, blockPos), BaseFireBlock.getState(level, blockPos), entity.getOwner());
                            if (!MinecraftForge.EVENT_BUS.post(placeEvent)) {
                                level.playSound(null, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                                level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
                                level.gameEvent(entity.getOwner(), GameEvent.BLOCK_CHANGE, blockPos);
                                entity.getSpellContext().removeWissen(this, getBlockWissen(entity, focusLevel));
                                WizardsRebornPacketHandler.sendToTracking(level, blockPos, new FireRaySpellPacket(new Vec3(blockPos.getX() + 0.5f, blockPos.getY() + 0.2f, blockPos.getZ() + 0.5f), getColor()));
                            }
                        }
                    }
                }
            }
        }
    }

    public int getBlockTicks(SpellEntity projectile, int focusLevel) {
        return (15 - (focusLevel * 3));
    }

    public int getBlockWissen(SpellEntity projectile, int focusLevel) {
        return getWissenCost();
    }
}
