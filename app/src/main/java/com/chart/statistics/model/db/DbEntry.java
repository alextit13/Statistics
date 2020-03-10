package com.chart.statistics.model.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chart.statistics.model.App;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.State;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DbEntry implements IDbEntry {

    private DbHelper dbHelper;

    private SQLiteDatabase getWritableDb() {
        if (dbHelper == null) {
            dbHelper = new DbHelper(App.instance);
        }
        return dbHelper.getWritableDatabase();
    }

    @Override
    public void insertObjectStatistics(ObjectStatistic objectStatistic) {
        String gsonData = new Gson().toJson(objectStatistic);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", objectStatistic.getId());
        contentValues.put("data", gsonData);
        getWritableDb().insert("statistic_table", null, contentValues);
    }

    @Override
    public void addStateToObjectStatistics(String idObjectStatistics, State state) {
        ObjectStatistic objectStatistic = getObjectStatistics(idObjectStatistics);
        List<State> stateList = objectStatistic.getStates();
        stateList.add(state);
        objectStatistic.setStates(stateList);
        insertObjectStatistics(objectStatistic);
    }

    @Override
    public ObjectStatistic getObjectStatistics(String id) {
        List<ObjectStatistic> objectStatisticList = getAllObjectStatistics();
        for (ObjectStatistic objectStatistic : objectStatisticList) {
            if (id.equals(objectStatistic.getId())) {
                return objectStatistic;
            }
        }
        return null;
    }

    @Override
    public List<ObjectStatistic> getAllObjectStatistics() {
        List<ObjectStatistic> objectStatisticList = new ArrayList<>();
        Cursor cursor = getWritableDb().query(
                "statistic_table",
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            int dataColumnIndex = cursor.getColumnIndex("data");
            do {
                String data = cursor.getString(dataColumnIndex);
                ObjectStatistic objectStatistic = new Gson().fromJson(data, ObjectStatistic.class);
                objectStatisticList.add(objectStatistic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return objectStatisticList;
    }
}