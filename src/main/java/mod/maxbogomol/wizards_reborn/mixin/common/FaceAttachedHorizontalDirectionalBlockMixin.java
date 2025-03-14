package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.ExtractorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.TinyExtractorBaseBlock;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FaceAttachedHorizontalDirectionalBlock.class)
public abstract class FaceAttachedHorizontalDirectionalBlockMixin {

    @Inject(at = @At("RETURN"), method = "canSurvive", cancellable = true)
    private void wizards_reborn$canSurvive(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(WizardsRebornBlockTags.EXTRACTOR_LEVER_CONNECTION)) {
            Block block = level.getBlockState(pos.relative(wizards_reborn$getConnectedDirection(state).getOpposite())).getBlock();
            if (block instanceof ExtractorBaseBlock) {
                cir.setReturnValue(true);
            }
            if (block instanceof TinyExtractorBaseBlock) {
                cir.setReturnValue(true);
            }
        }
    }

    @Unique
    private static Direction wizards_reborn$getConnectedDirection(BlockState state) {
        return switch (state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE)) {
            case CEILING -> Direction.DOWN;
            case FLOOR -> Direction.UP;
            default -> state.getValue(FaceAttachedHorizontalDirectionalBlock.FACING);
        };
    }
}
