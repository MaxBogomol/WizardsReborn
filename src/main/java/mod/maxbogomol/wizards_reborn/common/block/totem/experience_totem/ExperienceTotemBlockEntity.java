package mod.maxbogomol.wizards_reborn.common.block.totem.experience_totem;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IExperienceBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.ExperienceTotemBurstPacket;
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

public class ExperienceTotemBlockEntity extends BlockEntityBase implements TickableBlockEntity, IExperienceBlockEntity {

    public int experience = 0;
    public int cooldown = 0;

    public ExperienceTotemBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ExperienceTotemBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.EXPERIENCE_TOTEM.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (getExperience() < getMaxExperience() && cooldown <= 0) {
                List<ExperienceOrb> orbs = level.getEntitiesOfClass(ExperienceOrb.class, new AABB(getBlockPos().getX() - 1, getBlockPos().getY() - 1, getBlockPos().getZ() - 1, getBlockPos().getX() + 2, getBlockPos().getY() + 2, getBlockPos().getZ() + 2));
                for (ExperienceOrb orb : orbs) {
                    if (orb.tickCount > 50) {
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
                }
                cooldown = 20;
            }

            if (cooldown > 0) {
                cooldown--;
            }

            if (update) setChanged();
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("experience", experience);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        experience = tag.getInt("experience");
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public int getMaxExperience() {
        return 2500;
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
