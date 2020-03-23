package com.chart.statistics.view.add_object;

import androidx.annotation.IdRes;

public interface IAddObjectSView {
    void showToastMessage(@IdRes int idMessage);

    void showAddStateScreen();

    void clearNameField();

    void showDataScreen();
}