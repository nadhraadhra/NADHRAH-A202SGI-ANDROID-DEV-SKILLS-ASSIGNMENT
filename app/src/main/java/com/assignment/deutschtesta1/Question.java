package com.assignment.deutschtesta1;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {

    public static final String LEVEL_A1 = "A1";
    public static final String LEVEL_A2 = "A2";
    public static final String LEVEL_B1 = "B1";
    public static final String LEVEL_B2 = "B2";
    public static final String LEVEL_C1 = "C1";
    public static final String LEVEL_C2 = "C2";

    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private int answerNum;
    private String level;

    public Question() {}

    public Question(String question, String choice1, String choice2, String choice3, int answerNum, String level) {
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.answerNum = answerNum;
        this.level = level;
    }

    protected Question(Parcel in) {
        question = in.readString();
        choice1 = in.readString();
        choice2 = in.readString();
        choice3 = in.readString();
        answerNum = in.readInt();
        level = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(choice1);
        dest.writeString(choice2);
        dest.writeString(choice3);
        dest.writeInt(answerNum);
        dest.writeString(level);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public static String[] getAllLevels() {
        return new String[]{
                LEVEL_A1,
                LEVEL_A2,
                LEVEL_B1,
                LEVEL_B2,
                LEVEL_C1,
                LEVEL_C2
        };
    }
}
