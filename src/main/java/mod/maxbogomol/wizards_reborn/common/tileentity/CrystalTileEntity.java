package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtils;
import mod.maxbogomol.wizards_reborn.api.light.ILightTileEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IItemResultTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandControlledTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalTileEntity;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrystalTileEntity extends TileSimpleInventory implements TickableBlockEntity, ILightTileEntity, ICooldownTileEntity, IWissenWandFunctionalTileEntity, IWissenWandControlledTileEntity, IItemResultTileEntity {

    public int blockToX = 0;
    public int blockToY =0 ;
    public int blockToZ = 0;
    public boolean isToBlock = false;

    public int light = 0;
    public int cooldown = 0;
    public int maxCooldown = 0;

    public boolean startRitual = false;
    public int tickRitual = 0;
    public CompoundTag tagRitual = new CompoundTag();

    public Random random = new Random();

    public CrystalTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CrystalTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.CRYSTAL_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        CrystalRitual ritual = getCrystalRitual();
        boolean update = false;

        if (!level.isClientSide()) {
            if (getLight() > 0) {
                removeLight(1);
                update = true;
            }
        }

        if (!CrystalRitualUtils.isEmpty(ritual)) {
            if (ritual.canStartWithCrystal(this)) {
                if (getLight() > 0 && startRitual) {
                    if (tickRitual == 0) {
                        if (ritual.canStart(this)) {
                            ritual.start(this);
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
                if (CrystalRitualUtils.isEmpty(ritual)) {
                    isToBlock = false;
                } else if (ritual.hasLightRay(this)) {
                    LightRayHitResult hitResult = setupLightRay();
                    if (hitResult != null) {
                        BlockEntity hitTile = hitResult.getTile();
                        LightUtils.transferLight(this, hitTile);
                        PacketUtils.SUpdateTileEntityPacket(hitTile);
                    }
                }
                update = true;
            }

            if (random.nextFloat() < 0.001f) {
                if (!getCrystalItem().isEmpty()) {
                    if (getCrystalItem().getItem() instanceof CrystalItem crystalItem && crystalItem.getPolishing().getPolishingLevel() > 0) {
                        level.playSound(null, getBlockPos(), WizardsReborn.CRYSTAL_SHIMMER_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f + ((random.nextFloat() - 0.5f) / 2));
                    }
                }
            }
        }

        if (!level.isClientSide()) {
            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
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
    public float getCooldown() {
        float ritualCooldown = getRitualCooldown();
        if (ritualCooldown > 0) {
            return ritualCooldown;
        }
        return 0;
    }

    @Override
    public boolean wissenWandReceiveConnect(ItemStack stack, UseOnContext context, BlockEntity tile) {
        return false;
    }

    @Override
    public boolean wissenWandSendConnect(ItemStack stack, UseOnContext context, BlockEntity tile) {
        BlockPos oldBlockPos = WissenWandItem.getBlockPos(stack);
        BlockEntity oldTile = level.getBlockEntity(oldBlockPos);

        if (oldTile instanceof ILightTileEntity lightTile) {
            if (lightTile.canConnectSendLight()) {
                blockToX = oldBlockPos.getX();
                blockToY = oldBlockPos.getY();
                blockToZ = oldBlockPos.getZ();
                isToBlock = true;
                WissenWandItem.setBlock(stack, false);
                PacketUtils.SUpdateTileEntityPacket(this);
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean wissenWandReload(ItemStack stack, UseOnContext context, BlockEntity tile) {
        isToBlock = false;
        reload();
        PacketUtils.SUpdateTileEntityPacket(this);
        return true;
    }

    @Override
    public void wissenWandFuction() {
        startRitual = true;
    }

    public CrystalRitual getCrystalRitual() {
        if (level.getBlockEntity(getBlockPos().below()) instanceof RunicPedestalTileEntity pedestal) {
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
        return new Color(255, 255, 255);
    }

    public Color getCrystalColor() {
        return getCrystalColor(getCrystalItem());
    }

    public float getRitualCooldown() {
        CrystalRitual ritual = getCrystalRitual();
        if (!CrystalRitualUtils.isEmpty(ritual)) {
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

        if (level.isLoaded(pos)) {
            BlockEntity tileentity = level.getBlockEntity(pos);
            if (tileentity instanceof ILightTileEntity lightTileEntity) {
                Vec3 from = LightUtils.getLightLensPos(getBlockPos(), getLightLensPos());
                Vec3 to = LightUtils.getLightLensPos(pos, lightTileEntity.getLightLensPos());

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

                return LightUtils.getLightRayHitResult(level, getBlockPos(), from, to, 25);
            }
        }

        return null;
    }

    @Override
    public List<ItemStack> getItemsResult() {
        List<ItemStack> list = new ArrayList<>();

        CrystalRitual ritual = getCrystalRitual();
        if (!CrystalRitualUtils.isEmpty(ritual)) {
            return ritual.getItemsResult(this);
        }
        return list;
    }
}
