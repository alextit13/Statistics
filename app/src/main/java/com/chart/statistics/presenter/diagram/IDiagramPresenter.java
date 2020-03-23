package com.chart.statistics.presenter.diagram;

import com.chart.statistics.view.diagram.IDiagramView;

public interface IDiagramPresenter {

    void onViewAttach(IDiagramView view, DiagramType diagramType, String observationId);

    void onViewDetach();
}
