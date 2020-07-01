package com.pdfgenerator.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class QuestionData implements Serializable {

    private String points;
    private String question;
    private boolean lastQuestion;
    private String[] answers;
    public QuestionData() { }
    public QuestionData(String points, String question, boolean lastQuestion, String[] answers) {
        this.points = points;
        this.question = question;
        this.lastQuestion = lastQuestion;
        this.answers = answers;
    }
    public String getPoints() {
        return points;
    }
    public String getQuestion() {
        return question;
    }
    public boolean isLastQuestion() {
        return lastQuestion;
    }
    public String[] getAnswers() {
        return answers;
    }
    @Override
    public String toString() {
        return "{\"QuestionData\":{"
                + "\"points\":\"" + points + "\""
                + ", \"question\":\"" + question + "\""
                + ", \"lastQuestion\":\"" + lastQuestion + "\""
                + ", \"answers\":" + Arrays.toString(answers)
                + "}}";
    }
}
