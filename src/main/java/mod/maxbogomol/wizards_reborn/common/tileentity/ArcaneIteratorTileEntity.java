package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.client.particle.ArcaneIteratorBurst;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.ArcaneIteratorBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneIteratorRecipe;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ArcaneIteratorTileEntity extends BlockEntity implements TickableBlockEntity, IWissenTileEntity, ICooldownTileEntity, IWissenWandFunctionalTileEntity, IItemResultTileEntity {
    public int wissenInCraft= 0;
    public int wissenIsCraft = 0;
    public boolean startCraft = false;
    public double angleA = 0;
    public double angleB = 0;

    public int wissen = 0;

    public int rotate = 0;
    public int offset = 0;
    public int scale = 0;
    public List<ArcaneIteratorBurst> bursts = new ArrayList<>();

    public Random random = new Random();

    public ArcaneIteratorTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcaneIteratorTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ARCANE_ITERATOR_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (isWorks()) {
                List<ArcanePedestalTileEntity> pedestals = getPedestals();
                List<ItemStack> items = getItemsFromPedestals(pedestals);
                SimpleContainer inv = new SimpleContainer(items.size());
                for (int i = 0; i < items.size(); i++) {
                    inv.setItem(i, items.get(i));
                }

                Optional<ArcaneIteratorRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.ARCANE_ITERATOR_RECIPE.get(), inv, level);
                wissenInCraft = recipe.map(ArcaneIteratorRecipe::getRecipeWissen).orElse(0);

                if (wissenInCraft <= 0 && (wissenIsCraft > 0 || startCraft)) {
                    wissenIsCraft = 0;
                    startCraft = false;

                    update = true;
                }

                if ((wissenInCraft > 0) && (wissen > 0) && (startCraft)) {
                    int addRemainCraft = WissenUtils.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInCraft);
                    int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft);

                    wissenIsCraft = wissenIsCraft + (getWissenPerTick() - addRemainCraft - removeRemain);
                    removeWissen(getWissenPerTick() - addRemainCraft - removeRemain);

                    update = true;
                }

                if (wissenInCraft > 0 && startCraft) {
                    if (wissenInCraft <= wissenIsCraft) {
                        wissenInCraft = 0;
                        wissenIsCraft = 0;
                        startCraft = false;

                        CompoundTag tagPos = new CompoundTag();
                        ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
                        if (recipe.get().getRecipeIsSaveNBT()) {
                            stack.setTag(items.get(0).getOrCreateTag());
                        }

                        for (int i = 0; i < pedestals.size(); i++) {
                            pedestals.get(i).getItemHandler().removeItemNoUpdate(0);
                            PacketUtils.SUpdateTileEntityPacket(pedestals.get(i));
                            CompoundTag tagBlock = new CompoundTag();
                            tagBlock.putInt("x", pedestals.get(i).getBlockPos().getX());
                            tagBlock.putInt("y", pedestals.get(i).getBlockPos().getY());
                            tagBlock.putInt("z", pedestals.get(i).getBlockPos().getZ());
                            tagPos.put(String.valueOf(i), tagBlock);
                            level.playSound(WizardsReborn.proxy.getPlayer(), pedestals.get(i).getBlockPos(), WizardsReborn.WISSEN_TRANSFER_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                        }
                        getMainPedestal().setItem(0, stack);
                        PacketUtils.SUpdateTileEntityPacket(getMainPedestal());

                        PacketHandler.sendToTracking(level, getBlockPos(), new ArcaneIteratorBurstEffectPacket(tagPos));
                        level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.WISSEN_BURST_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                    }
                }
            } else {
                wissenIsCraft = 0;
                startCraft = false;

                update = true;
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }

        if (level.isClientSide()) {
            if (isWorks()) {
                rotate++;

                List<ArcaneIteratorBurst> newBursts = new ArrayList<>();
                newBursts.addAll(bursts);
                for (ArcaneIteratorBurst burst : newBursts) {
                    burst.tick();
                    if (burst.end) {
                        bursts.remove(burst);
                    }
                }

                if (getWissen() > 0) {
                    if (random.nextFloat() < 0.5) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                                .setAlpha(0.25f, 0).setScale(0.3f * getStage(), 0)
                                .setColor(0.466f, 0.643f, 0.815f)
                                .setLifetime(20)
                                .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() - 0.7F, worldPosition.getZ() + 0.5F);
                    }
                    if (random.nextFloat() < 0.1) {
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                                .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                                .setColor(0.466f, 0.643f, 0.815f)
                                .setLifetime(30)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() - 0.7F, worldPosition.getZ() + 0.5F);
                    }
                }

                if (wissenInCraft > 0 && startCraft) {
                    if (offset < 40) {
                        offset++;
                    }
                    if (scale < 60) {
                        scale++;
                    }

                    if (random.nextFloat() < 0.55) {
                        particleRay(0.03f);
                    }
                    if (random.nextFloat() < 0.55) {
                        particleRay(-0.03f);
                    }
                    if (random.nextFloat() < 0.25) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 40), ((random.nextDouble() - 0.5D) / 40), ((random.nextDouble() - 0.5D) / 40))
                                .setAlpha(0.4f, 0).setScale(0.5f, 0)
                                .setColor(0.611f, 0.352f, 0.447f, 0.807f, 0.800f, 0.639f)
                                .setLifetime(120)
                                .setSpin((0.25f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
                    }

                    List<ArcanePedestalTileEntity> pedestals = getPedestals();
                    for (ArcanePedestalTileEntity pedestal : pedestals) {
                        if (random.nextFloat() < 0.025) {
                            bursts.add(new ArcaneIteratorBurst(level, pedestal.getBlockPos().getX() + 0.5F, pedestal.getBlockPos().getY() + 1.3F, pedestal.getBlockPos().getZ() + 0.5F,
                                    getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F, 0.05f, 20, 200,
                                    random.nextFloat(), random.nextFloat(), random.nextFloat()));
                            level.playSound(WizardsReborn.proxy.getPlayer(), pedestal.getBlockPos(), WizardsReborn.WISSEN_TRANSFER_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                        }
                    }
                } else {
                    if (offset > 0) {
                        offset--;
                    }
                    if (scale > 0) {
                        scale--;
                    }
                }
            } else {
                if (offset > 0) {
                    offset--;
                }
                if (scale > 0) {
                    scale--;
                }
            }
        }
    }

    public void particleRay(float speed) {
        float xOffset = (float) (Math.cos(angleA) * Math.cos(angleB));
        float yOffset = (float) (Math.sin(angleA) * Math.cos(angleB));
        float zOffset = (float) Math.sin(angleB);
        float vx = xOffset * speed + random.nextFloat() * speed * 0.1f;
        float vy = yOffset * speed + random.nextFloat() * speed * 0.1f;
        float vz = zOffset * speed + random.nextFloat() * speed * 0.1f;

        Particles.create(WizardsReborn.STEAM_PARTICLE)
                .addVelocity(vx, vy, vz)
                .setAlpha(0.3f, 0).setScale(0.5f, 0)
                .setColor(0.611f, 0.352f, 0.447f, 0.807f, 0.800f, 0.639f)
                .setLifetime(120)
                .setSpin((0.25f * (float) ((random.nextDouble() - 0.5D) * 2)))
                .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
    }

    public boolean isWorks() {
        BlockEntity tile = level.getBlockEntity(getBlockPos().below().below());
        if (tile != null) {
            if (tile instanceof ArcanePedestalTileEntity pedestal) {
                return true;
            }
        }

        return false;
    }

    public ArcanePedestalTileEntity getMainPedestal() {
        BlockEntity tile = level.getBlockEntity(getBlockPos().below().below());
        if (tile != null) {
            if (tile instanceof ArcanePedestalTileEntity pedestal) {
                return pedestal;
            }
        }

        return null;
    }

    public List<ArcanePedestalTileEntity> getPedestals() {
        List<ArcanePedestalTileEntity> pedestals = new ArrayList<>();

        for (int x = -5; x < 5; x++) {
            for (int y = -3; y < 3; y++) {
                for (int z = -5; z < 5; z++) {
                    BlockEntity tile = level.getBlockEntity(new BlockPos(getBlockPos().getX() + x, getBlockPos().getY() + y, getBlockPos().getZ() + z));
                    if (tile != null) {
                        if (tile instanceof ArcanePedestalTileEntity pedestal) {
                            pedestals.add(pedestal);
                        }
                    }
                }
            }
        }

        ArcanePedestalTileEntity pedestal = getMainPedestal();
        if (pedestal != null) {
            if (pedestals.contains(pedestal)) {
                pedestals.remove(pedestal);
            }
            pedestals.add(0, pedestal);
        }

        return pedestals;
    }

    public List<ItemStack> getItemsFromPedestals(List<ArcanePedestalTileEntity> pedestals) {
        List<ItemStack> items = new ArrayList<>();
        for (ArcanePedestalTileEntity pedestal : pedestals) {
            if (!pedestal.getItemHandler().getItem(0).isEmpty()) {
                items.add(pedestal.getItemHandler().getItem(0));
            }
        }

        return items;
    }

    public List<ItemStack> getItemsFromPedestals() {
        return getItemsFromPedestals(getPedestals());
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
        tag.putInt("wissenInCraft", wissenInCraft);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putBoolean("startCraft", startCraft);
        tag.putDouble("angleA", angleA);
        tag.putDouble("angleB", angleB);
        tag.putInt("wissen", wissen);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        wissenInCraft = tag.getInt("wissenInCraft");
        wissenIsCraft = tag.getInt("wissenIsCraft");
        startCraft = tag.getBoolean("startCraft");
        angleA = tag.getDouble("angleA");
        angleB = tag.getDouble("angleB");
        wissen = tag.getInt("wissen");
    }

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    @Override
    public int getWissen() {
        return wissen;
    }

    @Override
    public int getMaxWissen() {
        return 15000;
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

    @Override
    public void wissenWandFuction() {
        if (isWorks()) {
            if (!startCraft) {
                angleA = random.nextDouble() * Math.PI * 2;
                angleB = random.nextDouble() * Math.PI * 2;
            }
            startCraft = true;
        }
    }

    @Override
    public float getCooldown() {
        if (wissenInCraft > 0) {
            return (float) wissenInCraft / wissenIsCraft;
        }
        return 0;
    }

    public int getWissenPerTick() {
        return 5;
    }

    @Override
    public List<ItemStack> getItemsResult() {
        List<ItemStack> list = new ArrayList<>();
        if (isWorks()) {
            List<ArcanePedestalTileEntity> pedestals = getPedestals();
            List<ItemStack> items = getItemsFromPedestals(pedestals);
            SimpleContainer inv = new SimpleContainer(items.size());
            for (int i = 0; i < items.size(); i++) {
                inv.setItem(i, items.get(i));
            }

            Optional<ArcaneIteratorRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.ARCANE_ITERATOR_RECIPE.get(), inv, level);
            if (recipe.isPresent()) {
                ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
                if (recipe.get().getRecipeIsSaveNBT()) {
                    stack.setTag(items.get(0).copy().getOrCreateTag());
                }
                list.add(stack);
            }
        }
        return list;
    }
}
