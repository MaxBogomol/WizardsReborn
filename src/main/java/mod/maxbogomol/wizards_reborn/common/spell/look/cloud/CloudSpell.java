package mod.maxbogomol.wizards_reborn.common.spell.look.cloud;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.utils.RenderUtils;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.spell.look.block.BlockLookSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class CloudSpell extends BlockLookSpell {
    public CloudSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public float getBlockDistance() {
        return 5f;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public int getCooldown() {
        return 350;
    }

    @Override
    public int getWissenCost() {
        return 200;
    }

    @Override
    public void lookSpell(Level world, Player player, InteractionHand hand) {
        CompoundTag stats = getStats(player.getItemInHand(hand));
        Vec3 pos = getBlockHit(world, player, hand).getPosHit();

        SpellProjectileEntity entity = new SpellProjectileEntity(WizardsReborn.SPELL_PROJECTILE.get(), world).shoot(
                pos.x, pos.y, pos.z, 0, 0, 0, player.getUUID(), this.getId(), stats
        );
        world.addFreshEntity(entity);
    }

    @Override
    public boolean canLookSpell(Level world, Player player, InteractionHand hand) {
        return !getBlockHit(world, player, hand).hasBlockHit();
    }

    @Override
    public HitResult getBlockHit(Level world, Player player, InteractionHand hand) {
        float distance = getLookDistance(world, player, hand);
        Vec3 firstPos = player.getLookAngle().scale(distance);
        Vec3 lookPos = getHitPos(world, player.getEyePosition(), player.getEyePosition().add(firstPos.x(), 0, firstPos.z())).getPosHit();
        return getHitPos(world, lookPos, new Vec3(lookPos.x(), lookPos.y() + getBlockDistance(world, player, hand), lookPos.z()));
    }

    @Override
    public void entityTick(SpellProjectileEntity entity) {
        if (!entity.level().isClientSide) {
            if (entity.tickCount > getLifeTime(entity)) {
                entity.remove();
            }
            rain(entity, entity.getSender());
        } else {
            cloudEffect(entity);
        }
    }

    public void cloudEffect(SpellProjectileEntity entity) {
        Color color = getColor();

        float ySize = 1f;
        float size = getCloudSize(entity);

        for (int i = 0; i < 5; i++) {
            if (random.nextFloat() < 0.4f) {
                ParticleBuilder.create(FluffyFur.SMOKE_PARTICLE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.45f, 0).build())
                        .setScaleData(GenericParticleData.create(1.5f).build())
                        .randomSpin(0.1f)
                        .setLifetime(20)
                        .randomVelocity(0.007f)
                        .randomOffset(size, ySize, size)
                        .spawn(entity.level(), entity.getX(), entity.getY(), entity.getZ());
            }
            if (random.nextFloat() < 0.8f) {
                ParticleBuilder.create(FluffyFur.SMOKE_PARTICLE)
                        .setRenderType(RenderUtils.DELAYED_PARTICLE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.45f, 0).build())
                        .setScaleData(GenericParticleData.create(1.5f).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .randomSpin(0.1f)
                        .setLifetime(20)
                        .randomVelocity(0.007f)
                        .randomOffset(size, ySize, size)
                        .spawn(entity.level(), entity.getX(), entity.getY(), entity.getZ());
            }
        }

        boolean trails = hasTrails(entity);

        if (random.nextFloat() < (trails ? 0.5f : 0.8f)) {
            ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                    .setColorData(ColorParticleData.create(color).build())
                    .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                    .setScaleData(GenericParticleData.create(0.2f, 0).build())
                    .setLifetime(100)
                    .randomOffset(size, ySize, size)
                    .addVelocity(0, -0.2f, 0)
                    .spawn(entity.level(), entity.getX(), entity.getY(), entity.getZ());
        }

        if (trails && random.nextFloat() < 0.4f) {
            ParticleBuilder.create(FluffyFur.TRAIL_PARTICLE)
                    .setColorData(ColorParticleData.create(color).build())
                    .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                    .setScaleData(GenericParticleData.create(0.2f, 0).build())
                    .setLifetime(70)
                    .randomOffset(size, ySize, size)
                    .addVelocity(0, -0.2f, 0)
                    .spawn(entity.level(), entity.getX(), entity.getY(), entity.getZ());
        }
    }

    public int getLifeTime(SpellProjectileEntity entity) {
        return 300;
    }

    public boolean hasTrails(SpellProjectileEntity entity) {
        return false;
    }

    public void rain(SpellProjectileEntity entity, Player player) {

    }

    public boolean isValidPos(SpellProjectileEntity entity, Vec3 pos) {
        return !getHitPos(entity.level(), pos, new Vec3(pos.x(), entity.getY(), pos.z())).hasBlockHit();
    }

    public float getCloudSize(SpellProjectileEntity entity) {
        int focusLevel = CrystalUtils.getStatLevel(entity.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getSender());
        return (2.25f + ((focusLevel + magicModifier) * 0.25f));
    }
}
