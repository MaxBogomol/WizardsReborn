package mod.maxbogomol.wizards_reborn.common.block.light_transfer_lens;

import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandControlledBlockEntity;
import mod.maxbogomol.wizards_reborn.client.sound.LightTransferLensSoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;

public class LightTransferLensBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity, ILightBlockEntity, IWissenWandControlledBlockEntity {

    public int blockToX = 0;
    public int blockToY =0 ;
    public int blockToZ = 0;
    public boolean isToBlock = false;

    public int light = 0;

    public LightTransferLensSoundInstance sound;

    public LightTransferLensBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public LightTransferLensBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.LIGHT_TRANSFER_LENS_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (getLight() > 0) {
                removeLight(1);
                update = true;
            }

            if (isToBlock) {
                BlockPos pos = new BlockPos(blockToX, blockToY, blockToZ);
                if (level.isLoaded(pos)) {
                    BlockEntity tileentity = level.getBlockEntity(pos);
                    if (tileentity instanceof ILightBlockEntity lightTileEntity) {
                        if (canWork()) {
                            Vec3 from = LightUtil.getLightLensPos(getBlockPos(), getLightLensPos());
                            Vec3 to = LightUtil.getLightLensPos(pos, lightTileEntity.getLightLensPos());

                            LightRayHitResult hitResult = LightUtil.getLightRayHitResult(level, getBlockPos(), from, to, 25);
                            BlockEntity hitTile = hitResult.getBlockEntity();
                            LightUtil.transferLight(this, hitTile);
                        }
                    } else {
                        isToBlock = false;
                        update = true;
                    }
                }
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (getLight() > 0 && isToBlock) {
                if (sound == null) {
                    sound = LightTransferLensSoundInstance.playSound(this);
                    sound.playSound();
                } else if (sound.isStopped()) {
                    sound = LightTransferLensSoundInstance.playSound(this);
                    sound.playSound();
                }
            }
        }
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

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        if (stack.is(WizardsReborn.ARCANE_LUMOS_ITEM_TAG)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return true;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("blockToX", blockToX);
        tag.putInt("blockToY", blockToY);
        tag.putInt("blockToZ", blockToZ);
        tag.putBoolean("isToBlock", isToBlock);

        tag.putInt("light", light);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        blockToX = tag.getInt("blockToX");
        blockToY = tag.getInt("blockToY");
        blockToZ = tag.getInt("blockToZ");
        isToBlock = tag.getBoolean("isToBlock");

        light = tag.getInt("light");
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
    }

    public Color getColor() {
        Color color = new Color(0.886f, 0.811f, 0.549f);

        ArcaneLumosBlock lumos = getLumos();
        if (lumos != null) {
            color = ArcaneLumosBlock.getColor(lumos.color);
        }

        return color;
    }

    public ArcaneLumosBlock getLumos() {
        if (!getItemHandler().getItem(0).isEmpty()) {
            if (getItemHandler().getItem(0).getItem() instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) getItemHandler().getItem(0).getItem();
                if (blockItem.getBlock() instanceof ArcaneLumosBlock) {
                    ArcaneLumosBlock lumos = (ArcaneLumosBlock) blockItem.getBlock();
                    return lumos;
                }
            }
        }

        return null;
    }

    public boolean canWork() {
        return !level.hasNeighborSignal(getBlockPos());
    }

    @Override
    public int getLight() {
        return light;
    }

    @Override
    public int getMaxLight() {
        return 10;
    }

    @Override
    public boolean canSendLight() {
        return false;
    }

    @Override
    public boolean canReceiveLight() {
        return true;
    }

    @Override
    public boolean canConnectSendLight() {
        return true;
    }

    @Override
    public boolean canConnectReceiveLight() {
        return true;
    }

    @Override
    public void setLight(int light) {
        this.light = light;
    }

    @Override
    public void addLight(int light) {
        this.light = this.light + light;
        if (this.light > getMaxLight()) {
            this.light = getMaxLight();
        }
    }

    @Override
    public void removeLight(int light) {
        this.light = this.light - light;
        if (this.light < 0) {
            this.light = 0;
        }
    }

    @Override
    public Vec3 getLightLensPos() {
        return new Vec3(0.5F, 0.5F, 0.5F);
    }

    @Override
    public float getLightLensSize() {
        return 0.0625f;
    }

    @Override
    public boolean wissenWandReceiveConnect(ItemStack stack, UseOnContext context, BlockEntity tile) {
        return false;
    }

    @Override
    public boolean wissenWandSendConnect(ItemStack stack, UseOnContext context, BlockEntity tile) {
        BlockPos oldBlockPos = WissenWandItem.getBlockPos(stack);
        BlockEntity oldTile = level.getBlockEntity(oldBlockPos);

        if (oldTile instanceof ILightBlockEntity lightTile) {
            if (lightTile.canConnectSendLight()) {
                blockToX = oldBlockPos.getX();
                blockToY = oldBlockPos.getY();
                blockToZ = oldBlockPos.getZ();
                isToBlock = true;
                WissenWandItem.setBlock(stack, false);
                BlockEntityUpdate.packet(this);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean wissenWandReload(ItemStack stack, UseOnContext context, BlockEntity tile) {
        isToBlock = false;
        BlockEntityUpdate.packet(this);
        return true;
    }
}
