package com.chart.statistics.view.diagram.linear;

import com.chart.statistics.model.utils.State;

import java.util.HashMap;
import java.util.List;

public interface ILinearDiagramView {
    void initListDiagramAdapter(HashMap<String, List<State>> mapNameStates,
                                String timeStart,
                                String timeFinish);
}
