package mod.maxbogomol.wizards_reborn.common.mobeffect;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ArcaneStrengthMobEffect extends MobEffect {

    public ArcaneStrengthMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xfbb3b0);
        addAttributeModifier(WizardsRebornAttributes.ARCANE_DAMAGE.get(), "2BBE949F-A715-43CC-92C5-BF737E538DF2", 2, AttributeModifier.Operation.ADDITION);
    }
}
