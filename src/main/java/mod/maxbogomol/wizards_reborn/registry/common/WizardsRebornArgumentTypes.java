package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.command.ArcaneEnchantmentArgument;
import mod.maxbogomol.wizards_reborn.common.command.KnowledgeArgument;
import mod.maxbogomol.wizards_reborn.common.command.SpellArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornArgumentTypes {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARG_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, WizardsReborn.MOD_ID);

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> KNOWLEDGE_ARG = ARG_TYPES.register("knowledge", () -> ArgumentTypeInfos.registerByClass(KnowledgeArgument.class, SingletonArgumentInfo.contextFree(KnowledgeArgument::knowledges)));
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> SPELLS_ARG = ARG_TYPES.register("spell", () -> ArgumentTypeInfos.registerByClass(SpellArgument.class, SingletonArgumentInfo.contextFree(SpellArgument::spells)));
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> ARCANE_ENCHANTMENT_ARG = ARG_TYPES.register("arcane_enchantment", () -> ArgumentTypeInfos.registerByClass(ArcaneEnchantmentArgument.class, SingletonArgumentInfo.contextFree(ArcaneEnchantmentArgument::arcaneEnchantments)));

    public static void register(IEventBus eventBus) {
        ARG_TYPES.register(eventBus);
    }
}
