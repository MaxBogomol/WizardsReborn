package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.common.block.baulk.CrossBaulkBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(at = @At("HEAD"), method = "canSupportCenter", cancellable = true)
    private static void wizards_reborn$canSupportCenter(LevelReader level, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (CrossBaulkBlock.canConnectLantern(level, pos, direction)) {
            cir.setReturnValue(true);
        }
    }
}
