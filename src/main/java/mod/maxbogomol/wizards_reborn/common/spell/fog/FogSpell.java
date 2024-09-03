package mod.maxbogomol.wizards_reborn.common.spell.fog;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FogSpell extends Spell {
    public FogSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public boolean canSpellAir(Level world, Player player, InteractionHand hand) {
        return false;
    }

    @Override
    public InteractionResult onWandUseOn(ItemStack stack, UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();

        if (!world.isClientSide && canSpell(world, player, context.getHand())) {
            CompoundTag stats = getStats(stack);

            Vec3 pos = context.getClickedPos().getCenter();
            SpellProjectileEntity entity = new SpellProjectileEntity(WizardsRebornEntities.SPELL_PROJECTILE.get(), world).shoot(
                    pos.x, pos.y + 0.5, pos.z, 0, 0, 0, player.getUUID(), this.getId(), stats
            );
            world.addFreshEntity(entity);

            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            awardStat(player, stack);
            spellSound(player, context.getLevel());
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void entityTick(SpellProjectileEntity entity) {
        if (!entity.level().isClientSide) {
            if (entity.tickCount > getLifeTime(entity)) {
                entity.remove();
            }
            fog(entity, entity.getSender());
        } else {
            fogEffect(entity);
        }
    }

    public int getLifeTime(SpellProjectileEntity entity) {
        return 200;
    }

    public int getHeight(SpellProjectileEntity entity) {
        return 2;
    }

    public int getSize(SpellProjectileEntity entity) {
        return 1;
    }

    public int getAdditionalSize(SpellProjectileEntity entity) {
        return 1;
    }

    public boolean isCircle(SpellProjectileEntity entity) {
        return true;
    }

    public void fog(SpellProjectileEntity entity, Player player) {

    }

    public void fogEffect(SpellProjectileEntity entity) {
        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        float alpha = 1;
        int lifeTime = getLifeTime(entity);

        if (entity.tickCount < 20) {
            alpha = (entity.tickCount) / 20f;
        }
        if (entity.tickCount > lifeTime - 20) {
            alpha = ((lifeTime - entity.tickCount) / 20f);
        }
        if (alpha > 1f) alpha = 1f;
        if (alpha < 0f) alpha = 0f;

        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        int size = getSize(entity) + (getSize(entity) * focusLevel);
        List<BlockPos> blocks = getBlocks(entity.level(), entity.getOnPos(), (int) (size * alpha), 4, isCircle(entity));

        for (BlockPos pos : blocks) {
            if (random.nextFloat() < 0.2f) {
                ParticleBuilder.create(FluffyFurParticles.SMOKE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.45f, 0).build())
                        .setScaleData(GenericParticleData.create(0.5f).build())
                        .randomSpin(0.1f)
                        .setLifetime(20)
                        .randomVelocity(0.007f)
                        .flatRandomOffset(0.5f, 0, 0.5f)
                        .spawn(entity.level(), pos.getX() + 0.5f, pos.getY() + 0.1f, pos.getZ() + 0.5f);
            }
            if (random.nextFloat() < 0.4f) {
                ParticleBuilder.create(FluffyFurParticles.SMOKE)
                        .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                        .setScaleData(GenericParticleData.create(0.5f).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .randomSpin(0.1f)
                        .setLifetime(20)
                        .randomVelocity(0.007f)
                        .flatRandomOffset(0.5f, 0, 0.5f)
                        .spawn(entity.level(), pos.getX() + 0.5f, pos.getY() + 0.1f, pos.getZ() + 0.5f);
            }
        }
    }

    public List<BlockPos> getBlocks(Level level, BlockPos startPos, int size, int height, boolean circle) {
        List<BlockPos> list = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                for (int y = height; y > -height; y--) {
                    if (circle) {
                        float dst = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                        if (dst > size) break;
                    }

                    BlockPos blockPos = new BlockPos(startPos.getX() + x, startPos.getY() + y, startPos.getZ() + z);
                    BlockState blockState = level.getBlockState(blockPos);
                    if (!blockState.isAir()) {
                        list.add(blockPos.above());
                        break;
                    }
                }
            }
        }

        return list;
    }

    public List<Entity> getEntities(Level level, List<BlockPos> blockList) {
        List<Entity> list = new ArrayList<>();

        for (BlockPos pos : blockList) {
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5f, pos.getZ() + 1));
            for (Entity entity : entities) {
                if (!list.contains(entity)) {
                    list.add(entity);
                }
            }
        }

        return list;
    }
}
