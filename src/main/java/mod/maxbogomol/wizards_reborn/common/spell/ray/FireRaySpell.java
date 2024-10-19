package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

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
                    if (fire <= 50) target.setSecondsOnFire(fire);
                    target.setSecondsOnFire(fire);
                    target.setTicksFrozen(0);

                    DamageSource damageSource = getDamage(target.damageSources().onFire().typeHolder(), entity, entity.getOwner());
                    target.hurt(damageSource, damage);
                }
            }
        }
    }

/*    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player) {
        super.onImpact(ray, level, projectile, player);

        if (player != null) {
            if (player.isShiftKeyDown()) {
                int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
                ItemStack stack = player.getItemInHand(player.getUsedItemHand());

                Color color = getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                if (projectile.tickCount % getBlockTicks(projectile, focusLevel) == 0) {
                    if (WissenItemUtil.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player, getBlockWissen(projectile, focusLevel)))) {
                        Vec3 vec = getBlockHitOffset(ray, projectile, -0.1f);
                        BlockPos blockPos = BlockPos.containing(vec.x(), vec.y(), vec.z());
                        BlockState blockState = level.getBlockState(blockPos);
                        if (!CampfireBlock.canLight(blockState) && !CandleBlock.canLight(blockState) && !CandleCakeBlock.canLight(blockState)) {
                            BlockEvent.EntityPlaceEvent placeEv = new BlockEvent.EntityPlaceEvent(
                                    BlockSnapshot.create(level.dimension(), level, blockPos),
                                    BaseFireBlock.getState(level, blockPos),
                                    player
                            );

                            if (BaseFireBlock.canBePlacedAt(level, blockPos, Direction.UP) && !MinecraftForge.EVENT_BUS.post(placeEv)) {
                                level.playSound(null, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.1F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                                BlockState blockstate1 = BaseFireBlock.getState(level, blockPos);
                                level.setBlock(blockPos, blockstate1, 11);

                                removeWissen(stack, projectile.getStats(), player, getBlockWissen(projectile, focusLevel));

                                PacketHandler.sendToTracking(level, player.getOnPos(), new FireRaySpellEffectPacket((float) blockPos.getX() + 0.5f, (float) blockPos.getY() + 0.2f, (float) blockPos.getZ() + 0.5f, r, g, b));
                            }
                        } else {
                            BlockEvent.EntityPlaceEvent placeEv = new BlockEvent.EntityPlaceEvent(
                                    BlockSnapshot.create(level.dimension(), level, blockPos),
                                    level.getBlockState(blockPos),
                                    player
                            );

                            if (!MinecraftForge.EVENT_BUS.post(placeEv)) {
                                level.playSound(player, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                                level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
                                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);

                                removeWissen(stack, projectile.getStats(), player, getBlockWissen(projectile, focusLevel));

                                PacketHandler.sendToTracking(level, player.getOnPos(), new FireRaySpellEffectPacket((float) blockPos.getX() + 0.5f, (float) blockPos.getY() + 0.5f, (float) blockPos.getZ() + 0.5f, r, g, b));
                            }
                        }
                    }
                }
            }
        }
    }*/

    public int getBlockTicks(SpellEntity projectile, int focusLevel) {
        return (15 - (focusLevel * 3));
    }

    public int getBlockWissen(SpellEntity projectile, int focusLevel) {
        return getWissenCost();
    }
}
