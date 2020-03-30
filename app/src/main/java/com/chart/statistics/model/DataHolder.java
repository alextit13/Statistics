package com.chart.statistics.model;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import java.util.Random;

public class DataHolder {

    private static DataHolder instance;

    public static DataHolder newInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public @ColorInt
    int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
