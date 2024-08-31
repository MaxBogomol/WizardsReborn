package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.damage.DamageSourceRegistry;
import mod.maxbogomol.wizards_reborn.common.network.MagicBladeEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MagicBladeArcaneEnchantment extends ArcaneEnchantment {
    private static Random random = new Random();

    public MagicBladeArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(110, 78, 169);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentType.MELEE_WEAPON);
        }
        return false;
    }

    public static void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            if (attacker instanceof Player player) {

                if (ArcaneEnchantmentUtil.isArcaneItem(stack)) {
                    int enchantmentLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsReborn.MAGIC_BLADE_ARCANE_ENCHANTMENT);

                    if (enchantmentLevel > 0 && random.nextFloat() < (getChanceDefault(stack) + ((enchantmentLevel - 1) * getChancePerLevel(stack)))) {
                        float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
                        List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
                        int wissen = WissenUtils.getWissenInItems(items);
                        int cost = (int) (40 + ((enchantmentLevel - 1) * 15) * (1 - costModifier));
                        if (cost <= 0) {
                            cost = 1;
                        }

                        if (WissenUtils.canRemoveWissen(wissen, cost)) {
                            AttributeInstance attr = attacker.getAttribute(WizardsReborn.ARCANE_DAMAGE.get());
                            WissenUtils.removeWissenFromWissenItems(items, cost);
                            target.invulnerableTime = 0;
                            target.hurt(new DamageSource(DamageSourceRegistry.create(target.level(), DamageSourceRegistry.ARCANE_MAGIC).typeHolder(), player), (1.0f * enchantmentLevel) + (float) attr.getValue());
                            target.level().playSound(WizardsReborn.proxy.getPlayer(), target.getOnPos(), WizardsReborn.CRYSTAL_HIT_SOUND.get(), SoundSource.PLAYERS, 1.3f, (float) (1.0f + ((random.nextFloat() - 0.5D) / 3)));
                            PacketHandler.sendToTracking(target.level(), target.getOnPos(), new MagicBladeEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ()));
                        }
                    }
                }
            }
        }
    }

    public static float getChanceDefault(ItemStack stack) {
        return 0.35F;
    }

    public static float getChancePerLevel(ItemStack stack) {
        return 0.025F;
    }

    @OnlyIn(Dist.CLIENT)
    public List<Component> modifierAppendHoverText(ItemStack stack, Level world, TooltipFlag flags) {
        List<Component> list = new ArrayList<>();
        int enchantmentLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, this);
        list.add(Component.literal(" +").append(String.valueOf(enchantmentLevel))
                .append(" ").append(Component.translatable("attribute.name.wizards_reborn.arcane_damage"))
                .append(" (").append(String.valueOf((getChanceDefault(stack) + ((enchantmentLevel - 1) * getChancePerLevel(stack))) * 100)).append("%)")
                .withStyle(ChatFormatting.DARK_GREEN));
        return list;
    }
}
