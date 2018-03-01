package com.codeapin.dicodingmovieapp.data.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns;
import com.codeapin.dicodingmovieapp.data.remote.model.MovieItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.*;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.*;

public class MovieHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public MovieItem queryById(String id) {
        Cursor cursor = database.query(TABLE_MOVIE, null
                , _ID + " = " + id
                , null
                , null
                , null
                , null
                , null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            MovieItem movieItem = new MovieItem(cursor);
            cursor.close();
            return movieItem;
        }

        return null;
    }

    public List<MovieItem> query() {
        List<MovieItem> movieItemList = new ArrayList<>();
        Cursor cursor = database.query(TABLE_MOVIE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                movieItemList.add(new MovieItem(cursor));
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return movieItemList;
    }

    public long insert(MovieItem movieItem) {
        return database.insert(
                TABLE_MOVIE,
                null,
                getContentValues(movieItem)
        );
    }

    public int update(MovieItem movieItem) {
        return database.update(TABLE_MOVIE, getContentValues(movieItem), _ID + " = " + String.valueOf(movieItem.getId()), null);
    }

    public int delete(int id) {
        return database.delete(TABLE_MOVIE, _ID + "=" + String.valueOf(id), null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(TABLE_MOVIE, null
                , _ID + " = " + id
                , null
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(TABLE_MOVIE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(TABLE_MOVIE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(TABLE_MOVIE, values, _ID + " = " + id, null);
    }

    public int deleteProvider(String id) {
        return database.delete(TABLE_MOVIE, _ID + " = " + id, null);
    }

    private ContentValues getContentValues(MovieItem movieItem) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(_ID, movieItem.getId());
        initialValues.put(COLUMN_NAME_OVERVIEW, movieItem.getOverview());
        initialValues.put(COLUMN_NAME_ORIGINAL_LANGUAGE, movieItem.getOriginalLanguage());
        initialValues.put(COLUMN_NAME_ORIGINAL_TITLE, movieItem.getOriginalTitle());
        initialValues.put(COLUMN_NAME_VIDEO, movieItem.isVideo());
        initialValues.put(COLUMN_NAME_TITLE, movieItem.getTitle());

        String ids = movieItem.getGenreIds().toString();
        if (ids != null) {
            ids = ids.substring(1, ids.length() - 1);
        }
        initialValues.put(COLUMN_NAME_GENRE_IDS, ids);
        initialValues.put(COLUMN_NAME_POSTER_PATH, movieItem.getPosterPath());
        initialValues.put(COLUMN_NAME_BACKDROP_PATH, movieItem.getBackdropPath());
        initialValues.put(COLUMN_NAME_RELEASE_DATE, movieItem.getReleaseDate());
        initialValues.put(COLUMN_NAME_VOTE_AVERAGE, movieItem.getVoteAverage());
        initialValues.put(COLUMN_NAME_POPULARITY, movieItem.getPopularity());
        initialValues.put(COLUMN_NAME_ADULT, movieItem.isAdult());
        initialValues.put(COLUMN_NAME_VOTE_COUNT, movieItem.getVoteCount());

        return initialValues;
    }
}
