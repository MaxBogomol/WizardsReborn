package mod.maxbogomol.wizards_reborn.common.tileentity;

import mezz.jei.api.constants.VanillaTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.data.recipes.WissenCrystallizerRecipe;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.PacketUtils;
import mod.maxbogomol.wizards_reborn.common.network.WissenCrystallizerBurstEffectPacket;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.Optional;
import java.util.Random;

public class WissenCrystallizerTileEntity extends TileSimpleInventory implements ITickableTileEntity, IWissenTileEntity, IWissenWandFunctionalTileEntity {

    public int wissenInCraft= 0;
    public int wissenIsCraft = 0;
    public boolean startCraft = false;

    public int wissen = 0;

    public Random random = new Random();

    public WissenCrystallizerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public WissenCrystallizerTileEntity() {
        this(WizardsReborn.WISSEN_CRYSTALLIZER_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        if (!world.isRemote()) {
            Optional<WissenCrystallizerRecipe> recipe = world.getRecipeManager().getRecipe(WizardsReborn.WISSEN_CRYSTALLIZER_RECIPE, getItemHandler(), world);
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
                    getItemHandler().removeStackFromSlot(2);
                    wissenInCraft = 0;
                    wissenIsCraft = 0;
                    startCraft = false;

                    for (int i = 0; i < getItemHandler().getSizeInventory(); i++) {
                        getItemHandler().removeStackFromSlot(i);
                    }
                    getItemHandler().setInventorySlotContents(0, recipe.get().getRecipeOutput());

                    PacketHandler.sendToTracking(world, getPos(), new WissenCrystallizerBurstEffectPacket(getPos()));
                    PacketUtils.SUpdateTileEntityPacket(this);
                }
            }
        }

        if (world.isRemote()) {
            if (getWissen() > 0) {
                if (random.nextFloat() < 0.5) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.3f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(20)
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.125F, pos.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.125F, pos.getZ() + 0.5F);
                }
            }

            if (wissenInCraft > 0 && startCraft) {
                if (random.nextFloat() < 0.2) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.125F, pos.getZ() + 0.5F);
                }

                if (random.nextFloat() < 0.3) {
                    Particles.create(WizardsReborn.KARMA_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 35), ((random.nextDouble() - 0.5D) / 35), ((random.nextDouble() - 0.5D) / 35))
                            .setAlpha(0.5f, 0).setScale(0.1f, 0.025f)
                            .setColor(0.733f, 0.564f, 0.937f, 0.733f, 0.564f, 0.937f)
                            .setLifetime(10)
                            .spawn(world, pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5), pos.getY() + 1.125F + ((random.nextDouble() - 0.5D) * 0.5), pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5));
                }
            }
        }
    }

    @Override
    protected Inventory createItemHandler() {
        return new Inventory(11) {
            @Override
            public int getInventoryStackLimit() {
                return 1;
            }
        };
    }

    @Override
    public final SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        write(tag);
        return new SUpdateTileEntityPacket(pos, -999, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        super.onDataPacket(net, packet);
        read(this.getBlockState(),packet.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        tag.putInt("wissenInCraft", wissenInCraft);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putBoolean("startCraft", startCraft);
        tag.putInt("wissen", wissen);
        CompoundNBT ret = super.write(tag);
        return ret;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
        wissenInCraft = tag.getInt("wissenInCraft");
        wissenIsCraft = tag.getInt("wissenIsCraft");
        startCraft = tag.getBoolean("startCraft");
        wissen = tag.getInt("wissen");
        super.read(state, tag);
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

        for (int i = 0; i < getItemHandler().getSizeInventory(); i++) {
            if (!getItemHandler().getStackInSlot(i).isEmpty()) {
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
