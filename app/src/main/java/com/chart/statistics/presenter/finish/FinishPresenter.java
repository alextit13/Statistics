package com.chart.statistics.presenter.finish;

import com.chart.statistics.R;
import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.view.finish.IFinishView;

public class FinishPresenter implements IFinishPresenter {

    private IFinishView view;
    private Observation observation;

    @Override
    public void onViewAttach(IFinishView view) {
        this.view = view;
        getSavedObservation();
    }

    private void getSavedObservation() {
        observation = DbEntry.newInstance().getLastNonCompleteObservation();
        if (observation == null) {
            view.showToastMessage(R.string.msg_error);
        }
    }

    @Override
    public void onClickSave(String name) {
        if (name.equals("")) {
            view.showToastMessage(R.string.msg_enter_correct_name);
            return;
        }
        if (observation == null) return;

        observation.setName(name);
        observation.setCompleted(true);
        DbEntry.newInstance().updateObservation(observation);
        view.openListObservationScreen();
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
