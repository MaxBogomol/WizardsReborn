package mod.maxbogomol.wizards_reborn.common.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class SpellArgumentType implements ArgumentType<Spell> {
    private static final DynamicCommandExceptionType UNKNOWN = new DynamicCommandExceptionType((obj) -> Component.translatable("commands.wizards_reborn.spell.unknown", obj));

    public static Spell getSpell(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Spell.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        for (Spell spell : SpellHandler.getSpells()) {
            if (spell.getId().startsWith(builder.getRemainingLowerCase())) builder.suggest(spell.getId());
        }
        return builder.buildFuture();
    }

    @Override
    public Spell parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocation.read(reader);
        Spell spell = SpellHandler.getSpell(resourceLocation.toString());
        if (spell == null) throw UNKNOWN.create(resourceLocation.toString());
        return spell;
    }

    public static SpellArgumentType spells() {
        return new SpellArgumentType();
    }
}