package mod.maxbogomol.wizards_reborn.common.block.steam_pipe;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.pipe.TinyPipeBaseBlock;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornTags;
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

import java.awt.*;

public class SteamPipeBlock extends TinyPipeBaseBlock {

    public SteamPipeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public TagKey<Block> getConnectionTag() {
        return WizardsRebornTags.STEAM_PIPE_CONNECTION_BLOCK;
    }

    @Override
    public TagKey<Block> getToggleConnectionTag() {
        return WizardsRebornTags.STEAM_PIPE_CONNECTION_TOGGLE_BLOCK;
    }

    @Override
    public boolean connected(Direction direction, BlockState state) {
        return false;
    }

    @Override
    public boolean connectToTile(BlockEntity blockEntity, Direction direction) {
        boolean connect = false;
        if (blockEntity != null) {
            if (blockEntity instanceof ISteamBlockEntity steamTile) {
                connect = steamTile.canSteamConnection(direction.getOpposite());
            }
        }
        return connect;
    }

    @Override
    public boolean unclog(BlockEntity blockEntity, Level level, BlockPos pos) {
        if (blockEntity instanceof SteamPipeBaseBlockEntity pipeEntity && pipeEntity.clogged) {
            pipeEntity.setSteam(0);
            level.updateNeighbourForOutputSignal(pos, this);
            BlockEntityUpdate.packet(pipeEntity);
            return true;
        }

        return false;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return WizardsRebornBlockEntities.STEAM_PIPE.get().create(pPos, pState);
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
        SteamPipeBaseBlockEntity tile = (SteamPipeBaseBlockEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) tile.getSteam() / tile.getMaxSteam()) * 14.0F);
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.isClientSide()) {
            if (!player.isCreative()) {
                if (world.getBlockEntity(pos) != null) {
                    if (world.getBlockEntity(pos) instanceof ISteamBlockEntity tile) {
                        if (tile.getMaxSteam() > 0) {
                            float amount = (float) tile.getSteam() / (float) tile.getMaxSteam();
                            ParticleBuilder.create(FluffyFurParticles.SMOKE)
                                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                                    .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                                    .randomSpin(0.1f)
                                    .setLifetime(30)
                                    .randomVelocity(0.015f)
                                    .repeat(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, (int) (15 * amount));
                        }
                    }
                }
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }
}
