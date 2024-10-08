package mod.maxbogomol.wizards_reborn.common.block.totem.flames;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.ParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
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
        this(WizardsRebornBlockEntities.TOTEM_OF_FLAMES.get(), pos, state);
    }

    @Override
    public void tick() {
        Container container = getItemHandler();
        if (!level.isClientSide()) {
            if (!container.getItem(0).isEmpty()) {
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
            if (!container.getItem(0).isEmpty()) {
                if (container.getItem(0).getItem() instanceof BlockItem blockItem) {
                    if (blockItem.getBlock() instanceof ArcaneLumosBlock lumos) {
                        Color color = lumos.color.getColor();

                        if (random.nextFloat() < 0.5) {
                            ParticleBuilder.create(FluffyFurParticles.WISP)
                                    .setColorData(ColorParticleData.create(color).build())
                                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.25f, 0).build())
                                    .setLifetime(20)
                                    .randomVelocity(0.01f)
                                    .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                        }
                        if (random.nextFloat() < 0.75) {
                            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                    .setBehavior(ParticleBehavior.create().enableCamera().enableSided()
                                            .setXSpinData(SpinParticleData.create().randomOffsetDegrees(-25, 25).build())
                                            .setYSpinData(SpinParticleData.create().randomOffsetDegrees(-25, 25).build())
                                            .setZSpinData(SpinParticleData.create().randomOffsetDegrees(-30, 30).randomSpin(0.005f).build())
                                            .build())
                                    .setColorData(ColorParticleData.create(color).build())
                                    .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.25f, 0).build())
                                    .setLifetime(60)
                                    .randomVelocity(0.0035f)
                                    .addVelocity(0, 0.015f, 0)
                                    .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                        }
                        if (random.nextFloat() < 0.1) {
                            ParticleBuilder.create(FluffyFurParticles.SMOKE)
                                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                                    .setColorData(ColorParticleData.create(Color.BLACK).build())
                                    .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.25f, 0).build())
                                    .setLightData(LightParticleData.DEFAULT)
                                    .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                                    .setLifetime(60)
                                    .randomVelocity(0.0035f)
                                    .addVelocity(0, 0.02f, 0)
                                    .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                        }

                        if (lumos.color.hasFirstStar()) {
                            if (random.nextFloat() < 0.1) {
                                ParticleBuilder.create(FluffyFurParticles.STAR)
                                        .setColorData(ColorParticleData.create(lumos.color.getColorSecondStar()).build())
                                        .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                                        .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                                        .setLifetime(10)
                                        .randomVelocity(0.0035f)
                                        .flatRandomOffset(0.2f, 0.2f, 0.2f)
                                        .addVelocity(0, 0.025f, 0)
                                        .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                            }
                        }
                        if (lumos.color.hasSecondStar()) {
                            if (random.nextFloat() < 0.1) {
                                ParticleBuilder.create(FluffyFurParticles.STAR)
                                        .setColorData(ColorParticleData.create(lumos.color.getColorSecondStar()).build())
                                        .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                                        .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                                        .setLifetime(10)
                                        .randomVelocity(0.0035f)
                                        .flatRandomOffset(0.2f, 0.2f, 0.2f)
                                        .addVelocity(0, 0.025f, 0)
                                        .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
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
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return stack.is(WizardsRebornItemTags.ARCANE_LUMOS);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return true;
    }
}
