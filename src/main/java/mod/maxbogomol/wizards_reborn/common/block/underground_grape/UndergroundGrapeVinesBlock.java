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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class UndergroundGrapeVinesBlock extends CaveVinesBlock {

    public UndergroundGrapeVinesBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected Block getBodyBlock() {
        return WizardsRebornBlocks.UNDERGROUND_GRAPE_VINES_PLANT.get();
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(WizardsRebornItems.UNDERGROUND_GRAPE_VINE.get());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return IUndergroundGrape.use(player, state, level, pos);
    }
}
