package com.assignment.deutschtesta1;

public class Question {
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private int answerNum;

    public Question() {}

    public Question(String question, String choice1, String choice2, String choice3, int answerNum) {
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.answerNum = answerNum;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }
}
