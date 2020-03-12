package com.chart.statistics.presenter.state;

import com.chart.statistics.model.DataHolder;
import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.db.IDbEntry;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.view.state.IStateView;

import java.util.Date;

public class StatePresenter implements IStatePresenter {

    private IStateView view;
    private ObjectStatistic objectStatistic;
    private IDbEntry dbEntry;
    private String objectStatisticsId;

    @Override
    public void onViewAttach(IStateView view, String idObjectStatistics) {
        this.view = view;
        objectStatisticsId = idObjectStatistics;
        getObjectStatisticsFromDb();
    }

    private void getObjectStatisticsFromDb() {
        objectStatistic = getDbInstance().getObjectStatistics(objectStatisticsId);
        if (objectStatistic == null) return;

        refreshUi();
    }

    private void refreshUi() {
        view.updateUi(objectStatistic);
    }

    @Override
    public void onViewDetach() {
        view = null;
    }

    @Override
    public void onClickFab() {
        view.showDialog(DataHolder.newInstance().getDataStatesAsArray());
    }

    @Override
    public void onSelectState(String state) {
        getDbInstance().addStateToObjectStatistics(objectStatisticsId,
                new State(String.valueOf(new Date().getTime()), state));
        getObjectStatisticsFromDb();
    }

    @Override
    public void onChartViewWasInit() {
        if (objectStatistic == null) return;

        view.setChart(objectStatistic.getStates());
    }

    private IDbEntry getDbInstance() {
        if (dbEntry == null) {
            dbEntry = new DbEntry();
        }
        return dbEntry;
    }
}
