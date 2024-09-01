package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.fluffy_fur.client.sound.BlockEntitySoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.arcane_workbench.ArcaneWorkbenchBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.Minecraft;

public class ArcaneWorkbenchSoundInstance extends BlockEntitySoundInstance<ArcaneWorkbenchBlockEntity> {
    public ArcaneWorkbenchSoundInstance(ArcaneWorkbenchBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsRebornSounds.ARCANE_WORKBENCH_LOOP.get(), volume, pitch);
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

    public static ArcaneWorkbenchSoundInstance getSound(ArcaneWorkbenchBlockEntity tileEntity) {
        return new ArcaneWorkbenchSoundInstance(tileEntity, 1, 1);
    }

    public void playSound() {
        Minecraft.getInstance().getSoundManager().queueTickingSound(this);
    }
}