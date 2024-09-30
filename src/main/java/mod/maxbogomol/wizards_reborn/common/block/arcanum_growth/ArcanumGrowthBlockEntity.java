package mod.maxbogomol.wizards_reborn.common.block.arcanum_growth;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.crystalritual.IGrowableCrystal;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class ArcanumGrowthBlockEntity extends BlockEntityBase implements TickableBlockEntity, ILightBlockEntity, IGrowableCrystal {

    public int light = 0;
    public int growingTicks = 0;
    public float growingPower = 0;

    public ArrayList<LightTypeStack> lightTypes = new ArrayList<>();

    public ArcanumGrowthBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcanumGrowthBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.ARCANUM_GROWTH.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

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

            if (growingTicks > 0) {
                growingTicks--;
                if (growingTicks <= 0) {
                    growingPower = 0;
                }
                update = true;
            }

            if (update) setChanged();
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("light", light);
        tag.putInt("growingTicks", growingTicks);
        tag.putFloat("growingPower", growingPower);

        tag.put("lightTypes", LightUtil.stacksToTag(lightTypes));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        light = tag.getInt("light");
        growingTicks = tag.getInt("growingTicks");
        growingPower = tag.getFloat("growingPower");

        lightTypes = LightUtil.stacksFromTag(tag.getList("lightTypes", Tag.TAG_COMPOUND));
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
        if (getBlockState().getBlock() instanceof ArcanumGrowthBlock block) {
            return new Vec3(0.5F, 0.1125F + (0.2 * ((float) getBlockState().getValue(block.getAgeProperty()) / block.getMaxAge())), 0.5F);
        }
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
        if (getLevel().getBlockState(getBlockPos()).getBlock() instanceof ArcanumGrowthBlock growth) {
            growth.grow(getLevel(), getBlockPos(), getLevel().getBlockState(getBlockPos()));
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
    }
}
