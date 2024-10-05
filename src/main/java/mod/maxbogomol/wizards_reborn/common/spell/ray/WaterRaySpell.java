package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

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
/*
    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, level, projectile, player, target);

        if (player != null) {
            if (target.tickCount % 10 == 0) {
                ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                if (WissenItemUtil.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player))) {
                    removeWissen(stack, projectile.getStats(), player);
                    int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
                    float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
                    float damage = (float) (1.0f + (focusLevel * 0.5)) + magicModifier;

                    target.clearFire();
                    int frost = target.getTicksFrozen() + 1;
                    if (frost > 250) frost = 250;
                    target.setTicksFrozen(frost);

                    target.hurt(new DamageSource(target.damageSources().drown().typeHolder(), projectile, player), damage);
                }
            }
        }
    }

    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player) {
        super.onImpact(ray, level, projectile, player);

        if (player != null) {
            if (player.isShiftKeyDown()) {
                Vec3 vec = getBlockHitOffset(ray, projectile, -0.1f);
                BlockPos blockPos = BlockPos.containing(vec.x(), vec.y(), vec.z());

                int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
                int radius = focusLevel + 1;

                Color color = getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                ItemStack stack = player.getItemInHand(player.getUsedItemHand());

                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            if (WissenItemUtil.canRemoveWissen(stack, 5)) {
                                BlockPos pos = blockPos.relative(Direction.Axis.X, x).relative(Direction.Axis.Y, y).relative(Direction.Axis.Z, z);

                                BlockEvent.BreakEvent breakEv = new BlockEvent.BreakEvent(level, blockPos, level.getBlockState(pos), player);

                                if (!level.getBlockState(pos).isAir() && !MinecraftForge.EVENT_BUS.post(breakEv)) {
                                    if (level.getBlockState(pos).getBlock() instanceof FireBlock) {
                                        level.destroyBlock(pos, false);
                                        removeWissen(stack, projectile.getStats(), player, 5);
                                        PacketHandler.sendToTracking(level, player.getOnPos(), new WaterRaySpellEffectPacket((float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f, r, g, b));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/
}
