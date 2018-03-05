package com.codeapin.dicodingmovieapp.data.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.codeapin.dicodingmovieapp.data.remote.model.MovieItem;

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
}
