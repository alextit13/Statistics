package com.chart.statistics.model;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import com.chart.statistics.model.utils.State;

import java.util.HashMap;
import java.util.Random;

public class DataHolder {

    private static DataHolder instance;
    private static HashMap<State, Integer> mapStateColors;

    public static DataHolder newInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public @ColorInt
    int getRandomColor(State state) {
        if (mapStateColors == null) {
            mapStateColors = new HashMap<>();
        }
        if (mapStateColors.get(state) == null) {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            mapStateColors.put(state, color);
            return color;
        } else {
            return mapStateColors.get(state);
        }
    }
}
