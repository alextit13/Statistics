package com.chart.statistics.presenter.diagram.linear;

import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.view.diagram.linear.ILinearDiagramView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    private void takeStatesFromObservationForEveryObject(Observation currentObservation) {
        if (mapNameStates == null) {
            mapNameStates = new HashMap<>();
        }
        List<ObjectStatistic> objectStatisticList = currentObservation.getStatisticList();
        if (objectStatisticList == null || objectStatisticList.isEmpty()) return;

        for (ObjectStatistic objectStatistic : objectStatisticList) {
            mapNameStates.put(objectStatistic.getName(), objectStatistic.getStates());
        }
        if (!mapNameStates.isEmpty()) {
            mapNameStates = sortMap(mapNameStates);
            view.initListDiagramAdapter(mapNameStates,
                    currentObservation.getId(),
                    currentObservation.getTimeFinish());
        }
    }

    private HashMap<String, List<State>> sortMap(
            HashMap<String, List<State>> sourceMap
    ) {
        Object[] keys = sourceMap.keySet().toArray();
        if (keys.length == 0) {
            return sourceMap;
        }

        for (Object key : keys) {
            List<State> list =
                    new ArrayList<>(
                            Objects.requireNonNull(sourceMap.get(key.toString())));
            Collections.sort(list,
                    new Comparator<State>() {
                        @Override
                        public int compare(State o1, State o2) {
                            return o1.getId().compareTo(o2.getId());
                        }
                    });
            sourceMap.put(key.toString(), list);
        }
        return sourceMap;
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
