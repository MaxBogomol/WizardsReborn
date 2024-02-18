package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.IGrowableCrystal;
import mod.maxbogomol.wizards_reborn.api.light.ILightTileEntity;
import mod.maxbogomol.wizards_reborn.common.block.CrystalGrowthBlock;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CrystalGrowthTileEntity extends BlockEntity implements TickableBlockEntity, ILightTileEntity, IGrowableCrystal {

    public int light = 0;
    public int growingTicks = 0;
    public float growingPower = 0;

    public Random random = new Random();

    public CrystalGrowthTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CrystalGrowthTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.CRYSTAL_GROWTH_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (getLight() > 0) {
                removeLight(1);
                update = true;
            }

            if (growingTicks > 0) {
                growingTicks--;
                if (growingTicks <= 0) {
                    growingPower = 0;
                }
                update = true;
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
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
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("light", light);
        tag.putInt("growingTicks", growingTicks);
        tag.putFloat("growingPower", growingPower);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        light = tag.getInt("light");
        growingTicks = tag.getInt("growingTicks");
        growingPower = tag.getFloat("growingPower");
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
        if (getBlockState().getBlock() instanceof CrystalGrowthBlock block) {
            return new Vec3(0.5F, 0.1125F + (0.2 * ((float) getBlockState().getValue(block.getAgeProperty()) / block.getMaxAge())), 0.5F);
        }
        return new Vec3(0.5F, 0.3125F, 0.5F);
    }

    @Override
    public float getLightLensSize() {
        return 0f;
    }

    @Override
    public void setGrowingPower(float power) {
        if (growingPower < power) {
            growingPower = power;
        }
    }

    @Override
    public float getGrowingPower() {
        return growingPower;
    }

    @Override
    public void addGrowing() {
        if (random.nextFloat() < 0.2f) {
            if (getLevel().getBlockState(getBlockPos()).getBlock() instanceof CrystalGrowthBlock growth) {
                growth.grow(getLevel(), getBlockPos(), getLevel().getBlockState(getBlockPos()));
            }
        }
    }
}
