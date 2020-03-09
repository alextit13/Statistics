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
                .replace(R.id.flFragmentContainer, fragment, fragment.getTag())
                .addToBackStack(fragment.getTag())
                .commit();
    }

    @Override
    public void backPressed() {
        if (!getSupportFragmentManager().getFragments().isEmpty() &&
                getSupportFragmentManager().getFragments().size() == 1) {
            finish();
        }
    }
}
