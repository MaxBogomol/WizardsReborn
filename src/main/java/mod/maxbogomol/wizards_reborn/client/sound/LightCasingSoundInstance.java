package mod.maxbogomol.wizards_reborn.client.sound;

import mod.maxbogomol.fluffy_fur.client.sound.BlockEntitySoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.casing.light.LightCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;

public class LightCasingSoundInstance extends BlockEntitySoundInstance<LightCasingBlockEntity> {
    public LightCasingSoundInstance(LightCasingBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, WizardsRebornSounds.ARCANUM_LENS_RESONATE.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
    }

    @Override
    public void tick() {
        if (blockEntity.getLight() <= 0) {
            System.out.println(123);
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

    public static LightCasingSoundInstance getSound(LightCasingBlockEntity tileEntity) {
        return new LightCasingSoundInstance(tileEntity, 1, 1);
    }

    public void playSound() {
        Minecraft.getInstance().getSoundManager().queueTickingSound(this);
    }
}