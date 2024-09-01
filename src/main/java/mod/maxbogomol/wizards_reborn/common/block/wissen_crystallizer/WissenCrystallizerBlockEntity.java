package mod.maxbogomol.wizards_reborn.common.block.wissen_crystallizer;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.client.sound.WissenCrystallizerSoundInstance;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.WissenCrystallizerBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenCrystallizerRecipe;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WissenCrystallizerBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity, IWissenBlockEntity, ICooldownBlockEntity, IWissenWandFunctionalBlockEntity, IItemResultBlockEntity {

    public int wissenInCraft= 0;
    public int wissenIsCraft = 0;
    public boolean startCraft = false;

    public int wissen = 0;

    public WissenCrystallizerSoundInstance sound;

    public WissenCrystallizerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WissenCrystallizerBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.WISSEN_CRYSTALLIZER.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            Container container = getItemHandler();
            if (!container.isEmpty()) {
                Optional<WissenCrystallizerRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.WISSEN_CRYSTALLIZER.get(), getItemHandler(), level);
                wissenInCraft = recipe.map(WissenCrystallizerRecipe::getRecipeWissen).orElse(0);

                if (sortItems()) update = true;

                if (wissenInCraft <= 0) {
                    wissenIsCraft = 0;
                    startCraft = false;
                    update = true;
                }

                if ((wissenInCraft > 0) && (wissen > 0) && (startCraft)) {
                    if (wissenIsCraft == 0) {
                        level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.WISSEN_CRYSTALLIZER_START.get(), SoundSource.BLOCKS, 1f, 1f);
                    }
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

                        ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
                        if (recipe.get().getRecipeIsNBTCrystal()) {
                            CrystalUtil.createCrystalFromFractured(stack, getItemHandler());
                        }
                        if (recipe.get().getRecipeIsSaveNBT()) {
                            stack.setTag(getItemHandler().getItem(0).getOrCreateTag());
                        }

                        for (int i = 0; i < getItemHandler().getContainerSize(); i++) {
                            getItemHandler().removeItem(i, 1);
                        }

                        int count = recipe.get().getResultItem(RegistryAccess.EMPTY).getCount();
                        if (count > getItemHandler().getContainerSize()) {
                            count = getItemHandler().getContainerSize();
                        }

                        for (int i = 0; i < count; i++) {
                            getItemHandler().setItem(i, stack.copy());
                        }

                        update = true;

                        PacketHandler.sendToTracking(level, getBlockPos(), new WissenCrystallizerBurstEffectPacket(getBlockPos()));
                        level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.WISSEN_CRYSTALLIZER_END.get(), SoundSource.BLOCKS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                    }
                }
            } else if (wissenInCraft != 0 || startCraft) {
                wissenInCraft = 0;
                wissenIsCraft = 0;
                startCraft = false;
                update = true;
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (getWissen() > 0) {
                if (random.nextFloat() < 0.5) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.3f * getStage(), 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.015f)
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.125F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .randomSpin(0.5f)
                            .setLifetime(30)
                            .randomVelocity(0.015f)
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.125F, worldPosition.getZ() + 0.5F);
                }
            }

            if (wissenInCraft > 0 && startCraft) {
                if (random.nextFloat() < 0.2) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f * getStage(), 0).build())
                            .randomSpin(0.5f)
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.125F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0.025f * getStage(), 0.05f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .randomSpin(0.1f)
                            .setLifetime(65)
                            .randomVelocity(0.0125f)
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.125F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.3) {
                    ParticleBuilder.create(WizardsRebornParticles.KARMA)
                            .setColorData(ColorParticleData.create(0.733f, 0.564f, 0.937f).build())
                            .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f * getStage(), 0).build())
                            .setLifetime(10)
                            .randomVelocity(0.015f)
                            .flatRandomOffset(0.25f, 0.25f, 0.25f)
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.125F, worldPosition.getZ() + 0.5F);
                }

                if (sound == null) {
                    sound = WissenCrystallizerSoundInstance.getSound(this);
                    sound.playSound();
                } else if (sound.isStopped()) {
                    sound = WissenCrystallizerSoundInstance.getSound(this);
                    sound.playSound();
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

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("wissenInCraft", wissenInCraft);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putBoolean("startCraft", startCraft);
        tag.putInt("wissen", wissen);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        wissenInCraft = tag.getInt("wissenInCraft");
        wissenIsCraft = tag.getInt("wissenIsCraft");
        startCraft = tag.getBoolean("startCraft");
        wissen = tag.getInt("wissen");
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
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
            }
        }

        return size;
    }

    @Override
    public void wissenWandFunction() {
        startCraft = true;
    }

    @Override
    public float getCooldown() {
        if (wissenInCraft > 0) {
            return (float) wissenInCraft / wissenIsCraft;
        }
        return 0;
    }


    @Override
    public List<ItemStack> getItemsResult() {
        List<ItemStack> list = new ArrayList<>();
        Optional<WissenCrystallizerRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.WISSEN_CRYSTALLIZER.get(), getItemHandler(), level);
        wissenInCraft =  recipe.map(WissenCrystallizerRecipe::getRecipeWissen).orElse(0);

        if (recipe.isPresent() && wissenInCraft > 0) {
            ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
            if (recipe.get().getRecipeIsSaveNBT()) {
                stack.setTag(getItemHandler().getItem(0).copy().getOrCreateTag());
            }
            list.add(stack);
        }

        return list;
    }

    public boolean sortItems() {
        List<ItemStack> stacks = new ArrayList<>();

        if (getInventorySize() > 0) {
            if (getItem(0).isEmpty()) {
                for (int i = 0; i < 11; i++) {
                    if (!getItem(i).isEmpty()) {
                        stacks.add(getItem(i).copy());
                    }
                }
                for (int i = 0; i < 11; i++) {
                    removeItem(i, 1);
                }

                for (int i = 0; i < stacks.size(); i++) {
                    setItem(i, stacks.get(i));
                }

                return true;
            }
        }

        return false;
    }
}
