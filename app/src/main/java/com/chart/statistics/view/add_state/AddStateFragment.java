package com.chart.statistics.view.add_state;

import android.annotation.SuppressLint;
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
import com.chart.statistics.presenter.add_state.AddStatePresenter;
import com.chart.statistics.presenter.add_state.IAddStatePresenter;

public class AddStateFragment extends Fragment implements IAddStateView {

    private IAddStatePresenter presenter;

    private EditText nameEditText;
    private Button saveBtn;
    private Button deleteBtn;
    private Button nextBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_state, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) {
            presenter = new AddStatePresenter();
        }
        initUi();
        initListeners();
        presenter.onViewAttach(this);
    }

    private void initUi() {
        if (getView() == null) return;

        nameEditText = getView().findViewById(R.id.etNameState);
        saveBtn = getView().findViewById(R.id.btnSaveState);
        deleteBtn = getView().findViewById(R.id.btnDeleteState);
        nextBtn = getView().findViewById(R.id.btnNextState);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSave(nameEditText.getText().toString());
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickDelete(nameEditText.getText().toString());
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickNext();
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public void showToastMessage(int idMessage) {
        Toast.makeText(getContext(), idMessage, Toast.LENGTH_LONG).show();
    }

    private void initListeners() {

    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        presenter = null;
        super.onPause();
    }
}
