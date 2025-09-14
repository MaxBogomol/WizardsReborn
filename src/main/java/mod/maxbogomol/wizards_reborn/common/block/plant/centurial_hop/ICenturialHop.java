package mod.maxbogomol.wizards_reborn.common.block.plant.centurial_hop;

import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public interface ICenturialHop {
    BooleanProperty BERRIES = BlockStateProperties.BERRIES;

    static InteractionResult use(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        if (state.getValue(BERRIES)) {
            Block.popResource(level, pos, new ItemStack(WizardsRebornItems.CENTURIAL_HOP_CONE.get(), 1));
            float f = Mth.randomBetween(level.random, 0.8F, 1.2F);
            level.playSound(null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, f);
            BlockState blockstate = state.setValue(BERRIES, Boolean.valueOf(false));
            level.setBlock(pos, blockstate, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockstate));
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }
}
