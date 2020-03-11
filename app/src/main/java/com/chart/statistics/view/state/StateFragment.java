package com.chart.statistics.view.state;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chart.statistics.R;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.presenter.state.IStatePresenter;
import com.chart.statistics.presenter.state.StatePresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StateFragment extends Fragment implements IStateView {

    private static final String TAG_ID_STAT_OBJ = "object_statistics_tag";
    private static StateFragment instance;

    public static StateFragment newInstance(String dataStatisticsObjectId) {
        if (instance == null) {
            instance = new StateFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(TAG_ID_STAT_OBJ, dataStatisticsObjectId);
        instance.setArguments(bundle);
        return instance;
    }

    private IStatePresenter presenter;
    private FloatingActionButton fab;
    private ImageView chartImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_state, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initUi();
        initListeners();
        if (presenter == null) {
            presenter = new StatePresenter();
        }
        if (getArguments() != null) {
            String idObjectStatistics = getArguments().getString(TAG_ID_STAT_OBJ);
            presenter.onViewAttach(this, idObjectStatistics);
        }
    }

    private void initUi() {
        if (getView() == null) return;

        fab = getView().findViewById(R.id.fabAddState);
        chartImageView = getView().findViewById(R.id.ivLinearChart);
    }

    private void initListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickFab();
            }
        });
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
    }

    @Override
    public void updateUi(ObjectStatistic objectStatistic) {
        if (getView() == null) return;

        ((TextView) getView().findViewById(R.id.testtv))
                .setText(objectStatistic.toString());
    }
}
