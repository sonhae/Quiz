package org.example.entity;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface CommandAction {
    boolean execute(CommandSender sender, String[] args) throws CommandException;
}
