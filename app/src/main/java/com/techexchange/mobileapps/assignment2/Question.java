package com.techexchange.mobileapps.assignment2;

public class Question {
    private final String question;
    private final String correctAnswer;
    private final String[] wrongAnswers = new String[3];
    private String answeredByUser;
    private final String[] options = new String[4];

    public Question(String question, String correctAnswer, String[] wrongAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answeredByUser = null;
        for(int i=0; i< wrongAnswers.length;i++){
            this.wrongAnswers[i] = wrongAnswers[i];
        }
        for(int i=0; i< options.length;i++){
            if(i==0)
                this.options[i] = this.getCorrectAnswer();
            else
                this.options[i] = this.wrongAnswers[i-1];
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

    public String getAnsweredByUser(){return answeredByUser;}

    public void setAnsweredByUser(String answered){this.answeredByUser = answered;}

    public String[] getOptions(){return options;}
}
