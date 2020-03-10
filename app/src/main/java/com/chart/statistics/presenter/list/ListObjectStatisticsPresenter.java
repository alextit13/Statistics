package com.chart.statistics.presenter.list;

import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.db.IDbEntry;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.view.list.IListObjectStatisticsView;

import java.util.List;

public class ListObjectStatisticsPresenter implements IListObjectStatisticsPresenter {

    private IListObjectStatisticsView view;
    private IDbEntry dbEntry;

    @Override
    public void onViewAttach(IListObjectStatisticsView view) {
        this.view = view;
        takeObjectsFromDb();
    }

    private void takeObjectsFromDb(){
        List<ObjectStatistic> objectStatisticList = getDbInstance().getAllObjectStatistics();
        view.updateList(objectStatisticList);
    }

    @Override
    public void onViewDetach() {
        view = null;
    }

    @Override
    public void onClickAddFab() {
        // TODO: 2020-03-09
    }

    private IDbEntry getDbInstance(){
        if (dbEntry == null) {
            dbEntry = new DbEntry();
        }
        return dbEntry;
    }
}