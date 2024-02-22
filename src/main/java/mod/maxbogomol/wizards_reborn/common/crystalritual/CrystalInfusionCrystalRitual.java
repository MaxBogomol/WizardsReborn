package mod.maxbogomol.wizards_reborn.common.crystalritual;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualArea;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.crystalritual.CrystalInfusionBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.CrystalInfusionRecipe;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcanePedestalTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
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
    public CrystalRitualArea getArea(CrystalTileEntity crystal) {
        return new CrystalRitualArea(4, 4, 4, 4, 4, 4);
    }

    @Override
    public boolean canStart(CrystalTileEntity crystal) {
        if (!crystal.getLevel().isClientSide()) {
            Optional<CrystalInfusionRecipe> recipe = getRecipe(crystal);
            return recipe.isPresent();
        }

        return false;
    }

    @Override
    public void start(CrystalTileEntity crystal) {
        Level level = crystal.getLevel();
        BlockPos blockPos = crystal.getBlockPos();

        if (!crystal.getLevel().isClientSide()) {
            ItemStack item = getCrystalItem(crystal);

            Optional<CrystalInfusionRecipe> recipe = getRecipe(crystal);

            if (recipe.isPresent()) {
                clearItemHandler(crystal);
                setMaxCooldown(crystal, getMaxRitualCooldown(recipe.get().getRecipeLight(), item) + 20);
                setCooldown(crystal, getMaxCooldown(crystal));

                CrystalRitualArea area = getArea(crystal);
                List<ArcanePedestalTileEntity> pedestals = getPedestalsWithArea(level, crystal.getBlockPos(), area);
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
    public void tick(CrystalTileEntity crystal) {
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

                    Optional<CrystalInfusionRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.CRYSTAL_INFUSION_RECIPE.get(), inv, level);

                    if (recipe.isPresent()) {
                        clearItemHandler(crystal);
                        container.setItem(0, recipe.get().getResultItem(RegistryAccess.EMPTY).copy());
                        updateRunicPedestal(crystal);
                        level.playSound(WizardsReborn.proxy.getPlayer(), blockPos, WizardsReborn.WISSEN_BURST_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));

                        float r = 1f;
                        float g = 1f;
                        float b = 1f;

                        ItemStack item = getCrystalItem(crystal);
                        if (!item.isEmpty()) {
                            Color color = getCrystalColor(item);
                            r = (color.getRed() / 255f);
                            g = (color.getGreen() / 255f);
                            b = (color.getBlue() / 255f);
                        }

                        PacketHandler.sendToTracking(level, blockPos, new CrystalInfusionBurstEffectPacket(blockPos, r, g, b));
                    }
                }
            }

            if (getCooldown(crystal) > 0) {
                setCooldown(crystal, getCooldown(crystal) - 1);
                if (random.nextFloat() < 0.5) {
                    level.playSound(WizardsReborn.proxy.getPlayer(), blockPos, WizardsReborn.SPELL_BURST_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                }
            }
        }

        if (level.isClientSide()) {
            Container container = getItemHandler(crystal);

            if (container != null && getCooldown(crystal) > 20) {
                int size = getInventorySize(container);
                float rotate = 360f / (size);

                float r = 1f;
                float g = 1f;
                float b = 1f;

                ItemStack item = getCrystalItem(crystal);
                if (!item.isEmpty()) {
                    Color color = getCrystalColor(item);
                    r = (color.getRed() / 255f);
                    g = (color.getGreen() / 255f);
                    b = (color.getBlue() / 255f);
                }

                for (int i = 0; i < size; i++) {
                    if (random.nextFloat() < 1.0) {
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

                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(-X / 20, -Y / 10, -Z / 20)
                                .setAlpha(0.35f, 0).setScale(0.3f, 0)
                                .setColor(r, g, b)
                                .setLifetime(30)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(level, blockPos.getX() + 0.5F + X, blockPos.getY() + 0.5F + v, blockPos.getZ() + 0.5F + Z);

                        if (random.nextFloat() < 0.25) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(-X / 20, -Y / 10, -Z / 20)
                                    .setAlpha(0.25f, 0).setScale(0.3f, 0)
                                    .setColor(r, g, b)
                                    .setLifetime(30)
                                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(level, blockPos.getX() + 0.5F + X + ((random.nextDouble() - 0.5D) / 10), blockPos.getY() + 0.5F + v + ((random.nextDouble() - 0.5D) / 10), blockPos.getZ() + 0.5F + Z + ((random.nextDouble() - 0.5D) / 10));
                        }
                    }
                }

                if (random.nextFloat() < 0.3) {
                    double X = ((random.nextDouble() - 0.5D) * 0.5);
                    double Z = ((random.nextDouble() - 0.5D) * 0.5);
                    Particles.create(WizardsReborn.KARMA_PARTICLE)
                            .addVelocity(-(X / 100), (random.nextDouble() / 20), -(Z / 100))
                            .setAlpha(0.5f, 0).setScale(0.1f, 0.025f)
                            .setColor(0.733f, 0.564f, 0.937f)
                            .setLifetime(15)
                            .spawn(level, blockPos.getX() + 0.5F + X, blockPos.getY() + 0.5625F, blockPos.getZ() + 0.5F + Z);
                }
            }
        }
    }

    @Override
    public boolean canEnd(CrystalTileEntity crystal) {
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
    public void end(CrystalTileEntity crystal) {
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

    public Optional<CrystalInfusionRecipe> getRecipe(CrystalTileEntity crystal, List<ItemStack> items) {
        Level level = crystal.getLevel();
        SimpleContainer inv = new SimpleContainer(items.size());
        for (int i = 0; i < items.size(); i++) {
            inv.setItem(i, items.get(i));
        }

        Optional<CrystalInfusionRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.CRYSTAL_INFUSION_RECIPE.get(), inv, level);
        return recipe;
    }

    public Optional<CrystalInfusionRecipe> getRecipe(CrystalTileEntity crystal) {
        CrystalRitualArea area = getArea(crystal);
        Level level = crystal.getLevel();
        List<ArcanePedestalTileEntity> pedestals = getPedestalsWithArea(level, crystal.getBlockPos(), area);
        List<ItemStack> items = getItemsFromPedestals(pedestals);

        return getRecipe(crystal, items);
    }

    @Override
    public List<ItemStack> getItemsResult(CrystalTileEntity crystal) {
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
    public void render(CrystalTileEntity crystal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();
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
                mc.getItemRenderer().renderStatic(container.getItem(i), ItemDisplayContext.FIXED, light, overlay, ms, buffers, crystal.getLevel(), 0);
                ms.popPose();
            }
        }

        if (container != null && getCooldown(crystal) < 20) {
            ms.pushPose();
            ms.translate(0.5F, 1.5F, 0.5F);
            ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
            ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
            ms.scale(0.5F, 0.5F, 0.5F);
            mc.getItemRenderer().renderStatic(container.getItem(0), ItemDisplayContext.FIXED, light, overlay, ms, buffers, crystal.getLevel(), 0);
            ms.popPose();
        }
    }
}
