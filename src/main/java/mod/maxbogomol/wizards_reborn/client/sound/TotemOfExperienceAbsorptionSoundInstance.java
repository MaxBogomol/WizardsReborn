package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.tileentity.TotemOfExperienceAbsorptionTileEntity;
import net.minecraft.client.Minecraft;

public class TotemOfExperienceAbsorptionSoundInstance extends TileEntitySoundInstance<TotemOfExperienceAbsorptionTileEntity> {
    public TotemOfExperienceAbsorptionSoundInstance(TotemOfExperienceAbsorptionTileEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsReborn.TOTEM_OF_EXPERIENCE_ABSORPTION_LOOP_SOUND.get(), volume, pitch);
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

    public static TotemOfExperienceAbsorptionSoundInstance playSound(TotemOfExperienceAbsorptionTileEntity tileEntity) {
        TotemOfExperienceAbsorptionSoundInstance sound = new TotemOfExperienceAbsorptionSoundInstance(tileEntity, 1, 1);
        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
        return sound;
    }
}