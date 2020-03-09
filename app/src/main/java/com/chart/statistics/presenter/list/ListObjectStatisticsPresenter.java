package com.chart.statistics.presenter.list;

import com.chart.statistics.view.list.IListObjectStatisticsView;

public class ListObjectStatisticsPresenter implements IListObjectStatisticsPresenter {

    private IListObjectStatisticsView view;

    @Override
    public void onViewAttach(IListObjectStatisticsView view) {
        this.view = view;
    }

    @Override
    public void onViewDetach() {
        view = null;
    }

    @Override
    public void onClickAddFab() {
        // TODO: 2020-03-09
    }
}