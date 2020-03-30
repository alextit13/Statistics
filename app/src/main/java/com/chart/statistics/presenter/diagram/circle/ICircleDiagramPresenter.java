package com.chart.statistics.presenter.diagram.circle;

import com.chart.statistics.view.diagram.circle.ICircleDiagramView;

public interface ICircleDiagramPresenter {

    void onViewAttach(ICircleDiagramView view, String observationId);

    void onViewDetach();
}
