package com.techexchange.mobileapps.assignment2;

public class Question {
    private final String question;
    private final String correctAnswer;
    private final String[] wrongAnswers = new String[3];

    public Question(String question, String correctAnswer, String[] wrongAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        for(int i=0; i< wrongAnswers.length;i++){
            this.wrongAnswers[i] = wrongAnswers[i];
        }
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getWrongAnswer() {
        return wrongAnswers;
    }
}
