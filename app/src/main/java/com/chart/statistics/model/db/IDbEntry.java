package com.chart.statistics.model.db;

import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.State;

import java.util.List;

public interface IDbEntry {

    void insertObjectStatistics(ObjectStatistic objectStatistic);

    void addStateToObjectStatistics(String idObjectStatistics, State state);

    ObjectStatistic getObjectStatistics(String id);

    List<ObjectStatistic> getAllObjectStatistics();
}
