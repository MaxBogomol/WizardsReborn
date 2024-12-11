package mod.maxbogomol.wizards_reborn.common.block.crystal;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IItemResultBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandControlledBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalBlockEntity;
import mod.maxbogomol.wizards_reborn.client.sound.CrystalSoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.runic_pedestal.RunicPedestalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeBlockEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CrystalBlockEntity extends BlockSimpleInventory implements TickableBlockEntity, ILightBlockEntity, ICooldownBlockEntity, IWissenWandFunctionalBlockEntity, IWissenWandControlledBlockEntity, IItemResultBlockEntity {

    public int blockToX = 0;
    public int blockToY = 0;
    public int blockToZ = 0;
    public boolean isToBlock = false;

    public int light = 0;
    public int cooldown = 0;
    public int maxCooldown = 0;

    public boolean startRitual = false;
    public int tickRitual = 0;
    public CompoundTag tagRitual = new CompoundTag();

    public ArrayList<LightTypeStack> lightTypes = new ArrayList<>();

    public CrystalSoundInstance sound;

    public CrystalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CrystalBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.CRYSTAL.get(), pos, state);
    }

    @Override
    public void tick() {
        CrystalRitual ritual = getCrystalRitual();
        boolean update = false;

        if (!level.isClientSide()) {
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
        }

        if (!CrystalRitualUtil.isEmpty(ritual)) {
            if (ritual.canStartWithCrystal(this)) {
                if (getLight() > 0 && startRitual) {
                    if (tickRitual == 0) {
                        if (ritual.canStart(this)) {
                            ritual.start(this);
                            level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.CRYSTAL_RITUAL_START.get(), SoundSource.BLOCKS, 1f, 1f);
                            tagRitual = new CompoundTag();
                        } else {
                            reload();
                            update = true;
                        }
                    }

                    if (startRitual) {
                        if (ritual.canTick(this)) ritual.tick(this);

                        if (ritual.canEnd(this)) {
                            ritual.end(this);
                            level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.CRYSTAL_RITUAL_END.get(), SoundSource.BLOCKS, 1f, 1f);
                            reload();
                        } else {
                            if (!level.isClientSide()) {
                                tickRitual++;
                                update = true;
                            }
                        }
                    }
                } else {
                    if (startRitual) {
                        reload();
                        update = true;
                    }
                }
            } else {
                if (startRitual) {
                    reload();
                    update = true;
                }
            }
        } else {
            if (startRitual) {
                reload();
                update = true;
            }
        }

        if (!level.isClientSide()) {
            if (isToBlock) {
                if (CrystalRitualUtil.isEmpty(ritual)) {
                    isToBlock = false;
                } else if (ritual.hasLightRay(this)) {
                    LightRayHitResult hitResult = setupLightRay();
                    if (hitResult != null) {
                        BlockEntity hitBlockEntity = hitResult.getBlockEntity();
                        LightUtil.transferLight(this, hitBlockEntity);
                        LightUtil.tickHitLightTypeStack(this, getLightTypes(), hitResult);
                        BlockEntityUpdate.packet(hitBlockEntity);
                    }
                }
                update = true;
            }

            if (random.nextFloat() < 0.001f) {
                if (!getCrystalItem().isEmpty()) {
                    if (getCrystalItem().getItem() instanceof CrystalItem crystalItem && crystalItem.getPolishing().getPolishingLevel() > 0) {
                        level.playSound(null, getBlockPos(), WizardsRebornSounds.CRYSTAL_SHIMMER.get(), SoundSource.BLOCKS, 1.0f, 1.0f + ((random.nextFloat() - 0.5f) / 2));
                    }
                }
            }
        }

        if (!level.isClientSide()) {
            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (getLight() > 0 && isToBlock || startRitual) {
                if (sound == null) {
                    sound = CrystalSoundInstance.getSound(this);
                    sound.playSound();
                } else if (sound.isStopped()) {
                    sound = CrystalSoundInstance.getSound(this);
                    sound.playSound();
                }
            }

            if (startRitual && !CrystalRitualUtil.isEmpty(ritual)) {
                if (random.nextFloat() < 0.08f) {
                    ParticleBuilder.create(FluffyFurParticles.SQUARE)
                            .setBehavior(SparkParticleBehavior.create()
                                    .setScaleData(GenericParticleData.create(1, 3, 1).build())
                                    .build())
                            .setColorData(ColorParticleData.create(ritual.getColor()).build())
                            .setTransparencyData(GenericParticleData.create(0.5f, 0.75f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setScaleData(GenericParticleData.create(0.025f, 0.05f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setLifetime(50)
                            .setVelocity(0, 0.05f, 0)
                            .flatRandomOffset(0.5f, 0f, 0.5f)
                            .disablePhysics()
                            .spawn(level, getBlockPos().getX() + 0.5f, getBlockPos().getY(), getBlockPos().getZ() + 0.5f);
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
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("blockToX", blockToX);
        tag.putInt("blockToY", blockToY);
        tag.putInt("blockToZ", blockToZ);
        tag.putBoolean("isToBlock", isToBlock);

        tag.putInt("light", light);
        tag.putInt("cooldown", cooldown);
        tag.putInt("maxCooldown", maxCooldown);

        tag.putBoolean("startRitual", startRitual);
        tag.putInt("tickRitual", tickRitual);
        tag.put("tagRitual", tagRitual);

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
        cooldown = tag.getInt("cooldown");
        maxCooldown = tag.getInt("maxCooldown");

        startRitual = tag.getBoolean("startRitual");
        tickRitual = tag.getInt("tickRitual");
        tagRitual = tag.getCompound("tagRitual");

        lightTypes = LightUtil.stacksFromTag(tag.getList("lightTypes", Tag.TAG_COMPOUND));
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
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
        return new Vec3(0.5F, 0.3125F, 0.5F);
    }

    @Override
    public float getLightLensSize() {
        return 0f;
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
    public float getCooldown() {
        float ritualCooldown = getRitualCooldown();
        if (ritualCooldown > 0) {
            return ritualCooldown;
        }
        return 0;
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
        if (startRitual) {
            reload();
            BlockEntityUpdate.packet(this);
            level.playSound(null, getBlockPos(), WizardsRebornSounds.CRYSTAL_RITUAL_END.get(), SoundSource.BLOCKS, 1f, 1f);
        }
        return true;
    }

    @Override
    public void wissenWandFunction() {
        startRitual = true;
        BlockEntityUpdate.packet(this);
    }

    public CrystalRitual getCrystalRitual() {
        if (level.getBlockEntity(getBlockPos().below()) instanceof RunicPedestalBlockEntity pedestal) {
            return pedestal.getCrystalRitual();
        }
        return null;
    }

    public ItemStack getCrystalItem() {
        if (!getItemHandler().getItem(0).isEmpty() && getItemHandler().getItem(0).getItem() instanceof CrystalItem crystalItem) {
            return getItemHandler().getItem(0);
        }
        return ItemStack.EMPTY;
    }

    public Color getCrystalColor(ItemStack crystal) {
        if (!crystal.isEmpty() && crystal.getItem() instanceof CrystalItem crystalItem) {
            CrystalType type = crystalItem.getType();
            if (type != null) {
                return type.getColor();
            }
        }
        return Color.WHITE;
    }

    public Color getCrystalColor() {
        return getCrystalColor(getCrystalItem());
    }

    public float getRitualCooldown() {
        CrystalRitual ritual = getCrystalRitual();
        if (!CrystalRitualUtil.isEmpty(ritual)) {
            return ritual.getCrystalCooldown(this);
        }

        return 0;
    }

    public void reload() {
        startRitual = false;
        tickRitual = 0;
        cooldown = 0;
        maxCooldown = 0;
        tagRitual = new CompoundTag();
        CrystalRitual.clearItemHandler(this);
    }

    public LightRayHitResult setupLightRay() {
        BlockPos pos = new BlockPos(blockToX, blockToY, blockToZ);

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ILightBlockEntity lightBlockEntity) {
            Vec3 from = LightUtil.getLightLensPos(getBlockPos(), getLightLensPos());
            Vec3 to = LightUtil.getLightLensPos(pos, lightBlockEntity.getLightLensPos());

            double dX = to.x() - from.x();
            double dY = to.y() - from.y();
            double dZ = to.z() - from.z();

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float rayDistance = 0.33f;

            double X = Math.sin(pitch) * Math.cos(yaw) * rayDistance;
            double Y = Math.cos(pitch) * rayDistance;
            double Z = Math.sin(pitch) * Math.sin(yaw) * rayDistance;

            from = from.add(-X, -Y, -Z);

            return LightUtil.getLightRayHitResult(level, getBlockPos(), from, to, 25);
        }

        return null;
    }

    @Override
    public List<ItemStack> getItemsResult() {
        List<ItemStack> list = new ArrayList<>();

        CrystalRitual ritual = getCrystalRitual();
        if (!CrystalRitualUtil.isEmpty(ritual)) {
            return ritual.getItemsResult(this);
        }
        return list;
    }
}
