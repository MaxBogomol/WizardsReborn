package mod.maxbogomol.wizards_reborn.common.block.arcane_censer;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownBlockEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SmokePacket;
import mod.maxbogomol.wizards_reborn.common.recipe.CenserRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArcaneCenserBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity, ISteamBlockEntity, ICooldownBlockEntity {
    public int steam = 0;
    public int cooldown = 0;

    public ArcaneCenserBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcaneCenserBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.ARCANE_CENSER.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (cooldown > 0) {
                cooldown = cooldown - 1;
                update = true;
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (cooldown > 0) {
                if (random.nextFloat() < 0.2F) {
                    ParticleBuilder.create(FluffyFurParticles.SMOKE)
                            .setColorData(ColorParticleData.create(Color.WHITE).build())
                            .setTransparencyData(GenericParticleData.create(0.1f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 3f).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                            .setLifetime(200, 100)
                            .randomVelocity(0.035f)
                            .addVelocity(0, 0.05f, 0)
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 1F, getBlockPos().getZ() + 0.5F);
                }
            }

            float amount = (float) getSteam() / (float) getMaxSteam();

            for (int i = 0; i < 2 * amount; i++) {
                if (random.nextFloat() < amount) {
                    ParticleBuilder.create(FluffyFurParticles.SMOKE)
                            .setColorData(ColorParticleData.create(Color.WHITE).build())
                            .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.3f).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                            .setLifetime(30)
                            .randomVelocity(0.015f)
                            .flatRandomOffset(0.025f, 0.075f, 0.025f)
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.375F, getBlockPos().getZ() + 0.5F);
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
            Optional<CenserRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.CENSER.get(), inv, level);
            if (recipe.isPresent()) {
                for (MobEffectInstance effectInstance : recipe.get().getEffects()) {
                    effects.add(new MobEffectInstance(effectInstance));
                }
                if (!usedItems.contains(getItem(i).getItem())) {
                    usedItems.add(getItem(i).getItem());
                }
            }
        }

        Color color = Color.WHITE;

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
            color = ColorUtil.getColor(PotionUtils.getColor(effects));
        }

        Vec3 posSmoke = player.getEyePosition().add(player.getLookAngle().scale(0.75f));
        Vec3 vel = player.getEyePosition().add(player.getLookAngle().scale(40)).subtract(posSmoke).scale(1.0 / 20).normalize().scale(0.05f);
        WizardsRebornPacketHandler.sendToTracking(level, player.getOnPos(), new SmokePacket(posSmoke, vel, color));
        level.playSound(null, player.getOnPos(), WizardsRebornSounds.STEAM_BURST.get(), SoundSource.PLAYERS, 0.1f, 2.0f);

        BlockEntityUpdate.packet(this);
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
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
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
        return switch (this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case NORTH -> 0F;
            case SOUTH -> 180F;
            case WEST -> 90F;
            case EAST -> 270F;
            default -> 0F;
        };
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

    @Override
    public float getCooldown() {
        if (cooldown > 0) {
            return (float) 100 / cooldown;
        }
        return 0;
    }
}
