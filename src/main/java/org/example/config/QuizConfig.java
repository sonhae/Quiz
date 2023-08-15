package org.example.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.entity.Question;
import org.example.entity.Quiz;
import org.example.entity.Reward;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuizConfig {
    private YamlConfiguration config;

    public QuizConfig(String path) {
        this.config = YamlConfiguration.loadConfiguration(new File(path));
    }

    public QuizConfig(YamlConfiguration config) {
        this.config = config;
    }


    public Map<String, Quiz> loadAllQuizes() {
       return config.getConfigurationSection("").getKeys(false).stream().
                collect(Collectors.toMap(
                        item -> item,
                        this::getQuiz
                ));
    }

    public List<Question> getRandomQuestion(int n){
        List<Question> questions = loadAllQuizes().values().stream().flatMap(
                quiz -> quiz.getQuestionMap().values().stream()
        ).collect(Collectors.toList());

        Collections.shuffle(questions);


       return questions.subList(0, Math.min(n, questions.size()));
    }

    public Quiz getQuiz(String name) {
        Reward reward = new Reward();
        Map<String, Question> questions = new LinkedHashMap<>();

//Todo:reward

        ConfigurationSection questionsSection = config.getConfigurationSection(name + ".questions");

        for (String key : Objects.requireNonNull(questionsSection).getKeys(false)) {

            String question = questionsSection.getString(key + ".question");
            String correct = questionsSection.getString(key + ".choices.correct");
            List<String> wrongs = questionsSection.getStringList(key + ".choices.wrong");

            List<String> choices = Stream.concat(Stream.of(correct), wrongs.stream())
                    .collect(Collectors.toList());


            Question q = new Question(key, name , question, choices);

            questions.put(key, q);
        }
    return new Quiz(name, reward, questions);
    }
}
