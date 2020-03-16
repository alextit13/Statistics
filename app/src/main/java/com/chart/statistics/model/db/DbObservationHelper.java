package com.chart.statistics.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbObservationHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "observationDb";
    private static final int DEFAULT_DB_VERSION = 1;

    public DbObservationHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DEFAULT_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table observation_table ("
                + "id text primary key,"
                + "data text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
