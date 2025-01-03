package mod.maxbogomol.wizards_reborn.common.block.light_transfer_lens;

import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandControlledBlockEntity;
import mod.maxbogomol.wizards_reborn.client.sound.LightTransferLensSoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
import java.util.ArrayList;

public class LightTransferLensBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity, ILightBlockEntity, IWissenWandControlledBlockEntity {

    public int blockToX = 0;
    public int blockToY = 0;
    public int blockToZ = 0;
    public boolean isToBlock = false;

    public int light = 0;

    public ArrayList<LightTypeStack> lightTypes = new ArrayList<>();

    public LightTransferLensSoundInstance sound;

    public LightTransferLensBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public LightTransferLensBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.LIGHT_TRANSFER_LENS.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (getLight() > 0) {
                removeLight(1);
                LightUtil.tickLightTypeStack(this, getLightTypes());
                if (getLight() <= 0) {
                    clearLightType();
                }
                update = true;
            } else if (!getLightTypes().isEmpty()) {
                clearLightType();
                update = true;
            }

            if (isToBlock) {
                BlockPos pos = new BlockPos(blockToX, blockToY, blockToZ);
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof ILightBlockEntity lightBlockEntity) {
                    if (canWork()) {
                        Vec3 from = LightUtil.getLightLensPos(getBlockPos(), getLightLensPos());
                        Vec3 to = LightUtil.getLightLensPos(pos, lightBlockEntity.getLightLensPos());

                        LightRayHitResult hitResult = LightUtil.getLightRayHitResult(level, getBlockPos(), from, to, 25);
                        BlockEntity hitBlock = hitResult.getBlockEntity();
                        LightUtil.transferLight(this, hitBlock);
                        LightUtil.tickHitLightTypeStack(this, getLightTypes(), hitResult);
                    }
                } else {
                    isToBlock = false;
                    update = true;
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
        return stack.is(WizardsRebornItemTags.ARCANE_LUMOS);
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

        tag.put("lightTypes", LightUtil.stacksToTag(lightTypes));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        blockToX = tag.getInt("blockToX");
        blockToY = tag.getInt("blockToY");
        blockToZ = tag.getInt("blockToZ");
        isToBlock = tag.getBoolean("isToBlock");

        light = tag.getInt("light");

        lightTypes = LightUtil.stacksFromTag(tag.getList("lightTypes", Tag.TAG_COMPOUND));
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
    }

    public Color getColor() {
        ArcaneLumosBlock lumos = getLumos();
        if (lumos != null) {
            return lumos.color.getColor();
        }
        return LightUtil.standardLightRayColor;
    }

    public ArcaneLumosBlock getLumos() {
        if (!getItemHandler().getItem(0).isEmpty()) {
            if (getItemHandler().getItem(0).getItem() instanceof BlockItem blockItem) {
                if (blockItem.getBlock() instanceof ArcaneLumosBlock) {
                    return (ArcaneLumosBlock) blockItem.getBlock();
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
    public ArrayList<LightTypeStack> getLightTypes() {
        return lightTypes;
    }

    @Override
    public void setLightTypes(ArrayList<LightTypeStack> lightTypes) {
        this.lightTypes = lightTypes;
    }

    @Override
    public void addLightType(LightTypeStack lightType) {
        lightTypes.add(lightType);
    }

    @Override
    public void removeLightType(LightTypeStack lightType) {
        lightTypes.remove(lightType);
    }

    @Override
    public void clearLightType() {
        lightTypes.clear();
    }

    @Override
    public boolean wissenWandReceiveConnect(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
        return false;
    }

    @Override
    public boolean wissenWandSendConnect(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
        BlockPos oldBlockPos = WissenWandItem.getBlockPos(stack);
        BlockEntity oldBlockEntity = level.getBlockEntity(oldBlockPos);

        if (oldBlockEntity instanceof ILightBlockEntity lightBlockEntity) {
            if (lightBlockEntity.canConnectSendLight()) {
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
    public boolean wissenWandReload(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
        isToBlock = false;
        BlockEntityUpdate.packet(this);
        return true;
    }
}
