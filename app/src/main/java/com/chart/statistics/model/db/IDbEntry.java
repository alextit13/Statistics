package com.chart.statistics.model.db;

import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.model.utils.State;

import java.util.List;

public interface IDbEntry {

    void insertObjectStatistics(ObjectStatistic objectStatistic);

    void addStateToObjectStatistics(String idObjectStatistics, State state);

    ObjectStatistic getObjectStatistics(String id);

    List<ObjectStatistic> getAllObjectStatistics();

    void deleteObjectStatistics(ObjectStatistic objectStatistic);

    boolean deleteObjectStatisticsByName(String name);

    void deleteObjectStatisticsById(String id);

    void insertState(State state);

    boolean deleteStateByName(String name);

    List<State> getAllState();

    void saveObservation(Observation observation);

    void updateObservation(Observation observation);

    List<Observation> getAllObservation();

    Observation getLastNonCompleteConfirmation();
}
