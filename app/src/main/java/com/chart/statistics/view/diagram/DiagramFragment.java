package com.chart.statistics.view.diagram;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chart.statistics.R;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.presenter.diagram.DiagramPresenter;
import com.chart.statistics.presenter.diagram.DiagramType;
import com.chart.statistics.presenter.diagram.IDiagramPresenter;
import com.chart.statistics.view.custom.CircleDiagram;
import com.chart.statistics.view.custom.LinearDiagram;

import java.util.List;

public class DiagramFragment extends Fragment implements IDiagramView {

    public static final String TAG_ARGUMENTS_TYPE = "tag_diagram_type";
    public static final String TAG_ARGUMENTS_OBSERVATION_ID = "tag_observation_id";
    private IDiagramPresenter presenter;
    private FrameLayout diagramContainerFrameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diagram, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) {
            presenter = new DiagramPresenter();
        }
        initUi();
        presenter.onViewAttach(this, getDiagramTypeFromArguments(), getObservationIdFromArguments());
    }

    private void initUi() {
        if (getView() == null) return;

        diagramContainerFrameLayout = getView().findViewById(R.id.frameLayoutDiagramContainer);
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
    }

    private DiagramType getDiagramTypeFromArguments() {
        if (getArguments() == null) {
            return null;
        }

        String nameTypeDiagram = getArguments().getString(TAG_ARGUMENTS_TYPE);
        if (nameTypeDiagram == null || nameTypeDiagram.equals("")) {
            return null;
        }

        if (nameTypeDiagram.equals(DiagramType.Linear.name())) {
            return DiagramType.Linear;
        } else if (nameTypeDiagram.equals(DiagramType.Circle.name())) {
            return DiagramType.Circle;
        } else {
            return null;
        }
    }

    private String getObservationIdFromArguments() {
        if (getArguments() == null) {
            return null;
        }

        String idObservation = getArguments().getString(TAG_ARGUMENTS_OBSERVATION_ID);
        if (idObservation == null || idObservation.equals("")) {
            return null;
        }

        return idObservation;
    }

    @Override
    public void initLinearDiagram(List<State> states) {
        LinearDiagram linearDiagram = new LinearDiagram(getContext());
        linearDiagram.setSourceData(states);
        diagramContainerFrameLayout.addView(linearDiagram);
    }

    @Override
    public void initCircleDiagram(List<State> states) {
        CircleDiagram circleDiagram = new CircleDiagram(getContext());
        circleDiagram.setSourceData(states);
        diagramContainerFrameLayout.addView(circleDiagram);
    }
}
