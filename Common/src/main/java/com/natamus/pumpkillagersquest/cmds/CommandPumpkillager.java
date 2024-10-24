package com.natamus.pumpkillagersquest.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.natamus.collective.functions.ItemFunctions;
import com.natamus.collective.functions.MessageFunctions;
import com.natamus.pumpkillagersquest.util.Data;
import com.natamus.pumpkillagersquest.util.Reference;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class CommandPumpkillager {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("pumpkillager").requires((iCommandSender) -> { return iCommandSender.hasPermission(2); })
			.then(Commands.literal("reset")
			.then(Commands.argument("targets", EntityArgument.entities())
			.executes((command) -> {
				CommandSourceStack source = command.getSource();
				for (Entity entity : EntityArgument.getEntities(command, "targets")) {
					List<String> tags = entity.getTags().stream().toList();

					int deletedCount = 0;
					for (String tag : tags) {
						if (tag.contains(Reference.MOD_ID)) {
							entity.getTags().remove(tag);
							deletedCount += 1;
						}
					}
					MessageFunctions.sendMessage(source, "Successfully deleted " + deletedCount + " pumpkillager tags for " + entity.getName().getString() + ".", ChatFormatting.DARK_GREEN);
				}
				return 1;
			})))

			.then(Commands.literal("book")
			.then(Commands.literal("pumpkillagers-quest")
			.executes((command) -> {
				CommandSourceStack source = command.getSource();

				try {
					Player player = source.getPlayerOrException();

					ItemFunctions.giveOrDropItemStack(player, Data.getQuestbook(player.level()));

					MessageFunctions.sendMessage(player, "You have been given the 'Pumpkillager's Quest' book.", ChatFormatting.DARK_GREEN);
				}
				catch (CommandSyntaxException ex) {
					MessageFunctions.sendMessage(source, "This command can only be executed as a player in-game.", ChatFormatting.RED);
					return 0;
				}

				return 1;
			})))

			.then(Commands.literal("book")
			.then(Commands.literal("stopping-the-pumpkillager")
			.executes((command) -> {
				CommandSourceStack source = command.getSource();

				try {
					Player player = source.getPlayerOrException();

					ItemFunctions.giveOrDropItemStack(player, Data.getStopPkbook(player.level()));

					MessageFunctions.sendMessage(player, "You have been given the 'Stopping the Pumpkillager' book.", ChatFormatting.DARK_GREEN);
				}
				catch (CommandSyntaxException ex) {
					MessageFunctions.sendMessage(source, "This command can only be executed as a player in-game.", ChatFormatting.RED);
					return 0;
				}

				return 1;
			})))
		);
    }
}
