package com.chart.statistics.presenter.add_object;

import com.chart.statistics.view.add_object.IAddObjectSView;

public interface IAddObjectSPresenter {
    void onViewAttach(IAddObjectSView view);

    void onViewDetach();

    void onClickSave(String name);

    void onClickDelete(String name);

    void onClickNext();
}
