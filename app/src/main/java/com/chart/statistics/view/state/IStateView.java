package com.chart.statistics.view.state;

import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.State;

import java.util.List;

public interface IStateView {
    void updateUi(ObjectStatistic objectStatistic);
    void showDialog(String [] arrayStates);

    void setChart(List<State> states);
}
