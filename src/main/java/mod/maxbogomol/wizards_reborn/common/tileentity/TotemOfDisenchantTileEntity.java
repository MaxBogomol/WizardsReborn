package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.TotemOfDisenchantBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class TotemOfDisenchantTileEntity extends BlockEntity implements TickableBlockEntity, IWissenTileEntity, ICooldownTileEntity {
    public final ItemStackHandler itemHandler = createHandler(1);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public int wissen = 0;
    public int cooldown = 0;

    public String enchantment = "";
    public String arcaneEnchantment = "";
    public int enchantmentLevel = 0;
    public boolean isStart = false;

    public Random random = new Random();

    public TotemOfDisenchantTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public TotemOfDisenchantTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.TOTEM_OF_DISENCHANT_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (itemHandler.getStackInSlot(0).isEmpty() && isStart) {
                cooldown = 0;
                enchantment = "";
                arcaneEnchantment = "";
                enchantmentLevel = 0;
                isStart = false;
                update = true;
            }

            if (cooldown <= 0 && isStart) {
                if (!itemHandler.getStackInSlot(0).isEmpty()) {
                    Map<Enchantment, Integer> enchantments =  itemHandler.getStackInSlot(0).getAllEnchantments();

                    int i = enchantment.indexOf(":");
                    if (i >= 0) {
                        String modId = enchantment.substring(0, i);
                        String enchantmentId = enchantment.substring(i + 1);
                        Enchantment enchantmentP = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(modId, enchantmentId));
                        boolean disenchant = false;

                        if (enchantments.size() > 0) {
                            if (enchantmentP != null && !enchantmentP.isCurse()) {
                                enchantments.remove(enchantmentP);
                                EnchantmentHelper.setEnchantments(enchantments, itemHandler.getStackInSlot(0));
                                disenchant =  true;
                            }
                        }

                        if (itemHandler.getStackInSlot(0).getItem().equals(Items.ENCHANTED_BOOK)) {
                            ListTag listtag = EnchantedBookItem.getEnchantments(itemHandler.getStackInSlot(0));
                            for (int ii = 0; ii < listtag.size(); ++ii) {
                                CompoundTag compoundtag = listtag.getCompound(ii);
                                Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(EnchantmentHelper.getEnchantmentId(compoundtag));
                                enchantments.put(enchantment, EnchantmentHelper.getEnchantmentLevel(compoundtag));
                            }

                            if (enchantments.size() > 0) {
                                if (enchantmentP != null && !enchantmentP.isCurse()) {
                                    enchantments.remove(enchantmentP);

                                    if (itemHandler.getStackInSlot(0).getItem().equals(Items.ENCHANTED_BOOK)) {
                                        CompoundTag nbt = itemHandler.getStackInSlot(0).getOrCreateTag();
                                        nbt.put("StoredEnchantments", new ListTag());
                                    }

                                    EnchantmentHelper.setEnchantments(enchantments, itemHandler.getStackInSlot(0));
                                    disenchant =  true;

                                    if (enchantments.size() <= 0) {
                                        itemHandler.setStackInSlot(0, new ItemStack(Items.BOOK));
                                    }
                                }
                            }
                        }

                        if (disenchant) {
                            addWissen((15 - Math.min(15, enchantmentP.getRarity().getWeight())) * (enchantmentLevel * 75));
                        }
                    }

                    ArcaneEnchantment arcaneEnchantmentP = ArcaneEnchantments.getArcaneEnchantment(arcaneEnchantment);
                    if (arcaneEnchantmentP != null && ArcaneEnchantmentUtils.isArcaneItem(itemHandler.getStackInSlot(0))  && !arcaneEnchantmentP.isCurse()) {
                        ArcaneEnchantmentUtils.removeArcaneEnchantment(itemHandler.getStackInSlot(0), arcaneEnchantmentP);
                        addWissen(enchantmentLevel * 1000);
                    }

                    if (itemHandler.getStackInSlot(0).getBaseRepairCost()  > 0) {
                        itemHandler.getStackInSlot(0).setRepairCost(itemHandler.getStackInSlot(0).getBaseRepairCost() - 1);
                    }

                    PacketHandler.sendToTracking(level, getBlockPos(), new TotemOfDisenchantBurstEffectPacket(getBlockPos()));
                    level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.TOTEM_OF_DISENCHANT_END_SOUND.get(), SoundSource.BLOCKS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                }

                cooldown = 0;
                enchantment = "";
                arcaneEnchantment = "";
                enchantmentLevel = 0;
                isStart = false;
                update = true;
            }

            if (cooldown > 0) {
                cooldown--;
                update = true;
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }

        if (level.isClientSide()) {
            if (getWissen() > 0) {
                if (random.nextFloat() < 0.3) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.2f * getStage(), 0)
                            .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                            .setLifetime(20)
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.075f * getStage(), 0)
                            .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
                }
            }

            if (isStart) {
                if (ClientTickHandler.ticksInGame % 2 == 0) {
                    for (int i = 0; i < 2; i++) {
                        double ticks = (ClientTickHandler.ticksInGame) * 6f;

                        float distance = 0.5f;
                        double yaw = Math.toRadians(180 * i + ticks);
                        double pitch = 90;

                        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
                        double Y = Math.cos(pitch);
                        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

                        float r = 0.784f;
                        float g = 1f;
                        float b = 0.560f;

                        if (i == 1) {
                            r = Config.wissenColorR();
                            g = Config.wissenColorG();
                            b = Config.wissenColorB();
                        }

                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(-X / 20, Y / 100, -Z / 20)
                                .setAlpha(0.75f, 0).setScale(0.1f, 0)
                                .setColor(r, g, b)
                                .setLifetime(30)
                                .setSpin(0.1f)
                                .spawn(level, getBlockPos().getX() + 0.5F + X, getBlockPos().getY() + 0.725F, getBlockPos().getZ() + 0.5F + Z);
                    }
                }
            }
        }
    }

    private ItemStackHandler createHandler(int size) {
        return new ItemStackHandler(size) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
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
        tag.put("inv", itemHandler.serializeNBT());

        tag.putInt("wissen", wissen);
        tag.putInt("cooldown", cooldown);

        tag.putString("enchantment", enchantment);
        tag.putString("arcaneEnchantment", arcaneEnchantment);
        tag.putInt("enchantmentLevel", enchantmentLevel);
        tag.putBoolean("isStart", isStart);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        wissen = tag.getInt("wissen");
        cooldown = tag.getInt("cooldown");

        enchantment = tag.getString("enchantment");
        arcaneEnchantment = tag.getString("arcaneEnchantment");
        enchantmentLevel = tag.getInt("enchantmentLevel");
        isStart = tag.getBoolean("isStart");
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
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
    public float getCooldown() {
        if (cooldown > 0) {
            return 200f / cooldown;
        }
        return 0;
    }
}
