package com.chart.statistics.view.list;

import com.chart.statistics.model.utils.ObjectStatistic;

public interface IListObjectStatisticsClickCallback {
    void onClickObject(ObjectStatistic statisticItem);
    void onClickDeleteObject(ObjectStatistic objectStatistic);
}
