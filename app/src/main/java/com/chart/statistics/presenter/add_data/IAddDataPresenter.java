package com.chart.statistics.presenter.add_data;

import com.chart.statistics.view.data.IAddDataView;

public interface IAddDataPresenter {
    void onViewAttach(IAddDataView view);

    void onViewDetach();

    void onClickSaveObservation(int objectPosition, int statePosition, String description);

    void onClickFinishObservation();
}
