package com.chart.statistics.presenter.diagram.circle;

import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.view.diagram.circle.ICircleDiagramView;

import java.util.HashMap;
import java.util.List;

public class CircleDiagramPresenter implements ICircleDiagramPresenter {

    private ICircleDiagramView view;
    private String observationId;
    private HashMap<String, List<State>> mapNameStates;

    @Override
    public void onViewAttach(ICircleDiagramView view, String observationId) {
        this.view = view;
        this.observationId = observationId;
        initTargetDiagram();
    }

    private void initTargetDiagram() {
        if (mapNameStates == null) {
            mapNameStates = new HashMap<>();
        }

        Observation currentObservation = null;
        List<Observation> listObservations = DbEntry.newInstance().getAllObservation();
        for (Observation o : listObservations) {
            if (o.getId().equals(observationId)) {
                currentObservation = o;
            }
        }
        if (currentObservation == null) return;

        List<ObjectStatistic> objectStatisticList = currentObservation.getStatisticList();
        if (objectStatisticList == null || objectStatisticList.isEmpty()) return;

        for (ObjectStatistic objectStatistic : objectStatisticList) {
            mapNameStates.put(objectStatistic.getName(), objectStatistic.getStates());
        }
        if (!mapNameStates.isEmpty()) {
            view.initListDiagramAdapter(mapNameStates, currentObservation.getTimeFinish(), currentObservation.getId());
        }
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
