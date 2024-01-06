package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SmokeEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.CenserRecipe;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ArcaneCenserTileEntity extends ExposedTileSimpleInventory implements TickableBlockEntity, ISteamTileEntity {
    public int steam = 0;
    public int cooldown = 0;

    public Random random = new Random();

    public ArcaneCenserTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcaneCenserTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ARCANE_CENSER_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (cooldown > 0) {
                cooldown = cooldown - 1;
                update = true;
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }

        if (level.isClientSide()) {
            if (cooldown > 0) {
                if (random.nextFloat() < 0.2F) {
                    Particles.create(WizardsReborn.STEAM_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15) + 0.05, ((random.nextDouble() - 0.5D) / 15))
                            .setAlpha(0.1f, 0).setScale(0.1f, 3)
                            .setColor(1f, 1f, 1f)
                            .setLifetime(200 + random.nextInt(100))
                            .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 1F, getBlockPos().getZ() + 0.5F);
                }
            }

            float amount = (float) getSteam() / (float) getMaxSteam();

            for (int i = 0; i < 2 * amount; i++) {
                if (random.nextFloat() < amount) {
                    Particles.create(WizardsReborn.STEAM_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                            .setAlpha(0.4f, 0).setScale(0f, 0.3f)
                            .setColor(1f, 1f, 1f)
                            .setLifetime(30)
                            .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, getBlockPos().getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.05), getBlockPos().getY() + 0.375F + ((random.nextDouble() - 0.5D) * 0.15), getBlockPos().getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.05));
                }
            }
        }
    }

    public void smoke(Player player) {
        cooldown = 100;
        removeSteam(150);

        List<MobEffectInstance> effects = new ArrayList<>();
        List<Item> usedItems = new ArrayList<>();

        SimpleContainer inv = new SimpleContainer(1);
        for (int i = 0; i < getInventorySize(); i++) {
            inv.setItem(0, getItem(i).copy());
            Optional<CenserRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.CENSER_RECIPE.get(), inv, level);
            if (recipe.isPresent()) {
                for (MobEffectInstance effectInstance : recipe.get().getEffects()) {
                    effects.add(new MobEffectInstance(effectInstance));
                }
                if (!usedItems.contains(getItem(i).getItem())) {
                    usedItems.add(getItem(i).getItem());
                }
            }
        }

        float R = 1f;
        float G = 1f;
        float B = 1f;

        for (int i = 0; i < getInventorySize(); i++) {
            ItemStack item = getItem(i);
            if (usedItems.contains(item.getItem())) {
                setItemBurnCenser(item, getItemBurnCenser(item) + 1);
                setItem(i, item);
                if (getItemBurnCenser(item) >= 3) {
                    level.playSound(null, getBlockPos(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
                usedItems.remove(item.getItem());
            }
        }

        sortItems();

        if (effects.size() > 0) {
            for (MobEffectInstance effectInstance : effects) {
                player.addEffect(new MobEffectInstance(effectInstance));
            }

            int color = PotionUtils.getColor(effects);
            R = ColorUtils.getRed(color) / 255F;
            G = ColorUtils.getGreen(color) / 255F;
            B = ColorUtils.getBlue(color) / 255F;
        }

        Vec3 posSmoke = player.getEyePosition().add(player.getLookAngle().scale(0.75f));
        Vec3 vel = player.getEyePosition().add(player.getLookAngle().scale(40)).subtract(posSmoke).scale(1.0 / 20).normalize().scale(0.05f);
        PacketHandler.sendToTracking(level, player.getOnPos(), new SmokeEffectPacket((float) posSmoke.x, (float) posSmoke.y, (float) posSmoke.z, (float) vel.x, (float) vel.y, (float) vel.z, R, G, B));
        level.playSound(null, player.getOnPos(), WizardsReborn.STEAM_BURST_SOUND.get(), SoundSource.PLAYERS, 0.1f, 2.0f);

        PacketUtils.SUpdateTileEntityPacket(this);
    }

    public void sortItems() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (!getItem(i).isEmpty() && getItemBurnCenser(getItem(i)) < 3) {
                stacks.add(getItem(i).copy());
            }
        }
        for (int i = 0; i < 8; i++) {
            removeItem(i, 1);
        }

        for (int i = 0; i < stacks.size(); i++) {
            setItem(i, stacks.get(i));
        }
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(8) {
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
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            PacketUtils.SUpdateTileEntityPacket(this);
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("steam", steam);
        tag.putInt("cooldown", cooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        steam = tag.getInt("steam");
        cooldown = tag.getInt("cooldown");
    }

    @Override
    public int getSteam() {
        return steam;
    }

    @Override
    public int getMaxSteam() {
        return 5000;
    }

    @Override
    public void setSteam(int steam) {
        this.steam = steam;
    }

    @Override
    public void addSteam(int steam) {
        this.steam = this.steam + steam;
        if (this.steam > getMaxSteam()) {
            this.steam = getMaxSteam();
        }
    }

    @Override
    public void removeSteam(int steam) {
        this.steam = this.steam - steam;
        if (this.steam < 0) {
            this.steam = 0;
        }
    }

    @Override
    public boolean canSteamTransfer(Direction side) {
        return true;
    }

    @Override
    public boolean canSteamConnection(Direction side) {
        return side == Direction.DOWN;
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

    public float getBlockRotate() {
        switch (this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case NORTH:
                return 0F;
            case SOUTH:
                return 180F;
            case WEST:
                return 90F;
            case EAST:
                return 270F;
            default:
                return 0F;
        }
    }

    public static void setItemBurnCenser(ItemStack itemStack, int burn) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        nbt.putInt("burnCenser", burn);
    }

    public static int getItemBurnCenser(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getTag();
        if (nbt != null) {
            if (nbt.contains("burnCenser")) {
                return nbt.getInt("burnCenser");
            }
        }

        return 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return false;
    }
}
