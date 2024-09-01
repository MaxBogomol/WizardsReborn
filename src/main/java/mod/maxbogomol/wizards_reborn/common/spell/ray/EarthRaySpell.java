package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.EarthRaySpellEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
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
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

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
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player) {
        super.onImpact(ray, world, projectile, player);

        if (player != null) {
            if (player.isShiftKeyDown()) {
                int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
                ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                if (projectile.tickCount % (20 - (focusLevel * 3)) == 0) {
                    if (WissenItemUtil.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player))) {
                        Vec3 vec = getBlockHitOffset(ray, projectile, 0.1f);
                        BlockPos blockPos = BlockPos.containing(vec.x(), vec.y(), vec.z());
                        BlockState blockState = world.getBlockState(blockPos);

                        BlockEvent.BreakEvent breakEv = new BlockEvent.BreakEvent(world, blockPos, blockState, player);

                        if (!blockState.isAir() && !MinecraftForge.EVENT_BUS.post(breakEv)) {
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
