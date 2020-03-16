package com.chart.statistics.view.add_state;

import androidx.annotation.IdRes;

public interface IAddStateView {
    void showToastMessage(@IdRes int idMessage);

    void showAddDataScreen();

    void clearStateFiled();
}
