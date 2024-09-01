package mod.maxbogomol.wizards_reborn.common.block.underground_grape;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class UndergroundGrapeVinesPlantBlock extends CaveVinesPlantBlock {

    public UndergroundGrapeVinesPlantBlock(Properties builder) {
        super(builder);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) WizardsRebornBlocks.UNDERGROUND_GRAPE_VINES.get();
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE_VINE.get());
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return IUndergroundGrape.use(player, blockState, level, blockPos);
    }
}
