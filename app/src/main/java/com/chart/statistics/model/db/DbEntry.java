package com.chart.statistics.model.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chart.statistics.model.App;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.model.utils.State;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DbEntry implements IDbEntry {

    private DbObjectsHelper dbObjectHelper;
    private DbStateHelper dbStateHelper;
    private DbObservationHelper dbObservationHelper;

    private SQLiteDatabase getWritableObjectsDb() {
        if (dbObjectHelper == null) {
            dbObjectHelper = new DbObjectsHelper(App.instance);
        }
        return dbObjectHelper.getWritableDatabase();
    }

    private SQLiteDatabase getWritableStateDb() {
        if (dbStateHelper == null) {
            dbStateHelper = new DbStateHelper(App.instance);
        }
        return dbStateHelper.getWritableDatabase();
    }

    private SQLiteDatabase getWritableObservationDb() {
        if (dbObservationHelper == null) {
            dbObservationHelper = new DbObservationHelper(App.instance);
        }
        return dbObservationHelper.getWritableDatabase();
    }

    @Override
    public void insertObjectStatistics(ObjectStatistic objectStatistic) {
        String gsonData = new Gson().toJson(objectStatistic);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", objectStatistic.getId());
        contentValues.put("data", gsonData);
        getWritableObjectsDb().insert("statistic_table", null, contentValues);
    }

    private void updateObjectStatistics(ObjectStatistic objectStatistic) {
        String gsonData = new Gson().toJson(objectStatistic);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", objectStatistic.getId());
        contentValues.put("data", gsonData);
        getWritableObjectsDb()
                .update("statistic_table", contentValues,
                        "id=" + objectStatistic.getId(), null);
    }

    @Override
    public void addStateToObjectStatistics(String idObjectStatistics, State state) {
        ObjectStatistic objectStatistic = getObjectStatistics(idObjectStatistics);
        List<State> stateList = objectStatistic.getStates();
        stateList.add(state);
        objectStatistic.setStates(stateList);
        updateObjectStatistics(objectStatistic);
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
        Cursor cursor = getWritableObjectsDb().query(
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
        getWritableObjectsDb().delete("statistic_table", "id=" + objectStatistic.getId(), null);
    }

    @Override
    public void deleteObjectStatisticsById(String id) {
        getWritableObjectsDb().delete("statistic_table", "id=" + id, null);
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

    @Override
    public void insertState(State state) {
        String gsonData = new Gson().toJson(state);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", state.getId());
        contentValues.put("data", gsonData);
        getWritableStateDb().insert("state_table", null, contentValues);
    }

    @Override
    public List<State> getAllState() {
        List<State> stateList = new ArrayList<>();
        Cursor cursor = getWritableStateDb().query(
                "state_table",
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
                State objectStatistic = new Gson().fromJson(data, State.class);
                stateList.add(objectStatistic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return stateList;
    }

    @Override
    public boolean deleteStateByName(String name) {
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

    @Override
    public void saveObservation(Observation observation) {
        String gsonData = new Gson().toJson(observation);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", observation.getId());
        contentValues.put("data", gsonData);
        getWritableObservationDb().insert("observation_table", null, contentValues);
    }

    @Override
    public void updateObservation(Observation observation) {
        String gsonData = new Gson().toJson(observation);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", observation.getId());
        contentValues.put("data", gsonData);
        getWritableObservationDb()
                .update("observation_table", contentValues,
                        "id=" + observation.getId(), null);
    }

    @Override
    public List<Observation> getAllObservation() {
        List<Observation> observationList = new ArrayList<>();
        Cursor cursor = getWritableStateDb().query(
                "observation_table",
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
                Observation objectStatistic = new Gson().fromJson(data, Observation.class);
                observationList.add(objectStatistic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return observationList;
    }

    @Override
    public Observation getLastNonCompleteObservation() {
        List<Observation> list = getAllObservation();
        Observation observation = null;
        for (Observation o : list) {
            if (o.isCompleted())
                observation = o;
        }
        /*if (observation == null) {
            observation = new Observation(
                    String.valueOf(new Date().getTime()),
                    "",
                    new ArrayList<ObjectStatistic>()
            );
        }*/
        return observation;
    }

    private static DbEntry instance;

    public static DbEntry newInstance() {
        if (instance == null) {
            instance = new DbEntry();
        }
        return instance;
    }
}