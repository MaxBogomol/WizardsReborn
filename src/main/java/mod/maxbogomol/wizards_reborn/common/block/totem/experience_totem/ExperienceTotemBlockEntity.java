package mod.maxbogomol.wizards_reborn.common.block.totem.experience_totem;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IExperienceBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.particle.ExperienceTotemBurst;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.ExperienceTotemBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExperienceTotemBlockEntity extends BlockEntity implements TickableBlockEntity, IExperienceBlockEntity {

    public int experience = 0;
    public int cooldown = 0;

    public Random random = new Random();

    public List<ExperienceTotemBurst> bursts = new ArrayList<>();

    public ExperienceTotemBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ExperienceTotemBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.EXPERIENCE_TOTEM_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (getExperience() < getMaxExperience() && cooldown <= 0) {
                List<ExperienceOrb> orbs = level.getEntitiesOfClass(ExperienceOrb.class, new AABB(getBlockPos().getX() - 1, getBlockPos().getY() - 1, getBlockPos().getZ() - 1, getBlockPos().getX() + 2, getBlockPos().getY() + 2, getBlockPos().getZ() + 2));
                for (ExperienceOrb orb : orbs) {
                    if (orb.tickCount > 50) {
                        int remain = WissenUtils.getRemoveWissenRemain(orb.value, 100);
                        remain = 100 - remain;
                        int remainAdd = WissenUtils.getAddWissenRemain(getExperience(), remain, getMaxExperience());
                        remainAdd = remain - remainAdd;
                        if (remainAdd > 0 && remain > 0) {
                            addExperience(remainAdd);
                            orb.value = orb.value - remainAdd;
                            if (orb.value <= 0) {
                                orb.kill();
                            }
                            level.playSound(null, orb.getOnPos(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.5f, 1.2f);
                            PacketHandler.sendToTracking(level, getBlockPos(), new ExperienceTotemBurstEffectPacket(getBlockPos(), orb.getPosition(0)));
                            update = true;
                        }
                    }
                }
                cooldown = 20;
            }

            if (cooldown > 0) {
                cooldown--;
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }

        if (level.isClientSide()) {
            List<ExperienceTotemBurst> newBursts = new ArrayList<>();
            newBursts.addAll(bursts);
            for (ExperienceTotemBurst burst : newBursts) {
                burst.tick();
                if (burst.end) {
                    bursts.remove(burst);
                }
            }
        }
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

    @OnlyIn(Dist.CLIENT)
    public void addBurst(Vec3 startPos, Vec3 endPos) {
        bursts.add(new ExperienceTotemBurst(level, (float) startPos.x(), (float) startPos.y(), (float) startPos.z(),
                (float) endPos.x(), (float) endPos.y(), (float) endPos.z(), 0.1f, 5, 200,
                0.784f, 1f, 0.560f));
    }
}
