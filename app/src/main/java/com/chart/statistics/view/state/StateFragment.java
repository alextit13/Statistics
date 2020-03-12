package com.chart.statistics.view.state;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chart.statistics.R;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.presenter.state.IStatePresenter;
import com.chart.statistics.presenter.state.StatePresenter;
import com.chart.statistics.view.base.INavigation;
import com.chart.statistics.view.custom.LinearDiagram;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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
    private LinearDiagram chartLinearDiagram;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_state, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initUi();
        if (presenter == null) {
            presenter = new StatePresenter();
        }
        if (getArguments() != null) {
            String idObjectStatistics = getArguments().getString(TAG_ID_STAT_OBJ);
            presenter.onViewAttach(this, idObjectStatistics);
        }
        initListeners();
    }

    private void initUi() {
        if (getView() == null) return;

        fab = getView().findViewById(R.id.fabAddState);
        chartLinearDiagram = getView().findViewById(R.id.ivLinearChart);
    }

    private void initListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickFab();
            }
        });
        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (chartLinearDiagram.getHeight() != 0) {
                    chartLinearDiagram.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
                    presenter.onChartViewWasInit();
                }
            }
        };
        chartLinearDiagram.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    @Override
    public void showDialog(final String [] arrayStates) {
        if (getContext() == null)
            return;

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_select_state);
        dialog.setTitle("Состояние объекта");

        final ListView lst = dialog.findViewById(R.id.listViewStates);

        lst.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_checked, android.R.id.text1,
                arrayStates));

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int item, long arg3) {
                presenter.onSelectState(arrayStates[item]);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void closeView() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void setChart(List<State> states) {
        chartLinearDiagram.setSourseData(states);
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateUi(ObjectStatistic objectStatistic) {
        if (getView() == null) return;

        String objectStatisticsState = "";
        if (!objectStatistic.getStates().isEmpty()) {
            objectStatisticsState = " (" + objectStatistic.getStates().get(
                    objectStatistic.getStates().size() - 1
            ).getName() + ")";
        }

        ((TextView) getView().findViewById(R.id.tvNameObjectStatistics))
                .setText(objectStatistic.getName() + objectStatisticsState);
    }
}
