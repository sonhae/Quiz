package org.example.entity;



import java.util.Map;

public class Quiz {

    private String ID;
    private Reward reward;
    private Map<String,Question> questionMap;

    public Quiz(String ID, Reward reward, Map<String, Question> questionMap) {
        this.ID = ID;
        this.reward = reward;
        this.questionMap = questionMap;
    }

    public Reward getReward() {
        return reward;
    }

    public Map<String, Question> getQuestionMap() {
        return questionMap;
    }
}
