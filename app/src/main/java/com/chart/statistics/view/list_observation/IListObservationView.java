package com.chart.statistics.view.list_observation;

import java.util.List;

public interface IListObservationView {

    void showToastMessage(int errorMessage);

    void initSpinnerAdapter(List<String> observationNames);

    void showListObservationChooser(List<String> observationList);

    void setObservationInSpinner(int position);
}
