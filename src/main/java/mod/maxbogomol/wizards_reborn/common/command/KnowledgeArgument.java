package mod.maxbogomol.wizards_reborn.common.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class KnowledgeArgument implements ArgumentType<Knowledge> {
    private static final DynamicCommandExceptionType UNKNOWN = new DynamicCommandExceptionType((obj) -> Component.translatable("wizards_reborn.arcanemicon.unknown", obj));

    public static Knowledge getKnowledge(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Knowledge.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        for (Knowledge s : Knowledges.getKnowledges())
            if (s.getId().startsWith(builder.getRemainingLowerCase()))
                builder.suggest(s.getId());
        return builder.buildFuture();
    }

    @Override
    public Knowledge parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocation.read(reader);
        System.out.println();
        Knowledge s = Knowledges.getKnowledge(rl.toString());
        if (s == null) throw UNKNOWN.create(rl.toString());
        return s;
    }

    public static KnowledgeArgument knowledges() {
        return new KnowledgeArgument();
    }
}