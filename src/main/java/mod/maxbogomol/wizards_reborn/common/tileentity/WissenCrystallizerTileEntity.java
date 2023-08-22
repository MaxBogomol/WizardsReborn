package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import mod.maxbogomol.wizards_reborn.common.network.WissenCrystallizerBurstEffectPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.SimpleContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Optional;
import java.util.Random;

public class WissenCrystallizerTileEntity extends TileSimpleInventory implements BlockEntityTicker, IWissenTileEntity, IWissenWandFunctionalTileEntity {

    public int wissenInCraft= 0;
    public int wissenIsCraft = 0;
    public boolean startCraft = false;

    public int wissen = 0;

    public Random random = new Random();

    public WissenCrystallizerTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WissenCrystallizerTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.WISSEN_CRYSTALLIZER_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, BlockEntity pBlockEntity) {
        if (!level.isClientSide()) {
            /*Optional<WissenCrystallizerRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.WISSEN_CRYSTALLIZER_RECIPE, getItemHandler(), level);
            wissenInCraft =  recipe.map(WissenCrystallizerRecipe::getRecipeWissen).orElse(0);

            if (wissenInCraft <= 0) {
                wissenIsCraft = 0;
                startCraft = false;
            }
            
            if ((wissenInCraft > 0) && (wissen > 0) && (startCraft)) {
                int addRemainCraft = WissenUtils.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInCraft);
                int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft);

                wissenIsCraft = wissenIsCraft + (getWissenPerTick() - addRemainCraft - removeRemain);
                removeWissen(getWissenPerTick() - addRemainCraft - removeRemain);

                PacketUtils.SUpdateTileEntityPacket(this);
            }

            if (wissenInCraft > 0 && startCraft) {
                if (wissenInCraft <= wissenIsCraft) {
                    getItemHandler().removeItemNoUpdate(2);
                    wissenInCraft = 0;
                    wissenIsCraft = 0;
                    startCraft = false;

                    for (int i = 0; i < getItemHandler().getContainerSize(); i++) {
                        getItemHandler().removeItemNoUpdate(i);
                    }

                    int count = recipe.get().getResultItem().getCount();
                    if (count > getItemHandler().getContainerSize()) {
                        count = getItemHandler().getContainerSize();
                    }

                    for (int i = 0; i < count; i++) {
                        getItemHandler().setItem(i, recipe.get().getResultItem());
                    }

                    PacketHandler.sendToTracking(level, getBlockPos(), new WissenCrystallizerBurstEffectPacket(getBlockPos()));
                    PacketUtils.SUpdateTileEntityPacket(this);
                }
            }*/
        }

        if (level.isClientSide()) {
            if (getWissen() > 0) {
                if (random.nextFloat() < 0.5) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.3f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(20)
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.125F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.125F, worldPosition.getZ() + 0.5F);
                }
            }

            if (wissenInCraft > 0 && startCraft) {
                if (random.nextFloat() < 0.2) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.125F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 40), ((random.nextDouble() - 0.5D) / 40), ((random.nextDouble() - 0.5D) / 40))
                            .setAlpha(0.75f, 0).setScale(0.05f * getStage(), 0)
                            .setColor(random.nextFloat(), random.nextFloat(), random.nextFloat())
                            .setLifetime(65)
                            .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.125F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.3) {
                    Particles.create(WizardsReborn.KARMA_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 35), ((random.nextDouble() - 0.5D) / 35), ((random.nextDouble() - 0.5D) / 35))
                            .setAlpha(0.5f, 0).setScale(0.1f, 0.025f)
                            .setColor(0.733f, 0.564f, 0.937f)
                            .setLifetime(10)
                            .spawn(level, worldPosition.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5), worldPosition.getY() + 1.125F + ((random.nextDouble() - 0.5D) * 0.5), worldPosition.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5));
                }
            }
        }
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(11) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
    }

    /*@Override
    public final ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        save(tag);
        return new ClientboundBlockEntityDataPacket(worldPosition, -999, tag);
    }*/

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        load(packet.getTag());
    }

    /*@Override
    public CompoundTag getUpdateTag()
    {
        return this.save(new CompoundTag());
    }*/

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    /*@Override
    public CompoundTag save(CompoundTag tag) {
        super.save(tag);
        tag.putInt("wissenInCraft", wissenInCraft);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putBoolean("startCraft", startCraft);
        tag.putInt("wissen", wissen);
        CompoundTag ret = super.save(tag);
        return ret;
    }*/

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        wissenInCraft = tag.getInt("wissenInCraft");
        wissenIsCraft = tag.getInt("wissenIsCraft");
        startCraft = tag.getBoolean("startCraft");
        wissen = tag.getInt("wissen");
        super.load(tag);
    }

    @Override
    public int getWissen() {
        return wissen;
    }

    @Override
    public int getMaxWissen() {
        return 10000;
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
    public int getWissenPerReceive() {
        return 0;
    }

    @Override
    public int getSendWissenCooldown() {
        return 0;
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

    public int getWissenPerTick() {
        return 10;
    }

    public int getInventorySize() {
        int size = 0;

        for (int i = 0; i < getItemHandler().getContainerSize(); i++) {
            if (!getItemHandler().getItem(i).isEmpty()) {
                size++;
            } else {
                break;
            }
        }

        return size;
    }

    @Override
    public void wissenWandFuction() {
        startCraft = true;
    }
}
