package com.chart.statistics.view.list_observation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chart.statistics.R;
import com.chart.statistics.presenter.list_observation.IListObservationPresenter;
import com.chart.statistics.presenter.list_observation.ListObservationPresenter;

import java.util.List;

public class ListObservationFragment extends Fragment implements IListObservationView {

    private IListObservationPresenter presenter;
    private Spinner observationsSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_observation, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) {
            presenter = new ListObservationPresenter();
        }
        initUi();
        presenter.onViewAttach(this);
    }

    private void initUi() {
        if (getView() == null) return;

        observationsSpinner = getView().findViewById(R.id.spinner_choose_observation);
    }

    @Override
    public void showToastMessage(int errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void initSpinnerAdapter(List<String> observationNames) {
        if (getContext() == null) return;

        observationsSpinner.setAdapter(
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, observationNames)
        );
        if (!observationNames.isEmpty()) {
            observationsSpinner.setSelection(0);
        }
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
    }
}
