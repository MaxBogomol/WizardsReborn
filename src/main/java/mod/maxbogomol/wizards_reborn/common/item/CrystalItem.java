package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalStat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.block.CrystalBlock;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class CrystalItem extends BlockItem implements IParticleItem {
    private static Random random = new Random();

    public CrystalItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    public CrystalType getType() {
        if (getBlock() instanceof CrystalBlock) {
            CrystalBlock block = (CrystalBlock) getBlock();
            return block.type;
        }

        return new CrystalType("");
    }

    public PolishingType getPolishing() {
        if (getBlock() instanceof CrystalBlock) {
            CrystalBlock block = (CrystalBlock) getBlock();
            return block.polishing;
        }

        return new PolishingType("", 0);
    }

    public static int getStatLevel(ItemStack stack, CrystalStat stat) {
        CompoundTag nbt = stack.getOrCreateTag();
        int statlevel = 0;
        if (nbt.contains(stat.getId())) {
            statlevel = nbt.getInt(stat.getId());
        }
        return statlevel;
    }

    public static ItemStack creativeTabRandomStats(Item item) {
        ItemStack stack = item.getDefaultInstance();
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putBoolean("random_stats", true);
        stack.setTag(nbt);
        return stack;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide()) {
            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("random_stats")) {
                nbt.remove("random_stats");
                CrystalUtils.createCrystalItemStats(stack, getType(), world, 6);
                stack.setTag(nbt);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        CrystalType type = getType();
        Color color = type.getColor();
        for (CrystalStat stat : type.getStats()) {
            int statlevel = getStatLevel(stack, stat);
            int red = (int) Mth.lerp((float) statlevel / stat.getMaxLevel(), Color.GRAY.getRed(), color.getRed());
            int green = (int) Mth.lerp((float) statlevel / stat.getMaxLevel(), Color.GRAY.getGreen(), color.getGreen());
            int blue = (int) Mth.lerp((float) statlevel / stat.getMaxLevel(), Color.GRAY.getBlue(), color.getBlue());

            int packColor = ColorUtils.packColor(255, red, green, blue);
            list.add(Component.translatable(stat.getTranslatedName()).append(": " + statlevel).withStyle(Style.EMPTY.withColor(packColor)));
        }
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("BlockEntityTag")) {
            CompoundTag tileNbt = new CompoundTag();
            NonNullList<ItemStack> ret = NonNullList.withSize(1, ItemStack.EMPTY);
            ret.set(0, stack);
            ContainerHelper.saveAllItems(tileNbt, ret);
            nbt.put("BlockEntityTag", tileNbt);
        }

        return BlockItem.updateCustomBlockEntityTag(level, player, pos, stack);
    }

    @Override
    public void addParticles(Level level, ItemEntity entity) {
        CrystalType type = getType();
        PolishingType polishing = getPolishing();

        if (random.nextFloat() < 0.01) {
            Color color = type.getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                    .setAlpha(0.5f, 0).setScale(0.1f, 0)
                    .setColor(r, g, b)
                    .setLifetime(30)
                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(level, entity.getX() + ((random.nextDouble() - 0.5D) * 0.25), entity.getY() + 0.25F + ((random.nextDouble() - 0.5D) * 0.25), entity.getZ() + ((random.nextDouble() - 0.5D) * 0.25));
        }

        if (polishing.hasParticle()) {
            if (random.nextFloat() < 0.01) {
                Color color = polishing.getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                        .setAlpha(0.5f, 0).setScale(0.1f, 0)
                        .setColor(r, g, b)
                        .setLifetime(30)
                        .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .spawn(level, entity.getX() + ((random.nextDouble() - 0.5D) * 0.25), entity.getY() + 0.125F + ((random.nextDouble() - 0.5D) * 0.25), entity.getZ() + ((random.nextDouble() - 0.5D) * 0.25));
            }
        }
    }
}