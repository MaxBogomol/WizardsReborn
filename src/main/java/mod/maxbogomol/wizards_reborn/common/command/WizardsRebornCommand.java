package mod.maxbogomol.wizards_reborn.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class WizardsRebornCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("wizards_reborn")
                .requires(s -> s.hasPermission(2))
                .then(Commands.literal("knowledge")
                        .then(Commands.literal("give")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("knowledge", new KnowledgeArgument())
                                                .executes(ctx -> giveKnowledge(ctx, EntityArgument.getPlayers(ctx, "player"), KnowledgeArgument.getKnowledge(ctx, "knowledge")))
                                        )
                                        .then(Commands.literal("all")
                                                .executes(ctx -> giveAllKnowledge(ctx, EntityArgument.getPlayers(ctx, "player")))
                                        )
                                )
                        )
                        .then(Commands.literal("remove")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("knowledge", new KnowledgeArgument())
                                                .executes(ctx -> removeKnowledge(ctx, EntityArgument.getPlayers(ctx, "player"), KnowledgeArgument.getKnowledge(ctx, "knowledge")))
                                        )
                                        .then(Commands.literal("all")
                                                .executes(ctx -> removeAllKnowledge(ctx, EntityArgument.getPlayers(ctx, "player")))
                                        )
                                )
                        )
                )
                .then(Commands.literal("spell")
                        .then(Commands.literal("give")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("spell", new SpellArgument())
                                                .executes(ctx -> giveSpell(ctx, EntityArgument.getPlayers(ctx, "player"), SpellArgument.getSpell(ctx, "spell")))
                                        )
                                        .then(Commands.literal("all")
                                                .executes(ctx -> giveAllSpell(ctx, EntityArgument.getPlayers(ctx, "player")))
                                        )
                                )
                        )
                        .then(Commands.literal("remove")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("spell", new SpellArgument())
                                                .executes(ctx -> removeSpell(ctx, EntityArgument.getPlayers(ctx, "player"), SpellArgument.getSpell(ctx, "spell")))
                                        )
                                        .then(Commands.literal("all")
                                                .executes(ctx -> removeAllSpell(ctx, EntityArgument.getPlayers(ctx, "player")))
                                        )
                                )
                        )
                )
                .then(Commands.literal("arcane_enchantment")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("arcane_enchantment", new ArcaneEnchantmentArgument())
                                        .executes(ctx -> addArcaneEnchantment(ctx, EntityArgument.getPlayers(ctx, "player"), ArcaneEnchantmentArgument.getArcaneEnchantments(ctx, "arcane_enchantment")))
                                .then(Commands.argument("level", IntegerArgumentType.integer(0))
                                        .executes(ctx -> arcaneEnchantment(ctx, EntityArgument.getPlayers(ctx, "player"), ArcaneEnchantmentArgument.getArcaneEnchantments(ctx, "arcane_enchantment"), IntegerArgumentType.getInteger(ctx,"level")))
                                ))
                        )
                )
        );
    }

    private static int giveKnowledge(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers, Knowledge knowledge) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            KnowledgeUtils.addKnowledge(player, knowledge);
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.knowledge.give.single", targetPlayers.iterator().next().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.knowledge.give.multiple", targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int removeKnowledge(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers, Knowledge knowledge) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            KnowledgeUtils.removeKnowledge(player, knowledge);
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.knowledge.remove.single", targetPlayers.iterator().next().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.knowledge.remove.multiple", targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int giveAllKnowledge(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            KnowledgeUtils.addAllKnowledge(player);
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.knowledge.give.all.single", targetPlayers.iterator().next().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.knowledge.give.all.multiple", targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int removeAllKnowledge(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            KnowledgeUtils.removeAllKnowledge(player);
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.knowledge.remove.all.single", targetPlayers.iterator().next().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.knowledge.remove.all.multiple", targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int giveSpell(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers, Spell spell) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            KnowledgeUtils.addSpell(player, spell);
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.spell.give.single", Component.translatable(spell.getTranslatedName()), targetPlayers.iterator().next().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.spell.give.multiple", Component.translatable(spell.getTranslatedName()), targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int removeSpell(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers, Spell spell) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            KnowledgeUtils.removeSpell(player, spell);
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.spell.remove.single", Component.translatable(spell.getTranslatedName()), targetPlayers.iterator().next().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.spell.remove.multiple", Component.translatable(spell.getTranslatedName()), targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int giveAllSpell(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            KnowledgeUtils.addAllSpell(player);
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.spell.give.all.single", targetPlayers.iterator().next().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.spell.give.all.multiple", targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int removeAllSpell(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            KnowledgeUtils.removeAllSpell(player);
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.spell.remove.all.single", targetPlayers.iterator().next().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.spell.remove.all.multiple", targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int addArcaneEnchantment(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers, ArcaneEnchantment arcaneEnchantment) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty()) {
                if (ArcaneEnchantmentUtils.isArcaneItem(stack)) {
                    int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, arcaneEnchantment) + 1;
                    if (ArcaneEnchantmentUtils.canAddArcaneEnchantment(stack, arcaneEnchantment, enchantmentLevel)) {
                        ArcaneEnchantmentUtils.addArcaneEnchantment(stack, arcaneEnchantment, enchantmentLevel);
                    }
                }
            }
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.arcane_enchantment.single", Component.translatable(arcaneEnchantment.getTranslatedName()), targetPlayers.iterator().next().getMainHandItem().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.arcane_enchantment.multiple", Component.translatable(arcaneEnchantment.getTranslatedName()), targetPlayers.size());
            }, true);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int arcaneEnchantment(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers, ArcaneEnchantment arcaneEnchantment, int enchantmentLevel) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty()) {
                if (ArcaneEnchantmentUtils.canAddArcaneEnchantment(stack, arcaneEnchantment, enchantmentLevel)) {
                    ArcaneEnchantmentUtils.addArcaneEnchantment(stack, arcaneEnchantment, enchantmentLevel);
                }
            }
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.arcane_enchantment.single", Component.translatable(arcaneEnchantment.getTranslatedName()), targetPlayers.iterator().next().getMainHandItem().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.arcane_enchantment.multiple", Component.translatable(arcaneEnchantment.getTranslatedName()), targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }
}