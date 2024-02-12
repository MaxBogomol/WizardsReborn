package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ArcaneIteratorTileEntity extends BlockEntity implements TickableBlockEntity, IWissenTileEntity, ICooldownTileEntity, IWissenWandFunctionalTileEntity, IItemResultTileEntity {
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

                if ((wissenInCraft > 0) && (wissen > 0) && (startCraft) && canCraft) {
                    int addRemainCraft = WissenUtils.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInCraft);
                    int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft);

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
                                player.hurt(new DamageSource(player.damageSources().magic().typeHolder()), 1f);
                            }
                        }
                    }

                    update = true;
                }

                if (wissenInCraft > 0 && startCraft && canCraft) {
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
                        }

                        if (recipe.get().hasRecipeArcaneEnchantment()) {
                            ArcaneEnchantment enchantment = recipe.get().getRecipeArcaneEnchantment();
                            ArcaneEnchantmentUtils.addItemArcaneEnchantment(stack, enchantment);
                        }

                        int ii = 0;
                        for (int i = 0; i < pedestals.size(); i++) {
                            if (!pedestals.get(i).getItemHandler().getItem(0).isEmpty()) {
                                if (pedestals.get(i).getItemHandler().getItem(0).hasCraftingRemainingItem()) {
                                    pedestals.get(i).getItemHandler().setItem(0, pedestals.get(i).getItemHandler().getItem(0).getCraftingRemainingItem());
                                } else {
                                    pedestals.get(i).getItemHandler().removeItemNoUpdate(0);
                                }
                                PacketUtils.SUpdateTileEntityPacket(pedestals.get(i));
                                CompoundTag tagBlock = new CompoundTag();
                                tagBlock.putInt("x", pedestals.get(i).getBlockPos().getX());
                                tagBlock.putInt("y", pedestals.get(i).getBlockPos().getY());
                                tagBlock.putInt("z", pedestals.get(i).getBlockPos().getZ());
                                tagPos.put(String.valueOf(ii), tagBlock);
                                ii++;
                                level.playSound(WizardsReborn.proxy.getPlayer(), pedestals.get(i).getBlockPos(), WizardsReborn.WISSEN_TRANSFER_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                            }
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

                if (experienceTick == 10) {
                    Player player = getPlayer();
                    if (player != null) {
                        bursts.add(new ArcaneIteratorBurst(level, (float) player.getX(), (float) player.getY() + (player.getEyeHeight() / 2), (float) player.getZ(),
                                getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F, 0.05f, 20, 200,
                                0F, 1F, 0F));
                    }
                }

                if (healthTick == 5) {
                    Player player = getPlayer();
                    if (player != null) {
                        bursts.add(new ArcaneIteratorBurst(level, (float) player.getX(), (float) player.getY() + (player.getEyeHeight() / 2), (float) player.getZ(),
                                getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F, 0.05f, 20, 200,
                                1F, 0F, 0F));
                    }
                }

                if (wissenInCraft > 0 && startCraft && wissenIsCraft < wissenInCraft) {
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
                        if (!pedestal.getItemHandler().getItem(0).isEmpty()) {
                            if (random.nextFloat() < 0.025) {
                                bursts.add(new ArcaneIteratorBurst(level, pedestal.getBlockPos().getX() + 0.5F, pedestal.getBlockPos().getY() + 1.3F, pedestal.getBlockPos().getZ() + 0.5F,
                                        getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F, 0.05f, 20, 200,
                                        random.nextFloat(), random.nextFloat(), random.nextFloat()));
                                level.playSound(WizardsReborn.proxy.getPlayer(), pedestal.getBlockPos(), WizardsReborn.WISSEN_TRANSFER_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                            }
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

                if (WissenUtils.isCanRenderWissenWand()) {
                    Color borderColor = new Color(191, 201, 104);
                    WissenUtils.connectBoxEffect(level, new Vec3(getBlockPos().getX() - 5, getBlockPos().getY() - 3, getBlockPos().getZ() - 5), new Vec3(getBlockPos().getX() + 6, getBlockPos().getY() + 4, getBlockPos().getZ() + 6), borderColor);
                }
            } else {
                if (offset > 0) {
                    offset--;
                }
                if (scale > 0) {
                    scale--;
                }

                if (WissenUtils.isCanRenderWissenWand()) {
                    WissenUtils.connectBlockEffect(level, getBlockPos().below(2), new Color(214, 118, 132));
                    WissenUtils.connectBlockEffect(level, getBlockPos(), new Color(214, 118, 132));
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

        for (int x = -5; x <= 5; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -5; z <= 5; z++) {
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
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
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
            list.addAll(getItemsResult(recipe));
        }

        return list;
    }

    public List<ItemStack> getItemsResult(Optional<ArcaneIteratorRecipe> recipe) {
        List<ItemStack> list = new ArrayList<>();
        if (isWorks()) {
            List<ArcanePedestalTileEntity> pedestals = getPedestals();
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
                        if (ArcaneEnchantmentUtils.canAddItemArcaneEnchantment(stack, enchantment)) {
                            ArcaneEnchantmentUtils.addItemArcaneEnchantment(stack, enchantment);
                        }
                    }
                    list.add(stack);
                } else {
                    ArcanePedestalTileEntity pedestal = getMainPedestal();
                    if (!pedestal.getItemHandler().getItem(0).isEmpty() && (recipe.get().hasRecipeEnchantment() || recipe.get().hasRecipeArcaneEnchantment())) {
                        ItemStack stack = pedestal.getItemHandler().getItem(0).copy();
                        boolean canEnchant = false;
                        if (recipe.get().hasRecipeEnchantment()) {
                            Enchantment enchantment = recipe.get().getRecipeEnchantment();
                            if (canEnchant(stack, enchantment)) {
                                canEnchant = true;
                                enchant(stack, enchantment);
                            }
                        }
                        if (recipe.get().hasRecipeArcaneEnchantment()) {
                            ArcaneEnchantment enchantment = recipe.get().getRecipeArcaneEnchantment();
                            if (ArcaneEnchantmentUtils.canAddItemArcaneEnchantment(stack, enchantment)) {
                                canEnchant = true;
                                ArcaneEnchantmentUtils.addItemArcaneEnchantment(stack, enchantment);
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

    public boolean canCraft(Optional<ArcaneIteratorRecipe> recipe) {
        return getItemsResult(recipe).size() > 0;
    }
}
