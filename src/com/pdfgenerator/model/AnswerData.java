package com.pdfgenerator.model;

import java.io.Serializable;
import java.util.Arrays;


public class AnswerData implements Serializable {
    private int questionId;
    private boolean lastQuestion;
    private Integer[] selectedAnswers;
    public AnswerData() { }
    public AnswerData(int questionId, boolean lastQuestion, Integer[] selectedAnswers) {
        this.questionId = questionId;
        this.lastQuestion = lastQuestion;
        this.selectedAnswers = selectedAnswers;
    }
    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) { this.questionId = questionId; }
    public boolean isLastQuestion() {
        return lastQuestion;
    }
    public void setLastQuestion(boolean lastQuestion) {
        this.lastQuestion = lastQuestion;
    }
    public Integer[] getSelectedAnswers() {
        return selectedAnswers;
    }
    public void setSelectedAnswers(Integer[] selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }
    @Override
    public String toString() {
        return "{\"AnswerData\":{"
                + "\"questionId\":\"" + questionId + "\""
                + ", \"lastQuestion\":\"" + lastQuestion + "\""
                + ", \"selectedAnswers\":\"" + Arrays.toString(selectedAnswers) + "\""
                + "}}";
    }
}
