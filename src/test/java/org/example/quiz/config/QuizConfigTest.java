package org.example.quiz.config;



import org.assertj.core.api.Assertions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.config.QuizConfig;
import org.example.entity.Question;
import org.example.entity.Quiz;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class QuizConfigTest {
    @DisplayName("Quiz 역직렬화 테스트")
    @Test
    public void deserilizeTest(){
        InputStream ymlStream = getClass().getClassLoader().getResourceAsStream("quiz.yml");
        Assertions.assertThat(ymlStream).isNotNull();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(ymlStream));
        QuizConfig quizConfig = new QuizConfig(config);
        Quiz testQuiz = quizConfig.getQuiz("vanilla");

        Assertions.assertThat(testQuiz.getQuestionMap()).containsKey("1");

        Assertions.assertThat(testQuiz.getQuestionMap().get("1").getCorrect()).hasValue("크리퍼가 번개를 맞으면 생성됩니다");
    }
    @Test
    public void randomTest(){
        InputStream ymlStream = getClass().getClassLoader().getResourceAsStream("quiz.yml");
        Assertions.assertThat(ymlStream).isNotNull();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(ymlStream));
        QuizConfig quizConfig = new QuizConfig(config);
        List<Question> list = quizConfig.getRandomQuestion(5);
        System.out.println(list.get(0).getQuestion());
        Assertions.assertThat(list.size()).isEqualTo(2);
    }
}