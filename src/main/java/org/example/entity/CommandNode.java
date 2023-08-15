package org.example.entity;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;
import java.util.stream.Collectors;

public class CommandNode implements CommandExecutor, TabCompleter {
    private String name;
    private Map<String, CommandNode> children = new HashMap<>();

    private String permission;
    private CommandAction executor;

    public CommandNode(String name, String permission, CommandAction executor) {
        this.name = name;
        this.executor = executor;
        this.permission = permission;
    }

    public void addChild(CommandNode child) {
        children.put(child.name, child);
    }

    public boolean checkPermission(CommandSender sender) throws CommandException {
        if (!sender.hasPermission(permission)) {
            throw new CommandException("No permission!");
        }
        return true;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            if (args.length == 0) {
                if (checkPermission(sender)) return executor.execute(sender, args);
            }

            CommandNode child = children.get(args[0]);
            if (child == null) {
                if (checkPermission(sender)) return executor.execute(sender, args);
            }
            return child.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));

        } catch (CommandException e) {

            sender.sendMessage(ChatColor.RED + e.getMessage());
            return false;
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            return children.values().stream().
                    map(CommandNode::getName)
                    .collect(Collectors.toList());
        }
        CommandNode child = children.get(args[0]);
        if (child == null) {
            return Collections.emptyList();
        }
        return child.onTabComplete(sender, cmd, alias, Arrays.copyOfRange(args, 1, args.length));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, CommandNode> getChildren() {
        return children;
    }

    public void setChildren(Map<String, CommandNode> children) {
        this.children = children;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public CommandAction getExecutor() {
        return executor;
    }

    public void setExecutor(CommandAction executor) {
        this.executor = executor;
    }
}

