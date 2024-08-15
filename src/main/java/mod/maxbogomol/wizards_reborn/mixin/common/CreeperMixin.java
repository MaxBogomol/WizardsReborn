package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.LeatherCollarItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public class CreeperMixin extends Monster {
    protected CreeperMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void wizards_reborn$addCollarGoal(CallbackInfo ci) {
        goalSelector.addGoal(0, new LeatherCollarItem.CreeperAvoidPlayerGoal((Creeper) (Object) this));
    }
}