package com.chart.statistics.view.data;

import androidx.annotation.StringRes;

import java.util.List;

public interface IAddDataView {
    void initObjectsSpinner(List<String> objectStatisticList);

    void initStateSpinner(List<String> stateList);

    void showToastMessage(@StringRes int idMessage);

    void clearFieldsToDefault();

    void showFinishScreen();
}
