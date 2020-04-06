package com.chart.statistics.view.diagram.circle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chart.statistics.R;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.presenter.diagram.circle.CircleDiagramPresenter;
import com.chart.statistics.presenter.diagram.circle.ICircleDiagramPresenter;

import java.util.HashMap;
import java.util.List;

public class CircleDiagramFragment extends Fragment implements ICircleDiagramView {

    public static final String TAG_ARGUMENTS_OBSERVATION_ID = "tag_observation_id";
    private ICircleDiagramPresenter presenter;
    private CircleDiagramAdapter adapter;
    private RecyclerView circleDiagramRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle_diagram, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) {
            presenter = new CircleDiagramPresenter();
        }
        initUi();
        presenter.onViewAttach(this, getObservationIdFromArguments());
    }

    private void initUi() {
        if (getView() == null) return;

        circleDiagramRecyclerView = getView().findViewById(R.id.circle_statistics_diagram);
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
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
    public void initListDiagramAdapter(HashMap<String, List<State>> mapNameStates, String timeStart, String timeFinish) {
        if (adapter == null) {
            adapter = new CircleDiagramAdapter(
                    mapNameStates,
                    timeStart,
                    timeFinish
            );
        } else {
            adapter.setMapNameStates(mapNameStates);
            if (circleDiagramRecyclerView.getAdapter() != null)
                circleDiagramRecyclerView.getAdapter().notifyDataSetChanged();
        }
        circleDiagramRecyclerView.setAdapter(adapter);
    }
}
