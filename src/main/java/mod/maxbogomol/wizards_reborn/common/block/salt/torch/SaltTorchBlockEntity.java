package mod.maxbogomol.wizards_reborn.common.block.salt.torch;

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

public class SaltTorchBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity {

    public static Color colorFirst = new Color(255, 170, 65);
    public static Color colorSecond = new Color(231, 71, 101);

    public SaltTorchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SaltTorchBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.SALT_TORCH.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
            Color colorF = colorFirst;
            Color color = colorSecond;
            Vec3 pos = new Vec3(0.5f, 0.6875f, 0.5f);
            Color colorFirstStarF = null;
            Color colorFirstStar = null;
            Color colorSecondStarF = null;
            Color colorSecondStar = null;
            boolean firstStar = false;
            boolean secondStar = false;


            if (getBlockState().getBlock() instanceof SaltWallTorchBlock) {
                BlockPos blockPos = new BlockPos(0, 0, 0).relative(getBlockState().getValue(SaltWallTorchBlock.FACING));
                pos = pos.add(blockPos.getX() * -0.25f, 0.09375f, blockPos.getZ() * -0.25f);
            }

            if (!getItemHandler().getItem(0).isEmpty()) {
                if (getItemHandler().getItem(0).getItem() instanceof BlockItem blockItem) {
                    if (blockItem.getBlock() instanceof ArcaneLumosBlock lumos) {
                        color = lumos.color.getColor();
                        if (lumos.color.hasFirstStar()) {
                            firstStar = true;
                            colorFirstStarF = lumos.color.getColorFirstStar();
                            colorFirstStar = lumos.color.getColorFirstStar();
                        }
                        if (lumos.color.hasSecondStar()) {
                            secondStar = true;
                            colorSecondStarF = lumos.color.getColorSecondStar();
                            colorSecondStar = lumos.color.getColorFirstStar();
                        }
                    }
                }
            }
            if (!getItemHandler().getItem(1).isEmpty()) {
                if (getItemHandler().getItem(1).getItem() instanceof BlockItem blockItem) {
                    if (blockItem.getBlock() instanceof ArcaneLumosBlock lumos) {
                        colorF = lumos.color.getColor();
                        if (lumos.color.hasFirstStar()) {
                            firstStar = true;
                            if (colorFirstStarF == null) colorFirstStarF = lumos.color.getColorFirstStar();
                            colorFirstStar = lumos.color.getColorFirstStar();
                        }
                        if (lumos.color.hasSecondStar()) {
                            secondStar = true;
                            if (colorSecondStarF == null) colorSecondStarF = lumos.color.getColorSecondStar();
                            colorSecondStar = lumos.color.getColorFirstStar();
                        }
                    }
                }
            }

            if (random.nextFloat() < 0.5) {
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setBehavior(ParticleBehavior.create(90, 0, 0)
                                .setXSpinData(SpinParticleData.create().randomOffsetDegrees(-25, 25).build())
                                .setYSpinData(SpinParticleData.create().randomOffsetDegrees(-25, 25).build())
                                .setZSpinData(SpinParticleData.create().randomOffset().randomSpin(0.05f).build())
                                .build())
                        .setColorData(ColorParticleData.create(colorF, color).build())
                        .setTransparencyData(GenericParticleData.create(0.1f, 0.25f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 0.35f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setLifetime(40)
                        .spawn(level, getBlockPos().getX() + pos.x(), getBlockPos().getY() + pos.y(), getBlockPos().getZ() + pos.z());
            }
            if (random.nextFloat() < 0.45) {
                ParticleBuilder.create(random.nextFloat() < 0.3 ? FluffyFurParticles.TINY_STAR : FluffyFurParticles.SPARKLE)
                        .setBehavior(ParticleBehavior.create(0, 0, 0).enableCamera().enableSided()
                                .setXSpinData(SpinParticleData.create().randomOffsetDegrees(-20, 20).build())
                                .setYSpinData(SpinParticleData.create().randomOffsetDegrees(-20, 20).build())
                                .setZSpinData(SpinParticleData.create().randomOffset().randomSpin(0.05f).build())
                                .build())
                        .setColorData(ColorParticleData.create(colorF, color).build())
                        .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                        .setScaleData(GenericParticleData.create(0.25f, 0).setEasing(Easing.SINE_IN_OUT).build())
                        .setLifetime(60)
                        .randomVelocity(0.007f)
                        .addVelocity(0, 0.015f, 0)
                        .spawn(level, getBlockPos().getX() + pos.x(), getBlockPos().getY() + pos.y(), getBlockPos().getZ() + pos.z());
            }
            if (random.nextFloat() < 0.45) {
                ParticleBuilder.create(FluffyFurParticles.TINY_WISP)
                        .setColorData(ColorParticleData.create(colorF, color).build())
                        .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                        .setScaleData(GenericParticleData.create(0.15f, 0).setEasing(Easing.SINE_OUT).build())
                        .setLifetime(30)
                        .randomVelocity(0.015f)
                        .addVelocity(0, 0.03f, 0)
                        .spawn(level, getBlockPos().getX() + pos.x(), getBlockPos().getY() + pos.y(), getBlockPos().getZ() + pos.z());
            }
            if (random.nextFloat() < 0.3) {
                ParticleBuilder.create(FluffyFurParticles.SMOKE)
                        .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                        .setColorData(ColorParticleData.create(Color.BLACK).build())
                        .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                        .setScaleData(GenericParticleData.create(0.25f, 0).build())
                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .setLifetime(60)
                        .randomVelocity(0.0035f)
                        .addVelocity(0, 0.03f, 0)
                        .spawn(level, getBlockPos().getX() + pos.x(), getBlockPos().getY() + pos.y(), getBlockPos().getZ() + pos.z());
            }

            if (firstStar) {
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(FluffyFurParticles.STAR)
                            .setColorData(ColorParticleData.create(colorFirstStarF, colorFirstStar).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                            .setLifetime(10)
                            .randomVelocity(0.015f)
                            .flatRandomOffset(0.2f, 0.2f, 0.2f)
                            .addVelocity(0, 0.025f, 0)
                            .spawn(level, getBlockPos().getX() + pos.x(), getBlockPos().getY() + pos.y() + 0.1f, getBlockPos().getZ() + pos.z());
                }
            }
            if (secondStar) {
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(FluffyFurParticles.STAR)
                            .setColorData(ColorParticleData.create(colorSecondStarF, colorSecondStarF).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                            .setLifetime(10)
                            .randomVelocity(0.015f)
                            .flatRandomOffset(0.2f, 0.2f, 0.2f)
                            .addVelocity(0, 0.025f, 0)
                            .spawn(level, getBlockPos().getX() + pos.x(), getBlockPos().getY() + pos.y() + 0.1f, getBlockPos().getZ() + pos.z());
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
