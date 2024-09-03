package mod.maxbogomol.wizards_reborn.common.block.salt_lantern;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.common.block.salt_torch.SaltTorchBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class SaltLanternBlockEntity extends SaltTorchBlockEntity {
    public SaltLanternBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SaltLanternBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.SALT_LANTERN.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
            Color colorF = colorFirst;
            Color color = colorSecond;
            Vec3 pos = new Vec3(0.5f, 0.25f, 0.5f);
            boolean isCosmic = false;

            if (getBlockState().getValue(SaltLanternBlock.HANGING)) {
                pos = pos.add(0f, 0.1875f, 0f);
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
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(colorF, color).build())
                        .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                        .setScaleData(GenericParticleData.create(0.45f, 0).setEasing(Easing.CUBIC_IN_OUT).build())
                        .randomSpin(0.005f)
                        .setLifetime(30)
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }
            if (random.nextFloat() < 0.45) {
                ParticleBuilder.create(random.nextFloat() < 0.3 ? FluffyFurParticles.TINY_STAR : FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(colorF, color).build())
                        .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                        .setScaleData(GenericParticleData.create(0f, 0.35f).setEasing(Easing.SINE_IN_OUT).build())
                        .randomSpin(0.01f)
                        .setLifetime(60)
                        .randomVelocity(0.00625f)
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }
            if (random.nextFloat() < 0.45) {
                ParticleBuilder.create(FluffyFurParticles.TINY_WISP)
                        .setColorData(ColorParticleData.create(colorF, color).build())
                        .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                        .setScaleData(GenericParticleData.create(0.15f, 0).setEasing(Easing.SINE_OUT).build())
                        .setLifetime(30)
                        .randomVelocity(0.015f)
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }
            if (random.nextFloat() < 0.3) {
                ParticleBuilder.create(FluffyFurParticles.SMOKE)
                        .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                        .setColorData(ColorParticleData.create(Color.BLACK).build())
                        .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                        .setScaleData(GenericParticleData.create(0.15f, 0.35f).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .randomSpin(0.1f)
                        .setLifetime(60)
                        .randomVelocity(0.0035f)
                        .addVelocity(0, 0.02f, 0)
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }

            if (isCosmic) {
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(FluffyFurParticles.STAR)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                            .randomSpin(0.1f)
                            .setLifetime(10)
                            .randomVelocity(0.015f)
                            .flatRandomOffset(0.2f, 0.2f, 0.2f)
                            .addVelocity(0, 0.025f, 0)
                            .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y() + 0.1f, worldPosition.getZ() + pos.z());
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(FluffyFurParticles.STAR)
                            .setColorData(ColorParticleData.create(Color.WHITE).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                            .randomSpin(0.1f)
                            .setLifetime(10)
                            .randomVelocity(0.015f)
                            .flatRandomOffset(0.2f, 0.2f, 0.2f)
                            .addVelocity(0, 0.025f, 0)
                            .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y() + 0.1f, worldPosition.getZ() + pos.z());
                }
            }
        }
    }
}
