package mod.maxbogomol.wizards_reborn.common.block.engraved_wisestone;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.CubeParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.extensions.IForgeBlockEntity;

import java.awt.*;

public class EngravedWisestoneBlockEntity extends BlockEntityBase implements TickableBlockEntity, IWissenWandFunctionalBlockEntity {

    public boolean glow = false;
    public int cooldown = 0;

    public int glowTicks = 0;

    public EngravedWisestoneBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public EngravedWisestoneBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.ENGRAVED_WISESTONE.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            if (cooldown > 0) {
                cooldown--;
                update = true;
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (glow && glowTicks < 20) glowTicks++;
            if (!glow && glowTicks > 0) glowTicks--;

            if (getBlockState().getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
                if (glow && glowTicks < 20) {
                    double ticks = (ClientTickHandler.ticksInGame) * 20f;

                    float distance = 0.875f;
                    double yaw = Math.toRadians(getHorizontalBlockRotate() + ticks);
                    double pitch = 90;

                    double X = Math.sin(pitch) * Math.cos(yaw) * distance;
                    double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

                    Monogram monogram = block.getMonogram();
                    Color color = monogram.getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    ParticleBuilder.create(FluffyFurParticles.SQUARE)
                            .setRenderType(FluffyFurRenderTypes.ADDITIVE)
                            .setBehavior(CubeParticleBehavior.create().build())
                            .setColorData(ColorParticleData.create(r, g, b, random.nextFloat(), random.nextFloat(), random.nextFloat()).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create(0.1f, 0).setSpinOffset(0.1f * (float) ticks).build())
                            .setLifetime(30)
                            .randomVelocity(0.005f)
                            .spawn(level, getBlockPos().getX() + 0.5F + X, getBlockPos().getY() + (glowTicks / 20f), getBlockPos().getZ() + 0.5F + Z);
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(r, g, b, random.nextFloat(), random.nextFloat(), random.nextFloat()).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                            .setLifetime(40)
                            .randomVelocity(0.005f)
                            .spawn(level, getBlockPos().getX() + 0.5F + X, getBlockPos().getY() + (glowTicks / 20f), getBlockPos().getZ() + 0.5F + Z);
                }
            }
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("glow", glow);
        tag.putInt("cooldown", cooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        glow = tag.getBoolean("glow");
        cooldown = tag.getInt("cooldown");
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
    }

    public float getHorizontalBlockRotate() {
        switch (this.getBlockState().getValue(EngravedWisestoneBlock.FACING)) {
            case NORTH:
                return 0F;
            case SOUTH:
                return 180F;
            case WEST:
                return 90F;
            case EAST:
                return 270F;
            default:
                return 0F;
        }
    }

    public float getVerticalBlockRotate() {
        switch (this.getBlockState().getValue(EngravedWisestoneBlock.FACE)) {
            case FLOOR:
                return 90F;
            case WALL:
                return 0F;
            case CEILING:
                return -90F;
            default:
                return 0F;
        }
    }

    @Override
    public void wissenWandFunction() {
        if (getBlockState().getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
            if (cooldown <= 0) {
                glow = !glow;
                BlockEntityUpdate.packet(this);
                level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.5f, glow ? 2f : 0.5f);
                cooldown = 20;
            }
        }
    }
}
