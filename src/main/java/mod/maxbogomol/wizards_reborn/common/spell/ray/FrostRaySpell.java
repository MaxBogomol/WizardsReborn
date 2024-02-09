package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.FrostRaySpellEffectPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.level.BlockEvent;

import java.awt.*;
import java.util.function.Predicate;

public class FrostRaySpell extends RaySpell {
    public FrostRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.WATER_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return new Color(221, 243, 254);
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
                    float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
                    float damage = (float) (1.5f + (focusLevel * 0.5)) + magicModifier;
                    target.hurt(new DamageSource(target.damageSources().freeze().typeHolder(), projectile, player), damage);
                    target.clearFire();
                    int frost = target.getTicksFrozen() + 75;
                    if (frost > 250) {
                        frost = 250;
                    }
                    target.setTicksFrozen(frost);

                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    PacketHandler.sendToTracking(world, player.getOnPos(), new FrostRaySpellEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
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
                if (projectile.tickCount % (4 - focusLevel) == 0) {
                    if (WissenItemUtils.canRemoveWissen(stack, 1)) {
                        Vec3 vec = getBlockHitOffset(ray, projectile, 0.1f);
                        BlockPos blockPos = new BlockPos(Mth.floor(vec.x()), Mth.floor(vec.y()), Mth.floor(vec.z()));
                        BlockState blockState = world.getBlockState(blockPos);
                        BlockState blockStateIce = Blocks.FROSTED_ICE.defaultBlockState();

                        BlockEvent.EntityPlaceEvent placeEv = new BlockEvent.EntityPlaceEvent(
                                BlockSnapshot.create(world.dimension(), world, blockPos),
                                blockStateIce,
                                player
                        );

                        if (blockState == FrostedIceBlock.meltsInto() && blockStateIce.canSurvive(world, blockPos) && world.isUnobstructed(blockStateIce, blockPos, CollisionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player, net.minecraftforge.common.util.BlockSnapshot.create(world.dimension(), world, blockPos), net.minecraft.core.Direction.UP)  && !MinecraftForge.EVENT_BUS.post(placeEv)) {
                            world.setBlockAndUpdate(blockPos, blockStateIce);
                            world.scheduleTick(blockPos, Blocks.FROSTED_ICE, Mth.nextInt(player.getRandom(), 300, 600));

                            WissenItemUtils.removeWissen(stack, 1);

                            Color color = getColor();
                            float r = color.getRed() / 255f;
                            float g = color.getGreen() / 255f;
                            float b = color.getBlue() / 255f;

                            PacketHandler.sendToTracking(world, player.getOnPos(), new FrostRaySpellEffectPacket((float) blockPos.getX() + 0.5f, (float) blockPos.getY() + 0.5f, (float) blockPos.getZ() + 0.5f, r, g, b));
                        }
                    }
                }
            }
        }
    }

    @Override
    public HitResult getHitResult(SpellProjectileEntity pProjectile, Vec3 pStartVec, Vec3 pEndVecOffset, Level pLevel, Predicate<Entity> pFilter) {
        if (pProjectile.getSender() != null) {
            if (pProjectile.getSender().isShiftKeyDown()) {
                Vec3 vec3 = pStartVec.add(pEndVecOffset);
                HitResult hitresult = pLevel.clip(new ClipContext(pStartVec, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.WATER, pProjectile));
                if (hitresult.getType() != HitResult.Type.MISS) {
                    vec3 = hitresult.getLocation();
                }

                HitResult hitresult1 = ProjectileUtil.getEntityHitResult(pLevel, pProjectile, pStartVec, vec3, pProjectile.getBoundingBox().expandTowards(pEndVecOffset).inflate(1.0D), pFilter);
                if (hitresult1 != null) {
                    hitresult = hitresult1;
                }

                return hitresult;
            }
        }

        return getHitResultStandart(pProjectile, pStartVec, pEndVecOffset, pLevel, pFilter);
    }
}