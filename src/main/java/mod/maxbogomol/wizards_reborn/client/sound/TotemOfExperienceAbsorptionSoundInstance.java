package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.fluffy_fur.client.sound.BlockEntitySoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience_absorption.TotemOfExperienceAbsorptionBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.Minecraft;

public class TotemOfExperienceAbsorptionSoundInstance extends BlockEntitySoundInstance<TotemOfExperienceAbsorptionBlockEntity> {
    public TotemOfExperienceAbsorptionSoundInstance(TotemOfExperienceAbsorptionBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsRebornSounds.TOTEM_OF_EXPERIENCE_ABSORPTION_LOOP.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
    }

    @Override
    public void tick() {
        if (!(blockEntity.getExperience() > 0 && blockEntity.getWissen() < blockEntity.getMaxWissen())) {
            stop();
        }
        super.tick();
    }

    public static TotemOfExperienceAbsorptionSoundInstance getSound(TotemOfExperienceAbsorptionBlockEntity tileEntity) {
        return new TotemOfExperienceAbsorptionSoundInstance(tileEntity, 1, 1);
    }

    public void playSound() {
        Minecraft.getInstance().getSoundManager().queueTickingSound(this);
    }
}