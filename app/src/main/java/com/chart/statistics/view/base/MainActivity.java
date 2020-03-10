package com.chart.statistics.view.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.chart.statistics.R;
import com.chart.statistics.view.list.ListObjectStatisticsFragment;

public class MainActivity extends AppCompatActivity implements INavigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragment(ListObjectStatisticsFragment.newInstance());
    }

    @Override
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flFragmentContainer, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (!getSupportFragmentManager().getFragments().isEmpty() &&
                getSupportFragmentManager().getFragments().size() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
