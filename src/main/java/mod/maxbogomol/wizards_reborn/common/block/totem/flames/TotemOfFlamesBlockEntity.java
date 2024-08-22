package mod.maxbogomol.wizards_reborn.common.block.totem.flames;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.utils.RenderUtils;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Random;

public class TotemOfFlamesBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity {

    public Random random = new Random();

    public TotemOfFlamesBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public TotemOfFlamesBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.TOTEM_OF_FLAMES_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            if (!getItemHandler().getItem(0).isEmpty()) {
                if (!getBlockState().getValue(BlockStateProperties.LIT)) {
                    BlockState blockState = getBlockState().setValue(BlockStateProperties.LIT, true);
                    level.setBlock(getBlockPos(), blockState, 3);
                }
            } else if (getBlockState().getValue(BlockStateProperties.LIT)) {
                BlockState blockState = getBlockState().setValue(BlockStateProperties.LIT, false);
                level.setBlock(getBlockPos(), blockState, 3);
            }
        }

        if (level.isClientSide()) {
            if (!getItemHandler().getItem(0).isEmpty()) {
                if (getItemHandler().getItem(0).getItem() instanceof BlockItem) {
                    BlockItem blockItem = (BlockItem) getItemHandler().getItem(0).getItem();
                    if (blockItem.getBlock() instanceof ArcaneLumosBlock) {
                        ArcaneLumosBlock lumos = (ArcaneLumosBlock) blockItem.getBlock();
                        Color color = ArcaneLumosBlock.getColor(lumos.color);

                        if (random.nextFloat() < 0.5) {
                            ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                                    .setColorData(ColorParticleData.create(color).build())
                                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.25f, 0).build())
                                    .setLifetime(20)
                                    .randomVelocity(0.01f)
                                    .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
                        }
                        if (random.nextFloat() < 0.75) {
                            ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                                    .setColorData(ColorParticleData.create(color).build())
                                    .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.25f, 0).build())
                                    .randomSpin(0.005f)
                                    .setLifetime(60)
                                    .randomVelocity(0.0035f)
                                    .addVelocity(0, 0.015f, 0)
                                    .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
                        }
                        if (random.nextFloat() < 0.1) {
                            ParticleBuilder.create(FluffyFur.SMOKE_PARTICLE)
                                    .setRenderType(RenderUtils.DELAYED_PARTICLE)
                                    .setColorData(ColorParticleData.create(Color.BLACK).build())
                                    .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.25f, 0).build())
                                    .setLightData(LightParticleData.DEFAULT)
                                    .randomSpin(0.1f)
                                    .setLifetime(60)
                                    .randomVelocity(0.0035f)
                                    .addVelocity(0, 0.02f, 0)
                                    .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
                        }

                        if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                            if (random.nextFloat() < 0.1) {
                                ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                                        .setColorData(ColorParticleData.create(color).build())
                                        .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                                        .setScaleData(GenericParticleData.create(0.1f, 0).build())
                                        .randomSpin(0.1f)
                                        .setLifetime(60)
                                        .randomVelocity(0.0035f)
                                        .flatRandomOffset(0.15f, 0.15f, 0.15f)
                                        .addVelocity(0, 0.025f, 0)
                                        .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
                            }
                            if (random.nextFloat() < 0.1) {
                                ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                                        .setColorData(ColorParticleData.create(Color.WHITE).build())
                                        .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                                        .setScaleData(GenericParticleData.create(0.1f, 0).build())
                                        .setLifetime(10)
                                        .randomVelocity(0.0035f)
                                        .flatRandomOffset(0.15f, 0.15f, 0.15f)
                                        .addVelocity(0, 0.025f, 0)
                                        .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
                            }
                        }
                    }
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
}
