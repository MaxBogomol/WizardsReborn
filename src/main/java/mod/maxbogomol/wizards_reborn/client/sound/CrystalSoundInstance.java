package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.fluffy_fur.client.sound.BlockEntitySoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.Minecraft;

public class CrystalSoundInstance extends BlockEntitySoundInstance<CrystalBlockEntity> {
    public CrystalSoundInstance(CrystalBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsRebornSounds.ARCANUM_LENS_RESONATE.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
    }

    @Override
    public void tick() {
        if (blockEntity.getLight() <= 0 || !blockEntity.isToBlock || !blockEntity.startRitual) {
            stop();
        }
        super.tick();
    }

    public static CrystalSoundInstance getSound(CrystalBlockEntity blockEntity) {
        return new CrystalSoundInstance(blockEntity, 1, 1);
    }

    public void playSound() {
        Minecraft.getInstance().getSoundManager().queueTickingSound(this);
    }
}