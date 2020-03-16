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

    private void update(ObjectStatistic objectStatistic) {
        String gsonData = new Gson().toJson(objectStatistic);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", objectStatistic.getId());
        contentValues.put("data", gsonData);
        getWritableDb().update("statistic_table", contentValues, "id=" + objectStatistic.getId(), null);
    }

    @Override
    public void addStateToObjectStatistics(String idObjectStatistics, State state) {
        ObjectStatistic objectStatistic = getObjectStatistics(idObjectStatistics);
        List<State> stateList = objectStatistic.getStates();
        stateList.add(state);
        objectStatistic.setStates(stateList);
        update(objectStatistic);
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

    @Override
    public void deleteObjectStatistics(ObjectStatistic objectStatistic) {
        getWritableDb().delete("statistic_table", "id=" + objectStatistic.getId(), null);
    }

    @Override
    public void deleteObjectStatisticsById(String id) {
        getWritableDb().delete("statistic_table", "id=" + id, null);
    }

    @Override
    public boolean deleteObjectStatisticsByName(String name) {
        String idFindBoject = "";
        List<ObjectStatistic> list = getAllObjectStatistics();
        for (ObjectStatistic objectStatistic : list) {
            if (objectStatistic.getName().equals(name)) {
                idFindBoject = objectStatistic.getId();
            }
        }
        if (idFindBoject.equals("")) {
            return false;
        }
        deleteObjectStatisticsById(idFindBoject);
        return true;
    }

    private static DbEntry instance;

    public static DbEntry newInstance() {
        if (instance == null) {
            instance = new DbEntry();
        }
        return instance;
    }
}