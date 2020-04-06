package com.chart.statistics.view.diagram.linear;

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
import com.chart.statistics.presenter.diagram.linear.ILinearDiagramPresenter;
import com.chart.statistics.presenter.diagram.linear.LinearDiagramPresenter;

import java.util.HashMap;
import java.util.List;

import static com.chart.statistics.view.diagram.circle.CircleDiagramFragment.TAG_ARGUMENTS_OBSERVATION_ID;

public class LinearDiagramFragment extends Fragment implements ILinearDiagramView {

    private ILinearDiagramPresenter presenter;
    private RecyclerView linearDiagramRecycleView;
    private LinearDiagramAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_linear_diagram, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) {
            presenter = new LinearDiagramPresenter();
        }
        initUi();
        presenter.onViewAttach(this, getObservationIdFromBundle());
    }

    private void initUi(){
        if (getView() == null) return;

        linearDiagramRecycleView = getView().findViewById(R.id.recycler_view_linear_diagram);
    }

    private String getObservationIdFromBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) return "";

        return bundle.getString(TAG_ARGUMENTS_OBSERVATION_ID);
    }

    @Override
    public void initListDiagramAdapter(
            HashMap<String, List<State>> mapNameStates,
            String timeStart,
            String timeFinish) {
        if (adapter == null) {
            adapter = new LinearDiagramAdapter(mapNameStates, timeStart, timeFinish);
        }
        linearDiagramRecycleView.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
    }
}
