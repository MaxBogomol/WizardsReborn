package mod.maxbogomol.wizards_reborn.common.block.steam_pipe;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SteamPipeBlockEntity extends SteamPipeBaseBlockEntity implements TickableBlockEntity {

    public SteamPipeBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public SteamPipeBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.STEAM_PIPE_TILE_ENTITY.get(), pos, state);
    }

    public void tick() {
        super.tick();
        if (level.isClientSide()) {
            if (clogged && isAnySideUnclogged()) {
                cloggedEffect();
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
