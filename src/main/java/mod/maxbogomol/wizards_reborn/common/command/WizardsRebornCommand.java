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
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramRecipe;
import mod.maxbogomol.wizards_reborn.api.monogram.Monograms;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.*;

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
                .then(Commands.literal("wissen")
                        .then(Commands.literal("set")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("wissen", IntegerArgumentType.integer(0))
                                            .executes(ctx -> setWissen(ctx, EntityArgument.getPlayers(ctx, "player"), IntegerArgumentType.getInteger(ctx,"wissen")))
                                        )
                                )
                        )
                        .then(Commands.literal("add")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("wissen", IntegerArgumentType.integer(0))
                                            .executes(ctx -> addWissen(ctx, EntityArgument.getPlayers(ctx, "player"), IntegerArgumentType.getInteger(ctx,"wissen")))
                                        )
                                )
                        )
                        .then(Commands.literal("remove")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("wissen", IntegerArgumentType.integer(0))
                                            .executes(ctx -> removeWissen(ctx, EntityArgument.getPlayers(ctx, "player"), IntegerArgumentType.getInteger(ctx,"wissen")))
                                        )
                                )
                        )
                )
                .then(Commands.literal("dev")
                        .then(Commands.literal("knowledge_points")
                                .executes(ctx -> getKnowledgePoints(ctx))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(ctx -> getKnowledgePoints(ctx, EntityArgument.getPlayer(ctx, "player")))
                                )
                        )
                        .then(Commands.literal("spell_points")
                                .executes(ctx -> getSpellPoints(ctx))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(ctx -> getSpellPoints(ctx, EntityArgument.getPlayer(ctx, "player")))
                                )
                        )
                        .then(Commands.literal("research")
                                .then(Commands.argument("start", IntegerArgumentType.integer(0)).then(Commands.argument("end", IntegerArgumentType.integer(1)).then(Commands.argument("size", IntegerArgumentType.integer(0)).then(Commands.argument("max", IntegerArgumentType.integer(2))
                                                .executes(ctx -> research(ctx, IntegerArgumentType.getInteger(ctx,"start"), IntegerArgumentType.getInteger(ctx,"end"), IntegerArgumentType.getInteger(ctx,"size"), IntegerArgumentType.getInteger(ctx,"max")))
                                ))))
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

    private static int setWissen(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers, int wissen) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IWissenItem wissenItem) {
                    if (wissen > wissenItem.getMaxWissen()) wissen = wissenItem.getMaxWissen();
                    WissenItemUtils.existWissen(stack);
                    WissenItemUtils.setWissen(stack, wissen);
                }
            }
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.arcane_enchantment.single", Component.empty(), targetPlayers.iterator().next().getMainHandItem().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.arcane_enchantment.multiple", Component.empty(), targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int addWissen(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers, int wissen) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IWissenItem wissenItem) {
                    WissenItemUtils.existWissen(stack);
                    WissenItemUtils.addWissen(stack, wissen, wissenItem.getMaxWissen());
                }
            }
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.arcane_enchantment.single", Component.empty(), targetPlayers.iterator().next().getMainHandItem().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.arcane_enchantment.multiple", Component.empty(), targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int removeWissen(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers, int wissen) throws CommandSyntaxException {
        for(ServerPlayer player : targetPlayers) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IWissenItem wissenItem) {
                    WissenItemUtils.existWissen(stack);
                    WissenItemUtils.removeWissen(stack, wissen);
                }
            }
        }

        if (targetPlayers.size() == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.arcane_enchantment.single", Component.empty(), targetPlayers.iterator().next().getMainHandItem().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.wizards_reborn.arcane_enchantment.multiple", Component.empty(), targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int getKnowledgePoints(CommandContext<CommandSourceStack> command, ServerPlayer targetPlayer) throws CommandSyntaxException {
        int points = KnowledgeUtils.getKnowledgePoints(targetPlayer);

        command.getSource().sendSuccess(() -> {
            return Component.literal(String.valueOf(points));
        }, true);
        return Command.SINGLE_SUCCESS;
    }

    private static int getKnowledgePoints(CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
        int points = KnowledgeUtils.getAllKnowledgePoints();

        command.getSource().sendSuccess(() -> {
            return Component.literal(String.valueOf(points));
        }, true);
        return Command.SINGLE_SUCCESS;
    }

    private static int getSpellPoints(CommandContext<CommandSourceStack> command, ServerPlayer targetPlayer) throws CommandSyntaxException {
        int points = KnowledgeUtils.getSpellPoints(targetPlayer);

        command.getSource().sendSuccess(() -> {
            return Component.literal(String.valueOf(points));
        }, true);
        return Command.SINGLE_SUCCESS;
    }

    private static int getSpellPoints(CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
        int points = KnowledgeUtils.getAllSpellPoints();

        command.getSource().sendSuccess(() -> {
            return Component.literal(String.valueOf(points));
        }, true);
        return Command.SINGLE_SUCCESS;
    }

    private static int research(CommandContext<CommandSourceStack> command, int start, int end, int size, int max) throws CommandSyntaxException {
        Random random = new Random();
        MutableComponent component = Component.empty();
        boolean error = false;

        ArrayList<Monogram> map = new ArrayList<Monogram>();
        ArrayList<Monogram> startMap = new ArrayList<Monogram>();
        ArrayList<Monogram> maxMap = new ArrayList<Monogram>();
        Map<Monogram, Integer> monograms = new HashMap<Monogram, Integer>();

        map.add(Monograms.getMonograms().get(random.nextInt(0, Monograms.size())));

        for (int i = 0; i < end - 1; i++) {
            boolean add = false;
            for (int iii = 0; iii < 1000; iii++) {
                Monogram monogram = Monograms.getMonograms().get(random.nextInt(0, Monograms.size()));

                if (maxMap.size() > max - 2) {
                    monogram = maxMap.get(random.nextInt(0, maxMap.size()));
                }

                for (MonogramRecipe recipe : Monograms.getRecipes().values()) {
                    int ii = map.size() - 1;
                    Monogram secondMonogram = map.get(ii);

                    if (secondMonogram != monogram) {
                        add = canMonogram(monogram, secondMonogram, recipe);

                        if (i == end - 2) {
                            boolean secondAdd = false;
                            if (add) {
                                secondMonogram = map.get(ii);
                                secondAdd = canMonogram(monogram, secondMonogram, recipe);
                            }

                            if (!secondAdd) {
                                add = false;
                            }
                        }
                    }

                    if (iii >= 999) {
                        error = true;
                        break;
                    }

                    if (add) {
                        break;
                    }
                }

                if (error) {
                    break;
                }

                if (add) {
                    if (!(maxMap.size() > max - 2)) {
                        if (!maxMap.contains(monogram)) {
                            maxMap.add(monogram);
                        }
                    }
                    map.add(monogram);
                    break;
                }
            }
        }

        ArrayList<Integer> numbers = new ArrayList<Integer>();

        if (map.size() > start) {
            while (numbers.size() < start) {
                int randomNumber = random.nextInt(map.size() - 1);
                if (!numbers.contains(randomNumber)) {
                    numbers.add(randomNumber);
                }
            }
        }

        Collections.sort(numbers);
        for (Integer i : numbers) {
            startMap.add(map.get(i));
        }

        for (Monogram monogram : map) {
            if (!monograms.containsKey(monogram)) {
                monograms.put(monogram, 1);
            } else {
                monograms.put(monogram, monograms.get(monogram) + 1);
            }
        }

        for (Monogram monogram : monograms.keySet()) {
            monograms.put(monogram, monograms.get(monogram) + random.nextInt(size / 2, size));
        }

        if (error) {
            component.append(Component.literal("ERROR ").withStyle(ChatFormatting.RED));
        }

        for (Monogram monogram : map) {
            if (monogram != null) {
                component.append(Component.translatable(monogram.getTranslatedName())).withStyle(ChatFormatting.GOLD).append(Component.literal(" "));
            } else {
                component.append(Component.literal("NULL ").withStyle(ChatFormatting.GOLD));
            }
        }

        component.append(Component.literal(String.valueOf(map.size())).withStyle(ChatFormatting.YELLOW));
        component.append(Component.literal(" | ").withStyle(ChatFormatting.WHITE));

        for (Monogram monogram : startMap) {
            if (monogram != null) {
                component.append(Component.translatable(monogram.getTranslatedName()).withStyle(ChatFormatting.BLUE)).append(Component.literal(" "));
            } else {
                component.append(Component.literal("NULL ").withStyle(ChatFormatting.BLUE));
            }
        }

        component.append(Component.literal(String.valueOf(startMap.size())).withStyle(ChatFormatting.DARK_AQUA));
        component.append(Component.literal(" | ").withStyle(ChatFormatting.WHITE));

        for (Monogram monogram : monograms.keySet()) {
            if (monogram != null) {
                component.append(Component.translatable(monogram.getTranslatedName()).withStyle(ChatFormatting.GREEN)).append(Component.literal(" ")).append(Component.literal(String.valueOf(monograms.get(monogram))).withStyle(ChatFormatting.DARK_GREEN)).append(Component.literal(" "));
            } else {
                component.append(Component.literal("NULL ").withStyle(ChatFormatting.GREEN));
            }
        }

        component.append(Component.literal(String.valueOf(monograms.size())).withStyle(ChatFormatting.GRAY));

        if (!error) {
            String string = "";
            for (Monogram monogram : startMap) {
                string = string + " WizardsReborn." + Component.translatable(monogram.getTranslatedName()).getString().toUpperCase() + "_MONOGRAM, ";
            }
            System.out.println(string);
            for (Monogram monogram : monograms.keySet()) {
                string = " new ResearchMonogramEntry(WizardsReborn." + Component.translatable(monogram.getTranslatedName()).getString().toUpperCase() + "_MONOGRAM, " + String.valueOf(monograms.get(monogram)) + "),";
                System.out.println(string);
            }
        }

        command.getSource().sendSuccess(() -> {
            return component;
        }, true);
        return Command.SINGLE_SUCCESS;
    }

    private static boolean canMonogram(Monogram monogram, Monogram secondMonogram, MonogramRecipe recipe) {
        boolean add = false;

        if (secondMonogram != monogram) {
            if (recipe.getInputs().contains(monogram)) {
                if (recipe.getOutput() == secondMonogram) {
                    add = true;
                }
            }
            if (recipe.getInputs().contains(secondMonogram) && recipe.getInputs().contains(monogram)) {
                add = true;
            }
            MonogramRecipe addRecipe = Monograms.getRecipe(monogram.getId());
            if (addRecipe != null) {
                if (addRecipe.getInputs().contains(secondMonogram)) {
                    add = true;
                }
            }
        }

        return add;
    }
}