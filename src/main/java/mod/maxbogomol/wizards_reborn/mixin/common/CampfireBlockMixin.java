package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {

    @Inject(method = "isSmokeSource", at = @At("HEAD"), cancellable = true)
    private void wizards_reborn$isSmokeSource(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(WizardsRebornBlockTags.CAMPFIRE_SIGNAL_SMOKE)) {
            cir.setReturnValue(true);
        }
    }
}