package com.chart.statistics.presenter.finish;

import com.chart.statistics.R;
import com.chart.statistics.view.finish.IFinishView;

public class FinishPresenter implements IFinishPresenter {

    private IFinishView view;

    @Override
    public void onViewAttach(IFinishView view, String observationId) {
        this.view = view;
    }

    @Override
    public void onClickSave(String name) {
        if (name.equals("")) {
            view.showToastMessage(R.string.msg_enter_correct_name);
            return;
        }
        // TODO(): Implement this
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
