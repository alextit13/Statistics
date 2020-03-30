package com.chart.statistics.presenter.diagram.linear;

import com.chart.statistics.view.diagram.linear.ILinearDiagramView;

public interface ILinearDiagramPresenter {
    void onViewAttach(ILinearDiagramView view, String idObservation);
    void onViewDetach();
}
