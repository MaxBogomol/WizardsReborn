package mod.maxbogomol.wizards_reborn.common.mobeffect;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemType;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.client.shader.postprocess.WissenAuraPostProcess;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;

import java.util.List;

public class WissenAuraMobEffect extends MobEffect {

    public WissenAuraMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x9fc9e7);
        addAttributeModifier(WizardsRebornAttributes.WISSEN_DISCOUNT.get(), "3DB196CE-7DF0-4254-A014-BD7fB6DFC33A", 5, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(WizardsRebornAttributes.MAGIC_ARMOR.get(), "46A55828-4BB3-4326-88DA-3C31E593C315", 5, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide()) {
            if (livingEntity instanceof Player player) {
                List<ItemStack> itemsAdd = WissenUtil.getWissenItemsActive(player);

                int wissenRemain = 10 * (amplifier + 1);

                for (ItemStack item : itemsAdd) {
                    if (item.getItem() instanceof IWissenItem wissenItem) {
                        if (wissenItem.getWissenItemType() != WissenItemType.OFF) {
                            WissenItemUtil.existWissen(item);
                            int itemWissenRemain = WissenItemUtil.getAddWissenRemain(item, wissenRemain, wissenItem.getMaxWissen(item));
                            WissenItemUtil.addWissen(item, wissenRemain, wissenItem.getMaxWissen(item));

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

    @OnlyIn(Dist.CLIENT)
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!Minecraft.getInstance().isPaused()) {
                WissenAuraPostProcess.INSTANCE.tickEffect();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean hasEffect() {
        if (WizardsReborn.proxy.getPlayer() != null) {
            return WizardsReborn.proxy.getPlayer().hasEffect(WizardsRebornMobEffects.WISSEN_AURA.get());
        }
        return false;
    }
}
