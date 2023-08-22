package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ArcanePedestalTileEntity extends TileSimpleInventory {
    public ArcanePedestalTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcanePedestalTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ARCANE_PEDESTAL_TILE_ENTITY.get(), pos, state);
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(1) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
    }

    //@Override
    //public final ClientboundBlockEntityDataPacket getUpdatePacket() {
    //    CompoundTag tag = new CompoundTag();
    //    return new ClientboundBlockEntityDataPacket(worldPosition, -999, tag);
    //}

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        load(packet.getTag());
    }

    //@Override
    //public CompoundTag getUpdateTag()
    //{
    //    return this.save(new CompoundTag());
    //}
}
