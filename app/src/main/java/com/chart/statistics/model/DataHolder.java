package com.chart.statistics.model;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import com.chart.statistics.model.utils.State;

import java.util.HashMap;
import java.util.Set;

public class DataHolder {

    private static DataHolder instance;

    public static DataHolder newInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    private HashMap<String, Integer> getDataStates() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Простой", Color.RED);
        map.put("Работает", Color.GREEN);
        map.put("Ремонт", Color.YELLOW);
        map.put("Обед", Color.BLUE);
        return map;
    }

    public String[] getDataStatesAsArray() {
        Set<String> set = getDataStates().keySet();
        String[] array = new String[getDataStates().keySet().size()];
        set.toArray(array);
        return array;
    }

    public @ColorInt
    int getStateColorByState(State state) {
        HashMap map = getDataStates();
        String stateName = state.getName();
        return ((int) map.get(stateName));
    }
}
