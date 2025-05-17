package mod.maxbogomol.wizards_reborn.common.item.food;

import mod.maxbogomol.wizards_reborn.common.item.PlacedItem;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.WizardsRebornFarmersDelight;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class JamItem extends PlacedItem {

    public int nourishmentTick = 0;
    public int nourishmentLevel = 0;
    public int comfortTick = 0;
    public int comfortLevel = 0;

    public int maxUses;
    public final Supplier<Item> bottle;

    public JamItem(Properties properties, int maxUses, Supplier<Item> bottle) {
        super(properties);
        this.maxUses = maxUses;
        this.bottle = bottle;
    }

    public JamItem setNourishmentTick(int nourishmentTick) {
        this.nourishmentTick = nourishmentTick;
        return this;
    }

    public JamItem setNourishmentLevel(int nourishmentLevel) {
        this.nourishmentLevel = nourishmentLevel;
        return this;
    }

    public JamItem setComfortTick(int comfortTick) {
        this.comfortTick = comfortTick;
        return this;
    }

    public JamItem setComfortLevel(int comfortLevel) {
        this.comfortLevel = comfortLevel;
        return this;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player) livingEntity : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
        }

        if (!level.isClientSide()) {
            livingEntity.eat(level, stack.copy());
            applyEffect(stack, level, livingEntity);
        }

        if (player != null) {
            stack = useJam(stack, level, player);
        }

        livingEntity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    public void applyEffect(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (WizardsRebornFarmersDelight.isLoaded()) {
            if (nourishmentTick > 0) {
                WizardsRebornFarmersDelight.LoadedOnly.addNourishmentEffect(livingEntity, nourishmentTick, nourishmentLevel);
            }
            if (comfortTick > 0) {
                WizardsRebornFarmersDelight.LoadedOnly.addComfortEffect(livingEntity, comfortTick, comfortLevel);
            }
        }
    }

    public ItemStack useJam(ItemStack stack, Level level, Player player) {
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            if (stack.getCount() > 1) {
                stack.shrink(1);
                ItemStack newStack = stack.copy();
                newStack.setCount(1);
                setUses(newStack, getUses(newStack) + 1);

                if (getUses(stack) == maxUses) {
                    newStack = new ItemStack(bottle.get());
                }

                player.getInventory().add(newStack);
            } else {
                setUses(stack, getUses(stack) + 1);
                if (getUses(stack) >= maxUses) {
                    stack.shrink(1);

                    if (stack.isEmpty()) {
                        return new ItemStack(bottle.get());
                    }

                    player.getInventory().add(new ItemStack(bottle.get()));
                }
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 48;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    public static int getUses(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("uses")) {
            nbt.putInt("uses", 0);
        }

        return nbt.getInt("uses");
    }

    public static void setUses(ItemStack stack, int uses) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("uses", uses);
    }

    public static ResourceLocation getModelTexture(ItemStack stack) {
        String string = stack.getDescriptionId();
        int i = string.indexOf(".");
        string = string.substring(i + 1);
        i = string.indexOf(".");
        String modId = string.substring(0, i);
        String jamId = string.substring(i + 1);
        return new ResourceLocation(modId, "textures/models/jam/" + jamId + ".png");
    }
}
