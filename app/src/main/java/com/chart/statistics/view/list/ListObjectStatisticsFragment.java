package com.chart.statistics.view.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chart.statistics.R;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.presenter.list.IListObjectStatisticsPresenter;
import com.chart.statistics.presenter.list.ListObjectStatisticsPresenter;

import java.util.ArrayList;
import java.util.List;

public class ListObjectStatisticsFragment extends Fragment
        implements
        IListObjectStatisticsView,
        IListObjectStatisticsClickCallback {

    private static ListObjectStatisticsFragment instance;

    public static ListObjectStatisticsFragment newInstance() {
        if (instance == null) {
            instance = new ListObjectStatisticsFragment();
        }
        return instance;
    }

    private IListObjectStatisticsPresenter presenter;
    private RecyclerView recyclerView;
    private ObjectStatisticsAdapter adapter;
    private TextView tvEmptyList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_objects_statistic, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initUi();
        if (presenter == null) {
            presenter = new ListObjectStatisticsPresenter();
        }
        presenter.onViewAttach(this);
    }

    private void initUi() {
        if (getView() == null) return;

        tvEmptyList = getView().findViewById(R.id.tvEmptyObjectStatisticsList);
        tvEmptyList.setVisibility(View.GONE);

        recyclerView = getView().findViewById(R.id.rlListObjectStatistics);
        if (adapter == null) {
            adapter = new ObjectStatisticsAdapter(new ArrayList<ObjectStatistic>(), this);
        }
        recyclerView.setAdapter(adapter);

        getView().findViewById(R.id.floatingActionButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.onClickAddFab();
                    }
                });
    }

    @Override
    public void updateList(List<ObjectStatistic> list) {
        if (recyclerView.getAdapter() == null) return;

        if (list.isEmpty()) {
            setVisibleEmptyList(true);
            return;
        } else {
            setVisibleEmptyList(false);
        }

        adapter.list = list;
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void setVisibleEmptyList(boolean isVisibleEmpty) {
        if (isVisibleEmpty) {
            tvEmptyList.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmptyList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
    }

    @Override
    public void onClickObject(ObjectStatistic statisticItem) {
        // TODO: 2020-03-09
    }
}