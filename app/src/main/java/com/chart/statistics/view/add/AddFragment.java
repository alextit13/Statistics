package com.chart.statistics.view.add;

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
import com.chart.statistics.presenter.add.AddPresenter;
import com.chart.statistics.presenter.add.IAddPresenter;
import com.chart.statistics.view.base.INavigation;

public class AddFragment extends Fragment implements IAddView {
    private static AddFragment instance;

    public static AddFragment newInstance() {
        if (instance == null) {
            instance = new AddFragment();
        }
        return instance;
    }

    private IAddPresenter presenter;

    private EditText nameEditText;
    private EditText describeEditText;
    private Button confirmButton;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) {
            presenter = new AddPresenter();
        }
        presenter.onViewAttach(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUi();
        initListeners();
    }

    private void initUi() {
        if (getView() == null) return;
        nameEditText = getView().findViewById(R.id.etObjectStatisticsName);
        describeEditText = getView().findViewById(R.id.etObjectStatisticsDescribe);
        confirmButton = getView().findViewById(R.id.btnConfirm);
        cancelButton = getView().findViewById(R.id.btnCancel);
    }

    private void initListeners() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickConfirm(nameEditText.getText().toString(), describeEditText.getText().toString());
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickCancel();
            }
        });
    }

    @Override
    public void closeCurrentScreen() {
        if (getActivity() == null) return;
        getActivity().onBackPressed();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
    }
}
