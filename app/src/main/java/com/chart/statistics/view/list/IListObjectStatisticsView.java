package com.chart.statistics.view.list;

import androidx.fragment.app.Fragment;

import com.chart.statistics.model.utils.ObjectStatistic;

import java.util.List;

public interface IListObjectStatisticsView {
    void updateList(List<ObjectStatistic> list);
    void showFragment(Fragment fragment);
}
