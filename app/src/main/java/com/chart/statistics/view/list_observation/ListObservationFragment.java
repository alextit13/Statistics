package com.chart.statistics.view.list_observation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.chart.statistics.R;
import com.chart.statistics.presenter.list_observation.IListObservationPresenter;
import com.chart.statistics.presenter.list_observation.ListObservationPresenter;

import java.util.List;

public class ListObservationFragment extends Fragment implements IListObservationView {

    private IListObservationPresenter presenter;
    private Spinner observationsSpinner;
    private Button listButton, linearDiagramButton, circleDiagramButton, csvButton;

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
        initListeners();
        presenter.onViewAttach(this);
    }

    private void initUi() {
        if (getView() == null) return;

        observationsSpinner = getView().findViewById(R.id.spinner_choose_observation);
        listButton = getView().findViewById(R.id.list_observation_btn);
        linearDiagramButton = getView().findViewById(R.id.linear_diagram_btn);
        circleDiagramButton = getView().findViewById(R.id.circle_diagram_btn);
        csvButton = getView().findViewById(R.id.csv_btn);
    }

    private void initListeners() {
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickListButton();
            }
        });
        linearDiagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickLinearDiagramButton();
            }
        });
        circleDiagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickCircleDiagramButton();
            }
        });
        csvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickCsvButton();
            }
        });
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
    public void showListObservationChooser(final List<String> observationList) {
        if (getContext() == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.lbl_observation);
        builder.setMessage(R.string.msg_choose_observation);
        ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, observationList);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onClickObservationInDialog(observationList.get(i));
                dialogInterface.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public void setObservationInSpinner(int position) {
        if (observationsSpinner.getAdapter() == null ||
                observationsSpinner.getAdapter().getCount() == 0) return;

        observationsSpinner.setSelection(position);
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
    }
}
