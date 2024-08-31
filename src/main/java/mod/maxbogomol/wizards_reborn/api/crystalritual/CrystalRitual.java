package mod.maxbogomol.wizards_reborn.api.crystalritual;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalStat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;
import mod.maxbogomol.wizards_reborn.common.block.arcane_pedestal.ArcanePedestalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.runic_pedestal.RunicPedestalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.crystalritual.CrystalRitualBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.CrystalRitualRecipe;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class CrystalRitual {
    public String id;
    public Random random = new Random();

    public CrystalRitual(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return new Color(200, 200, 200);
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String spellId = id.substring(i + 1);
        return "crystal_ritual."  + modId + "." + spellId;
    }

    public List<CrystalType> getCrystalsList() {
        List<CrystalType> list = new ArrayList<>();

        return list;
    }

    public int getMinimalPolishingLevel() {
        return 0;
    }

    public CrystalRitualArea getArea(CrystalBlockEntity crystal) {
        return new CrystalRitualArea(3, 3, 3, 3, 3, 3);
    }

    public boolean hasArea(CrystalBlockEntity crystal) {
        return true;
    }

    public boolean hasLightRay(CrystalBlockEntity crystal) {
        return false;
    }

    public int getStatLevel(CrystalBlockEntity crystal, CrystalStat stat) {
        ItemStack item = getCrystalItem(crystal);
        if (!item.isEmpty()) {
            return CrystalItem.getStatLevel(item, stat);
        }
        return 0;
    }

    public int getMaxRitualCooldown(CrystalBlockEntity crystal) {
        return 0;
    }

    public int getMaxRitualCooldown(int cooldown, ItemStack item) {
        int resonanceLevel = CrystalItem.getStatLevel(item, WizardsReborn.RESONANCE_CRYSTAL_STAT);
        return (int) (cooldown * (1 - (getCooldownStatModifier() * resonanceLevel)));
    }

    public int getMaxRitualCooldownWithStat(CrystalBlockEntity crystal) {
        ItemStack item = getCrystalItem(crystal);
        if (!item.isEmpty()) {
            return getMaxRitualCooldown(getMaxRitualCooldown(crystal), item);
        }
        return getMaxRitualCooldown(crystal);
    }

    public float getCooldownStatModifier() {
        return 0.15f;
    }

    public void setCooldown(CrystalBlockEntity crystal, int cooldown) {
        crystal.cooldown = cooldown;
    }

    public int getCooldown(CrystalBlockEntity crystal) {
        return crystal.cooldown;
    }

    public void setMaxCooldown(CrystalBlockEntity crystal, int cooldown) {
        crystal.maxCooldown = cooldown;
    }

    public int getMaxCooldown(CrystalBlockEntity crystal) {
        return crystal.maxCooldown;
    }

    public float getCrystalCooldown(CrystalBlockEntity crystal) {
        if (crystal.cooldown > 0) {
            return (float) crystal.maxCooldown / (float) crystal.cooldown;
        }
        return 0;
    }

    public void updateCrystal(CrystalBlockEntity crystal) {
        BlockEntityUpdate.packet(crystal);
    }

    public boolean canStart(CrystalBlockEntity crystal) {
        return true;
    }

    public boolean canTick(CrystalBlockEntity crystal) {
        return true;
    }

    public boolean canEnd(CrystalBlockEntity crystal) {
        return false;
    }

    public void start(CrystalBlockEntity crystal) {

    }

    public void tick(CrystalBlockEntity crystal) {

    }

    public void end(CrystalBlockEntity crystal) {

    }

    public ItemStack getCrystalItem(CrystalBlockEntity crystal) {
        return crystal.getCrystalItem();
    }

    public CrystalType getCrystalType(ItemStack crystal) {
        if (crystal.getItem() instanceof CrystalItem crystalItem) {
            return crystalItem.getType();
        }
        return null;
    }

    public PolishingType getCrystalPolishingType(ItemStack crystal) {
        if (crystal.getItem() instanceof CrystalItem crystalItem) {
            return crystalItem.getPolishing();
        }
        return null;
    }

    public Color getCrystalColor(ItemStack crystal) {
        CrystalType type = getCrystalType(crystal);
        if (type != null) {
            return type.getColor();
        }
        return new Color(255, 255, 255);
    }

    public static ArrayList<BlockPos> getBlockPosWithArea(Level level, BlockPos startPos, Vec3 sizeFrom, Vec3 sizeTo, Predicate<BlockPos> filter, boolean hasRandomOffset, boolean hasLimit, int limit) {
        ArrayList<BlockPos> blockPosList = new ArrayList<>();
        int count = 0;
        boolean reachLimit = false;

        float xOffset = 0;
        float yOffset = 0;
        float zOffset = 0;

        if (hasRandomOffset) {
            Random random = new Random();
            xOffset = (float) (random.nextFloat() * (sizeFrom.x() + sizeTo.x()));
            yOffset = (float) (random.nextFloat() * (sizeFrom.y() + sizeTo.y()));
            zOffset = (float) (random.nextFloat() * (sizeFrom.z() + sizeTo.z()));
        }

        for (double x = -sizeFrom.x(); x <= sizeTo.x(); x++) {
            for (double y = -sizeFrom.y(); y <= sizeTo.y(); y++) {
                for (double z = -sizeFrom.z(); z <= sizeTo.z(); z++) {
                    BlockPos pos = new BlockPos(new BlockPos(startPos.getX() + Mth.floor(x), startPos.getY() + Mth.floor(y), startPos.getZ() + Mth.floor(z)));
                    if (hasRandomOffset) {
                        float X = (float) (((xOffset + x + sizeFrom.x()) % (sizeFrom.x() + sizeTo.x() + 1)) - sizeFrom.x());
                        float Y = (float) (((yOffset + y + sizeFrom.y()) % (sizeFrom.y() + sizeTo.y() + 1)) - sizeFrom.y());
                        float Z = (float) (((zOffset + z + sizeFrom.z()) % (sizeFrom.z() + sizeTo.z() + 1)) - sizeFrom.z());
                        pos = new BlockPos(new BlockPos(startPos.getX() + Mth.floor(X), startPos.getY() + Mth.floor(Y), startPos.getZ() + Mth.floor(Z)));
                    }
                    if (level.isLoaded(pos)) {
                        if (filter.test(pos)) {
                            blockPosList.add(pos);
                            count++;
                            if (limit >= count && hasLimit) {
                                reachLimit = true;
                                break;
                            }
                        }
                    }
                }
                if (reachLimit) break;
            }
            if (reachLimit) break;
        }
        return blockPosList;
    }

    public static ArrayList<BlockPos> getBlockPosWithArea(Level level, BlockPos startPos, CrystalRitualArea area, Predicate<BlockPos> filter, boolean hasRandomOffset, boolean hasLimit, int limit) {
        return getBlockPosWithArea(level, startPos, area.getSizeFrom(), area.getSizeTo(), filter, hasRandomOffset, hasLimit, limit);
    }

    public static List<ArcanePedestalBlockEntity> getPedestalsWithArea(Level level, BlockPos startPos, Vec3 sizeFrom, Vec3 sizeTo) {
        List<ArcanePedestalBlockEntity> pedestals = new ArrayList<>();

        for (double x = -sizeFrom.x(); x <= sizeTo.x(); x++) {
            for (double y = -sizeFrom.y(); y <= sizeTo.y(); y++) {
                for (double z = -sizeFrom.z(); z <= sizeTo.z(); z++) {
                    BlockPos pos = new BlockPos(new BlockPos(startPos.getX() + Mth.floor(x), startPos.getY() + Mth.floor(y), startPos.getZ() + Mth.floor(z)));
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (blockEntity != null) {
                        if (blockEntity instanceof ArcanePedestalBlockEntity pedestal) {
                            pedestals.add(pedestal);
                        }
                    }
                }
            }
        }

        return pedestals;
    }

    public static List<ArcanePedestalBlockEntity>  getPedestalsWithArea(Level level, BlockPos startPos, CrystalRitualArea area) {
        return getPedestalsWithArea(level, startPos, area.getSizeFrom(), area.getSizeTo());
    }

    public static List<ItemStack> getItemsFromPedestals(List<ArcanePedestalBlockEntity> pedestals) {
        List<ItemStack> items = new ArrayList<>();
        for (ArcanePedestalBlockEntity pedestal : pedestals) {
            if (!pedestal.getItemHandler().getItem(0).isEmpty()) {
                items.add(pedestal.getItemHandler().getItem(0));
            }
        }

        return items;
    }

    public boolean canActivateWithItems(CrystalBlockEntity crystal, CrystalRitualArea area) {
        Level level = crystal.getLevel();
        List<ArcanePedestalBlockEntity> pedestals = getPedestalsWithArea(level, crystal.getBlockPos(), area);
        List<ItemStack> items = getItemsFromPedestals(pedestals);
        SimpleContainer inv = new SimpleContainer(items.size());
        for (int i = 0; i < items.size(); i++) {
            inv.setItem(i, items.get(i));
        }

        Optional<CrystalRitualRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.CRYSTAL_RITUAL_RECIPE.get(), inv, level);
        if (recipe.isPresent()) {
            return (recipe.get().getRecipeRitual() == this);
        }

        return false;
    }

    public static void deleteItemsFromPedestals(Level level, BlockPos startPos, List<ArcanePedestalBlockEntity> pedestals, boolean hasSound, boolean hasEffect) {
        CompoundTag tagPos = new CompoundTag();
        Random random = new Random();

        int ii = 0;
        for (int i = 0; i < pedestals.size(); i++) {
            if (!pedestals.get(i).getItemHandler().getItem(0).isEmpty()) {
                pedestals.get(i).getItemHandler().removeItemNoUpdate(0);
                BlockEntityUpdate.packet(pedestals.get(i));

                if (hasSound) {
                    level.playSound(WizardsReborn.proxy.getPlayer(), pedestals.get(i).getBlockPos(), WizardsReborn.WISSEN_TRANSFER_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                }

                if (hasEffect) {
                    CompoundTag tagBlock = new CompoundTag();
                    tagBlock.putInt("x", pedestals.get(i).getBlockPos().getX());
                    tagBlock.putInt("y", pedestals.get(i).getBlockPos().getY());
                    tagBlock.putInt("z", pedestals.get(i).getBlockPos().getZ());
                    tagPos.put(String.valueOf(ii), tagBlock);
                    ii++;
                }
            }
        }

        if (hasEffect) {
            PacketHandler.sendToTracking(level, startPos, new CrystalRitualBurstEffectPacket(tagPos));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void render(CrystalBlockEntity crystal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {

    }

    public static Container getItemHandler(CrystalBlockEntity crystal) {
        if (crystal.getLevel().getBlockEntity(crystal.getBlockPos().below()) instanceof RunicPedestalBlockEntity pedestal) {
            return pedestal.getItemHandler();
        }
        return null;
    }

    public static void updateRunicPedestal(CrystalBlockEntity crystal) {
        if (crystal.getLevel().getBlockEntity(crystal.getBlockPos().below()) != null) {
            BlockEntityUpdate.packet(crystal.getLevel().getBlockEntity(crystal.getBlockPos().below()));
        }
    }

    public static void clearItemHandler(CrystalBlockEntity crystal) {
        Container container = getItemHandler(crystal);
        if (container != null) {
            for (int i = 0; i < container.getContainerSize(); i++) {
                container.removeItemNoUpdate(i);
            }
            updateRunicPedestal(crystal);
        }
    }

    public int getInventorySize(Container container) {
        int size = 0;

        for (int i = 0; i < container.getContainerSize(); i++) {
            if (!container.getItem(i).isEmpty()) {
                size++;
            }
        }

        return size;
    }

    public List<ItemStack> getItemsResult(CrystalBlockEntity crystal) {
        List<ItemStack> list = new ArrayList<>();

        return list;
    }

    public boolean canStartWithCrystal(CrystalBlockEntity crystal) {
        List<CrystalType> list = getCrystalsList();

        if (getCrystalsList().isEmpty()) return true;
        ItemStack stack = crystal.getCrystalItem();

        if (stack != null) {
            for (CrystalType type : list) {
                if (stack.getItem() instanceof CrystalItem crystalItem) {
                    if (type == crystalItem.getType()) return true;
                }
            }
        }

        return false;
    }
}
