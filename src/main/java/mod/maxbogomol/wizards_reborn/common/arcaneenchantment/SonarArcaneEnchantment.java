package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.List;
import java.util.*;

public class SonarArcaneEnchantment extends ArcaneEnchantment {
    public static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public SonarArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(194, 115, 103);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentType.PICKAXE);
        }
        return false;
    }

    public static void writeOres(Player player, ItemStack stack, int enchantmentLevel) {
        if (!player.level().isClientSide()) {
            int distance = enchantmentLevel + 2;
            Vec3 playerPos = player.position();
            Vec3 vec = player.getLookAngle().scale(distance).add(player.position());
            BlockPos center = BlockPos.containing(vec.x(), vec.y(), vec.z());

            List<BlockPos> oresPos = new ArrayList<>();
            Map<Block, Integer> ores = new HashMap<>();
            Map<Block, Float> dist = new HashMap<>();

            List<Map<Integer, Float>> oreId = new ArrayList<>();

            for (int x = -distance; x <= distance; x++) {
                for (int y = -distance; y <= distance; y++) {
                    for (int z = -distance; z <= distance; z++) {
                        BlockPos pos = center.offset(x, y, z);
                        BlockState blockState = player.level().getBlockState(pos);
                        if (blockState.is(WizardsRebornBlockTags.ORES)) {
                            oresPos.add(pos);
                        }
                    }
                }
            }

            for (BlockPos pos : oresPos) {
                BlockState blockState = player.level().getBlockState(pos);
                Block block = blockState.getBlock();
                if (ores.keySet().contains(block)) {
                    ores.put(block, ores.get(block) + 1);
                } else {
                    ores.put(block, 1);
                }

                float dst = (float) Math.sqrt(pos.distToCenterSqr(playerPos.x(), playerPos.y(), playerPos.z()));
                if (dist.keySet().contains(block)) {
                    if (dst < dist.get(block)) {
                        dist.put(block, dst);
                    }
                } else {
                    dist.put(block, dst);
                }
            }

            for (Block block : ores.keySet()) {
                int id = getBlockId(block);
                Map<Integer, Float> map = new HashMap<>();
                map.put(id, dist.get(block));
                if (ores.get(block) >= 30) {
                    oreId.add(map);
                    oreId.add(map);
                    oreId.add(map);
                } else if (ores.get(block) >= 15) {
                    oreId.add(map);
                    oreId.add(map);
                } else {
                    oreId.add(map);
                }
            }

            Collections.shuffle(oreId);

            if (oreId.isEmpty()) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_DIDGERIDOO.get(), SoundSource.PLAYERS, 1f, 1f);
            } else {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_CHIME.get(), SoundSource.PLAYERS, 1f, 1f);
            }

            setOres(stack, oreId);
            setCooldown(stack, 50);
            setTick(stack, 10);
        }
    }

    public static void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (getCooldown(stack) > 0) {
            setCooldown(stack, getCooldown(stack) - 1);
        }

        List<Map<Integer, Float>> ores = getOres(stack);

        if (getTick(stack) > 0 && !ores.isEmpty()) {
            setTick(stack, getTick(stack) - 1);

            if (getTick(stack) <= 0) {
                Map<Integer, Float> map = ores.get(0);
                int id = (Integer) map.keySet().toArray()[0];
                float distance = map.get((Integer) map.keySet().toArray()[0]);

                float volume = 1;
                if (distance > 0) {
                    volume = 1f - (distance / 11f);
                }
                if (volume < 0.1f) volume = 0.1f;
                playSound(entity.level(), entity.position(), volume, id);

                Vec3 pos = entity.getLookAngle().scale(1.5f).add(entity.getEyePosition());
                world.addParticle(ParticleTypes.NOTE, pos.x(), pos.y(), pos.z(), (id % 7) / 7f, 0.0D, 0.0D);
                setTick(stack, 10);

                ores.remove(0);
                setOres(stack, ores);
            }
        }
    }

    public static int getBlockId(Block block) {
        String string = block.getDescriptionId();

        int i = string.indexOf(".");
        string = string.substring(i + 1);
        i = string.indexOf(".");
        String modId = string.substring(0, i);
        String blockId = string.substring(i + 1);
        return getBlockId(blockId);
    }

    public static int getBlockId(String block) {
        block = block.toLowerCase();
        if (block.contains("deepslate_")) {
            block = block.replace("deepslate_", "");
        }
        if (block.contains("nether_")) {
            block = block.replace("nether_", "");
        }

        int id = 0;
        char first = block.toCharArray()[0];
        int i = alphabet.indexOf(first);
        if (i >= 0) id = i;

        return id;
    }

    public static void playSound(Level level, Vec3 pos, float volume, int id) {
        SoundEvent soundEvent = SoundEvents.NOTE_BLOCK_HARP.get();
        if (id >= 7 && id < 14) {
            soundEvent = SoundEvents.NOTE_BLOCK_HAT.get();
        }
        if (id >= 14 && id < 21) {
            soundEvent = SoundEvents.NOTE_BLOCK_PLING.get();
        }
        if (id >= 21) {
            soundEvent = SoundEvents.NOTE_BLOCK_SNARE.get();
        }

        level.playSound(null, pos.x(), pos.y(), pos.z(), soundEvent, SoundSource.PLAYERS, volume, (id % 7) / 7f * 2);
    }

    public static int getCooldown(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains(WizardsReborn.MOD_ID+":sonarCooldown")) {
            return nbt.getInt(WizardsReborn.MOD_ID+":sonarCooldown");
        }

        return 0;
    }

    public static void setCooldown(ItemStack stack, int cooldown) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt(WizardsReborn.MOD_ID+":sonarCooldown", cooldown);
    }

    public static int getTick(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains(WizardsReborn.MOD_ID+":sonarTick")) {
            return nbt.getInt(WizardsReborn.MOD_ID+":sonarTick");
        }

        return 0;
    }

    public static void setTick(ItemStack stack, int tick) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt(WizardsReborn.MOD_ID+":sonarTick", tick);
    }

    public static List<Map<Integer, Float>> getOres(ItemStack stack) {
        List<Map<Integer, Float>> list = new ArrayList<>();
        CompoundTag compound = stack.getOrCreateTag();

        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains(WizardsReborn.MOD_ID+":sonarOres")) {
            ListTag tagList = compound.getList(WizardsReborn.MOD_ID+":sonarOres", Tag.TAG_COMPOUND);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag tags = tagList.getCompound(i);
                int ore = tags.getInt("ore");
                float distance = tags.getFloat("distance");
                Map<Integer, Float> map = new HashMap<>();
                map.put(ore, distance);
                list.add(map);
            }
        }

        return list;
    }

    public static void setOres(ItemStack stack, List<Map<Integer, Float>> ores) {
        CompoundTag nbt = stack.getOrCreateTag();

        ListTag nbtTagList = new ListTag();
        for (Map<Integer, Float> map : ores) {
            CompoundTag tag = new CompoundTag();
            tag.putInt("ore", (Integer) map.keySet().toArray()[0]);
            tag.putFloat("distance", map.get((Integer) map.keySet().toArray()[0]));
            nbtTagList.add(tag);
        }
        nbt.put(WizardsReborn.MOD_ID+":sonarOres", nbtTagList);
    }
}
