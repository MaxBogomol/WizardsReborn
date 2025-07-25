package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import mod.maxbogomol.wizards_reborn.registry.common.entity.WizardsRebornEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalMakeLove.class)
public class AnimalMakeLoveMixin {

    @Inject(at = @At("RETURN"), method = "<init>")
    private void wizards_reborn$AnimalMakeLove(EntityType partnerType, float speedModifier, CallbackInfo ci) {
        if (SniffaloEntity.isSnifferBrain) {
            AnimalMakeLove self = (AnimalMakeLove) ((Object) this);
            self.partnerType = WizardsRebornEntities.SNIFFALO.get();
        }
    }
}