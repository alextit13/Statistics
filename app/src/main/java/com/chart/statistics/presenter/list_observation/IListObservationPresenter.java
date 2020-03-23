package com.chart.statistics.presenter.list_observation;

import com.chart.statistics.view.list_observation.IListObservationView;

public interface IListObservationPresenter {
    void onViewAttach(IListObservationView view);

    void onClickObservationInDialog(String observationName);

    void onViewDetach();

    void onClickLinearDiagramButton();

    void onClickListButton();

    void onClickCircleDiagramButton();

    void onClickCsvButton();
}
