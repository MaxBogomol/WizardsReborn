package mod.maxbogomol.wizards_reborn.common.block.light_emitter;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandControlledBlockEntity;
import mod.maxbogomol.wizards_reborn.client.sound.LightEmitterSoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItemTags;
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

public class LightEmitterBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity, IWissenBlockEntity, ILightBlockEntity, IWissenWandControlledBlockEntity {

    public int blockToX = 0;
    public int blockToY =0 ;
    public int blockToZ = 0;
    public boolean isToBlock = false;

    public int wissen = 0;
    public int light = 0;

    public LightEmitterSoundInstance sound;

    public LightEmitterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public LightEmitterBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.LIGHT_EMITTER.get(), pos, state);
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
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (blockEntity instanceof ILightBlockEntity lightBlockEntity) {
                        if (canWork()) {
                            if (getWissen() > 0) {
                                removeWissen(1);
                                addLight(2);
                                update = true;
                                Vec3 from = LightUtil.getLightLensPos(getBlockPos(), getLightLensPos());
                                Vec3 to = LightUtil.getLightLensPos(pos, lightBlockEntity.getLightLensPos());

                                LightRayHitResult hitResult = LightUtil.getLightRayHitResult(level, getBlockPos(), from, to, 25);
                                BlockEntity hitBlock = hitResult.getBlockEntity();
                                LightUtil.transferLight(this, hitBlock);
                            }
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
            if (getWissen() > 0) {
                Color color = getColor();

                if (random.nextFloat() < 0.5) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f * getStage(), 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.005f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5625F, getBlockPos().getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.0375f * getStage(), 0.075f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.005f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5625F, getBlockPos().getZ() + 0.5F);
                }

                if (canWork()) {
                    lightEffect(new Vec3(0.5f, 0.5625f, 0.5), new Vec3(0.1875f, 0.5f, 0.1875f), 0.6f);
                    lightEffect(new Vec3(0.5f, 0.5625f, 0.5), new Vec3(0.8125f, 0.5f, 0.1875f), 0.6f);
                    lightEffect(new Vec3(0.5f, 0.5625f, 0.5), new Vec3(0.1875f, 0.5f, 0.8125f), 0.6f);
                    lightEffect(new Vec3(0.5f, 0.5625f, 0.5), new Vec3(0.8125f, 0.5f, 0.8125f), 0.6f);

                    lightEffect(new Vec3(0.1875f, 0.5f, 0.1875f), new Vec3(0.5f, 0.8125f, 0.5), 0.4f);
                    lightEffect(new Vec3(0.8125f, 0.5f, 0.1875f), new Vec3(0.5f, 0.8125f, 0.5), 0.4f);
                    lightEffect(new Vec3(0.1875f, 0.5f, 0.8125f), new Vec3(0.5f, 0.8125f, 0.5), 0.4f);
                    lightEffect(new Vec3(0.8125f, 0.5f, 0.8125f), new Vec3(0.5f, 0.8125f, 0.5), 0.4f);
                }
            }

            if (getLight() > 0) {
                if (sound == null) {
                    sound = LightEmitterSoundInstance.getSound(this);
                    sound.playSound();
                } else if (sound.isStopped()) {
                    sound = LightEmitterSoundInstance.getSound(this);
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

        tag.putInt("wissen", wissen);
        tag.putInt("light", light);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        blockToX = tag.getInt("blockToX");
        blockToY = tag.getInt("blockToY");
        blockToZ = tag.getInt("blockToZ");
        isToBlock = tag.getBoolean("isToBlock");

        wissen = tag.getInt("wissen");
        light = tag.getInt("light");
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
    }

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    public Color getColor() {
        Color color = new Color(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB());

        ArcaneLumosBlock lumos = getLumos();
        if (lumos != null) {
            color = ArcaneLumosBlock.getColor(lumos.color);
        }

        return color;
    }

    public Color getRayColor() {
        Color color = new Color(0.886f, 0.811f, 0.549f);

        ArcaneLumosBlock lumos = getLumos();
        if (lumos != null) {
            color = ArcaneLumosBlock.getColor(lumos.color);
        }

        return color;
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

    public void lightEffect(Vec3 from, Vec3 to, float chance) {
        Color color = getColor();

        if (random.nextFloat() < chance) {
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(color).build())
                    .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                    .setScaleData(GenericParticleData.create(0.1f, 0).build())
                    .setLifetime(20)
                    .addVelocity((to.x() - from.x()) / 20, (to.y() - from.y()) / 20, (to.z() - from.z()) / 20)
                    .spawn(level, getBlockPos().getX() + from.x(), getBlockPos().getY() + from.y(), getBlockPos().getZ() + from.z());
        }
        if (random.nextFloat() < chance / 2) {
            boolean square = random.nextBoolean();
            float i = square ? 0.5f : 1f;
            ParticleBuilder.create(square ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                    .setColorData(ColorParticleData.create(color).build())
                    .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                    .setScaleData(GenericParticleData.create(0.05f * i, 0.1f * i, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                    .setLifetime(30)
                    .addVelocity((to.x() - from.x()) / 20, (to.y() - from.y()) / 20, (to.z() - from.z()) / 20)
                    .spawn(level, getBlockPos().getX() + from.x(), getBlockPos().getY() + from.y(), getBlockPos().getZ() + from.z());
        }
    }

    @Override
    public int getWissen() {
        return wissen;
    }

    @Override
    public int getMaxWissen() {
        return 2000;
    }

    @Override
    public boolean canSendWissen() {
        return true;
    }

    @Override
    public boolean canReceiveWissen() {
        return true;
    }

    @Override
    public boolean canConnectSendWissen() {
        return true;
    }

    @Override
    public boolean canConnectReceiveWissen() {
        return true;
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
        return false;
    }

    @Override
    public boolean canConnectSendLight() {
        return false;
    }

    @Override
    public boolean canConnectReceiveLight() {
        return false;
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
        return new Vec3(0.5F, 0.8125F, 0.5F);
    }

    @Override
    public float getLightLensSize() {
        return 0.0625f;
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
