package org.example.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class QuizSchedulerConfig {
    private YamlConfiguration config;

    public QuizSchedulerConfig(YamlConfiguration config) {
        this.config = config;
    }

    public int count(){
        return  config.getInt("default.count");
    }
    public int interval(){
        return  config.getInt("default.interval");
    }
}
