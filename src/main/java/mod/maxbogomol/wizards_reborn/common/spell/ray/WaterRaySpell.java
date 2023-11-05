package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.WaterRaySpellEffectPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class WaterRaySpell extends RaySpell {
    public WaterRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.WATER_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return new Color(152, 180, 223);
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
                    target.hurt(new DamageSource(target.damageSources().drown().typeHolder(), projectile, player), (float) (1.0f + (focusLevel * 0.5)));
                    target.clearFire();
                    int frost = target.getTicksFrozen() + 1;
                    if (frost > 250) {
                        frost = 250;
                    }
                    target.setTicksFrozen(frost);
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
                BlockPos blockPos = new BlockPos(Mth.floor(vec.x()), Mth.floor(vec.y()), Mth.floor(vec.z()));

                int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
                int radius = focusLevel + 1;

                Color color = getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                ItemStack stack = player.getItemInHand(player.getUsedItemHand());

                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            if (WissenItemUtils.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player))) {
                                BlockPos pos = blockPos.relative(Direction.Axis.X, x).relative(Direction.Axis.Y, y).relative(Direction.Axis.Z, z);

                                if (!world.getBlockState(pos).isAir()) {
                                    if (world.getBlockState(pos).getBlock() instanceof FireBlock) {
                                        world.destroyBlock(pos, false);
                                        removeWissen(stack, projectile.getStats(), player);
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
