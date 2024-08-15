package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.LeatherCollarItem;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Phantom.class)
public class PhantomMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void wizards_reborn$addCollarGoal(CallbackInfo ci) {
        Phantom self = (Phantom) ((Object) this);
        GoalSelector targetSelector = self.goalSelector;
        for (WrappedGoal pg : targetSelector.getAvailableGoals()) {
            if (pg.getGoal() instanceof Phantom.PhantomSweepAttackGoal targetGoal && self.getTarget() instanceof Player) {
                List<Player> list = self.level().getEntitiesOfClass(Player.class, self.getBoundingBox().inflate(16.0D), EntitySelector.ENTITY_STILL_ALIVE);
                for (Player player : list) {
                    if (LeatherCollarItem.isWearCollar(player)) {
                        targetGoal.isScaredOfCat = true;
                        break;
                    }
                }
            }
        }
    }
}