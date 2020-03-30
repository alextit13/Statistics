package com.chart.statistics.presenter.diagram.linear;

import com.chart.statistics.view.diagram.linear.ILinearDiagramView;

public class LinearDiagramPresenter implements ILinearDiagramPresenter {

    private ILinearDiagramView view;
    private String observaionId = "";

    @Override
    public void onViewAttach(ILinearDiagramView view, String idObservation) {
        this.view = view;
        observaionId = idObservation;
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
