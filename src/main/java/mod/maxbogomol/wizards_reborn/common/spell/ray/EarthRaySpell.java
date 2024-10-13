package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;

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
                    float damage = (float) (2.0f + (focusLevel * 0.5)) + magicModifier;
                    DamageSource damageSource = new DamageSource(target.damageSources().generic().typeHolder(), projectile, player);
                    target.hurt(new DamageSource(target.damageSources().generic().typeHolder(), projectile, player), damage);
                    if (target instanceof Player targetPlayer) {
                        targetPlayer.getInventory().hurtArmor(damageSource, damage, Inventory.ALL_ARMOR_SLOTS);
                    }
                }
            }
        }
    }

    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player) {
        super.onImpact(ray, level, projectile, player);

        if (player != null) {
            if (player.isShiftKeyDown()) {
                int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
                ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                if (projectile.tickCount % (20 - (focusLevel * 3)) == 0) {
                    if (WissenItemUtil.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player))) {
                        Vec3 vec = getBlockHitOffset(ray, projectile, 0.1f);
                        BlockPos blockPos = BlockPos.containing(vec.x(), vec.y(), vec.z());
                        BlockState blockState = level.getBlockState(blockPos);

                        BlockEvent.BreakEvent breakEv = new BlockEvent.BreakEvent(level, blockPos, blockState, player);

                        if (!blockState.isAir() && !MinecraftForge.EVENT_BUS.post(breakEv)) {
                            if (canBreak(blockState)) {
                                level.destroyBlock(blockPos, true);

                                removeWissen(stack, projectile.getStats(), player);

                                Color color = getColor();
                                float r = color.getRed() / 255f;
                                float g = color.getGreen() / 255f;
                                float b = color.getBlue() / 255f;

                                PacketHandler.sendToTracking(level, player.getOnPos(), new EarthRaySpellEffectPacket((float) blockPos.getX() + 0.5f, (float) blockPos.getY() + 0.5f, (float) blockPos.getZ() + 0.5f, r, g, b));
                            }
                        }
                    }
                }
            }
        }
    }*/

    public boolean canBreak(BlockState blockState) {
        float destroyTime = blockState.getBlock().defaultDestroyTime();
        if (blockState.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        }
        if (blockState.is(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        }
        if (destroyTime > 0 && destroyTime < 10f) {
            return true;
        }

        return false;
    }
}
