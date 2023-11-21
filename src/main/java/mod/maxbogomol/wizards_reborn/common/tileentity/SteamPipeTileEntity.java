package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class SteamPipeTileEntity extends SteamPipeBaseTileEntity implements TickableBlockEntity {

    public SteamPipeTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public SteamPipeTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.STEAM_PIPE_TILE_ENTITY.get(), pos, state);
    }

    public void tick() {
        super.tick();
        if (level.isClientSide()) {
            if (clogged && isAnySideUnclogged()) {
                Random posRand = new Random(getBlockPos().asLong());
                double angleA = posRand.nextDouble() * Math.PI * 2;
                double angleB = posRand.nextDouble() * Math.PI * 2;
                float xOffset = (float) (Math.cos(angleA) * Math.cos(angleB));
                float yOffset = (float) (Math.sin(angleA) * Math.cos(angleB));
                float zOffset = (float) Math.sin(angleB);
                float speed = 0.03f;
                float vx = xOffset * speed + posRand.nextFloat() * speed * 0.3f;
                float vy = yOffset * speed + posRand.nextFloat() * speed * 0.3f;
                float vz = zOffset * speed + posRand.nextFloat() * speed * 0.3f;
                Particles.create(WizardsReborn.STEAM_PARTICLE)
                        .addVelocity(vx, vy, vz)
                        .setAlpha(0.4f, 0).setScale(0.05f, 0.15f)
                        .setColor(1F, 1F, 1F)
                        .setLifetime(30)
                        .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .spawn(level, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5);
            }
        }
    }

    @Override
    public int getCapacity() {
        return 350;
    }

    @Override
    public int getSteam() {
        return steam;
    }

    @Override
    public int getMaxSteam() {
        return getCapacity();
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
        return (getConnection(side).transfer);
    }

    @Override
    public boolean canSteamConnection(Direction side) {
        return true;
    }
}
