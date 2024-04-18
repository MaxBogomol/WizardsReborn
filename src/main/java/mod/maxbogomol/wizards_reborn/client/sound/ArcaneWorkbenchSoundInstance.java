package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneWorkbenchTileEntity;
import net.minecraft.client.Minecraft;

public class ArcaneWorkbenchSoundInstance extends TileEntitySoundInstance<ArcaneWorkbenchTileEntity> {
    public ArcaneWorkbenchSoundInstance(ArcaneWorkbenchTileEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsReborn.ARCANE_WORKBENCH_LOOP_SOUND.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
    }

    @Override
    public void tick() {
        if (!blockEntity.startCraft) {
            stop();
        }
        super.tick();
    }

    public static ArcaneWorkbenchSoundInstance playSound(ArcaneWorkbenchTileEntity tileEntity) {
        ArcaneWorkbenchSoundInstance sound = new ArcaneWorkbenchSoundInstance(tileEntity, 1, 1);
        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
        return sound;
    }
}