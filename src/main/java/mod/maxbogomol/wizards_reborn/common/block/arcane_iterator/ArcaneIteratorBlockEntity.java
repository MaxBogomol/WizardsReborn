package mod.maxbogomol.wizards_reborn.common.block.arcane_iterator;

import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.TrailParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.ItemParticleOptions;
import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.client.sound.ArcaneIteratorSoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.arcane_pedestal.ArcanePedestalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.ArcanePedestalsBurstPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneIteratorRecipe;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeBlockEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ArcaneIteratorBlockEntity extends BlockEntityBase implements TickableBlockEntity, IWissenBlockEntity, ICooldownBlockEntity, IWissenWandFunctionalBlockEntity, IItemResultBlockEntity {
    public int wissenInCraft= 0;
    public int wissenIsCraft = 0;
    public int experienceInCraft= 0;
    public int experienceIsCraft = 0;
    public int experienceTick = 0;
    public int healthInCraft= 0;
    public int healthIsCraft = 0;
    public int healthTick = 0;
    public boolean startCraft = false;
    public double angleA = 0;
    public double angleB = 0;

    public int wissen = 0;

    public int rotate = 0;
    public int offset = 0;
    public int scale = 0;

    public ArcaneIteratorSoundInstance sound;

    public ArcaneIteratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcaneIteratorBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.ARCANE_ITERATOR.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (isWorks()) {
                List<ArcanePedestalBlockEntity> pedestals = getPedestals();
                List<ItemStack> items = getItemsFromPedestals(pedestals);
                SimpleContainer inv = new SimpleContainer(items.size());
                for (int i = 0; i < items.size(); i++) {
                    inv.setItem(i, items.get(i));
                }

                if (!inv.isEmpty()) {
                    Optional<ArcaneIteratorRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.ARCANE_ITERATOR.get(), inv, level);
                    wissenInCraft = recipe.map(ArcaneIteratorRecipe::getRecipeWissen).orElse(0);
                    experienceInCraft = recipe.map(ArcaneIteratorRecipe::getRecipeExperience).orElse(0);
                    healthInCraft = recipe.map(ArcaneIteratorRecipe::getRecipeHealth).orElse(0);

                    boolean canCraft = canCraft(recipe);

                    if (wissenInCraft <= 0 && (wissenIsCraft > 0 || startCraft) || !canCraft) {
                        wissenIsCraft = 0;
                        experienceIsCraft = 0;
                        healthIsCraft = 0;
                        startCraft = false;

                        update = true;
                    }

                    if (experienceTick > 0) {
                        experienceTick--;

                        update = true;
                    }

                    if (healthTick > 0) {
                        healthTick--;

                        update = true;
                    }

                    if (wissenInCraft > 0 && wissen > 0 && startCraft) {
                        if (wissenIsCraft == 0) {
                            level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.ARCANE_ITERATOR_START.get(), SoundSource.BLOCKS, 1f, 1f);
                        }

                        int addRemainCraft = WissenUtil.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInCraft);
                        int removeRemain = WissenUtil.getRemoveWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft);

                        wissenIsCraft = wissenIsCraft + (getWissenPerTick() - addRemainCraft - removeRemain);
                        removeWissen(getWissenPerTick() - addRemainCraft - removeRemain);

                        if (experienceInCraft > 0 && experienceIsCraft < experienceInCraft && experienceTick == 0) {
                            Player player = getPlayer();
                            if (player != null) {
                                if (player.experienceLevel > 0) {
                                    experienceIsCraft++;
                                    experienceTick = 10;
                                    getPlayer().giveExperienceLevels(-1);
                                    level.playSound(WizardsReborn.proxy.getPlayer(), player.getOnPos(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                                }
                            }
                        }

                        if (healthInCraft > 0 && healthIsCraft < healthInCraft && healthTick == 0 && experienceTick == 0) {
                            Player player = getPlayer();
                            if (player != null) {
                                if (player.getHealth() > 0) {
                                    healthIsCraft++;
                                    healthTick = 10;
                                    player.hurt(DamageHandler.create(player.level(), WizardsRebornDamageTypes.ARCANE_MAGIC), 1f);
                                }
                            }
                        }

                        update = true;
                    }

                    if (wissenInCraft > 0 && startCraft) {
                        if (wissenInCraft <= wissenIsCraft && experienceInCraft <= experienceIsCraft) {
                            wissenInCraft = 0;
                            wissenIsCraft = 0;
                            experienceIsCraft = 0;
                            healthIsCraft = 0;
                            startCraft = false;

                            CompoundTag tagPos = new CompoundTag();
                            ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
                            if (!stack.isEmpty()) {
                                if (recipe.get().getRecipeIsSaveNBT()) {
                                    stack.setTag(items.get(0).getOrCreateTag());
                                }
                            } else {
                                stack = getMainPedestal().getItem(0).copy();
                            }

                            if (recipe.get().hasRecipeEnchantment()) {
                                Enchantment enchantment = recipe.get().getRecipeEnchantment();
                                if (canEnchant(stack, enchantment)) {
                                    enchant(stack, enchantment);
                                }

                                int bookEnchant = canEnchantBook(stack, enchantment);
                                if (bookEnchant >= 0) {
                                    if (stack.getItem().equals(Items.BOOK)) {
                                        stack = new ItemStack(Items.ENCHANTED_BOOK);
                                        EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(enchantment, bookEnchant + 1));
                                    } else if (stack.getItem().equals(Items.ENCHANTED_BOOK)) {
                                        EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(enchantment, bookEnchant + 1));
                                    }
                                }
                            }

                            if (recipe.get().hasRecipeArcaneEnchantment()) {
                                ArcaneEnchantment enchantment = recipe.get().getRecipeArcaneEnchantment();
                                ArcaneEnchantmentUtil.addItemArcaneEnchantment(stack, enchantment);
                            }

                            if (recipe.get().hasRecipeCrystalRitual()) {
                                CrystalRitual crystalRitual = recipe.get().getRecipeCrystalRitual();
                                CrystalRitualUtil.setCrystalRitual(stack, crystalRitual);
                            }

                            int ii = 0;
                            for (ArcanePedestalBlockEntity pedestal : pedestals) {
                                if (!pedestal.getItemHandler().getItem(0).isEmpty()) {
                                    if (pedestal.getItemHandler().getItem(0).hasCraftingRemainingItem()) {
                                        pedestal.getItemHandler().setItem(0, pedestal.getItemHandler().getItem(0).getCraftingRemainingItem());
                                    } else {
                                        pedestal.getItemHandler().removeItemNoUpdate(0);
                                    }
                                    BlockEntityUpdate.packet(pedestal);
                                    CompoundTag tagBlock = new CompoundTag();
                                    tagBlock.putInt("x", pedestal.getBlockPos().getX());
                                    tagBlock.putInt("y", pedestal.getBlockPos().getY());
                                    tagBlock.putInt("z", pedestal.getBlockPos().getZ());
                                    tagPos.put(String.valueOf(ii), tagBlock);
                                    ii++;
                                    level.playSound(WizardsReborn.proxy.getPlayer(), pedestal.getBlockPos(), WizardsRebornSounds.WISSEN_TRANSFER.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                                }
                            }
                            getMainPedestal().setItem(0, stack);
                            BlockEntityUpdate.packet(getMainPedestal());

                            WizardsRebornPacketHandler.sendToTracking(level, getBlockPos(), new ArcanePedestalsBurstPacket(tagPos));
                            level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.ARCANE_ITERATOR_END.get(), SoundSource.BLOCKS, 1f, 1f);
                        }
                    }
                } else if (wissenInCraft != 0 || startCraft) {
                    wissenIsCraft = 0;
                    startCraft = false;

                    update = true;
                }
            } else if (wissenInCraft != 0 || startCraft) {
                wissenIsCraft = 0;
                startCraft = false;

                update = true;
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (isWorks()) {
                rotate++;

                if (getWissen() > 0) {
                    if (random.nextFloat() < 0.5) {
                        ParticleBuilder.create(FluffyFurParticles.WISP)
                                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                .setScaleData(GenericParticleData.create(0.3f * getStage(), 0).build())
                                .setLifetime(20)
                                .randomVelocity(0.015f * getStage())
                                .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() - 0.7F, getBlockPos().getZ() + 0.5F);
                    }
                    if (random.nextFloat() < 0.1) {
                        ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                .setScaleData(GenericParticleData.create(0.05f * getStage(), 0.1f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                                .setLifetime(30)
                                .randomVelocity(0.015f * getStage())
                                .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() - 0.7F, getBlockPos().getZ() + 0.5F);
                    }
                }

                if (experienceTick == 10) {
                    Player player = getPlayer();
                    if (player != null) {
                        final Consumer<GenericParticle> blockTarget = p -> {
                            Vec3 blockPos = getBlockPos().getCenter();
                            Vec3 pos = p.getPosition();

                            double dX = blockPos.x() - pos.x();
                            double dY = blockPos.y() - pos.y();
                            double dZ = blockPos.z() - pos.z();

                            double yaw = Math.atan2(dZ, dX);
                            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                            float speed = 0.01f;
                            double x = Math.sin(pitch) * Math.cos(yaw) * speed;
                            double y = Math.cos(pitch) * speed;
                            double z = Math.sin(pitch) * Math.sin(yaw) * speed;

                            p.setSpeed(p.getSpeed().subtract(x, y, z));
                        };
                        ParticleBuilder.create(FluffyFurParticles.WISP)
                                .setColorData(ColorParticleData.create(0.784f, 1f, 0.560f).build())
                                .setTransparencyData(GenericParticleData.create(0.3f, 0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                                .setScaleData(GenericParticleData.create(0.05f, 0.15f, 0).setEasing(Easing.QUARTIC_OUT).build())
                                .addTickActor(blockTarget)
                                .setLifetime(100)
                                .randomVelocity(0.5f)
                                .disablePhysics()
                                .setFriction(0.9f)
                                .repeat(level, player.getX(), player.getY() + (player.getEyeHeight() / 2), player.getZ(), 5);
                    }
                }

                if (healthTick == 5) {
                    Player player = getPlayer();
                    if (player != null) {
                        final Consumer<GenericParticle> blockTarget = p -> {
                            Vec3 blockPos = getBlockPos().getCenter();
                            Vec3 pos = p.getPosition();

                            double dX = blockPos.x() - pos.x();
                            double dY = blockPos.y() - pos.y();
                            double dZ = blockPos.z() - pos.z();

                            double yaw = Math.atan2(dZ, dX);
                            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                            float speed = 0.01f;
                            double x = Math.sin(pitch) * Math.cos(yaw) * speed;
                            double y = Math.cos(pitch) * speed;
                            double z = Math.sin(pitch) * Math.sin(yaw) * speed;

                            p.setSpeed(p.getSpeed().subtract(x, y, z));
                        };
                        ParticleBuilder.create(FluffyFurParticles.WISP)
                                .setColorData(ColorParticleData.create(1f, 0, 0).build())
                                .setTransparencyData(GenericParticleData.create(0.3f, 0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                                .setScaleData(GenericParticleData.create(0.05f, 0.15f, 0).setEasing(Easing.QUARTIC_OUT).build())
                                .addTickActor(blockTarget)
                                .setLifetime(100)
                                .randomVelocity(0.5f)
                                .disablePhysics()
                                .setFriction(0.9f)
                                .repeat(level, player.getX(), player.getY() + (player.getEyeHeight() / 2), player.getZ(), 5);
                    }
                }

                if (wissenInCraft > 0 && startCraft && wissenIsCraft < wissenInCraft) {
                    if (offset < 40) {
                        offset++;
                    }
                    if (scale < 60) {
                        scale++;
                    }

                    if (random.nextFloat() < 0.255) particleRay(0.03f);
                    if (random.nextFloat() < 0.255) particleRay(-0.03f);
                    if (random.nextFloat() < 0.225) {
                        ParticleBuilder.create(FluffyFurParticles.WISP)
                                .setColorData(ColorParticleData.create(0.611f, 0.352f, 0.447f, 0.807f, 0.800f, 0.639f).build())
                                .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                                .setScaleData(GenericParticleData.create(0.4f, 0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.25f).build())
                                .setLifetime(30)
                                .randomVelocity(0.0125f)
                                .spawn(level, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f);
                    }

                    List<ArcanePedestalBlockEntity> pedestals = getPedestals();
                    for (ArcanePedestalBlockEntity pedestal : pedestals) {
                        if (!pedestal.getItemHandler().getItem(0).isEmpty()) {
                            if (pedestal != getMainPedestal()) {
                                if (random.nextFloat() < 0.025f) {
                                    final Consumer<GenericParticle> blockTarget = p -> {
                                        Vec3 blockPos = getBlockPos().getCenter();
                                        Vec3 pos = p.getPosition();

                                        double dX = blockPos.x() - pos.x();
                                        double dY = blockPos.y() - pos.y();
                                        double dZ = blockPos.z() - pos.z();

                                        double yaw = Math.atan2(dZ, dX);
                                        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                                        float speed = 0.01f;
                                        double x = Math.sin(pitch) * Math.cos(yaw) * speed;
                                        double y = Math.cos(pitch) * speed;
                                        double z = Math.sin(pitch) * Math.sin(yaw) * speed;

                                        p.setSpeed(p.getSpeed().subtract(x, y, z));
                                    };
                                    ParticleBuilder starBuilder = ParticleBuilder.create(FluffyFurParticles.STAR)
                                            .setColorData(ColorParticleData.create().setRandomColor().build())
                                            .setTransparencyData(GenericParticleData.create(0.6f, 0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                                            .setScaleData(GenericParticleData.create(0.15f).build())
                                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                                            .addTickActor(blockTarget)
                                            .setLifetime(150)
                                            .randomVelocity(0.5f)
                                            .disablePhysics()
                                            .setFriction(0.95f);
                                    ParticleBuilder.create(FluffyFurParticles.TRAIL)
                                            .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                                            .setBehavior(TrailParticleBehavior.create()
                                                    .setColorData(ColorParticleData.create().setRandomColor().build())
                                                    .setTransparencyData(GenericParticleData.create(1, 1, 0).setEasing(Easing.QUARTIC_OUT).build())
                                                    .enableSecondColor()
                                                    .setWidthFunction(RenderUtil.LINEAR_IN_SEMI_ROUND_WIDTH_FUNCTION)
                                                    .build())
                                            .setColorData(ColorParticleData.create().setRandomColor().build())
                                            .setTransparencyData(GenericParticleData.create(1, 1, 0).setEasing(Easing.QUARTIC_OUT).build())
                                            .setScaleData(GenericParticleData.create(0.5f).build())
                                            .addTickActor(blockTarget)
                                            .setLifetime(150)
                                            .randomVelocity(0.5f)
                                            .disablePhysics()
                                            .setFriction(0.95f)
                                            .addAdditionalBuilder(starBuilder)
                                            .spawn(level, pedestal.getBlockPos().getX() + 0.5f, pedestal.getBlockPos().getY() + 1.3f, pedestal.getBlockPos().getZ() + 0.5f);
                                    ParticleBuilder.create(FluffyFurParticles.SQUARE)
                                            .setColorData(ColorParticleData.create().setRandomColor().build())
                                            .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                                            .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                                            .setLifetime(30)
                                            .randomVelocity(0.035f)
                                            .repeat(level, pedestal.getBlockPos().getX() + 0.5f, pedestal.getBlockPos().getY() + 1.3f, pedestal.getBlockPos().getZ() + 0.5f, 5, 0.9f);
                                    level.playSound(WizardsReborn.proxy.getPlayer(), pedestal.getBlockPos(), WizardsRebornSounds.WISSEN_TRANSFER.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                                }
                                if (random.nextFloat() < 0.025f) {
                                    Vec3 to = getBlockPos().getCenter().add(0, -1.3f, 0);
                                    Vec3 from = pedestal.getBlockPos().getCenter().add(0, 0.7f, 0);

                                    double dX = to.x() - from.x();
                                    double dY = to.y() - from.y();
                                    double dZ = to.z() - from.z();
                                    float distance = (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2) + Math.pow(dZ, 2)) / 20f;

                                    double yaw = Math.atan2(dZ, dX);
                                    double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                                    double x = Math.sin(pitch) * Math.cos(yaw) * distance;
                                    double y = Math.cos(pitch) * distance;
                                    double z = Math.sin(pitch) * Math.sin(yaw) * distance;

                                    ItemParticleOptions options = new ItemParticleOptions(FluffyFurParticles.ITEM.get(), pedestal.getItemHandler().getItem(0));
                                    ParticleBuilder.create(options)
                                            .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
                                            .setColorData(ColorParticleData.create(Color.WHITE).build())
                                            .setTransparencyData(GenericParticleData.create(0.2f, 0.5f, 0).setEasing(Easing.EXPO_IN, Easing.ELASTIC_OUT).build())
                                            .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.EXPO_IN, Easing.ELASTIC_OUT).build())
                                            .setSpinData(SpinParticleData.create().randomSpin(0.2f).build())
                                            .setLifetime(20)
                                            .addVelocity(-x, -y, -z)
                                            .disablePhysics()
                                            .spawn(level, pedestal.getBlockPos().getX() + 0.5f, pedestal.getBlockPos().getY() + 1.3f, pedestal.getBlockPos().getZ() + 0.5f);
                                }
                            }
                        }
                    }

                    if (sound == null) {
                        sound = ArcaneIteratorSoundInstance.getSound(this);
                        sound.playSound();
                    } else if (sound.isStopped()) {
                        sound = ArcaneIteratorSoundInstance.getSound(this);
                        sound.playSound();
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

        ParticleBuilder.create(FluffyFurParticles.SMOKE)
                .setColorData(ColorParticleData.create(0.611f, 0.352f, 0.447f, 0.807f, 0.800f, 0.639f).build())
                .setTransparencyData(GenericParticleData.create(0.4f, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.5f, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.25f).build())
                .setLifetime(120)
                .addVelocity(vx, vy, vz)
                .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
    }

    public boolean isWorks() {
        BlockEntity blockEntity = level.getBlockEntity(getBlockPos().below().below());
        if (blockEntity != null) {
            return blockEntity instanceof ArcanePedestalBlockEntity;
        }

        return false;
    }

    public ArcanePedestalBlockEntity getMainPedestal() {
        BlockEntity blockEntity = level.getBlockEntity(getBlockPos().below().below());
        if (blockEntity != null) {
            if (blockEntity instanceof ArcanePedestalBlockEntity pedestal) {
                return pedestal;
            }
        }

        return null;
    }

    public List<ArcanePedestalBlockEntity> getPedestals() {
        List<ArcanePedestalBlockEntity> pedestals = new ArrayList<>();

        for (int x = -5; x <= 5; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -5; z <= 5; z++) {
                    BlockEntity blockEntity = level.getBlockEntity(new BlockPos(getBlockPos().getX() + x, getBlockPos().getY() + y, getBlockPos().getZ() + z));
                    if (blockEntity != null) {
                        if (blockEntity instanceof ArcanePedestalBlockEntity pedestal) {
                            pedestals.add(pedestal);
                        }
                    }
                }
            }
        }

        ArcanePedestalBlockEntity pedestal = getMainPedestal();
        if (pedestal != null) {
            if (pedestals.contains(pedestal)) {
                pedestals.remove(pedestal);
            }
            pedestals.add(0, pedestal);
        }

        return pedestals;
    }

    public List<ItemStack> getItemsFromPedestals(List<ArcanePedestalBlockEntity> pedestals) {
        List<ItemStack> items = new ArrayList<>();
        for (ArcanePedestalBlockEntity pedestal : pedestals) {
            if (!pedestal.getItemHandler().getItem(0).isEmpty()) {
                items.add(pedestal.getItemHandler().getItem(0));
            }
        }

        return items;
    }

    public List<ItemStack> getItemsFromPedestals() {
        return getItemsFromPedestals(getPedestals());
    }

    public Player getPlayer() {
        List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(getBlockPos()).inflate(4, 2, 4));
        Player minPlayer = null;
        if (players.size() > 0) {
            float minDistance = (float) Math.sqrt(players.get(0).distanceToSqr(getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F));
            minPlayer = players.get(0);

            for (Player player : players) {
                float distance = (float) Math.sqrt(player.distanceToSqr(getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F));

                if (distance < minDistance) {
                    minDistance = distance;
                    minPlayer = player;
                }
            }
        }

        return minPlayer;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("wissenInCraft", wissenInCraft);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putInt("experienceInCraft", experienceInCraft);
        tag.putInt("experienceIsCraft", experienceIsCraft);
        tag.putInt("experienceTick", experienceTick);
        tag.putInt("healthInCraft", healthInCraft);
        tag.putInt("healthIsCraft", healthIsCraft);
        tag.putInt("healthTick", healthTick);
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
        experienceInCraft = tag.getInt("experienceInCraft");
        experienceIsCraft = tag.getInt("experienceIsCraft");
        experienceTick = tag.getInt("experienceTick");
        healthInCraft = tag.getInt("healthInCraft");
        healthIsCraft = tag.getInt("healthIsCraft");
        healthTick = tag.getInt("healthTick");
        startCraft = tag.getBoolean("startCraft");
        angleA = tag.getDouble("angleA");
        angleB = tag.getDouble("angleB");
        wissen = tag.getInt("wissen");
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
    public void wissenWandFunction() {
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
            List<ArcanePedestalBlockEntity> pedestals = getPedestals();
            List<ItemStack> items = getItemsFromPedestals(pedestals);
            SimpleContainer inv = new SimpleContainer(items.size());
            for (int i = 0; i < items.size(); i++) {
                inv.setItem(i, items.get(i));
            }

            Optional<ArcaneIteratorRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.ARCANE_ITERATOR.get(), inv, level);
            list.addAll(getItemsResult(recipe));
        }

        return list;
    }

    public List<ItemStack> getItemsResult(Optional<ArcaneIteratorRecipe> recipe) {
        List<ItemStack> list = new ArrayList<>();
        if (isWorks()) {
            List<ArcanePedestalBlockEntity> pedestals = getPedestals();
            List<ItemStack> items = getItemsFromPedestals(pedestals);
            if (recipe.isPresent()) {
                if (!recipe.get().getResultItem(RegistryAccess.EMPTY).isEmpty()) {
                    ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
                    if (recipe.get().getRecipeIsSaveNBT()) {
                        stack.setTag(items.get(0).copy().getOrCreateTag());
                    }
                    if (recipe.get().hasRecipeEnchantment()) {
                        Enchantment enchantment = recipe.get().getRecipeEnchantment();
                        if (canEnchant(stack, enchantment)) {
                            enchant(stack, enchantment);
                        }
                    }
                    if (recipe.get().hasRecipeArcaneEnchantment()) {
                        ArcaneEnchantment enchantment = recipe.get().getRecipeArcaneEnchantment();
                        if (ArcaneEnchantmentUtil.canAddItemArcaneEnchantment(stack, enchantment)) {
                            ArcaneEnchantmentUtil.addItemArcaneEnchantment(stack, enchantment);
                        }
                    }
                    if (recipe.get().hasRecipeCrystalRitual()) {
                        CrystalRitual crystalRitual = recipe.get().getRecipeCrystalRitual();
                        CrystalRitualUtil.setCrystalRitual(stack, crystalRitual);
                    }
                    list.add(stack);
                } else {
                    ArcanePedestalBlockEntity pedestal = getMainPedestal();
                    if (!pedestal.getItemHandler().getItem(0).isEmpty() && (recipe.get().hasRecipeEnchantment() || recipe.get().hasRecipeArcaneEnchantment())) {
                        ItemStack stack = pedestal.getItemHandler().getItem(0).copy();
                        boolean canEnchant = false;
                        if (recipe.get().hasRecipeEnchantment()) {
                            Enchantment enchantment = recipe.get().getRecipeEnchantment();
                            if (canEnchant(stack, enchantment)) {
                                canEnchant = true;
                                enchant(stack, enchantment);
                            }
                            if (stack.getItem().equals(Items.BOOK) || stack.getItem().equals(Items.ENCHANTED_BOOK)) {
                                int bookEnchant = canEnchantBook(stack, enchantment);
                                if (bookEnchant >= 0) {
                                    if (stack.getItem().equals(Items.BOOK)) {
                                        canEnchant = true;
                                        stack = new ItemStack(Items.ENCHANTED_BOOK);
                                        EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(enchantment, bookEnchant + 1));
                                    } else if (stack.getItem().equals(Items.ENCHANTED_BOOK)) {
                                        EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(enchantment, bookEnchant + 1));
                                        canEnchant = true;
                                    }
                                }
                            }
                        }
                        if (recipe.get().hasRecipeArcaneEnchantment()) {
                            ArcaneEnchantment enchantment = recipe.get().getRecipeArcaneEnchantment();
                            if (ArcaneEnchantmentUtil.canAddItemArcaneEnchantment(stack, enchantment)) {
                                canEnchant = true;
                                ArcaneEnchantmentUtil.addItemArcaneEnchantment(stack, enchantment);
                            }
                        }
                        if (canEnchant) {
                            list.add(stack);
                        }
                    }
                }
            }
        }
        return list;
    }

    public static boolean canEnchant(ItemStack stack, Enchantment enchantment) {
        int enchantmentLevel = stack.getEnchantmentLevel(enchantment);
        int repairCost = stack.getBaseRepairCost();
        if (enchantmentLevel == 0) {
            repairCost++;
        }
        int xp = AnvilMenu.calculateIncreasedRepairCost(repairCost);
        return (enchantment.canEnchant(stack) && enchantmentLevel + 1 <= enchantment.getMaxLevel() && xp <= 60);
    }

    public static void enchant(ItemStack stack, Enchantment enchantment) {
        int enchantmentLevel = stack.getEnchantmentLevel(enchantment);
        if (enchantmentLevel == 0) {
            stack.enchant(enchantment, enchantmentLevel + 1);
            stack.setRepairCost(stack.getBaseRepairCost() + 1);
        } else {
            Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
            enchantments.put(enchantment, enchantmentLevel + 1);
            EnchantmentHelper.setEnchantments(enchantments, stack);
        }
    }

    public static int canEnchantBook(ItemStack stack, Enchantment enchantment) {
        ListTag listtag = EnchantedBookItem.getEnchantments(stack);
        boolean flag = true;
        ResourceLocation resourcelocation = EnchantmentHelper.getEnchantmentId(enchantment);

        for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            ResourceLocation resourcelocation1 = EnchantmentHelper.getEnchantmentId(compoundtag);
            if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
                if (EnchantmentHelper.getEnchantmentLevel(compoundtag) + 1 <= enchantment.getMaxLevel()) {
                    return EnchantmentHelper.getEnchantmentLevel(compoundtag);
                }

                flag = false;
                break;
            }
        }

        if (flag) return 0;

        return -1;
    }

    public boolean canCraft(Optional<ArcaneIteratorRecipe> recipe) {
        return getItemsResult(recipe).size() > 0;
    }
}
