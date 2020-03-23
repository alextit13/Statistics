package com.chart.statistics.presenter.list_observation;

import com.chart.statistics.R;
import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.view.list_observation.IListObservationView;

import java.util.ArrayList;
import java.util.List;

public class ListObservationPresenter implements IListObservationPresenter {

    private IListObservationView view;

    @Override
    public void onViewAttach(IListObservationView view) {
        this.view = view;
        initSpinnerObservation();
    }

    private void initSpinnerObservation() {
        List<Observation> observationList = DbEntry.newInstance().getAllObservation();
        if (observationList == null || observationList.isEmpty()) {
            view.showToastMessage(R.string.msg_error);
            return;
        }
        view.initSpinnerAdapter(getListObservationsLikeString(observationList));
    }

    private List<String> getListObservationsLikeString(List<Observation> observations){
        List<String> list = new ArrayList<>();
        for (Observation observation : observations) {
            list.add(observation.getName());
        }
        return list;
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
