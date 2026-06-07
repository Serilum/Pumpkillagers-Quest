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
import net.minecraft.server.permissions.Permissions;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class CommandPumpkillager {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("pumpkillager").requires((iCommandSender) -> { return iCommandSender.permissions().hasPermission(Permissions.COMMANDS_ADMIN); })
			.then(Commands.literal("reset")
			.then(Commands.argument("targets", EntityArgument.entities())
			.executes((command) -> {
				CommandSourceStack source = command.getSource();
				for (Entity entity : EntityArgument.getEntities(command, "targets")) {
					List<String> tags = entity.entityTags().stream().toList();

					int deletedCount = 0;
					for (String tag : tags) {
						if (tag.contains(Reference.MOD_ID)) {
							entity.entityTags().remove(tag);
							deletedCount += 1;
						}
					}
					MessageFunctions.sendTranslatableMessage(source, "collective.pumpkillagersquest.message.successfullydeletedpumpkillager", ChatFormatting.DARK_GREEN, deletedCount, entity.getName().getString());
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

					MessageFunctions.sendTranslatableMessage(player, "collective.pumpkillagersquest.message.givenbook", ChatFormatting.DARK_GREEN, Reference.NAME);
				}
				catch (CommandSyntaxException ex) {
					MessageFunctions.sendTranslatableMessage(source, "collective.shared.message.playeronly", ChatFormatting.RED);
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

					MessageFunctions.sendTranslatableMessage(player, "collective.pumpkillagersquest.message.givenstoppingpumpkillager", ChatFormatting.DARK_GREEN);
				}
				catch (CommandSyntaxException ex) {
					MessageFunctions.sendTranslatableMessage(source, "collective.shared.message.playeronly", ChatFormatting.RED);
					return 0;
				}

				return 1;
			})))
		);
    }
}
