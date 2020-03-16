package com.chart.statistics.model.utils;

import java.util.List;

public class Observation {

    private String id;
    private String name;
    private List<ObjectStatistic> statisticList;
    private boolean isCompleted = false;

    public Observation() {
    }

    public Observation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Observation(String id, String name, List<ObjectStatistic> statisticList) {
        this.id = id;
        this.name = name;
        this.statisticList = statisticList;
    }

    public List<ObjectStatistic> getStatisticList() {
        return statisticList;
    }

    public void setStatisticList(List<ObjectStatistic> statisticList) {
        this.statisticList = statisticList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
