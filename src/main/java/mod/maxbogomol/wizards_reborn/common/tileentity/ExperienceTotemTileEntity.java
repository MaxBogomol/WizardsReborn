package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.experience.IExperienceTileEntity;
import mod.maxbogomol.wizards_reborn.client.particle.ExperienceTotemBurst;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExperienceTotemTileEntity extends BlockEntity implements TickableBlockEntity, IExperienceTileEntity {

    public int experience = 0;

    public Random random = new Random();

    public List<ExperienceTotemBurst> bursts = new ArrayList<>();

    public ExperienceTotemTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ExperienceTotemTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.EXPERIENCE_TOTEM_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
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
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
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
