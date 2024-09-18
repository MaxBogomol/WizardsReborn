package mod.maxbogomol.wizards_reborn.common.block.steam_thermal_storage;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class SteamThermalStorageBlockEntity extends BlockEntityBase implements TickableBlockEntity, ISteamBlockEntity {
    public int steam = 0;

    public Random random = new Random();

    public SteamThermalStorageBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SteamThermalStorageBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.STEAM_THERMAL_STORAGE.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
            float amount = (float) getSteam() / (float) getMaxSteam();

            Vec3 posSteam = new Vec3(0F, 0F, 0F);
            switch (getBlockState().getValue(SteamThermalStorageBlock.AXIS)) {
                case X:
                    posSteam = new Vec3(0.25F, 0.15F, 0.15F);
                    break;
                case Y:
                    posSteam = new Vec3(0.15F, 0.25F, 0.15F);
                    break;
                case Z:
                    posSteam = new Vec3(0.15F, 0.15F, 0.25F);
                    break;
                default:
                    posSteam = new Vec3(0F, 0F, 0F);
                    break;
            }

            for (int i = 0; i < 2 * amount; i++) {
                if (random.nextFloat() < amount) {
                    ParticleBuilder.create(FluffyFurParticles.SMOKE)
                            .setColorData(ColorParticleData.create(Color.WHITE).build())
                            .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                            .setScaleData(GenericParticleData.create(0f, 0.3f).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                            .setLifetime(30)
                            .randomVelocity(0.015f)
                            .flatRandomOffset(0.5f * posSteam.x(), 0.5f * posSteam.y(), 0.5f * posSteam.z())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                }
            }
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("steam", steam);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        steam = tag.getInt("steam");
    }

    @Override
    public int getSteam() {
        return steam;
    }

    @Override
    public int getMaxSteam() {
        return 25000;
    }

    @Override
    public void setSteam(int steam) {
        this.steam = steam;
    }

    @Override
    public void addSteam(int steam) {
        this.steam = this.steam + steam;
        if (this.steam > getMaxSteam()) {
            this.steam = getMaxSteam();
        }
    }

    @Override
    public void removeSteam(int steam) {
        this.steam = this.steam - steam;
        if (this.steam < 0) {
            this.steam = 0;
        }
    }

    @Override
    public boolean canSteamTransfer(Direction side) {
        return true;
    }

    @Override
    public boolean canSteamConnection(Direction side) {
        return switch (getBlockState().getValue(SteamThermalStorageBlock.AXIS)) {
            case X -> (side == Direction.EAST || side == Direction.WEST);
            case Y -> (side == Direction.UP || side == Direction.DOWN);
            case Z -> (side == Direction.NORTH || side == Direction.SOUTH);
            default -> false;
        };
    }
}
