package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.integration.common.farmersdelight.WizardsRebornFarmersDelight;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

@Mixin(RichSoilBlock.class)
public abstract class RichSoilBlockMixin implements IMixinConfigPlugin {

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith("vectorwing.farmersdelight.common.block.RichSoilBlock")) {
            return WizardsRebornFarmersDelight.isLoaded();
        }
        return false;
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void wizards_reborn$randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!level.isClientSide()) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            Block aboveBlock = aboveState.getBlock();

            if (aboveBlock == WizardsRebornBlocks.MOR.get()) {
                level.setBlockAndUpdate(pos.above(), (WizardsRebornFarmersDelight.BlocksLoadedOnly.MOR_COLONY.get()).defaultBlockState());
                ci.cancel();
            }

            if (aboveBlock == WizardsRebornBlocks.ELDER_MOR.get()) {
                level.setBlockAndUpdate(pos.above(), (WizardsRebornFarmersDelight.BlocksLoadedOnly.ELDER_MOR_COLONY.get()).defaultBlockState());
                ci.cancel();
            }
        }
    }
}