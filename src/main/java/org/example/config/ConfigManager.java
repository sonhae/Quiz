package org.example.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.example.Main;

import java.io.File;

public class ConfigManager {
    QuizConfig quizConfig;
    QuizSchedulerConfig quizSchedulerConfig;


    public void initialize(){
        File quizFile = new File(Main.getInstance().getDataFolder(), "quiz.yml");
        if (!quizFile.exists()) {
            Main.getInstance().saveResource("quiz.yml", false);
        }
        File quizSchedulerFile = new File(Main.getInstance().getDataFolder(), "quiz.yml");
        if (!quizFile.exists()) {
            Main.getInstance().saveResource("quiz_scheduler.yml", false);
        }

        this.quizConfig = new QuizConfig(YamlConfiguration.loadConfiguration(quizFile));
        this.quizSchedulerConfig = new QuizSchedulerConfig(YamlConfiguration.loadConfiguration(quizSchedulerFile));
    }

    public QuizConfig getQuizConfig() {
        return quizConfig;
    }

    public QuizSchedulerConfig getQuizSchedulerConfig() {
        return quizSchedulerConfig;
    }

    public void setQuizConfig(QuizConfig quizConfig) {
        this.quizConfig = quizConfig;
    }

    public void setQuizSchedulerConfig(QuizSchedulerConfig quizSchedulerConfig) {
        this.quizSchedulerConfig = quizSchedulerConfig;
    }
}
