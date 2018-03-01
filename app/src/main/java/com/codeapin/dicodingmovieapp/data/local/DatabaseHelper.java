package com.codeapin.dicodingmovieapp.data.local;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MovieReader.db";

    private static final String COMMA_SEP = ",";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";

    private static final String SQL_CREATES_ENTRY = "CREATE TABLE " + MovieColumns.TABLE_MOVIE + "(" +
            MovieColumns._ID + ", INTEGER PRIMARY KEY," +
            MovieColumns.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMMA_SEP +
            MovieColumns.COLUMN_NAME_ORIGINAL_LANGUAGE + TEXT_TYPE + COMMA_SEP +
            MovieColumns.COLUMN_NAME_ORIGINAL_TITLE + TEXT_TYPE + COMMA_SEP +
            MovieColumns.COLUMN_NAME_VIDEO + INTEGER_TYPE + COMMA_SEP +
            MovieColumns.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            MovieColumns.COLUMN_NAME_GENRE_IDS + TEXT_TYPE + COMMA_SEP +
            MovieColumns.COLUMN_NAME_POSTER_PATH + TEXT_TYPE+ COMMA_SEP +
            MovieColumns.COLUMN_NAME_BACKDROP_PATH + TEXT_TYPE+ COMMA_SEP +
            MovieColumns.COLUMN_NAME_RELEASE_DATE + TEXT_TYPE+ COMMA_SEP +
            MovieColumns.COLUMN_NAME_VOTE_AVERAGE + REAL_TYPE + COMMA_SEP +
            MovieColumns.COLUMN_NAME_POPULARITY + REAL_TYPE + COMMA_SEP +
            MovieColumns.COLUMN_NAME_ADULT + INTEGER_TYPE + COMMA_SEP +
            MovieColumns.COLUMN_NAME_VOTE_COUNT + INTEGER_TYPE +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,
                null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATES_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieColumns.TABLE_MOVIE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieColumns.TABLE_MOVIE);
    }
}
