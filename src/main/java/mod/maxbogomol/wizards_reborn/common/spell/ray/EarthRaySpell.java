package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.EarthRaySpellEffectPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class EarthRaySpell extends RaySpell {
    public EarthRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.EARTH_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return new Color(138, 201, 123);
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        if (player != null) {
            if (target.tickCount % 10 == 0) {
                ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                if (WissenItemUtils.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player))) {
                    removeWissen(stack, projectile.getStats(), player);
                    int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
                    float damage = (float) (2.0f + (focusLevel * 0.5));
                    DamageSource damageSource = new DamageSource(target.damageSources().generic().typeHolder(), projectile, player);
                    target.hurt(new DamageSource(target.damageSources().generic().typeHolder(), projectile, player), damage);
                    player.getInventory().hurtArmor(damageSource, damage, Inventory.ALL_ARMOR_SLOTS);
                }
            }
        }
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player) {
        super.onImpact(ray, world, projectile, player);

        if (player != null) {
            if (player.isShiftKeyDown()) {
                int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
                ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                if (projectile.tickCount % (20 - (focusLevel * 3)) == 0) {
                    if (WissenItemUtils.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player))) {
                        Vec3 vec = getBlockHitOffset(ray, projectile, 0.1f);
                        BlockPos blockPos = new BlockPos((int) vec.x(), (int) vec.y(), (int) vec.z());
                        BlockState blockState = world.getBlockState(blockPos);
                        if (!blockState.isAir()) {
                            if (canBreak(blockState)) {
                                world.destroyBlock(blockPos, true);

                                removeWissen(stack, projectile.getStats(), player);

                                Color color = getColor();
                                float r = color.getRed() / 255f;
                                float g = color.getGreen() / 255f;
                                float b = color.getBlue() / 255f;

                                PacketHandler.sendToTracking(world, player.getOnPos(), new EarthRaySpellEffectPacket((float) blockPos.getX() + 0.5f, (float) blockPos.getY() + 0.5f, (float) blockPos.getZ() + 0.5f, r, g, b));
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
        if (destroyTime > 0 && destroyTime < 10f) {
            return true;
        }

        return false;
    }
}
