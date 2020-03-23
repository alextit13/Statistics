package com.chart.statistics.view.diagram;

import com.chart.statistics.model.utils.State;

import java.util.List;

public interface IDiagramView {
    void initLinearDiagram(List<State> states);

    void initCircleDiagram(List<State> states);
}
