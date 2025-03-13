package mod.maxbogomol.wizards_reborn.common.crystalritual;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpriteParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.ItemParticleOptions;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualArea;
import mod.maxbogomol.wizards_reborn.common.block.arcane_pedestal.ArcanePedestalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.crystalritual.CrystalInfusionBurstPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.CrystalInfusionRecipe;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrystalInfusionCrystalRitual extends CrystalRitual {

    public CrystalInfusionCrystalRitual(String id) {
        super(id);
    }

    @Override
    public Color getColor() {
        return new Color(190, 68, 201);
    }

    @Override
    public CrystalRitualArea getArea(CrystalBlockEntity crystal) {
        return new CrystalRitualArea(4, 4, 4, 4, 4, 4);
    }

    @Override
    public boolean canStart(CrystalBlockEntity crystal) {
        if (!crystal.getLevel().isClientSide()) {
            Optional<CrystalInfusionRecipe> recipe = getRecipe(crystal);
            return recipe.isPresent();
        }

        return false;
    }

    @Override
    public void start(CrystalBlockEntity crystal) {
        Level level = crystal.getLevel();
        BlockPos blockPos = crystal.getBlockPos();

        if (!crystal.getLevel().isClientSide()) {
            ItemStack item = getCrystalItem(crystal);

            Optional<CrystalInfusionRecipe> recipe = getRecipe(crystal);

            if (recipe.isPresent()) {
                clearItemHandler(crystal);
                setMaxCooldown(crystal, getMaxRitualCooldown(recipe.get().getLight(), item) + 20);
                setCooldown(crystal, getMaxCooldown(crystal));

                CrystalRitualArea area = getArea(crystal);
                List<ArcanePedestalBlockEntity> pedestals = getPedestalsWithArea(level, crystal.getBlockPos(), area);
                List<ItemStack> items = getItemsFromPedestals(pedestals);
                Container container = getItemHandler(crystal);
                if (container != null) {
                    for (int i = 0; i < items.size(); i++) {
                        container.setItem(i, items.get(i));
                    }
                    updateRunicPedestal(crystal);
                    deleteItemsFromPedestals(level, blockPos, pedestals, true, true);
                }
            }
        }
    }

    @Override
    public void tick(CrystalBlockEntity crystal) {
        Level level = crystal.getLevel();
        BlockPos blockPos = crystal.getBlockPos();

        if (!level.isClientSide()) {
            if (getCooldown(crystal) == 20) {
                Container container = getItemHandler(crystal);

                if (container != null) {
                    int size = getInventorySize(container);
                    SimpleContainer inv = new SimpleContainer(size);
                    for (int i = 0; i < size; i++) {
                        inv.setItem(i, container.getItem(i));
                    }

                    Optional<CrystalInfusionRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.CRYSTAL_INFUSION.get(), inv, level);

                    if (recipe.isPresent()) {
                        clearItemHandler(crystal);
                        container.setItem(0, recipe.get().getResultItem(RegistryAccess.EMPTY).copy());
                        updateRunicPedestal(crystal);
                        level.playSound(WizardsReborn.proxy.getPlayer(), blockPos, WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));

                        Color color = Color.WHITE;
                        ItemStack item = getCrystalItem(crystal);
                        if (!item.isEmpty()) {
                            color = getCrystalColor(item);
                        }

                        WizardsRebornPacketHandler.sendToTracking(level, blockPos, new CrystalInfusionBurstPacket(blockPos, color));
                    }
                }
            }

            if (getCooldown(crystal) > 0) {
                setCooldown(crystal, getCooldown(crystal) - 1);
                if (random.nextFloat() < 0.5) {
                    level.playSound(WizardsReborn.proxy.getPlayer(), blockPos, WizardsRebornSounds.SPELL_BURST.get(), SoundSource.BLOCKS, 0.25f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                }
            }
        }

        if (level.isClientSide()) {
            Container container = getItemHandler(crystal);

            if (container != null && getCooldown(crystal) > 20) {
                int size = getInventorySize(container);
                float rotate = 360f / (size);

                Color color = Color.WHITE;
                ItemStack stack = getCrystalItem(crystal);
                if (!stack.isEmpty()) {
                    color = getCrystalColor(stack);
                }

                for (int i = 0; i < size; i++) {
                    double ticks = (ClientTickHandler.ticksInGame) * 2;
                    double ticksUp = (ClientTickHandler.ticksInGame) * 4;
                    ticksUp = (ticksUp) % 360;

                    double yaw = Math.toRadians(rotate * i + ticks);
                    double pitch = 90;

                    float distance = 1.5f;

                    double X = Math.sin(pitch) * Math.cos(yaw) * distance;
                    double Y = Math.cos(pitch);
                    double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

                    double v = Math.sin(Math.toRadians(ticksUp + (rotate * i))) * 0.0625F;

                    if (random.nextFloat() < 0.2f) {
                        ParticleBuilder.create(FluffyFurParticles.WISP)
                                .setColorData(ColorParticleData.create(color).build())
                                .setTransparencyData(GenericParticleData.create(0.2f, 0.35f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                                .setScaleData(GenericParticleData.create(0.2f, 0.3f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                                .setLifetime(30)
                                .addVelocity(-X / 20, -Y / 10, -Z / 20)
                                .spawn(level, blockPos.getX() + 0.5F + X, blockPos.getY() + 0.5F + v, blockPos.getZ() + 0.5F + Z);
                    }

                    if (random.nextFloat() < 0.125f) {
                        boolean square = random.nextBoolean();
                        ParticleBuilder.create(square ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                                .setColorData(ColorParticleData.create(color).build())
                                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                .setScaleData(GenericParticleData.create(0, square ? 0.1f : 0.2f, 0).setEasing(Easing.SINE_IN_OUT).build())
                                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                                .setLifetime(30)
                                .randomOffset(0.05f)
                                .addVelocity(-X / 20, -Y / 10, -Z / 20)
                                .spawn(level, blockPos.getX() + 0.5F + X, blockPos.getY() + 0.5F + v, blockPos.getZ() + 0.5F + Z);
                    }

                    if (random.nextFloat() < 0.1f) {
                        ItemParticleOptions options = new ItemParticleOptions(FluffyFurParticles.ITEM.get(), container.getItem(i));
                        ParticleBuilder.create(options)
                                .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
                                .setColorData(ColorParticleData.create(Color.WHITE).build())
                                .setTransparencyData(GenericParticleData.create(0.2f, 0.5f, 0).setEasing(Easing.EXPO_IN, Easing.ELASTIC_OUT).build())
                                .setScaleData(GenericParticleData.create(0.025f, 0.05f, 0).setEasing(Easing.EXPO_IN, Easing.ELASTIC_OUT).build())
                                .setSpinData(SpinParticleData.create().randomSpin(0.2f).build())
                                .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                                .setLifetime(50)
                                .randomOffset(0.05f)
                                .addVelocity(-X / 30, -Y / 20, -Z / 30)
                                .spawn(level, blockPos.getX() + 0.5F + X, blockPos.getY() + 0.5F + v, blockPos.getZ() + 0.5F + Z);
                    }
                }

                if (random.nextFloat() < 0.3) {
                    double X = ((random.nextDouble() - 0.5D) * 0.5);
                    double Z = ((random.nextDouble() - 0.5D) * 0.5);
                    ParticleBuilder.create(WizardsRebornParticles.KARMA)
                            .setColorData(ColorParticleData.create(0.733f, 0.564f, 0.937f).build())
                            .setTransparencyData(GenericParticleData.create(0.1f, 0.5f, 0).setEasing(Easing.EXPO_IN, Easing.QUINTIC_IN_OUT).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.EXPO_IN, Easing.QUINTIC_IN_OUT).build())
                            .setLifetime(25)
                            .randomOffset(0.05f)
                            .addVelocity(-(X / 100), (random.nextDouble() / 20), -(Z / 100))
                            .spawn(level, blockPos.getX() + 0.5F + X, blockPos.getY() + 0.5625F, blockPos.getZ() + 0.5F + Z);
                }
            }
        }
    }

    @Override
    public boolean canEnd(CrystalBlockEntity crystal) {
        Level level = crystal.getLevel();

        if (!level.isClientSide()) {
            if (getCooldown(crystal) <= 0) {
                return true;
            }

            Optional<CrystalInfusionRecipe> recipe = getRecipe(crystal);
            if (!recipe.isPresent()) {
                return false;
            }
        }

        return false;
    }

    @Override
    public void end(CrystalBlockEntity crystal) {
        Level level = crystal.getLevel();
        BlockPos blockPos = crystal.getBlockPos();

        if (!level.isClientSide()) {
            Container container = getItemHandler(crystal);

            if (container != null) {
                level.addFreshEntity(new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 1.25F, blockPos.getZ() + 0.5F, container.getItem(0).copy()));
                level.playSound(WizardsReborn.proxy.getPlayer(), blockPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f,1f);
            }

            clearItemHandler(crystal);
        }
    }

    public Optional<CrystalInfusionRecipe> getRecipe(CrystalBlockEntity crystal, List<ItemStack> items) {
        Level level = crystal.getLevel();
        SimpleContainer inv = new SimpleContainer(items.size());
        for (int i = 0; i < items.size(); i++) {
            inv.setItem(i, items.get(i));
        }

        Optional<CrystalInfusionRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.CRYSTAL_INFUSION.get(), inv, level);
        return recipe;
    }

    public Optional<CrystalInfusionRecipe> getRecipe(CrystalBlockEntity crystal) {
        CrystalRitualArea area = getArea(crystal);
        Level level = crystal.getLevel();
        List<ArcanePedestalBlockEntity> pedestals = getPedestalsWithArea(level, crystal.getBlockPos(), area);
        List<ItemStack> items = getItemsFromPedestals(pedestals);

        return getRecipe(crystal, items);
    }

    @Override
    public List<ItemStack> getItemsResult(CrystalBlockEntity crystal) {
        List<ItemStack> list = new ArrayList<>();
        Optional<CrystalInfusionRecipe> recipe = getRecipe(crystal);

        if (recipe.isPresent()) {
            ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
            list.add(stack);
        }

        return list;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(CrystalBlockEntity crystal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Minecraft minecraft = Minecraft.getInstance();
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        Container container = getItemHandler(crystal);

        if (container != null && getCooldown(crystal) > 20) {
            int size = getInventorySize(container);
            float rotate = 360f / (size);

            for (int i = 0; i < size; i++) {
                ms.pushPose();
                ms.translate(0.5F, 0.5F, 0.5F);
                ms.translate(0F, (float) Math.sin(Math.toRadians(ticksUp + (rotate * i))) * 0.0625F, 0F);
                ms.mulPose(Axis.YP.rotationDegrees((float) -ticks + ((i - 1) * rotate)));
                ms.translate(1.5F, 0F, 0F);
                ms.mulPose(Axis.YP.rotationDegrees(90f));
                ms.scale(0.5F, 0.5F, 0.5F);
                minecraft.getItemRenderer().renderStatic(container.getItem(i), ItemDisplayContext.FIXED, light, overlay, ms, buffers, crystal.getLevel(), 0);
                ms.popPose();
            }
        }

        if (container != null && getCooldown(crystal) < 20) {
            ms.pushPose();
            ms.translate(0.5F, 1.5F, 0.5F);
            ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
            ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
            ms.scale(0.5F, 0.5F, 0.5F);
            minecraft.getItemRenderer().renderStatic(container.getItem(0), ItemDisplayContext.FIXED, light, overlay, ms, buffers, crystal.getLevel(), 0);
            ms.popPose();
        }
    }
}
