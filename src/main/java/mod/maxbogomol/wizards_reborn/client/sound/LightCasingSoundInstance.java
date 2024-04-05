package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.tileentity.LightCasingTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;

public class LightCasingSoundInstance extends TileEntitySoundInstance<LightCasingTileEntity> {
    public LightCasingSoundInstance(LightCasingTileEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsReborn.ARCANUM_LENS_RESONATE_SOUND.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
    }

    @Override
    public void tick() {
        if (blockEntity.getLight() <= 0) {
            stop();
        }

        boolean isLight = false;
        for (Direction direction : Direction.values()) {
            if (blockEntity.isConnection(direction)) {
                isLight = true;
            }
        }

        if (!isLight) {
            stop();
        }

        super.tick();
    }

    public static LightCasingSoundInstance playSound(LightCasingTileEntity tileEntity) {
        LightCasingSoundInstance sound = new LightCasingSoundInstance(tileEntity, 1, 1);
        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
        return sound;
    }
}