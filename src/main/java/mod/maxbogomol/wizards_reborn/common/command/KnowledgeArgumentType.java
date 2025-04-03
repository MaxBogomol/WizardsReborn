package mod.maxbogomol.wizards_reborn.common.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class KnowledgeArgumentType implements ArgumentType<Knowledge> {
    private static final DynamicCommandExceptionType UNKNOWN = new DynamicCommandExceptionType((obj) -> Component.translatable("commands.wizards_reborn.knowledge.unknown", obj));

    public static Knowledge getKnowledge(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Knowledge.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        for (Knowledge knowledge : KnowledgeHandler.getKnowledges()) {
            if (knowledge.getId().startsWith(builder.getRemainingLowerCase())) builder.suggest(knowledge.getId());
        }
        return builder.buildFuture();
    }

    @Override
    public Knowledge parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocation.read(reader);
        Knowledge knowledge = KnowledgeHandler.getKnowledge(resourceLocation.toString());
        if (knowledge == null) throw UNKNOWN.create(resourceLocation.toString());
        return knowledge;
    }

    public static KnowledgeArgumentType knowledges() {
        return new KnowledgeArgumentType();
    }
}