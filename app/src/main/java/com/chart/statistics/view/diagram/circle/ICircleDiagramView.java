package com.chart.statistics.view.diagram.circle;

import com.chart.statistics.model.utils.State;

import java.util.HashMap;
import java.util.List;

public interface ICircleDiagramView {
    void initListDiagramAdapter(HashMap<String, List<State>> mapNameStates, String timeStart, String timeFinish);
}
