package mod.maxbogomol.wizards_reborn.common.block.casing.wissen;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.wissen_translator.WissenTranslatorBlockEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class WissenCasingBlockEntity extends WissenTranslatorBlockEntity {
    public boolean[] connection = new boolean[Direction.values().length];

    public int maxCooldown = 0;

    public WissenCasingBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public WissenCasingBlockEntity(BlockPos pos, BlockState state) {
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
            int connectionCount = 0;

            for (Direction direction : Direction.values()) {
                if (isConnection(direction)) {
                    connectionCount++;
                }
            }

            int removeCount = (int) Math.floor((double) getWissenPerReceive() / connectionCount);
            int cooldownCount = 0;

            if (canReceiveWissen() && (cooldown <= 0) && canWork()) {
                for (Direction direction : Direction.values()) {
                    if (isConnection(direction)) {
                        int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), removeCount);

                        if (removeCount - removeRemain > 0 && getWissen() > 0) {
                            removeWissen(removeCount - removeRemain);
                            addWissenRay(getBlockPos(), getBlockPos().relative(direction), removeCount - removeRemain);

                            setCooldown = true;
                            update = true;
                            cooldownCount++;
                        }
                    }
                }
            }

            if (wissenRays.size() > 0) {
                updateWissenRays();
                update = true;
            }

            if (setCooldown) {
                cooldown = (int) (getSendWissenCooldown() + (((cooldownCount - 1) / 5f) * getSendWissenCooldown()));
                maxCooldown = cooldown;
                level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.WISSEN_TRANSFER_SOUND.get(), SoundSource.BLOCKS, 0.1f, (float) (1.1f + ((random.nextFloat() - 0.5D) / 2)));
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        maxCooldown = nbt.getInt("maxCooldown");
        for(Direction facing : Direction.values())
            if(nbt.contains("connection"+facing.get3DDataValue()))
                connection[facing.get3DDataValue()] = nbt.getBoolean("connection"+facing.get3DDataValue());
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("maxCooldown", maxCooldown);
        for(Direction facing : Direction.values())
            nbt.putBoolean("connection"+facing.get3DDataValue(),connection[facing.get3DDataValue()]);
    }

    public void setConnection(Direction facing, boolean flag) {
        connection[facing.get3DDataValue()] = flag;
    }

    public void resetsConnections() {
        for (Direction facing : Direction.values()) {
            setConnection(facing, false);
        }
    }

    public boolean isConnection(Direction facing) {
        return connection[facing.get3DDataValue()];
    }

    @Override
    public int getSendWissenCooldown() {
        return 20;
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
        resetsConnections();
        PacketUtils.SUpdateTileEntityPacket(this);
        return true;
    }

    @Override
    public float getCooldown() {
        if (cooldown > 0) {
            return (float) maxCooldown / cooldown;
        }
        return super.getCooldown();
    }
}
