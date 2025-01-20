package com.example.task02take;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TriviaApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "Users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_QUESTIONS = "Questions";
    private static final String COLUMN_QUESTION_ID = "id";
    private static final String COLUMN_QUESTION_TEXT = "question_text";
    private static final String COLUMN_OPTION1 = "option1";
    private static final String COLUMN_OPTION2 = "option2";
    private static final String COLUMN_OPTION3 = "option3";
    private static final String COLUMN_OPTION4 = "option4";
    private static final String COLUMN_CORRECT_ANSWER = "correct_answer";

    private static final String TABLE_SCORES = "Scores";
    private static final String COLUMN_SCORE_ID = "id";
    private static final String COLUMN_USER_ID_FK = "user_id";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        String createQuestionsTable = "CREATE TABLE " + TABLE_QUESTIONS + " (" +
                COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION_TEXT + " TEXT, " +
                COLUMN_OPTION1 + " TEXT, " +
                COLUMN_OPTION2 + " TEXT, " +
                COLUMN_OPTION3 + " TEXT, " +
                COLUMN_OPTION4 + " TEXT, " +
                COLUMN_CORRECT_ANSWER + " INTEGER)";
        db.execSQL(createQuestionsTable);

        String createScoresTable = "CREATE TABLE " + TABLE_SCORES + " (" +
                COLUMN_SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID_FK + " INTEGER, " +
                COLUMN_SCORE + " INTEGER, " +
                COLUMN_TIMESTAMP + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";
        db.execSQL(createScoresTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public void addQuestion(String questionText, String option1, String option2, String option3, String option4, int correctAnswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_TEXT, questionText);
        values.put(COLUMN_OPTION1, option1);
        values.put(COLUMN_OPTION2, option2);
        values.put(COLUMN_OPTION3, option3);
        values.put(COLUMN_OPTION4, option4);
        values.put(COLUMN_CORRECT_ANSWER, correctAnswer);
        db.insert(TABLE_QUESTIONS, null, values);
        db.close();
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS, null);
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION_TEXT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION1)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION2)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION3)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION4)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CORRECT_ANSWER))
                );
                questions.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return questions;
    }


public void populateQuestions() {
        SQLiteDatabase db = this.getWritableDatabase();

        String countQuery = "SELECT COUNT(*) FROM " + TABLE_QUESTIONS;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count > 0) {
            return;
        }

        addQuestion("What is the capital of France?", "Paris", "London", "Berlin", "Madrid", 1);
        addQuestion("What is the largest planet in our solar system?", "Earth", "Mars", "Jupiter", "Saturn", 3);
        addQuestion("Which element has the chemical symbol 'O'?", "Oxygen", "Gold", "Osmium", "Carbon", 1);
        addQuestion("How many continents are there on Earth?", "5", "6", "7", "8", 3);
        addQuestion("Which ocean is the largest by surface area?", "Atlantic", "Pacific", "Indian", "Arctic", 2);
        addQuestion("Who painted the Mona Lisa?", "Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Michelangelo", 1);
        addQuestion("What is the square root of 64?", "6", "8", "10", "12", 2);
        addQuestion("Which country is known as the Land of the Rising Sun?", "China", "South Korea", "Japan", "Thailand", 3);
        addQuestion("What is the freezing point of water in Celsius?", "0", "32", "-1", "100", 1);
        addQuestion("What is the smallest prime number?", "1", "2", "3", "5", 2);

        db.close();
    }



    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean result = cursor.moveToFirst();
        cursor.close();
        db.close();
        return result;
    }


    public boolean isUsernameTaken(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }



    public int getLastScore(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + TABLE_SCORES + "." + COLUMN_SCORE +
                " FROM " + TABLE_SCORES +
                " INNER JOIN " + TABLE_USERS +
                " ON " + TABLE_SCORES + "." + COLUMN_USER_ID_FK + " = " + TABLE_USERS + "." + COLUMN_USER_ID +
                " WHERE " + COLUMN_USERNAME + " = ?" +
                " ORDER BY " + COLUMN_TIMESTAMP + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        int lastScore = 0;
        if (cursor.moveToFirst()) {
            lastScore = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
        }
        cursor.close();
        db.close();
        return lastScore;
    }



    public void updateScore(String username, int newScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        String userIdQuery = "SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?";
        Cursor cursor = db.rawQuery(userIdQuery, new String[]{username});
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
            cursor.close();

            // Get current timestamp as a formatted string
            String currentTimestamp = String.valueOf(System.currentTimeMillis());

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID_FK, userId);
            values.put(COLUMN_SCORE, newScore);
            values.put(COLUMN_TIMESTAMP, currentTimestamp);

            String scoreCheckQuery = "SELECT * FROM " + TABLE_SCORES + " WHERE " + COLUMN_USER_ID_FK + "=?";
            Cursor scoreCursor = db.rawQuery(scoreCheckQuery, new String[]{String.valueOf(userId)});

            if (scoreCursor.moveToFirst()) {
                db.update(TABLE_SCORES, values, COLUMN_USER_ID_FK + "=?", new String[]{String.valueOf(userId)});
            } else {
                db.insert(TABLE_SCORES, null, values);
            }

            scoreCursor.close();
        } else {
            cursor.close();
            throw new RuntimeException("User not found for username: " + username);
        }

        db.close();
    }





    public String getLastScoreTimestamp(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + TABLE_SCORES + "." + COLUMN_TIMESTAMP +
                " FROM " + TABLE_SCORES +
                " INNER JOIN " + TABLE_USERS +
                " ON " + TABLE_SCORES + "." + COLUMN_USER_ID_FK + " = " + TABLE_USERS + "." + COLUMN_USER_ID +
                " WHERE " + COLUMN_USERNAME + " = ?" +
                " ORDER BY " + COLUMN_TIMESTAMP + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, new String[]{username});
        String timestamp = "No timestamp available";
        if (cursor.moveToFirst()) {
            timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP));
        }
        cursor.close();
        db.close();

        return timestamp;
    }



}


