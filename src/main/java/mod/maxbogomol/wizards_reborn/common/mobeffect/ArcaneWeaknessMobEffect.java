package mod.maxbogomol.wizards_reborn.common.mobeffect;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ArcaneWeaknessMobEffect extends MobEffect {

    public ArcaneWeaknessMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xfbb3b0);
        addAttributeModifier(WizardsRebornAttributes.ARCANE_DAMAGE.get(), "241C86D8-7BC9-4905-A981-88AB9E44E57D", -2, AttributeModifier.Operation.ADDITION);
    }
}
