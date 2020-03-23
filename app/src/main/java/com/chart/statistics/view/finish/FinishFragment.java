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
import com.chart.statistics.view.base.INavigation;
import com.chart.statistics.view.list_observation.ListObservationFragment;

public class FinishFragment extends Fragment implements IFinishView {

    private IFinishPresenter presenter;
    private EditText nameObservationEditText;
    private Button saveButton;

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
        initUi();
        initListeners();
        presenter.onViewAttach(this);
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
    public void openListObservationScreen() {
        if (getActivity() == null) {
            return;
        }

        ((INavigation) getActivity()).showFragment(new ListObservationFragment(),
                getString(R.string.lbl_observation));
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
