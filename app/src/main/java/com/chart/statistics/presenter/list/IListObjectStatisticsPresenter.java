package com.chart.statistics.presenter.list;

import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.view.list.IListObjectStatisticsView;

public interface IListObjectStatisticsPresenter {
    void onViewAttach(IListObjectStatisticsView view);
    void onViewDetach();
    void onClickAddFab();

    void onClickDeleteObject(ObjectStatistic objectStatistic);
}
