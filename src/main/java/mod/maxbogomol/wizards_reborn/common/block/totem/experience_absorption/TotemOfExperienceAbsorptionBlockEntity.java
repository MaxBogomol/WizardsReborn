package mod.maxbogomol.wizards_reborn.common.block.totem.experience_absorption;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IExperienceBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.client.sound.TotemOfExperienceAbsorptionSoundInstance;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.ExperienceTotemBurstPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.extensions.IForgeBlockEntity;

import java.util.List;
import java.util.Random;

public class TotemOfExperienceAbsorptionBlockEntity extends BlockEntityBase implements TickableBlockEntity, IWissenBlockEntity, IExperienceBlockEntity {

    public int wissen = 0;
    public int experience = 0;
    public int cooldown = 0;
    public int tick = 0;

    public Random random = new Random();

    public TotemOfExperienceAbsorptionSoundInstance sound;

    public TotemOfExperienceAbsorptionBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public TotemOfExperienceAbsorptionBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.TOTEM_OF_EXPERIENCE_ABSORPTION.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (getExperience() > 0 && getWissen() < getMaxWissen()) {
                removeExperience(1);
                addWissen(8);
                if (random.nextFloat() < 0.01) {
                    level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.BLOCKS, 0.25f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                }
                update = true;
            }

            if (getExperience() < getMaxExperience() && cooldown <= 0) {
                List<ExperienceOrb> orbs = level.getEntitiesOfClass(ExperienceOrb.class, new AABB(getBlockPos().getX() - 1, getBlockPos().getY() - 1, getBlockPos().getZ() - 1, getBlockPos().getX() + 2, getBlockPos().getY() + 2, getBlockPos().getZ() + 2));
                for (ExperienceOrb orb : orbs) {
                    int remain = WissenUtil.getRemoveWissenRemain(orb.value, 100);
                    remain = 100 - remain;
                    int remainAdd = WissenUtil.getAddWissenRemain(getExperience(), remain, getMaxExperience());
                    remainAdd = remain - remainAdd;
                    if (remainAdd > 0 && remain > 0) {
                        addExperience(remainAdd);
                        orb.value = orb.value - remainAdd;
                        if (orb.value <= 0) {
                            orb.kill();
                        }
                        level.playSound(null, orb.getOnPos(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.5f, 1.2f);
                        WizardsRebornPacketHandler.sendToTracking(level, getBlockPos(), new ExperienceTotemBurstPacket(getBlockPos(), orb.getPosition(0)));
                        update = true;
                    }
                }
                cooldown = 20;
            }

            if (cooldown > 0) {
                cooldown--;
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (getWissen() > 0) {
                if (random.nextFloat() < 0.3) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f * getStage(), 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.015f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.075f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.015f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                }
            }

            if (getExperience() > 0) {
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(0.784f, 1f, 0.560f).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                            .setLifetime(40)
                            .randomVelocity(0.085f, 0.05f, 0.085f)
                            .addVelocity(0, 0.02f, 0)
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                }
            }

            if (getExperience() > 0 && getWissen() < getMaxWissen()) {
                if (random.nextFloat() < 0.5) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB(), 0.784f, 1f, 0.560f).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0).build())
                            .setLifetime(60)
                            .randomVelocity(0.015f * getStage())
                            .addVelocity(0, 0.05f * getStage(), 0)
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                }

                if (sound == null) {
                    sound = TotemOfExperienceAbsorptionSoundInstance.getSound(this);
                    sound.playSound();
                } else if (sound.isStopped()) {
                    sound = TotemOfExperienceAbsorptionSoundInstance.getSound(this);
                    sound.playSound();
                }

                if (tick < 20) {
                    tick++;
                }
            } else {
                if (tick > 0) {
                    tick--;
                    if (tick <= 0) {
                        getLevel().playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.5f, 0.2f);
                    }
                }
            }
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("wissen", wissen);
        tag.putInt("experience", experience);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        wissen = tag.getInt("wissen");
        experience = tag.getInt("experience");
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
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
        return 5000;
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
    public int getExperience() {
        return experience;
    }

    @Override
    public int getMaxExperience() {
        return 500;
    }

    @Override
    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public void addExperience(int experience) {
        this.experience = this.experience + experience;
        if (this.experience > getMaxExperience()) {
            this.experience = getMaxExperience();
        }
    }

    @Override
    public void removeExperience(int experience) {
        this.experience = this.experience - experience;
        if (this.experience < 0) {
            this.experience = 0;
        }
    }
}
