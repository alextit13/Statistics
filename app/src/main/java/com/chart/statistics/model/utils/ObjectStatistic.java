package com.chart.statistics.model.utils;

import java.util.List;

public class ObjectStatistic {

    private String id; // long date in string format
    private String name;
    private List<State> states;

    public ObjectStatistic() {
    }

    public ObjectStatistic(String id, String name, List<State> states) {
        this.id = id;
        this.name = name;
        this.states = states;
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

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    @Override
    public String toString() {
        return "ObjectStatistic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", states=" + states +
                '}';
    }
}
