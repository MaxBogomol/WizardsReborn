package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment.MagicBladePacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
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

    private static final Random random = new Random();

    public MagicBladeArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(110, 78, 169);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (ArcaneEnchantmentUtil.isArcaneItem(stack)) {
            return ArcaneEnchantmentUtil.getArcaneEnchantmentTypes(stack).contains(ArcaneEnchantmentTypes.MELEE_WEAPON);
        }
        return false;
    }

    public static void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide()) {
            if (attacker instanceof Player player) {

                if (ArcaneEnchantmentUtil.isArcaneItem(stack)) {
                    int enchantmentLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.MAGIC_BLADE);

                    if (enchantmentLevel > 0 && random.nextFloat() < (getChanceDefault(stack) + ((enchantmentLevel - 1) * getChancePerLevel(stack)))) {
                        float costModifier = WissenUtil.getWissenCostModifierWithDiscount(player);
                        List<ItemStack> items = WissenUtil.getWissenItemsNoneAndStorage(WissenUtil.getWissenItemsCurios(player));
                        int wissen = WissenUtil.getWissenInItems(items);
                        int cost = (int) (40 + ((enchantmentLevel - 1) * 15) * (1 - costModifier));
                        if (cost <= 0) {
                            cost = 1;
                        }

                        if (WissenUtil.canRemoveWissen(wissen, cost)) {
                            WissenUtil.removeWissenFromWissenItems(items, cost);
                            int invulnerableTime = target.invulnerableTime;
                            target.invulnerableTime = 0;
                            target.hurt(DamageHandler.create(player.level(), WizardsRebornDamageTypes.ARCANE_MAGIC, player, player), 1.0f * enchantmentLevel);
                            target.invulnerableTime = invulnerableTime;
                            target.level().playSound(WizardsReborn.proxy.getPlayer(), target.getOnPos(), WizardsRebornSounds.CRYSTAL_HIT.get(), SoundSource.PLAYERS, 1.3f, (float) (1.0f + ((random.nextFloat() - 0.5D) / 3)));
                            WizardsRebornPacketHandler.sendToTracking(target.level(), target.getOnPos(), new MagicBladePacket(target.position().add(0, target.getBbHeight() / 2f, 0)));
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
    public List<Component> modifierAppendHoverText(ItemStack stack, Level level, TooltipFlag flags) {
        List<Component> list = new ArrayList<>();
        int enchantmentLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, this);
        list.add(Component.literal(" +").append(String.valueOf(enchantmentLevel))
                .append(" ").append(Component.translatable("attribute.name.wizards_reborn.arcane_damage"))
                .append(" (").append(String.valueOf((getChanceDefault(stack) + ((enchantmentLevel - 1) * getChancePerLevel(stack))) * 100)).append("%)")
                .withStyle(ChatFormatting.DARK_GREEN));
        return list;
    }
}
