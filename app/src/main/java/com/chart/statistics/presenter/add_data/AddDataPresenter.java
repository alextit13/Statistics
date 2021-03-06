package com.chart.statistics.presenter.add_data;

import com.chart.statistics.R;
import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.view.data.IAddDataView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddDataPresenter implements IAddDataPresenter {

    private IAddDataView view;
    private List<ObjectStatistic> objectStatisticList;
    private List<State> stateList;
    private Observation observation;

    @Override
    public void onViewAttach(IAddDataView view) {
        this.view = view;
        initObjectStatisticsList();
        initStateList();
        getCurrentObservation();
    }

    private void initObjectStatisticsList() {
        if (objectStatisticList == null) {
            objectStatisticList = new ArrayList<>();
        } else {
            objectStatisticList.clear();
        }

        objectStatisticList.addAll(DbEntry.newInstance().getAllObjectStatistics());
        view.initObjectsSpinner(getObjectStatisticListAsStrings());
    }

    private void initStateList() {
        if (stateList == null) {
            stateList = new ArrayList<>();
        } else {
            stateList.clear();
        }
        stateList.addAll(DbEntry.newInstance().getAllState());
        view.initStateSpinner(getStateListAsStrings());
    }

    @Override
    public void onClickSaveObservation(
            int objectPosition,
            int statePosition,
            String description) {
        ObjectStatistic objectStatistic = objectStatisticList.get(objectPosition);
        State state = new State(String.valueOf(new Date().getTime()), stateList.get(statePosition).getName());
        List<State> stateList = objectStatistic.getStates();
        stateList.add(state);

        List<ObjectStatistic> objectStatisticList = observation.getStatisticList();
        objectStatisticList.add(objectStatistic);
        observation.setStatisticList(objectStatisticList);
        DbEntry.newInstance().updateObservation(observation);

        view.showToastMessage(R.string.msg_save_success);
        view.clearFieldsToDefault();
    }

    @Override
    public void onClickFinishObservation() {
        view.showFinishScreen();
    }

    private List<String> getObjectStatisticListAsStrings() {
        List<String> list = new ArrayList<>();
        for (ObjectStatistic objectStatistic : objectStatisticList) {
            list.add(objectStatistic.getName());
        }
        return list;
    }

    private List<String> getStateListAsStrings() {
        List<String> list = new ArrayList<>();
        for (State state : stateList) {
            list.add(state.getName());
        }
        return list;
    }

    private void getCurrentObservation() {
        observation = DbEntry.newInstance().getLastNonCompleteObservation();
        if (observation == null) {
            // we want put first data to observation.
            observation = new Observation(
                    String.valueOf(new Date().getTime()),
                    "",
                    new ArrayList<ObjectStatistic>()
            );
            DbEntry.newInstance().saveObservation(observation);
        }
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
