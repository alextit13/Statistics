package com.chart.statistics.presenter.diagram.circle;

import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.Observation;
import com.chart.statistics.view.diagram.circle.ICircleDiagramView;

import java.util.List;

public class CircleDiagramPresenter implements ICircleDiagramPresenter {

    private ICircleDiagramView view;
    private String observationId;

    @Override
    public void onViewAttach(ICircleDiagramView view, String observationId) {
        this.view = view;
        this.observationId = observationId;
        initTargetDiagram();
    }

    private void initTargetDiagram() {
        Observation observation = null;
        List<Observation> listObservations = DbEntry.newInstance().getAllObservation();
        for (Observation o : listObservations) {
            if (o.getId().equals(observationId)) {
                observation = o;
            }
        }
        if (observation == null) return;

        // TODO(): Now show only first statistics. In future need replace viw to list views diagrams.
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
