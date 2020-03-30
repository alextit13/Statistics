package com.chart.statistics.view.add_object;

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
import com.chart.statistics.presenter.add_object.AddObjectPresenter;
import com.chart.statistics.presenter.add_object.IAddObjectSPresenter;
import com.chart.statistics.view.add_state.AddStateFragment;
import com.chart.statistics.view.base.INavigation;
import com.chart.statistics.view.data.AddDataFragment;
import com.chart.statistics.view.list_observation.ListObservationFragment;

public class AddObjectSFragment extends Fragment implements IAddObjectSView {

    private IAddObjectSPresenter presenter;

    private EditText nameEditText;
    private Button saveBtn;
    private Button deleteBtn;
    private Button nextBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_object_s, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) {
            presenter = new AddObjectPresenter();
        }
        initUi();
        initListeners();
        presenter.onViewAttach(this);
    }

    private void initUi() {
        if (getView() == null) return;

        nameEditText = getView().findViewById(R.id.etNameObjectS);
        saveBtn = getView().findViewById(R.id.btnSave);
        deleteBtn = getView().findViewById(R.id.btnDelete);
        nextBtn = getView().findViewById(R.id.btnNext);
    }

    private void initListeners() {
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

        getView().findViewById(R.id.btnTest)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((INavigation) getActivity())
                                .showFragment(new ListObservationFragment(),
                                        getString(R.string.title_add_object));
                    }
                });
    }

    @Override
    public void clearNameField() {
        nameEditText.setText("");
    }

    @SuppressLint("ResourceType")
    @Override
    public void showToastMessage(int idMessage) {
        Toast.makeText(getContext(), idMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAddStateScreen() {
        if (getActivity() == null) return;

        ((INavigation) getActivity()).showFragment(new AddStateFragment(), getString(R.string.title_add_state));
    }

    @Override
    public void showDataScreen() {
        if (getActivity() == null) return;

        ((INavigation) getActivity()).showFragment(new AddDataFragment(), getString(R.string.lbl_observation));
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        super.onPause();
    }
}
