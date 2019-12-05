package com.assignment.deutschtesta1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.assignment.deutschtesta1.TestContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TestDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DeutschTestA1.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public TestDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        //Create Database

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_CHOICE1 + " TEXT, " +
                QuestionsTable.COLUMN_CHOICE2 + " TEXT, " +
                QuestionsTable.COLUMN_CHOICE3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NUM + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);

    }

    //Question , Choice 1, Choice2 , Choice3, Answer of the question

    private void fillQuestionsTable() {
        Question q1 = new Question("Im Winter ist es __________", "frostig", "froh", "Schnee", 1);
        addQuestion(q1);
        Question q2 = new Question("Woher kommen Sie? - Ich komme_____Malaysia.", "von", "aus", "nach", 2);
        addQuestion(q2);
        Question q3 = new Question("Adrian, ist das dein Auto - Ja, das ist ______ Auto.", "mein", "unsere", "ihr", 1);
        addQuestion(q3);
        Question q4 = new Question("Ich esse Pizza am liebsten", "Ich macht Pizza nicht.", "Ich esse Pizza nicht gern.", "Ich esse Pizza sehr gern.", 3);
        addQuestion(q4);
        Question q5 = new Question("____ Orangensaft schmeckt____Gästen gar nicht.", "Der/den", "Der/die", "Den/die", 1);
        addQuestion(q5);
        Question q6 = new Question("Ich habe ____ Fieber.", "eine", "ein", "-", 3);
        addQuestion(q6);
        Question q7 = new Question("_____ ruhig!", "sein", "sei", "bist", 2);
        addQuestion(q7);
        Question q8 = new Question("Um wie viel Uhr bist du________?", "eingeschlafen", "schlafen ein", "eingeschlaft", 1);
        addQuestion(q8);
        Question q9 = new Question("Gestern bin ich in den Park _________", "gehe", "gegangen", "gegeht",2);
        addQuestion(q9);
        Question q10 = new Question("Die Tochter meiner Schwester ist meine __________", "Neffe", "Einkelin", "Nichte", 3);
        addQuestion(q10);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionsTable.COLUMN_CHOICE1,question.getChoice1());
        cv.put(QuestionsTable.COLUMN_CHOICE2,question.getChoice2());
        cv.put(QuestionsTable.COLUMN_CHOICE3,question.getChoice3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NUM,question.getAnswerNum());
        db.insert(QuestionsTable.TABLE_NAME,null,cv);
    }

    //Retrieve Database

    public List<Question>getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setChoice1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CHOICE1)));
                question.setChoice2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CHOICE2)));
                question.setChoice3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CHOICE3)));
                question.setAnswerNum(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUM)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
