package mod.maxbogomol.wizards_reborn.common.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class ArcaneEnchantmentArgument implements ArgumentType<ArcaneEnchantment> {
    private static final DynamicCommandExceptionType UNKNOWN = new DynamicCommandExceptionType((obj) -> Component.translatable("commands.wizards_reborn.arcane_enchantment.unknown", obj));

    public static ArcaneEnchantment getArcaneEnchantments(final CommandContext<?> context, final String name) {
        return context.getArgument(name, ArcaneEnchantment.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        for (ArcaneEnchantment e : ArcaneEnchantments.getArcaneEnchantments())
            if (e.getId().startsWith(builder.getRemainingLowerCase()))
                builder.suggest(e.getId());
        return builder.buildFuture();
    }

    @Override
    public ArcaneEnchantment parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocation.read(reader);
        ArcaneEnchantment e = ArcaneEnchantments.getArcaneEnchantment(rl.toString());
        if (e == null) throw UNKNOWN.create(rl.toString());
        return e;
    }

    public static ArcaneEnchantmentArgument arcaneEnchantments() {
        return new ArcaneEnchantmentArgument();
    }
}