package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.fluffy_fur.client.tooltip.AttributeTooltipModifier;
import mod.maxbogomol.fluffy_fur.client.tooltip.TooltipModifierHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ScytheItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, WizardsReborn.MOD_ID);

    public static final RegistryObject<Attribute> WISSEN_DISCOUNT = ATTRIBUTES.register("wissen_discount", () -> new RangedAttribute("attribute.name.wizards_reborn.wissen_discount", 0, 0, 75).setSyncable(true));
    public static final RegistryObject<Attribute> MAGIC_ARMOR = ATTRIBUTES.register("magic_armor", () -> new RangedAttribute("attribute.name.wizards_reborn.magic_armor", 0, 0, 100).setSyncable(true));
    public static final RegistryObject<Attribute> MAGIC_MODIFIER = ATTRIBUTES.register("magic_modifier", () -> new RangedAttribute("attribute.name.wizards_reborn.magic_modifier", 0, 0, 100).setSyncable(true));
    public static final RegistryObject<Attribute> ARCANE_DAMAGE = ATTRIBUTES.register("arcane_damage", () -> new RangedAttribute("attribute.name.wizards_reborn.arcane_damage", 0, 0, 1000).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeModificationEvent event) {
            for (EntityType<? extends LivingEntity> livingEntity : event.getTypes()) {
                event.add(livingEntity, MAGIC_ARMOR.get());
                event.add(livingEntity, ARCANE_DAMAGE.get());
            }
            event.add(EntityType.PLAYER, WISSEN_DISCOUNT.get());
            event.add(EntityType.PLAYER, MAGIC_MODIFIER.get());
        }
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerAttributeModifiers(FMLClientSetupEvent event) {
            TooltipModifierHandler.register(new AttributeTooltipModifier() {
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag) {
                    return modifier.getId().equals(ScytheItem.BASE_ENTITY_REACH_UUID);
                }
            });
            TooltipModifierHandler.register(new AttributeTooltipModifier() {
                public boolean isModifiable(Attribute key, AttributeModifier modifier, Player player, TooltipFlag flag) {
                    return key.equals(WISSEN_DISCOUNT.get());
                }

                public ModifyResult modify(AttributeModifier modifier, double amount, AttributeModifier.Operation operation) {
                    operation = AttributeModifier.Operation.MULTIPLY_BASE;
                    amount = amount / 100f;
                    return new ModifyResult(modifier, amount, operation);
                }
            });
            TooltipModifierHandler.register(new AttributeTooltipModifier() {
                public boolean isModifiable(Attribute key, AttributeModifier modifier, Player player, TooltipFlag flag) {
                    return key.equals(MAGIC_ARMOR.get());
                }

                public ModifyResult modify(AttributeModifier modifier, double amount, AttributeModifier.Operation operation) {
                    operation = AttributeModifier.Operation.MULTIPLY_BASE;
                    amount = amount / 100f;
                    return new ModifyResult(modifier, amount, operation);
                }
            });
        }
    }
}
