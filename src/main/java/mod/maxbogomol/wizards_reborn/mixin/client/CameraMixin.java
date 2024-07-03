package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.client.event.ScreenshakeHandler;
import net.minecraft.client.Camera;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {
    private static RandomSource RANDOM = RandomSource.create();

    @Inject(method = "setup", at = @At("RETURN"))
    private void wizards_reborn$screenshake(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        ScreenshakeHandler.cameraTick((Camera) (Object) this, RANDOM);
    }
}