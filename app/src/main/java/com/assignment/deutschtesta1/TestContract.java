package com.assignment.deutschtesta1;

import android.provider.BaseColumns;

public final class TestContract {

    private TestContract() {}

//Constant

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "test_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_CHOICE1 = "choice1";
        public static final String COLUMN_CHOICE2 = "choice2";
        public static final String COLUMN_CHOICE3 = "choice3";
        public static final String COLUMN_ANSWER_NUM = "answer_num";
        public static final String COLUMN_LEVEL= "level";

    }
}
