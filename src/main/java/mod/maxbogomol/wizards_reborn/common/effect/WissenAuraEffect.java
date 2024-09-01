package mod.maxbogomol.wizards_reborn.common.effect;

import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemType;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class WissenAuraEffect extends MobEffect {
    public WissenAuraEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x9fc9e7);
        addAttributeModifier(WizardsRebornAttributes.WISSEN_DISCOUNT.get(), "3DB196CE-7DF0-4254-A014-BD7fB6DFC33A", 5, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(WizardsRebornAttributes.MAGIC_ARMOR.get(), "46A55828-4BB3-4326-88DA-3C31E593C315", 5, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide()) {
            if (livingEntity instanceof Player player) {
                List<ItemStack> itemsAdd = WissenUtils.getWissenItemsActive(player);

                int wissenRemain = 10 * (amplifier + 1);

                for (ItemStack item : itemsAdd) {
                    if (item.getItem() instanceof IWissenItem wissenItem) {
                        if (wissenItem.getWissenItemType() != WissenItemType.OFF) {
                            WissenItemUtil.existWissen(item);
                            int itemWissenRemain = WissenItemUtil.getAddWissenRemain(item, wissenRemain, wissenItem.getMaxWissen());
                            WissenItemUtil.addWissen(item, wissenRemain, wissenItem.getMaxWissen());

                            wissenRemain = itemWissenRemain;
                            if (wissenRemain <= 0) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 60 == 0;
    }
}
