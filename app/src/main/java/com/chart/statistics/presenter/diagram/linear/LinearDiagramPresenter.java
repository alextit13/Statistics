package com.chart.statistics.presenter.diagram.linear;

import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.view.diagram.linear.ILinearDiagramView;

import java.util.HashMap;
import java.util.List;

public class LinearDiagramPresenter implements ILinearDiagramPresenter {

    private ILinearDiagramView view;
    private String observationId = "";
    private HashMap<String, List<State>> mapNameStates; // key - name object statistics, value - states this object

    @Override
    public void onViewAttach(ILinearDiagramView view, String idObservation) {
        this.view = view;
        observationId = idObservation;
        initListDiagrams();
    }

    private void initListDiagrams() {
        if (observationId.equals("")) return;

        Observation currentObservation = DbEntry.newInstance().getObservationById(observationId);
        if (currentObservation == null) return;

        takeStatesFromObservationForEveryObject(currentObservation);
    }

    private void takeStatesFromObservationForEveryObject(Observation currentObservation){
        if (mapNameStates == null) {
            mapNameStates = new HashMap<>();
        }
        List<ObjectStatistic> objectStatisticList = currentObservation.getStatisticList();
        if (objectStatisticList == null || objectStatisticList.isEmpty()) return;

        for (ObjectStatistic objectStatistic : objectStatisticList) {
            mapNameStates.put(objectStatistic.getName(), objectStatistic.getStates());
        }
        if (!mapNameStates.isEmpty()) {
            view.initListDiagramAdapter(mapNameStates, currentObservation.getTimeFinish());
        }
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
