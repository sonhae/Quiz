package org.example.command;

import org.example.Main;
import org.example.entity.CommandNode;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private Map<String, CommandNode> rootCommands = new HashMap<>();

    public CommandManager() {
    }


    public Map<String, CommandNode> getRootCommands() {
        return rootCommands;
    }

    public void registerCommand(String commandName, @NotNull CommandNode rootNode) {
        rootCommands.put(commandName, rootNode);
        Main.getInstance().getCommand(commandName).setExecutor(rootNode);
        Main.getInstance().getCommand(commandName).setTabCompleter(rootNode);
    }
}
