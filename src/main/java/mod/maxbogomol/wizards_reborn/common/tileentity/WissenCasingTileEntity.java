package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class WissenCasingTileEntity extends WissenTranslatorTileEntity {
    public WissenCasingTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public WissenCasingTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.WISSEN_CASING_TILE_ENTITY.get(), pos, state);
    }

    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            if (cooldown > 0) {
                cooldown = cooldown - 1;
                update = true;
            }

            boolean setCooldown = false;

            if (canReceiveWissen() && (cooldown <= 0) && canWork() && getWissen() > getWissenPerReceive()) {
                int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), getWissenPerReceive());

                if (getWissenPerReceive() - removeRemain > 0) {
                    removeWissen(getWissenPerReceive() - removeRemain);
                    addWissenRay(getBlockPos(), getBlockPos().above(), (getWissenPerReceive() - removeRemain) / 6);

                    addWissenRay(getBlockPos(), getBlockPos().north(), (getWissenPerReceive() - removeRemain) / 6);
                    addWissenRay(getBlockPos(), getBlockPos().west(), (getWissenPerReceive() - removeRemain) / 6);
                    addWissenRay(getBlockPos(), getBlockPos().south(), (getWissenPerReceive() - removeRemain) / 6);
                    addWissenRay(getBlockPos(), getBlockPos().east(), (getWissenPerReceive() - removeRemain) / 6);

                    addWissenRay(getBlockPos(), getBlockPos().below(), (getWissenPerReceive() - removeRemain) / 6);

                    setCooldown = true;
                    update = true;

                    level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.WISSEN_TRANSFER_SOUND.get(), SoundSource.BLOCKS, 0.1f, (float) (1.1f + ((random.nextFloat() - 0.5D) / 2)));
                }
            }

            if (wissenRays.size() > 0) {
                updateWissenRays();
                update = true;
            }

            if (setCooldown) {
                cooldown = getSendWissenCooldown();
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }
    }

    @Override
    public int getSendWissenCooldown() {
        return 40;
    }

    @Override
    public boolean wissenWandReceiveConnect(ItemStack stack, UseOnContext context, BlockEntity tile) {
        return false;
    }

    @Override
    public boolean wissenWandSendConnect(ItemStack stack, UseOnContext context, BlockEntity tile) {
        return false;
    }

    @Override
    public boolean wissenWandReload(ItemStack stack, UseOnContext context, BlockEntity tile) {
        return false;
    }
}
