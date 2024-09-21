package mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.steam;

import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.SteamUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.pipe.steam.SteamPipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SteamExtractorBlockEntity extends SteamPipeBaseBlockEntity {
    boolean active;
    public static final int MAX_DRAIN = 150;

    public SteamExtractorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public SteamExtractorBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.STEAM_EXTRACTOR.get(), pos, state);
    }

    public void tick() {
        if (!level.isClientSide()) {
            active = (level.hasNeighborSignal(getBlockPos()) != level.getBlockState(getBlockPos()).getValue(BlockStateProperties.POWERED));
            for (Direction facing : Direction.values()) {
                if (!getConnection(facing).transfer)
                    continue;
                BlockEntity blockEntity = level.getBlockEntity(getBlockPos().relative(facing));
                if (blockEntity != null && !(blockEntity instanceof SteamPipeBaseBlockEntity)) {
                    if (active) {
                        if (blockEntity instanceof ISteamBlockEntity steamBlockEntity) {
                            if (steamBlockEntity.canSteamTransfer(facing.getOpposite())) {
                                int steam_remain = WissenUtil.getAddWissenRemain(steam, MAX_DRAIN, getMaxSteam());
                                steam_remain = MAX_DRAIN - steam_remain;
                                int removeRemain = SteamUtil.getRemoveSteamRemain(steamBlockEntity.getSteam(), steam_remain);
                                steam_remain = steam_remain - removeRemain;
                                if (steam_remain > 0) {
                                    steamBlockEntity.removeSteam(steam_remain);
                                    addSteam(steam_remain);
                                    BlockEntityUpdate.packet(this);
                                    BlockEntityUpdate.packet(blockEntity);
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
