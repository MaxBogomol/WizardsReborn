package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.tileentity.SteamPipeBaseTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SteamExtractorBlock extends TinyExtractorBaseBlock {
    private static Random random = new Random();

    public SteamExtractorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public TagKey<Block> getConnectionTag() {
        return WizardsReborn.STEAM_PIPE_CONNECTION_BLOCK_TAG;
    }

    @Override
    public TagKey<Block> getToggleConnectionTag() {
        return WizardsReborn.STEAM_PIPE_CONNECTION_TOGGLE_BLOCK_TAG;
    }

    @Override
    public boolean connectToTile(BlockEntity blockEntity, Direction direction) {
        boolean connect = false;
        if (blockEntity != null) {
            if (blockEntity instanceof ISteamTileEntity steamTile) {
                connect = steamTile.canSteamConnection(direction.getOpposite());
            }
        }
        return connect;
    }

    @Override
    public boolean unclog(BlockEntity blockEntity, Level level, BlockPos pos) {
        if (blockEntity instanceof SteamPipeBaseTileEntity pipeEntity && pipeEntity.clogged) {
            pipeEntity.setSteam(0);
            level.updateNeighbourForOutputSignal(pos, this);
            PacketUtils.SUpdateTileEntityPacket(pipeEntity);
            return true;
        }

        return false;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return WizardsReborn.STEAM_EXTRACTOR_TILE_ENTITY.get().create(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        SteamPipeBaseTileEntity tile = (SteamPipeBaseTileEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) tile.getSteam() / tile.getMaxSteam()) * 14.0F);
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.isClientSide()) {
            if (!player.isCreative()) {
                if (world.getBlockEntity(pos) != null) {
                    if (world.getBlockEntity(pos) instanceof ISteamTileEntity tile) {
                        if (tile.getMaxSteam() > 0) {
                            float amount = (float) tile.getSteam() / (float) tile.getMaxSteam();
                            for (int i = 0; i < 15 * amount; i++) {
                                Particles.create(WizardsReborn.STEAM_PARTICLE)
                                        .addVelocity(((random.nextDouble() - 0.5D) / 30), (random.nextDouble() / 30) + 0.001, ((random.nextDouble() - 0.5D) / 30))
                                        .setAlpha(0.4f, 0).setScale(0.1f, 0.5f)
                                        .setColor(1f, 1f, 1f)
                                        .setLifetime(30)
                                        .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                        .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
                            }
                        }
                    }
                }
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }
}
