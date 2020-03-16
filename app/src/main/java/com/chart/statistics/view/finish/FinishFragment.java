package com.chart.statistics.view.finish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chart.statistics.R;
import com.chart.statistics.presenter.finish.FinishPresenter;
import com.chart.statistics.presenter.finish.IFinishPresenter;

public class FinishFragment extends Fragment implements IFinishView {

    public static final String KEY_OBSERVATION_ID = "key_observation_id";
    private IFinishPresenter presenter;
    private EditText nameObservationEditText;
    private Button saveButton;
    private String observationId = "-1";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_finish, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) {
            presenter = new FinishPresenter();
        }
        initArguments();
        initUi();
        initListeners();
        presenter.onViewAttach(this, observationId);
    }

    private void initArguments() {
        if (getArguments() == null) {
            return;
        }

        observationId = getArguments().getString(KEY_OBSERVATION_ID);
    }

    private void initUi() {
        if (getView() == null) {
            return;
        }

        nameObservationEditText = getView().findViewById(R.id.etNameObservation);
        saveButton = getView().findViewById(R.id.btnSaveObservation);
    }

    private void initListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSave(nameObservationEditText.getText().toString());
            }
        });
    }

    @Override
    public void showToastMessage(int idMessage) {
        Toast.makeText(getContext(), idMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
    }
}
