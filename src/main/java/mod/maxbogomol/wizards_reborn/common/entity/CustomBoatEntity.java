package mod.maxbogomol.wizards_reborn.common.entity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class CustomBoatEntity extends Boat {
    private static final EntityDataAccessor<String> WOOD_TYPE
            = SynchedEntityData.defineId(CustomBoatEntity.class, EntityDataSerializers.STRING);

    public CustomBoatEntity(EntityType<? extends Boat> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public CustomBoatEntity(Level worldIn, double x, double y, double z) {
        this(WizardsReborn.ARCANE_WOOD_BOAT.get(), worldIn);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WOOD_TYPE, "arcane_wood");
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        compound.putString("Type", this.getWoodType());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Type", this.getWoodType());
    }

    public String getWoodType() {
        return this.entityData.get(WOOD_TYPE);
    }

    public void setWoodType(String wood) {
        this.entityData.set(WOOD_TYPE, wood);
    }

    @Override
    public Item getDropItem() {
        /*switch (this.getWoodType()) {
            case "arcane_wood":
                return WizardsReborn.ARCANE_WOOD_BOAT_ITEM.get();
            default:
                return WizardsReborn.ARCANE_WOOD_BOAT_ITEM.get();
        }*/
        return WizardsReborn.ARCANE_WOOD_SAPLING_ITEM.get();
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(ForgeRegistries.ITEMS.getValue(
                new ResourceLocation(WizardsReborn.MOD_ID, this.getWoodType() + "_boat")));
    }

    /*@Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }*/
}