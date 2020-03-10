package com.chart.statistics.presenter.add;

import com.chart.statistics.R;
import com.chart.statistics.model.App;
import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.db.IDbEntry;
import com.chart.statistics.model.utils.ObjectStatistic;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.view.add.IAddView;

import java.util.ArrayList;
import java.util.Date;

public class AddPresenter implements IAddPresenter {

    private IDbEntry dbEntry;
    private IAddView view;

    @Override
    public void onViewAttach(IAddView view) {
        this.view = view;
    }

    @Override
    public void onViewDetach() {
        view = null;
    }

    @Override
    public void onClickConfirm(String name, String description) {
        if (name.isEmpty()) {
            view.showToastMessage(App.instance.getString(R.string.msg_enter_name));
            return;
        }
        if (description.isEmpty()) {
            view.showToastMessage(App.instance.getString(R.string.msg_enter_description));
            return;
        }
        generateObjectStatistics(name, description);
    }

    /**
     * description parameter or some other parameter
     * like a description you may use for create additional
     * functions.
     * */
    private void generateObjectStatistics(String name, String description) {
        String timestamp = String.valueOf(new Date().getTime());
        ObjectStatistic objectStatistic = new ObjectStatistic(
                timestamp,
                name,
                new ArrayList<State>()
        );
        getDbInstance().insertObjectStatistics(objectStatistic);
        view.closeCurrentScreen();
    }

    @Override
    public void onClickCancel() {
        view.closeCurrentScreen();
    }

    private IDbEntry getDbInstance() {
        if (dbEntry == null) {
            dbEntry = new DbEntry();
        }
        return dbEntry;
    }
}
