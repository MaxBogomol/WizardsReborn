package mod.maxbogomol.wizards_reborn.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TileEntitySoundInstance<T extends BlockEntity> extends AbstractTickableSoundInstance {

    public T blockEntity;
    public static RandomSource RANDOM = RandomSource.create();

    public TileEntitySoundInstance(T blockEntity, SoundEvent soundEvent, float volume, float pitch) {
        super(soundEvent, SoundSource.BLOCKS, RANDOM);
        this.blockEntity = blockEntity;
        this.volume = volume;
        this.pitch = pitch;
        this.delay = 0;
        this.looping = true;
    }

    @Override
    public void tick() {
        if (blockEntity.isRemoved()) {
            stop();
        }
    }
}
