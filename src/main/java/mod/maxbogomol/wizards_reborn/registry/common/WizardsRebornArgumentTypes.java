package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.command.ArcaneEnchantmentArgumentType;
import mod.maxbogomol.wizards_reborn.common.command.KnowledgeArgumentType;
import mod.maxbogomol.wizards_reborn.common.command.SpellArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornArgumentTypes {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, WizardsReborn.MOD_ID);

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> KNOWLEDGE = COMMAND_ARGUMENT_TYPES.register("knowledge", () -> ArgumentTypeInfos.registerByClass(KnowledgeArgumentType.class, SingletonArgumentInfo.contextFree(KnowledgeArgumentType::knowledges)));
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> SPELLS = COMMAND_ARGUMENT_TYPES.register("spell", () -> ArgumentTypeInfos.registerByClass(SpellArgumentType.class, SingletonArgumentInfo.contextFree(SpellArgumentType::spells)));
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> ARCANE_ENCHANTMENT = COMMAND_ARGUMENT_TYPES.register("arcane_enchantment", () -> ArgumentTypeInfos.registerByClass(ArcaneEnchantmentArgumentType.class, SingletonArgumentInfo.contextFree(ArcaneEnchantmentArgumentType::arcaneEnchantments)));

    public static void register(IEventBus eventBus) {
        COMMAND_ARGUMENT_TYPES.register(eventBus);
    }
}
