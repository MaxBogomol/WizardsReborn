package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneIteratorRecipe;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ArcaneIteratorTileEntity extends BlockEntity implements TickableBlockEntity, IWissenTileEntity {
    public int wissen = 0;
    public int ticks = 0;

    public Random random = new Random();

    public ArcaneIteratorTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcaneIteratorTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ARCANE_ITERATOR_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (isWorks()) {
                List<ArcanePedestalTileEntity> pedestals = getPedestals();
                List<ItemStack> items = getItemsFromPedestals(pedestals);
                SimpleContainer inv = new SimpleContainer(items.size());
                for (int i = 0; i < items.size(); i++) {
                    inv.setItem(i, items.get(i));
                }

                Optional<ArcaneIteratorRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.ARCANE_ITERATOR_RECIPE.get(), inv, level);
                if (recipe.isPresent()) {
                    for (int i = 0; i < pedestals.size(); i++) {
                        pedestals.get(i).getItemHandler().removeItemNoUpdate(0);
                        PacketUtils.SUpdateTileEntityPacket(pedestals.get(i));
                    }
                    getMainPedestal().setItem(0, recipe.get().getResultItem(RegistryAccess.EMPTY).copy());
                    PacketUtils.SUpdateTileEntityPacket(getMainPedestal());
                }
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }

        if (level.isClientSide()) {

        }
    }

    public boolean isWorks() {
        BlockEntity tile = level.getBlockEntity(getBlockPos().below());
        if (tile != null) {
            if (tile instanceof ArcanePedestalTileEntity pedestal) {
                return true;
            }
        }

        return false;
    }

    public ArcanePedestalTileEntity getMainPedestal() {
        BlockEntity tile = level.getBlockEntity(getBlockPos().below());
        if (tile != null) {
            if (tile instanceof ArcanePedestalTileEntity pedestal) {
                return pedestal;
            }
        }

        return null;
    }

    public List<ArcanePedestalTileEntity> getPedestals() {
        List<ArcanePedestalTileEntity> pedestals = new ArrayList<>();

        for (int x = -5; x < 5; x++) {
            for (int y = -1; y < 3; y++) {
                for (int z = -5; z < 5; z++) {
                    BlockEntity tile = level.getBlockEntity(new BlockPos(getBlockPos().getX() + x, getBlockPos().getY() + y, getBlockPos().getZ() + z));
                    if (tile != null) {
                        if (tile instanceof ArcanePedestalTileEntity pedestal) {
                            pedestals.add(pedestal);
                        }
                    }
                }
            }
        }

        ArcanePedestalTileEntity pedestal = getMainPedestal();
        if (pedestal != null) {
            if (pedestals.contains(pedestal)) {
                pedestals.remove(pedestal);
            }
            pedestals.add(0, pedestal);
        }

        return pedestals;
    }

    public List<ItemStack> getItemsFromPedestals(List<ArcanePedestalTileEntity> pedestals) {
        List<ItemStack> items = new ArrayList<>();
        for (ArcanePedestalTileEntity pedestal : pedestals) {
            if (!pedestal.getItemHandler().getItem(0).isEmpty()) {
                items.add(pedestal.getItemHandler().getItem(0));
            }
        }

        return items;
    }

    public List<ItemStack> getItemsFromPedestals() {
        return getItemsFromPedestals(getPedestals());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, (e) -> e.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @NotNull
    @Override
    public final CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            PacketUtils.SUpdateTileEntityPacket(this);
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("wissen", wissen);
        tag.putInt("ticks", ticks);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        wissen = tag.getInt("wissen");
        ticks = tag.getInt("ticks");
    }

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    @Override
    public int getWissen() {
        return wissen;
    }

    @Override
    public int getMaxWissen() {
        return 7500;
    }

    @Override
    public boolean canSendWissen() {
        return true;
    }

    @Override
    public boolean canReceiveWissen() {
        return false;
    }

    @Override
    public boolean canConnectSendWissen() {
        return false;
    }

    @Override
    public boolean canConnectReceiveWissen() {
        return true;
    }

    @Override
    public int getWissenPerReceive() {
        return 0;
    }

    @Override
    public int getSendWissenCooldown() {
        return 0;
    }

    @Override
    public void setWissen(int wissen) {
        this.wissen = wissen;
    }

    @Override
    public void addWissen(int wissen) {
        this.wissen = this.wissen + wissen;
        if (this.wissen > getMaxWissen()) {
            this.wissen = getMaxWissen();
        }
    }

    @Override
    public void removeWissen(int wissen) {
        this.wissen = this.wissen - wissen;
        if (this.wissen < 0) {
            this.wissen = 0;
        }
    }
}
