package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.WaterRaySpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;

import java.awt.*;

public class WaterRaySpell extends RaySpell {

    public WaterRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.waterSpellColor;
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
                    float damage = (1.0f + (focusLevel * 0.5f)) + magicModifier;

                    target.clearFire();
                    int frost = target.getTicksFrozen() + 10;
                    if (frost > 250) frost = 250;
                    target.setTicksFrozen(frost);

                    DamageSource damageSource = getDamage(target.damageSources().drown().typeHolder(), entity, entity.getOwner());
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
                BlockPos blockPos = hitResult.getBlockPos();
                int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
                int radius = focusLevel + 1;
                if (entity.tickCount % 10 == 0) {
                    for (int x = -radius; x <= radius; x++) {
                        for (int y = -radius; y <= radius; y++) {
                            for (int z = -radius; z <= radius; z++) {
                                if (random.nextFloat() < 0.65f && entity.getSpellContext().canRemoveWissen(5)) {
                                    BlockPos pos = blockPos.relative(Direction.Axis.X, x).relative(Direction.Axis.Y, y).relative(Direction.Axis.Z, z);
                                    Player player = null;
                                    if (entity.getOwner() instanceof Player) {
                                        player = (Player) entity.getOwner();
                                    }
                                    BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(level, blockPos, level.getBlockState(blockPos), player);
                                    if (!level.getBlockState(pos).isAir() && !MinecraftForge.EVENT_BUS.post(breakEvent)) {
                                        if (level.getBlockState(pos).getBlock() instanceof FireBlock) {
                                            level.destroyBlock(pos, false);
                                            entity.getSpellContext().removeWissen(5);
                                            level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.1F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                                            WizardsRebornPacketHandler.sendToTracking(level, blockPos, new WaterRaySpellPacket(blockPos.getCenter(), getColor()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
