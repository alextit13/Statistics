package com.chart.statistics.view.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chart.statistics.R;
import com.chart.statistics.presenter.add_data.AddDataPresenter;
import com.chart.statistics.presenter.add_data.IAddDataPresenter;
import com.chart.statistics.view.base.INavigation;
import com.chart.statistics.view.finish.FinishFragment;

import java.util.List;

public class AddDataFragment extends Fragment implements IAddDataView {

    private IAddDataPresenter presenter;
    private Spinner objectSpinner;
    private Spinner stateSpinner;
    private EditText descriptionEditText;
    private Button saveObservationButton;
    private Button finishObservationButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_data, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) {
            presenter = new AddDataPresenter();
        }
        initUi();
        initListeners();
        presenter.onViewAttach(this);
    }

    private void initUi() {
        if (getView() == null) return;

        objectSpinner = getView().findViewById(R.id.spinnerObjects);
        stateSpinner = getView().findViewById(R.id.spinnerStates);
        descriptionEditText = getView().findViewById(R.id.etSubscribeState);
        saveObservationButton = getView().findViewById(R.id.btnSaveObservation);
        finishObservationButton = getView().findViewById(R.id.btnFinishObservation);
    }

    private void initListeners() {
        saveObservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSaveObservation(
                        objectSpinner.getSelectedItemPosition(),
                        stateSpinner.getSelectedItemPosition(),
                        descriptionEditText.getText().toString()
                );
            }
        });
        finishObservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickFinishObservation();
            }
        });
    }

    @Override
    public void initObjectsSpinner(List<String> objectStatisticList) {
        if (getContext() == null) return;
        objectSpinner.setAdapter(
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, objectStatisticList)
        );
        if (!objectStatisticList.isEmpty()) {
            objectSpinner.setSelection(0);
        }
    }

    @Override
    public void showToastMessage(int idMessage) {
        Toast.makeText(getContext(), idMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearFieldsToDefault() {
        descriptionEditText.setText("");
        if (objectSpinner.getAdapter().getCount() != 0) {
            objectSpinner.setSelection(0);
        }
        if (stateSpinner.getAdapter().getCount() != 0) {
            stateSpinner.setSelection(0);
        }
    }

    @Override
    public void initStateSpinner(List<String> stateList) {
        if (getContext() == null) return;
        stateSpinner.setAdapter(
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, stateList)
        );
        if (!stateList.isEmpty()) {
            stateSpinner.setSelection(0);
        }
    }

    @Override
    public void showFinishScreen() {
        if (getActivity() == null)
            return;

        ((INavigation) getActivity()).showFragment(new FinishFragment(), getString(R.string.title_finish));
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        super.onPause();
    }
}