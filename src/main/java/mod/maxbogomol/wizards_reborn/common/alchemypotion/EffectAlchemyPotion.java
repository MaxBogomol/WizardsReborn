package mod.maxbogomol.wizards_reborn.common.alchemypotion;

import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.Arrays;

public class EffectAlchemyPotion extends AlchemyPotion {
    public EffectAlchemyPotion(String id, MobEffectInstance... effects) {
        super(id);
        this.effects = new ArrayList<>(Arrays.asList(effects));;
    }
}
