package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.SteamUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SteamExtractorTileEntity extends SteamPipeBaseTileEntity {
    boolean active;
    public static final int MAX_DRAIN = 150;

    public SteamExtractorTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public SteamExtractorTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.STEAM_EXTRACTOR_TILE_ENTITY.get(), pos, state);
    }

    public void tick() {
        if (!level.isClientSide()) {
            active = (level.hasNeighborSignal(getBlockPos()) != level.getBlockState(getBlockPos()).getValue(BlockStateProperties.POWERED));
            for (Direction facing : Direction.values()) {
                if (!getConnection(facing).transfer)
                    continue;
                BlockEntity tile = level.getBlockEntity(getBlockPos().relative(facing));
                if (tile != null && !(tile instanceof SteamPipeBaseTileEntity)) {
                    if (active) {
                        if (tile instanceof ISteamTileEntity steamTileEntity) {
                            if (steamTileEntity.canSteamTransfer(facing.getOpposite())) {
                                int steam_remain = WissenUtils.getAddWissenRemain(steam, MAX_DRAIN, getMaxSteam());
                                steam_remain = MAX_DRAIN - steam_remain;
                                int removeRemain = SteamUtils.getRemoveSteamRemain(steamTileEntity.getSteam(), steam_remain);
                                steam_remain = steam_remain - removeRemain;
                                if (steam_remain > 0) {
                                    steamTileEntity.removeSteam(steam_remain);
                                    addSteam(steam_remain);
                                    PacketUtils.SUpdateTileEntityPacket(this);
                                    PacketUtils.SUpdateTileEntityPacket(tile);
                                }
                            }
                        }
                        setFrom(facing, true);
                    } else {
                        setFrom(facing, false);
                    }
                }
            }
        }
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
