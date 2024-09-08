package mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.steam;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.TinyExtractorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.steam.SteamPipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
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

public class SteamExtractorBlock extends TinyExtractorBaseBlock {

    public SteamExtractorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TagKey<Block> getConnectionTag() {
        return WizardsRebornBlockTags.STEAM_PIPE_CONNECTION;
    }

    @Override
    public TagKey<Block> getToggleConnectionTag() {
        return WizardsRebornBlockTags.STEAM_PIPE_CONNECTION_TOGGLE;
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
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return WizardsRebornBlockEntities.STEAM_EXTRACTOR.get().create(pos, state);
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
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        SteamPipeBaseBlockEntity tile = (SteamPipeBaseBlockEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) tile.getSteam() / tile.getMaxSteam()) * 14.0F);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.isClientSide()) {
            if (!player.isCreative()) {
                if (level.getBlockEntity(pos) != null) {
                    if (level.getBlockEntity(pos) instanceof ISteamBlockEntity tile) {
                        if (tile.getMaxSteam() > 0) {
                            float amount = (float) tile.getSteam() / (float) tile.getMaxSteam();
                            ParticleBuilder.create(FluffyFurParticles.SMOKE)
                                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                                    .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                                    .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                                    .setLifetime(30)
                                    .randomVelocity(0.015f)
                                    .repeat(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, (int) (15 * amount));
                        }
                    }
                }
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }
}
