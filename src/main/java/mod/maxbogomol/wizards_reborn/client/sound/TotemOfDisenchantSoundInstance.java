package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.tileentity.TotemOfDisenchantTileEntity;
import net.minecraft.client.Minecraft;

public class TotemOfDisenchantSoundInstance extends TileEntitySoundInstance<TotemOfDisenchantTileEntity> {
    public TotemOfDisenchantSoundInstance(TotemOfDisenchantTileEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsReborn.TOTEM_OF_DISENCHANT_LOOP_SOUND.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
    }

    @Override
    public void tick() {
        if (!blockEntity.isStart) {
            stop();
        }
        super.tick();
    }

    public static void playSound(TotemOfDisenchantTileEntity tileEntity) {
        Minecraft.getInstance().getSoundManager().queueTickingSound(new TotemOfDisenchantSoundInstance(tileEntity, 1, 1));
    }
}