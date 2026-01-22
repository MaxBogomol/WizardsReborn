package mod.maxbogomol.wizards_reborn.common.item.equipment.arcane;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.client.model.sniffalo.SniffaloArmorModel;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class SniffaloArmorItem extends Item implements IArcaneItem {
    public List<ArcaneEnchantmentType> arcaneEnchantmentTypes = new ArrayList<>();

    public static final ResourceLocation SNIFFALO_ARMOR_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/armor/arcane_armor.png");

    public SniffaloArmorItem(Properties properties) {
        super(properties);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.SNIFFALO_ARMOR);
    }

    @Override
    public List<ArcaneEnchantmentType> getArcaneEnchantmentTypes() {
        return arcaneEnchantmentTypes;
    }

    public SniffaloArmorItem addArcaneEnchantmentType(ArcaneEnchantmentType type) {
        arcaneEnchantmentTypes.add(type);
        return this;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof SniffaloEntity sniffalo && target.isAlive()) {
            if (!sniffalo.isArmored()) {
                sniffalo.setArmor(stack.copy());
                target.level().gameEvent(target, GameEvent.EQUIP, target.position());
                stack.shrink(1);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    public float getArmorValue(ItemStack stack) {
        return 0;
    }

    public float getMagicArmorValue(ItemStack stack) {
        return 0;
    }

    public float getArmorValue(SniffaloEntity sniffalo, ItemStack stack) {
        return getArmorValue(stack);
    }

    public float getMagicArmorValue(SniffaloEntity sniffalo, ItemStack stack) {
        return  getMagicArmorValue(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getSniffaloArmorTexture(ItemStack stack, SniffaloEntity entity) {
        return getSniffaloArmorTexture();
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getSniffaloArmorTexture(ItemStack stack) {
        return getSniffaloArmorTexture();
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getSniffaloArmorTexture() {
        return SNIFFALO_ARMOR_TEXTURE;
    }

    @OnlyIn(Dist.CLIENT)
    public SniffaloArmorModel getSniffaloArmorModel(ItemStack stack, SniffaloEntity entity) {
        return getSniffaloArmorModel();
    }

    @OnlyIn(Dist.CLIENT)
    public SniffaloArmorModel getSniffaloArmorModel(ItemStack stack) {
        return getSniffaloArmorModel();
    }

    @OnlyIn(Dist.CLIENT)
    public SniffaloArmorModel getSniffaloArmorModel() {
        return WizardsRebornModels.SNIFFALO_ARCANE_ARMOR;
    }
}
