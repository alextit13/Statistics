package com.chart.statistics.presenter.finish;

import com.chart.statistics.view.finish.IFinishView;

public interface IFinishPresenter {
    void onViewAttach(IFinishView view, String observationId);

    void onViewDetach();

    void onClickSave(String name);
}
