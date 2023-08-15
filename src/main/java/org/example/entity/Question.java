package org.example.entity;

import java.util.*;
import java.util.stream.Collectors;

public class Question {
    private String ID;
    private String quizId;
    private String Question;
    private List<String> choices;

    public Question(String ID, String quizId, String question, List<String> choices) {
        this.ID = ID;
        this.quizId = quizId;
        Question = question;
        this.choices = choices;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getID() {
        return ID;
    }

    public String getQuestion() {
        return Question;
    }

    public List<String> getWrongs(){
       return choices.stream().skip(1).collect(Collectors.toList());
    }

    public Optional<String> getCorrect() {
        return choices.stream().findFirst();
    }

    public List<String> getChoices() {
        return choices;
    }

    public List<String> shuffleChoices(){
        List<String> shuffled = new ArrayList<>(choices);
        Collections.shuffle(shuffled, new Random());
        return shuffled;
    }
}
