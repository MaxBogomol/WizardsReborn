package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.WaterRaySpellEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

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
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player) {
        super.onImpact(ray, world, projectile, player);

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

                                BlockEvent.BreakEvent breakEv = new BlockEvent.BreakEvent(world, blockPos, world.getBlockState(pos), player);

                                if (!world.getBlockState(pos).isAir() && !MinecraftForge.EVENT_BUS.post(breakEv)) {
                                    if (world.getBlockState(pos).getBlock() instanceof FireBlock) {
                                        world.destroyBlock(pos, false);
                                        removeWissen(stack, projectile.getStats(), player, 5);
                                        PacketHandler.sendToTracking(world, player.getOnPos(), new WaterRaySpellEffectPacket((float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f, r, g, b));
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
