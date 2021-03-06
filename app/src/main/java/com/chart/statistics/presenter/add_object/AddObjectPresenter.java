package com.chart.statistics.presenter.add_object;

import android.annotation.SuppressLint;

import com.chart.statistics.R;
import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.view.add_object.IAddObjectSView;

import java.util.ArrayList;
import java.util.Date;

public class AddObjectPresenter implements IAddObjectSPresenter {

    private IAddObjectSView view;

    @Override
    public void onViewAttach(IAddObjectSView view) {
        this.view = view;
        handleNonCompleteObservation();
    }

    private void handleNonCompleteObservation() {
        Observation observation = DbEntry.newInstance().getLastNonCompleteObservation();
        /*
          If observation != null - we have non completed
          observation. This in process now. Now we can move to observation screen.
          On this moment it's AddDataFragment
         */
        if (observation != null) {
            view.showDataScreen();
        }
    }

    @SuppressLint("ResourceType")
    public void onClickSave(String name) {
        if (name.isEmpty()) {
            view.showToastMessage(R.string.msg_enter_name);
            return;
        }

        DbEntry.newInstance().insertObjectStatistics(
                new ObjectStatistic(
                        String.valueOf(new Date().getTime()),
                        name,
                        new ArrayList<State>()
                )
        );
        view.clearNameField();
        view.showToastMessage(R.string.msg_save_success);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClickDelete(String name) {
        if (name.isEmpty()) {
            view.showToastMessage(R.string.msg_enter_name);
            return;
        }
        boolean result = DbEntry.newInstance().deleteObjectStatisticsByName(name);
        if (result) {
            view.showToastMessage(R.string.msg_delete_success);
            view.clearNameField();
        } else {
            view.showToastMessage(R.string.msg_enter_correct_name);
        }
    }

    @Override
    public void onClickNext() {
        view.showAddStateScreen();
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
