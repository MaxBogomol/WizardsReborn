package mod.maxbogomol.wizards_reborn.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.util.stream.Stream;

public class WizardsRebornCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("wizards_reborn")
                .requires(s -> s.hasPermission(2))
                .then(Commands.literal("knowledge")
                        .then(Commands.literal("give")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("knowledge", new KnowledgeArgument())
                                                .executes(ctx -> give(ctx, EntityArgument.getPlayer(ctx, "player"), KnowledgeArgument.getKnowledge(ctx, "knowledge")))
                                        )
                                        .then(Commands.literal("all")
                                                .executes(ctx -> giveAll(ctx, EntityArgument.getPlayer(ctx, "player"))))
                                )
                        )
                        .then(Commands.literal("remove")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("knowledge", new KnowledgeArgument())
                                                .executes(ctx -> remove(ctx, EntityArgument.getPlayer(ctx, "player"), KnowledgeArgument.getKnowledge(ctx, "knowledge")))
                                        )
                                        .then(Commands.literal("all")
                                                .executes(ctx -> removeAll(ctx, EntityArgument.getPlayer(ctx, "player"))))
                                )
                        )
                )
        );
    }

    private static int give(CommandContext<CommandSourceStack> command, ServerPlayer player, Knowledge knowledge) throws CommandSyntaxException {
        KnowledgeUtils.addKnowledge(player, knowledge);
        return Command.SINGLE_SUCCESS;
    }

    private static int remove(CommandContext<CommandSourceStack> command, ServerPlayer player, Knowledge knowledge) throws CommandSyntaxException {
        KnowledgeUtils.removeKnowledge(player, knowledge);
        return Command.SINGLE_SUCCESS;
    }

    private static int giveAll(CommandContext<CommandSourceStack> command, ServerPlayer player) throws CommandSyntaxException {
        KnowledgeUtils.addAllKnowledge(player);
        return Command.SINGLE_SUCCESS;
    }

    private static int removeAll(CommandContext<CommandSourceStack> command, ServerPlayer player) throws CommandSyntaxException {
        KnowledgeUtils.removeAllKnowledge(player);
        return Command.SINGLE_SUCCESS;
    }

    public static Stream<String> getAnStream() {
        Stream<String> anStream = Knowledges.getKnowledges().stream().map(Knowledge::getId);

        Stream<String> newStream = Stream.concat(Stream.of("all"), anStream);
        return newStream;
    }
}