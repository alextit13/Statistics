package com.chart.statistics.presenter.add_state;

import com.chart.statistics.view.add_state.IAddStateView;

public interface IAddStatePresenter {

    void onViewAttach(IAddStateView view);

    void onViewDetach();

    void onClickSave(String name);

    void onClickDelete(String name);

    void onClickNext();
}
