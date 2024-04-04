package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.common.block.SaltWallTorchBlock;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Random;

public class SaltTorchTileEntity extends ExposedTileSimpleInventory implements TickableBlockEntity {

    public Random random = new Random();
    public Color colorFirst = new Color(255, 170, 65);
    public Color colorSecond = new Color(231, 71, 101);

    public SaltTorchTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SaltTorchTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.SALT_TORCH_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
            Color colorF = colorFirst;
            Color color = colorSecond;
            Vec3 pos = new Vec3(0.5f, 0.6875f, 0.5f);
            boolean isCosmic = false;

            if (getBlockState().getBlock() instanceof SaltWallTorchBlock) {
                BlockPos blockPos = new BlockPos(0, 0, 0).relative(getBlockState().getValue(SaltWallTorchBlock.FACING));
                pos = pos.add(blockPos.getX() * -0.25f, 0.09375f, blockPos.getZ() * -0.25f);
            }

            if (!getItemHandler().getItem(0).isEmpty()) {
                if (getItemHandler().getItem(0).getItem() instanceof BlockItem) {
                    BlockItem blockItem = (BlockItem) getItemHandler().getItem(0).getItem();
                    if (blockItem.getBlock() instanceof ArcaneLumosBlock) {
                        ArcaneLumosBlock lumos = (ArcaneLumosBlock) blockItem.getBlock();
                        color = ArcaneLumosBlock.getColor(lumos.color);

                        if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                            isCosmic = true;
                        }
                    }
                }
            }
            if (!getItemHandler().getItem(1).isEmpty()) {
                if (getItemHandler().getItem(1).getItem() instanceof BlockItem) {
                    BlockItem blockItem = (BlockItem) getItemHandler().getItem(1).getItem();
                    if (blockItem.getBlock() instanceof ArcaneLumosBlock) {
                        ArcaneLumosBlock lumos = (ArcaneLumosBlock) blockItem.getBlock();
                        colorF = ArcaneLumosBlock.getColor(lumos.color);

                        if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                            isCosmic = true;
                        }
                    }
                }
            }

            if (random.nextFloat() < 0.5) {
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .setAlpha(0.25f, 0).setScale(0.35f, 0)
                        .setColor(colorF.getRed() / 255f, colorF.getGreen()/ 255f, colorF.getBlue() / 255f, color.getRed() / 255f, color.getGreen()/ 255f, color.getBlue() / 255f)
                        .setLifetime(30)
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }
            if (random.nextFloat() < 0.45) {
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 300), ((random.nextDouble() - 0.5D) / 150) + 0.015f, ((random.nextDouble() - 0.5D) / 300))
                        .setAlpha(0.35f, 0).setScale(0.25f, 0)
                        .setColor(colorF.getRed() / 255f, colorF.getGreen()/ 255f, colorF.getBlue() / 255f, color.getRed() / 255f, color.getGreen()/ 255f, color.getBlue() / 255f)
                        .setLifetime(60)
                        .setSpin((0.01f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }
            if (random.nextFloat() < 0.45) {
                Particles.create(WizardsReborn.WISP_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 150), ((random.nextDouble() - 0.5D) / 150) + 0.03f, ((random.nextDouble() - 0.5D) / 150))
                        .setAlpha(0.35f, 0).setScale(0.15f, 0)
                        .setColor(colorF.getRed() / 255f, colorF.getGreen()/ 255f, colorF.getBlue() / 255f, color.getRed() / 255f, color.getGreen()/ 255f, color.getBlue() / 255f)
                        .setLifetime(30)
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }
            if (random.nextFloat() < 0.3) {
                Particles.create(WizardsReborn.SMOKE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 150), ((random.nextDouble() - 0.5D) / 150) + 0.03f, ((random.nextDouble() - 0.5D) / 150))
                        .setAlpha(0.2f, 0).setScale(0.25f, 0)
                        .setColor(0, 0, 0)
                        .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .setLifetime(60)
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }

            if (isCosmic) {
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 150), ((random.nextDouble() - 0.5D) / 150) + 0.025f, ((random.nextDouble() - 0.5D) / 150))
                            .setAlpha(0.75f, 0).setScale(0.1f, 0)
                            .setColor((float) color.getRed() / 255, (float) color.getGreen()/ 255, (float) color.getBlue() / 255)
                            .setLifetime(10)
                            .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + pos.x() + ((random.nextDouble() - 0.5D) / 3), worldPosition.getY() + pos.y() + ((random.nextDouble() - 0.5D) / 3), worldPosition.getZ() + pos.z() + ((random.nextDouble() - 0.5D) / 3));
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 150), ((random.nextDouble() - 0.5D) / 150) + 0.025f, ((random.nextDouble() - 0.5D) / 150))
                            .setAlpha(0.75f, 0).setScale(0.1f, 0)
                            .setColor(1f, 1f, 1f)
                            .setLifetime(10)
                            .spawn(level, worldPosition.getX() + pos.x() + ((random.nextDouble() - 0.5D) / 3), worldPosition.getY() + pos.y() + ((random.nextDouble() - 0.5D) / 3), worldPosition.getZ() + pos.z() + ((random.nextDouble() - 0.5D) / 3));
                }
            }
        }
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(2) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
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
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
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

    public int getInventorySize() {
        int size = 0;

        for (int i = 0; i < getItemHandler().getContainerSize(); i++) {
            if (!getItemHandler().getItem(i).isEmpty()) {
                size++;
            }
        }

        return size;
    }
}
