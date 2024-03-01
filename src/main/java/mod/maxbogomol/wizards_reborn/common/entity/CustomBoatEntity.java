package mod.maxbogomol.wizards_reborn.common.entity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.NetworkHooks;

public class CustomBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(CustomBoatEntity.class, EntityDataSerializers.INT);

    public CustomBoatEntity(EntityType<? extends Boat> type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }

    public CustomBoatEntity(Level level, double x, double y, double z) {
        this(WizardsReborn.BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public CustomBoatEntity.Type getCustomBoatEntityType() {
        return CustomBoatEntity.Type.byId(this.getEntityData().get(BOAT_TYPE));
    }

    @Override
    public Item getDropItem() {
        return switch (this.getCustomBoatEntityType()) {
            case ARCANE_WOOD -> WizardsReborn.ARCANE_WOOD_BOAT_ITEM.get();
            case INNOCENT_WOOD -> WizardsReborn.INNOCENT_WOOD_BOAT_ITEM.get();
        };
    }

    public void setCustomBoatEntityType(CustomBoatEntity.Type boatType) {
        this.getEntityData().set(BOAT_TYPE, boatType.ordinal());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(BOAT_TYPE, Type.ARCANE_WOOD.ordinal());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putString("Type", this.getCustomBoatEntityType().getName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Type", 8)) {
            this.setCustomBoatEntityType(CustomBoatEntity.Type.getTypeFromString(tag.getString("Type")));
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public enum Type {
        ARCANE_WOOD(WizardsReborn.ARCANE_WOOD_PLANKS.get(), "arcane_wood"),
        INNOCENT_WOOD(WizardsReborn.INNOCENT_WOOD_PLANKS.get(), "innocent_wood");

        private final String name;
        private final Block block;

        Type(Block block, String name) {
            this.name = name;
            this.block = block;
        }

        public String getName() {
            return this.name;
        }

        public Block asPlank() {
            return this.block;
        }

        public String toString() {
            return this.name;
        }

        public static CustomBoatEntity.Type byId(int id) {
            CustomBoatEntity.Type[] types = values();
            if (id < 0 || id >= types.length) {
                id = 0;
            }

            return types[id];
        }

        public static CustomBoatEntity.Type getTypeFromString(String nameIn) {
            CustomBoatEntity.Type[] types = values();

            for (Type type : types) {
                if (type.getName().equals(nameIn)) {
                    return type;
                }
            }

            return types[0];
        }
    }
}