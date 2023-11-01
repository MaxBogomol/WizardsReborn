package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

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

        if (target.tickCount % 10 == 0) {
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());
            removeWissen(stack, projectile.getStats());
            int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
            float damage = (float) (2.0f + (focusLevel * 0.5));
            DamageSource damageSource = new DamageSource(target.damageSources().generic().typeHolder(), projectile, player);
            target.hurt(new DamageSource(target.damageSources().generic().typeHolder(), projectile, player), damage);
            player.getInventory().hurtArmor(damageSource, damage, Inventory.ALL_ARMOR_SLOTS);
        }
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player) {
        super.onImpact(ray, world, projectile, player);

        if (projectile.tickCount % 10 == 0) {
            //BlockState blockState = world.getBlockState(new BlockPos((int) ray.getLocation().x(), (int) ray.getLocation().y(), (int) ray.getLocation().z()));
            //if (world instanceof ServerLevel serverLevel) {
            //    serverLevel.sendBlockUpdated(pPos, blockstate, blockstate, 3);
            //}
            //if (player instanceof ServerPlayer serverPlayer) {
            //    serverPlayer.blockActionRestricted(world, new BlockPos((int) ray.getLocation().x(), (int) ray.getLocation().y(), (int) ray.getLocation().z()), ((ServerPlayer) player).gameMode.getGameModeForPlayer());
            //}
            //ray.п().scale(2);
            //world.destroyBlock(new BlockPos((int) ray.getLocation().x(), (int) ray.getLocation().y(), (int) ray.getLocation().z()), true);
            BlockPos blockPos = new BlockPos((int) Math.floor(ray.getLocation().x()), (int) Math.floor(ray.getLocation().y()), (int) Math.floor(ray.getLocation().z()));
            System.out.println(ray.getLocation().toString());
            BlockState blockState = world.getBlockState(blockPos);
            if (!CampfireBlock.canLight(blockState) && !CandleBlock.canLight(blockState) && !CandleCakeBlock.canLight(blockState)) {
                if (BaseFireBlock.canBePlacedAt(world, blockPos, Direction.UP)) {
                    world.playSound(player, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                    BlockState blockstate1 = BaseFireBlock.getState(world, blockPos);
                    world.setBlock(blockPos, blockstate1, 11);
                }
            }
        }
    }
}
