package com.chart.statistics.presenter.add_state;

import android.annotation.SuppressLint;

import com.chart.statistics.R;
import com.chart.statistics.model.db.DbEntry;
import com.chart.statistics.model.utils.State;
import com.chart.statistics.view.add_state.IAddStateView;

import java.util.Date;

public class AddStatePresenter implements IAddStatePresenter {

    private IAddStateView view;

    @Override
    public void onViewAttach(IAddStateView view) {
        this.view = view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClickSave(String name) {
        if (name.equals("")) {
            view.showToastMessage(R.string.msg_enter_name);
            return;
        }
        DbEntry.newInstance().insertState(
                new State(String.valueOf(new Date().getTime()),
                        name)
        );
        view.showToastMessage(R.string.msg_save_success);
        view.clearStateFiled();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClickDelete(String name) {
        if (name.equals("")) {
            view.showToastMessage(R.string.msg_enter_name);
            return;
        }
        boolean result = DbEntry.newInstance().deleteStateByName(name);
        if (result) {
            view.showToastMessage(R.string.msg_enter_correct_name);
        } else {
            view.showToastMessage(R.string.msg_delete_success);
            view.clearStateFiled();
        }
    }

    @Override
    public void onClickNext() {
        view.showAddDataScreen();
    }

    @Override
    public void onViewDetach() {
        view = null;
    }
}
