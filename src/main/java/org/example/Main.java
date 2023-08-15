package org.example;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.command.CommandManager;
import org.example.command.QuizAdminCommand;
import org.example.command.QuizCallbackCommand;
import org.example.config.ConfigManager;
import org.example.config.QuizConfig;
import org.example.gui.GUIManager;
import org.example.quiz.QuizManager;

import java.io.File;

public class Main extends JavaPlugin {
    GUIManager guiManager;
    CommandManager commandManager;
    QuizManager quizManager;

    ConfigManager configManager;

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public QuizManager getQuizManager() {
        return quizManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.guiManager = new GUIManager();
        this.commandManager = new CommandManager();
        this.quizManager = new QuizManager();
        this.configManager = new ConfigManager();

        configManager.initialize();

        commandManager.registerCommand("quizadmin", new QuizAdminCommand());
        commandManager.registerCommand("qcb", new QuizCallbackCommand());

        Bukkit.getPluginManager().registerEvents(guiManager, this);

        Bukkit.getPluginManager().registerEvent(PlayerJoinEvent.class, new Listener() {
        }, EventPriority.NORMAL, (listener, event) -> {
            PlayerJoinEvent e = (PlayerJoinEvent) event;
            Player p = e.getPlayer();

            quizManager.startScheduler(p);
        }, this);

        Bukkit.getPluginManager().registerEvent(PlayerQuitEvent.class, new Listener() {
        }, EventPriority.NORMAL, (listener, event) -> {
            PlayerQuitEvent e = (PlayerQuitEvent) event;
            Player p = e.getPlayer();

            guiManager.getGuis().remove(p);
            guiManager.closeGUI(p);
            quizManager.stopScheduler(p);
        }, this);

        super.onEnable();


    }
}