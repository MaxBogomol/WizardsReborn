package mod.maxbogomol.wizards_reborn.common.mobeffect;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class MagicSensibilityMobEffect extends MobEffect {

    public MagicSensibilityMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xfbb3b0);
        addAttributeModifier(WizardsRebornAttributes.MAGIC_ARMOR.get(), "79015AD9-0823-456E-AF1E-84905978D18C", -10, AttributeModifier.Operation.ADDITION);
    }
}
