package com.chart.statistics.presenter.state;

import com.chart.statistics.view.state.IStateView;

public interface IStatePresenter {
    void onViewAttach(IStateView view, String idObjectStatistics);

    void onViewDetach();

    void onClickFab();

    void onSelectState(String state);

    void onChartViewWasInit();
}
