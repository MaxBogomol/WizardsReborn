package mod.maxbogomol.wizards_reborn.common.mobeffect;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class MagicResistanceMobEffect extends MobEffect {

    public MagicResistanceMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xfbb3b0);
        addAttributeModifier(WizardsRebornAttributes.MAGIC_ARMOR.get(), "AFB8E64A-CF9C-42AB-BABD-A752B65280AB", 10, AttributeModifier.Operation.ADDITION);
    }
}
