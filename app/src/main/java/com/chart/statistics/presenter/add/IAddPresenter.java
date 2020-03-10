package com.chart.statistics.presenter.add;

import com.chart.statistics.view.add.IAddView;

public interface IAddPresenter {

    void onViewAttach(IAddView view);

    void onViewDetach();

    void onClickConfirm(String name, String description);

    void onClickCancel();
}
