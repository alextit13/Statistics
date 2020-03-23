package com.chart.statistics.presenter.diagram;

import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.view.diagram.IDiagramView;

import java.util.List;

public class DiagramPresenter implements IDiagramPresenter {

    private IDiagramView view;
    private DiagramType diagramType;
    private String observationId;

    @Override
    public void onViewAttach(IDiagramView view, DiagramType diagramType, String observationId){
        this.view = view;
        this.diagramType = diagramType;
        this.observationId = observationId;
        initTargetDiagram();
    }

    private void initTargetDiagram(){
        if (diagramType == null) return;

        Observation observation = null;
        List<Observation> listObservations = DbEntry.newInstance().getAllObservation();
        for (Observation o : listObservations) {
            if (o.getId().equals(observationId)) {
                observation = o;
            }
        }
        if (observation == null) return;

        // TODO(): Now show only first statistics. In future need replace viw to list views diagrams.
        if (diagramType == DiagramType.Linear) {
            view.initLinearDiagram(observation.getStatisticList().get(0).getStates());
        } else if (diagramType == DiagramType.Circle) {
            view.initCircleDiagram(observation.getStatisticList().get(0).getStates());
        }
    }

    @Override
    public void onViewDetach(){
        view = null;
    }
}
