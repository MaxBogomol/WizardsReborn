package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.fluffy_fur.client.sound.BlockEntitySoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.wissen_crystallizer.WissenCrystallizerBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.Minecraft;

public class WissenCrystallizerSoundInstance extends BlockEntitySoundInstance<WissenCrystallizerBlockEntity> {
    public WissenCrystallizerSoundInstance(WissenCrystallizerBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsRebornSounds.WISSEN_CRYSTALLIZER_LOOP.get(), volume, pitch);
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

    public static WissenCrystallizerSoundInstance getSound(WissenCrystallizerBlockEntity blockEntity) {
        return new WissenCrystallizerSoundInstance(blockEntity, 1, 1);
    }

    public void playSound() {
        Minecraft.getInstance().getSoundManager().queueTickingSound(this);
    }
}