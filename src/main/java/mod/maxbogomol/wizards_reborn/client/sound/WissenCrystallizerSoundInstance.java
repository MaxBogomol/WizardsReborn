package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.wissen_crystallizer.WissenCrystallizerBlockEntity;
import net.minecraft.client.Minecraft;

public class WissenCrystallizerSoundInstance extends TileEntitySoundInstance<WissenCrystallizerBlockEntity> {
    public WissenCrystallizerSoundInstance(WissenCrystallizerBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsReborn.WISSEN_CRYSTALLIZER_LOOP_SOUND.get(), volume, pitch);
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

    public static WissenCrystallizerSoundInstance getSound(WissenCrystallizerBlockEntity tileEntity) {
        return new WissenCrystallizerSoundInstance(tileEntity, 1, 1);
    }

    public void playSound() {
        Minecraft.getInstance().getSoundManager().queueTickingSound(this);
    }
}