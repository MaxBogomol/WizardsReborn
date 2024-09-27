package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.fluffy_fur.client.sound.BlockEntitySoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.light_emitter.LightEmitterBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.Minecraft;

public class LightEmitterSoundInstance extends BlockEntitySoundInstance<LightEmitterBlockEntity> {
    public LightEmitterSoundInstance(LightEmitterBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsRebornSounds.ARCANUM_LENS_RESONATE.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
    }

    @Override
    public void tick() {
        if (blockEntity.getLight() <= 0) {
            stop();
        }
        super.tick();
    }

    public static LightEmitterSoundInstance getSound(LightEmitterBlockEntity blockEntity) {
        return new LightEmitterSoundInstance(blockEntity, 1, 1);
    }

    public void playSound() {
        Minecraft.getInstance().getSoundManager().queueTickingSound(this);
    }
}