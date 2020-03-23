package com.chart.statistics.presenter.list_observation;

import com.chart.statistics.R;
import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.view.list_observation.IListObservationView;

import java.util.ArrayList;
import java.util.List;

public class ListObservationPresenter implements IListObservationPresenter {

    private IListObservationView view;
    private List<Observation> observationList;

    @Override
    public void onViewAttach(IListObservationView view) {
        this.view = view;
        initSpinnerObservation();
    }

    private void initSpinnerObservation() {
        if (observationList != null) {
            if (!observationList.isEmpty()) {
                observationList.clear();
            }
        }
        observationList = DbEntry.newInstance().getAllObservation();
        if (observationList == null || observationList.isEmpty()) {
            view.showToastMessage(R.string.msg_error);
            return;
        }
        view.initSpinnerAdapter(getListObservationsLikeString());
    }

    private List<String> getListObservationsLikeString() {
        List<String> list = new ArrayList<>();
        for (Observation observation : observationList) {
            list.add(observation.getName());
        }
        return list;
    }

    @Override
    public void onClickObservationInDialog(String observationName) {
        view.setObservationInSpinner(getPositionObservationInListByName(observationName));
    }

    private int getPositionObservationInListByName(String observationName) {
        if (observationList == null || observationName.isEmpty()) {
            observationList = DbEntry.newInstance().getAllObservation();
        }
        int position = -1;
        if (observationName.isEmpty()) {
            return position;
        }
        for (int i = 0; i <= observationList.size(); i++) {
            if (observationList.get(i).getName().equals(observationName)) {
                position = i;
            }
        }
        return position;
    }

    @Override
    public void onClickListButton() {
        view.showListObservationChooser(getListObservationsLikeString());
    }

    @Override
    public void onClickLinearDiagramButton() {
        // TODO(): Implement this
    }

    @Override
    public void onClickCircleDiagramButton() {
        // TODO(): Implement this
    }

    @Override
    public void onClickCsvButton() {
        // TODO(): Implement this
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
